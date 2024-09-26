package co.gov.dafp.sigep2.mbean.vinculacion;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
/**
* @author Milciades Vargas, Maria Alejandra Colorado
* @version 1.0
* @Class ConsultarVinculacionBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Vinculacion/Desvinculacion de usuarios
* Fecha: 26/06/2018
*/
@Named
@ViewScoped
@ManagedBean
public class ConsultarVinculacionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -4754410642897506177L;
	

	private PersonaExt persona;// variable para almacenar la informacion de la persona consultada
	private List<VinculacionExt> vinculacionActiva; //Variable que trae las vinculaciones activas de una persona
	private List<VinculacionExt> vinculacionInactiva; //Variable que trae las vinculaciones inactivas de una persona
	private List<VinculacionExt> vinculacionEncargoComision; //Variable que trae las vinculaciones con cargo en Encargo de una persona
	private List<VinculacionExt> vinculacionPeriodoDePrueba; //Variable que trae las vinculaciones con cargo en Periodo De Prueba de una persona
	private VinculacionExt ultimaVinculacionActiva; //Variable que trae la ultima vinculacion activa de una persona
	private VinculacionExt ultimaDesvinculacion; //Variable que trae la ultima Desvinculacion de una persona
	private VinculacionExt ultimaVinculacionEncargo; //Variable que trae la ultima Vinculacion en Encargo de una persona
	private VinculacionExt ultimaVinculacionPeriodoPrueba; //Variable que trae la ultima Vinculacion en Periodo de Prueba de una persona
	private List<HojaVidaExt> vinculacionUTL; //Variable que trae las vinculaciones que presenta actualmente la persona en las UTLs
	
	private Boolean blnVinculacionActiva 		= false; //Indica si la persona tiene una vinculacion activa a un cargo especifico
	private Boolean blnCargoComision 			= false; //Indica si la persona tiene una vinculacion en Cargo/comision
	private Boolean blnPeriodoPruebaOtraEntidad = false; //Indica si la persona tiene una vinculacion en Periodo de Prueba
	private Boolean blnDesvinculado 			= false; //Indica si la persona tiene una desvinculacion
	private Boolean blnVinculacionUTL 			= false; //Indica si la persona tiene una vinculacion activa a una UTL
	
	
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String recursoId = paramMap.get("recursoId");
		this.validaPermisosAcciones(recursoId);
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String idPersona = request.getParameter("id");
        
        if (idPersona != null) {
        	persona = ComunicacionServiciosHV.getPersonaporIdExt(Long.valueOf(idPersona));
        	this.consultarVinculacion();
        }
	}
	
	@Override
	public String persist() { return null; }

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
		}
	}
	
	/**
	 * Metodo que consulta informacion de Vinculacion/ desvinculacion de una persona
	 */
	public void consultarVinculacion() {
		consultarVinculacionActiva();
		consultarCargoComision();
		consultarCargoPeriodoDePrueba();
		consultarDesvinculacion();
		consultarVinculacionUTL();
	}
	
	/**
	 * Metodo que trae los datos de la ultima vinculacion activa que tiene la persona
	 */
	public void consultarVinculacionActiva() {
		VinculacionExt objFilter = new VinculacionExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short)1);
		objFilter.setCodEntidad(getEntidadUsuario().getId());
		vinculacionActiva = ComunicacionServiciosVin.getvinculacionactual(objFilter);
		if(!vinculacionActiva.isEmpty()) {
			ultimaVinculacionActiva = vinculacionActiva.get(0);
			blnVinculacionActiva = true;
		}
	}
	

	/**
	 * Metodo que trae los datos del cargo en Comision de una persona
	 */
	public void consultarCargoComision() {
		VinculacionExt objFilter = new VinculacionExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short)1);
		vinculacionEncargoComision = ComunicacionServiciosVin.getVinculacionSituacionAdmin(objFilter);
		if(!vinculacionEncargoComision.isEmpty()) {
			ultimaVinculacionEncargo = vinculacionEncargoComision.get(0);
			blnCargoComision = true;
		}
	}
	
	/**
	 * Metodo que trae los datos del cargo en Perdiodo de Prueba de una persona
	 */
	public void consultarCargoPeriodoDePrueba() {
		VinculacionExt objFilter = new VinculacionExt();
		VinculacionExt objSA = new VinculacionExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short)1);
		objFilter.setCodEntidad(getEntidadUsuario().getId());
		vinculacionPeriodoDePrueba = ComunicacionServiciosVin.getVinculacionSituacionAdminPeriodoPrueba(objFilter);
		if(!vinculacionPeriodoDePrueba.isEmpty()) {
			objSA = vinculacionPeriodoDePrueba.get(0);
			objFilter.setCodPersona(persona.getCodPersona());
			objFilter.setFlgActivo((short)1);
			objFilter.setCodTipoNombramiento(1257);
			List<VinculacionExt> vinculacionTipoNombramiento = ComunicacionServiciosVin.getVinculacionPeriodoDePrueba(objFilter);
			if(!vinculacionTipoNombramiento.isEmpty()) {
				ultimaVinculacionPeriodoPrueba = vinculacionTipoNombramiento.get(0);
				ultimaVinculacionPeriodoPrueba.setFechaInicioSA(objSA.getFechaInicioSA());
				ultimaVinculacionPeriodoPrueba.setFechaFinSA(objSA.getFechaFinSA());
				blnPeriodoPruebaOtraEntidad = true;				
			}
		}
	}
	
	
	/**
	 * Metodo que trae los datos de desvinculacion de una persona
	 */
	public void consultarDesvinculacion() {
		VinculacionExt objFilter = new VinculacionExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short)0);
		objFilter.setLimitInit(0);
		objFilter.setLimitEnd(1);
		objFilter.setParCausalDesv(TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue());
		vinculacionInactiva = ComunicacionServiciosVin.getMostrarVinculaciones(objFilter);
		if(!vinculacionInactiva.isEmpty()) {
			ultimaDesvinculacion = vinculacionInactiva.get(0);
			blnDesvinculado = true;
		}else {
			blnDesvinculado = false;
		}
	}
	
	/**
	 * Metodo que consulta la vinculacion por UTL
	 */
	public void consultarVinculacionUTL() {
		HojaVidaExt objFilter = new HojaVidaExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short) 1);
		vinculacionUTL = ComunicacionServiciosHV.getHVPersonaAsociadaUTL(objFilter);
		if(!vinculacionUTL.isEmpty()) {
			blnVinculacionUTL = true;
			
		}
	}
	
	/**
	 * Metodo para abrir la pagina de consultar vinculacion
	 */
	public void redireccionarBusquedaServidor(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../vinculacion/personaVinculacion.xhtml");
			/*Al redireccionar a la pag ppal de vincuaciones se setea la persona en gestion par cargar por defecto la lista incial*/
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        externalContext.getSessionMap().put("personaFilter", persona);
		} catch (IOException e) {
			logger.error("El metodo no redirecciono - redireccionarConsultarVinculacion()", e);
		}
		
	}
	
	/**
	 * @return the persona
	 */
	
	public PersonaExt getPersona() {
		return persona;
	}
	
	/**
	 * @param persona the persona to set
	 */
	public void setPersona(PersonaExt persona) {
		this.persona = persona;
	}
	
	
	/**
	 * @param vinculacionActiva the vinculacionActiva to set
	 */
	public List<VinculacionExt> getVinculacionActiva() {
		return vinculacionActiva;
	}


	/**
	 * @param vinculacionActiva the vinculacionActiva to set
	 */
	public void setVinculacionActiva(List<VinculacionExt> vinculacionActiva) {
		this.vinculacionActiva = vinculacionActiva;
	}

	/**
	 * @return the blnVinculacionActiva
	 */
	public Boolean getBlnVinculacionActiva() {
		return blnVinculacionActiva;
	}

	/**
	 * @param blnVinculacionActiva the blnVinculacionActiva to set
	 */
	public void setBlnVinculacionActiva(Boolean blnVinculacionActiva) {
		this.blnVinculacionActiva = blnVinculacionActiva;
	}
	
	/**
	 * @return the blnCargoComision
	 */
	public Boolean getBlnCargoComision() {
		return blnCargoComision;
	}

	/**
	 * @param blnCargoComision the blnCargoComision to set
	 */
	public void setBlnCargoComision(Boolean blnCargoComision) {
		this.blnCargoComision = blnCargoComision;
	}
	
	/**
	 * @return the blnPeriodoPruebaOtraEntidad
	 */
	public Boolean getBlnPeriodoPruebaOtraEntidad() {
		return blnPeriodoPruebaOtraEntidad;
	}
	
	/**
	 * @param blnPeriodoPruebaOtraEntidad the blnPeriodoPruebaOtraEntidad to set
	 */
	public void setBlnPeriodoPruebaOtraEntidad(Boolean blnPeriodoPruebaOtraEntidad) {
		this.blnPeriodoPruebaOtraEntidad = blnPeriodoPruebaOtraEntidad;
	}

	/**
	 * @return the blnDesvinculado
	 */
	public Boolean getBlnDesvinculado() {
		return blnDesvinculado;
	}

	/**
	 * @param blnDesvinculado the blnDesvinculado to set
	 */
	public void setBlnDesvinculado(Boolean blnDesvinculado) {
		this.blnDesvinculado = blnDesvinculado;
	}
	
	/**
	 * @return the ultimaVinculacionActiva
	 */
	
	public VinculacionExt getUltimaVinculacionActiva() {
		return ultimaVinculacionActiva;
	}

	/**
	 * @param ultimaVinculacionActiva the ultimaVinculacionActiva to set
	 */
	public void setUltimaVinculacionActiva(VinculacionExt ultimaVinculacionActiva) {
		this.ultimaVinculacionActiva = ultimaVinculacionActiva;
	}

	/**
	 * @return the vinculacionInactiva
	 */
	public List<VinculacionExt> getVinculacionInactiva() {
		return vinculacionInactiva;
	}

	/**
	 * @param vinculacionInactiva the vinculacionInactiva to set
	 */
	public void setVinculacionInactiva(List<VinculacionExt> vinculacionInactiva) {
		this.vinculacionInactiva = vinculacionInactiva;
	}

	/**
	 * @return the ultimaDesvinculacion
	 */
	public VinculacionExt getUltimaDesvinculacion() {
		return ultimaDesvinculacion;
	}

	/**
	 * @param ultimaDesvinculacion the ultimaDesvinculacion to set
	 */
	public void setUltimaDesvinculacion(VinculacionExt ultimaDesvinculacion) {
		this.ultimaDesvinculacion = ultimaDesvinculacion;
	}

	/**
	 * @return the vinculacionEncargoComision
	 */
	public List<VinculacionExt> getVinculacionEncargoComision() {
		return vinculacionEncargoComision;
	}

	/**
	 * @param vinculacionEncargoComision the vinculacionEncargoComision to set
	 */
	public void setVinculacionEncargoComision(List<VinculacionExt> vinculacionEncargoComision) {
		this.vinculacionEncargoComision = vinculacionEncargoComision;
	}

	/**
	 * @return the ultimaVinculacionEncargo
	 */
	public VinculacionExt getUltimaVinculacionEncargo() {
		return ultimaVinculacionEncargo;
	}

	/**
	 * @param ultimaVinculacionEncargo the ultimaVinculacionEncargo to set
	 */
	public void setUltimaVinculacionEncargo(VinculacionExt ultimaVinculacionEncargo) {
		this.ultimaVinculacionEncargo = ultimaVinculacionEncargo;
	}
		
	
	public VinculacionExt getUltimaVinculacionPeriodoPrueba() {
		return ultimaVinculacionPeriodoPrueba;
	}

	public void setUltimaVinculacionPeriodoPrueba(VinculacionExt ultimaVinculacionPeriodoPrueba) {
		this.ultimaVinculacionPeriodoPrueba = ultimaVinculacionPeriodoPrueba;
	}

	/**
	 * @return the blnVinculacionUTL
	 */
	public Boolean getBlnVinculacionUTL() {
		return blnVinculacionUTL;
	}

	/**
	 * @param blnVinculacionUTL the blnVinculacionUTL to set
	 */
	public void setBlnVinculacionUTL(Boolean blnVinculacionUTL) {
		this.blnVinculacionUTL = blnVinculacionUTL;
	}
	
	/**
	 * @return the vinculacionUTL
	 */
	public List<HojaVidaExt> getVinculacionUTL() {
		return vinculacionUTL;
	}

	/**
	 * @param vinculacionUTL the vinculacionUTL to set
	 */
	public void setVinculacionUTL(List<HojaVidaExt> vinculacionUTL) {
		this.vinculacionUTL = vinculacionUTL;
	}

	@Override
	public String update() {
		return null;
	}

	@Override
	public void delete() { 
		//it isn't necessary
	}

	@Override
	public void validateForm(ComponentSystemEvent event) {
		//it isn't necessary
	}

}