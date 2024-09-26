package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;

@FacesConverter("listaEntidadesConverter")
public class ListaEntidadesConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<EntidadDTO> listaEntidades = (List<EntidadDTO>) contexto.getSessionMap()
				.get("entidades");
		try {
			if (listaEntidades == null) {
				listaEntidades = IngresoSistemaDelegate.obtenerEntidades();
				contexto.getSessionMap().put("entidades", listaEntidades);
			}
		} catch (Exception e) {
			return null;
		}

		EntidadDTO entidad = new EntidadDTO();
		entidad.setId(Long.valueOf(value));
		return listaEntidades.get(listaEntidades.indexOf(entidad));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((EntidadDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
