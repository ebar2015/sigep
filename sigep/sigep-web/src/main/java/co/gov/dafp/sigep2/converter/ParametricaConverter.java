package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.ParametricaDTO;

@FacesConverter("parametricaConverter")
public class ParametricaConverter implements Converter  {

	List<ParametricaDTO> tablasParametrica = null;

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		tablasParametrica = (List<ParametricaDTO>) contexto.getSessionMap().get("tablasParametrica");
		try {
			if (tablasParametrica == null) {
				tablasParametrica = AdministracionDelegate.listarTablasParametrica();
				contexto.getSessionMap().put("tablasParametrica", tablasParametrica);
			}
			
		} catch (Exception e) {
			return null;
		}
		ParametricaDTO parametrica = new ParametricaDTO();
		parametrica.setId(Long.valueOf(value));
		return tablasParametrica.get(tablasParametrica.indexOf(parametrica));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((ParametricaDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
