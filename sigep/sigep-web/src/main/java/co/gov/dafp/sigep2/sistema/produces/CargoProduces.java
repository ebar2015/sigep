package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.CargoDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class CargoProduces  implements Serializable{
	private static final long serialVersionUID = 5510625328940179199L;
	
	private List<CargoDTO> cargoEntidad;
	
	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<CargoDTO> getCargoEntidad() throws SIGEP2SistemaException{
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		cargoEntidad = (List<CargoDTO>)contexto.getSessionMap().get("cargoEntidad");
		
		if(cargoEntidad == null) {
			cargoEntidad = HojaDeVidaDelegate.buscarCargoPorEntidad();
			contexto.getSessionMap().put("cargoEntidad", cargoEntidad);
		}
		return cargoEntidad;
	}
}
