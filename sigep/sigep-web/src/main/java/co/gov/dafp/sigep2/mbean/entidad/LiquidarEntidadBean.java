package co.gov.dafp.sigep2.mbean.entidad;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.EntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ManagedBean
@ViewScoped
public class LiquidarEntidadBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 5131929950282880884L;

	private Long codEntidad;
	private Long codTipoNorma = 0L;
	private List<Norma> listaNormas;// Lista de entidades
	private Norma tipoNorma;
	private Date fechaNorma;
	private ExternalContext externalContext;
	private Entidad entidadActual;
	private List<VinculacionExt> listPersVinc;
	private List<VinculacionExt> listVinculaciones;
	private Entidad paramEntidad = null;
	private String strMensajeLiquidar = "";


	public LiquidarEntidadBean() {
		entidadActual = new Entidad();
		init();
	}

	public void cargarEntidad(Entidad entidadActual) {

		entidadActual = ComunicacionServiciosEnt.getEntidadPorId(entidadActual.getCodEntidad().longValue());

	}
	
	@Override
	public void init() {
		cerrarSessionFuncion(AMBITO_POLITICAS);
		if(!validarPermisoRolLiquidarEntidad()){
    		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR, getLocale()),
					"window.location.assign('entidad/../../gestionarEntidad.xhtml?faces-redirect=true')"); 
    		return;
		}			
		tipoNorma = new Norma();
		tipoNorma.setCodTipoNorma(0);
		entidadActual = new Entidad();
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		entidadActual = (Entidad) externalContext.getSessionMap().get("entidadLiquidar");
		tipoNorma = new Norma();
		cargarEntidad(entidadActual);
		cargarEntDesv();
		strMensajeLiquidar =MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.DLG_LIQUIDAR_ENTIDAD_MSG, getLocale())
				.replace("%ENTIDAD%", entidadActual.getNombreEntidad())
				.replace("%CODIGO%", entidadActual.getCodigoSigep());
	}

	public Entidad getEntidadActual() {
		return entidadActual;
	}

	public void setEntidadActual(Entidad entidadActual) {
		this.entidadActual = entidadActual;
	}

	public Long getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}

	public List<VinculacionExt> getListVinculaciones() {
		return listVinculaciones;
	}

	public void setListVinculaciones(List<VinculacionExt> listVinculaciones) {
		this.listVinculaciones = listVinculaciones;
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

	public Norma getTipoNorma() {
		return tipoNorma;
	}

	public void setTipoNorma(Norma tipoNorma) {
		this.tipoNorma = tipoNorma;
	}

	public List<Norma> recuperarNumeroNorma() {
		if (this.codTipoNorma != null)
			this.listaNormas = ComunicacionServiciosEnt.getNorma(this.codTipoNorma);

		return listaNormas;
	}

	ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	GestionEntidadBean mGestionEntidadBean = (GestionEntidadBean) elContext.getELResolver().getValue(elContext, null,
			"gestionEntidadBean");

	public void liquidarEntidad() throws IOException {
		Long codEntidadActual = null;
		if(entidadActual.getCodEntidad()!=null)
			codEntidadActual = entidadActual.getCodEntidad().longValue();
		if (codEntidadActual != null) {
			VinculacionExt viculacionActual = new VinculacionExt();
			viculacionActual.setCodCausalDesvinculacion(new BigDecimal(TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue()));
			viculacionActual.setCodEntidad(codEntidadActual);
			viculacionActual.setFlgActivo((short) 1);
			listVinculaciones = ComunicacionServiciosVin.getMostrarVinculaciones(viculacionActual);
			// si no tiene vinculaciones
			if (listVinculaciones == null || listVinculaciones.isEmpty()) {
				BigDecimal codEntidad = new BigDecimal(codEntidadActual);
				BigDecimal estadoEntidad = new BigDecimal(1481);
				// cambiamos el estado de la entidad a LIQUIDADA
				EntidadExt Ent = new EntidadExt();
				Ent.setCodEntidad(codEntidad);
				Ent.setCodEstadoEntidad(estadoEntidad);
				Ent.setCodSubestadoEntidad(1485);
				Ent.setAudFechaActualizacion(DateUtils.getFechaSistema());
				Ent.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
				Ent.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
				Ent.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
				Entidad resultado = ComunicacionServiciosEnt.setEntidade(Ent);
				if (resultado.getMensaje().equals("Actualizacion Existosa.")) {
					mostrarMensajeBotonAccion(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_LIQUIDAR_ENTIDAD_EXITO, TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR, getLocale()), "window.location.assign('../gestionarEntidad.xhtml')");
				} else {
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "No ha sido pósible liquidar la entidad: " + paramEntidad.getNombreEntidad());
				}
			}else {
				// mostramos el mensaje de si desea liquidar y hacer desvinculacion automática
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("$('#dialogLiquidarEntidades').modal('show')");
			}
		} else  {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,	"No ha seleccionado ninguna entidad");
		}
		
	}

	public void desactivaEntVincAutomatica() throws IOException {
		Long codEntidadActual = entidadActual.getCodEntidad().longValue();
		String usuariosActivos = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if (codEntidadActual != null) {
			paramEntidad = ComunicacionServiciosEnt.getEntidadPorId(codEntidadActual);
			if (listVinculaciones != null) {
				for (VinculacionExt listaVinc : listVinculaciones) {
					Persona persona = ComunicacionServiciosHV.getPersonaPorId(listaVinc.getCodPersona().longValue());
					String nombrePersona = persona.getPrimerNombre().toUpperCase() + " " + persona.getPrimerApellido().toUpperCase();
					usuariosActivos = usuariosActivos + nombrePersona + ", "; 
					VinculacionExt a = new VinculacionExt();
					a.setCodCausalDesvinculacion(new BigDecimal(TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue()));
					a.setFlgActivo((short) 0);
					a.setFechaActoAdminDesvinculacion(new Date());
					a.setCodTipoActoAdminDesvinculacion(null);
					a.setCodVinculacion(listaVinc.getCodVinculacion());
					a.setFechaFinalizacion(new Date());
					a.setCodEntidadPlantaDetalle(listaVinc.getCodEntidadPlantaDetalle());
					a.setFlgMedicoDocente(listaVinc.getFlgMedicoDocente());
					BigDecimal lECodRol = new BigDecimal(getUsuarioSesion().getCodRol());
					BigDecimal IEUsuarioId = new BigDecimal(getUsuarioSesion().getId());
					a.setAudAccion((int) TipoAccionEnum.UPDATE.getIdAccion());
					a.setAudCodRol(lECodRol);
					a.setAudCodUsuario(IEUsuarioId);
					a.setAudFechaActualizacion(DateUtils.getFechaSistema());
					ComunicacionServiciosVin.setVinculacion(a);
				}
				usuariosActivos = usuariosActivos.substring(0, usuariosActivos.length() - 2);
				BigDecimal codEntidad = new BigDecimal(codEntidadActual);
				BigDecimal estadoEntidad = new BigDecimal(1481);
				// cambiamos el estado de la entidad a ENLIQUIDACIÓN
				EntidadExt Ent = new EntidadExt();
				Ent.setCodEntidad(codEntidad);
				Ent.setCodEstadoEntidad(estadoEntidad);
				Ent.setCodSubestadoEntidad(1485);
				Ent.setAudFechaActualizacion(DateUtils.getFechaSistema());
				Ent.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
				Ent.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
	            Ent.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
				Entidad resultado = ComunicacionServiciosEnt.setEntidade(Ent);
				if (resultado.getMensaje().equals("Actualizacion Existosa.")) {
					// Cerramos el modal de desea liquidar y hacer desvinculacion automática
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("$('#dialogLiquidarEntidades').modal('hide')");
					/**
					 * Manda correo electronico a los roles descritos.
					 */
					PersonaExt personaRoles = new PersonaExt();
					Integer[] roles = new Integer[1];
					roles[0] = 8;
					personaRoles.setCodRolList(roles);

					List<PersonaExt> listPersonas = ComunicacionServiciosEnt.getPersonasPorRoles(personaRoles);
					if (!listPersonas.isEmpty()) {
						String asunto = MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.MSG_INFO_ASUNTO_LIQUIDACION_ENTIDAD, getLocale());
						String mensaje = MessagesBundleConstants
								.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_MENSAJE_LIQUIDACION_ENTIDAD,
										getLocale())
								.replace("%nombreEntidad%", entidadActual.getNombreEntidad())
								.replace("%usuariosAsociados%", usuariosActivos);
						for (PersonaExt personaExt : listPersonas) {
							ConnectionHttp.sendEmail(personaExt.getCorreoElectronico(), mensaje, asunto);
						}
					}
					mostrarMensajeBotonAccion(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,  "Liquidación exitosa- La entidad quedó en estado (en liquidaci\\u00f3n o liquidada), se desvincularon: " + listVinculaciones.size() + " persona(s) con la fecha de liquidación de la entidad", 
							TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR, getLocale()), "window.location.assign('../gestionarEntidad.xhtml')");
					
					
				} else {
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							"No ha sido posible liquidar la entidad: " + paramEntidad.getNombreEntidad());
				}
			}
		}
	}

	// desvinculaciones
	public void cargarEntDesv() {
		Long codEntidadActual = entidadActual.getCodEntidad().longValue();
		VinculacionExt e = new VinculacionExt();
		e.setCodEntidad(codEntidadActual);
		e.setFlgActivo((short) 1);
		listPersVinc = ComunicacionServiciosVin.getVinculacionExportacion(e);
	}

	/**
	 * metodo utilizado para obtener los numeros de norma de una entidad.
	 * @param tipoNorma
	 * @param fechaNorma
	 * @return
	 */
	public List<SelectItem> getNumerosNorma(Long tipoNorma, Date fechaNorma){
		List<SelectItem> list = new ArrayList<>();
		Norma norma = new Norma();
		if(tipoNorma != null) {	
			norma.setCodTipoNorma(tipoNorma.intValue());
		}
		norma.setFechaNorma(fechaNorma);
		
		List<Norma> listNorma = ComunicacionServiciosEnt.getFiltroNorma(norma);
		try {
			if (!listNorma.isEmpty()) {
				for (Norma aux : listNorma) {
					list.add(new SelectItem(aux.getNumeroNorma(), aux.getNumeroNorma()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}
		return list;
	}

	public List<VinculacionExt> getListPersVinc() {
		return listPersVinc;
	}

	public void setListPersVinc(PersonaExt entidadActual) {
		this.listPersVinc = listPersVinc;
	}

	public void mostrarDialogoLiquidarEntidad() {
		RequestContext.getCurrentInstance()
				.execute("$('#divDesviculacionesEnt').attr('style','display:display:block');");
	}

	public Date getFechaNorma() {
		return fechaNorma;
	}

	public void setFechaNorma(Date fechaNorma) {
		this.fechaNorma = fechaNorma;
	}


    private boolean validarPermisoRolLiquidarEntidad(){
    	try {
			if(usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES)){
				return true;
			}else{
				return false;
			}
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    /**
	 * @return the strMensajeLiquidar
	 */
	public String getStrMensajeLiquidar() {
		return strMensajeLiquidar;
	}

	/**
	 * @param strMensajeLiquidar the strMensajeLiquidar to set
	 */
	public void setStrMensajeLiquidar(String strMensajeLiquidar) {
		this.strMensajeLiquidar = strMensajeLiquidar;
	}

	/**
     * Sobreescribe el método para el botón regresar
     *
     * @return
     */
    public String regresarGestionarEntidad() {
        return "/entidad/gestionarEntidad?faces-redirect=true";
    }

    
    
}
