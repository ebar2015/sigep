package co.gov.dafp.sigep2.scheduler.job;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import co.gov.dafp.sigep2.bean.CalidadDeDatosNotificacion;
import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.ProcesosCargaMasiva;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.ProcesosCargaMasivaExt;
import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.server.SendMail;
import co.gov.dafp.sigep2.services.CalidadDeDatosNotificacionService;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.services.ProcesosCargaMasivaService;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.TipoParametro;

public class JobCargasMasivas implements Job  {
	
	LoggerSigep logger = new LoggerSigep();
	
	private static String 		strEnvioNotificacionesCM;
	private static String 		strCantidadDiasDisponibilidad;
	final   static String 		S_VALOR_SI="S";
	private String 				strCuerpoMensaje;
	private String				strAsuntoMensaje;
	private String				strCuerpoConHTML;
	private static final String DIAS_CONSULTAR_REPORTE = "8";
	
	private List<ProcesosCargaMasivaExt> lstProcesosCargaMasiva;
	
	
	/*Notificaciones calidad de datos*/
	private String strCuerpoMensajeCalidadDatos;
	private String strAsuntoMensajeCalidadDatos;
	private List<CalidadDeDatosNotificacion> lstcalidadNotificacion;
	
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		inicializarParametricas();
		if(S_VALOR_SI.equals(strEnvioNotificacionesCM)){
			buscarProcesosCM();
			enviarCorreo();
		}
		
