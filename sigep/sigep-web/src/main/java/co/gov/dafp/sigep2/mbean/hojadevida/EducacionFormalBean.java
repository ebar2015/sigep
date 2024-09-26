package co.gov.dafp.sigep2.mbean.hojadevida;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.google.gson.Gson;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entities.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.entities.InstitucionEducativa;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.DatosPersonalesBean;
import co.gov.dafp.sigep2.mbean.ext.EducacionFormalExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.IdiomaPersonaExt;
import co.gov.dafp.sigep2.mbean.ext.InstitucionEducativaExt;
import co.gov.dafp.sigep2.mbean.ext.OtroConocimientoExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;
import co.gov.dafp.sigep2.utils.ErrorMensajes;

@Named
@ViewScoped
@ManagedBean
public class EducacionFormalBean extends BaseBean implements Serializable {
	
	private static final long serialVersionUID = 3244515669584860404L;

	private PersonaExt persona;// recibe el objeto de PersonaExt
	private EducacionFormalExt educacionFormal;// recibe el objeto de EducacionFormalExt
	private OtroConocimientoExt otroConocimiento;// recibe el objeto de OtroConocimientoExt
	private IdiomaPersonaExt idiomaPersona;// recibe el objeto de IdiomaPersonaExt
	private UploadedFile archivoSoporte = null;// recibe el objeto de UploadedFile
	private HistoricoModificacionHojaVida modificacionHojaVida;// recibe el objeto de HistoricoModificacionHojaVida
	private EducacionFormalExt edFormalEditado;// recibe el objeto de EducacionFormalExt
	private boolean lbUpload = false;//Verifica si el archivo de educacion formal si se cargo

	private List<EducacionFormalExt> listaEducacionFormalPorPersona;// recibe un listado de objetos de EducacionFormalExt
	private List<OtroConocimientoExt> listaOtroConocimientoPorPersona;// recibe un listado de objetos de OtroConocimientoExt
	private List<IdiomaPersonaExt> listaIdiomasPorPersona;// recibe un listado de objetos de IdiomaPersonaExt

	private Boolean habilitadoFormularioEducacion;// habilita el formulario de educacion
	private Boolean habilitadoFormularioCertificacion;// habilita el formulario de certificacion
	private Boolean habilitadoFormularioIdiomas;// habilita el formulario de idiomas
	private Boolean flgNivelEducativo;// habilita campos por nivel educativo
	private Boolean habilitadoArchivoSoporte;// habilita los campos para agregar un soporte
	private Boolean habilitadoCampo;// habilita los campos del formulario
	private Boolean flgValidRolPermission = false;// valida los permisos por rol
	private Boolean flgValidadoAcademica = Boolean.FALSE;// valida si es requerido el campo
	
	/*Variables utilizadas para validaciones*/
	private Boolean blnPaisDiferenteColombia;
	private Boolean blnHabilitarFechaConvalidacion;
	private Boolean blnDeshabilitarProgramaAcademico;
	private Boolean blnDeshabilitarTituloObtener;
	private Boolean blnDeshabilitarEstudioExterior;
	private Parametrica parNoAplica;
	private String lblNoAplica;
	private String strMensajeTituloObtenido;
	
	private Integer codFormulario = null;// valida que formulario se esta actualizando
	private String nombreArchivoAcademica;// variable para recibir un nombre especifico
	private String nombreArchivoTarjetaprofesional;
	private String nombreArchivoHumano;// variable para recibir un nombre especifico
	private String nombreArchivoIdioma;// variable para recibir un nombre especifico
	
	private InstitucionEducativa 		institucionEducativa;
	private List<InstitucionEducativaExt> 	lstInstitucionEducativa;
	
	ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	DatosPersonalesBean mBDatosPersonalesBean = (DatosPersonalesBean) elContext.getELResolver().getValue(elContext, null, "datosPersonalesBean");
	long codPersona = getUsuarioSesion().getCodPersona();// variable recibe el codigo de la persona
	private AuditoriaSeguridad  auditoriaSeguridad; 
	
	private String rutaArchivo;
	static final long COD_JEFE_CONTROL_INTERNO = 15; // variable para envio de correos desde modificar HV

	@PostConstruct
	public void init() {
		rutaArchivo = null;
		this.initialization();
		educacionFormal 		= new EducacionFormalExt();
		edFormalEditado 		= new EducacionFormalExt();
		otroConocimiento 		= new OtroConocimientoExt();
		idiomaPersona 			= new IdiomaPersonaExt();
		modificacionHojaVida 	= new HistoricoModificacionHojaVida();
		institucionEducativa	= new InstitucionEducativa();
		
		auditoriaSeguridad = new AuditoriaSeguridad();
		if(this.getUsuarioSesion()!=null) {
			auditoriaSeguridad.setCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
			auditoriaSeguridad.setCodUsuarioRol(new BigDecimal(getUsuarioSesion().getCodRol()));
			ComunicacionServiciosHV.setAuditoriaSeguridad(auditoriaSeguridad);
		}
		lbUpload = false;
		
		persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		listaEducacionFormalPorPersona = ComunicacionServiciosHV.getEducacionFormal001(codPersona, true);
		if(!listaEducacionFormalPorPersona.isEmpty()) {
			for (int i = 0; i < listaEducacionFormalPorPersona.size(); i++) {
				listaEducacionFormalPorPersona.get(i).setUrlTarjetaProfesional(WebUtils.validarUrl(listaEducacionFormalPorPersona.get(i).getUrlTarjetaProfesional(), persona.getNumeroIdentificacion(),listaEducacionFormalPorPersona.get(i).getCodEducacionFormal()+"", WebUtils.TP_TARJETA_PROFESIONAL));
				listaEducacionFormalPorPersona.get(i).setUrlAnexo(WebUtils.validarUrl(listaEducacionFormalPorPersona.get(i).getUrlAnexo(), persona.getNumeroIdentificacion(),listaEducacionFormalPorPersona.get(i).getCodEducacionFormal()+"",WebUtils.TP_EDUCACION_FORMAL));
			}
		}
		
		listaOtroConocimientoPorPersona = ComunicacionServiciosHV.getotroconocimientoporpersona(codPersona, true);
		if(!listaOtroConocimientoPorPersona.isEmpty()) {
			for (int i = 0; i < listaOtroConocimientoPorPersona.size(); i++) {
				listaOtroConocimientoPorPersona.get(i).setUrlDocumentoSoporte(WebUtils.validarUrl(listaOtroConocimientoPorPersona.get(i).getUrlDocumentoSoporte(), persona.getNumeroIdentificacion(),listaOtroConocimientoPorPersona.get(i).getCodOtroConocimiento()+"",WebUtils.TP_CONOCIMIENTO_SOPORTE));
			}
		}
		
		listaIdiomasPorPersona = ComunicacionServiciosHV.getidiomapersonaporpersona(codPersona, true);
		if(!listaIdiomasPorPersona.isEmpty()) {
			for (int i = 0; i < listaIdiomasPorPersona.size(); i++) {
				listaIdiomasPorPersona.get(i).setUrlCertificacion(WebUtils.validarUrl(listaIdiomasPorPersona.get(i).getUrlCertificacion(), persona.getNumeroIdentificacion(), listaIdiomasPorPersona.get(i).getCodIdiomaPersona()+"",WebUtils.TP_IDIOMA_SOPORTE));
			}
		}
		this.setNombreArchivoTarjetaprofesional("");
		inicializarVariablesValidaciones();
		parNoAplica =  ComunicacionServiciosVin.getParametricaporId(new BigDecimal(TipoParametro.NO_APLICA_GENERICO.getValue()));
		lblNoAplica = (parNoAplica!=null  && parNoAplica.getValorParametro()!=null) ? parNoAplica.getValorParametro() : "NO APLICA"; 
	}

	/*Inicializa las variables de validaciones del formulario*/
	public void inicializarVariablesValidaciones() {
		strMensajeTituloObtenido			= "";
		blnPaisDiferenteColombia 			= false;
		blnHabilitarFechaConvalidacion 		= false;
		blnDeshabilitarProgramaAcademico 	= false;
		blnDeshabilitarTituloObtener 		= false;
		blnDeshabilitarEstudioExterior		= false;
	}
	
	/**
	 * metodo para validar los roles y permisos, tambien para inicializar algunas
	 * variables
	 */
	public void initialization() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String idPersona = request.getParameter("id");

