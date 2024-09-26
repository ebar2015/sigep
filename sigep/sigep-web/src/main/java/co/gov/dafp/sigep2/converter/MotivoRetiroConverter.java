package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.MotivoRetiroDTO;

@FacesConverter("motivoRetiroConverter")
public class MotivoRetiroConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<MotivoRetiroDTO> motivoRetiro = (List<MotivoRetiroDTO>) contexto.getSessionMap().get("motivoRetiro");
		try {
			if (motivoRetiro == null) {
				motivoRetiro = HojaDeVidaDelegate.buscarMotivoRetiro();
				contexto.getSessionMap().put("motivoRetiro", motivoRetiro);
			}
		} catch (Exception e) {
			return null;
		}

		MotivoRetiroDTO motivoRetiroDTO = new MotivoRetiroDTO();
		motivoRetiroDTO.setId(Long.valueOf(value));
		return motivoRetiro.get(motivoRetiro.indexOf(motivoRetiroDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((MotivoRetiroDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}

}
