package co.gov.dafp.sigep2.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.entity.MunicipioDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.ParametricaDTO;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Rol;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;
import co.gov.dafp.sigep2.entity.jpa.seguridad.UsuarioEntidad;
import co.gov.dafp.sigep2.entity.jpa.seguridad.UsuarioRolEntidad;
import co.gov.dafp.sigep2.entity.seguridad.AuditoriaConfiguracionDTO;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioRolEntidadDTO;
import co.gov.dafp.sigep2.entity.view.AccionAuditoriaDTO;
import co.gov.dafp.sigep2.entity.view.AreaConocimientoDTO;
import co.gov.dafp.sigep2.entity.view.ClaseLibretaMilitarDTO;
import co.gov.dafp.sigep2.entity.view.EstadoCivilDTO;
import co.gov.dafp.sigep2.entity.view.GeneroDTO;
import co.gov.dafp.sigep2.entity.view.NivelEducativoDTO;
import co.gov.dafp.sigep2.entity.view.PoblacionEtnicaDTO;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.entity.view.TipoEntidadDTO;
import co.gov.dafp.sigep2.entity.view.TipoZonaDTO;
import co.gov.dafp.sigep2.factoria.AccionAuditoriaFactoria;
import co.gov.dafp.sigep2.factoria.AreaConocimientoFactoria;
import co.gov.dafp.sigep2.factoria.AuditoriaConfiguracionFactoria;
import co.gov.dafp.sigep2.factoria.AuditoriaFactoria;
import co.gov.dafp.sigep2.factoria.ClaseLibretaMilitarFactoria;
import co.gov.dafp.sigep2.factoria.DepartamentoFactoria;
import co.gov.dafp.sigep2.factoria.EntidadFactoria;
import co.gov.dafp.sigep2.factoria.EstadoCivilFactoria;
import co.gov.dafp.sigep2.factoria.GeneroFactoria;
import co.gov.dafp.sigep2.factoria.MunicipioFactoria;
import co.gov.dafp.sigep2.factoria.NivelEducativoFactoria;
import co.gov.dafp.sigep2.factoria.PaisFactoria;
import co.gov.dafp.sigep2.factoria.ParametricaFactoria;
import co.gov.dafp.sigep2.factoria.PersonaFactoria;
import co.gov.dafp.sigep2.factoria.PoblacionEtnicaFactoria;
import co.gov.dafp.sigep2.factoria.RolFactoria;
import co.gov.dafp.sigep2.factoria.TipoAsociacionFactoria;
import co.gov.dafp.sigep2.factoria.TipoDocumentoFactoria;
import co.gov.dafp.sigep2.factoria.TipoEntidadFactoria;
import co.gov.dafp.sigep2.factoria.TipoZonaFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioEntidadFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioRolEntidadFactoria;
import co.gov.dafp.sigep2.interfaces.IAdministracionRemote;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2NegocioException;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.view.Genero;
import co.gov.dafp.sigep2.view.TipoAsociacion;
import co.gov.dafp.sigep2.view.TipoDocumento;

/**
 * @author LuiFer
 *
 */
/**
 * @author LuiFer
 *
 */
/**
 * @author LuiFer
 *
 */
/**
 * @author LuiFer
 *
 */
@Stateless
public class AdministracionEJB implements IAdministracionRemote {

	private static final long serialVersionUID = 5662350904891047133L;

	transient Logger logger = Logger.getInstance(AdministracionEJB.class);

	@EJB
	TipoDocumentoFactoria tipoDocumentoFactoria;

	@EJB
	GeneroFactoria generoFactoria;

	@EJB
	ClaseLibretaMilitarFactoria claseLibretaMilitarFactoria;

	@EJB
	TipoAsociacionFactoria tipoAsociacionFactoria;

	@EJB
	TipoEntidadFactoria tipoEntidadFactoria;

	@EJB
	AuditoriaFactoria auditoriaFactoria;
	
	@EJB
	private ParametricaFactoria parametricaFactoria;

	@EJB
	AuditoriaConfiguracionFactoria auditoriaConfiguracionFactoria;

	@EJB
	PaisFactoria paisFactoria;

	@EJB
	DepartamentoFactoria departamentoFactoria;

	@EJB
	MunicipioFactoria municipioFactoria;

	@EJB
	AccionAuditoriaFactoria accionAuditoriaFactoria;

	@EJB
	EstadoCivilFactoria estadoCivilFactoria;

	@EJB
	PoblacionEtnicaFactoria poblacionEtnicaFactoria;

	@EJB
	TipoZonaFactoria tipoZonaFactoria;

	@EJB
	NivelEducativoFactoria nivelEducativoFactoria;

