package co.gov.dafp.sigep2.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.entities.InstitucionEducativa;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

/**
 * @author Maria Alejandra C
 */
@FacesConverter(value = "institucionEducativaEdFormalConverter")
public class InstitucionEducativaEdFormalConverter implements Converter{
	InstitucionEducativa resultInstitucionEducativaEdFormal;
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			if (value == null || value.isEmpty()) {
	            return null;
	        }else {
	        	ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
	        	resultInstitucionEducativaEdFormal = (InstitucionEducativa) contexto.getSessionMap().get("institucionEdFormal");
	    		try {
		        	resultInstitucionEducativaEdFormal = ComunicacionServiciosSis.getInstitucionEducaporId(Long.parseLong(value));
	    		} catch (Exception e) {
	    			return null;
	    		}
	        	return resultInstitucionEducativaEdFormal;
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
	       return String.valueOf(((InstitucionEducativa) value).getCodInstitucionEducativa());
	}

}
