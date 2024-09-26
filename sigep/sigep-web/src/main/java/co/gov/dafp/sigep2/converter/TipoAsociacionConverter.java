package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;

@FacesConverter("tipoAsociacionConverter")
public class TipoAsociacionConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<TipoAsociacionDTO> tipoAsociacion = (List<TipoAsociacionDTO>) contexto.getSessionMap()
				.get("tipoAsociacion");
		try {
			if (tipoAsociacion == null) {
				tipoAsociacion = AdministracionDelegate.findTipoAsociacion();
				contexto.getSessionMap().put("tipoAsociacion", tipoAsociacion);
			}
		} catch (Exception e) {
			return null;
		}

		TipoAsociacionDTO tipAsociacion = new TipoAsociacionDTO();
		tipAsociacion.setId(Long.valueOf(value));
		return tipoAsociacion.get(tipoAsociacion.indexOf(tipAsociacion));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((TipoAsociacionDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
