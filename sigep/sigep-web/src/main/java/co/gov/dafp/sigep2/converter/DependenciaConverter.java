package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.entities.DependenciaEntidad;

@FacesConverter("dependenciaConverter")
public class DependenciaConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<DependenciaEntidad> dependencias = (List<DependenciaEntidad>) contexto.getSessionMap()
				.get("dependencias");
		
		DependenciaEntidad dependenciaDTO = new DependenciaEntidad();
		dependenciaDTO.setCodDependenciaEntidad(Long.valueOf(value));
		return dependencias.get(dependencias.indexOf(dependenciaDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((DependenciaEntidad) value).getCodDependenciaEntidad());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}