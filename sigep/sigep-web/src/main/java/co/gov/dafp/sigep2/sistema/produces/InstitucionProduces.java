package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.InstitucionEducativaDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class InstitucionProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<InstitucionEducativaDTO> institucion;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<InstitucionEducativaDTO> getInstitucion() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		institucion = (List<InstitucionEducativaDTO>) contexto.getSessionMap().get("institucion");
		
		if (institucion == null) {
			institucion = HojaDeVidaDelegate.findInstitucion();
			contexto.getSessionMap().put("institucion", institucion);
		}
		return institucion;
	}
}
