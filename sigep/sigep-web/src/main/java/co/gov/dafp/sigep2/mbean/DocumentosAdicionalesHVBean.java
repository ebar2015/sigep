package co.gov.dafp.sigep2.mbean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
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
import co.gov.dafp.sigep2.entities.DocumentosAdicionalesHv;
import co.gov.dafp.sigep2.entities.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.DocumentosAdicionalesHvExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.utils.ErrorMensajes;

@Named
@ViewScoped
@ManagedBean
public class DocumentosAdicionalesHVBean extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private final BigDecimal CODEPDF = new BigDecimal(692);
	private UsuarioDTO usuarioSesion = new UsuarioDTO();
	protected Integer codAudCodRol;
	protected Integer codAudAccionInsert = 3;
	protected Integer codAudAccionUpdate = 2;
	protected BigDecimal codAudUsuario,codigoPersona;
	private List<DocumentosAdicionalesHvExt> lstDocumentosAdicionales ;
	private String strNombreArchivoDocumento;
	private UploadedFile cargarDodumento = null;
	private Long tamPdf;
	private DocumentosAdicionalesHvExt documentoSeleccionado;
	private PersonaExt persona;
	private String rutaArchivo = null;
	private boolean habilitaFormulario = false;
	private String strMensajeConfirmacion;
	private BigDecimal tipoDocumentoAdicional;
	private String descripciontipo;
	private String msgAdicionalduplicado;
	
	private boolean lbAccionCancelar = false;
	private boolean lbAccionEliminar = false;
	private boolean lbEsConsulta = false;
	private boolean lbEsModificar = false;
	private boolean lbUpload = false;
	private boolean lbNuevo = false;
	private String fileUploadEvent;
	private long iddoc;

	private ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	private DatosPersonalesBean mBDatosPersonalesBean = (DatosPersonalesBean) elContext.getELResolver().getValue(elContext,	null, "datosPersonalesBean");	
	
	private HistoricoModificacionHojaVida modificacionHojaVida;	
	static final long COD_JEFE_CONTROL_INTERNO = 15; // variable para envio de correos desde modificar HV

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		usuarioSesion = (UsuarioDTO) externalContext.getSessionMap().get("usuarioSesion");	
		lbUpload = false;
		try {
			HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
			String idPersona = request.getParameter("id");
			if(idPersona != null) {
				codigoPersona = new BigDecimal(idPersona);
			}else {
				codigoPersona = BigDecimal.valueOf(usuarioSesion.getCodPersona());	
			}
		} catch (Exception e) {
			codigoPersona = BigDecimal.valueOf(usuarioSesion.getCodPersona());	
		}
		codAudUsuario = BigDecimal.valueOf(usuarioSesion.getId());
		codAudCodRol  = (int) this.getRolAuditoria().getId();
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		Parametrica param = ComunicacionServiciosSis.getParametricaporId(CODEPDF);
		try {
			tamPdf = Long.parseLong(param.getValorParametro());
		} catch (NumberFormatException e) {
			tamPdf = (long) 800000;
		}
		
		persona = ComunicacionServiciosHV.getPersonaporIdExt(codigoPersona.longValue());
		cargarListaDocumentosAdicionales();
		setStrMensajeConfirmacion(MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_SEGURO_ELIMINAR_REGISTRO, getLocale()));
		msgAdicionalduplicado = MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_DOC_ADICIONAL_EXISTE, getLocale());
		
		modificacionHojaVida = new HistoricoModificacionHojaVida();
		
	}

	@Override
	public String persist() throws NotSupportedException {
		return null;
	}
	
	
	public void documentoUpload(FileUploadEvent e) throws IOException {
		cargarDodumento = e.getFile();
		String response = "";
		byte[] bytes = null;
			try {
				if (null != cargarDodumento) {  
					bytes = cargarDodumento.getContents();
					String ext = FilenameUtils.getExtension(cargarDodumento.getFileName());
					String filename = persona.getNumeroIdentificacion() + "." + ext;
					String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_DOCUMENTOS_ADICIONALES)+ filename;
					String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
					
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(bytes);
					stream.close();
					if(!lbEsModificar) {
						ErrorMensajes resp = null;
						if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("0")){
							response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
									                         ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_DOCUMENTOS_ADICIONALES,
									                         WebUtils.TP_DOCUMENTOS_ADICIONALES, mBDatosPersonalesBean.getPersona().getNumeroIdentificacion() );
							Gson gson = new Gson();
							resp = gson.fromJson(response, ErrorMensajes.class);
						}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("1")){/*Operacion cliente windows*/
							resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_DOCUMENTOS_ADICIONALES, WebUtils.TP_DOCUMENTOS_ADICIONALES, filePath, persona.getNumeroIdentificacion());			
						}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente windows*/
							resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_DOCUMENTOS_ADICIONALES, WebUtils.TP_DOCUMENTOS_ADICIONALES, filePath, persona.getNumeroIdentificacion());	
						}						
						
						if(!resp.isError()) {
							documentoSeleccionado.setUrlDocumentoAdicional(resp.getUrlArchivo());
							lbUpload = true;
							String rutaF = documentoSeleccionado.getUrlDocumentoAdicional();
							rutaArchivo = WebUtils.WS_MULTIMEDIA_SHOW+""+rutaF;
							RequestContext.getCurrentInstance().execute("uploadEmpy(0)");
							
							this.setStrNombreArchivoDocumento(resp.getNombreArchivo());
							mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
									MessagesBundleConstants
											.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_EXITOSA, getLocale())
											.replace("%nombrearchivo%", resp.getNombreArchivo()));	
						}else {
							mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
									MessagesBundleConstants
											.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
						}
					}else {
						fileUploadEvent = filePath;
						RequestContext.getCurrentInstance().execute("PF('dlgAdicionalRepetido').show();");
					}
				}

			} catch (Exception e2) {
				logger.log().error("******************** documentoUpload() ********************", response);
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants
								.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
			}
		
	}	
	

	public static void enviarNotificacioncambios(String usuarioModificado, String correoUsuarioModificado, List<String> camposEditados){
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		EntidadDTO entidadContexto = (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");
		BigDecimal bdEntidad = BigDecimal.valueOf(entidadContexto.getId());		
		
		PersonaExt personaControlInterno = new PersonaExt();
		personaControlInterno.setCodRol(new BigDecimal(COD_JEFE_CONTROL_INTERNO));/*JEFE DE CONTROL INTERNO*/
		personaControlInterno.setCodEntidad(bdEntidad);
		
		List<PersonaExt> listaPersonalControlInterno = ComunicacionServiciosHV.getPersonaControlInterno(personaControlInterno);
		List<String> email = new ArrayList<>();
		List<String> ccEmail = new ArrayList<>();

		try {
			for (PersonaExt personaExt : listaPersonalControlInterno) {
				ccEmail.add(personaExt.getCorreoElectronico());
			}
			email.add(correoUsuarioModificado);
			HojaDeVidaDelegate.emailActualizacionDatosPersonaDC(usuarioModificado,new Date(), personaControlInterno.getNombreUsuario(), email,
					ccEmail, camposEditados);
		} catch (Exception e) {
			logger.error("void enviarNotificacioncambios() - enviar correo", e);
		}	
		
	}
	public void cancelarConfirmacionDatos() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogJustificaDocAdicional').hide();");
	}	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   Apr 9, 2019, 11:17:15 AM
	 * @File:   DocumentosAdicionalesHVBean.java
	 */
	public void confirmarReemplazarDocumento() {/*es otro upload*/
		String response = "";
		Gson gson = new Gson();
		String nombreArchivo = "";
		try{
			String filePath = fileUploadEvent;
			RequestContext.getCurrentInstance().execute("PF('dlgAdicionalRepetido').hide();");
			String filename = persona.getNumeroIdentificacion() + ".pdf";
			ErrorMensajes resp = null;
			if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("0")){
					response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
							                         ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_DOCUMENTOS_ADICIONALES,
							                         WebUtils.TP_DOCUMENTOS_ADICIONALES, mBDatosPersonalesBean.getPersona().getNumeroIdentificacion() );
					resp = gson.fromJson(response, ErrorMensajes.class);
			}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("1")){/*Operacion cliente windows*/
				resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_DOCUMENTOS_ADICIONALES, WebUtils.TP_DOCUMENTOS_ADICIONALES, filePath, persona.getNumeroIdentificacion());			
			}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente windows*/
				resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_DOCUMENTOS_ADICIONALES, WebUtils.TP_DOCUMENTOS_ADICIONALES, filePath, persona.getNumeroIdentificacion());	
			}					
			if(!resp.isError()) {
				documentoSeleccionado.setUrlDocumentoAdicional(resp.getUrlArchivo());
				nombreArchivo = resp.getNombreArchivo();
			}else {
				documentoSeleccionado.setUrlDocumentoAdicional(resp.getUrlArchivo());
				nombreArchivo = filename;
			}
			lbUpload = true;
			lbEsConsulta = false;
			String rutaF = documentoSeleccionado.getUrlDocumentoAdicional();
			rutaArchivo = WebUtils.WS_MULTIMEDIA_SHOW+""+rutaF;
			RequestContext.getCurrentInstance().execute("uploadEmpy(0)");
			this.setStrNombreArchivoDocumento(nombreArchivo);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_EXITOSA, getLocale()));
		} catch (Exception e2) {
			RequestContext.getCurrentInstance().execute("PF('dlgAdicionalRepetido').hide();");
			logger.log().error("documentoUpload()", e2);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
		}
	}
	
	@Override
	public void retrieve() throws NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String update() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		// TODO Auto-generated method stub
		
	}
	
	public void nuevoDocumentoAdicional(){
		documentoSeleccionado = new DocumentosAdicionalesHvExt();
		habilitaFormulario = true;
		lbNuevo = true;
		documentoSeleccionado.setCodPersona(persona.getCodPersona());
		documentoSeleccionado.setCodDocumentoAdicional(null);
		tipoDocumentoAdicional = null;
		descripciontipo = "";
		rutaArchivo = "";
		strNombreArchivoDocumento = "";
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   2 abr. 2019, 7:58:36
	 * @File:   DocumentosAdicionalesHVBean.java
	 */
	public void guardarDocumentoAdicional(){
		if(mBDatosPersonalesBean.isLbISGestionarHV()){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogJustificaDocAdicional').show()");
		}else{
			guardarDocumentoAdicionalSinConfirmacion();
		}		
	}
	
	public void guardarDocumentoAdicionalSinConfirmacion(){
		if(!lbUpload) {
			RequestContext.getCurrentInstance().execute("uploadEmpy(1)");
			return;
		}
		documentoSeleccionado.setCodTipoDocumentoAdicional(tipoDocumentoAdicional);
		documentoSeleccionado.setAudFechaActualizacion(new Date());
		documentoSeleccionado.setFlgActivo((short) 1);
		if(lbEsModificar) {
			documentoSeleccionado.setAudAccion(785);
			documentoSeleccionado.setCodDocumentoAdicional(iddoc);
		}else {
			documentoSeleccionado.setAudAccion(63);
		}
		documentoSeleccionado.setFlgValidado((short) 0);
		documentoSeleccionado.setAudCodRol((int) getUsuarioSesion().getCodRol());
		documentoSeleccionado.setAudCodUsuario(getUsuarioSesion().getId());
		if(lbNuevo) {
			lbNuevo = false;
			documentoSeleccionado.setCodDocumentoAdicional(null);
		}
		DocumentosAdicionalesHv res = ComunicacionServiciosHV.setDocumentosAdicionalesHv(documentoSeleccionado);
		if(!res.isError()){
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			cancelarDocumentoAdicional();
			cargarListaDocumentosAdicionales();
			mBDatosPersonalesBean.actualizaModificacionHv();
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error guardando");
		}
		strNombreArchivoDocumento = "";
		lbUpload = false;
	}	
	
	public void guardarDocumentoAdicionalConConfirmacion(){
		
		List<String> camposEditados = new ArrayList<>();
		camposEditados.add("Documento Adicional: " + documentoSeleccionado.getNombretipo());
		camposEditados.add("Justificación : " + modificacionHojaVida.getJustificacionModificacion());
		enviarNotificacioncambios(persona.getNombreUsuario(),persona.getCorreoElectronico(),camposEditados);		
		HojaVidaExt hojaVidaFilter = new HojaVidaExt();
		hojaVidaFilter.setCodPersona(persona.getCodPersona());
		hojaVidaFilter.setFlgActivo(true);
		hojaVidaFilter.setLimitEnd(1);
		List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);
		if(listHojaVida!=null && listHojaVida.size()>0){
			modificacionHojaVida.setCodHojaVida(BigDecimal.valueOf(listHojaVida.get(0).getCodHojaVida()));
			modificacionHojaVida.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
			modificacionHojaVida.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
			modificacionHojaVida.setAudCodRol((int) this.getRolAuditoria().getId());
			modificacionHojaVida.setAudFechaModificacion(DateUtils.getFechaSistema());
			ComunicacionServiciosHV.setmodificacionhohadevida(modificacionHojaVida);
			modificacionHojaVida = new HistoricoModificacionHojaVida();
		}
		guardarDocumentoAdicionalSinConfirmacion();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogJustificaDocAdicional').hide()");
		cargarListaDocumentosAdicionales();
		habilitaFormulario=false;
	}		
	
	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   2 abr. 2019, 8:05:54
	 * @File:   DocumentosAdicionalesHVBean.java
	 */
	public void aceptarConsulta() {
		habilitaFormulario = false;
		lbEsConsulta = false;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   2 abr. 2019, 8:06:00
	 * @File:   DocumentosAdicionalesHVBean.java
	 */
	public void solicitarConfirmacionCancelar(){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PREGUNTA_CANCELAR, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionDocumentoAdicional').show();");
		accionesFalse();
		lbAccionCancelar = true;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   Apr 9, 2019, 1:56:23 PM
	 * @File:   DocumentosAdicionalesHVBean.java
	 */
	public void cancelarDocumentoAdicional(){
		mBDatosPersonalesBean.getUiDatosModificados().setValue("0");
		RequestContext.getCurrentInstance().update("frmPrincipal:modificadoHV");
		accionesFalse();
		cargarListaDocumentosAdicionales();
		habilitaFormulario=false;
		lbEsModificar = false;
		lbEsConsulta = false;
		
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   Apr 9, 2019, 1:56:17 PM
	 * @File:   DocumentosAdicionalesHVBean.java
	 * @param doc
	 */
	public void solicitarConfirmacionEliminarDocumentoAdicional(DocumentosAdicionalesHvExt doc){
		documentoSeleccionado = doc;
		RequestContext.getCurrentInstance().update("frmPrincipal:dlgConfirmarAccionDocumentoAdicional");
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionDocumentoAdicional').show();");
		accionesFalse();
		lbAccionEliminar = true;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   1 abr. 2019, 10:58:42
	 * @File:   DocumentosAdicionalesHVBean.java
	 */
	public void eliminarDocumentoAdicional(){
		DocumentosAdicionalesHv res = ComunicacionServiciosHV.delDocumentosAdicionalesHVById(documentoSeleccionado);
		if(!res.isError()){
			
			mBDatosPersonalesBean.actualizaModificacionHv();
			
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_HV_GP_ELIMINADO_EXITO);
			cancelarDocumentoAdicional();
			cargarListaDocumentosAdicionales();
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error guardando");
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   1 abr. 2019, 10:58:12
	 * @File:   DocumentosAdicionalesHVBean.java
	 */
	public void cargarListaDocumentosAdicionales(){
		DocumentosAdicionalesHv buscador = new DocumentosAdicionalesHv();
		buscador.setCodPersona(persona.getCodPersona());
		buscador.setFlgActivo((short) 1);
		lbEsConsulta = false;
		lstDocumentosAdicionales = ComunicacionServiciosHV.getDocumentosAdicionalesHVFiltro(buscador);
		if(!lstDocumentosAdicionales.isEmpty()) {
			for (int i = 0; i < lstDocumentosAdicionales.size(); i++) {
				lstDocumentosAdicionales.get(i).setUrlDocumentoAdicional(WebUtils.validarUrl(lstDocumentosAdicionales.get(i).getUrlDocumentoAdicional(), persona.getNumeroIdentificacion(), lstDocumentosAdicionales.get(i).getCodDocumentoAdicional()+"", WebUtils.TP_DOCUMENTOS_ADICIONALES));
			}
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   1 abr. 2019, 10:58:08
	 * @File:   DocumentosAdicionalesHVBean.java
	 * @param doc
	 */
	public void modificarDocumentoAdicional(DocumentosAdicionalesHvExt doc){
		documentoSeleccionado = doc;
		tipoDocumentoAdicional = doc.getCodTipoDocumentoAdicional();
		iddoc = doc.getCodDocumentoAdicional();
		descripciontipo = doc.getDescripciontipo();
		String ruta = documentoSeleccionado.getUrlDocumentoAdicional();
//		rutaArchivo = WebUtils.getUrlFileHadoop(ruta, persona.getNumeroIdentificacion(), doc.getCodDocumentoAdicional()+"", WebUtils.TP_DOCUMENTOS_ADICIONALES);
		rutaArchivo = WebUtils.getUrlFile(ruta);
		habilitaFormulario = true;
		lbEsConsulta = false;
		lbEsModificar = true;
		lbUpload = true;
		strNombreArchivoDocumento = "";
		
		modificacionHojaVida = new HistoricoModificacionHojaVida();
		modificacionHojaVida.setJustificacionModificacion("");		
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   1 abr. 2019, 10:58:21
	 * @File:   DocumentosAdicionalesHVBean.java
	 * @param doc
	 */
	public void consultarDocumentoAdicional(DocumentosAdicionalesHvExt doc){
		documentoSeleccionado = doc;
		documentoSeleccionado.setCodDocumentoAdicional(doc.getCodDocumentoAdicional());
		tipoDocumentoAdicional = doc.getCodTipoDocumentoAdicional();
		descripciontipo = doc.getDescripciontipo();
		String ruta = documentoSeleccionado.getUrlDocumentoAdicional();
//		rutaArchivo = WebUtils.getUrlFileHadoop(ruta, persona.getNumeroIdentificacion(), doc.getCodDocumentoAdicional()+"", WebUtils.TP_DOCUMENTOS_ADICIONALES);
		rutaArchivo = WebUtils.getUrlFile(ruta);
		habilitaFormulario 			= true;
		lbEsConsulta 				= true;
		lbEsModificar 				= false;
		strNombreArchivoDocumento 	= "";
	}
	
	private void accionesFalse(){
		lbAccionCancelar = false;
		lbAccionEliminar = false; 
	}
	
	public void accionConfirmada(){
		if(lbAccionCancelar){
			cancelarDocumentoAdicional();
		}
		if(lbAccionEliminar) {
			eliminarDocumentoAdicional();
		}
	}

	public String getStrNombreArchivoDocumento() {
		return strNombreArchivoDocumento;
	}

	public void setStrNombreArchivoDocumento(String strNombreArchivoDocumento) {
		this.strNombreArchivoDocumento = strNombreArchivoDocumento;
	}


	public List<DocumentosAdicionalesHvExt> getLstDocumentosAdicionales() {
		return lstDocumentosAdicionales;
	}

	public void setLstDocumentosAdicionales(List<DocumentosAdicionalesHvExt> lstDocumentosAdicionales) {
		this.lstDocumentosAdicionales = lstDocumentosAdicionales;
	}

	public DocumentosAdicionalesHv getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(DocumentosAdicionalesHvExt documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}

	public UploadedFile getCargarDodumento() {
		return cargarDodumento;
	}

	public void setCargarDodumento(UploadedFile cargarDodumento) {
		this.cargarDodumento = cargarDodumento;
	}

	public Long getTamPdf() {
		return tamPdf;
	}

	public void setTamPdf(Long tamPdf) {
		this.tamPdf = tamPdf;
	}
	

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public boolean isHabilitaFormulario() {
		return habilitaFormulario;
	}

	public void setHabilitaFormulario(boolean habilitaFormulario) {
		this.habilitaFormulario = habilitaFormulario;
	}

	public String getStrMensajeConfirmacion() {
		return strMensajeConfirmacion;
	}

	public void setStrMensajeConfirmacion(String strMensajeConfirmacion) {
		this.strMensajeConfirmacion = strMensajeConfirmacion;
	}

	public BigDecimal getTipoDocumentoAdicional() {
		return tipoDocumentoAdicional;
	}

	public void setTipoDocumentoAdicional(BigDecimal tipoDocumentoAdicional) {
		this.tipoDocumentoAdicional = tipoDocumentoAdicional;
	}

	/**
	 * @return the descripciontipo
	 */
	public String getDescripciontipo() {
		return descripciontipo;
	}

	/**
	 * @param descripciontipo the descripciontipo to set
	 */
	public void setDescripciontipo(String descripciontipo) {
		this.descripciontipo = descripciontipo;
	}
	/**
	 * 
	 * @param event
	 */
	public void leerDescripcion(final AjaxBehaviorEvent event) {
		Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(tipoDocumentoAdicional);
		this.descripciontipo = parametrica.getDescripcion() ;
		for (DocumentosAdicionalesHvExt documentosAdicionalesHvExt : lstDocumentosAdicionales) {
			if(tipoDocumentoAdicional.longValue() == documentosAdicionalesHvExt.getCodTipoDocumentoAdicional().longValue()) {
				this.documentoSeleccionado = documentosAdicionalesHvExt;
			}
		}
		
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   Apr 3, 2019, 3:04:20 PM
	 * @File:   DocumentosAdicionalesHVBean.java
	 */
	public void noreemplazar() {
		tipoDocumentoAdicional = new BigDecimal(0);
		descripciontipo = "";
		habilitaFormulario = false;
	}

	/**
	 * @return the msgAdicionalduplicado
	 */
	public String getMsgAdicionalduplicado() {
		return msgAdicionalduplicado;
	}

	/**
	 * @param msgAdicionalduplicado the msgAdicionalduplicado to set
	 */
	public void setMsgAdicionalduplicado(String msgAdicionalduplicado) {
		this.msgAdicionalduplicado = msgAdicionalduplicado;
	}
	
	
	/**
	 * @return the lbEsModificar
	 */
	public boolean isLbEsModificar() {
		return lbEsModificar;
	}

	/**
	 * @param lbEsModificar the lbEsModificar to set
	 */
	public void setLbEsModificar(boolean lbEsModificar) {
		this.lbEsModificar = lbEsModificar;
	}

	/**
	 * @return the lbEsConsulta
	 */
	public boolean isLbEsConsulta() {
		return lbEsConsulta;
	}

	/**
	 * @param lbEsConsulta the lbEsConsulta to set
	 */
	public void setLbEsConsulta(boolean lbEsConsulta) {
		this.lbEsConsulta = lbEsConsulta;
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

	public HistoricoModificacionHojaVida getModificacionHojaVida() {
		return modificacionHojaVida;
	}

	public void setModificacionHojaVida(HistoricoModificacionHojaVida modificacionHojaVida) {
		this.modificacionHojaVida = modificacionHojaVida;
	}

}
