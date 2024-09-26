package co.gov.dafp.sigep2.converter;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

@FacesConverter("departamentoIDConverter")
public class DepartamentoIDConverter implements Converter{
	
	List<Departamento> departamentoList = null;

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
        	Departamento departamento = ComunicacionServiciosSis.getDepartamentoId(codigo);
            return departamento.getNombreDepartamento();
		} catch (Exception e) {
			 return "";
		}
        

        
	}	
}