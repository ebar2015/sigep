package co.gov.dafp.sigep2.mbean.vinculacion;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entities.Contrato;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.UsuarioEntidad;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.ContratoExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.mbean.ext.EstructuraExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.SituacionAdminVinculacionExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioRolEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosCO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.enums.TipoDocumentoIdentidadEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 * @author Milciades Vargas, Maria Alejandra Colorado
 * @version 1.0
 * @Class PersonaVinculacionBean.java
 * @Proyect DAFP
 * @Company ADA S.A.
 * @Module Vinculacion/Desvinculacion de usuarios Fecha: 26/06/2018
 */
@Named
@ViewScoped
@ManagedBean
public class PersonaVinculacionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -131671368173191781L;

	private static final int TIEMPO_DIAS_DEFECTO 	= 30;
	private static final int MAYOR_EDAD 			= 18;
	private String TIPO_DOC_PASAPORTE 				= TipoDocumentoIdentidadEnum.PA.getDescripcion();

	private VinculacionExt 	vinculacion;// variable para llenar los datos de vinculacion
	private VinculacionExt 	ultimaVinDesv; // Variable para llenar la ultima vinculacion o desvinculacion de una persona
	private PersonaExt 		personaFilter;// variable para almancenar la informacion del usuario a buscar
	private PersonaExt 		persona;// variable para almacenar la informacion de un usuario
	private PersonaExt 		infoPersona; // variable que trae informacion completa de la persona
	private EntidadPlantaDetalleExt cargo; // Variable que trae informacion de Cargo
	private EntidadPlantaDetalleExt cargoRepresentante;

	private List<PersonaExt> 	 listPersona;// variable para listar las personas que se encuentren listar para vincular
	private List<VinculacionExt> listVinculacion;// variable para listar las vinculaciones que tiene una persona
	private List<PersonaExt> 	 listRolesPersona; // Variable para listar los roles de la persona
	private List<VinculacionExt> listVinculacionDesvinc;// variable para listar las vinculaciones y desvinculaciones de
														// una persona

	private Integer tiempoAprobacion; // Variable que almacena el tiempo de aprobacion que puede tener una HV
	private Integer dias;

	private Boolean visible 		 = false;// variable para visualizar panel en el formulario
	private Boolean flgTipoPlanta 	 = true; // valor para mostrar el tipo de dependencia a escoger
	private Boolean flgTitularidad 	 = false;// variable para habilitar el campo de titularidad
	private Boolean blnPasaporte 	 = false; // Variable para verificar si lo que se selecciono en TI fue pasaporte
	private Boolean blnCargoExpuesto = false;
	private Boolean blnExpuestoPoliticamente = false;
	private Boolean flgPersonaExpuestaPublicamente = false;// variable para habilitar el campo de Persona Expuesta
	private Boolean visiblePanelVinc;// variable para visualizar panel en el formulario
	
	private String strTextoEntidadAVincular;// variable para agregar el nombre de la entidad que se va a vincular
	private String strTextoVinDesv; // Variable para agregar el tipo de accion (Vincular o desvincular) según el caso
	private String strFechaVinDes; // variable para agregar la fecha (finalizacion o de posesion) segun sea el caso
	private String strMensajeDeshacer; // Variable que contiene mensaje de la vinculacion/Desvinculacion
	private String strMensajeOtraVinculacion; // Variable que contiene mensaje de existencia de Vinculacion
	private String strMensajeVinculacionUTL; // Variable que contiene el mensaje de vinculacion a UTL
	private Long   lngValorEntidad;// valor de la entidad seleccionada o de session por default

	ExternalContext externalContext;// Variable para acceder al context y obtener y setear vbles

	@PostConstruct
	public void init() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_VINCULACIONES);			
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String recursoId = paramMap.get("recursoId");
		String strIsLinkMenu = paramMap.get("isMenu");
		this.validaPermisosAcciones(recursoId);
		persona 	= new PersonaExt();
		vinculacion = new VinculacionExt();
		visible 	= false;
		visiblePanelVinc = false;
		lngValorEntidad = getEntidadUsuario().getId();
		try {
			Parametrica parTiempoAprobacion = ComunicacionServiciosVin.getParametricaporId(new BigDecimal(TipoParametro.TIEMPO_APROBACION_HV.getValue()));
			tiempoAprobacion = parTiempoAprobacion.getValorParametro() != null ? Integer.parseInt(parTiempoAprobacion.getValorParametro()) : TIEMPO_DIAS_DEFECTO;
		} catch (Exception e) {
			tiempoAprobacion = TIEMPO_DIAS_DEFECTO;
		}
		try {
			Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.TIEMPO_DIAS_DESVINCULADO.getValue()));
			dias = parametrica.getValorParametro() != null ? Integer.parseInt(parametrica.getValorParametro()) : TIEMPO_DIAS_DEFECTO;
		} catch (Exception e) {
			dias = TIEMPO_DIAS_DEFECTO;
		}
		
		try {
			Boolean validRold = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_TALENTO_HUMANO,
					RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.JEFE_CONTROL_INTERNO,
					RolDTO.ADMINISTRADOR_FUNCIONAL);
			if (!validRold) {
				this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init() usuarioTieneRolAsignado", e);
		} catch (IOException e) {
			logger.error("void init() finalizarConversacion", e);
		}
		validarInicial();
		this.setStrTextoEntidadAVincular(MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_ENTIDAD_VINCULAR, getLocale())
				.replace("%nombreEntidad%", this.getEntidadUsuario().getNombreEntidad()));

		/*
		 * Cuando viene de desvincular se toma la persona gestionada de contexto y se
		 * reestablece la lista inciial
		 */
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		if (externalContext.getSessionMap() != null && externalContext.getSessionMap().get("personaFilter") != null
				&& !"1".equals(strIsLinkMenu)) {
			personaFilter = (PersonaExt) externalContext.getSessionMap().get("personaFilter");
			if (personaFilter != null) {
				validateForm();
			} else {
				personaFilter = new PersonaExt();
			}
		} else {
			personaFilter = new PersonaExt();
		}
	}

	/**
	 * Metodo constructor de la clase
	 */
	public PersonaVinculacionBean() {
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		personaFilter = new PersonaExt();
	}

	/**
	 * Metodo de validaciones iniciales de vinculacion
	 */
	public void validarInicial() {
		boolean valid = false;
		valid = this.validarEntidadActiva();
		if (!valid) {
			return;
		}
	}

	/**
	 * Metodo que valida si la entidad a la que van a realizar nuevas vinculaciones
	 * si se encuentra activa
	 */
	private boolean validarEntidadActiva() {
		boolean blnActiva 	= true;
		Entidad entidad 	= new Entidad();
		entidad.setCodEntidad(new BigDecimal(lngValorEntidad));
		entidad.setCodEstadoEntidad(new BigDecimal(TipoParametro.ENTIDAD_ACTIVA.getValue()));
		List<Entidad> activa = ComunicacionServiciosEnt.getEntidadesFiltro(entidad);
		if (activa.isEmpty()) {
			blnActiva = false;
			String mensajeInactivo = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_ENTIDAD_INACTIVA, getLocale())
					.replace("%entidad%", this.getEntidadUsuario().getNombreEntidad());
			mostrarMensajeBotonAccion(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					mensajeInactivo,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR, getLocale()),
					"window.location.assign('../index.xhtml?faces-redirect=true')");
		}
		return blnActiva;
	}

	/**
	 * Metodo que almacena una vinculacion
	 */
	public void guardarVinculacion() {
		this.validarUsuarioVinculadoAEntidad();
		if (validarFechaPosesion()) {
			return;
		}
		vinculacion.setAudFechaActualizacion(DateUtils.getFechaSistema());
		vinculacion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		vinculacion.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		vinculacion.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
		vinculacion.setFlgActivo((short) 1);

		if ((!flgPersonaExpuestaPublicamente && vinculacion.getPersonaExpuestaPublicamente() == 1)
				|| blnCargoExpuesto) {
			vinculacion.setFlgPEP(vinculacion.getPersonaExpuestaPublicamente());
		}
		
		if (cargo.getCodTipoPlanta() != null && cargo.getCodTipoPlanta().longValue() == TipoParametro.PLANTA_ESTRUCTURAL.getValue() 
				&& cargo.getCodDependenciaEntidad() == null) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_CARGO_ESTRUCTURAL_SIN_DEPENDENCIA);
			return;
		}
		
		boolean valid = ComunicacionServiciosVin.setVinculacion(vinculacion);

		if (valid) {
			this.eliminarRol();
			if (vinculacion.getPersonaExpuestaPublicamente() == 1 || blnExpuestoPoliticamente) {
				this.guardarDatosHV(vinculacion.getCodPersona(), vinculacion.getPersonaExpuestaPublicamente(),
						DateUtils.getFechaSistema());
			}
			
			guardarRepresentanteLegal(vinculacion);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_MENSAJE_VINCULACION);
			this.buscarFilter();
			vinculacion = new VinculacionExt();
			persona = new PersonaExt();

		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
			vinculacion = new VinculacionExt();
			persona 	= new PersonaExt();
		}
		visiblePanelVinc 		 = false;
		blnExpuestoPoliticamente = false;
	}

	

	/**
	 * Metodo que valida la fecha de posesion de una vinculacion 
	 * 1. Valida  que la fecha de posesion de la persona sea mayor a la fecha de nacimiento de la persona.
	 * 2. Valida que la persona a vincular no sea menor de edad 
	 * 3. Valida si la persona va a ser creada en la fecha en la que la entdad ya existia
	 */
	public boolean validarFechaPosesion() {
		if (validarFechaNacimiento())
			return true;
		if (validarServidorMayorEdad())
			return true;
		if (validarFechaCreacionEntidad())
			return true;
		return false;
	}

	/**
	 * Valida que la fecha de posesion de la persona sea mayor a la fecha de
	 * nacimiento de la persona.
	 * 
	 * @return fechaIncorrecta
	 */
	public boolean validarFechaNacimiento() {
		boolean blnFechaIncorrecta = false;
		if (vinculacion.getFechaPosesion() != null && persona.getFechaNacimiento() != null) {
			LocalDate fechaPosesion = vinculacion.getFechaPosesion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fechaNacimiento = persona.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (fechaPosesion.isBefore(fechaNacimiento)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_VINCULACION_FECHA_POSESION_MENOR_NACIMIENTO);
				blnFechaIncorrecta = true;
			}
		}
		return blnFechaIncorrecta;
	}

	/**
	 * Valida que la persona a vincular, sea mayor de edad
	 * 
	 * @return blnMenorEdad
	 */
	public boolean validarServidorMayorEdad() {
		boolean blnMenorEdad = false;
		if (persona.getFechaNacimiento() != null) {
			LocalDate hoy = LocalDate.now();
			LocalDate nacimiento = persona.getFechaNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			long edad = ChronoUnit.YEARS.between(nacimiento, hoy);
			if (edad < MAYOR_EDAD) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_VINCULACION_MENOR_EDAD);
				blnMenorEdad = true;
			}
		}
		return blnMenorEdad;
	}

	/**
	 * Valida que la entidad se haya creado antes de la fecha de posesion de la
	 * vinculacion a realizar
	 * @return fechaCreacionIncorrecta
	 */
	public boolean validarFechaCreacionEntidad() {
		boolean blnFechaCreacionIncorrecta = false;
		Entidad entidad = ComunicacionServiciosEnt.getEntidadPorId(lngValorEntidad);
		if (vinculacion.getFechaPosesion() != null && entidad.getCodNorma() != null) {
			Norma filtroNorma = new Norma();
			filtroNorma.setCodNorma(entidad.getCodNorma().longValue());
			List<Norma> lstNorma = ComunicacionServiciosEnt.getFiltroNorma(filtroNorma);
			if (!lstNorma.isEmpty()) {
				LocalDate fechaNorma = lstNorma.get(0).getFechaNorma().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate fechaPosesion = vinculacion.getFechaPosesion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				if (fechaNorma.isAfter(fechaPosesion)) {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_VINCULACION_FECHA_POSESION_MENOR_ENTIDAD);
					blnFechaCreacionIncorrecta = true;
				}
			}
		} else {
			if (vinculacion.getFechaPosesion() != null && entidad.getFechaInicio() != null) {
				LocalDate fechaPosesion = vinculacion.getFechaPosesion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate fechaEntidad = entidad.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				if (fechaEntidad.isAfter(fechaPosesion)) {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_VINCULACION_FECHA_POSESION_MENOR_ENTIDAD);
					blnFechaCreacionIncorrecta = true;
				}
			}
		}
		return blnFechaCreacionIncorrecta;
	}
	
	/**
	 * Metodo que almacena el representante legal en el componente de entidad.
	 * Para esto, es necesario que el cargo al cual se le vinculo la persona tenga el flg_representante_legal = 1.
	 */
	public void guardarRepresentanteLegal(VinculacionExt vinculacion) {
	    long usuarioSesion = this.getUsuarioSesion().getId();
        long rol = this.getRolAuditoria().getId();
        long tipoVin = TipoParametro.TIPO_VINCULACION_SERV_PUBLICO.getValue();
		if(validarCargoRepresentanteLegal(vinculacion)){
			String strNombrePersona = persona.getNombreUsuario();
			Entidad entidadN = new Entidad();
			entidadN.setCodEntidad(new BigDecimal(cargoRepresentante.getCodEntidad()));
	        entidadN.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
	        entidadN.setAudCodUsuario(BigDecimal.valueOf(getUsuarioSesion().getId()));
	        entidadN.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
	        entidadN.setAudFechaActualizacion(Calendar.getInstance().getTime());
	        entidadN.setCodRepresentanteLegal(strNombrePersona);
	        Entidad ent = ComunicacionServiciosEnt.setEntidadExt(entidadN,tipoVin,usuarioSesion,rol);
		}		
	}
	

	
	/**
	 * Metodo que valida si el cargo al cual fue asociado la persona, corresponde a un cargo de tipo representante legal
	 * */
	public boolean validarCargoRepresentanteLegal(VinculacionExt vinculacion) {
		boolean blnCargoRepresentante = false;
		cargoRepresentante = new EntidadPlantaDetalleExt();
		if(vinculacion !=null) {
			if (vinculacion.getCodEntidadPlantaDetalle() != null) {
				cargoRepresentante = ComunicacionServiciosVin.getEntidadPlantaDetalleId(vinculacion.getCodEntidadPlantaDetalle().longValue());
			}
			if (cargoRepresentante != null && cargoRepresentante.getCodEntidadPlantaDetalle()!= null 
					&& cargoRepresentante.getFlgRepresentanteLegal() != null && cargoRepresentante.getFlgRepresentanteLegal() == 1) {
				blnCargoRepresentante = true;
			}
		}
		return blnCargoRepresentante;
	}
	
	
	/**
	 * Metodo que guarda los registros en hoja de vida correspondientes a:
	 * @param expuesto.
	 *            Variable que guarda en FLG_PERSONA_EXPUESTA_POLITICAMENTE
	 * @param fecha
	 *            . Variable que guarda en FECHA_MARCACION_PEP Este metodo es
	 *            llamado desde guardarVinculacion() o deshacerVinculacion.
	 */
	public void guardarDatosHV(BigDecimal codPersona, short expuesto, Date fecha) {
		PersonaExt objetoPersona = new PersonaExt();
		objetoPersona.setCodPersona(codPersona);
		objetoPersona.setExpuestoPoliticamente(expuesto);
		objetoPersona.setFechaMarcacionPep(fecha);
		objetoPersona.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		objetoPersona.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		objetoPersona.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
		objetoPersona.setAudFechaActualizacion(DateUtils.getFechaSistema());
		/* Valida si la persona marco que era expuesta politicamente desde la opcion de HV */
		if (objetoPersona.getExpuestoPoliticamente() && !flgPersonaExpuestaPublicamente)
			objetoPersona.setMarcacionPepVinc((short) 1);
		else
			objetoPersona.setMarcacionPepVinc((short) 0);
		ComunicacionServiciosHV.updatePersona(objetoPersona);
	}

	/**
	 * Metodo que elimina los roles de una persona
	 */
	public void eliminarRol() {
		UsuarioRolEntidadExt ure = new UsuarioRolEntidadExt();
		UsuarioExt usuario = ComunicacionServiciosHV.getusuarioext(persona.getCodPersona().longValue(),
				lngValorEntidad);
		if (usuario != null) {
			ure.setCodUsuario(usuario.getCodUsuario().intValue());
			ure.setCodRol(new BigDecimal(4));
			ure.setAudFechaActualizacion(DateUtils.getFechaSistema());
			ure.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
			ure.setAudAccion(BigDecimal.valueOf(TipoAccionEnum.UPDATE.getIdAccion()));
			ure.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
			ure.setFlgActivo((short) 1);
			ure.setFlgEstado(new BigDecimal(1));
			Boolean valid = ComunicacionServiciosVin.desactivarRolContratista(ure);
			if (valid) {
				cambiarTipoVinculacion(usuario);
			}
		}
	}

	/**
	 * Metodo que cambia el tipo de vinculacion a SERVIDOR PUBLICO cuando este
	 * inialmente estaba vinculado como CONTRATISTA
	 * @param usuarioCon
	 */
	public void cambiarTipoVinculacion(UsuarioExt usuarioCon) {
		UsuarioEntidad objtUsuario = new UsuarioEntidad();
		objtUsuario.setAudFechaActualizacion(DateUtils.getFechaSistema());
		objtUsuario.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		objtUsuario.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		objtUsuario.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
		objtUsuario.setCodTipoVinculacion(110);
		objtUsuario.setCodUsuario(usuarioCon.getCodUsuario());
		objtUsuario.setCodEntidad(new BigDecimal(lngValorEntidad));
		Boolean valid = ComunicacionServiciosVin.setUsuarioEntidadPorUsuario(objtUsuario);
	}

	/**
	 * Metodo para las validaciones del usuario antes de vincular
	 */
	public String formVinculacion() {
		visiblePanelVinc = false;

		if (validacionPersonaRolActivoEntidad()) {
			return null;
		}

		if (validacionPersonaVinculadaUTL()) {
			return null;
		}
		
		if(validarPersonaViva()) {
			return null;
		}
		
		if (validarExistenciaPlantaNoUTL()) {
			return null;
		}

		if (validacionPersonaSinAprobar()) {
			return null;
		}

		if (validacionCambiosHv()) {
			return null;
		}

		if (validacionContratoConEntidad()) {
			return null;
		}

		if (validacionContratoClausulaExclusividad()) {
			return null;
		}

		if (validacionUsuarioVinculadoMismaEntidad()) {
			return null;
		}

		if (validacionEntidadSinPlanta()) {
			return null;
		}

		if (obtenerEstructura()) {
			return null;
		}
		
		if (validacionUsuarioVinculado()) {
			return null;
		}
		
		this.showFormVinculacion();
		return null;
	}

	/**
	 * Metodo que valida si la persona a vincular, anteriormente presento una desvinculacion a como causal de muerte
	 * @return blnPersonaMuerta
	 */
	public boolean validarPersonaViva() {
		boolean blnPersonaMuerta = false;
		VinculacionExt objeto = new VinculacionExt();
		objeto.setCodPersona(persona.getCodPersona());
		objeto.setCodCausalDesvinculacion(new BigDecimal(TipoParametro.DESVINCULACION_X_MUERTE.getValue()));
		List<VinculacionExt> listVinculacion = ComunicacionServiciosVin.getMostrarVinculaciones(objeto);
		if (!listVinculacion.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_VINCULACION_PERSONA_MUERTA);
			blnPersonaMuerta = true;
		}
		return blnPersonaMuerta;
	}
	
	/**
	 * Validar entidad con estructura y dependencias
	 */
	private boolean obtenerEstructura() {
		boolean valid = false;
		EstructuraExt fil = new EstructuraExt();
		fil.setCodEntidad(new BigDecimal(this.lngValorEntidad));
		fil.setFlgDependencia((short) 1);
		List<EstructuraExt> lista = ComunicacionServiciosEnt.obtenerEstructuraFiltro(fil);
		if (lista.isEmpty()) {
			valid = true;
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_VINCULAR_ENTIDAD_SIN_ESTRUCTURA);
		}
		return valid;
	}

	/**
	 * Metodo para realizar la busqueda de los usuarios a realizar una vinculacion
	 * El metodo envia al servicio getPersonaporFiltroVinculacion los siguientes
	 * parametros: CAUSAL_DESVINCULACION_DESHACER, parametrica utilizada para que el
	 * servicio retorne el total de vin/desv de una persona que no tiene este causal
	 * DIAS_DESHACER_VINCULACION, parametrica que dice el numero de dias que una
	 * persona tiene para deshacer una vin/desv cod_entidad, el codigo de la entidad
	 * a la cual se piensan vincular las personas
	 */
	public void buscarFilter() {
		personaFilter.setCodEntidad(BigDecimal.valueOf(getEntidadUsuario().getId()));
		personaFilter.setCausalDesv(TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue());
		personaFilter.setParDiasDesv(TipoParametro.DIAS_DESHACER_VINCULACION.getValue());
		listPersona = ComunicacionServiciosVin.getPersonaporFiltroVinculacion(personaFilter);
		if (listPersona.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_MENSAJE_BUSQUEDA);
		}
	}

	/**
	 * Metodo que valida los vacantes, la naturaleza del empleo, el tipo de
	 * nombramiento, la dependencia de un cargo y si desde la opcion de planta se
	 * marco que el cargo es expuesto publicamente.
	 */
	public void validarCargo() {
		vinculacion.setPersonaExpuestaPublicamente((short) 0);
		flgPersonaExpuestaPublicamente = false;
		blnExpuestoPoliticamente = false;
		blnCargoExpuesto = false;
		buscarPersonaExpuestaPoliticamenteHV();
		if (vinculacion.getCodEntidadPlantaDetalle() != null) {
			cargo = ComunicacionServiciosVin.getEntidadPlantaDetalleId(vinculacion.getCodEntidadPlantaDetalle().longValue());
		}
		if (cargo != null) {
			boolean valid = this.validarVacantes(DateUtils.getFechaSistema());
			if (valid) {
				vinculacion.setNombreNaturalezaEmpleo(
						cargo.getNombreNaturalezaEmpleo() != null ? cargo.getNombreNaturalezaEmpleo() : "");
				vinculacion.setCodNaturalezaEmpleo(
						cargo.getCodNaturalezaEmpleo() != null ? cargo.getCodNaturalezaEmpleo().intValue() : 0);
				validarDependencia();
				// Condicional que valida si el cargo es expuesto publicamente.
				if (cargo.getFlgCargoExpuestoPublicamente() != null && cargo.getFlgCargoExpuestoPublicamente() == 1) {
					vinculacion.setPersonaExpuestaPublicamente((short) 1);
					flgPersonaExpuestaPublicamente = true;
					blnExpuestoPoliticamente = true;
					blnCargoExpuesto = true;
				}
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_INFO_CARGO_SIN_VACANTES);
				vinculacion.setVacantes("0");
				vinculacion.setCodEntidadPlantaDetalle(null);
				vinculacion.setNombreNaturalezaEmpleo(null);
				vinculacion.setCodNaturalezaEmpleo(null);
				vinculacion.setCodTipoNombramiento(null);
				vinculacion.setStrNombreDependencia(null);
				vinculacion.setCodDependenciaEntidad(null);
				vinculacion.setFlgTitularidadCargo((short) 0);
				flgTitularidad = false;
			}
		}

	}

	/**
	 * Metodo que valida el tipo de planta al que pertenece el cargo
	 */
	public void validarDependencia() {
		flgTipoPlanta = false;
		if (cargo.getCodTipoPlanta() != null && cargo.getCodTipoPlanta().longValue() == TipoParametro.PLANTA_ESTRUCTURAL.getValue()) {
			flgTipoPlanta = true;
			if (cargo.getCodDependenciaEntidad() != null) {
				vinculacion.setStrNombreDependencia(cargo.getNombreDependencia());
				vinculacion.setCodDependenciaEntidad(new BigDecimal(cargo.getCodDependenciaEntidad()));
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_INFO_CARGO_ESTRUCTURAL_SIN_DEPENDENCIA);
			}
			
		}
	}

	/**
	 * Metodo para validar los vacantes existentes para el cargo.
	 * Este metodo se encarga de:
	 * 1. Validar que la persona no se asigne nuevamente el mismo cargo.
	 * 2. Obtener la cantidad de SA que aplican para el cargo seleccionado: Este metodo llama
	 * 		a una funcion llamada F_OBTENER_VACANCIAS_CARGOS que se encarga de realizar las validaciones
	 * 		de vacancia temporal segun las especificaciones dadas en el caso de uso de SA.
	 * 3. Obtener las vinculaciones que se encuentran activas para ese cargo
	 * A partir de esto, se calcula el numero de vacantes que tiene disponible el cargo.
	 * @return valid
	 */
	public boolean validarVacantes(Date fecha) {
		long vacantes 				= 0;
		long ocupados 				= 0;
		long cantidadSAConVacancia 	= 0;
		long cantidadCargoEncargo 	= 0;
		long cantidadVinculaciones	= 0;
		boolean valid 				= false;
		vinculacion.setVacantes("0");
		verificarCargoMismaPersona();
		cantidadSAConVacancia 	= validarSituacionesAdministrativasPorCargo();
		cantidadCargoEncargo 	= obtenerCantidadCargoEnEncargo();
		cantidadVinculaciones 	= validarVinculacionSinSA();
		ocupados = cantidadVinculaciones +  cantidadCargoEncargo;
		long cantidadCargos = cargo.getCantidadCargoApropiacionPresupuestal() != null ? cargo.getCantidadCargoApropiacionPresupuestal() : 0;
		vacantes = cantidadCargos - ocupados;
		
		if (vacantes > 0 ) {
			valid = true;
			vinculacion.setVacantes(String.valueOf(vacantes));
		}
		return valid;
	}

	/**
	 * Metodo que obtiene la cantidad de vacantes que tiene un cargo
	 * */
	public long obtenerCantidadVinculacionesPorCargo() {
		long cantidadVinculaciones = 0;
		VinculacionExt vinculacionConsulta = new VinculacionExt();
		vinculacionConsulta.setCodEntidadPlantaDetalle(BigDecimal.valueOf(cargo.getCodEntidadPlantaDetalle()));
		vinculacionConsulta.setFlgActivo((short) 1);
		List<VinculacionExt> vinculacionTotal = ComunicacionServiciosVin.getVinculacionFilter(vinculacionConsulta);
		cantidadVinculaciones = vinculacionTotal.size();
		return cantidadVinculaciones;
	}
		
	/**
	 * Metodo que valida las Situaciones administrativas que se encuentran asociadas a las vinculaciones
	 *  del cargo y que generan vacancia. Es decir, existe un cargo que tiene personas vinculadas, y esas
	 *  vinculaciones presentan situaciones que hacen que el cargo quede libre
	 * Es necesario que se envie al servicio el codigo de entidadPlantaDetalle
	 */
	public long validarSituacionesAdministrativasPorCargo() {
		long cantidadSA = 0;
		EntidadPlantaDetalleExt objEntidadPlantaDetalle = new EntidadPlantaDetalleExt();
		objEntidadPlantaDetalle.setCodEntidadPlantaDetalle(cargo.getCodEntidadPlantaDetalle());
		EntidadPlantaDetalleExt situacionAdminTotal = ComunicacionServiciosVin.getVacanciaPorCargo(objEntidadPlantaDetalle);
		if (!situacionAdminTotal.isError() && situacionAdminTotal.getVacantesCargo() != null) {
			cantidadSA = situacionAdminTotal.getVacantesCargo();
		}
		return cantidadSA;
	}
	
	/**
	 * Metodo que valida si el cargo seleccionado se encuentra en un encargo activo
	 * Es necesario que se envie el codigo de CodEntidadPlanta_detalle y el flg_activo
	 **/
	public long obtenerCantidadCargoEnEncargo() {
		long cantidadCargoEncargo = 0;
		SituacionAdminVinculacionExt objSituacionAdmin = new SituacionAdminVinculacionExt();
		objSituacionAdmin.setCodEntidadPlantaDetalleEncargo(cargo.getCodEntidadPlantaDetalle());
		objSituacionAdmin.setFlgActivo((short) 1);
		List<SituacionAdminVinculacionExt> situacionAdminTotal = ComunicacionServiciosVin.getCargoEnEncargo(objSituacionAdmin);
		cantidadCargoEncargo = situacionAdminTotal.size();
		return cantidadCargoEncargo;
	}
	
	/**
	 * Metodo que valida las vinculaciones que actualmente tiene ese cargo
	 * pero que no cuenta con SituacionesAdministrativas o las que tiene
	 * tienen fecha mayor a la fecha actual del sistema o la fecha de posesion.
	 */
	public long validarVinculacionSinSA() {
		long cantidadVinSinSA = 0;
		VinculacionExt vinculacionConsulta = new VinculacionExt();
		vinculacionConsulta.setCodEntidadPlantaDetalle(BigDecimal.valueOf(cargo.getCodEntidadPlantaDetalle()));
		List<VinculacionExt> vinculacionSinSAActivas = ComunicacionServiciosVin.getVinculacionSinSAXProcesar(vinculacionConsulta);
		if(!vinculacionSinSAActivas.isEmpty()) {
			cantidadVinSinSA = vinculacionSinSAActivas.size();
		}
		return cantidadVinSinSA;
	}

	/**
	 * Metodo que verifica si una persona esta seleccionando un cargo que aun sigue
	 * activo. Este se encuentra activo debido a que no se ha realizado la
	 * desvinculacion manual o porque la fecha en que el proceso la desvincula no se
	 * ha corrido
	 */
	public void verificarCargoMismaPersona() {
		VinculacionExt vinculacionConsulta = new VinculacionExt();
		vinculacionConsulta.setCodEntidadPlantaDetalle(BigDecimal.valueOf(cargo.getCodEntidadPlantaDetalle()));
		vinculacionConsulta.setFlgActivo((short) 1);
		vinculacionConsulta.setCodPersona(persona.getCodPersona());
		List<VinculacionExt> vinculacionIgual = ComunicacionServiciosVin.getVinculacionFilter(vinculacionConsulta);
		if (!vinculacionIgual.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_CARGO_YA_ASIGNADO);
			vinculacion.setCodEntidadPlantaDetalle(null);
			vinculacion.setNombreNaturalezaEmpleo(null);
			vinculacion.setCodNaturalezaEmpleo(null);
			vinculacion.setCodTipoNombramiento(null);
			vinculacion.setStrNombreDependencia(null);
			vinculacion.setCodDependenciaEntidad(null);
			vinculacion.setFlgTitularidadCargo((short) 0);
			flgTitularidad = false;
		}
	}

	/**
	 * Metodo para validar la titularidad en cargo
	 */
	public void validarTitularidaEnCargo() {
		flgTitularidad = false;
		Integer[] valores = { TipoParametro.NOMBRAMIENTO_PERIODO_FIJO.getValue(),
							  TipoParametro.NOMBRAMIENTO_CARRERA_CARRERA_ADMIN.getValue(),
							  TipoParametro.NOMBRAMIENTO_CARRERA_CARRERA_DIPLOMATICA.getValue(),
							  TipoParametro.NOMBRAMIENTO_CARRERA_DOCENTES.getValue(),
							  TipoParametro.NOMBRAMIENTO_CARRERA_INSTRUCTORES.getValue(),
							  TipoParametro.NOMBRAMIENTO_CARRERA_RAMA_JUDICIAL.getValue(),
							  TipoParametro.NOMBRAMIENTO_CARRERA_MILITAR.getValue()
							 };
		if (vinculacion.getCodTipoNombramiento() != null
				&& Arrays.asList(valores).contains(vinculacion.getCodTipoNombramiento())) {
			vinculacion.setFlgTitularidadCargo((short) 1);
			flgTitularidad = true;
		}
	}

	/**
	 * @param cargoQuery
	 * Metodo para listar los cargos de una planta
	 */
	public List<EntidadPlantaDetalleExt> listarCargoPlanta(String cargoQuery) {
		if (cargoQuery == null) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Debe ingresar un cargo");
		}
		EntidadPlantaDetalleExt ecp = new EntidadPlantaDetalleExt();
		ecp.setCodEntidad(lngValorEntidad);
		ecp.setCodClasificacionPlanta(vinculacion.getCodClasificacionPlanta().longValue());
		ecp.setCodClasePlanta(vinculacion.getCodClasePlanta().longValue());
		ecp.setFlgActivo((short) 1);
		List<EntidadPlantaDetalleExt> listaCargoPlanta = ComunicacionServiciosVin.getCargoPlanta(ecp);
		List<EntidadPlantaDetalleExt> filtroCargosPlanta = new ArrayList<>();
		for (EntidadPlantaDetalleExt cargoPlanta : listaCargoPlanta) {
			if (cargoPlanta.getNombreCargo().toLowerCase().contains(cargoQuery)) {
				filtroCargosPlanta.add(cargoPlanta);
			}
		}
		return filtroCargosPlanta;
	}

	/**
	 * Metodo que verifica si una persona tiene una vinculacion o desvinculacion
	 */
	public void verificarVinculacionDesvinculacion() {
		visiblePanelVinc = false;
		listVinculacionDesvinc = new ArrayList<>();
		VinculacionExt vinculacionConsulta = new VinculacionExt();
		vinculacionConsulta.setCodPersona(persona.getCodPersona());
		vinculacionConsulta.setCodEntidad(lngValorEntidad);
		vinculacionConsulta.setParCausalDesv(TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue());
		listVinculacionDesvinc = ComunicacionServiciosVin.getUltimaVinculacionDesv(vinculacionConsulta);
		if (!listVinculacionDesvinc.isEmpty()) {
			ultimaVinDesv = listVinculacionDesvinc.get(0);
			if (ultimaVinDesv.getFechaFinalizacion() == null && ultimaVinDesv.getFlgActivo() == 1) {
				strTextoVinDesv = "Vinculación";
				strFechaVinDes = ultimaVinDesv.getStrfechaPosesion();
			} else {
				if (ultimaVinDesv.getFechaFinalizacion() != null && ultimaVinDesv.getFlgActivo() == 0) {
					strTextoVinDesv = "Desvinculación";
					strFechaVinDes = ultimaVinDesv.getStrFechaDesvinculacion();
				}
			}
			strMensajeDeshacer = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_DESHACER_VINCULACION, getLocale())
					.replace("%tipoAccion%", strTextoVinDesv)
					.replace("%nombreEntidad%", ultimaVinDesv.getNombreEntidad()).replace("%fecha%", strFechaVinDes);
			RequestContext.getCurrentInstance()
					.execute("$('#dialogDeshacerVinculacion').modal({backdrop: 'static', keyboard: false});");
		}
	}

	/**
	 * Metodo que deshace una vinculacion o desvinculacion
	 */
	public void deshacerVinculacionDesvinculacion() {
		int situacionAdm = 0;
		ultimaVinDesv.setAudFechaActualizacion(DateUtils.getFechaSistema());
		ultimaVinDesv.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		ultimaVinDesv.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));

		/**
		 * Condicional que valida si lo que se quiere deshacer es una vinculacion. Si el
		 * flg de Expuesto politicamente se activo fue en el formulario de vinculacion,
		 * este valor se debe de cambiar en la tabla de HV.
		 */
		if (ultimaVinDesv.getFechaFinalizacion() == null && ultimaVinDesv.getFlgActivo() == 1) {
			/** Deshacer la vinculacion hecha (La inactiva) */
			ultimaVinDesv.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
			ultimaVinDesv.setFlgActivo((short) 0);
			ultimaVinDesv.setFechaFinalizacion(DateUtils.getFechaSistema());
			Parametrica causalDesv = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue()));
			if (causalDesv != null) {
				ultimaVinDesv.setCodCausalDesvinculacion(BigDecimal.valueOf(TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue()));
			}
			
			
		} else {
			/**
			 * Deshace una desvinculacion (La activa nuevamente)
			 */
			if (ultimaVinDesv.getFechaFinalizacion() != null && ultimaVinDesv.getFlgActivo() == 0) {
				ultimaVinDesv.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
				ultimaVinDesv.setFlgActivo((short) 1);
				ultimaVinDesv.setCodCausalDesvinculacion(null);
				ultimaVinDesv.setFechaFinalizacion(null);
				situacionAdm = 1;
			}
		}
		boolean valid = ComunicacionServiciosVin.setVinculacion(ultimaVinDesv);

		if (valid) {
			String contenido = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_BORRADO_DESH_VINCULACION, getLocale())
					.replace("%accion%", strTextoVinDesv).replace("%fecha%", strFechaVinDes);
			ModificarSituacionAdminAsociadas(situacionAdm);
			/**
			 * Condicional que verifica si la persona marco que es expuesto politicamente
			 * dentro del formulario de vinculacion. Si es asi, como la opcion que desea
			 * hacer es revertir la vinculacion, el sistema llama al metodo guardarDatosHV
			 * donde envia ExpuestoPoliticamente = 0 y FechaTerminacion=null
			 */
			if (ultimaVinDesv.getFlgPEP() != null && ultimaVinDesv.getFlgPEP() == 1) {
				ultimaVinDesv.setFlgPEP((short) 0);
				this.guardarDatosHV(ultimaVinDesv.getCodPersona(), (short) 0, null);
			}
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, contenido);
			ultimaVinDesv = new VinculacionExt();
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
			ultimaVinDesv = new VinculacionExt();
		}
		
		
		personaFilter = new PersonaExt();
		listPersona = new ArrayList<PersonaExt>();
	}
	
	/**
	 * Metodo utilizado para activvar o desactivar las situaciones administrativas asociadas a una vinculación revertida*/
	public void ModificarSituacionAdminAsociadas(int situacionAdm) {
		SituacionAdminVinculacionExt objSA = new SituacionAdminVinculacionExt();
		objSA.setFlgActivo((short)situacionAdm);
		objSA.setCodVinculacion(ultimaVinDesv.getCodVinculacion().longValue());
		boolean valid = ComunicacionServiciosVin.setSituacionPorVinculacion(objSA);
	}
	
	/**
	 * Metodo para validar el formulario de filtro
	 * @return String
	 */
	public String validateForm() {
		borrarDatosConsulta();
		if (!visible && (personaFilter.getCodTipoIdentificacion() == null
				|| personaFilter.getNumeroIdentificacion().equals(""))) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_VALIDAR_FILTER_IDENTIFICACION);
			return null;
		}

		if (visible) {
			Integer valid = 0;

			if (!personaFilter.getPrimerNombre().equals("")) {
				valid++;
			} else if (personaFilter.getPrimerNombre().equals("")) {
				personaFilter.setPrimerNombre(null);
			}

			if (!personaFilter.getPrimerApellido().equals("")) {
				valid++;
			} else if (personaFilter.getPrimerApellido().equals("")) {
				personaFilter.setPrimerApellido(null);
			}

			if (!personaFilter.getSegundoNombre().equals("")) {
				valid++;
			} else if (personaFilter.getSegundoNombre().equals("")) {
				personaFilter.setSegundoNombre(null);
			}

			if (!personaFilter.getSegundoApellido().equals("")) {
				valid++;
			} else if (personaFilter.getSegundoApellido().equals("")) {
				personaFilter.setSegundoApellido(null);
			}

			if (valid < 2) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_VALIDAR_FILTER_NOMBRE_SERVIDOR);
				return null;
			}
		}
		this.visiblePanelVinc = false;
		this.buscarFilter();
		return null;
	}

	/**
	 * Metodo que permite borrar los datos de busqueda iniciales de filtro
	 */
	public void borrarDatosConsulta() {
		if (!this.visible) {
			personaFilter.setPrimerNombre(null);
			personaFilter.setPrimerApellido(null);
			personaFilter.setSegundoNombre(null);
			personaFilter.setSegundoApellido(null);
		} else {
			personaFilter.setCodTipoIdentificacion(null);
			personaFilter.setNumeroIdentificacion(null);
		}
	}

	/**
	 * Metodo para descargar una vinculacion oculta el formulario
	 */
	public void descartarVinculacion() {
		vinculacion = new VinculacionExt();
		cargo 		= new EntidadPlantaDetalleExt();
		visiblePanelVinc = false;
	}

	/**
	 * Metodo para validar si la entidad para realizar la vinculacion tiene una
	 * planta activa
	 */
	private boolean validacionEntidadSinPlanta() {
		boolean valid = false;
		EntidadPlantaExt objFilter = new EntidadPlantaExt();
		objFilter.setCodEntidad(lngValorEntidad);
		objFilter.setFlgActivo((short) 1);
		objFilter.setFlgGuardadoParcial((short) 0);

		List<EntidadPlantaExt> list = ComunicacionServiciosVin.getEntidadPlantaFilter(objFilter);
		if (list.isEmpty()) {
			valid = true;
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_VINCULAR_ENTIDAD_SIN_PLANTA);
		}
		return valid;
	}

	/**
	 * Metodo para validar si la entidad tiene plantas pero que no sean de tipo UTL
	 * para poder proceder con la vinculacion a un cargo especifico
	 */
	private boolean validarExistenciaPlantaNoUTL() {
		boolean valid = false;
		EntidadPlantaExt objFilter = new EntidadPlantaExt();
		objFilter.setCodEntidad(lngValorEntidad);
		objFilter.setCodClasificacionPlanta(Long.valueOf(TipoParametro.CLASIFICACION_PLANTA_UTL.getValue()));
		objFilter.setFlgActivo((short) 1);
		objFilter.setFlgGuardadoParcial((short) 0);

		List<EntidadPlantaExt> list = ComunicacionServiciosVin.getSelectEntidadPlantaExceptoUTL(objFilter);
		if (list.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_VINCULAR_ENTIDAD_SIN_PLANTA);
			valid = true;
		}
		return valid;
	}

	/**
	 * Metodo que verifica si una persona tiene una vinculacion activa en esa
	 * entidad o varias
	 * @return vinculadoPorCargo  retorna el valor de 'true' cuando tiene un cargo
	 */
	public boolean verificarVinculacionCargosVariasEntidades() {
		boolean vinculadoPorCargo = false;
		VinculacionExt objFilter = new VinculacionExt();
		objFilter.setCodPersona(persona.getCodPersona());
		listVinculacion = ComunicacionServiciosVin.getVinculacionFilter(objFilter);
		if (!listVinculacion.isEmpty()) {
			// Se eliminan los registros que se realizaron por desvinculacion automatica.
			listVinculacion.removeIf(vinc -> vinc.getCodCausalDesvinculacion() != null && vinc.getCodCausalDesvinculacion().intValue() == TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue());
			vinculadoPorCargo = !listVinculacion.isEmpty();
		}
		return vinculadoPorCargo;
	}

	/**
	 * Metodo que verifica si la persona se encuentra en una planta de tipo UTL UAN
	 */
	public boolean verificarVinculacionUTL() {
		boolean vinculadoPorUTL = false;
		HojaVidaExt objFilter = new HojaVidaExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short) 1);
		List<HojaVidaExt> list = ComunicacionServiciosHV.getHVPersonaAsociadaUTL(objFilter);
		if (!list.isEmpty()) {
			// Es porque tiene vinculaciones asociadas
			vinculadoPorUTL = true;
		}
		return vinculadoPorUTL;
	}

	/**
	 * Metodo para abrir la pagina de consultar vinculacion
	 */
	public void redireccionarConsultarVinculacion() {
		visiblePanelVinc = false;
		if (verificarVinculacionCargosVariasEntidades() || verificarVinculacionUTL()) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(
						"../vinculacion/consultaVinculacionDesvinculacion.xhtml?id=" + persona.getCodPersona());
			} catch (IOException e) {
				logger.error("El metodo no redirecciono - redireccionarConsultarVinculacion()", e);
			}
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_USUARIO_SIN_VINCULACION);
		}
	}

	/**
	 * Metodo para abrir la pagina de desvincular Persona
	 */
	public void redireccionarDesvincularPersona() {
		visiblePanelVinc = false;
		if (validarVinculacionCargoDesvincular() || validarVinculacionUTLDesvincular()) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(
						"../vinculacion/desvincularPorEntidadFuncionPublica.xhtml?id=" + persona.getCodPersona());
			} catch (IOException e) {
				logger.error("El metodo no redirecciono - redireccionarDesvincularPersona()", e);
			}
		} else {
			Entidad objEntidad = ComunicacionServiciosEnt.getEntidadPorId(lngValorEntidad);
			String nombreEntidad = null;
			if (objEntidad != null) {
				nombreEntidad = objEntidad.getNombreEntidad();
			}
			String contenido = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_USUARIO_SIN_VINCULACION_ENTIDAD,
							getLocale())
					.replace("%entidad%", nombreEntidad);
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, contenido);
		}
	}

	/**
	 * Metodo que valida si existe un vinculacion activa para la persona
	 * seleccionada en un cargo
	 */
	public boolean validarVinculacionCargoDesvincular() {
		boolean vinculacionactiva 	= false;
		VinculacionExt objFilter 	= new VinculacionExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setCodEntidad(lngValorEntidad);
		objFilter.setFlgActivo((short) 1);
		listVinculacion = ComunicacionServiciosVin.getMostrarVinculaciones(objFilter);
		if (!listVinculacion.isEmpty()) {
			vinculacionactiva = true;
		}
		return vinculacionactiva;
	}
	
	/**
	 * Metodo que valida si la persona presenta alguna vinculacion para UTL
	 * @return vinculacionUTLActiva
	 */
	public boolean validarVinculacionUTLDesvincular() {
		boolean vinculacionUTLActiva = false;
		HojaVidaExt objFilter = new HojaVidaExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short) 1);
		objFilter.setCodEntidad(getEntidadUsuario().getId());
		List<HojaVidaExt> list = ComunicacionServiciosHV.getHVPersonaAsociadaUTL(objFilter);
		if (!list.isEmpty()) {
			// Es porque tiene vinculaciones asociadas
			vinculacionUTLActiva = true;
		}
		return vinculacionUTLActiva;
	}

	/**
	 * Metodo para validar si la persona ya tiene una vinculacion con otra entidad
	 * @return valid
	 */
	private boolean validacionUsuarioVinculado() {
		boolean valid = false;
		VinculacionExt objFilter = new VinculacionExt();
		objFilter.setCodEntidad(lngValorEntidad);
		objFilter.setCodPersona(persona.getCodPersona());
		List<VinculacionExt> list = ComunicacionServiciosVin.getVinculacionDiferenteEntidad(objFilter);
		if (!list.isEmpty()) {
			strMensajeOtraVinculacion = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_USUARIO_EN_VINCULACION, getLocale())
					.replace("%nombreUsuario%", persona.getNombreUsuario()).replace("%tiempo%", dias.toString())
					.replace("%nombreEntidad%", list.get(0).getNombreEntidad());
			RequestContext.getCurrentInstance().execute("$('#dialogOtraVinculacion').modal({backdrop: 'static', keyboard: false});");
			valid = true;
		}
		return valid;
	}

	/**
	 * Metodo para validar si la persona ya tiene una vinculacion para esa entidad y
	 * es diferente a medico docente
	 * @return boolean valid
	 */
	private boolean validacionUsuarioVinculadoMismaEntidad() {
		boolean valid = false;
		VinculacionExt objFilter = new VinculacionExt();
		objFilter.setCodEntidad(lngValorEntidad);
		objFilter.setCodPersona(persona.getCodPersona());
		List<VinculacionExt> list = ComunicacionServiciosVin.getVinculacionMismaEntidad(objFilter);
		if (!list.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_USUARIO_VINC_IGUAL);
			valid = true;
		}
		return valid;
	}

	/**
	 * Metodo para validar si la persona ya tiene una vinculacion en una UTL
	 * @return boolean
	 */
	private boolean validacionPersonaVinculadaUTL() {
		boolean valid = false;
		HojaVidaExt objFilter = new HojaVidaExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short) 1);

		List<HojaVidaExt> list = ComunicacionServiciosHV.getHVPersonaAsociadaUTL(objFilter);
		if (!list.isEmpty()) {
			strMensajeVinculacionUTL = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_VINCULACION_PERSONA_UTL, getLocale())
					.replace("%nombrePlanta%", list.get(0).getNombreUtlUan());
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					strMensajeVinculacionUTL);
			valid = true;
		}
		return valid;
	}

	/**
	 * Metodo para mostrar el formulario de vinculacion
	 */
	public void showFormVinculacion() {
		vinculacion = new VinculacionExt();
		cargo 		= new EntidadPlantaDetalleExt();
		vinculacion.setCodPersona(persona.getCodPersona());
		vinculacion.setFlgMedicoDocente((short) 0);
		vinculacion.setFlgTitularidadCargo((short) 0);
		vinculacion.setPersonaExpuestaPublicamente((short) 0);
		flgPersonaExpuestaPublicamente 	= false;
		blnExpuestoPoliticamente 		= false;
		buscarPersonaExpuestaPoliticamenteHV();
		visiblePanelVinc = true;
	}

	/**
	 * Metodo que busca si la persona es expuesta politicamente en la hoja de Vida.
	 */
	public void buscarPersonaExpuestaPoliticamenteHV() {
		infoPersona = ComunicacionServiciosHV.getPersonaporIdExt(persona.getCodPersona().longValue());
		if (infoPersona != null && infoPersona.getExpuestoPoliticamente()) {
			vinculacion.setPersonaExpuestaPublicamente((short) 1);
			flgPersonaExpuestaPublicamente 	= true;
			blnExpuestoPoliticamente 		= true;
		}
	}

	/**
	 * Metodo para validar si la persona tiene un rol activo en la entidad
	 * @return boolean valid
	 */
	private boolean validacionPersonaRolActivoEntidad() {
		persona.setCodEntidad(BigDecimal.valueOf(lngValorEntidad));
		boolean valid = false;
		listRolesPersona = ComunicacionServiciosVin.getPersonaRolActivoEntidad(persona);
		if (listRolesPersona.isEmpty()) {
			Entidad objEntidad = ComunicacionServiciosEnt.getEntidadPorId(lngValorEntidad);
			String nombreEntidad;
			if (objEntidad != null) {
				nombreEntidad = objEntidad.getNombreEntidad();
			} else {
				nombreEntidad = "Sin Entidad";
			}
			String contenido = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_USUARIO_SIN_ROL_ACTIVO, getLocale())
					.replace("%nombreEntidad%", nombreEntidad);
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, contenido);
			valid = true;
		}
		return valid;
	}

	/**
	 * Metodo para validar si la persona tiene la hoja de vida aprobada
	 * @return boolean valid
	 */
	private boolean validacionPersonaSinAprobar() {
		boolean valid 			= false;
		HojaVidaExt objFilter 	= new HojaVidaExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setCodEntidad(lngValorEntidad);
		objFilter.setTiempoAprobacion(tiempoAprobacion);
		List<HojaVidaExt> list = ComunicacionServiciosVin.getPersonaHojaVidaAprobada(objFilter);
		if (list.isEmpty()) {
			String contenido = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_USUARIO_SIN_APROBAR, getLocale())
					.replace("%tiempo%", objFilter.getTiempoAprobacion().toString());

			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, contenido);
			valid = true;
		}
		return valid;
	}

	/**
	 * Metodo para validar si la hoja de vida de la persona a probar cuenta con
	 * datos modificados y que no estan aprobados.
	 * @return boolean valid
	 */
	private boolean validacionCambiosHv() {
		boolean valid 			= false;
		HojaVidaExt objFilter 	= new HojaVidaExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short) 1);
		objFilter.setFlgAprobado((short) 0);
		HojaVidaExt hojaVidaSinAprobacion = ComunicacionServiciosHV.getHojaVidaAprobacion(objFilter);
		if (hojaVidaSinAprobacion != null) {
			if (hojaVidaSinAprobacion.getTotalSinVerificar() != null
					&& hojaVidaSinAprobacion.getTotalSinVerificar() > 0) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_INFO_HOJA_SIN_APROBAR);
				valid = true;
			}
		}
		return valid;
	}

	/**
	 * Metodo para validar si la persona tiene un contrato en otra entidad
	 * @return boolean valid
	 */
	private boolean validacionContratoConEntidad() {
		boolean valid 		= false;
		Contrato objFilter 	= new Contrato();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short) 1);
		objFilter.setLimitEnd(0);
		objFilter.setLimitEnd(1000);
		List<ContratoExt> list = ComunicacionServiciosCO.getContratoFiltro(objFilter);
		if (!list.isEmpty()) {
			Entidad objEntidad = ComunicacionServiciosEnt.getEntidadPorId(list.get(0).getCodEntidad());
			String contenido = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_USUARIO_CONTRATO_CON_ENTIDAD, getLocale())
					.replace("%nombreEntidad%", objEntidad.getNombreEntidad());

			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, contenido);
			valid = true;
		}
		return valid;
	}

	/**
	 * Metodo para validar si la persona tiene un contrato con la misma entidad
	 * @return boolean valid
	 */
	private boolean validacionContratoClausulaExclusividad() {
		boolean valid 		= false;
		Contrato objFilter 	= new Contrato();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setDerechoExclusividad((short) 1);
		objFilter.setLimitEnd(1);
		List<ContratoExt> list = ComunicacionServiciosCO.getContratoFiltro(objFilter);
		if (!list.isEmpty()) {
			Entidad objEntidad = ComunicacionServiciosEnt.getEntidadPorId(lngValorEntidad);
			String contenido = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_USUARIO_CONTRATO_EXCLUSIVIDAD,
							getLocale())
					.replace("%nombreEntidad%", objEntidad.getNombreEntidad());
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, contenido);
			valid = true;
		}
		return valid;
	}

	/**
	 * Metodo que genera correo electronico a las direcciones regisradas en el
	 * sistema para los usuarios activos con roles Administrador Funcional, Jefe TH
	 * y Operador TH de la entidad donde se tiene vinculacion
	 */
	public void validarUsuarioVinculadoAEntidad() {
		VinculacionExt objFilter = new VinculacionExt();
		objFilter.setCodEntidad(lngValorEntidad);
		objFilter.setCodPersona(persona.getCodPersona());
		List<VinculacionExt> list = ComunicacionServiciosVin.getVinculacionDiferenteEntidad(objFilter);
		if (!list.isEmpty()) {
			Entidad nuevaEntidad = ComunicacionServiciosEnt.getEntidadPorId(lngValorEntidad);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, dias);
			Date fechaFinalizacion = calendar.getTime();

			for (VinculacionExt vinculacionExt : list) {
				String asunto = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_ASUNTO_DESVINCULACION, getLocale());
				String mensaje = MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_MENSAJE_DESVINCULACION, getLocale())
						.replace("%nombreEntidad%", vinculacionExt.getNombreEntidad())
						.replace("%nombreUsuario%", persona.getNombreUsuario()).replace("%tiempo%", dias.toString())
						.replace("%nombreNuevaEntidad%", nuevaEntidad.getNombreEntidad());

				List<PersonaExt> listPersonas = ComunicacionServiciosVin.getEmailPersonasDesvincular(vinculacionExt.getCodEntidad());
				for (PersonaExt personaExt : listPersonas) {
					try {
						if (personaExt.getCorreoElectronico() != null && !"".equals(personaExt.getCorreoElectronico()))
							HojaDeVidaDelegate.enviarEmailPersonasDesvincular(asunto, mensaje, personaExt.getCorreoElectronico());
					} catch (SIGEP2SistemaException e) {
						logger.error("El metodo no envio correo - HojaDeVidaDelegate.enviarEmailPersonasDesvincular(asunto, mensaje, personaExt.getCorreoElectronico())",
								e);
					}
				}
				if(dias!= null && dias == 0) {
					vinculacionExt.setFlgActivo((short) 0);
					vinculacionExt.setCodCausalDesvinculacion(new BigDecimal(TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue()));
				}
				vinculacionExt.setFechaFinalizacion(fechaFinalizacion);
				ComunicacionServiciosVin.setVinculacion(vinculacionExt);
				inactivarUsuarioEntidadVinculacion(vinculacionExt.getCodEntidad(), persona.getCodPersona());
				
				
			}
		}
	}

	/**Metodo que se encarga de inactivar el usuario y los roles de la persona
	 * cuando sólo se encuentra activo en la entidad
	 * */
	public void inactivarUsuarioEntidadVinculacion(Long codEntidad, BigDecimal codPersona) {
		UsuarioRolEntidadExt usuarioRolEntidad = new UsuarioRolEntidadExt();
		usuarioRolEntidad.setCodEntidad(codEntidad.intValue());
		usuarioRolEntidad.setCodPersona(codPersona.intValue());
		ComunicacionServiciosSis.eliminarUsuarioEntidad(usuarioRolEntidad);
		
	}
	
	/**
	 * Metodo la validacion en la vista de pasaporte, donde pasaporte si permite
	 * letras y numeros, mientras que los otros no.
	 */
	public void compararDocumento() {
		if (personaFilter.getCodTipoIdentificacion() != null) {
			blnPasaporte = false;
			personaFilter.setNumeroIdentificacion(null);
			Parametrica tipoDocumento = ComunicacionServiciosVin
					.getParametricaporId(new BigDecimal(personaFilter.getCodTipoIdentificacion()));
			if (tipoDocumento != null
					&& tipoDocumento.getNombreParametro().toUpperCase().contains(TIPO_DOC_PASAPORTE.toUpperCase())) {
				blnPasaporte = true;
			}
		}
	}

	/**
	 * Metodo que permite limpiar los campos de un formulario
	 */
	public void limpiarCampos() {
		cargo = new EntidadPlantaDetalleExt();
		vinculacion.setCodClasePlanta(null);
		vinculacion.setCodCargo(null);
		vinculacion.setNombreNaturalezaEmpleo("");
		vinculacion.setCodNaturalezaEmpleo(null);
		vinculacion.setCodTipoNombramiento(null);
		vinculacion.setStrNombreDependencia(null);
		vinculacion.setCodDependenciaEntidad(null);
		vinculacion.setVacantes(null);
		vinculacion.setFechaFinalizacion(null);
		vinculacion.setPersonaExpuestaPublicamente((short) 0);
		vinculacion.setCodEntidadPlantaDetalle(null);
		flgPersonaExpuestaPublicamente 	= false;
		blnExpuestoPoliticamente 		= false;
		buscarPersonaExpuestaPoliticamenteHV();
	}

	/**
	 * Metodo que valida si para la la clase y clasificacion seleccionada existen
	 * cargos creados.
	 */
	public void validarExistenciaCargo() {
		EntidadPlantaDetalleExt ent = new EntidadPlantaDetalleExt();
		if (vinculacion.getCodClasificacionPlanta() != null) {
			ent.setCodClasificacionPlanta(vinculacion.getCodClasificacionPlanta().longValue());
			ent.setCodEntidad(lngValorEntidad);
			ent.setCodClasePlanta(vinculacion.getCodClasePlanta().longValue());
			ent.setFlgActivo((short) 1);
			List<EntidadPlantaDetalleExt> listP = ComunicacionServiciosVin.getCargoPlanta(ent);
			if (listP.isEmpty()) {
				vinculacion = new VinculacionExt();
				limpiarCampos();
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_INFO_MENSAJE_PLANTA_SIN_CARGOS);
			}
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_MENSAJE_NO_CLASIFICACION);
		}
	}

	@Override
	public String persist() {
		return null;
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
		}
	}

	/*  Se generan Get y Set de las variables */

	/**
	 * @return the personaFilter
	 */
	public PersonaExt getPersonaFilter() {
		return personaFilter;
	}

	/**
	 * @param personaFilter
	 *            the personaFilter to set
	 */
	public void setPersonaFilter(PersonaExt personaFilter) {
		this.personaFilter = personaFilter;
	}

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
	 * @return the vinculacion
	 */
	public VinculacionExt getVinculacion() {
		return vinculacion;
	}

	/**
	 * @param vinculacion
	 *            the vinculacion to set
	 */
	public void setVinculacion(VinculacionExt vinculacion) {
		this.vinculacion = vinculacion;
	}

	/**
	 * @return the cargo
	 */

	public EntidadPlantaDetalleExt getCargo() {
		return cargo;
	}

	/**
	 * @param cargo
	 *            the cargo to set
	 */

	public void setCargo(EntidadPlantaDetalleExt cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the listPersona
	 */
	public List<PersonaExt> getListPersona() {
		return listPersona;
	}

	/**
	 * @param listPersona
	 *            the listPersona to set
	 */
	public void setListPersona(List<PersonaExt> listPersona) {
		this.listPersona = listPersona;
	}

	/**
	 * @return the visible
	 */
	public Boolean getVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the visiblePanelVinc
	 */
	public Boolean getVisiblePanelVinc() {
		return visiblePanelVinc;
	}

	/**
	 * @param visiblePanelVinc
	 *            the visiblePanelVinc to set
	 */
	public void setVisiblePanelVinc(Boolean visiblePanelVinc) {
		this.visiblePanelVinc = visiblePanelVinc;
	}

	/**
	 * @return the strTextoEntidadAVincular
	 */
	public String getStrTextoEntidadAVincular() {
		return strTextoEntidadAVincular;
	}

	/**
	 * @param strTextoEntidadAVincular
	 *            the strTextoEntidadAVincular to set
	 */
	public void setStrTextoEntidadAVincular(String strTextoEntidadAVincular) {
		this.strTextoEntidadAVincular = strTextoEntidadAVincular;
	}

	/**
	 * @return the lngValorEntidad
	 */
	public Long getLngValorEntidad() {
		return lngValorEntidad;
	}

	/**
	 * @param lngValorEntidad
	 *            the lngValorEntidad to set
	 */
	public void setLngValorEntidad(Long lngValorEntidad) {
		this.lngValorEntidad = lngValorEntidad;
	}

	/**
	 * @return the tiempoAprobacion
	 */
	public Integer getTiempoAprobacion() {
		return tiempoAprobacion;
	}

	/**
	 * @param tiempoAprobacion
	 *            the tiempoAprobacion to set
	 */
	public void setTiempoAprobacion(Integer tiempoAprobacion) {
		this.tiempoAprobacion = tiempoAprobacion;
	}

	/**
	 * @return the listVinculacion
	 */
	public List<VinculacionExt> getListVinculacion() {
		return listVinculacion;
	}

	/**
	 * @param listVinculacion
	 *            the listVinculacion to set
	 */
	public void setListVinculacion(List<VinculacionExt> listVinculacion) {
		this.listVinculacion = listVinculacion;
	}

	/**
	 * @return the flgTitularidad
	 */
	public Boolean getFlgTitularidad() {
		return flgTitularidad;
	}

	/**
	 * @param flgTitularidad
	 *            the flgTitularidad to set
	 */
	public void setFlgTitularidad(Boolean flgTitularidad) {
		this.flgTitularidad = flgTitularidad;
	}

	/**
	 * @return the flgTipoPlanta
	 */
	public Boolean getFlgTipoPlanta() {
		return flgTipoPlanta;
	}

	/**
	 * @param flgTipoPlanta
	 *            the flgTipoPlanta to set
	 */
	public void setFlgTipoPlanta(Boolean flgTipoPlanta) {
		this.flgTipoPlanta = flgTipoPlanta;
	}

	/**
	 * @return the listRolesPersona
	 */
	public List<PersonaExt> getListRolesPersona() {
		return listRolesPersona;
	}

	/**
	 * @param listRolesPersona
	 *            the listRolesPersona to set
	 */
	public void setListRolesPersona(List<PersonaExt> listRolesPersona) {
		this.listRolesPersona = listRolesPersona;
	}

	/**
	 * @return the listVinculacionDesvinc
	 */
	public List<VinculacionExt> getListVinculacionDesvinc() {
		return listVinculacionDesvinc;
	}

	/**
	 * @param listVinculacionDesvinc
	 *            the listVinculacionDesvinc to set
	 */
	public void setListVinculacionDesvinc(List<VinculacionExt> listVinculacionDesvinc) {
		this.listVinculacionDesvinc = listVinculacionDesvinc;
	}

	/**
	 * @return the ultimaVinDesv
	 */
	public VinculacionExt getUltimaVinDesv() {
		return ultimaVinDesv;
	}

	/**
	 * @param ultimaVinDesv
	 *            the ultimaVinDesv to set
	 */
	public void setUltimaVinDesv(VinculacionExt ultimaVinDesv) {
		this.ultimaVinDesv = ultimaVinDesv;
	}

	/**
	 * @return the strTextoVinDesv
	 */
	public String getStrTextoVinDesv() {
		return strTextoVinDesv;
	}

	/**
	 * @param strTextoVinDesv
	 *            the strTextoVinDesv to set
	 */
	public void setStrTextoVinDesv(String strTextoVinDesv) {
		this.strTextoVinDesv = strTextoVinDesv;
	}

	/**
	 * @return the strFechaVinDes
	 */
	public String getStrFechaVinDes() {
		return strFechaVinDes;
	}

	/**
	 * @param strFechaVinDes
	 *            the strFechaVinDes to set
	 */
	public void setStrFechaVinDes(String strFechaVinDes) {
		this.strFechaVinDes = strFechaVinDes;
	}

	/**
	 * @return the strMensajeDeshacer
	 */
	public String getStrMensajeDeshacer() {
		return strMensajeDeshacer;
	}

	/**
	 * @param strMensajeDeshacer
	 *            the strMensajeDeshacer to set
	 */
	public void setStrMensajeDeshacer(String strMensajeDeshacer) {
		this.strMensajeDeshacer = strMensajeDeshacer;
	}

	/**
	 * @return the strMensajeOtraVinculacion
	 */
	public String getStrMensajeOtraVinculacion() {
		return strMensajeOtraVinculacion;
	}

	/**
	 * @param strMensajeOtraVinculacion
	 *            the strMensajeOtraVinculacion to set
	 */
	public void setStrMensajeOtraVinculacion(String strMensajeOtraVinculacion) {
		this.strMensajeOtraVinculacion = strMensajeOtraVinculacion;
	}

	/**
	 * @return the flgPersonaExpuestaPublicamente
	 */
	public Boolean getFlgPersonaExpuestaPublicamente() {
		return flgPersonaExpuestaPublicamente;
	}

	/**
	 * @param flgPersonaExpuestaPublicamente
	 *            the flgPersonaExpuestaPublicamente to set
	 */
	public void setFlgPersonaExpuestaPublicamente(Boolean flgPersonaExpuestaPublicamente) {
		this.flgPersonaExpuestaPublicamente = flgPersonaExpuestaPublicamente;
	}

	/**
	 * @return the infoPersona
	 */
	public PersonaExt getInfoPersona() {
		return infoPersona;
	}

	/**
	 * @param infoPersona
	 *            the infoPersona to set
	 */
	public void setInfoPersona(PersonaExt infoPersona) {
		this.infoPersona = infoPersona;
	}

	/**
	 * @return the blnExpuestoPoliticamente
	 */
	public Boolean getBlnExpuestoPoliticamente() {
		return blnExpuestoPoliticamente;
	}

	/**
	 * @param blnExpuestoPoliticamente
	 *            the blnExpuestoPoliticamente to set
	 */
	public void setBlnExpuestoPoliticamente(Boolean blnExpuestoPoliticamente) {
		this.blnExpuestoPoliticamente = blnExpuestoPoliticamente;
	}

	/**
	 * @return the blnPasaporte
	 */
	public Boolean getBlnPasaporte() {
		return blnPasaporte;
	}

	/**
	 * @param blnPasaporte
	 *            the blnPasaporte to set
	 */
	public void setBlnPasaporte(Boolean blnPasaporte) {
		this.blnPasaporte = blnPasaporte;
	}

	/**
	 * @return the blnCargoExpuesto
	 */
	public Boolean getBlnCargoExpuesto() {
		return blnCargoExpuesto;
	}

	/**
	 * @param blnCargoExpuesto
	 *            the blnCargoExpuesto to set
	 */
	public void setBlnCargoExpuesto(Boolean blnCargoExpuesto) {
		this.blnCargoExpuesto = blnCargoExpuesto;
	}
	
	/**
	 * @return the dias
	 */
	public Integer getDias() {
		return dias;
	}

	/**
	 * @param dias the dias to set
	 */
	public void setDias(Integer dias) {
		this.dias = dias;
	}

	/**
	 * @return the cargoRepresentante
	 */
	public EntidadPlantaDetalleExt getCargoRepresentante() {
		return cargoRepresentante;
	}

	/**
	 * @param cargoRepresentante the cargoRepresentante to set
	 */
	public void setCargoRepresentante(EntidadPlantaDetalleExt cargoRepresentante) {
		this.cargoRepresentante = cargoRepresentante;
	}

	@Override
	public String update() {
		return null;
	}

	@Override
	public void delete() {
		// it isn't necessary
	}

	@Override
	public void validateForm(ComponentSystemEvent event) {
		// it isn't necessary
	}
}