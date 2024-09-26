package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class TipoDocumentoEntidadProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<TipoDocumentoDTO> tiposDocumentoEntidad;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<TipoDocumentoDTO> getTiposDocumentoEntidad() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		tiposDocumentoEntidad = (List<TipoDocumentoDTO>) contexto.getSessionMap().get("tiposDocumentoEntidad");
		
		if (tiposDocumentoEntidad == null) {
			tiposDocumentoEntidad = AdministracionDelegate.findTipoDocumento();
			contexto.getSessionMap().put("tiposDocumentoEntidad", tiposDocumentoEntidad);
		}
		return tiposDocumentoEntidad;
	}
}