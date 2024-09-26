/**
 * 
 */
package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * @author joseviscaya
 *
 */
@ManagedBean(name = "preubaBean")
@ViewScoped
public class PreubaBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8737084726182650296L;
	private String usuario;
	private String pass;
	private String tele;
	private String correo;
	private boolean checB = true;
	private boolean tab2 = true;
	private boolean tab3 = true;
	private boolean tab4 = true;
	private boolean tab5 = true;
	private boolean tab6 = true;
	private boolean tab7 = true;
	private boolean tab8 = true;
	private boolean tab9 = true;
	
	
	public PreubaBean() {
		this.checB = false;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the pss
	 */
	public String getPass() {
		return pass;
	}
	/**
	 * @param pss the pss to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
	public void login1(String msg) {
		System.out.println("Message"+msg);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Mensage"+msg));
		System.out.println("usuario "+usuario);
		System.out.println("pss "+pass);
		System.out.println("tele "+tele);
		System.out.println("correo "+correo);
		usuario = null;
		pass = null;
		tele = null;
		correo = null;
		tab2 = false;
	}
	
	public void login2(String msg) {
		System.out.println("Message"+msg);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Mensage"+msg));
		System.out.println("usuario "+usuario);
		System.out.println("pss "+pass);
		System.out.println("tele "+tele);
		System.out.println("correo "+correo);
		usuario = null;
		pass = null;
		tele = null;
		correo = null;
		tab3 = false;
	}
	
	public void login3(String msg) {
		System.out.println("Message"+msg);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Mensage"+msg));
		System.out.println("usuario "+usuario);
		System.out.println("pss "+pass);
		System.out.println("tele "+tele);
		System.out.println("correo "+correo);
		usuario = null;
		pass = null;
		tele = null;
		correo = null;
		tab3 = false;
	}
	
	public void login4(String msg) {
		System.out.println("Message"+msg);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Mensage"+msg));
		System.out.println("usuario "+usuario);
		System.out.println("pss "+pass);
		System.out.println("tele "+tele);
		System.out.println("correo "+correo);
		usuario = null;
		pass = null;
		tele = null;
		correo = null;
		tab3 = false;
	}
	/**
	 * @return the tele
	 */
	public String getTele() {
		return tele;
	}
	/**
	 * @param tele the tele to set
	 */
	public void setTele(String tele) {
		this.tele = tele;
	}
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	/**
	 * @return the checB
	 */
	public boolean isChecB() {
		return checB;
	}
	/**
	 * @param checB the checB to set
	 */
	public void setChecB(boolean checB) {
		this.checB = checB;
	}
	public boolean isTab2() {
		return tab2;
	}
	public void setTab2(boolean tab2) {
		this.tab2 = tab2;
	}
	public boolean isTab3() {
		return tab3;
	}
	public void setTab3(boolean tab3) {
		this.tab3 = tab3;
	}
	public boolean isTab4() {
		return tab4;
	}
	public void setTab4(boolean tab4) {
		this.tab4 = tab4;
	}
	public boolean isTab5() {
		return tab5;
	}
	public void setTab5(boolean tab5) {
		this.tab5 = tab5;
	}
	public boolean isTab6() {
		return tab6;
	}
	public void setTab6(boolean tab6) {
		this.tab6 = tab6;
	}
	public boolean isTab7() {
		return tab7;
	}
	public void setTab7(boolean tab7) {
		this.tab7 = tab7;
	}
	public boolean isTab8() {
		return tab8;
	}
	public void setTab8(boolean tab8) {
		this.tab8 = tab8;
	}
	public boolean isTab9() {
		return tab9;
	}
	public void setTab9(boolean tab9) {
		this.tab9 = tab9;
	}

}
