package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.ParametricaDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@SessionScoped
public class ParametricaProduces implements Serializable {
	private static final long serialVersionUID = -1544532181540612424L;
	
	private List<ParametricaDTO> tablasParametrica;
	public void setCodPadre(ParametricaDTO codPadre){
	}
	
	@Named
	@Produces
	public List<ParametricaDTO> getTablasParametrica() throws SIGEP2SistemaException {
		if(this.tablasParametrica == null) {
			tablasParametrica = AdministracionDelegate.listarTablasParametrica();
		}
		return tablasParametrica;
	}
	
	
	
}
