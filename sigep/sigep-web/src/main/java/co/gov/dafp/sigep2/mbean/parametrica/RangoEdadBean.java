/**
 * @Author: Jose Viscaya
 * @Date  : 8 may. 2019, 10:16:54
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
import co.gov.dafp.sigep2.entities.RangoEdad;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;

/**
 * @author: Jose Viscaya
 * @Date  : 8 may. 2019, 10:16:54
 */
@Named
@ViewScoped
@ManagedBean
public class RangoEdadBean extends BaseBean implements Serializable {
	
	
	private static final long serialVersionUID = 2664482953810991785L;
	private UsuarioDTO usuarioSesion;
	protected Integer codAudCodRol;
	protected Integer codAudAccionInsert = 3;
	protected Integer codAudAccionUpdate = 2;
	protected BigDecimal codAudUsuario;
	private List<RangoEdad> lstRangoEdad;
	private RangoEdad rangoEdadSeleccionada;
	
	
	private String strMensajeConfirmacion;
	private String strMensajeConfirmacionEliminacion;
	private String nombreRangoEdad;
	
	/*Variables para identificar si el rango ya esta creado*/
	private String nombreRangoAnt;
	private Short limitInferiorAnt;
	private Short limitSuperAnt;
	
	
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
		RangoEdad rangoEdad = new RangoEdad();
		lstRangoEdad = ComunicacionServiciosSis.getRangoedadFiltro(rangoEdad);
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 8, 2019, 8:14:48 AM
	 * @File:   RangoEdadBean.java
	 * @param entidadPrivQuery
	 * @return
	 */
	public List<String> listaRangoEdad(String entidadPrivQuery){
		List<String> filtroRangoEdad = new ArrayList<>();
		for(RangoEdad rangoEdad : lstRangoEdad ) {
			if(rangoEdad.getNombreRango().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
				filtroRangoEdad.add(rangoEdad.getNombreRango());
			}
		}
		return filtroRangoEdad;
   }
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 8, 2019, 8:16:48 AM
	 * @File:   RangoEdadBean.java
	 */
	public void filtrarRangoEdad() {
		RangoEdad rangoEdad = new RangoEdad();
		rangoEdad.setNombreRango(nombreRangoEdad);
		lstRangoEdad = ComunicacionServiciosSis.getRangoedadFiltro(rangoEdad);
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 8, 2019, 8:17:23 AM
	 * @File:   RangoEdadBean.java
	 */
	public void nuevaRangoEdad() {
		rangoEdadSeleccionada = new RangoEdad();
		habilitaFormulario = true;
		lbModificacion = false;
		blnFlgActivo = true;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 8, 2019, 8:18:11 AM
	 * @File:   RangoEdadBean.java
	 * @param RangoEdad
	 */
	public void modificarRangoEdad(RangoEdad rangoEdad) {
		rangoEdadSeleccionada = rangoEdad;
		nombreRangoAnt = rangoEdad.getNombreRango();
		limitInferiorAnt = rangoEdad.getLimiteInferior();
		limitSuperAnt = rangoEdad.getLimiteSuperior();
		habilitaFormulario = true;
		lbEsConsulta = false;
		lbModificacion = true;
		lbEliminar  = false;
		if(rangoEdadSeleccionada.getFlgActivo() != null) {
			if(rangoEdadSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 8, 2019, 8:18:57 AM
	 * @File:   RangoEdadBean.java
	 * @param RangoEdad
	 */
	public void consultarRangoEdad(RangoEdad rangoEdad) {
		rangoEdadSeleccionada = rangoEdad;
		habilitaFormulario = true;
		lbEsConsulta = true;
		lbModificacion = false;
		lbEliminar = false;
		if(rangoEdadSeleccionada.getFlgActivo() != null) {
			if(rangoEdadSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 8, 2019, 8:31:23 AM
	 * @File:   RangoEdadBean.java
	 * @param RangoEdad
	 */
	public void solicitarConfirmacionEliminarRangoEdad(RangoEdad rangoEdad) {
		rangoEdadSeleccionada = rangoEdad;
		lbEliminar = true;
		RequestContext.getCurrentInstance().execute("PF('dlgEliminarnRangoEdad').show();");
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 8, 2019, 8:31:20 AM
	 * @File:   RangoEdadBean.java
	 */
	public void guardarRangoEdad() {
		if(!lbEliminar) {
			if(rangoEdadSeleccionada.getLimiteSuperior() != null) {
				if(rangoEdadSeleccionada.getLimiteInferior() > rangoEdadSeleccionada.getLimiteSuperior()) {
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_RANGO_EDAD_LIMTEINFERIOR_MAYOR);
					RangoEdad rangoEdad = new RangoEdad();
					rangoEdad.setFlgActivo((short) 1);
					lstRangoEdad = ComunicacionServiciosSis.getRangoedadFiltro(rangoEdad);
					return;
				}
			}
		}
		if(lbModificacion) {
			rangoEdadSeleccionada.setAudAccion(codAudAccionUpdate);
		}else {
			rangoEdadSeleccionada.setAudAccion(codAudAccionInsert);
		}
		if(lbEliminar) {
			rangoEdadSeleccionada.setAudAccion(codAudAccionUpdate);
		}
		if(blnFlgActivo)
			rangoEdadSeleccionada.setFlgActivo((short) 1);
		else
			rangoEdadSeleccionada.setFlgActivo((short) 0);
		rangoEdadSeleccionada.setAudFechaActualizacion(new Date());
		rangoEdadSeleccionada.setAudCodRol(new BigDecimal (getUsuarioSesion().getCodRol()).intValue());
		rangoEdadSeleccionada.setAudCodUsuario(new BigDecimal (getUsuarioSesion().getId()));
		
		boolean rangoDuplicado = validarRangoDuplicado();
		if(rangoDuplicado) 
			return;
		
		RangoEdad den = ComunicacionServiciosSis.setRangoEdad(rangoEdadSeleccionada);	
		if(!den.isError()) {
			if(lbEliminar) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_REGISTRO_ELIMINADO_CORRECTAMENTE);
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			}
			RangoEdad rangoEdad = new RangoEdad();
			lstRangoEdad = ComunicacionServiciosSis.getRangoedadFiltro(rangoEdad);
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
	 * Metodo que valdia si un Rango Edad esta duplicado
	 * @return rangoDuplicado
	 */
	public boolean validarRangoDuplicado() {
		boolean rangoDuplicado = false;
		if(rangoEdadSeleccionada.getCodRangoEdad()==null ||
				(rangoEdadSeleccionada.getCodRangoEdad()!=null 
				&& (!(rangoEdadSeleccionada.getNombreRango().equals(nombreRangoAnt))
					||!(rangoEdadSeleccionada.getLimiteInferior().equals(limitInferiorAnt))
					||!(rangoEdadSeleccionada.getLimiteSuperior().equals(limitSuperAnt))
				    ))) {
			RangoEdad fil = new RangoEdad();
			fil.setNombreRango(rangoEdadSeleccionada.getNombreRango());
			fil.setLimiteInferior(rangoEdadSeleccionada.getLimiteInferior());
			fil.setLimiteSuperior(rangoEdadSeleccionada.getLimiteSuperior());
			
			List<RangoEdad> rangoEdad = ComunicacionServiciosSis.getRangoEdadDuplicado(fil);
			if(rangoEdad != null && !rangoEdad.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_RANGO_EDAD_DUPLICADO);
				rangoDuplicado = true;
			}
		}
		return rangoDuplicado;
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 8, 2019, 8:32:58 AM
	 * @File:   RangoEdadBean.java
	 */
	public void aceptarConsulta() {
		habilitaFormulario = false;
		lbEsConsulta = false;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 8, 2019, 8:34:24 AM
	 * @File:   RangoEdadBean.java
	 */
	public void buscarRangoEdad() {
		RangoEdad rangoEdad = new RangoEdad();
		rangoEdad.setNombreRango(rangoEdad.getNombreRango());
		lstRangoEdad = ComunicacionServiciosSis.getRangoedadFiltro(rangoEdad);
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 8, 2019, 8:35:01 AM
	 * @File:   RangoEdadBean.java
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
			guardarRangoEdad();
		}
		
		RangoEdad rangoEdad = new RangoEdad();
		lstRangoEdad = ComunicacionServiciosSis.getRangoedadFiltro(rangoEdad);
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
	 * @return the lstRangoEdad
	 */
	public List<RangoEdad> getLstRangoEdad() {
		return lstRangoEdad;
	}

	/**
	 * @param lstRangoEdad the lstRangoEdad to set
	 */
	public void setLstRangoEdad(List<RangoEdad> lstRangoEdad) {
		this.lstRangoEdad = lstRangoEdad;
	}

	/**
	 * @return the rangoEdadSeleccionada
	 */
	public RangoEdad getRangoEdadSeleccionada() {
		return rangoEdadSeleccionada;
	}

	/**
	 * @param rangoEdadSeleccionada the rangoEdadSeleccionada to set
	 */
	public void setRangoEdadSeleccionada(RangoEdad rangoEdadSeleccionada) {
		this.rangoEdadSeleccionada = rangoEdadSeleccionada;
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
	 * @return the nombreRangoEdad
	 */
	public String getNombreRangoEdad() {
		return nombreRangoEdad;
	}

	/**
	 * @param nombreRangoEdad the nombreRangoEdad to set
	 */
	public void setNombreRangoEdad(String nombreRangoEdad) {
		this.nombreRangoEdad = nombreRangoEdad;
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
	 * @return the nombreRangoAnt
	 */
	public String getNombreRangoAnt() {
		return nombreRangoAnt;
	}

	/**
	 * @param nombreRangoAnt the nombreRangoAnt to set
	 */
	public void setNombreRangoAnt(String nombreRangoAnt) {
		this.nombreRangoAnt = nombreRangoAnt;
	}

	/**
	 * @return the limitInferiorAnt
	 */
	public Short getLimitInferiorAnt() {
		return limitInferiorAnt;
	}

	/**
	 * @param limitInferiorAnt the limitInferiorAnt to set
	 */
	public void setLimitInferiorAnt(Short limitInferiorAnt) {
		this.limitInferiorAnt = limitInferiorAnt;
	}

	/**
	 * @return the limitSuperAnt
	 */
	public Short getLimitSuperAnt() {
		return limitSuperAnt;
	}

	/**
	 * @param limitSuperAnt the limitSuperAnt to set
	 */
	public void setLimitSuperAnt(Short limitSuperAnt) {
		this.limitSuperAnt = limitSuperAnt;
	}
}
