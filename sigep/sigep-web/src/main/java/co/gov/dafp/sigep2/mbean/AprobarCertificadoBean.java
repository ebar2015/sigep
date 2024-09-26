package co.gov.dafp.sigep2.mbean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entities.DocumentosAdicionalesHv;
import co.gov.dafp.sigep2.entities.EducacionFormal;
import co.gov.dafp.sigep2.entities.ExperienciaProfesional;
import co.gov.dafp.sigep2.entities.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.entities.LogroRecurso;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.ParticipacionInstitucion;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.DatoAdicionalExt;
import co.gov.dafp.sigep2.mbean.ext.DocumentosAdicionalesHvExt;
import co.gov.dafp.sigep2.mbean.ext.EducacionFormalExt;
import co.gov.dafp.sigep2.mbean.ext.EvaluacionDesempenoExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaDocenteExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.mbean.ext.HojaDeVidaPrintExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.IdiomaPersonaExt;
import co.gov.dafp.sigep2.mbean.ext.OtroConocimientoExt;
import co.gov.dafp.sigep2.mbean.ext.ParticipacionProyectoExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PlantaPersonaUtlUanExt;
import co.gov.dafp.sigep2.mbean.ext.PublicacionExt;
import co.gov.dafp.sigep2.mbean.ext.ReconocimientoExt;
import co.gov.dafp.sigep2.mbean.ext.SoporteVariosExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.mbean.hojadevida.DescargarFotoHojaVidaBean;
import co.gov.dafp.sigep2.mbean.hojadevida.FotoHojaVida;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.ErrorMensajes;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
@ManagedBean
public class AprobarCertificadoBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 3547191088862266480L;
	private PersonaExt persona;
	private HojaVidaExt hojadevida;
	
	private Long codPersona 			= null;
	private String rutaArchivo 			= null;
	private String msnUtlUan 			= null;
	private String urlReturn 			= "gestionarHojaDeVida.xhtml?faces-redirect=true";
	private String validarAprobacion 	= null;
	private Boolean flgPanelAprobar 	= true;
	private Boolean panelUtlUan 		= false;
	private Boolean panelCargo 			= false;
	private Boolean panelContrato 		= false;
	private Boolean panelMsnUtlUan 		= false;
	private Integer selectedCodCargo 	= null;
	
	private List<ExperienciaDocenteExt> listExperienciaDocenteExt;
	private List<ExperienciaProfesionalExt> listExperienciaProfesionalExt;
	private List<EducacionFormalExt> listaEducacionFormalExt;
	private List<SoporteVariosExt> listSoportesVarios = new ArrayList<SoporteVariosExt>();
	private List<DocumentosAdicionalesHvExt> lstDocumentosAdicionales;
	
	public static String HADOOP_OPCION_CARGA 	="0";
	private static String HADOOP_USER_REPO 		="N";
	private static String HADOOP_URL_BY_SO 		="N";
	
	
	HojaDeVidaPrintExt hojaDeVidaToPdf;
	private List<String> urlsDocumentos = new ArrayList<>();
	private List<DocumentosAdicionalesHvExt> docsAdi;
	private List<HojaDeVidaPrintExt> listaHojasVidaToPrint = new ArrayList<>();
	private Long codUTLAnt;
	private static final Logger logger = Logger.getInstance(DescargarFotoHojaVidaBean.class);
	
	public PersonaExt getPersona() {
		return persona;
	}

	public void setPersona(PersonaExt persona) {
		this.persona = persona;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) {}

	public List<ExperienciaDocenteExt> getListExperienciaDocenteExt() {
		return listExperienciaDocenteExt;
	}

	public void setListExperienciaDocenteExt(List<ExperienciaDocenteExt> listExperienciaDocenteExt) {
		this.listExperienciaDocenteExt = listExperienciaDocenteExt;
	}

	public List<ExperienciaProfesionalExt> getListExperienciaProfesionalExt() {
		return listExperienciaProfesionalExt;
	}

	public void setListExperienciaProfesionalExt(List<ExperienciaProfesionalExt> listExperienciaProfesionalExt) {
		this.listExperienciaProfesionalExt = listExperienciaProfesionalExt;
	}

	public List<EducacionFormalExt> getListaEducacionFormalExt() {
		return listaEducacionFormalExt;
	}

	public void setListaEducacionFormalExt(List<EducacionFormalExt> listaEducacionFormalExt) {
		this.listaEducacionFormalExt = listaEducacionFormalExt;
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public List<SoporteVariosExt> getListSoportesVarios() {
		return listSoportesVarios;
	}

	public void setListSoportesVarios(List<SoporteVariosExt> listSoportesVarios) {
		this.listSoportesVarios = listSoportesVarios;
	}

	public Boolean getFlgPanelAprobar() {
		return flgPanelAprobar;
	}

	public void setFlgPanelAprobar(Boolean flgPanelAprobar) {
		this.flgPanelAprobar = flgPanelAprobar;
	}

	public HojaVidaExt getHojadevida() {
		return hojadevida;
	}

	public void setHojadevida(HojaVidaExt hojadevida) {
		this.hojadevida = hojadevida;
	}

	public Boolean getPanelUtlUan() {
		return panelUtlUan;
	}

	public void setPanelUtlUan(Boolean panelUtlUan) {
		this.panelUtlUan = panelUtlUan;
	}

	public Boolean getPanelCargo() {
		return panelCargo;
	}

	public void setPanelCargo(Boolean panelCargo) {
		this.panelCargo = panelCargo;
	}

	public Boolean getPanelContrato() {
		return panelContrato;
	}

	public void setPanelContrato(Boolean panelContrato) {
		this.panelContrato = panelContrato;
	}

	public String getMsnUtlUan() {
		return msnUtlUan;
	}

	public void setMsnUtlUan(String msnUtlUan) {
		this.msnUtlUan = msnUtlUan;
	}

	public Boolean getPanelMsnUtlUan() {
		return panelMsnUtlUan;
	}

	public void setPanelMsnUtlUan(Boolean panelMsnUtlUan) {
		this.panelMsnUtlUan = panelMsnUtlUan;
	}

	public List<String> getUrlsDocumentos() {
		return urlsDocumentos;
	}

	public void setUrlsDocumentos(List<String> urlsDocumentos) {
		this.urlsDocumentos = urlsDocumentos;
	}
	
	public void setUrlsDocumentos(String urlsDocumentos) {
        this.urlsDocumentos.add(urlsDocumentos);
    }

	@PostConstruct
	public void init() {		
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String idPersona = request.getParameter("id");
        String validation = request.getParameter("validation");
        
        generateUrlReturn();
        
        try {
			Boolean validRold = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_CONTRATOS,RolDTO.JEFE_TALENTO_HUMANO,RolDTO.OPERADOR_CONTRATOS,RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.OPERADOR_TALENTO_HUMANO);
			if(validRold == false || idPersona == null) {
				this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init() usuarioTieneRolAsignado", e);
		} catch (IOException e) {
			logger.error("void init() finalizarConversacion", e);
		}
        
        codPersona = Long.valueOf(idPersona);
        hojadevida = new HojaVidaExt();
        persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
        DatoAdicionalExt datosAdicionales	 	= ComunicacionServiciosHV.getDatoContactoAdi(codPersona);

        if(validation != null && !validation.equals("")) {
        	byte[] decodedBytes = Base64.getDecoder().decode(validation);
        	validarAprobacion = new String(decodedBytes);
        	reiniciarVerificacion();
        }
        
        listExperienciaDocenteExt = ComunicacionServiciosHV.geExperienciaDocenteBycodPer(codPersona, true);
        listExperienciaProfesionalExt = ComunicacionServiciosHV.getcargoentidadpersona(codPersona, true);
        listaEducacionFormalExt = ComunicacionServiciosHV.getEducacionFormal001(codPersona, true);
        if(validarAprobacion!= null && validarAprobacion.equals("REVERSE_APPROVAL")){
        	if(listaEducacionFormalExt != null) {
        		for (int i = 0; i < listaEducacionFormalExt.size(); i++) {
        			listaEducacionFormalExt.get(i).setFlgValidadoTarjetaProfesional(false);
        			EducacionFormal edu = listaEducacionFormalExt.get(i);
        			ComunicacionServiciosHV.seteducacionformal(edu);
    			}
        	}
        }
        
        DocumentosAdicionalesHv buscador = new DocumentosAdicionalesHv();
		buscador.setCodPersona(new BigDecimal(codPersona));
		buscador.setFlgActivo((short) 1);
		lstDocumentosAdicionales = ComunicacionServiciosHV.getDocumentosAdicionalesHVFiltro(buscador);
        
        if(persona.getCodPersona() != null) {
	        listSoportesVarios.add(new SoporteVariosExt(persona.getCodPersona(), "Documento de identificación", persona.getNombreTipoDocuento()+"-"+persona.getNumeroIdentificacion(), persona.getFlgDocumentoIdentificacionValidado(), persona.getDocumentoIdentificacionUrl(), "PDI"));
	        
	        if(persona.getNombreGenero().equals("MASCULINO")) {
	        	listSoportesVarios.add(new SoporteVariosExt(persona.getCodPersona(), "Libreta Militar", persona.getNumeroLibretaMilitar(), persona.getFlgLibretaMilitarValidada(), persona.getLibretaMilitarUrl(), "PLM"));
	        }
	        
	        if(datosAdicionales.getFlgDiscapacidad()){
	        	listSoportesVarios.add(new SoporteVariosExt(persona.getCodPersona(), "Archivo de discapacidad", "Discapacidad", persona.getFlgDiscapacidadValidada(), persona.getDocumentoDiscapacidadUrl(), "PD"));
	        }
	        
        }
        
        Long codParametro = (long) TipoParametro.HADOOP_BY_SO.getValue();
		if(codParametro!=null && codParametro >0){
			Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(codParametro));
			if(parametrica!=null && parametrica.getValorParametro()!=null ){
				HADOOP_OPCION_CARGA = parametrica.getValorParametro();
			}
		}
		
		codParametro=null;
		codParametro = (long) TipoParametro.HADOOP_URL_BY_SO.getValue();
		if(codParametro!=null && codParametro >0){
			Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(codParametro));
			if(parametrica!=null && parametrica.getValorParametro()!=null ){
				HADOOP_URL_BY_SO = parametrica.getValorParametro();
			}
		}
		
		codParametro=null;
		codParametro = (long) TipoParametro.HADOOP_FOLDER_RAIZ.getValue();
		if(codParametro!=null && codParametro >0){
			Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(codParametro));
			if(parametrica!=null && parametrica.getValorParametro()!=null ){
				HADOOP_USER_REPO = parametrica.getValorParametro();
			}
		}
	}

	public void generateUrlReturn() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String codIdentificacion = request.getParameter("codIdentificacion");
        String numIdentificacion = request.getParameter("numIdentificacion");
        String nombreFilter = request.getParameter("nombreFilter");
        String codTipoAsociacion = request.getParameter("codTipoAsociacion");
        
        String url = "";
        
        if(codIdentificacion != null && !codIdentificacion.equals("null")) {
        	url= url+"&codIdentificacion="+codIdentificacion;
        }
        
        if(numIdentificacion!=null && !numIdentificacion.equals("null")) {
        	url= url+"&numIdentificacion="+numIdentificacion;
        }
        
        if(nombreFilter!=null && !nombreFilter.equals("null") && !nombreFilter.equals("")) {
        	System.out.println("entro a buscar");
        	url= url+"&nombreFilter="+nombreFilter;
        }
        
        if(codTipoAsociacion!=null && !codTipoAsociacion.equals("null") && !codTipoAsociacion.equals("0")) {
        	url= url+"&codTipoAsociacion="+codTipoAsociacion;
        }
        
        urlReturn = urlReturn+url;
	}
	
	
	/**Metodo que se encarga de validar que todas las tablas tengan las casillas de verificacion aprobadas, principalmente
	 * cuanto estas tienen mas de un tabs en la tabla
	 * */
	public boolean validarCheckCompleto() {
		if(!validarCheckCompletoExpProf()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_APROBAR_HV_CHECK_EXP_PROFESIONAL);
			return false;
		}	
		
		if(!validarCheckCompletoEdFormalTarjetaProfesional()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_APROBAR_HV_CHECK_TARJETA_PROFESIONAL);
			return false;
		}
		
		if(!validarCheckCompletoEdFormal()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_APROBAR_HV_CHECK_ED_FORMAL);
			return false;
		}
		
		if(!validarCheckCompletoExpDocente()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_APROBAR_HV_CHECK_EXP_DOCENTE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Metodo que se encarga de validar que todas las experiencias profesionales si tengan el check verificado
	 * para proceder con la aprobación de la HV. Este sólo se valida si existe un documento adjunto
	 * */
	public boolean validarCheckCompletoExpProf() {
		for (ExperienciaProfesionalExt experienciaProfesional : listExperienciaProfesionalExt) {
			if (experienciaProfesional.getUrlDocumentoSoporte()!= null && !experienciaProfesional.getUrlDocumentoSoporte().equals("") &&
					!experienciaProfesional.getFlgValidaJefe()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Metodo que se encarga de validar que todas las Educaciones formales para la opción de tarjeta profesional si tengan el check verificado
	 * para proceder con la aprobación de la HV. Este sólo se valida si existe un documento adjunto
	 * */
	public boolean validarCheckCompletoEdFormalTarjetaProfesional() {
		for (EducacionFormalExt educacionFormal : listaEducacionFormalExt) {
			if (educacionFormal.getUrlTarjetaProfesional()!= null && !educacionFormal.getUrlTarjetaProfesional().equals("") &&
					!educacionFormal.getFlgValidadoTarjetaProfesional()) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Metodo que se encarga de validar que todas las Educaciones formales para la opción de Anexos de la educación Formal si tengan el check verificado
	 * para proceder con la aprobación de la HV. Este sólo se valida si existe un documento adjunto
	 * */
	public boolean validarCheckCompletoEdFormal() {
		for (EducacionFormalExt educacionFormal : listaEducacionFormalExt) {
			if (educacionFormal.getUrlAnexo()!= null && !educacionFormal.getUrlAnexo().equals("") &&
					!educacionFormal.getFlgValidadoRrhh()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Metodo que se encarga de validar que todas las Experiencias Docentes si tengan el check verificado
	 * para proceder con la aprobación de la HV. Este sólo se valida si existe un documento adjunto
	 * */
	public boolean validarCheckCompletoExpDocente() {
		for (ExperienciaDocenteExt experienciaDocente : listExperienciaDocenteExt) {
			if (experienciaDocente.getUrlDocumentoSoporte()!= null && !experienciaDocente.getUrlDocumentoSoporte().equals("") &&
					!experienciaDocente.isFlgVerificado()) {
				return false;
			}
		}
		return true;
	}
	
	
	public void persistEducacionFormal(EducacionFormalExt educacionFormalExt) { 
        
		for (EducacionFormalExt educacionFormal : listaEducacionFormalExt) {
			if (educacionFormal.getCodEducacionFormal().equals(educacionFormalExt.getCodEducacionFormal())) {
				educacionFormal.setFlgValidadoRrhh(educacionFormalExt.getFlgValidadoRrhh());
				educacionFormal.setFlgValidadoTarjetaProfesional(educacionFormalExt.getFlgValidadoTarjetaProfesional());
				educacionFormal.setFechaValidacionRrhh(DateUtils.getFechaSistema());
				educacionFormal.setAudCodUsuario(this.getUsuarioSesion().getId());				
				educacionFormal.setCodUsuarioVerifica(this.getUsuarioSesion().getId());				
				educacionFormal.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
				educacionFormal.setAudCodRol((int) this.getRolAuditoria().getId());
				educacionFormal.setAudFechaActualizacion(DateUtils.getFechaSistema());
				break;
			}
		}
	}
	
	public void validaTarjetaProfesional(EducacionFormalExt educacionFormalExt) { 
        
		for (EducacionFormalExt educacionFormal : listaEducacionFormalExt) {
			if (educacionFormal.getCodEducacionFormal().equals(educacionFormalExt.getCodEducacionFormal())) {
				educacionFormal.setAudCodUsuario(this.getUsuarioSesion().getId());
				educacionFormal.setFlgValidadoTarjetaProfesional(educacionFormalExt.getFlgValidadoTarjetaProfesional());
				educacionFormal.setCodUsuarioVerifica(this.getUsuarioSesion().getId());				
				educacionFormal.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
				educacionFormal.setAudCodRol((int) this.getRolAuditoria().getId());
				educacionFormal.setAudFechaActualizacion(DateUtils.getFechaSistema());
				break;
			}
		}
	}	
	
	public void persistExperienciaDocente(ExperienciaDocenteExt experienciaDocenteExt) {
		
		for (ExperienciaDocenteExt experienciaDocente : listExperienciaDocenteExt) {
			if (experienciaDocente.getCodExperienciaDocente().equals(experienciaDocenteExt.getCodExperienciaDocente())) {
				experienciaDocente.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));				
				experienciaDocente.setCodUsuarioVerifica(BigDecimal.valueOf(this.getUsuarioSesion().getId()));				
				experienciaDocente.setFlgVerificado(experienciaDocenteExt.isFlgVerificado());
				experienciaDocente.setFechaVerificacion(DateUtils.getFechaSistema());
				experienciaDocente.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
				experienciaDocente.setAudCodRol((int) this.getRolAuditoria().getId());
				experienciaDocente.setAudFechaActualizacion(DateUtils.getFechaSistema());
				break;
			}
		}
	}
	
	public void persistExperienciaProfecional(ExperienciaProfesionalExt experienciaProfesionalExt) {
		
		for (ExperienciaProfesionalExt experienciaProfesional : listExperienciaProfesionalExt) {
			if (experienciaProfesional.getCodExperienciaProfesional().equals(experienciaProfesionalExt.getCodExperienciaProfesional())) {
				experienciaProfesional.setFlgValidaJefe(experienciaProfesionalExt.getFlgValidaJefe());
				experienciaProfesional.setFechaValidacion(DateUtils.getFechaSistema());
				experienciaProfesional.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
				experienciaProfesional.setCodUsaurioValida(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
				experienciaProfesional.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
				experienciaProfesional.setAudCodRol((int) this.getRolAuditoria().getId());
				experienciaProfesional.setAudFechaActualizacion(DateUtils.getFechaSistema());
				break;
			}
		}
	}
	
	public void persistSoporteVarios(SoporteVariosExt soporteVariosExt) {
				
		if(soporteVariosExt.getTipoDato().equals("PDI")) {
			persona.setFlgDocumentoIdentificacionValidado(soporteVariosExt.getVerificacion());
		}
		
		if(soporteVariosExt.getTipoDato().equals("PLM")) {
			persona.setFlgLibretaMilitarValidada(soporteVariosExt.getVerificacion());
		}
		
		if(soporteVariosExt.getTipoDato().equals("PD")) {
			persona.setFlgDiscapacidadValidada(soporteVariosExt.getVerificacion());
		}
		
		persona.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		persona.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
		persona.setAudFechaActualizacion(DateUtils.getFechaSistema());
	}
	
	public void persistDocumentoAdicional(DocumentosAdicionalesHvExt doc) {
		doc.setAudFechaActualizacion(new Date());
		doc.setAudCodUsuario(this.getUsuarioSesion().getId());				
		doc.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		ComunicacionServiciosHV.setDocumentosAdicionalesHv(doc);
	}
	
	public void guardar(Boolean redirect) { 
		
		ComunicacionServiciosHV.updatePersona(persona);
		
		for (ExperienciaProfesionalExt experienciaProfesional : listExperienciaProfesionalExt) {
			experienciaProfesional.setFlgActivo(true);
			ComunicacionServiciosHV.setexpProfesional(experienciaProfesional);
		}
		
		for (ExperienciaDocenteExt experienciaDocente : listExperienciaDocenteExt) {
			ComunicacionServiciosHV.setExperiancaDoc(experienciaDocente);
		}
		
		for (EducacionFormalExt educacionFormal : listaEducacionFormalExt) {
			ComunicacionServiciosHV.seteducacionformal(educacionFormal);
		}
		
		if(redirect.equals(true)) {
			mostrarMensajeBotonAccion(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_DATOS_GUARDADOS,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
					"redirectGestionarHv();"); 
		}
	}
	
	public void reiniciarVerificacion() { 
		
		persona.setFlgLibretaMilitarValidada(false);
		persona.setFlgDocumentoIdentificacionValidado(false);
		
		ComunicacionServiciosHV.updatePersona(persona);
		
		ExperienciaProfesional experienciaProfesional = new ExperienciaProfesional();
		experienciaProfesional.setCodPersona(persona.getCodPersona());
		experienciaProfesional.setFechaValidacion(null);
		experienciaProfesional.setAudCodUsuario(BigDecimal.valueOf(getUsuarioSesion().getId()));
		experienciaProfesional.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		experienciaProfesional.setAudCodRol((int) this.getRolAuditoria().getId());
		experienciaProfesional.setAudFechaActualizacion(DateUtils.getFechaSistema());
		
		ComunicacionServiciosHV.updateMasivoHojaVida(experienciaProfesional);
		
	}
    
    public void activarFormularioAprobacion(boolean valid){
    	if(!validarCheckCompleto()) {
			return;
		}
    	flgPanelAprobar = valid;
    	if(valid == false) {
    		this.guardar(false);
    		HojaVidaExt hojaVidaFilter = new HojaVidaExt();
        	hojaVidaFilter.setCodPersona(persona.getCodPersona());
        	hojaVidaFilter.setCodEntidad(this.getEntidadUsuario().getId());
        	hojaVidaFilter.setLimitEnd(1);
        	List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);
        	if(listHojaVida.size() > 0) {
        		hojadevida = listHojaVida.get(0);
        		selectedCodCargo = hojadevida.getCodCargo();
        		if(hojadevida.getNombreUsuarioAprueba() == null || !hojadevida.getNombreUsuarioAprueba().equals(""))
        			hojadevida.setNombreUsuarioAprueba(this.getUsuarioSesion().getNombrePersona());
        	}else {
        		hojadevida = new HojaVidaExt();
        		hojadevida.setNombreEntidad(this.getEntidadUsuario().getNombreEntidad());
        		hojadevida.setNombreUsuarioAprueba(this.getUsuarioSesion().getNombrePersona());
        		hojadevida.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
    			hojadevida.setCodPersona(persona.getCodPersona());
    			hojadevida.setCodEntidad(this.getEntidadUsuario().getId());
        	}
    		this.cambiarModalidad();
    	}
    	else 
    		this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_CANCELAR_APROBAR);
    	
    }
    
    public void volver() {
    	RequestContext.getCurrentInstance().execute("delay(function() { redirectGestionarHv(); }, 2000);");
		this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_CANCELAR_VERIFICAR_SOPORTE);
    }
    
    public void redirectGestionarHv() {
    	try {            
			FacesContext.getCurrentInstance().getExternalContext().redirect(urlReturn);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void cambiarModalidad() {
    	if(hojadevida != null && hojadevida.getCodModalidadContrato() != null) {
    		switch (hojadevida.getCodModalidadContrato()) {
				case 1014:
					if(validarExistenciaUTLs()) {
						codUTLAnt = null;
						panelUtlUan = true;
						panelContrato = false;
						panelCargo = false;
						hojadevida.setDetallesContrato(null);
						hojadevida.setCodCargo(null);
						codUTLAnt = hojadevida.getCodUtlUan() != null ?  hojadevida.getCodUtlUan() : null;
						break;
					}
					break;
				case 1015:
					panelUtlUan = false;
					panelContrato = true;
					panelCargo = false;
					hojadevida.setCodCargo(null);
					hojadevida.setCodUtlUan(null);
					hojadevida.setSalario(null);
					break;
				case 1016:
					panelUtlUan = false;
					panelContrato = false;
					panelCargo = true;
					hojadevida.setCodUtlUan(null);
					hojadevida.setSalario(null);
					hojadevida.setDetallesContrato(null);
					break;
				default:
					panelUtlUan = false;
					panelContrato = false;
					panelCargo = false;
					break;
			}
    	}else {
    		panelUtlUan = false;
			panelContrato = false;
			panelCargo = false;
    	}
    }
    
    /**
     * Metodo que verifica si existen UTLs disponibles para la entidad al momento de seleccionar la modalidad UTL/AUN.
     * En caso de no  existir UTLs con guardado definitivo, el sistema saca mensaje informativo
     * @return existenciaUTL
     */
    public boolean validarExistenciaUTLs() {
    	PlantaPersonaUtlUanExt objPlanta = new PlantaPersonaUtlUanExt();
		objPlanta.setFlgActivo((short)1);
		objPlanta.setCodEntidad(new BigDecimal(this.getEntidadUsuario().getId()));
		objPlanta.setFlgGuardadoParcialPlanta((short)0);
		List<PlantaPersonaUtlUanExt> plantas= ComunicacionServiciosEnt.obtenerPlantaPersonaUTLFiltro(objPlanta);
		if(!plantas.isEmpty()) {
			return true;
		}
		this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_CONSULTA_UTL_HV);
    	return false;
    }
    
	public String visualizarArchivo(String ruta) {
		
		if(ruta == null || ruta == "") {
			rutaArchivo = null;
			return null;
		}
	//	rutaArchivo =  WebUtils.getUrlFileHadoop(ruta, null, null, null);
		rutaArchivo =  WebUtils.getUrlFile(ruta);
		return null;
	}
    
	@Override
	public String persist() {
		if(hojadevida.getCodModalidadContrato() != null && hojadevida.getCodModalidadContrato().equals(TipoParametro.GESTION_HV_UTL_UAN.getValue())) {
			if(panelCargo != null) {
				/*Consultamos si la persona ya cuenta con una vinculación activa a un cargo*/
				VinculacionExt vinFil = new VinculacionExt();
				vinFil.setCodPersona(hojadevida.getCodPersona());
				vinFil.setFlgActivo((short) 1);
				List<VinculacionExt> vinculacionesPer = ComunicacionServiciosVin.getVinculacionFilter(vinFil);
				if(vinculacionesPer == null || (vinculacionesPer != null && !vinculacionesPer.isEmpty())) {
					this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
							"La persona se encuentra actualmente vinculada con una entidad y por lo tanto no se puede vincular a la UTL");
					return null;
				}
				/*Consultamos la planta utl en la que vamos a aprobar la hv del usuario*/
				BigDecimal lbdCantidadSalariosMinimos, lbdValorSalarioMinimo, lbdTopeValorUTL, lbdPresupuestoUtilizadoUTL;
				Integer liCantidadMaximaColaboradores, liCantidadActualColaboradesPlantaUTL;
				liCantidadActualColaboradesPlantaUTL = 0;
				lbdCantidadSalariosMinimos = lbdValorSalarioMinimo = lbdTopeValorUTL =   lbdPresupuestoUtilizadoUTL = new BigDecimal(0); 
				PlantaPersonaUtlUanExt bucadorPlanta = new PlantaPersonaUtlUanExt();
				bucadorPlanta.setFlgActivo((short) 1);
				bucadorPlanta.setCodPlantaPersonaUtlUan(hojadevida.getCodUtlUan());
				List<PlantaPersonaUtlUanExt> lstPlantasUtl = ComunicacionServiciosEnt.obtenerPlantaPersonaUTLFiltro(bucadorPlanta);
				PlantaPersonaUtlUanExt plantaPersonaUtlUanExt=null;
				if(lstPlantasUtl!=null && lstPlantasUtl.size()>0)
					plantaPersonaUtlUanExt = lstPlantasUtl.get(0);
				if(plantaPersonaUtlUanExt == null){
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
							"Error Consultando Planta UTL UAN");
					return null;
				}
				if(plantaPersonaUtlUanExt.getCantidadSalariosUTL()!=null)
					lbdCantidadSalariosMinimos = plantaPersonaUtlUanExt.getCantidadSalariosUTL();
				liCantidadMaximaColaboradores = plantaPersonaUtlUanExt.getCantidadColaboradoresUTL();
				Parametrica pvalorMinimo = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.SALARIOS_MENSUALES_MINIMOS.getValue()));
				if(pvalorMinimo!=null && pvalorMinimo.getValorParametro()!=null && !"".equals(pvalorMinimo)){
					lbdValorSalarioMinimo = BigDecimal.valueOf( Double.parseDouble(pvalorMinimo.getValorParametro()) );
				}else{
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
							"Error Consultando Parametro valor salario minimo");
					return null;
				}
				
				HojaVidaExt hojaFiltro = new HojaVidaExt();
				hojaFiltro.setCodUtlUan(hojadevida.getCodUtlUan());
				hojaFiltro.setFlgActivo((short) 1);
				hojaFiltro.setLimitEnd(10000);
				List<HojaVidaExt> vinculacionesActualesUTL = ComunicacionServiciosHV.getHojaVidafiltro(hojaFiltro);
				if(vinculacionesActualesUTL!=null && vinculacionesActualesUTL.size()>0) {
					for(HojaVidaExt vinculaacionUtl: vinculacionesActualesUTL){
						if(!vinculaacionUtl.getCodHojaVida().equals(hojadevida.getCodHojaVida())){
							liCantidadActualColaboradesPlantaUTL++;
							if(vinculaacionUtl.getSalario()!=null)
								lbdPresupuestoUtilizadoUTL = lbdPresupuestoUtilizadoUTL.add(BigDecimal.valueOf(vinculaacionUtl.getSalario()));
						}
					}
				}
				lbdTopeValorUTL = lbdCantidadSalariosMinimos.multiply(lbdValorSalarioMinimo);
				lbdPresupuestoUtilizadoUTL = lbdPresupuestoUtilizadoUTL.add( BigDecimal.valueOf( hojadevida.getSalario()));
				if(lbdPresupuestoUtilizadoUTL.compareTo(lbdTopeValorUTL) > 0 ){
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
							"La persona no se puede asignar a la UTL seleccionada porque la UTL ha superado el monto máximo");
					return null;					
				}
				if ( (liCantidadActualColaboradesPlantaUTL + 1) > liCantidadMaximaColaboradores ){
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
							"La persona no se puede asignar a la UTL seleccionada porque la UTL ha superado el número máximo de colaboradores");
					return null;		 
				}
				
			}
			
		}		
		if(hojadevida.getCodModalidadContrato() != null && hojadevida.getCodModalidadContrato().equals(TipoParametro.MODALIDAD_CARGO_HV.getValue())) {
			if(validarAprobacion != null && validarAprobacion.equals("MAKE_APPROVAL_WITHOUT")) {
				if(hojadevida.getCodCargo().equals(selectedCodCargo)) {
					RequestContext.getCurrentInstance().execute("delay(function() { redirectGestionarHv(); }, 2000);");
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_VALIDAR_DIFERENTE_UTL_UAN);
					return null;
				}
			}
		}
		
		hojadevida.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		hojadevida.setCodUsuarioAprueba(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		hojadevida.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		hojadevida.setAudCodRol((int) this.getRolAuditoria().getId());
		hojadevida.setAudFechaActualizacion(DateUtils.getFechaSistema());
		hojadevida.setFlgActivo(true);
		hojadevida.setFlgAprobado(true);
		hojadevida.setFechaAprobacion(DateUtils.getFechaSistema());
		
		boolean valid = ComunicacionServiciosHV.setHojaVida(hojadevida);
		
		if(valid){
			this.copiaCompletaHojaVida();
			hojadevida = new HojaVidaExt();
			flgPanelAprobar = true;
			mostrarMensajeBotonAccion(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					 MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA,TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
					"redirectGestionarHv();");
		} 
		else
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
		
		
		return null; 
	}
	 
	public void aceptar() {
		panelMsnUtlUan = false;
		msnUtlUan = null;
	}	
	
	/**
	 * Metodo que valida si la persona ya esta registrada en una UTL, si se selecciono aprobar a la misma utl y esta se cambio
	 */
	public void validarUTLIgual() {

		/*Consultamos si la persona va a cambiar de utl cuando selecciono la opcion registrar misma UTL*/
		if(codUTLAnt!= null && validarAprobacion != null&&
				validarAprobacion.equals("MAKE_APPROVAL_WITH") &&(codUTLAnt!= hojadevida.getCodUtlUan())) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,MessagesBundleConstants.MSG_VALIDAR_UTL_IGUAL);
			hojadevida.setCodUtlUan(codUTLAnt);
		}
	}
	
	public void copiaCompletaHojaVida() {
		
		HojaVidaExt hojaVidaFilter = new HojaVidaExt();
		HistoricoModificacionHojaVida modificacionHojaVida = new HistoricoModificacionHojaVida();
		FotoHojaVida hojaDeVidaCompleta = new FotoHojaVida();
		List<PaisDTO> nacionalidadDTO = null;
		
		LogroRecurso buscadorlogroRecurso = new LogroRecurso();
		buscadorlogroRecurso.setCodPersona( BigDecimal.valueOf(codPersona));
		buscadorlogroRecurso.setFlgActivo((short) 1);
		
		PublicacionExt buscadorPublicaciones = new PublicacionExt();
		buscadorPublicaciones.setCodPersona( BigDecimal.valueOf(codPersona));
		buscadorPublicaciones.setFlgActivo((short) 1);	
		
		EvaluacionDesempenoExt buscadorEvsDesempenno = new EvaluacionDesempenoExt();
		buscadorEvsDesempenno.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadorEvsDesempenno.setFlgActivo((short) 1);
		
		ReconocimientoExt buscadorPremios = new ReconocimientoExt();
		buscadorPremios.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadorPremios.setFlgActivo((short) 1);
		
		ParticipacionProyectoExt buscadorParticipacionproyectos = new ParticipacionProyectoExt();
		buscadorParticipacionproyectos.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadorParticipacionproyectos.setFlgActivo((short) 1);
		
		ParticipacionInstitucion buscadorCorporaciones = new ParticipacionInstitucion();
		buscadorCorporaciones.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadorCorporaciones.setFlgActivo((short) 1);		
		
		
		
    	hojaVidaFilter.setCodPersona(BigDecimal.valueOf(codPersona));
    	hojaVidaFilter.setCodEntidad(this.getEntidadUsuario().getId());
    	hojaVidaFilter.setFlgActivo(true);
    	hojaVidaFilter.setLimitEnd(1);
    	
    	List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);
    	
    	modificacionHojaVida.setJustificacionModificacion("FOTO HOJA DE VIDA EN LA PARTE DE APROBACION");
    	modificacionHojaVida.setCodHojaVida(BigDecimal.valueOf(listHojaVida.get(0).getCodHojaVida()));
		modificacionHojaVida.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		modificacionHojaVida.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		modificacionHojaVida.setAudCodRol((int) this.getRolAuditoria().getId());
		modificacionHojaVida.setAudFechaModificacion(DateUtils.getFechaSistema());
		modificacionHojaVida.setFlgActivo((short) 1);
		
		hojaDeVidaCompleta.setDetallePersona(ComunicacionServiciosHV.getpersonacontacto(codPersona));
		hojaDeVidaCompleta.setDatoAdicional(ComunicacionServiciosHV.getDatoContactoAdi(codPersona));
		hojaDeVidaCompleta.setDatoContacto(ComunicacionServiciosHV.getDatoContacto(codPersona));
		hojaDeVidaCompleta.setDetalleEducacion(ComunicacionServiciosHV.getEducacionFormal001(codPersona, true));
		hojaDeVidaCompleta.setDetalleIdioma(ComunicacionServiciosHV.getidiomapersonaporpersona(codPersona, true));
		hojaDeVidaCompleta.setOtroConocimientoExt(ComunicacionServiciosHV.getotroconocimientoporpersona(codPersona, true));
		hojaDeVidaCompleta.setDetalleLaboral(ComunicacionServiciosHV.getcargoentidadpersona(codPersona, true));
		hojaDeVidaCompleta.setDetalleExpDocente(ComunicacionServiciosHV.geExperienciaDocenteBycodPer(codPersona, true));
		hojaDeVidaCompleta.setLstLogrosRecursos(ComunicacionServiciosHV.getLogrosRecursosFiltro(buscadorlogroRecurso));
		hojaDeVidaCompleta.setLstpublicaciones(ComunicacionServiciosHV.getPublicacionesFiltro(buscadorPublicaciones));
		hojaDeVidaCompleta.setLstReconocimientos(ComunicacionServiciosHV.getPremiosReconocimientosHV(buscadorPremios));
		hojaDeVidaCompleta.setLstParticipacionProyecto(ComunicacionServiciosHV.getParticipacionProyectosHV(buscadorParticipacionproyectos));
		hojaDeVidaCompleta.setLstParticipacionInstitucion(ComunicacionServiciosHV.getParticipacionInstitucionHV(buscadorCorporaciones));
		hojaDeVidaCompleta.setLstEvaluacionDesempeno(ComunicacionServiciosHV.getEvDesempenno(buscadorEvsDesempenno));
		DocumentosAdicionalesHv buscador = new DocumentosAdicionalesHv();
		buscador.setCodPersona(new BigDecimal(codPersona));
		buscador.setFlgActivo((short) 1);
		hojaDeVidaCompleta.setLstDocumentosAdicionales(ComunicacionServiciosHV.getDocumentosAdicionalesHVFiltro(buscador));
		try {
			nacionalidadDTO = HojaDeVidaDelegate.findNacionalidadByCodPersonaId(codPersona);
		} catch (SIGEP2SistemaException e) {
			logger.log().warn("void init() - datosAdicionalesDTO", e);
		}
		
		if (nacionalidadDTO != null) {
			hojaDeVidaCompleta.setNacionalidad(nacionalidadDTO);
		}
		
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		
		String json = gson.toJson(hojaDeVidaCompleta);
		
		BufferedWriter bw;
		Date fechaActual = new Date();
    	String filename = fechaActual.getTime()+".txt";
    	String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_FILE_FOTO_HOJA_VIDA) + filename;
    	String filePath=ConfigurationBundleConstants.getRutaArchivo(ruta);
        File archivo = new File(filePath);
        
        try {
			bw = new BufferedWriter(new FileWriter(archivo));
	        bw.write(json);
	        bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        modificacionHojaVida.setImagenHojaVidaUrl(filename);
        String urlPdf = crearPDFHojaVidaAprobada(hojaDeVidaCompleta);
        modificacionHojaVida.setUrlPdf(urlPdf);
		ComunicacionServiciosHV.setmodificacionhohadevida(modificacionHojaVida);
		
	}
	
	/**
	 * Metodo que se encarga de procesar el json generado, para que sea generado el PDF*/
	public String crearPDFHojaVidaAprobada(FotoHojaVida hojaDeVidaCompleta) {
		hojaDeVidaToPdf = new HojaDeVidaPrintExt();
		listaHojasVidaToPrint = new ArrayList<>();
		hojaDeVidaToPdf.setDetallePersona(hojaDeVidaCompleta.getDetallePersona());
		hojaDeVidaToPdf.setDatoAdicional(hojaDeVidaCompleta.getDatoAdicional());
		hojaDeVidaToPdf.setFlgFormatoCompleto(true);
		hojaDeVidaToPdf.setFlgGerenciaPublica(true);
		hojaDeVidaToPdf.setDetalleEducacion(hojaDeVidaCompleta.getDetalleEducacion());
		hojaDeVidaToPdf.setOtroConocimientoExt(hojaDeVidaCompleta.getOtroConocimientoExt());
		hojaDeVidaToPdf.setDetalleIdioma(hojaDeVidaCompleta.getDetalleIdioma());
		hojaDeVidaToPdf.setDetalleLaboral(hojaDeVidaCompleta.getDetalleLaboral());
		hojaDeVidaToPdf.setDetalleExpDocente(hojaDeVidaCompleta.getDetalleExpDocente());
		hojaDeVidaToPdf.setLogroRecurso(hojaDeVidaCompleta.getLstLogrosRecursos());
		hojaDeVidaToPdf.setPublicacion(hojaDeVidaCompleta.getLstpublicaciones());
		hojaDeVidaToPdf.setEvaluacionDesempeno(hojaDeVidaCompleta.getLstEvaluacionDesempeno());
		hojaDeVidaToPdf.setReconocimiento(hojaDeVidaCompleta.getLstReconocimientos());
		hojaDeVidaToPdf.setParticipacionProyecto(hojaDeVidaCompleta.getLstParticipacionProyecto());
		hojaDeVidaToPdf.setParticipacionInstitucion(hojaDeVidaCompleta.getLstParticipacionInstitucion());
		
		for(EducacionFormalExt ed:hojaDeVidaToPdf.getDetalleEducacion()){
        	if(ed.getCodNivelFormacion()!=null && (
        			   ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_PRIMERO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_SEGUNDO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_TERCERO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_CUARTO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_QUINTO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_SEXTO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_SEPTIMO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_OCTAVO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_NOVENO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_DECIMO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_ONCE.getValue())
        			)
        			){
        		hojaDeVidaToPdf.getDetalleEducacionPrimaria().add(ed);
        	}else{
        		hojaDeVidaToPdf.getDetalleEducacionProfesional().add(ed);
        	}
		}
		
		List<ExperienciaProfesionalExt> lstExperiencias =  ComunicacionServiciosHV.getcargoentidadpersona(codPersona.longValue(), true);
    	long aniopublico = 0; // variable para el valor año
    	long mespublico = 0;// variable para el valor mes
    	long anioprivado = 0; // variable para el valor año
    	long mesprivado = 0;// variable para el valor mes
    	long aniototal = 0; // variable para el valor año
    	long mestotal = 0;// variable para el valor mes
    	Long[] tiempos = DatosPersonalesBean.calculartiempoExperienciaprofesional(lstExperiencias);
    	aniopublico = tiempos[0];	
    	mespublico  = tiempos[1];	
    	anioprivado = tiempos[3];	
    	mesprivado  = tiempos[4];	
    	aniototal  = tiempos[6];	
    	mestotal   = tiempos[7];	
    	hojaDeVidaToPdf.setTiempoTrabajoPublicoano(aniopublico);
    	hojaDeVidaToPdf.setTiempoTrabajoPublicomes(mespublico);
    	hojaDeVidaToPdf.setTiempoTrabajoPrivadoano(anioprivado);
    	hojaDeVidaToPdf.setTiempoTrabajoPrivadomes(mesprivado);            
    	hojaDeVidaToPdf.setTiempoTrabajoAno(aniototal);
    	hojaDeVidaToPdf.setTiempoTrabajoMes(mestotal);
    	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaTexto = formato.format(new Date());
        hojaDeVidaToPdf.setFechaActual("" + fechaTexto);
        docsAdi = hojaDeVidaCompleta.getLstDocumentosAdicionales();
        cargarDireccionDatoContacto();
        validarAnexosExistentes();
        listaHojasVidaToPrint.add(hojaDeVidaToPdf);
       String urlPDF =  generarPDF();
       
       return urlPDF;
	}
	public void cargarDireccionDatoContacto() {
		if (this.hojaDeVidaToPdf.getDetallePersona().getDireccionResidencia() != null && !this.hojaDeVidaToPdf.getDetallePersona().getDireccionResidencia().isEmpty()) {
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			try {
				EditarDireccionDTO direccion = gson.fromJson(this.hojaDeVidaToPdf.getDetallePersona().getDireccionResidencia(), EditarDireccionDTO.class);
				if (direccion != null) {
					hojaDeVidaToPdf.getDetallePersona().setDireccionResidencia(direccion.getDireccionGenerada());
				}
			} catch (JsonSyntaxException e) {

			}
		}
	}
	
	/**
	 * Metodo que se encarga de generar el pdf y guardarlo en hadoop*/
	public String generarPDF() {
		String strRutaUrl = "";
	    try {
        	if(!listaHojasVidaToPrint.isEmpty()) {
        		String jasperPath = "/resources/jasper/hojaDeVidaFormatoUnico.jasper";
                String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
                File filePDF = new File(relativePath);
                JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaHojasVidaToPrint, false);
                JasperPrint jPrint = JasperFillManager.fillReport(filePDF.getPath(), null, source);
            	HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType("application/pdf");
                Date fechaActual = new Date();
                String filename = fechaActual.getTime() + ".pdf";
                String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_FILE_TEMP) + filename;
                String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
                OutputStream stream = new FileOutputStream(new File(filePath));
                JasperExportManager.exportReportToPdfStream(jPrint, stream);
                stream.flush();
                stream.close();
                //Proceso de Hadoop para subir el archivo
                ErrorMensajes resp = null;
                String responseP = "";
                try {
                	if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("0")){
						responseP = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
								                         ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_FILE_FOTO_HOJA_VIDA,
								                         WebUtils.TP_FILE_FOTO_HOJA_VIDA, hojaDeVidaToPdf.getNumeroIdentificacion());
						Gson gson = new Gson();
						resp = gson.fromJson(responseP, ErrorMensajes.class);
					}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("1")){/*Operacion cliente windows*/
						resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_FILE_FOTO_HOJA_VIDA, WebUtils.TP_FILE_FOTO_HOJA_VIDA, filePath, persona.getNumeroIdentificacion());			
					}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente windows*/
						resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_FILE_FOTO_HOJA_VIDA, WebUtils.TP_FILE_FOTO_HOJA_VIDA, filePath, persona.getNumeroIdentificacion());	
					}						
					if(!resp.isError()) {
						strRutaUrl = resp.getUrlArchivo();	
						return strRutaUrl;
					}
				} catch (Exception e) {
					strRutaUrl = "";
				}
        	}else {
        		mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
    					MessagesBundleConstants.MSG_REPORTE_HOJA_VIDA_SIN_APROBAR_SIGEPII);
        	}
        } catch (JRException e) {
            logger.error("void generarPDF() JRException", e);
        } catch (IOException e) {
            logger.error("void generarPDF() IOException", e);
        }
	    return strRutaUrl;
	}
	
	/**
	 * Metodo que se  encarga de validar los anexos existentes para la generación de la hoja de Vida de PDF*/
	public void validarAnexosExistentes() {
        if (hojaDeVidaToPdf.getDetallePersona().getDocumentoIdentificacionUrl() != null && !hojaDeVidaToPdf.getDetallePersona().getDocumentoIdentificacionUrl().equals("")) 
            this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + hojaDeVidaToPdf.getDetallePersona().getDocumentoIdentificacionUrl().replace("/getShowFile/", "/"));      
        if (hojaDeVidaToPdf.getDetallePersona().getLibretaMilitarUrl() != null && !hojaDeVidaToPdf.getDetallePersona().getLibretaMilitarUrl().equals(""))
                this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + hojaDeVidaToPdf.getDetallePersona().getLibretaMilitarUrl().replace("/getShowFile/", "/"));
        for (EducacionFormalExt educacionFormal : hojaDeVidaToPdf.getDetalleEducacion()) {
            if (educacionFormal.getUrlAnexo() != null && !educacionFormal.getUrlAnexo().equals("")) 
            	this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + educacionFormal.getUrlAnexo().replace("/getShowFile/", "/"));
        }
        for (OtroConocimientoExt otroConocimiento : hojaDeVidaToPdf.getOtroConocimientoExt()) {
            if (otroConocimiento.getUrlDocumentoSoporte() != null && !otroConocimiento.getUrlDocumentoSoporte().equals("")) 
            	this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + otroConocimiento.getUrlDocumentoSoporte().replace("/getShowFile/", "/"));
        }
        for (IdiomaPersonaExt idiomaPersona : hojaDeVidaToPdf.getDetalleIdioma()) {
            if(idiomaPersona.getUrlCertificacion() != null && !idiomaPersona.getUrlCertificacion().equals("")) 
            	this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + idiomaPersona.getUrlCertificacion().replace("/getShowFile/", "/"));
        }
        for (ExperienciaProfesionalExt experienciaProfesional : hojaDeVidaToPdf.getDetalleLaboral()) {
            if (experienciaProfesional.getUrlDocumentoSoporte() != null && !experienciaProfesional.getUrlDocumentoSoporte().equals("")) 
            	this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + experienciaProfesional.getUrlDocumentoSoporte().replace("/getShowFile/", "/"));
        }
        for (ExperienciaDocenteExt experienciaDocente : hojaDeVidaToPdf.getDetalleExpDocente()) {
            if (experienciaDocente.getUrlDocumentoSoporte() != null && !experienciaDocente.getUrlDocumentoSoporte().equals(""))
            	this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + experienciaDocente.getUrlDocumentoSoporte().replace("/getShowFile/", "/"));
        }
        for (DocumentosAdicionalesHvExt doc : this.docsAdi) {
            if (doc.getUrlDocumentoAdicional() != null && !doc.getUrlDocumentoAdicional().isEmpty())
            	this.setUrlsDocumentos( WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + doc.getUrlDocumentoAdicional().replace("/getShowFile/", "/"));
        }
    }

	public List<SelectItem> obtenerUtlUan() {
		return WebUtils.getUtlUan(new BigDecimal(this.getEntidadUsuario().getId()));
	}
	
	@Override
	public void retrieve(){}

	@Override
	public String update() { return null;}

	@Override
	public void delete() {}

	/**
	 * @return the lstDocumentosAdicionales
	 */
	public List<DocumentosAdicionalesHvExt> getLstDocumentosAdicionales() {
		return lstDocumentosAdicionales;
	}

	/**
	 * @param lstDocumentosAdicionales the lstDocumentosAdicionales to set
	 */
	public void setLstDocumentosAdicionales(List<DocumentosAdicionalesHvExt> lstDocumentosAdicionales) {
		this.lstDocumentosAdicionales = lstDocumentosAdicionales;
	}

	public Long getCodUTLAnt() {
		return codUTLAnt;
	}

	public void setCodUTLAnt(Long codUTLAnt) {
		this.codUTLAnt = codUTLAnt;
	}
	
	
	public static ErrorMensajes uploadHaddopApi(String carpeta, String tipologia, String nombreArchivo, String numeroIdentificacion) {
        final ErrorMensajes errorM = new ErrorMensajes();
        String urlArchivo = "/getShowFile/";
        try {
        	carpeta=carpeta.replace("/", "")+"/";
            String nombreArchivoFinal = construirNombre(nombreArchivo, tipologia, numeroIdentificacion);
        	
            InputStream inputStream = new FileInputStream(new File(nombreArchivo));
            if (HADOOP_OPCION_CARGA.equals("1")){/*Operacion cliente windows*/
            	if(!uploadWindows(nombreArchivo,inputStream, carpeta, tipologia, nombreArchivoFinal, numeroIdentificacion)){
                    errorM.setError(Boolean.valueOf(true));
                    errorM.setMensaje("Error al Guardar Archivo");
                    errorM.setCodigoEstado(500);
                    return errorM;
            	}
            }else if (HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente linux*/
            	if(!uploadLinux(nombreArchivo, carpeta, tipologia, nombreArchivoFinal, numeroIdentificacion)){
                    errorM.setError(Boolean.valueOf(true));
                    errorM.setMensaje("Error al Guardar Archivo");
                    errorM.setCodigoEstado(500);
                    return errorM;
            	}
            }
            
        	errorM.setError(Boolean.valueOf(false));
            errorM.setMensaje("Archivo Almacenado Correctamente");
            errorM.setNombreArchivo(nombreArchivoFinal);
            urlArchivo = String.valueOf(urlArchivo) + carpeta + nombreArchivoFinal;
            errorM.setUrlArchivo(urlArchivo);
            errorM.setCodigoEstado(200);
            return errorM;
        } catch (Exception e) {
            errorM.setError(Boolean.valueOf(true));
            errorM.setMensaje("Error al Guardar Archivo");
            errorM.setCodigoEstado(500);
			e.printStackTrace();
            return errorM;
        }
    }
	
	public static boolean uploadLinux(String path, String carpeta, String tipologia, String nombreArchivo, String numeroIdentificacion) {
		String finalUrl="";
		try {
	    	String configPath        = System.getProperty("CONFIG_PATH");
	    	System.out.println("configPath: "+configPath);
	    	finalUrl = "bash "+configPath+"co/gov/dafp/sigep2/hadoopso/upload_file.sh "+path+" "+nombreArchivo+" "+carpeta.replace("/","")+" "+HADOOP_USER_REPO.replace("/", "");
			Process process = Runtime.getRuntime().exec(finalUrl);
			System.out.println(process.exitValue());
			return true;
		} catch (Exception e) {
			logger.log().error("finalUrl()", finalUrl);
			logger.log().error("Exception uploadLinux()", e.getMessage());
			return false;
		}
	}
	public static boolean uploadWindows(String path, InputStream in, String carpeta, String tipologia, String nombreArchivo, String numeroIdentificacion) {
		String finalUrl="";
		try {
	    	String configPath        = System.getProperty("CONFIG_PATH");
			String curlUrl = configPath+"co/gov/dafp/sigep2/hadoopso/curl/curl-7.69.1-win64-mingw/bin/curl";
			String options = " -i -X PUT -T ";
			String repositorio =HADOOP_USER_REPO; 
			String hadoopUrl = "\"" +HADOOP_URL_BY_SO+repositorio+carpeta+nombreArchivo+"?op=CREATE&user.name=hdfs&namenoderpcaddress=cluster-hdfs&createflag=&createparent=true&overwrite=false\"";
			finalUrl = curlUrl + options + path + " " + hadoopUrl;
			Process process = Runtime.getRuntime().exec(finalUrl);
			System.out.println("**************************************************************");
			System.out.println(process.exitValue());
			System.out.println("**************************************************************");
			return true;
		} catch (Exception e) {
			logger.log().error("finalUrl()", finalUrl);
			logger.log().error("Exception uploadWindows()", e.getMessage());
			return false;
		}
	}	
	
	public static String construirNombre(String path, String tipologia, String numeroIdentificacion) {
		String nombreArchivo = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
		LocalDateTime now = LocalDateTime.now();
		String[] pathArray = path.split("\\.");
		String extension = pathArray[pathArray.length-1];
		extension = extension.toLowerCase();
		String autonumerico = dtf.format(now);
		nombreArchivo=tipologia+autonumerico+numeroIdentificacion+"."+extension;
		return nombreArchivo;
	}
	
}