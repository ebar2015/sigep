package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.entity.MunicipioDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;
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
import co.gov.dafp.sigep2.util.exceptions.SIGEP2NegocioException;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Remote
public interface IAdministracionRemote extends IServiceRemote {

	public List<TipoDocumentoDTO> findTipoDocumento();

	public List<GeneroDTO> findGenero();

	public List<TipoAsociacionDTO> findTipoAsociacion();

	public TipoAsociacionDTO findTipoAsociacion(long codTipoAsociacion);

	public List<TipoEntidadDTO> findTipoEntidad();

	public List<AuditoriaConfiguracionDTO> findAuditoriaConfiguracion();

	public List<ClaseLibretaMilitarDTO> findClaseLibretaMilitar();

	public List<PaisDTO> findPais();

	public List<DepartamentoDTO> findDepartamento();

	public List<MunicipioDTO> findMunicipio();

	public List<TipoZonaDTO> findTipoZona();

	public List<EstadoCivilDTO> findEstadoCivil();

	public List<PoblacionEtnicaDTO> findPoblacionEtnica();

	public List<NivelEducativoDTO> findNivelEducativo();

	public List<AreaConocimientoDTO> findAreaConocimiento();

	public GeneroDTO findGeneroId(long id);

	public ClaseLibretaMilitarDTO findClaseLibretaMilitarId(long id);

	public TipoDocumentoDTO findTipoDocumentoId(long id);

	public boolean actualizarAuditoriaConfiguracion(AuditoriaConfiguracionDTO auditoria) throws SIGEP2NegocioException;

	public List<AccionAuditoriaDTO> findAccionAuditoria();

	public List<AuditoriaConfiguracionDTO> findAuditoriaConfiguracion(int first, int pageSize);

	public int findAuditoriaConfiguracionTotalRows();

	public PaisDTO encontrarPaisPorId(long id);

	public List<EntidadDTO> obtenerDependenciasEntidades(Long entidad) throws SIGEP2SistemaException;

	public EntidadDTO obtenerEntidadPorNombre(String nombreEntidad) throws SIGEP2SistemaException;

	public List<RolDTO> obtenerRolesPorDescripcion(String... nombre) throws SIGEP2SistemaException;

	public void asociarRolUsuario(UsuarioRolEntidadDTO usuarioAsociar, UsuarioDTO usuarioAuditoria, String accion)
			throws SIGEP2SistemaException;

	public void crearRol(UsuarioDTO usuarioAuditoria, RolDTO... roles) throws SIGEP2SistemaException;

	public void actualizarRoles(UsuarioDTO usuarioAuditoria, RolDTO reasignarA, RolDTO... roles)
			throws SIGEP2SistemaException;

	public boolean isRolTieneUsuarioAsociados(Long codRol) throws SIGEP2SistemaException;
	
	public List<PersonaDTO> buscarPersonas(Long entidadID,Long tipoDocumento, String numeroIdentificacion, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido,int first, int pageSize, String sortField, String sortOrder);
	
	public List<RolDTO> obtenerRolesByPersona(long personaId, long entidadId);
}