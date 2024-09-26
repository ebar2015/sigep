package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@SessionScoped
public class EntidadProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<EntidadDTO> entidadesUsuario;

	private List<EntidadDTO> entidades;
	

	@Named
	@Produces
	public List<EntidadDTO> getEntidadesUsuario() throws SIGEP2SistemaException {
		if (entidadesUsuario == null) {
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
			if (usuarioSesion != null) {
				entidadesUsuario = IngresoSistemaDelegate.obtenerEntidadesUsuario(usuarioSesion);
			}
		}
		return entidadesUsuario;
	}
	@Named
	@Produces
	public List<EntidadDTO> getEntidadesUsuariorRefresca() throws SIGEP2SistemaException {
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
			if (usuarioSesion != null) {
				entidadesUsuario = IngresoSistemaDelegate.obtenerEntidadesUsuario(usuarioSesion);
				contexto.getSessionMap().put("entidadesUsuario", entidadesUsuario);
			}
		return entidadesUsuario;
	}	

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<EntidadDTO> getEntidades() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		entidades = (List<EntidadDTO>) contexto.getSessionMap().get("entidades");
		
		if (entidades == null) {
			entidades = IngresoSistemaDelegate.obtenerEntidades();
			contexto.getSessionMap().put("entidades", entidades);
		}
		return entidades;
	}
}
