package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.view.ProcesoArchivoDTO;

@FacesConverter("plantillaConverter")
public class PlantillaConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<ProcesoArchivoDTO> plantillas = (List<ProcesoArchivoDTO>) contexto.getSessionMap().get("plantillas");
		try {
			if (plantillas == null) {
				plantillas = IngresoSistemaDelegate.getPlantillas();
				contexto.getSessionMap().put("plantillas", plantillas);
			}
		} catch (Exception e) {
			return null;
		}
		ProcesoArchivoDTO plantilla = new ProcesoArchivoDTO();
		plantilla.setId(Long.valueOf(value));
		return plantillas.get(plantillas.indexOf(plantilla));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((ProcesoArchivoDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
