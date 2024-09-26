package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.TipoInstitucionDTO;

@FacesConverter("tipoInstitucionConverter")
public class TipoInstitucionConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<TipoInstitucionDTO> tipoInstitucion = (List<TipoInstitucionDTO>) contexto.getSessionMap()
				.get("tipoInstitucion");
		try {
			if (tipoInstitucion == null) {
				tipoInstitucion = HojaDeVidaDelegate.findTipoInstitucion();
				contexto.getSessionMap().put("tipoInstitucion", tipoInstitucion);
			}
		} catch (Exception e) {
			return null;
		}

		TipoInstitucionDTO tipoInstitucionDTO = new TipoInstitucionDTO();
		tipoInstitucionDTO.setId(Long.valueOf(value));
		return tipoInstitucion.get(tipoInstitucion.indexOf(tipoInstitucionDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((TipoInstitucionDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