		if (idPersona != null) {
			codPersona = Long.valueOf(idPersona);

			if (getUsuarioSesion() != null && getUsuarioSesion().getCodPersona() != codPersona) {
				try {
					flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_CONTROL_INTERNO, RolDTO.AUDITOR, RolDTO.ADMINISTRADOR_FUNCIONAL,
							RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.JEFE_CONTRATOS, RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_CONTRATOS, RolDTO.OPERADOR_TALENTO_HUMANO);
					if (flgValidRolPermission == false) {
						this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
					}
				} catch (SIGEP2SistemaException e) {
					logger.error("void init() usuarioTieneRolAsignado", e);
				} catch (IOException e) {
					logger.error("void init() finalizarConversacion", e);
				}
			}
		} else {
			if (getUsuarioSesion() != null) {
				codPersona = getUsuarioSesion().getCodPersona();
				try {
					Boolean flgValidPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA, RolDTO.SERVIDOR_PUBLICO);
					if (flgValidPermission == false) {
						this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
					}
				} catch (SIGEP2SistemaException e) {
					logger.error("void init() usuarioTieneRolAsignado", e);
				} catch (IOException e) {
					logger.error("void init() finalizarConversacion", e);
				}
			} else {
				try {
					this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
				} catch (IOException e) {
					logger.error("void init() finalizarConversacion", e);
				}
			}
		}
	}

	/**
	 * @param event
	 * @throws IOException
	 *             metodo para cargar un soporte para la tarjeta profesional
	 */
	public void tarjetaProfesionalUpload(FileUploadEvent event) throws IOException {
		archivoSoporte = event.getFile();
		byte[] bytes = null;
		String response ="";
		try {
			if (null != archivoSoporte) {
				bytes = archivoSoporte.getContents();
				Date fechaActual = new Date();
				String ext = FilenameUtils.getExtension(archivoSoporte.getFileName());
				String filename = fechaActual.getTime() + "." + ext;
				String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_TARJETA_PROFESIONAL) + filename;
				String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
				
				try {
				    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				    stream.write(bytes);
				    stream.close();
				} catch (IOException e) {
					logger.log().error("Error al trata de escribir el archivo en la ruta:" + ruta, e.getMessage());
				}
				ErrorMensajes resp=null;
				if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("0")){
					response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
		                    ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_TARJETA_PROFESIONAL,
		                    WebUtils.TP_TARJETA_PROFESIONAL, mBDatosPersonalesBean.getPersona().getNumeroIdentificacion());
					Gson gson = new Gson();
					resp = gson.fromJson(response, ErrorMensajes.class);
				}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("1") || DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente windows*/
					resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_TARJETA_PROFESIONAL, WebUtils.TP_TARJETA_PROFESIONAL, filePath, persona.getNumeroIdentificacion());			
				}
				
				if(!resp.isError()) {
					educacionFormal.setUrlTarjetaProfesional(resp.getUrlArchivo());
					this.setNombreArchivoTarjetaprofesional(resp.getNombreArchivo());
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_EXITOSA, getLocale()).replace("%nombrearchivo%", resp.getNombreArchivo()));					
				}else {
					educacionFormal.setUrlTarjetaProfesional(null);
					this.setNombreArchivoTarjetaprofesional(null);
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));					
				}
			}
			RequestContext.getCurrentInstance().execute("uploadEmpy(0)");
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
		}
	}
	
	/**
	 * @param event
	 * @throws IOException
	 *             metodo para cargar un soporte para la tarjeta profesional
	 */
	public void educacionFormalUpload(FileUploadEvent event) throws IOException {
		archivoSoporte = event.getFile();
		byte[] bytes = null;
		try {
			if (null != archivoSoporte) {
				bytes = archivoSoporte.getContents();
				Date fechaActual = new Date();
				String ext = FilenameUtils.getExtension(archivoSoporte.getFileName());
				String filename = fechaActual.getTime() + "." + ext;
				String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_EDUCACION_FORMAL) + filename;
				String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
				educacionFormal.setUrlAnexo(ruta);
				BufferedOutputStream stream;
				try {
					stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(bytes);
					stream.close();	
				}catch (IOException e) {
					logger.log().error("Error al trata de escribir el archivo de EdFormal en la ruta:" + ruta, e.getMessage());
				}
				ErrorMensajes resp=null;
				
				if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("0")){
					String response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
		                    ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_EDUCACION_FORMAL,
		                    WebUtils.TP_EDUCACION_FORMAL, mBDatosPersonalesBean.getPersona().getNumeroIdentificacion());
					Gson gson = new Gson();
					resp = gson.fromJson(response, ErrorMensajes.class);
				}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("1")||DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente windows*/
					resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_EDUCACION_FORMAL, WebUtils.TP_EDUCACION_FORMAL, filePath, persona.getNumeroIdentificacion());			
				}
			
				if(!resp.isError()) {
					educacionFormal.setUrlAnexo(resp.getUrlArchivo());
					this.setNombreArchivoAcademica(resp.getNombreArchivo());

					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_EXITOSA, getLocale()).replace("%nombrearchivo%", resp.getNombreArchivo()));					
				}else {
					educacionFormal.setUrlAnexo(null);
					this.setNombreArchivoAcademica(null);
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));					
				}
				
			}

		} catch (Exception e) {
			logger.log().error("educacionFormalUpload()", e);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
		}
	}	

	/**
	 * @param event
	 * @throws IOException
	 *             metodo para cargar un soporte para la otros estudios
	 */
	public void certificadoOtroConocimientoUpload(FileUploadEvent event) throws IOException {
		archivoSoporte = event.getFile();
		byte[] bytes = null;
		try {
			if (null != archivoSoporte) {
				Date fechaActual = new Date();
				bytes = archivoSoporte.getContents();
				String ext = FilenameUtils.getExtension(archivoSoporte.getFileName());
				String filename = fechaActual.getTime() + "." + ext;
				String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_OTRO_CONOCIMIENTO_SOPORTE) + filename;
				String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(bytes);
					stream.close();	
				}catch (IOException err) {
					logger.log().error("Error al trata de escribir el archivo de EdFormal en la ruta:" + ruta, err.getMessage());
				}
				ErrorMensajes resp=null;
				if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("0")){
					String response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
		                    ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_OTRO_CONOCIMIENTO_SOPORTE,
		                    WebUtils.TP_CONOCIMIENTO_SOPORTE, mBDatosPersonalesBean.getPersona().getNumeroIdentificacion());
					Gson gson = new Gson();
					resp = gson.fromJson(response, ErrorMensajes.class);
				}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("1") || DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente windows*/
					resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_OTRO_CONOCIMIENTO_SOPORTE, WebUtils.TP_CONOCIMIENTO_SOPORTE, filePath, persona.getNumeroIdentificacion());			
				}			
				
				if(!resp.isError()) {
					otroConocimiento.setUrlDocumentoSoporte(resp.getUrlArchivo());
					this.setNombreArchivoHumano(resp.getNombreArchivo());
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_EXITOSA, getLocale()).replace("%nombrearchivo%", resp.getNombreArchivo()));					
				}else {
					otroConocimiento.setUrlDocumentoSoporte(null);
					this.setNombreArchivoHumano(null);
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_FALLIDA, getLocale()));					
				}
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_FALLIDA, getLocale()));
		}
	}

	/**
	 * @param event
	 * @throws IOException
	 *             metodo para cargar un soporte para certificado de idioma
	 */
	public void certificadoIdiomaUpload(FileUploadEvent event) throws IOException {
		logger.log().error("********FileUploadEvent()****** Disparó el evento de certificadoIdiomaUpload");
		archivoSoporte = event.getFile();
		byte[] bytes = null;
		try {
			if (null != archivoSoporte) {
				Date fechaActual = new Date();
				bytes = archivoSoporte.getContents();
				String ext = FilenameUtils.getExtension(archivoSoporte.getFileName());
				String filename = fechaActual.getTime() + "." + ext;
				String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_IDIOMA_SOPORTE) + filename;
				String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(bytes);
					stream.close();
				}catch (IOException err) {
					logger.log().error("Error al trata de escribir el archivo en la ruta:" + ruta, err.getMessage());
				}
				ErrorMensajes resp = null;
				if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("0")){
					String response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
		                    ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_OTRO_CONOCIMIENTO_SOPORTE,
		                    WebUtils.TP_CONOCIMIENTO_SOPORTE, mBDatosPersonalesBean.getPersona().getNumeroIdentificacion());
					Gson gson = new Gson();
					resp = gson.fromJson(response, ErrorMensajes.class);
				}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("1") || DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("2") ){/*Operacion cliente windows*/
					resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_OTRO_CONOCIMIENTO_SOPORTE, WebUtils.TP_CONOCIMIENTO_SOPORTE, filePath, persona.getNumeroIdentificacion());			
				}
				if(!resp.isError()) {
					idiomaPersona.setUrlCertificacion(resp.getUrlArchivo());
					this.setNombreArchivoIdioma(resp.getNombreArchivo());
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_EXITOSA, getLocale()).replace("%nombrearchivo%", resp.getNombreArchivo()));					
				}else {
					idiomaPersona.setUrlCertificacion(null);
					this.setNombreArchivoIdioma(null);
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_FALLIDA, getLocale()));		
				}
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_FALLIDA, getLocale()));
		}
	}

	public void validarHabilitadoAreaYsemestre() {
		Integer[] valores = { TipoParametro.PREESCOLAR.getValue(), TipoParametro.BASICA_PRIMARIA.getValue(),
							  TipoParametro.BASICA_SECUNDARIA.getValue(), TipoParametro.EDUCACION_MEDIA.getValue()};
		this.blnDeshabilitarProgramaAcademico = false;
		this.blnDeshabilitarTituloObtener = false;
		if (educacionFormal.getCodNivelEducativo() != null && Arrays.asList(valores).contains(educacionFormal.getCodNivelEducativo())) {
			blnDeshabilitarProgramaAcademico = true;
			blnDeshabilitarTituloObtener = true;
			this.setFlgNivelEducativo(false);
			educacionFormal.setCodAreaConocimiento(null);
			educacionFormal.setSemestresAprobado(null);
			educacionFormal.setCodProgramaAcademico(null);
			educacionFormal.setCodInstitucionEducativa(null);
			
			validarNivelAcademicoAndTitulo();
			
		} else {
			this.setFlgNivelEducativo(true);
		}
	}

	/**
	 * Metodo que permite validar dependiendo del nivel academico y el estado del estudio, que valores toma el campo 'Titulo obtenido o a obtener'
	 * 
	 */
	public void validarNivelAcademicoAndTitulo() {
		strMensajeTituloObtenido = "";
		if(educacionFormal.getCodNivelEducativo()!=null) {
			if(educacionFormal.getCodNivelEducativo().equals(TipoParametro.PREESCOLAR.getValue()) && educacionFormal.getFlgFinalizado()) {
				Parametrica parametricaBasicaPrimaria = ComunicacionServiciosVin.getParametricaporId(new BigDecimal(TipoParametro.PREESCOLAR.getValue()));
				strMensajeTituloObtenido = (parametricaBasicaPrimaria!=null  && parametricaBasicaPrimaria.getValorParametro()!=null) ? parametricaBasicaPrimaria.getValorParametro() : "PREESCOLAR";
			}else if(educacionFormal.getCodNivelEducativo().equals(TipoParametro.BASICA_PRIMARIA.getValue()) && educacionFormal.getFlgFinalizado()) {
				Parametrica parametricaBasicaPrimaria = ComunicacionServiciosVin.getParametricaporId(new BigDecimal(TipoParametro.BASICA_PRIMARIA.getValue()));
				strMensajeTituloObtenido = (parametricaBasicaPrimaria!=null  && parametricaBasicaPrimaria.getValorParametro()!=null) ? parametricaBasicaPrimaria.getValorParametro() : "PRIMARIA";
			}else if(educacionFormal.getCodNivelEducativo().equals(TipoParametro.BASICA_SECUNDARIA.getValue()) && educacionFormal.getFlgFinalizado()) {
				Parametrica parametricaBasicaPrimaria = ComunicacionServiciosVin.getParametricaporId(new BigDecimal(TipoParametro.BASICA_SECUNDARIA.getValue()));
				strMensajeTituloObtenido = (parametricaBasicaPrimaria!=null  && parametricaBasicaPrimaria.getValorParametro()!=null) ? parametricaBasicaPrimaria.getValorParametro() : "BÁSICA SECUNDARIA";
			}else if(educacionFormal.getCodNivelEducativo().equals(TipoParametro.EDUCACION_MEDIA.getValue()) && educacionFormal.getFlgFinalizado()) {
				strMensajeTituloObtenido = "BACHILLER";
			}
		}
	}
	
	public void habilitarUrlTarjetaProfesional() {
		if (educacionFormal.getNumTarjetaProfesional() != null && !educacionFormal.getNumTarjetaProfesional().trim().equals("")) {
			setHabilitadoArchivoSoporte(true);
			this.setNombreArchivoTarjetaprofesional("TarjetaProfesional.pdf");
		} else {
			setHabilitadoArchivoSoporte(false);
		}
		if(educacionFormal!=null && educacionFormal.getUrlTarjetaProfesional()!=null && !"".equals(educacionFormal.getUrlTarjetaProfesional()))
			this.setNombreArchivoTarjetaprofesional("TarjetaProfesional.pdf");
		
		if(educacionFormal!=null && educacionFormal.getUrlAnexo()!=null && !"".equals(educacionFormal.getUrlAnexo()))
			this.setNombreArchivoAcademica("EducacionFormal.pdf");
	}

	public void activarPanelArchivo() {
		String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nameParam");
		if (value.equals("true")) {
			setHabilitadoArchivoSoporte(true);
		} else {
			setHabilitadoArchivoSoporte(false);
		}
	}

	public void mostrarFormularioEducacionFormal() {
		nombreArchivoAcademica = "";
		educacionFormal 		= new EducacionFormalExt();
		institucionEducativa 	= new InstitucionEducativa();
		educacionFormal.setCodPersona(codPersona);
		educacionFormal.setFlgFinalizado(true);
		habilitadoFormularioEducacion 	= true;
		habilitadoCampo 				= false;
		this.setNombreArchivoTarjetaprofesional("");
		this.validarHabilitadoAreaYsemestre();
		this.habilitarUrlTarjetaProfesional();
	}

	public void mostrarFormularioOtroConocimiento() {
		nombreArchivoHumano = "";
		otroConocimiento = new OtroConocimientoExt();
		otroConocimiento.setCodPersona(codPersona);
		otroConocimiento.setFlgActivo(true);
		habilitadoFormularioCertificacion = true;
		habilitadoCampo = false;
	}

	public void mostrarFormularioIdiomas() {
		nombreArchivoIdioma = "";
		idiomaPersona = new IdiomaPersonaExt();
		idiomaPersona.setCodPersona(codPersona);
		idiomaPersona.setFlgActivo(true);
		habilitadoFormularioIdiomas = true;
		habilitadoCampo = false;
	}

	public void verDetalleEducacionFormal(EducacionFormalExt detalleEdFormal, boolean habilitar) {
		educacionFormal = new EducacionFormalExt();
		nombreArchivoAcademica 			= "";
		nombreArchivoHumano 			= "";
		nombreArchivoIdioma 			= "";
		nombreArchivoTarjetaprofesional = "";
		educacionFormal 				= detalleEdFormal;
		habilitadoCampo 				= habilitar;
		habilitadoFormularioEducacion 	= true;
		this.flgValidadoAcademica 		= false;
		if (educacionFormal.getFlgValidadoRrhh() && this.flgValidRolPermission) {
			this.flgValidadoAcademica = true;
		}
		institucionEducativa = new InstitucionEducativa();
		if(educacionFormal != null && educacionFormal.getCodInstitucionEducativa() != null) {
			obtenerInstitucionEducativaFiltroPorID(educacionFormal.getCodInstitucionEducativa());
		}
		if (!habilitar) {
			this.habilitarUrlTarjetaProfesional();
		}
		this.validarHabilitadoAreaYsemestre();
		this.realizarValidacionesCampos();
	}

	public void verDetalleOtroConocimiento(OtroConocimientoExt detalleOtroConocimiento, boolean habilitar) {
		nombreArchivoAcademica = "";
		nombreArchivoHumano = "";
		nombreArchivoIdioma = "";
		otroConocimiento = detalleOtroConocimiento;
		habilitadoCampo = habilitar;
		setHabilitadoArchivoSoporte(false);
		setHabilitadoFormularioCertificacion(true);
		
		if(otroConocimiento!=null && otroConocimiento.getUrlDocumentoSoporte()!=null && !"".equals(otroConocimiento.getUrlDocumentoSoporte()))
			this.setNombreArchivoHumano("CertificadoEstudio.pdf");
	}

	public void verDetalleIdioma(IdiomaPersonaExt detalleIdiomaPersona, boolean habilitar) {
		nombreArchivoAcademica = "";
		nombreArchivoHumano = "";
		nombreArchivoIdioma = "";
		idiomaPersona = detalleIdiomaPersona;
		habilitadoFormularioIdiomas = true;
		habilitadoCampo = habilitar;
		
		if(idiomaPersona!=null && idiomaPersona.getUrlCertificacion()!=null && !"".equals(idiomaPersona.getUrlCertificacion()))
			this.setNombreArchivoIdioma("CertificadoIdioma.pdf");
	}

	public void confirmarEliminarEdFormal() {
		educacionFormal.setFlgActivo(false);
		educacionFormal.setAudFechaActualizacion(DateUtils.getFechaSistema());
		educacionFormal.setAudCodUsuario(this.getUsuarioSesion().getId());
		educacionFormal.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
		educacionFormal.setAudCodRol((int) this.getRolAuditoria().getId());
		
		Boolean valid = ComunicacionServiciosHV.seteducacionformal(educacionFormal);
		if (valid) {
			
			mBDatosPersonalesBean.actualizaModificacionHv();
			
			educacionFormal = new EducacionFormalExt();
			habilitadoFormularioEducacion = false;
			listaEducacionFormalPorPersona = ComunicacionServiciosHV.getEducacionFormal001(codPersona, true);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.MSG_INFO_ELIMINAR, "");
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.MSG_ERROR_GUARDAR, "");
		}
	}

	public void cancelarEliminarEdFormal() {
		educacionFormal = new EducacionFormalExt();
		habilitadoFormularioEducacion = false;
	}

	public void confirmarEliminarCertificado() {

		otroConocimiento.setFlgActivo(false);
		otroConocimiento.setAudFechaActualizacion(DateUtils.getFechaSistema());
		otroConocimiento.setAudCodUsuario(this.getUsuarioSesion().getId());
		otroConocimiento.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
		otroConocimiento.setAudCodRol((int) this.getRolAuditoria().getId());
		boolean valid = ComunicacionServiciosHV.setotroconocimiento(otroConocimiento);

		if (valid) {
			
			mBDatosPersonalesBean.actualizaModificacionHv();
			listaOtroConocimientoPorPersona = ComunicacionServiciosHV.getotroconocimientoporpersona(codPersona, true);
			otroConocimiento = new OtroConocimientoExt();
			habilitadoFormularioCertificacion = false;
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.MSG_INFO_ELIMINAR, "");
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.MSG_ERROR_GUARDAR, "");
		}
	}

	public void cancelarEliminarCertificado() {
		otroConocimiento = new OtroConocimientoExt();
		habilitadoFormularioCertificacion = false;
	}

	public void confirmarEliminarIdioma() {

		idiomaPersona.setFlgActivo(false);
		idiomaPersona.setAudFechaActualizacion(DateUtils.getFechaSistema());
		idiomaPersona.setAudCodUsuario(this.getUsuarioSesion().getId());
		idiomaPersona.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
		idiomaPersona.setAudCodRol((int) this.getRolAuditoria().getId());
		boolean valid = ComunicacionServiciosHV.setIdiomaPersona(idiomaPersona);

		if (valid) {
			mBDatosPersonalesBean.actualizaModificacionHv();
			listaIdiomasPorPersona = ComunicacionServiciosHV.getidiomapersonaporpersona(codPersona, true);
			idiomaPersona = new IdiomaPersonaExt();
			habilitadoFormularioIdiomas = false;
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.MSG_INFO_ELIMINAR, "");
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.MSG_ERROR_GUARDAR, "");
		}
	}

	public void cancelarEliminarIdioma() {
		idiomaPersona = new IdiomaPersonaExt();
		habilitadoFormularioIdiomas = false;
	}

	public void limpiarForm() {
			educacionFormal = new EducacionFormalExt();
			otroConocimiento = new OtroConocimientoExt();
			idiomaPersona = new IdiomaPersonaExt();
			setHabilitadoFormularioEducacion(false);
			setHabilitadoFormularioCertificacion(false);
			setHabilitadoFormularioIdiomas(false);
			archivoSoporte = null;
			this.setNombreArchivoTarjetaprofesional("");
			this.setNombreArchivoAcademica("");
			listaEducacionFormalPorPersona = ComunicacionServiciosHV.getEducacionFormal001(codPersona, true);
	}
	
	public String guardarFormacionAcademica(){
		codFormulario = 1;
		if (habilitadoArchivoSoporte == true && educacionFormal.getNumTarjetaProfesional()!=null
				&& !"".equals(educacionFormal.getNumTarjetaProfesional()) &&educacionFormal.getUrlTarjetaProfesional() == null) {
			lbUpload = true;
            RequestContext.getCurrentInstance().execute("uploadEmpy(1)");
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.MSG_SOPORTE_TARJETA_PROFESIONAL, "");
			return null;
		}
		
		if(validarFechaGradoNacimiento()) {
			return null;
		}
		
		if(mBDatosPersonalesBean.isLbISGestionarHV()){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogEducacionPersona').show()");
		}else{
			guardarFormacionAcademicaSinConfirmar();
		}
		return null;
	}
	
	/*Metodo que valida que la fecha de grado de una experiencia profesional no sea anterior a la fecha de nacimiento del usuario*/
	public boolean validarFechaGradoNacimiento() {
		boolean blnFechaIncorrecta = false;
		if (educacionFormal.getFechaGrado() != null && persona.getFechaNacimiento() != null) {
			LocalDate fechaGrado = educacionFormal.getFechaGrado() .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fechaNacimiento = persona.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (fechaGrado.isBefore(fechaNacimiento)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_EDUCACION_FORMAL_ERROR_FECHA_GRADO);
				blnFechaIncorrecta = true;
			}
			if(educacionFormal.getFechaGrado() !=null && educacionFormal.getFechaFinalizacion()!=null 
					&& educacionFormal.getFechaGrado().before(educacionFormal.getFechaFinalizacion())){
				blnFechaIncorrecta = true;
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_EDUCACION_FORMAL_ERROR_FECHA_GRADO_FINALIZACION);
				
			}
		}
		return blnFechaIncorrecta;
	}

	
	
	
	public boolean guardarFormacionAcademicaSinConfirmar() {
		educacionFormal.setAudCodUsuario(this.getUsuarioSesion().getId());
		educacionFormal.setAudCodRol((int) this.getRolAuditoria().getId());
		educacionFormal.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		if (educacionFormal.getCodEducacionFormal() == null || educacionFormal.getCodEducacionFormal().equals(0l)) {
			educacionFormal.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		}
		if(educacionFormal.getFlgValidadoTarjetaProfesional()==null)
			educacionFormal.setFlgValidadoTarjetaProfesional(false);
		
		educacionFormal.setCodInstitucionEducativa(institucionEducativa.getCodInstitucionEducativa()!= null ? institucionEducativa.getCodInstitucionEducativa().longValue() : null );
		boolean valid = ComunicacionServiciosHV.seteducacionformal(educacionFormal);
		if (valid) {
			mBDatosPersonalesBean.setEditado(false);
			educacionFormal = new EducacionFormalExt();
			nombreArchivoAcademica 	= "";
			nombreArchivoHumano 	= "";
			nombreArchivoIdioma 	= "";
			institucionEducativa 	= new InstitucionEducativa();
			listaEducacionFormalPorPersona = ComunicacionServiciosHV.getEducacionFormal001(codPersona, true);
			habilitadoFormularioEducacion = false;
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.MSG_INFO_GUARDAR, "");
			mBDatosPersonalesBean.getUiDatosModificados().setValue("0");
			RequestContext.getCurrentInstance().update("frmPrincipal:modificadoHV");
			mBDatosPersonalesBean.refrescarProgresoHojaVida();
			mBDatosPersonalesBean.actualizaModificacionHv();
			inicializarVariablesValidaciones();
			
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.MSG_ERROR_GUARDAR, "");
			inicializarVariablesValidaciones();
			return false;
		}
		return true;
	}
	
	
	public String guardarEducacionDesarrolloHumano(){
		codFormulario = 2;
		if(mBDatosPersonalesBean.isLbISGestionarHV()){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogEducacionPersona').show()");
		}else{
			guardarEducacionDesarrolloHumanoSinConfirmar();
		}
		return null;
	}

	public boolean guardarEducacionDesarrolloHumanoSinConfirmar() {
		otroConocimiento.setAudCodUsuario(getUsuarioSesion().getId());
		otroConocimiento.setAudCodRol((int) this.getRolAuditoria().getId());
		otroConocimiento.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());

		if (otroConocimiento.getCodOtroConocimiento() == null) {
			otroConocimiento.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		}

		boolean valid = ComunicacionServiciosHV.setotroconocimiento(otroConocimiento);

		if (valid) {
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			DatosPersonalesBean mBDatosPersonalesBean = (DatosPersonalesBean) elContext.getELResolver().getValue(elContext, null, "datosPersonalesBean");
			mBDatosPersonalesBean.setEditado(false);
			listaOtroConocimientoPorPersona = ComunicacionServiciosHV.getotroconocimientoporpersona(codPersona, true);
			otroConocimiento = new OtroConocimientoExt();
			nombreArchivoAcademica = "";
			nombreArchivoHumano = "";
			nombreArchivoIdioma = "";
			habilitadoFormularioCertificacion = false;
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.MSG_INFO_GUARDAR, "");
			mBDatosPersonalesBean.getUiDatosModificados().setValue("0");
			RequestContext.getCurrentInstance().update("frmPrincipal:modificadoHV");
			mBDatosPersonalesBean.refrescarProgresoHojaVida();
			mBDatosPersonalesBean.actualizaModificacionHv();
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.MSG_ERROR_GUARDAR, "");
			return false;
		}

		return true;
	}
	public String guardarNuevoIdioma(){
		codFormulario = 3;
		if(mBDatosPersonalesBean.isLbISGestionarHV()){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogEducacionPersona').show()");
		}else{
			guardarNuevoIdiomaSinConfirmar();
		}
		return null;
	}	

	public void guardarNuevoIdiomaSinConfirmar() {
		idiomaPersona.setAudCodUsuario(getUsuarioSesion().getId());
		idiomaPersona.setAudCodRol((int) this.getRolAuditoria().getId());
		idiomaPersona.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());

		if (idiomaPersona.getCodIdiomaPersona() == null) {
			idiomaPersona.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		}

		boolean valid = ComunicacionServiciosHV.setIdiomaPersona(idiomaPersona);

		if (valid) {
			mBDatosPersonalesBean.setEditado(false);
			listaIdiomasPorPersona = ComunicacionServiciosHV.getidiomapersonaporpersona(codPersona, true);
			idiomaPersona = new IdiomaPersonaExt();
			nombreArchivoAcademica = "";
			nombreArchivoHumano = "";
			nombreArchivoIdioma = "";
			habilitadoFormularioIdiomas = false;
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.MSG_INFO_GUARDAR_IDIOMA, "");
			mBDatosPersonalesBean.getUiDatosModificados().setValue("0");
			RequestContext.getCurrentInstance().update("frmPrincipal:modificadoHV");
			mBDatosPersonalesBean.refrescarProgresoHojaVida();
			mBDatosPersonalesBean.actualizaModificacionHv();
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.MSG_ERROR_GUARDAR, "");
		}
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
	}

	@Override
	public void retrieve() {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		if (this.conversation.isTransient()) {
			this.conversation.begin();
			this.conversation.setTimeout(timeOut);
		}
		this.init();
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
	}

	public void guardarConfirmacionDatos() {
		List<String> camposEditados = new ArrayList<>();
		if (codFormulario.intValue() == 1 && educacionFormal.getCodEducacionFormal() != null) {
			EducacionFormalExt educacionAnterior = ComunicacionServiciosHV.getEducacionFormalId(educacionFormal.getCodEducacionFormal(), 1);
			if (educacionAnterior.getCodNivelEducativo()==null || educacionAnterior.getCodNivelEducativo().intValue() != educacionFormal.getCodNivelEducativo().intValue()) {
				camposEditados.add(
						"Nivel educativo: " + ComunicacionServiciosSis.getParametricaporId(new BigDecimal(educacionFormal.getCodNivelEducativo())).getNombreParametro());
			}
			if (educacionAnterior.getCodNivelFormacion()==null || educacionAnterior.getCodNivelFormacion().intValue() != educacionFormal.getCodNivelFormacion().intValue()) {
				camposEditados.add("Nivel de formación: "
						+ ComunicacionServiciosSis.getParametricaporId(new BigDecimal(educacionFormal.getCodNivelFormacion())).getNombreParametro());
			}

			if (educacionFormal.getCodAreaConocimiento() != null && !educacionFormal.getCodAreaConocimiento().equals(educacionAnterior.getCodAreaConocimiento())) {
				camposEditados.add("Área de conocimiento: "
						+ ComunicacionServiciosSis.getParametricaporId(new BigDecimal(educacionFormal.getCodAreaConocimiento())).getNombreParametro());
			}
			if (educacionFormal.getCodPais() != null && !educacionFormal.getCodPais().equals(educacionAnterior.getCodPais())) {
				camposEditados.add("Pais de estudio: " + ComunicacionServiciosSis.getpisporid(Long.parseLong("" + educacionFormal.getCodPais())).getNombrePais());
			}
			if (educacionAnterior.getCodInstitucionEducativa()==null || educacionAnterior.getCodInstitucionEducativa() != educacionFormal.getCodInstitucionEducativa()) {
				if(educacionFormal.getCodInstitucionEducativa()!=null){
					camposEditados.add("Institución educativa: "
						+ ComunicacionServiciosHV.getInstitucionEducaporId333(educacionFormal.getCodInstitucionEducativa()).getNombreInstitucion());
				}else{
					camposEditados.add("Institución educativa: NO APLICA");
				}
			}
			if (educacionAnterior.getCodProgramaAcademico()==null  
					||(educacionFormal.getCodProgramaAcademico()!=null && educacionAnterior.getCodProgramaAcademico()!= null && educacionAnterior.getCodProgramaAcademico().longValue() != educacionFormal.getCodProgramaAcademico().longValue())) {
				if(educacionFormal.getCodTituloObtenido()!=null){
					camposEditados.add("Programa académico: "
						+ ComunicacionServiciosHV.getprogramaacademicoporid333(educacionFormal.getCodTituloObtenido()).getNombreProgramaAcademico());
				}else{
					camposEditados.add("Programa académico: NO APLICA");
				}
			}
			if (educacionAnterior.getCodTituloObtenido()==null ||
					(educacionAnterior.getCodTituloObtenido() != null && educacionAnterior.getCodTituloObtenido().intValue() != educacionFormal.getCodTituloObtenido().intValue())  ) {
				camposEditados.add(
						"Título académico: " + ComunicacionServiciosHV.getprogramaacademicoporid333(educacionFormal.getCodTituloObtenido()).getNombreTituloOtorgado());
			}
			if (educacionAnterior.getSemestresAprobado() != educacionFormal.getSemestresAprobado()) {
				camposEditados.add("Semestres aprobados: " + educacionFormal.getSemestresAprobado());
			}
			if (educacionAnterior.getFlgFinalizado() != educacionFormal.getFlgFinalizado()) {
				if (educacionFormal.getFlgFinalizado()) {
					camposEditados.add("Estudio finalizado: Terminado");
				} else {
					camposEditados.add("Estudio finalizado: Sin terminar");
				}
			}
			if (!(educacionAnterior.getFechaFinalizadoString().trim().equals(educacionFormal.getFechaFinalizadoString().trim()))) {
				camposEditados.add("Fecha de finalización de estudios: " + educacionFormal.getFechaFinalizadoString());
			}
			if (!(educacionAnterior.getFechaGradoString().trim().equals(educacionFormal.getFechaGradoString().trim()))) {
				camposEditados.add("Fecha de grado: " + educacionFormal.getFechaGradoString());
			}
			if (educacionAnterior.getEstudioConvalidado() != educacionFormal.getEstudioConvalidado()) {
				if (educacionFormal.getEstudioConvalidado()) {
					camposEditados.add("Estudio convalidado: Si");
				} else {
					camposEditados.add("Estudio convalidado: No");
				}
			}
			if (educacionAnterior.getFlgEducacionExterior() != educacionFormal.getFlgEducacionExterior()) {
				if (educacionFormal.getFlgEducacionExterior()) {
					camposEditados.add("Estudio en el exterior: Si");
				} else {
					camposEditados.add("Estudio en el exterior: No");
				}
			}
			if (educacionAnterior.getFechaConvalidadoString() == null) {
				educacionAnterior.setFechaConvalidadoString("");
			}
			if (educacionFormal.getFechaConvalidadoString() == null) {
				educacionFormal.setFechaConvalidadoString("");
			}
			if (!(educacionAnterior.getFechaConvalidadoString().trim().equals(educacionFormal.getFechaConvalidadoString().trim()))) {
				camposEditados.add("Fecha de convalidado: " + educacionFormal.getFechaConvalidadoString());
			}
			if (educacionAnterior.getNumTarjetaProfesional() == null) {
				educacionAnterior.setNumTarjetaProfesional("");
			}
			if (educacionFormal.getNumTarjetaProfesional() == null) {
				educacionFormal.setNumTarjetaProfesional("");
			}
			if (!(educacionAnterior.getNumTarjetaProfesional().trim().equals(educacionFormal.getNumTarjetaProfesional().trim()))) {
				camposEditados.add("Tarjeta profesional: " + educacionFormal.getNumTarjetaProfesional());
			}
			if (camposEditados.size() > 0) {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> " + new Gson().toJson(camposEditados));
			} else {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> SIN MODIFICACION");
			}
		} else if (codFormulario.intValue() == 2 && otroConocimiento.getCodOtroConocimiento() != null) {
			OtroConocimientoExt conocimientoAdicionalAnterior = ComunicacionServiciosHV.getotroconocimientoporid(otroConocimiento.getCodOtroConocimiento().longValue());
			if (conocimientoAdicionalAnterior.getFechaFinalizacionString() == null) {
				conocimientoAdicionalAnterior.setFechaFinalizacionString("");
			}
			if (otroConocimiento.getFechaFinalizacionString() == null) {
				otroConocimiento.setFechaFinalizacionString("");
			}
			if (!(conocimientoAdicionalAnterior.getFechaFinalizacionString().trim().equals(otroConocimiento.getFechaFinalizacionString().trim()))) {
				camposEditados.add("Fecha de finalización de estudios: " + otroConocimiento.getFechaFinalizacionString());
			}
			if (conocimientoAdicionalAnterior.getTotHoras() == null) {
				conocimientoAdicionalAnterior.setTotHoras(0);
			}
			if (otroConocimiento.getTotHoras() == null) {
				otroConocimiento.setTotHoras(0);
			}
			if (conocimientoAdicionalAnterior.getTotHoras().intValue() != otroConocimiento.getTotHoras().intValue()) {
				camposEditados.add("Número total de horas: " + otroConocimiento.getTotHoras());
			}
			if (conocimientoAdicionalAnterior.getCodPais().intValue() != otroConocimiento.getCodPais().intValue()) {
				camposEditados.add("Pais de estudio: " + ComunicacionServiciosSis.getpisporid(Long.parseLong("" + otroConocimiento.getCodPais())).getNombrePais());
			}
			if (!(conocimientoAdicionalAnterior.getNombreCurso().trim().equals(otroConocimiento.getNombreCurso().trim()))) {
				camposEditados.add("Nombre del curso: " + otroConocimiento.getNombreCurso());
			}
			if (!(conocimientoAdicionalAnterior.getInstitucionFormacion().trim().equals(otroConocimiento.getInstitucionFormacion().trim()))) {
				camposEditados.add("Nombre de la institución: " + otroConocimiento.getInstitucionFormacion());
			}
			if (conocimientoAdicionalAnterior.getCodMedioCapacitacion() == null) {
				conocimientoAdicionalAnterior.setCodMedioCapacitacion(0);
			}
			if (otroConocimiento.getCodMedioCapacitacion() == null) {
				otroConocimiento.setCodMedioCapacitacion(0);
			}
			if (conocimientoAdicionalAnterior.getCodMedioCapacitacion().intValue() != otroConocimiento.getCodMedioCapacitacion().intValue()) {
				if (otroConocimiento.getCodMedioCapacitacion().intValue() == 0) {
					camposEditados.add("Medio de capacitación: Sin seleccionar opción");
				} else {
					camposEditados.add("Medio de capacitación: "
							+ ComunicacionServiciosSis.getParametricaporId(new BigDecimal(otroConocimiento.getCodMedioCapacitacion())).getNombreParametro());
				}
			}
			if (conocimientoAdicionalAnterior.getCodModalidad().intValue() != otroConocimiento.getCodModalidad().intValue()) {
				camposEditados.add(
						"Modalidad de estudio: " + ComunicacionServiciosSis.getParametricaporId(new BigDecimal(otroConocimiento.getCodModalidad())).getNombreParametro());
			}
			if (camposEditados.size() > 0) {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> " + new Gson().toJson(camposEditados));
			} else {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> SIN MODIFICACION");
			}
		} else if (codFormulario.intValue() == 3 && idiomaPersona.getCodIdiomaPersona() != null) {
			IdiomaPersonaExt idiomaPersonaAnterior = ComunicacionServiciosHV.getidiomapersonaporpersonaporid(idiomaPersona.getCodIdiomaPersona().longValue());
			if (idiomaPersonaAnterior.getCodIdioma().longValue() != idiomaPersona.getCodIdioma().longValue()) {
				camposEditados
						.add("Idioma: " + ComunicacionServiciosHV.getidiomapersonaporpersonaporid(idiomaPersona.getCodIdiomaPersona().longValue()).getNombreIdioma());
			}
			if (!(idiomaPersonaAnterior.getFechaCertificacionString().trim().equals(idiomaPersona.getFechaCertificacionString().trim()))) {
				camposEditados.add("Fecha de certificado: " + idiomaPersona.getFechaCertificacionString());
			}
			if (idiomaPersonaAnterior.getCodNivelConversacion() == null) {
				idiomaPersonaAnterior.setCodNivelConversacion(0);
			}
			if (idiomaPersona.getCodNivelConversacion() == null) {
				idiomaPersona.setCodNivelConversacion(0);
			}
			if (idiomaPersonaAnterior.getCodNivelConversacion().intValue() != idiomaPersona.getCodNivelConversacion().intValue()) {
				if (idiomaPersona.getCodNivelConversacion().intValue() == 0) {
					camposEditados.add("Nivel de conversación: Sin seleccionar opción");
				} else {
					camposEditados.add("Nivel de conversación: "
							+ ComunicacionServiciosSis.getParametricaporId(new BigDecimal(idiomaPersona.getCodNivelConversacion().intValue())).getNombreParametro());
				}
			}
			if (idiomaPersonaAnterior.getCodNivelLectura() == null) {
				idiomaPersonaAnterior.setCodNivelLectura(0);
			}
			if (idiomaPersona.getCodNivelLectura() == null) {
				idiomaPersona.setCodNivelLectura(0);
			}
			if (idiomaPersonaAnterior.getCodNivelLectura().intValue() != idiomaPersona.getCodNivelLectura().intValue()) {
				if (idiomaPersona.getCodNivelLectura().intValue() == 0) {
					camposEditados.add("Nivel de lectura: Sin seleccionar opción");
				} else {
					camposEditados.add("Nivel de lectura: "
							+ ComunicacionServiciosSis.getParametricaporId(new BigDecimal(idiomaPersona.getCodNivelLectura().intValue())).getNombreParametro());
				}
			}
			if (idiomaPersonaAnterior.getCodNivelEscritura() == null) {
				idiomaPersonaAnterior.setCodNivelEscritura(0);
			}
			if (idiomaPersona.getCodNivelEscritura() == null) {
				idiomaPersona.setCodNivelEscritura(0);
			}
			if (idiomaPersonaAnterior.getCodNivelEscritura().intValue() != idiomaPersona.getCodNivelEscritura().intValue()) {
				if (idiomaPersona.getCodNivelEscritura().intValue() == 0) {
					camposEditados.add("Nivel de escritura: Sin seleccionar opción");
				} else {
					camposEditados.add("Nivel de escritura: "
							+ ComunicacionServiciosSis.getParametricaporId(new BigDecimal(idiomaPersona.getCodNivelEscritura().intValue())).getNombreParametro());
				}
			}
			if (idiomaPersonaAnterior.isFlgIdiomaNativo() != idiomaPersona.isFlgIdiomaNativo()) {
				if (idiomaPersona.isFlgIdiomaNativo()) {
					camposEditados.add("Nativo: Si");
				} else {
					camposEditados.add("Nativo: No");
				}
			}
			if (camposEditados.size() > 0) {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> " + new Gson().toJson(camposEditados));
			} else {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> SIN MODIFICACION");
			}
		} else {
			camposEditados.add("Se ha agregado un nuevo registro.");
		}

		camposEditados.add("Modificado Por : " + this.getUsuarioSesion().getNumeroIdentificacion() + " - " + this.getUsuarioSesion().getNombrePersona());
		camposEditados.add("Entidad que Modifica : " + this.getEntidadUsuario().getNombreEntidad());
		switch (codFormulario) {
		case 1:
			this.guardarFormacionAcademicaSinConfirmar();
			break;
		case 2:
			this.guardarEducacionDesarrolloHumanoSinConfirmar();
			break;
		case 3:
			this.guardarNuevoIdiomaSinConfirmar();
			break;
		default:
			break;
		}

		HojaVidaExt hojaVidaFilter = new HojaVidaExt();
		hojaVidaFilter.setCodPersona(persona.getCodPersona());
		hojaVidaFilter.setCodEntidad(this.getEntidadUsuario().getId());
		hojaVidaFilter.setFlgActivo(true);
		hojaVidaFilter.setLimitEnd(1);

		List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);

		modificacionHojaVida.setCodHojaVida(BigDecimal.valueOf(listHojaVida.get(0).getCodHojaVida()));
		modificacionHojaVida.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		modificacionHojaVida.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		modificacionHojaVida.setAudCodRol((int) this.getRolAuditoria().getId());
		modificacionHojaVida.setAudFechaModificacion(DateUtils.getFechaSistema());

		ComunicacionServiciosHV.setmodificacionhohadevida(modificacionHojaVida);

		codFormulario = null;
		modificacionHojaVida = new HistoricoModificacionHojaVida();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogEducacionPersona').hide();");
		PersonaExt personaControlInterno = new PersonaExt();
		personaControlInterno.setCodRol(new BigDecimal(COD_JEFE_CONTROL_INTERNO));
		personaControlInterno.setCodEntidad(new BigDecimal(getEntidadUsuario().getId()));
		List<PersonaExt> listaPersonasControlInterno = ComunicacionServiciosHV.getPersonaControlInterno(personaControlInterno);
		List<String> email = new ArrayList<>();
		List<String> ccEmail = new ArrayList<>();
		try {
			for (PersonaExt personaExt : listaPersonasControlInterno) {
				ccEmail.add(personaExt.getCorreoElectronico());
			}
			email.add(persona.getCorreoElectronico());
			email.add(persona.getCorreoAlternativo());
			HojaDeVidaDelegate.emailActualizacionDatosPersonaDC(persona.getNombreUsuario(), modificacionHojaVida.getAudFechaModificacion(),
					personaControlInterno.getNombreUsuario(), email, ccEmail, camposEditados);
		} catch (Exception e) {
			e.printStackTrace();
		}

		nombreArchivoAcademica = "";
		nombreArchivoHumano = "";
		nombreArchivoIdioma = "";
	}
	
	
	/**
	 * Metodo que permite limpiar las variables de fecha de terminacion de materias y fecha de grado,
	 * cuando se selecciona el estado del estudio como "En proceso"
	 */
	public void estudioActivo() {
		if(!educacionFormal.getFlgFinalizado()) {
			educacionFormal.setFechaGrado(null);
			educacionFormal.setFechaFinalizacion(null);
		}
	}
	
	/**
	 * Metodo que realiza todas las validaciones de los campos, cuando la opcion seleccionada es modificar.
	 */
	public void realizarValidacionesCampos() {
		validarCondicionesEstadoEstudio();
		validarCondicionesEstudioExterior();
		validarCondicionesPais();
		validarCondicionesEstudioConvalidado();
	}
	
	/**
	 * Metodo que valida las condiciones y cambios que debe de hacer el campo de 'Estado del estudio'
	 */
	public void validarCondicionesEstadoEstudio () {
		estudioActivo();
		validarEstudioExterior();
		validarFechaConvalidacion();
		validarNivelAcademicoAndTitulo();
	}
	
	/**
	 * Metodo que valida las condiciones y cambios que debe de hacer el campo de 'Estudio en el exterior'
	 */
	public void validarCondicionesEstudioExterior() {
		validarEstudioExterior();
	}
	
	/**
	 * Metodo que valida las condiciones y cambios que debe de hacer el campo Pais sobre los demas campos del formulario
	 */
	public void validarCondicionesPais(){
		Integer[] valores = { TipoParametro.PREESCOLAR.getValue(), TipoParametro.BASICA_PRIMARIA.getValue(),
				  			  TipoParametro.BASICA_SECUNDARIA.getValue(), TipoParametro.EDUCACION_MEDIA.getValue()};
		
		blnDeshabilitarEstudioExterior			= true;
		blnDeshabilitarProgramaAcademico 		= false;
		blnDeshabilitarTituloObtener 			= false;
		blnPaisDiferenteColombia 				= false;
		educacionFormal.setFlgEducacionExterior(false);
		if(educacionFormal.getCodPais()!=null && !educacionFormal.getCodPais().equals(TipoParametro.PAIS_COLOMBIA.getValue())) {	
			educacionFormal.setFlgEducacionExterior(true);
			blnPaisDiferenteColombia 			= true;
		}
		if(Arrays.asList(valores).contains(educacionFormal.getCodNivelEducativo())) {
			blnDeshabilitarProgramaAcademico 	= true;
			blnDeshabilitarTituloObtener 		= true;
		}
			
		validarFechaConvalidacion();
	}
	
	
	/**
	 * Metodo que valida las condiciones y cambios que debe de hacer el campo 'Estudio Convalidado' sobre los demas campos del formulario
	 */
	public void validarCondicionesEstudioConvalidado() {
		validarFechaConvalidacion();
	}
	/**
	 * Valida que solo se habilite la fecha de convalidacion cuando el pais es diferente a Colombia, si ha finalizado un estudio y tiene un estudio convalidado
	 */
	public void validarFechaConvalidacion() {
		blnHabilitarFechaConvalidacion = false;
		if((educacionFormal.getCodPais()!=null && !educacionFormal.getCodPais().equals(TipoParametro.PAIS_COLOMBIA.getValue()))
			&& (educacionFormal.getFlgFinalizado() != null && educacionFormal.getFlgFinalizado())
			&& (educacionFormal.getEstudioConvalidado() != null &&  educacionFormal.getEstudioConvalidado()))  {
			blnHabilitarFechaConvalidacion = true;
		}
	}
	
	/**
	 * Metodo que valida si la persona ha tenido una formacion academica sin finalizar(proceso) y lo realizo en el exterior,
	 * de ser asi, debe borrarse la informacion de las opciones fecha de convalidación y estudio convalidado
	 * Nota: En Colombia únicamente es posible la convalidación una vez la persona se ha graduado en una entidad educativa del extranjero
	 */
	public void validarEstudioExterior() {
		if(educacionFormal.getFlgFinalizado() != null && !educacionFormal.getFlgFinalizado() 
			&& educacionFormal.getFlgEducacionExterior()!=null && educacionFormal.getFlgEducacionExterior()) {
			educacionFormal.setFechaConvalidacionEstudio(null);
			educacionFormal.setEstudioConvalidado(false);
		}
	}
	
	public void cancelarConfirmacionDatos() {
		this.limpiarForm();
		inicializarVariablesValidaciones();
		codFormulario = null;
		lbUpload = false;
		modificacionHojaVida = new HistoricoModificacionHojaVida();
		listaEducacionFormalPorPersona = ComunicacionServiciosHV.getEducacionFormal001(codPersona, true);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogEducacionPersona').hide();");
	}
	
	/**
	 * Pablo Quintana - 26/09/2018
	 * Método que obtiene la url del anexo
	 * @param tipoDocVisualiza
	 * 1. Hoja de vida - Educación - Formación Académica - Tarjeta profesional
	 * 2. Hoja de vida datos basicos - tarjeta profesional
	 * 3. Hoja de vida datos basicos - Otros conocimientos
	 * 4. Hoja de vida datos basicos - Idioma
	 */
	public void  visualizarArchivoEducacionFormal(String tipoDocVisualiza){
		String ruta="";
		String tipo = null;
		String codRegistro = null;
		if("1".equals(tipoDocVisualiza)){
			tipo = WebUtils.TP_TARJETA_PROFESIONAL;
			if(educacionFormal!=null && educacionFormal.getUrlTarjetaProfesional()!=null) {
				codRegistro = educacionFormal.getCodEducacionFormal() + "";
				ruta =  educacionFormal.getUrlTarjetaProfesional();
			}
		}else if("2".equals(tipoDocVisualiza)){
			tipo = WebUtils.TP_EDUCACION_FORMAL;
			if(educacionFormal!=null && educacionFormal.getUrlAnexo()!=null) {
				ruta = educacionFormal.getUrlAnexo();
				codRegistro = educacionFormal.getCodEducacionFormal() + "";
			}
		}else if("3".equals(tipoDocVisualiza)){
			tipo = WebUtils.TP_CONOCIMIENTO_SOPORTE;
			if(otroConocimiento!=null && otroConocimiento.getUrlDocumentoSoporte()!=null) {
				ruta = otroConocimiento.getUrlDocumentoSoporte();
				codRegistro = otroConocimiento.getCodOtroConocimiento() + "";
			}
		}else if("4".equals(tipoDocVisualiza)){
			tipo = WebUtils.TP_IDIOMA_SOPORTE;
			if(idiomaPersona!=null && idiomaPersona.getUrlCertificacion()!=null) {
				ruta = idiomaPersona.getUrlCertificacion();
				codRegistro = idiomaPersona.getCodIdiomaPersona() + "";
			}
		}
		
		if(ruta==null || "".equals(ruta)) {
			rutaArchivo=null;
		}else {
			if(persona == null) {
				persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
			}
			if(otroConocimiento!=null && otroConocimiento.getUrlDocumentoSoporte()!=null)
				rutaArchivo = WebUtils.getUrlFile(ruta);
		}
	}	
	
	
	/**
	 * Metodo que filtra la Institucion Educativa segun lo ingresado por el usuario
	 * @param institucionPrivQuery
	 * @return filtroInsitucionEducativa
	 */
	public List<InstitucionEducativaExt> listarInsitucionEducativaFiltro(String institucionPrivQuery){
		InstitucionEducativa filtroInstitucionEducativa = new InstitucionEducativa();
		if(!institucionPrivQuery.equals("")) {
			filtroInstitucionEducativa.setNombreInstitucion(institucionPrivQuery);	
		}
		
		filtroInstitucionEducativa.setFlgActivo((short)1);
		if(educacionFormal.getCodPais() !=null && !educacionFormal.getCodPais().equals(TipoParametro.PAIS_COLOMBIA.getValue())) {
			filtroInstitucionEducativa.setFlgInstExtranjera((short) 1);
			
		}else {
			filtroInstitucionEducativa.setFlgInstExtranjera((short) 0);
		}
		lstInstitucionEducativa = ComunicacionServiciosAdmin.obtenerInstitucionEducativaFiltro(filtroInstitucionEducativa);
		if(lstInstitucionEducativa.isEmpty()) {
	    	 return new ArrayList<>();
	     }
		return lstInstitucionEducativa;
	}
	
	
	/**
	 * Metodo que filtra la insitucion educativa por id
	 * @param entidadPrivQuery
	 * @return filtroDependenciaHV
	 */
	public void obtenerInstitucionEducativaFiltroPorID(long codInstitucionEducativa){
		institucionEducativa = ComunicacionServiciosSis.getInstitucionEducaporId(codInstitucionEducativa);
	}
	
	/*Metodos get y set*/
	/**
	 * @return the persona
	 */
	public PersonaExt getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(PersonaExt persona) {
		this.persona = persona;
	}

	/**
	 * @return the educacionFormal
	 */
	public EducacionFormalExt getEducacionFormal() {
		return educacionFormal;
	}

	/**
	 * @param educacionFormal
	 *            the educacionFormal to set
	 */
	public void setEducacionFormal(EducacionFormalExt educacionFormal) {
		this.educacionFormal = educacionFormal;
	}

	/**
	 * @return the otroConocimiento
	 */
	public OtroConocimientoExt getOtroConocimiento() {
		return otroConocimiento;
	}

	/**
	 * @param otroConocimiento
	 *            the otroConocimiento to set
	 */
	public void setOtroConocimiento(OtroConocimientoExt otroConocimiento) {
		this.otroConocimiento = otroConocimiento;
	}

	/**
	 * @return the idiomaPersona
	 */
	public IdiomaPersonaExt getIdiomaPersona() {
		return idiomaPersona;
	}

	/**
	 * @param idiomaPersona
	 *            the idiomaPersona to set
	 */
	public void setIdiomaPersona(IdiomaPersonaExt idiomaPersona) {
		this.idiomaPersona = idiomaPersona;
	}

	/**
	 * @return the archivoSoporte
	 */
	public UploadedFile getArchivoSoporte() {
		return archivoSoporte;
	}

	/**
	 * @param archivoSoporte
	 *            the archivoSoporte to set
	 */
	public void setArchivoSoporte(UploadedFile archivoSoporte) {
		this.archivoSoporte = archivoSoporte;
	}

	/**
	 * @return the modificacionHojaVida
	 */
	public HistoricoModificacionHojaVida getModificacionHojaVida() {
		return modificacionHojaVida;
	}

	/**
	 * @param modificacionHojaVida
	 *            the modificacionHojaVida to set
	 */
	public void setModificacionHojaVida(HistoricoModificacionHojaVida modificacionHojaVida) {
		this.modificacionHojaVida = modificacionHojaVida;
	}

	/**
	 * @return the edFormalEditado
	 */
	public EducacionFormalExt getEdFormalEditado() {
		return edFormalEditado;
	}

	/**
	 * @param edFormalEditado
	 *            the edFormalEditado to set
	 */
	public void setEdFormalEditado(EducacionFormalExt edFormalEditado) {
		this.edFormalEditado = edFormalEditado;
	}

	/**
	 * @return the listaEducacionFormalPorPersona
	 */
	public List<EducacionFormalExt> getListaEducacionFormalPorPersona() {
		return listaEducacionFormalPorPersona;
	}

	/**
	 * @param listaEducacionFormalPorPersona
	 *            the listaEducacionFormalPorPersona to set
	 */
	public void setListaEducacionFormalPorPersona(List<EducacionFormalExt> listaEducacionFormalPorPersona) {
		this.listaEducacionFormalPorPersona = listaEducacionFormalPorPersona;
	}

	/**
	 * @return the listaOtroConocimientoPorPersona
	 */
	public List<OtroConocimientoExt> getListaOtroConocimientoPorPersona() {
		return listaOtroConocimientoPorPersona;
	}

	/**
	 * @param listaOtroConocimientoPorPersona
	 *            the listaOtroConocimientoPorPersona to set
	 */
	public void setListaOtroConocimientoPorPersona(List<OtroConocimientoExt> listaOtroConocimientoPorPersona) {
		this.listaOtroConocimientoPorPersona = listaOtroConocimientoPorPersona;
	}

	/**
	 * @return the listaIdiomasPorPersona
	 */
	public List<IdiomaPersonaExt> getListaIdiomasPorPersona() {
		return listaIdiomasPorPersona;
	}

	/**
	 * @param listaIdiomasPorPersona
	 *            the listaIdiomasPorPersona to set
	 */
	public void setListaIdiomasPorPersona(List<IdiomaPersonaExt> listaIdiomasPorPersona) {
		this.listaIdiomasPorPersona = listaIdiomasPorPersona;
	}

	/**
	 * @return the habilitadoFormularioEducacion
	 */
	public Boolean getHabilitadoFormularioEducacion() {
		return habilitadoFormularioEducacion;
	}

	/**
	 * @param habilitadoFormularioEducacion
	 *            the habilitadoFormularioEducacion to set
	 */
	public void setHabilitadoFormularioEducacion(Boolean habilitadoFormularioEducacion) {
		this.habilitadoFormularioEducacion = habilitadoFormularioEducacion;
	}

	/**
	 * @return the habilitadoFormularioCertificacion
	 */
	public Boolean getHabilitadoFormularioCertificacion() {
		return habilitadoFormularioCertificacion;
	}

	/**
	 * @param habilitadoFormularioCertificacion
	 *            the habilitadoFormularioCertificacion to set
	 */
	public void setHabilitadoFormularioCertificacion(Boolean habilitadoFormularioCertificacion) {
		this.habilitadoFormularioCertificacion = habilitadoFormularioCertificacion;
	}

	/**
	 * @return the habilitadoFormularioIdiomas
	 */
	public Boolean getHabilitadoFormularioIdiomas() {
		return habilitadoFormularioIdiomas;
	}

	/**
	 * @param habilitadoFormularioIdiomas
	 *            the habilitadoFormularioIdiomas to set
	 */
	public void setHabilitadoFormularioIdiomas(Boolean habilitadoFormularioIdiomas) {
		this.habilitadoFormularioIdiomas = habilitadoFormularioIdiomas;
	}

	/**
	 * @return the habilitadoArchivoSoporte
	 */
	public Boolean getHabilitadoArchivoSoporte() {
		return habilitadoArchivoSoporte;
	}

	/**
	 * @param habilitadoArchivoSoporte
	 *            the habilitadoArchivoSoporte to set
	 */
	public void setHabilitadoArchivoSoporte(Boolean habilitadoArchivoSoporte) {
		this.habilitadoArchivoSoporte = habilitadoArchivoSoporte;
	}

	/**
	 * @return the habilitadoCampo
	 */
	public Boolean getHabilitadoCampo() {
		return habilitadoCampo;
	}

	/**
	 * @param habilitadoCampo
	 *            the habilitadoCampo to set
	 */
	public void setHabilitadoCampo(Boolean habilitadoCampo) {
		this.habilitadoCampo = habilitadoCampo;
	}

	/**
	 * @return the flgValidRolPermission
	 */
	public Boolean getFlgValidRolPermission() {
		return flgValidRolPermission;
	}

	/**
	 * @param flgValidRolPermission
	 *            the flgValidRolPermission to set
	 */
	public void setFlgValidRolPermission(Boolean flgValidRolPermission) {
		this.flgValidRolPermission = flgValidRolPermission;
	}

	/**
	 * @return the flgValidadoAcademica
	 */
	public Boolean getFlgValidadoAcademica() {
		return flgValidadoAcademica;
	}

	/**
	 * @param flgValidadoAcademica
	 *            the flgValidadoAcademica to set
	 */
	public void setFlgValidadoAcademica(Boolean flgValidadoAcademica) {
		this.flgValidadoAcademica = flgValidadoAcademica;
	}

	/**
	 * @return the codFormulario
	 */
	public Integer getCodFormulario() {
		return codFormulario;
	}

	/**
	 * @param codFormulario
	 *            the codFormulario to set
	 */
	public void setCodFormulario(Integer codFormulario) {
		this.codFormulario = codFormulario;
	}

	/**
	 * @return the nombreArchivoAcademica
	 */
	public String getNombreArchivoAcademica() {
		return nombreArchivoAcademica;
	}

	/**
	 * @param nombreArchivoAcademica
	 *            the nombreArchivoAcademica to set
	 */
	public void setNombreArchivoAcademica(String nombreArchivoAcademica) {
		this.nombreArchivoAcademica = nombreArchivoAcademica;
	}

	/**
	 * @return the nombreArchivoHumano
	 */
	public String getNombreArchivoHumano() {
		return nombreArchivoHumano;
	}

	/**
	 * @param nombreArchivoHumano
	 *            the nombreArchivoHumano to set
	 */
	public void setNombreArchivoHumano(String nombreArchivoHumano) {
		this.nombreArchivoHumano = nombreArchivoHumano;
	}

	/**
	 * @return the nombreArchivoIdioma
	 */
	public String getNombreArchivoIdioma() {
		return nombreArchivoIdioma;
	}

	/**
	 * @param nombreArchivoIdioma
	 *            the nombreArchivoIdioma to set
	 */
	public void setNombreArchivoIdioma(String nombreArchivoIdioma) {
		this.nombreArchivoIdioma = nombreArchivoIdioma;
	}

	/**
	 * @return the flgNivelEducativo
	 */
	public Boolean getFlgNivelEducativo() {
		return flgNivelEducativo;
	}

	/**
	 * @param flgNivelEducativo
	 *            the flgNivelEducativo to set
	 */
	public void setFlgNivelEducativo(Boolean flgNivelEducativo) {
		this.flgNivelEducativo = flgNivelEducativo;
	}

	
	
	
	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public String getNombreArchivoTarjetaprofesional() {
		return nombreArchivoTarjetaprofesional;
	}

	public void setNombreArchivoTarjetaprofesional(String nombreArchivoTarjetaprofesional) {
		this.nombreArchivoTarjetaprofesional = nombreArchivoTarjetaprofesional;
	}

	/**
	 * @return the lbUpload
	 */
	public boolean isLbUpload() {
		return lbUpload;
	}

	/**
	 * @param lbUpload the lbUpload to set
	 */
	public void setLbUpload(boolean lbUpload) {
		this.lbUpload = lbUpload;
	}
	
	/**
	 * @return the blnPaisDiferenteColombia
	 */
	public Boolean getBlnPaisDiferenteColombia() {
		return blnPaisDiferenteColombia;
	}

	/**
	 * @param blnPaisDiferenteColombia the blnPaisDiferenteColombia to set
	 */
	public void setBlnPaisDiferenteColombia(Boolean blnPaisDiferenteColombia) {
		this.blnPaisDiferenteColombia = blnPaisDiferenteColombia;
	}
	

	/**
	 * @return the blnHabilitarFechaConvalidacion
	 */
	public Boolean getBlnHabilitarFechaConvalidacion() {
		return blnHabilitarFechaConvalidacion;
	}

	/**
	 * @param blnHabilitarFechaConvalidacion the blnHabilitarFechaConvalidacion to set
	 */
	public void setBlnHabilitarFechaConvalidacion(Boolean blnHabilitarFechaConvalidacion) {
		this.blnHabilitarFechaConvalidacion = blnHabilitarFechaConvalidacion;
	}
	
	/**
	 * @return the lblNoAplica
	 */
	public String getLblNoAplica() {
		return lblNoAplica;
	}

	/**
	 * @param lblNoAplica the lblNoAplica to set
	 */
	public void setLblNoAplica(String lblNoAplica) {
		this.lblNoAplica = lblNoAplica;
	}
	
	/**
	 * @return the blnDeshabilitarProgramaAcademico
	 */
	public Boolean getBlnDeshabilitarProgramaAcademico() {
		return blnDeshabilitarProgramaAcademico;
	}

	/**
	 * @param blnDeshabilitarProgramaAcademico the blnDeshabilitarProgramaAcademico to set
	 */
	public void setBlnDeshabilitarProgramaAcademico(Boolean blnDeshabilitarProgramaAcademico) {
		this.blnDeshabilitarProgramaAcademico = blnDeshabilitarProgramaAcademico;
	}

	/**
	 * @return the strMensajeTituloObtenido
	 */
	public String getStrMensajeTituloObtenido() {
		return strMensajeTituloObtenido;
	}

	/**
	 * @param strMensajeTituloObtenido the strMensajeTituloObtenido to set
	 */
	public void setStrMensajeTituloObtenido(String strMensajeTituloObtenido) {
		this.strMensajeTituloObtenido = strMensajeTituloObtenido;
	}
	
	/**
	 * @return the blnDeshabilitarTituloObtener
	 */
	public Boolean getBlnDeshabilitarTituloObtener() {
		return blnDeshabilitarTituloObtener;
	}

	/**
	 * @param blnDeshabilitarTituloObtener the blnDeshabilitarTituloObtener to set
	 */
	public void setBlnDeshabilitarTituloObtener(Boolean blnDeshabilitarTituloObtener) {
		this.blnDeshabilitarTituloObtener = blnDeshabilitarTituloObtener;
	}
	
	/**
	 * @return the blnDeshabilitarEstudioExterior
	 */
	public Boolean getBlnDeshabilitarEstudioExterior() {
		return blnDeshabilitarEstudioExterior;
	}

	/**
	 * @param blnDeshabilitarEstudioExterior the blnDeshabilitarEstudioExterior to set
	 */
	public void setBlnDeshabilitarEstudioExterior(Boolean blnDeshabilitarEstudioExterior) {
		this.blnDeshabilitarEstudioExterior = blnDeshabilitarEstudioExterior;
	}

	/**
	 * @return the institucionEducativa
	 */
	public InstitucionEducativa getInstitucionEducativa() {
		return institucionEducativa;
	}

	/**
	 * @param institucionEducativa the institucionEducativa to set
	 */
	public void setInstitucionEducativa(InstitucionEducativa institucionEducativa) {
		this.institucionEducativa = institucionEducativa;
	}
	

	public List<InstitucionEducativaExt> getLstInstitucionEducativa() {
		return lstInstitucionEducativa;
	}

	public void setLstInstitucionEducativa(List<InstitucionEducativaExt> lstInstitucionEducativa) {
		this.lstInstitucionEducativa = lstInstitucionEducativa;
	}

	@Override
	public String persist() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}