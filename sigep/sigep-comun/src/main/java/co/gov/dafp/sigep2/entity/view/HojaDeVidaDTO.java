package co.gov.dafp.sigep2.entity.view;

import java.io.Serializable;
import java.util.List;

import co.gov.dafp.sigep2.converter.CapitalCaseConverter;

public class HojaDeVidaDTO extends VistaBaseDTO implements Serializable {
	private static final long serialVersionUID = -2493024798076646953L;

	long personaId;
	String tipoDocumento;
	String numeroIdentificacion;
	String primerNombre;
	String segundoNombre;
	String primerApellido;
	String segundoApellido;
	String numeroLibretaMilitar;
	String distritoMilitar;
	String claseLibretaMilitar;
	String fechaNacimiento;
	String direccionResidencia;
	String paisNacimiento;
	String paisResidencia;
	String departamentoNacimiento;
	String departamentoResidencia;
	String municipioNacimiento;
	String municipioResidencia;
	String correoElectronico;
	String nombreCompleto;
	String telefonoMovil;
	String genero;

	String nivelEducativo;
	String areaConocimiento;
	String paisEstudio;
	String departamentoEstudio;
	String municipioEstudio;
	String institucionEducativa;
	List<String> institucionesEducativas;
	String programaAcademico;
	String tituloObtenido;
	String estadoEstudio;
	String fechaInicio;
	String semestresAprobados;
	String fechaTerminacion;
	String fechaGrado;
	String tarjetaProfesional;

	public HojaDeVidaDTO() {
		super();
	}

	public HojaDeVidaDTO(long personaId, String tipoDocumento, String numeroIdentificacion, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido) {
		super();
		this.personaId = personaId;
		this.tipoDocumento = tipoDocumento;
		this.numeroIdentificacion = numeroIdentificacion;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
	}

	public HojaDeVidaDTO(long personaId, String tipoDocumento, String numeroIdentificacion, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido, String numeroLibretaMilitar,
			String distritoMilitar, String claseLibretaMilitar, String fechaNacimiento, String direccionResidencia,
			String paisNacimiento, String paisResidencia, String departamentoNacimiento, String departamentoResidencia,
			String municipioNacimiento, String municipioResidencia, String correoElectronico, String nombreCompleto,
			String telefonoMovil, String genero) {
		super();
		this.personaId = personaId;
		this.tipoDocumento = tipoDocumento;
		this.numeroIdentificacion = numeroIdentificacion;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.numeroLibretaMilitar = numeroLibretaMilitar;
		this.distritoMilitar = distritoMilitar;
		this.claseLibretaMilitar = claseLibretaMilitar;
		this.fechaNacimiento = fechaNacimiento;
		this.direccionResidencia = direccionResidencia;
		this.paisNacimiento = paisNacimiento;
		this.paisResidencia = paisResidencia;
		this.departamentoNacimiento = departamentoNacimiento;
		this.departamentoResidencia = departamentoResidencia;
		this.municipioNacimiento = municipioNacimiento;
		this.municipioResidencia = municipioResidencia;
		this.correoElectronico = correoElectronico;
		this.nombreCompleto = nombreCompleto;
		this.telefonoMovil = telefonoMovil;
		this.genero = genero;
	}

	public HojaDeVidaDTO(long personaId, String nivelEducativo, String areaConocimiento, String paisEstudio,
			String departamentoEstudio, String municipioEstudio, String institucionEducativa, String programaAcademico,
			String tituloObtenido, String estadoEstudio, String fechaInicio, String semestresAprobados,
			String fechaTerminacion, String fechaGrado, String tarjetaProfesional) {
		super();
		this.personaId = personaId;
		this.nivelEducativo = nivelEducativo;
		this.areaConocimiento = areaConocimiento;
		this.paisEstudio = paisEstudio;
		this.departamentoEstudio = departamentoEstudio;
		this.municipioEstudio = municipioEstudio;
		this.institucionEducativa = institucionEducativa;
		this.programaAcademico = programaAcademico;
		this.tituloObtenido = tituloObtenido;
		this.estadoEstudio = estadoEstudio;
		this.fechaInicio = fechaInicio;
		this.semestresAprobados = semestresAprobados;
		this.fechaTerminacion = fechaTerminacion;
		this.fechaGrado = fechaGrado;
		this.tarjetaProfesional = tarjetaProfesional;
	}

	public HojaDeVidaDTO(long personaId, String nivelEducativo, String areaConocimiento, String paisEstudio,
			String departamentoEstudio, String municipioEstudio, String institucionEducativa,
			List<String> institucionesEducativas, String programaAcademico, String tituloObtenido, String estadoEstudio,
			String fechaInicio, String semestresAprobados, String fechaTerminacion, String fechaGrado,
			String tarjetaProfesional) {
		super();
		this.personaId = personaId;
		this.nivelEducativo = nivelEducativo;
		this.areaConocimiento = areaConocimiento;
		this.paisEstudio = paisEstudio;
		this.departamentoEstudio = departamentoEstudio;
		this.municipioEstudio = municipioEstudio;
		this.institucionEducativa = institucionEducativa;
		this.institucionesEducativas = institucionesEducativas;
		this.programaAcademico = programaAcademico;
		this.tituloObtenido = tituloObtenido;
		this.estadoEstudio = estadoEstudio;
		this.fechaInicio = fechaInicio;
		this.semestresAprobados = semestresAprobados;
		this.fechaTerminacion = fechaTerminacion;
		this.fechaGrado = fechaGrado;
		this.tarjetaProfesional = tarjetaProfesional;
	}

