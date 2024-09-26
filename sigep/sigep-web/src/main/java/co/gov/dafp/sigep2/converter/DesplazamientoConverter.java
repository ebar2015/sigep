package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.DesplazamientoDTO;

@FacesConverter("desplazamientoConverter")
public class DesplazamientoConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<DesplazamientoDTO> desplazamiento = (List<DesplazamientoDTO>) contexto.getSessionMap().get("desplazamiento");
		try {
			if (desplazamiento == null) {
				desplazamiento = HojaDeVidaDelegate.obtenerParametricasDesplazamiento();
				contexto.getSessionMap().put("desplazamiento", desplazamiento);
			}
		} catch (Exception e) {
			return null;
		}

		DesplazamientoDTO despla = new DesplazamientoDTO();
		despla.setId(Long.valueOf(value));
		return desplazamiento.get(desplazamiento.indexOf(despla));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}
		
		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((DesplazamientoDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
