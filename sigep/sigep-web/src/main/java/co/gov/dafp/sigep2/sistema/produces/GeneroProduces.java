package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.GeneroDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class GeneroProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<GeneroDTO> genero;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<GeneroDTO> getGenero() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		genero = (List<GeneroDTO>) contexto.getSessionMap().get("genero");
		
		if (genero == null) {
			genero = AdministracionDelegate.findGenero();
			contexto.getSessionMap().put("genero", genero);
		}
		return genero;
	}
}
