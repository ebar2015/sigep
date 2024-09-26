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

@FacesValidator("webSiteValidator")
public class WebSiteValidator extends BaseValidator implements Validator, ClientValidator {
	private Pattern pattern;
	private Pattern patternProtocol;

	private static final String WEB_SITE_PATTERN = ExpresionesRegularesConstants.REGEX_URL;
	private static final String WEB_SITE_PATTERN_PROTOCOL = ExpresionesRegularesConstants.REGEX_URL_PROTOCOL;

	public WebSiteValidator() {
		pattern = Pattern.compile(WEB_SITE_PATTERN);
		patternProtocol = Pattern.compile(WEB_SITE_PATTERN_PROTOCOL);
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "webSiteValidator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null || value.toString().isEmpty()) {
			return;
		}

		if (value.toString().startsWith("http")) {
			if (!patternProtocol.matcher(value.toString()).matches()) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
								getLocale()),
						"'" + value + "' " + MessagesBundleConstants
								.getStringMessagesBundle(MessagesBundleConstants.DLG_URL_INVALID, getLocale())));
			}
			return;
		}

		if (!pattern.matcher(value.toString()).matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
							getLocale()),
					"'" + value + "' " + MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.DLG_URL_INVALID, getLocale())));
		}
	}
}
