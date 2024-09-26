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
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.util.ExpresionesRegularesConstants;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@FacesValidator("nickNameValidator")
public class NickNameValidator extends BaseValidator implements Validator, ClientValidator {
	private Pattern patternNickName;

	private static final String PATH_NICK_NAME = ExpresionesRegularesConstants.REGEX_NICK_NAME;

	public NickNameValidator() {
		patternNickName = Pattern.compile(PATH_NICK_NAME);
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "nickNameValidator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null || value.toString().isEmpty()) {
			return;
		}

		if (value.toString().startsWith("0")) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
							getLocale()),
					"'" + value + "' " + MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.DLG_CAMPO_NOT_START_WITH_ZERO, getLocale())));
		}

		if (!patternNickName.matcher(value.toString()).matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
							getLocale()),
					"'" + value + "' " + MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.DLG_NICK_NAME_INVALID, getLocale())));

		}

		Object documentType = component.getAttributes().get("documentType");
		if (documentType != null) {
			try {
				UsuarioPasswordDTO usuario = IngresoSistemaDelegate
						.getUsuarioByLogin(Long.valueOf(documentType.toString()), value.toString());
				if (usuario == null) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
									getLocale()),
							"'" + value + "' " + MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_EXISTE, getLocale())));
				}
			} catch (NumberFormatException | SIGEP2SistemaException e) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
								getLocale()),
						"'" + value + "' " + MessagesBundleConstants
								.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO, getLocale())));
			}
		}
	}
}
