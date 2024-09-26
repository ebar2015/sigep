/**
 * @Author: Jose Viscaya
 * @Date  : May 8, 2019, 8:04:06 AM
 */
package co.gov.dafp.sigep2.mbean.parametrica;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Enfermedad;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;


/**
 * @author: Jose Viscaya
 * @Date  : May 8, 2019, 8:04:06 AM
 */
@Named
@ViewScoped
@ManagedBean
public class EnfermedadBean extends BaseBean implements Serializable{
	

	private static final long serialVersionUID = 7257287313972821118L;
	private UsuarioDTO usuarioSesion;
	protected Integer codAudCodRol;
	protected Integer codAudAccionInsert = 3;
	protected Integer codAudAccionUpdate = 2;
	protected BigDecimal codAudUsuario;
	private List<Enfermedad> lstEnfermedad;
	private Enfermedad enfermedadSeleccionada;
	
	private String strMensajeConfirmacion;
	private String strMensajeConfirmacionEliminacion;
	private String nombreEnfermedad;
	
	private boolean habilitaFormulario;
	private boolean lbEsConsulta;
	private boolean lbModificacion;
	private boolean lbEliminar;
	private boolean blnFlgActivo;
	


	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@PostConstruct
	public void init()  {
		blnFlgActivo = false;
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		usuarioSesion = (UsuarioDTO) externalContext.getSessionMap().get("usuarioSesion");	
		codAudUsuario = BigDecimal.valueOf(usuarioSesion.getId());
		codAudCodRol  = (int) this.getRolAuditoria().getId();
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		strMensajeConfirmacion = MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_HV_GP_SOLICITAR_CANCELAR, getLocale());
		strMensajeConfirmacionEliminacion =  MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_PREGUNTA_ELIMINAR_REGISTRO, getLocale());
		Enfermedad enfermedad = new Enfermedad();
		enfermedad.setLimitIni(0);
		enfermedad.setLimitFin(2000);
		lstEnfermedad = ComunicacionServiciosAdmin.getEnfermedadFiltro(enfermedad);
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
		
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 13:51:23
	 * @File:   EnfermedadBean.java
	 * @param entidadPrivQuery
	 * @return
	 */
	public List<String> listaEnfermedad(String entidadPrivQuery){
		List<String> filtroEnfermedad = new ArrayList<>();
		for(Enfermedad enfermedad : lstEnfermedad ) {
			if(enfermedad.getNombreEnfermedad().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
				filtroEnfermedad.add(enfermedad.getNombreEnfermedad());
			}
		}
		return filtroEnfermedad;
   }
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 13:51:26
	 * @File:   EnfermedadBean.java
	 */
	public void filtrarEnfermedad() {
		Enfermedad enfermedad = new Enfermedad();
		enfermedad.setNombreEnfermedad(nombreEnfermedad);
		enfermedad.setLimitIni(0);
		enfermedad.setLimitFin(2000);
		lstEnfermedad = ComunicacionServiciosAdmin.getEnfermedadFiltro(enfermedad);
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 13:51:30
	 * @File:   EnfermedadBean.java
	 */
	public void nuevaEnfermedad() {
		enfermedadSeleccionada = new Enfermedad();
		habilitaFormulario = true;
		lbModificacion = false;
		blnFlgActivo = true;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 13:51:34
	 * @File:   EnfermedadBean.java
	 * @param enfermedad
	 */
	public void modificarEnfermedad(Enfermedad enfermedad) {
		enfermedadSeleccionada = enfermedad;
		habilitaFormulario = true;
		lbEsConsulta = false;
		lbModificacion = true;
		lbEliminar  = false;
		if(enfermedadSeleccionada.getFlgActivo() != null) {
			if(enfermedadSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 13:51:38
	 * @File:   EnfermedadBean.java
	 * @param enfermedad
	 */
	public void consultarEnfermedad(Enfermedad enfermedad) {
		enfermedadSeleccionada = enfermedad;
		habilitaFormulario = true;
		lbEsConsulta = true;
		lbModificacion = false;
		lbEliminar = false;
		if(enfermedadSeleccionada.getFlgActivo() != null) {
			if(enfermedadSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 13:51:41
	 * @File:   EnfermedadBean.java
	 * @param enfermedad
	 */
	public void solicitarConfirmacionEliminarEnfermedad(Enfermedad enfermedad) {
		enfermedadSeleccionada = enfermedad;
		lbEliminar = true;
		RequestContext.getCurrentInstance().execute("PF('dlgEliminarnEnfermedad').show();");
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 13:51:45
	 * @File:   EnfermedadBean.java
	 */
	public void guardarEnfermedad() {
		if(lbModificacion) {
			enfermedadSeleccionada.setAudAccion(codAudAccionUpdate);
		}else {
			enfermedadSeleccionada.setAudAccion(codAudAccionInsert);
		}
		if(lbEliminar) {
			enfermedadSeleccionada.setAudAccion(codAudAccionUpdate);
			enfermedadSeleccionada.setFlgActivo((short) 0);
		}
		
		if(blnFlgActivo) {
			enfermedadSeleccionada.setFlgActivo((short)1);
		}else {
			enfermedadSeleccionada.setFlgActivo((short)0);
		}
			
		
		enfermedadSeleccionada.setAudFechaActualizacion(new Date());
		enfermedadSeleccionada.setAudCodRol(new BigDecimal (getUsuarioSesion().getCodRol()).intValue());
		enfermedadSeleccionada.setAudCodUsuario(new BigDecimal (getUsuarioSesion().getId()));
		Enfermedad den = ComunicacionServiciosAdmin.setEnfermedad(enfermedadSeleccionada);	
		if(!den.isError()) {
			if(lbEliminar) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_REGISTRO_ELIMINADO_CORRECTAMENTE);
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			}
			Enfermedad enfermedad = new Enfermedad();
			enfermedad.setLimitIni(0);
			enfermedad.setLimitFin(2000);
			lstEnfermedad = ComunicacionServiciosAdmin.getEnfermedadFiltro(enfermedad);
		}else {
			if(den.getCodigoEstado() == 110) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants
								.getStringMessagesBundle(MessagesBundleConstants.MSG_CODIGO_ENFERMEDAD_DUPLICADO, getLocale()));
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants
								.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
			}
		}
		habilitaFormulario = false;
		lbModificacion = false;
		lbEliminar = false;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 13:51:51
	 * @File:   EnfermedadBean.java
	 */
	public void aceptarConsulta() {
		habilitaFormulario = false;
		lbEsConsulta = false;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 13:51:55
	 * @File:   EnfermedadBean.java
	 */
	public void buscarEnfermedad() {
		Enfermedad enfermedad = new Enfermedad();
		enfermedad.setNombreEnfermedad(enfermedadSeleccionada.getNombreEnfermedad());
		enfermedad.setLimitIni(0);
		enfermedad.setLimitFin(2000);
		lstEnfermedad = ComunicacionServiciosAdmin.getEnfermedadFiltro(enfermedad);
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 13:51:58
	 * @File:   EnfermedadBean.java
	 */
	public void accionConfirmada() {
		if(habilitaFormulario) {
			habilitaFormulario = false;
		}
		if(lbEsConsulta) {
			lbEsConsulta = false;
		}
		if(lbModificacion) {
			lbModificacion = false;
		}
		if(lbEliminar) {
			guardarEnfermedad();
		}
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

	/**
	 * @return the lstEnfermedad
	 */
	public List<Enfermedad> getLstEnfermedad() {
		return lstEnfermedad;
	}

	/**
	 * @param lstEnfermedad the lstEnfermedad to set
	 */
	public void setLstEnfermedad(List<Enfermedad> lstEnfermedad) {
		this.lstEnfermedad = lstEnfermedad;
	}

	/**
	 * @return the enfermedadSeleccionada
	 */
	public Enfermedad getEnfermedadSeleccionada() {
		return enfermedadSeleccionada;
	}

	/**
	 * @param enfermedadSeleccionada the enfermedadSeleccionada to set
	 */
	public void setEnfermedadSeleccionada(Enfermedad enfermedadSeleccionada) {
		this.enfermedadSeleccionada = enfermedadSeleccionada;
	}

	/**
	 * @return the strMensajeConfirmacion
	 */
	public String getStrMensajeConfirmacion() {
		return strMensajeConfirmacion;
	}

	/**
	 * @param strMensajeConfirmacion the strMensajeConfirmacion to set
	 */
	public void setStrMensajeConfirmacion(String strMensajeConfirmacion) {
		this.strMensajeConfirmacion = strMensajeConfirmacion;
	}

	/**
	 * @return the strMensajeConfirmacionEliminacion
	 */
	public String getStrMensajeConfirmacionEliminacion() {
		return strMensajeConfirmacionEliminacion;
	}

	/**
	 * @param strMensajeConfirmacionEliminacion the strMensajeConfirmacionEliminacion to set
	 */
	public void setStrMensajeConfirmacionEliminacion(String strMensajeConfirmacionEliminacion) {
		this.strMensajeConfirmacionEliminacion = strMensajeConfirmacionEliminacion;
	}

	/**
	 * @return the habilitaFormulario
	 */
	public boolean isHabilitaFormulario() {
		return habilitaFormulario;
	}

	/**
	 * @param habilitaFormulario the habilitaFormulario to set
	 */
	public void setHabilitaFormulario(boolean habilitaFormulario) {
		this.habilitaFormulario = habilitaFormulario;
	}

	/**
	 * @return the lbEsConsulta
	 */
	public boolean isLbEsConsulta() {
		return lbEsConsulta;
	}

	/**
	 * @param lbEsConsulta the lbEsConsulta to set
	 */
	public void setLbEsConsulta(boolean lbEsConsulta) {
		this.lbEsConsulta = lbEsConsulta;
	}

	/**
	 * @return the lbModificacion
	 */
	public boolean isLbModificacion() {
		return lbModificacion;
	}

	/**
	 * @param lbModificacion the lbModificacion to set
	 */
	public void setLbModificacion(boolean lbModificacion) {
		this.lbModificacion = lbModificacion;
	}

	/**
	 * @return the lbEliminar
	 */
	public boolean isLbEliminar() {
		return lbEliminar;
	}

	/**
	 * @param lbEliminar the lbEliminar to set
	 */
	public void setLbEliminar(boolean lbEliminar) {
		this.lbEliminar = lbEliminar;
	}

	/**
	 * @return the nombreEnfermedad
	 */
	public String getNombreEnfermedad() {
		return nombreEnfermedad;
	}

	/**
	 * @param nombreEnfermedad the nombreEnfermedad to set
	 */
	public void setNombreEnfermedad(String nombreEnfermedad) {
		this.nombreEnfermedad = nombreEnfermedad;
	}

	/**
	 * @return the blnFlgActivo
	 */
	public boolean isBlnFlgActivo() {
		return blnFlgActivo;
	}

	/**
	 * @param blnFlgActivo the blnFlgActivo to set
	 */
	public void setBlnFlgActivo(boolean blnFlgActivo) {
		this.blnFlgActivo = blnFlgActivo;
	}
}
