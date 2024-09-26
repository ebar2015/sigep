package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.PaisDTO;

@FacesConverter("paisConverter")
public class PaisConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<PaisDTO> pais = (List<PaisDTO>) contexto.getSessionMap()
				.get("pais");
		try {
			if (pais == null) {
				pais = AdministracionDelegate.findPais();
				contexto.getSessionMap().put("pais", pais);
			}
		} catch (Exception e) {
			return null;
		}

		PaisDTO tipoPais = new PaisDTO();
		tipoPais.setId(Long.valueOf(value));
		return pais.get(pais.indexOf(tipoPais));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((PaisDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
