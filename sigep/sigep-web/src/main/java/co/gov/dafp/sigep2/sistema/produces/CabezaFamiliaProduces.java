package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.view.CabezaFamiliaDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class CabezaFamiliaProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<CabezaFamiliaDTO> cabezaFamilia;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<CabezaFamiliaDTO> getCabezaFamilia() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		cabezaFamilia = (List<CabezaFamiliaDTO>) contexto.getSessionMap().get("cabezaFamilia");
		
		if (cabezaFamilia == null) {
			cabezaFamilia = HojaDeVidaDelegate.obtenerParametricasCabezaFamilia();
			contexto.getSessionMap().put("cabezaFamilia", cabezaFamilia);
		}
		return cabezaFamilia;
	}
}
