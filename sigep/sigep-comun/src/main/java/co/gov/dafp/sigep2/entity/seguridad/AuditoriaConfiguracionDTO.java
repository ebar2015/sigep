package co.gov.dafp.sigep2.entity.seguridad;

import java.util.Date;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;


public class AuditoriaConfiguracionDTO extends EntidadBaseDTO implements java.io.Serializable {

	private static final long serialVersionUID = 2302481719365755533L;


	private long codTablaAuditoria;
	
	
	private String nombreTabla;
	
	private String descripcionTabla;

	
	private Boolean flgInsert = true;

	
	private Boolean flgDelete = true;

	
	private Boolean flgUpdate = true;

	
	private Boolean flgSelect = true;

	
	private Date fechaInicioAuditoria;

	
	private Date fechaFinAuditoria;
	
	public AuditoriaConfiguracionDTO() {
		super();
	}

	public AuditoriaConfiguracionDTO(long codTablaAuditoria, String nombreTabla, String descripcionTabla, Boolean flgInsert, Boolean flgDelete,
			Boolean flgUpdate, Boolean flgSelect, Date fechaInicioAuditoria, Date fechaFinAuditoria) {
		super();
		this.codTablaAuditoria = codTablaAuditoria;
		this.nombreTabla = nombreTabla;
		this.descripcionTabla=descripcionTabla;
		this.flgInsert = flgInsert;
		this.flgDelete = flgDelete;
		this.flgUpdate = flgUpdate;
		this.flgSelect = flgSelect;
		this.fechaInicioAuditoria = fechaInicioAuditoria;
		this.fechaFinAuditoria = fechaFinAuditoria;
	}

	public long getId() {
		return this.codTablaAuditoria;
	}

	public void setId(long id) {
		this.codTablaAuditoria = id;
	}
	
	public long getCodTablaAuditoria() {
		return codTablaAuditoria;
	}

	public void setCodTablaAuditoria(long codTablaAuditoria) {
		this.codTablaAuditoria = codTablaAuditoria;
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}
	
	public String getDescripcionTabla() {
		return descripcionTabla;
	}
	
	public void setDescripcionTabla(String descripcionTabla) {
		this.descripcionTabla = descripcionTabla;
	}

	public Boolean getFlgInsert() {
		return flgInsert;
	}

	public void setFlgInsert(Boolean flgInsert) {
		this.flgInsert = flgInsert;
	}

	public Boolean getFlgDelete() {
		return flgDelete;
	}

	public void setFlgDelete(Boolean flgDelete) {
		this.flgDelete = flgDelete;
	}

	public Boolean getFlgUpdate() {
		return flgUpdate;
	}

	public void setFlgUpdate(Boolean flgUpdate) {
		this.flgUpdate = flgUpdate;
	}

	public Boolean getFlgSelect() {
		return flgSelect;
	}

	public void setFlgSelect(Boolean flgSelect) {
		this.flgSelect = flgSelect;
	}

	public Date getFechaInicioAuditoria() {
		return fechaInicioAuditoria;
	}

	public void setFechaInicioAuditoria(Date fechaInicioAuditoria) {
		this.fechaInicioAuditoria = fechaInicioAuditoria;
	}

	public Date getFechaFinAuditoria() {
		return fechaFinAuditoria;
	}

	public void setFechaFinAuditoria(Date fechaFinAuditoria) {
		this.fechaFinAuditoria = fechaFinAuditoria;
	}


	@Override
	public String toString() {
		return "AuditoriaConfiguracionDTO [codTablaAuditoria=" + codTablaAuditoria + ", nombreTabla=" + nombreTabla
				+ ", flgInsert=" + flgInsert + ", flgDelete=" + flgDelete + ", flgUpdate=" + flgUpdate + ", flgSelect="
				+ flgSelect + ", fechaInicioAuditoria=" + fechaInicioAuditoria + ", fechaFinAuditoria="
				+ fechaFinAuditoria + "]";
	}

	
	
	
	
	
	
}

