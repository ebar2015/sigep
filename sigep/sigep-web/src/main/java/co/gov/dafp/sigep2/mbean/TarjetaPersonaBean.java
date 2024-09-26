package co.gov.dafp.sigep2.mbean;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.google.gson.Gson;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.ImageBase64Utils;
import co.gov.dafp.sigep2.utils.ErrorMensajes;

@Named
@ViewScoped
@ManagedBean
public class TarjetaPersonaBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 3695472896249884567L;
	private PersonaExt persona;
	private String nombreUsuario, nombreTipoDocumento, nombreGenero, correoElectronico;

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}

	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}

	public String getNombreGenero() {
		return nombreGenero;
	}

	public void setNombreGenero(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	long codPersona = getUsuarioSesion().getCodPersona();

	public PersonaExt getPersona() {
		return persona;
	}

	public void setPersona(PersonaExt persona) {
		this.persona = persona;
	}

	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String idPersona = request.getParameter("id");
        if(idPersona != null) {
        	codPersona = Long.valueOf(idPersona);
        }
		persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		if(persona!=null ) {
			nombreUsuario 		= persona.getNombreUsuario()!=null ?persona.getNombreUsuario().toLowerCase():"";
			nombreTipoDocumento = persona.getNombreTipoDocuento()!=null ?persona.getNombreTipoDocuento().toLowerCase():"";
			nombreGenero 		= persona.getNombreGenero()!=null ? persona.getNombreGenero().toLowerCase() : "";
			if(persona.getCorreoElectronico()!=null)
				correoElectronico = persona.getCorreoElectronico().toLowerCase();
		}
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	public String persist() {
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public void imagenUpload(FileUploadEvent e) throws IOException {
		UploadedFile imagen = e.getFile();
		String ext 			= FilenameUtils.getExtension(imagen.getFileName());
		Long tam 			= this.obtenerTamanio(2017L);
		String response ="";
		if (e.getFile().getSize() > tam) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"No es posible cargar la imagen, Supera el tamaño máximo permitido : " + (tam / 1000) + "KB");
			return;
		}
		UploadedFile uploadedFile = e.getFile();
		byte[] bytes = IOUtils.toByteArray(uploadedFile.getInputstream());
		if (this.validarDimensiones(bytes))
			return;
		if (ext.toUpperCase().equals("JPEG") || ext.toUpperCase().equals("JPG")) {
			bytes = null;
			try {
				if (null != imagen) {
					bytes = imagen.getContents();
					String filename = persona.getNumeroIdentificacion() + "." + ext;
					String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_FOTO_USUARIO)
							+ filename;
					String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(bytes);
					stream.close();
					ErrorMensajes resp=null;
					
					if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("0")){
						response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
			                    ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_FOTO_USUARIO,
			                    WebUtils.TP_FOTO_USUARIO, persona.getNumeroIdentificacion());
						Gson gson = new Gson();
						resp = gson.fromJson(response, ErrorMensajes.class);
					}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("1")){/*Operacion cliente windows*/
						resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_FOTO_USUARIO, WebUtils.TP_FOTO_USUARIO, filePath, persona.getNumeroIdentificacion());			
					}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente windows*/
						resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_FOTO_USUARIO, WebUtils.TP_FOTO_USUARIO, filePath, persona.getNumeroIdentificacion());	
					}
					
					if(!resp.isError()) {
						persona.setFotoUrl(resp.getUrlArchivo());
					}else{
						logger.log().error("******************** documentoUpload() ********************", response);
						mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants
										.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
					}
					persona.setImagenUsuario(ImageBase64Utils.converterImgBase64(ruta));
					ComunicacionServiciosHV.updatePersonaTarjeta(persona);
				}

				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						"Imagen cargada correctamente");
			} catch (Exception e2) {
				logger.log().error("imagenUpload()", e2.getMessage());
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
			}
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"No es posible cargar la imagen, no tiene el formato adecuado, seleccione una imagen con formato JPG");
		}
	}

	public Long obtenerTamanio(Long id) {
		try {
			Parametrica param = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(id));
			if (param.getFlgEstado() != 1)
				return (id == 2017) ? 500000L : 500;
			return Long.parseLong(param.getValorParametro());
		} catch (Exception ex) {
			logger.error("Error en obtenerTamanio(Long id) TarjetaPersonaBean", ex);
			return (id == 2017) ? 500000L : 500;
		}
	}

	private boolean validarDimensiones(byte[] bytes) {
		try {
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
			Long alto = this.obtenerTamanio(2018L);
			Long ancho = this.obtenerTamanio(2019L);
			if (bufferedImage.getHeight() > alto || bufferedImage.getWidth() > ancho) {
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						"Resolución máxima de la imagen excedida. Alto máximo permitido: " + alto
								+ "px ancho máximo permitido: " + ancho + "px");
				return true;
			}
			return false;
		} catch (Exception ex) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"No es posible cargar este archivo. Elija un archivo en formato JPG");
			logger.error("validarDimensiones(byte[] bytes)", ex);
			return true;
		}
	}
}