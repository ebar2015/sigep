package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;

@FacesConverter("seleccionarEntidadConverter")
public class SeleccionarEntidadConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<EntidadDTO> entidades = (List<EntidadDTO>) contexto.getSessionMap().get("entidades");
		try {
			if (entidades == null) {
				entidades = IngresoSistemaDelegate.obtenerEntidades();
				contexto.getSessionMap().put("entidades", entidades);
			}
		} catch (Exception e) {
			return null;
		}

		EntidadDTO entidadDTO = new EntidadDTO();
		entidadDTO.setId(Long.valueOf(value));
		return entidades.get(entidades.indexOf(entidadDTO));
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
