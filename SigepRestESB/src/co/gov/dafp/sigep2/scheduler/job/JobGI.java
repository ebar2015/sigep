package co.gov.dafp.sigep2.scheduler.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.Persona;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.VinculacionExt;
import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.server.SendMail;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.services.PersonaService;
import co.gov.dafp.sigep2.services.VinculacionService;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.TipoParametro;

public class JobGI implements Job{
	
	LoggerSigep logger = new LoggerSigep();
	Long llMesesInactividad, llDiasInactividad;
	String lsAlertasGeneralesActivas, lsAlertasVinculacionesActivas, lsAlertasDesVinculacionesActivas,lsAlertasSitAdsActivas, lsCuerpoMultientidad,
	       lsCuerpoMonoentidad, lsAsuntoVinculacion, lsAsuntoDesvinculacion, lsAsuntoSitAds, lsIdentificacionUsuario, configPath ;
	final static String S_VALOR_SI="S";
	final static String S_SEPARATOR_NAME_FILE="_";
	final static String S_NOMBRE_PLANTILLA ="GI_ALERTAS_NOTIFICACION.xls";
	final static String S_NOMBRE_PLANTILLA_TMP ="GI_ALERTAS_NOTIFICACION_TMP.xls";
	final static String S_PATH_PLANTILLA ="co/gov/dafp/sigep2/cargue/notificacionesgestioninformacion/plantillas/";
	final static String S_PATH_TMP ="co/gov/dafp/sigep2/cargue/notificacionesgestioninformacion/temp/";
	final static String S_NOTIFICACION_VINCULACIONES ="NotificacionVinculaciones";
	final static String S_NOTIFICACION_DESVINCULACIONES ="Notificacion Desvinculaciones";
	final static String S_NOTIFICACION_SITUACIONES_AD ="Notificacion Situaciones Administrativas";
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Ejecuta tarea JobGi inicia: "+ new Date(), JobGI.class);
		cuerpoJob();
	}
	
	public void cuerpoJob(){
		try{
			/*
			 * Inicializamos las variables del proceso de envio de alertas
			 */
			ParametricaService parametricaService = new ParametricaService();
			Parametrica parametrica;
			llMesesInactividad = llDiasInactividad=0L;
			lsAlertasGeneralesActivas = lsAlertasVinculacionesActivas = lsAlertasDesVinculacionesActivas = lsAlertasSitAdsActivas=lsCuerpoMultientidad
			= lsCuerpoMonoentidad = lsAsuntoVinculacion = lsAsuntoDesvinculacion = lsAsuntoSitAds = "N";
			/**
			 * Validamos si las alertas en general están activas
			 */
			try{
				parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_ACTIVAS.getValue()));
				if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
					lsAlertasGeneralesActivas = parametrica.getValorParametro();
			}catch(Exception z){
				logger.error("Error consultando GI_ALERTAS_ACTIVAS: "+ z.getMessage(), JobGI.class);
				lsAlertasGeneralesActivas="N";
			}
			if(S_VALOR_SI.equals(lsAlertasGeneralesActivas)){
				/**
				 * Consultamos cuales alertas especificas están activas
				*/
				Double random = ((Math.random()*1000));
				random = (double) Math.round(random.doubleValue());
				try{
					parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_VINCULACIONES_ACTIVA.getValue()));
					if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
						lsAlertasVinculacionesActivas = parametrica.getValorParametro();
				}catch(Exception z){
					logger.error("Error consultando GI_ALERTAS_VINCULACIONES_ACTIVA: "+ z.getMessage(), JobGI.class);
					lsAlertasVinculacionesActivas="N";
				}
				try{
					parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_DESVINCULACIONES_ACTIVA.getValue()));
					if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
						lsAlertasDesVinculacionesActivas = parametrica.getValorParametro();
				}catch(Exception z){
					logger.error("Error consultando GI_ALERTAS_DESVINCULACIONES_ACTIVA: "+ z.getMessage(), JobGI.class);
					lsAlertasDesVinculacionesActivas="N";
				}
				try{
					parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_SITUACIONESADMINISTRATIVAS_ACTIVA.getValue()));
					if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
						lsAlertasSitAdsActivas = parametrica.getValorParametro();
				}catch(Exception z){
					logger.error("Error consultando GI_ALERTAS_SITUACIONESADMINISTRATIVAS_ACTIVA: "+ z.getMessage(), JobGI.class);
					lsAlertasSitAdsActivas="N";
				}	
				if ( S_VALOR_SI.equals(lsAlertasVinculacionesActivas) || S_VALOR_SI.equals(lsAlertasDesVinculacionesActivas) 
						|| S_VALOR_SI.equals(lsAlertasSitAdsActivas)){
					
					configPath        = System.getProperty("CONFIG_PATH");
					if(configPath==null)
						configPath ="D:/ADA/JAVA/DAFP/01_BRANCHES/branche_development/";
					String filePathPlantilla = configPath+S_PATH_PLANTILLA+S_NOMBRE_PLANTILLA;
					String filePathTmP;
					FileInputStream fisPlantilla=null;//File
					
					HSSFWorkbook hsWbPlantilla;       //Documento de trabajo
					HSSFSheet hssSheetPlantilla;      //Hoja
					Cell cell;                        //Celda
					
					/*Date date = Calendar.getInstance().getTime();  
		            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");  
		            String fechaReporte = dateFormat.format(date);  
		            String lstrDate = dateFormat.format(date);*/
					
					Date date = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
					
		            
		            List<VinculacionExt> lstVinculacionesNotificar;
		            List<VinculacionExt> lstDesVinculacionesNotificar;
		            List<VinculacionExt> lstSituacionesNotificar;
		            VinculacionService   vinculacionService;
		            VinculacionExt       buscador;
		            Long                 liDiasNotificacion;
		            int controws;
		            Row destRow ;
		            
		            MimeMultipart multiPartes;	
		        	MimeBodyPart messageBodyPart;
		        	BodyPart adjunto;
		        	
		        	PersonaExt buscadorRoles;
		        	List<String> lstCorreosToMultiEntidad = null;
		        	
		        	String fileNameAdjunto;
		        	
					try{
						parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_MESES_INACTIVIDAD.getValue()));
						if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
							llMesesInactividad = Long.valueOf(parametrica.getValorParametro());
					}catch(Exception z){
						logger.error("Error consultando GI_ALERTAS_MESES_INACTIVIDAD: "+ z.getMessage(), JobGI.class);
						llMesesInactividad=0l;
					}
					try{
						parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_DIAS_INACTIVIDAD.getValue()));
						if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
							llDiasInactividad = Long.valueOf(parametrica.getValorParametro());
					}catch(Exception z){
						logger.error("Error consultando GI_ALERTAS_DIAS_INACTIVIDAD: "+ z.getMessage(), JobGI.class);
						llDiasInactividad=0l;
					}
					try{
						parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_CUERPO_MONOENTIDAD.getValue()));
						if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
							lsCuerpoMonoentidad = parametrica.getValorParametro();
					}catch(Exception z){
						logger.error("Error consultando GI_ALERTAS_CUERPO_MONOENTIDAD: "+ z.getMessage(), JobGI.class);
						lsCuerpoMonoentidad="N";
					}	
					try{
						parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_ASUNTO_VINCULACIONES.getValue()));
						if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
							lsAsuntoVinculacion = parametrica.getValorParametro();
					}catch(Exception z){
						logger.error("Error consultando GI_ALERTAS_ASUNTO_VINCULACIONES: "+ z.getMessage(), JobGI.class);
						lsAsuntoVinculacion="Sin Asunto";
					}	
					try{
						parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_ASUNTO_DESVINCULACIONES.getValue()));
						if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
							lsAsuntoDesvinculacion = parametrica.getValorParametro();
					}catch(Exception z){
						logger.error("Error consultando GI_ALERTAS_ASUNTO_DESVINCULACIONES: "+ z.getMessage(), JobGI.class);
						lsAsuntoDesvinculacion="Sin Asunto";
					}	
					try{
						parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_ASUNTO_SITUACIONES_ADMINISTRATIVAS.getValue()));
						if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
							lsAsuntoSitAds = parametrica.getValorParametro();
					}catch(Exception z){
						logger.error("Error consultando GI_ALERTAS_ASUNTO_SITUACIONES_ADMINISTRATIVAS: "+ z.getMessage(), JobGI.class);
						lsAsuntoSitAds="Sin Asunto";
					}
					
					liDiasNotificacion = (llMesesInactividad * 30 ) + llDiasInactividad;
			        /*Roles MultiEntidad son los mismos para las tres notificaciones*/
					buscadorRoles = new PersonaExt();
			        buscadorRoles.setCodRol(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_ROLES_MULTIENTIDAD.getValue()));
			        PersonaService personaService = new PersonaService();
			        List<PersonaExt> lstPersonasRolesMultiEntidad = personaService.selectPersonasRolesNotificaGI(buscadorRoles);
			        if(lstPersonasRolesMultiEntidad!=null && lstPersonasRolesMultiEntidad.size()>0){
			        	lstCorreosToMultiEntidad = new ArrayList<String>();
			        	for(Persona persona: lstPersonasRolesMultiEntidad){
				        	if(persona.getCorreoElectronico()!=null && !"".equals(persona.getCorreoElectronico()) && SendMail.isEmail(persona.getCorreoElectronico()))
				        		lstCorreosToMultiEntidad.add(persona.getCorreoElectronico());
				        	if(persona.getCorreoAlternativo()!=null && !"".equals(persona.getCorreoAlternativo()) && SendMail.isEmail(persona.getCorreoAlternativo()))
				        		lstCorreosToMultiEntidad.add(persona.getCorreoAlternativo());
				        						        	
				        }
			        }

					
					/* INICIO VINCULACIONES */			        
					if ( S_VALOR_SI.equals(lsAlertasVinculacionesActivas))
					{						
						try{
							parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_CUERPO_MULTIENTIDAD.getValue()));
							if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
								lsCuerpoMultientidad = parametrica.getValorParametro();
						}catch(Exception z){
							logger.error("Error consultando GI_ALERTAS_CUERPO_MULTIENTIDAD: "+ z.getMessage(), JobGI.class);
							lsCuerpoMultientidad="N";
						}							
			        	lsCuerpoMultientidad = lsCuerpoMultientidad.replace("[VMESES]", llMesesInactividad.toString())
				        		.replace("[VDIAS]", llDiasInactividad.toString())
				        		.replace("[VTIPO_PROCESO]", S_NOTIFICACION_VINCULACIONES)	;						
						fisPlantilla  = new FileInputStream(new File(filePathPlantilla)); //Read the spreadsheet that needs to be updated
						
						hsWbPlantilla = new HSSFWorkbook(fisPlantilla); //Access the workbook
						hssSheetPlantilla= hsWbPlantilla.getSheetAt(0); //Access the worksheet, so that we can update / modify it.
						cell = hssSheetPlantilla.getRow(0).getCell(1);  //Acces cell tipo Notificacion
						cell.setCellValue(S_NOTIFICACION_VINCULACIONES);
						cell = hssSheetPlantilla.getRow(1).getCell(1);   // Access the second cell in second row to update the value
				        cell.setCellValue(dateFormat.format(date));                     //Acces cell date
				        
				        /*RECORREMOS E INSERTAMOS*/
				        vinculacionService = new VinculacionService();
				        buscador = new VinculacionExt();
				        buscador.setDiasNotificacion(liDiasNotificacion.intValue());
				        buscador.setFlgVinculacion((short) 1);
				        lstVinculacionesNotificar = vinculacionService.getVinculacionAlerta(buscador);
				        if(lstVinculacionesNotificar!=null && lstVinculacionesNotificar.size()>0){
				        	 hssSheetPlantilla.shiftRows(3, hssSheetPlantilla.getLastRowNum(), lstVinculacionesNotificar.size());//creamos una fila nueva: 1 es la cantidad
				        	 controws = 3;
				        	 for(VinculacionExt vinculacionNotificar: lstVinculacionesNotificar){
				        		 destRow = hssSheetPlantilla.createRow(controws); 
							     destRow.createCell(0);
							     destRow.createCell(1);
							     destRow.createCell(2);
							     destRow.createCell(3);
							     destRow.createCell(4);
							     destRow.createCell(5);		
							     /*codigo sigep*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(0); 
							     cell.setCellValue(vinculacionNotificar.getCodigoSigep());
							     /*Entidad*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(1); 
							     cell.setCellValue(vinculacionNotificar.getNombreEntidad());
							     /*Fecha ultima actualizacion*/ 
							     cell = hssSheetPlantilla.getRow(controws).getCell(2); 
							     cell.setCellValue(dateFormat.format(vinculacionNotificar.getAudFechaActualizacion()));
							     /*Detalle*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(3); 
							     cell.setCellValue(vinculacionNotificar.getDetalle());							     
							     /*Usuario*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(4); 
							     cell.setCellValue(vinculacionNotificar.getNombreUsuario());
							     /*Rol*/
							     /*Detalle*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(5); 
							     cell.setCellValue(vinculacionNotificar.getNombreRol());							     
							     /*envio a roles monoentidad*/
							       enviaNotificacionMonoEntidadVinculaciones(filePathPlantilla, vinculacionNotificar);
							     /*fin envio roles monoentidad*/
							     controws++;
				        	 }
				        }
				        
				        fisPlantilla.close();//Close the InputStream
					    random = (double) Math.round(random.doubleValue());
					    
					    
					    fileNameAdjunto = String.valueOf(random.intValue())+S_SEPARATOR_NAME_FILE
		    					+S_NOMBRE_PLANTILLA_TMP;
					    
					    
			
					    filePathTmP       = configPath+S_PATH_TMP+fileNameAdjunto;
				        FileOutputStream output_file =new FileOutputStream(new File(filePathTmP));  //Open FileOutputStream to write updates
				        hsWbPlantilla.write(output_file); //write changes
				        output_file.close();  //close the stream  
				        
				        multiPartes = new MimeMultipart();	
			        	messageBodyPart = new MimeBodyPart();
			        	
			        	lsCuerpoMultientidad = getBodyWithHtml(lsCuerpoMultientidad);
			        	messageBodyPart.setContent(lsCuerpoMultientidad,"text/html; charset=" + ConfigurationBundleConstants.charset());
			        	multiPartes.addBodyPart(messageBodyPart);
					    adjunto = new MimeBodyPart();
				        adjunto.setDataHandler(new DataHandler(new FileDataSource(filePathTmP)));
				        adjunto.setFileName(fileNameAdjunto); 
				        multiPartes.addBodyPart(adjunto);
			            /*
			             * Enviamos el correo
			             */
				        if(lstCorreosToMultiEntidad!=null && lstCorreosToMultiEntidad.size()>0){
					        enviarMail(lstCorreosToMultiEntidad,lsAsuntoVinculacion, multiPartes);
				        }
	
				        
					}
					/* FIN VINCULACIONES */
					
					/* INICIO DESVINCULACIONES */
					if ( S_VALOR_SI.equals(lsAlertasDesVinculacionesActivas)){

						try{
							parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_CUERPO_MULTIENTIDAD.getValue()));
							if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
								lsCuerpoMultientidad = parametrica.getValorParametro();
						}catch(Exception z){
							logger.error("Error consultando GI_ALERTAS_CUERPO_MULTIENTIDAD: "+ z.getMessage(), JobGI.class);
							lsCuerpoMultientidad="N";
						}							
			        	lsCuerpoMultientidad = lsCuerpoMultientidad.replace("[VMESES]", llMesesInactividad.toString())
				        		.replace("[VDIAS]", llDiasInactividad.toString())
				        		.replace("[VTIPO_PROCESO]", S_NOTIFICACION_DESVINCULACIONES)	;							
						fisPlantilla  = new FileInputStream(new File(filePathPlantilla)); //Read the spreadsheet that needs to be updated
						hsWbPlantilla = new HSSFWorkbook(fisPlantilla); //Access the workbook
						hssSheetPlantilla= hsWbPlantilla.getSheetAt(0); //Access the worksheet, so that we can update / modify it.
						cell = hssSheetPlantilla.getRow(0).getCell(1);  //Acces cell tipo Notificacion
						cell.setCellValue(S_NOTIFICACION_DESVINCULACIONES);
						cell = hssSheetPlantilla.getRow(1).getCell(1);   // Access the second cell in second row to update the value
				        cell.setCellValue(dateFormat.format(date));                     //Acces cell date
				        
				        /*RECORREMOS E INSERTAMOS*/
				        
				        vinculacionService = new VinculacionService();
				        buscador = new VinculacionExt();
				        buscador.setDiasNotificacion(liDiasNotificacion.intValue());
				        buscador.setFlgVinculacion((short) 0);
				        lstDesVinculacionesNotificar = vinculacionService.getVinculacionAlerta(buscador);
				        if(lstDesVinculacionesNotificar!=null && lstDesVinculacionesNotificar.size()>0){
				        	 hssSheetPlantilla.shiftRows(3, hssSheetPlantilla.getLastRowNum(), lstDesVinculacionesNotificar.size());//creamos una fila nueva: 1 es la cantidad
				        	 controws = 3;
				        	 for(VinculacionExt vinculacionNotificar: lstDesVinculacionesNotificar){
				        		 destRow = hssSheetPlantilla.createRow(controws); 
							     destRow.createCell(0);
							     destRow.createCell(1);
							     destRow.createCell(2);
							     destRow.createCell(3);
							     destRow.createCell(4);
							     destRow.createCell(5);		
							     /*codigo sigep*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(0); 
							     cell.setCellValue(vinculacionNotificar.getCodigoSigep());
							     /*Entidad*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(1); 
							     cell.setCellValue(vinculacionNotificar.getNombreEntidad());
							     /*Fecha ultima actualizacion*/ 
							     cell = hssSheetPlantilla.getRow(controws).getCell(2); 
							     cell.setCellValue(dateFormat.format(vinculacionNotificar.getAudFechaActualizacion()));
							     /*Detalle*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(3); 
							     cell.setCellValue(vinculacionNotificar.getDetalle());							     
							     /*Usuario*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(4); 
							     cell.setCellValue(vinculacionNotificar.getNombreUsuario());
							     /*Rol*/
							     /*Detalle*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(5); 
							     cell.setCellValue(vinculacionNotificar.getNombreRol());							     
							     /*envio a roles monoentidad*/
							       enviaNotificacionMonoEntidadDesVinculaciones(filePathPlantilla, vinculacionNotificar);
							     /*fin envio roles monoentidad*/
							     controws++;
				        	 }
				        }
				        fisPlantilla.close();//Close the InputStream

				        fileNameAdjunto=S_NOTIFICACION_DESVINCULACIONES+S_SEPARATOR_NAME_FILE+String.valueOf(random.intValue())
						  +S_SEPARATOR_NAME_FILE+S_NOMBRE_PLANTILLA_TMP;
				        
				        
				        filePathTmP       = configPath+S_PATH_TMP+fileNameAdjunto;
				        FileOutputStream output_file =new FileOutputStream(new File(filePathTmP));  //Open FileOutputStream to write updates
				        hsWbPlantilla.write(output_file); //write changes
				        output_file.close();  //close the stream  
				        
				        multiPartes = new MimeMultipart();	
			        	messageBodyPart = new MimeBodyPart();
			        	
			        	lsCuerpoMultientidad = getBodyWithHtml(lsCuerpoMultientidad);
			        	messageBodyPart.setContent(lsCuerpoMultientidad,"text/html; charset=" + ConfigurationBundleConstants.charset());
			        	multiPartes.addBodyPart(messageBodyPart);
					    adjunto = new MimeBodyPart();
				        adjunto.setDataHandler(new DataHandler(new FileDataSource(filePathTmP)));
				        adjunto.setFileName(fileNameAdjunto); 
				        multiPartes.addBodyPart(adjunto);
			            /*
			             * Enviamos el correo
			             */
				        if(lstCorreosToMultiEntidad!=null && lstCorreosToMultiEntidad.size()>0){
					        enviarMail(lstCorreosToMultiEntidad,lsAsuntoDesvinculacion, multiPartes);
				        }

			       
					}
					/* FIN DESVINCULACIONES */
					
					/* INICIO SITUACIONES ADMIN */
					if ( S_VALOR_SI.equals(lsAlertasSitAdsActivas)){
						
						try{
							parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_CUERPO_MULTIENTIDAD.getValue()));
							if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
								lsCuerpoMultientidad = parametrica.getValorParametro();
						}catch(Exception z){
							logger.error("Error consultando GI_ALERTAS_CUERPO_MULTIENTIDAD: "+ z.getMessage(), JobGI.class);
							lsCuerpoMultientidad="N";
						}							
						
			        	lsCuerpoMultientidad = lsCuerpoMultientidad.replace("[VMESES]", llMesesInactividad.toString())
				        		.replace("[VDIAS]", llDiasInactividad.toString())
				        		.replace("[VTIPO_PROCESO]", S_NOTIFICACION_SITUACIONES_AD)	;							
						fisPlantilla  = new FileInputStream(new File(filePathPlantilla)); //Read the spreadsheet that needs to be updated
						hsWbPlantilla = new HSSFWorkbook(fisPlantilla); //Access the workbook
						hssSheetPlantilla= hsWbPlantilla.getSheetAt(0); //Access the worksheet, so that we can update / modify it.
						cell = hssSheetPlantilla.getRow(0).getCell(1);  //Acces cell tipo Notificacion
						cell.setCellValue(S_NOTIFICACION_SITUACIONES_AD);
						cell = hssSheetPlantilla.getRow(1).getCell(1);   // Access the second cell in second row to update the value
				        cell.setCellValue(dateFormat.format(date));                     //Acces cell date
				        
				        vinculacionService = new VinculacionService();
				        buscador = new VinculacionExt();
				        buscador.setDiasNotificacion(liDiasNotificacion.intValue());
				        lstSituacionesNotificar = vinculacionService.getSituacionesAdminAlerta(buscador);
				        if(lstSituacionesNotificar!=null && lstSituacionesNotificar.size()>0){
				        	 hssSheetPlantilla.shiftRows(3, hssSheetPlantilla.getLastRowNum(), lstSituacionesNotificar.size());//creamos una fila nueva: 1 es la cantidad
				        	 controws = 3;
				        	 for(VinculacionExt vinculacionNotificar: lstSituacionesNotificar){
				        		 destRow = hssSheetPlantilla.createRow(controws); 
							     destRow.createCell(0);
							     destRow.createCell(1);
							     destRow.createCell(2);
							     destRow.createCell(3);
							     destRow.createCell(4);
							     destRow.createCell(5);		
							     /*codigo sigep*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(0); 
							     cell.setCellValue(vinculacionNotificar.getCodigoSigep());
							     /*Entidad*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(1); 
							     cell.setCellValue(vinculacionNotificar.getNombreEntidad());
							     /*Fecha ultima actualizacion*/ 
							     cell = hssSheetPlantilla.getRow(controws).getCell(2); 
							     cell.setCellValue(dateFormat.format(vinculacionNotificar.getAudFechaActualizacion()));
							     /*Detalle*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(3); 
							     cell.setCellValue(vinculacionNotificar.getDetalle());							     
							     /*Usuario*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(4); 
							     cell.setCellValue(vinculacionNotificar.getNombreUsuario());
							     /*Rol*/
							     /*Detalle*/
							     cell = hssSheetPlantilla.getRow(controws).getCell(5); 
							     cell.setCellValue(vinculacionNotificar.getNombreRol());							     
							     /*envio a roles monoentidad*/
							       enviaNotificacionMonoEntidadSituacionesAdministrativas(filePathPlantilla, vinculacionNotificar);
							     /*fin envio roles monoentidad*/							     
							     controws++;
				        	 }
				        }
				        
				        fisPlantilla.close();//Close the InputStream
				        
						fileNameAdjunto=  S_NOTIFICACION_SITUACIONES_AD+S_SEPARATOR_NAME_FILE+String.valueOf(random)
						  +S_SEPARATOR_NAME_FILE+S_NOMBRE_PLANTILLA_TMP;
						  
						filePathTmP       = configPath+S_PATH_TMP+fileNameAdjunto;

