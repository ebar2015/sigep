/**
 * @Author: Jose Viscaya
 * @Date  : 9 may. 2019, 9:11:26
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
import co.gov.dafp.sigep2.entities.SituacionAdministrativa;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;

/**
 * @author: Jose Viscaya
 * @Date  : 9 may. 2019, 9:11:26
 */
@Named
@ViewScoped
@ManagedBean
public class SituacionAdministrativaBean extends BaseBean implements Serializable {

	
	private static final long serialVersionUID = -122986279978837189L;
	private UsuarioDTO usuarioSesion;
	protected Integer codAudCodRol;
	protected Integer codAudAccionInsert = 3;
	protected Integer codAudAccionUpdate = 2;
	protected BigDecimal codAudUsuario;
	private List<SituacionAdministrativa> lstSituacionAdministrativa;
	private SituacionAdministrativa situacionAdministrativaSeleccionada;
	
	private String strMensajeConfirmacion;
	private String strMensajeConfirmacionEliminacion;
	private String nombreSituacionAdministrativa;
	private String nombreSituacionAdminAnt;
	
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
		filtrarSituacionAdministrativa();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 13:21:51
	 * @File:   SituacionAdministrativaBean.java
	 * @param entidadPrivQuery
	 * @return
	 */
	public List<String> listaSituacionAdministrativa(String entidadPrivQuery){
		List<String> filtroSituacionAdministrativa = new ArrayList<>();
		for(SituacionAdministrativa SituacionAdministrativa : lstSituacionAdministrativa ) {
			if(SituacionAdministrativa.getNombreSituacion().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
				filtroSituacionAdministrativa.add(SituacionAdministrativa.getNombreSituacion());
			}
		}
		return filtroSituacionAdministrativa;
   }
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 13:21:45
	 * @File:   SituacionAdministrativaBean.java
	 */
	public void filtrarSituacionAdministrativa() {
		SituacionAdministrativa situacionAdministrativa = new SituacionAdministrativa();
		situacionAdministrativa.setFlgEsgenerica((short) 1);
		situacionAdministrativa.setNombreSituacion(nombreSituacionAdministrativa);
		lstSituacionAdministrativa = ComunicacionServiciosAdmin.getSituacionAdministrativaFiltro(situacionAdministrativa);
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 13:21:58
	 * @File:   SituacionAdministrativaBean.java
	 */
	public void nuevaSituacionAdministrativa() {
		situacionAdministrativaSeleccionada = new SituacionAdministrativa();
		habilitaFormulario = true;
		lbModificacion = false;
		blnFlgActivo = true;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 13:22:01
	 * @File:   SituacionAdministrativaBean.java
	 * @param situacionAdministrativa
	 */
	public void modificarSituacionAdministrativa(SituacionAdministrativa situacionAdministrativa) {
		situacionAdministrativaSeleccionada = situacionAdministrativa;
		nombreSituacionAdminAnt = situacionAdministrativa.getNombreSituacion();
		habilitaFormulario = true;
		lbEsConsulta = false;
		lbModificacion = true;
		lbEliminar  = false;
		if(situacionAdministrativaSeleccionada.getFlgActivo() != null) {
			if(situacionAdministrativaSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 13:22:04
	 * @File:   SituacionAdministrativaBean.java
	 * @param situacionAdministrativa
	 */
	public void consultarSituacionAdministrativa(SituacionAdministrativa situacionAdministrativa) {
		situacionAdministrativaSeleccionada = situacionAdministrativa;
		habilitaFormulario = true;
		lbEsConsulta = true;
		lbModificacion = false;
		lbEliminar = false;
		if(situacionAdministrativaSeleccionada.getFlgActivo() != null) {
			if(situacionAdministrativaSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 13:22:08
	 * @File:   SituacionAdministrativaBean.java
	 * @param situacionAdministrativa
	 */
	public void solicitarConfirmacionEliminarSituacionAdministrativa(SituacionAdministrativa situacionAdministrativa) {
		situacionAdministrativaSeleccionada = situacionAdministrativa;
		lbEliminar = true;
		RequestContext.getCurrentInstance().execute("PF('dlgEliminarnSituacionAdministrativa').show();");
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 13:22:11
	 * @File:   SituacionAdministrativaBean.java
	 */
	public void guardarSituacionAdministrativa() {
		situacionAdministrativaSeleccionada.setAudFechaActualizacion(new Date());
		situacionAdministrativaSeleccionada.setAudCodRol(new BigDecimal (getUsuarioSesion().getCodRol()).intValue());
		situacionAdministrativaSeleccionada.setAudCodUsuario(new BigDecimal (getUsuarioSesion().getId()));
		if(lbModificacion) {
			situacionAdministrativaSeleccionada.setAudAccion(codAudAccionUpdate);
		}else {
			situacionAdministrativaSeleccionada.setAudAccion(codAudAccionInsert);
			situacionAdministrativaSeleccionada.setFlgEsgenerica((short) 1);
		}
		if(lbEliminar) {
			situacionAdministrativaSeleccionada.setAudAccion(codAudAccionUpdate);
		}
		if(blnFlgActivo)
			situacionAdministrativaSeleccionada.setFlgActivo((short)1);
		else
			situacionAdministrativaSeleccionada.setFlgActivo((short)0);
	
		boolean situacionAdministrativaDuplicado = validarSituacionDuplicada();
		if(situacionAdministrativaDuplicado) 
			return;
		
		SituacionAdministrativa den = ComunicacionServiciosAdmin.setSituacionAdministrativa(situacionAdministrativaSeleccionada);	
		
		if(!den.isError()) {
			if(lbEliminar)
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_REGISTRO_ELIMINADO_CORRECTAMENTE);
			else
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			
			SituacionAdministrativa SituacionAdministrativa = new SituacionAdministrativa();
			lstSituacionAdministrativa = ComunicacionServiciosAdmin.getSituacionAdministrativaFiltro(SituacionAdministrativa);
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ERROR_GUARDAR);
		}

		habilitaFormulario = false;
		lbModificacion = false;
		lbEliminar = false;
	}
	
	/**
	 * Metodo que valdia si una situacion administrativa esta duplicada
	 * @return situacionAdminDuplicada
	 */
	public boolean validarSituacionDuplicada() {
		boolean situacionAdminDuplicada = false;
		if(situacionAdministrativaSeleccionada.getCodSituacionAdministrativa()==null ||
				(situacionAdministrativaSeleccionada.getCodSituacionAdministrativa()!=null && 
				!(situacionAdministrativaSeleccionada.getNombreSituacion().equals(nombreSituacionAdminAnt)))) {
			SituacionAdministrativa fil = new SituacionAdministrativa();
			fil.setNombreSituacion(situacionAdministrativaSeleccionada.getNombreSituacion());
			List<SituacionAdministrativa> situacion = ComunicacionServiciosSis.getSituacionesDuplicado(fil);
			if(situacion != null && !situacion.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_NOMBRE_SITUACIONADMIN_DUPLICADO);
				situacionAdminDuplicada = true;
			}
		}
		return situacionAdminDuplicada;
	}
	
	
	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 13:22:18
	 * @File:   SituacionAdministrativaBean.java
	 */
	public void aceptarConsulta() {
		habilitaFormulario = false;
		lbEsConsulta = false;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 13:22:21
	 * @File:   SituacionAdministrativaBean.java
	 */
	public void buscarSituacionAdministrativa() {
		SituacionAdministrativa situacionAdministrativa = new SituacionAdministrativa();
		situacionAdministrativa.setFlgEsgenerica((short) 1);
		situacionAdministrativa.setNombreSituacion(situacionAdministrativaSeleccionada.getNombreSituacion());
		lstSituacionAdministrativa = ComunicacionServiciosAdmin.getSituacionAdministrativaFiltro(situacionAdministrativa);
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 13:22:24
	 * @File:   SituacionAdministrativaBean.java
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
			guardarSituacionAdministrativa();
		}
	}

	@Override
	public String persist()  {
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
	 * @return the lstSituacionAdministrativa
	 */
	public List<SituacionAdministrativa> getLstSituacionAdministrativa() {
		return lstSituacionAdministrativa;
	}

	/**
	 * @param lstSituacionAdministrativa the lstSituacionAdministrativa to set
	 */
	public void setLstSituacionAdministrativa(List<SituacionAdministrativa> lstSituacionAdministrativa) {
		this.lstSituacionAdministrativa = lstSituacionAdministrativa;
	}

	/**
	 * @return the situacionAdministrativaSeleccionada
	 */
	public SituacionAdministrativa getsituacionAdministrativaSeleccionada() {
		return situacionAdministrativaSeleccionada;
	}

	/**
	 * @param situacionAdministrativaSeleccionada the situacionAdministrativaSeleccionada to set
	 */
	public void setsituacionAdministrativaSeleccionada(SituacionAdministrativa situacionAdministrativaSeleccionada) {
		this.situacionAdministrativaSeleccionada = situacionAdministrativaSeleccionada;
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
	 * @return the nombreSituacionAdministrativa
	 */
	public String getNombreSituacionAdministrativa() {
		return nombreSituacionAdministrativa;
	}

	/**
	 * @param nombreSituacionAdministrativa the nombreSituacionAdministrativa to set
	 */
	public void setNombreSituacionAdministrativa(String nombreSituacionAdministrativa) {
		this.nombreSituacionAdministrativa = nombreSituacionAdministrativa;
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
	 * @return the nombreSituacionAdminAnt
	 */
	public String getNombreSituacionAdminAnt() {
		return nombreSituacionAdminAnt;
	}

	/**
	 * @param nombreSituacionAdminAnt the nombreSituacionAdminAnt to set
	 */
	public void setNombreSituacionAdminAnt(String nombreSituacionAdminAnt) {
		this.nombreSituacionAdminAnt = nombreSituacionAdminAnt;
	}
	
}
