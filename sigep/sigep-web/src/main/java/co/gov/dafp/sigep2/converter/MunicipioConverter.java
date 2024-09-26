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
import co.gov.dafp.sigep2.entity.MunicipioDTO;

@FacesConverter("municipioConverter")
public class MunicipioConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<MunicipioDTO> municipio = (List<MunicipioDTO>) contexto.getSessionMap()
				.get("municipio");
		try {
			if (municipio == null) {
				municipio = AdministracionDelegate.findMunicipio();
				contexto.getSessionMap().put("municipio", municipio);
			}
		} catch (Exception e) {
			return null;
		}

		MunicipioDTO tipoMunicipio = new MunicipioDTO();
		tipoMunicipio.setId(Long.valueOf(value));
		return municipio.get(municipio.indexOf(tipoMunicipio));
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
					List<MunicipioDTO> municipio = (List<MunicipioDTO>) contexto.getSessionMap()
							.get("municipio");
					try {
						if (municipio == null) {
							municipio = AdministracionDelegate.findMunicipio();
							contexto.getSessionMap().put("municipio", municipio);
						}
					} catch (Exception e) {
						return null;
					}

					MunicipioDTO tipoMunicipio = new MunicipioDTO();
					tipoMunicipio.setId(((BigDecimal) value).longValue());
					return municipio.get(municipio.indexOf(tipoMunicipio)).getNombreMunicipio().toString();					
				}

		Object id = String.valueOf(((MunicipioDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
