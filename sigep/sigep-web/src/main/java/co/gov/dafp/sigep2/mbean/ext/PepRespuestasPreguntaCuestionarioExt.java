package co.gov.dafp.sigep2.mbean.ext;

import java.util.List;

import co.gov.dafp.sigep2.entities.PepRespuestaCuestionarioDetalle;
import co.gov.dafp.sigep2.entities.PepRespuestasPreguntaCuestionario;

public class PepRespuestasPreguntaCuestionarioExt extends PepRespuestasPreguntaCuestionario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<PepRespuestaCuestionarioDetalle> lstrespuestaDetalle;

	public List<PepRespuestaCuestionarioDetalle> getLstrespuestaDetalle() {
		return lstrespuestaDetalle;
	}

	public void setLstrespuestaDetalle(List<PepRespuestaCuestionarioDetalle> lstrespuestaDetalle) {
		this.lstrespuestaDetalle = lstrespuestaDetalle;
	}
	
	

}
