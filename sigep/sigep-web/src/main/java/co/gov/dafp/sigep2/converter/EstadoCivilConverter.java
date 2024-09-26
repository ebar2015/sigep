package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.EstadoCivilDTO;

@FacesConverter("estadoCivilConverter")
public class EstadoCivilConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<EstadoCivilDTO> estadoCivil = (List<EstadoCivilDTO>) contexto.getSessionMap()
				.get("estadoCivil");
		try {
			if (estadoCivil == null) {
				estadoCivil = AdministracionDelegate.findEstadoCivil();
				contexto.getSessionMap().put("estadoCivil", estadoCivil);
			}
		} catch (Exception e) {
			return null;
		}

		EstadoCivilDTO tipEstadoCivil = new EstadoCivilDTO();
		tipEstadoCivil.setId(Long.valueOf(value));
		return estadoCivil.get(estadoCivil.indexOf(tipEstadoCivil));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((EstadoCivilDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
