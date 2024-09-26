package co.gov.dafp.sigep2.util;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;

/********************************************************************************************************************************************
 * P A S S W O R D G E N E R A T O R
 * 
 * Extrae aleatoriamente los caracteres de una cadena que contiene los posibles
 * caracteres para la contraseña. Al metodo getPassword() se le puede
 * suministrar tanto los tipos de caracteres posibles para la contraseña como la
 * longitud deseadad. Para facilitar la generación de las contraseñas mas
 * comunes, se han implementado un par de métodos, getPinNumber(), que genera
 * una contraseña de cuatro numeros y getPassword(), que sin argumentos generan
 * una contraseña de numeros y letras con una longitud de 8 caracteres.
 *
 ********************************************************************************************************************************************/
public class PasswordUtil {

	public static String NUMEROS = "0123456789";
	public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
	public static String ESPECIALES = ConfigurationBundleConstants
			.getString(ConfigurationBundleConstants.PASS_VALIDATE_EXPRESION_REGULAR_SIMBOLOS);

	public static String getPassword() {
		return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS + ESPECIALES);
	}

	/**
	 * Genera un nuevo Password conforme a los criterios recibidos
	 * 
	 * @param key
	 * @param length
	 * @return
	 */
	private static String getPassword(String key) {
		String pswd = "";
		int length = ConfigurationBundleConstants.getInt(ConfigurationBundleConstants.PASS_VALIDATE_MINIMO);

		for (int i = 0; i < length; i++) {
			pswd = pswd + (key.charAt((int) (Math.random() * key.length())));
		}

		return pswd;
	}
}
