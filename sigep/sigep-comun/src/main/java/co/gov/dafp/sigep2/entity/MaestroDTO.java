package co.gov.dafp.sigep2.entity;

import java.io.Serializable;
import java.util.List;

import co.gov.dafp.sigep2.entity.DetalleMaestroDTO;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

/**
 * Entity implementation class for Entity: Maestro
 *
 */
public class MaestroDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = 1138408177909665934L;

	public MaestroDTO(long id, String nombreTabla, Boolean estado, List<DetalleMaestroDTO> listaDetalleMaestro) {
		super();
		this.id = id;
		this.nombreTabla = nombreTabla;
		this.estado = estado;
		this.listaDetalleMaestro = listaDetalleMaestro;
	}

	protected long id;

	private String nombreTabla;

	protected Boolean estado = Boolean.TRUE;

	private List<DetalleMaestroDTO> listaDetalleMaestro;

	public MaestroDTO() {
		super();
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	public List<DetalleMaestroDTO> getListaDetalleMaestro() {
		return listaDetalleMaestro;
	}

	public void setListaDetalleMaestro(List<DetalleMaestroDTO> listaDetalleMaestro) {
		this.listaDetalleMaestro = listaDetalleMaestro;
	}

	@Override
	public String toString() {
		return "MaestroDTO [id=" + id + ", nombreTabla=" + nombreTabla + ", estado=" + estado + ", listaDetalleMaestro="
				+ listaDetalleMaestro + ", audFechaActualizacion=" + audFechaActualizacion + ", audCodUsuario="
				+ audCodUsuario + ", audCodRol=" + audCodRol + ", audAccion=" + audAccion + "]";
	}

}
