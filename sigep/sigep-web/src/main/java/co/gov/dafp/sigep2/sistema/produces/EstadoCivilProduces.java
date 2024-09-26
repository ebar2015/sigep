package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.EstadoCivilDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class EstadoCivilProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<EstadoCivilDTO> estadoCivil;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<EstadoCivilDTO> getEstadoCivil() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		estadoCivil = (List<EstadoCivilDTO>) contexto.getSessionMap().get("estadoCivil");
		
		if (estadoCivil == null) {
			estadoCivil = AdministracionDelegate.findEstadoCivil();
			contexto.getSessionMap().put("estadoCivil", estadoCivil);
		}
		return estadoCivil;
	}
}
