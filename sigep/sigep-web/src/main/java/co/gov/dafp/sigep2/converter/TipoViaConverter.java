package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.TipoViaDTO;

@FacesConverter("tipoViaConverter")
public class TipoViaConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<TipoViaDTO> tipoVia = (List<TipoViaDTO>) contexto.getSessionMap()
				.get("tipoVia");
		try {
			if (tipoVia == null) {
				tipoVia = HojaDeVidaDelegate.buscarTipoVia();
				contexto.getSessionMap().put("tipoVia", tipoVia);
			}
		} catch (Exception e) {
			return null;
		}

		TipoViaDTO tipoViaDTO = new TipoViaDTO();
		tipoViaDTO.setId(Long.valueOf(value));
		return tipoVia.get(tipoVia.indexOf(tipoViaDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((TipoViaDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
