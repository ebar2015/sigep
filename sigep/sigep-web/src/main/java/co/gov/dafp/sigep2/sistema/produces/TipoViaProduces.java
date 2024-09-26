package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.TipoViaDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class TipoViaProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<TipoViaDTO> tipoVia;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<TipoViaDTO> getTipoVia() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		tipoVia = (List<TipoViaDTO>) contexto.getSessionMap().get("tipoVia");
		
		if (tipoVia == null) {
			tipoVia = HojaDeVidaDelegate.buscarTipoVia();
			contexto.getSessionMap().put("tipoVia", tipoVia);
		}
		return tipoVia;
	}
}
