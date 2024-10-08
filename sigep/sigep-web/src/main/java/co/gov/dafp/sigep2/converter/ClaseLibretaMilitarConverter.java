package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.ClaseLibretaMilitarDTO;

@FacesConverter("claseLibretaMilitarConverter")
public class ClaseLibretaMilitarConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<ClaseLibretaMilitarDTO> claseLibretaMilitar = (List<ClaseLibretaMilitarDTO>) contexto.getSessionMap()
				.get("claseLibretaMilitar");
		try {
			if (claseLibretaMilitar == null) {
				claseLibretaMilitar = AdministracionDelegate.findClaseLibretaMilitar();
				contexto.getSessionMap().put("claseLibretaMilitar", claseLibretaMilitar);
			}
		} catch (Exception e) {
			return null;
		}

		ClaseLibretaMilitarDTO claseLibreta = new ClaseLibretaMilitarDTO();
		claseLibreta.setId(Long.valueOf(value));
		return claseLibretaMilitar.get(claseLibretaMilitar.indexOf(claseLibreta));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((ClaseLibretaMilitarDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
