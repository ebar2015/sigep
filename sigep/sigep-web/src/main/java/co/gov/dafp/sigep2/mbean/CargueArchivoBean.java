package co.gov.dafp.sigep2.mbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.view.ProcesoArchivoDTO;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.produces.ProcesoArchivoProduces;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2NegocioException;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.xml.Xml;
import co.gov.dafp.sigep2.util.xml.elemento.Archivo;
import co.gov.dafp.sigep2.util.xml.elemento.TipoCargue;

@Named
@ConversationScoped
public class CargueArchivoBean extends BaseBean implements Serializable {

	@Inject
	private ProcesoArchivoProduces procesoArchivoProduces;

	private ProcesoArchivoDTO procesoArchivo;
	private StreamedContent formato;
	private StreamedContent salida;

	private Date fechaCargue;
	transient List<UploadedFile> files;
	transient UploadedFile file;
	private Xml xml;
	private String extensiones;

	private static final long serialVersionUID = -35190269375261143L;

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	@PostConstruct
	public void init() {
		this.procesoArchivo = null;
		this.fechaCargue = Calendar.getInstance().getTime();
		this.file = null;
		this.files = new LinkedList<>();
		this.procesoArchivoProduces.setNombresPlantilla(ConfigurationBundleConstants
				.getListString(ConfigurationBundleConstants.CNS_ARCHIVO_PLANTILLAS_ADMINISTRACION_USUARIOS));
	}

