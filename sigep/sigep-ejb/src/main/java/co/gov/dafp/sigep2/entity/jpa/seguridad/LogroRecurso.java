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
import co.gov.dafp.sigep2.entity.seguridad.LogroRecursoDTO;



@Entity(name = "LogroRecurso")
@Table(name = "LOGRO_RECURSO")
@SqlResultSetMappings({ @SqlResultSetMapping(name = LogroRecurso.LOGRO_RECURSO_MAPPING, classes = {
		@ConstructorResult(targetClass = LogroRecursoDTO.class, columns = {
				@ColumnResult(name = "COD_LOGRO_RECURSO", type = Long.class),
				@ColumnResult(name = "COD_PERSONA", type = Long.class),
				@ColumnResult(name = "FLG_ADMINISTRA_RECURSOS", type = Boolean.class),
				@ColumnResult(name = "NOMBRE_ENTIDAD", type = String.class),
				@ColumnResult(name = "NUM_EMPLEADOS", type = Long.class),
				@ColumnResult(name = "FLG_PERSONAS_CARGO", type = Boolean.class),
				@ColumnResult(name = "NUM_PERSONAS_CARGO", type = Long.class),
				@ColumnResult(name = "FLG_RECURSO_ECONOMICO", type = Boolean.class),
				@ColumnResult(name = "VALOR_RECURSO_ECONOMICO", type = Double.class),
				@ColumnResult(name = "LOGRO_SOBREDSALIENTE", type = String.class) }) }) })

public class LogroRecurso extends EntidadBase implements java.io.Serializable {
	
	private static final long serialVersionUID = 1876566329593827343L;
	public static final String LOGRO_RECURSO_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.LogroRecurso";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_LOGRO_RECURSO", unique = true, nullable = false, precision = 22, scale = 0)
	private Long codLogroRecurso;
	
	@Column(name = "COD_PERSONA", nullable = false, length = 20)
	private Long codPersona;

	@Column(name = "FLG_ADMINISTRA_RECURSOS", nullable = false)
	private Boolean flgAdministraRecursos;
	
	@Column(name = "NOMBRE_ENTIDAD", nullable = false)
	private String nombreEntidad;

	@Column(name = "NUM_EMPLEADOS", length = 50)
	private Long numEmpleados;

	@Column(name = "FLG_PERSONAS_CARGO", length = 20)
	private Boolean flgPersonasCargo;

	@Column(name = "NUM_PERSONAS_CARGO", length = 50)
	private Long numPersonasCargo;

	@Column(name = "FLG_RECURSO_ECONOMICO", nullable = false)
	private Boolean flgRecursoEconomico;

	@Column(name = "VALOR_RECURSO_ECONOMICO", nullable = false)
	private Double valorRecursoEconomico;
	
	@Column(name = "LOGRO_SOBREDSALIENTE", nullable = false)
	private String logroSobresaliente;

	@Temporal(TemporalType.DATE)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	private Date audFechaActualizacion;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	private Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	private Long audAccion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	private Long audCodUsuario;

	public LogroRecurso() {
	}

	@Override
	public Long getId() {
		return this.codLogroRecurso;
	}

	@Override
	public void setId(Long codLogroRecurso) {	
		this.codLogroRecurso = codLogroRecurso;
	}

	public Boolean getflgAdministraRecursos() {
		return flgAdministraRecursos;
	}

	public void setflgAdministraRecursos(Boolean flgAdministraRecursos) {
		this.flgAdministraRecursos = flgAdministraRecursos;
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

	public Long getNumEmpleados() {
		return numEmpleados;
	}

	public void setNumEmpleados(Long numEmpleados) {
		this.numEmpleados = numEmpleados;
	}

	public Boolean getFlgPersonasCargo() {
		return flgPersonasCargo;
	}

	public void setFlgPersonasCargo(Boolean flgPersonasCargo) {
		this.flgPersonasCargo = flgPersonasCargo;
	}

	public Long getNumPersonasCargo() {
		return numPersonasCargo;
	}

	public void setNumPersonasCargo(Long numPersonasCargo) {
		this.numPersonasCargo = numPersonasCargo;
	}

	public Boolean getFlgRecursoEconomico() {
		return flgRecursoEconomico;
	}

	public void setFlgRecursoEconomico(Boolean flgRecursoEconomico) {
		this.flgRecursoEconomico = flgRecursoEconomico;
	}

	public Double getValorRecursoEconomico() {
		return valorRecursoEconomico;
	}

	public void setvalorRecursoEconomico(Double valorRecursoEconomico) {
		this.valorRecursoEconomico = valorRecursoEconomico;
	}

	public String getLogroSobresaliente() {
		return logroSobresaliente;
	}

	public void setLogroSobresaliente(String logroSobresaliente) {
		this.logroSobresaliente = logroSobresaliente;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new LogroRecursoDTO(codLogroRecurso, codPersona, flgAdministraRecursos, nombreEntidad, numEmpleados, flgPersonasCargo, numPersonasCargo, flgRecursoEconomico, valorRecursoEconomico, logroSobresaliente);
	}
}