package co.gov.dafp.sigep2.entity.jpa.seguridad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.gov.dafp.sigep2.converter.BooleanToNumberConverter;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.Pais;
import co.gov.dafp.sigep2.entity.seguridad.NacionalidadPerfilDTO;

@Entity(name = "NacionalidadPerfil")
@Table(name = "NACIONALIDAD_PERFIL")
public class NacionalidadPerfil extends EntidadBase implements java.io.Serializable {
	
	private static final long serialVersionUID = -1189658913619345617L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_NACIONALIDAD_PERFIL", insertable = false, updatable = false, unique = true, nullable = false)
	private long codNacionalidadPerfil;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_PERSONA")
	private Persona codPersona;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_PAIS")
	private Pais codPais;
	
	@Column(name = "ADJUNTO_URL", length = 255)
	private String adjuntoUrl;
	
	@Column(name = "FLG_ACTIVO", precision = 22, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private boolean flgActivo = true;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;

	public NacionalidadPerfil() {
	}

	public Long getId() {
		return this.codNacionalidadPerfil;
	}

	public void setId(Long id) {
		this.codNacionalidadPerfil = id;
	}

	public long getCodNacionalidadPerfil() {
		return this.codNacionalidadPerfil;
	}

	public void setCodNacionalidadPerfil(long codNacionalidadPerfil) {
		this.codNacionalidadPerfil = codNacionalidadPerfil;
	}

	public Persona getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Persona codPersona) {
		this.codPersona = codPersona;
	}

	public Pais getCodPais() {
		return codPais;
	}

	public void setCodPais(Pais codPais) {
		this.codPais = codPais;
	}

	public String getAdjuntoUrl() {
		return adjuntoUrl;
	}

	public void setAdjuntoUrl(String adjuntoUrl) {
		this.adjuntoUrl = adjuntoUrl;
	}

	public boolean isFlgActivo() {
		return flgActivo;
	}

	public void setFlgActivo(boolean flgActivo) {
		this.flgActivo = flgActivo;
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
		return "NacionalidadPerfil [codNacionalidadPerfil=" + codNacionalidadPerfil + ", codPersona=" + codPersona
				+ ", codPais=" + codPais + ", adjuntoUrl=" + adjuntoUrl + ", flgActivo=" + flgActivo + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		
		PaisDTO pais = codPais != null ? (PaisDTO)codPais.getDTO() : new PaisDTO();
		
		return new NacionalidadPerfilDTO(codNacionalidadPerfil, codPersona.getId(), pais, adjuntoUrl, flgActivo);
	}
}
