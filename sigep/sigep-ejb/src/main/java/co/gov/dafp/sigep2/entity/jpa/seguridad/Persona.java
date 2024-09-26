package co.gov.dafp.sigep2.entity.jpa.seguridad;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.gov.dafp.sigep2.converter.BooleanToNumberConverter;
import co.gov.dafp.sigep2.converter.UpperCaseConverter;
import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.MunicipioDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.Departamento;
import co.gov.dafp.sigep2.entity.jpa.comun.Municipio;
import co.gov.dafp.sigep2.entity.jpa.comun.Pais;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.view.ClaseLibretaMilitarDTO;
import co.gov.dafp.sigep2.entity.view.EstadoCivilDTO;
import co.gov.dafp.sigep2.entity.view.PoblacionEtnicaDTO;
import co.gov.dafp.sigep2.view.ClaseLibretaMilitar;
import co.gov.dafp.sigep2.view.EstadoCivil;
import co.gov.dafp.sigep2.view.PoblacionEtnica;

@Entity(name = "Persona")
@Table(name = "PERSONA")
@SqlResultSetMappings({
		@SqlResultSetMapping(name = Persona.PERSONA_MAPPING, classes = {
				@ConstructorResult(targetClass = PersonaDTO.class, columns = {
						@ColumnResult(name = "COD_PERSONA", type = Long.class),
						@ColumnResult(name = "COD_GENERO", type = Long.class),
						@ColumnResult(name = "COD_FACTOR_RH", type = Long.class),
						@ColumnResult(name = "COD_TIPO_IDENTIFICACION", type = Long.class),
						@ColumnResult(name = "NUMERO_IDENTIFICACION", type = String.class),
						@ColumnResult(name = "PRIMER_NOMBRE", type = String.class),
						@ColumnResult(name = "SEGUNDO_NOMBRE", type = String.class),
						@ColumnResult(name = "PRIMER_APELLIDO", type = String.class),
						@ColumnResult(name = "SEGUNDO_APELLIDO", type = String.class),
						@ColumnResult(name = "RESUMEN_PROFESIONAL", type = String.class),
						@ColumnResult(name = "NUMERO_LIBRETA_MILITAR", type = String.class),
						@ColumnResult(name = "DISTRITO_MILITAR", type = String.class),
						@ColumnResult(name = "LIBRETA_MILITAR_URL", type = String.class),
						@ColumnResult(name = "FOTO_URL", type = String.class),
						@ColumnResult(name = "DOCUMENTO_IDENTIFICACION_URL", type = String.class),
						@ColumnResult(name = "FECHA_NACIMIENTO", type = Date.class),
						@ColumnResult(name = "EXPUESTO_POLITICAMENTE", type = Boolean.class),
						@ColumnResult(name = "CORREO_ELECTRONICO", type = String.class),
						@ColumnResult(name = "CORREO_ALTERNATIVO", type = String.class),
						@ColumnResult(name = "FLG_ACTIVO", type = Boolean.class),
						@ColumnResult(name = "FLG_ESTADO", type = Boolean.class) }) }),
		@SqlResultSetMapping(name = Persona.MAPPING_DATOS_DE_IDENTIFICACION, classes = {
				@ConstructorResult(targetClass = PersonaDTO.class, columns = {
						@ColumnResult(name = "COD_PERSONA", type = Long.class),
						@ColumnResult(name = "COD_ENTIDAD", type = Long.class),
						@ColumnResult(name = "COD_TIPO_IDENTIFICACION", type = Long.class),
						@ColumnResult(name = "NUMERO_IDENTIFICACION", type = String.class),
						@ColumnResult(name = "PRIMER_NOMBRE", type = String.class),
						@ColumnResult(name = "SEGUNDO_NOMBRE", type = String.class),
						@ColumnResult(name = "PRIMER_APELLIDO", type = String.class),
						@ColumnResult(name = "SEGUNDO_APELLIDO", type = String.class) }) }),
		@SqlResultSetMapping(name = Persona.MAPPING_LAZYMODEL, classes = {
				@ConstructorResult(targetClass = PersonaDTO.class, columns = {
						@ColumnResult(name = "COD_PERSONA", type = Long.class),
						@ColumnResult(name = "COD_GENERO", type = Long.class),
						@ColumnResult(name = "COD_FACTOR_RH", type = Long.class),
						@ColumnResult(name = "COD_TIPO_IDENTIFICACION", type = Long.class),
						@ColumnResult(name = "NUMERO_IDENTIFICACION", type = String.class),
						@ColumnResult(name = "PRIMER_NOMBRE", type = String.class),
						@ColumnResult(name = "SEGUNDO_NOMBRE", type = String.class),
						@ColumnResult(name = "PRIMER_APELLIDO", type = String.class),
						@ColumnResult(name = "SEGUNDO_APELLIDO", type = String.class),
						@ColumnResult(name = "RESUMEN_PROFESIONAL", type = String.class),
						@ColumnResult(name = "NUMERO_LIBRETA_MILITAR", type = String.class),
						@ColumnResult(name = "DISTRITO_MILITAR", type = String.class),
						@ColumnResult(name = "LIBRETA_MILITAR_URL", type = String.class),
						@ColumnResult(name = "FOTO_URL", type = String.class),
						@ColumnResult(name = "DOCUMENTO_IDENTIFICACION_URL", type = String.class),
						@ColumnResult(name = "FECHA_NACIMIENTO", type = Date.class),
						@ColumnResult(name = "EXPUESTO_POLITICAMENTE", type = Boolean.class),
						@ColumnResult(name = "CORREO_ELECTRONICO", type = String.class),
						@ColumnResult(name = "CORREO_ALTERNATIVO", type = String.class),
						@ColumnResult(name = "FLG_ACTIVO", type = Boolean.class),
						@ColumnResult(name = "FLG_ESTADO", type = Boolean.class),
						@ColumnResult(name = "TOTAL", type = BigDecimal.class),
						@ColumnResult(name = "NOMBRE_ENTIDAD", type = String.class),
						@ColumnResult(name = "NOMBRE_DEPENDENCIA", type = String.class) }) }),
		@SqlResultSetMapping(name = Persona.MAPPING_PERSONA_ROLES, classes = {
				@ConstructorResult(targetClass = PersonaDTO.class, columns = {
						@ColumnResult(name = "COD_PERSONA", type = Long.class),
						@ColumnResult(name = "COD_GENERO", type = Long.class),
						@ColumnResult(name = "COD_FACTOR_RH", type = Long.class),
						@ColumnResult(name = "COD_TIPO_IDENTIFICACION", type = Long.class),
						@ColumnResult(name = "NUMERO_IDENTIFICACION", type = String.class),
						@ColumnResult(name = "PRIMER_NOMBRE", type = String.class),
						@ColumnResult(name = "SEGUNDO_NOMBRE", type = String.class),
						@ColumnResult(name = "PRIMER_APELLIDO", type = String.class),
						@ColumnResult(name = "SEGUNDO_APELLIDO", type = String.class),
						@ColumnResult(name = "RESUMEN_PROFESIONAL", type = String.class),
						@ColumnResult(name = "NUMERO_LIBRETA_MILITAR", type = String.class),
						@ColumnResult(name = "DISTRITO_MILITAR", type = String.class),
						@ColumnResult(name = "LIBRETA_MILITAR_URL", type = String.class),
						@ColumnResult(name = "FOTO_URL", type = String.class),
						@ColumnResult(name = "DOCUMENTO_IDENTIFICACION_URL", type = String.class),
						@ColumnResult(name = "FECHA_NACIMIENTO", type = Date.class),
						@ColumnResult(name = "EXPUESTO_POLITICAMENTE", type = Boolean.class),
						@ColumnResult(name = "CORREO_ELECTRONICO", type = String.class),
						@ColumnResult(name = "CORREO_ALTERNATIVO", type = String.class),
						@ColumnResult(name = "FLG_ACTIVO", type = Boolean.class),
						@ColumnResult(name = "FLG_ESTADO", type = Boolean.class),
						@ColumnResult(name = "TOTAL", type = BigDecimal.class),
						@ColumnResult(name = "COD_ENTIDAD", type = Long.class),
						@ColumnResult(name = "NOMBRE_ENTIDAD", type = String.class),
						@ColumnResult(name = "NOMBRE_DEPENDENCIA", type = String.class) }) }) })
