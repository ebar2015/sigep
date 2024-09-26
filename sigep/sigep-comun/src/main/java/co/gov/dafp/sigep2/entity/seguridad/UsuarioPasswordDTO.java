package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

/**
 * The persistent class for the USUARIO database table.
 *
 */
public class UsuarioPasswordDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = 1958451851710958540L;

	public UsuarioPasswordDTO(long id, String nombreUsuario, String nombrePersona, String correoElectronico, String correoAlternativo,
			String contrasena, boolean flgBloqueado, Date fechaVence, String ticket, Boolean flgEsInterno,
			Boolean flgEstado) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario.toUpperCase();
		this.nombrePersona = nombrePersona;
		this.correoElectronico = correoElectronico;
		this.correoAlternativo = correoAlternativo;
		this.contrasena = contrasena;
		this.flgBloqueado = flgBloqueado;
		this.fechaVence = fechaVence;
		this.ticket = ticket;
		this.flgEsInterno = flgEsInterno;
		this.flgEstado = flgEstado;
	}

	private long id;

	private String nombreUsuario;

	private String nombrePersona;

	private String correoElectronico;
	
	private String correoAlternativo;

	private String contrasena;

	private boolean flgBloqueado;

	private Date fechaVence;

	private String ticket;

	private Boolean flgEsInterno = Boolean.FALSE;

	private Boolean flgEstado = Boolean.TRUE;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario.toUpperCase();
	}

	public String getNombrePersona() {
		return nombrePersona;
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

	public String getCorreoAlternativo() {
		return correoAlternativo;
	}

	public void setCorreoAlternativo(String correoAlternativo) {
		this.correoAlternativo = correoAlternativo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public boolean isFlgBloqueado() {
		return flgBloqueado;
	}

	public void setFlgBloqueado(boolean flgBloqueado) {
		this.flgBloqueado = flgBloqueado;
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

	public Boolean getFlgEsInterno() {
		return flgEsInterno;
	}

	public void setFlgEsInterno(Boolean flgEsInterno) {
		this.flgEsInterno = flgEsInterno;
	}

	public Boolean getFlgEstado() {
		return flgEstado;
	}

	public void setFlgEstado(Boolean flgEstado) {
		this.flgEstado = flgEstado;
	}

	@Override
	public String toString() {
		return "UsuarioPasswordDTO [id=" + id + ", nombreUsuario=" + nombreUsuario + ", nombrePersona=" + nombrePersona
				+ ", correoElectronico=" + correoElectronico + ", correoAlternativo=" + correoAlternativo
				+ ", contrasena=" + contrasena + ", flgBloqueado=" + flgBloqueado + ", fechaVence=" + fechaVence
				+ ", ticket=" + ticket + ", flgEsInterno=" + flgEsInterno + ", flgEstado=" + flgEstado + "]";
	}

	public UsuarioDTO getUsuarioDTO() {
		return new UsuarioDTO(id, null, nombreUsuario, nombrePersona, contrasena, null, null, flgBloqueado, false, null,
				correoElectronico, fechaVence, null, null, null, ticket, flgEsInterno, flgEstado);
	}
}
