package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ApplicationScoped
public class DepartamentoProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<DepartamentoDTO> departamento;

	@SuppressWarnings("unchecked")
	@Named
	@Produces
	public List<DepartamentoDTO> getDepartamento() throws SIGEP2SistemaException {
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		departamento = (List<DepartamentoDTO>) contexto.getSessionMap().get("departamento");
		
		if (departamento == null) {
			departamento = AdministracionDelegate.findDepartamento();
			contexto.getSessionMap().put("departamento", departamento);
		}
		return departamento;
	}
}
