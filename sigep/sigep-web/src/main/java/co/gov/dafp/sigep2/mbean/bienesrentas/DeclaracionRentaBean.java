/**
 * 
 */
package co.gov.dafp.sigep2.mbean.bienesrentas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Declaracion;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.DeclaracionExt;
import co.gov.dafp.sigep2.mbean.ext.RecursoExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosBR;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoAccion;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.UtilidadesFaces;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ViewScoped
@ManagedBean
public class DeclaracionRentaBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 217343538249226141L;

	/**
	 * Validacion seguridad de los botones
	 */
	private boolean btnValidarTipoDeshabilitado;

	/**
	 * Mensajes de Validacion seguridad de roles
	 */
	private String btnValidarTipoMensaje;

	private List<SelectItem> lisEntida 	= new ArrayList<>();
	private List<SelectItem> lisDecla 	= new ArrayList<>();
	private List<Entidad> entidades;
	private UsuarioDTO usuarioSesion 	= new UsuarioDTO();
	private EntidadDTO entidadUsuario 	= new EntidadDTO();
	private int tipoDeclaracion;
	private Declaracion declaracionUsuario = new Declaracion();
	private List<Declaracion> lisd;
	private String fechaInicio;
	private String fechaFinal;
	private String fechaLimite;
	private int annoDeclaracion;
	private boolean lbInicializoFechas;
	private String fechaActual;
	private String mensajeant;
	private Declaracion declaracion;
	private String PAGE_RENTAS = "bienesrentas.xhtml?recursoId=MiDeclaracionHistoricoBienesRentasSunMenu";
	private String PAGE_ERROR = "mensajeError.xhtml?recursoId=MiDeclaracionHistoricoBienesRentasSunMenu";
	private String PAGE_CANCEL = "../persona/informacionPersonal.xhtml?recursoId=HojaDeVidaSubMenu";
	private ExternalContext contexto;

	private int totalEntidades;
	private int totalDeclaraciones;
	private int entidadSelect;
	private String nombreEntidad;
	private String mensaje;
	private String mensajeE;
	private String mensajeini;
	private boolean distritoC = false;
	private int rol = 0;
	private SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private long declaracionM;
	private String codDec ="0";
	private String strErrorNoContinuar;
	
	/**
	 * @return the rol
	 */
	public int getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(int rol) {
		this.rol = rol;
	}
	
	public void cancelar() {
		UtilidadesFaces.redirect(PAGE_CANCEL);
	}

	/**
	 * @return the lisEntida
	 */
	public List<SelectItem> getLisEntida() {
		return lisEntida;
	}

	/**
	 * @param lisEntida
	 *            the lisEntida to set
	 */
	public void setLisEntida(List<SelectItem> lisEntida) {
		this.lisEntida = lisEntida;
	}

	/**
	 * @return the totalEntidades
	 */
	public int getTotalEntidades() {
		return totalEntidades;
	}

	/**
	 * @param totalEntidades
	 *            the totalEntidades to set
	 */
	public void setTotalEntidades(int totalEntidades) {
		this.totalEntidades = totalEntidades;
	}

	/**
	 * @return the nombreEntidad
	 */
	public String getNombreEntidad() {
		return nombreEntidad;
	}

	/**
	 * @param nombreEntidad
	 *            the nombreEntidad to set
	 */
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	/**
	 * @return the entidadSelect
	 */
	public int getEntidadSelect() {
		return entidadSelect;
	}

	/**
	 * @param entidadSelect
	 *            the entidadSelect to set
	 */
	public void setEntidadSelect(int entidadSelect) {
		this.entidadSelect = entidadSelect;
	}

	public EntidadDTO getEntidadUsuario() {
		return entidadUsuario;
	}

	public void setEntidadUsuario(EntidadDTO entidadUsuario) {
		this.entidadUsuario = entidadUsuario;
	}

	/**
	 * @return the tipoDeclaracion
	 */
	public int getTipoDeclaracion() {
		return tipoDeclaracion;
	}

	/**
	 * @param tipoDeclaracion
	 *            the tipoDeclaracion to set
	 */
	public void setTipoDeclaracion(int tipoDeclaracion) {
		this.tipoDeclaracion = tipoDeclaracion;
	}

	/**
	 * @return the declaracionUsuario
	 */
	public Declaracion getDeclaracionUsuario() {
		return declaracionUsuario;
	}

	/**
	 * @param declaracionUsuario
	 *            the declaracionUsuario to set
	 */
	public void setDeclaracionUsuario(Declaracion declaracionUsuario) {
		this.declaracionUsuario = declaracionUsuario;
	}

	/**
	 * @return the lisd
	 */
	public List<Declaracion> getLisd() {
		return lisd;
	}

	/**
	 * @param lisd
	 *            the lisd to set
	 */
	public void setLisd(List<Declaracion> lisd) {
		this.lisd = lisd;
	}

	/**
	 * @return the fechaInicio
	 */
	public String getfechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio
	 *            the fechaInicio to set
	 */
	public void setfechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFinal
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * @param fechaFinal
	 *            the fechaFinal to set
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the fechaLimite
	 */
	public String getFechaLimite() {
		return fechaLimite;
	}

	/**
	 * @param fechaLimite
	 *            the fechaLimite to set
	 */
	public void setFechaLimite(String fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	/**
	 * @return the fechaActual
	 */
	public String getFechaActual() {
		return fechaActual;
	}

	/**
	 * @param fechaActual
	 *            the fechaActual to set
	 */
	public void setFechaActual(String fechaActual) {
		this.fechaActual = fechaActual;
	}

	/**
	 * @return the distritoC
	 */
	public boolean isDistritoC() {
		return distritoC;
	}

	/**
	 * @param distritoC
	 *            the distritoC to set
	 */
	public void setDistritoC(boolean distritoC) {
		this.distritoC = distritoC;
	}

	/**
	 * @return the totalDeclaraciones
	 */
	public int getTotalDeclaraciones() {
		return totalDeclaraciones;
	}

	/**
	 * @param totalDeclaraciones
	 *            the totalDeclaraciones to set
	 */
	public void setTotalDeclaraciones(int totalDeclaraciones) {
		this.totalDeclaraciones = totalDeclaraciones;
	}

	/**
	 * @return the mensajeini
	 */
	public String getMensajeini() {
		return mensajeini;
	}

	/**
	 * @param mensajeini
	 *            the mensajeini to set
	 */
	public void setMensajeini(String mensajeini) {
		this.mensajeini = mensajeini;
	}

	/**
	 * @return the mensajeant
	 */
	public String getMensajeant() {
		return mensajeant;
	}

	/**
	 * @param mensajeant
	 *            the mensajeant to set
	 */
	public void setMensajeant(String mensajeant) {
		this.mensajeant = mensajeant;
	}

	/**
	 * @return the lisDecla
	 */
	public List<SelectItem> getLisDecla() {
		return lisDecla;
	}

	/**
	 * @param lisDecla
	 *            the lisDecla to set
	 */
	public void setLisDecla(List<SelectItem> lisDecla) {
		this.lisDecla = lisDecla;
	}

	/**
	 * @return the declaracionM
	 */
	public long getDeclaracionM() {
		return declaracionM;
	}

	/**
	 * @param declaracionM
	 *            the declaracionM to set
	 */
	public void setDeclaracionM(long declaracionM) {
		this.declaracionM = declaracionM;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo(int i) {
		Parametrica param = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(i));
		return param.getNombreParametro();
	}

	public boolean isBtnValidarTipoDeshabilitado() {
		return btnValidarTipoDeshabilitado;
	}

	public void setBtnValidarTipoDeshabilitado(boolean btnValidarTipoDeshabilitado) {
		this.btnValidarTipoDeshabilitado = btnValidarTipoDeshabilitado;
	}

	public String getBtnValidarTipoMensaje() {
		return btnValidarTipoMensaje;
	}

	public void setBtnValidarTipoMensaje(String btnValidarTipoMensaje) {
		this.btnValidarTipoMensaje = btnValidarTipoMensaje;
	}

	public DeclaracionRentaBean() {
		contexto = FacesContext.getCurrentInstance().getExternalContext();
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		setMensajeE(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_FECHA_PERIODICA_BR, getLocale()));
		// Inicio - Validacion de seguridad de roles por funcionalidad
		setBtnValidarTipoDeshabilitado(this.validarFuncionalidadDeshabilitada("btnValidarTipo"));
		setBtnValidarTipoMensaje(isBtnValidarTipoDeshabilitado() ? "Deshabilitado por seguridad Roles" : "");
		// Fin - Validacion de seguridad de roles por funcionalidad
		mensajeini = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_DECLARACION_012, getLocale());
		contexto.getSessionMap().put("cumpleFlujo", "true");
		Declaracion dec = new Declaracion();
		SimpleDateFormat formatAn = new SimpleDateFormat("yyyy");
		int an = Integer.parseInt(formatAn.format(new Date())) - 1;
		dec.setCodPersona(new BigDecimal(usuarioSesion.getCodPersona()));
		lisd = ComunicacionServiciosBR.getdeclaracioncreacionn(dec);
		totalDeclaraciones = lisd.size();
		dec.setConfirmacion((short) 1);
		dec.setAnnoDeclaracion((short) an);
		fechaActual = formateador.format(new Date());
		entidades = ComunicacionServiciosSis.getEntidadCodPersona(usuarioSesion.getId());
		lisd = ComunicacionServiciosBR.getdeclaracioncreacionn(dec);
		
		entidadUsuario = (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");
		totalEntidades = entidades.size();
		rol = (int) usuarioSesion.getCodRol();
		DeclaracionExt consultP1 = new DeclaracionExt();
		consultP1.setCodPersona(new BigDecimal(usuarioSesion.getCodPersona()));
		List<DeclaracionExt> todasDec = new ArrayList<>();
		todasDec = ComunicacionServiciosBR.getListaModificarFiltro(consultP1);
		if (todasDec.size() > 0) {
			for (DeclaracionExt aux : todasDec)
				if (aux != null)
					lisDecla.add(new SelectItem(aux.getCodDeclaracion(), 
							aux.getNombreTipoDeclaracion() 
							+ "  [" + formateador.format(aux.getFechaInicio()) 
							+ " al " + formateador.format(aux.getFechaFin()) + "]" 
							+ " fecha declaración [" + formateador.format(aux.getFechaPresentacion()) + "]"
							
						));
		}
		cargarListaEntidades();
		validacionEntidadDistritoBogotaVariasEntidades( (int) entidadUsuario.getId());
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_BYR);		
	}

	/**
	 *Metodo que carga la lista de entidades a desplegar cuando el usuario tiene mas de una asociada. 
	 */
	public void cargarListaEntidades() {
		if(totalEntidades > 1) {
			for (Entidad aux : entidades) {
				lisEntida
				.add(new SelectItem(aux.getCodEntidad(), aux.getNombreEntidad()));
			}
		}
	}
	/**
	 * Metodo que valida si la entidad seleccionada (En caso de que exista mas de una) o la entidad
	 * en sesion se territorial, distrital Bogotá
	 */
	public void validarEntidadesTerritorialesUsuario() {
		distritoC=false;
		List<Entidad> entidadesBogota =  ComunicacionServiciosEnt.getEntidadesBogotaUsuario((int) usuarioSesion.getId());
		if(entidadesBogota!=null && entidadesBogota.size()>0) {
			distritoC = true;
			nombreEntidad =entidadesBogota.get(0).getNombreEntidad();
		}
		/*validación de entidad SIDEAP*/
		if (distritoC) { 
			distritoC = true;
			mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_DECLARACION_011, getLocale());
			mensaje = mensaje.replace("#entidad", nombreEntidad);
		}
	}
	
	/**
	 * Metodo que valida si la entidad seleccionada (En caso de que exista mas de una) o la entidad
	 * en sesion se territorial, distrital Bogotá
	 */
	public void validacionEntidadDistritoBogotaVariasEntidades(int entidad) {
		if(entidad > 0) {
			Entidad entBog = ComunicacionServiciosEnt.getEntidadBogota((int) (long)entidad);
			if (entBog.getCodEntidad() != null) {
				distritoC = true;
				nombreEntidad = entBog.getNombreEntidad();
			}
		}
		if (distritoC) { 
			distritoC = true;
			mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_DECLARACION_011, getLocale());
			mensaje = mensaje.replace("#entidad", nombreEntidad);
			
		}
	}
	public void validacionEntidadDistritoBogotaVariasEntidadesSelect() {
		if(entidadSelect > 0) {
			Entidad entBog = ComunicacionServiciosEnt.getEntidadBogota(entidadSelect);
			if (entBog.getCodEntidad() != null) {
				distritoC = true;
				nombreEntidad = entBog.getNombreEntidad();
			}
		}
		if (distritoC) { 
			distritoC = true;
			mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_DECLARACION_011, getLocale());
			mensaje = mensaje.replace("#entidad", nombreEntidad);
			entidadSelect=0;
			RequestContext.getCurrentInstance().execute("$('#modalEntidadCapital').modal('show');");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	protected boolean validarFuncionalidadDeshabilitada(String idBoton) {
		/* Consultamos los componentes del módulo con un servicio a bd() */
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String recursoId = paramMap.get("recursoId");
		List<RecursoExt> idCompoments = new ArrayList<RecursoExt>();
		List<RolDTO> rolesUsuario = (List<RolDTO>) contexto.getSessionMap().get("rolesUsuarioSesion");
		int[] idRoles = new int[rolesUsuario.size()];
		for (int i = 0; i < rolesUsuario.size(); i++)
			idRoles[i] = (int) rolesUsuario.get(i).getId();
		RecursoExt recurso = new RecursoExt();
		recurso.setCodigoVentana(recursoId);
		recurso.setCodRolList(idRoles);
		idCompoments = ComunicacionServiciosSis.getRecursoList(recurso);
		for (RecursoExt componentId : idCompoments) {
			if (idBoton.equals(componentId.getIdBoton())) {
				if (componentId.getFlgEstado() == 0)
					return true;
			}
		}
		return false;
	}

	public String getNombreEntidad(int index) {
		String name = "";
		if (entidades.size() > 1) {
			for (int i = 0; i < entidades.size(); i++) {
				if (entidades.get(i).getCodEntidad().intValue() == index) 
					name = entidades.get(i).getNombreEntidad();
			}
		}
		return name;
	}


	public void validarTipo() {
		
		/*Los parámetros son independientes para cada tipo de declaración*/
		Parametrica parametricaD = null;
		SimpleDateFormat formatoAnno = new SimpleDateFormat("yyyy");
		Date fechaLimitePresentacion=null;
		boolean lbErrorFechas = false;
		if(tipoDeclaracion>0) {
			if(tipoDeclaracion == BienesRentasBean.COD_TIPO_DECLARACION_INGRESO.intValue()) {
				parametricaD = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.PERIODO_INICIAL_DECLARACION_INGRESO.getValue()));
				fechaInicio = parametricaD.getValorParametro();
				parametricaD = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.PERIODO_FINAL_DECLARACION_INGRESO.getValue()));
				fechaFinal = parametricaD.getValorParametro();
				lbInicializoFechas = true;
			}else if(tipoDeclaracion == BienesRentasBean.COD_TIPO_DECLARACION_RETIRO.intValue()) {
				parametricaD = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.PERIODO_INICIAL_DECLARACION_RETIRO.getValue()));
				fechaInicio = parametricaD.getValorParametro();
				parametricaD = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.PERIODO_FINAL_DECLARACION_RETIRO.getValue()));
				fechaFinal = parametricaD.getValorParametro();
				lbInicializoFechas = true;
			}else if(tipoDeclaracion == BienesRentasBean.COD_TIPO_DECLARACION_PERIODICA.intValue()) {
				parametricaD = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.PERIODO_INICIAL_DECLARACION_PERIODICA.getValue()));
				fechaInicio = parametricaD.getValorParametro();
				parametricaD = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.PERIODO_FINAL_DECLARACION_PERIODICA.getValue()));
				fechaFinal = parametricaD.getValorParametro();
				parametricaD = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.FECHA_LIMITE_PRESENTACION_DECLARACION_PERIODICA.getValue()));
				fechaLimite = parametricaD.getValorParametro();					
				lbInicializoFechas = true;
			}else {
				lbInicializoFechas = false;
			}
		}else {
			lbInicializoFechas = false;
		}
		if(!lbInicializoFechas) {
			/*se toman por defaultlos parámetros de tipo ingreso*/
			parametricaD = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.PERIODO_INICIAL_DECLARACION_INGRESO.getValue()));
			fechaInicio = parametricaD.getValorParametro();
			parametricaD = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.PERIODO_FINAL_DECLARACION_INGRESO.getValue()));
			fechaFinal = parametricaD.getValorParametro();
		}	
		/*Validamos cada fecha*/
		if(fechaFinal==null || fechaFinal.trim().equals("") || fechaFinal.toUpperCase().equals("SYSDATE") ){
			fechaFinal = formateador.format(new Date());
		}
		if( tipoDeclaracion == BienesRentasBean.COD_TIPO_DECLARACION_PERIODICA.intValue()
				&&(fechaLimite==null || fechaLimite.trim().equals("") || fechaLimite.toUpperCase().equals("SYSDATE")) ){
			fechaLimite = formateador.format(new Date());
		}
		try {
			formateador.parse(fechaInicio);
		    formatter.parse(fechaInicio);
		} catch (ParseException e2) {
			lbErrorFechas = true;
			logger.error("DeclaracionRentasBean Fecha Inicio Invalida: ", e2.getMessage());
		}catch(DateTimeException ex){
			lbErrorFechas = true;
			logger.error("DeclaracionRentasBean Fecha Inicio Invalida: ", ex.getMessage());			
		}
		catch(Exception z){
			lbErrorFechas = true;
			logger.error("DeclaracionRentasBean Fecha Inicio Invalida: ", z.getMessage());
		}
		try {
			formateador.parse(fechaFinal);
			formatter.parse(fechaFinal);
		} catch (ParseException e2) {
			lbErrorFechas = true;
			logger.error("DeclaracionRentasBean Fecha Final Invalida: ", e2.getMessage());
		}
		catch(DateTimeException ex){
			lbErrorFechas = true;
			logger.error("DeclaracionRentasBean Fecha Final Invalida: ", ex.getMessage());			
		}
		catch(Exception z){
			lbErrorFechas = true;
			logger.error("DeclaracionRentasBean Fecha Final Invalida: ", z.getMessage());
		}		
		if (tipoDeclaracion == BienesRentasBean.COD_TIPO_DECLARACION_PERIODICA.intValue()){
			try {
				fechaLimitePresentacion = formateador.parse(fechaLimite);
				formatter.parse(fechaLimite);
			} catch (ParseException e2) {
				lbErrorFechas = true;
				logger.error("DeclaracionRentasBean Fecha Limite Invalida: ", e2.getMessage());
			} 
			catch(DateTimeException ex){
				lbErrorFechas = true;
				logger.error("DeclaracionRentasBean Fecha Limite Invalida: ", ex.getMessage());			
			}catch(Exception z){
				lbErrorFechas = true;
				logger.error("DeclaracionRentasBean Fecha Limite Invalida: ", z.getMessage());
			}	
			
		}
		try {
			annoDeclaracion = Integer.parseInt(formatoAnno.format(formateador.parse(fechaInicio)));
		} catch (NumberFormatException e1) {
			lbErrorFechas = true;
			logger.error("DeclaracionRentasBean Fecha Final Invalida: ", e1.getMessage());
		} catch (ParseException e1) {
			lbErrorFechas = true;
			logger.error("DeclaracionRentasBean Fecha Final Invalida: ", e1.getMessage());
		}	
		
		if(lbErrorFechas){
			strErrorNoContinuar = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_BR_ERROR_PARAMETROS_FECHAS, getLocale());
			if (this.totalEntidades==1)
				RequestContext.getCurrentInstance().execute("$('#unaEntidad').modal('hide');");
			else
				RequestContext.getCurrentInstance().execute("$('#listEntidades').modal('hide');");
			
			RequestContext.getCurrentInstance().execute("$('#accionRealizarDialog').modal('hide');");
			RequestContext.getCurrentInstance().execute("$('#modalErrorNoContinuar').modal('show');");
			return;
		}
		this.contexto.getSessionMap().put("modificacion", "false");
		this.contexto.getSessionMap().put("nuevaDec", "false");
		this.contexto.getSessionMap().put("tipoDeclaracion", tipoDeclaracion + "");
		this.contexto.getSessionMap().put("fechaInicio", fechaInicio);
		this.contexto.getSessionMap().put("fechaFinal", fechaFinal);
		this.contexto.getSessionMap().put("fechaLimite", fechaLimite);
		
		if(tipoDeclaracion == BienesRentasBean.COD_TIPO_DECLARACION_PERIODICA.intValue()) {
			
			String anioActial = formatoAnno.format(new Date());
			try {
				int res = Integer.parseInt(anioActial) - annoDeclaracion;
				if(res > 1) {
					this.contexto.getSessionMap().put("accion", "14");
					UtilidadesFaces.redirect(PAGE_ERROR);
				}
			} catch (NumberFormatException e) {
				this.contexto.getSessionMap().put("accion", "14");
			}	
		}
		this.contexto.getSessionMap().put("msg", "0");
		if (tipoDeclaracion == BienesRentasBean.COD_TIPO_DECLARACION_MODIFICACION.intValue() && declaracionM == 0) {
			mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_DECLARACION_014, getLocale());
	    	RequestContext.getCurrentInstance().execute("$('#modificacion').modal('show');");
			return;
		}
		Date dFechaFinal = new Date();
		Date dFechaInicial =  new Date();
		try {
			dFechaFinal = formateador.parse(fechaFinal);
			dFechaInicial  = formateador.parse(fechaInicio);
		} catch (ParseException e2) {
			lbErrorFechas = true;
			logger.error("DeclaracionRentasBean Fecha Final Invalida: ", e2.getMessage());
		}
		
		Date hoy = new Date();
		Declaracion dec = new Declaracion();
		dec.setCodPersona(new BigDecimal(usuarioSesion.getCodPersona()));
		dec.setAnnoDeclaracion((short) annoDeclaracion);
		dec.setCodTipoDeclaracion(null);
		dec.setConfirmacion((short) 0);
		dec.setFechaInicio(dFechaInicial);
		dec.setFechaFin(dFechaFinal);
		
		try {
			if (declaracionM > 0) {
				Declaracion decla = new Declaracion();
				SimpleDateFormat forAnno = new SimpleDateFormat("yyyy");
				decla = ComunicacionServiciosBR.getdeclaracionid(declaracionM);
				if(decla.getCodTipoDeclaracion() == 742) {
					this.contexto.getSessionMap().put("accion", "15");
					this.contexto.getSessionMap().put("codDeclaracionCarga", "0");
					this.contexto.getSessionMap().put("codDeclaracion", declaracionM+ "");
					return;
				}else {
					this.contexto.getSessionMap().put("accion", "4");
					this.contexto.getSessionMap().put("codDeclaracionCarga", declaracionM + "");
					this.contexto.getSessionMap().put("paracarga", "true");
					this.contexto.getSessionMap().put("modificacion", "true");
					int annoDeclaracionN = 0;

					try {
						annoDeclaracionN = Integer.parseInt(formatoAnno.format(decla.getFechaInicio()));
					} catch (NumberFormatException e1) {
						lbErrorFechas = true;
						logger.error("DeclaracionRentasBean Fecha Final Invalida: ", e1.getMessage());
					} 
					
					
					decla.setFlgModificado((short) 1);
					decla.setAudAccion(TipoAccion.INSERTAR.getValue());
					decla.setAnnoDeclaracion((short) annoDeclaracionN);
					decla.setAudCodRol((int) usuarioSesion.getCodRol());
					decla.setAudFechaActualizacion(new Date());
					decla.setConfirmacion((short) 0);
					decla.setCodDeclaracion(null);
					decla.setTabIndex((short) 0);
					decla.setFlgModificado((short) 1);
					codDec = ComunicacionServiciosBR.setDeclaracion(decla);
					this.contexto.getSessionMap().put("codDeclaracion", codDec);
					return;
				 }
			}
		} catch (NullPointerException e) {

		}
		List<Declaracion> listDecSinConfirmar = ComunicacionServiciosBR.getdeclaracioncreacionn(dec);
		int i = 0;
		for (Declaracion declaracion : listDecSinConfirmar) {
			if (declaracion.getCodTipoDeclaracion() == tipoDeclaracion)
				i++;
		}
		if (tipoDeclaracion == BienesRentasBean.COD_TIPO_DECLARACION_PERIODICA.intValue() && hoy.after(fechaLimitePresentacion)  && i == 0) { 
			/*Periódica,  extemporanea y sin borrador a*/
			Parametrica param = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(tipoDeclaracion));
			mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_DECLARACION_001, getLocale());
			mensaje = mensaje.replace("#fechalimite", fechaLimite);
			mensaje = mensaje.replace("#fechaInicio", fechaInicio);
			mensaje = mensaje.replace("#fechaFinal", fechaFinal);
			mensaje = mensaje.replace("#tipo", param.getNombreParametro());
			entidades = ComunicacionServiciosSis.getEntidadCodPersona(usuarioSesion.getCodPersona());
			if (entidades.size() > 1) {
				try {
					mensaje = mensaje.replace("#entidad", getNombreEntidad(entidadSelect));
				} catch (Exception e) {
					mensaje = mensaje.replace("#entidad", entidadUsuario.getNombreEntidad());
				}
			} else
				mensaje = mensaje.replace("#entidad", entidadUsuario.getNombreEntidad());
			
			
			this.contexto.getSessionMap().put("accion", "5");
			this.contexto.getSessionMap().put("codDeclaracion", "0");
			Declaracion decC = ComunicacionServiciosBR.getdeclaracionUltima(usuarioSesion.getCodPersona());
			if (decC.getCodTipoDeclaracion() != null) {
				this.contexto.getSessionMap().put("codDeclaracionCarga", decC.getCodDeclaracion() + "");
				this.contexto.getSessionMap().put("paracarga", "true");
			} else {
				this.contexto.getSessionMap().put("codDeclaracionCarga", "0");
				this.contexto.getSessionMap().put("paracarga", "false");
			}
			this.contexto.getSessionMap().put("nuevaDec", "true");
		} else {
			Parametrica param = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(tipoDeclaracion));
			// si el usuario ya ha registrado una declaracion para ese tipo en la vigencia
			// actual
			if (listDecSinConfirmar.size() > 0) {
				Declaracion decv = new Declaracion();
				i = 0;
				for (Declaracion declaracion : listDecSinConfirmar) {
					if (declaracion.getCodTipoDeclaracion() == tipoDeclaracion) {
						decv = declaracion;
						i++;
					}
				}
				if (i > 0) {
					mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_DECLARACION_002, getLocale());
					mensaje = mensaje.replace("#tipo", param.getNombreParametro());
					try {
						mensaje = mensaje.replace("#entidad", decv.getEntidadReceptora());
					} catch (Exception e) {
						mensaje = mensaje.replace("#entidad", getNombreEntidad(0));
					}

					//mensaje = mensaje.replace("#fechaInicio", formateador.format(decv.getFechaInicio()));
					//mensaje = mensaje.replace("#fechaFinal", formateador.format(decv.getFechaFin()));
					mensaje = mensaje.replace("#fechaInicio", fechaInicio);
					mensaje = mensaje.replace("#fechaFinal", fechaFinal);
					
					this.contexto.getSessionMap().put("accion", "4");
					this.contexto.getSessionMap().put("codDeclaracion", decv.getCodDeclaracion().toString());
					this.contexto.getSessionMap().put("codDeclaracionCarga", 0 + "");
					this.contexto.getSessionMap().put("paracarga", "false");
					mensaje = mensaje.replace(" ", " ");
				} 
				else {
					this.contexto.getSessionMap().put("nuevaDec", "true");
					mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_DECLARACION_006, getLocale());
					this.contexto.getSessionMap().put("paracarga", "true");
					this.contexto.getSessionMap().put("accion", "3");
					this.contexto.getSessionMap().put("msg", "1");
					mensaje = mensaje.replace("#tipo", param.getNombreParametro());
					entidades = ComunicacionServiciosSis.getEntidadCodPersona(usuarioSesion.getCodPersona());
					if (entidades.size() > 1) {
						try {
							mensaje = mensaje.replace("#entidad", getNombreEntidad(entidadSelect));
						} catch (Exception e) {
							mensaje = mensaje.replace("#entidad", entidadUsuario.getNombreEntidad());
						}
					} 
					else 
						mensaje = mensaje.replace("#entidad", entidadUsuario.getNombreEntidad());
					if (tipoDeclaracion == 743 || tipoDeclaracion == 822) {
						try {
							mensaje = mensaje.replace("#fechaInicio", formateador.format(decv.getFechaInicio()));
							mensaje = mensaje.replace("#fechaFinal", formateador.format(decv.getFechaFin()));
						} catch (Exception e) {
							mensaje = mensaje.replace("#fechaInicio", fechaInicio);
							mensaje = mensaje.replace("#fechaFinal", fechaFinal);
						}
					}else {
						mensaje = mensaje.replace("#fechaInicio", fechaInicio);
						mensaje = mensaje.replace("#fechaFinal", fechaFinal);
					}
				}
			} 
			else {
				mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_DECLARACION_006, getLocale());
				this.contexto.getSessionMap().put("paracarga", "true");
				this.contexto.getSessionMap().put("accion", "3");
				this.contexto.getSessionMap().put("msg", "1");
				mensaje = mensaje.replace("#tipo", param.getNombreParametro());
				entidades = ComunicacionServiciosSis.getEntidadCodPersona(usuarioSesion.getCodPersona());
				if (entidades.size() > 1) {
					try {
						mensaje = mensaje.replace("#entidad", getNombreEntidad(entidadSelect));
					} catch (Exception e) {
						mensaje = mensaje.replace("#entidad", entidadUsuario.getNombreEntidad());
					}
				} else 
					mensaje = mensaje.replace("#entidad", entidadUsuario.getNombreEntidad());
				mensaje = mensaje.replace("#fechaInicio", fechaInicio);
				mensaje = mensaje.replace("#fechaFinal", fechaFinal);
			}
		}
		if(tipoDeclaracion>0) {
		    /**
			function continueOne(){
				var totalEntidades = #{declaracionRentaBean.totalEntidades};	
				console.log(id);
				if(id == 742){
					 $('#modificacion').modal({backdrop: 'static', keyboard: false})  ;
				}else if(totalEntidades == 1){
			        $('#msgantes').modal({backdrop: 'static', keyboard: false})  ;
				}else{
					$('#listEntidades').modal({backdrop: 'static', keyboard: false})  ;
				}
				
			}
		     */
			
			RequestContext.getCurrentInstance().execute("$('#accionRealizarDialog').modal('hide');");
		    if (tipoDeclaracion == BienesRentasBean.COD_TIPO_DECLARACION_MODIFICACION.intValue()){
		    	RequestContext.getCurrentInstance().execute("$('#modificacion').modal('show');");
		    }else{
		    	if (this.totalEntidades==1){
		    		RequestContext.getCurrentInstance().execute(" $('#msgantes').modal({backdrop: 'static', keyboard: false})  ;");
		    		RequestContext.getCurrentInstance().execute("$('#msgantes').modal('show');");
		    	}else{
		    		RequestContext.getCurrentInstance().execute(" $('#listEntidades').modal({backdrop: 'static', keyboard: false})  ;");
		    		RequestContext.getCurrentInstance().execute("$('#listEntidades').modal('show');");
		    	}
		    }
		}
	}

	public void guardarDeclaracion() throws ParseException {
		String accion;
		try {
			accion = (String) this.contexto.getSessionMap().get("accion");
		} catch (Exception e) {
			accion = "8";
		}
		if (accion!=null && accion.equals("3") || accion.equals("2") || accion.equals("5")) {
			fechaInicio = (String) this.contexto.getSessionMap().get("fechaInicio");
			fechaFinal = (String) this.contexto.getSessionMap().get("fechaFinal");
			
			nombreEntidad = entidades!=null ? entidades.get(0).getNombreEntidad():"";
			SimpleDateFormat forAnno = new SimpleDateFormat("yyyy");
			declaracion = new Declaracion();
			declaracion.setFechaInicio(formateador.parse(fechaInicio));
			declaracion.setFechaFin(formateador.parse(fechaFinal));
			declaracion.setAudAccion(TipoAccion.INSERTAR.getValue());
			String annode = forAnno.format(declaracion.getFechaInicio());
			declaracion.setAnnoDeclaracion(new Short(annode));
			declaracion.setAudCodRol((int) usuarioSesion.getCodRol());
			declaracion.setAudCodUsuario(new BigDecimal(usuarioSesion.getId()));
			declaracion.setAudFechaActualizacion(new Date());
			declaracion.setCodPersona(new BigDecimal(usuarioSesion.getCodPersona()));
			declaracion.setCodTipoDeclaracion(tipoDeclaracion);
			declaracion.setFlgAcreenciaObligacion((short) 0);
			declaracion.setFlgActividadEconomicaPrivada((short) 0);
			declaracion.setFlgTengoOtrosIngresos((short) 0);
			declaracion.setFlgSinBienesPatrimoniales((short) 0);
			declaracion.setFlgSinCuentasAhorro((short) 0);
			declaracion.setFlgSinParticipacionJuntas((short) 0);
			declaracion.setFlgSinParientesConyugues((short) 0);
			declaracion.setFlgTengoIngresosLaborales((short) 0);
			declaracion.setFlgModificado((short) 0);
			if (totalEntidades > 1)
				declaracion.setEntidadReceptora(getNombreEntidad(entidadSelect));
			else 
				declaracion.setEntidadReceptora(nombreEntidad);
			declaracion.setFechaPresentacion(new Date());
			declaracion.setTabIndex((short) 0);
			declaracion.setConfirmacion((short) 0);
			codDec = ComunicacionServiciosBR.setDeclaracion(declaracion);
			if (codDec!=null && !codDec.equals("0")) {
				Declaracion de = ComunicacionServiciosBR.getdeclaracionCarga(usuarioSesion.getCodPersona());
				if (de.getCodDeclaracion() != null) 
					this.contexto.getSessionMap().put("codDeclaracionCarga", de.getCodDeclaracion() + "");
				else
					this.contexto.getSessionMap().put("codDeclaracionCarga", "0");
				this.contexto.getSessionMap().put("codDeclaracion", codDec);
				this.contexto.getSessionMap().put("accion", "0");
				UtilidadesFaces.redirect(PAGE_RENTAS);
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_DECLARACION_008, getLocale())));
				UtilidadesFaces.redirect(PAGE_CANCEL);
				this.contexto.getSessionMap().put("accion", "0");
			}
		} else if (accion.equals("1")) {
			UtilidadesFaces.redirect(PAGE_CANCEL);
			this.contexto.getSessionMap().put("accion", "0");
		} else if (accion.equals("4")) {
			String cod = (String) this.contexto.getSessionMap().get("codDeclaracion");
			Declaracion dec = ComunicacionServiciosBR.getdeclaracionid(Integer.parseInt(cod));
			dec.setAudCodUsuario(new BigDecimal(usuarioSesion.getId()));
			dec.setAudCodRol((int) usuarioSesion.getCodRol());
			dec.setAudAccion(63);
			nombreEntidad = entidades.get(0).getNombreEntidad();
			if (totalEntidades > 1) {
				String entidadReceptora = getNombreEntidad(entidadSelect);
				if(entidadReceptora!=null && !"".equals(entidadReceptora))
					dec.setEntidadReceptora(entidadReceptora);
			} else {
				dec.setEntidadReceptora(nombreEntidad);
			}
			ComunicacionServiciosBR.setDeclaracionUp(dec);
			this.contexto.getSessionMap().put("accion", "0");
			UtilidadesFaces.redirect(PAGE_RENTAS);
		}else if (accion.equals("14")) {
			mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_FECHA_PERIODICA_BR, getLocale());
			UtilidadesFaces.redirect(PAGE_ERROR);
			this.contexto.getSessionMap().put("accion", "0");
		}else if (accion.equals("15")) { 
			this.contexto.getSessionMap().put("paracarga", "false");
			this.contexto.getSessionMap().put("accion", "0");
			UtilidadesFaces.redirect(PAGE_RENTAS);
		}else {
			UtilidadesFaces.redirect(PAGE_CANCEL);
			this.contexto.getSessionMap().put("accion", "0");
		}

	}

	public List<SelectItem> getTiposDeclaracion() {
		SimpleDateFormat formatAn = new SimpleDateFormat("yyyy");
		int an = Integer.parseInt(formatAn.format(new Date())) - 1;
		List<SelectItem> list = new ArrayList<SelectItem>();
		List<Parametrica> listP = ComunicacionServiciosHV.getParametrica(740);
		Declaracion fil = new Declaracion();
		fil.setCodPersona(new BigDecimal(this.usuarioSesion.getCodPersona()));
		fil.setConfirmacion((short) 1);
		fil.setAnnoDeclaracion((short) an);
		fil.setCodTipoDeclaracion(741);
		List<Declaracion> dec = ComunicacionServiciosBR.getdeclaracionpersona(fil);
		boolean b = true;
		for (Declaracion d : dec) {
			if (d.getCodTipoDeclaracion() == 741 && d.getAnnoDeclaracion() == an) {
				b = false;
				break;
			}
		}
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) {
					if (!b && aux.getCodTablaParametrica().intValue() == 741)
						continue;
					list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getNombreParametro()));
				}
			}
		} catch (NullPointerException e) {
			return new ArrayList<>();
		}

		return list;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() throws NotSupportedException, SIGEP2SistemaException {
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

	/**
	 * @return the mensajeE
	 */
	public String getMensajeE() {
		return mensajeE;
	}

	/**
	 * @param mensajeE the mensajeE to set
	 */
	public void setMensajeE(String mensajeE) {
		this.mensajeE = mensajeE;
	}

	public String getStrErrorNoContinuar() {
		return strErrorNoContinuar;
	}

	public void setStrErrorNoContinuar(String strErrorNoContinuar) {
		this.strErrorNoContinuar = strErrorNoContinuar;
	}
	
	
}