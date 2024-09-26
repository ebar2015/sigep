package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.sistema.BaseBean;

@Named
@RequestScoped
public class ControlRecursoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -4633788446241758003L;

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void init() throws NotSupportedException {
		throw new NotSupportedException();
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

	public String go() {
		return this.recurso;
	}

	public String go(String recurso, String recursoId) {
		this.recurso = recurso;
		this.recursoId = recursoId;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		request.getSession().setAttribute("recurso", this.recurso);
		request.getSession().setAttribute("recursoId", this.recursoId);
		return this.recurso;
	}

}
