package co.gov.dafp.sigep2.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import co.gov.dafp.sigep2.entity.VistaBase;
import co.gov.dafp.sigep2.entity.view.HojaDeVidaDTO;
import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

@Entity(name = "DetalleEducativo")
@Table(name = "V_HOJA_VIDA_EDUCACION")
@SqlResultSetMappings({ @SqlResultSetMapping(name = DetalleEducativo.MAPPING_HOJA_VIDA_EDUCACION, classes = {
		@ConstructorResult(targetClass = HojaDeVidaDTO.class, columns = {
				@ColumnResult(name = "PERSONA_ID", type = Long.class),
				@ColumnResult(name = "NIVEL_EDUCATIVO", type = String.class),
				@ColumnResult(name = "AREA_CONOCIMIENTO", type = String.class),
				@ColumnResult(name = "PAIS_ESTUDIO", type = String.class),
				@ColumnResult(name = "DEPARTAMENTO_ESTUDIO", type = String.class),
				@ColumnResult(name = "MUNICIPIO_ESTUDIO", type = String.class),
				@ColumnResult(name = "INSTITUCION_EDUCATIVA", type = String.class),
				@ColumnResult(name = "PROGRAMA_ACADEMICO", type = String.class),
				@ColumnResult(name = "TITULO_OBTENIDO", type = String.class),
				@ColumnResult(name = "ESTADO_ESTUDIO", type = String.class),
				@ColumnResult(name = "FECHA_INICIO_ESTUDIO", type = String.class),
				@ColumnResult(name = "SEMESTRES_APROBADO", type = String.class),
				@ColumnResult(name = "FECHA_FINALIZACION_ESTUDIO", type = String.class),
				@ColumnResult(name = "FECHA_GRADO", type = String.class),
				@ColumnResult(name = "NUM_TARJETA_PROFESIONAL", type = String.class) }) }) })
public class DetalleEducativo extends VistaBase implements Serializable {
	private static final long serialVersionUID = -973330265129616471L;

	public static final String MAPPING_HOJA_VIDA_EDUCACION = "MAPPING_HOJA_VIDA_EDUCACION";

	@Id
	@Column(name = "PERSONA_ID", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	protected Long personaId;

	@Column(name = "NIVEL_EDUCATIVO", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String nivelEducativo;

	@Column(name = "AREA_CONOCIMIENTO", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String areaConocimiento;

	@Column(name = "PAIS_ESTUDIO", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String paisEstudio;

	@Column(name = "DEPARTAMENTO_ESTUDIO", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String departamentoEstudio;

	@Column(name = "MUNICIPIO_ESTUDIO", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String municipioEstudio;

	@Column(name = "INSTITUCION_EDUCATIVA", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String institucionEducativa;

	@Column(name = "PROGRAMA_ACADEMICO", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String programaAcademico;

	@Column(name = "TITULO_OBTENIDO", insertable = false, updatable = false)
	String tituloObtenido;

	@Column(name = "ESTADO_ESTUDIO", insertable = false, updatable = false)
	String estadoEstudio;

	@Column(name = "FECHA_INICIO_ESTUDIO", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String fechaInicio;

	@Column(name = "SEMESTRES_APROBADO", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String semestresAprobados;

	@Column(name = "FECHA_FINALIZACION_ESTUDIO", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String fechaTerminacion;

	@Column(name = "FECHA_GRADO", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String fechaGrado;

	@Column(name = "NUM_TARJETA_PROFESIONAL", unique = true, nullable = false, precision = 50, insertable = false, updatable = false)
	String tarjetaProfesional;

	public Long getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Long personaId) {
		this.personaId = personaId;
	}

	public String getNivelEducativo() {
		return nivelEducativo;
	}

	public void setNivelEducativo(String nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public String getAreaConocimiento() {
		return areaConocimiento;
	}

	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	public String getPaisEstudio() {
		return paisEstudio;
	}

	public void setPaisEstudio(String paisEstudio) {
		this.paisEstudio = paisEstudio;
	}

	public String getDepartamentoEstudio() {
		return departamentoEstudio;
	}

	public void setDepartamentoEstudio(String departamentoEstudio) {
		this.departamentoEstudio = departamentoEstudio;
	}

	public String getMunicipioEstudio() {
		return municipioEstudio;
	}

	public void setMunicipioEstudio(String municipioEstudio) {
		this.municipioEstudio = municipioEstudio;
	}

	public String getInstitucionEducativa() {
		return institucionEducativa;
	}

	public void setInstitucionEducativa(String institucionEducativa) {
		this.institucionEducativa = institucionEducativa;
	}

	public String getProgramaAcademico() {
		return programaAcademico;
	}

	public void setProgramaAcademico(String programaAcademico) {
		this.programaAcademico = programaAcademico;
	}

	public String getTituloObtenido() {
		return tituloObtenido;
	}

	public void setTituloObtenido(String tituloObtenido) {
		this.tituloObtenido = tituloObtenido;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getSemestresAprobados() {
		return semestresAprobados;
	}

	public void setSemestresAprobados(String semestresAprobados) {
		this.semestresAprobados = semestresAprobados;
	}

	public String getFechaTerminacion() {
		return fechaTerminacion;
	}

	public void setFechaTerminacion(String fechaTerminacion) {
		this.fechaTerminacion = fechaTerminacion;
	}

	public String getFechaGrado() {
		return fechaGrado;
	}

	public void setFechaGrado(String fechaGrado) {
		this.fechaGrado = fechaGrado;
	}

	public String getTarjetaProfesional() {
		return tarjetaProfesional;
	}

	public void setTarjetaProfesional(String tarjetaProfesional) {
		this.tarjetaProfesional = tarjetaProfesional;
	}

	public DetalleEducativo() {
		super();
	}

	public DetalleEducativo(Long personaId, String tituloObtenido) {
		super();
		this.personaId = personaId;
		this.tituloObtenido = tituloObtenido;
	}

	@Override
	public VistaBaseDTO getDTO() {
		return new HojaDeVidaDTO(personaId, nivelEducativo, areaConocimiento, paisEstudio, departamentoEstudio,
				municipioEstudio, institucionEducativa, programaAcademico, tituloObtenido, estadoEstudio, fechaInicio,
				semestresAprobados, fechaTerminacion, fechaGrado, tarjetaProfesional);
	}

	@Override
	public Long getId() {
		return this.personaId;
	}

	@Override
	public void setId(Long id) {
		this.personaId = id;
	}

	@Override
	public String toString() {
		return "";
	}
}