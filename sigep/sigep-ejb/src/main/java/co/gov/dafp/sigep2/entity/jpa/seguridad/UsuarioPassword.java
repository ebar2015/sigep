package co.gov.dafp.sigep2.entity.jpa.seguridad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.converter.BooleanToNumberConverter;
import co.gov.dafp.sigep2.converter.EncryptCaseConverter;
import co.gov.dafp.sigep2.converter.UpperCaseConverter;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.StringEncrypt;

/**
 * The persistent class for the USUARIO database table.
 *
 */
@Entity(name = "UsuarioPassword")
@Table(name = "USUARIO")
@SqlResultSetMappings({ @SqlResultSetMapping(name = UsuarioPassword.USUARIO_MAPPING, classes = {
		@ConstructorResult(targetClass = UsuarioPasswordDTO.class, columns = {
				@ColumnResult(name = "COD_USUARIO", type = Long.class),
				@ColumnResult(name = "NOMBRE_USUARIO", type = String.class),
				@ColumnResult(name = "NOMBRE_PERSONA", type = String.class),
				@ColumnResult(name = "CORREO_ELECTRONICO", type = String.class),
				@ColumnResult(name = "CORREO_ALTERNATIVO", type = String.class),
				@ColumnResult(name = "CONTRASENA", type = String.class),
				@ColumnResult(name = "FLG_BLOQUEADO", type = Boolean.class),
				@ColumnResult(name = "FECHA_VENCE", type = Date.class),
				@ColumnResult(name = "TICKET", type = String.class),
				@ColumnResult(name = "FLG_ES_INTERNO", type = Boolean.class),
				@ColumnResult(name = "FLG_ESTADO", type = Boolean.class) }) }) })
public class UsuarioPassword extends EntidadBase implements Serializable {
	private static final long serialVersionUID = 1958451851710958540L;

	public static final String USUARIO_MAPPING = "co.gov.dafp.sigep2.entity.seguridad.mapping.UsuarioPassword";

	@Id
	@Column(name = "COD_USUARIO", unique = true, nullable = false, precision = 22, scale = 0)
	private long id;

	@Column(name = "FLG_BLOQUEADO", insertable = false)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean bloqueado = Boolean.FALSE;

	@Column(name = "NOMBRE_USUARIO", insertable = false)
	@Convert(converter = UpperCaseConverter.class)
	private String login;

	@Column(name = "CONTRASENA", insertable = false)
	@Convert(converter = EncryptCaseConverter.class)
	private String passwd;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_VENCE")
	private Date fechaVence;

	@Column(name = "TICKET", precision = 200, insertable = false)
	private String ticket;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = Persona.class)
	@JoinColumn(name = "COD_PERSONA", insertable = false, updatable = false)
	private Persona persona;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public String getNombreUsuario() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCorreoElectronico() {
		return this.persona.getCorreoElectronico();
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.persona.setCorreoElectronico(correoElectronico);
	}
	
	public String getCorreoAlternativo() {
		return this.persona.getCorreoAlternativo();
	}
	
	public void setCorreoAlternativo(String correoAlternativo) {
		this.persona.setCorreoAlternativo(correoAlternativo);
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Date getFechaVence() {
		return fechaVence;
	}

	public void setFechaVence(Date fechaVence) {
		this.fechaVence = fechaVence;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
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
		return "UsuarioPassword [id=" + id + ", bloqueado=" + bloqueado + ", login=" + login + ", passwd="
				+ StringEncrypt.encrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(), passwd)
				+ ", fechaVence=" + DateUtils.formatearACadena(fechaVence, DateUtils.FECHA_FORMATO_VO) + ", ticket="
				+ ticket + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}
}
