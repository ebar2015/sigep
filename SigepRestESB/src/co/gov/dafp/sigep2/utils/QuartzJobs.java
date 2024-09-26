package co.gov.dafp.sigep2.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class QuartzJobs {
	
	static LoggerSigep logger = new LoggerSigep();	
	static String configPath;
	public static final String typeQuartzCMP ="CMP";
	public static final String typeQuartzRAM ="RAM";
	public static final String typeQuartzTX ="TX";
	
	public  static Properties getPropertiesQuartz(String strTypeQuartz){		
		Properties quartzTx = new Properties();
		if (strTypeQuartz.equals(typeQuartzTX)){
			configPath = System.getProperty("CONFIG_PATH") + "co/gov/dafp/sigep2/quartzjobs/quartzJobsTX.properties";
		}else if (strTypeQuartz.equals(typeQuartzCMP)){
			configPath = System.getProperty("CONFIG_PATH") + "co/gov/dafp/sigep2/quartzjobs/quartzJobsCMP.properties";
		}else{
			configPath = System.getProperty("CONFIG_PATH") + "co/gov/dafp/sigep2/quartzjobs/quartzJobsRAM.properties";
		}
		
		InputStreamReader in;
		try {
			in = new InputStreamReader(new FileInputStream(configPath), "UTF-8");
			quartzTx.load(in);
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			logger.error("Error Consultando Propiedades Quartz Jobs: "+ e.getMessage(), QuartzJobs.class);
		} catch (IOException e) {
			logger.error("Error Consultando Propiedades Quartz Jobs: "+ e.getMessage(), QuartzJobs.class);
		}
		return quartzTx;
	 }
}
