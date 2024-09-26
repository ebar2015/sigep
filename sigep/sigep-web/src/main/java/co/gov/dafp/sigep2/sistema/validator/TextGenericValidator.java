package co.gov.dafp.sigep2.sistema.validator;

import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;

@FacesValidator("textGenericValidator")
public class TextGenericValidator extends BaseValidator implements Validator, ClientValidator {

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "textGenericValidator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null || value.toString().isEmpty()) {
			return;
		}

		Object pattern = component.getAttributes().get("pattern");
		if (pattern == null) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
							getLocale()),
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_CONFIGURATION_INVALID,
							getLocale())));
		}
		Pattern textGeneric = null;
		try {
			textGeneric = Pattern.compile(pattern.toString());
		} catch (Exception e) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
							getLocale()),
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_CONFIGURATION_INVALID,
							getLocale()) + ". '" + e + "'"));
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
							"'" + value + "' " + MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_BET, getLocale())));
				}
			} catch (NumberFormatException e) {

			}
		}

		if (minLength != null && maxLength == null) {
			try {
				int minL = Integer.parseInt(minLength.toString());
				if (value.toString().trim().length() > minL) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
									getLocale()),
							"'" + value + "' " + MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_MIN, getLocale())));
				}
			} catch (NumberFormatException e) {

			}
		}

		if (maxLength != null && minLength == null) {
			try {
				int maxL = Integer.parseInt(maxLength.toString());
				if (value.toString().trim().length() > maxL) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
									getLocale()),
							"'" + value + "' " + MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_MAX, getLocale())));
				}
			} catch (NumberFormatException e) {

			}
		}

		if (!textGeneric.matcher(value.toString().trim()).matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
							getLocale()),
					"'" + value + "' " + MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.DLG_CAMPO_INVALID, getLocale())));

		}
	}
}
