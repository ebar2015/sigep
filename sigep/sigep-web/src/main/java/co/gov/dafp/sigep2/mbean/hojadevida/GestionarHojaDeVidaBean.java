package co.gov.dafp.sigep2.mbean.hojadevida;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entities.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ConversationScoped
@ManagedBean
public class GestionarHojaDeVidaBean extends BaseBean implements Serializable
{
    private static final long serialVersionUID = -131671368173191781L;
    
    /**
     * Validacion seguridad de los botones
     */
    private boolean btnBuscarGestionarHVDeshabilitado;
    private boolean btnVerDetalleGestionarHVDeshabilitado;
    private boolean btnEditarGestionarHVDeshabilitado;
    private boolean btnVerificarAprobarGestionarHVDeshabilitado;
    private boolean btnEliminarGestionarHVDeshabilitado;
    private boolean btnEvaluacionDesempenoGestionarHVDeshabilitado;
    
    /**
     * Mensajes de Validacion seguridad de roles
     */
    private String btnBuscarGestionarHVMensaje;
    private String btnVerDetalleGestionarHVMensaje;
    private String btnEditarGestionarHVMensaje;
    private String btnVerificarAprobarGestionarHVMensaje;
    private String btnEliminarGestionarHVMensaje;
    private String btnEvaluacionDesempenoGestionarHVMensaje;
    
    
    private PersonaDTO persona;
    private PersonaDTO personaFilter;
    private UsuarioDTO usuario;
    private TipoAsociacionDTO tipoAsociacionV;
    private HojaVidaExt hojaVida = new HojaVidaExt();
    
    private LazyDataModel<PersonaDTO> listaPersonasConHV;
    private List<SelectItem> listAprobacion = new ArrayList<SelectItem>();
    
    private Boolean visible = false;
    private Boolean flgEliminarHv = false;
    private Boolean flgEditarHv = false;
    private Boolean flgTipoAsociacion = false;
    private String msnVerificacion = "";
    private String validacionVerificacion = "";
    private String justificarEliminacion = "";
    private Integer complejidaBusquedad = null;
    private Boolean flgAprobarHv = false;

    public Boolean getFlgAprobarHv() {
		return flgAprobarHv;
	}

	public void setFlgAprobarHv(Boolean flgAprobarHv) {
		this.flgAprobarHv = flgAprobarHv;
	}

	public UsuarioDTO getUsuario()
    {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario)
    {
        this.usuario = usuario;
    }

    public TipoAsociacionDTO getTipoAsociacionV()
    {
        return tipoAsociacionV;
    }

    public void setTipoAsociacionV(TipoAsociacionDTO tipoAsociacionV)
    {
        this.tipoAsociacionV = tipoAsociacionV;
    }

    public PersonaDTO getPersona()
    {
        return persona;
    }

    public void setPersona(PersonaDTO persona)
    {
        this.persona = persona;
    }

    public Boolean getVisible()
    {
        return visible;
    }

    public void setVisible(Boolean visible)
    {
        this.visible = visible;
    }

    public LazyDataModel<PersonaDTO> getListaPersonasConHV()
    {
        return listaPersonasConHV;
    }

    public void setListaPersonasConHV(LazyDataModel<PersonaDTO> listaPersonasConHV)
    {
        this.listaPersonasConHV = listaPersonasConHV;
    }

    public void onChangeVisible()
    {
        if (!visible)
        {
            visible = !visible;
        } else
        {
            visible = !visible;
        }
        visible = true;
    }
    
    public void printTable()
    {
        if (getEntidadUsuario() != null){
        	personaFilter.setCodFactorRh(complejidaBusquedad.longValue());
        	personaFilter.setPrimerNombre(personaFilter.getPrimerNombre().replace(' ','%'));
            listaPersonasConHV = new LazyModelHV(personaFilter, usuario, getEntidadUsuario().getId(), getTipoAsociacionV().getId().toString().trim());
            onChangeVisible();
        }else{
        	mostrarMensaje(FacesMessage.SEVERITY_INFO, "Info","Usted no tiene permisos para realizar esta busqueda.");
        }
    }

