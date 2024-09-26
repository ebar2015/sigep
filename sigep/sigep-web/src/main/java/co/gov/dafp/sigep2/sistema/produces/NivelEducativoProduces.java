package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.NivelEducativoDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class NivelEducativoProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<NivelEducativoDTO> nivelEducativo;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<NivelEducativoDTO> getNivelEducativo() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		nivelEducativo = (List<NivelEducativoDTO>) contexto.getSessionMap()
										.get("nivelEducativo");
		
		if (nivelEducativo == null) {
			nivelEducativo = AdministracionDelegate.findNivelEducativo();
			contexto.getSessionMap().put("nivelEducativo", nivelEducativo);
		}
		return nivelEducativo;
	}
}