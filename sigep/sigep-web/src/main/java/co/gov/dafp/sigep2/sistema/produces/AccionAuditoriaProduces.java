package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.view.AccionAuditoriaDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class AccionAuditoriaProduces implements Serializable {
	
	private static final long serialVersionUID = 3525225250113176596L;
	private List<AccionAuditoriaDTO> accionAuditoriaList;
	
	@Named
	@Produces
	public List<AccionAuditoriaDTO> getAccionAuditoriaList() throws SIGEP2SistemaException {
		if (accionAuditoriaList == null) {
			accionAuditoriaList = AdministracionDelegate.findAccionAuditoria();
		}
		return accionAuditoriaList;
	}
}
