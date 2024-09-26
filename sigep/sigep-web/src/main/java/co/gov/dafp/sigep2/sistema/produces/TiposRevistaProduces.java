package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.TiposRevistaDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class TiposRevistaProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<TiposRevistaDTO> tiposRevista;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<TiposRevistaDTO> getTiposRevista() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		tiposRevista = (List<TiposRevistaDTO>) contexto.getSessionMap().get("tiposRevista");
		
		if (tiposRevista == null) {
			tiposRevista = HojaDeVidaDelegate.obtenerParametricasTiposRevista();
			contexto.getSessionMap().put("tiposRevista", tiposRevista);
		}
		return tiposRevista;
	}
}