		buscarNotificacionCalidadDatos();
	}
	
	/**
	 * Metodo que busca los procesos de carga masiva que se encuentran finalizados y a los que aun no se les ha notificado
	 */
	public void buscarProcesosCM() {
		ProcesosCargaMasivaService cmService = new ProcesosCargaMasivaService();
		PersonaExt  obPersona = new PersonaExt();
		obPersona.setEstado(TipoParametro.CM_ESTADO_FINALIZADO.getValue());
		obPersona.setFlgNotificado((short)0);
		lstProcesosCargaMasiva = cmService.cargaMasivafiltro(obPersona);
	}
	
	/**
	 * Metodo que envia el correo electronico a las personas que estan pendientes por confirmacion
	 */
	public void enviarCorreo() {
		if(!lstProcesosCargaMasiva.isEmpty()) {
			for (ProcesosCargaMasivaExt procesoCM : lstProcesosCargaMasiva) {
				if(procesoCM.getCorreoElectronico() != null) {
					SendMail send = new SendMail();
					generarMensajeCorreo(procesoCM);
				    String resulEnvio = send.sendmailHtml(procesoCM.getCorreoElectronico().toLowerCase(), strCuerpoConHTML, strAsuntoMensaje );
				    if(resulEnvio=="true") {
				    	actualizarProcesoCargaMasiva(procesoCM);
				    }
				}
			}
		}	
	}
	
	/**
	 * Metodo que llena las variables utilizadas para la generacion de los mensajes.
	 * @param procesoCM
	 */
	public void generarMensajeCorreo(ProcesosCargaMasivaExt procesoCM) {
		strCuerpoMensaje = "";
		strAsuntoMensaje = "";
		if(procesoCM != null) {
			strAsuntoMensaje =  MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CARGA_MASIVA_ASUNTO_CORREO)
					.replace("%NOMBRE_TIPO_CARGUE", procesoCM.getNombreTipoCargue() != null ? procesoCM.getNombreTipoCargue() : "Sin dato");
			
			strCuerpoMensaje = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CARGA_MASIVA_CUERPO_CORREO)
					.replace("%NOMBRE_TIPO_CARGUE", procesoCM.getNombreTipoCargue() != null ? procesoCM.getNombreTipoCargue() : "Sin dato")
					.replace("%DIAS_CONSULTA", 		strCantidadDiasDisponibilidad != null ? strCantidadDiasDisponibilidad : DIAS_CONSULTAR_REPORTE)
					.replace("%FECHA_INICIO_CARGA", procesoCM.getFechaInicio() +"" )
					.replace("%FECHA_TERMINACION",  procesoCM.getFechaFin() + "")
					.replace("%NOMBRE_ENTIDAD", 	procesoCM.getNombreEntidad())
					.replace("%TIPO_CARGUE", 		procesoCM.getNombreTipoCargue())
					.replace("%USUARIO_CARGUE",		procesoCM.getNombreCompleto())
					.replace("%ESTADO_PROCESO", 	procesoCM.getNombreEstado())
					.replace("%CANTIDAD_EXITOSOS",  procesoCM.getRegistrosExitosos() != null ? procesoCM.getRegistrosExitosos().toString() : "0")
					.replace("%CANTIDAD_ERROR",		procesoCM.getRegistrosFallidos() != null ? procesoCM.getRegistrosFallidos().toString() : "0" )
					.replace("%RETORNO", 			HTMLUtil.retornoCarro);
			generarMensajeConHTML();
		}
			
	}
	
	/**
	 * Metodo que genera el cuerpo del correo con estilos
	 */
	public void generarMensajeConHTML() {
		strCuerpoConHTML = strCuerpoMensaje;
		strCuerpoConHTML = HTMLUtil.abreParrafo + "Apreciado Usuario, " + HTMLUtil.retornoCarro
				+ HTMLUtil.retornoCarro + strCuerpoConHTML + HTMLUtil.cierraParrafo;

		strCuerpoConHTML = ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.MAIL_SISTEMA_BASE_CUERPO_ENCABEZADO)
				.replace("CNS_LOGO",			ConfigurationBundleConstants.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LOGO_CORREO))
				.replace("CNS_LEMA_GOBIERNO",	ConfigurationBundleConstants.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LEMA_GOBIERNO))
				+ strCuerpoConHTML
				+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_SISTEMA_FIRMA)
						.replace("CNS_LOGO",		 		ConfigurationBundleConstants.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LOGO_CORREO))
						.replace("CNS_ADMIN_NOMBRE", 		ConfigurationBundleConstants.adminNombre())
						.replace("CNS_ADMIN_CARGO", 		ConfigurationBundleConstants.adminCargo())
						.replace("CNS_CUENTA_CORREO_ADMIN", ConfigurationBundleConstants.adminCuentaCorreo())
						.replace("CNS_ADMIN_TELEFONO", 		ConfigurationBundleConstants.adminTelefono())
				+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_SISTEMA_BASE_CUERPO_PIE);
		
	}
	
	/**
	 * Metodo que actualiza el encabezado de la carga masiva. FLG_NOTIFICADO cambia a 1
	 * @param procesoCM
	 */
	public void actualizarProcesoCargaMasiva(ProcesosCargaMasivaExt procesoCM) {
		ProcesosCargaMasivaService cmService = new ProcesosCargaMasivaService();
    	ProcesosCargaMasiva procesosCargaMasiva = new ProcesosCargaMasiva();
    	procesosCargaMasiva = procesoCM;
    	procesosCargaMasiva.setFlgNotificado((short)1);
    	ProcesosCargaMasiva result = cmService.updateProcesosCargaMasiva(procesosCargaMasiva);
    	if(result.isError()) {
    		logger.info("Error actualizando Proceso de carga masiva despues de envio de correo", JobCargasMasivas.class);
    	}
	}

	/**
	 * Metodo que inicializa las parametricas utilizadas para el envio de notificaciones de CM
	 */
	public void inicializarParametricas() {
		try{
			ParametricaService parametricaService = new ParametricaService();
			BigDecimal bdCodAlertasCMActivas = BigDecimal.valueOf(TipoParametro.CARGA_MASIVA_ACTIVA_NOTIFICACIONES.getValue());
			Parametrica prActivaNotificacionCM = parametricaService.getPrametricaById(bdCodAlertasCMActivas);
			if(prActivaNotificacionCM!=null && prActivaNotificacionCM.getValorParametro()!=null)
				strEnvioNotificacionesCM = prActivaNotificacionCM.getValorParametro();
			BigDecimal bdCodDiasReporte = BigDecimal.valueOf(TipoParametro.CM_NUMERO_DIAS_REPORTE_DISPONIBLE.getValue());
			Parametrica prDiasReporte = parametricaService.getPrametricaById(bdCodDiasReporte);
			if(prDiasReporte!=null && prDiasReporte.getValorParametro()!=null)
				strCantidadDiasDisponibilidad =  prDiasReporte.getValorParametro();
		}catch(Exception z){
			strEnvioNotificacionesCM = "N";
		}	
	}
	
	/**
	 * Variables get y set
	 */
	
	/**
	 * @return the strCuerpoMensaje
	 */
	public String getStrCuerpoMensaje() {
		return strCuerpoMensaje;
	}

	/**
	 * @param strCuerpoMensaje the strCuerpoMensaje to set
	 */
	public void setStrCuerpoMensaje(String strCuerpoMensaje) {
		this.strCuerpoMensaje = strCuerpoMensaje;
	}

	/**
	 * @return the strAsuntoMensaje
	 */
	public String getStrAsuntoMensaje() {
		return strAsuntoMensaje;
	}

	/**
	 * @param strAsuntoMensaje the strAsuntoMensaje to set
	 */
	public void setStrAsuntoMensaje(String strAsuntoMensaje) {
		this.strAsuntoMensaje = strAsuntoMensaje;
	}

	/**
	 * @return the strCuerpoConHTML
	 */
	public String getStrCuerpoConHTML() {
		return strCuerpoConHTML;
	}

	/**
	 * @param strCuerpoConHTML the strCuerpoConHTML to set
	 */
	public void setStrCuerpoConHTML(String strCuerpoConHTML) {
		this.strCuerpoConHTML = strCuerpoConHTML;
	}
	
	
	private void buscarNotificacionCalidadDatos(){
		String resulEnvio,strCuerpo ;
		SendMail send ;
		CalidadDeDatosNotificacionService service = new CalidadDeDatosNotificacionService();
		lstcalidadNotificacion = service.getProcesosPorNotificar();
		
		strCuerpo = "Se han aplicado reglas de calidad de datos";
		strCuerpo = HTMLUtil.abreParrafo + "Apreciado Usuario, " + HTMLUtil.retornoCarro
				+ HTMLUtil.retornoCarro + strCuerpo + HTMLUtil.cierraParrafo;

		strCuerpo = ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.MAIL_SISTEMA_BASE_CUERPO_ENCABEZADO)
				.replace("CNS_LOGO",			ConfigurationBundleConstants.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LOGO_CORREO))
				.replace("CNS_LEMA_GOBIERNO",	ConfigurationBundleConstants.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LEMA_GOBIERNO))
				+ strCuerpo
				+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_SISTEMA_FIRMA)
						.replace("CNS_LOGO",		 		ConfigurationBundleConstants.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LOGO_CORREO))
						.replace("CNS_ADMIN_NOMBRE", 		ConfigurationBundleConstants.adminNombre())
						.replace("CNS_ADMIN_CARGO", 		ConfigurationBundleConstants.adminCargo())
						.replace("CNS_CUENTA_CORREO_ADMIN", ConfigurationBundleConstants.adminCuentaCorreo())
						.replace("CNS_ADMIN_TELEFONO", 		ConfigurationBundleConstants.adminTelefono())
				+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_SISTEMA_BASE_CUERPO_PIE);		
		
		
		
		
		for(CalidadDeDatosNotificacion notificacion:lstcalidadNotificacion){
			send = new SendMail();
			resulEnvio = send.sendmailHtml(notificacion.getCorreoNotificacion().toLowerCase(), strCuerpo, "Notificación Proceso Calidad De Datos" );
		    if(resulEnvio=="true") {
		    	notificacion.setNotificado((short) 1);
		    	service.update(notificacion);
		    }	
		}
		
		

	}
}
