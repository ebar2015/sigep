/**
 * @Author: Jose Viscaya
 * @Date  : 8 may. 2019, 13:45:11
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

import com.ibm.icu.text.SimpleDateFormat;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Festivo;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;

/**
 * @author: Jose Viscaya
 * @Date  : 8 may. 2019, 13:45:11
 */
@Named
@ViewScoped
@ManagedBean
public class FestivoBean extends BaseBean implements Serializable {

	
	private static final long serialVersionUID = 8297870621561086548L;
	private UsuarioDTO usuarioSesion;
	protected Integer codAudCodRol;
	protected Integer codAudAccionInsert = 3;
	protected Integer codAudAccionUpdate = 2;
	protected Integer codAudAccionDelete = 62;
	protected BigDecimal codAudUsuario;
	private List<Festivo> lstFestivo;
	private Festivo festivoSeleccionada;
	
	private String strMensajeConfirmacion;
	private String strMensajeConfirmacionEliminacion;
	private String anio;
	
	private boolean habilitaFormulario;
	private boolean lbEsConsulta;
	private boolean lbModificacion;
	private boolean lbEliminar;
	private SimpleDateFormat formatAnio = new SimpleDateFormat("yyyy");
	
	private Date fechaFeriadoAnt;
	
	private boolean blnFlgActivo;
	
	
	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@PostConstruct
	public void init()  {
		blnFlgActivo= false;
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		usuarioSesion = (UsuarioDTO) externalContext.getSessionMap().get("usuarioSesion");	
		codAudUsuario = BigDecimal.valueOf(usuarioSesion.getId());
		codAudCodRol  = (int) this.getRolAuditoria().getId();
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		codAudAccionDelete = 62;
		strMensajeConfirmacion = MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_HV_GP_SOLICITAR_CANCELAR, getLocale());
		strMensajeConfirmacionEliminacion =  MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_PREGUNTA_ELIMINAR_REGISTRO, getLocale());
		Festivo festivo = new Festivo();
		lstFestivo = ComunicacionServiciosSis.getFestivoFiltro(festivo);
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
		
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 15:56:13
	 * @File:   FestivoBean.java
	 * @param entidadPrivQuery
	 * @return
	 */
	public List<String> listaFestivo(String entidadPrivQuery){
		List<String> filtroFestivo = new ArrayList<>();
		for(Festivo festivo : lstFestivo ) {
			if(isNumber(entidadPrivQuery)) {
				if(festivo.getAnio().equals(new Short(entidadPrivQuery))) {
					filtroFestivo.add(Integer.toString(festivo.getAnio()));
				}
			}else {
				if(festivo.getDescripcion().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
					filtroFestivo.add(Integer.toString(festivo.getAnio()));
				}
			}
		}
		return filtroFestivo;
   }
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 15:56:17
	 * @File:   FestivoBean.java
	 */
	public void filtrarFestivo() {
		Festivo festivo = new Festivo();
		if(isNumber(anio)) {
			festivo.setAnio(new Short(anio));
		}else {
			festivo.setDescripcion(anio);
		}
		lstFestivo = ComunicacionServiciosSis.getFestivoFiltro(festivo);
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 17:05:35
	 * @File:   FestivoBean.java
	 */
	public void nuevaFestivo() {
		festivoSeleccionada = new Festivo();
		habilitaFormulario = true;
		lbModificacion = false;
		blnFlgActivo= true;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 17:05:45
	 * @File:   FestivoBean.java
	 * @param festivo
	 */
	public void modificarFestivo(Festivo festivo) {
		festivoSeleccionada = festivo;
		fechaFeriadoAnt = festivo.getFechaFeriado();
		habilitaFormulario = true;
		lbEsConsulta = false;
		lbModificacion = true;
		lbEliminar  = false;
		if(festivoSeleccionada.getFlgActivo() != null) {
			if(festivoSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 17:05:50
	 * @File:   FestivoBean.java
	 * @param festivo
	 */
	public void consultarFestivo(Festivo festivo) {
		festivoSeleccionada = festivo;
		habilitaFormulario = true;
		lbEsConsulta = true;
		lbModificacion = false;
		lbEliminar = false;
		if(festivoSeleccionada.getFlgActivo() != null) {
			if(festivoSeleccionada.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 17:05:56
	 * @File:   FestivoBean.java
	 * @param festivo
	 */
	public void solicitarConfirmacionEliminarFestivo(Festivo festivo) {
		festivoSeleccionada = festivo;
		lbEliminar = true;
		RequestContext.getCurrentInstance().execute("PF('dlgEliminarnFestivo').show();");
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 17:05:59
	 * @File:   FestivoBean.java
	 */
	public void guardarFestivo() {
		festivoSeleccionada.setAudFechaActualizacion(new Date());
		festivoSeleccionada.setAudCodRol(new BigDecimal (getUsuarioSesion().getCodRol()).intValue());
		festivoSeleccionada.setAudCodUsuario(new BigDecimal (getUsuarioSesion().getId()));
		
		if(lbModificacion) {
			festivoSeleccionada.setAudAccion(codAudAccionUpdate);
			festivoSeleccionada.setAnio(new Short(formatAnio.format(festivoSeleccionada.getFechaFeriado())));
		}else {
			festivoSeleccionada.setAudAccion(codAudAccionInsert);
			festivoSeleccionada.setAnio(new Short(formatAnio.format(festivoSeleccionada.getFechaFeriado())));
		}
		if(lbEliminar) {
			festivoSeleccionada.setAudAccion(codAudAccionDelete);
		}
		
		if(blnFlgActivo)
			festivoSeleccionada.setFlgActivo((short)1);
		else
			festivoSeleccionada.setFlgActivo((short)0);
		
		boolean festivoDuplicado = validarFestivoDuplicado();
		if(festivoDuplicado) 
			return;
		
		
		Festivo den = ComunicacionServiciosSis.setFestivo(festivoSeleccionada);	
		if(!den.isError()) {
			if(lbEliminar) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_REGISTRO_ELIMINADO_CORRECTAMENTE);
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			}
			Festivo festivo = new Festivo();
			lstFestivo = ComunicacionServiciosSis.getFestivoFiltro(festivo);
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
		}
		habilitaFormulario = false;
		lbModificacion = false;
		lbEliminar = false;
	}
	
	/**
	 * Metodo que valdia si un Festivo esta duplicado
	 * @return festivoDuplicado
	 */
	public boolean validarFestivoDuplicado() {
		boolean festivoDuplicado = false;
		if(festivoSeleccionada.getCodFeriado()==null ||
				(festivoSeleccionada.getCodFeriado()!=null && !(festivoSeleccionada.getFechaFeriado().equals(fechaFeriadoAnt)))) {
			Festivo fil = new Festivo();
			fil.setFechaFeriado(festivoSeleccionada.getFechaFeriado());
			List<Festivo> isFestivo = ComunicacionServiciosSis.getFestivoFiltro(fil);
			if(isFestivo != null && !isFestivo.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FESTIVO_DUPLICADO);
				festivoDuplicado = true;
			}
		}
		return festivoDuplicado;
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 17:06:04
	 * @File:   FestivoBean.java
	 */
	public void aceptarConsulta() {
		habilitaFormulario = false;
		lbEsConsulta = false;
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 17:06:08
	 * @File:   FestivoBean.java
	 */
	public void buscarFestivo() {
		Festivo festivo = new Festivo();
		festivo.setAnio(festivoSeleccionada.getAnio());
		lstFestivo = ComunicacionServiciosSis.getFestivoFiltro(festivo);
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 17:06:11
	 * @File:   FestivoBean.java
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
			guardarFestivo();
		}
		
		Festivo festivo = new Festivo();
		lstFestivo = ComunicacionServiciosSis.getFestivoFiltro(festivo);
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
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   8 may. 2019, 15:11:49
	 * @File:   FestivoBean.java
	 * @param value
	 * @return
	 */
	private boolean isNumber(String value) {
		int val = 0;
		try {
			val = Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
		
	}
	
	/**
	 * @return the lstFestivo
	 */
	public List<Festivo> getLstFestivo() {
		return lstFestivo;
	}

	/**
	 * @param lstFestivo the lstFestivo to set
	 */
	public void setLstFestivo(List<Festivo> lstFestivo) {
		this.lstFestivo = lstFestivo;
	}

	/**
	 * @return the festivoSeleccionada
	 */
	public Festivo getfestivoSeleccionada() {
		return festivoSeleccionada;
	}

	/**
	 * @param festivoSeleccionada the festivoSeleccionada to set
	 */
	public void setfestivoSeleccionada(Festivo festivoSeleccionada) {
		this.festivoSeleccionada = festivoSeleccionada;
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
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
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
	 * @return the fechaFeriadoAnt
	 */
	public Date getFechaFeriadoAnt() {
		return fechaFeriadoAnt;
	}

	/**
	 * @param fechaFeriadoAnt the fechaFeriadoAnt to set
	 */
	public void setFechaFeriadoAnt(Date fechaFeriadoAnt) {
		this.fechaFeriadoAnt = fechaFeriadoAnt;
	}
	
	
}
