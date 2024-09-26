package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.PoblacionEtnicaDTO;

@FacesConverter("poblacionEtnicaConverter")
public class PoblacionEtnicaConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<PoblacionEtnicaDTO> poblacionEtnica = (List<PoblacionEtnicaDTO>) contexto.getSessionMap()
				.get("poblacionEtnica");
		try {
			if (poblacionEtnica == null) {
				poblacionEtnica = AdministracionDelegate.findPoblacionEtnica();
				contexto.getSessionMap().put("poblacionEtnica", poblacionEtnica);
			}
		} catch (Exception e) {
			return null;
		}

		PoblacionEtnicaDTO tipPoblacionEtnica = new PoblacionEtnicaDTO();
		tipPoblacionEtnica.setId(Long.valueOf(value));
		return poblacionEtnica.get(poblacionEtnica.indexOf(tipPoblacionEtnica));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((PoblacionEtnicaDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
