package co.gov.dafp.sigep2.sistema.validator;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.util.ExpresionesRegularesConstants;

@FacesValidator("decimalValidator")
public class DecimalValidator extends BaseValidator implements Validator {

	private Pattern decimalOnly;

	private static final String REGEX_DECIMAL_ONLY = ExpresionesRegularesConstants.REGEX_DECIMAL_ONLY;

	public DecimalValidator() {
		decimalOnly = Pattern.compile(REGEX_DECIMAL_ONLY);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (value == null || value.toString().isEmpty()) {
			return;
		}

		Object minValue = component.getAttributes().get("minValue");
		Object maxValue = component.getAttributes().get("maxValue");

		if (minValue != null && maxValue != null) {
			try {
				Double minV = Double.parseDouble(minValue.toString());
				Double maxV = Double.parseDouble(maxValue.toString());
				Double valor = new Double(value.toString().trim());
				if (valor < minV || valor > maxV) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
									getLocale()),
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.DLG_VALOR_CAMPO_INVALID_NUM_BET,
											getLocale())
									.replace("VALUE_MIN", String.valueOf(minV.intValue()))
									.replace("VALUE_MAX", String.valueOf(maxV.intValue()))));
				}
			} catch (NumberFormatException e) {

			}
		}

		if (minValue != null) {
			try {
				Double minV = Double.parseDouble(minValue.toString());
				Double valor = new Double(value.toString().trim());
				if (valor < minV) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
									getLocale()),
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.DLG_VALOR_CAMPO_INVALID_NUM_MIN,
											getLocale())
									.replace("VALUE_MIN", String.valueOf(minV.intValue()))));
				}
			} catch (NumberFormatException e) {

			}
		}

		if (maxValue != null) {
			try {
				Double maxV = Double.parseDouble(maxValue.toString());
				Double valor = new Double(value.toString().trim());
				if (valor > maxV) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
									getLocale()),
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.DLG_VALOR_CAMPO_INVALID_NUM_MAX,
											getLocale())
									.replace("VALUE_MAX", String.valueOf(maxV.intValue()))));
				}
			} catch (NumberFormatException e) {

			}
		}

		if (!decimalOnly.matcher(value.toString().trim()).matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
							getLocale()),
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_CAMPO_NUMBER_INVALID,
							getLocale())));

		}
	}

}
