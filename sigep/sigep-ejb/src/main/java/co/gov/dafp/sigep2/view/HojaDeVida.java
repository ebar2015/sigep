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

@Entity(name = "HojaDeVida")
@Table(name = "V_HOJA_VIDA_DETALLE")
@SqlResultSetMappings({
		@SqlResultSetMapping(name = HojaDeVida.MAPPING_DATOS_PERSONALES, classes = {
				@ConstructorResult(targetClass = HojaDeVidaDTO.class, columns = {
						@ColumnResult(name = "PERSONA_ID", type = Long.class),
						@ColumnResult(name = "TIPO_DOCUMENTO", type = String.class),
						@ColumnResult(name = "NUMERO_IDENTIFICACION", type = String.class),
						@ColumnResult(name = "PRIMER_NOMBRE", type = String.class),
						@ColumnResult(name = "SEGUNDO_NOMBRE", type = String.class),
						@ColumnResult(name = "PRIMER_APELLIDO", type = String.class),
						@ColumnResult(name = "SEGUNDO_APELLIDO", type = String.class) }) }),
		@SqlResultSetMapping(name = HojaDeVida.MAPPING_DETALLE_HOJA_VIDA, classes = {
				@ConstructorResult(targetClass = HojaDeVidaDTO.class, columns = {
						@ColumnResult(name = "PERSONA_ID", type = Long.class),
						@ColumnResult(name = "TIPO_DOCUMENTO", type = String.class),
						@ColumnResult(name = "NUMERO_IDENTIFICACION", type = String.class),
						@ColumnResult(name = "PRIMER_NOMBRE", type = String.class),
						@ColumnResult(name = "SEGUNDO_NOMBRE", type = String.class),
						@ColumnResult(name = "PRIMER_APELLIDO", type = String.class),
						@ColumnResult(name = "SEGUNDO_APELLIDO", type = String.class),
						@ColumnResult(name = "NUMERO_LIBRETA_MILITAR", type = String.class),
						@ColumnResult(name = "DISTRITO_MILITAR", type = String.class),
						@ColumnResult(name = "CLASE_LIBRETA_MILITAR", type = String.class),
						@ColumnResult(name = "FECHA_NACIMIENTO", type = String.class),
						@ColumnResult(name = "DIRECCION_RESIDENCIA", type = String.class),
						@ColumnResult(name = "PAIS_NACIMIENTO", type = String.class),
						@ColumnResult(name = "PAIS_RESIDENCIA", type = String.class),
						@ColumnResult(name = "DEPARTAMENTO_NACIMIENTO", type = String.class),
						@ColumnResult(name = "DEPARTAMENTO_RESIDENCIA", type = String.class),
						@ColumnResult(name = "MUNICIPIO_NACIMIENTO", type = String.class),
						@ColumnResult(name = "MUNICIPIO_RESIDENCIA", type = String.class),
						@ColumnResult(name = "EMAIL", type = String.class),
						@ColumnResult(name = "NOMBRE_COMPLETO", type = String.class),
						@ColumnResult(name = "MOVIL", type = String.class),
						@ColumnResult(name = "GENERO", type = String.class) }) }) })
public class HojaDeVida extends VistaBase implements Serializable {
	private static final long serialVersionUID = -6650039185464561838L;
	public static final String MAPPING_DATOS_PERSONALES = "MAPPING_DATOS_PERSONALES";
	public static final String MAPPING_DETALLE_HOJA_VIDA = "MAPPING_DETALLE_HOJA_VIDA";

