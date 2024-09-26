package com.formater.jsons2xsd;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de almacenar los diferemntes tipos de dato 
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Date 9/10/2018
 */
public enum JsonComplexType
{
    OBJECT("object"),
    ARRAY("array");

    public static final String OBJECT_VALUE = "object";
    public static final String ARRAY_VALUE = "array";

    private final String type;

    JsonComplexType(String type)
    {
        this.type = type;
    }

    private String value()
    {
        return type;
    }
}
