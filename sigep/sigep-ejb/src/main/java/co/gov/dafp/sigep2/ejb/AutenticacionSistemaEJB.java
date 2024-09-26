package co.gov.dafp.sigep2.ejb;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;
import co.gov.dafp.sigep2.entity.jpa.seguridad.UsuarioEntidad;
import co.gov.dafp.sigep2.entity.jpa.seguridad.UsuarioRolEntidad;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioEntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioRolEntidadDTO;
import co.gov.dafp.sigep2.factoria.ProcesoBackgroundFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioEntidadFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioPasswordFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioRolEntidadFactoria;
import co.gov.dafp.sigep2.interfaces.IAutenticacionSistemaLocal;
import co.gov.dafp.sigep2.interfaces.IAutenticacionSistemaRemote;

import java.util.List;

import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.AuthenticationResultEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class AutenticacionSistemaEJB implements IAutenticacionSistemaLocal, IAutenticacionSistemaRemote {
	private static final long serialVersionUID = 1352471373248439462L;

	transient Logger logger = Logger.getInstance(AutenticacionSistemaEJB.class);

	private UsuarioDTO usuarioSesion;

	@EJB
	private UsuarioFactoria usuarioFactoria;
	
	@EJB
	private UsuarioRolEntidadFactoria usuarioRolEntidadFactoria;
	
	@EJB
	private UsuarioPasswordFactoria usuarioPasswordDao;

	@EJB
	private ProcesoBackgroundFactoria procesoBackgroundDao;

	private Long intentosFallidosLogin;
	
	@EJB
	private UsuarioEntidadFactoria usuarioEntidadFactoria;

	@Override
	public UsuarioDTO login(Long tipoDocumento, String login, String password, String mac, String ipAddress, long maxFallidos)
			throws SIGEP2SistemaException {
		this.usuarioSesion = new UsuarioDTO();
		try {
			this.usuarioSesion = usuarioFactoria.getUsuarioByLogin(login, tipoDocumento);

			if (this.usuarioSesion == null) {
				lanzarException(MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_EXISTE);
			}
			if (this.usuarioSesion.isFlgBloqueado()) {
				lanzarException(MessagesBundleConstants.MSG_CUENTA_USUARIO_BLOQUEADA);
			}
			if (!this.usuarioSesion.isFlgEstado()) {
				lanzarException(MessagesBundleConstants.MSG_CUENTA_USUARIO_ELIMINADA);
			}
			if (this.usuarioSesion.getContrasena() == null || !this.usuarioSesion.getContrasena().trim().equals(password.trim())) {
				if (!UsuarioDTO.USUARIO_SUPER_ADMINISTRADOR.equals(this.usuarioSesion.getNombreUsuario())
						&& ConfigurationBundleConstants.getBoolean(ConfigurationBundleConstants.PASS_VALIDATE_LOGIN)) {
					intentosFallidosLogin = validarBloquearUsuario(AuthenticationResultEnum.FAILED,
							intentosFallidosLogin, maxFallidos);
				}
				lanzarException(MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_INVALIDO);
			}
			this.intentosFallidosLogin = 0l;
			if (this.usuarioSesion.getContrasena().equals(this.usuarioSesion.getNumeroIdentificacion())) {
				lanzarException(MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_CAMBIO);
			}
			if (this.usuarioSesion.getMacAutorizada() != null && !this.usuarioSesion.getMacAutorizada().equals(mac)) {
				lanzarException(MessagesBundleConstants.MSG_CUENTA_USUARIO_DIRECCION_MAC_INVALIDA);
			}
			if (this.usuarioSesion.getIpAutorizada() != null
					&& !this.usuarioSesion.getIpAutorizada().equals(ipAddress)) {
				lanzarException(MessagesBundleConstants.MSG_CUENTA_USUARIO_DIRECCION_IP_INVALIDA);
			}
			Usuario usuarioLogueado = this.usuarioFactoria.registrarIngreso(this.usuarioSesion.getId(), ipAddress);

			return (UsuarioDTO) usuarioLogueado.getDTO();
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e);
		}
	}

	private Long validarBloquearUsuario(AuthenticationResultEnum result, Long intentos, long maxFallidos) throws Exception {

		Long maxIntentosFallidos = maxFallidos;
//				ConfigurationBundleConstants
//				.getLong(ConfigurationBundleConstants.MAXIMO_INTENTOS_FALLIDOS_LOGIN);
		int tiempoVigenciaPassword = ConfigurationBundleConstants
				.getInt(ConfigurationBundleConstants.CNS_DIAS_VENCE_CLAVE_USUARIO);

		if (this.usuarioSesion != null) {
			boolean passwordVencido = false;
			if (tiempoVigenciaPassword > 0 && DateUtils.getFechaSistema().after(this.usuarioSesion.getFechaVence())) {
				result = AuthenticationResultEnum.FAILED;
				intentosFallidosLogin = maxIntentosFallidos;
				passwordVencido = true;
			}

			if (AuthenticationResultEnum.FAILED.equals(result)) {
				Usuario usuarioIntentos = this.usuarioFactoria.find(this.usuarioSesion.getId());
				Usuario sistemaIntentosLog = this.usuarioFactoria.getUsuarioSistema();

				intentosFallidosLogin = usuarioIntentos.getNumeroReintentos() == null ? 1L : usuarioIntentos.getNumeroReintentos() + 1L;

				usuarioIntentos.setNumeroReintentos(intentosFallidosLogin);
				usuarioFactoria.merge(usuarioIntentos, sistemaIntentosLog);

				if (intentosFallidosLogin.longValue() >= maxIntentosFallidos.longValue()) {
					try {
						Usuario sistema = this.usuarioFactoria.getUsuarioSistema();
						Usuario usuarioBloqueado = this.usuarioFactoria.find(this.usuarioSesion.getId());
						usuarioBloqueado.setFlgBloqueado(Boolean.TRUE);
						usuarioBloqueado.setFechaVence(DateUtils.getFechaSistema());
						usuarioFactoria.merge(usuarioBloqueado, sistema);
						intentosFallidosLogin = null;
						lanzarException(MessagesBundleConstants.MSG_CUENTA_USUARIO_BLOQUEADA);
					} catch (Exception e) {
						logger.error("Bloqueo Usuario", e);
						lanzarException(e.getMessage());
					}
				}
			} else {
				return null;
			}
		} else {
			lanzarException(MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_EXISTE);
		}
		return intentosFallidosLogin;
	}

	@Override
	public void aceptaHabeasData(UsuarioDTO usuario) throws SIGEP2SistemaException {
		this.usuarioFactoria.aceptaHabeasData(usuario);
	}

	@Override
	public List<EntidadDTO> obtenerEntidadesUsuario(UsuarioDTO usuario) {
		return this.usuarioFactoria.obtenerEntidadesUsuario(usuario);
	}

	@Override
	public List<UsuarioDTO> obtenerEntidadesUsuarioAsociadas(Long usuario, Long entidad) {
		return this.usuarioFactoria.obtenerEntidadesUsuarioAsociadas(usuario, entidad);
	}

	@Override
	public List<EntidadDTO> obtenerEntidades() {
		return this.usuarioFactoria.obtenerEntidades();
	}

	private void lanzarException(String message) throws SIGEP2SistemaException {
		throw new SIGEP2SistemaException(message);
	}

	@Override
	public UsuarioRolEntidadDTO buscarUsuarioRolEntidad(Long codUsuarioEntidad) throws SIGEP2SistemaException {
		
		UsuarioRolEntidadDTO usuarioRolEntidadDTO = null;
		UsuarioRolEntidad usuarioRolEntidad = usuarioRolEntidadFactoria.buscarUsuarioRolEntidad(codUsuarioEntidad);
		
		if(usuarioRolEntidad !=null) {
			usuarioRolEntidadDTO = (UsuarioRolEntidadDTO) usuarioRolEntidad.getDTO();
		}
		
		return usuarioRolEntidadDTO;
	}
	
	@Override
	public UsuarioEntidadDTO consultarUsuarioEntidad(Long codUsuario, Long codEntidad) throws SIGEP2SistemaException {
		
		UsuarioEntidadDTO usuarioEntidadDTO = null;
		UsuarioEntidad usuarioEntidad = usuarioEntidadFactoria.consultarUsuarioEntidad(codUsuario, codEntidad);
		
		if(usuarioEntidad !=null) {
			usuarioEntidadDTO = (UsuarioEntidadDTO) usuarioEntidad.getDTO();
		}		
		
		return usuarioEntidadDTO;
	}
}
