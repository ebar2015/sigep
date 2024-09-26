/**
 * 
 */
package co.gov.dafp.sigep2.sistema;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.deledago.GestionInformacionDelegate;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.CatalogoBean;
import co.gov.dafp.sigep2.mbean.ReporteBean;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.Parametro;
import co.gov.dafp.sigep2.util.Registro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2NegocioException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.reporte.config.Columna;

/**
 * @author joseviscaya
 *
 */
public class AsyncReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4708086243625849251L;
	
	public static void starTask(co.gov.dafp.sigep2.util.xml.reporte.config.Registro tipoRegistroDetalle,List<Columna> columnas, String nombre, Persona persona, 
			CatalogoBean catalogoBean, int totalReg, UsuarioDTO usuarioSession){
		TaskReport taskReport = new TaskReport(tipoRegistroDetalle, columnas, nombre, persona, catalogoBean,totalReg,usuarioSession);
		taskReport.start();
	}

	public static void starTask(co.gov.dafp.sigep2.util.xml.reporte.config.Registro tipoRegistroDetalle,List<Columna> columnas, String nombre, Persona persona, 
			ReporteBean reporteBean, int totalReg, UsuarioDTO usuarioSession ) {
		TaskReport taskReport = new TaskReport(tipoRegistroDetalle, columnas, nombre, persona, reporteBean,totalReg,usuarioSession);
		taskReport.start();
		
	}

}

class TaskReport extends Thread{
	
	private static String CONFIG_PATH 				= "CONFIG_PATH";
	private static String  FORMATO_FECHA_HORA 		= "ddMMyyyyHHmmssSSS";
	private static String  FORMATO_FECHA_HORA_1 	= "dd/MM/yyyy HH:mm:ss";
	private static String  PATH_PLANTILLA 			= "co/gov/dafp/sigep2/reportes/";
	public  static String  FILEURL 					="/getReporte/";
	
	final static int totals = 40000;
	
	private static String HADOOP_OPCION_CARGA;
	private static String HADOOP_USER_REPO;
	private static String HADOOP_URL_BY_SO;
		
	private List<Registro> data;
	private List<Columna> columnas;
	
	private SimpleDateFormat dateformat = new SimpleDateFormat(FORMATO_FECHA_HORA);
	private SimpleDateFormat dateformat_1 = new SimpleDateFormat(FORMATO_FECHA_HORA_1);
	
	private String nombre;
	private String configPath = System.getProperty(CONFIG_PATH);
	String reporte = "";
	
	private Persona persona;
	private UsuarioDTO usuarioSession;
	

	private CatalogoBean catalogoBean 	= null;
	private ReporteBean reporteBean 	= null;
	String CSV_SEPARATOR = ";";
	private int totalReg;
	private co.gov.dafp.sigep2.util.xml.reporte.config.Registro tipoRegistroDetalle;
	private static final Logger logger = Logger.getInstance(TaskReport.class);
	

