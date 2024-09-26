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

import co.gov.dafp.sigep2.entities.DependenciaHojaVida;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

/**
 * @author Maria Alejandra C
 */
@FacesConverter(value = "dependenciaHVConverter")
public class DependenciaHVConverter implements Converter{
	DependenciaHojaVida resultDependencia;
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			if (value == null || value.isEmpty()) {
	            return null;
	        }else {
	        	ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
	        	resultDependencia = (DependenciaHojaVida) contexto.getSessionMap().get("dependenciaHV");
	    		try {
					DependenciaHojaVida filtroDependenciaHVS = new DependenciaHojaVida();
		        	filtroDependenciaHVS.setCodDependenciaHojaVida(new BigDecimal (value));
		        	resultDependencia = ComunicacionServiciosSis.getDependenciaHojaVidaById(filtroDependenciaHVS);
	    		} catch (Exception e) {
	    			return null;
	    		}
	        	return resultDependencia;
	    	}
		}catch(Exception e) {
			return null;
		}
     }
		
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		 if (value == null) {
	            return null;
	      }
	       return String.valueOf(((DependenciaHojaVida) value).getCodDependenciaHojaVida());
	}

}
