package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.TipoDiscapacidadDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class TipoDiscapacidadProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<TipoDiscapacidadDTO> tipoDiscapacidad;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<TipoDiscapacidadDTO> getTipoDiscapacidad() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		tipoDiscapacidad = (List<TipoDiscapacidadDTO>) contexto.getSessionMap().get("tipoDiscapacidad");
		
		if (tipoDiscapacidad == null) {
			tipoDiscapacidad = HojaDeVidaDelegate.findTipoDiscapacidad();
			contexto.getSessionMap().put("tipoDiscapacidad", tipoDiscapacidad);
		}
		return tipoDiscapacidad;
	}
}
