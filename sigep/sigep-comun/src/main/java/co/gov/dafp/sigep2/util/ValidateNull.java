/**
 * 
 */
package co.gov.dafp.sigep2.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jose.viscaya
 *
 */
public class ValidateNull implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2469533084148424138L;
	
	/**
	 * @Metodo Elaborado por Jose Viscaya para validar que el dato ingresado no sea Nulo
	 * @Fecha  24 de enero de 2018
	 * @param value
	 * @return
	 */
	public static int validInt(Integer data) {
		try {
			if(data != null) {
				return  data;
			}else {
				return  0;
			}
		} catch (NullPointerException e) {
			return  0;
		}
	}
	/**
	 * @Metodo Elaborado por Jose Viscaya para validar que el dato ingresado no sea Nulo
	 * @Fecha  24 de enero de 2018
	 * @param value
	 * @return
	 */
	public static String validString(String value) {
		try {
			if(value != null) {
				return value;
			}else {
				return "";
			}
		} catch (NullPointerException e) {
			return  "";
		}
	}
	/**
	 * @Metodo Elaborado por Jose Viscaya para validar que el dato ingresado no sea Nulo
	 * @Fecha  24 de enero de 2018
	 * @param value
	 * @return
	 */
	public static boolean validBoolean(Boolean value) {
		try {
			if(value != null) {
				return value;
			}else {
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}
	}
	/**
	 * @Metodo Elaborado por Jose Viscaya para validar que el dato ingresado no sea Nulo
	 * @Fecha  24 de enero de 2018
	 * @param value
	 * @return
	 */
	public static long validLong(Long value) {
		try {
			if(value != null) {
				return value;
			}else {
				return 0l;
			}
		} catch (NullPointerException e) {
			return  0l;
		}
	}
	/**
	 * @Metodo Elaborado por Jose Viscaya para validar que el dato ingresado no sea Nulo
	 * @Fecha  24 de enero de 2018
	 * @param value
	 * @return
	 */
	public static Date validDate(Date value) {
		try {
			if(value != null) {
				return value;
			}else {
				return  new Date();
			}
		} catch (NullPointerException e) {
			return  new Date();
		}
	}
	/**
	 * @Metodo Elaborado por Jose Viscaya para validar que el dato ingresado no sea Nulo
	 * @Fecha  24 de enero de 2018
	 * @param value
	 * @return
	 */
	public static BigDecimal validBigDecimal(Long value) {
		try {
			if(value != null) {
				return new BigDecimal(value);
			}else {
				return  new BigDecimal(0);
			}
		} catch (NullPointerException e) {
			return  new BigDecimal(0);
		}
	}
	/**
	 * @Metodo Elaborado por Jose Viscaya para validar que el dato ingresado no sea Nulo
	 * @Fecha  24 de enero de 2018
	 * @param value
	 * @return
	 */
	public static Object validObject(Object value) {
		try {
			if(value != null) {
				return value;
			}else {
				return  new Object();
			}
		} catch (NullPointerException e) {
			 return  new Object();
		}
	}

}
