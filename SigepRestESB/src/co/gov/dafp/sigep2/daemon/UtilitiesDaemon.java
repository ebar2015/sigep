/**
 * 
 */
package co.gov.dafp.sigep2.daemon;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author joseviscaya
 *
 */
public class UtilitiesDaemon implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6681956550376375007L;
	
	public static final String TABLA_PERSONAS                     = "PERSONA";
	public static final String TABLA_EXPERIENCIA_DOCENTE          = "EXPERIENCIA DOCENTE";
	public static final String TABLA_EXPERIENCIA_PROFESIONAL      = "EXPERIENCIA PROFESIONAL";
	public static final String TABLA_EDUCACION_FORMAL     	      = "EDUCACION_FORMAL";
	public static final String TABLA_IDIOMA_PERSONA               = "IDIOMA_PERSONA";
	public static final String TABLA_ENTIDAD_HORARIO              = "ENTIDAD_HORARIO";
	public static final String TABLA_OTRO_CONOCIMIENTO            = "OTRO_CONOCIMIENTO";
	public static final String TABLA_NACIONALIDAD_PERFIL          = "NACIONALIDAD_PERFIL";
	public static final String TABLA_DOCUMENTOS_ADICIONALES       = "DOCUMENTOS_ADICIONALES_HV";
	public static final String TABLA_ENTIDAD_BILINGUISMO          = "ENTIDAD_BILINGUISMO";
	public static final String ON          						  = "1";
	public static final String DATASOURCE          				  = "/SIGEP2DS";
	
	public static final BigDecimal RUN_DAEMON_MIG                 = new BigDecimal(5933);
	public static final BigDecimal FECHA_MIG                      = new BigDecimal(5934);
	
	
	
	public static final String CAMPO_DOCUMENTOS                   = "DOCUMENTO_IDENTIFICACION_URL";
	public static final String CAMPO_FOTO_USUARIO                 = "FOTO_URL";
	public static final String CAMPO_LIBRETA_MILITAR              = "LIBRETA_MILITAR_URL";
	public static final String CAMPO_EXPERIENCIA_DOCENTE          = "URL_DOCUMENTO_SOPORTE";
	public static final String CAMPO_EXPERIENCIA_PROFESIONAL      = "URL_DOCUMENTO_SOPORTE";
	public static final String CAMPO_EDUCACION_ANEXO     	      = "URL_ANEXO";
	public static final String CAMPO_EDUCACION_TARJETA_PRO        = "URL_TARJETA_PROFESIONAL";
	public static final String CAMPO_IDIOMA_PERSONA               = "URL_CERTIFICACION";
	public static final String CAMPO_HORARIO_NORMA                = "ACTO_ADMINISTRTIVO_URL";
	public static final String CAMPO_OTRO_CONOCIMIENTO            = "URL_DOCUMENTO_SOPORTE";
	public static final String CAMPO_NACIONALIDAD_PERFIL          = "ADJUNTO_URL";
	public static final String CAMPO_DOCUMENTOS_ADICIONALES       = "URL_DOCUMENTO_ADICIONAL";
	public static final String CAMPO_ENTIDAD_BILINGUISMO          = "CARTA_COMPROMISO_URL";
	
	
	public static final String CODIGO_ID_REPLACE 				  = "#IDTABLA";
	public static final String URL_REPLACE 				          = "#URL";
	public static final String PROD_REPLACE 				      = "#PROD";
	public static final String PROCESADO_REPLACE 				  = "#PROCESADO";
	public static final String NOMBRE_TABLA_REPLACE 			  = "#TABLA";
	public static final String NOMBRE_CAMPO_REPLACE 			  = "#NOMBRECAMPO";
	public static final String RUTA_ARCHIVO_REPLACE 			  = "#RUTA_ARCHIVO";
	public static final String DESCRIPCION_REPLACE 			  	  = "#DESCRIPCION";
	public static final String CODIGO_REGISTRO_REPLACE 			  = "#CODIGO_REGISTRO";
	public static final String ESTADO_REPLACE 			          = "#ESTADO";
	public static final String RUTA_ARCHIVO_SIGEPI_REPLACE 		  = "#OLD_URL";
	public static final String NUMERO_IDENTIFICACION_REPLACE 	  = "#NUMERO_IDENTIFICACION";
	public static final String RUTA_REPLACE 	  				  = "#RUTA";
	public static final String CODIGO_REPLACE 	  				  = "#CODIGO";
	public static final String MENSAGE_REPLACE 	  				  = "#MSG";
	
	
	public static final String DOCUMENTOS_UPDATE			  	 = "UPDATE  PERSONA SET DOCUMENTO_IDENTIFICACION_URL = '#URL' WHERE COD_PERSONA = #CODIGO_REGISTRO  AND INSTR(DOCUMENTO_IDENTIFICACION_URL,'getShowFile',2,1)  = 0";
	
	public static final String FOTO_USUARIO_UPDATE		  		 = "UPDATE  PERSONA SET FOTO_URL = '#URL' WHERE COD_PERSONA = #CODIGO_REGISTRO AND INSTR(FOTO_URL,'getShowFile',2,1)  = 0";
	
	public static final String LIBRETA_MILITAR_UPDATE		     = "UPDATE  PERSONA SET LIBRETA_MILITAR_URL = '#URL' WHERE COD_PERSONA = #CODIGO_REGISTRO AND INSTR(LIBRETA_MILITAR_URL,'getShowFile',2,1)  = 0";
	
	public static final String EXPERIENCIA_DOCENTE_UPDATE	     = "UPDATE  EXPERIENCIA_DOCENTE SET URL_DOCUMENTO_SOPORTE = '#URL' WHERE COD_EXPERIENCIA_DOCENTE = #CODIGO_REGISTRO AND INSTR(URL_DOCUMENTO_SOPORTE,'getShowFile',2,1)  = 0";
	
	public static final String EXPERIENCIA_PROFESIONAL_UPDATE	 = "UPDATE  EXPERIENCIA_PROFESIONAL SET URL_DOCUMENTO_SOPORTE = '#URL' WHERE COD_EXPERIENCIA_PROFESIONAL = #CODIGO_REGISTRO AND INSTR(URL_DOCUMENTO_SOPORTE,'getShowFile',2,1)  = 0";
	
	public static final String EDUCACION_ANEXO_UPDATE	  	     = "UPDATE  EDUCACION_FORMAL SET URL_ANEXO = '#URL' WHERE COD_EDUCACION_FORMAL = #CODIGO_REGISTRO AND INSTR(URL_ANEXO,'getShowFile',2,1)  = 0";
	
	public static final String EDUCACION_TARJETA_PRO_UPDATE      = "UPDATE  EDUCACION_FORMAL SET URL_TARJETA_PROFESIONAL = '#URL' WHERE COD_EDUCACION_FORMAL = #CODIGO_REGISTRO AND INSTR(URL_TARJETA_PROFESIONAL,'getShowFile',2,1)  = 0";
	
	public static final String IDIOMA_PERSONA_UPDATE	         = "UPDATE  IDIOMA_PERSONA SET URL_CERTIFICACION = '#URL' WHERE COD_IDIOMA_PERSONA = #CODIGO_REGISTRO AND INSTR(URL_CERTIFICACION,'getShowFile',2,1)  = 0";
	
	public static final String OTRO_CONOCIMIENTO_UPDATE	         = "UPDATE  OTRO_CONOCIMIENTO SET URL_DOCUMENTO_SOPORTE = '#URL' WHERE COD_OTRO_CONOCIMIENTO = #CODIGO_REGISTRO AND INSTR(URL_DOCUMENTO_SOPORTE,'getShowFile',2,1)  = 0";
	
	public static final String NACIONALIDAD_PERFIL_UPDATE	     = "UPDATE  NACIONALIDAD_PERFIL SET ADJUNTO_URL = '#URL' WHERE COD_NACIONALIDAD_PERFIL = #CODIGO_REGISTRO AND INSTR(ADJUNTO_URL,'getShowFile',2,1)  = 0";
	
	public static final String DOCUMENTOS_ADICIONALES_UPDATE     = "UPDATE  DOCUMENTOS_ADICIONALES_HV SET URL_DOCUMENTO_ADICIONAL = '#URL' WHERE COD_DOCUMENTO_ADICIONAL = #CODIGO_REGISTRO AND INSTR(URL_DOCUMENTO_ADICIONAL,'getShowFile',2,1)  = 0";
	
	public static final String URL_MIG_PROD_UPDATE     			 = "UPDATE  URL_MIG_PROD SET PROD = '#PROD' WHERE COD_URL_DATA_NO_ESTRUCTURADA = #CODIGO_REGISTRO";
	
	
	public static final String URL_MIG_PROD_TOTAL     			 = "SELECT COUNT(*) TOTAL FROM URL_MIG_PROD WHERE TRUNC(FECHA_EJECUCION) > TO_DATE('#FECHA','dd/mon/yyyy') AND PROD = 'NO' AND CODIGO_ESTADO = 200";
	
	public static final String URL_MIG_PROD_DATA     			 = "SELECT COD_URL_DATA_NO_ESTRUCTURADA, NUMERO_IDENTIFICACION, ID_REGISTRO, RUTA_ARCHIVO, TABLA_DESTINO, "
																 + "    NOMBRE_CAMPO, URL_GENERADA, PROCESADO, FECHA_EJECUCION, CODIGO_ESTADO, MENSAGE_ESTADO, "
																 + "    ACTIVO, PROD FROM URL_MIG_PROD WHERE TRUNC(FECHA_EJECUCION) > TO_DATE('#FECHA','dd/mon/yyyy') AND PROD = 'NO' AND CODIGO_ESTADO = 200 OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY";
	
	
	
	

}