	public long getPersonaId() {
		return personaId;
	}

	public void setPersonaId(long personaId) {
		this.personaId = personaId;
	}

	public String getTipoDocumento() {
		return CapitalCaseConverter.convert(tipoDocumento);
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getPrimerNombre() {
		return CapitalCaseConverter.convert(primerNombre);
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return CapitalCaseConverter.convert(segundoNombre);
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return CapitalCaseConverter.convert(primerApellido);
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return CapitalCaseConverter.convert(segundoApellido);
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getNumeroLibretaMilitar() {
		return numeroLibretaMilitar;
	}

	public void setNumeroLibretaMilitar(String numeroLibretaMilitar) {
		this.numeroLibretaMilitar = numeroLibretaMilitar;
	}

	public String getDistritoMilitar() {
		return distritoMilitar;
	}

	public void setDistritoMilitar(String distritoMilitar) {
		this.distritoMilitar = distritoMilitar;
	}

	public String getClaseLibretaMilitar() {
		return CapitalCaseConverter.convert(claseLibretaMilitar);
	}

	public void setClaseLibretaMilitar(String claseLibretaMilitar) {
		this.claseLibretaMilitar = claseLibretaMilitar;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDireccionResidencia() {
		return CapitalCaseConverter.convert(direccionResidencia);
	}

	public void setDireccionResidencia(String direccionResidencia) {
		this.direccionResidencia = direccionResidencia;
	}

	public String getPaisNacimiento() {
		return CapitalCaseConverter.convert(paisNacimiento);
	}

	public void setPaisNacimiento(String paisNacimiento) {
		this.paisNacimiento = paisNacimiento;
	}

	public String getPaisResidencia() {
		return CapitalCaseConverter.convert(paisResidencia);
	}

	public void setPaisResidencia(String paisResidencia) {
		this.paisResidencia = paisResidencia;
	}

	public String getDepartamentoNacimiento() {
		return CapitalCaseConverter.convert(departamentoNacimiento);
	}

	public void setDepartamentoNacimiento(String departamentoNacimiento) {
		this.departamentoNacimiento = departamentoNacimiento;
	}

	public String getDepartamentoResidencia() {
		return CapitalCaseConverter.convert(departamentoResidencia);
	}

	public void setDepartamentoResidencia(String departamentoResidencia) {
		this.departamentoResidencia = departamentoResidencia;
	}

	public String getMunicipioNacimiento() {
		return CapitalCaseConverter.convert(municipioNacimiento);
	}

	public void setMunicipioNacimiento(String municipioNacimiento) {
		this.municipioNacimiento = municipioNacimiento;
	}

	public String getMunicipioResidencia() {
		return CapitalCaseConverter.convert(municipioResidencia);
	}

	public void setMunicipioResidencia(String municipioResidencia) {
		this.municipioResidencia = municipioResidencia;
	}

	public String getCorreoElectronico() {
		return correoElectronico.toLowerCase();
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getNombreCompleto() {
		return CapitalCaseConverter.convert(nombreCompleto);
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public String getGenero() {
		return CapitalCaseConverter.convert(genero);
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNivelEducativo() {
		return CapitalCaseConverter.convert(nivelEducativo);
	}

	public void setNivelEducativo(String nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public String getAreaConocimiento() {
		return CapitalCaseConverter.convert(areaConocimiento);
	}

	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	public String getPaisEstudio() {
		return CapitalCaseConverter.convert(paisEstudio);
	}

	public void setPaisEstudio(String paisEstudio) {
		this.paisEstudio = paisEstudio;
	}

	public String getDepartamentoEstudio() {
		return CapitalCaseConverter.convert(departamentoEstudio);
	}

	public void setDepartamentoEstudio(String departamentoEstudio) {
		this.departamentoEstudio = departamentoEstudio;
	}

	public String getMunicipioEstudio() {
		return CapitalCaseConverter.convert(municipioEstudio);
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
		return CapitalCaseConverter.convert(programaAcademico);
	}

	public void setProgramaAcademico(String programaAcademico) {
		this.programaAcademico = programaAcademico;
	}

	public String getTituloObtenido() {
		return CapitalCaseConverter.convert(tituloObtenido);
	}

	public void setTituloObtenido(String tituloObtenido) {
		this.tituloObtenido = tituloObtenido;
	}

	public String getEstadoEstudio() {
		return estadoEstudio;
	}

	public void setEstadoEstudio(String estadoEstudio) {
		this.estadoEstudio = estadoEstudio;
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

	public List<String> getInstitucionesEducativas() {
		return institucionesEducativas;
	}

	public void setInstitucionesEducativas(List<String> institucionesEducativas) {
		this.institucionesEducativas = institucionesEducativas;
	}

	@Override
	public Long getId() {
		return personaId;
	}

	@Override
	public void setId(Long personaId) {
		this.personaId = personaId;
	}

	@Override
	public String toString() {
		return "HojaDeVidaDTO";
	}
}