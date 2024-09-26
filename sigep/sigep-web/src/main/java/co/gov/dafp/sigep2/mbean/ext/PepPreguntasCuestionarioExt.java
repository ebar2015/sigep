package co.gov.dafp.sigep2.mbean.ext;

import java.util.List;

import co.gov.dafp.sigep2.entities.PepPreguntasCuestionario;
import co.gov.dafp.sigep2.entities.PepRespuestaCuestionarioDetalle;

public class PepPreguntasCuestionarioExt extends PepPreguntasCuestionario {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient String dsRespuesta;
	private transient String dsNombreArchivo;
	
	transient List<PepRespuestaCuestionarioDetalle> lstRespuestaDetalle;
	
	public String getDsRespuesta() {
		return dsRespuesta;
	}

	public List<PepRespuestaCuestionarioDetalle> getLstRespuestaDetalle() {
		return lstRespuestaDetalle;
	}

	public void setLstRespuestaDetalle(List<PepRespuestaCuestionarioDetalle> lstRespuestaDetalle) {
		this.lstRespuestaDetalle = lstRespuestaDetalle;
	}

	public void setDsRespuesta(String dsRespuesta) {
		this.dsRespuesta = dsRespuesta;
	}

	public String getDsNombreArchivo() {
		return dsNombreArchivo;
	}

	public void setDsNombreArchivo(String dsNombreArchivo) {
		this.dsNombreArchivo = dsNombreArchivo;
	}

	
}
