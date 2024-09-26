package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.OrientacionSexualDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class OrientacionSexualProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<OrientacionSexualDTO> orientacionSexual;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<OrientacionSexualDTO> getOrientacionSexual() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		orientacionSexual = (List<OrientacionSexualDTO>) contexto.getSessionMap().get("orientacionSexual");
		
		if (orientacionSexual == null) {
			orientacionSexual = HojaDeVidaDelegate.obtenerParametricasOrientacionSexual();
			contexto.getSessionMap().put("orientacionSexual", orientacionSexual);
		}
		return orientacionSexual;
	}
}
