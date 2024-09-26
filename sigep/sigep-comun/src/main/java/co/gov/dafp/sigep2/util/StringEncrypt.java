package co.gov.dafp.sigep2.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Formatter;

public class StringEncrypt {
	// Definicion del tipo de algoritmo a utilizar (AES, DES, RSA)
	private final static String alg = "AES";
	// Definicion del modo de cifrado a utilizar
	private final static String cI = "AES/CBC/PKCS5Padding";
	
	public static final String CNS_KEY = "92AE31A79FEEB2A3";
    public static final String CNS_IV = "0123456789ABCDEF";

 	
	public static final String CNS_KEY_INTEGRACION = "92AE31A79FEEB2A392AE31A79FEEB2A3";
    public static final String CNS_IV_INTEGRACION = "0123456789ABCDEF0123456789ABCDEF";
    
	
	/**
	 * Función de tipo String que recibe una llave (key), un vector de
	 * inicialización (iv) y el texto que se desea cifrar
	 * 
	 * @param key
	 *            la llave en tipo String a utilizar
	 * @param iv
	 *            el vector de inicialización a utilizar
	 * @param cleartext
	 *            el texto sin cifrar a encriptar
	 * @return el texto cifrado en modo String
	 * @throws Exception
	 *             puede devolver excepciones de los siguientes tipos:
	 *             NoSuchAlgorithmException, InvalidKeyException,
	 *             InvalidAlgorithmParameterException,
	 *             IllegalBlockSizeException, BadPaddingException,
	 *             NoSuchPaddingException
	 */
	public static String encrypt(String key, String iv, String cleartext) {
		try {
			Cipher cipher = Cipher.getInstance(cI);
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
			byte[] encrypted = cipher.doFinal(cleartext.getBytes("UTF-8"));
			return new String(encodeBase64(encrypted));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Función de tipo String que recibe una llave (key), un vector de
	 * inicialización (iv) y el texto que se desea cifrar
	 * 
	 * @param cleartext
	 *            el texto sin cifrar a encriptar
	 * @return el texto cifrado en modo String
	 * @throws Exception
	 *             puede devolver excepciones de los siguientes tipos:
	 *             NoSuchAlgorithmException, InvalidKeyException,
	 *             InvalidAlgorithmParameterException,
	 *             IllegalBlockSizeException, BadPaddingException,
	 *             NoSuchPaddingException
	 */
	public static String encrypt(String cleartext) {
		try {
			String key = ConfigurationBundleConstants.key();
			String iv = ConfigurationBundleConstants.iv();
			return encrypt(key, iv, cleartext);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Función de tipo String que recibe una llave (key), un vector de
	 * inicialización (iv) y el texto que se desea descifrar
	 * 
	 * @param key
	 *            la llave en tipo String a utilizar
	 * @param iv
	 *            el vector de inicialización a utilizar
	 * @param encrypted
	 *            el texto cifrado en modo String
	 * @return el texto desencriptado en modo String
	 * @throws Exception
	 *             puede devolver excepciones de los siguientes tipos:
	 *             NoSuchAlgorithmException, NoSuchPaddingException,
	 *             InvalidKeyException, InvalidAlgorithmParameterException,
	 *             IllegalBlockSizeException
	 */
	public static String decrypt(String key, String iv, String encrypted) throws SIGEP2SistemaException {
		try {
			Cipher cipher = Cipher.getInstance(cI);
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
			byte[] enc = decodeBase64(encrypted.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
			byte[] decrypted = cipher.doFinal(enc);
			return new String(decrypted);
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e);
		}
	}

	/**
	 * Función de tipo String que recibe una llave (key), un vector de
	 * inicialización (iv) y el texto que se desea descifrar
	 * 
	 * @param encrypted
	 *            el texto cifrado en modo String
	 * @return el texto desencriptado en modo String
	 * @throws Exception
	 *             puede devolver excepciones de los siguientes tipos:
	 *             NoSuchAlgorithmException, NoSuchPaddingException,
	 *             InvalidKeyException, InvalidAlgorithmParameterException,
	 *             IllegalBlockSizeException
	 */
	public static String decrypt(String encrypted) throws SIGEP2SistemaException {
		try {
			String key = ConfigurationBundleConstants.key();
			String iv = ConfigurationBundleConstants.iv();
			return decrypt(key, iv, encrypted);
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e);
		}
	}

	/**
	 * Funcion para enmascaramiento de correo electronico
	 * 
	 * @param correoElectronico
	 * @return String <b>Correo con m�scara reemplazando con * en igual cantidad
	 *         a los caracteres que cumplan con la expresi�n regular
	 *         ^[a-zA-Z0-9.]{n} al comienzo de la cadena y con [a-zA-Z0-9.]{n}$
	 *         al final</b>
	 */
	@SuppressWarnings("resource")
	public static String enmascararCorreoElectronico(String correoElectronico) {
		try {
			String correoElectronicoEnmascarado = correoElectronico;
			int index = correoElectronico.indexOf("@") - 3;
			correoElectronicoEnmascarado = correoElectronicoEnmascarado.replaceAll("^[a-zA-Z0-9._]{" + index + "}", "");

			int len = correoElectronico.length() - correoElectronicoEnmascarado.length();

			String f = "%" + len + "s";
			Formatter fmt = new Formatter();
			f = fmt.format(f, "*").toString().replace(' ', '*');
			correoElectronicoEnmascarado = f + correoElectronicoEnmascarado;

			index = (correoElectronico.substring(correoElectronico.indexOf("@"), correoElectronico.length())).length()
					- 3;
			correoElectronicoEnmascarado = correoElectronicoEnmascarado.replaceAll("[a-zA-Z0-9._]{" + index + "}$", "");

			len = correoElectronico.length() - correoElectronicoEnmascarado.length();

			f = "%" + len + "s";
			fmt = new Formatter();
			f = fmt.format(f, "*").toString().replace(' ', '*');
			correoElectronicoEnmascarado = correoElectronicoEnmascarado + f;

			return correoElectronicoEnmascarado;
		} catch (Exception e) {
			return correoElectronico;
		}
	}	
	
	public static String cifrarBase64(String a){
        Base64.Encoder encoder = Base64.getEncoder();
        String b = encoder.encodeToString(a.getBytes(StandardCharsets.UTF_8) );        
        return b;
	}
	    
    public static String descifrarBase64(String a){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByteArray = decoder.decode(a);
        
        String b = new String(decodedByteArray);        
        return b;
    }
	

}
