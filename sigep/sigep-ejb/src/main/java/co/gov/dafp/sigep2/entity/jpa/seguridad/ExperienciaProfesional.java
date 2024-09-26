package co.gov.dafp.sigep2.entity.jpa.seguridad;

import java.util.Date;

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
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.gov.dafp.sigep2.converter.BooleanToNumberConverter;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.Departamento;
import co.gov.dafp.sigep2.entity.jpa.comun.Municipio;
import co.gov.dafp.sigep2.entity.jpa.comun.Pais;
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaProfesionalDTO;
import co.gov.dafp.sigep2.view.JornadaLaboral;
import co.gov.dafp.sigep2.view.MotivoRetiro;

@Entity(name = "ExperienciaProfesional")
@Table(name = "EXPERIENCIA_PROFESIONAL")
@SqlResultSetMappings({ @SqlResultSetMapping(name = ExperienciaProfesional.EXPERIENCIA_PROFESIONAL_MAPPING, classes = {
		@ConstructorResult(targetClass = ExperienciaProfesionalDTO.class, columns = {
				@ColumnResult(name = "COD_EXPERIENCIA_PROFESIONAL", type = Long.class),
				@ColumnResult(name = "COD_PERSONA", type = Long.class),
				@ColumnResult(name = "COD_PAIS", type = Long.class),
				@ColumnResult(name = "COD_DEPARTAMENTO", type = Long.class),
				@ColumnResult(name = "COD_MUNICIPIO", type = Long.class),
				@ColumnResult(name = "COD_JORNADA_LABORAL", type = Long.class),
				@ColumnResult(name = "COD_MOTIVO_RETIRO", type = Long.class),
				@ColumnResult(name = "COD_TIPO_ENTIDAD", type = Long.class),
				@ColumnResult(name = "FLG_ENTIDAD_PUBLICA", type = Boolean.class),
				@ColumnResult(name = "INDICATIVO_TELEFONO", type = Long.class),
				@ColumnResult(name = "NOMBRE_ENTIDAD", type = String.class),
				@ColumnResult(name = "DIRECCION_ENTIDAD", type = String.class),
				@ColumnResult(name = "TELEFONO", type = Long.class),
				@ColumnResult(name = "DEPENDENCIA", type = String.class),
				@ColumnResult(name = "CARGO", type = String.class),
				@ColumnResult(name = "FLG_ACTIVO_ENTIDAD", type = Boolean.class),
				@ColumnResult(name = "FECHA_INGRESO", type = Date.class),
				@ColumnResult(name = "FECHA_RETIRO", type = Date.class),
				@ColumnResult(name = "HORAS_PROMEDIO_MES", type = Long.class),
				@ColumnResult(name = "TIEMPO_EXPERIENCIA", type = String.class),
				@ColumnResult(name = "URL_DOCUMENTO_SOPORTE", type = String.class),
				@ColumnResult(name = "NOMBRE_JEFE", type = String.class),
				@ColumnResult(name = "FLG_VALIDA_JEFE", type = Boolean.class),
				@ColumnResult(name = "COD_USAURIO_VALIDA", type = Long.class),
				@ColumnResult(name = "FECHA_VALIDACION", type = Date.class),
				@ColumnResult(name = "JUSTIFICACION_ACTUALIZACION", type = String.class),
				@ColumnResult(name = "FECHA_ACTUALIZACION", type = Date.class) }) }),
	@SqlResultSetMapping(name = ExperienciaProfesional.TABLA_EXPERIENCIA_PROFESIONAL_MAPPING, classes = {
			@ConstructorResult(targetClass = ExperienciaProfesionalDTO.class, columns = {
					@ColumnResult(name = "COD_EXPERIENCIA_PROFESIONAL", type = Long.class),
					@ColumnResult(name = "DESCRIPCION", type = String.class),
					@ColumnResult(name = "NOMBRE_ENTIDAD", type = String.class),
					@ColumnResult(name = "DEPENDENCIA", type = String.class),
					@ColumnResult(name = "CARGO", type = String.class),
					@ColumnResult(name = "FECHA_INGRESO", type = Date.class),
					@ColumnResult(name = "FECHA_RETIRO", type = Date.class),
					@ColumnResult(name = "FLG_VALIDA_JEFE", type = Boolean.class) }) }) })

