package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;

@ManagedBean
@ViewScoped
public class ProgressBarView implements Serializable {
	private static final long serialVersionUID = 7185480487593493486L;

	protected Integer progress;

	public Integer getProgress() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		progress = (Integer) contexto.getSessionMap().get("progress");

		if (progress == null) {
			progress = 0;
		}

		return progress;
	}

	public void setProgress(Integer progress) {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("progress", progress);
		this.progress = progress;
	}

	public void cancel() {
		progress = null;
	}

	public void onComplete() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_EXITOSO)));
	}
}
