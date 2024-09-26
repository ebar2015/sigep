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
import co.gov.dafp.sigep2.entity.seguridad.EvaluacionDesempenoDTO;



@Entity(name = "EvaluacionDesempeno")
@Table(name = "EVALUACION_DESEMPENO")
@SqlResultSetMappings({ @SqlResultSetMapping(name = EvaluacionDesempeno.EVALUACION_DESEMPENO_MAPPING, classes = {
		@ConstructorResult(targetClass = EvaluacionDesempenoDTO.class, columns = {
				@ColumnResult(name = "COD_EVALUACION_DESEMPENO", type = Long.class),
				@ColumnResult(name = "COD_PERSONA", type = Long.class),
				@ColumnResult(name = "COD_ENTIDAD", type = Long.class),
				@ColumnResult(name = "CARGO_ENTIDAD_PUBLICA", type = String.class),
				@ColumnResult(name = "CARGO_ENTIDAD_PRIVADA", type = String.class),
				@ColumnResult(name = "CALIFICACION_OBTENIDA", type = Long.class),
				@ColumnResult(name = "ESCALA_EVALUACION", type = Long.class),
				@ColumnResult(name = "FECHA_INICIO", type = Date.class),
				@ColumnResult(name = "FECHA_FIN", type = Date.class) }) }) })

public class EvaluacionDesempeno extends EntidadBase implements java.io.Serializable {
	
	private static final long serialVersionUID = 1876566329593827343L;
	public static final String EVALUACION_DESEMPENO_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.EvaluacionDesempeno";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_EVALUACION_DESEMPENO", unique = true, nullable = false, precision = 22, scale = 0)
	private Long codEvaluacionDesempeno;
	
	@Column(name = "COD_PERSONA", nullable = false, length = 20)
	private Long codPersona;

	@Column(name = "COD_ENTIDAD", nullable = false)
	private Long codEntidad;
	
	@Column(name = "CARGO_ENTIDAD_PUBLICA", precision = 126, nullable = false)
	private String cargoEntidadPublica;
	
	@Column(name = "CARGO_ENTIDAD_PRIVADA", precision = 126, nullable = false)
	private String cargoEntidadPrivada;

	@Column(name = "CALIFICACION_OBTENIDA", length = 50)
	private Long calificacionObtenida;

	@Column(name = "ESCALA_EVALUACION", length = 20)
	private Long escalaEvaluacion;

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

	public EvaluacionDesempeno() {
	}

	@Override
	public Long getId() {
		return this.codEvaluacionDesempeno;
	}

	@Override
	public void setId(Long id) {	
		this.codEvaluacionDesempeno = id;
	}

	public Long getCodEvaluacionDesempeno() {
		return codEvaluacionDesempeno;
	}

	public void setCodEvaluacionDesempeno(Long codEvaluacionDesempeno) {
		this.codEvaluacionDesempeno = codEvaluacionDesempeno;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public Long getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}

	public String getCargoEntidadPublica() {
		return cargoEntidadPublica;
	}

	public void setCargoEntidadPublica(String cargoEntidadPublica) {
		this.cargoEntidadPublica = cargoEntidadPublica;
	}
	
	public String getCargoEntidadPrivada(){
		return cargoEntidadPrivada;
	}

	public void setCargoEntidadPrivada(String cargoEntidadPrivada) {
		this.cargoEntidadPrivada = cargoEntidadPrivada;
	}

	public Long getCalificacionObtenida() {
		return calificacionObtenida;
	}

	public void setCalificacionObtenida(Long calificacionObtenida) {
		this.calificacionObtenida = calificacionObtenida;
	}

	public Long getEscalaEvaluacion() {
		return escalaEvaluacion;
	}

	public void setEscalaEvaluacion(Long escalaEvaluacion) {
		this.escalaEvaluacion = escalaEvaluacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
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
		return codEvaluacionDesempeno + ',' + codPersona + ',' + codEntidad + ',' + cargoEntidadPublica + ',' + cargoEntidadPrivada + ',' + calificacionObtenida + ',' + escalaEvaluacion + ',' + fechaInicio +',' + fechaFin;
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new EvaluacionDesempenoDTO(codEvaluacionDesempeno, codPersona, codEntidad, cargoEntidadPublica, cargoEntidadPrivada, calificacionObtenida, escalaEvaluacion, fechaInicio, fechaFin);
	}
}