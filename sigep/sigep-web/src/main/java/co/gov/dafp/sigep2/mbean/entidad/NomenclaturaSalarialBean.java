package co.gov.dafp.sigep2.mbean.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.AsignacionSalarial;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EscalaSalarial;
import co.gov.dafp.sigep2.entities.IncrementoSalarial;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Nomenclatura;
import co.gov.dafp.sigep2.entities.NomenclaturaEntidad;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.AsignacionSalarialExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ManagedBean
@ViewScoped
public class NomenclaturaSalarialBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -5635488685008277017L;
	private List<Entidad> listaEntidades;
	private List<Entidad> entidadesSeleccionadas;
	private List<NomenclaturaExt> listaNomenclaturas;
	private EntidadExt filtro;
	private NomenclaturaExt nomenclaturaSeleccionada;
	private Boolean verContenidoNomenclatura;
	private Boolean verContenidoEscalaSalarial;
	private boolean visibilidaFormEnti;
	private boolean visibilidaFormNomen;
	private boolean estadoSelectMunicipio;
	private Map<String, Long> listadoNomenclaturas;
	private Norma normaSeleccionada;
	private EscalaSalarial filtroEscala;
	private EscalaSalarial escalaSeleccionada;
	private Integer codDepto;
	private Long codOrden;
	private Long codSistemaCarrera;
	private Long codRama;
	private Long codNaturaleza;
	private Long codNomenclatura;
	private String nombreEntidad;
	private IncrementoSalarial incrementoSeleccionado;
	private List<Norma> normas;
	private Integer codMun;
	private List<EscalaSalarial> listaEscalas;
	private List<AsignacionSalarialExt> listaAsignaciones;
	private AsignacionSalarialExt asignacionSeleccionada;
	private Long codTipoNorma;
	private Date fechaNorma;
	private HashMap<String, Long> listaNormas;
	private boolean estadoBotonesFormulario;
	private boolean visibilidadFormEscala;
	private boolean formEscalaCreacion;
	private boolean formIncrementarEscala;
	private AsignacionSalarialExt asignacionFiltro;
	private boolean lbIsRolEscalaSalarial, lbIsRolNomenclatura; 
	private List<EntidadExt> entidadesPersona;
	private boolean estadoTablaNome;
	private Long codNorma;
	
	public List<Norma> getNormas() {
		return normas;
	}

	public void setNormas(List<Norma> normas) {
		this.normas = normas;
	}
	public Norma getNormaSeleccionada() {
		return normaSeleccionada;
	}

	public void setNormaSeleccionada(Norma normaSeleccionada) {
		this.normaSeleccionada = normaSeleccionada;
	}

	public HashMap<String, Long> getListaNormas() {
		return listaNormas;
	}

	public void setListaNormas(HashMap<String, Long> listaNormas) {
		this.listaNormas = listaNormas;
	}

	public boolean isVisibilidaFormEnti() {
		return visibilidaFormEnti;
	}

	public void setVisibilidaFormEnti(boolean visibilidaFormEnti) {
		this.visibilidaFormEnti = visibilidaFormEnti;
	}

	public List<EscalaSalarial> getListaEscalas() {
		return listaEscalas;
	}

	public void setListaEscalas(List<EscalaSalarial> listaEscalas) {
		this.listaEscalas = listaEscalas;
	}

	public Entidad getFiltro() {
		return filtro;
	}

	public void setFiltro(EntidadExt filtro) {
		this.filtro = filtro;
	}

	public Nomenclatura getNomenclaturaSeleccionada() {
		return nomenclaturaSeleccionada;
	}

	public void setNomenclaturaSeleccionada(NomenclaturaExt nomenclaturaSeleccionada) {
		this.nomenclaturaSeleccionada = nomenclaturaSeleccionada;
	}

	public Boolean getVerContenidoNomenclatura() {
		return verContenidoNomenclatura;
	}

	public void setVerContenidoNomenclatura(Boolean verContenidoNomenclatura) {
		this.verContenidoNomenclatura = verContenidoNomenclatura;
	}

	public Boolean getVerContenidoEscalaSalarial() {
		return verContenidoEscalaSalarial;
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

	public List<NomenclaturaExt> getListaNomenclaturas() {
		return listaNomenclaturas;
	}

	public void setListaNomenclaturas(List<NomenclaturaExt> listaNomenclaturas) {
		this.listaNomenclaturas = listaNomenclaturas;
	}

	public boolean isVisibilidaFormNomen() {
		return visibilidaFormNomen;
	}

	public void setVisibilidaFormNomen(boolean visibilidaFormNomen) {
		this.visibilidaFormNomen = visibilidaFormNomen;
	}

	public Map<String, Long> getListadoNomenclaturas() {
		return listadoNomenclaturas;
	}

	public void setListadoNomenclaturas(Map<String, Long> listadoNomenclaturas) {
		this.listadoNomenclaturas = listadoNomenclaturas;
	}

	public void setVerContenidoEscalaSalarial(Boolean verContenidoEscalaSalarial) {
		this.verContenidoEscalaSalarial = verContenidoEscalaSalarial;
	}
	
	public List<AsignacionSalarialExt> getListaAsignaciones() {
		return listaAsignaciones;
	}

	public void setListaAsignaciones(List<AsignacionSalarialExt> listaAsignaciones) {
		this.listaAsignaciones = listaAsignaciones;
	}

	public NomenclaturaSalarialBean() {
		cerrarSessionFuncion(AMBITO_POLITICAS);
		this.escalaSeleccionada = new EscalaSalarial();
		this.normaSeleccionada = new Norma();
		this.verContenidoNomenclatura = true;
		this.listaEscalas = new ArrayList<>();
		this.verContenidoEscalaSalarial = false;
		this.visibilidadFormEscala = true;
		this.listaEntidades = new ArrayList<>();
		this.asignacionSeleccionada = new AsignacionSalarialExt();
		this.visibilidaFormNomen = false;
		this.setFormEscalaCreacion(false);
		this.filtro = new EntidadExt();
		this.formIncrementarEscala = false;
		this.mostrarNomenclatura();
		this.mostrarEscalas();
		this.nomenclaturaSeleccionada = new NomenclaturaExt();
		this.estadoTablaNome = true;
		this.normas = ComunicacionServiciosEnt.getTodasNormas();
		this.incrementoSeleccionado = new IncrementoSalarial();
		this.incrementoSeleccionado.setEsincrementoPorcentual("1");
		this.cargarListaEntidades(); 
	}
	
	/**
	 * Metodo que lista todas las escalas salariales.
	 */
	public void mostrarEscalas() {
		this.asignacionFiltro = new AsignacionSalarialExt(); 
		this.listaAsignaciones = ComunicacionServiciosEnt.getASignacionSalarialFitro(asignacionFiltro);
	}

	/**
	 * Metodo que lista las nomenclaturas activas
	 */
	public void mostrarNomenclatura() {
		NomenclaturaExt nomFilter = new NomenclaturaExt();
		nomFilter.setFlgActivo((short)1);
		this.listaNomenclaturas = ComunicacionServiciosEnt.getNomenclaturaFiltro(nomFilter);
	}
	/**
	 * Metodo que muestra la opcion de asociar nomenclatura entidad
	 * @param nomen
	 */
	public void mostrarEntidades(NomenclaturaExt nomen) {
		RequestContext.getCurrentInstance().execute("document.location.href = '#anclaAsociar'");
		this.nomenclaturaSeleccionada = nomen;
		this.visibilidaFormEnti = true;
		this.visibilidaFormNomen = false;
	}

	public void habilitarCreacionNomen() {
		this.nomenclaturaSeleccionada = new NomenclaturaExt();
		this.visibilidaFormEnti = false;
		this.visibilidaFormNomen = true;
		this.estadoBotonesFormulario = false;
	}

	public void asignarNomenclatura() {
		try {
			for (Entidad e : entidadesSeleccionadas){
				NomenclaturaEntidad n = new NomenclaturaEntidad();
				n.setAudAccion(785);
				n.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
				n.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
				n.setCodEntidad(e.getCodEntidad().longValue());
				n.setAudFechaActualizacion(new Date());
				n.setCodNomenclatura(nomenclaturaSeleccionada.getCodNomenclatura());
				ComunicacionServiciosEnt.setNomenclaturaEntidad(n);
				this.visibilidaFormEnti = false;
				this.visibilidaFormNomen = false;
			}
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Asignación finalizada", "La asignación se ha finalizado con éxito");
			entidadesSeleccionadas.clear();
			this.listaEntidades.clear();
			this.filtro = new EntidadExt();
		} catch (Exception ex) {
			logger.error("Se ha generado un error en public void asignarNomenclatura() NomenclaturaSalarialBean", ex);
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error en asignación", "Se ha generado un error asignado la nomenclatura a las entidades seleccionadas");
		}
	}

	/**
	 * Metodo que permite guardar una nomenclatura
	 */
	public void guardarNomenclatura() {
		try {
			if(this.nomenclaturaSeleccionada.getCodNorma() == null && this.nomenclaturaSeleccionada.getJustificacion() == null) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, "Estimado usuario", "Debe proporcioonar un número de norma o una justificación");
				return;
			}
			if(this.nomenclaturaSeleccionada.getCodNomenclatura() == null)
				this.nomenclaturaSeleccionada.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
			else
				this.nomenclaturaSeleccionada.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
			this.nomenclaturaSeleccionada.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
			this.nomenclaturaSeleccionada.setAudFechaActualizacion(new Date());
			this.nomenclaturaSeleccionada.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
			this.nomenclaturaSeleccionada.setFlgActivo((short)1);
			boolean valid = ComunicacionServiciosEnt.setNomenclatura(nomenclaturaSeleccionada);
			if(valid) {
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Señor usuario", "La nomenclatura se ha guardado con exito");
				this.visibilidaFormEnti = false;
				this.visibilidaFormNomen = false;
				this.normaSeleccionada = new Norma();
				this.nomenclaturaSeleccionada = new NomenclaturaExt();
				this.mostrarNomenclatura();
			}else {
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error guardando nomenclatura", "Se ha generado un error mientras se guardaba la nomenclatura");
			}
			
		}catch(Exception ex) {
			logger.error("Se ha generado un error en public void guardarNomenclatura() NomenclaturaSalarialBean: " + ex.getMessage(), ex);
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error guardando nomenclatura", "Se ha generado un error mientras se guardaba la nomenclatura");
		}
	}

	/**
	 * Metodo que busca todas las entidades
	 * a las que esta asociada la persona en sesion
	 */
	public void buscarEntidades() {
		if(filtro.getCodigoSigep().equals(""))
			filtro.setCodigoSigep(null);
		if(filtro.getNombreEntidad().equals(""))
			filtro.setNombreEntidad(null);
		this.cargarListaEntidades();
		if(entidadesPersona.isEmpty()) {
			String msg = " No se encontraron coincidencias";
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Búsqueda finalizada", "La búsqueda ha finalizado." + msg);
		}else {
			String msg = "Se encontraron resultados para esta búsqueda";
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Búsqueda finalizada", "La búsqueda ha finalizado." + msg);
		}
	}

	public String obtenerNombreParametrica(Long id) {
		try {
			if (id == null)
				return "Dato no configurado";
			Parametrica param = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(id));
			if (param.getNombreParametro() != null && !param.getNombreParametro().isEmpty())
				return param.getNombreParametro();
			else
				return "Dato sin configurar";
		} catch (NullPointerException ex) {
			logger.error("public String obtenerNombreParametrica(Long id) NomenclaturaSalarialBean", ex);
			return "No se encontro el parametro con el id especidicado";
		} catch (Exception ex) {
			logger.error("public String obtenerNombreParametrica(Long id) NomenclaturaSalarialBean", ex);
			return "Error recuperando dato";
		}
	}

	public void buscarEscalasSalariales() {
		this.filtroEscala.setFlgActivo((short) 0);
		this.listaEscalas = ComunicacionServiciosEnt.getEscalasSalariales();
	}

	public void cambiarVisibilidadContenido() {
		verContenidoNomenclatura = !verContenidoNomenclatura;
		verContenidoEscalaSalarial = !verContenidoEscalaSalarial;
	}
	
	//Reemplazar por servicio para mejorar rendimiento
	public void obtenerNormasPorTipo() {
		List<Norma> normasAux = ComunicacionServiciosEnt.getTodasNormas();
		this.listaNormas = new HashMap<>();
		if(normasAux.isEmpty())
			return;
		for(Norma n : normasAux) {
			if(this.codTipoNorma != null && this.codTipoNorma == n.getCodTipoNorma().intValue()) {
				listaNormas.put(n.getNumeroNorma(), n.getCodNorma());
				normas.add(n);
				continue;
			}
			if(this.codTipoNorma == null) {
				normas.add(n);
				listaNormas.put(n.getNumeroNorma(), n.getCodNorma());
			}
		}
	}
	
	//reemplazar por servicio para mejorar rendimiento
	public void obtenerNormasPorFecha() {
		if(this.fechaNorma == null) {
			this.obtenerNormasPorTipo();
			return;
		}
		HashMap<String, Long> listaNormasAux = new HashMap<>();
		List<Norma> normasAux = new ArrayList<>();
		for(Norma n : normas) {
			if(this.fechaNorma == n.getFechaNorma()) {
				listaNormasAux.put(n.getNumeroNorma(), n.getCodNorma());
				normasAux.add(n);
			}
		}
		normas = normasAux;
		listaNormas = listaNormasAux;
		
	}
	
	public void atras() {
		
	}
	
	public void activarCreacionEscala() {
		this.formEscalaCreacion = true;
		this.formIncrementarEscala = false;

	}

	public void editarEscalaSalarial() {

	}

	public void activarEdicionEscalaSalarial(AsignacionSalarialExt escala) {
		this.asignacionSeleccionada = escala;
	}

	public Long validarCiudadDepto() {
		Departamento depto = ComunicacionServiciosSis.getdeptoporid(this.codDepto);
		if (this.codOrden != null && this.codOrden == 1363 ) {
			if(depto.getNombreDepartamento().equalsIgnoreCase("bogotá") || depto.getNombreDepartamento().equalsIgnoreCase("bogota")) {
				List<Municipio> muns = ComunicacionServiciosSis.getMunicipios(id);
				for (Municipio m : muns) {
					if (m.getCodMunicipioDane() == 1) {
						this.estadoSelectMunicipio = false;
						return (long) (m.getCodMunicipio());
					}
				}
			}
			else {
				this.codOrden = this.getEntidadUsuario().getCodOrden().longValue();
				this.setCodSistemaCarrera(this.getEntidadUsuario().getCodSistemaCarrera().longValue());
				this.setCodSistemaCarrera(this.nomenclaturaSeleccionada.getCodSistemaCarrera());
				//this.setCodRama(this.getNomenclaturaSeleccionada().getCodRamaOrganizacionEnte());
			}
		}
		return null;
	}

	public void crearEscala() {
		try {
			if(this.asignacionSeleccionada.getValorAsignacion() != null && this.asignacionSeleccionada.getPorcentajeAsignacion() != null) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.MSG_ENTIDAD_ESCALA_INFO, MessagesBundleConstants.MSG_ENTIDAD_ESCALA_ERROR);
				return;
			}
			if(this.asignacionSeleccionada.getValorAsignacion() == null && this.asignacionSeleccionada.getPorcentajeAsignacion() != null)
				this.asignacionSeleccionada.setValorAsignacion(this.asignacionSeleccionada.getPorcentajeAsignacion() * 4000);
			this.asignacionSeleccionada.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
			this.asignacionSeleccionada.setAudFechaActualizacion(new Date());
			this.asignacionSeleccionada.setFlgActivo((short) 0);
			this.asignacionSeleccionada.setAudAccion(785);
			this.asignacionSeleccionada.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
			AsignacionSalarial respu = ComunicacionServiciosEnt.setAsignacionSalarial(this.asignacionSeleccionada);
			if(respu.isError()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Estimado usuario", "se ha generado un error: " + respu.getMensajeTecnico());
				return;
			}
			else 
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "El registro se ha guardado correctamente.");
			mostrarEscalas();
			this.setFormEscalaCreacion(false);
		}catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Imposible agregar escala", "Se ha generado un erroa al guardar la escala salarial, consulte con el administrador");
		}
	}

	public void eliminarEscalaSalarial() {
		try {
			this.escalaSeleccionada.setFlgActivo((short) 0);
			ComunicacionServiciosEnt.setEscalaSalarial(this.escalaSeleccionada);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "La escala se ha eliminado con éxito");
		}catch(Exception ex) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "Se ha generado un error eliminando la escala , por favor consulte con el administrador");
		}
	}
	
	public void consultarNomenclatura(NomenclaturaExt nomen){
		this.nomenclaturaSeleccionada = nomen;
		this.visibilidaFormEnti = false;
		this.visibilidaFormNomen = true;
		this.estadoBotonesFormulario = true;
	}
	
	public void editarNomenclatura(NomenclaturaExt nomen){
		this.nomenclaturaSeleccionada = nomen;
		this.visibilidaFormEnti = false;
		this.visibilidaFormNomen = true;
		this.estadoBotonesFormulario = false;
	}
	
	public void confirmarEliminarNomenclatura() {
		try {
			this.nomenclaturaSeleccionada.setFlgActivo((short) 0);
			this.nomenclaturaSeleccionada.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
			this.nomenclaturaSeleccionada.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
			this.nomenclaturaSeleccionada.setAudFechaActualizacion(DateUtils.getFechaSistema());
			this.nomenclaturaSeleccionada.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
			boolean valid = ComunicacionServiciosEnt.setNomenclatura(nomenclaturaSeleccionada);
			if(valid) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "La nomenclatura se ha eliminado con exito");
				this.mostrarNomenclatura();
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "Ha ocurrido un error eliminando la nomenclatura");
			}
		}catch(Exception ex) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "Ha ocurrido un error eliminando la nomenclatura");
		}
	}
	
	
	public void eliminarNomenclatura(NomenclaturaExt nomen){
		this.nomenclaturaSeleccionada = nomen;
	}
	
	/**
	 * Metodo que cancela la creacion de una nomenclatura()
	 */
	public void cancelarNomenclatura() {
		this.visibilidaFormEnti = false;
		this.visibilidaFormNomen = false;
		this.nomenclaturaSeleccionada = new NomenclaturaExt();
		this.normaSeleccionada = new Norma();
	}
	
	
	/**
	 * Metodo de confimación de cancelacion de filtro de busqueda
	 */
	public void confirmacionFiltroBusqueda() {
		cancelarBusquedaAsociarNomenclatura();
		mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_CANCELAR_BUSQUEDA_ENTIDADES_ASOCIAR);
	}
	
	/**
	 *Metodo que permite cancelar el filtro de busqueda de asociar nomenclatura 
	 */
	public void cancelarBusquedaAsociarNomenclatura() {
		filtro = new EntidadExt();
		this.entidadesPersona = new ArrayList<>();
			
	}
	
	/**
	 * Metodo que cancela la asocion de una nomenclatura
	 */
	public void cancelarAsociacionNomenclatura() {
		cancelarBusquedaAsociarNomenclatura();
		this.visibilidaFormEnti = false;
		this.visibilidaFormNomen = false;
		entidadesSeleccionadas = new ArrayList<>();
	}
	
	public void activarEliminacionEscalaSalarial(EscalaSalarial escala) {
		
	}

	@Override
	public void validateForm(ComponentSystemEvent event) {

	}

	@Override
	@PostConstruct
	public void init() {
		validarPermisoRolNomenclatura();
		validarPermisoRolEscalaSalarial();
		if(!lbIsRolNomenclatura && !lbIsRolEscalaSalarial){
    		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
					"window.location.assign('entidad/../../../gestionarEntidad.xhtml?faces-redirect=true')"); 
    		return;
		}
		if(lbIsRolEscalaSalarial && !lbIsRolNomenclatura){
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_NOMENCLATURA);
		}
	}

	@Override
	public String persist() {
		return null;
	}

	@Override
	public void retrieve() {
	}

	@Override
	public String update() {
		return null;
	}

	@Override
	public void delete() {

	}

	public EscalaSalarial getFiltroEscala() {
		return filtroEscala;
	}

	public void setFiltroEscala(EscalaSalarial filtroEscala) {
		this.filtroEscala = filtroEscala;
	}

	/**
	 * @return the codDepto
	 */
	public Integer getCodDepto() {
		return codDepto;
	}

	/**
	 * @param codDepto
	 *            the codDepto to set
	 */
	public void setCodDepto(Integer codDepto) {
		this.codDepto = codDepto;
	}

	/**
	 * @return the codMun
	 */
	public Integer getCodMun() {
		return codMun;
	}

	/**
	 * @param codMun
	 *            the codMun to set
	 */
	public void setCodMun(Integer codMun) {
		this.codMun = codMun;
	}

	/**
	 * @return the escalaSeleccionada
	 */
	public EscalaSalarial getEscalaSeleccionada() {
		return escalaSeleccionada;
	}

	/**
	 * @param escalaSeleccionada
	 *            the escalaSeleccionada to set
	 */
	public void setEscalaSeleccionada(EscalaSalarial escalaSeleccionada) {
		this.escalaSeleccionada = escalaSeleccionada;
	}

	/**
	 * @return the estadoMunicipio
	 */
	public boolean isEstadoSelectMunicipio() {
		return estadoSelectMunicipio;
	}

	/**
	 * @param estadoMunicipio
	 *            the estadoMunicipio to set
	 */
	public void setEstadoSelectMunicipio(boolean estadoSelectMunicipio) {
		this.estadoSelectMunicipio = estadoSelectMunicipio;
	}

	/**
	 * @return the codOrden
	 */
	public Long getCodOrden() {
		return codOrden;
	}

	/**
	 * @param codOrden the codOrden to set
	 */
	public void setCodOrden(Long codOrden) {
		this.codOrden = codOrden;
	}

	/**
	 * @return the codSistemaCarrera
	 */
	public Long getCodSistemaCarrera() {
		return codSistemaCarrera;
	}

	/**
	 * @param codSistemaCarrera the codSistemaCarrera to set
	 */
	public void setCodSistemaCarrera(Long codSistemaCarrera) {
		this.codSistemaCarrera = codSistemaCarrera;
	}

	/**
	 * @return the codNaturaleza
	 */
	public Long getCodNaturaleza() {
		return codNaturaleza;
	}

	/**
	 * @param codNaturaleza the codNaturaleza to set
	 */
	public void setCodNaturaleza(Long codNaturaleza) {
		this.codNaturaleza = codNaturaleza;
	}

	/**
	 * @return the codRama
	 */
	public Long getCodRama() {
		return codRama;
	}

	/**
	 * @param codRama the codRama to set
	 */
	public void setCodRama(Long codRama) {
		this.codRama = codRama;
	}

	/**
	 * @return the codNomenclatura
	 */
	public Long getCodNomenclatura() {
		return codNomenclatura;
	}

	/**
	 * @param codNomenclatura the codNomenclatura to set
	 */
	public void setCodNomenclatura(Long codNomenclatura) {
		this.codNomenclatura = codNomenclatura;
	}

	/**
	 * @return the nommbreEntidad
	 */
	public String getNombreEntidad() {
		return nombreEntidad;
	}

	/**
	 * @param nommbreEntidad the nommbreEntidad to set
	 */
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	/**
	 * @return the incrementoSeleccionado
	 */
	public IncrementoSalarial getIncrementoSeleccionado() {
		return incrementoSeleccionado;
	}

	/**
	 * @param incrementoSeleccionado the incrementoSeleccionado to set
	 */
	public void setIncrementoSeleccionado(IncrementoSalarial incrementoSeleccionado) {
		this.incrementoSeleccionado = incrementoSeleccionado;
	}

	/**
	 * @return the asignacionSeleccionada
	 */
	public AsignacionSalarial getAsignacionSeleccionada() {
		return asignacionSeleccionada;
	}

	/**
	 * @param asignacionSeleccionada the asignacionSeleccionada to set
	 */
	public void setAsignacionSeleccionada(AsignacionSalarialExt asignacionSeleccionada) {
		this.asignacionSeleccionada = asignacionSeleccionada;
	}

	/**
	 * @return the codTipoNorma
	 */
	public Long getCodTipoNorma() {
		return codTipoNorma;
	}

	/**
	 * @param codTipoNorma the codTipoNorma to set
	 */
	public void setCodTipoNorma(Long codTipoNorma) {
		this.codTipoNorma = codTipoNorma;
	}

	/**
	 * @return the fechaNorma
	 */
	public Date getFechaNorma() {
		return fechaNorma;
	}

	/**
	 * @param fechaNorma the fechaNorma to set
	 */
	public void setFechaNorma(Date fechaNorma) {
		this.fechaNorma = fechaNorma;
	}

	public boolean isEstadoBotonesFormulario() {
		return estadoBotonesFormulario;
	}

	public void setEstadoBotonesFormulario(boolean estadoFormulario) {
		this.estadoBotonesFormulario = estadoFormulario;
	}

	public boolean isVisibilidadFormEscala() {
		return visibilidadFormEscala;
	}

	public void setVisibilidadFormEscala(boolean visibilidadFormEscala) {
		this.visibilidadFormEscala = visibilidadFormEscala;
	}

	public boolean isFormEscalaCreacion() {
		return formEscalaCreacion;
	}

	public void setFormEscalaCreacion(boolean formEscalaCreacion) {
		this.formEscalaCreacion = formEscalaCreacion;
	}

	public boolean isFormIncrementarEscala() {
		return formIncrementarEscala;
	}

	public void setFormIncrementarEscala(boolean formIncrementarEscala) {
		this.formIncrementarEscala = formIncrementarEscala;
	}

	public AsignacionSalarialExt getAsignacionFiltro() {
		return asignacionFiltro;
	}

	public void setAsignacionFiltro(AsignacionSalarialExt asignacionFiltro) {
		this.asignacionFiltro = asignacionFiltro;
	}
	

	
	
    private void validarPermisoRolNomenclatura(){
    	try {
			lbIsRolNomenclatura = usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,RolDTO.CARACTERIZADOR_ENTIDAD);
		}catch (SIGEP2SistemaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private void validarPermisoRolEscalaSalarial(){
    	try {
    		lbIsRolEscalaSalarial = usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
    														RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_TALENTO_HUMANO,RolDTO.CARACTERIZADOR_ENTIDAD);
		} catch (SIGEP2SistemaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }    

	public boolean isLbIsRolEscalaSalarial() {
		return lbIsRolEscalaSalarial;
	}

	public void setLbIsRolEscalaSalarial(boolean lbIsRolEscalaSalarial) {
		this.lbIsRolEscalaSalarial = lbIsRolEscalaSalarial;
	}

	public boolean isLbIsRolNomenclatura() {
		return lbIsRolNomenclatura;
	}

	public void setLbIsRolNomenclatura(boolean lbIsRolNomenclatura) {
		this.lbIsRolNomenclatura = lbIsRolNomenclatura;
	}

	/**
	 * @return the entidadesPersona
	 */
	public List<EntidadExt> getEntidadesPersona() {
		return entidadesPersona;
	}

	/**
	 * @param entidadesPersona the entidadesPersona to set
	 */
	public void setEntidadesPersona(List<EntidadExt> entidadesPersona) {
		this.entidadesPersona = entidadesPersona;
	}

	public boolean isEstadoTablaNome() {
		return estadoTablaNome;
	}

	public void setEstadoTablaNome(boolean estadoTablaNome) {
		this.estadoTablaNome = estadoTablaNome;
	}
	
	public void cargarListaEntidades(){
		filtro.setCodUsuarioSesion(new BigDecimal(this.getUsuarioSesion().getId()));
		filtro.setCodEstadoEntidad(new BigDecimal(TipoParametro.ENTIDAD_INACTIVA.getValue()));
		entidadesPersona = ComunicacionServiciosEnt.getEntidadesPersonaFiltro(filtro);
		if(entidadesPersona.size() <= 1)
			this.estadoTablaNome = false;
	}
	
	public void activarIncrementoEscala(){
		this.formIncrementarEscala = true;
		this.formEscalaCreacion = false;
	}
	
	public void guardarIncrementoEscala() {
		try {
			if(this.incrementoSeleccionado.getValor() == null) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Debe ingresar un valor para el incremento salarial");
				return;
			}
			if(this.incrementoSeleccionado.getEsincrementoPorcentual().equalsIgnoreCase("1") && this.incrementoSeleccionado.getValor()>99) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, "El valor porcentual es mayor a 100");
				return;
			}
			this.incrementoSeleccionado.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
			this.incrementoSeleccionado.setAudFechaActualizacion(new Date());
			this.incrementoSeleccionado.setAudCodRol((int)(this.getUsuarioSesion().getCodRol()));
			this.incrementoSeleccionado.setAudAccion(785);
			this.incrementoSeleccionado.setFlgActivo((short) 1);
			IncrementoSalarial incremento = ComunicacionServiciosEnt.guardarIncremento(this.incrementoSeleccionado);
			if(incremento == null) {
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INCREMENTO_SALARIAL_ERROR_GUARDAR_SERVICIO);
				return;
			}
			if(incremento.isError())
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INCREMENTO_SALARIAL_ERROR_GUARDAR + (incremento.getMensajeTecnico() == null ? "" : incremento.getMensajeTecnico()));
			else {
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "Se ha guardado el incremento.");
				this.formIncrementarEscala = false;
			}
		}catch(Exception ex){
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error guardando el incremento salarial");
		}
	}
	
	public List<SelectItem> obtenerNumerosNorma(Long tipoNorma, Date fechaNorma) {
		List<SelectItem> list = new ArrayList<>();
		Norma norma = new Norma();
		if (tipoNorma != null) {
			norma.setCodTipoNorma(tipoNorma.intValue());
		}
		norma.setFechaNorma(fechaNorma);

		List<Norma> listNorma = ComunicacionServiciosEnt.getFiltroNorma(norma);
		try {
			if (!listNorma.isEmpty()) {
				for (Norma aux : listNorma) {
					list.add(new SelectItem(aux.getNumeroNorma(), aux.getNumeroNorma()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}
		return list;
	}
	
	public Long getCodNorma() {
		return codNorma;
	}

	public void setCodNorma(Long codNorma) {
		this.codNorma = codNorma;
	}
	
}
