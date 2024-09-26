package co.gov.dafp.sigep2.entity.jpa.seguridad;
// Generated 24/11/2017 03:31:14 PM by Hibernate Tools 4.3.1.Final

import java.util.Date;
//import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.gov.dafp.sigep2.converter.BooleanToNumberConverter;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.seguridad.ReconocimientoDTO;

@Entity(name = "Reconocimiento")
@Table(name = "RECONOCIMIENTO")
@SqlResultSetMappings({ @SqlResultSetMapping(name = Reconocimiento.RECONOCIMIENTO_MAPPING, classes = {
		@ConstructorResult(targetClass = ReconocimientoDTO.class, columns = {
				@ColumnResult(name = "COD_RECONOCIMIENTO", type = Long.class),
				@ColumnResult(name = "COD_PERSONA", type = Long.class),
				@ColumnResult(name = "FLG_ES_PREMIO", type = Boolean.class),
				@ColumnResult(name = "FLS_ES_RECONOCIMIENTO", type = Boolean.class),
				@ColumnResult(name = "NOMBRE_ENTIDAD", type = String.class),
				@ColumnResult(name = "FECHA_RECONOCIMIENTO", type = Date.class),
				@ColumnResult(name = "COD_PAIS", type = Long.class),
				@ColumnResult(name = "COD_DEPARTAMENTO", type = Long.class),
				@ColumnResult(name = "COD_MUNICIPIO", type = Long.class) }) }) })

public class Reconocimiento extends EntidadBase implements java.io.Serializable {
	
	private static final long serialVersionUID = 1876566329593827343L;
	public static final String RECONOCIMIENTO_MAPPING= "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.Reconocimiento";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_RECONOCIMIENTO", unique = true, nullable = false, precision = 22, scale = 0)
	private Long codReconocimiento;
	
	@Column(name = "COD_PERSONA", nullable = false, length = 20)
	private Long codPersona;
	
	@Column(name = "FLG_ES_PREMIO", nullable = true)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgEsPremio;
	
	@Column(name = "FLG_ES_RECONOCIMIENTO", nullable = true)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgEsReconocimiento;
	
	@Column(name = "NOMBRE_ENTIDAD", precision = 126, nullable = true)
	private String nombreEntidad;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_RECONOCIMIENTO", precision = 126, nullable = true)
	private Date fechaReconocimiento;

	@Column(name = "COD_PAIS", length = 50, nullable = true)
	private Long codPais;

	@Column(name = "COD_DEPARTAMENTO", length = 20, nullable = true)
	private Long codDepartamento;
	
	@Column(name = "COD_MUNICIPIO", nullable = true)
	private Long codMunicipio;

	@Temporal(TemporalType.DATE)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	private Date audFechaActualizacion;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	private Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	private Long audAccion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	private Long audCodUsuario;

	public Reconocimiento() {
	}

	@Override
	public Long getId() {
		return this.codReconocimiento;
	}

	@Override
	public void setId(Long codReconocimiento) {	
		this.codReconocimiento = codReconocimiento;
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

	public Long getAudCodRol() {
		return audCodRol;
	}

	public void setAudCodRol(Long audCodRol) {
		this.audCodRol = audCodRol;
	}

	public Long getAudAccion() {
		return audAccion;
	}

	public void setAudAccion(Long audAccion) {
		this.audAccion = audAccion;
	}

	public Long getAudCodUsuario() {
		return audCodUsuario;
	}

	public void setAudCodUsuario(Long audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}
	
	@Override
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	@Override
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}
	
	@Override
	public String toString() {
		return String.valueOf(codReconocimiento) + ',' + codPersona + ',' + flgEsPremio + ',' + flgEsReconocimiento + ',' + nombreEntidad + ',' + fechaReconocimiento + ',' + codPais + ',' + codDepartamento +',' + codMunicipio;
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new ReconocimientoDTO(codReconocimiento, codPersona, flgEsPremio, flgEsPremio, nombreEntidad, fechaReconocimiento, codPais, codDepartamento, codMunicipio);
	}
}