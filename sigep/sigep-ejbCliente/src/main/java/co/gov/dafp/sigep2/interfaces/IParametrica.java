package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.ParametricaDTO;

import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Remote
public interface IParametrica extends IServiceRemote{
	public List<ParametricaDTO> listarTablas() throws SIGEP2SistemaException;
	
	public void crearParametro(String descripcion, ParametricaDTO... detalleParametro) throws SIGEP2SistemaException;
}
