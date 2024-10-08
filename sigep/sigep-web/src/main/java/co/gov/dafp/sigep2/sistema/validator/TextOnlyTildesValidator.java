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

@FacesValidator("textOnlyTildesValidator")
public class TextOnlyTildesValidator extends BaseValidator implements Validator, ClientValidator {
	private Pattern textOnlyTildes;
	private static final String TEXT_ONLY_TILDES = ExpresionesRegularesConstants.REGEX_TEXT_TILDES_ONLY;

	public TextOnlyTildesValidator() {
		textOnlyTildes = Pattern.compile(TEXT_ONLY_TILDES);
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "textOnlyTildesValidator";
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
							"'" + value + "' " + MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_BET,
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
							"'" + value + "' "
									+ MessagesBundleConstants
											.getStringMessagesBundle(
													MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_MIN, getLocale())
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
							"'" + value + "' "
									+ MessagesBundleConstants
											.getStringMessagesBundle(
													MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_MAX, getLocale())
											.replace("VALUE_MAX", String.valueOf(maxL))));
				}
			} catch (NumberFormatException e) {

			}
		}

		if (!textOnlyTildes.matcher(value.toString().trim()).matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
							getLocale()),
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_TEXTO_INVALIDO,
							getLocale())));

		}
	}
}