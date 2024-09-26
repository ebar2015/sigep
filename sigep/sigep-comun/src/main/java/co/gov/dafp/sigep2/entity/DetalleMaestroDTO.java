package co.gov.dafp.sigep2.entity;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.MaestroDTO;

/**
 * Entity implementation class for Entity: DetalleMaestro
 *
 */

public class DetalleMaestroDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = 6246501768639447872L;

	protected long id;

	private MaestroDTO maestroId;

	private String registro;

	private String aliasRegistro;

	private String aliasRegistro2;

	private String aliasRegistro3;

	protected Boolean estado = Boolean.TRUE;

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public DetalleMaestroDTO() {
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

	public MaestroDTO getMaestroId() {
		return maestroId;
	}

	public void setMaestroId(MaestroDTO maestroId) {
		this.maestroId = maestroId;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getAliasRegistro() {
		return aliasRegistro;
	}

	public void setAliasRegistro(String aliasRegistro) {
		this.aliasRegistro = aliasRegistro;
	}

	public String getAliasRegistro2() {
		return aliasRegistro2;
	}

	public void setAliasRegistro2(String aliasRegistro2) {
		this.aliasRegistro2 = aliasRegistro2;
	}

	public String getAliasRegistro3() {
		return aliasRegistro3;
	}

	public void setAliasRegistro3(String aliasRegistro3) {
		this.aliasRegistro3 = aliasRegistro3;
	}

	@Override
	public String toString() {
		return "DetalleMaestro [id=" + id + ", maestroId=" + maestroId.getNombreTabla() + ", registro=" + registro
				+ ", aliasRegistro=" + aliasRegistro + ", aliasRegistro2=" + aliasRegistro2 + ", aliasRegistro3="
				+ aliasRegistro3 + ", estado=" + estado + "]";
	}

}
