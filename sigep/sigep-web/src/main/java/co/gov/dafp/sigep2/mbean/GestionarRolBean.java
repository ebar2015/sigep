package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.converter.CapitalCaseConverter;
import co.gov.dafp.sigep2.datamodel.RolLazyDataModel;
import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entities.DependenciaEntidad;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entities.Rol;
import co.gov.dafp.sigep2.entities.Usuario;
import co.gov.dafp.sigep2.entities.UsuarioRolDependencia;
import co.gov.dafp.sigep2.entities.UsuarioRolEntidad;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioEntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioRolEntidadDTO;
import co.gov.dafp.sigep2.entity.view.GeneroDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.mbean.ext.EntidadExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.RolExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioRolEntidadExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ConversationScoped
@ManagedBean
public class GestionarRolBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -131671368173191781L;
	
	private Integer TIPO_DOC_PASAPORTE = 42;
	private static final  short ESTADO_ACTIVO 	= 1;
	private static final  short ESTADO_INACTIVO = 0;
	public 	static final  int 	COD_ADMIN_TEC 	= 9;
	public 	static final  int 	COD_ADMIN_FUNC 	= 8;
	private final  static int 	ELIMINAR_ROL 	= 0;
	private final  static int 	ASIGNAR_JERARQUIA_ROL 	= 1;
	private final  static int 	ROL_INTEROPERABILIDAD 	= 21;
	private final  static int 	ROL_SERVIDOR_PUBLICO  	= 4;
	private final  static int 	ROL_JEFE_TH  			= 16;
	private final  static int 	ROL_JEFE_CONTRATOS  	= 14;
	
	private RolDTO rol;
	private RolDTO rolPadre;
	private RolDTO rolReasignar;
	private RolDTO detalleRolJerarquiaParam;
	private RolDTO rolSeleccionado;
	private EntidadDTO entidad;
	private TipoDocumentoDTO tipoDocumento;
	
	
	private List<EntidadExt> lstEntidades;
	private List<EntidadExt> entidadesSeleccionadas;
	private List<RolDTO> listaRoles;
	private List<RolDTO> jerarquiaRolesDetalle;
	private List<RolExt> listaRolesByUser;
	private List<SelectItem> lstRolBase;
	private List<DependenciaEntidad> dependencias;
	private LazyDataModel<RolDTO> listaRol;
	
	private RolExt rolAsignado;
	private PersonaDTO usuarioSeleccionado;

	private String numeroId;
	private String nombreUsuario;
	private String codigoSigep;
	private String nombreEntidad;
	private String numeroDocumento;
	
	private Long tipoId;
	private long dependencia;
	private BigDecimal rolIdPadre;
	
	private Usuario usuarioVariasEntidades;
	private UsuarioRolEntidad usuarioRoEntidad;

	private boolean verEntidades;
	private boolean habilitarFormulario 		= false;
	private boolean habilitarFormularioCrear 	= false;
	private boolean permiteEditarEntidad 		= false;
	private boolean habilitarDetalleJerarquia 	= false;
	private boolean blnPermisoBtnSelectEnt 		= false;

	private Date fechaIncio = null;
	private Date fechaFin;
	private Date today = DateUtils.getFechaSistema();

	@PostConstruct
	public void init() {
		if (getEntidadUsuario().getNombreEntidad() != null) {
			this.nombreEntidad = getEntidadUsuario().getNombreEntidad();
		}
		try {
			fechaIncio = DateUtils.formatearAFecha(DateUtils.FECHA_FORMATO, DateUtils.getHoy());
		} catch (ParseException e2) {
			fechaIncio = null;
		}
		this.codigoSigep = getEntidadUsuario().getCodigoSigep();
		if (id != null) {
			entidad 				= null;
			tipoDocumento 			= null;
			numeroDocumento 		= null;
			habilitarFormulario 	= false;
			permiteEditarEntidad 	= false;

			if (numeroId != null) {
				numeroDocumento = numeroId + "";
			}

			if (tipoId != null) {
				TipoDocumentoDTO tipoDocumento = null;
				try {
					tipoDocumento = AdministracionDelegate.findTipoDocumentoId(tipoId);
					this.tipoDocumento = tipoDocumento;

					if (this.tipoDocumento != null && this.numeroDocumento != null && !this.numeroDocumento.isEmpty()) {
						buscarUsuario();
					}
				} catch (NumberFormatException | SIGEP2SistemaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			rol = ComunicacionServiciosSis.getRolPorId(id);
			if(rol.getDescripcionRol() == null) {
				rol.setDescripcionRol(rol.getNombre());
			}else if(rol.getDescripcionRol().isEmpty()){
				rol.setDescripcionRol(rol.getNombre());
			}
			jerarquiaRolesDetalle = new ArrayList<RolDTO>();

			if (rol != null && rol.getCodRolPadre() != null) {
				RolDTO rolPadre = ComunicacionServiciosSis.getRolPorId(rol.getCodRolPadre().longValue());
				if (rolPadre != null) {
					habilitarDetalleJerarquia = true;
					jerarquiaRolesDetalle.add(rolPadre);
				}
			}
			try {
				if (usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL)) {
					verEntidades = true;
				} else {
					verEntidades = false;
				}
			} catch (SIGEP2SistemaException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cargarRolesJerarquia();
			try {
				if (usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL,
						RolDTO.ADMINISTRADOR_TECNICO)) {
					this.permiteEditarEntidad = true;
				}
				if (!this.permiteEditarEntidad) {
					this.entidad = getEntidadUsuario();
					this.cargarDependencias();
				}
			} catch (Exception e) {
				logger.error("void init()", e);
			}
		} else {
			try {
				rol = new RolDTO();
				printTable();
				cargarRolesJerarquia();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		validarPermisosRol();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}
	
	
	public void printTable() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL,
					RolDTO.ADMINISTRADOR_TECNICO, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.JEFE_TALENTO_HUMANO,
					RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.JEFE_CONTRATOS, RolDTO.OPERADOR_CONTRATOS)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}
		listaRol = new RolLazyDataModel(rol);
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
	}

	@Override
	public String persist() throws NotSupportedException {
		try {
			if (usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL,
					RolDTO.ADMINISTRADOR_TECNICO)) {
				if (this.rol != null) {
					List<RolDTO> roles = AdministracionDelegate.obtenerRolesPorDescripcion(this.rol.getNombre().toUpperCase());
					if (roles.isEmpty()) {

						/* Se consulta que el rol no esté y esté inactivo */
						List<Rol> rolNombre = ComunicacionServiciosSis
								.getRolByNombre(this.rol.getNombre().toUpperCase());
						if (rolNombre.isEmpty()) {
							AdministracionDelegate.crearRol(getUsuarioSesion(), rol);
							finalizarConversacion("/rol/gestionarRol.xhtml", MessagesBundleConstants.MSG_ROL_CREA,
									null);
						} else {
							mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
									MessagesBundleConstants.MSG_ROL_EXISTE_INACTIVO);
						}
					} else {
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.MSG_ROL_EXISTE);
					}
				}
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
		return null;
	}

	public void limpiarDoc() {
		numeroDocumento = "";
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
		if (id != null) {
			this.init();
		}
	}

	/**
	 * Metodo que valida los permisos del ususario en sesion ADMINFUN: Tiene la
	 * opcion de ver el boton "Seleccionar entidades"
	 */
	public void validarPermisosRol() {
		try {
			if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.SUPER_ADMINISTRADOR)) {
				blnPermisoBtnSelectEnt = true;
			}
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
	}

	public void listener() {
		getRoles();
		buscarUsuario();
	}

	public void buscarUsuario() {
		PersonaDTO personaDTO = null;
		try {
			personaDTO = IngresoSistemaDelegate.findByTipoDocumentoAndNumeroIdentificacion(tipoDocumento.getId(),
					numeroDocumento.toUpperCase());
			if (personaDTO != null) {
				nombreUsuario = personaDTO.getPrimerNombre();
				if (personaDTO.getSegundoNombre() != null) {
					nombreUsuario += " " + personaDTO.getSegundoNombre();
				}
				nombreUsuario += " " + personaDTO.getPrimerApellido();
				if (personaDTO.getSegundoApellido() != null) {
					nombreUsuario += " " + personaDTO.getSegundoApellido();
				}
				try {
					GeneroDTO generoDTO = AdministracionDelegate.findGeneroId(personaDTO.getCodGenero());
					if (generoDTO != null) {
						personaDTO.setGenero(generoDTO);
					}
				} catch (Exception e) {
					logger.error("String update()", e);
				}
			} else {
				RequestContext.getCurrentInstance().execute("$('#dialogActivarUsuario').modal('show')");
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("String update()", e);
		} catch (NullPointerException e2) {
			logger.error("String update()", e2);
		}
	}

	/**
	 * Asocia el rol a un usuario-entidad activo
	 */
	public void asociarUsuarioEntidad() {
		try {
			if(!validarPermisoAsignar()) {
				return;
			}
	     
	     	if(!validarRolFuncional()) {
				return;
			}
	     	EntidadDTO entidadAsociar = null;
			entidadAsociar = getEntidadUsuario();
			UsuarioPasswordDTO usuario = IngresoSistemaDelegate.getUsuarioByLogin(tipoDocumento.getId(), numeroDocumento.toUpperCase());
			if (usuario != null) {
				List<UsuarioDTO> usuariosAsociados = IngresoSistemaDelegate.obtenerEntidadesUsuarioAsociadas(usuario.getId(), entidadAsociar.getId());

				if (!usuariosAsociados.isEmpty()) {
					UsuarioEntidadDTO usuarioEntidad = IngresoSistemaDelegate.consultarUsuarioEntidad(usuario.getId(),
							entidadAsociar.getId());
					if (usuarioEntidad != null) {
						List<RolDTO> listaRolesByUser = AdministracionDelegate.obtenerRolesByPersona(
								usuariosAsociados.get(0).getCodPersona(), entidadAsociar.getId());
						if (!existeRol(rol, listaRolesByUser)) {
							List<UsuarioExt> lstUsuext = ComunicacionServiciosSis
									.getUsuarioRolesValidarOP(usuario.getId());
							if (lstUsuext.size() > 0) {
								for (UsuarioExt usuarioExt : lstUsuext) {
									if (usuarioExt.getCodRol().longValue() == ROL_INTEROPERABILIDAD) {
										mostrarMensaje(FacesMessage.SEVERITY_WARN,
												MessagesBundleConstants.DLG_HEADER_MENSAJES,
												MessagesBundleConstants.MSG_USUARIO_NO_VALIDO_OP_ROL);
										return;
									}
								}

							}
							if (rol.getId() == ROL_INTEROPERABILIDAD) {
								if (lstUsuext.size() != 1) {
									if (lstUsuext.get(0).getCodRol().longValue() != ROL_SERVIDOR_PUBLICO) {
										mostrarMensaje(FacesMessage.SEVERITY_WARN,
												MessagesBundleConstants.DLG_HEADER_MENSAJES,
												MessagesBundleConstants.MSG_USUARIO_NO_VALIDO_OP_ROL);
										return;
									}
									if (lstUsuext.get(0).getCodEntidad().longValue() != entidadAsociar.getId()) {
										mostrarMensaje(FacesMessage.SEVERITY_WARN,
												MessagesBundleConstants.DLG_HEADER_MENSAJES,
												MessagesBundleConstants.MSG_USUARIO_NO_VALIDO_OP_ENTIDAD);
										return;
									}
								}
							}
							UsuarioRolEntidadDTO usuarioAsociar = new UsuarioRolEntidadDTO(0L, rol.getId(), usuarioEntidad.getId(), true, true);
							UsuarioDTO usuarioAuditoria = getUsuarioSesion();
							usuarioAuditoria.setCodRol(getRolAuditoria().getId());
							AdministracionDelegate.asociarRolUsuario(usuarioAsociar, usuarioAuditoria,
									String.valueOf(TipoParametro.AUDITORIA_INSERT.getValue()));
							if (rol.getId() == 21) {
								ComunicacionServiciosSis.updateUsuarioOP(usuario.getId());
							}
							UsuarioRolEntidad usEnt = ComunicacionServiciosEnt.getUsuarioRolEntidadByRol(usuario.getId(), rol.getId());
							if(usEnt!= null && usEnt.getCodUsuarioRolEntidad() != null) {
								asociarDependenciaFechas(usEnt.getCodUsuarioRolEntidad());
							}
							asociarUsuariofuncionalTodasEntidades(usuarioAsociar);
							getRoles();
							mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_USUARIO_ROL_ASOCIADO);								
						} else {
							rol = new RolDTO();
							mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
									MessagesBundleConstants.MSG_USUARIO_ROL_ASOCIADO_EXISTE);
						}
					} else {
						RequestContext.getCurrentInstance().execute("$('#dialogActivarUsuarioInactivo').modal('show')");
					}
				} else {
					RequestContext.getCurrentInstance().execute("$('#dialogActivarUsuarioInactivo').modal('show')");
				}
			} else {
				RequestContext.getCurrentInstance().execute("$('#dialogActivarUsuarioInactivo').modal('show')");
			}
		}catch (Exception e) {
			logger.error("void asociarUsuarioEntidad()", e);
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	/**
	 * Metodo que asocia a una persona que se le acaba de asociar un rol
	 * funcional todas las entidades del sistema
	 */
	public void asociarUsuariofuncionalTodasEntidades(UsuarioRolEntidadDTO usuarioAsociar) {
		usuarioRoEntidad = new UsuarioRolEntidad();
		usuarioRoEntidad.setCodUsuarioEntidad(new BigDecimal(usuarioAsociar.getUsuarioEntidad()));
		usuarioRoEntidad.setFlgActivo((short) 1);
		usuarioRoEntidad.setFlgEstado(new BigDecimal(1));
		usuarioRoEntidad.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		usuarioRoEntidad.setAudAccion(new BigDecimal(TipoAccionEnum.INSERT.getIdAccion()));
		usuarioRoEntidad.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
		usuarioRoEntidad.setCodRol(new BigDecimal(usuarioAsociar.getRol()));
		ComunicacionServiciosAdmin.asociarRolFuncionalTodasEntidades(usuarioRoEntidad);
	}

	/**
	 * Verifica si un rol pertenece a jerarquia del rol en sesion
	 * @param id
	 * @return
	 */
	private boolean rolAutorizadoJerarquia(long idRol) {
		List<RolDTO> rolesJerarquia = ComunicacionServiciosSis.getRolesHijos(this.getRolAuditoria().getId(),
				(int) ESTADO_ACTIVO);
		for (RolDTO rolj : rolesJerarquia) {
			if (rolj.getId() == idRol) {
				return true;
			}
		}
		return false;
	}

	private boolean existeRol(RolDTO rol, List<RolDTO> listaRolesByUser) {
		for (RolDTO auxRol : listaRolesByUser) {
			if (auxRol.getId() == rol.getId()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Carga las entidades asociadas como dependendientes a la seleccionada
	 */
	public void cargarDependencias() throws SIGEP2SistemaException {
		dependencias = new LinkedList<>();
		PersonaDTO personaDTO = null;
		try {
			personaDTO = IngresoSistemaDelegate.findByTipoDocumentoAndNumeroIdentificacion(tipoDocumento.getId(),
					numeroDocumento.toUpperCase());

			if (personaDTO != null) {
				if (this.entidad != null) {
					dependencias = ComunicacionServiciosSis.getDependenciaEntidadPersona(personaDTO.getId(),
							entidad.getId());
					ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
					contexto.getSessionMap().put("dependencias", dependencias);
				}
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("String update()", e);
		}
	}
	
	/**
	 * Metodo para hacer back hacia la grilla de consulta de roles
	 */
	@Override
	public void cancelar() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("gestionarRol.xhtml");
		} catch (IOException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	/**
	 * Metodo para desactivar la bandera que muestra el panel de creación de
	 * roles
	 */
	public void ocultarPanelCrear() {
		habilitarFormularioCrear = false;
		RequestContext.getCurrentInstance().execute("$('#nombreRol').focus();");
	}

	/**
	 * Inicializa el listado de roles a los cuales se les puede asignar como padre a un rol determinado.
	 * Validaciones:
	 * 1. Si el rol de mayor jerarquia (getRolAuditoria().getId()) es Jefe de Talento Humano o Jefe de contratos:
	 * 	  El sistema valida si el usuario presenta estos dos roles simultameamnete, si es así, hace la unión de los roles Hijos
	 * 2. Si el rol de mayor jerarquia (getRolAuditoria().getId()) es diferente a JefeTH y JefeContratos, el sistema consulta los roles hijos sólo por el rol 
	 *    de mayor jerarquia (getRolAuditoria().getId())
	 */
	public void cargarRolesJerarquia() {
		this.listaRoles = new LinkedList<>();
		List<RolDTO> listaRolTemp = new LinkedList<>();
		if(verficarRolJefeTHJefeContatos() && (this.getRolAuditoria().getId() ==  ROL_JEFE_TH || this.getRolAuditoria().getId() ==  ROL_JEFE_CONTRATOS)) {
			RolExt rolExt = new RolExt();
			rolExt.setFlgEstado( new BigDecimal(ESTADO_ACTIVO));
			listaRolTemp = ComunicacionServiciosSis.getRolesHijosJefeTHYContratos(rolExt);
		}else {
			listaRolTemp = ComunicacionServiciosSis.getRolesHijos(this.getRolAuditoria().getId(),
					(int) ESTADO_ACTIVO);
		}
		if (!listaRolTemp.isEmpty()) {
			for (RolDTO rol : listaRolTemp) {
				if (!this.rol.equals(rol)) {
					if (!esRolAsignado(rol, this.jerarquiaRolesDetalle)) {
						if(rol.getDescripcionRol() == null) {
							rol.setDescripcionRol(rol.getNombre());
						}else if(rol.getDescripcionRol().isEmpty()){
							rol.setDescripcionRol(rol.getNombre());
						}
						this.listaRoles.add(rol);
					}
				}
			}
		}
		
	}
	
	/** Metodo que verifica si la persona cuenta con los roles de jefe de talento humano y jefe de contratos, ambos a la vez.
	 * Si es asi, realiza una consulta diferente que obtiene los roles que puede asignar, esto de acuerdo a la jerarquia de roles
	 * */
	public boolean verficarRolJefeTHJefeContatos() {			
			RolExt rolExt = new RolExt();
			int cont = 0;
			rolExt.setCodPersona(getUsuarioSesion().getCodPersona());
			rolExt.setOcultarRolBase("1");
			rolExt.setCodEntidad(getEntidadUsuario().getId());
			List<RolExt> listaRolesByUserTemp = ComunicacionServiciosSis.getRolPorPersonaEntidad(rolExt);
			for (RolExt rolUser : listaRolesByUserTemp) {
				if(rolUser.getCodRol().longValue() == ROL_JEFE_TH || rolUser.getCodRol().longValue() == ROL_JEFE_CONTRATOS) {
					cont += 1; 
				}
			}
			if(cont >=2) {
				return true;
			}
		return false;
	}
	
	private boolean esRolAsignado(RolDTO rol, List<RolDTO> listadoRolesAsignados) {
		if (listadoRolesAsignados == null || listadoRolesAsignados.isEmpty()) {
			return false;
		}
		for (RolDTO rolAux : listadoRolesAsignados) {
			if (rol.getNombre().equals(rolAux.getNombre())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Inicializa el listado de roles a los cuales se les puede asignar como
	 * padre a un rol determinado
	 */
	public void cargarRolesNuevoAsociar() {
		List<RolDTO> listaRolTemp = ComunicacionServiciosSis.getRolesSistema("", 1, 0, (int) ESTADO_ACTIVO);
		this.listaRoles = new LinkedList<>();
		for (RolDTO rol : listaRolTemp) {
			if (!this.rol.equals(rol)) {
				this.listaRoles.add(rol);
			}
		}
	}

	/**
	 * Funcionalidad que permite definir la jerarquia de roles del sistema en
	 * modo de adición
	 */
	public void asignarJerarquiaRol() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}

			RolDTO rolValidar = ComunicacionServiciosSis.getRolPorId(rol.getId());
			if (!validaJerarquiaModificarRol(rolValidar, ASIGNAR_JERARQUIA_ROL))
				return;

			if (!jerarquiaRolesDetalle.isEmpty()) {
				eliminardetalleRolJerarquiaParametrica(rol);
			}
			Rol rolAsignar = new Rol();
			rolAsignar.setCodRol(new BigDecimal(rol.getId()));
			rolAsignar.setNombre(rol.getNombre().toUpperCase());
			rolAsignar.setDescripcionRol(rol.getDescripcionRol());
			rolAsignar.setCodRolPadre(rol.getCodRolPadre());
			rolAsignar.setFlgActivo(rol.isFlgActivo() ? ESTADO_ACTIVO : ESTADO_INACTIVO);
			rolAsignar.setFechaModificacion(new Date());
			rolAsignar
					.setFlgEstado(rol.isFlgEstado() ? new BigDecimal(ESTADO_ACTIVO) : new BigDecimal(ESTADO_INACTIVO));
			rolAsignar.setAudFechaActualizacion(new Date());
			rolAsignar.setAudAccion(TipoParametro.AUDITORIA_UPDATE.getValue());
			UsuarioDTO usuarioSesion = getUsuarioSesion();
			rolAsignar.setAudCodRol((int) usuarioSesion.getCodRol());
			rolAsignar.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
			boolean resultadoExitoso = ComunicacionServiciosSis.setanduprol(rolAsignar);
			if (resultadoExitoso) {
				jerarquiaRolesDetalle = new LinkedList<>();
				jerarquiaRolesDetalle.add(ComunicacionServiciosSis.getRolPorId(rol.getCodRolPadre().longValue()));
				habilitarDetalleJerarquia = true;
				cargarRolesJerarquia();
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_JERARQUIA_ROL_ASIGNADA);
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			}
		} catch (Exception e) {
			logger.error("void asignarJerarquiaRol()", e);
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	/**
	 * Funcionalidad que permite definir la jerarquia de roles del sistema en
	 * modo de remoción
	 */
	public void desasignarJerarquiaRol() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
			rol.setRolPadre(null);
			AdministracionDelegate.actualizarRoles(getUsuarioSesion(), rol);
			finalizarConversacion("/rol/gestionarRol.xhtml", MessagesBundleConstants.DLG_PROCESO_EXITOSO, null);
		} catch (Exception e) {
			logger.error("void desasignarJerarquiaRol()", e);
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	/**
	 * Funcionalidad que permite eliminar un rol y reasignar a otro en caso de
	 * tener usuarios asociados
	 */
	public void eliminarRol(RolDTO rolSeleccionado) {
		rol = rolSeleccionado;
		try {
			if (!usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL,
					RolDTO.ADMINISTRADOR_TECNICO)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}

			RolDTO rolTemp = ComunicacionServiciosSis.getRolPorId(rolSeleccionado.getId());
			if (!validaJerarquiaModificarRol(rolTemp, ELIMINAR_ROL))
				return;

			if (AdministracionDelegate.isRolTieneUsuarioAsociados(rol.getId())) {
				cargarRolesJerarquia();
				RequestContext.getCurrentInstance().execute("PF('dialogReasignarRolUsuarios').show();");

			} else {
				rol.setFlgEstado(false);
				rol.setFlgActivo(false);
				AdministracionDelegate.actualizarRoles(getUsuarioSesion(), rolPadre, rol);
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_USUARIO_ROL_ELIMINADO);
				habilitarFormularioCrear = false;
				rol = new RolDTO();
			}
		} catch (Exception e) {
			logger.error("void desasignarJerarquiaRol()", e);
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	public void redireccionarAsignarRol(String tipoIdentificacion, String numeroIdentificacion) throws IOException {
		finalizarConversacion("/rol/associate.xhtml", null,
				"id=0&tipoIdentificacion=" + tipoIdentificacion + "&numeroIdentificacion=" + numeroIdentificacion);
	}

	
	public void eliminardetalleRolJerarquiaParametrica(RolDTO detalleRolJerarquiaParam) throws SIGEP2SistemaException {
		if (!usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL)) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
			return;
		}

		for (int i = 0; i < jerarquiaRolesDetalle.size(); i++) {
			RolDTO existente = jerarquiaRolesDetalle.get(i);
			if (existente.getNombre().equals(detalleRolJerarquiaParam.getNombre())) {
				Rol rolAsignar = new Rol();
				rolAsignar.setCodRol(new BigDecimal(rol.getId()));
				rolAsignar.setNombre(rol.getNombre());
				rolAsignar.setDescripcionRol(rol.getDescripcionRol());
				rolAsignar.setCodRolPadre(null);
				rolAsignar.setFlgActivo(rol.isFlgActivo() ? ESTADO_ACTIVO : ESTADO_INACTIVO);
				rolAsignar.setFechaModificacion(new Date());
				rolAsignar.setFlgEstado(
						rol.isFlgEstado() ? new BigDecimal(ESTADO_ACTIVO) : new BigDecimal(ESTADO_INACTIVO));
				rolAsignar.setAudFechaActualizacion(new Date());
				rolAsignar.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
				UsuarioDTO usuarioSesion = getUsuarioSesion();
				rolAsignar.setAudCodRol((int) usuarioSesion.getCodRol());
				rolAsignar.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				boolean resultadoExitoso = ComunicacionServiciosSis.setanduprol(rolAsignar);
				if (resultadoExitoso) {
					jerarquiaRolesDetalle.remove(i);
					rol = ComunicacionServiciosSis.getRolPorId(id);
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_PROCESO_EXITOSO);
				} else {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_PROCESO_FALLIDO);
				}
			}
			cargarRolesJerarquia();
		}
	}

	public void reasignarRolUsuarios(RolDTO rolReasignar) throws IOException, SIGEP2SistemaException {
		rol.setFlgEstado(false);
		rol.setFlgActivo(false);
		AdministracionDelegate.actualizarRoles(getUsuarioSesion(), rolReasignar, rol);
		rol = new RolDTO();
		finalizarConversacion("/rol/gestionarRol.xhtml", MessagesBundleConstants.MSG_USUARIO_ROL_ELIMINADO, null);
	}

	public void redireccionarActivarUsuario() throws IOException {
		finalizarConversacion("/usuario/asociarUsuarioEntidad.xhtml", null,
				"tipoId=" + tipoDocumento.getId() + "&numeroId=" + numeroDocumento);
	}

	public void abrirVentanaCreacion() {
		rol = new RolDTO();
		this.habilitarFormularioCrear = true;
	}

	
	public void abrirFormularioTabla() {
		this.entidadesSeleccionadas = new ArrayList<>();
		if (tipoDocumento != null) {
			if (tipoDocumento.getId() != null) {
				if (numeroDocumento != null) {
					Persona persona = new Persona();
					persona.setCodTipoIdentificacion(tipoDocumento.getId() + "");
					persona.setNumeroIdentificacion(numeroDocumento);
					persona.setLimitInit(0);
					persona.setLimitEnd(2);
					List<PersonaExt> ltsPer = ComunicacionServiciosHV.getpersonaFiltro(persona);
					if (ltsPer.size() == 0) {
						mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.MSG_USUARIO_SIN_DATOS);
						this.habilitarFormulario = false;
					} else {
						nombreUsuario = ltsPer.get(0).getPrimerNombre();
						if (ltsPer.get(0).getSegundoNombre() != null) {
							nombreUsuario += " " + ltsPer.get(0).getSegundoNombre();
						}
						nombreUsuario += " " + ltsPer.get(0).getPrimerApellido();
						if (ltsPer.get(0).getSegundoApellido() != null) {
							nombreUsuario += " " + ltsPer.get(0).getSegundoApellido();
						}
						this.habilitarFormulario = true;
						try {
							if(usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL)) {
							    EntidadExt ent = new EntidadExt();
								ent.setLimitInit(0);
					 			ent.setLimitEnd(5000);
					 			ent.setFlgActivo((short)1);
					 			lstEntidades = ComunicacionServiciosEnt.getEntidadesPersonaRol(ent);
							}else {
								List<EntidadExt> listentExt = ComunicacionServiciosSis
										.getEntidadesPersonaRol(ltsPer.get(0).getCodPersona().longValue());
								lstEntidades = generarListaEntidades(rol.getId(), listentExt);
							}
						} catch (SIGEP2SistemaException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return;
				}
			}
		}
		this.habilitarFormulario = false;
		mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
				MessagesBundleConstants.MSG_DATOS_OBLIGATORIOS);
	}

	public void cerrarFormularioTabla() {
		this.habilitarFormulario = false;
	}

	/**
	 * Elaborado Por: Jose Viscaya 11 feb. 2019 GestionarRolBean.java
	 * @param flgDependencial
	 * @return
	 */
	public boolean getFlgDependencial(Integer flgDependencial) {
		if (flgDependencial != null) {
			if (flgDependencial == 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Elaborado Por: Jose Viscaya 11 feb. 2019 GestionarRolBean.java
	 * @param rol
	 * @param ltsEntidad
	 * @return
	 */
	private List<EntidadExt> generarListaEntidades(long rol, List<EntidadExt> ltsEntidad) {
		List<EntidadExt> ltsEntidad2 = new ArrayList<>();
		for (int i = 0; i < ltsEntidad.size(); i++) {
			if (ltsEntidad.get(i).getCodRol() == rol) {
				ltsEntidad2.add(ltsEntidad.get(i));
			}
		}
		List<EntidadExt> ltsEntidad3 = ltsEntidad;
		for (int i = 0; i < ltsEntidad.size(); i++) {
			for (int j = 0; j < ltsEntidad2.size(); j++) {
				if (ltsEntidad2.get(j).getCodEntidad() == ltsEntidad.get(i).getCodEntidad()) {
					ltsEntidad3.remove(i);
				}
			}
		}
		long id = 0;
		List<Long> codentidad = new ArrayList<>();
		for (int i = 0; i < ltsEntidad3.size(); i++) {
			if (id != ltsEntidad3.get(i).getCodEntidad().longValue()) {
				id = ltsEntidad3.get(i).getCodEntidad().longValue();
				codentidad.add(ltsEntidad3.get(i).getCodEntidad().longValue());
			}
		}
		EntidadExt req = new EntidadExt();
		req.setLstCodEntidad(codentidad);
		;
		return ltsEntidad2;
	}

	/**
	 * Metodo utilizado para la asignacion de rol a varias entidades desde el botón "Seleccionar Entidades"
	 * 1. validarPermisoAsignar(): Valida que la persona en sesion tenga permiso para asignar roles
	 * 2. validarRolFuncional():Valida que la persona en sesion tenga rol administrador funcional
	 * 3. validarEntidadesSeleccionadas(): Valida que si existan entidades seleccionadas
	 * 4. verificarExistenciaUsuario(): Valida que la persona a la que se le va a asignar el rol si tenga un usuario activo
	 * 5. asociarRolAEntidades(): Si se cumple con todo lo anterior, se procede a realizar las verificaciones propias por entidad
	 * 6. validarExistenciaDeRolInteroperabilidad: Verifica si el rol seleccionado corresponde al rol de interoperabilidad
	 */
	public void asignarRoles() {
		if(!validarPermisoAsignar()) {
			return;
		}
		if(!validarRolFuncional()) {
			return;
		}
		
		if(!validarEntidadesSeleccionadas()) {
			return;
		}
		
		if(!verificarExistenciaUsuario()) {
			return;
		}
		
		if(validarExistenciaDeRolInteroperabilidad()) {
			return;
		}
		asociarRolAEntidades();
		this.entidadesSeleccionadas = new ArrayList<>(); 
		cerrarFormularioTabla();
	}
	
	/**
	 * Metodo que recorre la lista de entidades seleccionadas y sobre esta realiza la asociacion del usuarioRolEntidad
	 * 1. validarExistenciaDeRolEntidad(): Verifica que la persona no tenga asociado ese rol para la entidad.
	 */
	public void asociarRolAEntidades() {
		for (EntidadExt entidadExt : entidadesSeleccionadas) {
			
			if(validarExistenciaDeRolEntidad(entidadExt.getCodEntidad(), entidadExt.getNombreEntidad())) {
				continue;
			}
			if(!validarExistenciaDeRolServidorPublico(entidadExt.getCodEntidad(), entidadExt.getNombreEntidad())) {
				continue;
			}
			asignarRolAUsuario(entidadExt.getCodEntidad(), entidadExt);
		}
	}
	
	/**
	 * Metodo que verifica que la persona no tenga el rol ya asignado a la entidad
	 * @return blnExisteRolAsignado. Es true cuando este rol ya existe 
	 */
	public boolean validarExistenciaDeRolEntidad(BigDecimal codEntidad, String nombreEntidad) {
		boolean blnExisteRolAsignado = false;
		if(usuarioVariasEntidades!= null && usuarioVariasEntidades.getCodUsuario() != null 
			&& codEntidad != null && rol.getId()>0) {
			UsuarioRolEntidadExt objUsuarioRolEntidad = new UsuarioRolEntidadExt();
			objUsuarioRolEntidad.setCodUsuario(usuarioVariasEntidades.getCodUsuario().intValue());
			objUsuarioRolEntidad.setCodEntidad(codEntidad.intValue());
			objUsuarioRolEntidad.setCodRol(new BigDecimal(rol.getId()));
			UsuarioRolEntidad result = ComunicacionServiciosAdmin.getUsuarioRolEntidad(objUsuarioRolEntidad);
			if(result != null && result.getCodUsuarioRolEntidad()!= null) {
				String strMensaje = MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_USUARIO_ROL_ASOCIADO_EXISTE_ENTIDAD, getLocale())
						.replace("%entidad%", nombreEntidad);
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,strMensaje);
				blnExisteRolAsignado = true;
			}
		}		
		return blnExisteRolAsignado;
	}
	

	/**
	 * Metodo que valida si el usuario ya cuenta con el rol de interoperabilidad.
	 * Este rol debe de ser asignado solo una vez al usuario.
	 * @return blnExisteRolInteroperabilidad. 
	 * 		Retorna el valor de true: cuando el usuario ya tiene asignado el rol de interoperabilidad independiente de la entidad.
	 */
	public boolean validarExistenciaDeRolInteroperabilidad() {
		boolean blnExisteRolInteroperabilidad = false;
		if(usuarioVariasEntidades!= null && usuarioVariasEntidades.getCodUsuario() != null 
			&& rol.getId()>0 && rol.getId() == ROL_INTEROPERABILIDAD) {
			UsuarioRolEntidadExt objUsuarioRolEntidad = new UsuarioRolEntidadExt();
			objUsuarioRolEntidad.setCodUsuario(usuarioVariasEntidades.getCodUsuario().intValue());
			objUsuarioRolEntidad.setCodRol(new BigDecimal(rol.getId()));
			UsuarioRolEntidad result = ComunicacionServiciosAdmin.getUsuarioRolEntidad(objUsuarioRolEntidad);
			if(result != null && result.getCodUsuarioRolEntidad()!= null) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_USUARIO_NO_VALIDO_OP_ENTIDAD);
				blnExisteRolInteroperabilidad = true;
			}
		}
		return blnExisteRolInteroperabilidad;
	}
	
	/**
	 * Metodo que valida si el rol a asignar es de interoperabilidad. 
	 * Si el rol es interoperabilidad, valida que para la entidad se tenga previo el rol de Servidor publico 
	 * @return blnTieneRolServidorPublico
	 */
	public boolean validarExistenciaDeRolServidorPublico(BigDecimal codEntidad, String nombreEntidad) {
		boolean blnTieneRolServidorPublico = true;
		if(usuarioVariasEntidades!= null && usuarioVariasEntidades.getCodUsuario() != null 
				&& rol.getId()>0 && rol.getId() == ROL_INTEROPERABILIDAD && codEntidad != null) {
			UsuarioRolEntidadExt objUsuarioRolEntidad = new UsuarioRolEntidadExt();
			objUsuarioRolEntidad.setCodUsuario(usuarioVariasEntidades.getCodUsuario().intValue());
			objUsuarioRolEntidad.setCodEntidad(codEntidad.intValue());
			objUsuarioRolEntidad.setCodRol(new BigDecimal(ROL_SERVIDOR_PUBLICO));
			UsuarioRolEntidad result = ComunicacionServiciosAdmin.getUsuarioRolEntidad(objUsuarioRolEntidad);
			if(result != null && result.getCodUsuarioRolEntidad()!= null) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_USUARIO_NO_VALIDO_OP_ROL);
				blnTieneRolServidorPublico = false;
			}
		}
		return blnTieneRolServidorPublico;
	}
	
	
	/**
	 * Metodo que se encarga de asignar el usuario a las entidades seleccionadas con el rol especificado.
	 * Este metodo construye un objeto con toda la información necesaria para poblar las tablas de:
	 *  UsuarioEntidad y UsuarioRolEntidad.
	 *  Este metodo llama un servicio que envia la informacion al procedimiento en BD SP_ASOCIAR_ROL_USUARIO.
	 * @param codEntidad
	 */
	public void asignarRolAUsuario(BigDecimal codEntidad, EntidadExt entidadExt ) {
		UsuarioRolEntidadExt objUsuarioRolEntidad = new UsuarioRolEntidadExt();
		if(usuarioVariasEntidades!= null && usuarioVariasEntidades.getCodUsuario() != null && codEntidad != null && rol.getId()>0 ) {
			objUsuarioRolEntidad.setCodRol(new BigDecimal(rol.getId()));
			objUsuarioRolEntidad.setCodUsuario(usuarioVariasEntidades.getCodUsuario().intValue());
			objUsuarioRolEntidad.setCodEntidad(codEntidad.intValue());
			objUsuarioRolEntidad.setFlgActivo((short) 1);
			objUsuarioRolEntidad.setFlgEstado(new BigDecimal(1));
			objUsuarioRolEntidad.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
			objUsuarioRolEntidad.setAudAccion(new BigDecimal(TipoAccionEnum.INSERT.getIdAccion()));
			objUsuarioRolEntidad.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
			UsuarioRolEntidad result = ComunicacionServiciosAdmin.asociarRolAUsuario(objUsuarioRolEntidad);
			if(result != null && !result.isError()) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_USUARIO_ROL_ASOCIADO);
				if(result.getCodUsuarioRolEntidad()!= null) {
					asociarDependenciaFechas(result.getCodUsuarioRolEntidad());
				}
				getRoles();
			}
		}
	}
	
	/**
	 * Metodo que verifica que la persona a la que se le va a asignar un rol si presente un usuario en estado activo
	 * @return blUsuarioActivo
	 * - Si el usuario esta activo retorna blUsuarioActivo = true y continua con el flujo de asociar rol a las entidades
	 * - Si el usuario no existe, o esta inactivo retorna blnUsuarioActivo = false y abre dialogo para que la persona
	 * 	 escoja si desea "Activar el usuario" desde el componente de Activar Usuario. Este validacion se hace de acuerdo
	 *   al comportamiento que tiene la aplicación cuando el usuario no tiene el rol funcional y selecciona solo la opción
	 *   permitida de una entidad.
	 */
	public boolean verificarExistenciaUsuario() {
		boolean blUsuarioActivo = true;
		UsuarioExt objUsuario = new UsuarioExt();
		objUsuario.setCodTipoIdentificacionPersona(new BigDecimal(tipoDocumento.getId()));
		objUsuario.setNumeroIdentificacionPersona(numeroDocumento.toUpperCase());
		usuarioVariasEntidades = ComunicacionServiciosAdmin.getUsuarioByPersona(objUsuario);
		if(usuarioVariasEntidades!= null && usuarioVariasEntidades.getCodUsuario() != null && usuarioVariasEntidades.getFlgEstado()) {
			blUsuarioActivo = true;
		}else {
			RequestContext.getCurrentInstance().execute("$('#dialogActivarUsuarioInactivo').modal('show')");
			blUsuarioActivo = false;
			usuarioVariasEntidades = new Usuario();
		}
		return blUsuarioActivo;
	}
	
	/**
	 * Metodo que valida si el usuario tiene permisos para asignar roles
	 * @return true si tiene permiso, de lo contrario false.
	 */
	public boolean validarPermisoAsignar() {
		boolean blnTienePermiso = true;
		try {
			if (!usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL,
					RolDTO.ADMINISTRADOR_TECNICO, RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTRATOS,
					RolDTO.ADMINISTRADOR_ENTIDADES)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				blnTienePermiso = false;
			}
		} catch (Exception e) {
			blnTienePermiso = false;
		}
		return blnTienePermiso;
	}

	/**
	 * Metodo que valida si el rol es funcional.
	 * Si el rol es funcional no valida la jerarquia
	 * @return blnRolAutorizado
	 */
	public boolean validarRolFuncional() {
		boolean blnRolAutorizado = true;
		try {
			boolean lbIsRolFuncional = usuarioTieneRolAsignado((RolDTO.ADMINISTRADOR_FUNCIONAL));
			if (!lbIsRolFuncional) {
				// Validacion de Jerarquia
				if (!rolAutorizadoJerarquia(rol.getId())) {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_ASIGNACION_ROL_USUARIO_NO_AUTORIZADO);
					blnRolAutorizado = false;
				}
			}
		} catch (Exception e) {
			blnRolAutorizado = false;
		}
		return blnRolAutorizado;
	}
	
	
	/**
	 * Metodo que valida si la persona si selecciono entidades a asignar
	 * @return true si existen entidades, de lo contrario false
	 */
	public boolean validarEntidadesSeleccionadas() {
		boolean blnExisteEntidad = true;
		if (entidadesSeleccionadas == null || entidadesSeleccionadas.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_SELECCIONAR_REGISTRO);
			blnExisteEntidad = false;
		}
		return blnExisteEntidad;
	}
	
	/**
	 * Elaborado Por: Jose Viscaya 12 feb. 2019 GestionarRolBean.java
	 * @param event
	 */
	public void getRoles() {
		try {			
			PersonaDTO per = IngresoSistemaDelegate.findByTipoDocumentoAndNumeroIdentificacion(tipoDocumento.getId(),
					numeroDocumento.toUpperCase());
			RolExt rolExt = new RolExt();
			setListaRolesByUser(new ArrayList<>());
			if (per != null) {
				if (per.getId() > 0) {
					rolExt.setCodPersona(per.getId());
					rolExt.setOcultarRolBase("0");
					List<RolExt> listaRolesByUserTemp = ComunicacionServiciosSis.getRolPorPersonaEntidad(rolExt);
					List<RolExt> listaRolesByUser = new LinkedList<>();
					for (RolExt rolUser : listaRolesByUserTemp) {
						if (!listaRolesByUser.contains(rolUser)) {
							listaRolesByUser.add(rolUser);
						}
					}
					setListaRolesByUser(listaRolesByUser);
				}
			}

		} catch (NullPointerException e) {
			setListaRolesByUser(new ArrayList<>());
			e.printStackTrace();
		} catch (SIGEP2SistemaException e) {
			setListaRolesByUser(new ArrayList<>());
			e.printStackTrace();
		}
	}

	/**
	 * Metodo utilizado para almacenar las fechas de inicio, fin y dependencia de un rol.
	 * Esto es almanenado en el maestro usuario_rol_dependencia
	 */
	public void asociarDependenciaFechas(BigDecimal objCodUsuarioRolEntidad) {
		try {
			int cont = 0;
			UsuarioRolDependencia usDep = new UsuarioRolDependencia();
			if (dependencia > 0) {
				usDep.setCodDependenciaEntidad(new BigDecimal(dependencia));
				cont++;
			}
			if (fechaIncio != null) {
				usDep.setFechaInicio(fechaIncio);
				cont++;
			} else {
				usDep.setFechaInicio(new Date());
				cont++;
			}
			if (fechaFin != null) {
				usDep.setFechaFin(fechaFin);
				cont++;
			}
			if (cont > 0) {
				UsuarioRolDependencia usuarioRolDependencia = new UsuarioRolDependencia();
				usuarioRolDependencia.setCodUsuarioRolEntidad(objCodUsuarioRolEntidad);
				usuarioRolDependencia.setFlgActivo((short) 1);
				List<UsuarioRolDependencia>  lstRolDependencia = ComunicacionServiciosEnt.getUsuarioRolDependencia(usuarioRolDependencia);
				if(!lstRolDependencia.isEmpty()) {
					usDep.setCodUsuarioRolDependencia(lstRolDependencia.get(0).getCodUsuarioRolDependencia());
					usDep.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
					
				}else {
					usDep.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
				}
				usDep.setCodUsuarioRolEntidad(objCodUsuarioRolEntidad);
				usDep.setAudCodRol(new BigDecimal(getRolAuditoria().getId()).intValue());
				usDep.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				usDep.setAudFechaActualizacion(new Date());
				usDep.setFlgActivo((short) 1);
				usDep = ComunicacionServiciosEnt.setUsuarioRolDependencia(usDep);
				if (usDep.isError()) {
					mostrarMensaje(FacesMessage.SEVERITY_WARN,
							MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_PROCESO_FALLIDO_DEPENDENCIA);
				}
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO_DEPENDENCIA);
		}
		
	}
	
	public void seleccionarModificarFechas(RolExt rolAsignado) {
		this.rolAsignado = new RolExt();
		this.rolAsignado = rolAsignado;
		RequestContext.getCurrentInstance().execute("PF('editarFecha').show();");
	}

	/**
	 * Elaborado Por: Jose Viscaya 12 feb. 2019 GestionarRolBean.java
	 * @param rolAsignado
	 * @throws SIGEP2SistemaException
	 * @throws IOException
	 */
	public void modificarRolAsignadoUsuario(RolExt rolAsignado) throws SIGEP2SistemaException, IOException {
		if (rolAsignado.getCodUsuarioRolEntidad() != null) {
			UsuarioRolDependencia dep = new UsuarioRolDependencia();
			dep.setFlgActivo((short) 1);
			dep.setCodUsuarioRolEntidad(new BigDecimal(rolAsignado.getCodUsuarioRolEntidad()));
			dep.setAudCodRol((int) getUsuarioSesion().getCodRol());
			dep.setAudFechaActualizacion(new Date());
			dep.setFechaFin(rolAsignado.getFechaFin());
			dep.setCodDependenciaEntidad(rolAsignado.getCodDependenciaEntidad());
			dep.setFechaInicio(rolAsignado.getFechaInicio());
			dep.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
			if (rolAsignado.getCodUsuarioRolDependencia() != null) {
				dep.setAudAccion(63);
				dep.setCodUsuarioRolDependencia(new BigDecimal(rolAsignado.getCodUsuarioRolDependencia()));
				dep.setCodDependenciaEntidad(rolAsignado.getCodDependenciaEntidad());
			} else {
				dep.setAudAccion(785);
			}
			dep = ComunicacionServiciosEnt.setUsuarioRolDependencia(dep);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_USUARIO_ROL_MODIFICADO);
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_USUARIO_ASOCIADO_NO_EXISTE);
		}
		RequestContext.getCurrentInstance().execute("PF('editarFecha').hide();");
	}

	/**
	 * 
	 * Elaborado Por: Jose Viscaya 12 feb. 2019 GestionarRolBean.java
	 * @param rolAsignado
	 * @param usuarioSeleccionado
	 * @throws SIGEP2SistemaException
	 * @throws IOException
	 */
	public void eliminarRolAsignadoUsuario(RolExt rolAsignado) throws SIGEP2SistemaException, IOException {
		try {
			UsuarioPasswordDTO usuario = IngresoSistemaDelegate.getUsuarioByLogin(tipoDocumento.getId(), numeroDocumento.toUpperCase());
			if (usuario != null) {
				UsuarioEntidadDTO usuarioEntidad = IngresoSistemaDelegate.consultarUsuarioEntidad(usuario.getId(), rolAsignado.getCodEntidad());
				if (usuarioEntidad != null) {
					UsuarioRolEntidadDTO usuarioAsociar = new UsuarioRolEntidadDTO(0L, rolAsignado.getCodRol().intValue(), usuarioEntidad.getId(), false, false);
					UsuarioDTO usuarioAuditoria = getUsuarioSesion();
					usuarioAuditoria.setCodRol(getRolAuditoria().getId());
					AdministracionDelegate.asociarRolUsuario(usuarioAsociar, usuarioAuditoria, String.valueOf(TipoParametro.AUDITORIA_DELETE.getValue()));
					eliminarUsuarioRolDependencia(usuarioEntidad.getUsuario(), rolAsignado.getCodRol().intValue());
					getRoles();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_USUARIO_ROL_ELIMINADO);
				} else {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_USUARIO_ASOCIADO_NO_EXISTE);
				}
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.BR_MSG_ELIMINACION_FALLIDA);
		}
	}

	/**
	 * 	Metodo utilizado para eliminar logicamente registro en el maestro usuario_rol_dependencia una vez se elimine un rol a un usuario
	 */
	public void eliminarUsuarioRolDependencia(long  objCodUsuario, long objCodRol ) {
		UsuarioRolDependencia usDep 					= new UsuarioRolDependencia();
		List<UsuarioRolDependencia> lstRolDependencia  	= new ArrayList<>();
		UsuarioRolDependencia usuarioRolDependencia 	= new UsuarioRolDependencia();
		UsuarioRolEntidad usEnt							= new UsuarioRolEntidad();
		usEnt = ComunicacionServiciosEnt.getUsuarioRolEntidadByRol(objCodUsuario, objCodRol);
		usuarioRolDependencia.setCodUsuarioRolEntidad(usEnt.getCodUsuarioRolEntidad());
		usuarioRolDependencia.setFlgActivo((short) 1);
		lstRolDependencia = ComunicacionServiciosEnt.getUsuarioRolDependencia(usuarioRolDependencia);
		if(!lstRolDependencia.isEmpty()) {
			usDep.setCodUsuarioRolDependencia(lstRolDependencia.get(0).getCodUsuarioRolDependencia());
			usDep.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
			usDep.setCodUsuarioRolEntidad(usEnt.getCodUsuarioRolEntidad());
			usDep.setAudCodRol(new BigDecimal(getRolAuditoria().getId()).intValue());
			usDep.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
			usDep.setAudFechaActualizacion(new Date());
			usDep.setFlgActivo((short) 0);
			usDep.setFechaInicio(lstRolDependencia.get(0).getFechaInicio() != null ? lstRolDependencia.get(0).getFechaInicio() : new Date() );
			usDep.setFechaFin(new Date());
			usDep = ComunicacionServiciosEnt.setUsuarioRolDependencia(usDep);
			
		}
	}
	
	/**
	 * Metodo que valida si el rol en sesion tiene permiso para ejecutar la
	 * <code>accion</code> el rol seleccionado. Se valida que el rol tecnico
	 * solo pueda eliminar los roles cuyo rol padre sea ADMINISTRADOR TECNICO,
	 * Se valida que el rol funcional solo pueda eliminar los roles cuyo rol
	 * padre sea ADMINISTRADOR FUNCIONAL,
	 * 
	 * @param rolSeleccionado
	 * @param accion
	 *            Indica la accion a validar sobre el rol de acuerdo a los
	 *            permisos del usuario
	 * @return boolean
	 * @throws SIGEP2SistemaException
	 */
	private boolean validaJerarquiaModificarRol(RolDTO rolSeleccionado, int accion) throws SIGEP2SistemaException {
		boolean permiso = true;

		if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR)) {
			return permiso;
		} else if (rolSeleccionado.getCodRolPadre() == null) {
			if ((usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL)
					&& rolSeleccionado.getAudCodRol().intValue() == COD_ADMIN_FUNC)
					|| (usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_TECNICO)
							&& rolSeleccionado.getAudCodRol().intValue() == COD_ADMIN_TEC)) {
				permiso = true;
			} else {
				permiso = false;
			}
		} else if (rolSeleccionado.getCodRolPadre() != null) {
			RolDTO rolPadreRolSeleccionado = ComunicacionServiciosSis
					.getRolPorId(rolSeleccionado.getCodRolPadre().longValue());
			if (rolPadreRolSeleccionado != null) {
				if ((usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL)
						&& !RolDTO.ADMINISTRADOR_FUNCIONAL.equalsIgnoreCase(rolPadreRolSeleccionado.getNombre()))
						|| (usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_TECNICO)
								&& !RolDTO.ADMINISTRADOR_TECNICO
										.equalsIgnoreCase(rolPadreRolSeleccionado.getNombre()))) {
					permiso = false;
				}
			} else {
				permiso = false;
			}
		}

		if (!permiso) {
			String bundle = "";
			switch (accion) {
			case ELIMINAR_ROL:
				bundle = MessagesBundleConstants.MSG_ROL_ELIMINAR_PERMISO;
				break;
			case ASIGNAR_JERARQUIA_ROL:
				bundle = MessagesBundleConstants.MSG_ROL_ASIGNAR_PERMISO;
			default:
				break;
			}
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, bundle);
		}

		return permiso;
	}

	/**
	 * Metodo utilizado para la lista de autocompletar en los filtros de
	 * busqueda
	 * 
	 * @param query
	 *            Texto a buscar de forma inflexiva dentro de atributo nombre
	 *            del rol
	 * @return {@link List} de {@link String} con las coincidencias
	 */
	public List<String> completeText(String query) {
		List<String> results = new LinkedList<>();

		if (query == null || query.isEmpty()) {
			return results;
		}

		int pageSize = ConfigurationBundleConstants.getpaginatorSize();

		RolLazyDataModel dataModel = new RolLazyDataModel(new RolDTO());
		dataModel.load(0, ConfigurationBundleConstants.getpaginatorSize(), "", SortOrder.ASCENDING,
				new HashMap<String, Object>());

		rol.setNombre(query);
		rol.setDescripcionRol(query);

		dataModel = new RolLazyDataModel(rol);
		List<RolDTO> listaRolTemp = dataModel.load(0, pageSize, "", SortOrder.ASCENDING, new HashMap<String, Object>());

		for (RolDTO rolSearch : listaRolTemp) {
			if (!results.contains(rolSearch.getNombre())) {
				results.add(rolSearch.getNombre());
			}
		}

		Collections.sort(results, new Comparator<String>() {
			@Override
			public int compare(String c1, String c2) {
				return c1.compareTo(c2);
			}
		});

		return results;
	}

	
	/**
	 * Genera la lista de roles base para la asignación de jerarquia, a
	 * excepcion de los roles 'SERVIDOR PUBLICO', 'CONTRATISTA', 'SISTEMA',
	 * 'InterOperatibilidad' los cuales son roles de gestion del sistema
	 * 
	 * @return {@link List} de {@link SelectItem}
	 */
	public void cargarRolesBase() {
		lstRolBase = new ArrayList<>();

		try {
			if (this.rol.getId() == 0) {
				return;
			}
						
			boolean tieneRolEsperado = false;
			for (RolDTO rolDTO : getRolesUsuarioSesion()) {
				if( RolDTO.ADMINISTRADOR_FUNCIONAL.equalsIgnoreCase(rolDTO.getNombre()) || RolDTO.SUPER_ADMINISTRADOR.equalsIgnoreCase(rolDTO.getNombre())) {
					tieneRolEsperado= true;
					break;
				}
			}
			
			if(!tieneRolEsperado) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ROL_ASIGNAR_PERMISO);
			}
			
			/*
			RolDTO rol = ComunicacionServiciosSis.getRolPorId(this.rol.getId());
			if (rol.isFlgRolBase()) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_ROL_ASIGNAR_PERMISO);
				return;
			}*/

			BigDecimal codRol = BigDecimal.valueOf(rol.getId());
			BigDecimal codRolPadre = rol.getCodRolPadre() != null ? rol.getCodRolPadre() : BigDecimal.ZERO;

			RolDTO rolPadreActual = null;
			if (!BigDecimal.ZERO.equals(codRolPadre)) {
				rolPadreActual = ComunicacionServiciosSis.getRolPorId(codRolPadre.longValue());
			}

			if (BigDecimal.ZERO.equals(codRolPadre)) {
				List<Rol> listP = ComunicacionServiciosSis.getRolBase();
				if (!listP.isEmpty()) {
					for (Rol aux : listP) {
						if (!codRol.equals(aux.getCodRol()) && !codRolPadre.equals(aux.getCodRol())) {
							if (RolDTO.SUPER_ADMINISTRADOR.equals(aux.getNombre())) {
								if ((usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR))) {
									lstRolBase.add(new SelectItem(aux.getCodRol(), aux.getNombre() + " - "
											+ CapitalCaseConverter.convert(aux.getDescripcionRol())));
								}
								continue;
							} else if (RolDTO.ADMINISTRADOR_FUNCIONAL.equals(aux.getNombre())) {
								if ((usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL))) {
									lstRolBase.add(new SelectItem(aux.getCodRol(), aux.getNombre() + " - "
											+ CapitalCaseConverter.convert(aux.getDescripcionRol())));
								}
								continue;
							}
							lstRolBase.add(new SelectItem(aux.getCodRol(),
									aux.getNombre() + " - " + CapitalCaseConverter.convert(aux.getDescripcionRol())));
						}
					}
				}
			} else {
				List<RolDTO> rolesDAFP = new LinkedList<>();
				RolDTO nuevoNodo = new RolDTO();
				nuevoNodo.setId(rolPadreActual.getId());
				nuevoNodo.setNombre(rolPadreActual.getNombre());

				generarArbolJerarquia(rolesDAFP, nuevoNodo);

				for (RolDTO aux : rolesDAFP) {
					if (!codRol.equals(BigDecimal.valueOf(aux.getId()))
							&& !codRolPadre.equals(BigDecimal.valueOf(aux.getId()))) {
						lstRolBase.add(new SelectItem(BigDecimal.valueOf(aux.getId()), aux.getNombre().toUpperCase()
								+ " - " + CapitalCaseConverter.convert(aux.getDescripcionRol())));
					}
				}
			}

			Collections.sort(lstRolBase, new Comparator<SelectItem>() {
				@Override
				public int compare(SelectItem c1, SelectItem c2) {
					return c1.getLabel().compareTo(c2.getLabel());
				}
			});
		} catch (Exception e) {
			lstRolBase = new ArrayList<>();
		}
	}

	/**
	 * Genera el arbol de jerarquia a partir de un nodo
	 * 
	 * @param arbolJerarquico
	 *            Lista con la estructura jerarquica
	 * @param nuevoNodo
	 *            Nodo a partir del cual se inicia la jerarquia
	 * @return {@link List} de {@link RolDTO} con la jerarquia incrementada por
	 *         <code>nuevoNodo</code>
	 */
	private List<RolDTO> generarArbolJerarquia(List<RolDTO> arbolJerarquico, RolDTO nuevoNodo) {
		List<RolDTO> rolesHijo = ComunicacionServiciosSis.getRolesPorPadreId(nuevoNodo.getId());
		for (RolDTO rolHijo : rolesHijo) {
			if (rolHijo.isFlgRolBase()) {
				if (!arbolJerarquico.contains(rolHijo)) {
					arbolJerarquico.add(rolHijo);
				}
				generarArbolJerarquia(arbolJerarquico, rolHijo);
			}
		}
		return arbolJerarquico;
	}
	
	/**
	 * Evento utilizado para detectar si se selecciono la opcion de la tabla: todas las entidades.
	 * @param tse
	 */
    public void toggleSelected(ToggleSelectEvent tse) {
    	entidadesSeleccionadas = new ArrayList<>();
    	if(tse.isSelected() && lstEntidades .size()>1) {
        	entidadesSeleccionadas.addAll(lstEntidades);
        } 
    }
	
	/*Metodos get y set*/
	
	/**
	 * @return the lstEntidades
	 */
	public List<EntidadExt> getLstEntidades() {
		return lstEntidades;
	}

	/**
	 * @param lstEntidades the lstEntidades to set
	 */
	public void setLstEntidades(List<EntidadExt> lstEntidades) {
		this.lstEntidades = lstEntidades;
	}

	/**
	 * @return the usuarioVariasEntidades
	 */
	public Usuario getUsuarioVariasEntidades() {
		return usuarioVariasEntidades;
	}

	/**
	 * @param usuarioVariasEntidades the usuarioVariasEntidades to set
	 */
	public void setUsuarioVariasEntidades(Usuario usuarioVariasEntidades) {
		this.usuarioVariasEntidades = usuarioVariasEntidades;
	}

	public String getNumeroId() {
		return numeroId;
	}

	public void setNumeroId(String numeroId) {
		this.numeroId = numeroId;
	}
	
	public RolDTO getRol() {
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}

	public RolDTO getRolPadre() {
		return rolPadre;
	}

	public void setRolPadre(RolDTO rolPadre) {
		this.rolPadre = rolPadre;
	}

	public List<RolDTO> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<RolDTO> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public TipoDocumentoDTO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public EntidadDTO getEntidad() {
		return entidad;
	}

	public void setEntidad(EntidadDTO entidad) {
		this.entidad = entidad;
	}

	public boolean isHabilitarFormulario() {
		return habilitarFormulario;
	}

	public void setHabilitarFormulario(boolean habilitarFormulario) {
		this.habilitarFormulario = habilitarFormulario;
	}

	public boolean isPermiteEditarEntidad() {
		return permiteEditarEntidad;
	}

	public void setPermiteEditarEntidad(boolean permiteEditarEntidad) {
		this.permiteEditarEntidad = permiteEditarEntidad;
	}

	public List<DependenciaEntidad> getDependencias() {
		return dependencias;
	}

	public void setDependencias(List<DependenciaEntidad> dependencias) {
		this.dependencias = dependencias;
	}

	public LazyDataModel<RolDTO> getListaRol() {
		return listaRol;
	}

	public void setlistaRol(LazyDataModel<RolDTO> listaRol) {
		this.listaRol = listaRol;
	}

	public String longToString(long id) {
		return id + "";
	}

	/**
	 * @return the dependencia
	 */
	public long getDependencia() {
		return dependencia;
	}

	/**
	 * @param dependencia
	 *            the dependencia to set
	 */
	public void setDependencia(long dependencia) {
		this.dependencia = dependencia;
	}

	/**
	 * @return the usuarioRoEntidad
	 */
	public UsuarioRolEntidad getUsuarioRoEntidad() {
		return usuarioRoEntidad;
	}

	/**
	 * @param usuarioRoEntidad
	 *            the usuarioRoEntidad to set
	 */
	public void setUsuarioRoEntidad(UsuarioRolEntidad usuarioRoEntidad) {
		this.usuarioRoEntidad = usuarioRoEntidad;
	}

	public void setLstRolBase(List<SelectItem> lstRolBase) {
		this.lstRolBase = lstRolBase;
	}

	public List<SelectItem> getLstRolBase() {
		return lstRolBase;
	}

	public boolean isHabilitarDetalleJerarquia() {
		return habilitarDetalleJerarquia;
	}

	public void setHabilitarDetalleJerarquia(boolean habilitarDetalleJerarquia) {
		this.habilitarDetalleJerarquia = habilitarDetalleJerarquia;
	}

	public List<RolDTO> getJerarquiaRolesDetalle() {
		return jerarquiaRolesDetalle;
	}

	public void setJerarquiaRolesDetalle(List<RolDTO> jerarquiaRolesDetalle) {
		this.jerarquiaRolesDetalle = jerarquiaRolesDetalle;
	}

	public RolDTO getDetalleRolJerarquiaParam() {
		return detalleRolJerarquiaParam;
	}

	public void setDetalleRolJerarquiaParam(RolDTO detalleRolJerarquiaParam) {
		this.detalleRolJerarquiaParam = detalleRolJerarquiaParam;
	}
	
	/**
	 * @return the listaRolesByUser
	 */
	public List<RolExt> getListaRolesByUser() {
		return listaRolesByUser;
	}

	/**
	 * @param listaRolesByUser
	 *            the listaRolesByUser to set
	 */
	public void setListaRolesByUser(List<RolExt> listaRolesByUser) {
		this.listaRolesByUser = listaRolesByUser;
	}

	/**
	 * @return the rolAsignado
	 */
	public RolExt getRolAsignado() {
		return rolAsignado;
	}

	/**
	 * @param rolAsignado
	 *            the rolAsignado to set
	 */
	public void setRolAsignado(RolExt rolAsignado) {
		this.rolAsignado = rolAsignado;
	}

	/**
	 * @return the usuarioSeleccionado
	 */
	public PersonaDTO getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	/**
	 * @param usuarioSeleccionado
	 *            the usuarioSeleccionado to set
	 */
	public void setUsuarioSeleccionado(PersonaDTO usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	/**
	 * @return the blnPermisoBtnSelectEnt
	 */
	public Boolean getBlnPermisoBtnSelectEnt() {
		return blnPermisoBtnSelectEnt;
	}

	/**
	 * @param blnPermisoBtnSelectEnt
	 *            the blnPermisoBtnSelectEnt to set
	 */
	public void setBlnPermisoBtnSelectEnt(Boolean blnPermisoBtnSelectEnt) {
		this.blnPermisoBtnSelectEnt = blnPermisoBtnSelectEnt;
	}
	/**
	 * @return the entidadesSeleccionadas
	 */
	public List<EntidadExt> getEntidadesSeleccionadas() {
		return entidadesSeleccionadas;
	}

	/**
	 * @param entidadesSeleccionadas
	 * the entidadesSeleccionadas to set
	 */
	public void setEntidadesSeleccionadas(List<EntidadExt> entidadesSeleccionadas) {
		this.entidadesSeleccionadas = entidadesSeleccionadas;
	}
	
	public RolDTO getRolSeleccionado() {
		return rolSeleccionado;
	}

	public void setRolSeleccionado(RolDTO rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

	public RolDTO getRolReasignar() {
		return rolReasignar;
	}

	public void setRolReasignar(RolDTO rolReasignar) {
		this.rolReasignar = rolReasignar;
	}

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	public boolean isHabilitarFormularioCrear() {
		return habilitarFormularioCrear;
	}

	public void setHabilitarFormularioCrear(boolean habilitarFormularioCrear) {
		this.habilitarFormularioCrear = habilitarFormularioCrear;
	}

	/**
	 * @return the rolIdPadre
	 */
	public BigDecimal getRolIdPadre() {
		return rolIdPadre;
	}

	/**
	 * @param rolIdPadre
	 * the rolIdPadre to set
	 */
	public void setRolIdPadre(BigDecimal rolIdPadre) {
		this.rolIdPadre = rolIdPadre;
	}

	/**
	 * @return the fechaIncio
	 */
	public Date getFechaIncio() {
		return fechaIncio;
	}

	/**
	 * @param fechaIncio
	 * the fechaIncio to set
	 */
	public void setFechaIncio(Date fechaIncio) {
		this.fechaIncio = fechaIncio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 * the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the today
	 */
	public Date getToday() {
		return today;
	}

	/**
	 * @param today
	 * the today to set
	 */
	public void setToday(Date today) {
		this.today = today;
	}

	/**
	 * @return the tIPO_DOC_PASAPORTE
	 */
	public Integer getTIPO_DOC_PASAPORTE() {
		return TIPO_DOC_PASAPORTE;
	}

	/**
	 * @param tIPO_DOC_PASAPORTE
	 *            the tIPO_DOC_PASAPORTE to set
	 */
	public void setTIPO_DOC_PASAPORTE(Integer tIPO_DOC_PASAPORTE) {
		TIPO_DOC_PASAPORTE = tIPO_DOC_PASAPORTE;
	}
	
	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario
	 *            the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the verEntidades
	 */
	public boolean isVerEntidades() {
		return verEntidades;
	}

	/**
	 * @param verEntidades
	 * the verEntidades to set
	 */
	public void setVerEntidades(boolean verEntidades) {
		this.verEntidades = verEntidades;
	}

	/**
	 * @return the codigoSigep
	 */
	public String getCodigoSigep() {
		return codigoSigep;
	}

	/**
	 * @param codigoSigep
	 * the codigoSigep to set
	 */
	public void setCodigoSigep(String codigoSigep) {
		this.codigoSigep = codigoSigep;
	}

	/**
	 * @return the nombreEntidad
	 */
	public String getNombreEntidad() {
		return nombreEntidad;
	}

	/**
	 * @param nombreEntidad
	 * the nombreEntidad to set
	 */
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public void rowSelectListener(SelectEvent evt) {
		entidadesSeleccionadas.add((EntidadExt) evt.getObject());
	}

	public void rowUnselectListener(UnselectEvent evt) {
		entidadesSeleccionadas.remove((Entidad) evt.getObject());
	}
}