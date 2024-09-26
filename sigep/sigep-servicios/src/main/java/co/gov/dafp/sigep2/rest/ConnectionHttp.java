package co.gov.dafp.sigep2.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.json.JsonException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import co.gov.dafp.sigep2.entities.Archivo;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;

/**
 *
 * @author jose Class that can connect to server
 */
public class ConnectionHttp implements Serializable{

    private static final long serialVersionUID = -2601616153757111602L;
    protected static Logger logger = Logger.getInstance(ConnectionHttp.class);
    
    /**
     * @author: Jose Viscaya
     * @param urlr
     * @param input
     * @param token
     * @param timeout
     * @return
     */
    public static String sendPost(String urlr, String input, String token, long timeout, AuditoriaSeguridad auditJson) {
    	ErrorMensajes error = new ErrorMensajes();
    	Gson gson = new Gson();
   
    	try {
    		 input = new String(Base64.encodeBase64(input.getBytes()));
    		URL url = new URL(urlr);
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setDoOutput(true);
    		conn.setRequestMethod("POST");
    		conn.setRequestProperty("Content-Type", "text/plain");
    		conn.setRequestProperty("Accept", "text/plain");
    		conn.setRequestProperty("token", token);
    		conn.setRequestProperty("auditJson", gson.toJson(auditJson));
    		conn.setRequestProperty("timeout", String.valueOf(timeout));
    		OutputStream os = conn.getOutputStream();
    		os.write(input.getBytes());
    		os.flush();
    		if (conn.getResponseCode() != 201) {
    			error.setError(true);
    			error.setMensaje("Failed : HTTP error code : "+ conn.getResponseCode());
    			return gson.toJson(error);
    		}
    		BufferedReader br = new BufferedReader(new InputStreamReader(
    				(conn.getInputStream()), StandardCharsets.UTF_8));
    		String output;
    		String result = "";
            while ((output = br.readLine()) != null) {
            		result = output;
            }
    		conn.disconnect();
    		return result;

    	  } catch (MalformedURLException e) {
    		error.setError(true);
  		error.setMensaje("Failed : Url");
  		error.setMensajeTecnico(e.getMessage());
  		return gson.toJson(error);

    	  } catch (IOException e) {
    		error.setError(true);
    	  	error.setMensaje("Failed : Conexion");
    	    error.setMensajeTecnico(e.getMessage());
    	    return gson.toJson(error);
    	 }
		
    }
    
    /**
     * @author: Jose Viscaya
     * @param urlr
     * @param input
     * @param token
     * @param timeout
     * @return
     */
    public static String sendPostNoBase64(String urlr, String input, String token, long timeout, AuditoriaSeguridad auditJson) {
    	ErrorMensajes error = new ErrorMensajes();
    	Gson gson = new Gson();
   
    	try {
    		 //input = new String(Base64.encodeBase64(input.getBytes()));
    		URL url = new URL(urlr);
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setDoOutput(true);
    		conn.setRequestMethod("POST");
    		conn.setRequestProperty("Content-Type", "text/plain");
    		conn.setRequestProperty("Accept", "text/plain");
    		conn.setRequestProperty("token", token);
    		conn.setRequestProperty("auditJson", gson.toJson(auditJson));
    		conn.setRequestProperty("timeout", String.valueOf(timeout));
    		OutputStream os = conn.getOutputStream();
    		os.write(input.getBytes());
    		os.flush();
    		if (conn.getResponseCode() != 201) {
    			error.setError(true);
    			error.setMensaje("Failed : HTTP error code : "+ conn.getResponseCode());
    			return gson.toJson(error);
    		}
    		BufferedReader br = new BufferedReader(new InputStreamReader(
    				(conn.getInputStream()), StandardCharsets.UTF_8));
    		String output;
    		String result = "";
            while ((output = br.readLine()) != null) {
            		result = output;
            }
    		conn.disconnect();
    		return result;

    	  } catch (MalformedURLException e) {
    		error.setError(true);
  		error.setMensaje("Failed : Url");
  		error.setMensajeTecnico(e.getMessage());
  		return gson.toJson(error);

    	  } catch (IOException e) {
    		error.setError(true);
    	  	error.setMensaje("Failed : Conexion");
    	    error.setMensajeTecnico(e.getMessage());
    	    return gson.toJson(error);
    	 }
		
    }
    
    
  /**
   * @author: Jose Viscaya
   * @param email
   * @param mensage
   * @param asunto
   * @return
   */
    public static String sendEmail(String email, String mensage, String asunto) {
        ErrorMensajes error = new ErrorMensajes();
    	Gson gson = new Gson();
        String out = "";
        try {
            URL server = new URL(SerivesRestURL.SENT_EMAIL + URLEncoder.encode(email,  java.nio.charset.StandardCharsets.UTF_8.toString())+"/"+URLEncoder.encode(mensage,  java.nio.charset.StandardCharsets.UTF_8.toString())+"/"+URLEncoder.encode(asunto,  java.nio.charset.StandardCharsets.UTF_8.toString()));
            
            URLConnection connection = server.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine = "";
            while ((inputLine = in.readLine()) != null) {
                out += inputLine;
            }
            in.close();
            return out;
        } catch (UnsupportedEncodingException e) {
        	error.setError(true);
  		    error.setMensaje("Failed : UnsupportedEncodingException");
  		    error.setMensajeTecnico(e.getMessage());
  		    return gson.toJson(error);
        } catch (MalformedURLException e) {
        	error.setError(true);
  		    error.setMensaje("Failed : MalformedURLException");
  		    error.setMensajeTecnico(e.getMessage());
  		    return gson.toJson(error);
        } catch (IOException e) {
        	error.setError(true);
  		    error.setMensaje("Failed : IOException");
  		    error.setMensajeTecnico(e.getMessage());
  		    return gson.toJson(error);
        } catch (NullPointerException e) {
        	error.setError(true);
  		    error.setMensaje("Failed : NullPointerException");
  		    error.setMensajeTecnico(e.getMessage());
  		    return gson.toJson(error);
        }

    }
    
