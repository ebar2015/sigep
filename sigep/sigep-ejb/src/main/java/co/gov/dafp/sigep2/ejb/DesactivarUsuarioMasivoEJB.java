package co.gov.dafp.sigep2.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.factoria.UsuarioFactoria;
import co.gov.dafp.sigep2.interfaces.IDesactivarUsuarioMasivo;

@Stateless
public class DesactivarUsuarioMasivoEJB implements IDesactivarUsuarioMasivo {
	private static final long serialVersionUID = 1352471373248439462L;	
	 
	 @EJB
	 private UsuarioFactoria usuarioFactoria;	 
	 
	 @Override
	 public void desactivarUsuarios(Long entidad) throws Exception {
		this.usuarioFactoria.desactivarUsuariosProcedure(entidad);
	 }
}