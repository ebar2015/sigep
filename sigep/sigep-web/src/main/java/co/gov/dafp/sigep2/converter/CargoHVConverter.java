/**
 * 
 */
package co.gov.dafp.sigep2.converter;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.entities.CargoHojaVida;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

/**
 * @author Maria Alejandra C
 */
@FacesConverter(value = "cargoHVConverter")
public class CargoHVConverter implements Converter{
	CargoHojaVida resultCargoHV;
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			if (value == null || value.isEmpty()) {
	            return null;
	        }else {
	        	ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
	        	resultCargoHV = (CargoHojaVida) contexto.getSessionMap().get("cargoHV");
	    		try {
					CargoHojaVida filtroCargoHV = new CargoHojaVida();
					filtroCargoHV.setCodCargoHojaVida(new BigDecimal (value));
		        	resultCargoHV = ComunicacionServiciosSis.getCargoHojaVidaById(filtroCargoHV);
	    		} catch (Exception e) {
	    			return null;
	    		}
	        	return resultCargoHV;
	    	}
		}catch (Exception e) {
			return null;
		}
     }
		
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (value == null) {
	            return null;
	      }
		 
		 
	       return String.valueOf(((CargoHojaVida) value).getCodCargoHojaVida());
	}
}