    /**
     * @author: Jose Viscaya
     * @param email
     * @param mensage
     * @param asunto
     * @return
     */
      public static String sendEmailHTML(String email, String mensage, String asunto) {
          ErrorMensajes error = new ErrorMensajes();
      	Gson gson = new Gson();
          String out = "";
          try {
              URL server = new URL(SerivesRestURL.SENT_EMAIL_HTML + URLEncoder.encode(email)+"/"+mensage+"/"+URLEncoder.encode(asunto));
              
              URLConnection connection = server.openConnection();
              BufferedReader in = new BufferedReader(
                      new InputStreamReader(connection.getInputStream()));
              String inputLine = "";
              while ((inputLine = in.readLine()) != null) {
                  out += inputLine;
              }
              in.close();
              return out;
          } catch (UnsupportedEncodingException e) {
          	error.setError(true);
    		    error.setMensaje("Failed : UnsupportedEncodingException");
    		    error.setMensajeTecnico(e.getMessage());
    		    return gson.toJson(error);
          } catch (MalformedURLException e) {
          	error.setError(true);
    		    error.setMensaje("Failed : MalformedURLException");
    		    error.setMensajeTecnico(e.getMessage());
    		    return gson.toJson(error);
          } catch (IOException e) {
          	error.setError(true);
    		    error.setMensaje("Failed : IOException");
    		    error.setMensajeTecnico(e.getMessage());
    		    return gson.toJson(error);
          }

      }
    
      
      public static String sendEmailHTML64(String email, String mensage, String asunto) {
          ErrorMensajes error = new ErrorMensajes();
      	Gson gson = new Gson();
          String out = "";
          try {
              URL server = new URL(SerivesRestURL.SENT_EMAIL_HTML64 + URLEncoder.encode(email)+"/"+mensage+"/"+URLEncoder.encode(asunto));
              
              URLConnection connection = server.openConnection();
              BufferedReader in = new BufferedReader(
                      new InputStreamReader(connection.getInputStream()));
              String inputLine = "";
              while ((inputLine = in.readLine()) != null) {
                  out += inputLine;
              }
              in.close();
              return out;
          } catch (UnsupportedEncodingException e) {
          	error.setError(true);
    		    error.setMensaje("Failed : UnsupportedEncodingException");
    		    error.setMensajeTecnico(e.getMessage());
    		    return gson.toJson(error);
          } catch (MalformedURLException e) {
          	error.setError(true);
    		    error.setMensaje("Failed : MalformedURLException");
    		    error.setMensajeTecnico(e.getMessage());
    		    return gson.toJson(error);
          } catch (IOException e) {
          	error.setError(true);
    		    error.setMensaje("Failed : IOException");
    		    error.setMensajeTecnico(e.getMessage());
    		    return gson.toJson(error);
          }

      }
      
