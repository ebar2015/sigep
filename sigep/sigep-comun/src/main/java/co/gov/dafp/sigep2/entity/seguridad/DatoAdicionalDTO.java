package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.view.CabezaFamiliaDTO;
import co.gov.dafp.sigep2.entity.view.DesplazamientoDTO;
import co.gov.dafp.sigep2.entity.view.OrientacionSexualDTO;
import co.gov.dafp.sigep2.entity.view.TipoDiscapacidadDTO;

/**
 * The persistent class for the DATOS CONTACTO database table.
 * 
 */
public class DatoAdicionalDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = 4040037313631254729L;

	private long id;
	
	private Long codPersona;
	
	private CabezaFamiliaDTO codCabezaHogar;
	
	private Boolean flgPrepensionado;
	
	private Boolean flgDiscapacidad;
	
	private TipoDiscapacidadDTO codTipoDiscapacidad;
	
	private Boolean flgVictimaConflicto;
	
	private String urlFacebook;
	
	private String urlTwitter;
	
	private String urlLinkedin;
	
	private String urlInstagram;
	
	private String urlSnapchat;

	private String otraOrientacionSexual;
	
	private String usuarioSkype;
	
	private String urlOtraRedSocial;
	
	private String urlPaginaWeb;
	
	private Long codOrientacionSexual;
	
	private DesplazamientoDTO desplazamiento;
	
	private OrientacionSexualDTO orientacion;
	
	public DatoAdicionalDTO() {
	}	

	public DatoAdicionalDTO(long id,Long codPersona, CabezaFamiliaDTO codCabezaHogar,Boolean flgPrepensionado
			,Boolean flgDiscapacidad,TipoDiscapacidadDTO codTipoDiscapacidad,Boolean flgVictimaConflicto
			,String urlFacebook,String urlTwitter,String urlLinkedin,String urlInstagram, String urlSnapchat, 
			String otraOrientacionSexual, String usuarioSkype , String urlOtraRedSocial, String urlPaginaWeb, 
			OrientacionSexualDTO codOrientacionSexual, DesplazamientoDTO desplazamiento) {
		super();
		this.id = id;
		this.codPersona = codPersona;
		this.codCabezaHogar = codCabezaHogar;
		this.flgPrepensionado = flgPrepensionado;
		this.flgDiscapacidad = flgDiscapacidad;
		this.codTipoDiscapacidad = codTipoDiscapacidad;
		this.flgVictimaConflicto = flgVictimaConflicto;
		this.urlFacebook = urlFacebook;
		this.urlTwitter = urlTwitter;
		this.urlLinkedin = urlLinkedin;
		this.urlInstagram = urlInstagram;
		this.urlSnapchat = urlSnapchat;
		this.otraOrientacionSexual = otraOrientacionSexual;
		this.usuarioSkype = usuarioSkype;
		this.urlOtraRedSocial = urlOtraRedSocial;
		this.urlPaginaWeb = urlPaginaWeb;
		this.desplazamiento = desplazamiento;
		this.orientacion = codOrientacionSexual;
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

	public CabezaFamiliaDTO getCodCabezaHogar() {
		return codCabezaHogar;
	}

	public void setCodCabezaHogar(CabezaFamiliaDTO codCabezaHogar) {
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

	public TipoDiscapacidadDTO getCodTipoDiscapacidad() {
		return codTipoDiscapacidad;
	}

	public void setCodTipoDiscapacidad(TipoDiscapacidadDTO codTipoDiscapacidad) {
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
	
	
	public DesplazamientoDTO getCodVictimaDesplazamiento() {
		return desplazamiento;
	}
	
	public void setCodOrientacionSexual(OrientacionSexualDTO orientacion) {
		this.orientacion = orientacion;
	}
	
	public OrientacionSexualDTO getCodOrientacionSexual() {
		return orientacion;
	}
	
	public void setCodVictimaDesplazamiento(DesplazamientoDTO desplazamiento) {
		this.desplazamiento = desplazamiento;
	}

	@Override
	public String toString() {
		return "DatosContactoDTO [id=" + id + ", codPersona=" + codPersona
				+ ", flgPrepensionado=" + flgPrepensionado + ", flgDiscapacidad=" + flgDiscapacidad + ", flgVictimaConflicto=" 
				+ flgVictimaConflicto + ", urlFacebook=" + urlFacebook + ", urlTwitter=" + urlTwitter
				+ ", urlLinkedin=" + urlLinkedin + ", urlInstagram=" + urlInstagram + "urlOtraRedSocial=" + urlOtraRedSocial + ", urlPaginaWeb=" + urlPaginaWeb + ", usuarioSkype=" 
						+ usuarioSkype + ", otraOrientacionSexual= " + otraOrientacionSexual + ", urlSnapchat=" + urlSnapchat + ", codOrientacionSexual=" +codOrientacionSexual + " codVictimaDesplazamiento="  +  desplazamiento + "]";
	}
}