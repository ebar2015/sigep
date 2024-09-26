package co.gov.dafp.sigep2.sistema.validator;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.util.StringPassword;

@FacesValidator("passwordValidator")
public class PasswordValidator extends BaseValidator implements Validator, ClientValidator {

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "passwordValidator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null || value.toString().isEmpty()) {
			return;
		}

		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		Boolean usuarioBloqueado = (Boolean) contexto.getSessionMap().get("usuarioBloqueado");
		UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");

		if (usuarioBloqueado != null && usuarioBloqueado) {
			return;
		}

		if (usuarioSesion != null) {
			if (value.toString().equals(usuarioSesion.getNumeroIdentificacion())) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
								getLocale()),
						MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_NO_VALIDA, getLocale())));
			}

			if (value.toString().equals(usuarioSesion.getContrasena())) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
								getLocale()),
						MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_EQUAL, getLocale())));
			}
		}

		List<String> validaciones = StringPassword.validarPassword(value.toString(), value.toString());
		if (!validaciones.isEmpty()) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES, getLocale()),
					validaciones.toString().replace("[", "").replace("]", "")));
		}
	}
}
