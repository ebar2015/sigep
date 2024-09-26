package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.PermisoUsuarioRolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.RecursoActivoPerfilUsuarioDTO;
import co.gov.dafp.sigep2.mbean.SesionBean;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.util.StringEncrypt;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@SessionScoped
public class PermisoUsuarioRolProduces implements Serializable {
	private static final long serialVersionUID = 5827483562144360862L;

	private List<PermisoUsuarioRolDTO> permisosUsuarioRol;

	private String recursoId;

	@Inject
	private SesionBean sesionBean;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		permisosUsuarioRol = (List<PermisoUsuarioRolDTO>) contexto.getSessionMap().get("permisosUsuarioRol");
		try {
			UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
			if (usuarioSesion != null) {
				permisosUsuarioRol = IngresoSistemaDelegate.obtenerPermisosPorUsuario(usuarioSesion.getId());
				contexto.getSessionMap().put("permisosUsuarioRol", permisosUsuarioRol);
			}
		} catch (Exception e) {

		}
	}

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<PermisoUsuarioRolDTO> getPermisosUsuarioRol() throws SIGEP2SistemaException {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<PermisoUsuarioRolDTO> permisosUsuarioRolRecurso = new LinkedList<>();
		try {
			UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
			if (usuarioSesion == null) {
				return permisosUsuarioRolRecurso;
			}
			if (this.permisosUsuarioRol == null) {
				this.init();
			}
			String recursoId = (String) contexto.getSessionMap().get("recursoId");
			if (((recursoId != null && this.recursoId != null) || (recursoId != null && this.recursoId == null))
					&& !recursoId.equals(this.recursoId)) {
				this.recursoId = recursoId;
				RecursoActivoPerfilUsuarioDTO recursoPermiso = IngresoSistemaDelegate
						.findByCodigoVentana(this.recursoId, usuarioSesion.getId());
				contexto.getSessionMap().put("recursoPermiso", recursoPermiso);
				if (recursoPermiso != null) {
					List<PermisoUsuarioRolDTO> permisosUsuarioRolRecursoTemp = IngresoSistemaDelegate
							.obtenerPermisosPorCodigoVentana(recursoPermiso.getCodigoVentana(), usuarioSesion.getId(),
									sesionBean.getRolesUsuarioSesion());
					for (PermisoUsuarioRolDTO permiso : permisosUsuarioRolRecursoTemp) {
						if (!permisosUsuarioRolRecurso.contains(permiso)) {
							
							if(permiso.getRecurso() == 194L  && permiso.getAccion().equalsIgnoreCase("TTL_HOJA_VIDA2") && permiso.getRol() == 3)
							{		
								
								PersonaExt persona = ComunicacionServiciosHV.getPersonaporIdExt(usuarioSesion.getCodPersona());
								String password = usuarioSesion.getContrasena();	
								String data = usuarioSesion.getNumeroIdentificacion().trim() + ";" + persona.getCodTipoIdentificacion().trim() + ";" + password.trim(); 
								String datoEncriptado = StringEncrypt.encrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(), data);
								String datoEncriptadoEncoded = URLEncoder.encode(datoEncriptado, "UTF-8");
								String nroIdentificacion  =      datoEncriptadoEncoded ;	
							
								Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(5945));
								String urlHv = parametrica.getValorParametro().trim();							
								permiso.setPagina(urlHv + nroIdentificacion);															
							}
							
							if(((permiso.getRecurso() == 195L && permiso.getAccion().equalsIgnoreCase("TTL_DECLARACION_BIENES_RENTA")) || 
									(permiso.getRecurso() == 194L && permiso.getAccion().equalsIgnoreCase("TTL_HOJA_VIDA2"))) && permiso.getRol() == 4)
							{	
								
								if(permiso.getAccion().equalsIgnoreCase("TTL_DECLARACION_BIENES_RENTA")){
									PersonaExt persona = ComunicacionServiciosHV.getPersonaporIdExt(usuarioSesion.getCodPersona());
									String password = usuarioSesion.getContrasena();	
									String data = usuarioSesion.getNumeroIdentificacion().trim() + ";" + persona.getCodTipoIdentificacion().trim() + ";" + password.trim(); 
									String datoEncriptado = StringEncrypt.encrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(), data);
									String datoEncriptadoEncoded = URLEncoder.encode(datoEncriptado, "UTF-8");
									String nroIdentificacion  =      datoEncriptadoEncoded ;	
									Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(5946));
									String urlHv = parametrica.getValorParametro().trim();							
									permiso.setPagina(urlHv + nroIdentificacion);	
								}
								if(permiso.getAccion().equalsIgnoreCase("TTL_HOJA_VIDA2")){
									PersonaExt persona = ComunicacionServiciosHV.getPersonaporIdExt(usuarioSesion.getCodPersona());
									String password = usuarioSesion.getContrasena();	
									String data = usuarioSesion.getNumeroIdentificacion().trim() + ";" + persona.getCodTipoIdentificacion().trim() + ";" + password.trim(); 
									String datoEncriptado = StringEncrypt.encrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(), data);
									String datoEncriptadoEncoded = URLEncoder.encode(datoEncriptado, "UTF-8");
									String nroIdentificacion  =      datoEncriptadoEncoded ;	
									Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(5945));
									String urlHv = parametrica.getValorParametro().trim();							
									permiso.setPagina(urlHv + nroIdentificacion);			
								}
								
															
							}							
							
							permisosUsuarioRolRecurso.add(permiso);
						}
					}
					contexto.getSessionMap().put("permisosUsuarioRolRecurso", permisosUsuarioRolRecurso);
					return permisosUsuarioRolRecurso;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return (List<PermisoUsuarioRolDTO>) contexto.getSessionMap().get("permisosUsuarioRolRecurso");
	}
}
