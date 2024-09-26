package co.gov.dafp.sigep2.converter;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.DepartamentoDTO;

@FacesConverter("departamentoConverter")
public class DepartamentoConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<DepartamentoDTO> departamento = (List<DepartamentoDTO>) contexto.getSessionMap()
				.get("departamento");
		try {
			if (departamento == null) {
				departamento = AdministracionDelegate.findDepartamento();
				contexto.getSessionMap().put("departamento", departamento);
			}
		} catch (Exception e) {
			return null;
		}

		DepartamentoDTO tipoDepartamento = new DepartamentoDTO();
		tipoDepartamento.setId(Long.valueOf(value));
		return departamento.get(departamento.indexOf(tipoDepartamento));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}
		
		//Valida si se recibe el codigo BigDecimal
		if(value instanceof BigDecimal) {
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			List<DepartamentoDTO> departamento = (List<DepartamentoDTO>) contexto.getSessionMap()
					.get("departamento");
			try {
				if (departamento == null) {
					departamento = AdministracionDelegate.findDepartamento();
					contexto.getSessionMap().put("departamento", departamento);
				}
			} catch (Exception e) {
				return null;
			}

			DepartamentoDTO tipoDepartamento = new DepartamentoDTO();
			tipoDepartamento.setId(((BigDecimal) value).longValue());
			return departamento.get(departamento.indexOf(tipoDepartamento)).getNombreDepartamento().toString();
			
		}

		Object id = String.valueOf(((DepartamentoDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
