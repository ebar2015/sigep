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
import co.gov.dafp.sigep2.entity.seguridad.ParticipacionInstitucionDTO;



@Entity(name = "ParticipacionInstitucion")
@Table(name = "PARTICIPACION_INSTITUCION")
@SqlResultSetMappings({ @SqlResultSetMapping(name = ParticipacionInstitucion.PARTICIPACION_INSTITUCION_MAPPING, classes = {
		@ConstructorResult(targetClass = EvaluacionDesempenoDTO.class, columns = {
				@ColumnResult(name = "COD_PARTICIPACION_INSTITUCION", type = Long.class),
				@ColumnResult(name = "COD_PERSONA", type = Long.class),
				@ColumnResult(name = "NOMBRE_INSTITUCION", type = String.class),
				@ColumnResult(name = "NOMBRE_RAZON_SOCIAL_INSTITUCION", type = String.class),
				@ColumnResult(name = "NOMBRE_ENTIDAD_ORGANIZACION", type = String.class) }) }) })

public class ParticipacionInstitucion extends EntidadBase implements java.io.Serializable {
	
	private static final long serialVersionUID = 1876566329593827343L;
	public static final String PARTICIPACION_INSTITUCION_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.ParticipacionInstitucion";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_PARTICIPACION_INSTITUCION", unique = true, nullable = false, precision = 22, scale = 0)
	private Long codParticipacionInstitucion;
	
	@Column(name = "COD_PERSONA", nullable = false, length = 21)
	private Long codPersona;

	@Column(name = "NOMBRE_INSTITUCION", nullable = true)
	private String nombreInstitucion;
	
	@Column(name = "NOMBRE_RAZON_SOCIAL_INSTITUCION", nullable = true)
	private String nombreRazonSocialInstitucion;
	
	@Column(name = "NOMBRE_ENTIDAD_ORGANIZACION", nullable = true)
	private String nombreEntidadOrganizacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	private Date audFechaActualizacion;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	private Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false,  precision = 22, scale = 0)
	private Long audAccion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	private Long audCodUsuario;

	public ParticipacionInstitucion() {
		
	}

	@Override
	public Long getId() {
		return this.codParticipacionInstitucion;
	}

	@Override
	public void setId(Long codParticipacionInstitucion) {	
		this.codParticipacionInstitucion = codParticipacionInstitucion;
	}

	public Long getCodParticipacionInstitucion() {
		return codParticipacionInstitucion;
	}

	public void setCodParticipacionInstitucion(Long codParticipacionInstitucion) {
		this.codParticipacionInstitucion = codParticipacionInstitucion;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public String getNombreRazonSocialInstitucion() {
		return nombreRazonSocialInstitucion;
	}

	public void setNombreRazonSocialInstitucion(String nombreRazonSocialInstitucion) {
		this.nombreRazonSocialInstitucion = nombreRazonSocialInstitucion;
	}
	
	public String getNombreEntidadOrganizacion(){
		return nombreEntidadOrganizacion;
	}

	public void setNombreEntidadOrganizacion(String nombreEntidadOrganizacion) {
		this.nombreEntidadOrganizacion = nombreEntidadOrganizacion;
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
		return codParticipacionInstitucion + ',' + codPersona + ',' + nombreInstitucion + ',' + nombreRazonSocialInstitucion + ',' + nombreEntidadOrganizacion;
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new ParticipacionInstitucionDTO(codParticipacionInstitucion, codPersona, nombreInstitucion, nombreRazonSocialInstitucion, nombreEntidadOrganizacion);
	}
}