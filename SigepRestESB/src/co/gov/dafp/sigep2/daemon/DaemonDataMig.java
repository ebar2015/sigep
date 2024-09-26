/**
 * 
 */
package co.gov.dafp.sigep2.daemon;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import co.gov.dafp.sigep2.bean.UrlMigProd;
import co.gov.dafp.sigep2.services.ParametricaService;


/**
 * @author joseviscaya
 *
 */
public class DaemonDataMig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9091508296923373596L;
	
	
	@PostConstruct
	public static void starTask(){
		TaskDeamon taskDeamon = new TaskDeamon();
		taskDeamon.start();
	}

}

class TaskDeamon extends Thread{
	
	ParametricaService service = new ParametricaService();
    
	public void run(){
		String onOff ="";
		String dateLimit ="";
		String query = "";
		long total = 0;
		do {
			onOff 		= service.getPrametricaById(UtilitiesDaemon.RUN_DAEMON_MIG).getValorParametro();
			dateLimit   = service.getPrametricaById(UtilitiesDaemon.FECHA_MIG).getValorParametro();
			if(onOff != null && onOff.equals(UtilitiesDaemon.ON)){
				if(dateLimit != null && !dateLimit.isEmpty()) {
					total = executeQueryCount(UtilitiesDaemon.URL_MIG_PROD_TOTAL.replace("#FECHA", dateLimit));
					for (int i = 0; i < total; i++) {
						UrlMigProd urlMigProd = getUrlMigProd(dateLimit);
						switch (urlMigProd.getTablaDestino()) {
						case UtilitiesDaemon.TABLA_PERSONAS:
							switch (urlMigProd.getNombreCampo()) {
								case UtilitiesDaemon.CAMPO_DOCUMENTOS:
									executeQuery(UtilitiesDaemon.DOCUMENTOS_UPDATE,urlMigProd);
								break;
	
								case UtilitiesDaemon.CAMPO_FOTO_USUARIO:
									executeQuery(UtilitiesDaemon.FOTO_USUARIO_UPDATE,urlMigProd);
								break;
									
								case UtilitiesDaemon.CAMPO_LIBRETA_MILITAR:
									executeQuery(UtilitiesDaemon.LIBRETA_MILITAR_UPDATE,urlMigProd);
								break;
							}
							
							break;
						case UtilitiesDaemon.TABLA_DOCUMENTOS_ADICIONALES:
							executeQuery(UtilitiesDaemon.FOTO_USUARIO_UPDATE,urlMigProd);
						break;
						case UtilitiesDaemon.TABLA_EDUCACION_FORMAL:
							switch (urlMigProd.getNombreCampo()) {
							case UtilitiesDaemon.CAMPO_EDUCACION_ANEXO:
								executeQuery(UtilitiesDaemon.EDUCACION_ANEXO_UPDATE,urlMigProd);
								break;
							case UtilitiesDaemon.CAMPO_EDUCACION_TARJETA_PRO:
								executeQuery(UtilitiesDaemon.EDUCACION_TARJETA_PRO_UPDATE,urlMigProd);
								break;
							}
						break;
						case UtilitiesDaemon.TABLA_EXPERIENCIA_DOCENTE:
							executeQuery(UtilitiesDaemon.EXPERIENCIA_DOCENTE_UPDATE,urlMigProd);
						break;
						case UtilitiesDaemon.TABLA_EXPERIENCIA_PROFESIONAL:
							executeQuery(UtilitiesDaemon.EXPERIENCIA_PROFESIONAL_UPDATE,urlMigProd);
						break;
						case UtilitiesDaemon.TABLA_IDIOMA_PERSONA:
							executeQuery(UtilitiesDaemon.IDIOMA_PERSONA_UPDATE,urlMigProd);
						break;
						case UtilitiesDaemon.TABLA_NACIONALIDAD_PERFIL:
							executeQuery(UtilitiesDaemon.NACIONALIDAD_PERFIL_UPDATE,urlMigProd);
						break;
						case UtilitiesDaemon.TABLA_OTRO_CONOCIMIENTO:
							executeQuery(UtilitiesDaemon.OTRO_CONOCIMIENTO_UPDATE,urlMigProd);
						break;
						
						}
						
					}
				}
				
				
			}
			
		}while(true);
		
	}
	
