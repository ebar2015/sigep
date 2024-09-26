package co.gov.dafp.sigep2.entity.jpa.seguridad;

import java.util.Date;

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
import co.gov.dafp.sigep2.entity.seguridad.PreguntaOpinionDTO;


@Entity(name = "PreguntaOpinion")
@Table(name = "PREGUNTA_OPINION")
@SqlResultSetMappings({ @SqlResultSetMapping(name = PreguntaOpinion.PREGUNTA_OPINION_MAPPING, classes = {
		@ConstructorResult(targetClass = PreguntaOpinionDTO.class, columns = {
				@ColumnResult(name = "COD_PREGUNTA_OPINION", type = Long.class),
				@ColumnResult(name = "PREGUNTA", type = String.class),
				@ColumnResult(name = "FECHA_INICIO", type = Date.class),
				@ColumnResult(name = "FECHA_FIN", type = Date.class) }) }) })
public class PreguntaOpinion  extends EntidadBase implements java.io.Serializable {
	private static final long serialVersionUID = 2590275739178570235L;
	
	public static final String PREGUNTA_OPINION_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.PreguntaOpinion";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_PREGUNTA_OPINION", insertable = false, updatable = false, unique = true, nullable = false)
	protected Long codPreguntaOpinion;
	
	@Column(name = "PREGUNTA", length = 126)
	protected String pregunta;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INICIO", length = 7)
	protected Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_FIN", length = 7)
	protected Date fechaFin;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;
	
	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;
	
	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;
	
	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;
	
	public PreguntaOpinion() {
	}
	
	public Long getId() {
		return this.codPreguntaOpinion;
	}

	public void setId(Long id) {
		this.codPreguntaOpinion = id;
	}
	
	public Long getCodPreguntaOpinion() {
		return codPreguntaOpinion;
	}

	public void setCodPreguntaOpinion(Long codPreguntaOpinion) {
		this.codPreguntaOpinion = codPreguntaOpinion;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
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

	public Long getAudAccion() {
		return audAccion;
	}

	public void setAudAccion(Long audAccion) {
		this.audAccion = audAccion;
	}
	
	@Override
	public String toString() {
		return "PreguntaOpinio [id=" + this.getId() + "]";
	}
	
	@Override
	public EntidadBaseDTO getDTO() {
		return new PreguntaOpinionDTO(codPreguntaOpinion, pregunta, fechaInicio, fechaFin);
	}
}
