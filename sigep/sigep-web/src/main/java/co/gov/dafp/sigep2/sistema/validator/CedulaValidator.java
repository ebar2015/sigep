package co.gov.dafp.sigep2.sistema.validator;

import java.time.LocalDate;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;

@FacesValidator("cedulaValidator")
public class CedulaValidator extends BaseValidator implements Validator, ClientValidator {

	private String strPicoCedula;
	private Boolean blnHabilitaPicoCedula;
	
	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "cedulaValidator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null || value.toString().isEmpty()) {
			return;
		}
		this.strPicoCedula =ConfigurationBundleConstants.getString(ConfigurationBundleConstants.HABILITAR_PICO_CEDULA);
		this.blnHabilitaPicoCedula = false;
		if(strPicoCedula!= null && !strPicoCedula.equals("")){
			if("S".equals(strPicoCedula)) {
				this.blnHabilitaPicoCedula = true;
			}
		}
		
		if(blnHabilitaPicoCedula) {
			LocalDate date = LocalDate.now();
			int dia = date.getDayOfMonth(); /**el día será la posición de la matriz creada para la validación, por tanto, es necesario realizar la resta (-1)*/
			String cedula = value.toString();
			int  digito = Integer.parseInt(cedula.substring(cedula.length() - 1));    
			if(!validarPicoCedula(dia, digito )) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
								getLocale()),
						MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.MSG_PICO_CEDULA, getLocale())));
			};
		}
	}
	
	/**
	 * Metodo que se encarga de realizar la validación del pico y cédula; para esto, utiliza 2 variables:
	 * (1) variable 'día' -> esta variable corresponde a día de la semana actual
	 * (2) variable 'digito' -> esta variable corresponde al último digito de la cédula de la persona
	 * De esta manera, tanto el día como el digito deben de ser pares para que permita el ingreso a la plataforma.
	 * */
	public boolean validarPicoCedula(int dia, int digito) {
		boolean blnPermiso = false;
		if((dia % 2 == 0 && digito % 2 == 0) || (dia % 2 != 0 && digito % 2 != 0)) {
			blnPermiso = true;
		}
		return blnPermiso;
	}

	public String getStrPicoCedula() {
		return strPicoCedula;
	}

	public void setStrPicoCedula(String strPicoCedula) {
		this.strPicoCedula = strPicoCedula;
	}

	public Boolean getBlnHabilitaPicoCedula() {
		return blnHabilitaPicoCedula;
	}

	public void setBlnHabilitaPicoCedula(Boolean blnHabilitaPicoCedula) {
		this.blnHabilitaPicoCedula = blnHabilitaPicoCedula;
	}
}
