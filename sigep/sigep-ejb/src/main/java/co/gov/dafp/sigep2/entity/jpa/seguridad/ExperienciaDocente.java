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
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaDocenteDTO;
import co.gov.dafp.sigep2.view.AreaConocimiento;
import co.gov.dafp.sigep2.view.NivelEducativo;

@Entity(name = "ExperienciaDocente")
@Table(name = "EXPERIENCIA_DOCENTE")
@SqlResultSetMappings({
	@SqlResultSetMapping(name = ExperienciaDocente.EXPERIENCIA_DOCENTE_MAPPING, classes = {
		@ConstructorResult(targetClass = ExperienciaDocenteDTO.class, columns = {
				@ColumnResult(name = "COD_EXPERIENCIA_DOCENTE", type = Long.class),
				@ColumnResult(name = "COD_PERSONA", type = Long.class),
				@ColumnResult(name = "COD_PAIS", type = Long.class),
				@ColumnResult(name = "COD_DEPARTAMENTO", type = Long.class),
				@ColumnResult(name = "COD_CIUDAD", type = Long.class),
				@ColumnResult(name = "COD_NIVEL_EDUCATIVO", type = Long.class),
				@ColumnResult(name = "COD_AREA_CONOCIMIENTO", type = Long.class),
				@ColumnResult(name = "COD_INSTITUCION", type = Long.class),
				@ColumnResult(name = "FECHA_INGRESO", type = Date.class),
				@ColumnResult(name = "FLG_ACTUALMENTE", type = Boolean.class),
				@ColumnResult(name = "FECHA_FINALIZACION", type = Date.class),
				@ColumnResult(name = "DIRECCION", type = String.class),
				@ColumnResult(name = "TELEFONO", type = String.class),
				@ColumnResult(name = "HORAS_SEMANA", type = Long.class),
				@ColumnResult(name = "MATERIA_IMPARTIDA", type = String.class),
				@ColumnResult(name = "URL_DOCUMENTO_SOPORTE", type = String.class),
				@ColumnResult(name = "FLG_VERIFICADO", type = Boolean.class),
				@ColumnResult(name = "COD_USUARIO_VERIFICA", type = Long.class),
				@ColumnResult(name = "FECHA_VERIFICACION", type = Date.class),
				@ColumnResult(name = "FLG_ENTIDAD_PUBLICA", type = Long.class),
				@ColumnResult(name = "NOMBRE_INSTITUCION", type = String.class),
				@ColumnResult(name = "INDICATIVO_TELEFONO", type = String.class),
				@ColumnResult(name = "TIEMPO_EXPERIENCIA_DOCENTE", type = String.class),
				@ColumnResult(name = "FLG_ACTIVO", type = Boolean.class),
				@ColumnResult(name = "COD_TIPO_ZONA", type = Long.class)
				 }) }),
	@SqlResultSetMapping(name = ExperienciaDocente.DATOS_TABLA_EXPERIENCIA_DOCENTE_MAPPING, classes = {
			@ConstructorResult(targetClass = ExperienciaDocenteDTO.class, columns = {
					@ColumnResult(name = "COD_EXPERIENCIA_DOCENTE", type = Long.class),
					@ColumnResult(name = "NOMBRE", type = String.class),
					@ColumnResult(name = "NIVEL_EDUCATIVO", type = String.class),
					@ColumnResult(name = "AREA_CONOCIMIENTO", type = String.class),
					@ColumnResult(name = "PAISTABLA", type = String.class),
					@ColumnResult(name = "FECHA_INGRESO", type = Date.class),
					@ColumnResult(name = "FECHA_FINALIZACION", type = Date.class),
					@ColumnResult(name = "VERIFICADO", type = Boolean.class) }) 
					}) })

