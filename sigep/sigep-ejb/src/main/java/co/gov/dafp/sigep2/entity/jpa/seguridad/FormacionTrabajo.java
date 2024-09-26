package co.gov.dafp.sigep2.entity.jpa.seguridad;
// Generated 24/11/2017 03:31:14 PM by Hibernate Tools 4.3.1.Final

import java.util.Date;
//import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.seguridad.FormacionTrabajoDTO;



@Entity(name = "FormacionTrabajo")
@Table(name = "FORMACION_TRABAJO")
@SqlResultSetMappings({ @SqlResultSetMapping(name = FormacionTrabajo.FORMACION_TRABAJO_MAPPING, classes = {
		@ConstructorResult(targetClass = FormacionTrabajoDTO.class, columns = {
				@ColumnResult(name = "COD_FORMACION_TRABAJO", type = Long.class),
				@ColumnResult(name = "COD_PERSONA", type = Long.class),
				@ColumnResult(name = "COD_TIPO_FORMACION", type = Long.class),
				@ColumnResult(name = "INTENSIDAD_HORAS", type = Long.class),
				@ColumnResult(name = "NOMBRE_INSTITUCION", type = String.class),
				@ColumnResult(name = "COD_PAIS", type = Long.class),
				@ColumnResult(name = "COD_DEPARTAMENTO", type = Long.class),
				@ColumnResult(name = "COD_MUNICIPIO", type = Long.class),
				@ColumnResult(name = "FECHA_INICIO", type = Date.class),
				@ColumnResult(name = "FECHA_TERMINACION", type = Date.class) }) }) })

public class FormacionTrabajo extends EntidadBase implements java.io.Serializable {
	
	private static final long serialVersionUID = 1876566329593827343L;
	public static final String FORMACION_TRABAJO_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.FormacionTrabajo";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_FORMACION_TRABAJO", unique = true, nullable = false, precision = 22, scale = 0)
	private Long codFormacionTrabajo;
	
	@Column(name = "COD_PERSONA", nullable = false, length = 20)
	private Long codPersona;

	@Column(name = "COD_TIPO_FORMACION", nullable = false)
	private Long codTipoFormacion;
	
	@Column(name = "INTENSIDAD_HORAS", precision = 126, nullable = false)
	private Long intensidadHoras;
	
	@Column(name = "NOMBRE_INSTITUCION", precision = 126, nullable = false)
	private String nombreInstitucion;

	@Column(name = "COD_PAIS", length = 50)
	private Long codPais;

	@Column(name = "COD_DEPARTAMENTO", length = 20)
	private Long codDepartamento;

	@Column(name = "COD_MUNICIPIO", nullable = false)
	private Long codMunicipio;

	@Column(name = "FECHA_INICIO", nullable = false)
	private Date fechaInicio;
	
	@Column(name = "FECHA_FIN", nullable = false)
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	private Date audFechaActualizacion;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	private Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	private Long audAccion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	private Long audCodUsuario;

	public FormacionTrabajo() {
	}

	@Override
	public Long getId() {
		return this.codFormacionTrabajo;
	}
	
	@Override
	public void setId(Long id) {	
		this.codFormacionTrabajo = id;
	}

	public Long getCodFormacionTrabajo() {
		return codFormacionTrabajo;
	}

	public void setCodFormacionTrabajo(Long codFormacionTrabajo) {
		this.codFormacionTrabajo = codFormacionTrabajo;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public Long getCodTipoFormacion() {
		return codTipoFormacion;
	}

	public void setCodTipoFormacion(Long codTipoFormacion) {
		this.codTipoFormacion = codTipoFormacion;
	}

	public Long getIntensidadHoras() {
		return intensidadHoras;
	}

	public void setIntensidadHoras(Long intensidadHoras) {
		this.intensidadHoras = intensidadHoras;
	}
	
	public void setNombreInstitucion(String nombreInstitucion ){
		this.nombreInstitucion = nombreInstitucion;
	}
	
	public String getNombreInstitucion(){
		return nombreInstitucion;
	}

	public void setCodPais(long codPais) {
		this.codPais = codPais;
	}

	public Long getCodPais() {
		return codPais;
	}

	public Long getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(Long codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public void setCodMunicipio(Long codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public Long getCodMunicipio() {
		return codMunicipio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public Date getFechaTerminacion() {
		return fechaFin;
	}

	public void setFechaTerminacion(Date fechaFin) {
		this.fechaFin = fechaFin;
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
		return codFormacionTrabajo + ',' + codPersona + ',' + codTipoFormacion + ',' + intensidadHoras + ',' + nombreInstitucion + ',' + codPais + ',' + codDepartamento + ',' + codMunicipio +',' + fechaInicio + ',' + fechaFin;
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new FormacionTrabajoDTO(codFormacionTrabajo, codPersona, codTipoFormacion, intensidadHoras, nombreInstitucion, codPais, codDepartamento, codMunicipio, fechaInicio, fechaFin);
	}
}