package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.TipoEntidadDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class TipoEntidadProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<TipoEntidadDTO> tipoEntidad;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<TipoEntidadDTO> getTipoEntidad() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		tipoEntidad = (List<TipoEntidadDTO>) contexto.getSessionMap().get("tipoEntidad");
		
		if (tipoEntidad == null) {
			tipoEntidad = AdministracionDelegate.findTipoEntidad();
			contexto.getSessionMap().put("tipoEntidad", tipoEntidad);
		}
		return tipoEntidad;
	}
}