	public TaskReport(co.gov.dafp.sigep2.util.xml.reporte.config.Registro tipoRegistroDetalle,List<Columna> columnas, String nombre, Persona persona, CatalogoBean catalogoBean, int totalReg, UsuarioDTO usuarioSession) {
		this.catalogoBean 			= catalogoBean;
		this.tipoRegistroDetalle 	= tipoRegistroDetalle;
		this.totalReg 				= totalReg;
		this.usuarioSession 		= usuarioSession;
			this.columnas 			= columnas;
			this.nombre 			= nombre;
			this.persona 			= persona;
			HADOOP_OPCION_CARGA 	= ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.HADOOP_BY_SO.getValue())).getValorParametro();
			HADOOP_USER_REPO		= ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.HADOOP_FOLDER_RAIZ.getValue())).getValorParametro();
			HADOOP_URL_BY_SO 		= ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.HADOOP_URL_BY_SO.getValue())).getValorParametro();
		
	}
	public TaskReport(co.gov.dafp.sigep2.util.xml.reporte.config.Registro tipoRegistroDetalle,List<Columna> columnas, String nombre, Persona persona, ReporteBean reporteBean, int totalReg, UsuarioDTO usuarioSession) {
		this.reporteBean 			= reporteBean;
		this.tipoRegistroDetalle 	= tipoRegistroDetalle;
		this.totalReg 				= totalReg;
		this.usuarioSession 		= usuarioSession;
			this.columnas 			= columnas;
			this.nombre 			= nombre;
			this.persona 			= persona;
			HADOOP_OPCION_CARGA 	= ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.HADOOP_BY_SO.getValue())).getValorParametro();
			HADOOP_USER_REPO		= ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.HADOOP_FOLDER_RAIZ.getValue())).getValorParametro();
			HADOOP_URL_BY_SO 		= ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.HADOOP_URL_BY_SO.getValue())).getValorParametro();
		
	}

	
	public void run() {
		String asunto 			= "Reporte Asyncrono [Dafp]";
	    int totalPro 			= 0;
	    int existeEncabezado 	= 0;
	    int subProceso 			= totalReg / totals;
	    int residuo 			= totalReg % totals;
	    subProceso = (subProceso == 0 && totalReg > 0) ? 1 : subProceso;

	    nombre = WebUtils.stripAccents(nombre);
	    
	    try {
	        reporte = new String(nombre);
	    } catch (NullPointerException e) {
	        return;
	    }

	    nombre += dateformat.format(new Date()) + ".csv";

	    String MSG_EMAIL_REPORTE 	= ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.MSG_EMAIL_REPORTE.getValue())).getValorParametro();
	    String DIAS_REPORTE 		= ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.DIAS_REPORTE.getValue())).getValorParametro();
	    MSG_EMAIL_REPORTE 			= MSG_EMAIL_REPORTE.replace("#dias", DIAS_REPORTE)
	                                         .replace("#DIAS", DIAS_REPORTE)
	                                         .replace("#report", reporte)
	                                         .replace("#REPORT", reporte);

	    nombre = nombre.replace(" ", "_");
	    String pathFile = configPath + PATH_PLANTILLA + nombre;
	    
	    File file = new File(pathFile);

	    try {
	        if (!file.exists()) {
	            file.createNewFile();
	        } else {
	            file.delete();
	        }

	        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

	            int valorIni = 0;
	            int valorFin = 0;

	            for (int i = 0; i < subProceso; i++) {
	                if (subProceso != 1) {
	                    valorIni = valorFin;
	                    valorFin += totals + residuo;
	                }

	                totalPro = (totals > totalReg) ? residuo : totals + residuo;
	                residuo = 0;

	                try {
	                    if (catalogoBean != null) {
	                        this.data = GestionInformacionDelegate.exec(tipoRegistroDetalle, catalogoBean.getXml(),
	                                usuarioSession, catalogoBean.getParametros(), DateUtils.getFechaSistema(),
	                                ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_ARCHIVO_REPORTE),
	                                valorIni, totalPro, false);
	                    } else {
	                        this.data = GestionInformacionDelegate.exec(tipoRegistroDetalle, reporteBean.getXml(),
	                                usuarioSession, reporteBean.getParametros(), DateUtils.getFechaSistema(),
	                                ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_ARCHIVO_REPORTE),
	                                valorIni, totalPro, false);
	                    }

	                } catch (SIGEP2NegocioException e) {
	                    return;
	                }

	                if (existeEncabezado == 0) {
	                    writeHeader(bw);
	                    existeEncabezado = 1;
	                }

	                for (Registro registro : data) {
	                    writeRecord(bw, registro);
	                }
	            }
	        }

	        MSG_EMAIL_REPORTE = MSG_EMAIL_REPORTE.replace("#link", WebUtils.WS_MULTIMEDIA_DOWNLOAD_R + FILEURL + nombre)
	                                             .replace("#LINK", WebUtils.WS_MULTIMEDIA_DOWNLOAD_R + FILEURL + nombre);

	        if (persona.getCorreoElectronico() != null && !persona.getCorreoElectronico().isEmpty()) {
	            MSG_EMAIL_REPORTE = new String(Base64.encodeBase64(MSG_EMAIL_REPORTE.getBytes()));
	            ConnectionHttp.sendEmailHTML64(persona.getCorreoElectronico(), MSG_EMAIL_REPORTE, asunto);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * Generación del encabezado
	 * */
	private void writeHeader(BufferedWriter bw) throws IOException {
	    bw.write("Reporte:" + CSV_SEPARATOR + reporte);
	    bw.newLine();
	    bw.write("Reporte Generado Por:" + CSV_SEPARATOR + persona.getNombres());
	    bw.newLine();
	    bw.write("Fecha:" + CSV_SEPARATOR + dateformat_1.format(new Date()));
	    bw.newLine();
	    bw.newLine();
	    for (Columna columna : columnas) {
	        bw.write(columna.getEtiquetaColumna());
	        bw.write(CSV_SEPARATOR);
	    }
	    bw.newLine();
	}

	/**
	 * Generación del detalle del encabezado
	 * */
	private void writeRecord(BufferedWriter bw, Registro registro) throws IOException {
	    int totalCol = 0;
	    for (Parametro param : registro.getItem()) {
	        totalCol++;
	        if (totalCol == 1) {
	            try {
	                if (param.getValue() != null) {
	                    int c = Integer.parseInt(param.getValue().toString());
	                } else {
	                    break;
	                }
	            } catch (NumberFormatException e) {
	                break;
	            }
	        }
	        String value = (param.getValue() == null || param.getValue().equals("null")) ? "" : param.getValue().toString().trim();
	        value = value.replaceAll("\\R", " ");
	        bw.write(value);
	        bw.write(CSV_SEPARATOR);
	    }
	    bw.newLine();
	}

	public static boolean uploadWindows(String path, InputStream in, String carpeta, String tipologia, String nombreArchivo) {
		String finalUrl="";
		try {
	    	String configPath        = System.getProperty("CONFIG_PATH");
			String curlUrl = configPath+"co/gov/dafp/sigep2/hadoopso/curl/curl-7.69.1-win64-mingw/bin/curl";
			String options = " -i -X PUT -T ";
			String repositorio =HADOOP_USER_REPO; 
			String hadoopUrl = "\"" +HADOOP_URL_BY_SO+repositorio+carpeta+nombreArchivo+"?op=CREATE&user.name=hdfs&namenoderpcaddress=cluster-hdfs&createflag=&createparent=true&overwrite=false\"";
			finalUrl = curlUrl + options + path + " " + hadoopUrl;
			Process process = Runtime.getRuntime().exec(finalUrl);
			return true;
		} catch (Exception e) {
			logger.log().error("finalUrl()", finalUrl);
			logger.log().error("Exception uploadWindows()", e.getMessage());
			return false;
		}
	}
	
	public static boolean uploadLinux(String path, String carpeta, String tipologia, String nombreArchivo) {
		String finalUrl="";
		try {
	    	String configPath        = System.getProperty("CONFIG_PATH");
	    	System.out.println("configPath: "+configPath);
	    	finalUrl = "bash "+configPath+"co/gov/dafp/sigep2/hadoopso/upload_file.sh "+path+" "+nombreArchivo+" "+carpeta.replace("/","")+" "+HADOOP_USER_REPO.replace("/", "");
			Process process = Runtime.getRuntime().exec(finalUrl);
			return true;
		} catch (Exception e) {
			logger.log().error("finalUrl()", finalUrl);
			logger.log().error("Exception uploadLinux()", e.getMessage());
			return false;
		}
	}
	
}
