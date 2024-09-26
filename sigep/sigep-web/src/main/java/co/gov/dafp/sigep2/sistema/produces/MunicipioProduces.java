package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.MunicipioDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class MunicipioProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<MunicipioDTO> municipio;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<MunicipioDTO> getMunicipio() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		municipio = (List<MunicipioDTO>) contexto.getSessionMap().get("municipio");
		
		if (municipio == null) {
			municipio = AdministracionDelegate.findMunicipio();
			contexto.getSessionMap().put("municipio", municipio);
		}
		return municipio;
	}
}
