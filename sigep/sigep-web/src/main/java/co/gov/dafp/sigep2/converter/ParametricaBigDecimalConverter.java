package co.gov.dafp.sigep2.converter;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.ParametricaDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

@FacesConverter("parametricaBgDecimalConverter")
public class ParametricaBigDecimalConverter implements Converter{
	
	List<ParametricaDTO> tablasParametrica = null;

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		tablasParametrica = (List<ParametricaDTO>) contexto.getSessionMap().get("tablasParametrica");
		try {
			if (tablasParametrica == null) {
				tablasParametrica = AdministracionDelegate.listarTablasParametrica();
				contexto.getSessionMap().put("tablasParametrica", tablasParametrica);
			}
			
		} catch (Exception e) {
			return null;
		}
		ParametricaDTO parametrica = new ParametricaDTO();
		parametrica.setId(Long.valueOf(value));
		return tablasParametrica.get(tablasParametrica.indexOf(parametrica));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null) {
            return "";
        }
        try {
        	BigDecimal codigo = new BigDecimal(value.toString());
        	Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(codigo);
            return parametrica.getValorParametro();
		} catch (Exception e) {
			 return "";
		}
        

        
	}	
}