//				        filePathTmP       = configPath+S_PATH_TMP+S_SEPARATOR_NAME_FILE+S_NOTIFICACION_SITUACIONES_AD+S_SEPARATOR_NAME_FILE+String.valueOf(random)
//				        							  +S_SEPARATOR_NAME_FILE+S_NOMBRE_PLANTILLA_TMP;
				        FileOutputStream output_file =new FileOutputStream(new File(filePathTmP));  //Open FileOutputStream to write updates
				        hsWbPlantilla.write(output_file); //write changes
				        output_file.close();  //close the stream  
				        
				        multiPartes = new MimeMultipart();	
			        	messageBodyPart = new MimeBodyPart();
			        	
			        	lsCuerpoMultientidad = getBodyWithHtml(lsCuerpoMultientidad);
			        	messageBodyPart.setContent(lsCuerpoMultientidad,"text/html; charset=" + ConfigurationBundleConstants.charset());
			        	multiPartes.addBodyPart(messageBodyPart);
					    adjunto = new MimeBodyPart();
				        adjunto.setDataHandler(new DataHandler(new FileDataSource(filePathTmP)));
				        adjunto.setFileName(fileNameAdjunto); 
				        multiPartes.addBodyPart(adjunto);
			            /*
			             * Enviamos el correo
			             */
				        if(lstCorreosToMultiEntidad!=null && lstCorreosToMultiEntidad.size()>0){
					        enviarMail(lstCorreosToMultiEntidad,lsAsuntoSitAds, multiPartes);
				        }
			       
					}
					/* FIN SITUACIONES ADMIN */
				}else{
					logger.error("GI: ALERTAS ESPECIFICAS DE GESTION DE LA INFORMACION APAGADAS: "+ new Date(), JobGI.class);
				}
			}else{
				logger.error("GI: ALERTAS DE GESTION DE LA INFORMACION APAGADAS: "+ new Date(), JobGI.class);
			}
		} catch(Exception z){
	    	logger.info("Error General Alertas GI: "+ z.getMessage(), JobGI.class);
	    }
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	private void enviaNotificacionMonoEntidadVinculaciones(String filePathPlantilla, VinculacionExt vinculacionNotificar){
		try{
			String fileName="";
			ParametricaService parametricaService = new ParametricaService();
			Parametrica parametrica;
			try{
				parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_CUERPO_MONOENTIDAD.getValue()));
				if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
					lsCuerpoMonoentidad = parametrica.getValorParametro();
			}catch(Exception z){
				logger.error("Error consultando GI_ALERTAS_CUERPO_MONOENTIDAD SITUACIONES ADS: "+ z.getMessage(), JobGI.class);
				lsCuerpoMonoentidad="N";
			}				
			lsCuerpoMonoentidad = lsCuerpoMonoentidad.replace("[VMESES]", llMesesInactividad.toString())
	        		.replace("[VDIAS]", llDiasInactividad.toString())
	        		.replace("[VTIPO_PROCESO]", S_NOTIFICACION_VINCULACIONES)
	        		.replace("[V_NOMBREENTIDAD]", vinculacionNotificar.getNombreEntidad())
	        		.replace("[V_NOMBREUSUARIO]", vinculacionNotificar.getNombreUsuario());			
			Cell cell;
			Row destRow;
			int controws = 3;
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
			
			Double random = ((Math.random()*1000));
			random = (double) Math.round(random.doubleValue());
			String filePathTmP;
			PersonaExt buscadorRoles = new PersonaExt();
	        buscadorRoles.setCodRol(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_ROLES_MONOENTIDAD.getValue()));
	        buscadorRoles.setCodEntidad(BigDecimal.valueOf( Double.parseDouble(vinculacionNotificar.getCodigoEntidad())));
	        PersonaService personaService = new PersonaService();
	        List<PersonaExt> lstPersonasRolesMonoEntidad = personaService.selectPersonasRolesNotificaGI(buscadorRoles);
	        List<String> lstCorreosToMonoEntidad = null;
	        if(lstPersonasRolesMonoEntidad!=null && lstPersonasRolesMonoEntidad.size()>0){
	        	lstCorreosToMonoEntidad = new ArrayList<String>();
	        	for(Persona persona: lstPersonasRolesMonoEntidad){
		        	if(persona.getCorreoElectronico()!=null && !"".equals(persona.getCorreoElectronico()) && SendMail.isEmail(persona.getCorreoElectronico()))
		        		lstCorreosToMonoEntidad.add(persona.getCorreoElectronico());
		        	if(persona.getCorreoAlternativo()!=null && !"".equals(persona.getCorreoAlternativo()) && SendMail.isEmail(persona.getCorreoAlternativo()))
		        		lstCorreosToMonoEntidad.add(persona.getCorreoAlternativo());
		        						        	
		        }
	        }
	        
	        configPath        = System.getProperty("CONFIG_PATH");
	        FileInputStream fisPlantillaMonoEntidad = new FileInputStream(new File(filePathPlantilla));
	        HSSFWorkbook hsWbPlantillaMonoEntidad = new HSSFWorkbook(fisPlantillaMonoEntidad); //Access the workbook
	        HSSFSheet hssSheetPlantillaMonoEntidad= hsWbPlantillaMonoEntidad.getSheetAt(0); //Access the worksheet, so that we can update / modify it.
			cell = hssSheetPlantillaMonoEntidad.getRow(0).getCell(1);  //Acces cell tipo Notificacion
			cell.setCellValue(S_NOTIFICACION_VINCULACIONES);
			cell = hssSheetPlantillaMonoEntidad.getRow(1).getCell(1);   // Access the second cell in second row to update the value
	        cell.setCellValue(dateFormat.format(date));                     //Acces cell date
	        hssSheetPlantillaMonoEntidad.createRow(3);
	        destRow = hssSheetPlantillaMonoEntidad.createRow(controws); 
   	        destRow.createCell(0);
		    destRow.createCell(1);
		    destRow.createCell(2);
		    destRow.createCell(3);
		    destRow.createCell(4);
		    destRow.createCell(5);		
		    /*codigo sigep*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(0); 
		    cell.setCellValue(vinculacionNotificar.getCodigoSigep());
		    /*Entidad*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(1); 
		    cell.setCellValue(vinculacionNotificar.getNombreEntidad());
		    /*Fecha ultima actualizacion*/ 
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(2); 
		    cell.setCellValue(dateFormat.format(vinculacionNotificar.getAudFechaActualizacion()));
		    /*Detalle*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(3); 
		    cell.setCellValue(vinculacionNotificar.getDetalle());							     
		    /*Usuario*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(4); 
		    cell.setCellValue(vinculacionNotificar.getNombreUsuario());
		    /*Rol*/
		    /*Detalle*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(5); 
		    cell.setCellValue(vinculacionNotificar.getNombreRol());
		    fisPlantillaMonoEntidad.close();
		    fileName = S_NOTIFICACION_VINCULACIONES+S_SEPARATOR_NAME_FILE+String.valueOf(random.intValue()) 
		    			+S_SEPARATOR_NAME_FILE+vinculacionNotificar.getNombreUsuario().trim()+S_SEPARATOR_NAME_FILE+S_NOMBRE_PLANTILLA_TMP;
		    filePathTmP       = configPath+S_PATH_TMP+fileName;
		    FileOutputStream output_fileMonoEntidad =new FileOutputStream(new File(filePathTmP));  //Open FileOutputStream to write updates
	        hsWbPlantillaMonoEntidad.write(output_fileMonoEntidad); //write changes
	        output_fileMonoEntidad.close();  //close the stream 
         
            MimeMultipart multiPartes = new MimeMultipart();	
            MimeBodyPart	messageBodyPart = new MimeBodyPart();
        	
            lsCuerpoMonoentidad = getBodyWithHtml(lsCuerpoMonoentidad);
            messageBodyPart.setContent(lsCuerpoMonoentidad,"text/html; charset=" + ConfigurationBundleConstants.charset());
        	multiPartes.addBodyPart(messageBodyPart);
        	MimeBodyPart adjunto = new MimeBodyPart();
	        adjunto.setDataHandler(new DataHandler(new FileDataSource(filePathTmP)));
	        adjunto.setFileName(fileName); 
	        multiPartes.addBodyPart(adjunto);
	        if(lstCorreosToMonoEntidad!=null && lstCorreosToMonoEntidad.size()>0){
		        enviarMail(lstCorreosToMonoEntidad,lsAsuntoVinculacion, multiPartes);
	        }

		}catch(Exception z){
			logger.info("Error Enviando MonoEntidad Vinculaciones: "+ z.getMessage(), JobGI.class);
	    }
	}
	
	private void enviaNotificacionMonoEntidadDesVinculaciones(String filePathPlantilla, VinculacionExt desvinculacionNotificar){
		try{
			ParametricaService parametricaService = new ParametricaService();
			Parametrica parametrica;
			String fileName="";
			try{
				parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_CUERPO_MONOENTIDAD.getValue()));
				if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
					lsCuerpoMonoentidad = parametrica.getValorParametro();
			}catch(Exception z){
				logger.error("Error consultando GI_ALERTAS_CUERPO_MONOENTIDAD SITUACIONES ADS: "+ z.getMessage(), JobGI.class);
				lsCuerpoMonoentidad="N";
			}				
			lsCuerpoMonoentidad = lsCuerpoMonoentidad.replace("[VMESES]", llMesesInactividad.toString())
	        		.replace("[VDIAS]", llDiasInactividad.toString())
	        		.replace("[VTIPO_PROCESO]", S_NOTIFICACION_DESVINCULACIONES)
	        		.replace("[V_NOMBREENTIDAD]", desvinculacionNotificar.getNombreEntidad())
	        		.replace("[V_NOMBREUSUARIO]", desvinculacionNotificar.getNombreUsuario());			
			Cell cell;
			Row destRow;
			int controws = 3;
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
			
			Double random = ((Math.random()*1000));
			random = (double) Math.round(random.doubleValue());
			String filePathTmP;
			PersonaExt buscadorRoles = new PersonaExt();
	        buscadorRoles.setCodRol(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_ROLES_MONOENTIDAD.getValue()));
	        buscadorRoles.setCodEntidad(BigDecimal.valueOf( Double.parseDouble(desvinculacionNotificar.getCodigoEntidad())));
	        PersonaService personaService = new PersonaService();
	        List<PersonaExt> lstPersonasRolesMonoEntidad = personaService.selectPersonasRolesNotificaGI(buscadorRoles);
	        List<String> lstCorreosToMonoEntidad = null;
	        if(lstPersonasRolesMonoEntidad!=null && lstPersonasRolesMonoEntidad.size()>0){
	        	lstCorreosToMonoEntidad = new ArrayList<String>();
	        	for(Persona persona: lstPersonasRolesMonoEntidad){
		        	if(persona.getCorreoElectronico()!=null && !"".equals(persona.getCorreoElectronico()) && SendMail.isEmail(persona.getCorreoElectronico()))
		        		lstCorreosToMonoEntidad.add(persona.getCorreoElectronico());
		        	if(persona.getCorreoAlternativo()!=null && !"".equals(persona.getCorreoAlternativo()) && SendMail.isEmail(persona.getCorreoAlternativo()))
		        		lstCorreosToMonoEntidad.add(persona.getCorreoAlternativo());
		        						        	
		        }
	        }	        
	        configPath        = System.getProperty("CONFIG_PATH");
	        FileInputStream fisPlantillaMonoEntidad = new FileInputStream(new File(filePathPlantilla));
	        HSSFWorkbook hsWbPlantillaMonoEntidad = new HSSFWorkbook(fisPlantillaMonoEntidad); //Access the workbook
	        HSSFSheet hssSheetPlantillaMonoEntidad= hsWbPlantillaMonoEntidad.getSheetAt(0); //Access the worksheet, so that we can update / modify it.
			cell = hssSheetPlantillaMonoEntidad.getRow(0).getCell(1);  //Acces cell tipo Notificacion
			cell.setCellValue(S_NOTIFICACION_DESVINCULACIONES);
			cell = hssSheetPlantillaMonoEntidad.getRow(1).getCell(1);   // Access the second cell in second row to update the value
	        cell.setCellValue(dateFormat.format(date));                     //Acces cell date
	        hssSheetPlantillaMonoEntidad.createRow(3);
	        destRow = hssSheetPlantillaMonoEntidad.createRow(controws); 
   	        destRow.createCell(0);
		    destRow.createCell(1);
		    destRow.createCell(2);
		    destRow.createCell(3);
		    destRow.createCell(4);
		    destRow.createCell(5);		
		    /*codigo sigep*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(0); 
		    cell.setCellValue(desvinculacionNotificar.getCodigoSigep());
		    /*Entidad*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(1); 
		    cell.setCellValue(desvinculacionNotificar.getNombreEntidad());
		    /*Fecha ultima actualizacion*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(2); 
		    cell.setCellValue(dateFormat.format(desvinculacionNotificar.getAudFechaActualizacion()));
		    /*Detalle*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(3); 
		    cell.setCellValue(desvinculacionNotificar.getDetalle());							     
		    /*Usuario*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(4); 
		    cell.setCellValue(desvinculacionNotificar.getNombreUsuario());
		    /*Rol*/
		    /*Detalle*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(5); 
		    cell.setCellValue(desvinculacionNotificar.getNombreRol());
		    fisPlantillaMonoEntidad.close();
		    fileName= S_NOTIFICACION_DESVINCULACIONES+S_SEPARATOR_NAME_FILE+String.valueOf(random.intValue())
		             +S_SEPARATOR_NAME_FILE+desvinculacionNotificar.getNombreUsuario().trim()+S_NOMBRE_PLANTILLA_TMP;
		    
		    filePathTmP       = configPath+S_PATH_TMP+fileName;
		    FileOutputStream output_fileMonoEntidad =new FileOutputStream(new File(filePathTmP));  //Open FileOutputStream to write updates
	        hsWbPlantillaMonoEntidad.write(output_fileMonoEntidad); //write changes
	        output_fileMonoEntidad.close();  //close the stream 
         
            MimeMultipart multiPartes = new MimeMultipart();	
            MimeBodyPart	messageBodyPart = new MimeBodyPart();
            lsCuerpoMonoentidad = getBodyWithHtml(lsCuerpoMonoentidad);
            messageBodyPart.setContent(lsCuerpoMonoentidad,"text/html; charset=" + ConfigurationBundleConstants.charset());
        	multiPartes.addBodyPart(messageBodyPart);
        	MimeBodyPart adjunto = new MimeBodyPart();
	        adjunto.setDataHandler(new DataHandler(new FileDataSource(filePathTmP)));
	        adjunto.setFileName(fileName); 
	        multiPartes.addBodyPart(adjunto);
	        if(lstCorreosToMonoEntidad!=null && lstCorreosToMonoEntidad.size()>0){
		        enviarMail(lstCorreosToMonoEntidad,lsAsuntoDesvinculacion, multiPartes);
	        }
		}catch(Exception z){
			logger.info("Error Enviando MonoEntidad DesVinculaciones: "+ z.getMessage(), JobGI.class);
	    }
	}	
	
	private void enviaNotificacionMonoEntidadSituacionesAdministrativas(String filePathPlantilla, VinculacionExt situacionAd){
		try{
			ParametricaService parametricaService = new ParametricaService();
			Parametrica parametrica;
			String fileName="";
			try{
				parametrica = parametricaService.getPrametricaById(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_CUERPO_MONOENTIDAD.getValue()));
				if(parametrica!=null && parametrica.getValorParametro()!=null && !"".equals(parametrica.getValorParametro()))
					lsCuerpoMonoentidad = parametrica.getValorParametro();
			}catch(Exception z){
				logger.error("Error consultando GI_ALERTAS_CUERPO_MONOENTIDAD SITUACIONES ADS: "+ z.getMessage(), JobGI.class);
				lsCuerpoMonoentidad="N";
			}				
			lsCuerpoMonoentidad = lsCuerpoMonoentidad.replace("[VMESES]", llMesesInactividad.toString())
	        		.replace("[VDIAS]", llDiasInactividad.toString())
	        		.replace("[VTIPO_PROCESO]", S_NOTIFICACION_SITUACIONES_AD)
	        		.replace("[V_NOMBREENTIDAD]", situacionAd.getNombreEntidad())
	        		.replace("[V_NOMBREUSUARIO]", situacionAd.getNombreUsuario());			
			Cell cell;
			Row destRow;
			int controws = 3;

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
			
			Double random = ((Math.random()*1000));
			random = (double) Math.round(random.doubleValue());
			String filePathTmP;
			PersonaExt buscadorRoles = new PersonaExt();
	        buscadorRoles.setCodRol(BigDecimal.valueOf(TipoParametro.GI_ALERTAS_ROLES_MONOENTIDAD.getValue()));
	        buscadorRoles.setCodEntidad(BigDecimal.valueOf( Double.parseDouble(situacionAd.getCodigoEntidad())));
	        PersonaService personaService = new PersonaService();
	        List<PersonaExt> lstPersonasRolesMonoEntidad = personaService.selectPersonasRolesNotificaGI(buscadorRoles);
	        List<String> lstCorreosToMonoEntidad = null;
	        if(lstPersonasRolesMonoEntidad!=null && lstPersonasRolesMonoEntidad.size()>0){
	        	lstCorreosToMonoEntidad = new ArrayList<String>();
	        	for(Persona persona: lstPersonasRolesMonoEntidad){
		        	if(persona.getCorreoElectronico()!=null && !"".equals(persona.getCorreoElectronico()) && SendMail.isEmail(persona.getCorreoElectronico()))
		        		lstCorreosToMonoEntidad.add(persona.getCorreoElectronico());
		        	if(persona.getCorreoAlternativo()!=null && !"".equals(persona.getCorreoAlternativo()) && SendMail.isEmail(persona.getCorreoAlternativo()))
		        		lstCorreosToMonoEntidad.add(persona.getCorreoAlternativo());
		        						        	
		        }
	        }	        
	        configPath        = System.getProperty("CONFIG_PATH");
	        FileInputStream fisPlantillaMonoEntidad = new FileInputStream(new File(filePathPlantilla));
	        HSSFWorkbook hsWbPlantillaMonoEntidad = new HSSFWorkbook(fisPlantillaMonoEntidad); //Access the workbook
	        HSSFSheet hssSheetPlantillaMonoEntidad= hsWbPlantillaMonoEntidad.getSheetAt(0); //Access the worksheet, so that we can update / modify it.
			cell = hssSheetPlantillaMonoEntidad.getRow(0).getCell(1);  //Acces cell tipo Notificacion
			cell.setCellValue(S_NOTIFICACION_SITUACIONES_AD);
			cell = hssSheetPlantillaMonoEntidad.getRow(1).getCell(1);   // Access the second cell in second row to update the value
	        cell.setCellValue(dateFormat.format(date));                     //Acces cell date
	        hssSheetPlantillaMonoEntidad.createRow(3);
	        destRow = hssSheetPlantillaMonoEntidad.createRow(controws); 
   	        destRow.createCell(0);
		    destRow.createCell(1);
		    destRow.createCell(2);
		    destRow.createCell(3);
		    destRow.createCell(4);
		    destRow.createCell(5);		
		    /*codigo sigep*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(0); 
		    cell.setCellValue(situacionAd.getCodigoSigep());
		    /*Entidad*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(1); 
		    cell.setCellValue(situacionAd.getNombreEntidad());
		    /*Fecha ultima actualizacion*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(2); 
		    cell.setCellValue(dateFormat.format(situacionAd.getAudFechaActualizacion()));
		    /*Detalle*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(3); 
		    cell.setCellValue(situacionAd.getDetalle());							     
		    /*Usuario*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(4); 
		    cell.setCellValue(situacionAd.getNombreUsuario());
		    /*Rol*/
		    /*Detalle*/
		    cell = hssSheetPlantillaMonoEntidad.getRow(3).getCell(5); 
		    cell.setCellValue(situacionAd.getNombreRol());
		    fisPlantillaMonoEntidad.close();
	        
		    fileName= String.valueOf(random.intValue())+S_SEPARATOR_NAME_FILE+situacionAd.getNombreUsuario().trim()+S_SEPARATOR_NAME_FILE+S_NOMBRE_PLANTILLA_TMP;
		    
		    fileName=S_NOTIFICACION_SITUACIONES_AD+fileName;
		    
		    filePathTmP       = configPath+S_PATH_TMP+fileName;
		    FileOutputStream output_fileMonoEntidad =new FileOutputStream(new File(filePathTmP));  //Open FileOutputStream to write updates
	        hsWbPlantillaMonoEntidad.write(output_fileMonoEntidad); //write changes
	        output_fileMonoEntidad.close();  //close the stream 
         
            MimeMultipart multiPartes = new MimeMultipart();	
            MimeBodyPart	messageBodyPart = new MimeBodyPart();
            lsCuerpoMonoentidad = getBodyWithHtml(lsCuerpoMonoentidad);
            messageBodyPart.setContent(lsCuerpoMonoentidad,"text/html; charset=" + ConfigurationBundleConstants.charset());
        	multiPartes.addBodyPart(messageBodyPart);
        	MimeBodyPart adjunto = new MimeBodyPart();
	        adjunto.setDataHandler(new DataHandler(new FileDataSource(filePathTmP)));
	        adjunto.setFileName(fileName); 
	        multiPartes.addBodyPart(adjunto);
	        if(lstCorreosToMonoEntidad!=null && lstCorreosToMonoEntidad.size()>0){
		        enviarMail(lstCorreosToMonoEntidad,lsAsuntoSitAds, multiPartes);
	        }
		}catch(Exception z){
			logger.info("Error Enviando MonoEntidad DesVinculaciones: "+ z.getMessage(), JobGI.class);
	    }
	}
	
	private String getBodyWithHtml (String p_body){
		String strCuerpoConHTML;
		strCuerpoConHTML = p_body;
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
		
		return strCuerpoConHTML;
		
	}	
	
	private void enviarMail(List<String> plstCorreosTo, String plstrAsunto, MimeMultipart pmultiPartes){
		SendMail send = new SendMail();
        send.sendmailHtmlWithAttachment(plstCorreosTo,plstrAsunto, pmultiPartes);
	}
	
	 public static void main(String[] args) throws Exception{
	 }

}