      /**
       * @author: Jose Viscaya
       * @param url
       * @param file
       * @param token
       * @param ruta
       * @return
       */ 
      public static String sentFile(String url, File file, String token, String carpeta ,  String tipologia,
  			String numeroIdentificacion) {
    	ErrorMensajes error = new ErrorMensajes();
    	Gson gson = new Gson();
        try {
	         HttpClient httpclient = new DefaultHttpClient(); 
	         HttpPost httpPost = new HttpPost(url); 
	         HttpEntity entity = MultipartEntityBuilder
	        		    .create()
	        		    .addTextBody("token", token)
	        		    .addTextBody("carpeta", carpeta)
	        		    .addTextBody("tipologia", tipologia)
	        		    .addTextBody("numeroIdentificacion", numeroIdentificacion)
	        		    .addBinaryBody("file", file, ContentType.create("multipart/form-data"), getNameFile(file.getName()))
	        		    .build();
	         httpPost.setEntity(entity);
	         HttpResponse response = httpclient.execute(httpPost); 
	         HttpEntity entity1 = response.getEntity();
	         String responseString = EntityUtils.toString(entity1, "UTF-8");
	         return responseString;
		} catch (FileNotFoundException e) {
			error.setError(true);
  		    error.setMensaje("Failed : Url");
  		    error.setMensajeTecnico(e.getMessage());
  		   logger.log().error("******************** sentFile() ********************", e.getMessage());
  		    return gson.toJson(error);
		} catch (IOException e) {
			error.setError(true);
  		    error.setMensaje("Failed : Url");
  		    error.setMensajeTecnico(e.getMessage());
  		  logger.log().error("******************** sentFile() ********************", e.getMessage());
  		    return gson.toJson(error);
		} 
    }
      
      
      public static void main(String arg[]) {
    	  String urlr = "http://10.116.8.23:8080/ServerMultimediaSigep/ws/uploadReportes";
    	  String response = sentFileR(urlr,null,null);
    	  logger.log().error("response", response);
    	  
      }
      
      
      public static String sentFileR(String urlr, File file, String nombreArchivo) {
    	  ErrorMensajes error = new ErrorMensajes();
    	  Archivo archivoFile  = new Archivo();
          archivoFile.setNombreArchivo(nombreArchivo);
          archivoFile.setArchivodata(encodeFileToBase64Binary(file));
          Gson gson = new Gson();
          try {
        	    String input = gson.toJson(archivoFile);
      			URL url = new URL(urlr);
      			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      			conn.setDoOutput(true);
      			conn.setRequestMethod("POST");
      			conn.setRequestProperty("Content-Type", "application/json");
      			conn.setRequestProperty("Accept", "application/json");
      			OutputStream os = conn.getOutputStream();
      			os.write(input.getBytes());
      			os.flush();
      			if (conn.getResponseCode() != 200) {
      				error.setError(true);
      				error.setMensaje("Failed : HTTP error code : "+ conn.getResponseCode());
      				return gson.toJson(error);
      			}
      			BufferedReader br = new BufferedReader(new InputStreamReader(
      				(conn.getInputStream()), StandardCharsets.UTF_8));
      			String output;
      			String result = "";
      			while ((output = br.readLine()) != null) {
              		result = output;
      			}
      			conn.disconnect();
      			return result;

      	  } catch (MalformedURLException e) {
      		e.getStackTrace();
      		error.setError(true);
    		error.setMensaje("Failed : Url");
    		error.setMensajeTecnico(e.getMessage());
    		return gson.toJson(error);

      	  } catch (IOException e) {
      		logger.log().error("IOException", e.getMessage());
      		error.setError(true);
      	  	error.setMensaje("Failed : Conexión");
      	    error.setMensajeTecnico(e.getMessage());
      	    return gson.toJson(error);
      	 }catch(JsonException e) {
      		logger.log().error("JsonException", e.getMessage());
      		error.setError(true);
      	  	error.setMensaje("Failed : Conexion");
      	    error.setMensajeTecnico(e.getMessage());
      	    return gson.toJson(error);
      	 }catch(OutOfMemoryError e) {
      		logger.log().error("Memory", e.getMessage());
      		error.setError(true);
      	  	error.setMensaje("Failed : Conexion");
      	  	error.setMensajeTecnico("No es posible generar el reporte. El tamaño del reporte supera el máximo permitido para la generación del mismo.");
      	    return gson.toJson(error);  
      	 }
      }
      /**
       * @author: Jose Viscaya
       * @param path
       * @return
       */
      private static String getNameFile(String path) {
			path = path.replace("\\", "/");
			String data[] = path.split("/");
			if(data.length > 0) {
				return data[data.length-1];
			}
			return path;
	  }
      
