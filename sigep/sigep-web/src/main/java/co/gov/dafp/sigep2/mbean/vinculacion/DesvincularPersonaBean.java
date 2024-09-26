package co.gov.dafp.sigep2.mbean.vinculacion;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaDenominacionExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.SituacionAdminVinculacionExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import java.util.Map;
/**
* @author Milciades Vargas, Maria Alejandra Colorado
* @version 1.0
* @Class PersonaVinculacionBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Vinculacion/Desvinculacion de usuarios
* Fecha: 26/06/2018
*/
@Named
@ViewScoped
@ManagedBean
public class DesvincularPersonaBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 2434871298936757646L;
	private PersonaExt persona;// variable para almacenar la informacion de la persona consultada
	private VinculacionExt desvinculacion;// variable para llenar los datos de la desvinculacion
	private HojaVidaExt hojaVidaDesvincularUTL; //Almacena la hoja de vida de la persona a desvincular
	
	private List<VinculacionExt> vinculacion; //Variable para almacenar las vinculaciones de una persona
	private  List<SelectItem> getUtls;
	
	private boolean blnDesvinculacionPorCargo;
	private boolean blnDesvinculacionPorUTL;
	private Date fechaPosesion ; //Variable que contiene la fecha de posesion
	private Long lngValorEntidad;//valor de la entidad seleccionada o de session por default
	
	ExternalContext externalContext;//Variable para acceder al context y obtener y setear vbles

	@PostConstruct
	public void init() {
		reiniciarCampos();
		FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String recursoId = paramMap.get("recursoId");
		this.validaPermisosAcciones(recursoId);
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String idPersona = request.getParameter("id");
        lngValorEntidad = getEntidadUsuario().getId();
        if (idPersona != null) {
        	persona = ComunicacionServiciosHV.getPersonaporIdExt(Long.valueOf(idPersona));
        	this.verificarTipoVinculacionADesvincular();
        }
	}
	
	public void reiniciarCampos() {
		desvinculacion = new VinculacionExt();
		vinculacion = new ArrayList<>();
		persona = new PersonaExt();
		blnDesvinculacionPorCargo = false;
		blnDesvinculacionPorUTL = false;
	}
	/**
	 * Metodo que verifica si la vinculacion hecha es por UTL o por cargo Normal
	 */
	public void verificarTipoVinculacionADesvincular() {
		obtenerVinculacionActualCargo();
		obtenerVinculacionUtl();
	}
	
	/**
	 * Metodo que obtiene las vinculaciones a UTLs de una persona
	 */
	public void obtenerVinculacionUtl() {
		HojaVidaExt objFilter = new HojaVidaExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short) 1);
		objFilter.setCodEntidad(getEntidadUsuario().getId());
		List<HojaVidaExt> list = ComunicacionServiciosHV.getHVPersonaAsociadaUTL(objFilter);
		if(!list.isEmpty()) {
			blnDesvinculacionPorUTL = true;
			blnDesvinculacionPorCargo = false;
		}
	}
	
	/**
	 * Retorna la lista de las UTLs a las cuales se encuentra asociada una persona
	 * para proceder con el proceso de desvinculacion
	 * @return
	 */
	public List<SelectItem> getUtlUanDesvincular() {
		List<SelectItem> listFinal = new ArrayList<>();
		HojaVidaExt objFilter = new HojaVidaExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short) 1);
		List<HojaVidaExt> list = ComunicacionServiciosHV.getHVPersonaAsociadaUTL(objFilter);
		try {
			if (!list.isEmpty()) {
				for (HojaVidaExt aux : list) {
					listFinal.add(new SelectItem(aux.getCodUtlUan(), aux.getNombreUtlUan()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return listFinal;
	}
	
	
	/**
	 * Metodo que consulta la vinculacion actual a desvincular de la persona
	 */
	public void obtenerVinculacionActualCargo() {
		VinculacionExt objFilter = new VinculacionExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setCodEntidad(lngValorEntidad);
		objFilter.setFlgActivo((short)1);
		try {
			vinculacion = ComunicacionServiciosVin.getMostrarVinculaciones(objFilter);
			if(!vinculacion.isEmpty()) {
				blnDesvinculacionPorUTL = false;
				blnDesvinculacionPorCargo = true;
				desvinculacion = vinculacion.get(0);
				fechaPosesion = vinculacion.get(0).getFechaPosesion();
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error: " + e.getMessage() + e.getCause() + e.getStackTrace());
		}
	}
	
	
	/**
	 * Metodo que desvincula a una persona del cargo
	 */
	public void desvincularServidorPublico() {
		if(blnDesvinculacionPorCargo) {
			desvincularServidorPorCargo();
		}else {
			desvincularServidorPorUTL();
		}
	}
	
	/**
	 * Pone el campo MARCACION_PEP_VIN de la tabla PERSONA = 0
	 * Esto se realiza con el fin de que desde la opcion de hoja de vida se
	 * puedan realizar nuevamente cambios.
	 */
	public void guardarDatosHV() {
		PersonaExt objetoPersona = new PersonaExt();
		objetoPersona.setCodPersona(persona.getCodPersona());
		objetoPersona.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		objetoPersona.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		objetoPersona.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
		objetoPersona.setAudFechaActualizacion(DateUtils.getFechaSistema());
		objetoPersona.setMarcacionPepVinc((short)0);
		ComunicacionServiciosHV.updatePersona(objetoPersona);
}
	
	
	
	/**
	 * Metodo que desvincula a una persona de un cargo especificoo
	 */
	public void desvincularServidorPorCargo() {
		desvinculacion.setAudFechaActualizacion(DateUtils.getFechaSistema());
		desvinculacion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		desvinculacion.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
		desvinculacion.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
		desvinculacion.setFlgActivo((short)0);
		
		boolean valid = ComunicacionServiciosVin.setVinculacion(desvinculacion);
		if(valid) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INFO_MENSAJE_DESVINCULACION_EXITOSA);
			eliminarSituacionAdminAsociadas();
			guardarDatosHV();
			desvinculacion = new VinculacionExt();
			blnDesvinculacionPorCargo = false;
			redireccionarBusquedaServidor();
			
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
			desvinculacion = new VinculacionExt();
		}
	}
	
	/**
	 * Metodo que verifica si la desvinculacion contaba con situaciones administrativas dependientes 
	 */
	public void eliminarSituacionAdminAsociadas() {
		SituacionAdminVinculacionExt objSA = new SituacionAdminVinculacionExt();
		objSA.setFlgActivo((short)0);
		objSA.setCodVinculacion(desvinculacion.getCodVinculacion().longValue());
		boolean valid = ComunicacionServiciosVin.setSituacionPorVinculacion(objSA);
	}
	
	/**
	 * Metodo que desvincula a una persona cuando esta esta vinculada a una UTL
	 */
	public void desvincularServidorPorUTL() {
		/**Busca la persona a desvincular **/
		HojaVidaExt objFilter = new HojaVidaExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short) 1);
		objFilter.setCodEntidad(getEntidadUsuario().getId());
		objFilter.setCodUtlUan(desvinculacion.getCodUtlUan());
		List<HojaVidaExt> list = ComunicacionServiciosHV.getHVPersonaAsociadaUTL(objFilter);
		/** Procede con la desvinculacion de la UTL**/
		if(!list.isEmpty()) {
			hojaVidaDesvincularUTL = list.get(0);
			hojaVidaDesvincularUTL.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
			hojaVidaDesvincularUTL.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
			hojaVidaDesvincularUTL.setAudCodRol((int) this.getRolAuditoria().getId());
			hojaVidaDesvincularUTL.setAudFechaActualizacion(DateUtils.getFechaSistema());
			hojaVidaDesvincularUTL.setCodUtlUan(null);
			hojaVidaDesvincularUTL.setSalario(null);
			boolean valid = ComunicacionServiciosHV.setHojaVida(hojaVidaDesvincularUTL);
			if(valid) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INFO_MENSAJE_DESVINCULACION_EXITOSA);
				blnDesvinculacionPorUTL = false;
				guardarDatosHV();
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
			}
		}
		
		redireccionarBusquedaServidor();
	}
	
	/**
	 * Metodo que redirecciona al menu principal de busquedas
	 */
	public void redireccionarBusquedaServidor() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../vinculacion/personaVinculacion.xhtml?recursoId=VincularDesvincularSubMenu&&isMenu=0");
			/*Al redireccionar a la pag ppal de vincuaciones se setea la persona en gestion par cargar por defecto la lista incial*/
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        externalContext.getSessionMap().put("personaFilter", persona);
		} catch (IOException e) {
			logger.error("El metodo no redirecciono - redireccionarBusquedaServidor()", e);
		}
	}
	
	/**
	 * Metodo que abre Dialogo de confirmacion de desvinculacion
	 */
	public void abrirDialogoConfirmacion() {
		if(desvinculacion.getFechaFinalizacion()!=null && desvinculacion.getCodCausalDesvinculacion()!=null) {
			RequestContext.getCurrentInstance().execute("$('#desvincularServidor').modal('show');");
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
	 * @return the desvinculacion
	 */
	public VinculacionExt getDesvinculacion() {
		return desvinculacion;
	}

	/**
	 * @param desvinculacion the desvinculacion to set
	 */
	public void setDesvinculacion(VinculacionExt desvinculacion) {
		this.desvinculacion = desvinculacion;
	}

	/**
	 * @return the lngValorEntidad
	 */
	public Long getLngValorEntidad() {
		return lngValorEntidad;
	}

	/**
	 * @param lngValorEntidad the lngValorEntidad to set
	 */
	public void setLngValorEntidad(Long lngValorEntidad) {
		this.lngValorEntidad = lngValorEntidad;
	}
	

	/**
	 * @return the vinculacion
	 */
	public List<VinculacionExt> getVinculacion() {
		return vinculacion;
	}

	/**
	 * @param vinculacion the vinculacion to set
	 */
	public void setVinculacion(List<VinculacionExt> vinculacion) {
		this.vinculacion = vinculacion;
	}

	/**
	 * @return the blnDesvinculacionPorCargo
	 */
	public boolean isBlnDesvinculacionPorCargo() {
		return blnDesvinculacionPorCargo;
	}

	/**
	 * @param blnDesvinculacionPorCargo the blnDesvinculacionPorCargo to set
	 */
	public void setBlnDesvinculacionPorCargo(boolean blnDesvinculacionPorCargo) {
		this.blnDesvinculacionPorCargo = blnDesvinculacionPorCargo;
	}

	/**
	 * @return the blnDesvinculacionPorUTL
	 */
	public boolean isBlnDesvinculacionPorUTL() {
		return blnDesvinculacionPorUTL;
	}

	/**
	 * @param blnDesvinculacionPorUTL the blnDesvinculacionPorUTL to set
	 */
	public void setBlnDesvinculacionPorUTL(boolean blnDesvinculacionPorUTL) {
		this.blnDesvinculacionPorUTL = blnDesvinculacionPorUTL;
	}

	/**
	 * @return the getUtls
	 */
	public List<SelectItem> getGetUtls() {
		return getUtls;
	}

	/**
	 * @param getUtls the getUtls to set
	 */
	public void setGetUtls(List<SelectItem> getUtls) {
		this.getUtls = getUtls;
	}

	/**
	 * @return the fechaPosesion
	 */
	public Date getFechaPosesion() {
		return fechaPosesion;
	}

	/**
	 * @param fechaPosesion the fechaPosesion to set
	 */
	public void setFechaPosesion(Date fechaPosesion) {
		this.fechaPosesion = fechaPosesion;
	}

	/**
	 * @return the hojaVidaDesvincularUTL
	 */
	public HojaVidaExt getHojaVidaDesvincularUTL() {
		return hojaVidaDesvincularUTL;
	}

	/**
	 * @param hojaVidaDesvincularUTL the hojaVidaDesvincularUTL to set
	 */
	public void setHojaVidaDesvincularUTL(HojaVidaExt hojaVidaDesvincularUTL) {
		this.hojaVidaDesvincularUTL = hojaVidaDesvincularUTL;
	}
}