	private static void executeQuery(String query, UrlMigProd urlMigProd ) {
		if(urlMigProd.getUrlGenerada() != null && !urlMigProd.getUrlGenerada().isEmpty()) {
			query = query.replace(UtilitiesDaemon.URL_REPLACE, urlMigProd.getUrlGenerada());
			query = query.replace(UtilitiesDaemon.CODIGO_REGISTRO_REPLACE, urlMigProd.getIdRegistro());
			if(executeUpdate(query)) {
				query = UtilitiesDaemon.URL_MIG_PROD_UPDATE.replace(UtilitiesDaemon.CODIGO_REGISTRO_REPLACE, urlMigProd.getCodUrlDataNoEstructurada()+"");
				query = query.replace(UtilitiesDaemon.PROD_REPLACE, "SI");
				executeUpdate(query);
			}else {
				query = UtilitiesDaemon.URL_MIG_PROD_UPDATE.replace(UtilitiesDaemon.CODIGO_REGISTRO_REPLACE, urlMigProd.getCodUrlDataNoEstructurada()+"");
				query = query.replace(UtilitiesDaemon.PROD_REPLACE, "F");
				executeUpdate(query);
			}
		}else {
			query = UtilitiesDaemon.URL_MIG_PROD_UPDATE.replace(UtilitiesDaemon.CODIGO_REGISTRO_REPLACE, urlMigProd.getCodUrlDataNoEstructurada()+"");
			query = query.replace(UtilitiesDaemon.PROD_REPLACE, "F");
			executeUpdate(query);
		}
		
	}
	
	private static Connection getconection() {
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:");
	        DataSource ds = (DataSource) envContext.lookup("/SIGEP2DS");
	        Connection conn = ds.getConnection();
	        return conn;
		} catch (NamingException e) {
			System.out.println(e.getMessage());
			return null;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
       
	}
	
	/**
	 * 
	 * @param query
	 * @param dataSource
	 * @return
	 */
	public static Long executeQueryCount(String query){
		Connection connObj = getconection();
		ResultSet rsObj = null;
		PreparedStatement pstmtObj = null;
		long total = 0;
		try {
			pstmtObj = connObj.prepareStatement(query);
			rsObj = pstmtObj.executeQuery();
			while (rsObj.next()) {
				total = rsObj.getLong("TOTAL");
				}
		} catch (SQLException e) {
			total = 0;
		}finally {
			try {
				if(pstmtObj != null) {
					pstmtObj.close();
				}
				if(connObj != null) {
					connObj.close();
				}
			} catch(Exception sqlException) {
				sqlException.printStackTrace();
			}
		}
		return total;
	}
	
	
	public static  UrlMigProd  getUrlMigProd(String dateLimit){
		Connection connObj = getconection();
		ResultSet rsObj = null;
		PreparedStatement pstmtObj = null;
		UrlMigProd urlMigProd = new UrlMigProd();
		try {
			pstmtObj = connObj.prepareStatement(UtilitiesDaemon.URL_MIG_PROD_DATA.replace("#FECHA", dateLimit));
			rsObj = pstmtObj.executeQuery();
			while (rsObj.next()) {
				urlMigProd.setCodUrlDataNoEstructurada(rsObj.getLong("COD_URL_DATA_NO_ESTRUCTURADA"));
				urlMigProd.setNumeroIdentificacion(rsObj.getString("NUMERO_IDENTIFICACION"));
				urlMigProd.setIdRegistro(rsObj.getString("ID_REGISTRO"));
				urlMigProd.setRutaArchivo(rsObj.getString("RUTA_ARCHIVO"));
				urlMigProd.setTablaDestino(rsObj.getString("TABLA_DESTINO"));
				urlMigProd.setNombreCampo(rsObj.getString("NOMBRE_CAMPO"));
				urlMigProd.setUrlGenerada(rsObj.getString("URL_GENERADA"));
				urlMigProd.setProcesado(rsObj.getString("PROCESADO"));
				urlMigProd.setFechaEjecucion(rsObj.getDate("FECHA_EJECUCION")); 
				urlMigProd.setCodigoEstado(rsObj.getString("CODIGO_ESTADO"));
				urlMigProd.setMensageEstado(rsObj.getString("MENSAGE_ESTADO"));
				urlMigProd.setActivo(rsObj.getShort("ACTIVO"));
				urlMigProd.setProd(rsObj.getString("PROD"));
			}
			return urlMigProd;
		} catch (SQLException e) {
			e.printStackTrace();
			return new UrlMigProd();
		}finally {
			try {
				if(pstmtObj != null) {
					pstmtObj.close();
				}
				if(connObj != null) {
					connObj.close();
				}
			} catch(Exception sqlException) {
				return new UrlMigProd();
			}
		}
	}
	
	
	public static boolean executeUpdate(String query) {
		Connection connObj = getconection();
		PreparedStatement pstmtObj = null;
		try {
			pstmtObj = connObj.prepareStatement(query);
			pstmtObj.executeQuery();
			if(!connObj.getAutoCommit()) {
				connObj.commit();
			}
		} catch (SQLException e) {
			return false;
		}finally {
			try {
				if(pstmtObj != null) {
					pstmtObj.close();
				}
				if(connObj != null) {
					connObj.close();
				}
			} catch(Exception sqlException) {
				sqlException.printStackTrace();
			}
		}
		return true;
	}

}
