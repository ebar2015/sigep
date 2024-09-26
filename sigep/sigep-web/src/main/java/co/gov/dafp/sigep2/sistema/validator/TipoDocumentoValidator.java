package co.gov.dafp.sigep2.sistema.validator;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.enums.TipoDocumentoIdentidadEnum;


/**
* @author Nestor J. Riasco Mosquera
* @version 3.0
* @Class Clase para validar los Tipos de Documentos Permitidos Para Ingresar al sistema, Activar y Desasociar Usuarios
* @Proyect SIGEP II
* @Company ADA S.A
* @Module Ingreso al Sistema
* Fecha: 06/06/2018
*/

@FacesValidator("tipoDocumentoValidator")
public class TipoDocumentoValidator extends BaseValidator implements Validator, ClientValidator {
	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "tipoDocumentoValidator";
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null) {
			return;
		}
		
		TipoDocumentoDTO tipoDoc = (TipoDocumentoDTO)value;

		if (tipoDoc != null) {
			try {
				if (tipoDoc.getId().intValue() != TipoParametro.TIPO_DOCUMENTO_CEDULA.getValue()
						& tipoDoc.getId().intValue() != TipoParametro.TIPO_DOCUMENTO_CEDULA_EXTRANJERIA.getValue()
						& tipoDoc.getId().intValue() != TipoParametro.TIPO_DOCUMENTO_PASAPORTE.getValue()) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
									getLocale()),
							"'" + tipoDoc.getDescripcion() + "' " + MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.DLG_TIPO_DOCUMENTO_INVALIDO, getLocale())));
				}
			} catch (NumberFormatException e) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,
								getLocale()),
						"'" + value + "' " + MessagesBundleConstants
								.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO, getLocale())));
			}
		}
	}

}
