package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Remote
public interface ICarguesMasivosRemote extends IServiceRemote{

	public void emailResultadoCargueMasivo(List<String> email, List<String> ccEmail, String resultado, String proceso) throws SIGEP2SistemaException;
}
