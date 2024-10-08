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
import co.gov.dafp.sigep2.util.ExpresionesRegularesConstants;

@FacesValidator("textAlfaCaractEspecialValidator")
public class TextAlfaCarEspecialesValidator extends BaseValidator implements Validator, ClientValidator {
	private Pattern textAlfaCaractEspecialValidator;
	private static final String TEXT_ALFA_CARAESPECIAL= ExpresionesRegularesConstants.REGEX_ALFA_NUM_ESPECIALES;

	public TextAlfaCarEspecialesValidator() {
		textAlfaCaractEspecialValidator = Pattern.compile(TEXT_ALFA_CARAESPECIAL);
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "textAlfaCaractEspecialValidator";
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
							"'" + value + "' " + MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_BET, getLocale())));
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
							"'" + value + "' " + MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_MIN, getLocale())));
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
							"'" + value + "' " + MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_MAX, getLocale())));
				}
			} catch (NumberFormatException e) {

			}
		}

		if (!textAlfaCaractEspecialValidator.matcher(value.toString().trim()).matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
							getLocale()), MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.DLG_CAMPO_INVALID, getLocale())));

		}
	}
}