      /**
       * @author: Jhon De Avila Mercado
       * @param urlr
       * @param input
       * @param token
       * @param timeout
       * @return
       */
      public static String sendGet(String urlr, String token, long timeout, AuditoriaSeguridad auditJson) {
      	ErrorMensajes error = new ErrorMensajes();
      	Gson gson = new Gson();
     
      	try {
      		URL url = new URL(urlr);
      		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      		conn.setDoOutput(true);
      		conn.setRequestMethod("GET");
      		conn.setRequestProperty("Content-Type", "text/plain");
      		conn.setRequestProperty("Accept", "text/plain");
      		conn.setRequestProperty("token", token);
      		conn.setRequestProperty("auditJson", gson.toJson(auditJson));
      		conn.setRequestProperty("timeout", String.valueOf(timeout));
      		if (conn.getResponseCode() != 201) {
      			error.setError(true);
      			error.setMensaje("Failed : HTTP error code : "+ conn.getResponseCode());
      			return gson.toJson(error);
      		}
      		BufferedReader br = new BufferedReader(new InputStreamReader(
      				(conn.getInputStream()), StandardCharsets.UTF_8));
      		String output;
      		String result = "";
              while ((output = br.readLine()) != null) {
              		result = output;
              }
      		conn.disconnect();
      		return result;

      	  } catch (MalformedURLException e) {
      		error.setError(true);
    		error.setMensaje("Failed : Url");
    		error.setMensajeTecnico(e.getMessage());
    		return gson.toJson(error);

      	  } catch (IOException e) {
      		error.setError(true);
      	  	error.setMensaje("Failed : Conexion");
      	    error.setMensajeTecnico(e.getMessage());
      	    return gson.toJson(error);
      	 }
  		
      }
      /**
       * 
       * @param urlr
       * @param ruta
       * @param docIdentidad
       * @param codigoRegistro
       * @param tipo
       * @return
       */
      public static String sendGetHadoop(String urlr, String ruta, String docIdentidad, String codigoRegistro, String tipo) {
        	try {
        		ruta = new String(Base64.encodeBase64(ruta.getBytes()));
        		docIdentidad = new String(Base64.encodeBase64(docIdentidad.getBytes()));
        		codigoRegistro = new String(Base64.encodeBase64(codigoRegistro.getBytes()));
        		tipo = new String(Base64.encodeBase64(tipo.getBytes()));
        		URL url = new URL(urlr+ruta+"/"+docIdentidad+"/"+tipo+"/"+codigoRegistro);
        		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        		conn.setDoOutput(true);
        		conn.setRequestMethod("GET");
        		conn.setRequestProperty("Content-Type", "text/plain");
        		conn.setRequestProperty("Accept", "text/plain");
        		BufferedReader br = new BufferedReader(new InputStreamReader(
        				(conn.getInputStream()), StandardCharsets.UTF_8));
        		String output;
        		String result = null;
                while ((output = br.readLine()) != null) {
                		result = output;
                }
        		conn.disconnect();
        		return result;
        	  } catch (MalformedURLException e) {
        		  return null;
        	  } catch (IOException e) {
        	    return null;
        	 }
        }
      
      private static String encodeFileToBase64Binary(File file){
  		byte[] bytes;
		try {
			bytes = loadFile(file);
		} catch (IOException e) {
			e.printStackTrace();
			 return null;
		}
  		byte[] encoded = Base64.encodeBase64(bytes);
  		String encodedString = new String(encoded);

  		return encodedString;
  	}

  	private static byte[] loadFile(File file) throws IOException {
  	    InputStream is = new FileInputStream(file);

  	    long length = file.length();
  	  
  	    byte[] bytes = new byte[(int)length];
  	    
  	    int offset = 0;
  	    int numRead = 0;
  	    while (offset < bytes.length
  	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
  	        offset += numRead;
  	    }

  	    if (offset < bytes.length) {
  	        System.out.println("Could not completely read file "+file.getName());
  	        return null;
  	    }

  	    is.close();
  	    return bytes;
  	}
    
}
