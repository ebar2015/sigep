package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
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

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.entities.LogroRecurso;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.ParticipacionInstitucion;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.DatoAdicionalExt;
import co.gov.dafp.sigep2.mbean.ext.EvaluacionDesempenoExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.ParticipacionProyectoExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PublicacionExt;
import co.gov.dafp.sigep2.mbean.ext.ReconocimientoExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;

@Named
@ViewScoped
@ManagedBean

public class GerenciaPublicaHVBean extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3058967348829367150L;
	/*Dato Adicional (Informacion Personal)*/
	private List<DatoAdicionalExt> lstDatosAdicionales;
	private DatoAdicionalExt datoAdicionalProcesar;
	private boolean lbMostrarTextoOtraOrientacionSexual;
	
	/*Logros y Manejo de recursos públicos*/
	private List<LogroRecurso> lstLogrosYRecursos;
	private LogroRecurso logroRecursoProcesar;
	private boolean flgHabilitaPanelLogroRecurso,flgLogroRecursoEsConsulta;
	
	/*Publicaciones*/
	private List<PublicacionExt> lstPublicaciones;
	private PublicacionExt publicacionProcesar;
	private boolean flgHabilitaPanelPublicacion,flgPublicacionEsConsulta;
	
	/*Evaluación de desempeño*/
	EvaluacionDesempenoExt evDesempennoProcesar;
	List<EvaluacionDesempenoExt> lstEvsDedesempenno;
	private boolean flgHabilitaPanelEvDesempenno, flgEvDesempennoEsConsulta;
	
	/*Premios y Reconocimientos*/
	ReconocimientoExt premioReconocimientoProcesar;
	List<ReconocimientoExt> lstPremiosReconocimientos;
	private boolean flgHabilitaPanelPremioReconocimiento, flgPremioReconocimientoEsConsulta, flgPremioReconocimientoActivaDepto, flgPremioReconocimientoActivaMunicipio;
	
	/*Participacion Proyectos*/
	private ParticipacionProyectoExt participacionProyectoProcesar;
	private List<ParticipacionProyectoExt> lstParticipacionproyectos;
	private boolean flgHabilitaPanelParticipacionProyecto, flgParticipacionProyectoEsConsulta,flgParticipacionproyectoActivaDepto,flgParticipacionproyectoActivaMunicipio;
	
	/*Participacion Corporaciones (Institucion)*/
	private ParticipacionInstitucion participacionCorporacionProcesar;
	private List<ParticipacionInstitucion> lstParticipacionCorporaciones;
	private boolean flgHabilitaPanelParticipacionCorporacion, flgParticipacionCorporacionEsConsulta;
	
	/*Generales*/
	private String strMensajeConfirmacion;
	private boolean flgAccionCancelaLogroRecurso,flgAccionEliminaLogroRecurso,
					flgAccionCancelaPublicacion,flgAccionEliminaPublicacion, 
					flgAccionCancelaEvDesempenno, flgAccionEliminaEvDesempenno,
					flgAccionCancelaPremioReconocimiento, flgAccionEliminaPremioReconocimiento,
					flgAccionCancelaParticipacionProyecto, flgAccionEliminaParticipacionProyecto,
					flgAccionCancelaParticipacionCorporacion, flgAccionEliminaParticipacionCorporacion;
	private UsuarioDTO usuarioSesion = new UsuarioDTO();
	Integer codAudCodRol, codAudAccionInsert = 3, codAudAccionUpdate = 2;
	BigDecimal codAudUsuario,codigoPersona;

	ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	DatosPersonalesBean mBDatosPersonalesBean = (DatosPersonalesBean) elContext.getELResolver().getValue(elContext,	null, "datosPersonalesBean");
	
	/*Messages*/
	private String strMsgSolictarCancelar;
	
	/*Consulta desde gestion HV*/
	private boolean lbIsConsultaGestionHV;
	
	private HistoricoModificacionHojaVida modificacionHojaVida;	
	PersonaExt persona;

	
	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
	}

	@PostConstruct
	public void init() {
		
		/* Validamos el origen del acceso
		 * 1: Mi Hoja de vida
		 * 2: Accedo directo formulario gerencia pública
		 * 3: Gestionar Hoja de vida
		 * 4: Gestionar Hoja de vida - Consulta
		 * */
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String recursoId = paramMap.get("opcionGerenciaPublica");
		lbIsConsultaGestionHV = false;
		if(recursoId!=null && !"".equals(recursoId)){
			if("3".equals(recursoId) || "4".equals(recursoId)){
				Long codPer = (Long) externalContext.getSessionMap().get("id");
				if (codPer != null){
					codigoPersona = BigDecimal.valueOf(codPer);
				}
				if(codigoPersona==null){
					usuarioSesion = (UsuarioDTO) externalContext.getSessionMap().get("usuarioSesion");		
					codigoPersona = BigDecimal.valueOf(usuarioSesion.getCodPersona());
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error obteniendo persona");
				}
				if("4".equals(recursoId)){
					lbIsConsultaGestionHV = true;
					cargarListas();
				}
			}else{
				usuarioSesion = (UsuarioDTO) externalContext.getSessionMap().get("usuarioSesion");		
				codigoPersona = BigDecimal.valueOf(usuarioSesion.getCodPersona());	
				if("2".equals(recursoId))
					cargarListas();
			}
		}else{
			usuarioSesion = (UsuarioDTO) externalContext.getSessionMap().get("usuarioSesion");		
			codigoPersona = BigDecimal.valueOf(usuarioSesion.getCodPersona());			
		}
		if("1".equals(recursoId))
			cargarListas();
		datoAdicionalProcesar = new DatoAdicionalExt();
		
		codAudUsuario=BigDecimal.valueOf(usuarioSesion.getId());
		if(codAudUsuario==null || codAudUsuario.equals(new BigDecimal(0))) {
			UsuarioDTO user = (UsuarioDTO) externalContext.getSessionMap().get("usuarioSesion");	
			codAudUsuario = BigDecimal.valueOf(user.getId());
		}
		codAudCodRol = (int) this.getRolAuditoria().getId();
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());	
		
		strMsgSolictarCancelar = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_HV_GP_SOLICITAR_CANCELAR, getLocale());
		
		modificacionHojaVida = new HistoricoModificacionHojaVida();	
		persona = ComunicacionServiciosHV.getPersonaporIdExt(codigoPersona.longValue());
		

		/*cargarListas();*/
	}
	public void cargarListas(){
		cargarListaLogrosYRecursos();
		cargarListaPublicaciones();
		cargarListaEvDesempenno();
		cargarListaPremioReconocimiento();
		cargarListaParticipacionProyecto();
		cargarListaParticipacionCorporacion();		
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
	
	public void guardarGerenciaPublica(){
		if(mBDatosPersonalesBean.isLbISGestionarHV()){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogJustificaGerenciaPublica').show()");
		}else{
			if(mBDatosPersonalesBean.getTabGerenciaPublica()==0){
				guardarLogroYRecurso();
			}else if(mBDatosPersonalesBean.getTabGerenciaPublica()==1){
				guardarPublicacion();
			}else if(mBDatosPersonalesBean.getTabGerenciaPublica()==2){
				guardarEvDesempenno();
			}else if(mBDatosPersonalesBean.getTabGerenciaPublica()==3){
				guardarPremioReconocimiento();
			}else if(mBDatosPersonalesBean.getTabGerenciaPublica()==4){
				guardarParticipacionProyecto();
			}else if(mBDatosPersonalesBean.getTabGerenciaPublica()==5){
				guardarParticipacionCorporacion();
			}
		}
	}
	
	public void confirmarDatosGuardar() {
		List<String> camposEditados = new ArrayList<>();
		if(mBDatosPersonalesBean.getTabGerenciaPublica()==0){
			guardarLogroYRecurso();
			camposEditados.add("CAMBIOS EN: " +"LOGROS Y MANEJO DE LOS RECURSOS");
		}else if(mBDatosPersonalesBean.getTabGerenciaPublica()==1){
			guardarPublicacion();
			camposEditados.add("CAMBIOS EN: " +"PUBLICACIONES");
		}else if(mBDatosPersonalesBean.getTabGerenciaPublica()==2){
			guardarEvDesempenno();
			camposEditados.add("CAMBIOS EN: " +"EVALUACIONES DE DESEMPEÑO");
		}else if(mBDatosPersonalesBean.getTabGerenciaPublica()==3){
			guardarPremioReconocimiento();
			camposEditados.add("CAMBIOS EN: " +"PREMIOS Y RECONOCIMIENTOS");
		}else if(mBDatosPersonalesBean.getTabGerenciaPublica()==4){
			guardarParticipacionProyecto();
			camposEditados.add("CAMBIOS EN: " +"PARTICIPACION EN PROYECTOS");
		}else if(mBDatosPersonalesBean.getTabGerenciaPublica()==5){
			guardarParticipacionCorporacion();
			camposEditados.add("CAMBIOS EN: " +"PARTICIPACION EN CORPORACIONES");
		}
		HojaVidaExt hojaVidaFilter = new HojaVidaExt();
		hojaVidaFilter.setCodPersona(codigoPersona);
		hojaVidaFilter.setCodEntidad(this.getEntidadUsuario().getId());
		hojaVidaFilter.setFlgActivo(true);
		hojaVidaFilter.setLimitEnd(1);

		
		List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);		
		modificacionHojaVida.setCodHojaVida(BigDecimal.valueOf(listHojaVida.get(0).getCodHojaVida()));
		modificacionHojaVida.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		modificacionHojaVida.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		modificacionHojaVida.setAudCodRol((int) this.getRolAuditoria().getId());
		modificacionHojaVida.setAudFechaModificacion(DateUtils.getFechaSistema());
		ComunicacionServiciosHV.setmodificacionhohadevida(modificacionHojaVida);		
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogJustificaGerenciaPublica').hide()");	
		camposEditados.add("JUSTIFICACION DE LA MODIFICACION: " +modificacionHojaVida.getJustificacionModificacion());
		DocumentosAdicionalesHVBean.enviarNotificacioncambios(persona.getNombreUsuario(),persona.getCorreoElectronico(),camposEditados);
		modificacionHojaVida = new HistoricoModificacionHojaVida();

	}	
	
	/*Datos Adicionales*/
	public void guardarDatoAdicional(){
		
	}
	public void mostrarTextoOtraOrientacion(){
		if(datoAdicionalProcesar!=null &&datoAdicionalProcesar.getCodOrientacionSexual()!=null 
				&& datoAdicionalProcesar.getCodOrientacionSexual().equals(TipoParametro.GERENCIA_PUBLICA_OTRA_ORIENTACION_SEXUAL.getValue())){
			lbMostrarTextoOtraOrientacionSexual = true;
		}else{
			lbMostrarTextoOtraOrientacionSexual =  false;	
		}
		logger.error("lbMostrarTextoOtraOrientacionSexual",lbMostrarTextoOtraOrientacionSexual);
	}
	/*Fin datos adicionales*/
	
	/*Logros y Manejo de recursos*/
	/*public void nuevoLogroRecurso(){
		flgHabilitaPanelLogroRecurso = true;
		logroRecursoProcesar = new LogroRecurso();
		logroRecursoProcesar.setAudFechaActualizacion(new Date());
		logroRecursoProcesar.setAudCodRol(codAudCodRol);
		logroRecursoProcesar.setAudAccion(codAudAccionInsert);
		logroRecursoProcesar.setAudCodUsuario(codAudUsuario);		
		logroRecursoProcesar.setCodPersona(codigoPersona);
		logroRecursoProcesar.setFlgActivo((short) 1);
		
	}*/
	public void guardarLogroYRecurso(){
		boolean valid = ComunicacionServiciosHV.setLogroRecursoPorPersona(logroRecursoProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error guardando");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			cancelarGeneral();
			cargarListaLogrosYRecursos();
			mBDatosPersonalesBean.refrescarProgresoHojaVida();
			mBDatosPersonalesBean.actualizaModificacionHv();
		}
	}
	public void solicitarConfirmacionCancelarLogroRecurso(){
		setStrMensajeConfirmacion(strMsgSolictarCancelar);
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublica').show();");
		accionesFalse();
		flgAccionCancelaLogroRecurso = true;
	}
	private void cancelarLogroRecurso(){
		cancelarGeneral();
	}
	public void solicitarConfirmacionEliminarLogroRecurso(LogroRecurso logro){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_GP_ELIMINAR_LOGRO, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublica').show();");
		accionesFalse();
		flgAccionEliminaLogroRecurso = true;
		logroRecursoProcesar = logro;
	}
	private void eliminarLogroRecurso(){
		logroRecursoProcesar.setAudFechaActualizacion(new Date());
		logroRecursoProcesar.setAudCodRol(codAudCodRol);
		logroRecursoProcesar.setAudAccion(codAudAccionUpdate);
		logroRecursoProcesar.setAudCodUsuario(codAudUsuario);		
		logroRecursoProcesar.setCodPersona(codigoPersona);
		logroRecursoProcesar.setValorRecursoEconomico(null);
		logroRecursoProcesar.setNumPersonasCargo(null);
		logroRecursoProcesar.setNumEmpleados(null);
		logroRecursoProcesar.setLogroSobresaliente(null);
		logroRecursoProcesar.setCodTipoRegistro(null);
		logroRecursoProcesar.setFlgActivo((short) 0);
		boolean valid = ComunicacionServiciosHV.setLogroRecursoPorPersona(logroRecursoProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error eliminando");
		}else{
			
			mBDatosPersonalesBean.actualizaModificacionHv();
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"eliminacion Exitoso");
			cancelarGeneral();
			cargarListaLogrosYRecursos();
		}		
	}
	
	private void cargarListaLogrosYRecursos(){
		LogroRecurso buscador = new LogroRecurso();
		buscador.setCodPersona(codigoPersona);
		buscador.setFlgActivo((short) 1);
		lstLogrosYRecursos = ComunicacionServiciosHV.getLogrosRecursosFiltroGerencia(buscador);
	}
	
	public void modificarLogroRecurso(LogroRecurso logro){
		logroRecursoProcesar = logro;
		flgLogroRecursoEsConsulta = false;
		flgHabilitaPanelLogroRecurso = true;
	}
	public void consultarLogroRecurso(LogroRecurso logro){
		logroRecursoProcesar = logro;
		flgLogroRecursoEsConsulta = true;
		flgHabilitaPanelLogroRecurso = true;
	}
	
	public void habilitaPanelesLogros(){
		logroAdmonRecursoEconomico();
		logroAdmonPersonal();
	}
	public boolean logroAdmonRecursoEconomico(){
		if(logroRecursoProcesar!=null && ("1".equals(logroRecursoProcesar.getCodTipoRegistro()) ||"3".equals(logroRecursoProcesar.getCodTipoRegistro())) )
			return true;
		return false;
	}
	public boolean logroAdmonPersonal(){
		if(logroRecursoProcesar!=null && ("2".equals(logroRecursoProcesar.getCodTipoRegistro()) ||"3".equals(logroRecursoProcesar.getCodTipoRegistro())) )
			return true;
		return false;
	}
	public boolean logroAdmonAmbos(){
		if(logroRecursoProcesar!=null && ("3".equals(logroRecursoProcesar.getCodTipoRegistro()) ) )
			return true;
		return false;
	}
	
	
	
	
	
	/*Publicaciones*/
	public void nuevoPublicacion(){
		flgHabilitaPanelPublicacion = true;
		flgPublicacionEsConsulta = false;
		publicacionProcesar = new PublicacionExt();
		publicacionProcesar.setAudFechaActualizacion(new Date());
		publicacionProcesar.setAudCodRol(codAudCodRol);
		publicacionProcesar.setAudAccion(codAudAccionInsert);
		publicacionProcesar.setAudCodUsuario(codAudUsuario);		
		publicacionProcesar.setCodPersona(codigoPersona);
		publicacionProcesar.setFlgActivo((short) 1);
	}
	public void guardarPublicacion(){
		boolean valid = ComunicacionServiciosHV.setpublicacionoporpersona(publicacionProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error guardando");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			cancelarGeneral();
			cargarListaPublicaciones();
			mBDatosPersonalesBean.refrescarProgresoHojaVida();
			mBDatosPersonalesBean.actualizaModificacionHv();
		}		
	}
	public void solicitarConfirmacionCancelarPublicacion(){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_HV_GP_SOLICITAR_CANCELAR, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublicaPublicaciones').show();");
		accionesFalse();
		flgAccionCancelaPublicacion = true;		
	}
	private void cancelarPublicacion(){
		cancelarGeneral();
	}
	public void solicitarConfirmacionEliminarPublicacion(PublicacionExt pub){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_GP_ELIMINAR_PUBLICACION, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublicaPublicaciones').show();");
		accionesFalse();
		flgAccionEliminaPublicacion = true;
		publicacionProcesar = pub;		
	}
	private void eliminarPublicacion(){
		publicacionProcesar.setAudFechaActualizacion(new Date());
		publicacionProcesar.setAudCodRol(codAudCodRol);
		publicacionProcesar.setAudAccion(codAudAccionUpdate);
		publicacionProcesar.setAudCodUsuario(codAudUsuario);		
		publicacionProcesar.setCodPersona(codigoPersona);
		publicacionProcesar.setFlgActivo((short) 0);
		boolean valid =  ComunicacionServiciosHV.setpublicacionoporpersona(publicacionProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error eliminando");
		}else{
			
			mBDatosPersonalesBean.actualizaModificacionHv();
			
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"eliminacion Exitoso");
			cancelarGeneral();
			cargarListaPublicaciones();
		}	
	}
	private void cargarListaPublicaciones(){
		PublicacionExt buscador = new PublicacionExt();
		buscador.setCodPersona(codigoPersona);
		buscador.setFlgActivo((short) 1);
		lstPublicaciones = ComunicacionServiciosHV.getPublicacionesFiltro(buscador);
	}
	public void modificarPublicacion(PublicacionExt pub){
		publicacionProcesar = pub;
		flgPublicacionEsConsulta = false;
		flgHabilitaPanelPublicacion = true;
	}
	public void consultarPublicacion(PublicacionExt pub){
		publicacionProcesar = pub;
		flgPublicacionEsConsulta = true;
		flgHabilitaPanelPublicacion = true;
	}
	
	/*EV DE DESEMPENNO*/
	/*public void nuevoEvDesempenno(){
		flgHabilitaPanelEvDesempenno = true;
		flgEvDesempenno = true;
		evDesempennoProcesar = new EvaluacionDesempenoExt();
		evDesempennoProcesar.setAudFechaActualizacion(new Date());
		evDesempennoProcesar.setAudCodRol(codAudCodRol);
		evDesempennoProcesar.setAudAccion(codAudAccionInsert);
		evDesempennoProcesar.setAudCodUsuario(codAudUsuario);		
		evDesempennoProcesar.setCodPersona(codigoPersona);
		evDesempennoProcesar.setFlgActivo((short) 1);
	}*/
	public void guardarEvDesempenno(){
		evDesempennoProcesar.setCodTipoEvaluacion(566);
		evDesempennoProcesar.setCodNivelCumplimiento(568);
		boolean valid = ComunicacionServiciosHV.setEvaluacionDesempeno(evDesempennoProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error guardando");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			cancelarGeneral();
			cargarListaEvDesempenno();
		}			
	}
	public void solicitarConfirmacionCancelarEvDesempenno(){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_HV_GP_SOLICITAR_CANCELAR, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublicaEvDesempenno').show();");
		accionesFalse();
		flgAccionCancelaEvDesempenno = true;				
	}
	private void cancelarEvDesempenno(){
		cancelarGeneral();
	}
	public void solicitarConfirmacionEliminarEvDesempenno(EvaluacionDesempenoExt ev){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_GP_ELIMINAR_EVALUACION, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublicaEvDesempenno').show();");
		accionesFalse();
		flgAccionEliminaEvDesempenno = true;
		evDesempennoProcesar = ev;
	}
	private void eliminarEvDesempenno(){
		evDesempennoProcesar.setAudFechaActualizacion(new Date());
		evDesempennoProcesar.setAudCodRol(codAudCodRol);
		evDesempennoProcesar.setAudAccion(codAudAccionUpdate);
		evDesempennoProcesar.setAudCodUsuario(codAudUsuario);		
		evDesempennoProcesar.setCodPersona(codigoPersona);
		evDesempennoProcesar.setEscalaEvaluacion(null);
		evDesempennoProcesar.setResultadoEvaluacion(null);
		evDesempennoProcesar.setFechaInicioEvaluacion(null);
		evDesempennoProcesar.setFechaFinEvaluacion(null);
		evDesempennoProcesar.setFlgActivo((short) 0);
		boolean valid = ComunicacionServiciosHV.setEvaluacionDesempeno(evDesempennoProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error eliminando");
		}else{
			
			mBDatosPersonalesBean.actualizaModificacionHv();
			
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"eliminacion Exitoso");
			cancelarGeneral();
			cargarListaEvDesempenno();
		}			
	}
	private void cargarListaEvDesempenno(){
		EvaluacionDesempenoExt buscador = new EvaluacionDesempenoExt();
		buscador.setCodPersona(codigoPersona);
		buscador.setFlgActivo((short) 1);
		lstEvsDedesempenno = ComunicacionServiciosHV.getEvDesempenno(buscador);
		
	}
	public void modificarEvDesempenno(EvaluacionDesempenoExt ev){
		evDesempennoProcesar = ev;
		flgEvDesempennoEsConsulta = false;
		flgHabilitaPanelEvDesempenno = true;
	}
	public void consultarEvDesempenno(EvaluacionDesempenoExt ev){
		evDesempennoProcesar = ev;
		flgEvDesempennoEsConsulta = true;
		flgHabilitaPanelEvDesempenno = true;
	}
	
	/*PREMIOS Y RECONOMCIMIENTOS*/
	public void nuevoPremioReconocimiento(){
		flgHabilitaPanelPremioReconocimiento = true;
		premioReconocimientoProcesar = new ReconocimientoExt();
		premioReconocimientoProcesar.setAudFechaActualizacion(new Date());
		premioReconocimientoProcesar.setAudCodRol(codAudCodRol);
		premioReconocimientoProcesar.setAudAccion(codAudAccionInsert);
		premioReconocimientoProcesar.setAudCodUsuario(codAudUsuario);		
		premioReconocimientoProcesar.setCodPersona(codigoPersona);
		premioReconocimientoProcesar.setFlgActivo((short) 1);		
		
	}
	public void guardarPremioReconocimiento(){
		if(premioReconocimientoProcesar.isBanderaEsPremio()){
			premioReconocimientoProcesar.setFlgEsPremio((short) 1);
			premioReconocimientoProcesar.setFlgEsReconocimiento((short) 0);
		}else{
			premioReconocimientoProcesar.setFlgEsPremio((short) 0);
			premioReconocimientoProcesar.setFlgEsReconocimiento((short) 1);			
		}
			
		boolean valid = ComunicacionServiciosHV.setreconocimientoporpersona(premioReconocimientoProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error guardando");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			cancelarGeneral();
			cargarListaPremioReconocimiento();
			mBDatosPersonalesBean.refrescarProgresoHojaVida();
			mBDatosPersonalesBean.actualizaModificacionHv();
		}		
	}
	public void solicitarConfirmacioncancelarPremioReconocimiento(){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_HV_GP_SOLICITAR_CANCELAR, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublicaPremio').show();");
		accionesFalse();
		flgAccionCancelaPremioReconocimiento = true;			
	}
	private void cancelarPremioReconocimiento(){
		cancelarGeneral();
	}
	public void solicitarConfirmacionEliminarPremioReconocimiento(ReconocimientoExt premio){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_GP_ELIMINAR_PREMIO, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublicaPremio').show();");
		accionesFalse();
		flgAccionEliminaPremioReconocimiento = true;
		premioReconocimientoProcesar = premio;		
	}
	private void eliminarPremioReconocimiento(){
		premioReconocimientoProcesar.setAudFechaActualizacion(new Date());
		premioReconocimientoProcesar.setAudCodRol(codAudCodRol);
		premioReconocimientoProcesar.setAudAccion(codAudAccionUpdate);
		premioReconocimientoProcesar.setAudCodUsuario(codAudUsuario);		
		premioReconocimientoProcesar.setCodPersona(codigoPersona);
		premioReconocimientoProcesar.setFlgActivo((short) 0);
		boolean valid = ComunicacionServiciosHV.setreconocimientoporpersona(premioReconocimientoProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error eliminando");
		}else{
			
			mBDatosPersonalesBean.actualizaModificacionHv();
			
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"eliminacion Exitoso");
			cancelarGeneral();
			cargarListaPremioReconocimiento();
		}		
	}
	private void cargarListaPremioReconocimiento(){
		
		
		ReconocimientoExt buscador = new ReconocimientoExt();
		buscador.setCodPersona(codigoPersona);
		buscador.setFlgActivo((short) 1);
		lstPremiosReconocimientos = ComunicacionServiciosHV.getPremiosReconocimientosHV(buscador);		
		
		
	}
	public void modificarPremioReconocimiento(ReconocimientoExt premio){
		premioReconocimientoProcesar = premio;
		if (premio.getFlgEsPremio()!=null && premio.getFlgEsPremio()==1)
			premio.setBanderaEsPremio(true);
		else
			premio.setBanderaEsPremio(false);
		flgPremioReconocimientoEsConsulta = false;
		flgHabilitaPanelPremioReconocimiento = true;
	}
	public void consultarPremioReconocimiento(ReconocimientoExt premio){
		premioReconocimientoProcesar = premio;
		flgPremioReconocimientoEsConsulta = true;
		flgHabilitaPanelPremioReconocimiento = true;
	}
	
	/*PARTICIPACION PROYECTOS*/
	public void nuevoParticipacionProyecto(){
		flgHabilitaPanelParticipacionProyecto = true;
		participacionProyectoProcesar = new ParticipacionProyectoExt();
		participacionProyectoProcesar.setAudFechaActualizacion(new Date());
		participacionProyectoProcesar.setAudCodRol(codAudCodRol);
		participacionProyectoProcesar.setAudAccion(codAudAccionInsert);
		participacionProyectoProcesar.setAudCodUsuario(codAudUsuario);		
		participacionProyectoProcesar.setCodPersona(codigoPersona);
		participacionProyectoProcesar.setFlgActivo((short) 1);
		
	}
	public void guardarParticipacionProyecto(){
		boolean valid = ComunicacionServiciosHV.setparticipacionProyeporpersona(participacionProyectoProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error guardando");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			cancelarGeneral();
			cargarListaParticipacionProyecto();
			mBDatosPersonalesBean.refrescarProgresoHojaVida();
			mBDatosPersonalesBean.actualizaModificacionHv();
		}	
	}
	public void solicitarConfirmacionCancelarParticipacionProyecto(){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_HV_GP_SOLICITAR_CANCELAR, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublicaParticipacionPro').show();");
		accionesFalse();
		flgAccionCancelaParticipacionProyecto = true;			
	}
	private void cancelarParticipacionProyecto(){
		cancelarGeneral();
	}
	public void solicitarConfirmacionEliminarParticipacionProyecto(ParticipacionProyectoExt participacion){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_GP_ELIMINAR_PARTICIPACION_PROYECTO, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublicaParticipacionPro').show();");
		accionesFalse();
		flgAccionEliminaParticipacionProyecto = true;
		participacionProyectoProcesar = participacion;			
	}
	private void eliminarParticipacionProyecto(){
		participacionProyectoProcesar.setAudFechaActualizacion(new Date());
		participacionProyectoProcesar.setAudCodRol(codAudCodRol);
		participacionProyectoProcesar.setAudAccion(codAudAccionUpdate);
		participacionProyectoProcesar.setAudCodUsuario(codAudUsuario);		
		participacionProyectoProcesar.setCodPersona(codigoPersona);
		participacionProyectoProcesar.setFlgActivo((short) 0);
		boolean valid = ComunicacionServiciosHV.setparticipacionProyeporpersona(participacionProyectoProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error eliminando");
		}else{
			
			mBDatosPersonalesBean.actualizaModificacionHv();
			
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"eliminacion Exitoso");
			cancelarGeneral();
			cargarListaParticipacionProyecto();
		}			
	}
	private void cargarListaParticipacionProyecto(){
		ParticipacionProyectoExt buscador = new ParticipacionProyectoExt();
		buscador.setCodPersona(codigoPersona);
		buscador.setFlgActivo((short) 1);
		lstParticipacionproyectos = ComunicacionServiciosHV.getParticipacionProyectosHV(buscador);
	}
	public void modificarParticipacionProyecto(ParticipacionProyectoExt participacion){
		participacionProyectoProcesar = participacion;
		flgParticipacionProyectoEsConsulta = false;
		flgHabilitaPanelParticipacionProyecto = true;
	}
	public void consultarParticipacionProyecto(ParticipacionProyectoExt participacion){
		participacionProyectoProcesar = participacion;
		flgParticipacionProyectoEsConsulta = true;
		flgHabilitaPanelParticipacionProyecto = true;
	}
	/*PARTICIPACION CORPORACIONES*/
	public void nuevoParticipacionCorporacion(){
		flgHabilitaPanelParticipacionCorporacion = true;
		participacionCorporacionProcesar = new ParticipacionInstitucion();
		participacionCorporacionProcesar.setAudFechaActualizacion(new Date());
		participacionCorporacionProcesar.setAudCodRol(codAudCodRol);
		participacionCorporacionProcesar.setAudCodAccion(codAudAccionInsert.intValue());
		participacionCorporacionProcesar.setAudCodUsuario(codAudUsuario);		
		participacionCorporacionProcesar.setCodPersona(codigoPersona);
		participacionCorporacionProcesar.setFlgActivo((short) 1);		
	}
	
	public void guardarParticipacionCorporacion(){
		boolean valid = ComunicacionServiciosHV.setparticipacionInstitucionporpersona(participacionCorporacionProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error guardando");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_HV_GP_GUARDAR_EXITO);
			cancelarGeneral();
			cargarListaParticipacionCorporacion();
			mBDatosPersonalesBean.refrescarProgresoHojaVida();
			mBDatosPersonalesBean.actualizaModificacionHv();
		}			
	}
	
	public void solicitarConfirmacionCancelarParticipacionCorporacion(){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_HV_GP_SOLICITAR_CANCELAR, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublicaParticipacionCor').show();");
		accionesFalse();
		flgAccionCancelaParticipacionCorporacion = true;		
	}
	private void cancelarParticipacionCorporacion(){
		cancelarGeneral();
	}
	public void solicitarConfirmacionEliminarParticipacionCorporacion(ParticipacionInstitucion participacion){
		setStrMensajeConfirmacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_GP_ELIMINAR_PARTICIPACION_CORPORACION, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionGerenciaPublicaParticipacionCor').show();");
		accionesFalse();
		flgAccionEliminaParticipacionCorporacion= true;
		participacionCorporacionProcesar = participacion;		
	}
	private void eliminarParticipacionCorporacion(){
		participacionCorporacionProcesar.setAudFechaActualizacion(new Date());
		participacionCorporacionProcesar.setAudCodRol(codAudCodRol);
		participacionCorporacionProcesar.setAudCodAccion(codAudAccionUpdate);
		participacionCorporacionProcesar.setAudCodUsuario(codAudUsuario);		
		participacionCorporacionProcesar.setCodPersona(codigoPersona);
		participacionCorporacionProcesar.setFlgActivo((short) 0);
		boolean valid = ComunicacionServiciosHV.setparticipacionInstitucionporpersona(participacionCorporacionProcesar);
		if(!valid){
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error eliminando");
		}else{
			
			mBDatosPersonalesBean.actualizaModificacionHv();
			
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"eliminacion Exitoso");
			cancelarGeneral();
			cargarListaParticipacionCorporacion();
		}			
	}
	private void cargarListaParticipacionCorporacion(){
		ParticipacionInstitucion buscador = new ParticipacionInstitucion();
		buscador.setCodPersona(codigoPersona);
		buscador.setFlgActivo((short) 1);
		lstParticipacionCorporaciones = ComunicacionServiciosHV.getParticipacionInstitucionHV(buscador);
	}
	public void modificarParticipacionCorporacion(ParticipacionInstitucion participacion){
		participacionCorporacionProcesar = participacion;
		flgParticipacionCorporacionEsConsulta = false;
		flgHabilitaPanelParticipacionCorporacion = true;
	}
	public void consultarParticipacionCorporacion(ParticipacionInstitucion participacion){
		participacionCorporacionProcesar = participacion;
		flgParticipacionCorporacionEsConsulta = true;
		flgHabilitaPanelParticipacionCorporacion = true;
	}	
	

	/*Generales*/
	private void cancelarGeneral(){
		flgHabilitaPanelLogroRecurso = false;
		logroRecursoProcesar = null;
		
		flgHabilitaPanelPublicacion = false;
		publicacionProcesar = null;
		
		flgHabilitaPanelEvDesempenno = false;
		evDesempennoProcesar = null;

		flgHabilitaPanelPremioReconocimiento = false;
		premioReconocimientoProcesar = null;
		
		flgHabilitaPanelParticipacionProyecto = false;
		participacionProyectoProcesar = null;
		
		flgHabilitaPanelParticipacionCorporacion = false;
		participacionCorporacionProcesar = null;
		
		if(!lbIsConsultaGestionHV){
			mBDatosPersonalesBean.getUiDatosModificados().setValue("0");
			RequestContext.getCurrentInstance().update("frmPrincipal:modificadoHV");
		}
		
		accionesFalse();
	}
	private void accionesFalse(){
		flgAccionCancelaLogroRecurso=false;
		flgAccionEliminaLogroRecurso=false;
		flgAccionCancelaPublicacion=false;
		flgAccionEliminaPublicacion=false;
		flgAccionCancelaEvDesempenno=false;
		flgAccionEliminaEvDesempenno=false;		
		flgAccionCancelaPremioReconocimiento=false;
		flgAccionEliminaPremioReconocimiento=false;
		flgAccionCancelaParticipacionProyecto = false;
		flgAccionEliminaParticipacionProyecto = false;
		flgAccionCancelaParticipacionCorporacion = false;
		flgAccionEliminaParticipacionCorporacion = false;
	}
	public void accionConfirmada(){
		if(flgAccionCancelaLogroRecurso){
			cancelarLogroRecurso();
		}else if(flgAccionEliminaLogroRecurso){
			eliminarLogroRecurso();
		}else if(flgAccionCancelaPublicacion){
			cancelarPublicacion();
		}else if(flgAccionEliminaPublicacion){
			eliminarPublicacion();
		}else if(flgAccionCancelaEvDesempenno){
			cancelarEvDesempenno();
		}else if(flgAccionEliminaEvDesempenno){
			eliminarEvDesempenno();
		}else if(flgAccionCancelaPremioReconocimiento){
			cancelarPremioReconocimiento();
		}else if(flgAccionEliminaPremioReconocimiento){
			eliminarPremioReconocimiento();
		}else if(flgAccionCancelaParticipacionProyecto){
			cancelarParticipacionProyecto();
		}else if(flgAccionEliminaParticipacionProyecto){
			eliminarParticipacionProyecto();
		}else if(flgAccionCancelaParticipacionCorporacion){
			cancelarParticipacionCorporacion();
		}else if(flgAccionEliminaParticipacionCorporacion){
			eliminarParticipacionCorporacion();
		}
	}
	
	public void changePais() {
		if (premioReconocimientoProcesar.getCodPais() != null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(premioReconocimientoProcesar.getCodPais());
			if (pais.getNombrePais().toUpperCase().equals("COLOMBIA")) {
				flgPremioReconocimientoActivaDepto = true;
			} else {
				flgPremioReconocimientoActivaDepto = false;
				premioReconocimientoProcesar.setCodDepartamento(null);
				premioReconocimientoProcesar.setCodMunicipio(null);
			}
		}
	}
	public void changePaisParticipacionProyecto() {
		if (participacionProyectoProcesar.getCodPais() != null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(participacionProyectoProcesar.getCodPais());
			if (pais.getNombrePais().toUpperCase().equals("COLOMBIA")) {
				flgParticipacionproyectoActivaDepto = true;
			} else {
				flgParticipacionproyectoActivaDepto = false;
				participacionProyectoProcesar.setCodDepartamento(null);
				participacionProyectoProcesar.setCodMunicipio(null);
			}
		}
	}	
	
	public List<String> lstEntidadesParticipacionProyecto(String likeEntidad){
		List<String> lstFiltroEntidades = new ArrayList<>();
		if(likeEntidad!=null && likeEntidad.length()>2){
			Entidad buscador = new Entidad();
			buscador.setFlgActivo((short) 1);
			buscador.setNombreEntidad(likeEntidad);
			List<Entidad> lstEntidades = ComunicacionServiciosEnt.getEntidadesFiltro(buscador);
			if(lstEntidades!=null && lstEntidades.size()>0){
				for(Entidad ent:lstEntidades){
					lstFiltroEntidades.add(ent.getNombreEntidad());
				}
			}
		}
		return lstFiltroEntidades;
	}
	

	
	/*Getters y Setters*/
	public List<LogroRecurso> getLstLogrosYRecursos() {
		return lstLogrosYRecursos;
	}
	public void setLstLogrosYRecursos(List<LogroRecurso> lstLogrosYRecursos) {
		this.lstLogrosYRecursos = lstLogrosYRecursos;
	}
	public LogroRecurso getLogroRecursoProcesar() {
		return logroRecursoProcesar;
	}
	public void setLogroRecursoProcesar(LogroRecurso logroRecursoProcesar) {
		this.logroRecursoProcesar = logroRecursoProcesar;
	}
	public List<DatoAdicionalExt> getLstDatosAdicionales() {
		return lstDatosAdicionales;
	}
	public void setLstDatosAdicionales(List<DatoAdicionalExt> lstDatosAdicionales) {
		this.lstDatosAdicionales = lstDatosAdicionales;
	}
	public DatoAdicionalExt getDatoAdicionalProcesar() {
		return datoAdicionalProcesar;
	}
	public void setDatoAdicionalProcesar(DatoAdicionalExt datoAdicionalProcesar) {
		this.datoAdicionalProcesar = datoAdicionalProcesar;
	}
	public boolean isLbMostrarTextoOtraOrientacionSexual() {
		return lbMostrarTextoOtraOrientacionSexual;
	}
	public void setLbMostrarTextoOtraOrientacionSexual(boolean lbMostrarTextoOtraOrientacionSexual) {
		this.lbMostrarTextoOtraOrientacionSexual = lbMostrarTextoOtraOrientacionSexual;
	}
	public boolean isFlgHabilitaPanelLogroRecurso() {
		return flgHabilitaPanelLogroRecurso;
	}
	public void setFlgHabilitaPanelLogroRecurso(boolean flgHabilitaPanelLogroRecurso) {
		this.flgHabilitaPanelLogroRecurso = flgHabilitaPanelLogroRecurso;
	}
	public List<PublicacionExt> getLstPublicaciones() {
		return lstPublicaciones;
	}
	public void setLstPublicaciones(List<PublicacionExt> lstPublicaciones) {
		this.lstPublicaciones = lstPublicaciones;
	}
	public PublicacionExt getPublicacionProcesar() {
		return publicacionProcesar;
	}
	public void setPublicacionProcesar(PublicacionExt publicacionProcesar) {
		this.publicacionProcesar = publicacionProcesar;
	}
	public String getStrMensajeConfirmacion() {
		return strMensajeConfirmacion;
	}
	public void setStrMensajeConfirmacion(String strMensajeConfirmacion) {
		this.strMensajeConfirmacion = strMensajeConfirmacion;
	}
	public boolean isFlgHabilitaPanelPublicacion() {
		return flgHabilitaPanelPublicacion;
	}
	public void setFlgHabilitaPanelPublicacion(boolean flgHabilitaPanelPublicacion) {
		this.flgHabilitaPanelPublicacion = flgHabilitaPanelPublicacion;
	}
	public boolean isFlgLogroRecursoEsConsulta() {
		return flgLogroRecursoEsConsulta;
	}
	public void setFlgLogroRecursoEsConsulta(boolean flgLogroRecursoEsConsulta) {
		this.flgLogroRecursoEsConsulta = flgLogroRecursoEsConsulta;
	}
	public boolean isFlgPublicacionEsConsulta() {
		return flgPublicacionEsConsulta;
	}
	public void setFlgPublicacionEsConsulta(boolean flgPublicacionEsConsulta) {
		this.flgPublicacionEsConsulta = flgPublicacionEsConsulta;
	}
	public EvaluacionDesempenoExt getEvDesempennoProcesar() {
		return evDesempennoProcesar;
	}
	public void setEvDesempennoProcesar(EvaluacionDesempenoExt evDesempennoProcesar) {
		this.evDesempennoProcesar = evDesempennoProcesar;
	}
	public List<EvaluacionDesempenoExt> getLstEvsDedesempenno() {
		return lstEvsDedesempenno;
	}
	public void setLstEvsDedesempenno(List<EvaluacionDesempenoExt> lstEvsDedesempenno) {
		this.lstEvsDedesempenno = lstEvsDedesempenno;
	}
	public boolean isFlgHabilitaPanelEvDesempenno() {
		return flgHabilitaPanelEvDesempenno;
	}
	public void setFlgHabilitaPanelEvDesempenno(boolean flgHabilitaPanelEvDesempenno) {
		this.flgHabilitaPanelEvDesempenno = flgHabilitaPanelEvDesempenno;
	}
	public boolean isFlgEvDesempennoEsConsulta() {
		return flgEvDesempennoEsConsulta;
	}
	public void setFlgEvDesempennoEsConsulta(boolean flgEvDesempennoEsConsulta) {
		this.flgEvDesempennoEsConsulta = flgEvDesempennoEsConsulta;
	}
	public ReconocimientoExt getPremioReconocimientoProcesar() {
		return premioReconocimientoProcesar;
	}
	public void setPremioReconocimientoProcesar(ReconocimientoExt premioReconocimientoProcesar) {
		this.premioReconocimientoProcesar = premioReconocimientoProcesar;
	}
	public List<ReconocimientoExt> getLstPremiosReconocimientos() {
		return lstPremiosReconocimientos;
	}
	public void setLstPremiosReconocimientos(List<ReconocimientoExt> lstPremiosReconocimientos) {
		this.lstPremiosReconocimientos = lstPremiosReconocimientos;
	}
	public boolean isFlgHabilitaPanelPremioReconocimiento() {
		return flgHabilitaPanelPremioReconocimiento;
	}
	public void setFlgHabilitaPanelPremioReconocimiento(boolean flgHabilitaPanelPremioReconocimiento) {
		this.flgHabilitaPanelPremioReconocimiento = flgHabilitaPanelPremioReconocimiento;
	}
	public boolean isFlgPremioReconocimientoEsConsulta() {
		return flgPremioReconocimientoEsConsulta;
	}
	public void setFlgPremioReconocimientoEsConsulta(boolean flgPremioReconocimientoEsConsulta) {
		this.flgPremioReconocimientoEsConsulta = flgPremioReconocimientoEsConsulta;
	}

	public ParticipacionProyectoExt getParticipacionProyectoProcesar() {
		return participacionProyectoProcesar;
	}

	public void setParticipacionProyectoProcesar(ParticipacionProyectoExt participacionProyectoProcesar) {
		this.participacionProyectoProcesar = participacionProyectoProcesar;
	}

	public List<ParticipacionProyectoExt> getLstParticipacionproyectos() {
		return lstParticipacionproyectos;
	}

	public void setLstParticipacionproyectos(List<ParticipacionProyectoExt> lstParticipacionproyectos) {
		this.lstParticipacionproyectos = lstParticipacionproyectos;
	}

	public boolean isFlgHabilitaPanelParticipacionProyecto() {
		return flgHabilitaPanelParticipacionProyecto;
	}

	public void setFlgHabilitaPanelParticipacionProyecto(boolean flgHabilitaPanelParticipacionProyecto) {
		this.flgHabilitaPanelParticipacionProyecto = flgHabilitaPanelParticipacionProyecto;
	}

	public boolean isFlgParticipacionProyectoEsConsulta() {
		return flgParticipacionProyectoEsConsulta;
	}

	public void setFlgParticipacionProyectoEsConsulta(boolean flgParticipacionProyectoEsConsulta) {
		this.flgParticipacionProyectoEsConsulta = flgParticipacionProyectoEsConsulta;
	}
	public ParticipacionInstitucion getParticipacionCorporacionProcesar() {
		return participacionCorporacionProcesar;
	}
	public void setParticipacionCorporacionProcesar(ParticipacionInstitucion participacionCorporacionProcesar) {
		this.participacionCorporacionProcesar = participacionCorporacionProcesar;
	}
	public List<ParticipacionInstitucion> getLstParticipacionCorporaciones() {
		return lstParticipacionCorporaciones;
	}
	public void setLstParticipacionCorporaciones(List<ParticipacionInstitucion> lstParticipacionCorporaciones) {
		this.lstParticipacionCorporaciones = lstParticipacionCorporaciones;
	}
	public boolean isFlgHabilitaPanelParticipacionCorporacion() {
		return flgHabilitaPanelParticipacionCorporacion;
	}
	public void setFlgHabilitaPanelParticipacionCorporacion(boolean flgHabilitaPanelParticipacionCorporacion) {
		this.flgHabilitaPanelParticipacionCorporacion = flgHabilitaPanelParticipacionCorporacion;
	}
	public boolean isFlgParticipacionCorporacionEsConsulta() {
		return flgParticipacionCorporacionEsConsulta;
	}
	public void setFlgParticipacionCorporacionEsConsulta(boolean flgParticipacionCorporacionEsConsulta) {
		this.flgParticipacionCorporacionEsConsulta = flgParticipacionCorporacionEsConsulta;
	}
	public boolean isFlgPremioReconocimientoActivaDepto() {
		return flgPremioReconocimientoActivaDepto;
	}
	public void setFlgPremioReconocimientoActivaDepto(boolean flgPremioReconocimientoActivaDepto) {
		this.flgPremioReconocimientoActivaDepto = flgPremioReconocimientoActivaDepto;
	}
	public boolean isFlgPremioReconocimientoActivaMunicipio() {
		return flgPremioReconocimientoActivaMunicipio;
	}
	public void setFlgPremioReconocimientoActivaMunicipio(boolean flgPremioReconocimientoActivaMunicipio) {
		this.flgPremioReconocimientoActivaMunicipio = flgPremioReconocimientoActivaMunicipio;
	}

	public boolean isFlgParticipacionproyectoActivaDepto() {
		return flgParticipacionproyectoActivaDepto;
	}

	public void setFlgParticipacionproyectoActivaDepto(boolean flgParticipacionproyectoActivaDepto) {
		this.flgParticipacionproyectoActivaDepto = flgParticipacionproyectoActivaDepto;
	}

	public boolean isFlgParticipacionproyectoActivaMunicipio() {
		return flgParticipacionproyectoActivaMunicipio;
	}

	public void setFlgParticipacionproyectoActivaMunicipio(boolean flgParticipacionproyectoActivaMunicipio) {
		this.flgParticipacionproyectoActivaMunicipio = flgParticipacionproyectoActivaMunicipio;
	}

	public boolean isLbIsConsultaGestionHV() {
		return lbIsConsultaGestionHV;
	}

	public void setLbIsConsultaGestionHV(boolean lbIsConsultaGestionHV) {
		this.lbIsConsultaGestionHV = lbIsConsultaGestionHV;
	}

	public HistoricoModificacionHojaVida getModificacionHojaVida() {
		return modificacionHojaVida;
	}

	public void setModificacionHojaVida(HistoricoModificacionHojaVida modificacionHojaVida) {
		this.modificacionHojaVida = modificacionHojaVida;
	}
	
	
	
	
	
}
