package com.formater.jsons2xsd;

import org.xml.sax.SAXParseException;
/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar Schemas Invalidos
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Date 9/10/2018
 */
public class InvalidXsdSchema extends RuntimeException
{
    private static final long serialVersionUID = -6133929781335945777L;
	/**
	 * 
	 * Elaborado Por Jose Viscaya
	 * @Fecha 9/10/2018 - 11:52:53 a. m.
	 * @param msg
	 * @param cause
	 */
    public InvalidXsdSchema(String msg, SAXParseException cause)
    {
        super(msg, cause);
    }
}
