package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.InstitucionEducativaDTO;

@FacesConverter("institucionConverter")
public class InstitucionConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<InstitucionEducativaDTO> institucion = (List<InstitucionEducativaDTO>) contexto.getSessionMap()
				.get("institucion");
		try {
			if (institucion == null) {
				institucion = HojaDeVidaDelegate.findInstitucion();
				contexto.getSessionMap().put("institucion", institucion);
			}
		} catch (Exception e) {
			return null;
		}

		InstitucionEducativaDTO institucionEducativaDTO = new InstitucionEducativaDTO();
		institucionEducativaDTO.setId(Long.valueOf(value));
		return institucion.get(institucion.indexOf(institucionEducativaDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((InstitucionEducativaDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
