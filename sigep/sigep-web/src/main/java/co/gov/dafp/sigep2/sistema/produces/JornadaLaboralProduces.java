package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.JornadaLaboralDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class JornadaLaboralProduces implements Serializable {
	private static final long serialVersionUID = 6361226504866965619L;

	private List<JornadaLaboralDTO> jornadaLaboral;
	
	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<JornadaLaboralDTO> getJornadaLaboral() throws SIGEP2SistemaException {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		jornadaLaboral = (List<JornadaLaboralDTO>)contexto.getSessionMap().get("jornadaLaboral");
		if(jornadaLaboral == null) {
			jornadaLaboral = HojaDeVidaDelegate.buscarJornadaLaboral();
			contexto.getSessionMap().put("jornadaLaboral", jornadaLaboral);
		}
		return jornadaLaboral;
	}
}