public class Persona extends EntidadBase implements java.io.Serializable {
	private static final long serialVersionUID = 5805070928150957024L;

	public static final String BY_LIKE_FULL_NAME = "co.gov.dafp.sigep2.entity.jpa.seguridad.Persona.BY_LIKE_FULL_NAME";

	public static final String NAME_SUPER_ADMIN = "SUPER ADMIN";

	public static final String PERSONA_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.Persona";
	public static final String MAPPING_DATOS_DE_IDENTIFICACION = "MAPPING_DATOS_DE_IDENTIFICACION";
	public static final String MAPPING_LAZYMODEL = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.PersonaLazyModel";
	public static final String MAPPING_PERSONA_ROLES = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.PersonaRolLazyModel";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_PERSONA", insertable = false, updatable = false, unique = true, nullable = false)
	private long codPersona;

	@Column(name = "COD_GENERO", precision = 22, scale = 0)
	private Long codGenero;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_ESTADO_CIVIL", nullable = true)
	private EstadoCivil codEstadoCivil;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_PAIS_NACIMIENTO", nullable = true)
	private Pais codPaisNacimiento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_DEPARTAMENTO_NACIMIENTO", nullable = true)
	private Departamento codDepartamentoNacimiento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_MUNICIPIO_NACIMIENTO", nullable = true)
	private Municipio codMunicipioNacimiento;

	@Column(name = "COD_FACTOR_RH", precision = 22, scale = 0)
	private Long codFactorRh;

	@Column(name = "COD_TIPO_IDENTIFICACION", nullable = false, precision = 22, scale = 0)
	private Long codTipoIdentificacion;

	@Column(name = "NUMERO_IDENTIFICACION", length = 15)
	@Convert(converter = UpperCaseConverter.class)
	private String numeroIdentificacion;

	@Column(name = "PRIMER_NOMBRE", nullable = false, length = 50)
	private String primerNombre;

	@Column(name = "SEGUNDO_NOMBRE", length = 50)
	private String segundoNombre;

	@Column(name = "PRIMER_APELLIDO", nullable = false, length = 50)
	private String primerApellido;

	@Column(name = "SEGUNDO_APELLIDO", length = 50)
	private String segundoApellido;

	@Column(name = "RESUMEN_PROFESIONAL", length = 1000)
	private String resumenProfesional;

	@Column(name = "NUMERO_LIBRETA_MILITAR", length = 20)
	private String numeroLibretaMilitar;

	@Column(name = "DISTRITO_MILITAR", length = 2)
	private String distritoMilitar;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLASE_LIBRETA_MILITAR", nullable = true)
	private ClaseLibretaMilitar codClaseLibretaMilitar;

	@Column(name = "LIBRETA_MILITAR_URL")
	private String libretaMilitarUrl;

	@Column(name = "FOTO_URL")
	private String fotoUrl;

	@Column(name = "DOCUMENTO_IDENTIFICACION_URL", length = 10)
	private String documentoIdentificacionUrl;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_NACIMIENTO", length = 7)
	private Date fechaNacimiento;

	@Column(name = "EXPUESTO_POLITICAMENTE", precision = 1, scale = 0)
	private Boolean expuestoPoliticamente;

	@Column(name = "CORREO_ELECTRONICO", length = 100)
	private String correoElectronico;

	@Column(name = "CORREO_ALTERNATIVO", length = 100)
	private String correoAlternativo;

	@Column(name = "FLG_ACTIVO", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgActivo;

	@Column(name = "FLG_ESTADO", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgEstado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_PERTENENCIA_ETNICA", nullable = true)
	private PoblacionEtnica codPertenenciaEtnica;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "codPersona", orphanRemoval = true)
	private List<NacionalidadPerfil> nacionalidadPerfil;

	public Persona() {
	}

	public Persona(Long codTipoIdentificacion, String primerNombre, String primerApellido) {
		this.codTipoIdentificacion = codTipoIdentificacion;
		this.primerNombre = primerNombre;
		this.primerApellido = primerApellido;
	}

	public Persona(Long codGenero, EstadoCivil codEstadoCivil, Pais codPaisNacimiento,
			Departamento codDepartamentoNacimiento, Municipio codMunicipioNacimiento, Long codFactorRh,
			Long codTipoIdentificacion, String numeroIdentificacion, String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido, String resumenProfesional, String numeroLibretaMilitar,
			String distritoMilitar, String libretaMilitarUrl, String fotoUrl, String documentoIdentificacionUrl,
			Date fechaNacimiento, Boolean expuestoPoliticamente, String correoElectronico, String correoAlternativo,
			Boolean flgActivo, Boolean flgEstado) {
		this.codGenero = codGenero;
		this.codEstadoCivil = codEstadoCivil;
		this.codPaisNacimiento = codPaisNacimiento;
		this.codDepartamentoNacimiento = codDepartamentoNacimiento;
		this.codMunicipioNacimiento = codMunicipioNacimiento;
		this.codFactorRh = codFactorRh;
		this.codTipoIdentificacion = codTipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.resumenProfesional = resumenProfesional;
		this.numeroLibretaMilitar = numeroLibretaMilitar;
		this.distritoMilitar = distritoMilitar;
		this.libretaMilitarUrl = libretaMilitarUrl;
		this.fotoUrl = fotoUrl;
		this.documentoIdentificacionUrl = documentoIdentificacionUrl;
		this.fechaNacimiento = fechaNacimiento;
		this.expuestoPoliticamente = expuestoPoliticamente;
		this.correoElectronico = correoElectronico;
		this.correoAlternativo = correoAlternativo;
		this.flgActivo = flgActivo;
		this.flgEstado = flgEstado;
	}

	public Persona(long id, Long codTipoIdentificacion, String numeroIdentificacion, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido) {
		super();
		this.codPersona = id;
		this.codTipoIdentificacion = codTipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
	}

	public Long getId() {
		return this.codPersona;
	}

	public void setId(Long id) {
		this.codPersona = id;
	}

	public long getCodPersona() {
		return this.codPersona;
	}

	public void setCodPersona(long codPersona) {
		this.codPersona = codPersona;
	}

	public Long getCodGenero() {
		return this.codGenero;
	}

	public void setCodGenero(Long codGenero) {
		this.codGenero = codGenero;
	}

	public EstadoCivil getCodEstadoCivil() {
		return this.codEstadoCivil;
	}

	public void setCodEstadoCivil(EstadoCivil codEstadoCivil) {
		this.codEstadoCivil = codEstadoCivil;
	}

	public Pais getCodPaisNacimiento() {
		return codPaisNacimiento;
	}

	public void setCodPaisNacimiento(Pais codPaisNacimiento) {
		this.codPaisNacimiento = codPaisNacimiento;
	}

	public Departamento getCodDepartamentoNacimiento() {
		return codDepartamentoNacimiento;
	}

	public void setCodDepartamentoNacimiento(Departamento codDepartamentoNacimiento) {
		this.codDepartamentoNacimiento = codDepartamentoNacimiento;
	}

	public Municipio getCodMunicipioNacimiento() {
		return this.codMunicipioNacimiento;
	}

	public void setCodMunicipioNacimiento(Municipio codMunicipioNacimiento) {
		this.codMunicipioNacimiento = codMunicipioNacimiento;
	}

	public Long getCodFactorRh() {
		return this.codFactorRh;
	}

	public void setCodFactorRh(Long codFactorRh) {
		this.codFactorRh = codFactorRh;
	}

	public Long getCodTipoIdentificacion() {
		return this.codTipoIdentificacion;
	}

	public void setCodTipoIdentificacion(Long codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return this.numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getPrimerNombre() {
		return this.primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		if (this.segundoNombre == null) {
			return "";
		}
		return this.segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return this.primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		if (this.segundoApellido == null) {
			return "";
		}
		return this.segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getResumenProfesional() {
		return this.resumenProfesional;
	}

	public void setResumenProfesional(String resumenProfesional) {
		this.resumenProfesional = resumenProfesional;
	}

	public String getNumeroLibretaMilitar() {
		return this.numeroLibretaMilitar;
	}

	public void setNumeroLibretaMilitar(String numeroLibretaMilitar) {
		this.numeroLibretaMilitar = numeroLibretaMilitar;
	}

	public String getDistritoMilitar() {
		return this.distritoMilitar;
	}

	public void setDistritoMilitar(String distritoMilitar) {
		this.distritoMilitar = distritoMilitar;
	}

	public ClaseLibretaMilitar getCodClaseLibretaMilitar() {
		return this.codClaseLibretaMilitar;
	}

	public void setCodClaseLibretaMilitar(ClaseLibretaMilitar codClaseLibretaMilitar) {
		this.codClaseLibretaMilitar = codClaseLibretaMilitar;
	}

	public String getLibretaMilitarUrl() {
		return this.libretaMilitarUrl;
	}

	public void setLibretaMilitarUrl(String libretaMilitarUrl) {
		this.libretaMilitarUrl = libretaMilitarUrl;
	}

	public String getFotoUrl() {
		return this.fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	public String getDocumentoIdentificacionUrl() {
		return this.documentoIdentificacionUrl;
	}

	public void setDocumentoIdentificacionUrl(String documentoIdentificacionUrl) {
		this.documentoIdentificacionUrl = documentoIdentificacionUrl;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Boolean getExpuestoPoliticamente() {
		return this.expuestoPoliticamente;
	}

	public void setExpuestoPoliticamente(Boolean expuestoPoliticamente) {
		this.expuestoPoliticamente = expuestoPoliticamente;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getCorreoAlternativo() {
		return this.correoAlternativo;
	}

	public void setCorreoAlternativo(String correoAlternativo) {
		this.correoAlternativo = correoAlternativo;
	}

	public Boolean getFlgActivo() {
		return this.flgActivo;
	}

	public void setFlgActivo(Boolean flgActivo) {
		this.flgActivo = flgActivo;
	}

	public Boolean getFlgEstado() {
		return this.flgEstado;
	}

	public void setFlgEstado(Boolean flgEstado) {
		this.flgEstado = flgEstado;
	}

	public PoblacionEtnica getCodPertenenciaEtnica() {
		return codPertenenciaEtnica;
	}

	public void setCodPertenenciaEtnica(PoblacionEtnica codPertenenciaEtnica) {
		this.codPertenenciaEtnica = codPertenenciaEtnica;
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
		return "Persona [id=" + this.getId() + "]";
	}

	public List<NacionalidadPerfil> getNacionalidadPerfil() {
		return nacionalidadPerfil;
	}

	public void addNacionalidadPerfil(NacionalidadPerfil nacionalidadPerfil) {
		this.nacionalidadPerfil.add(nacionalidadPerfil);
		nacionalidadPerfil.setCodPersona(this);
	}

	public void removeNacionalidadPerfil(NacionalidadPerfil nacionalidadPerfil) {
		this.nacionalidadPerfil.remove(nacionalidadPerfil);
		nacionalidadPerfil.setCodPersona(null);
	}

	@Override
	public EntidadBaseDTO getDTO() {

		EstadoCivilDTO estadoCivil = codEstadoCivil != null ? (EstadoCivilDTO) codEstadoCivil.getDTO() : null;
		ClaseLibretaMilitarDTO claseLibretaMilitar = codClaseLibretaMilitar != null
				? (ClaseLibretaMilitarDTO) codClaseLibretaMilitar.getDTO() : null;
		PaisDTO pais = codPaisNacimiento != null ? (PaisDTO) codPaisNacimiento.getDTO() : null;
		DepartamentoDTO departamento = codDepartamentoNacimiento != null
				? (DepartamentoDTO) codDepartamentoNacimiento.getDTO() : null;
		MunicipioDTO municipio = codMunicipioNacimiento != null ? (MunicipioDTO) codMunicipioNacimiento.getDTO() : null;
		PoblacionEtnicaDTO pertenenciaEtnica = codPertenenciaEtnica != null
				? (PoblacionEtnicaDTO) codPertenenciaEtnica.getDTO() : null;

		return new PersonaDTO(codPersona, codGenero, estadoCivil, pais, departamento, municipio, codFactorRh,
				codTipoIdentificacion, numeroIdentificacion, primerNombre, segundoNombre, primerApellido,
				segundoApellido, resumenProfesional, numeroLibretaMilitar, claseLibretaMilitar, distritoMilitar,
				libretaMilitarUrl, fotoUrl, documentoIdentificacionUrl, fechaNacimiento, expuestoPoliticamente,
				correoElectronico, correoAlternativo, flgActivo, flgEstado, pertenenciaEtnica);
	}

}
