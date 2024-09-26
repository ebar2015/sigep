package co.gov.dafp.sigep2.interfaces;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Remote
public interface IDesactivarUsuarioMasivo extends IServiceRemote {
	public void desactivarUsuarios(Long entidad) 
			throws SIGEP2SistemaException, Exception;
}
