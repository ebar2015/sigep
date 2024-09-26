package co.gov.dafp.sigep2.scheduler.factoria;

import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.scheduler.job.JobContratos;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.utils.QuartzJobs;

public class FactoriaJobContratos {
	
	private static String strNombreGrupo="groupoContratosCluster";
	private static String strNombreJob="jobContratosCluster";
	private static String strNombreTrigger="cronTriggerContratosCluster";
	private static String strRepeticionTarea;
	private static String strTipoTareaCluster="N";/*N = NORMAL  = Ejecuto el hilo normal -
	                                                S =  CLUSTER = Ejecuto quartz con l�gica de cluster  (es necesario cambios en bd modelo de quartz)*/
	static Properties defaultQuartzProperties;

	public static void executeJobContratos(){
		try{
			ParametricaService servicioparametrica = new ParametricaService();
			List<Parametrica> lstparametrica = servicioparametrica.getParametricaIntentos("P_CT_NOT_TIEMPO_NOTIFCACIONES");
			if(lstparametrica!=null && lstparametrica.size()>0 && lstparametrica.get(0).getValorParametro()!=null
					&& !"".equals(lstparametrica.get(0).getValorParametro() ))
				strRepeticionTarea = lstparametrica.get(0).getValorParametro();
			if(strRepeticionTarea==null||"".equals(strRepeticionTarea))
				strRepeticionTarea="";
		}catch(Exception z){
			strRepeticionTarea="";
		}
		/*REPETICI�N TAREA
		 0 0 15 * * ? Todos los d�as a las 15
		 0 0 20 * * ? Todos los d�as a las 8pm 
		 0 0/60 * * * ? cadah hora
		 0/40 * * * * ? al segundo 0 y al segundo 40
		 40 * * * * ?   al segundo 40
		 0 0 14-16 * * ? todos los dias a las 14,15,16
		 0 0 14-16 * * ?
		 * */
		
		
		try{
			ParametricaService servicioparametrica = new ParametricaService();
			List<Parametrica> lstparametrica = servicioparametrica.getParametricaIntentos("P_CT_NOT_TIPO_TAREA_CLUSTER");
			if(lstparametrica!=null && lstparametrica.size()>0 && lstparametrica.get(0).getValorParametro()!=null
					&& !"".equals(lstparametrica.get(0).getValorParametro() ))
				strTipoTareaCluster = lstparametrica.get(0).getValorParametro();
			if(strTipoTareaCluster==null ||"".equals(strTipoTareaCluster))
				strTipoTareaCluster="N";
		}catch(Exception z){
			strTipoTareaCluster="N";
		}
		if(strRepeticionTarea!=null && !"".equals(strRepeticionTarea)){	
			cleanJobs();
			if("N".equals(strTipoTareaCluster)){
				strNombreGrupo="groupoContratosRAM";
				strNombreJob="jobContratosRAM";
				strNombreTrigger="cronTriggerContratosRAM";
				JobDetail job1 = JobBuilder.newJob(JobContratos.class).withIdentity(strNombreJob, strNombreGrupo).build();
				Trigger   trigger1 = TriggerBuilder.newTrigger().withIdentity(strNombreTrigger, strNombreGrupo)
		        		.withSchedule(CronScheduleBuilder.cronSchedule(strRepeticionTarea)).build();
		        try {
		        	defaultQuartzProperties = QuartzJobs.getPropertiesQuartz(QuartzJobs.typeQuartzRAM);
		        	/*getPropertiesQuartzRam();*/
		        	Scheduler scheduler = new StdSchedulerFactory(/*defaultQuartzProperties*/).getScheduler();
			        TriggerKey trgKeydel = new TriggerKey(strNombreJob, strNombreGrupo);
			        if(trgKeydel!=null)
			        	scheduler.unscheduleJob(trgKeydel);
					JobKey jobKeydel = new JobKey(strNombreTrigger, strNombreGrupo);
					if(jobKeydel!=null)
						scheduler.deleteJob(jobKeydel);
					scheduler.start();
					scheduler.scheduleJob(job1, trigger1);
					Thread.sleep(10000);
					//scheduler.shutdown();
					//cleanJobs();
				} catch (SchedulerException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}else if("S".equals(strTipoTareaCluster)){
				try{
					/*getPropertiesQuartzCluster();*/
					defaultQuartzProperties = QuartzJobs.getPropertiesQuartz(QuartzJobs.typeQuartzTX);
					Thread.sleep(10000);
					Scheduler scheduler = new StdSchedulerFactory(defaultQuartzProperties).getScheduler();
			        TriggerKey trgKeydel = new TriggerKey(strNombreTrigger, strNombreGrupo);
			        if(trgKeydel!=null)
			        	scheduler.unscheduleJob(trgKeydel);
					JobKey jobKeydel = new JobKey(strNombreJob, strNombreGrupo);
					if(jobKeydel!=null)
						scheduler.deleteJob(jobKeydel);
					/*scheduler.shutdown();*/
			        JobKey jobKey = new JobKey(strNombreJob, strNombreGrupo);
			        TriggerKey triggerKey = new TriggerKey(strNombreTrigger, strNombreGrupo);
			        JobDetail jobContratosCluster = scheduler.getJobDetail(jobKey);
			        if(jobContratosCluster == null) {
			        	jobContratosCluster = JobBuilder.newJob(JobContratos.class)
			                  .withIdentity(jobKey)
			                  .requestRecovery(true)
			                  .storeDurably(true)
			                  .build();
			            scheduler.addJob(jobContratosCluster, true);
			        } else {
			        }
			        Trigger trigger = scheduler.getTrigger(triggerKey);
			        if(trigger == null) {
			            trigger = newTrigger()
			                    .withIdentity(triggerKey)
			                    .withSchedule(
			                    		CronScheduleBuilder.cronSchedule(strRepeticionTarea)
			                    		)
			                    .forJob(jobContratosCluster)
			                    .build();
			            scheduler.scheduleJob(trigger);
			        }else {
			        }
			        if((jobContratosCluster != null) && (trigger != null)) {
			            scheduler.start();
			        } else {
			            scheduler.scheduleJob(jobContratosCluster, trigger);
			            scheduler.start();
			        }
					Thread.sleep(10000);
					//scheduler.shutdown();
				}catch(Exception z){
		        	z.printStackTrace();
		        }
			}
		}
	}		
	
	/*
	 private static void getPropertiesQuartzCluster(){		   
		   try {
			InputStream is = JobContratos.class.getResource("/co/gov/dafp/sigep2/utils/quartz/quartzTX.properties").openStream();
			defaultQuartzProperties = new Properties();
    		defaultQuartzProperties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	private static void getPropertiesQuartzRam(){		   
		   try {
			InputStream is = JobContratos.class.getResource("/co/gov/dafp/sigep2/utils/quartz/quartzRAM.properties").openStream();
			defaultQuartzProperties = new Properties();
			defaultQuartzProperties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }*/
	
	private static void cleanJobs(){
		Scheduler scheduler;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			for (String groupName : scheduler.getJobGroupNames()) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				  String jobName = jobKey.getName();
				  String jobGroup = jobKey.getGroup();
				  List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
				  Date nextFireTime = triggers.get(0).getNextFireTime(); 
					scheduler.deleteJob(jobKey);
				  }
			    }	
			scheduler.shutdown(false);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	

	

}
