package co.gov.dafp.sigep2.sistema;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MenuBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.mbean.MenuBean;

@Named
@SessionScoped
public class LenguajeBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -7104829007076214737L;

	private String localeCode = Locale.ROOT.getCountry();

	private List<Idioma> lenguajes;
	private Idioma idioma;

	public static final String ESPANIOL_COLOMBIA = "es";
	private static final String localeCodeCookieString = "localeCode";

	private boolean idiomaActualizado = false;
	private String strLoggin;

	public String getLocaleCode() {
		if (localeCode == null || localeCode.isEmpty()) {
			localeCode = ESPANIOL_COLOMBIA;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();

		Cookie cookie = getCookie(localeCodeCookieString);
		if (cookie != null) {
			localeCode = cookie.getValue();
		}

		Control control = Control.getControl(Control.FORMAT_PROPERTIES);
		if (localeCode == null || localeCode.isEmpty() || localeCode.equals(ESPANIOL_COLOMBIA)) {
			localeCode = ESPANIOL_COLOMBIA;
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ROOT);
			ResourceBundle.getBundle(TitlesBundleConstants.TITLES_BUNDLE_CONSTANTS, Locale.ROOT, control);
			ResourceBundle.getBundle(MessagesBundleConstants.MESSAGES_BUNDLE_CONSTANTS, Locale.ROOT, control);
			ResourceBundle.getBundle(MenuBundleConstants.MENU_BUNDLE_CONSTANTS, Locale.ROOT, control);
			contexto.getSessionMap().put("locale", Locale.ROOT);
		} else {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.US);
			ResourceBundle.getBundle(TitlesBundleConstants.TITLES_BUNDLE_CONSTANTS, Locale.US, control);
			ResourceBundle.getBundle(MessagesBundleConstants.MESSAGES_BUNDLE_CONSTANTS, Locale.US, control);
			ResourceBundle.getBundle(MenuBundleConstants.MENU_BUNDLE_CONSTANTS, Locale.US, control);
			contexto.getSessionMap().put("locale", Locale.US);
		}
		return localeCode;
	}

	public void setLocaleCode(String strLocaleCode) {
		if (strLocaleCode == null || strLocaleCode.isEmpty() || "null".equals(strLocaleCode)) {
			strLocaleCode = Locale.ROOT.getLanguage();
		}
		if (strLocaleCode != null && !strLocaleCode.equals(this.localeCode)) {
			this.idiomaActualizado = true;
			Cookie cookie = getCookie(localeCodeCookieString);
			if (cookie == null) {
				crearCookie(localeCodeCookieString, strLocaleCode, getVigenciaCookie(7, 0, 0, 0));
			} else {
				cookie.setValue(strLocaleCode);
				cookie.setMaxAge(getVigenciaCookie(7, 0, 0, 0));
				cookie.setPath("/");
				refrescarCookie(cookie);
			}
			this.localeCode = strLocaleCode;
		}
	}
	
	public void solicitarRefrescarIdioma(Idioma idiomap,Long origen){
		idioma = idiomap;
		if(origen == 1)/*Basictemplate*/
			RequestContext.getCurrentInstance().execute("PF('wvComprobarCambioIdiomaBT').show();");
		else if (origen == 2)/*Pagetemplate*/
			RequestContext.getCurrentInstance().execute("PF('wvComprobarCambioIdiomaPT').show();");
	}
	

	public void refrescarIdiomaConfirmacion() {
		strLoggin="N";
		setLocaleCode(idioma.getPais());
		refrescarIdioma();
	}
	public void refrescarIdioma() {
		if("N".equals(strLoggin)) {
			Cookie cookie = getCookie(localeCodeCookieString);
			if (cookie != null) {
				this.localeCode = cookie.getValue();
			} else {
				return;
			}
			String localeCode2 = localeCode;
	
			if (localeCode2 == null) {
				localeCode2 = ESPANIOL_COLOMBIA;
			}
	
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			contexto.getSessionMap().put(localeCodeCookieString, localeCode2);
			this.getLocaleCode();
	
			MenuBean menuBean = (MenuBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuBean");
			if (menuBean != null) {
				menuBean.setRecursoDTOsHabilitados(null);
				try {
					menuBean.init();
				} catch (Exception e) {
					logger.error("", e);
				}
			}
			try {
				if (this.idiomaActualizado) {
					this.idiomaActualizado = false;
					String path = FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo();
					boolean restablecerPassword = path.contains("restablecerPassword.xhtml");
					String parametros = "";
					if (restablecerPassword) {
						String ticket = (String) contexto.getSessionMap().get("ticket");
						parametros = "s=false&ticket=" + ticket;
					}
					setForzarRedireccionamientoIndex(true);
					finalizarConversacion(!restablecerPassword, null, parametros);
				}
			} catch (IOException e) {
				logger.error("void setLocaleCode(String localeCode)", e);
			}
		}
	}





	@Named
	@Produces
	public List<Idioma> getLenguajes() {
		return lenguajes;
	}

	public void setLenguajes(List<Idioma> lenguajes) {
		this.lenguajes = lenguajes;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	@PostConstruct
	public void init() {
		Cookie cookie = getCookie(localeCodeCookieString);
		if (cookie == null) {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ROOT);
			ResourceBundle.getBundle(TitlesBundleConstants.TITLES_BUNDLE_CONSTANTS, Locale.ROOT);
			ResourceBundle.getBundle(MessagesBundleConstants.MESSAGES_BUNDLE_CONSTANTS, Locale.ROOT);
			ResourceBundle.getBundle(MenuBundleConstants.MENU_BUNDLE_CONSTANTS, Locale.ROOT);
		}

		lenguajes = new LinkedList<>();
		lenguajes.add(
				new Idioma(ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_LENGUAJE_ESPANIOL),
						ESPANIOL_COLOMBIA, "esp.svg"));
		lenguajes.add(
				new Idioma(ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_LENGUAJE_INGLES),
						Locale.US.getLanguage(), "eng.svg"));
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void retrieve() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public List<Idioma> getCountriesInMap() {
		return lenguajes;
	}

	public class Idioma {
		private String etiqueta;
		private String pais;
		private String imagen;

		public Idioma(String etiqueta, String pais, String imagen) {
			this.etiqueta = etiqueta;
			this.pais = pais;
			this.imagen = imagen;
		}

		public String getEtiqueta() {
			return etiqueta;
		}

		public void setEtiqueta(String etiqueta) {
			this.etiqueta = etiqueta;
		}

		public String getPais() {
			return pais;
		}

		public void setPais(String pais) {
			this.pais = pais;
		}

		public String getImagen() {
			return imagen;
		}

		public void setImagen(String imagen) {
			this.imagen = imagen;
		}

		@Override
		public String toString() {
			return etiqueta;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((etiqueta == null) ? 0 : etiqueta.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Idioma other = (Idioma) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (etiqueta == null) {
				if (other.etiqueta != null)
					return false;
			} else if (!etiqueta.equals(other.etiqueta))
				return false;
			return true;
		}

		private LenguajeBean getOuterType() {
			return LenguajeBean.this;
		}
	}

}
