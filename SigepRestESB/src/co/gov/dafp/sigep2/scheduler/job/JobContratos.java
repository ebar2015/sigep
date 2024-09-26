package co.gov.dafp.sigep2.scheduler.job;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import co.gov.dafp.sigep2.bean.Contrato;
import co.gov.dafp.sigep2.bean.Entidad;
import co.gov.dafp.sigep2.bean.ModificacionContrato;
import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.Persona;
import co.gov.dafp.sigep2.bean.ext.ContratoExt;
import co.gov.dafp.sigep2.bean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.server.SendMail;
import co.gov.dafp.sigep2.services.ContratoService;
import co.gov.dafp.sigep2.services.EntidadService;
import co.gov.dafp.sigep2.services.HojaVidaService;
import co.gov.dafp.sigep2.services.ModificacionContratoService;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.services.PersonaService;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.TipoParametro;

public class JobContratos implements Job{
	
	static Integer liDiasVencimiento1		=30;
	static Integer liDiasVencimiento2		=15;
	static Integer liDiasVencimiento3		=1;
	static Integer liDiasinactivaHV			=20;
	static String  strAsuntoNotificacion;
	static Integer liMotivoFinalizacionAut;
	static Integer liTipoModAutomatica;
	static Integer liCodUsuarioAuditoria;
	static Integer liCodRolAuditoria;
	/*Auditoria*/
	Integer codAudCodRol;
	static Integer codAudAccionInsert;
	static Integer codAudAccionUpdate;
	/*debug*/
	boolean lbDebug = true;
	
    /*bandera envio de notificaciones*/
    private static String strEnvioNotificaciones;
    
    /*Roles*/
    private String strRolADMINENT="10", strJEFECONTRATOS="14",strOPERCONTRATOS="17";
    
    LoggerSigep logger = new LoggerSigep();
    
    private List<Entidad> 	lstEntidades;
    List<ContratoExt> 		lstContratosInactivarHoy;
    List<ContratoExt> 		lstContratos30;
    Contrato contratoHoy = new Contrato();
    
    /*Busqueda de contratos*/
	ContratoService contratoService = new ContratoService();
	ContratoExt contrato30 = new ContratoExt();
	List<ContratoExt> lstContratosVencimeinto2;
	List<ContratoExt> lstContratosVencimeinto1;
	List<ContratoExt> lstContratosHV;
	
	String strCuerpoMensaje 				= "";
	String strAsuntoMensaje 				= "";
	String strMensajeInicial 				= "";
	String strMensajeContratoVencido 		= "";
	String strTablaContratoVencido 			= "";
	String strMensajeContratoA30DiasVencer 	= "";
	String strTablaContratoA30DiasVencer	= "";
	String strTablaContratoA15DiasVencer	= "";
	String strTablaContratoA1DiaVencer		= "";
	String strContratistas					= "";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Ejecuta tarea JobContratos inicia: "+ new Date(), JobContratos.class);
		cargarEntidades();
		obtenerParametricas();
		/*Modificacion*/
		ModificacionContrato modificacionContrato;
		boolean lbEnviar,lbInactivarHV;
		Integer liEnviarInactivaHv=0;
		ModificacionContratoService modificacionContratoService;
		
		/*Nombre Contratista*/
		String strNombreContratista;
			for(Entidad entidad: lstEntidades){
				lbEnviar = false;
				strMensajeInicial = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INICIAL_CONTRATO_ASUNTO_CORREO)
						.replace("%ENTIDAD%", entidad.getNombreEntidad() != null ? entidad.getNombreEntidad() : "Sin dato");
				
				StringBuilder bodyMsg = new StringBuilder();
				
