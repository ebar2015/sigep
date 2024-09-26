package co.gov.dafp.sigep2.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToNumberConverter implements AttributeConverter<Boolean, Long> {

	public Long convertToDatabaseColumn(Boolean value) {
		return value != null && value ? 1L : 0L;
	}

	public Boolean convertToEntityAttribute(Long value) {
		return value != null && value.equals(1L) ? Boolean.TRUE : Boolean.FALSE;
	}

}
