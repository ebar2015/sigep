package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.DesplazamientoDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class DesplazamientoProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<DesplazamientoDTO> desplazamiento;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<DesplazamientoDTO> getDesplazamiento() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		desplazamiento = (List<DesplazamientoDTO>) contexto.getSessionMap().get("desplazamiento");
		
		if (desplazamiento == null) {
			desplazamiento = HojaDeVidaDelegate.obtenerParametricasDesplazamiento();
			contexto.getSessionMap().put("desplazamiento", desplazamiento);
		}
		return desplazamiento;
	}
}
