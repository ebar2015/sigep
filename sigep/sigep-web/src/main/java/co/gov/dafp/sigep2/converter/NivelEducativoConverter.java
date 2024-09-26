package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.NivelEducativoDTO;

@FacesConverter("nivelEducativoConverter")
public class NivelEducativoConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<NivelEducativoDTO> nivelEducativo = (List<NivelEducativoDTO>) contexto.getSessionMap()
				.get("nivelEducativo");
		try {
			if (nivelEducativo == null) {
				nivelEducativo = AdministracionDelegate.findNivelEducativo();
				contexto.getSessionMap().put("nivelEducativo", nivelEducativo);
			}
		} catch (Exception e) {
			return null;
		}

		NivelEducativoDTO nivelEducativoDTO = new NivelEducativoDTO();
		nivelEducativoDTO.setId(Long.valueOf(value));
		return nivelEducativo.get(nivelEducativo.indexOf(nivelEducativoDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((NivelEducativoDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
