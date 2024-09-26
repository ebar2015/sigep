package co.gov.dafp.sigep2.converter;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

@FacesConverter("paisIDConverter")
public class PaisIDConverter implements Converter{
	
	List<Pais> paisList = null;

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
        	BigDecimal codigo = new BigDecimal(value.toString());
        	Pais pais = ComunicacionServiciosSis.getPaisId(codigo);
            return pais.getNombrePais();
		} catch (Exception e) {
			 return "";
		}
        

        
	}	
}