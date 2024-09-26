package co.gov.dafp.sigep2.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UpperCaseConverter implements AttributeConverter<String, String> {

	public String convertToDatabaseColumn(String attribute) {
		return attribute != null && !attribute.isEmpty() ? attribute.toUpperCase() : attribute;
	}

	public String convertToEntityAttribute(String dbData) {
		if (dbData != null) {
			dbData = dbData.toUpperCase();
		}
		return dbData;
	}

}