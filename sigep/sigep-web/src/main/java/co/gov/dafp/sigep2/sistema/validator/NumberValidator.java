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

@FacesValidator("numberValidator")
public class NumberValidator extends BaseValidator implements Validator {

	private Pattern textOnly;

	private static final String TEXT_ONLY = ExpresionesRegularesConstants.ONLY_NUMBER;

	public NumberValidator() {
		textOnly = Pattern.compile(TEXT_ONLY);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (value == null || value.toString().isEmpty()) {
			return;
		}

		Object minLength = component.getAttributes().get("minLength");
		Object maxLength = component.getAttributes().get("maxLength");

		if (minLength != null && maxLength != null) {
			try {
				int minL = Integer.parseInt(minLength.toString());
				int maxL = Integer.parseInt(maxLength.toString());
				if (value.toString().trim().length() < minL || value.toString().trim().length() > maxL) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
									getLocale()),
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_NUM_BET,
											getLocale())
									.replace("VALUE_MIN", String.valueOf(minL))
									.replace("VALUE_MAX", String.valueOf(maxL))));
				}
			} catch (NumberFormatException e) {

			}
		}

		if (minLength != null) {
			try {
				int minL = Integer.parseInt(minLength.toString());
				if (value.toString().trim().length() < minL) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
									getLocale()),
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_NUM_MIN,
											getLocale())
									.replace("VALUE_MIN", String.valueOf(minL))));
				}
			} catch (NumberFormatException e) {

			}
		}

		if (maxLength != null) {
			try {
				int maxL = Integer.parseInt(maxLength.toString());
				if (value.toString().trim().length() > maxL) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
									getLocale()),
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_NUM_MAX,
											getLocale())
									.replace("VALUE_MAX", String.valueOf(maxL))));
				}
			} catch (NumberFormatException e) {

			}
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

		if (!textOnly.matcher(value.toString().trim()).matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
							getLocale()),
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_CAMPO_NUMBER_INVALID,
							getLocale())));

		}
	}

}