    @Override
    public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException
    {
    }

    public String getMsnVerificacion() {
		return msnVerificacion;
	}

	public void setMsnVerificacion(String msnVerificacion) {
		this.msnVerificacion = msnVerificacion;
	}

	public String getValidacionVerificacion() {
		return validacionVerificacion;
	}

	public void setValidacionVerificacion(String validacionVerificacion) {
		this.validacionVerificacion = validacionVerificacion;
	}

	public HojaVidaExt getHojaVida() {
		return hojaVida;
	}

	public void setHojaVida(HojaVidaExt hojaVida) {
		this.hojaVida = hojaVida;
	}

	public List<SelectItem> getListAprobacion() {
		return listAprobacion;
	}
	

	public void setListAprobacion(SelectItem listAprobacion) {
		this.listAprobacion.add(listAprobacion);
	}
	

	public PersonaDTO getPersonaFilter() {
		return personaFilter;
	}

	public void setPersonaFilter(PersonaDTO personaFilter) {
		this.personaFilter = personaFilter;
	}

	public Boolean getFlgEliminarHv() {
		return flgEliminarHv;
	}

	public void setFlgEliminarHv(Boolean flgEliminarHv) {
		this.flgEliminarHv = flgEliminarHv;
	}

	public Boolean getFlgTipoAsociacion() {
		return flgTipoAsociacion;
	}

	public void setFlgTipoAsociacion(Boolean flgTipoAsociacion) {
		this.flgTipoAsociacion = flgTipoAsociacion;
	}

	public String getJustificarEliminacion() {
		return justificarEliminacion;
	}

	public void setJustificarEliminacion(String justificarEliminacion) {
		this.justificarEliminacion = justificarEliminacion;
	}

	public Boolean getFlgEditarHv() {
		return flgEditarHv;
	}

	public void setFlgEditarHv(Boolean flgEditarHv) {
		this.flgEditarHv = flgEditarHv;
	}

	@PostConstruct
    public void init()
    {
		setBtnBuscarGestionarHVDeshabilitado(this.validarFuncionalidadDeshabilitada("btnBuscarGestionarHV"));
		setBtnBuscarGestionarHVMensaje(isBtnBuscarGestionarHVDeshabilitado() ? 
				TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_DESHABILITADO_SEGURIDAD_ROLES, getLocale()) : "");
		
