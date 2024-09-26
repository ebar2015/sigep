package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.entities.Denominacion;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

@FacesConverter("codigoPostalConverter")
public class CodigoPostalConverter implements Converter{
	
	List<Denominacion> denominacionList = null;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null) {
            return "";
        }
        try {
        	Long codigo = Long.parseLong(value.toString());
        	Municipio municipio = ComunicacionServiciosSis.getMunicipiosid(codigo);
        	String codigoPostal = municipio.getCodigoPostal();
        	if(codigoPostal == null) {
        		codigoPostal = "";
        	}
            return codigoPostal;
		} catch (Exception e) {
			 return "";
		}
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null) {
            return "";
        }
        try {
        	Long codigo = Long.parseLong(value.toString());
        	Municipio municipio = ComunicacionServiciosSis.getMunicipiosid(codigo);
        	String codigoPostal = municipio.getCodigoPostal();
        	if(codigoPostal == null) {
        		codigoPostal = "";
        	}
            return codigoPostal;
		} catch (Exception e) {
			 return "";
		}
        

        
	}	
}