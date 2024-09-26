package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.util.FileUtil;

@FacesConverter("entidadConverter")
public class EntidadConverter implements Converter {
	List<EntidadDTO> entidadesUsuario = null;

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<EntidadDTO> entidadesUsuario = (List<EntidadDTO>) contexto.getSessionMap().get("entidadesUsuario");
		try {
			UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
			if (usuarioSesion != null) {
				if (entidadesUsuario == null) {
					entidadesUsuario = IngresoSistemaDelegate.obtenerEntidadesUsuario(usuarioSesion);
					contexto.getSessionMap().put("entidadesUsuario", entidadesUsuario);
				}
			}
		} catch (Exception e) {
			return null;
		}
		EntidadDTO entidad = new EntidadDTO();
		Long id = Long.valueOf(value.split(FileUtil.PUNTO_COMA)[0]);
		String codigoSigep = value.split(FileUtil.PUNTO_COMA)[1];
		String nombreEntidad = value.split(FileUtil.PUNTO_COMA)[2];
		
		entidad.setId(id);
		entidad.setCodigoSigep(codigoSigep);
		entidad.setNombreEntidad(nombreEntidad);
		return entidadesUsuario.get(entidadesUsuario.indexOf(entidad));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		EntidadDTO entidad = (EntidadDTO) value;
		return entidad.getId() + FileUtil.PUNTO_COMA + entidad.getCodigoSigep() + FileUtil.PUNTO_COMA + entidad.getNombreEntidad();
	}
}
