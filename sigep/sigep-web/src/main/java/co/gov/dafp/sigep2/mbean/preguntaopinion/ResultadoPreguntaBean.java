package co.gov.dafp.sigep2.mbean.preguntaopinion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.mbean.SesionBean;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;

/**
* @author Maria Alejandra Colorado Rios
* @version 1.0
* @Class ResultadoPreguntaBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Administracion del sistema
* Fecha: 24/03/2018
*/

@Named
@ViewScoped
@ManagedBean
public class ResultadoPreguntaBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	//Variables para la habilitar el formulario
	private boolean habilitarPanelDialogoPregunta = false;
	private boolean habilitarPanelDialogAgradecimiento = false;

	//variables que almacenan la informacion de la pregunta de opinion y el resultado
	private ResultadoPregunta resultadoPregunta;
	private PreguntaOpinion preguntaOpinion;
	private String preguntaRandom = "";

	/**
	 * @return the habilitarPanelDialogoPregunta
	 */
	public boolean isHabilitarPanelDialogoPregunta() {
		return habilitarPanelDialogoPregunta;
	}

	/**
	 * @param habilitarPanelDialogoPregunta the habilitarPanelDialogoPregunta to set
	 */
	public void setHabilitarPanelDialogoPregunta(boolean habilitarPanelDialogoPregunta) {
		this.habilitarPanelDialogoPregunta = habilitarPanelDialogoPregunta;
	}

	/**
	 * @return the habilitarPanelDialogAgradecimiento
	 */
	public boolean isHabilitarPanelDialogAgradecimiento() {
		return habilitarPanelDialogAgradecimiento;
	}

	/**
	 * @param habilitarPanelDialogAgradecimiento the habilitarPanelDialogAgradecimiento to set
	 */
	public void setHabilitarPanelDialogAgradecimiento(boolean habilitarPanelDialogAgradecimiento) {
		this.habilitarPanelDialogAgradecimiento = habilitarPanelDialogAgradecimiento;
	}

	/**
	 * @return the resultadoPregunta
	 */
	public ResultadoPregunta getResultadoPregunta() {
		return resultadoPregunta;
	}

	/**
	 * @param resultadoPregunta the resultadoPregunta to set
	 */
	public void setResultadoPregunta(ResultadoPregunta resultadoPregunta) {
		this.resultadoPregunta = resultadoPregunta;
	}

	/**
	 * @return the preguntaOpinion
	 */
	public PreguntaOpinion getPreguntaOpinion() {
		return preguntaOpinion;
	}

	/**
	 * @param preguntaOpinion the preguntaOpinion to set
	 */
	public void setPreguntaOpinion(PreguntaOpinion preguntaOpinion) {
		this.preguntaOpinion = preguntaOpinion;
	}

	/**
	 * @return the preguntaRandom
	 */
	public String getPreguntaRandom() {
		return preguntaRandom;
	}

	/**
	 * @param preguntaRandom the preguntaRandom to set
	 */
	public void setPreguntaRandom(String preguntaRandom) {
		this.preguntaRandom = preguntaRandom;
	}

	/**
	 * @return the sesionBean
	 */
	public SesionBean getSesionBean() {
		return sesionBean;
	}

	/**
	 * @param sesionBean the sesionBean to set
	 */
	public void setSesionBean(SesionBean sesionBean) {
		this.sesionBean = sesionBean;
	}

	@Inject
	private SesionBean sesionBean;

	@PostConstruct
	public void init() {
		resultadoPregunta = new ResultadoPregunta();
		preguntaOpinion = new PreguntaOpinion();
	}

	/**
	 * Metodo que permite guardar el resultado de la pregunta de opinion que ingresa el usuario
	 */
	
	public void guardarResultadoPregunta() {
		resultadoPregunta.setCodPreguntaOpinion(preguntaOpinion.getCodPreguntaOpinion());
		resultadoPregunta.setCodPersona(BigDecimal.valueOf(this.getUsuarioSesion().getCodPersona()));
		resultadoPregunta.setAudFechaActualizacion(DateUtils.getFechaSistema());
		resultadoPregunta.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		resultadoPregunta.setAudCodRol(this.getRolAuditoria()!=null ? (int) this.getRolAuditoria().getId(): 1);
		resultadoPregunta.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		resultadoPregunta.setCodEntidadSesion(getEntidadUsuario().getId());
		Boolean valid = ComunicacionServiciosSis.setpreguntaopinion(resultadoPregunta);
		if (valid) {
			System.out.println("Creacion correcta");
			habilitarPanelDialogAgradecimiento = true;
			habilitarPanelDialogoPregunta = false;
			try {
				TimeUnit.SECONDS.sleep(1);
			}catch(Exception e) {}
			sesionBean.logout();
		} else {
			System.out.println("Presentas un error de creacion");
			resultadoPregunta = new ResultadoPregunta();
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("$('#DialogGenerarPreguntaOpinion').modal('hide')");
		}
	}
	
	/**
	 * Metodo que cierra la sesion del usuario
	 */
	public void cerrarSesion() {
		try {
			habilitarPanelDialogoPregunta = false;
			sesionBean.logout();
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			contexto.getSessionMap().put("pathInfoAnterior", "/index.xhtml");
			finalizarConversacion(true, null, null);
		} catch (Exception e) {
			logger.error("void abrirDialogo()", e);
		}
	}

	/**
	 * Metodo que verifica que existan preguntas de opinion validas para hacerle al usuario,
	 * si se cumple, abre un mensaje de dialogo con la pregunta Random y hasta que este no responda no cierra sesion
	 * Si no tiene preguntas validas, simplementa cierra sesion
	 */
	public void abrirDialogo() {
		preguntaOpinion = new PreguntaOpinion();
		preguntaOpinion = ComunicacionServiciosSis.getPreguntaOpinionRDN();
		preguntaRandom = preguntaOpinion.getPregunta();
		if (preguntaRandom == null) {
			try {
				sesionBean.logout();
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
				contexto.getSessionMap().put("pathInfoAnterior", "/index.xhtml");
				finalizarConversacion(true, null, null);
			} catch (Exception e) {
				logger.error("void abrirDialogo()", e);
			}
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			habilitarPanelDialogoPregunta = true;
			habilitarPanelDialogAgradecimiento = false;
			context.execute("$('#DialogGenerarPreguntaOpinion').modal({backdrop: 'static', keyboard: false})");
		}
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub

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
}