public class ExperienciaProfesional extends EntidadBase implements java.io.Serializable {
	private static final long serialVersionUID = 3949554687291277379L;

	public static final String EXPERIENCIA_PROFESIONAL_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.ExperienciaProfesional";
	public static final String TABLA_EXPERIENCIA_PROFESIONAL_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.ExperienciaProfesionalDatos";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_EXPERIENCIA_PROFESIONAL", insertable = false, updatable = false, unique = true, nullable = false)
	private Long codExperienciaProfesional;
	
	@Column(name = "COD_PERSONA", nullable = false)
	private Long codPersona;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_PAIS", nullable = false)
	private Pais codPaisEntidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_DEPARTAMENTO", nullable = false)
	private Departamento codDepartamentoEntidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_MUNICIPIO", nullable = false)
	private Municipio codMunicipioEntidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_JORNADA_LABORAL")
	private JornadaLaboral codJornadaLaboral;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_MOTIVO_RETIRO")
	private MotivoRetiro codMotivoRetiro;

	@Column(name = "COD_TIPO_ENTIDAD", nullable = false)
	private Long codTipoEntidad;

	@Column(name = "FLG_ENTIDAD_PUBLICA", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean isEntidadPublica;

	@Column(name = "INDICATIVO_TELEFONO", precision = 22, scale = 0)
	private Long indicativoTelefono;
	
	@Column(name = "NOMBRE_ENTIDAD", nullable = false)
	private String nombreEntidad;

	@Column(name = "DIRECCION_ENTIDAD", length = 126, nullable = false)
	private String direccionEntidad;

	@Column(name = "TELEFONO", precision = 22, scale = 0)
	private Long telefonoEntidad;

	@Column(name = "DEPENDENCIA", length = 50, nullable = false)
	private String dependencia;

	@Column(name = "CARGO", length = 50, nullable = false)
	private String cargoEntidad;

	@Column(name = "FLG_ACTIVO_ENTIDAD", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean isActivaEntidad;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INGRESO", length = 7, nullable = false)
	private Date fechaIngreso;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_RETIRO", length = 7)
	private Date fechaRetiro;

	@Column(name = "HORAS_PROMEDIO_MES", precision = 22, scale = 0)
	private Long horasPromedioMes;

	@Column(name = "TIEMPO_EXPERIENCIA", precision = 22, scale = 0)
	private Long tiempoExperiencia;

	@Column(name = "URL_DOCUMENTO_SOPORTE", length = 50)
	private String urlDocumentoSoporte;

	@Column(name = "NOMBRE_JEFE", length = 50)
	private String nombreJefe;

	@Column(name = "FLG_VALIDA_JEFE", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgValidaJefe;

	@Column(name = "COD_USAURIO_VALIDA", precision = 22, scale = 0)
	private Long codUsuarioValida;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_VALIDACION", length = 7, nullable = false)
	private Date fechaValidacion;
	
	@Column(name = "JUSTIFICACION_ACTUALIZACION", length = 1000)
	private String justificacionActualizacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ACTUALIZACION", length = 7, nullable = false)
	private Date fechaActualizacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;

	public ExperienciaProfesional() {
		super();
	}

	public ExperienciaProfesional(Long codExperienciaProfesional, Long codPersona, Pais codPaisEntidad,
			Departamento codDepartamentoEntidad, Municipio codMunicipioEntidad, JornadaLaboral codJornadaLaboral,
			MotivoRetiro codMotivoRetiro, Long codTipoEntidad, Boolean isEntidadPublica, Long indicativoTelefono,
			String nombreEntidad, String direccionEntidad, Long telefonoEntidad, String dependencia,
			String cargoEntidad, Boolean isActivaEntidad, Date fechaIngreso, Date fechaRetiro, Long horasPromedioMes,
			Long tiempoExperiencia, String urlDocumentoSoporte, String nombreJefe, Boolean flgValidaJefe,
			Long codUsuarioValida, Date fechaValidacion, String justificacionActualizacion, Date fechaActualizacion,
			Date audFechaActualizacion, Long audCodUsuario, Long audCodRol, String audAccion) {
		super();
		this.codExperienciaProfesional = codExperienciaProfesional;
		this.codPersona = codPersona;
		this.codPaisEntidad = codPaisEntidad;
		this.codDepartamentoEntidad = codDepartamentoEntidad;
		this.codMunicipioEntidad = codMunicipioEntidad;
		this.codJornadaLaboral = codJornadaLaboral;
		this.codMotivoRetiro = codMotivoRetiro;
		this.codTipoEntidad = codTipoEntidad;
		this.isEntidadPublica = isEntidadPublica;
		this.indicativoTelefono = indicativoTelefono;
		this.nombreEntidad = nombreEntidad;
		this.direccionEntidad = direccionEntidad;
		this.telefonoEntidad = telefonoEntidad;
		this.dependencia = dependencia;
		this.cargoEntidad = cargoEntidad;
		this.isActivaEntidad = isActivaEntidad;
		this.fechaIngreso = fechaIngreso;
		this.fechaRetiro = fechaRetiro;
		this.horasPromedioMes = horasPromedioMes;
		this.tiempoExperiencia = tiempoExperiencia;
		this.urlDocumentoSoporte = urlDocumentoSoporte;
		this.nombreJefe = nombreJefe;
		this.flgValidaJefe = flgValidaJefe;
		this.codUsuarioValida = codUsuarioValida;
		this.fechaValidacion = fechaValidacion;
		this.justificacionActualizacion = justificacionActualizacion;
		this.fechaActualizacion = fechaActualizacion;
		this.audFechaActualizacion = audFechaActualizacion;
		this.audCodUsuario = audCodUsuario;
		this.audCodRol = audCodRol;
		this.audAccion = Long.valueOf(audAccion);
	}

	public Long getId() {
		return this.codExperienciaProfesional;
	}

	public void setId(Long id) {
		this.codExperienciaProfesional = id;
	}

	public Long getCodExperienciaProfesional() {
		return codExperienciaProfesional;
	}

	public void setCodExperienciaProfesional(Long codExperienciaProfesional) {
		this.codExperienciaProfesional = codExperienciaProfesional;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public Pais getCodPaisEntidad() {
		return codPaisEntidad;
	}

	public void setCodPaisEntidad(Pais codPaisEntidad) {
		this.codPaisEntidad = codPaisEntidad;
	}

	public Departamento getCodDepartamentoEntidad() {
		return codDepartamentoEntidad;
	}

	public void setCodDepartamentoEntidad(Departamento codDepartamentoEntidad) {
		this.codDepartamentoEntidad = codDepartamentoEntidad;
	}

	public Municipio getCodMunicipioEntidad() {
		return codMunicipioEntidad;
	}

	public void setCodMunicipioEntidad(Municipio codMunicipioEntidad) {
		this.codMunicipioEntidad = codMunicipioEntidad;
	}

	public JornadaLaboral getCodJornadaLaboral() {
		return codJornadaLaboral;
	}

	public void setCodJornadaLaboral(JornadaLaboral codJornadaLaboral) {
		this.codJornadaLaboral = codJornadaLaboral;
	}

	public MotivoRetiro getCodMotivoRetiro() {
		return codMotivoRetiro;
	}

	public void setCodMotivoRetiro(MotivoRetiro codMotivoRetiro) {
		this.codMotivoRetiro = codMotivoRetiro;
	}

	public Long getCodTipoEntidad() {
		return codTipoEntidad;
	}

	public void setCodTipoEntidad(Long codTipoEntidad) {
		this.codTipoEntidad = codTipoEntidad;
	}

	public Boolean getIsEntidadPublica() {
		return isEntidadPublica;
	}

	public void setIsEntidadPublica(Boolean isEntidadPublica) {
		this.isEntidadPublica = isEntidadPublica;
	}

	public Long getIndicativoTelefono() {
		return indicativoTelefono;
	}

	public void setIndicativoTelefono(Long indicativoTelefono) {
		this.indicativoTelefono = indicativoTelefono;
	}

	public String getnombreEntidad() {
		return nombreEntidad;
	}

	public void setnombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getDireccionEntidad() {
		return direccionEntidad;
	}

	public void setDireccionEntidad(String direccionEntidad) {
		this.direccionEntidad = direccionEntidad;
	}

	public Long getTelefonoEntidad() {
		return telefonoEntidad;
	}

	public void setTelefonoEntidad(Long telefonoEntidad) {
		this.telefonoEntidad = telefonoEntidad;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getCargoEntidad() {
		return cargoEntidad;
	}

	public void setCargoEntidad(String cargoEntidad) {
		this.cargoEntidad = cargoEntidad;
	}

	public Boolean getIsActivaEntidad() {
		return isActivaEntidad;
	}

	public void setIsActivaEntidad(Boolean isActivaEntidad) {
		this.isActivaEntidad = isActivaEntidad;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaRetiro() {
		return fechaRetiro;
	}

	public void setFechaRetiro(Date fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

	public Long getHorasPromedioMes() {
		return horasPromedioMes;
	}

	public void setHorasPromedioMes(Long horasPromedioMes) {
		this.horasPromedioMes = horasPromedioMes;
	}

	public Long getTiempoExperiencia() {
		return tiempoExperiencia;
	}

	public void setTiempoExperiencia(Long tiempoExperiencia) {
		this.tiempoExperiencia = tiempoExperiencia;
	}

	public String getUrlDocumentoSoporte() {
		return urlDocumentoSoporte;
	}

	public void setUrlDocumentoSoporte(String urlDocumentoSoporte) {
		this.urlDocumentoSoporte = urlDocumentoSoporte;
	}

	public String getNombreJefe() {
		return nombreJefe;
	}

	public void setNombreJefe(String nombreJefe) {
		this.nombreJefe = nombreJefe;
	}

	public Boolean getFlgValidaJefe() {
		return flgValidaJefe;
	}

	public void setFlgValidaJefe(Boolean flgValidaJefe) {
		this.flgValidaJefe = flgValidaJefe;
	}

	public Long getCodUsuarioValida() {
		return codUsuarioValida;
	}

	public void setCodUsuarioValida(Long codUsuarioValida) {
		this.codUsuarioValida = codUsuarioValida;
	}

	public Date getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(Date fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getJustificacionActualizacion() {
		return justificacionActualizacion;
	}

	public void setJustificacionActualizacion(String justificacionActualizacion) {
		this.justificacionActualizacion = justificacionActualizacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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
		return "ExperienciaProfesional [codExperienciaProfesional=" + codExperienciaProfesional + ", codPersona="
				+ codPersona + ", codPaisEntidad=" + codPaisEntidad + ", codDepartamentoEntidad="
				+ codDepartamentoEntidad + ", codMunicipioEntidad=" + codMunicipioEntidad + ", codJornadaLaboral="
				+ codJornadaLaboral + ", codMotivoRetiro=" + codMotivoRetiro + ", codTipoEntidad=" + codTipoEntidad
				+ ", isEntidadPublica=" + isEntidadPublica + ", indicativoTelefono=" + indicativoTelefono
				+ ", nombreEntidad=" + nombreEntidad + ", direccionEntidad=" + direccionEntidad + ", telefonoEntidad="
				+ telefonoEntidad + ", dependencia=" + dependencia + ", cargoEntidad=" + cargoEntidad
				+ ", isActivaEntidad=" + isActivaEntidad + ", fechaIngreso=" + fechaIngreso + ", fechaRetiro="
				+ fechaRetiro + ", horasPromedioMes=" + horasPromedioMes + ", tiempoExperiencia=" + tiempoExperiencia
				+ ", urlDocumentoSoporte=" + urlDocumentoSoporte + ", nombreJefe=" + nombreJefe + ", flgValidaJefe="
				+ flgValidaJefe + ", codUsuarioValida=" + codUsuarioValida + ", fechaValidacion=" + fechaValidacion
				+ ", justificacionActualizacion=" + justificacionActualizacion + ", fechaActualizacion="
				+ fechaActualizacion + ", audFechaActualizacion=" + audFechaActualizacion + ", audCodUsuario="
				+ audCodUsuario + ", audCodRol=" + audCodRol + ", audAccion=" + audAccion + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new ExperienciaProfesionalDTO();
	}

	

}