	@EJB
	AreaConocimientoFactoria areaConocimientoFactoria;

	@EJB
	private EntidadFactoria entidadFactoria;

	@EJB
	private RolFactoria rolFactoria;

	@EJB
	private UsuarioRolEntidadFactoria usuarioRolEntidadFactoria;

	@EJB
	private UsuarioEntidadFactoria usuarioEntidadFactoria;

	@EJB
	private UsuarioFactoria usuarioFactoria;

	@EJB
	private PersonaFactoria personaFactoria;

	public List<TipoDocumentoDTO> findTipoDocumento() {
		return tipoDocumentoFactoria.findTipoDocumento();
	}

	@Override
	public List<GeneroDTO> findGenero() {
		return generoFactoria.findGenero();
	}

	@Override
	public List<ClaseLibretaMilitarDTO> findClaseLibretaMilitar() {
		return claseLibretaMilitarFactoria.findClaseLibretaMilitar();
	}

	@Override
	public List<TipoAsociacionDTO> findTipoAsociacion() {
		return tipoAsociacionFactoria.findTipoAsociacion();
	}

	@Override
	public TipoAsociacionDTO findTipoAsociacion(long codTipoAsociacion) {
		TipoAsociacion ta = tipoAsociacionFactoria.find(codTipoAsociacion);
		if (ta != null) {
			return (TipoAsociacionDTO) ta.getDTO();
		} else {
			return null;
		}
	}

	@Override
	public List<TipoEntidadDTO> findTipoEntidad() {
		return tipoEntidadFactoria.findTipoEntidad();
	}

	@Override
	public List<AuditoriaConfiguracionDTO> findAuditoriaConfiguracion() {
		return auditoriaFactoria.findAuditoriaConfiguracion();
	}

	@Override
	public List<PaisDTO> findPais() {
		return paisFactoria.findPais();
	}

	@Override
	public List<DepartamentoDTO> findDepartamento() {
		return departamentoFactoria.findDepartamento();
	}

	@Override
	public List<MunicipioDTO> findMunicipio() {
		return municipioFactoria.findMunicipio();
	}

	@Override
	public List<TipoZonaDTO> findTipoZona() {
		return tipoZonaFactoria.findTipoZona();
	}

	@Override
	public ClaseLibretaMilitarDTO findClaseLibretaMilitarId(long id) {
		ClaseLibretaMilitarDTO claseLibretaMilitar = (ClaseLibretaMilitarDTO) claseLibretaMilitarFactoria.find(id)
				.getDTO();
		return claseLibretaMilitar;
	}

	@Override
	public GeneroDTO findGeneroId(long id) {
		Genero genero = generoFactoria.find(id);
		if (genero != null) {
			return (GeneroDTO) genero.getDTO();
		}
		return null;
	}

	@Override
	public TipoDocumentoDTO findTipoDocumentoId(long id) {
		try {
			TipoDocumento tipoDocumento = tipoDocumentoFactoria.find(id);
			if (tipoDocumento != null) {
				return (TipoDocumentoDTO) tipoDocumento.getDTO();
			} else {
				return null;
			}
		} catch (NoResultException e) {
			logger.error("No se encontraron resultados tipo documento", e);
			return null;
		}
	}

	public PaisDTO encontrarPaisPorId(long id) {
		try {
			PaisDTO pais = paisFactoria.encontrarPaisPorId(id);
			return pais;
		} catch (NoResultException e) {
			logger.error("No se encontraron resultados tipo documento", e);
			return null;
		}
	}

	@Override
	public boolean actualizarAuditoriaConfiguracion(AuditoriaConfiguracionDTO auditoria) throws SIGEP2NegocioException {
		return auditoriaConfiguracionFactoria.actualizarAuditoriaConfiguracion(auditoria);

	}

	@Override
	public List<AccionAuditoriaDTO> findAccionAuditoria() {
		return accionAuditoriaFactoria.findAccionAuditoria();
	}

	@Override
	public List<EstadoCivilDTO> findEstadoCivil() {
		return estadoCivilFactoria.findEstadoCivil();
	}

	@Override
	public List<PoblacionEtnicaDTO> findPoblacionEtnica() {
		return poblacionEtnicaFactoria.findPoblacionEtnica();
	}

	@Override
	public List<AuditoriaConfiguracionDTO> findAuditoriaConfiguracion(int first, int pageSize) {
		return auditoriaFactoria.findAuditoriaConfiguracion(first, pageSize);
	}

	@Override
	public int findAuditoriaConfiguracionTotalRows() {
		return auditoriaFactoria.findAuditoriaConfiguracionTotalRows();
	}