public class ExperienciaDocente extends EntidadBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String EXPERIENCIA_DOCENTE_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.ExperienciaDocente";
	public static final String DATOS_TABLA_EXPERIENCIA_DOCENTE_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.ExperienciaDocenteDatos";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_EXPERIENCIA_DOCENTE", insertable = false, updatable = false, unique = true, nullable = false)
	private Long codExperienciaDocente;
	
	@Column(name = "COD_PERSONA", nullable = false)
	private Long codPersona;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_PAIS", nullable = false)
	private Pais codPais;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_DEPARTAMENTO")
	private Departamento codDepartamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_CIUDAD", nullable = false)
	private Municipio codCiudad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_NIVEL_EDUCATIVO", nullable = false)
	private NivelEducativo nivelEducativo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_AREA_CONOCIMIENTO", nullable = false)
	private AreaConocimiento areaConocimiento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_INSTITUCION")
	private InstitucionEducativa codInstitucion;
	
	@Column(name = "NOMBRE_INSTITUCION", length = 255, nullable = false)
	private String nombreInstitucion;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INGRESO", length = 7)
	private Date fechaIngreso;
	
	@Column(name = "FLG_ACTUALMENTE", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean isActivaActualmente;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_FINALIZACION", length = 7)
	private Date fechaFinalizacion;
	
	@Column(name = "DIRECCION", length = 255, nullable = false)
	private String direccion;

	@Column(name = "TELEFONO", length = 20)
	private String telefono;

	@Column(name = "HORAS_SEMANA", precision = 2, scale = 0)
	private Long horasSemana;
	
	@Column(name = "MATERIA_IMPARTIDA", length = 256)
	private String materiaImpartida;
	
	@Column(name = "INDICATIVO_TELEFONO", length = 255)
	private String indicativoTelefono;

	@Column(name = "COD_TIPO_ZONA", precision = 6, scale = 0)
	private Long codTipoZona;
	
	@Column(name = "TIEMPO_EXPERIENCIA_DOCENTE", length = 255)
	private String tiempoExperiencia;
	
	@Column(name = "FLG_VERIFICADO", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgVerificado;
	

	@Column(name = "FLG_ACTIVO", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgActivo;
	
	@Column(name = "COD_USUARIO_VERIFICA", nullable = false)
	private Long codigoUsuarioVerifica;
	
	@Column(name = "FLG_ENTIDAD_PUBLICA", nullable = false )
	private Long flgEntidadPublica;
	
	@Column(name = "URL_DOCUMENTO_SOPORTE", length = 255)
	private String urlDocumentoSoporte;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_VERIFICACION", length = 7)
	private Date fechaVerificacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 20, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 6, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;
	
	
	public ExperienciaDocente() {
		
	}
	
	public Long getId() {
		return this.codExperienciaDocente;
	}

	public void setId(Long id) {
		this.codExperienciaDocente = id;
	}

	public InstitucionEducativa getCodInstitucion() {
		return codInstitucion;
	}

	public void setCodInstitucion(InstitucionEducativa codInstitucion) {
		this.codInstitucion = codInstitucion;
	}

	public String getIndicativoTelefono() {
		return indicativoTelefono;
	}

	public void setIndicativoTelefono(String indicativoTelefono) {
		this.indicativoTelefono = indicativoTelefono;
	}

	public Long getCodTipoZona() {
		return codTipoZona;
	}

	public void setCodTipoZona(Long codTipoZona) {
		this.codTipoZona = codTipoZona;
	}

	public String getTiempoExperiencia() {
		return tiempoExperiencia;
	}

	public void setTiempoExperiencia(String tiempoExperiencia) {
		this.tiempoExperiencia = tiempoExperiencia;
	}

	public Boolean getFlgActivo() {
		return flgActivo;
	}

	public void setFlgActivo(Boolean flgActivo) {
		this.flgActivo = flgActivo;
	}

	public Long getCodExperienciaDocente() {
		return codExperienciaDocente;
	}

	public void setCodExperienciaDocente(Long codExperienciaDocente) {
		this.codExperienciaDocente = codExperienciaDocente;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}


	public Pais getCodPais() {
		return codPais;
	}

	public void setCodPais(Pais codPais) {
		this.codPais = codPais;
	}

	public Departamento getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(Departamento codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public Municipio getCodCiudad() {
		return codCiudad;
	}

	public void setCodCiudad(Municipio codCiudad) {
		this.codCiudad = codCiudad;
	}

	public NivelEducativo getNivelEducativo() {
		return nivelEducativo;
	}

	public void setNivelEducativo(NivelEducativo nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public AreaConocimiento getAreaConocimiento() {
		return areaConocimiento;
	}

	public void setAreaConocimiento(AreaConocimiento areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Boolean getIsActivaActualmente() {
		return isActivaActualmente;
	}

	public void setIsActivaActualmente(Boolean isActivaActualmente) {
		this.isActivaActualmente = isActivaActualmente;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Long getHorasSemana() {
		return horasSemana;
	}

	public void setHorasSemana(Long horasSemana) {
		this.horasSemana = horasSemana;
	}

	public String getMateriaImpartida() {
		return materiaImpartida;
	}

	public void setMateriaImpartida(String materiaImpartida) {
		this.materiaImpartida = materiaImpartida;
	}

	public String getUrlDocumentoSoporte() {
		return urlDocumentoSoporte;
	}

	public void setUrlDocumentoSoporte(String urlDocumentoSoporte) {
		this.urlDocumentoSoporte = urlDocumentoSoporte;
	}

	public Boolean getFlgVerificado() {
		return flgVerificado;
	}
	
	public Long getFlgEntidadPublica() {
		return flgEntidadPublica;
	}

	public void setFlgEntidadPublica(Long flgEntidadPublica) {
		this.flgEntidadPublica = flgEntidadPublica;
	}

	public void setFlgVerificado(Boolean flgVerificado) {
		this.flgVerificado = flgVerificado;
	}

	public Long getCodigoUsuarioVerifica() {
		return codigoUsuarioVerifica;
	}

	public void setCodigoUsuarioVerifica(Long codigoUsuarioVerifica) {
		this.codigoUsuarioVerifica = codigoUsuarioVerifica;
	}

	public Date getFechaVerificacion() {
		return fechaVerificacion;
	}

	public void setFechaVerificacion(Date fechaVerificacion) {
		this.fechaVerificacion = fechaVerificacion;
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
		return "ExperienciaDocente [getId()=" + getId() + ", getCodInstitucion()=" + getCodInstitucion()
				+ ", getIndicativoTelefono()=" + getIndicativoTelefono() + ", getCodTipoZona()=" + getCodTipoZona()
				+ ", getTiempoExperiencia()=" + getTiempoExperiencia() + ", getFlgActivo()=" + getFlgActivo()
				+ ", getCodExperienciaDocente()=" + getCodExperienciaDocente() + ", getCodPersona()=" + getCodPersona()
				+ ", getCodPais()=" + getCodPais() + ", getCodDepartamento()=" + getCodDepartamento()
				+ ", getCodCiudad()=" + getCodCiudad() + ", getNivelEducativo()=" + getNivelEducativo()
				+ ", getAreaConocimiento()=" + getAreaConocimiento() + ", getNombreInstitucion()="
				+ getNombreInstitucion() + ", getFechaIngreso()=" + getFechaIngreso() + ", getIsActivaActualmente()="
				+ getIsActivaActualmente() + ", getFechaFinalizacion()=" + getFechaFinalizacion() + ", getDireccion()="
				+ getDireccion() + ", getTelefono()=" + getTelefono() + ", getHorasSemana()=" + getHorasSemana()
				+ ", getMateriaImpartida()=" + getMateriaImpartida() + ", getUrlDocumentoSoporte()="
				+ getUrlDocumentoSoporte() + ", getFlgVerificado()=" + getFlgVerificado() + ", getFlgEntidadPublica()="
				+ getFlgEntidadPublica() + ", getCodigoUsuarioVerifica()=" + getCodigoUsuarioVerifica()
				+ ", getFechaVerificacion()=" + getFechaVerificacion() + ", getAudFechaActualizacion()="
				+ getAudFechaActualizacion() + ", getAudCodUsuario()=" + getAudCodUsuario() + ", getAudCodRol()="
				+ getAudCodRol() + ", getAudAccion()=" + getAudAccion() + ", getDTO()=" + getDTO() + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new ExperienciaDocenteDTO();
	}

}
