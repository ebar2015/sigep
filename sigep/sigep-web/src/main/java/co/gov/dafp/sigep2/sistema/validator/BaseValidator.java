package co.gov.dafp.sigep2.sistema.validator;

import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import co.gov.dafp.sigep2.sistema.LenguajeBean;

public abstract class BaseValidator {
	protected Locale getLocale() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		Locale locale = (Locale) contexto.getSessionMap().get("locale");
		if (locale == null) {
			Cookie cookie = getCookie("localeCode");
			if (cookie == null || LenguajeBean.ESPANIOL_COLOMBIA.equals(cookie.getValue())) {
				locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
			} else {
				locale = Locale.US;
			}
		}
		return locale;
	}

	/**
	 * Retorna el estado de una cookie en el navegador
	 * 
	 * @param nombreCookie
	 * @return {@link Cookie}
	 */
	public Cookie getCookie(String nombreCookie) {
		Object localeCodeCookie = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap()
				.get(nombreCookie);
		if (localeCodeCookie != null) {
			return (Cookie) ((Cookie) localeCodeCookie).clone();
		} else {
			return null;
		}
	}
}