				if(consultarContratosVencidos(entidad)) {
					lbEnviar = true;
					strMensajeContratoVencido = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_CORREO_CONTRATO_VENCIDO);
					strTablaContratoVencido = HTMLUtil.inicioTabla + HTMLUtil.abreFilaTabla 
							+ HTMLUtil.abreColumnaTabla + "Persona"  +  HTMLUtil.cierraColumnaTabla 
							+ HTMLUtil.abreColumnaTabla + "Número del Contrato"  +  HTMLUtil.cierraColumnaTabla +  
					HTMLUtil.abreColumnaTabla + "Objeto Contrato" +  HTMLUtil.cierraColumnaTabla + HTMLUtil.cierraFilaTabla;
					
					for(ContratoExt cont:lstContratosInactivarHoy){
						cont.setFlgActivo((short) 0);cont.getCodContrato();
						cont.setAudFechaActualizacion(new Date());
						cont.setAudCodRol(Integer.valueOf(liCodRolAuditoria));
						cont.setAudCodUsuario(BigDecimal.valueOf(liCodUsuarioAuditoria));
						cont.setAudAccion(codAudAccionUpdate);
						modificacionContrato = new ModificacionContrato();
						modificacionContrato.setCodTipoModificacionContrato(liTipoModAutomatica);
						modificacionContrato.setCodContrato(cont.getCodContrato());
						modificacionContrato.setAudFechaActualizacion(new Date());
						modificacionContrato.setAudCodUsuario(BigDecimal.valueOf(liCodUsuarioAuditoria));
						modificacionContrato.setAudCodRol(Integer.valueOf(liCodRolAuditoria));
						modificacionContrato.setAudAccion(codAudAccionInsert);
						modificacionContrato.setJustificacionModificacion("Cumplimiento del objeto del Contrato");
						modificacionContrato.setCodMotivoSuspencion(liMotivoFinalizacionAut);
						modificacionContrato.setFlgActivo((short) 1);
						modificacionContratoService = new ModificacionContratoService();
						modificacionContratoService.insertModificacionContrato(modificacionContrato);
						contratoService.updateContrato(cont);
						strTablaContratoVencido += HTMLUtil.abreFilaTabla
								+HTMLUtil.abreColumnaTabla + cont.getPrimerNombre() + " " + cont.getPrimerApellido() +  HTMLUtil.cierraColumnaTabla
								+ HTMLUtil.abreColumnaTabla + cont.getNumeroContrato()  +  HTMLUtil.cierraColumnaTabla +  
						HTMLUtil.abreColumnaTabla + cont.getObjetoContrato() +  HTMLUtil.cierraColumnaTabla + HTMLUtil.cierraFilaTabla ;
					}
					strTablaContratoVencido += HTMLUtil.finTabla;
				}
				
				if(consultarContratosA30DiasVencer(entidad)) {
					strMensajeContratoA30DiasVencer = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_CORREO_CONTRATO_A_X_DIAS_VENCER)
							.replace("%DIAS%",liDiasVencimiento1 +"");
					lbEnviar = true;
					strTablaContratoA30DiasVencer = HTMLUtil.inicioTabla + HTMLUtil.abreFilaTabla 
							+ HTMLUtil.abreColumnaTabla + "Persona"  +  HTMLUtil.cierraColumnaTabla 
							+ HTMLUtil.abreColumnaTabla + "Número del Contrato"  +  HTMLUtil.cierraColumnaTabla +  
							HTMLUtil.abreColumnaTabla + "Objeto Contrato" +  HTMLUtil.cierraColumnaTabla + HTMLUtil.cierraFilaTabla; 
					for(ContratoExt cont:lstContratos30){
						strTablaContratoA30DiasVencer += HTMLUtil.abreFilaTabla
								+ HTMLUtil.abreColumnaTabla + cont.getPrimerNombre() + " "+ cont.getPrimerApellido() +  HTMLUtil.cierraColumnaTabla
								+ HTMLUtil.abreColumnaTabla + cont.getNumeroContrato()  +  HTMLUtil.cierraColumnaTabla 
								+ HTMLUtil.abreColumnaTabla + cont.getObjetoContrato() +  HTMLUtil.cierraColumnaTabla + HTMLUtil.cierraFilaTabla ;
					}
					strMensajeContratoA30DiasVencer += HTMLUtil.finTabla;
				}
				
				if(consultarContratosA15DiasVencer(entidad)) {
					lbEnviar = true;
					strTablaContratoA15DiasVencer = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_CORREO_CONTRATO_A_X_DIAS_VENCER)
							.replace("%DIAS%",liDiasVencimiento2 +"")+ HTMLUtil.retornoCarro 
							+ HTMLUtil.inicioTabla + HTMLUtil.abreFilaTabla 
							+ HTMLUtil.abreColumnaTabla + "Persona"  +  HTMLUtil.cierraColumnaTabla 
							+ HTMLUtil.abreColumnaTabla + "Número del Contrato"  +  HTMLUtil.cierraColumnaTabla +  
							HTMLUtil.abreColumnaTabla + "Objeto Contrato" +  HTMLUtil.cierraColumnaTabla + HTMLUtil.cierraFilaTabla;		
					for(ContratoExt cont:lstContratosVencimeinto2){
						strTablaContratoA15DiasVencer += HTMLUtil.abreFilaTabla
								+ HTMLUtil.abreColumnaTabla + cont.getPrimerNombre() + " "+ cont.getPrimerApellido() +  HTMLUtil.cierraColumnaTabla
								+ HTMLUtil.abreColumnaTabla + cont.getNumeroContrato()  +  HTMLUtil.cierraColumnaTabla 
								+ HTMLUtil.abreColumnaTabla + cont.getObjetoContrato() +  HTMLUtil.cierraColumnaTabla + HTMLUtil.cierraFilaTabla ;				
					}
					strTablaContratoA15DiasVencer += HTMLUtil.finTabla;
				}
				
				/*enviar 4.  al Jefe y operador de Contratos de la Entidad y al Administrador de la Entidad informï¿½ndoles de este hecho.*/
	
				if(consultarContratosA1DiaVencer(entidad)){
					lbEnviar = true;
					strTablaContratoA1DiaVencer = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_CORREO_CONTRATO_A_X_DIAS_VENCER)
							.replace("%DIAS%",liDiasVencimiento3 +"")+ HTMLUtil.retornoCarro 
							+ HTMLUtil.inicioTabla + HTMLUtil.abreFilaTabla 
							+ HTMLUtil.abreColumnaTabla + "Persona"  +  HTMLUtil.cierraColumnaTabla 
							+ HTMLUtil.abreColumnaTabla + "Número del Contrato"  +  HTMLUtil.cierraColumnaTabla +  
							HTMLUtil.abreColumnaTabla + "Objeto Contrato" +  HTMLUtil.cierraColumnaTabla + HTMLUtil.cierraFilaTabla;
					for(ContratoExt cont:lstContratosVencimeinto1){
						strTablaContratoA1DiaVencer += HTMLUtil.abreFilaTabla
								+ HTMLUtil.abreColumnaTabla + cont.getPrimerNombre() + " "+ cont.getPrimerApellido() +  HTMLUtil.cierraColumnaTabla
								+ HTMLUtil.abreColumnaTabla + cont.getNumeroContrato()  +  HTMLUtil.cierraColumnaTabla 
								+ HTMLUtil.abreColumnaTabla + cont.getObjetoContrato() +  HTMLUtil.cierraColumnaTabla + HTMLUtil.cierraFilaTabla ;	
					}
					strTablaContratoA1DiaVencer += HTMLUtil.finTabla;
				}
				
				/*Inactivar HV*/
				/*6.  El sistema verifica si el contratista tiene otros contratos vigentes (en cualquier entidad). 
				 * Si no tiene contratos vigentes inactivarï¿½ la hoja de vida del contratista ï¿½nï¿½ dï¿½as despuï¿½s de la inactivaciï¿½n del contrato. 
				 * Se sugiere que el numero de dias para inactivar al contratista sea de 30. Sin embargo, 
				 * el numero dias debe ser parametrizable (unicamente por el rol adminFUNC) en el componente de administración del sistema. 
				 * Si tiene otros contratos en otras entidades, debe finalizar el contrato mas no inactivar la hoja de vida.
				 */
				consultarContratosInactivos(entidad);
				liEnviarInactivaHv = 0;
				Contrato contratoBuscar;
				List<ContratoExt> lstContratos;
				HojaVidaExt hoja ;
				List<HojaVidaExt> lstHojaVida;
				HojaVidaService serviceHV = new HojaVidaService();
				if(lstContratosHV!=null && lstContratosHV.size()>0){
					for(ContratoExt contHV:lstContratosHV){
						lbInactivarHV=true;
						/*para cada uno miro si tiene mas contratos vigentes*/
						contratoBuscar = new Contrato();
						contratoBuscar.setCodPersona(contHV.getCodPersona());
						lstContratos= contratoService.getContratoPersona(contratoBuscar);
						
						for(ContratoExt cont:lstContratos){
							if(cont.getFlgActivo()==1){
								lbInactivarHV = false;
							}
						}
						if(lbInactivarHV){
							hoja = new HojaVidaExt();
							hoja.setCodPersona(contHV.getCodPersona());
							hoja.setCodEntidad(entidad.getCodEntidad().longValue());
							hoja.setLimitInit(0);
							hoja.setLimitEnd(10);	
							lstHojaVida = serviceHV.getHojaVidafiltro(hoja);
							if(lstHojaVida!=null && lstHojaVida.size()>0){
								liEnviarInactivaHv=liEnviarInactivaHv+1;
								if(liEnviarInactivaHv==1){
									strContratistas = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_CORREO_CONTRATO_CONTRATISTAS)
											.replace("%DIAS%",liDiasinactivaHV +"")+ HTMLUtil.retornoCarro 
											+ HTMLUtil.inicioTabla + HTMLUtil.abreFilaTabla 
											+ HTMLUtil.abreColumnaTabla + "Persona"  +  HTMLUtil.cierraColumnaTabla 
											+ HTMLUtil.abreColumnaTabla + "Número del Contrato"  +  HTMLUtil.cierraColumnaTabla +  
											HTMLUtil.abreColumnaTabla + "Objeto Contrato" +  HTMLUtil.cierraColumnaTabla + HTMLUtil.cierraFilaTabla;
									lbEnviar = true;
								}
								hoja = lstHojaVida.get(0);
								hoja.setFlgActivo((short) 0);
								hoja.setAudAccion(codAudAccionUpdate);
								hoja.setAudCodRol(liCodRolAuditoria);
								hoja.setAudCodUsuario(BigDecimal.valueOf(liCodUsuarioAuditoria));
								hoja.setAudFechaActualizacion(new Date());
								serviceHV.updateHojaVida(hoja);
								strNombreContratista=(contHV.getPrimerNombre()!=null?contHV.getPrimerNombre():"")+" "+
									       (contHV.getSegundoNombre()!=null?contHV.getSegundoNombre():"")+" "+
									       (contHV.getPrimerApellido()!=null?contHV.getPrimerApellido():"")+" "+
									       (contHV.getSegundoApellido()!=null?contHV.getSegundoApellido():"");
								strContratistas += HTMLUtil.abreFilaTabla
										+ HTMLUtil.abreColumnaTabla + strNombreContratista +  HTMLUtil.cierraColumnaTabla
										+ HTMLUtil.abreColumnaTabla + contHV.getNumeroContrato()  +  HTMLUtil.cierraColumnaTabla 
										+ HTMLUtil.abreColumnaTabla + contHV.getObjetoContrato() +  HTMLUtil.cierraColumnaTabla + HTMLUtil.cierraFilaTabla ;	
							}
						}
					}
					if(liEnviarInactivaHv>0){
						strContratistas += HTMLUtil.finTabla;	
					}
				}
				if(lbEnviar){
			        try {
			        	PersonaService service = new PersonaService();
			        	PersonaExt personaBuscar;
			        	List<PersonaExt> lstDestinatarios ;
			        	
			        	personaBuscar= new PersonaExt();
			        	personaBuscar.setCodEntidad(entidad.getCodEntidad());
			        	personaBuscar.setCodRol(new BigDecimal(strRolADMINENT));
			        	lstDestinatarios = service.getPersonaRolEntidad(personaBuscar);
			        	String strDestinatarios="";
			        	Integer i=0;
			        	for(Persona per:lstDestinatarios){
			        		if (per.getCorreoElectronico()!= null ) {
			        			if(i==0)
				        			strDestinatarios = per.getCorreoElectronico(); 
				        		else{
				        			if(!strDestinatarios.contains(per.getCorreoElectronico()))
				        					strDestinatarios = strDestinatarios+","+per.getCorreoElectronico();
				        		}
			        		}
			        		
			        		i++;
			        	}
			        	personaBuscar= new PersonaExt();
			        	personaBuscar.setCodEntidad(entidad.getCodEntidad());
			        	personaBuscar.setCodRol(new BigDecimal(strJEFECONTRATOS));
			        	lstDestinatarios = service.getPersonaRolEntidad(personaBuscar);
			        	for(Persona per:lstDestinatarios){
			        		if (per.getCorreoElectronico()!= null ) {

				        		if(i==0){
				        			if(!strDestinatarios.contains(per.getCorreoElectronico()))
				        				strDestinatarios = per.getCorreoElectronico();
				        		}else{
				        			if(strDestinatarios!=null && !strDestinatarios.contains(per.getCorreoElectronico())){
				        				strDestinatarios = strDestinatarios+","+per.getCorreoElectronico();
				        			}else if(strDestinatarios==null){
				        				strDestinatarios = per.getCorreoElectronico();
				        			}
				        		}
			        		}
			        		
			        		i++;
			        	}
			        	personaBuscar= new PersonaExt();
			        	personaBuscar.setCodEntidad(entidad.getCodEntidad());
			        	personaBuscar.setCodRol(new BigDecimal(strOPERCONTRATOS));
			        	lstDestinatarios = service.getPersonaRolEntidad(personaBuscar);
			        	for(Persona per:lstDestinatarios){
			        		if (per.getCorreoElectronico()!= null ) {
			        			if(i==0){
				        			if(!strDestinatarios.contains(per.getCorreoElectronico()))
				        				strDestinatarios = per.getCorreoElectronico();
				        		}else{
				        			if(strDestinatarios!=null && !strDestinatarios.contains(per.getCorreoElectronico())){
				        				strDestinatarios = strDestinatarios+","+per.getCorreoElectronico();
				        			}else if(strDestinatarios==null){
				        				strDestinatarios = per.getCorreoElectronico();
				        			}
				        		}
			        		}
			        		
			        		i++;
			        	}			        	
			        	if ("S".equals(strEnvioNotificaciones)){
				            SendMail send = new SendMail();
				            generarMensajeConHTML();
				            //send.sendmailHtml(strDestinatarios, bodyMsg.toString(), strAsuntoNotificacion);		
				            send.sendmailHtml(strDestinatarios, strCuerpoMensaje, strAsuntoNotificacion);
			        	}
			        } catch (Exception e) {
			            throw new RuntimeException(e);
			        }
				}
				
			}
	}
	
	static void obtenerParametricas(){
		ParametricaService servicioparametrica = new ParametricaService();
		List<Parametrica> lstparametrica;
		try{
			lstparametrica = servicioparametrica.getParametricaIntentos("P_CT_NOT_TIEMPO_VENCIMIENTO_1");
			if(lstparametrica!=null && lstparametrica.size()>0 && lstparametrica.get(0).getValorParametro()!=null
					&& !"".equals(lstparametrica.get(0).getValorParametro() ))
				liDiasVencimiento1 = Integer.parseInt(lstparametrica.get(0).getValorParametro());
			if(liDiasVencimiento1==null||"".equals(liDiasVencimiento1))
				liDiasVencimiento1=30;
		}catch(Exception z){
			liDiasVencimiento1=30;
		}
		try{
			lstparametrica = servicioparametrica.getParametricaIntentos("P_CT_NOT_TIEMPO_VENCIMIENTO_2");
			if(lstparametrica!=null && lstparametrica.size()>0 && lstparametrica.get(0).getValorParametro()!=null
					&& !"".equals(lstparametrica.get(0).getValorParametro() ))
				liDiasVencimiento2 = Integer.parseInt(lstparametrica.get(0).getValorParametro());
			if(liDiasVencimiento2==null||"".equals(liDiasVencimiento2))
				liDiasVencimiento2=15;
		}catch(Exception z){
			liDiasVencimiento2=15;
		}
		try{
			lstparametrica = servicioparametrica.getParametricaIntentos("P_CT_NOT_TIEMPO_VENCIMIENTO_3");
			if(lstparametrica!=null && lstparametrica.size()>0 && lstparametrica.get(0).getValorParametro()!=null
					&& !"".equals(lstparametrica.get(0).getValorParametro() ))
				liDiasVencimiento3 = Integer.parseInt(lstparametrica.get(0).getValorParametro());
			if(liDiasVencimiento3==null||"".equals(liDiasVencimiento3))
				liDiasVencimiento3=1;
		}catch(Exception z){
			liDiasVencimiento3=1;
		}
		try{
			lstparametrica = servicioparametrica.getParametricaIntentos("P_CT_NOT_DIAS_INACTIVA_HV");
			if(lstparametrica!=null && lstparametrica.size()>0 && lstparametrica.get(0).getValorParametro()!=null
					&& !"".equals(lstparametrica.get(0).getValorParametro() ))
				liDiasinactivaHV = Integer.parseInt(lstparametrica.get(0).getValorParametro());
			if(liDiasinactivaHV==null||"".equals(liDiasinactivaHV))
				liDiasinactivaHV=30;
		}catch(Exception z){
			liDiasinactivaHV=30;
		}		
		try{
			lstparametrica = servicioparametrica.getParametricaIntentos("P_CT_NOT_ASUNTO");
			if(lstparametrica!=null && lstparametrica.size()>0 && lstparametrica.get(0).getValorParametro()!=null
					&& !"".equals(lstparametrica.get(0).getValorParametro() ))
				strAsuntoNotificacion = lstparametrica.get(0).getValorParametro();
			if(strAsuntoNotificacion==null||"".equals(strAsuntoNotificacion))
				strAsuntoNotificacion="NOTIFICACION AUTOMATICA ESTADO CONTRATOS";
		}catch(Exception z){
			strAsuntoNotificacion="NOTIFICACION AUTOMATICA ESTADO CONTRATOS";
		}
		
		try{
			lstparametrica = servicioparametrica.getParametricaIntentos("P_CT_NOT_ENVIA_NOTIFICACIONES");
			if(lstparametrica!=null && lstparametrica.size()>0 && lstparametrica.get(0).getValorParametro()!=null
					&& !"".equals(lstparametrica.get(0).getValorParametro() ))
				strEnvioNotificaciones = lstparametrica.get(0).getValorParametro();
			if(strEnvioNotificaciones==null||"".equals(strEnvioNotificaciones))
				strEnvioNotificaciones="N";
		}catch(Exception z){
			strEnvioNotificaciones="N";
		}
		
		liTipoModAutomatica = Integer.valueOf(TipoParametro.CONTRATO_MODIFICACION_TERMINACION_AUTOMATICA.getValue());
		liMotivoFinalizacionAut = Integer.valueOf(TipoParametro.CONTRATO_MOTIVO_SUSPENSION_CUMPLIMIENTO_OBJETO.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());	
		liCodUsuarioAuditoria = 2; //usuario SISTEMA
		liCodRolAuditoria = 5; //Rol Sistema
			
	}
	
	/*Busqueda de entidades*/
    public void cargarEntidades() {
		EntidadService entidadService = new EntidadService();
		Entidad entidadBuscar = new Entidad();
		entidadBuscar.setLimitInit(0);
		entidadBuscar.setLimitEnd(10000);
		lstEntidades = entidadService.getEntidadByAll(entidadBuscar);
   }
   
    /* Si la fecha final del contrato (o fecha final de la última prorroga si es el caso) es igual a la fecha del sistema, 
	 *   el sistema debe inactivar el contrato, dejandolo en estado Finalizado y con el motivo Cumplimiento del objeto del Contrato
	 *   @TODO Informar Administrador de la Entidad
	*/
    public boolean consultarContratosVencidos(Entidad entidad) {
    	boolean blnContratoVencido = false;
		contratoHoy = new Contrato();
		contratoHoy.setFechaFin(new Date());
		contratoHoy.setFlgActivo((short) 1);
		contratoHoy.setLimitInit(0);
		contratoHoy.setLimitEnd(1000);
		contratoHoy.setCodEntidad(entidad.getCodEntidad().longValue());
		contratoHoy.setFlgActivo((short) 1);
		lstContratosInactivarHoy = contratoService.getContratoPersonaFecha(contratoHoy);
		if(lstContratosInactivarHoy!=null && lstContratosInactivarHoy.size()>0){
			blnContratoVencido = true;	
		}
		return blnContratoVencido;
    }

    /*
	 * Si la fecha final del contrato es igual a la fecha de hoy mas 30 dias calendario (cantidad parametrizable en dias), 
	 *   el sistema debe enviar un correo con el listado completo de los contratos que estan proximos a terminar al Jefe y operador de Contratos de la Entidad 
	 *   y al Administrador de la Entidad informï¿½ndoles de este hecho (para el envï¿½o de correo se parametrizan por cada entidad, Asunto y direcciones destinatarias).
	**/
    public boolean consultarContratosA30DiasVencer(Entidad entidad) {
    	boolean blnContratosA30diasVencidos = false;
			contrato30 = new ContratoExt();
			contrato30.setDiasIni(liDiasVencimiento1);
			contrato30.setDiasFin(liDiasVencimiento1);
			contrato30.setLimitInit(0);
			contrato30.setLimitEnd(10000);
			contrato30.setCodEntidad(entidad.getCodEntidad().longValue());
			contrato30.setFlgActivo((short) 1);
			lstContratos30= contratoService.getContratoEntreDias(contrato30);
			if(lstContratos30!=null && lstContratos30.size()>0){
				blnContratosA30diasVencidos = true;
			}
		return blnContratosA30diasVencidos;
    }
    
    /*
	 *  Si la fecha final del contrato es igual a la fecha de hoy mas 15 dias calendario (cantidad parametrizable en dias), 
	 * el sistema debe enviar un correo con el listado completo de los contratos que están próximos a terminar al Jefe y operador de Contratos de la Entidad 
	 * y al Administrador de la Entidad informandoles de este hecho.
	 */
    public boolean consultarContratosA15DiasVencer(Entidad entidad) {
    	boolean blnContratoA15DiasVencer = false;
    	contrato30 = new ContratoExt();
		contrato30.setDiasIni(liDiasVencimiento2);
		contrato30.setDiasFin(liDiasVencimiento2);
		contrato30.setLimitInit(0);
		contrato30.setLimitEnd(10000);		
		contrato30.setCodEntidad(entidad.getCodEntidad().longValue());
		contrato30.setFlgActivo((short) 1);
		lstContratosVencimeinto2= contratoService.getContratoEntreDias(contrato30);
		if(lstContratosVencimeinto2!=null && lstContratosVencimeinto2.size()>0){
			blnContratoA15DiasVencer = false;
		}
		return blnContratoA15DiasVencer;
    }
    
    /**
	 * Si la fecha final del contrato es igual a la fecha de hoy mas 1 dia calendario, el sistema debe enviar un correo con el listado completo 
	 * de los contratos que estan proximos a terminar al Jefe y operador de Contratos, operador de contratos de la Entidad y 
	 * al Administrador de la Entidad informandoles de este hecho.
	 */
    public boolean consultarContratosA1DiaVencer(Entidad entidad) {
    	boolean blnContratosA1DiaVencer = false;
		contrato30 = new ContratoExt();
		contrato30.setDiasIni(liDiasVencimiento3);
		contrato30.setDiasFin(liDiasVencimiento3);
		contrato30.setFlgActivo((short) 1);
		contrato30.setLimitInit(0);
		contrato30.setLimitEnd(10000);
		contrato30.setCodEntidad(entidad.getCodEntidad().longValue());
		contrato30.setFlgActivo((short) 1);
		lstContratosVencimeinto1= contratoService.getContratoEntreDias(contrato30);
		if(lstContratosVencimeinto1!=null && lstContratosVencimeinto1.size()>0){
			blnContratosA1DiaVencer = true;
		}
		return blnContratosA1DiaVencer;
    }
    
    /*Inactivar HV*/
	/* El sistema verifica si el contratista tiene otros contratos vigentes (en cualquier entidad). 
	 * Si no tiene contratos vigentes inactivarï¿½ la hoja de vida del contratista ï¿½nï¿½ dï¿½as despuï¿½s de la inactivaciï¿½n del contrato. 
	 * Se sugiere que el numero de dias para inactivar al contratista sea de 30. Sin embargo, 
	 * el nï¿½mero de dï¿½as debe ser parametrizable (ï¿½nicamente por el rol adminFUNC) en el componente d
	 * e administraciï¿½n del sistema. 
	 * Si tiene otros contratos en otras entidades, debe finalizar el contrato mas no inactivar la hoja de vida.	 
     * se traen los contratos con mï¿½s de 30 dï¿½as de finalizados*/
    public void consultarContratosInactivos(Entidad entidad) {
    	contrato30 = new ContratoExt();
		contrato30.setDiasIni(liDiasinactivaHV-1);
		contrato30.setDiasFin(liDiasinactivaHV);
		contrato30.setLimitInit(0);
		contrato30.setLimitEnd(10000);
		contrato30.setContratosActivos("N");
		contrato30.setCodEntidad(entidad.getCodEntidad().longValue());
		lstContratosHV= contratoService.getContratoEntreDias(contrato30);
    }


	public void generarMensajeConHTML() {
		strCuerpoMensaje = HTMLUtil.abreParrafo +  HTMLUtil.retornoCarro + HTMLUtil.retornoCarro + strMensajeInicial + HTMLUtil.cierraParrafo
				+ HTMLUtil.retornoCarro+ strMensajeContratoVencido + HTMLUtil.retornoCarro + HTMLUtil.retornoCarro + strTablaContratoVencido + HTMLUtil.retornoCarro 
				+ strMensajeContratoA30DiasVencer+ HTMLUtil.retornoCarro + strTablaContratoA30DiasVencer + HTMLUtil.retornoCarro + strTablaContratoA15DiasVencer
				+ HTMLUtil.retornoCarro + strContratistas + HTMLUtil.retornoCarro ;

		strCuerpoMensaje = ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.MAIL_SISTEMA_BASE_CUERPO_ENCABEZADO)
				.replace("CNS_LOGO",			ConfigurationBundleConstants.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LOGO_CORREO))
				.replace("CNS_LEMA_GOBIERNO",	ConfigurationBundleConstants.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LEMA_GOBIERNO))
				+ strCuerpoMensaje
				+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_SISTEMA_FIRMA)
						.replace("CNS_LOGO",		 		ConfigurationBundleConstants.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LOGO_CORREO))
						.replace("CNS_ADMIN_NOMBRE", 		ConfigurationBundleConstants.adminNombre())
						.replace("CNS_ADMIN_CARGO", 		ConfigurationBundleConstants.adminCargo())
						.replace("CNS_CUENTA_CORREO_ADMIN", ConfigurationBundleConstants.adminCuentaCorreo())
						.replace("CNS_ADMIN_TELEFONO", 		ConfigurationBundleConstants.adminTelefono())
				+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_SISTEMA_BASE_CUERPO_PIE);
	}
}
