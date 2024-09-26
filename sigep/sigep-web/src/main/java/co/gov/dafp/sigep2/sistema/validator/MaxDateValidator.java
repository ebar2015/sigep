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

@FacesValidator("maxDateValidator")
public class MaxDateValidator extends BaseValidator implements Validator, ClientValidator {

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "maxDateValidator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null) {
			return;
		}

		Object maxDateValue = component.getAttributes().get("maxDate");
		if (maxDateValue == null) {
			return;
		}

		
		
		Date maxDate = (Date)maxDateValue;
		Date valueDate = (Date)value;
		if (valueDate.after(maxDate)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO, getLocale()),
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_FECHA_MAXIMA, getLocale())
							+ "'" + DateUtils.formatearACadena(maxDate, DateUtils.FECHA_FORMATO_VO) + "'"));
		}
		
		
	}
}
