package co.gov.dafp.sigep2.filtro;

import java.io.IOException;

import javax.faces.application.ViewExpiredException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.util.FileUtil;

public class ServletWrapper extends HttpServletResponseWrapper {
	public ServletWrapper(HttpServletResponse response) {
		super(response);
	}

	protected void doFilter(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain)
			throws ServletException, IOException {

		String pathInfo = httpRequest.getPathInfo();
		if (pathInfo == null) {
			pathInfo = httpRequest.getRequestURI();
		}

		pathInfo = pathInfo.replaceFirst(FileUtil.SLASH, "");

		HttpSession session = httpRequest.getSession();
		String token = (String) session.getAttribute("token");
		Boolean sesionValida = Boolean.valueOf(String.valueOf(session.getAttribute("sesionValida")));

		if (sesionValida && token != null && !FiltroUsuario.TOKEN_DEFAULT.equals(token)
				&& !FiltroUsuario.URL_LOGIN.equals(pathInfo) && !FiltroUsuario.URL_LOGOUT.equals(pathInfo)) {
			boolean estadoSesion = ComunicacionServiciosAdmin.getEstadoSesionValida(token);
			if (!estadoSesion) {
				session.invalidate();
				session = httpRequest.getSession(true);
			}
		}
		


		// Validacion clickjacking
		try {
			String name = FiltroUsuario.HEADER_X_FRAME_OPTIONS;
			String value = null;
			if (FiltroUsuario.PORTAL_PAGE.equals(pathInfo)) {
				value = ConfigurationBundleConstants.xFrameOptionsSomeOrigin();
			} else {
				value = ConfigurationBundleConstants.xFrameOptionsDeny();
			}

			header(name, value);

			name = FiltroUsuario.HEADER_PRAGMA;
			value = FiltroUsuario.VALUE_PRAGMA;
			header(name, value);

			name = FiltroUsuario.HEADER_CACHE_CONTROL;
			value = FiltroUsuario.VALUE_CACHE_CONTROL;
			header(name, value);

			name = FiltroUsuario.HEADER_X_UA_COMPATIBLE;
			value = FiltroUsuario.VALUE_X_UA_COMPATIBLE;
			header(name, value);

			name = FiltroUsuario.HEADER_EXPIRES;
			value = "0";
			header(name, value);

			name = FiltroUsuario.HEADER_X_XSS_PROTECTION;
			value = FiltroUsuario.VALUE_X_XSS_PROTECTION;
			header(name, value);

			name = FiltroUsuario.HEADER_X_CONTENT_TYPE_OPTIONS;
			value = FiltroUsuario.VALUE_X_CONTENT_TYPE_OPTIONS;
			header(name, value);

			name = FiltroUsuario.HEADER_DESCRIPTION;
			value = FiltroUsuario.VALUE_DESCRIPTION;
			header(name, value);

			name = FiltroUsuario.HEADER_KEYWORDS;
			value = FiltroUsuario.VALUE_KEYWORDS;
			header(name, value);

			name = FiltroUsuario.HEADER_VIEWPORT;
			value = FiltroUsuario.VALUE_VIEWPORT;
			header(name, value);
		} catch (Exception e) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} finally {
			try {
				chain.doFilter(httpRequest, httpResponse);
			} catch (ViewExpiredException e) {
			    // Acciones personalizadas cuando se produce la excepción "View /index.xhtml could not be restored"
			    // Por ejemplo, puedes redirigir a una página de error o mostrar un mensaje de error
			    httpRequest.getRequestDispatcher("index.xhtml");
			}catch(Exception e ){
				httpRequest.getRequestDispatcher("index.xhtml");
			}
		}
	}

	private void header(String name, String value) {
		if (super.containsHeader(name)) {
			super.setHeader(name, value);
		} else {
			super.addHeader(name, value);
		}
	}
}
