/**
 * 
 */
package co.gov.dafp.sigep2.sistema;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;

/**
 * @author joseviscaya
 *
 */
public class UtilidadesFaces implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5624681115335873988L;
	
	public static void redirect(String pagePath){
		 try {
	            FacesContext.getCurrentInstance().getExternalContext().redirect(pagePath);                    
	        } catch (IOException ex) {
	        	     System.out.println(ex.getMessage());
	        }
	}
	
	public static String eliminarCaracteresEspeciales(String palabra) {
		//cambiar por expresiones regulares
		palabra = palabra.replace('Á', 'A');
		palabra = palabra.replace('É', 'E');
		palabra = palabra.replace('Í', 'I');
		palabra = palabra.replace('Ó', 'O');
		palabra = palabra.replace('Ú', 'U');
		palabra = palabra.replace('á', 'a');
		palabra = palabra.replace('é', 'e');
		palabra = palabra.replace('í', 'i');
		palabra = palabra.replace('ó', 'o');
		palabra = palabra.replace('ú', 'u');
		for(int i=0; i < palabra.length(); i++) {
			if((palabra.charAt(i) >= 48 && palabra.charAt(i) <= 57) || (palabra.charAt(i) >= 65 && palabra.charAt(i) <= 90 ) || (palabra.charAt(i) >= 97 && palabra.charAt(i) <= 122) || palabra.charAt(i) == 95)
				continue;
			else
				palabra = palabra.replace(palabra.charAt(i), '_');
		}
		return palabra;
	}

}
