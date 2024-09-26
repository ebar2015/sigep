package co.gov.dafp.sigep2.entity;

import java.io.Serializable;
import java.util.Date;

public class ParametricaDTO implements Serializable {
	private static final long serialVersionUID = -8428525824861930226L;

	protected long id;
	private String nombreParametro;
	private String valorParametro;
	private Long codPadreParametrica;
	private boolean flgEstado;
	protected Date audFechaActualizacion;
	protected Long audCodUsuario;
	protected Long audCodRol;
	protected String audAccion;
	private String tipoParametro;
	private String descripcion;
	
	public static final int AUDITORIA_INSERT = 785;
	public static final int AUDITORIA_UPDATE = 63;
	public static final int AUDITORIA_DELETE = 62;

	public ParametricaDTO() {
		super();
	}

	public ParametricaDTO(long id, String nombreParametro, String valorParametro, Long codPadreParametrica,
			boolean flgEstado, Date audFechaActualizacion, Long audCodUsuario, Long audCodRol, String audAccion) {
		super();
		this.id = id;
		this.nombreParametro = nombreParametro;
		this.valorParametro = valorParametro;
		this.codPadreParametrica = codPadreParametrica;
		this.flgEstado = flgEstado;
		this.audFechaActualizacion = audFechaActualizacion;
		this.audCodUsuario = audCodUsuario;
		this.audCodRol = audCodRol;
		this.audAccion = audAccion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombreParametro() {
		return nombreParametro;
	}

	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}

	public String getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
	}

	public Long getCodPadreParametrica() {
		return codPadreParametrica;
	}

	public void setCodPadreParametrica(Long codPadreParametrica) {
		this.codPadreParametrica = codPadreParametrica;
	}

	public boolean isFlgEstado() {
		return flgEstado;
	}

	public void setFlgEstado(boolean flgEstado) {
		this.flgEstado = flgEstado;
	}

	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	public Long getAudCodUsuario() {
		return audCodUsuario;
	}

	public void setAudCodUsuario(Long audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	public Long getAudCodRol() {
		return audCodRol;
	}

	public void setAudCodRol(Long audCodRol) {
		this.audCodRol = audCodRol;
	}

	public String getAudAccion() {
		return audAccion;
	}

	public void setAudAccion(String audAccion) {
		this.audAccion = audAccion;
	}

	@Override
	public String toString() {
		return "ParametricaDTO [id=" + id + ", nombreParametro=" + nombreParametro + ", valorParametro="
				+ valorParametro + ", codPadreParametrica=" + codPadreParametrica + ", flgEstado=" + flgEstado
				+ ", audFechaActualizacion=" + audFechaActualizacion + ", audCodUsuario=" + audCodUsuario
				+ ", audCodRol=" + audCodRol + ", audAccion=" + audAccion + ", tipoParametro=" + tipoParametro + "]";
	}

	public String getTipoParametro() {
		return tipoParametro;
	}

	public void setTipoParametro(String tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
