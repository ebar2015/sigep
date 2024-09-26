/**
 * 
 */
package co.gov.dafp.sigep2.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Norma;

/**
 * @author joseviscaya
 *
 */

@FacesConverter(value = "normaBdConverter")
public class NormaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
            return null;
        }else {
        	 Norma  norma = new  Norma();
        	 norma.setNumeroNorma(value);
        	 return norma;
    	}
        }
		

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		 if (value == null || !(value instanceof Norma) || ((Norma) value).getNumeroNorma() == null ) {
	            return null;
	        }
	        return ((Norma) value).getNumeroNorma();
	}

}
