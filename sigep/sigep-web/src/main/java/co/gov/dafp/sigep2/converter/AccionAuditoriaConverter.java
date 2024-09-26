package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.AccionAuditoriaDTO;

@FacesConverter("accionAuditoriaConverter")
public class AccionAuditoriaConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<AccionAuditoriaDTO> accionAuditoriaList = (List<AccionAuditoriaDTO>) contexto.getSessionMap()
				.get("accionAuditoriaList");
		try {
			if (accionAuditoriaList == null) {
				accionAuditoriaList = AdministracionDelegate.findAccionAuditoria();
				contexto.getSessionMap().put("accionAuditoriaList", accionAuditoriaList);
			}
		} catch (Exception e) {
			return null;
		}

		AccionAuditoriaDTO accionAuditoriaDTO = new AccionAuditoriaDTO();
		accionAuditoriaDTO.setId(Long.valueOf(value));
		return accionAuditoriaList.get(accionAuditoriaList.indexOf(accionAuditoriaDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((AccionAuditoriaDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