	@Override
	public String persist() {
		try {
			if (this.files.isEmpty()) {
				throw new SIGEP2SistemaException(MessagesBundleConstants.MSG_ARCHIVO_NOMBRE_INVALIDO);
			}
			this.setProgress(25);
			List<File> archivosCargue = new LinkedList<>();
			for (UploadedFile fileUpload : this.files) {
				File archivoCargue = new File(
						ConfigurationBundleConstants.getRutaArchivo(this.xml.getArchivo().get(0).getRutaEntrada())
								+ fileUpload.getFileName());
				if (archivoCargue.exists()) {
					archivosCargue.add(archivoCargue);
				} else {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							"El archivo no existe : " + archivoCargue.getName());
					this.setProgress(0);
					return null;
				}
			}
			this.setProgress(30);
			for (File file : archivosCargue) {
				String nombreArchivo = file.getName();
				if (IngresoSistemaDelegate.findByNombreArchivoCargue(nombreArchivo) != null) {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_REGISTRO_EXISTE, getLocale())
									+ ": " + nombreArchivo);
					this.setProgress(0);
					return null;
				}
			}

			this.setProgress(40);
			if (!archivosCargue.isEmpty()) {
				IngresoSistemaDelegate.init(this.procesoArchivo, xml.getArchivo().get(0), getEntidadUsuario().getId(),
						this.getUsuarioSesion(), getRolAuditoria().getId(), archivosCargue, fechaCargue, "", true);
				this.setProgress(100);
			}
			String rutaArchivo = archivosCargue.get(0).getName();
			this.downloadSalida(rutaArchivo);

			this.init();
			// this.conversation.end();
			String path = FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo();
			path = path.substring(path.lastIndexOf(FileUtil.SLASH) + 1, path.lastIndexOf("."));
			return path + "?faces-redirect=true&dialog=" + MessagesBundleConstants.MSG_INFO_CARGA_ARCHIVO;
		} catch (Exception e) {
			logger.log().error("void persist()", e);
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
		}
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		try {
			if (this.conversation.isTransient()) {
				this.conversation.begin();
				this.conversation.setTimeout(timeOut);
			}
		} catch (NonexistentConversationException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_URL_INVALID);
			return;
		}

		this.setProgress(0);
		String path = FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo();
		try {
			path = path.substring(path.lastIndexOf(FileUtil.SLASH) + 1, path.lastIndexOf("."));
			this.procesoArchivo = IngresoSistemaDelegate.getProcesosArchivo(Arrays.asList(path + FileUtil.XML)).get(0);
			seleccionarPlantilla();
		} catch (SIGEP2SistemaException e) {
			throw new NotSupportedException();
		}
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public ProcesoArchivoDTO getProcesoArchivo() {
		return procesoArchivo;
	}

	public void setProcesoArchivo(ProcesoArchivoDTO procesoArchivo) {
		this.procesoArchivo = procesoArchivo;
	}

	public Date getFechaCargue() {
		return fechaCargue;
	}

	public void setFechaCargue(Date fechaCargue) {
		this.fechaCargue = fechaCargue;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Xml getXml() {
		return xml;
	}

	public void setXml(Xml xml) {
		this.xml = xml;
	}

	public String getExtensiones() {
		return extensiones;
	}

	public void setExtensiones(String extensiones) {
		this.extensiones = extensiones;
	}

	public List<UploadedFile> getFiles() {
		return files;
	}

	public void setFiles(List<UploadedFile> files) {
		this.files = files;
	}

	// public EntidadDTO getEntidadUsuario() {
	// return entidadUsuario;
	// }
	//
	// public void setEntidadUsuario(EntidadDTO entidadUsuario) {
	// this.entidadUsuario = entidadUsuario;
	// }

	public void seleccionarPlantilla() {
		try {
			xml = Xml.getEstructura(Xml.getXml(this.procesoArchivo.getNombrePlantilla()));
			if (this.xml.getTipoCargue() != null
					&& TipoCargue.AUTOMATICO.value().equals(this.xml.getTipoCargue().value())) {
				throw new SIGEP2NegocioException(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_SOLO_CARGUE_AUTOMATICO, getLocale()));
			}
			this.files = new LinkedList<>();
			this.extensiones = "";
			for (Archivo archivo : xml.getArchivo()) {
				this.extensiones = (this.extensiones + archivo.getExtension().value().toLowerCase().replace(".", ""))
						+ "|" + (this.extensiones + archivo.getExtension().value().toUpperCase().replace(".", ""));
				if (xml.getArchivo().indexOf(archivo) < (xml.getArchivo().size() - 1)) {
					this.extensiones = this.extensiones + "|";
				}
			}
			this.extensiones = "/(\\.|\\/)(" + this.extensiones + ")$/";
			this.downloadFormato();
		} catch (Exception e) {
			logger.error(MessagesBundleConstants.DLG_PROCESO_FALLIDO, e);
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {

		this.files = new LinkedList<>();

		try {
			if (this.xml.getTipoCargue() != null
					&& TipoCargue.AUTOMATICO.value().equals(this.xml.getTipoCargue().value())) {
				throw new SIGEP2NegocioException(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_SOLO_CARGUE_AUTOMATICO, getLocale()));
			}
			this.file = event.getFile();
			if (file != null) {
				try {
					File rutaCargue = new File(
							ConfigurationBundleConstants.getRutaArchivo(this.xml.getArchivo().get(0).getRutaEntrada()));
					if (!rutaCargue.exists() && !rutaCargue.mkdirs()) {
						throw new SIGEP2SistemaException(MessagesBundleConstants.MSG_ARCHIVO_ARCHIVO_NO_EXISTE);
					} else {
						String nombreArchivoCargado = rutaCargue + FileUtil.getFileSeparator() + file.getFileName()
								+ (ConfigurationBundleConstants
										.getBoolean(ConfigurationBundleConstants.CNS_COMPRIMIR_ARCHIVO_CARGUE)
												? ConfigurationBundleConstants.getExtensionZip() : "");
						File archivoCargado = new File(nombreArchivoCargado);
						if (archivoCargado.exists()) {
							archivoCargado.delete();
						}
					}
					String nombreArchivo = ConfigurationBundleConstants
							.getRutaArchivo(this.xml.getArchivo().get(0).getRutaEntrada()) + file.getFileName();
					String nombreArchivoCargado = ConfigurationBundleConstants
							.getRutaArchivo(this.xml.getArchivo().get(0).getRutaSalida());
					if (nombreArchivoCargado == null) {
						throw new SIGEP2NegocioException(MessagesBundleConstants
								.getStringMessagesBundle(MessagesBundleConstants.MSG_RUTA_ARCHIVO_CARGADO_NO_EXISTE, getLocale()));
					}
					nombreArchivoCargado = nombreArchivoCargado + file.getFileName()
							+ (ConfigurationBundleConstants
									.getBoolean(ConfigurationBundleConstants.CNS_COMPRIMIR_ARCHIVO_CARGUE)
											? ConfigurationBundleConstants.getExtensionZip() : "");
					File archivoCargado = new File(nombreArchivoCargado);
					if (archivoCargado.exists()) {
						throw new SIGEP2NegocioException(MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.DLG_REGISTRO_EXISTE, getLocale()) + ": " + file.getFileName());
					}
					file.write(nombreArchivo);
				} catch (Exception e) {
					throw e;
				}
				this.files.add(file);
			} else {
				throw new SIGEP2NegocioException(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_CARGA_ARCHIVO, getLocale()));
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
			logger.log().error("void handleFileUpload(FileUploadEvent event)", e);
		}
	}

	public void onFileSelect(SelectEvent event) {
		this.file = (UploadedFile) event.getObject();
	}

	public void onFileUnselect(UnselectEvent event) {
		logger.info(MessagesBundleConstants.DLG_PROCESO_EXITOSO, event.getObject());
		this.file = null;
	}

	public void quitarArchivo() {
		this.files.remove(this.file);
		this.file = null;
		mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
				MessagesBundleConstants.DLG_PROCESO_EXITOSO);
	}

	public void downloadFormato() {
		if (this.procesoArchivo == null) {
			return;
		}
		String rutaArchivo = ConfigurationBundleConstants
				.getRutaArchivoApp(ConfigurationBundleConstants.CNS_FORMATOS_CARGUE)
				+ procesoArchivo.getNombrePlantilla().replace(FileUtil.XML, FileUtil.XLSX);

		InputStream stream;
		try {
			stream = new URL(rutaArchivo).openStream();
			formato = new DefaultStreamedContent(stream, FileUtil.XLSX_CONTENT_TYPE,
					procesoArchivo.getNombrePlantilla().replace(FileUtil.XML, FileUtil.XLSX));
		} catch (Exception e) {
			logger.error("void downloadFormato()", e);
		}
	}

	private void downloadSalida(String rutaArchivo) throws IOException {
		if (rutaArchivo == null) {
			return;
		}
		File initialFile = new File(
				ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_ARCHIVO_SALIDA)
						+ FileUtil.BACKSLASH + getEntidadUsuario().getId() + FileUtil.BACKSLASH + rutaArchivo);
		if (initialFile.exists()) {
			InputStream stream;
			try {
				stream = new FileInputStream(initialFile);
				salida = new DefaultStreamedContent(stream, FileUtil.XLSX_CONTENT_TYPE, rutaArchivo);
			} catch (FileNotFoundException e) {
				logger.error("void downloadSalida(String rutaArchivo)", e);
			}
		} else {
			salida = null;
			finalizarConversacion(false, MessagesBundleConstants.MSG_ARCHIVO_NOMBRE_INVALIDO, null);
		}
	}

	public StreamedContent getFormato() {
		return formato;
	}

	public void setFormato(StreamedContent formato) {
		this.formato = formato;
	}

	public StreamedContent getSalida() {
		return salida;
	}

	public void setSalida(StreamedContent salida) {
		this.salida = salida;
	}
}
