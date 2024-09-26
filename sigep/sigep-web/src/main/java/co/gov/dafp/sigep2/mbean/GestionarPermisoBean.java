package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.model.LazyDataModel;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.datamodel.PermisoLazyDataModel;
import co.gov.dafp.sigep2.entity.RecursoDTO;
import co.gov.dafp.sigep2.entity.seguridad.RecursoAccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.DateUtils;

/**
 * Controlador de la vista <code>gestionarPermisos.xhtml</code> para la gestion
 * de permisos de recursos del sistema
 */
@Named
@ConversationScoped
@ManagedBean
public class GestionarPermisoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -8999117806627139841L;

	private static final  short ESTADO_ACTIVO 	= 1;
	/**
	 * Instancia el rol segun el paramentro de vista <code>id</code>
	 */
	private RolDTO rol;

	/**
	 * Administra el <code>lazy</code> de la tabla de la vista
	 */
	private LazyDataModel<RecursoAccionDTO> listaRecursos;

	/**
	 * Mantiene los registros seleccionados desde el <code>dataTable</code> en
	 * la vista
	 */
	private List<RecursoAccionDTO> recursosSeleccionados;

	/**
	 * Indica si se habilitan los botones de <code>Guardar</code> y
	 * <code>Cancelar</code> en la vista despues de detectado un cambio.
	 * <code>true</code> habilita los botones <code>false</code> los mantiene
	 * deshabilitados
	 */
	private boolean habilitarGuardar = false;

	public RolDTO getRol() {
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}

	public LazyDataModel<RecursoAccionDTO> getListaRecursos() {
		return listaRecursos;
	}

	public void setListaRecursos(LazyDataModel<RecursoAccionDTO> listaRecursos) {
		this.listaRecursos = listaRecursos;
	}

	public List<RecursoAccionDTO> getRecursosSeleccionados() {
		return recursosSeleccionados;
	}

	public void setRecursosSeleccionados(List<RecursoAccionDTO> recursosSeleccionados) {
		this.recursosSeleccionados = recursosSeleccionados;
	}

	public boolean isHabilitarGuardar() {
		return habilitarGuardar;
	}

	public void setHabilitarGuardar(boolean habilitarGuardar) {
		this.habilitarGuardar = habilitarGuardar;
	}

	/**
	 * Ejecuta la primera consulta una vez es accedida a la vista
	 * <code>gestionarPermisos.xhtml</code>. Valida ademas las restricciones de
	 * seguridad en cuanto al consumo de esta funcionalidad
	 */
	public void printTable() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL,
					RolDTO.ADMINISTRADOR_TECNICO)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}
		
		listaRecursos = new PermisoLazyDataModel(rol, recurso);
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	private boolean noEdit;

	@PostConstruct
	public void init() {
		noEdit = false;
		listaRecursos = new PermisoLazyDataModel(rol, recurso);
		
	}

	/**
	 * Maneja la persistencia de datos a traves de los microservicios
	 * 
	 * @return <code>null</code>
	 */
	@Override
	public String persist() throws NotSupportedException {
		try {
			if (usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL,
					RolDTO.ADMINISTRADOR_TECNICO)) {

				boolean procesoConError = false;

				// crear permiso
				for (RecursoAccionDTO recurso : this.recursosSeleccionados) {
					// Se valida que solo ADMINFUNC y ADMINTEC se puedan
					// modificar exclusivamente sus propios permisos
					if (!tienePermisoModificarRol(recurso.getCodRol().longValue())) {
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
						return MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO;
					}

					if (recurso.isFlgAccion()) {
						PermisoRolAccion permisoRolAccion = new PermisoRolAccion();
						// Valores auditoria
						permisoRolAccion.setAudAccion(BigInteger.ONE);
						permisoRolAccion.setAudCodRol(BigInteger.valueOf(getRolAuditoria().getId()));
						permisoRolAccion.setAudCodUsuario(BigInteger.valueOf(getUsuarioSesion().getId()));
						permisoRolAccion.setAudFechaActualizacion(DateUtils.getFechaSistema());
						// Valores recurso
						permisoRolAccion.setCodPermisoRol(recurso.getCodPermisoRol());
						permisoRolAccion.setCodPermisoRolAcciones(recurso.getCodPermisoRolAcciones());
						if (permisoRolAccion.getCodPermisoRolAcciones() == null) {
							permisoRolAccion.setCodPermisoRolAcciones(BigInteger.ONE.negate());
						}
						permisoRolAccion.setCodRecursoAccion(recurso.getCodRecursoAccion());
						permisoRolAccion.setFlgActivo(recurso.isFlgEstado());

						if (permisoRolAccion.getCodPermisoRolAcciones().equals(BigInteger.ZERO)
								|| permisoRolAccion.getCodPermisoRolAcciones().equals(BigInteger.ONE.negate())) {
							PermisoRol nuevo = new PermisoRol();
							nuevo.setCodRecurso(BigInteger.valueOf(recurso.getId()));
							nuevo.setCodRol(BigInteger.valueOf(rol.getId()));
							nuevo.setFlgEstado(true);

							nuevo.setAudCodRol(permisoRolAccion.getAudCodRol().intValue());
							nuevo.setAudCodUsuario(permisoRolAccion.getAudCodUsuario().longValue());
							nuevo.setAudFechaActualizacion(DateUtils.getFechaSistema());

							nuevo = (PermisoRol) ComunicacionServiciosSis.procesarPermiso(nuevo);

							permisoRolAccion.setCodPermisoRol(nuevo.getCodPermisoRol());
						}

						permisoRolAccion = (PermisoRolAccion) ComunicacionServiciosSis
								.procesarPermiso(permisoRolAccion);

						if (!procesoConError && permisoRolAccion.isError()) {
							procesoConError = true;
						}
					} else {
						PermisoRol permisoRol = new PermisoRol();
						// Valores auditoria
						permisoRol.setAudAccion(1);
						permisoRol.setAudCodRol(Integer.valueOf(String.valueOf(getRolAuditoria().getId())));
						permisoRol.setAudCodUsuario(getUsuarioSesion().getId());
						permisoRol.setAudFechaActualizacion(DateUtils.getFechaSistema());
						// Valores recurso
						permisoRol.setCodPermisoRol(recurso.getCodPermisoRol());
						permisoRol.setCodRecurso(BigInteger.valueOf(recurso.getId()));
						permisoRol.setCodRol(recurso.getCodRol());
						permisoRol.setFlgEstado(recurso.isFlgEstado());

						permisoRol = (PermisoRol) ComunicacionServiciosSis.procesarPermiso(permisoRol);

						if (!procesoConError && permisoRol.isError()) {
							procesoConError = true;
						}
					}
				}

				if (!procesoConError) {
					if (habilitarGuardar) {
						/*finalizarConversacion(false, MessagesBundleConstants.MSG_INFO_PERMISO_CONFIRM, "id=" + this.id);
						return MessagesBundleConstants.MSG_INFO_PERMISO_CONFIRM;
						*/
						mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.MSG_INFO_PERMISO_CONFIRM);
						return MessagesBundleConstants.MSG_INFO_PERMISO_CONFIRM;
						
						
						
						
					} else {
						finalizarConversacion(false, null, "id=" + this.id);
						return null;
					}
				} else {
					finalizarConversacion(false, MessagesBundleConstants.MSG_WARN_PERMISO_CONFIRM, "id=" + this.id);
					return MessagesBundleConstants.MSG_WARN_PERMISO_CONFIRM;
				}
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO;
			}
		} catch (

		Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return MessagesBundleConstants.DLG_PROCESO_FALLIDO;
		}
	}

	/**
	 * Realiza el alistamiento de la vista de forma predeterminada
	 * 
	 * @throws NotSupportedException
	 *             En caso de no ser implementado, devolver la excepcion
	 */
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
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_URL_INVALID);
		}
		if (id != null) {
			// Se valida que solo ADMINFUNC y ADMINTEC se puedan
			// modificar exclusivamente sus propios permisos
			if (!tienePermisoModificarRol(id)) {
				abrirAccion(id, "gestionarRol.xhtml",
						"dialog=" + MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
			rol = ComunicacionServiciosSis.getRolPorId(id);

			if (rol != null) {
				if (recurso == null) {
					this.init();
				}
			}
		}
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	/**
	 * Metodo para hacer back hacia la grilla de consulta de permisos
	 */
	@Override
	public void cancelar() {
		try {
			finalizarConversacion(false, null, "id=" + this.id);
		} catch (IOException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	/**
	 * Metodo para hacer back hacia la grilla de consulta de roles
	 */
	public void home() {
		try {
			finalizarConversacion("/rol/gestionarRol.xhtml", null, null);
		} catch (IOException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	/**
	 * Valida, adiciona o remueve un registro de la grilla a la lista de
	 * recursos seleccionados por el usuario para su respectivo ajuste
	 * 
	 * @param id
	 *            Corresponde al {@link RecursoDTO#getId()}. Registro
	 *            seleccionado en el <code>dataTable</code> de la vista
	 *            <code>gestionarPermisos.xhtml</code>
	 */
	public void procesarRecurso(long id) {
		if (this.recursosSeleccionados == null) {
			this.recursosSeleccionados = new LinkedList<>();
		}
		RecursoAccionDTO seleccion = new RecursoAccionDTO();
		seleccion.setId(id);

		int index = this.recursosSeleccionados.indexOf((seleccion));
		List<RecursoAccionDTO> resultadoConsulta = ((PermisoLazyDataModel) listaRecursos).getListaRecursos();
		if (index >= 0) {
			seleccion = this.recursosSeleccionados.get(index);
			seleccion.setFlgEstado(!seleccion.isFlgEstado());

			RecursoAccionDTO itemConsulta = new RecursoAccionDTO();
			itemConsulta.setId(id);
			int indexConsulta = resultadoConsulta.indexOf(itemConsulta);
			boolean estadosIguales = false;
			if (indexConsulta >= 0) {
				itemConsulta = resultadoConsulta.get(indexConsulta);
				estadosIguales = Boolean.logicalOr(
						Boolean.logicalAnd(seleccion.isFlgEstado(), itemConsulta.isFlgEstado()),
						Boolean.logicalAnd(!seleccion.isFlgEstado(), !itemConsulta.isFlgEstado()));

				if (estadosIguales) {
					this.recursosSeleccionados.remove(seleccion);
				}
			}

			if (!estadosIguales) {
				this.recursosSeleccionados.add(seleccion);
				if (this.recursosSeleccionados.size() > 1) {
					this.recursosSeleccionados.remove(index);
				}
			}
		} else {
			index = resultadoConsulta.indexOf(seleccion);
			if (index >= 0) {
				seleccion = resultadoConsulta.get(index);

				this.recursosSeleccionados.add(seleccion);
			}
		}

		if (!this.recursosSeleccionados.isEmpty()) {
			this.habilitarGuardar = true;
		} else {
			this.habilitarGuardar = false;
		}
	}

	/**
	 * @return the noEdit
	 */
	public boolean isNoEdit() {
		return noEdit;
	}

	/**
	 * @param noEdit
	 *            the noEdit to set
	 */
	public void setNoEdit(boolean noEdit) {
		this.noEdit = noEdit;
	}

	/**
	 * Determina si el usuario tiene permiso para modificar el rol y el acceso a
	 * sus funcionalidades
	 * 
	 * @param id
	 *            Identificador del rol a gestionar o modificar
	 * 
	 * @return {@link Boolean} indica si el usuario puede gestionar el rol en
	 *         referencia
	 */
	public boolean tienePermisoModificarRol(Long id) {
		try {
			if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR)) {
				return true;
			}
            
			RolDTO rolGestionar = ComunicacionServiciosSis.getRolPorId(id);

			boolean isAdmonFunc = usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL);
			boolean isAdmonTec = usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_TECNICO);

			RolDTO rolPadre = null;
			if (rolGestionar.getCodRolPadre() != null) {
				rolPadre = ComunicacionServiciosSis.getRolPorId(rolGestionar.getCodRolPadre().longValue());
			}

			// Se valida que solo ADMINFUNC y ADMINTEC se puedan
			// modificar exclusivamente sus propios permisos
			/*if (isAdmonFunc && rolGestionar.isFlgRolBase()
					&& RolDTO.ADMINISTRADOR_FUNCIONAL.equalsIgnoreCase(rolGestionar.getNombre())) {
				return true;
			} else if (isAdmonFunc && rolPadre == null && !rolGestionar.isFlgRolBase()
					&& rolGestionar.getAudCodRol().longValue() == getRolAuditoria().getId()
					&& RolDTO.ADMINISTRADOR_FUNCIONAL.equalsIgnoreCase(getRolAuditoria().getNombre())) {
				return true;
			} else if (isAdmonFunc && rolPadre != null && !rolGestionar.isFlgRolBase()
					&& RolDTO.ADMINISTRADOR_FUNCIONAL.equalsIgnoreCase(rolPadre.getNombre())) {
				return true;
			}  
			*/
			if (isAdmonFunc) {
				return true;
			}
			
			if (isAdmonTec && rolGestionar.isFlgRolBase()
					&& RolDTO.ADMINISTRADOR_TECNICO.equalsIgnoreCase(rolGestionar.getNombre())) {
				return true;
			} else if (isAdmonTec && rolPadre != null && !rolGestionar.isFlgRolBase()
					&& RolDTO.ADMINISTRADOR_TECNICO.equalsIgnoreCase(rolPadre.getNombre())) {
				return true;
			}else if(validarPermisoAsignarRol(id)) {
				return true;
			}
			rol = rolGestionar;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	
	/**
	 * Metodo que valida si el usuario en sesion tiene permiso para Asignar 
	 * un rol
	 * */
	public boolean validarPermisoAsignarRol(Long id) {
		List<RolDTO> lstRolesHijos = ComunicacionServiciosSis.getRolesHijos(this.getRolAuditoria().getId(), (int) ESTADO_ACTIVO);
		
		for (RolDTO rolAux : lstRolesHijos) {
			if (rolAux.getId() == id) {
				return true;
			}
		}
		
		return false;
	}
}