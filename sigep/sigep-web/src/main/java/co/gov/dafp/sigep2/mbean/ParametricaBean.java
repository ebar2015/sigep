package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.enums.TipoParametricaEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ViewScoped
public class ParametricaBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1589822750990973293L;
	private List<Parametrica> parametros;
	private List<Parametrica> parametrosDetalle;
	private List<Parametrica> parametrosFiltro;
	private Parametrica selectedParam;
	private Parametrica detalleParam;
	private String nombreParametro;
	private boolean habilitarFormulario = false;
	private boolean habilitarControles = true;
	private boolean habilitarDetalleParametrica = false;
	private boolean habilitarDetalleParametricaSistema = false;
	private boolean flgActivoParametro;
	private boolean flgActivoParametroSistema;
	private Boolean flgValidRolPermission = false;
	
	public boolean getHabilitarControles() {
		return habilitarControles;
	}

	public void setHabilitarControles(boolean habilitarControles) {
		this.habilitarControles = habilitarControles;
	}

	private boolean nuevo = false;

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public String getNombreParametro() {
		return nombreParametro;
	}

	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}
	
	public List<Parametrica> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametrica> parametros) {
		this.parametros = parametros;
	}

	public Parametrica getSelectedParam() {
		return selectedParam;
	}

	public void setSelectedParam(Parametrica selectedParam) {
		this.selectedParam = selectedParam;
	}

	public List<Parametrica> getParametrosFiltro() {
		return parametrosFiltro;
	}

	public void setParametrosFiltro(List<Parametrica> parametrosFiltro) {
		this.parametrosFiltro = parametrosFiltro;
	}

	public boolean isHabilitarFormulario() {
		return habilitarFormulario;
	}

	public void setHabilitarFormulario(boolean habilitarFormulario) {
		this.habilitarFormulario = habilitarFormulario;
	}

	public Parametrica getDetalleParam() {
		return detalleParam;
	}

	public void setDetalleParam(Parametrica detalleParam) {
		this.detalleParam = detalleParam;
	}

	public List<Parametrica> getParametrosDetalle() {
		return parametrosDetalle;
	}

	public void setParametrosDetalle(List<Parametrica> parametrosDetalle) {
		this.parametrosDetalle = parametrosDetalle;
	}
	
	public Boolean getFlgValidRolPermission() {
		return flgValidRolPermission;
	}

	public void setFlgValidRolPermission(Boolean flgValidRolPermission) {
		this.flgValidRolPermission = flgValidRolPermission;
	}
	
	
	/**
	 * @return the flgActivoParametro
	 */
	public boolean isFlgActivoParametro() {
		return flgActivoParametro;
	}

	/**
	 * @param flgActivoParametro the flgActivoParametro to set
	 */
	public void setFlgActivoParametro(boolean flgActivoParametro) {
		this.flgActivoParametro = flgActivoParametro;
	}

	/**
	 * @return the flgActivoParametroSistema
	 */
	public boolean isFlgActivoParametroSistema() {
		return flgActivoParametroSistema;
	}

	/**
	 * @param flgActivoParametroSistema the flgActivoParametroSistema to set
	 */
	public void setFlgActivoParametroSistema(boolean flgActivoParametroSistema) {
		this.flgActivoParametroSistema = flgActivoParametroSistema;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String recursoId = paramMap.get("recursoId");
		this.validaPermisosAcciones(recursoId);
		initialization();
		parametros = new ArrayList<Parametrica>();
		parametrosFiltro = new ArrayList<Parametrica>();
		parametrosDetalle = new ArrayList<Parametrica>();
		Parametrica par = new Parametrica();
		par.setFlgEstado((short)1);
		par.setLimitInit(0);
		par.setLimitEnd(1000);
		
		parametros = ComunicacionServiciosSis.getParametricaPadreTodo(par);
		parametrosFiltro = ComunicacionServiciosSis.getParametricaPadreTodo(par);
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}
	
	public void initialization() {
		String rolesValid[] = { RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_TECNICO };
		if (getUsuarioSesion() != null) {
			try {
				flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(rolesValid);
				if (flgValidRolPermission == false) {
					this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
				}
			} catch (SIGEP2SistemaException e) {
				logger.error("void init() usuarioTieneRolAsignado", e);
			} catch (IOException e) {
				logger.error("void init() finalizarConversacion", e);
			}

		}

	}
	
	public List<String> listarParametricas(String entidadPrivQuery){
		List<String> filtroListParametricas = new ArrayList<>();
		parametrosFiltro = new ArrayList<Parametrica>();
		Parametrica par = new Parametrica();
		par.setFlgEstado((short)1);
		par.setLimitInit(0);
		par.setLimitEnd(1000);
		par.setNombreParametro(entidadPrivQuery);
		parametrosFiltro = ComunicacionServiciosSis.getParametricaPadreTodo(par);
		
		for(Parametrica param : parametrosFiltro ) {
			filtroListParametricas.add(param.getNombreParametro());
		}
		return filtroListParametricas;
	}
	
	/**
	 * Metodo que filtra las parametricas
	 * si tiene nombre del parametro, el metodo realiza filtro para mostrar solo aquellas que coinciden
	 * sino, el metodo realiza una busqueda de todas las parametricas actuales que se tienen activas
	 */
	public void filtrarParametrica() {
		habilitarDetalleParametrica = false;
		habilitarDetalleParametricaSistema = false;
		habilitarFormulario = false;
		if(nombreParametro == null) {
			Parametrica par = new Parametrica();
			par.setFlgEstado((short)1);
			par.setLimitInit(0);
			par.setLimitEnd(10000);
			parametros = ComunicacionServiciosSis.getParametricaPadreTodo(par);
		}else {
			parametros.clear();
			parametrosFiltro = new ArrayList<Parametrica>();
			Parametrica par = new Parametrica();
			par.setFlgEstado((short)1);
			par.setLimitInit(0);
			par.setLimitEnd(1000);
			par.setNombreParametro(nombreParametro);
			parametrosFiltro = ComunicacionServiciosSis.getParametricaPadreTodo(par);
			for(Parametrica param : parametrosFiltro) {
				parametros.add(param);
				habilitarControles = true;
				habilitarFormulario = false;
				parametrosDetalle.clear();
				break;
			}
		}
		nombreParametro = "";
	}


	/**
	 * Metodo que muestra el detalle de la parametrica dependiendo de la seleccion
	 * @param paramSeleccionada
	 */
	public void mostrarTablaDetalleParametrica(Parametrica paramSeleccionada) {
		habilitarFormulario = false;
		if(TipoParametricaEnum.PARAM_SISTEMA.getTipo().equals(paramSeleccionada.getTipoParametro())){
			//Si el parametro seleccionado es S
			selectedParam = paramSeleccionada;
			if(selectedParam.getFlgEstado()==1) {
				flgActivoParametroSistema = true;
			}
			habilitarDetalleParametricaSistema = true;
			habilitarDetalleParametrica = false;
			nombreParametro = "";
		}else {
			//Si el parametro seleccionado es T
			habilitarDetalleParametrica = true;
			habilitarDetalleParametricaSistema = false;
			parametrosDetalle = ComunicacionServiciosSis.getParametricaPorIdPadre(paramSeleccionada.getCodTablaParametrica().longValue());
			selectedParam =  paramSeleccionada;
			habilitarControles = false;
			nombreParametro = "";
		}
	}
	
	/**
	 * Metodo que habilita formulario para agregar un nuevo parametro cuando la parametrica seleccionada
	 * es una de tipo T
	 * @param registroNuevo
	 */
	public void habilitarFormularioDetalleParam(boolean registroNuevo) {
		detalleParam = new Parametrica();
		detalleParam.setCodPadreParametrica(selectedParam.getCodTablaParametrica());
		nuevo = registroNuevo;
		habilitarFormulario = true;
		//campos adicionales
		flgActivoParametro = true;
		detalleParam.setFechaInicio(DateUtils.getFechaSistema());
		detalleParam.setFlgEstado((short)1);
	}

	/**
	 * Metodo que trae la informacion del parametro a actualizar y
	 * habilita el formulario para su edicion
	 * @param id : Id de la tabla
	 */
	public void actualizarDetalleParametrica(BigDecimal id) {
		detalleParam = ComunicacionServiciosSis.getParametricaporId(id);
		if(detalleParam.getFlgEstado() ==1) {
			flgActivoParametro = true;
		}
		nombreParametro = "";
		habilitarFormulario = true;
		nuevo = false;
	}
	
	/**
	 * Metodo que limpia el formulario de parametricas
	 */
	public void limpiarForm() {
		habilitarFormulario = false;
	}
	
	/**
	 * Metodo que almacena una nueva parametrica tipo P
	 * Donde P son las parametricas que pertenecen a una tabla especifica
	 * @throws IOException
	 */
	public void guardarParametrica() throws IOException {
		if(detalleParam != null) {
			if(nuevo) {
				detalleParam.setValorParametro(detalleParam.getValorParametro().equals("") ? " " : detalleParam.getValorParametro());
				detalleParam.setAudFechaActualizacion(new Date());
				detalleParam.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
				UsuarioDTO usuarioSesion = getUsuarioSesion();
				detalleParam.setAudCodRol((short)usuarioSesion.getCodRol());
				detalleParam.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				detalleParam.setTipoParametro(TipoParametricaEnum.PARAM_PARAMETRO.getTipo());
				Parametrica param = ComunicacionServiciosSis.setparametrica(detalleParam);
				if(param.isError()) {
					if(param.getMensaje().equals("1")) {
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.MSG_PARAMETRO_NOGUARDADO,
								MessagesBundleConstants.MSG_PARAMETRO_DUPLICADO);
					}else{
						mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.MSG_PARAMETRO_NOGUARDADO,
								MessagesBundleConstants.DLG_PROCESO_FALLIDO);
					}
					detalleParam.setNombreParametro("");
					detalleParam.setValorParametro("");
					detalleParam.setDescripcion("");
					return;
				}
				mostrarTablaDetalleParametrica(selectedParam);
				habilitarFormulario = false;
				nombreParametro = "";
				if(!param.isError()) {
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO,
							MessagesBundleConstants.MSG_CREACION_PARAMETRO);
					detalleParam = new Parametrica();
					
				}
				
			}else {	
				detalleParam.setValorParametro(detalleParam.getValorParametro().equals("") ? " " : detalleParam.getValorParametro());
				detalleParam.setAudFechaActualizacion(new Date());
				detalleParam.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
				UsuarioDTO usuarioSesion = getUsuarioSesion();
				detalleParam.setAudCodRol((short)usuarioSesion.getCodRol());
				detalleParam.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				detalleParam.setTipoParametro(TipoParametricaEnum.PARAM_PARAMETRO.getTipo());
				Parametrica param = ComunicacionServiciosSis.setparametrica(detalleParam);
				mostrarTablaDetalleParametrica(selectedParam);
				habilitarFormulario = false;
				nombreParametro = "";
				if(!param.isError()) {
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO,
							MessagesBundleConstants.MSG_ACTUALIZACION_PARAMETRO);
					detalleParam = new Parametrica();
				}else {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
							MessagesBundleConstants.DLG_PROCESO_FALLIDO);
					detalleParam = new Parametrica();
				}
			}
			
		}
		flgActivoParametro = false;
		flgActivoParametroSistema = false;
		WebUtils.getInstance().cargarParametros();
		
	}
	
	/**
	 * Metodo que permite guardar parametricas tipo S
	 */
	public void guardarParametricaSistema() {
		selectedParam.setAudFechaActualizacion(new Date());
		selectedParam.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		UsuarioDTO usuarioSesion = getUsuarioSesion();
		selectedParam.setAudCodRol((short)usuarioSesion.getCodRol());
		selectedParam.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
		Parametrica param = ComunicacionServiciosSis.setparametrica(selectedParam);
		nombreParametro = "";
		if(!param.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO,
					MessagesBundleConstants.MSG_CREACION_PARAMETRO_SISTEMA);
			selectedParam = new Parametrica();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			selectedParam = new Parametrica();
		}
		habilitarDetalleParametricaSistema = false;
		flgActivoParametro = false;
		flgActivoParametroSistema = false;
		WebUtils.getInstance().cargarParametros();
	}
	
	public void ocultarPanelSis() {
		habilitarDetalleParametricaSistema = false;
	}
	
	/**
	 * Metodo que elimina una parametrica tipo P
	 * donde: si la parametrica a eliminar no tiene referencia en el sistema,
	 * el metodo realiza un eliminado fisico del parametro. 
	 * Sino, el metodo realiza un eliminado logico y desactiva la parametrica
	 * @param detalleParam
	 */
	public void eliminarDetalleParametrica(Parametrica detalleParam) {
		habilitarDetalleParametricaSistema = false;
		habilitarFormulario= false;
		if(detalleParam.getCodTablaParametrica() !=null) {
			long codTablaParametrica = detalleParam.getCodTablaParametrica().longValue();
			String eliminado = ComunicacionServiciosSis.eliminarParametrica(codTablaParametrica);
			if (eliminado.equals("\"true\"")) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO,
						MessagesBundleConstants.MSG_ELIMINACION_PARAMETRO);
				
			}else {
				detalleParam.setFlgEstado((short)0);
				detalleParam.setFechaFin(new Date());
				detalleParam.setAudFechaActualizacion(new Date());
				detalleParam.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
				UsuarioDTO usuarioSesion = getUsuarioSesion();
				detalleParam.setAudCodRol((short)usuarioSesion.getCodRol());
				detalleParam.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				Parametrica param = ComunicacionServiciosSis.setparametrica(detalleParam);
				nombreParametro = "";
				if(!param.isError()) {
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO,
							MessagesBundleConstants.MSG_ELIMINACION_PARAMETRO_LOGICO);
				}else {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
							MessagesBundleConstants.DLG_PROCESO_FALLIDO);
				}
			}
		}
		
		flgActivoParametro = false;
		WebUtils.getInstance().cargarParametros();
		mostrarTablaDetalleParametrica(selectedParam);
		RequestContext.getCurrentInstance().execute("$('#eliminarParametrica').modal('hide');");
	}
	
	/**
	 * Metodo para funcinalidad de parametros tipo P, 
	 * que muestra la fecha de inicio o fin, dependiendo de si esta activo o no
	 * el flg_de estado
	 */
	public void llenarFechas() {
		if(flgActivoParametro) {
			detalleParam.setFechaInicio(DateUtils.getFechaSistema());
			detalleParam.setFechaFin(null);
			detalleParam.setFlgEstado((short)1);
		}else {
			detalleParam.setFechaInicio(DateUtils.getFechaSistema());
			detalleParam.setFechaFin(DateUtils.getFechaSistema());
			detalleParam.setFlgEstado((short)0);
		}
	}
	
	/**
	 * Metodo para parametros tipo S:
	 * que muestra la fecha de inicio o fin, dependiendo de si esta activo o no el flg del parametro
	 */
	public void llenarFechaParSistema() {
		if(flgActivoParametroSistema) {
			selectedParam.setFechaInicio(DateUtils.getFechaSistema());
			selectedParam.setFechaFin(null);
			selectedParam.setFlgEstado((short)1);
		}else {
			selectedParam.setFechaInicio(DateUtils.getFechaSistema());
			selectedParam.setFechaFin(DateUtils.getFechaSistema());
			selectedParam.setFlgEstado((short)0);
		}
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
		
		this.init();
		
	}
	
	@Override
	public String persist() throws NotSupportedException {
		
		return null;
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		
	}

	public boolean isHabilitarDetalleParametrica() {
		return habilitarDetalleParametrica;
	}

	public void setHabilitarDetalleParametrica(boolean habilitarDetalleParametrica) {
		this.habilitarDetalleParametrica = habilitarDetalleParametrica;
	}

	public boolean isHabilitarDetalleParametricaSistema() {
		return habilitarDetalleParametricaSistema;
	}

	public void setHabilitarDetalleParametricaSistema(boolean habilitarDetalleParametricaSistema) {
		this.habilitarDetalleParametricaSistema = habilitarDetalleParametricaSistema;
	}

}
