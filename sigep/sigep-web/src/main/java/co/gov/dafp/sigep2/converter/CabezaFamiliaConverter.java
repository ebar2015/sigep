package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.CabezaFamiliaDTO;

@FacesConverter("cabezaFamiliaConverter")
public class CabezaFamiliaConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<CabezaFamiliaDTO> cabezaFamilia = (List<CabezaFamiliaDTO>) contexto.getSessionMap().get("cabezaFamilia");
		try {
			if (cabezaFamilia == null) {
				cabezaFamilia = HojaDeVidaDelegate.obtenerParametricasCabezaFamilia();
				contexto.getSessionMap().put("cabezaFamilia", cabezaFamilia);
			}
		} catch (Exception e) {
			return null;
		}

		CabezaFamiliaDTO cabezaFamiliaDTO = new CabezaFamiliaDTO();
		cabezaFamiliaDTO.setId(Long.valueOf(value));
		return cabezaFamilia.get(cabezaFamilia.indexOf(cabezaFamiliaDTO));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((CabezaFamiliaDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
