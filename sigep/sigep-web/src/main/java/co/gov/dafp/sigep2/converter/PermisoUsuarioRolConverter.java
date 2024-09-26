package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.PermisoUsuarioRolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;

@FacesConverter("permisoUsuarioRolConverter")
public class PermisoUsuarioRolConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<PermisoUsuarioRolDTO> permisosUsuarioRol = (List<PermisoUsuarioRolDTO>) contexto.getSessionMap()
				.get("permisosUsuarioRol");
		try {
			UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
			if (usuarioSesion != null) {
				if (permisosUsuarioRol == null) {
					permisosUsuarioRol = IngresoSistemaDelegate.obtenerPermisosPorUsuario(usuarioSesion.getId());
					contexto.getSessionMap().put("permisosUsuarioRol", permisosUsuarioRol);
				}
			}
		} catch (Exception e) {
			return null;
		}

		PermisoUsuarioRolDTO permisoUsuario = new PermisoUsuarioRolDTO();
		permisoUsuario.setId(Long.valueOf(value));
		return permisosUsuarioRol.get(permisosUsuarioRol.indexOf(permisoUsuario));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((PermisoUsuarioRolDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
