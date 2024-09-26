package co.gov.dafp.sigep2.sistema.validator;

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
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@FacesValidator("passwordSesionValidator")
public class PasswordSesionValidator extends BaseValidator implements Validator, ClientValidator {

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "passwordSesionValidator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null || value.toString().isEmpty()) {
			return;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();

		Object nickName = component.getAttributes().get("nickName");
		Object documentType = component.getAttributes().get("documentType");
		if ((nickName != null && documentType == null) || (nickName == null && documentType != null)) {
			return;
		}
		if (nickName != null && documentType != null) {
			try {
				IngresoSistemaDelegate.login(Long.valueOf(documentType.toString()), nickName.toString(),
						value.toString(), null, null);
				contexto.getSessionMap().put("usuarioBloqueado", false);
				return;
			} catch (SIGEP2SistemaException e) {
				Boolean usuarioBloqueado = (Boolean) contexto.getSessionMap().get("usuarioBloqueado");
				if (e.getMessage().contains(MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_INVALIDO)) {
					if (usuarioBloqueado == null) {
						contexto.getSessionMap().put("usuarioBloqueado", false);
					}
				} else if (e.getMessage().contains(MessagesBundleConstants.MSG_CUENTA_USUARIO_BLOQUEADA)) {
					contexto.getSessionMap().put("usuarioBloqueado", true);
					return;
				} else if (e.getMessage().contains(MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_EXISTE)) {
					return;
				}
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
								getLocale()),
						MessagesBundleConstants.getStringMessagesBundle(e.getMessage(), getLocale())));
			}
		}
		UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		TipoDocumentoDTO tipoDocumento = (TipoDocumentoDTO) contexto.getSessionMap().get("tipoDocumento");
		if (usuarioSesion == null || tipoDocumento == null) {
			return;
		}

		try {
			Object password = component.getAttributes().get("password");
			if (password == null) {
				password = value;
			}
			IngresoSistemaDelegate.login(tipoDocumento.getId(), usuarioSesion.getNombreUsuario(), password.toString(),
					null, null);
		} catch (SIGEP2SistemaException e) {
			if (e.getMessage().contains(MessagesBundleConstants.MSG_CUENTA_USUARIO_BLOQUEADA)) {
				contexto.getSessionMap().put("usuarioBloqueado", true);
				return;
			}
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
							getLocale()),
					MessagesBundleConstants.getStringMessagesBundle(e.getMessage(), getLocale())));
		}
	}
}
