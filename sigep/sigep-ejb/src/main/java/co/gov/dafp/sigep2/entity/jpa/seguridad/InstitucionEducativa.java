package co.gov.dafp.sigep2.entity.jpa.seguridad;
// Generated 24/11/2017 03:31:14 PM by Hibernate Tools 4.3.1.Final

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
import co.gov.dafp.sigep2.entity.seguridad.InstitucionEducativaDTO;

@Entity(name = "InstitucionEducativa")
@Table(name = "INSTITUCION_EDUCATIVA")
@SqlResultSetMappings({ @SqlResultSetMapping(name = InstitucionEducativa.INSTITUCION_EDUCATIVA_MAPPING, classes = {
		@ConstructorResult(targetClass = InstitucionEducativaDTO.class, columns = {
				@ColumnResult(name = "COD_INSTITUCION_EDUCATIVA", type = Long.class),
				@ColumnResult(name = "COD_INSTITUCION_MEN", type = Long.class),
				@ColumnResult(name = "COD_TIPO_INSTITUCION", type = Long.class),
				@ColumnResult(name = "NOMBRE_INSTITUCION", type = String.class) }) }) })

public class InstitucionEducativa extends EntidadBase implements java.io.Serializable {
	
	private static final long serialVersionUID = -2650146312441554922L;
	public static final String INSTITUCION_EDUCATIVA_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.InstitucionEducativa";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_INSTITUCION_EDUCATIVA", unique = true, nullable = false, precision = 15, scale = 0)
	private Long codInstitucion;

	@Column(name = "COD_INSTITUCION_MEN", nullable = false, precision = 15, scale = 0)
	private Long codInstitucionMen;
	
	@Column(name = "COD_TIPO_INSTITUCION", nullable = false, precision = 6, scale = 0)
	private Long codTipoInstitucion;
	
	@Column(name = "NOMBRE_INSTITUCION", length = 255)
	private String nombreInstitucion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 20, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 6, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;
	
	public InstitucionEducativa() {
	}

	public Long getCodInstitucion() {
		return codInstitucion;
	}

	public void setCodInstitucion(Long codInstitucion) {
		this.codInstitucion = codInstitucion;
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
	public Long getId() {
		return this.codInstitucion;
	}

	@Override
	public void setId(Long id) {
		this.codInstitucion = id;
	}

	@Override
	public String toString() {
		return "InstitucionEducativa [codInstitucion=" + codInstitucion + ", codInstitucionMen=" + codInstitucionMen
				+ ", codTipoInstitucion=" + codTipoInstitucion + ", nombreInstitucion=" + nombreInstitucion
				+ ", audFechaActualizacion=" + audFechaActualizacion + ", audCodUsuario=" + audCodUsuario
				+ ", audCodRol=" + audCodRol + ", audAccion=" + audAccion + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new InstitucionEducativaDTO();
	}
}
