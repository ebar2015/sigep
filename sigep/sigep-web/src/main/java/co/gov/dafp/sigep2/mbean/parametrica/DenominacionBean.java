/**
 * @Author: Jose Viscaya
 * @Date  : 12 abr. 2019, 9:49:47
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
import co.gov.dafp.sigep2.entities.Denominacion;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;

/**
 * @author: Jose Viscaya
 * @Date  : 12 abr. 2019, 9:49:47
 */
@Named
@ViewScoped
@ManagedBean
public class DenominacionBean extends BaseBean implements Serializable{
	
	
	private UsuarioDTO usuarioSesion;
	protected Integer codAudCodRol;
	protected Integer codAudAccionInsert = 3;
	protected Integer codAudAccionUpdate = 2;
	
	protected BigDecimal codAudUsuario;
	private List<Denominacion> lstDenominacion;
	private Denominacion denominacionSeleccionada;
	
	private String strMensajeConfirmacion;
	private String strMensajeConfirmacionEliminacion;
	private String nombreDenominacion;
	private String strNombreDenominacionAnt;
	
	private boolean habilitaFormulario;
	private boolean lbEsConsulta;
	private boolean lbModificacion;
	private boolean lbEliminar;
	
	private boolean blnFlgActivo;

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1981489735745035228L;

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@PostConstruct
	public void init() {
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
		Denominacion denomincacion = new Denominacion();
		lstDenominacion = ComunicacionServiciosEnt.getDenomincacionesFiltro(denomincacion);
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
		
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   Apr 30, 2019, 9:52:29 AM
	 * @File:   DenominacionBean.java
	 * @param entidadPrivQuery
	 * @return
	 */
	public List<String> listaDenominaciones(String entidadPrivQuery){
		List<String> filtroDenolimacion = new ArrayList<>();
		for(Denominacion denominacion : lstDenominacion ) {
			if(denominacion.getNombreDenominacion().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
				filtroDenolimacion.add(denominacion.getNombreDenominacion());
			}
		}
		return filtroDenolimacion;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   30 abr. 2019, 13:56:40
	 * @File:   DenominacionBean.java
	 */
	public void filtrarDenominacion() {
		Denominacion denomincacion = new Denominacion();
		denomincacion.setNombreDenominacion(nombreDenominacion);
		lstDenominacion = ComunicacionServiciosEnt.getDenomincacionesFiltro(denomincacion);
	}
	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   12 abr. 2019, 9:55:59
	 * @File:   DenominacionBean.java
	 */
	public void nuevaDenominacion() {
		denominacionSeleccionada = new Denominacion();
		habilitaFormulario = true;
		lbModificacion = false;
		blnFlgActivo = true;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   12 abr. 2019, 11:44:20
	 * @File:   DenominacionBean.java
	 * @param denominacion
	 */
	public void modificarDenominacion(Denominacion denominacion) {
		denominacionSeleccionada = denominacion;
		strNombreDenominacionAnt = denominacion.getNombreDenominacion();
		habilitaFormulario = true;
		lbEsConsulta = false;
		lbModificacion = true;
		lbEliminar  = false;
		if(denominacionSeleccionada.getFlgActivo() != null) {
			if(denominacionSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   12 abr. 2019, 11:44:23
	 * @File:   DenominacionBean.java
	 * @param denominacion
	 */
	public void consultarDenominacion(Denominacion denominacion) {
		denominacionSeleccionada = denominacion;
		habilitaFormulario = true;
		lbEsConsulta = true;
		lbModificacion = false;
		lbEliminar = false;
		if(denominacionSeleccionada.getFlgActivo() != null) {
			if(denominacionSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   12 abr. 2019, 15:19:24
	 * @File:   DenominacionBean.java
	 */
	public void solicitarConfirmacionEliminarDenominacion(Denominacion denominacion) {
		denominacionSeleccionada = denominacion;
		lbEliminar = true;
		RequestContext.getCurrentInstance().execute("PF('dlgEliminarnDenominacion').show();");
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   12 abr. 2019, 15:18:55
	 * @File:   DenominacionBean.java
	 */
	public void guardarDenominacion() {
		denominacionSeleccionada.setAudFechaActualizacion(new Date());
		denominacionSeleccionada.setAudCodRol(new BigDecimal (getUsuarioSesion().getCodRol()));
		denominacionSeleccionada.setAudCodUsuario(new BigDecimal (getUsuarioSesion().getId()));
		
		if(lbModificacion) {
			denominacionSeleccionada.setAudAccion(codAudAccionUpdate);
		}else {
			denominacionSeleccionada.setAudAccion(codAudAccionInsert);
		}
		if(lbEliminar) {
			denominacionSeleccionada.setAudAccion(codAudAccionUpdate);
			denominacionSeleccionada.setFlgActivo((short) 0);
		}
		if(blnFlgActivo)
			denominacionSeleccionada.setFlgActivo((short)1);
		else
			denominacionSeleccionada.setFlgActivo((short)0);
		
		boolean denominacionDuplicado = validarDenominacionDuplicado();
		if(denominacionDuplicado) 
			return;
		
		Denominacion den = ComunicacionServiciosEnt.setDenominacion(denominacionSeleccionada);	
		if(!den.isError()) {
			if(lbEliminar) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_REGISTRO_ELIMINADO_CORRECTAMENTE);
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			}
			Denominacion denomincacion = new Denominacion();
			lstDenominacion = ComunicacionServiciosEnt.getDenomincacionesFiltro(denomincacion);
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
		}
		habilitaFormulario = false;
		lbModificacion = false;
		lbEliminar = false;
	}
	
	/**
	 * Metodo que valdia si un denominacion esta duplicado
	 * @return denominacionDuplicado
	 */
	public boolean validarDenominacionDuplicado() {
		boolean denominacionDuplicado = false;
		if(denominacionSeleccionada.getCodDenominacion()==null ||
				(denominacionSeleccionada.getCodDenominacion()!=null 
				&& !(denominacionSeleccionada.getNombreDenominacion().equals(strNombreDenominacionAnt)))) {
			Denominacion fil = new Denominacion();
			fil.setNombreDenominacion(denominacionSeleccionada.getNombreDenominacion());
			List<Denominacion> denominacion = ComunicacionServiciosSis.getDenominacionDuplicado(fil);
			if(denominacion != null && !denominacion.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_NOMBRE_DENOMINACION_DUPLICADO);
				denominacionDuplicado = true;
			}
		}
		return denominacionDuplicado;
	}
	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   12 abr. 2019, 15:20:00
	 * @File:   DenominacionBean.java
	 */
	public void aceptarConsulta() {
		habilitaFormulario = false;
		lbEsConsulta = false;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   12 abr. 2019, 16:44:57
	 * @File:   DenominacionBean.java
	 */
	public void buscarDenominacion() {
		Denominacion denomincacion = new Denominacion();
		denomincacion.setNombreDenominacion(denominacionSeleccionada.getNombreDenominacion());
		lstDenominacion = ComunicacionServiciosEnt.getDenomincacionesFiltro(denomincacion);
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   12 abr. 2019, 15:50:59
	 * @File:   DenominacionBean.java
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
			guardarDenominacion();
		}
		
		Denominacion denomincacion = new Denominacion();
		lstDenominacion = ComunicacionServiciosEnt.getDenomincacionesFiltro(denomincacion);
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
	 * @return the lstDenominacion
	 */
	public List<Denominacion> getLstDenominacion() {
		return lstDenominacion;
	}

	/**
	 * @param lstDenominacion the lstDenominacion to set
	 */
	public void setLstDenominacion(List<Denominacion> lstDenominacion) {
		this.lstDenominacion = lstDenominacion;
	}

	/**
	 * @return the denominacionSeleccionada
	 */
	public Denominacion getDenominacionSeleccionada() {
		return denominacionSeleccionada;
	}

	/**
	 * @param denominacionSeleccionada the denominacionSeleccionada to set
	 */
	public void setDenominacionSeleccionada(Denominacion denominacionSeleccionada) {
		this.denominacionSeleccionada = denominacionSeleccionada;
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
	 * @return the nombreDenominacion
	 */
	public String getNombreDenominacion() {
		return nombreDenominacion;
	}

	/**
	 * @param nombreDenominacion the nombreDenominacion to set
	 */
	public void setNombreDenominacion(String nombreDenominacion) {
		this.nombreDenominacion = nombreDenominacion;
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

	/**
	 * @return the strNombreDenominacionAnt
	 */
	public String getStrNombreDenominacionAnt() {
		return strNombreDenominacionAnt;
	}

	/**
	 * @param strNombreDenominacionAnt the strNombreDenominacionAnt to set
	 */
	public void setStrNombreDenominacionAnt(String strNombreDenominacionAnt) {
		this.strNombreDenominacionAnt = strNombreDenominacionAnt;
	}
	
}
