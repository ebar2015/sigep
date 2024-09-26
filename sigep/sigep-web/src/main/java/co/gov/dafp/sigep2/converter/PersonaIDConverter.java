package co.gov.dafp.sigep2.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;

@FacesConverter("personaIDConverter")
public class PersonaIDConverter implements Converter{
	
	Persona persona 		= new Persona();
	String objPersona 		= "";
	String primerNombre 	= "";
	String segundoNombre 	= "";
	String primerApellido 	= "";
	String segundoApellido 	= "";
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null) {
			return null;
		}
		
		return new Object();
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null) {
            return "";
        }
        try {
        	Long codigo = new Long(value.toString());
        	Persona persona =  ComunicacionServiciosHV.getPersonaPorId(codigo);
        	primerNombre = persona.getPrimerNombre() != null ? persona.getPrimerNombre() : "";
        	segundoNombre = persona.getSegundoNombre() != null ? " "+ persona.getSegundoNombre() :"";
        	primerApellido = persona.getPrimerApellido() != null ? " "+ persona.getPrimerApellido() :"";
        	segundoApellido = persona.getSegundoApellido() != null  ? " "+ persona.getSegundoApellido() :"";
        	objPersona = primerNombre + segundoNombre + primerApellido + segundoApellido ;
        	return  objPersona;
            		
		} catch (Exception e) {
			 return "";
		}
        

        
	}	
}