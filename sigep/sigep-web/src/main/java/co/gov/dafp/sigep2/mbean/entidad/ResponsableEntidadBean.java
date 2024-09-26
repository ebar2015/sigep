package co.gov.dafp.sigep2.mbean.entidad;

import java.io.Serializable;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ManagedBean(name = "responsableEntidadBean")
@ViewScoped
@Named
public class ResponsableEntidadBean extends BaseBean implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private boolean estSeccPpals;
	private Entidad filtroBusqueda;
	private List<Entidad> listaEntidades;
	private List<Entidad> entidadesSeleccionadas;
	private List<PersonaExt> listaAdmin;
	private List<PersonaExt> lstResponsableEntidad; //Lista que trae la informacion de cada responsable con respecto a las entidades a las que esta asociada
	private static final String MSG_ERROR = "Entidad sin este dato";
	private static final String MSG_INFO = "Dato no configurado";
	private Map<String, Long> responsablesEntidad;
	private Long codResponsable;
	private boolean recarga;
	

	
	public ResponsableEntidadBean() {
		try {
			cerrarSessionFuncion(AMBITO_POLITICAS);
			if(!this.usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL))
				this.mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL,
						MessagesBundleConstants.DLG_HEADER_MENSAJES, 
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
						TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR, getLocale()), 
						"window.location.assign('entidad/../../gestionarEntidad.xhtml?faces-redirect=true')");
			filtroBusqueda = new Entidad();
			this.realizarBusqueda();
			this.responsablesEntidad = new HashMap<>();
			this.filtroBusqueda = new Entidad();
			this.cargarLista();
			this.estSeccPpals = this.recarga = true;
			this.cargarTablaResponsable();
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			contexto.getSessionMap().put("verVideo","modificarEntidad");	
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ENTIDADES);			
		}
		catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR,  MessagesBundleConstants.DLG_HEADER_MENSAJES, "Se ha generado un error al cargar la página");
			logger.error("error public ResponsableEntidadBean() ResponsableEntidadBean: " + ex.getMessage() , ex);
		}
	}
	
	/**
	 * @autor Maria Alejandra Colorado
	 * Metodo que carga una lista con el nombre de los responsables, el total de entidades 
	 * al que pertence general, y segun su orden (Nacional, o territorial)
	 */
	public void cargarTablaResponsable() {
		PersonaExt personaFiltro = new PersonaExt();
		personaFiltro.setCodRol(new BigDecimal(10));
		personaFiltro.setFlgActivo((short)1);
		personaFiltro.setFlgEstado((short) 1);
		personaFiltro.setOrdenNacional(TipoParametro.ORDEN_NACIONAL.getValue());
		personaFiltro.setOrdenTerritorial(TipoParametro.ORDEN_TERRITORIAL.getValue());
		lstResponsableEntidad = ComunicacionServiciosEnt.getPersonaResponsableEntidad(personaFiltro);
	}
	
	public void regresarMenu() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml");
		} catch (IOException ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ERROR_REDIRECCION);
		}
	}
	
	public void quitarFiltros() {
		this.filtroBusqueda = new Entidad();
		this.realizarBusqueda();
	}
	
	private void cargarLista() {
		this.responsablesEntidad.clear();
		this.listaAdmin = new ArrayList<>();
		this.listaAdmin.clear();
		PersonaExt personaFil = new PersonaExt();
		personaFil.setCodRol(new BigDecimal(10));
		personaFil.setFlgActivo((short)1);
		personaFil.setFlgEstado((short) 1);
		personaFil.setFlgAprobado(0);
		listaAdmin = ComunicacionServiciosHV.getPersonaRolEntidad(personaFil);
		for(PersonaExt p : listaAdmin) {
			responsablesEntidad.put(p.getPrimerNombre() + " " + p.getPrimerApellido(), p.getCodPersona().longValue());
			
		}
		
	}
	
	public void asignarResponsable() {
		try {
			if(this.codResponsable == null) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_SELECCIONAR_PERSONA);
				return;
			}
			if(this.entidadesSeleccionadas == null || this.entidadesSeleccionadas.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_SELECCIONAR_ENTIDAD);
				return;
			}
			for(Entidad e : entidadesSeleccionadas) {
				e.setAudAccion(63);
				e.setAudCodRol(new BigDecimal(this.getUsuarioSesion().getCodRol()));
				e.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
				e.setAudFechaActualizacion(new Date());
				e.setCodResponsableGestion(new BigDecimal(this.codResponsable));
				if(!ComunicacionServiciosEnt.setDatosEntidad(e)) {
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ASIGNAR_RESPONSABLE_ERROR);
					return;
				}else {
					cargarTablaResponsable();
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ASIGNAR_RESPONSABLE_PROCESO_EXITOSO);
				}
			}
			this.entidadesSeleccionadas = null;
			this.codResponsable = null;
			
		}catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Se ha generado un error, informacion del error: " + ex.getMessage() + " " + ex.getCause());
		}
	}
	
	public String obtenerNombreParametrica(Long id) {
		try {
			if(id == null)
				return MSG_INFO;
			Parametrica param = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(id));
			if(param == null || param.getNombreParametro() == null)
				return MSG_INFO;
			return param.getNombreParametro();
		}catch(Exception ex) {
			return "Error recuperando datos";
		}
	}
	
	public String obtenerNombreResponsable(long id) {
		Persona per = ComunicacionServiciosHV.getPersonaPorId(id);
		if(per.getPrimerNombre()  == null)
			return "Responsable sin asignar";
		String segundoNom = (per.getSegundoNombre() == null) ? " " : (" " + per.getSegundoNombre() + " " );
		String segunApell = (per.getSegundoApellido() == null) ? " " : (" " + per.getSegundoApellido() + " ");
		return per.getPrimerNombre() + segundoNom + per.getPrimerApellido() + segunApell ;
	}
	
	public String obtenerNombreMun(Long id) {
		try {
			return ComunicacionServiciosSis.getMunicipiosid(id).getNombreMunicipio();
		}catch(Exception ex) {
			return MSG_ERROR;
		}
	}
	
	public String obtenerNombreDepto(Long id) {
		try {
			return ComunicacionServiciosSis.getdeptoporid(id).getNombreDepartamento();
		}catch(Exception ex) {
			return MSG_ERROR;
		}
	}

	public void realizarBusqueda() {
		try {
			if(!this.recarga) {
				this.filtroBusqueda.setCodEstadoEntidad(new BigDecimal(1480));
				this.filtroBusqueda.setCodOrden(new BigDecimal(1363));
				this.filtroBusqueda.setCodClasificacionOrganica(new BigDecimal(1374));
			}
			if(this.filtroBusqueda.getNombreEntidad() != null && this.filtroBusqueda.getNombreEntidad().equals(""))
				this.filtroBusqueda.setNombreEntidad(null);
			if(this.filtroBusqueda.getCodigoSigep() != null && this.filtroBusqueda.getCodigoSigep().equals(""))
				this.filtroBusqueda.setCodigoSigep(null);
			this.filtroBusqueda.setLimitEnd(1000);
			this.filtroBusqueda.setLimitInit(0);
			this.listaEntidades = ComunicacionServiciosEnt.getEntidadesFiltro(this.filtroBusqueda);
		}catch(Exception ex) {
			logger.error("Error realizarBusqueda() ResponsableEntidadBean", ex);
		}
	}

	public boolean isEstSeccPpals() {
		return estSeccPpals;
	}

	public void setEstSeccPpals(boolean estSeccPpals) {
		this.estSeccPpals = estSeccPpals;
	}

	public Entidad getFiltroBusqueda() {
		return filtroBusqueda;
	}

	public void setFiltroBusqueda(Entidad filtroBusqueda) {
		this.filtroBusqueda = filtroBusqueda;
	}

	public List<Entidad> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(List<Entidad> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}
	
	public List<Entidad> getEntidadesSeleccionadas() {
		return entidadesSeleccionadas;
	}

	public void setEntidadesSeleccionadas(List<Entidad> entidadesSeleccionadas) {
		this.entidadesSeleccionadas = entidadesSeleccionadas;
	}

	public List<PersonaExt> getListaAdmin() {
		return listaAdmin;
	}

	public void setListaAdmin(List<PersonaExt> listaAdmin) {
		this.listaAdmin = listaAdmin;
	}

	public Map<String, Long> getResponsablesEntidad() {
		return responsablesEntidad;
	}

	public void setResponsablesEntidad(Map<String, Long> responsablesEntidad) {
		this.responsablesEntidad = responsablesEntidad;
	}
	
	public Long getCodResponsable() {
		return codResponsable;
	}

	public void setCodResponsable(Long codResponsable) {
		this.codResponsable = codResponsable;
	}

	public boolean isRecarga() {
		return recarga;
	}

	public void setRecarga(boolean recarga) {
		this.recarga = recarga;
	}
	
	public String retornarTamanio() {
		return String.valueOf(this.listaEntidades.size());
	}
	
	/**
	 * @return the lstResponsableEntidad
	 */
	public List<PersonaExt> getLstResponsableEntidad() {
		return lstResponsableEntidad;
	}

	/**
	 * @param lstResponsableEntidad the lstResponsableEntidad to set
	 */
	public void setLstResponsableEntidad(List<PersonaExt> lstResponsableEntidad) {
		this.lstResponsableEntidad = lstResponsableEntidad;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		/*ComunicacionServiciosHV.getPersonaRolEntidad(persona);
		ComunicacionServiciosHV.getPersonaporfiltro(persona);
		ComunicacionServiciosHV.getpersonaFiltro(persona);*/
	}

	@Override
	public void init() throws NotSupportedException, SIGEP2SistemaException {
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
}
