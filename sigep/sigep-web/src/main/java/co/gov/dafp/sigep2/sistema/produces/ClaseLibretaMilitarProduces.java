package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.ClaseLibretaMilitarDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class ClaseLibretaMilitarProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<ClaseLibretaMilitarDTO> claseLibretaMilitar;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<ClaseLibretaMilitarDTO> getClaseLibretaMilitar() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		claseLibretaMilitar = (List<ClaseLibretaMilitarDTO>) contexto.getSessionMap().get("claseLibretaMilitar");
		
		if (claseLibretaMilitar == null) {
			claseLibretaMilitar = AdministracionDelegate.findClaseLibretaMilitar();
			contexto.getSessionMap().put("claseLibretaMilitar", claseLibretaMilitar);
		}
		return claseLibretaMilitar;
	}
}
