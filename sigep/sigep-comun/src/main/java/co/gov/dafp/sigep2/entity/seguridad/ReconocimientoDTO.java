package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;


public class ReconocimientoDTO extends EntidadBaseDTO implements Serializable{
	private static final long serialVersionUID = -5791364205407419573L;

	private long id;
	private long codPersona;
	private boolean flgEsPremio;
	private boolean flgEsReconocimiento;
	private String nombreEntidad;
	private Date fechaReconocimiento;
	private long codPais;
	private long codDepartamento;
	private long codMunicipio;
	
	public ReconocimientoDTO(){
		super();
	}
	
	public ReconocimientoDTO(long id, long codPersona, boolean flgEsPremio, boolean flgEsReconocimiento, String nombreEntidad, Date fechaReconocimiento, long codPais, long codDepartamento, long codMunicipio) {
		super();
		this.id = id;
		this.codPersona = codPersona;
		this.flgEsPremio = flgEsPremio;
		this.flgEsReconocimiento = flgEsReconocimiento;
		this.nombreEntidad = nombreEntidad;
		this.fechaReconocimiento = fechaReconocimiento ;
		this.codPais = codPais;
		this.codDepartamento = codDepartamento;
		this.codMunicipio = codMunicipio;
	}

	public long getId() {
		return this.id;
	}

	
	public void setId(long id) {	
		this.id = id;
	}
	
	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}
	
	public Boolean getFlgEsPremio() {
		return flgEsPremio;
	}

	public void setflgEsPremio(Boolean flgEsPremio) {
		this.flgEsPremio = flgEsPremio;
	}

	public Boolean getFlgEsReconocimiento() {
		return flgEsReconocimiento;
	}

	public void setFlgEsReconocimiento(Boolean flgEsReconocimiento) {
		this.flgEsReconocimiento = flgEsReconocimiento;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	
	public Date getFechaReconocimiento(){
		return fechaReconocimiento;
	}

	public void setFechaReconocimiento(Date fechaReconocimiento) {
		this.fechaReconocimiento = fechaReconocimiento;
	}

	public Long getCodPais() {
		return codPais;
	}

	public void setCodPais(Long codPais) {
		this.codPais = codPais;
	}

	public Long getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(Long codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public Long getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(Long codMunicipio) {
		this.codMunicipio = codMunicipio;
	}


	@Override
	public String toString() {
		return String.valueOf(id) + ',' + codPersona + ',' + flgEsPremio + ',' + flgEsReconocimiento + ',' + nombreEntidad + ',' + fechaReconocimiento + ',' + codPais + ',' + codDepartamento +',' + codMunicipio;
	}
}