package co.gov.dafp.sigep2.mbean.hojadevida;

import java.io.Serializable;

import javax.faces.context.FacesContext;

public class CertificadoInformacionActual implements Serializable{
    
	private static final long serialVersionUID = 6301963482833989450L;
	private String titulo;
   	private String contenido;
   	private String urlLogo;
   	private String urlBackground;
   	private String urlFooter;
	
   	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public String getUrlLogo() {
		return urlLogo;
	}

	public void setUrlLogo(String urlLogo) {
		this.urlLogo = FacesContext.getCurrentInstance().getExternalContext().getRealPath(urlLogo);
	}
	
   	public String getUrlBackground() {
		return urlBackground;
	}

	public void setUrlBackground(String urlBackground) {
		this.urlBackground = FacesContext.getCurrentInstance().getExternalContext().getRealPath(urlBackground);
	}

	public String getUrlFooter() {
		return urlFooter;
	}

	public void setUrlFooter(String urlFooter) {
		this.urlFooter = FacesContext.getCurrentInstance().getExternalContext().getRealPath(urlFooter);
	}
}
