package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.converter.CapitalCaseConverter;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.util.StringEncrypt;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 * The persistent class for the USUARIO database table.
 *
 */
public class UsuarioDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = 9079321647521240196L;

	public static final String USUARIO_SUPER_ADMINISTRADOR = "superadmin";
	public static final String USUARIO_SISTEMA = "sistema";

	private long id;

	private Long codPersona;

	private String nombreUsuario;

	private String contrasena;
	
	private long codTipoIdentificacion;

	private String numeroIdentificacion;

	private String nombrePersona;

	private String correoElectronico;

	private String correoElectronicoAlterno;

	private Long numeroReintentos;

	private Long numeroSesionesActivas;

	private boolean flgBloqueado;

	private boolean flgAceptoHabeasData;

	private Date fechaAceptoHabeas;

	private Date fechaVence;

	private Date fechaUltimoIngreso;

	private String ipAutorizada;

	private String macAutorizada;

	private String ticket;

	private boolean flgEsInterno;

	private boolean flgEstado;

	private List<UsuarioEntidadDTO> usuarioEntidades;

	private List<UsuarioRolDTO> roles;

	private Long persona;
	
	private long codRol;

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(long id, Long codPersona, String nombreUsuario, String nombrePersona, String contrasena,
			Long numeroReintentos, Long numeroSesionesActivas, Boolean flgBloqueado, Boolean flgAceptoHabeasData,
			Date fechaAceptoHabeas, String correoElectronico, Date fechaVence, Date fechaUltimoIngreso,
			String ipAutorizada, String macAutorizada, String ticket, Boolean flgEsInterno, Boolean flgEstado) {
		super();
		this.id = id;
		this.codPersona = codPersona;
		this.nombreUsuario = nombreUsuario.toUpperCase();
		try {
			this.contrasena = StringEncrypt.decrypt(ConfigurationBundleConstants.key(),
					ConfigurationBundleConstants.iv(), contrasena);
		} catch (SIGEP2SistemaException e) {
			this.contrasena = contrasena;
		}
		this.numeroIdentificacion = nombreUsuario;
		this.nombrePersona = nombrePersona;
		this.correoElectronico = correoElectronico;
		this.numeroReintentos = numeroReintentos;
		this.numeroSesionesActivas = numeroSesionesActivas;
		this.flgBloqueado = flgBloqueado;
		this.flgAceptoHabeasData = flgAceptoHabeasData;
		this.fechaAceptoHabeas = fechaAceptoHabeas;
		this.fechaVence = fechaVence;
		this.fechaUltimoIngreso = fechaUltimoIngreso;
		this.ipAutorizada = ipAutorizada;
		this.macAutorizada = macAutorizada;
		this.ticket = ticket;
		this.flgEsInterno = flgEsInterno;
		this.flgEstado = flgEstado != null ? flgEstado : false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario.toUpperCase();
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena.trim();
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getNombrePersona() {
		return CapitalCaseConverter.convert(nombrePersona);
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getCorreoElectronicoAlterno() {
		return correoElectronicoAlterno;
	}

	public void setCorreoElectronicoAlterno(String correoElectronicoAlterno) {
		this.correoElectronicoAlterno = correoElectronicoAlterno;
	}

	public Long getNumeroReintentos() {
		return numeroReintentos;
	}

	public void setNumeroReintentos(Long numeroReintentos) {
		this.numeroReintentos = numeroReintentos;
	}

	public Long getNumeroSesionesActivas() {
		return numeroSesionesActivas;
	}

	public void setNumeroSesionesActivas(Long numeroSesionesActivas) {
		this.numeroSesionesActivas = numeroSesionesActivas;
	}

	public boolean isFlgBloqueado() {
		return flgBloqueado;
	}

	public void setFlgBloqueado(boolean flgBloqueado) {
		this.flgBloqueado = flgBloqueado;
	}

	public boolean isFlgAceptoHabeasData() {
		return flgAceptoHabeasData;
	}

	public void setFlgAceptoHabeasData(boolean flgAceptoHabeasData) {
		this.flgAceptoHabeasData = flgAceptoHabeasData;
	}

	public Date getFechaAceptoHabeas() {
		return fechaAceptoHabeas;
	}

	public void setFechaAceptoHabeas(Date fechaAceptoHabeas) {
		this.fechaAceptoHabeas = fechaAceptoHabeas;
	}

	public Date getFechaVence() {
		return fechaVence;
	}

	public void setFechaVence(Date fechaVence) {
		this.fechaVence = fechaVence;
	}

	public Date getFechaUltimoIngreso() {
		return fechaUltimoIngreso;
	}

	public void setFechaUltimoIngreso(Date fechaUltimoIngreso) {
		this.fechaUltimoIngreso = fechaUltimoIngreso;
	}

	public String getIpAutorizada() {
		return ipAutorizada;
	}

	public void setIpAutorizada(String ipAutorizada) {
		this.ipAutorizada = ipAutorizada;
	}

	public String getMacAutorizada() {
		return macAutorizada;
	}

	public void setMacAutorizada(String macAutorizada) {
		this.macAutorizada = macAutorizada;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public boolean isFlgEsInterno() {
		return flgEsInterno;
	}

	public void setFlgEsInterno(boolean flgEsInterno) {
		this.flgEsInterno = flgEsInterno;
	}

	public boolean isFlgEstado() {
		return flgEstado;
	}

	public void setFlgEstado(boolean flgEstado) {
		this.flgEstado = flgEstado;
	}

	public List<UsuarioEntidadDTO> getUsuarioEntidades() {
		return usuarioEntidades;
	}

	public void setUsuarioEntidades(List<UsuarioEntidadDTO> usuarioEntidades) {
		this.usuarioEntidades = usuarioEntidades;
	}

	public List<UsuarioRolDTO> getRoles() {
		if (roles == null) {
			return new LinkedList<>();
		}
		return roles;
	}

	public void setRoles(List<UsuarioRolDTO> roles) {
		this.roles = roles;
	}

	public Long getPersona() {
		return persona;
	}

	public void setPersona(Long persona) {
		this.persona = persona;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", codPersona=" + codPersona + ", nombreUsuario=" + nombreUsuario
				+ ", contrasena=" + contrasena + ", numeroIdentificacion=" + numeroIdentificacion + ", nombrePersona="
				+ nombrePersona + ", correoElectronico=" + correoElectronico + ", correoElectronicoAlterno="
				+ correoElectronicoAlterno + ", numeroReintentos=" + numeroReintentos + ", numeroSesionesActivas="
				+ numeroSesionesActivas + ", flgBloqueado=" + flgBloqueado + ", flgAceptoHabeasData="
				+ flgAceptoHabeasData + ", fechaAceptoHabeas=" + fechaAceptoHabeas + ", fechaVence=" + fechaVence
				+ ", fechaUltimoIngreso=" + fechaUltimoIngreso + ", ipAutorizada=" + ipAutorizada + ", macAutorizada="
				+ macAutorizada + ", ticket=" + ticket + ", flgEsInterno=" + flgEsInterno + ", flgEstado=" + flgEstado
				+ ", usuarioEntidades=" + usuarioEntidades + ", roles=" + roles + ", persona=" + persona + "]";
	}

	/**
	 * @return the codTipoIdentificacion
	 */
	public long getCodTipoIdentificacion() {
		return codTipoIdentificacion;
	}

	/**
	 * @param codTipoIdentificacion the codTipoIdentificacion to set
	 */
	public void setCodTipoIdentificacion(long codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}

	public long getCodRol() {
		return codRol;
	}

	public void setCodRol(long codRol) {
		this.codRol = codRol;
	}
}
