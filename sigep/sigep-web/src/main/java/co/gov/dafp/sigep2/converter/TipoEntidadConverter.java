package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.TipoEntidadDTO;

@FacesConverter("tipoEntidadConverter")
public class TipoEntidadConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<TipoEntidadDTO> tipoEntidad = (List<TipoEntidadDTO>) contexto.getSessionMap()
				.get("tipoEntidad");
		try {
			if (tipoEntidad == null) {
				tipoEntidad = AdministracionDelegate.findTipoEntidad();
				contexto.getSessionMap().put("tipoEntidad", tipoEntidad);
			}
		} catch (Exception e) {
			return null;
		}

		TipoEntidadDTO tipEntidad = new TipoEntidadDTO();
		tipEntidad.setId(Long.valueOf(value));
		return tipoEntidad.get(tipoEntidad.indexOf(tipEntidad));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((TipoEntidadDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
