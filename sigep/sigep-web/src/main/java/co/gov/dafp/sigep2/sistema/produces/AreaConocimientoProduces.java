package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.AreaConocimientoDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class AreaConocimientoProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<AreaConocimientoDTO> areaConocimiento;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<AreaConocimientoDTO> getAreaConocimiento() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		areaConocimiento = (List<AreaConocimientoDTO>) contexto.getSessionMap()
										.get("areaConocimiento");
		
		if (areaConocimiento == null) {
			areaConocimiento = AdministracionDelegate.findAreaConocimiento();
			contexto.getSessionMap().put("areaConocimiento", areaConocimiento);
		}
		return areaConocimiento;
	}
}