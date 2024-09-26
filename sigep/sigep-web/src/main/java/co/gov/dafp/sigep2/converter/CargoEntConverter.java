package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.mbean.ext.CargoExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;


@FacesConverter("cargoEntConverter")
public class CargoEntConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		List<CargoExt> cargoEnt = ComunicacionServiciosEnt.getcargostodos(0, 100);

		CargoExt cargo = new CargoExt();
		cargo.setCodCargo(Long.valueOf(value));
		return cargoEnt.get(cargoEnt.indexOf(cargo));
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((CargoExt) value).getCodCargo());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}