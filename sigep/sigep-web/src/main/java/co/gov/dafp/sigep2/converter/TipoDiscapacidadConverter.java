package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.TipoDiscapacidadDTO;

@FacesConverter("tipoDiscapacidadConverter")
public class TipoDiscapacidadConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<TipoDiscapacidadDTO> tipoDiscapacidad = (List<TipoDiscapacidadDTO>) contexto.getSessionMap()
				.get("tipoDiscapacidad");
		try {
			if (tipoDiscapacidad == null) {
				tipoDiscapacidad = HojaDeVidaDelegate.findTipoDiscapacidad();
				contexto.getSessionMap().put("tipoDiscapacidad", tipoDiscapacidad);
			}
		} catch (Exception e) {
			return null;
		}

		TipoDiscapacidadDTO tipTipoDiscapacidad = new TipoDiscapacidadDTO();
		tipTipoDiscapacidad.setId(Long.valueOf(value));
		return tipoDiscapacidad.get(tipoDiscapacidad.indexOf(tipTipoDiscapacidad));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((TipoDiscapacidadDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
