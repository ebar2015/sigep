package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.PoblacionEtnicaDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class PoblacionEtnicaProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<PoblacionEtnicaDTO> poblacionEtnica;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<PoblacionEtnicaDTO> getPoblacionEtnica() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		poblacionEtnica = (List<PoblacionEtnicaDTO>) contexto.getSessionMap().get("poblacionEtnica");
		
		if (poblacionEtnica == null) {
			poblacionEtnica = AdministracionDelegate.findPoblacionEtnica();
			contexto.getSessionMap().put("poblacionEtnica", poblacionEtnica);
		}
		return poblacionEtnica;
	}
}
