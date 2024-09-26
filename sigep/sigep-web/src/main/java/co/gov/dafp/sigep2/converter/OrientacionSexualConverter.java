package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.OrientacionSexualDTO;

@FacesConverter("orientacionSexualConverter")
public class OrientacionSexualConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<OrientacionSexualDTO> orientacionSexual = (List<OrientacionSexualDTO>) contexto.getSessionMap().get("orientacionSexual");
		try {
			if (orientacionSexual == null) {
				orientacionSexual = HojaDeVidaDelegate.obtenerParametricasOrientacionSexual();
				contexto.getSessionMap().put("orientacionSexual", orientacionSexual);
			}
		} catch (Exception e) {
			return null;
		}

		OrientacionSexualDTO orientDTO = new OrientacionSexualDTO();
		orientDTO.setId(Long.valueOf(value));
		return orientacionSexual.get(orientacionSexual.indexOf(orientDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((OrientacionSexualDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
