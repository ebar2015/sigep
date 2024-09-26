package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.TipoInstitucionDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class TipoInstitucionProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<TipoInstitucionDTO> tipoInstitucion;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<TipoInstitucionDTO> getTipoInstitucion() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		tipoInstitucion = (List<TipoInstitucionDTO>) contexto.getSessionMap().get("tipoInstitucion");
		
		if (tipoInstitucion == null) {
			tipoInstitucion = HojaDeVidaDelegate.findTipoInstitucion();
			contexto.getSessionMap().put("tipoInstitucion", tipoInstitucion);
		}
		return tipoInstitucion;
	}
}
