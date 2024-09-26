package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.JornadaLaboralDTO;


@FacesConverter("jornadaLaboralConverter")
public class JornadaLaboralConverter implements Converter{
	
	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<JornadaLaboralDTO> jornadaLaboral = (List<JornadaLaboralDTO>) contexto.getSessionMap().get("jornadaLaboral");
		try {
			if (jornadaLaboral == null) {
				jornadaLaboral = HojaDeVidaDelegate.buscarJornadaLaboral();
				contexto.getSessionMap().put("jornadaLaboral", jornadaLaboral);
			}
		} catch (Exception e) {
			return null;
		}

		JornadaLaboralDTO jornadaLaboralDTO = new JornadaLaboralDTO();
		jornadaLaboralDTO.setId(Long.valueOf(value));
		return jornadaLaboral.get(jornadaLaboral.indexOf(jornadaLaboralDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((JornadaLaboralDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
