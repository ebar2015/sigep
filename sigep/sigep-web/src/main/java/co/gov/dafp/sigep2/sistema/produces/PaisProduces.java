package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class PaisProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<PaisDTO> pais;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<PaisDTO> getPais() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		pais = (List<PaisDTO>) contexto.getSessionMap().get("pais");
		
		if (pais == null) {
			pais = AdministracionDelegate.findPais();
			contexto.getSessionMap().put("pais", pais);
		}
		return pais;
	}
}