	@Id
	@Column(name = "PERSONA_ID", unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	protected Long personaId;

	@Column(name = "TIPO_DOCUMENTO", insertable = false, updatable = false)
	private String tipoDocumento;

	@Column(name = "NUMERO_IDENTIFICACION", insertable = false, updatable = false)
	private String numeroIdentificacion;

	@Column(name = "PRIMER_NOMBRE", insertable = false, updatable = false)
	private String primerNombre;

	@Column(name = "SEGUNDO_NOMBRE", insertable = false, updatable = false)
	private String segundoNombre;

	@Column(name = "PRIMER_APELLIDO", insertable = false, updatable = false)
	private String primerApellido;

	@Column(name = "SEGUNDO_APELLIDO", insertable = false, updatable = false)
	private String segundoApellido;

	@Column(name = "NUMERO_LIBRETA_MILITAR", insertable = false, updatable = false)
	private String numeroLibretaMilitar;

	@Column(name = "DISTRITO_MILITAR", insertable = false, updatable = false)
	private String distritoMilitar;

	@Column(name = "CLASE_LIBRETA_MILITAR", insertable = false, updatable = false)
	private String claseLibretaMilitar;

	@Column(name = "FECHA_NACIMIENTO", insertable = false, updatable = false)
	private String fechaNacimiento;

	@Column(name = "DIRECCION_RESIDENCIA", insertable = false, updatable = false)
	private String direccionResidencia;

	@Column(name = "PAIS_NACIMIENTO", insertable = false, updatable = false)
	private String paisNacimiento;

	@Column(name = "PAIS_RESIDENCIA", insertable = false, updatable = false)
	private String paisResidencia;

	@Column(name = "DEPARTAMENTO_NACIMIENTO", insertable = false, updatable = false)
	private String departamentoNacimiento;

	@Column(name = "DEPARTAMENTO_RESIDENCIA", insertable = false, updatable = false)
	private String departamentoResidencia;

	@Column(name = "MUNICIPIO_NACIMIENTO", insertable = false, updatable = false)
	private String municipioNacimiento;

	@Column(name = "MUNICIPIO_RESIDENCIA", insertable = false, updatable = false)
	private String municipioResidencia;

	@Column(name = "EMAIL", insertable = false, updatable = false)
	private String correoElectronico;

	@Column(name = "NOMBRE_COMPLETO", insertable = false, updatable = false)
	private String nombreCompleto;

	@Column(name = "MOVIL", insertable = false, updatable = false)
	private String telefonoMovil;

	@Column(name = "GENERO", insertable = false, updatable = false)
	private String genero;

	public HojaDeVida() {
		super();
	}

	public HojaDeVida(Long personaId, String tipoDocumento, String numeroIdentificacion, String primerNombre,
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

	public HojaDeVida(Long personaId, String tipoDocumento, String numeroIdentificacion, String primerNombre,
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

	public Long getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Long personaId) {
		this.personaId = personaId;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
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
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
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
		return claseLibretaMilitar;
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
		return direccionResidencia;
	}

	public void setDireccionResidencia(String direccionResidencia) {
		this.direccionResidencia = direccionResidencia;
	}

	public String getPaisNacimiento() {
		return paisNacimiento;
	}

	public void setPaisNacimiento(String paisNacimiento) {
		this.paisNacimiento = paisNacimiento;
	}

	public String getPaisResidencia() {
		return paisResidencia;
	}

	public void setPaisResidencia(String paisResidencia) {
		this.paisResidencia = paisResidencia;
	}

	public String getDepartamentoNacimiento() {
		return departamentoNacimiento;
	}

	public void setDepartamentoNacimiento(String departamentoNacimiento) {
		this.departamentoNacimiento = departamentoNacimiento;
	}

	public String getDepartamentoResidencia() {
		return departamentoResidencia;
	}

	public void setDepartamentoResidencia(String departamentoResidencia) {
		this.departamentoResidencia = departamentoResidencia;
	}

	public String getMunicipioNacimiento() {
		return municipioNacimiento;
	}

	public void setMunicipioNacimiento(String municipioNacimiento) {
		this.municipioNacimiento = municipioNacimiento;
	}

	public String getMunicipioResidencia() {
		return municipioResidencia;
	}

	public void setMunicipioResidencia(String municipioResidencia) {
		this.municipioResidencia = municipioResidencia;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
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
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
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

	@Override
	public VistaBaseDTO getDTO() {
		return null;
//		return new HojaDeVidaDTO(personaId, tipoDocumento, numeroIdentificacion, primerNombre, segundoNombre,
//				primerApellido, segundoApellido, numeroLibretaMilitar, distritoMilitar, claseLibretaMilitar,
//				fechaNacimiento, direccionResidencia, paisNacimiento, paisResidencia, departamentoNacimiento,
//				departamentoResidencia, municipioNacimiento, municipioResidencia, correoElectronico, nombreCompleto,
//				telefonoMovil, genero);
	}
}