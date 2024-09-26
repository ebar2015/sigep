package co.gov.dafp.sigep2.mbean.entidad;

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
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Cargo;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EntidadFusion;
import co.gov.dafp.sigep2.entities.EntidadPlanta;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.SequenciasSigep;
import co.gov.dafp.sigep2.entities.Usuario;
import co.gov.dafp.sigep2.entities.UsuarioEntidad;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.CargoExt;
import co.gov.dafp.sigep2.mbean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.UtilidadesFaces;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

@Named
@ManagedBean
@ViewScoped
public class FusionarEntidadBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 5131929950282880884L;

	private ExternalContext externalContext;
	private Entidad entidadActual;
	private static final Logger logger = Logger.getInstance(FusionarEntidadBean.class);

	private List<Entidad> selectedEntidades;// Lista de entidades

	private Entidad entidad;// Lista de entidades

	private List<VinculacionExt> lstPersonasEntidad;
	private PersonaExt personaEntidad;
	private List<Cargo> lstCargosEntidad;
	private Cargo cargoEntidad;
	private List<DependenciaEntidadExt> lstDependenciasEntidad;

	private Norma tipoNorma;
	private Entidad entidadDestino;
	private Date fechaNorma;
	private Long codEntidad;
	private Long codCargo;
	private boolean estadoActu;
	private String numeroNorma;
	@SuppressWarnings("rawtypes")
	ArrayList entSeleccionadas = new ArrayList();
	@SuppressWarnings("rawtypes")
	ArrayList personasListado = new ArrayList();
	private List<VinculacionExt> lstPersonasEntidadActivas;

	// private List<EntidadPlantaDetalleExt

	public FusionarEntidadBean() {
		/*
		 * nuevaSituacionParticular = new SituacionAdministrativaExt();
		 * strTituloFormulario = ""; boolHabilitadoFormulario = false;
		 * boolHabilitadoDetalle = false; boolVerBotonesForm = false; listaSituaciones =
		 * new ArrayList<>(); listaSituacionAsignada = new ArrayList<>();
		 */
		personaEntidad = new PersonaExt();
		entidadDestino = new Entidad();
		entidadActual = new Entidad();
		init();
	}

	@Override
	@PostConstruct
	public void init() {
		cerrarSessionFuncion(AMBITO_POLITICAS);
		if(!validarPermisoRolFusionarEntidad()){
    		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
					"window.location.assign('entidad/../../gestionarEntidad.xhtml?faces-redirect=true')"); 
    		return;
		}		
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		entidadActual = (Entidad) externalContext.getSessionMap().get("entidadFusionar");

		entidad = new Entidad();
		cargoEntidad = new Cargo();
		tipoNorma = new Norma();
		getInfoFusionar();
	}

	public void cargarEntidad(Entidad entidadActual) {

		entidadActual = ComunicacionServiciosEnt.getEntidadPorId(entidadActual.getCodEntidad().longValue());

	}

	public Entidad getEntidadActual() {
		return entidadActual;
	}

	public void setEntidadActual(Entidad entidadActual) {
		this.entidadActual = entidadActual;
	}

	public Cargo getCargoEntidad() {
		return cargoEntidad;
	}

	public void setCargoEntidad(Cargo cargosEntidad) {
		this.cargoEntidad = cargosEntidad;
	}

	public Long getCodEntidad() {
		return codEntidad;
	}

	public void setNombres(Long codEntidad) {
		this.codEntidad = codEntidad;
	}

	public boolean isEstadoActu() {
		return estadoActu;
	}

	public void setEstadoActu(boolean estadoActu) {
		this.estadoActu = estadoActu;
	}
	
	public String getNumeroNorma() {
		return numeroNorma;
	}

	public void setNumeroNorma(String numeroNorma) {
		this.numeroNorma = numeroNorma;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String persist() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
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

	public List<Entidad> getSelectedEntidades() {
		return selectedEntidades;
	}

	public void setSelectedEntidades(List<Entidad> selectedEntidades) {
		this.selectedEntidades = selectedEntidades;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	public Long getCodCargo() {
		return codCargo;
	}

	public void setCodCargo(Long codCargo) {
		this.codCargo = codCargo;
	}
	
	ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	GestionEntidadBean mGestionEntidadBean = (GestionEntidadBean) elContext.getELResolver().getValue(elContext, null,
			"gestionEntidadBean");
	
	public void getInfoFusionar() {
		
		BigDecimal codEntidadActual = entidadActual.getCodEntidad();
		BigDecimal codEstado = new BigDecimal(1480);
		
		setLstPersonasEntidadActivas(obtenerNombrePersonasActivasEntidad());
		
		if(codEntidadActual != null) {
			EntidadFusion xy = new EntidadFusion();
			xy.setCodEntidadOrigen(codEntidadActual);
			xy.setCodEstadoProceso(codEstado);
			
			List<EntidadFusion> resulEntFusion = ComunicacionServiciosEnt.getEntidadFusion(xy);
			
			if(resulEntFusion != null && !resulEntFusion.isEmpty()) {
				
				Long codigoNorma = resulEntFusion.get(0).getCodNorma();			
				tipoNorma.setCodNorma(codigoNorma);			
				entidadDestino.setCodEntidad(resulEntFusion.get(0).getCodEntidadDestino());
				
				Norma ax = new Norma();
				ax.setCodNorma(codigoNorma);
				tipoNorma = ComunicacionServiciosEnt.getNormaPorId(codigoNorma);
				
				VinculacionExt e = new VinculacionExt();
				e.setCodEntidad(resulEntFusion.get(0).getCodEntidadOrigen().longValue());
				e.setFlgActivo((short) 1); 
				lstPersonasEntidad = ComunicacionServiciosVin.getVinculacionExportacion(e);
			}
		}
	}

	/**
	 * Retorna la lista de personas activas de la entidad a fusionar
	 * @return
	 */
	private List<VinculacionExt> obtenerNombrePersonasActivasEntidad() {
		List<VinculacionExt> lstPersonasActivasEntidad = null;
		BigDecimal codEntidadFusionar = entidadActual.getCodEntidad();

		if (codEntidadFusionar != null) {			
			
			VinculacionExt e = new VinculacionExt();
			e.setCodEntidad(codEntidadFusionar.longValue());
			e.setFlgActivo((short) 1); 
			
			lstPersonasActivasEntidad = ComunicacionServiciosVin.getVinculacionExportacion(e);
			
			if(lstPersonasActivasEntidad != null && !lstPersonasActivasEntidad.isEmpty()) {
				lstPersonasActivasEntidad = obtenerVinculacionesSinDuplicados(lstPersonasActivasEntidad);
			}
			
		}
		return lstPersonasActivasEntidad;
	}

	/**
	 * Listado de entidades del usuario
	 *
	 * @author Hugo Quintero
	 * @param entidad
	 *
	 */

	public List<SelectItem> getEntidadesUsuario() {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Entidad entidadActual = (Entidad) externalContext.getSessionMap().get("entidadFusionar");

		List<SelectItem> list = new ArrayList<>();
		Entidad filtro = new Entidad();
		filtro.setLimitEnd(1000);
		filtro.setLimitInit(0);
		filtro.setCodEstadoEntidad(new BigDecimal(1480));
		List<Entidad> listN = ComunicacionServiciosEnt.getEntidadesFiltro(filtro);
		//List<Entidad> listN = ComunicacionServiciosSis.getEntidadCodPersona(getUsuarioSesion().getId());
		try {
			if (!listN.isEmpty()) {
				for (Entidad aux : listN) {
					if (aux != null) {
						if (!aux.getCodEntidad().equals(entidadActual.getCodEntidad())) {
							list.add(new SelectItem(aux.getCodEntidad(), aux.getNombreEntidad()));
						}
					}
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	
	
	public void fusionarEntidad() {

		BigDecimal codEntidadFusionar = entidadActual.getCodEntidad();
		BigDecimal codEntidadDestino = entidadDestino.getCodEntidad();		
		
		//desactivamos la entidad actual
		if(codEntidadFusionar != null && codEntidadDestino != null) {
			
			Entidad entidadDest = ComunicacionServiciosEnt.getEntidadPorId(entidadDestino.getCodEntidad().longValue());
			
			VinculacionExt e = new VinculacionExt();
			e.setCodEntidad(codEntidadFusionar.longValue());
			e.setFlgActivo((short) 1); 
			
			lstPersonasEntidad = ComunicacionServiciosVin.getVinculacionExportacion(e);
			
			if(lstPersonasEntidad.size() == 0) {			
			
				Entidad a = new Entidad();				
				a.setCodEntidad(codEntidadFusionar);
				a.setCodEstadoEntidad(new BigDecimal(1481));
				a.setCodSubestadoEntidad(1486);
				a.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
				a.setAudFechaActualizacion(DateUtils.getFechaSistema());
	            a.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
	            a.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
				
				boolean entidadFusionar = ComunicacionServiciosEnt.setDatosEntidad(a);				
				
				Entidad b = new Entidad();
				b.setCodEntidad(codEntidadDestino);
				b.setCodEstadoEntidad(new BigDecimal(1480));
				b.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
				b.setAudFechaActualizacion(DateUtils.getFechaSistema());
	            b.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
	            b.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
				//Activo entidad destino
	            boolean entidadDestino = ComunicacionServiciosEnt.setDatosEntidad(b);
				
				if (entidadDestino) {
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_FUSIONAR_EXITO);
				}
			
			}
			else {
				String mensaje = MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_FUSIONAR_PERSONAS_VINCULADAS, getLocale())
						.replace("%entidad%", entidadActual.getNombreEntidad())
						.replace("%personas%", Integer.toString(lstPersonasEntidad.size()));
				
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,mensaje);
			}
		}

	}

	public void cambiarCargoPersona(VinculacionExt lstPersonasEntidad) {
		
		try {
			boolean resultadoVin = false;
			boolean resultadoDesvin = false;
			Long CodEntidadCargoPlan = 0L;
			
			BigDecimal lECodRol = new BigDecimal(getUsuarioSesion().getCodRol());
			BigDecimal IEUsuarioId = new BigDecimal(getUsuarioSesion().getId());
			
			Date fechaNorma = tipoNorma.getFechaNorma();
			
			Entidad entidadDest = ComunicacionServiciosEnt.getEntidadPorId(entidadDestino.getCodEntidad().longValue());
	
			BigDecimal b = new BigDecimal(lstPersonasEntidad.getCodPersona().longValue());
			BigDecimal codCausalVinculacion = new BigDecimal("2015");
			
			//verificamos si el cargo tiene entidad planta
			//Long CodEntidadCargoPlan = obtenerCodEntidadPlantaDetalle(lstPersonasEntidad.getCodCargo().longValue());
			
			if(lstPersonasEntidad.getCodEntidadPlantaDetalle() != null) {
				
				CodEntidadCargoPlan = obtenerCodEntidadPlantaDetalle(lstPersonasEntidad.getCodEntidadPlantaDetalle().longValue());
			
				if(CodEntidadCargoPlan != 0) {
					VinculacionExt a = new VinculacionExt();
					a.setCodVinculacion(lstPersonasEntidad.getCodVinculacion());
					a.setCodPersona(b);
					a.setCodEntidad(entidadActual.getCodEntidad().longValue());
					a.setCodEntidadPlantaDetalle(lstPersonasEntidad.getCodEntidadPlantaDetalle());
					a.setNumActoAdminDesvinculacion(tipoNorma.getNumeroNorma());
					a.setFlgMedicoDocente(lstPersonasEntidad.getFlgMedicoDocente());
					a.setAudCodUsuario(IEUsuarioId);
					a.setAudCodRol(lECodRol);
					a.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
					a.setAudFechaActualizacion(DateUtils.getFechaSistema());
					a.setCodCausalDesvinculacion(codCausalVinculacion);
					a.setFechaActoAdminDesvinculacion(fechaNorma);
					a.setFlgGuardadoParcial((short) 0);
					a.setFlgActivo((short) 0);
			
					resultadoDesvin = ComunicacionServiciosVin.setVinculacion(a);
			
					if (resultadoDesvin == true) {
						EntidadPlantaDetalleExt entidadPlantaDetalle = ComunicacionServiciosVin.getEntidadPlantaDetalleId(CodEntidadCargoPlan);
						if(entidadPlantaDetalle != null) {
							EntidadPlanta entidadPlanta = ComunicacionServiciosSis.getEntidadPlantaId(entidadPlantaDetalle.getCodEntidadPlanta().longValue());
							entidadPlanta.setCodEntidad(getEntidadDestino().getCodEntidad().longValue());
							entidadPlanta.setAudCodUsuario(IEUsuarioId);
							entidadPlanta.setAudCodRol(lECodRol.intValue());
							entidadPlanta.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
							entidadPlanta.setAudFechaActualizacion(DateUtils.getFechaSistema());
							boolean entidadPlantaActualizada = ComunicacionServiciosSis.setEntidadPlanta(entidadPlanta);
							if(entidadPlantaActualizada) {
								VinculacionExt vin = new VinculacionExt();
								//vin.setCodVinculacion(lstPersonasEntidad.getCodVinculacion());
								vin.setCodPersona(b);
								vin.setCodEntidad(getEntidadDestino().getCodEntidad().longValue());
								vin.setCodDependenciaEntidad(lstPersonasEntidad.getCodDependenciaEntidad());
								vin.setCodEntidadPlantaDetalle(new BigDecimal(CodEntidadCargoPlan));
								vin.setFlgMedicoDocente(lstPersonasEntidad.getFlgMedicoDocente());
								vin.setFlgGuardadoParcial((short) 0);
								vin.setAudCodUsuario(IEUsuarioId);
								vin.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
								vin.setAudFechaActualizacion(DateUtils.getFechaSistema());
								vin.setAudCodRol(lECodRol);
								vin.setFlgActivo((short) 1);
					
								resultadoVin = ComunicacionServiciosVin.setVinculacion(vin);
					
								if (resultadoVin == true) {
									//Aquí debemos cambiar la entidad de la Hoja de Vida
									HojaVidaExt hv = new HojaVidaExt();
									hv.setCodPersona(b);
									hv.setLimitInit(0);
									hv.setLimitEnd(1);
									List<HojaVidaExt> personas = ComunicacionServiciosHV.getHojaVidafiltro(hv);
									if(personas != null && !personas.isEmpty()) {
										HojaVidaExt persona = personas.get(0);
										persona.setCodEntidad(getEntidadDestino().getCodEntidad().longValue());
										persona.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
										persona.setAudFechaActualizacion(DateUtils.getFechaSistema());
										persona.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
										persona.setAudCodRol(lECodRol.intValue());
										boolean entidadActualizada = ComunicacionServiciosHV.setHojaVida(persona);
										
										if(entidadActualizada) {
											//Aquí se hace el proceso para actualizar la entidad del usuario
											Usuario usuario = new Usuario();
											usuario.setCodPersona(b);
											usuario = ComunicacionServiciosHV.getusuarioid(usuario);
											UsuarioEntidad usuarioEntidad = new UsuarioEntidad();
											usuarioEntidad.setCodUsuario(usuario.getCodUsuario());
											usuarioEntidad.setCodEntidad(entidadActual.getCodEntidad());
											usuarioEntidad.setFlgActivo((short)1);
											usuarioEntidad = ComunicacionServiciosHV.usenticoduscodent(usuarioEntidad);
											
											//Se cambia la entidad en la tabla UsuarioEntidad
											usuarioEntidad.setCodEntidad(getEntidadDestino().getCodEntidad());
											usuarioEntidad.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
											usuarioEntidad.setAudFechaActualizacion(DateUtils.getFechaSistema());
											usuarioEntidad.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
											usuarioEntidad.setAudCodRol(lECodRol);
											ComunicacionServiciosEnt.setUsuarioEntidad(usuarioEntidad);
											
											
										}
									}
									
									retiraPersonaLista();//retiramos la persona de la lista
									RequestContext.getCurrentInstance().update("tblPersonasActivas");
									mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
											"La persona ha sido desvinculada de la entidad: " + entidadActual.getNombreEntidad() + 
											" y vinculada a la entidad: " + entidadDest.getNombreEntidad());
								}
					
							}
						}			
					}
				}
				else {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							"El cargo no esta relacionado a una planta de personal");
				}
			}
				
		}catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error: " + ex.getMessage() + ex.getCause() + ex.getStackTrace());
			logger.error("Error cambiarCargoPersona() FusionarEntidadBean" , ex);
		}

	}	
	
	public void retiraPersonaLista() {
		VinculacionExt v;
		try {
			for(int i = 0; i < (lstPersonasEntidad.size()); i++) {
				v = lstPersonasEntidad.get(i);
				if(v.getCodPersona() == lstPersonasEntidad.get(i).getCodPersona()) {
					lstPersonasEntidad.remove(i);
					break;
				}
			}
		}catch(IndexOutOfBoundsException ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.ENT_ESC_MSG_ERROR_SIN_REGISTROS_PARA_ASIGNAR + ex.getMessage());
		}catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error: " + ex.getMessage());
		}
	}

	public List<Cargo> cargarCargosEntidad(Long codEntidad) {// se puede borrar

		lstCargosEntidad = null;

		if (codEntidad != null) {
			lstCargosEntidad = ComunicacionServiciosEnt.getCargosEntidad(codEntidad);
		}

		return lstCargosEntidad;
	}

	// Metodo que devuelbe información de una dependencia
	public String getDependencia(Long codDependenciaEntidad) {

		List<DependenciaEntidadExt> valor = null;
		String nombreDependencia = null;

		if (codDependenciaEntidad == null)
			codDependenciaEntidad = 0L;

		if (codDependenciaEntidad != null) {
			DependenciaEntidadExt xy = new DependenciaEntidadExt();
			xy.setCodDependenciaEntidad(codDependenciaEntidad);
			valor = ComunicacionServiciosVin.getDependenciaEntidadFilter(xy);
		}

		if (valor != null && valor.size() != 0)
			nombreDependencia = (String) valor.get(0).getNombreDependencia();

		return nombreDependencia;
	}
	
	public List<SelectItem> getDependenciasEntidad(Long codEntidad) {
		List<SelectItem> list = new ArrayList<>();

		if (codEntidad != null) {
			
			DependenciaEntidadExt xy = new DependenciaEntidadExt();
			xy.setCodEntidad(codEntidad);
			xy.setFlgActivo((short) 1);

			List<DependenciaEntidadExt> listN = ComunicacionServiciosVin.getDependenciaEntidadFilter(xy);
			try {
				if (!listN.isEmpty()) {
					for (DependenciaEntidadExt aux : listN) {
						if (aux != null) {
							list.add(new SelectItem(aux.getCodDependenciaEntidad(), aux.getNombreDependencia()));
						}
					}
				}
			} catch (NullPointerException e) {
				e.getStackTrace();
				return new ArrayList<>();
			}
		}

		return list;
	}
	
	// Metodo que devuelbe información de un cargo
	public String getCargo(Long codCargo) {

		List<CargoExt> valor = null;
		String nombreCargo = null;

		if (codCargo == null)
			codCargo = 0L;

		if (codCargo != null) {
			Cargo xy = new Cargo();
			xy.setCodCargo(codCargo);
			valor = ComunicacionServiciosEnt.getCargosFiltro(xy);
		}

		if (valor != null && valor.size() != 0)
			nombreCargo = (String) valor.get(0).getNombreCargo();

		return nombreCargo;
	}

	public List<SelectItem> getCargosEntidad(Long codEntidad) {
		List<SelectItem> list = new ArrayList<>();

		if (codEntidad != null) {
			// Se consultan las plantas de la entidad
			EntidadPlantaExt entFiltro = new EntidadPlantaExt();
			entFiltro.setCodEntidad(codEntidad);
			List<EntidadPlantaExt> listN = ComunicacionServiciosVin.getEntidadPlantaFilter(entFiltro);
			
			try {
				if (!listN.isEmpty()) {
					for (EntidadPlantaExt aux : listN) {
						if (aux != null) { 
							EntidadPlantaDetalleExt enPlaDe = new EntidadPlantaDetalleExt();
							enPlaDe.setCodEntidadPlanta(new BigDecimal(aux.getCodEntidadPlanta()));
							List<EntidadPlantaDetalleExt> listPlantaDetalle = ComunicacionServiciosVin.getEntidadPlantaDetalleFilter(enPlaDe);
							if (!listPlantaDetalle.isEmpty()) {
								for (EntidadPlantaDetalleExt auxPlantaDetalle : listPlantaDetalle) {
									if (auxPlantaDetalle != null && auxPlantaDetalle.getNombreCargo() != null) { 
										list.add(new SelectItem(auxPlantaDetalle.getCodNomenclaturaDenominacion(), auxPlantaDetalle.getNombreCargo()));
									}
								}
							}
							
						}
					}
				}
			} catch (Exception ex) {
				logger.error("error List<SelectItem> getCargosEntidad(Long codEntidad)" , ex);
				return new ArrayList<>();
			}
		}

		return list;
	}
	
	public List<DependenciaEntidadExt> getLstDependenciasEntidad() {
		return lstDependenciasEntidad;
	}

	public void setLstDependenciasEntidad(List<DependenciaEntidadExt> lstDependenciasEntidad) {
		this.lstDependenciasEntidad = lstDependenciasEntidad;
	}

	public List<Cargo> getLstCargosEntidad() {
		return lstCargosEntidad;
	}

	public void setLstLstCargosEntidad(Cargo cargosEntidad) {
		this.lstCargosEntidad = lstCargosEntidad;
	}

	// personas activas por entidad
	public void cargarPersonasEntidad() {

		BigDecimal codEntidadFusionar = entidadActual.getCodEntidad();

		if (codEntidadFusionar != null) {			
			
			VinculacionExt e = new VinculacionExt();
			e.setCodEntidad(codEntidadFusionar.longValue());
			e.setFlgActivo((short) 1); 
			
			lstPersonasEntidad = ComunicacionServiciosVin.getVinculacionExportacion(e);
			
			if(lstPersonasEntidad == null || lstPersonasEntidad.isEmpty()) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						"La entidad: " + entidadActual.getNombreEntidad() + " no tiene personal vinculado activo");
			}else {
				lstPersonasEntidad = obtenerVinculacionesSinDuplicados(lstPersonasEntidad);
			}			
		}
	}
	
	private List<VinculacionExt> obtenerVinculacionesSinDuplicados(List<VinculacionExt> listaPersonasEntidad) {
		List<VinculacionExt> registros = new ArrayList<VinculacionExt>();
		
		for(VinculacionExt vinc : listaPersonasEntidad) {
			if(!existeRegistroPersona(vinc.getCodPersona(), registros)) {
				registros.add(vinc);
			}
		}
		
		return registros;
	}

	private boolean existeRegistroPersona(BigDecimal codPersona, List<VinculacionExt> registros) {
		for(VinculacionExt vinc : registros) {
			if(codPersona.compareTo(vinc.getCodPersona()) == 0) {
				return true;
			}
		}
		return false;
	}

	public void guardadoParcial() {
		
		List<VinculacionExt> lstPersonasEnt;
		boolean resultadoVin = false;
		boolean resultadoDesvin = false;
		Integer audAccion, audAccionVin;
		BigDecimal codEntidadFusion;
		Long CodEntidadCargoPlan = 0L;
		BigDecimal lECodRol = new BigDecimal(getUsuarioSesion().getCodRol());
		BigDecimal IEUsuarioId = new BigDecimal(getUsuarioSesion().getId());
		
		BigDecimal entidadAct = new BigDecimal(getEntidadActual().getCodEntidad().longValue());
		BigDecimal entidadDes = new BigDecimal(getEntidadDestino().getCodEntidad().longValue());
		BigDecimal codEstadoProceso = new BigDecimal(1480);
		
		//Verificamos si ya existe una vinculacion con guardado parcial para esta entidad
		BigDecimal codEntidadActual = entidadActual.getCodEntidad();
		BigDecimal codEstado = new BigDecimal(1480);
		
		EntidadFusion as = new EntidadFusion();
		as.setCodEntidadOrigen(codEntidadActual);
		as.setCodEstadoProceso(codEstado);
		
		List<EntidadFusion> getEntFusion = ComunicacionServiciosEnt.getEntidadFusion(as);		
		if(getEntFusion != null && !getEntFusion.isEmpty()) {
			audAccion = 63;
			codEntidadFusion = getEntFusion.get(0).getCodEntidadFusion();
		}
		else {
			audAccion = 785;
			codEntidadFusion = new BigDecimal(0);
		}		
		
		//Guardamos en la tabla ENTIDAD_FUSION		
		EntidadFusion xy = new EntidadFusion();
		xy.setCodEntidadFusion(codEntidadFusion);
		xy.setCodEntidadOrigen(entidadAct);
		xy.setCodEntidadDestino(entidadDes);
		xy.setFechaProceso(tipoNorma.getFechaNorma());
		xy.setCodEstadoProceso(codEstadoProceso);
		xy.setAudCodUsuario(IEUsuarioId);
		xy.setAudCodRol(lECodRol);
		xy.setAudAccion(audAccion);
		xy.setAudFechaActualizacion(DateUtils.getFechaSistema());
		
		Norma norma = new Norma();
		norma.setCodTipoNorma(tipoNorma.getCodTipoNorma());
		norma.setFechaNorma(tipoNorma.getFechaNorma());
		norma.setNumeroNorma(tipoNorma.getNumeroNorma());
		
		List<Norma> listNorma = ComunicacionServiciosEnt.getFiltroNorma(norma);
		if (!listNorma.isEmpty()) {
			xy.setCodNorma(listNorma.get(0).getCodNorma());
		} else {
			// Si la norma no existe se guarda
			SequenciasSigep secuenciaNorma = ComunicacionServiciosSis.getSequenciasSigep("NORMA");
			if (secuenciaNorma != null) {
				norma.setCodNorma(secuenciaNorma.getSecuencia());
				norma.setAudCodRol(lECodRol.intValue());
				norma.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
				norma.setAudFechaActualizacion(new Date());
				norma.setFechaNorma(new Date());
				norma.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
				Norma nuevaNorma = ComunicacionServiciosEnt.setNorma(norma);
				if (nuevaNorma != null && nuevaNorma.getCodNorma() != null) {
					xy.setCodNorma(nuevaNorma.getCodNorma());
				} else {
					xy.setCodNorma(null);
				}

			}
		}
		
		EntidadFusion resulEntFusion = ComunicacionServiciosEnt.setEntidadFusion(xy);
		
		if(resulEntFusion != null && (resulEntFusion.getMensaje().equals("Registro Almacenado Existosamente.") || resulEntFusion.getMensaje().equals("Actualizacion Existosa."))) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Guardado parcial exitoso");
			regresarGestionarEntidad();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Ha ocurrido un problema al realizar el guardado parcial");
		}
	}
	
	
	public long obtenerCodEntidadPlantaDetalle(Long codEntidadPlantaDetalle) throws EntidadExcepcion {
		try {
		EntidadPlantaDetalleExt entiCargoP = new EntidadPlantaDetalleExt();
		entiCargoP.setCodEntidadPlantaDetalle(codEntidadPlantaDetalle);
		entiCargoP.setFlgActivo((short)1);
		return ComunicacionServiciosVin.getEntidadPlantaDetalleFilter(entiCargoP).get(0).getCodEntidadPlantaDetalle();
		}catch(Exception ex) {
			/*mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"El cargo no esta relacionado a una planta de personal");*/
					
			return 0;
		}
	}
	
	public boolean crearEntidadPlantaDetalle(Long codCargo, Long codEntidad) {
		
		boolean resulECP;
		
		try {	
		
			EntidadPlantaDetalleExt cp = new EntidadPlantaDetalleExt();		
			cp.setCodCargo(codCargo);		
			List<EntidadPlantaDetalleExt> listCCP = ComunicacionServiciosVin.getCargoPlanta(cp);
			
			EntidadPlantaExt pp = new EntidadPlantaExt();
			pp.setCodEntidad(codEntidad);			
			List<EntidadPlantaExt> listPP = ComunicacionServiciosVin.getEntidadPlantaFilter(pp);	
			
			EntidadPlantaDetalleExt ecp = new EntidadPlantaDetalleExt();		
			/*@TODO Modifica temporalmente PabloQuintana
			ecp.setCodEntidadCargo(listCCP.get(0).getCodEntidadCargo());
			*/
			ecp.setCodEntidadPlanta(new BigDecimal(listPP.get(0).getCodEntidadPlanta()));
			ecp.setCodTipoPlanta(new BigDecimal(1958));
			
			resulECP = ComunicacionServiciosVin.setEntidadPlantaDetalle(ecp);
			
		}catch(Exception ex) {
			/*mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"El cargo no esta relacionado a una planta de personal");*/
			resulECP = false;
		}
	
		return resulECP;
		
	}

	public void celdaEditada(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		if (newValue != null && !newValue.equals(oldValue)) {
		}
	}
	
	/**
	 * metodo utilizado para obtener los numeros de norma de una entidad.
	 * 
	 * @param tipoNorma
	 * @param fechaNorma
	 * @return
	 */
	public List<SelectItem> getNumerosNorma(Long tipoNorma, Date fechaNorma) {
		List<SelectItem> list = new ArrayList<>();
		Norma norma = new Norma();
		if (tipoNorma != null)
			norma.setCodTipoNorma(tipoNorma.intValue());
		norma.setFechaNorma(fechaNorma);

		List<Norma> listNorma = ComunicacionServiciosEnt.getFiltroNorma(norma);
		try {
			if (!listNorma.isEmpty()) {
				for (Norma aux : listNorma)
					list.add(new SelectItem(aux.getNumeroNorma(), aux.getNumeroNorma()));
			}
		} 
		catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}
		return list;
	}

	public List<SelectItem> getPersonEntidad(Long codEntidad) {
		List<SelectItem> list = new ArrayList<>();

		BigDecimal b = new BigDecimal(codEntidad);

		PersonaExt y = new PersonaExt();
		y.setCodEntidad(b);
		y.setFlgActivo((short) 1);

		List<PersonaExt> listN = ComunicacionServiciosEnt.getPersonasActivasEntidad(y);
		try {
			if (!listN.isEmpty()) {
				for (PersonaExt aux : listN) {
					if (!aux.isError()) {
						list.add(new SelectItem(aux.getCodEntidad(), aux.getNombreEntidad()));
					}
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	public List<VinculacionExt> getLstPersonasEntidad() {
		return lstPersonasEntidad;
	}

	public void setLstPersonasEntidad(PersonaExt personasEntidad) {
		this.lstPersonasEntidad = lstPersonasEntidad;
	}

	public PersonaExt getPersonaEntidad() {
		return personaEntidad;
	}

	public void setPersonaEntidad(PersonaExt personaEntidad) {
		this.personaEntidad = personaEntidad;
	}

	public Entidad getEntidadDestino() {
		return entidadDestino;
	}

	public void setEntidadDestino(Entidad entidadDestino) {
		this.entidadDestino = entidadDestino;
	}

	public Norma getTipoNorma() {
		return tipoNorma;
	}

	public void setTipoNorma(Norma tipoNorma) {
		this.tipoNorma = tipoNorma;
	}

	public Date getFechaNorma() {
		return fechaNorma;
	}

	public void setFechaNorma(Date fechaNorma) {
		this.fechaNorma = fechaNorma;
	}

	/**
	 * Redirecciona a Gestionar entidades
	 *
	 * @author Hugo Quintero
	 * @param entidad
	 *
	 */
	public void regresarGestionarEntidad() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		UtilidadesFaces.redirect("../gestionarEntidad.xhtml");
	}
	
    private boolean validarPermisoRolFusionarEntidad(){
    	try {
			if(usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES)){
				return true;
			}else{
				return false;
			}
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
    	return false;
    }

	public List<VinculacionExt> getLstPersonasEntidadActivas() {
		return lstPersonasEntidadActivas;
	}

	public void setLstPersonasEntidadActivas(List<VinculacionExt> lstPersonasEntidadActivas) {
		this.lstPersonasEntidadActivas = lstPersonasEntidadActivas;
	}
}