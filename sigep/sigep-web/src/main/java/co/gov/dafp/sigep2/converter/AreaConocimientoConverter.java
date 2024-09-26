package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.AreaConocimientoDTO;

@FacesConverter("areaConocimientoConverter")
public class AreaConocimientoConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<AreaConocimientoDTO> areaConocimiento = (List<AreaConocimientoDTO>) contexto.getSessionMap()
				.get("areaConocimiento");
		try {
			if (areaConocimiento == null) {
				areaConocimiento = AdministracionDelegate.findAreaConocimiento();
				contexto.getSessionMap().put("areaConocimiento", areaConocimiento);
			}
		} catch (Exception e) {
			return null;
		}

		AreaConocimientoDTO areaConocimientoDTO = new AreaConocimientoDTO();
		areaConocimientoDTO.setId(Long.valueOf(value));
		return areaConocimiento.get(areaConocimiento.indexOf(areaConocimientoDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((AreaConocimientoDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
