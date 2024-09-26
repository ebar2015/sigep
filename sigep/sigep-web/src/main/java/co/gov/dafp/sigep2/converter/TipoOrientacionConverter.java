package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.TipoOrientacionDTO;

@FacesConverter("tipoOrientacionConverter")
public class TipoOrientacionConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<TipoOrientacionDTO> tipoOrientacion = (List<TipoOrientacionDTO>) contexto.getSessionMap()
				.get("tipoOrientacion");
		try {
			if (tipoOrientacion == null) {
				tipoOrientacion = HojaDeVidaDelegate.buscarTipoOrientacion();
				contexto.getSessionMap().put("tipoOrientacion", tipoOrientacion);
			}
		} catch (Exception e) {
			return null;
		}

		TipoOrientacionDTO tipoOrientacionDTO = new TipoOrientacionDTO();
		tipoOrientacionDTO.setId(Long.valueOf(value));
		return tipoOrientacion.get(tipoOrientacion.indexOf(tipoOrientacionDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((TipoOrientacionDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
