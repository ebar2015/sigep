package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.TipoZonaDTO;

@FacesConverter("tipoZonaConverter")
public class TipoZonaConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<TipoZonaDTO> tipoZona = (List<TipoZonaDTO>) contexto.getSessionMap()
				.get("tipoZona");
		try {
			if (tipoZona == null) {
				tipoZona = AdministracionDelegate.findTipoZona();
				contexto.getSessionMap().put("tipoZona", tipoZona);
			}
		} catch (Exception e) {
			return null;
		}

		TipoZonaDTO tipZona = new TipoZonaDTO();
		tipZona.setId(Long.valueOf(value));
		return tipoZona.get(tipoZona.indexOf(tipZona));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((TipoZonaDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
