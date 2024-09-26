package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;

@FacesConverter("entidadFiltroConverter")
public class EntidadFiltroConverter implements Converter {
	List<EntidadDTO> entidadesUsuario = null;

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<EntidadDTO> entidadesUsuario = (List<EntidadDTO>) contexto.getSessionMap().get("entidadesFiltro");
		try {
			if (entidadesUsuario == null || entidadesUsuario.isEmpty()) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		EntidadDTO entidad = null;
		
		for(EntidadDTO aux : entidadesUsuario) {
			if(aux.getId() == Long.valueOf(value)) {
				entidad = aux;
				break;
			}
		}
		return entidad;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((EntidadDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
