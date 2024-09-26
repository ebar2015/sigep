package co.gov.dafp.sigep2.sistema.validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.util.DateUtils;

@FacesValidator("edadMinDateValidator")
public class EdadMinDateValidator extends BaseValidator implements Validator, ClientValidator {

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "edadMinDateValidator";
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

		Date valueDate = (Date) value;
		LocalDate hoy = LocalDate.now();
		LocalDate fechaNac = valueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period periodo = Period.between(fechaNac, hoy);

		int edad = periodo.getYears();
		int edadMin = ConfigurationBundleConstants.getInt(ConfigurationBundleConstants.PASS_EDAD_MINIMA);
		int edadMax = ConfigurationBundleConstants.getInt(ConfigurationBundleConstants.PASS_EDAD_MAXIMA);

		if (edad < edadMin) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants .getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO, getLocale()),
									MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_EDAD_MINIMA, getLocale())
									+ " '" + edadMin + "' " + TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_ANIOS, getLocale()) ));
		}

		if (periodo.getYears() > edadMax
				|| (periodo.getYears() >= edadMax && (periodo.getMonths() > 0 || periodo.getDays() > 0))) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO, getLocale()),
									MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_EDAD_MAXIMA, getLocale())
									+ " '" + edadMax + "' " +  TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_ANIOS, getLocale()) ));
		}

	}
}
