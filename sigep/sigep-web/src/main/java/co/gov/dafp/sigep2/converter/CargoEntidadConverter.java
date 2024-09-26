package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.CargoDTO;


@FacesConverter("cargoEntidadConverter")
public class CargoEntidadConverter implements Converter{
	
	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<CargoDTO> cargoEntidad = (List<CargoDTO>) contexto.getSessionMap()
				.get("cargoEntidad");
		try {
			if (cargoEntidad == null) {
				cargoEntidad = HojaDeVidaDelegate.buscarCargoPorEntidad();
				contexto.getSessionMap().put("cargoEntidad", cargoEntidad);
			}
		} catch (Exception e) {
			return null;
		}

		CargoDTO cargoEntidadDTO = new CargoDTO();
		cargoEntidadDTO.setId(Long.valueOf(value));
		return cargoEntidad.get(cargoEntidad.indexOf(cargoEntidadDTO));
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((CargoDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}


}