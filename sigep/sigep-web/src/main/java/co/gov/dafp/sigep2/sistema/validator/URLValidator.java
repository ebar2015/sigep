/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.sistema.validator;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.util.ExpresionesRegularesConstants;
import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;

/**
 * Se crea este <code>JSFValidator</code> dado que el que se implementó para las
 * URL de sitios web no funcionó, al parecer hay algo en el regex que permite
 * insertar datos erróneos, según el Issue 0001700, sobre lo cual se hicieron
 * pruebas en las que el resultado es que el regex no funciona como se espera
 * (ejemplo, al insertar "asdasd" el regex lo toma como válido)
 *
 * @author Sergio Martínez
 * @fecha Agosto 17 de 2018
 */
@FacesValidator(value = "urlValidator")
public class URLValidator extends BaseValidator implements Validator, ClientValidator {

    public static final String REGEX_NO_SPECIAL_CHARS = "^[a-zA-Z0-9./-]+$";
    public static final String PREFIX_HTTP = "http://";
    public static final String PREFIX_HTTPS = "https://";
    public static final String PREFIX_WWW = "www.";

    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }

    @Override
    public String getValidatorId() {
        return "urlValidator";
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null || value.toString().isEmpty()) {
            return;
        }
        String valor = value.toString();
        boolean http = valor.startsWith(PREFIX_HTTP);
        boolean https = valor.startsWith(PREFIX_HTTPS);
        boolean www = valor.startsWith(PREFIX_WWW);
        String textAfterPrefix = null;
        if (!http && !https && !www) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
                            getLocale()),MessagesBundleConstants
                    .getStringMessagesBundle(MessagesBundleConstants.DLG_URL_INVALID, getLocale())));
        } else if (http) {
            textAfterPrefix = valor.substring(PREFIX_HTTP.length());
        } else if (https) {
            textAfterPrefix = valor.substring(PREFIX_HTTPS.length());
        } else if (www) {
            textAfterPrefix = valor.substring(PREFIX_WWW.length());
        }
        if (textAfterPrefix == null || textAfterPrefix.isEmpty()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
                            getLocale()),
                    MessagesBundleConstants
                    .getStringMessagesBundle(MessagesBundleConstants.DLG_URL_INVALID, getLocale())));
        } else {
            Pattern pattern = Pattern.compile(REGEX_NO_SPECIAL_CHARS);
            boolean matches = pattern.matcher(textAfterPrefix).matches();
            boolean startMatch = pattern.matcher(textAfterPrefix.substring(0, 1)).matches() && !textAfterPrefix.startsWith(".");
            boolean endMatch = pattern.matcher(textAfterPrefix.substring(textAfterPrefix.length() - 1)).matches() && !textAfterPrefix.endsWith(".");
            boolean containsPoints = textAfterPrefix.contains(".");
            if (!matches || !startMatch || !endMatch || !containsPoints) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
                                getLocale()), MessagesBundleConstants
                        .getStringMessagesBundle(MessagesBundleConstants.DLG_URL_INVALID, getLocale())));
            }
        }
    }
}
