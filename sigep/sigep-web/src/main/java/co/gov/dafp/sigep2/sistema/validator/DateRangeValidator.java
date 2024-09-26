package co.gov.dafp.sigep2.sistema.validator;

import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;

@FacesValidator("dateRangeValidator")
public class DateRangeValidator extends BaseValidator implements Validator, ClientValidator {

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "dateRangeValidator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null) {
			return;
		}
		
		//Leave the null handling of startDate to required="true"
        Object startDateValue = component.getAttributes().get("startDate");
        Object startDateName = component.getAttributes().get("startDateName");
        Object endDateName = component.getAttributes().get("endDateName");
        
        if (startDateValue==null) {
            return;
        }
        
        Date startDate = (Date)startDateValue;
        Date endDate = (Date)value; 
        if (endDate.before(startDate)) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
        			MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO,getLocale()),
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_FECHA_INVALIDA_MENOR_INICIAL,getLocale())
							.replace("START_DATE_NAME", String.valueOf(startDateName))
							.replace("END_DATE_NAME", String.valueOf(endDateName))));
        }
	}
}
