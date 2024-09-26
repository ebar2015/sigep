package co.gov.dafp.sigep2.sistema.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.inputtext.InputText;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;;;

@FacesValidator("maxByteLengthValidator")
public class MaxByteLengthValidator extends BaseValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	if (value == null || value.toString().isEmpty()) {
	    return;
	}

	int maxlength = ((InputText) component).getMaxlength();
	if (maxlength <= 0) {
	    return;
	}

	int lengthb = value.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8).length;

	if (lengthb > maxlength) {
	    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
		    MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO,
			    getLocale()),
		    MessagesBundleConstants
			    .getStringMessagesBundle(MessagesBundleConstants.DLG_LONGITUD_CAMPO_INVALID_MAX,
				    getLocale())
			    .replace("VALUE_MAX", String.valueOf(maxlength)

			    )));
	}
	return;
    }
}