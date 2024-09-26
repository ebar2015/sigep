package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.TipoOrientacionDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class TipoOrientacionProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<TipoOrientacionDTO> tipoOrientacion;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<TipoOrientacionDTO> getTipoOrientacion() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		tipoOrientacion = (List<TipoOrientacionDTO>) contexto.getSessionMap().get("tipoOrientacion");
		
		if (tipoOrientacion == null) {
			tipoOrientacion = HojaDeVidaDelegate.buscarTipoOrientacion();
			contexto.getSessionMap().put("tipoOrientacion", tipoOrientacion);
		}
		return tipoOrientacion;
	}
}
