package co.gov.dafp.sigep2.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.util.logger.Logger;

public class EntityConverter<T extends EntidadBaseDTO> implements Converter {
	

	// private ConverterDao converterDao;

	private Logger logger = Logger.getInstance(EntityConverter.class);

	public EntityConverter(Class<T> clazz) {
		// this.converterDao = CDI.current().select(ConverterDao.class).get();
		logger.log().debug("EntityConverter(Class<T> clazz)", "Conversion a", clazz);
	}

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || Long.valueOf(value) == 0) {
			return null;
		}
		Object o = null;// this.converterDao.find(clazz, Long.parseLong(value));
		logger.log().debug("Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)",
				"Conversion a", o);
		return o;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((EntidadDTO) value).getId());
		if (id == null || Long.valueOf(id.toString()) == 0) {
			return "";
		}

		logger.log().debug("String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value)",
				"Conversion a", id);
		return id.toString();
	}
}
