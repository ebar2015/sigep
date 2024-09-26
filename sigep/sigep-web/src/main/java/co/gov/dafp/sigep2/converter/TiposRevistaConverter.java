package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.TiposRevistaDTO;

@FacesConverter("tiposRevistaConverter")
public class TiposRevistaConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<TiposRevistaDTO> tiposRevista = (List<TiposRevistaDTO>) contexto.getSessionMap().get("tiposRevista");
		try {
			if (tiposRevista == null) {
				tiposRevista = HojaDeVidaDelegate.obtenerParametricasTiposRevista();
				contexto.getSessionMap().put("tiposRevista", tiposRevista);
			}
		} catch (Exception e) {
			return null;
		}

		TiposRevistaDTO tiposRevistaDTO = new TiposRevistaDTO();
		tiposRevistaDTO.setId(Long.valueOf(value));
		return tiposRevista.get(tiposRevista.indexOf(tiposRevistaDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((TiposRevistaDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