	@Override
	public List<NivelEducativoDTO> findNivelEducativo() {
		return nivelEducativoFactoria.findNivelEducativo();
	}

	@Override
	public List<AreaConocimientoDTO> findAreaConocimiento() {
		return areaConocimientoFactoria.findAreaConocimiento();
	}

	@Override
	public List<EntidadDTO> obtenerDependenciasEntidades(Long entidad) throws SIGEP2SistemaException {
		return entidadFactoria.obtenerDependenciasEntidades(entidad);
	}

	@Override
	public EntidadDTO obtenerEntidadPorNombre(String nombreEntidad) throws SIGEP2SistemaException {
		return entidadFactoria.obtenerEntidadPorNombre(nombreEntidad);
	}

	public List<RolDTO> obtenerRolesPorDescripcion(String... nombre) throws SIGEP2SistemaException {
		return this.rolFactoria.obtenerRolesPorDescripcion(nombre);
	}

	@Override
	public void asociarRolUsuario(UsuarioRolEntidadDTO usuarioAsociar, UsuarioDTO usuarioAuditoria, String accion)
			throws SIGEP2SistemaException {
		UsuarioEntidad usuarioEntidad = usuarioEntidadFactoria.find(usuarioAsociar.getUsuarioEntidad());
		Usuario usuarioAud = usuarioFactoria.getUsuarioParaAuditoria(usuarioAuditoria);
		//Parametrica accionActualizar = parametricaFactoria.findByDescripcion(ParametricaDTO.ACCION_AUDITORIA_ACTUALIZAR);
		//Parametrica accionEliminar = parametricaFactoria.findByDescripcion(ParametricaDTO.ACCION_AUDITORIA_ELIMINAR);
		
		if (!usuarioEntidad.isFlgActivo()) {
			usuarioEntidad.setFlgActivo(true);
			usuarioEntidad.setAudFechaActualizacion(DateUtils.getFechaSistema());
			usuarioEntidad.setCodUsuarioEntidad(usuarioAud.getId());
			usuarioEntidadFactoria.merge(usuarioEntidad, usuarioAud);
		}
		Rol rol = rolFactoria.find(usuarioAsociar.getRol());
		if (rol != null) {
			UsuarioRolEntidad usuarioRolEntidad = usuarioRolEntidadFactoria
					.buscarUsuarioRolEntidad(usuarioEntidad.getId(), rol.getId());
			if (usuarioRolEntidad == null) {
				usuarioRolEntidad = new UsuarioRolEntidad();
				usuarioRolEntidad.setUsuarioEntidad(usuarioEntidad);
				usuarioRolEntidad.setRol(rol);
				usuarioRolEntidad.setAudAccion(Long.valueOf(accion));
			} else {
				if(Integer.parseInt(accion) == ParametricaDTO.AUDITORIA_DELETE) {
					usuarioRolEntidad.setAudAccion(Long.valueOf(accion));
				}else {
					accion = String.valueOf(ParametricaDTO.AUDITORIA_UPDATE);
					usuarioRolEntidad.setAudAccion(Long.valueOf(accion));
				}
			}
			usuarioRolEntidad.setFlgActivo(usuarioAsociar.isFlgActivo());
			usuarioRolEntidad.setFlgEstado(usuarioAsociar.isFlgEstado());

			usuarioRolEntidad.setAudCodRol(usuarioAud.getCodRol());
			usuarioRolEntidad.setAudCodUsuario(usuarioAud.getId());
			usuarioRolEntidad.setAudFechaActualizacion(DateUtils.getFechaSistema());
			usuarioRolEntidadFactoria.merge(usuarioRolEntidad, usuarioAud, accion);
			
		} else {
			throw new SIGEP2SistemaException(MessagesBundleConstants.DLG_SIN_REGISTROS);
		}
	}

	/**
	 * Permite la creación de roles en <b>roles</b>
	 * 
	 * @param usuarioAuditoria
	 * @param roles
	 */
	@Override
	public void crearRol(UsuarioDTO usuarioAuditoria, RolDTO... roles) throws SIGEP2SistemaException {
		Usuario usuario = usuarioFactoria.getUsuarioParaAuditoria(usuarioAuditoria);
		for (RolDTO rolDTO : roles) {
			Rol rol = new Rol();
			rol.setNombre(rolDTO.getNombre());
			rol.setDescripcionRol(rolDTO.getDescripcionRol());
			rol.setRolPadre(rolDTO.getRolPadre());
			rol.setFlgEstado(true);
			rol.setFlgActivo(true);
			

			rol.setFechaModificacion(DateUtils.getFechaSistema());
			rol.setAudFechaActualizacion(DateUtils.getFechaSistema());
			rolFactoria.crearRoles(usuario, rol);
		}
	}

