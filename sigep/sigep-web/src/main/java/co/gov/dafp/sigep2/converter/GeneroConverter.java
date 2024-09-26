package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.GeneroDTO;

@FacesConverter("generoConverter")
public class GeneroConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<GeneroDTO> genero = (List<GeneroDTO>) contexto.getSessionMap()
				.get("genero");
		try {
			if (genero == null) {
				genero = AdministracionDelegate.findGenero();
				contexto.getSessionMap().put("genero", genero);
			}
		} catch (Exception e) {
			return null;
		}

		GeneroDTO tipoGenero = new GeneroDTO();
		tipoGenero.setId(Long.valueOf(value));
		return genero.get(genero.indexOf(tipoGenero));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((GeneroDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
