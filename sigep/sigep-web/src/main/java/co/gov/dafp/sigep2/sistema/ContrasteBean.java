package co.gov.dafp.sigep2.sistema;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

@Named
@SessionScoped
public class ContrasteBean extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean lbContrasteApplied;
	private String temaAplicado;
	private String temaSinContraste = "/css/style.css";
	private String temaConContraste = "/css/styleContraste.css";
	private String strCambiarContraste;


	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
	}
	
	

	@PostConstruct
	public void init()  {
	     lbContrasteApplied = false;
	     temaAplicado = temaSinContraste;
		
	}

	@Override
	public String persist() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String update() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		// TODO Auto-generated method stub
	}
	
	public String cambiarContraste(){
		lbContrasteApplied = !lbContrasteApplied;
		if(!lbContrasteApplied)
			temaAplicado = temaSinContraste;
		else
			temaAplicado = temaConContraste;
		toIndex();
		return "";
	}

	public boolean isLbContrasteApplied() {
		return lbContrasteApplied;
	}

	public void setLbContrasteApplied(boolean lbContrasteApplied) {
		this.lbContrasteApplied = lbContrasteApplied;
	}

	public String getTemaAplicado() {
		return temaAplicado;
	}

	public void setTemaAplicado(String temaAplicado) {
		this.temaAplicado = temaAplicado;
	}
	
	private void toIndex(){
		try {
			//this.finalizarConversacion("/index.xhtml",null,null);
			//FacesContext.getCurrentInstance().getExternalContext().redirect("../../persona/informacionPersonal.xhtml?");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml?faces-redirect=true");	
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}



	public String getStrCambiarContraste() {
		return strCambiarContraste;
	}



	public void setStrCambiarContraste(String strCambiarContraste) {
		this.strCambiarContraste = strCambiarContraste;
	}
	
	

}
