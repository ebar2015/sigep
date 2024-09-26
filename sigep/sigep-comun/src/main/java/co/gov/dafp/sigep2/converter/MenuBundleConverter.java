package co.gov.dafp.sigep2.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import co.gov.dafp.sigep2.constante.MenuBundleConstants;

@Converter
public class MenuBundleConverter implements AttributeConverter<String, String> {
	public String convertToDatabaseColumn(String attribute) {
		return attribute;
	}

	public String convertToEntityAttribute(String dbData) {
		try {
			if (dbData != null) {
				dbData = MenuBundleConstants.getValue(dbData);
			}
		} catch (Exception e) {
			return dbData;
		}
		return dbData;
	}

}
