package co.gov.dafp.sigep2.sistema.validator;

import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.validate.ClientValidator;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.util.DateUtils;

@FacesValidator("minDateValidator")
public class MinDateValidator extends BaseValidator implements Validator, ClientValidator {

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "minDateValidator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null) {
			return;
		}

		Object minDateValue = component.getAttributes().get("minDate");
		if (minDateValue == null) {
			return;
		}

		Date minDate = (Date) minDateValue;
		Date valueDate = (Date) value;
		if (valueDate.before(minDate)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO,
							getLocale()),
					((Calendar) component).getLabel() + ": '"
							+ DateUtils.formatearACadena(valueDate, DateUtils.FECHA_FORMATO_VO) + "' "
							+ MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_FECHA_MINIMA,
									getLocale())
							+ "'" + DateUtils.formatearACadena(minDate, DateUtils.FECHA_FORMATO_VO) + "'"));
		}
	}
}
