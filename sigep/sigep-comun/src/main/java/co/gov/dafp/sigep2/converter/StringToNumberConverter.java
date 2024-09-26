package co.gov.dafp.sigep2.converter;

import java.math.BigInteger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.util.StringEncrypt;

@Converter
public class StringToNumberConverter implements AttributeConverter<BigInteger, String> {
	public String convertToDatabaseColumn(BigInteger attribute) {
		return attribute != null ? StringEncrypt.encrypt(ConfigurationBundleConstants.key(),
				ConfigurationBundleConstants.iv(), attribute.toString()) : null;
	}

	public BigInteger convertToEntityAttribute(String dbData) {
		if (dbData != null && !dbData.isEmpty()) {
			try {
				return BigInteger.valueOf(Long.valueOf(StringEncrypt.decrypt(ConfigurationBundleConstants.key(),
						ConfigurationBundleConstants.iv(), dbData)));
			} catch (Exception e) {
			}
		}
		return null;
	}
}
