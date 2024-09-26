package co.gov.dafp.sigep2.filtro;

import java.io.IOException;

import javax.faces.application.ViewExpiredException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.util.FileUtil;

@WebFilter(urlPatterns = "/*", dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD,
		DispatcherType.INCLUDE }, initParams = { @WebInitParam(name = "portalPage", value = "sigepencifras.xhtml"),
				@WebInitParam(name = "urlLogin", value = "login.xhtml"),
				@WebInitParam(name = "urlLogout", value = "logout.xhtml"),
				@WebInitParam(name = "tokenDefault", value = "11111-11111-11111-0000") })
public class FiltroUsuario implements Filter {
	private FilterConfig config;

	static String PORTAL_PAGE;
	static String URL_LOGIN;
	static String URL_LOGOUT;
	static String TOKEN_DEFAULT;

	public static final String HEADER_VIEWPORT 			= "viewport";
	public static final String HEADER_PRAGMA 			= "pragma";
	public static final String HEADER_CACHE_CONTROL 	= "Cache-Control";
	public static final String HEADER_EXPIRES		 	= "expires";
	public static final String HEADER_X_FRAME_OPTIONS 	= "X-FRAME-OPTIONS";
	public static final String HEADER_X_UA_COMPATIBLE 	= "X-UA-Compatible";

	public static final String HEADER_X_XSS_PROTECTION 	= "X-XSS-Protection";
	public static final String HEADER_X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";

	public static final String HEADER_DESCRIPTION 		= "description";
	public static final String HEADER_KEYWORDS 			= "keywords";

	public static final String VALUE_VIEWPORT 			= ConfigurationBundleConstants.viewport();
	public static final String VALUE_PRAGMA 			= ConfigurationBundleConstants.pragma();
	public static final String VALUE_CACHE_CONTROL 		= ConfigurationBundleConstants.cacheControl();
	public static final String VALUE_EXPIRES 			= "-1";
	public static final String VALUE_X_FRAME_OPTIONS 	= ConfigurationBundleConstants.xFrameOptionsDeny();
	public static final String VALUE_X_UA_COMPATIBLE 	= ConfigurationBundleConstants.xUACompatible();

	public static final String VALUE_X_XSS_PROTECTION 		= ConfigurationBundleConstants.xXSSProtection();
	public static final String VALUE_X_CONTENT_TYPE_OPTIONS = ConfigurationBundleConstants.xContentTypeOptions();

	public static final String VALUE_DESCRIPTION = TitlesBundleConstants
			.getStringMessagesBundle(TitlesBundleConstants.TTL_SYSTEM_OWNER) + " :: "
			+ TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_SYSTEM_DESCRIPTION);
	public static final String VALUE_KEYWORDS = TitlesBundleConstants
			.getStringMessagesBundle(TitlesBundleConstants.TTL_SYSTEM_KIEWORDS);

	@Override
	public void destroy() {
		config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String pathInfo = httpRequest.getPathInfo();
		if (pathInfo == null) {
			pathInfo = httpRequest.getRequestURI();
		}

		pathInfo = pathInfo.replaceFirst(FileUtil.SLASH, "");

		try {
		    if (pathInfo.endsWith(FileUtil.XHTML)) {
		        ServletWrapper wrappedResponse = new ServletWrapper(httpResponse);
		        wrappedResponse.doFilter(httpRequest, httpResponse, chain);
		    } else {
		        chain.doFilter(request, response);
		    }
		} catch (ViewExpiredException e) {
		    httpRequest.getRequestDispatcher("index.xhtml").forward(request, response);
		}catch(Exception e ){
			httpRequest.getRequestDispatcher("index.xhtml");
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;

		// Tambien se pueden cargar los parametros
		setPORTAL_PAGE(config.getInitParameter("portalPage"));
		setURL_LOGIN(config.getInitParameter("urlLogin"));
		setURL_LOGOUT(config.getInitParameter("urlLogout"));
		setTOKEN_DEFAULT(config.getInitParameter("tokenDefault"));

		if (PORTAL_PAGE == null || PORTAL_PAGE.isEmpty()) {
			// Error al cargar la url de pagina portal
			throw new ServletException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_NO_CONFIGURADA_PAGINA_ERROR));
		}

		if (URL_LOGIN == null || URL_LOGIN.isEmpty()) {
			// Error al cargar la url de login
			throw new ServletException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_NO_CONFIGURADA_PAGINA_LOGIN));
		}

		if (URL_LOGOUT == null || URL_LOGOUT.isEmpty()) {
			// Error al cargar la url de login
			throw new ServletException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_NO_CONFIGURADA_PAGINA_LOGIN));
		}

		if (TOKEN_DEFAULT == null || TOKEN_DEFAULT.isEmpty()) {
			// Error al cargar el valor del token por default
			throw new ServletException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_SESION_NO_INICIADA));
		}
	}

	private static void setPORTAL_PAGE(String pORTAL_PAGE) {
		PORTAL_PAGE = pORTAL_PAGE;
	}

	private static void setURL_LOGIN(String uRL_LOGIN) {
		URL_LOGIN = uRL_LOGIN;
	}

	private static void setTOKEN_DEFAULT(String tOKEN_DEFAULT) {
		TOKEN_DEFAULT = tOKEN_DEFAULT;
	}

	private static void setURL_LOGOUT(String uRL_LOGOUT) {
		URL_LOGOUT = uRL_LOGOUT;
	}
}
