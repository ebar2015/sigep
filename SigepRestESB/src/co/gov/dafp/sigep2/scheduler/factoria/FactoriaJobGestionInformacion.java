package co.gov.dafp.sigep2.scheduler.factoria;

import static org.quartz.TriggerBuilder.newTrigger;

import java.math.BigDecimal;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.scheduler.job.JobGI;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.QuartzJobs;
import co.gov.dafp.sigep2.utils.TipoParametro;

public class FactoriaJobGestionInformacion {
	
	static LoggerSigep logger = new LoggerSigep();
	static String strCalendarioNotificacion;
	static BigDecimal bdcodCalendarioNotificacion;
	static ParametricaService parametricaService;
	static Parametrica parametroGi;
	static Properties defaultQuartzProperties;
	private static String strNombreGrupo="groupoGINotificacionesCluster";
	private static String strNombreJob="jobGINotificacionesCluster";
	private static String strNombreTrigger="cronTriggerGINotificacionesCluster";	
	
	public static void executeFactoriaJobGestionInformacion(){
		parametricaService = new ParametricaService();
		bdcodCalendarioNotificacion = BigDecimal.valueOf(TipoParametro.GI_ALERTAS_CALENDARIONOTIFICACION.getValue());
		parametroGi= parametricaService.getPrametricaById(bdcodCalendarioNotificacion);
		Scheduler scheduler = null;
		if(parametroGi!=null && parametroGi.getValorParametro()!=null && !"".equals(parametroGi.getValorParametro())){
			strCalendarioNotificacion = parametroGi.getValorParametro();
			//strCalendarioNotificacion = "40 * * * * ?";
			//Tarea ejecutada en cl�ster (base de datos)
			try {
				defaultQuartzProperties = QuartzJobs.getPropertiesQuartz(QuartzJobs.typeQuartzTX);
				Thread.sleep(10000);
				scheduler = new StdSchedulerFactory(defaultQuartzProperties).getScheduler();
		        TriggerKey trgKeydel = new TriggerKey(strNombreTrigger, strNombreGrupo);
		        if(trgKeydel!=null)
		        	scheduler.unscheduleJob(trgKeydel);
				JobKey jobKeydel = new JobKey(strNombreJob, strNombreGrupo);
				if(jobKeydel!=null)
					scheduler.deleteJob(jobKeydel);
				/*scheduler.shutdown();*/
		        JobKey jobKey = new JobKey(strNombreJob, strNombreGrupo);
		        TriggerKey triggerKey = new TriggerKey(strNombreTrigger, strNombreGrupo);
		        JobDetail jobGICluster = scheduler.getJobDetail(jobKey);	
		        if(jobGICluster == null) {
		        	jobGICluster = JobBuilder.newJob(JobGI.class)
		                  .withIdentity(jobKey)
		                  .requestRecovery(true)
		                  .storeDurably(true)
		                  .build();
		            scheduler.addJob(jobGICluster, true);
		        }	
		        Trigger trigger = scheduler.getTrigger(triggerKey);
		        if(trigger == null) {
		            trigger = newTrigger()
		                    .withIdentity(triggerKey)
		                    .withSchedule(
		                    		CronScheduleBuilder.cronSchedule(strCalendarioNotificacion)
		                    		)
		                    .forJob(jobGICluster)
		                    .build();
		            scheduler.scheduleJob(trigger);
		        }	
		        if((jobGICluster != null) && (trigger != null)) {
		            scheduler.start();
		        } else {
		            scheduler.scheduleJob(jobGICluster, trigger);
		            scheduler.start();
		        }
				Thread.sleep(10000);		        
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), FactoriaJobGestionInformacion.class);
			} catch (SchedulerException e) {
				logger.error(e.getMessage(), FactoriaJobGestionInformacion.class);
			}
		}
	}
	
}