	/**
	 * Permite la actualización de roles en <b>roles</b>. En caso de
	 * <b>reasignarA</b> sea <b>not null</b> se considerará el cambio de los
	 * usuarios asignados de <b>roles</b> a éste
	 * 
	 * @param usuarioAuditoria
	 * @param reasignarA
	 * @param roles
	 */
	@Override
	public void actualizarRoles(UsuarioDTO usuarioAuditoria, RolDTO reasignarA, RolDTO... roles)
			throws SIGEP2SistemaException {
		Usuario usuario = usuarioFactoria.getUsuarioParaAuditoria(usuarioAuditoria);

		for (RolDTO rolDTO : roles) {
			if (rolDTO.getId() > 0) {
				Rol rol = new Rol();
				rol.setId(rolDTO.getId());
				rol.setNombre(rolDTO.getNombre().toUpperCase());
				rol.setDescripcionRol(rolDTO.getDescripcionRol());
				rol.setFlgEstado(rolDTO.isFlgEstado());
				rol.setFlgActivo(rolDTO.isFlgActivo());

				if (rolDTO.getRolPadre() != null) {
					//Rol rolPadre = rolFactoria.find(rolDTO.getRolPadre());
					rol.setRolPadre(rolDTO.getRolPadre());
				}

				rol.setFechaModificacion(DateUtils.getFechaSistema());
				rol.setAudFechaActualizacion(DateUtils.getFechaSistema());
				if (rol.getFlgEstado()) {
					rol.setAudAccion(2L);
				} else {
					rol.setAudAccion(3L);
				}

				// Se desasocian los roles asociados al rol a eliminar
				if (reasignarA != null) {
					List<UsuarioRolEntidad> usuarios = this.usuarioRolEntidadFactoria.buscarUsuarioRol(rol.getId());
					if (!usuarios.isEmpty()) {
						Rol nuevoRol = rolFactoria.find(reasignarA.getId());
						for (UsuarioRolEntidad usuarioRolEntidad : usuarios) {
							UsuarioRolEntidad existe = this.usuarioRolEntidadFactoria.buscarUsuarioRolEntidad(
									usuarioRolEntidad.getUsuarioEntidad().getId(), nuevoRol.getId());
							if (existe == null) {
								usuarioRolEntidad.setRol(nuevoRol);
								this.usuarioRolEntidadFactoria.merge(usuarioRolEntidad, usuario);
							}else {
								existe.setFlgActivo(true);
								existe.setFlgEstado(true);
								this.usuarioRolEntidadFactoria.merge(existe, usuario);
								usuarioRolEntidad.setFlgActivo(false);
								usuarioRolEntidad.setFlgEstado(false);
								this.usuarioRolEntidadFactoria.merge(usuarioRolEntidad, usuario);
							}
						}
					}
				}

				rolFactoria.actualizarRoles(usuario, rol);
			}
		}
	}

	/**
	 * Indica si el rol tiene registros de usuarios asociados a él
	 * 
	 * @param codRol
	 * @return {@link Boolean}
	 */
	@Override
	public boolean isRolTieneUsuarioAsociados(Long codRol) throws SIGEP2SistemaException {
		return !this.usuarioRolEntidadFactoria.buscarUsuarioRol(codRol).isEmpty();
	}

	@Override
	public List<PersonaDTO> buscarPersonas(Long entidadID, Long tipoDocumento, String numeroIdentificacion, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido, int first, int pageSize,String sortField, String sortOrder) {

		List<PersonaDTO> listaPersonas = null;

		if (tipoDocumento != null && tipoDocumento != -1L && !numeroIdentificacion.isEmpty()) {
			listaPersonas = personaFactoria.obtenerUsuariosFiltros(entidadID,tipoDocumento, numeroIdentificacion, "", "", "", "",
					true, true, first, pageSize, sortField, sortOrder );
		} else {
			listaPersonas = personaFactoria.obtenerUsuariosFiltros(entidadID,tipoDocumento, numeroIdentificacion, primerNombre,
					segundoNombre, primerApellido, segundoApellido, false, true, first, pageSize,sortField, sortOrder);
		}

		if (listaPersonas != null && listaPersonas.size() > 0) {

			for (PersonaDTO p : listaPersonas) {
				p.setTipoIdentificacionId(
						(TipoDocumentoDTO) tipoDocumentoFactoria.find(p.getCodTipoIdentificacion()).getDTO());
			}

		}

		return listaPersonas;

	}
	
	@Override
	public List<RolDTO> obtenerRolesByPersona(long personaId, long entidadId){
		
		return rolFactoria.obtenerRolesPorPersona(personaId, entidadId);
		
	}
}