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
import co.gov.dafp.sigep2.scheduler.job.JobCargasMasivas;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.QuartzJobs;
import co.gov.dafp.sigep2.utils.TipoParametro;

public class FactoriaJobCargasMasivas {
	
	static Properties 			defaultQuartzProperties;
	static LoggerSigep 			logger;
	static BigDecimal 			bdCodParametricaCalendario;
	static Parametrica 			parametroCalendario;
	static String 				strValorParametricaCalendario;
	static ParametricaService	parametricaService;
	
	private static String strNombreGrupo	= "groupoCMNotificacionesCluster";
	private static String strNombreJob		= "jobCMNotificacionesCluster";
	private static String strNombreTrigger	= "cronTriggerCMNotificacionesCluster";
	
	public static void executeJobCargasMasivas(){
		Scheduler scheduler = null;
		parametricaService = new ParametricaService();
		bdCodParametricaCalendario = BigDecimal.valueOf(TipoParametro.CARGA_MASIVA_CALENDARIO_NOTIFICACION.getValue());
		parametroCalendario = parametricaService.getPrametricaById(bdCodParametricaCalendario);
		if(parametroCalendario!= null && parametroCalendario.getValorParametro() != null && !"".equals(parametroCalendario.getValorParametro())) {
			strValorParametricaCalendario = parametroCalendario.getValorParametro();
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
				JobKey jobKey = new JobKey(strNombreJob, strNombreGrupo);
		        TriggerKey triggerKey = new TriggerKey(strNombreTrigger, strNombreGrupo);
		        JobDetail jobCMCluster = scheduler.getJobDetail(jobKey);
		        if(jobCMCluster == null) {
		        	jobCMCluster = JobBuilder.newJob(JobCargasMasivas.class)
		                  .withIdentity(jobKey)
		                  .requestRecovery(true)
		                  .storeDurably(true)
		                  .build();
		            scheduler.addJob(jobCMCluster, true);
		        }
		        Trigger trigger = scheduler.getTrigger(triggerKey);
		        if(trigger == null) {
		            trigger = newTrigger()
		                    .withIdentity(triggerKey)
		                    .withSchedule(CronScheduleBuilder.cronSchedule(strValorParametricaCalendario))
		                    .forJob(jobCMCluster)
		                    .build();
		            scheduler.scheduleJob(trigger);
		        }
		        if((jobCMCluster != null) && (trigger != null)) {
		            scheduler.start();
		        } else {
		            scheduler.scheduleJob(jobCMCluster, trigger);
		            scheduler.start();
		        }
				Thread.sleep(10000);	
			}catch (InterruptedException e) {
				logger.error(e.getMessage(), FactoriaJobCargasMasivas.class);
			} catch (SchedulerException e) {
				logger.error(e.getMessage(), FactoriaJobCargasMasivas.class);
			}
		}
	}
}
