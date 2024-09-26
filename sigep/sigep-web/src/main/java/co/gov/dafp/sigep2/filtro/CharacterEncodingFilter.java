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
import javax.servlet.http.HttpServletRequest;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.util.FileUtil;

@WebFilter(urlPatterns = { "/*" }, dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD,
		DispatcherType.INCLUDE, DispatcherType.ERROR, DispatcherType.ASYNC })
public class CharacterEncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		return;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String pathInfo = httpRequest.getPathInfo();
		if (pathInfo == null) {
			pathInfo = httpRequest.getRequestURI();
		}

		pathInfo = pathInfo.replaceFirst(FileUtil.SLASH, "");

		if (pathInfo.endsWith(FileUtil.XHTML)) {
			request.setCharacterEncoding(ConfigurationBundleConstants.encoding());
			response.setCharacterEncoding(ConfigurationBundleConstants.encoding());
		}
		try {
			chain.doFilter(request, response);
		} catch (ViewExpiredException e) {
		    // Acciones personalizadas cuando se produce la excepción "View /index.xhtml could not be restored"
		    // Por ejemplo, puedes redirigir a una página de error o mostrar un mensaje de error
		    httpRequest.getRequestDispatcher("index.xhtml");
		}catch(Exception e ){
			httpRequest.getRequestDispatcher("index.xhtml");
		}
		
	}

	@Override
	public void destroy() {
		return;
	}

}
