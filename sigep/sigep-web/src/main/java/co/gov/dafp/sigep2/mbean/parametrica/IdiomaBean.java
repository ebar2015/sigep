/**
 * @Author: Jose Viscaya
 * @Date  : May 9, 2019, 7:43:21 AM
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
import co.gov.dafp.sigep2.entities.Idioma;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;

/**
 * @author: Jose Viscaya
 * @Date  : May 9, 2019, 7:43:21 AM
 */
@Named
@ViewScoped
@ManagedBean
public class IdiomaBean extends BaseBean implements Serializable {

	
	private static final long serialVersionUID = -5998748074099866136L;
	private UsuarioDTO usuarioSesion;
	protected Integer codAudCodRol;
	protected Integer codAudAccionInsert = 3;
	protected Integer codAudAccionUpdate = 2;
	protected BigDecimal codAudUsuario;
	private List<Idioma> lstIdioma;
	private Idioma idiomaSeleccionada;
	
	private String nombreIdiomaAnt;
	
	private String strMensajeConfirmacion;
	private String strMensajeConfirmacionEliminacion;
	private String nombreIdioma;
	
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
		Idioma idioma = new Idioma();
		lstIdioma = ComunicacionServiciosSis.getIdiomaFiltro(idioma);
		blnFlgActivo = false;
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 9:32:21
	 * @File:   IdiomaBean.java
	 * @param entidadPrivQuery
	 * @return
	 */
	public List<String> listaIdioma(String entidadPrivQuery){
		List<String> filtroIdioma = new ArrayList<>();
		for(Idioma Idioma : lstIdioma ) {
			if(Idioma.getNombreIdioma().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
				filtroIdioma.add(Idioma.getNombreIdioma());
			}
		}
		return filtroIdioma;
   }
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 9:32:25
	 * @File:   IdiomaBean.java
	 */
	public void filtrarIdioma() {
		Idioma idioma = new Idioma();
		idioma.setNombreIdioma(nombreIdioma);
		lstIdioma = ComunicacionServiciosSis.getIdiomaFiltro(idioma);
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 9:32:28
	 * @File:   IdiomaBean.java
	 */
	public void nuevaIdioma() {
		idiomaSeleccionada = new Idioma();
		habilitaFormulario = true;
		lbModificacion = false;
		blnFlgActivo = true;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 9:32:32
	 * @File:   IdiomaBean.java
	 * @param Idioma
	 */
	public void modificarIdioma(Idioma Idioma) {
		idiomaSeleccionada = Idioma;
		nombreIdiomaAnt = Idioma.getNombreIdioma();
		habilitaFormulario = true;
		lbEsConsulta = false;
		lbModificacion = true;
		lbEliminar  = false;
		if(idiomaSeleccionada.getFlgActivo() != null) {
			if(idiomaSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 9:32:35
	 * @File:   IdiomaBean.java
	 * @param Idioma
	 */
	public void consultarIdioma(Idioma Idioma) {
		idiomaSeleccionada = Idioma;
		habilitaFormulario = true;
		lbEsConsulta = true;
		lbModificacion = false;
		lbEliminar = false;
		if(idiomaSeleccionada.getFlgActivo() != null) {
			if(idiomaSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 9:32:39
	 * @File:   IdiomaBean.java
	 * @param Idioma
	 */
	public void solicitarConfirmacionEliminarIdioma(Idioma Idioma) {
		idiomaSeleccionada = Idioma;
		lbEliminar = true;
		RequestContext.getCurrentInstance().execute("PF('dlgEliminarnIdioma').show();");
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 9:32:42
	 * @File:   IdiomaBean.java
	 */
	public void guardarIdioma() {
		idiomaSeleccionada.setAudFechaActualizacion(new Date());
		idiomaSeleccionada.setAudCodRol(new BigDecimal (getUsuarioSesion().getCodRol()).intValue());
		idiomaSeleccionada.setAudCodUsuario(new BigDecimal (getUsuarioSesion().getId()));
		if(lbModificacion) 
			idiomaSeleccionada.setAudAccion(codAudAccionUpdate);
		else 
			idiomaSeleccionada.setAudAccion(codAudAccionInsert);
		
		if(lbEliminar) {
			idiomaSeleccionada.setAudAccion(codAudAccionUpdate);
			idiomaSeleccionada.setFlgActivo((short) 0);
		}
		if(blnFlgActivo)
			idiomaSeleccionada.setFlgActivo((short)1);
		else
			idiomaSeleccionada.setFlgActivo((short)0);
		
		boolean idiomaDuplicado = validarIdiomaDuplicado();
		if(idiomaDuplicado) 
			return;
	
		Idioma den = ComunicacionServiciosSis.setIdioma(idiomaSeleccionada);	
		
		if(!den.isError()) {
			if(lbEliminar) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_REGISTRO_ELIMINADO_CORRECTAMENTE);
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			}
			Idioma idioma = new Idioma();
			lstIdioma = ComunicacionServiciosSis.getIdiomaFiltro(idioma);
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ERROR_GUARDAR);
		}
		habilitaFormulario = false;
		lbModificacion = false;
		lbEliminar = false;
	}
	
	/**
	 * Metodo que valdia si un idioma esta duplicado
	 * @return idiomaDuplicado
	 */
	public boolean validarIdiomaDuplicado() {
		boolean idiomaDuplicado = false;
		if(idiomaSeleccionada.getCodIdioma()==null ||
				!(idiomaSeleccionada.getCodIdioma()!=null && (idiomaSeleccionada.getNombreIdioma().equals(nombreIdiomaAnt)))) {
			Idioma fil = new Idioma();
			fil.setNombreIdioma(idiomaSeleccionada.getNombreIdioma());
			List<Idioma> idioma = ComunicacionServiciosSis.getIdiomaDuplicado(fil);
			if(idioma != null && !idioma.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_NOMBRE_IDIOMA_DUPLICADO);
				idiomaDuplicado = true;
			}
		}
		return idiomaDuplicado;
	}
	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 9:32:48
	 * @File:   IdiomaBean.java
	 */
	public void aceptarConsulta() {
		habilitaFormulario = false;
		lbEsConsulta = false;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 9:32:53
	 * @File:   IdiomaBean.java
	 */
	public void buscarIdioma() {
		Idioma idioma = new Idioma();
		idioma.setNombreIdioma(idiomaSeleccionada.getNombreIdioma());
		lstIdioma = ComunicacionServiciosSis.getIdiomaFiltro(idioma);
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 9:32:57
	 * @File:   IdiomaBean.java
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
			guardarIdioma();
		}
		
		Idioma idioma = new Idioma();
		lstIdioma = ComunicacionServiciosSis.getIdiomaFiltro(idioma);

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
	 * @return the lstIdioma
	 */
	public List<Idioma> getLstIdioma() {
		return lstIdioma;
	}

	/**
	 * @param lstIdioma the lstIdioma to set
	 */
	public void setLstIdioma(List<Idioma> lstIdioma) {
		this.lstIdioma = lstIdioma;
	}

	/**
	 * @return the idiomaSeleccionada
	 */
	public Idioma getIdiomaSeleccionada() {
		return idiomaSeleccionada;
	}

	/**
	 * @param idiomaSeleccionada the idiomaSeleccionada to set
	 */
	public void setIdiomaSeleccionada(Idioma idiomaSeleccionada) {
		this.idiomaSeleccionada = idiomaSeleccionada;
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
	 * @return the nombreIdioma
	 */
	public String getNombreIdioma() {
		return nombreIdioma;
	}

	/**
	 * @param nombreIdioma the nombreIdioma to set
	 */
	public void setNombreIdioma(String nombreIdioma) {
		this.nombreIdioma = nombreIdioma;
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
	 * @return the nombreIdiomaAnt
	 */
	public String getNombreIdiomaAnt() {
		return nombreIdiomaAnt;
	}

	/**
	 * @param nombreIdiomaAnt the nombreIdiomaAnt to set
	 */
	public void setNombreIdiomaAnt(String nombreIdiomaAnt) {
		this.nombreIdiomaAnt = nombreIdiomaAnt;
	}
}
