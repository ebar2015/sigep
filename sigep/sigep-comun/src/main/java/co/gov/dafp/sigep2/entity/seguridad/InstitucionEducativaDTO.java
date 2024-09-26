package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class InstitucionEducativaDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = -1636969809789308631L;

	private long id;
	private Long codInstitucionMen;
	private Long codTipoInstitucion;
	private String nombreInstitucion;

	public InstitucionEducativaDTO() {
		super();
	}

	public InstitucionEducativaDTO(long id, Long codInstitucionMen, Long codTipoInstitucion, String nombreInstitucion) {
		super();
		this.id = id;
		this.codInstitucionMen = codInstitucionMen;
		this.codTipoInstitucion = codTipoInstitucion;
		this.nombreInstitucion = nombreInstitucion;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public Long getCodInstitucionMen() {
		return codInstitucionMen;
	}

	public void setCodInstitucionMen(Long codInstitucionMen) {
		this.codInstitucionMen = codInstitucionMen;
	}

	public Long getCodTipoInstitucion() {
		return codTipoInstitucion;
	}

	public void setCodTipoInstitucion(Long codTipoInstitucion) {
		this.codTipoInstitucion = codTipoInstitucion;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	@Override
	public String toString() {
		return "InstitucionEducativaDTO [id=" + id + ", codInstitucionMen=" + codInstitucionMen
				+ ", codTipoInstitucion=" + codTipoInstitucion + ", nombreInstitucion=" + nombreInstitucion + "]";
	}
}