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
import co.gov.dafp.sigep2.entity.seguridad.DatoAdicionalDTO;
import co.gov.dafp.sigep2.entity.view.CabezaFamiliaDTO;
import co.gov.dafp.sigep2.entity.view.DesplazamientoDTO;
import co.gov.dafp.sigep2.entity.view.OrientacionSexualDTO;
import co.gov.dafp.sigep2.entity.view.TipoDiscapacidadDTO;
import co.gov.dafp.sigep2.view.CabezaFamilia;
import co.gov.dafp.sigep2.view.Desplazamiento;
import co.gov.dafp.sigep2.view.OrientacionSexual;
import co.gov.dafp.sigep2.view.TipoDiscapacidad;

@Entity(name = "DatoAdicional")
@Table(name = "DATO_ADICIONAL")
public class DatoAdicional extends EntidadBase implements java.io.Serializable {	
	private static final long serialVersionUID = 5805070928150957024L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_DATO_ADICIONAL", insertable = false, updatable = false, unique = true, nullable = false)
	private long codDatoAdicional;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_PERSONA")
	private Persona codPersona;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_CABEZA_HOGAR", nullable = true)
	private CabezaFamilia codCabezaHogar;
	
	@Column(name = "FLG_PREPENSIONADO", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgPrepensionado;
	
	@Column(name = "FLG_DISCAPACIDAD", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgDiscapacidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_TIPO_DISCAPACIDAD", nullable = true)
	private TipoDiscapacidad codTipoDiscapacidad;
	
	@Column(name = "FLG_VICTIMA_CONFLICTO", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgVictimaConflicto;
	
	@Column(name = "URL_FACEBOOK", nullable = false, length = 255)
	private String urlFacebook;
	
	@Column(name = "URL_PAGINA_WEB", nullable = true, length = 255)
	private String urlPaginaWeb;
	
	@Column(name = "URL_OTRA_RED_SOCIAL", nullable = true, length = 255)
	private String urlOtraRedSocial;
	
	@Column(name = "OTRA_ORIENTACION_SEXUAL", nullable = true, length = 255)
	private String otraOrientacionSexual;
	
	@Column(name = "URL_SNAPCHAT", nullable = true, length = 255)
	private String urlSnapchat;
	
	@Column(name = "USUARIO_SKYPE", nullable = true, length = 255)
	private String usuarioSkype;
	
	@Column(name = "COD_ORIENTACION_SEXUAL", nullable = true)
	private OrientacionSexual codOrientacionSexual;
	
	@Column(name = "URL_TWITTER", nullable = false, length = 255)
	private String urlTwitter;
	
	@Column(name = "URL_LINKEDIN", nullable = false, length = 255)
	private String urlLinkedin;
	
	@Column(name = "URL_INSTAGRAM", nullable = false, length = 255)
	private String urlInstagram;
	
	@Column(name = "COD_VICTIMA_DESPLAZAMIENTO", precision = 1, scale = 0)
	private Desplazamiento codVictimaDesplazamiento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;

	public DatoAdicional() {
	}

	public Long getId() {
		return this.codDatoAdicional;
	}

	public void setId(Long id) {
		this.codDatoAdicional = id;
	}

	public long getCodDatoAdicional() {
		return this.codDatoAdicional;
	}

	public void setCodDatoAdicional(long codDatoAdicional) {
		this.codDatoAdicional = codDatoAdicional;
	}

	public Persona getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Persona codPersona) {
		this.codPersona = codPersona;
	}

	public CabezaFamilia getCodCabezaHogar() {
		return codCabezaHogar;
	}

	public void setCodCabezaHogar(CabezaFamilia codCabezaHogar) {
		this.codCabezaHogar = codCabezaHogar;
	}

	public Boolean getFlgPrepensionado() {
		return flgPrepensionado;
	}

	public void setFlgPrepensionado(Boolean flgPrepensionado) {
		this.flgPrepensionado = flgPrepensionado;
	}

	public Boolean getFlgDiscapacidad() {
		return flgDiscapacidad;
	}

	public void setFlgDiscapacidad(Boolean flgDiscapacidad) {
		this.flgDiscapacidad = flgDiscapacidad;
	}

	public TipoDiscapacidad getCodTipoDiscapacidad() {
		return codTipoDiscapacidad;
	}

	public void setCodTipoDiscapacidad(TipoDiscapacidad codTipoDiscapacidad) {
		this.codTipoDiscapacidad = codTipoDiscapacidad;
	}

	public Boolean getFlgVictimaConflicto() {
		return flgVictimaConflicto;
	}

	public void setFlgVictimaConflicto(Boolean flgVictimaConflicto) {
		this.flgVictimaConflicto = flgVictimaConflicto;
	}

	public String getUrlFacebook() {
		return urlFacebook;
	}

	public void setUrlFacebook(String urlFacebook) {
		this.urlFacebook = urlFacebook;
	}

	public String getUrlSnapchat() {
		return urlSnapchat;
	}
	
	public void setUrlSnapchat(String urlSnapchat) {
		this.urlSnapchat = urlSnapchat;
	}
	
	public String getUrlPaginaWeb() {
		return urlPaginaWeb;
	}
	public void setUrlPaginaWeb(String urlPaginaWeb) {
		this.urlPaginaWeb = urlPaginaWeb;
	}
	
	public String getUrlOtraRedSocial() {
		return urlOtraRedSocial;
	}
	
	public void setUrlOtraRedSocial(String urlOtraRedSocial) {
		this.urlOtraRedSocial = urlOtraRedSocial;
	}
	
	public String getUsuarioSkype() {
		return usuarioSkype;
	}

	public void setUsuarioSkype(String usuarioSkype) {
		this.usuarioSkype = usuarioSkype;
	}
	public String getOtraOrientacionSexual() {
		return otraOrientacionSexual;
	}

	public void setOtraOrientacionSexual(String otraOrientacionSexual) {
		this.otraOrientacionSexual = otraOrientacionSexual;
	}
	
	public OrientacionSexual getCodOrientacionSexual() {
		return codOrientacionSexual;
	}
	
	public void setCodOrientacionSexual(OrientacionSexual codOrientacionSexual) {
		this.codOrientacionSexual = codOrientacionSexual;
	}
	
	public String getUrlTwitter() {
		return urlTwitter;
	}

	public void setUrlTwitter(String urlTwitter) {
		this.urlTwitter = urlTwitter;
	}

	public String getUrlLinkedin() {
		return urlLinkedin;
	}

	public void setUrlLinkedin(String urlLinkedin) {
		this.urlLinkedin = urlLinkedin;
	}

	public String getUrlInstagram() {
		return urlInstagram;
	}

	public void setUrlInstagram(String urlInstagram) {
		this.urlInstagram = urlInstagram;
	}
	
	public Desplazamiento getCodVictimaDesplazamiento() {
		return this.codVictimaDesplazamiento;
	}

	public void setCodVictimaDesplazamiento(Desplazamiento codVictimaDesplazamiento) {
		this.codVictimaDesplazamiento = codVictimaDesplazamiento;
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
		return "DatoAdicional [codDatoAdicional=" + codDatoAdicional + ", codPersona=" + codPersona
				+ ", flgPrepensionado=" + flgPrepensionado + ", flgDiscapacidad="
				+ flgDiscapacidad + ", flgVictimaConflicto=" + flgVictimaConflicto + ", urlFacebook=" + urlFacebook
				+ ", urlPaginaWeb=" + urlPaginaWeb + ", urlOtraRedSocial=" + urlOtraRedSocial
				+ ", otraOrientacionSexual=" + otraOrientacionSexual + ", snapchat=" + urlSnapchat + ", usuarioSkype="
				+ usuarioSkype + ", urlTwitter=" + urlTwitter + ", urlLinkedin=" + urlLinkedin + ", urlInstagram="
				+ urlInstagram + ", codVictimaDesplamiento=" + codVictimaDesplazamiento + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		
		TipoDiscapacidadDTO tipoDiscapacidad =  codTipoDiscapacidad != null ? (TipoDiscapacidadDTO)codTipoDiscapacidad.getDTO() : null;
		CabezaFamiliaDTO cabezaFamilia = codCabezaHogar != null ? (CabezaFamiliaDTO)codCabezaHogar.getDTO() : null;
		DesplazamientoDTO desplazamiento  = codVictimaDesplazamiento != null ? (DesplazamientoDTO)codVictimaDesplazamiento.getDTO() : null;
		OrientacionSexualDTO orientacionSexual  = codOrientacionSexual != null ? (OrientacionSexualDTO)codOrientacionSexual.getDTO() : null;
		return new DatoAdicionalDTO(codDatoAdicional, codPersona.getId(), cabezaFamilia, flgPrepensionado,	flgDiscapacidad, 
					tipoDiscapacidad, flgVictimaConflicto, urlFacebook, urlTwitter,	urlLinkedin, urlInstagram, urlSnapchat,
					otraOrientacionSexual, usuarioSkype, urlOtraRedSocial, urlPaginaWeb, orientacionSexual, desplazamiento);
	}
}
