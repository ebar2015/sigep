package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.PepPreguntasCuestionarioDetalle;

public class PepPreguntasCuestionarioDetalleExt extends PepPreguntasCuestionarioDetalle implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient String dsDetalleRespuesta;
	private transient String dsDetalleRespuestaDetalle;
	
	public String getDsDetalleRespuesta() {
		return dsDetalleRespuesta;
	}
	public void setDsDetalleRespuesta(String dsDetalleRespuesta) {
		this.dsDetalleRespuesta = dsDetalleRespuesta;
	}
	public String getDsDetalleRespuestaDetalle() {
		return dsDetalleRespuestaDetalle;
	}
	public void setDsDetalleRespuestaDetalle(String dsDetalleRespuestaDetalle) {
		this.dsDetalleRespuestaDetalle = dsDetalleRespuestaDetalle;
	}
	

	

}
