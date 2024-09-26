package co.gov.dafp.sigep2.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.ParametricaDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.Parametrica;
import co.gov.dafp.sigep2.factoria.ParametricaFactoria;
import co.gov.dafp.sigep2.interfaces.IParametrica;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Stateless
public class ParametricaEJB implements IParametrica{
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ParametricaFactoria parametrica;
	
	@Override
	public List<ParametricaDTO> listarTablas() throws SIGEP2SistemaException {
		return this.parametrica.listarTablasDeParametrica();
	}

	@Override
	public void crearParametro(String descripcion, ParametricaDTO... detalleParametro) throws SIGEP2SistemaException {
		
		if(detalleParametro !=null){
				Parametrica[] parametros=new Parametrica[detalleParametro.length];
				int i=0;
				for (ParametricaDTO parametroItem : detalleParametro) {
					Parametrica p=new Parametrica();
					p.setId(parametroItem.getId());
					Parametrica padre=new Parametrica();
					padre.setId(parametroItem.getCodPadreParametrica());
					p.setCodPadreParametrica(padre);					
					p.setNombreParametro(parametroItem.getNombreParametro());
					p.setValorParametro(parametroItem.getValorParametro());
					p.setTipoParametro(parametroItem.getTipoParametro());
					p.setDescripcion(parametroItem.getDescripcion());
					p.setFlgEstado(parametroItem.isFlgEstado());
					p.setAudAccion(Long.valueOf(parametroItem.getAudAccion()));
					p.setAudCodRol(parametroItem.getAudCodRol());
					p.setAudCodUsuario(parametroItem.getAudCodUsuario());
					p.setAudFechaActualizacion(parametroItem.getAudFechaActualizacion());
					parametros[i]=p;
					
				}
				this.parametrica.crearParametro(descripcion, parametros);
		}
	}
}
