package com.formater.jsons2xsd;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar los tipos de dato de los objetos 
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Date 9/10/2018
 */
public enum JsonSimpleType
{
    STRING, NUMBER, BOOLEAN, INTEGER, DATE;

    public static final String STRING_VALUE = "string";
    public static final String NUMBER_VALUE = "number";
    public static final String BOOLEAN_VALUE = "boolean";
    public static final String INTEGER_VALUE = "integer";
    public static final String DATE_VALUE = "date";
    /**
     * 
     * Elaborado por Jose Viscaya 
     * @fecha 9/10/2018 - 11:54:35 a. m.
     * @return
     */
    public String value()
    {
        return this.toString().toLowerCase();
    }
}
