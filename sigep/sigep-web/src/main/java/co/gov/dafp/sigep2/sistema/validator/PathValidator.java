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

@FacesValidator("pathValidator")
public class PathValidator extends BaseValidator implements Validator, ClientValidator {
	private Pattern patternPathWindows;
	private Pattern patternPathFTP;
	private Pattern patternPathNetwork;

	private static final String PATH_PATTERN_WINDOWS = ExpresionesRegularesConstants.REGEX_PATH_WINDOWS;
	private static final String PATH_PATTERN_FTP = ExpresionesRegularesConstants.REGEX_PATH_FTP;
	private static final String PATH_PATTERN_NETWORK = ExpresionesRegularesConstants.REGEX_PATH_NETWORK;

	public PathValidator() {
		patternPathWindows = Pattern.compile(PATH_PATTERN_WINDOWS);
		patternPathFTP = Pattern.compile(PATH_PATTERN_FTP);
		patternPathNetwork = Pattern.compile(PATH_PATTERN_NETWORK);
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "pathValidator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null || value.toString().isEmpty()) {
			return;
		}

		if (!patternPathWindows.matcher(value.toString()).matches()
				&& !patternPathFTP.matcher(value.toString()).matches()
				&& !patternPathNetwork.matcher(value.toString()).matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO,
							getLocale()),
					"'" + value + "' " + MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.DLG_PATH_INVALID, getLocale())));

		}
	}
}
