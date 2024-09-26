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
import co.gov.dafp.sigep2.entity.seguridad.ParticipacionProyectoDTO;



@Entity(name = "ParticipacionProyecto")
@Table(name = "PARTICIPACION_PROYECTO")
@SqlResultSetMappings({ @SqlResultSetMapping(name = ParticipacionProyecto.PARTICIPACION_PROYECTO_MAPPING, classes = {
		@ConstructorResult(targetClass = EvaluacionDesempenoDTO.class, columns = {
				@ColumnResult(name = "COD_PARTICIPACION_PROYECTO", type = Long.class),
				@ColumnResult(name = "COD_PERSONA", type = Long.class),
				@ColumnResult(name = "NOMBRE_ENTIDAD", type = String.class),
				@ColumnResult(name = "NOMBRE_PROYECTO", type = String.class),
				@ColumnResult(name = "ROL_LABORADO", type = String.class),
				@ColumnResult(name = "COD_PAIS", type = Long.class),
				@ColumnResult(name = "COD_MUNICIPIO", type = Long.class),
				@ColumnResult(name = "COD_DEPARTAMENTO", type = Long.class),
				@ColumnResult(name = "FECHA_INCIO", type = Date.class),
				@ColumnResult(name = "FECHA_TERMINACION", type = Date.class) }) }) })

public class ParticipacionProyecto extends EntidadBase implements java.io.Serializable {
	
	private static final long serialVersionUID = 1876566329593827343L;
	public static final String PARTICIPACION_PROYECTO_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.ParticipacionProyecto";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_PARTICIPACION_PROYECTO", unique = true, nullable = false, precision = 22, scale = 0)
	private Long codParticipacionProyecto;
	
	@Column(name = "COD_PERSONA", nullable = false, length = 20)
	private Long codPersona;

	@Column(name = "NOMBRE_ENTIDAD", nullable = false)
	private String nombreEntidad;
	
	@Column(name = "NOMBRE_PROYECTO", nullable = false)
	private String nombreProyecto;
	
	@Column(name = "ROL_LABORADO", nullable = true)
	private String rolLaborado;

	@Column(name = "COD_PAIS", length = 50, nullable = true)
	private Long codPais;

	@Column(name = "COD_MUNICIPIO", length = 20 , nullable = true)
	private Long codMunicipio;

	@Column(name = "COD_DEPARTAMENTO", nullable = true)
	private Long codDepartamento;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INCIO", nullable = true)
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_TERMINACION", nullable = true)
	private Date fechaTerminacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	private Date audFechaActualizacion;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	private Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false,  precision = 22, scale = 0)
	private Long audAccion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	private Long audCodUsuario;

	public ParticipacionProyecto() {
		
	}

	@Override
	public Long getId() {
		return this.codParticipacionProyecto;
	}

	@Override
	public void setId(Long codParticipacionProyecto) {	
		this.codParticipacionProyecto = codParticipacionProyecto;
	}

	public Long getCodParticipacionProyecto() {
		return codParticipacionProyecto;
	}

	public void setCodParticipacionProyecto(Long codParticipacionProyecto) {
		this.codParticipacionProyecto = codParticipacionProyecto;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}
	
	public String getgetRolLaborado(){
		return rolLaborado;
	}

	public void setRolLaborado(String rolLaborado) {
		this.rolLaborado = rolLaborado;
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

	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public Date getFechaTerminacion() {
		return fechaTerminacion;
	}
	
	
	public void setFechaTerminacion(Date fechaTerminacion) {
		this.fechaTerminacion = fechaTerminacion;
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
		return codParticipacionProyecto + ',' + codPersona + ',' + nombreEntidad + ',' + nombreProyecto + ',' + rolLaborado + ',' + codPais + ',' + codDepartamento + ',' + codMunicipio +',' + fechaInicio + ',' + fechaTerminacion ;
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new ParticipacionProyectoDTO(codParticipacionProyecto, codPersona, nombreEntidad, nombreProyecto, rolLaborado, codPais, codDepartamento, codMunicipio, fechaInicio, fechaTerminacion);
	}
}