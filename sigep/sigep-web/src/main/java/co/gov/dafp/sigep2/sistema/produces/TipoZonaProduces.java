package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.TipoZonaDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class TipoZonaProduces implements Serializable {
	private static final long serialVersionUID = 7808259411202454256L;
	
	private List<TipoZonaDTO> tipoZona;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<TipoZonaDTO> getTipoZona() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		tipoZona = (List<TipoZonaDTO>) contexto.getSessionMap().get("tipoZona");
		
		if (tipoZona == null) {
			tipoZona = AdministracionDelegate.findTipoZona();
			contexto.getSessionMap().put("tipoZona", tipoZona);
		}
		return tipoZona;
	}
}
