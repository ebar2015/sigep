package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.MotivoRetiroDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class MotivoRetiroProduces implements Serializable {
	private static final long serialVersionUID = 906665382204949085L;
	
	private List<MotivoRetiroDTO> motivoRetiro;
	
	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<MotivoRetiroDTO> getMotivoRetiro() throws SIGEP2SistemaException {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		motivoRetiro = (List<MotivoRetiroDTO>) contexto.getSessionMap().get("motivoRetiro");
		if(motivoRetiro == null) {
			motivoRetiro = HojaDeVidaDelegate.buscarMotivoRetiro();
			contexto.getSessionMap().put("motivoRetiro", motivoRetiro);
		}
		return motivoRetiro;
	}
	
	
}
