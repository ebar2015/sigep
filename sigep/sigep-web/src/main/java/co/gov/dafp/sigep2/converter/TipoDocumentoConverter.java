package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;

@FacesConverter("tipoDocumentoConverter")
public class TipoDocumentoConverter implements Converter {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<TipoDocumentoDTO> tiposDocumentos = (List<TipoDocumentoDTO>) contexto.getSessionMap()
				.get("tiposDocumentos");
		try {
			if (tiposDocumentos == null) {
				tiposDocumentos = AdministracionDelegate.findTipoDocumento();
				contexto.getSessionMap().put("tiposDocumentos", tiposDocumentos);
			}
		} catch (Exception e) {
			return null;
		}

		TipoDocumentoDTO tipoDoc = new TipoDocumentoDTO();
		tipoDoc.setId(Long.valueOf(value));
		return tiposDocumentos.get(tiposDocumentos.indexOf(tipoDoc));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((TipoDocumentoDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