		setBtnVerDetalleGestionarHVDeshabilitado(this.validarFuncionalidadDeshabilitada("btnVerDetalleGestionarHV"));
		setBtnVerDetalleGestionarHVMensaje(isBtnVerDetalleGestionarHVDeshabilitado() ? 
				TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_DESHABILITADO_SEGURIDAD_ROLES, getLocale()) 
				: TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_VER_DETALLE, getLocale()));
		
		setBtnEditarGestionarHVDeshabilitado(this.validarFuncionalidadDeshabilitada("btnEditarGestionarHV"));
		setBtnEditarGestionarHVMensaje(isBtnEditarGestionarHVDeshabilitado() ? 
				TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_DESHABILITADO_SEGURIDAD_ROLES, getLocale()) 
				: TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_EDIT, getLocale()));
		
		setBtnVerificarAprobarGestionarHVDeshabilitado(this.validarFuncionalidadDeshabilitada("btnVerificarAprobarGestionarHV"));
		setBtnVerificarAprobarGestionarHVMensaje(isBtnVerificarAprobarGestionarHVDeshabilitado() ? 
				TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_DESHABILITADO_SEGURIDAD_ROLES, getLocale()) 
				: TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_APROBAR_VERIFICAR, getLocale()));
		
		setBtnEliminarGestionarHVDeshabilitado(this.validarFuncionalidadDeshabilitada("btnEliminarGestionarHV"));
		setBtnEliminarGestionarHVMensaje(isBtnEliminarGestionarHVDeshabilitado() ? 
				TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_DESHABILITADO_SEGURIDAD_ROLES, getLocale()) 
				: TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.BTN_TABLA_ELIMINAR, getLocale()));
		
		setBtnEvaluacionDesempenoGestionarHVDeshabilitado(this.validarFuncionalidadDeshabilitada("btnEvaluacionDesempenoGestionarHV"));
		setBtnEvaluacionDesempenoGestionarHVMensaje(isBtnEvaluacionDesempenoGestionarHVDeshabilitado() ? 
				TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_DESHABILITADO_SEGURIDAD_ROLES, getLocale()) 
				: TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_EVALUACIONES_DE_DESEMPENIO_ACUERDOS, getLocale()));
		
		setBtnBuscarGestionarHVDeshabilitado(this.validarFuncionalidadDeshabilitada("btnBuscarGestionarHV"));
		setBtnBuscarGestionarHVMensaje(isBtnBuscarGestionarHVDeshabilitado() ? 
				TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_DESHABILITADO_SEGURIDAD_ROLES, getLocale()) : "");
		
        tipoAsociacionV = new TipoAsociacionDTO();
        persona = new PersonaDTO();
        personaFilter = new PersonaDTO();
        usuario = getUsuarioSesion();
        
        try {
			Boolean validRold = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.OPERADOR_CONTRATOS, RolDTO.JEFE_CONTRATOS, RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTROL_INTERNO, RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.AUDITOR);
			flgEliminarHv = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL);
			flgEditarHv = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.OPERADOR_CONTRATOS, RolDTO.JEFE_CONTRATOS, RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES);
			flgTipoAsociacion = true;
			flgAprobarHv  = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.OPERADOR_CONTRATOS, RolDTO.JEFE_CONTRATOS, RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES);
			
			if(validRold == false) {
				this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init() usuarioTieneRolAsignadoSinJerarquia", e);
		} catch (IOException e) {
			logger.error("void init() finalizarConversacion", e);
		}
        
        seleccionarComplejidadBusquedad();
        aplicarFiltroUrl();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_HOJAVIDA);		        
    }

	@SuppressWarnings("unchecked")
	public void aplicarFiltroUrl() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        String codIdentificacion = request.getParameter("codIdentificacion");
        String numIdentificacion = request.getParameter("numIdentificacion");
        String nombreFilter = request.getParameter("nombreFilter");
        String codTipoAsociacion = request.getParameter("codTipoAsociacion");
        Boolean valid = false;
        
        if(codIdentificacion != null) {
        	List<TipoDocumentoDTO> tiposDocumentos = (List<TipoDocumentoDTO>) contexto.getSessionMap().get("tiposDocumentos");
        	try {
    			if (tiposDocumentos == null) {
    				tiposDocumentos = AdministracionDelegate.findTipoDocumento();
    				contexto.getSessionMap().put("tiposDocumentos", tiposDocumentos);
    			}
    		} catch (Exception e) {
    			valid = false;
    		}
        	
        	if (tiposDocumentos != null) {
	        	TipoDocumentoDTO tipoDoc = new TipoDocumentoDTO();
	    		tipoDoc.setId(Long.valueOf(codIdentificacion));
	        	personaFilter.setTipoIdentificacionId(tiposDocumentos.get(tiposDocumentos.indexOf(tipoDoc)));
	        	valid = true;
        	}
        }
        
        if(numIdentificacion!=null) {
        	personaFilter.setNumeroIdentificacion(numIdentificacion);
        	valid = true;
        }
        
        if(nombreFilter!=null) {
        	personaFilter.setPrimerNombre(nombreFilter);
        	valid = true;
        }
        
        if(codTipoAsociacion!=null) {
        	List<TipoAsociacionDTO> tipoAsociacion = (List<TipoAsociacionDTO>) contexto.getSessionMap().get("tipoAsociacion");
        	try {
    			if (tipoAsociacion == null) {
    				tipoAsociacion = AdministracionDelegate.findTipoAsociacion();
    				contexto.getSessionMap().put("tipoAsociacion", tipoAsociacion);
    			}
    		} catch (Exception e) {
    			valid = false;
    		}
        	
        	if (tipoAsociacion != null) {
	        	TipoAsociacionDTO tipAsociacion = new TipoAsociacionDTO();
	    		tipAsociacion.setId(Long.valueOf(codTipoAsociacion));
	        	setTipoAsociacionV(tipoAsociacion.get(tipoAsociacion.indexOf(tipAsociacion)));
	        	valid = true;
        	}
        }
        
        if(valid == true) {
        	printTable();
        }
	}
	
    public void redireccionDetalle()
    {
        try
        {	
        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("id", persona.getId());
            FacesContext.getCurrentInstance().getExternalContext().redirect("hojaDeVida.xhtml?id=" + persona.getId()+"&&isMenu=0&&opcionGerenciaPublica=4&&isGestionSoloConsulta=1");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void redireccionActualizar()
    {
        try
        {
        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("id", persona.getId());
        	FacesContext.getCurrentInstance().getExternalContext().redirect("hojaDeVida.xhtml?id=" + persona.getId()+"&&isMenu=0&&opcionGerenciaPublica=3&&isGestionSoloConsulta=0");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String redireccionAprobar()
    {
    	
    	listAprobacion = new ArrayList<SelectItem>();
    	HojaVidaExt hojaVidaFilter = new HojaVidaExt();
    	hojaVidaFilter.setCodPersona(BigDecimal.valueOf(persona.getId()));
    	hojaVidaFilter.setCodEntidad(this.getEntidadUsuario().getId());
    	hojaVidaFilter.setFlgActivo(true);
    	hojaVidaFilter.setFlgAprobado(true);
    	hojaVidaFilter.setLimitEnd(1);
    	
    	List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);
    	
    	if(listHojaVida.size() > 0) {
    		hojaVida = listHojaVida.get(0);
    		PersonaExt personaExt = ComunicacionServiciosHV.getPersonaporIdExt(persona.getId());
        	msnVerificacion = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_VALIDAR_APROBACION, getLocale())
    								.replace("%tipo%", personaExt.getNombreTipoDocuento())
    								.replace("%numero%", personaExt.getNumeroIdentificacion())
    								.replace("%nombreusuario%", personaExt.getNombreUsuario());
        	
        	setListAprobacion(new SelectItem("REVERSE_APPROVAL",  TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_REVERSAR_APROBACION, getLocale()) + hojaVida.getFechaStringAprobacion()+(
        									hojaVida.getTipoAprobacion()!=null?(" y "+hojaVida.getTipoAprobacion()):"")));
        	setListAprobacion(new SelectItem("MAKE_APPROVAL_WITHOUT", TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_REALIZAR_APROBACION_UTL, getLocale())));
        	setListAprobacion(new SelectItem("MAKE_APPROVAL_WITH", TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_REALIZAR_APROBACION_UTL_IGUAL, getLocale())));
        	
        	validacionVerificacion = "REVERSE_APPROVAL";
        	
        	RequestContext.getCurrentInstance().execute("PF('verificarHojaVidaDialog').show();");
        	
        	return null;
    	}
    	
        try {
        	String url = "aprobarCertificado.xhtml?id=" + persona.getId();
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void validRedireccionAprobar() {
    	
    	hojaVida.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
    	hojaVida.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
    	hojaVida.setAudCodRol((int) this.getRolAuditoria().getId());
    	hojaVida.setAudFechaActualizacion(DateUtils.getFechaSistema());
    	hojaVida.setFlgAprobado(false);
    	hojaVida.setCodUsuarioAprueba(null);
    	hojaVida.setFechaAprobacion(null);
    	hojaVida.setDetallesContrato(null);
    	
    	boolean valid = ComunicacionServiciosHV.setHojaVida(hojaVida);
		
		if(valid == true){
			/*Inactivar modificaciones (fotos)*/
			HistoricoModificacionHojaVida buscador = new HistoricoModificacionHojaVida();
			buscador.setCodHojaVida(BigDecimal.valueOf(hojaVida.getCodHojaVida()));
			List<HistoricoModificacionHojaVida> lstModificaciones = ComunicacionServiciosHV.getHistoricoModificacionHojaVidaByFiltro (buscador);
			if(!lstModificaciones.isEmpty()){
				for(HistoricoModificacionHojaVida historico:lstModificaciones){
					historico.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
					historico.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
					historico.setAudCodRol((int) this.getRolAuditoria().getId());
					historico.setAudFechaModificacion(DateUtils.getFechaSistema());
					historico.setFlgActivo((short) 0);
					historico.setJustificacionModificacion("SE REVERSA APROBACION");
					ComunicacionServiciosHV.setmodificacionhohadevida(historico);
				}
			}
			
        	String url = "aprobarCertificado.xhtml?id=" + persona.getId() +"&validation="+Base64.getEncoder().encodeToString(validacionVerificacion.getBytes());
        	
	        try {
	            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		} else{
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO,"");
		}
    }
    
    public void redireccionEvaluacion()
    {
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("evaluacionDesempeno.xhtml?backView=gestionarHojaDeVida&id=" + persona.getId());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void cargarDialogEliminarHV()
    {
    	justificarEliminacion = "";
    	msnVerificacion = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_VALIDAR_ELIMINACION, getLocale())
											.replace("%tipo%", persona.getTipoIdentificacionId().getDescripcion())
											.replace("%numero%", persona.getNumeroIdentificacion())
											.replace("%nombreusuario%", persona.getNombre());
    	
        RequestContext.getCurrentInstance().execute("PF('eliminarHojaVidaDialog').show();");
    }

    public void eliminarHojaVida()
    {
    	HojaVidaExt hojaVidaFilter = new HojaVidaExt();
    	hojaVidaFilter.setCodPersona(BigDecimal.valueOf(persona.getId()));
    	hojaVidaFilter.setCodEntidad(this.getEntidadUsuario().getId());
    	hojaVidaFilter.setFlgActivo(true);
    	hojaVidaFilter.setLimitEnd(1);
    	
    	List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);
    	
        Boolean valid = ComunicacionServiciosHV.eliminarHojaVida(persona.getId(), getUsuarioSesion().getId(), persona.getCodEntidad(), this.getJustificarEliminacion(), listHojaVida.get(0).getCodHojaVida());
        if (valid){
            persona = new PersonaDTO();
            printTable();
            RequestContext.getCurrentInstance().execute("PF('eliminarHojaVidaDialog').hide()");
            mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ELIMINAR_HOJAVIDA);
        }else {
        	mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
        }
    }

    @Override
    public String persist() throws NotSupportedException { return null; }

    @Override
    public void retrieve() throws NotSupportedException
    {
        if (FacesContext.getCurrentInstance().isPostback())
        {
            return;
        }
        try
        {
            if (this.conversation.isTransient())
            {
                this.conversation.begin();
                this.conversation.setTimeout(timeOut);
            }
        } catch (NonexistentConversationException e)
        {
            mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO, MessagesBundleConstants.DLG_URL_INVALID);
        }
    }

    public void seleccionarComplejidadBusquedad(){
    	if(complejidaBusquedad == null) {
    		//La busqueda va a presentar complejdad 0, es decir que cualquier rol permitido va a poder gestionar las HV de cualquier persona.
    		complejidaBusquedad = 0;
    	}
    }
    
    @Override
    public String update() throws NotSupportedException { return null; }

    @Override
    public void delete() throws NotSupportedException{}

	public boolean isBtnBuscarGestionarHVDeshabilitado() {
		return btnBuscarGestionarHVDeshabilitado;
	}

	public void setBtnBuscarGestionarHVDeshabilitado(boolean btnBuscarGestionarHVDeshabilitado) {
		this.btnBuscarGestionarHVDeshabilitado = btnBuscarGestionarHVDeshabilitado;
	}

	public String getBtnBuscarGestionarHVMensaje() {
		return btnBuscarGestionarHVMensaje;
	}

	public void setBtnBuscarGestionarHVMensaje(String btnBuscarGestionarHVMensaje) {
		this.btnBuscarGestionarHVMensaje = btnBuscarGestionarHVMensaje;
	}

	public boolean isBtnVerDetalleGestionarHVDeshabilitado() {
		return btnVerDetalleGestionarHVDeshabilitado;
	}

	public void setBtnVerDetalleGestionarHVDeshabilitado(boolean btnVerDetalleGestionarHVDeshabilitado) {
		this.btnVerDetalleGestionarHVDeshabilitado = btnVerDetalleGestionarHVDeshabilitado;
	}

	public boolean isBtnEditarGestionarHVDeshabilitado() {
		return btnEditarGestionarHVDeshabilitado;
	}

	public void setBtnEditarGestionarHVDeshabilitado(boolean btnEditarGestionarHVDeshabilitado) {
		this.btnEditarGestionarHVDeshabilitado = btnEditarGestionarHVDeshabilitado;
	}

	public boolean isBtnVerificarAprobarGestionarHVDeshabilitado() {
		return btnVerificarAprobarGestionarHVDeshabilitado;
	}

	public void setBtnVerificarAprobarGestionarHVDeshabilitado(boolean btnVerificarAprobarGestionarHVDeshabilitado) {
		this.btnVerificarAprobarGestionarHVDeshabilitado = btnVerificarAprobarGestionarHVDeshabilitado;
	}

	public boolean isBtnEliminarGestionarHVDeshabilitado() {
		return btnEliminarGestionarHVDeshabilitado;
	}

	public void setBtnEliminarGestionarHVDeshabilitado(boolean btnEliminarGestionarHVDeshabilitado) {
		this.btnEliminarGestionarHVDeshabilitado = btnEliminarGestionarHVDeshabilitado;
	}

	public boolean isBtnEvaluacionDesempenoGestionarHVDeshabilitado() {
		return btnEvaluacionDesempenoGestionarHVDeshabilitado;
	}

	public void setBtnEvaluacionDesempenoGestionarHVDeshabilitado(boolean btnEvaluacionDesempenoGestionarHVDeshabilitado) {
		this.btnEvaluacionDesempenoGestionarHVDeshabilitado = btnEvaluacionDesempenoGestionarHVDeshabilitado;
	}

	public String getBtnVerDetalleGestionarHVMensaje() {
		return btnVerDetalleGestionarHVMensaje;
	}

	public void setBtnVerDetalleGestionarHVMensaje(String btnVerDetalleGestionarHVMensaje) {
		this.btnVerDetalleGestionarHVMensaje = btnVerDetalleGestionarHVMensaje;
	}

	public String getBtnEditarGestionarHVMensaje() {
		return btnEditarGestionarHVMensaje;
	}

	public void setBtnEditarGestionarHVMensaje(String btnEditarGestionarHVMensaje) {
		this.btnEditarGestionarHVMensaje = btnEditarGestionarHVMensaje;
	}

	public String getBtnVerificarAprobarGestionarHVMensaje() {
		return btnVerificarAprobarGestionarHVMensaje;
	}

	public void setBtnVerificarAprobarGestionarHVMensaje(String btnVerificarAprobarGestionarHVMensaje) {
		this.btnVerificarAprobarGestionarHVMensaje = btnVerificarAprobarGestionarHVMensaje;
	}

	public String getBtnEliminarGestionarHVMensaje() {
		return btnEliminarGestionarHVMensaje;
	}

	public void setBtnEliminarGestionarHVMensaje(String btnEliminarGestionarHVMensaje) {
		this.btnEliminarGestionarHVMensaje = btnEliminarGestionarHVMensaje;
	}

	public String getBtnEvaluacionDesempenoGestionarHVMensaje() {
		return btnEvaluacionDesempenoGestionarHVMensaje;
	}

	public void setBtnEvaluacionDesempenoGestionarHVMensaje(String btnEvaluacionDesempenoGestionarHVMensaje) {
		this.btnEvaluacionDesempenoGestionarHVMensaje = btnEvaluacionDesempenoGestionarHVMensaje;
	}
	
	

}