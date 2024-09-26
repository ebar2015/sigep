package co.gov.dafp.sigep2.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.util.StringEncrypt;

@Converter
public class EncryptCaseConverter implements AttributeConverter<String, String> {

	public String convertToDatabaseColumn(String attribute) {
		try {
			if (attribute != null) {
				attribute = StringEncrypt.encrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(),
						attribute);
			}
		} catch (Exception e) {
			return attribute;
		}
		return attribute;
	}

	public String convertToEntityAttribute(String dbData) {
		try {
			if (dbData != null) {
				dbData = StringEncrypt.decrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(),
						dbData);
			}
		} catch (Exception e) {
			return dbData;
		}
		return dbData;
	}

}
