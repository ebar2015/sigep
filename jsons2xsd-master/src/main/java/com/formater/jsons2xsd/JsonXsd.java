/**
 * @Author Jose Viscaya
 * JsonXsd.java
 * 13 feb. 2019
 */
package com.formater.jsons2xsd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jose Viscaya
 * JsonXsd.java
 * 13 feb. 2019
 * AnotacionesExample
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JsonXsd {
    // nombre del servicio que usa este parametro
	String [] nombreServicio();
	// nombre del atributo a mostar
	String nombreAtributo();
    // validacion de requerido
	boolean requerido() default false;
	// id de la parametrica
	long  listEnumID() default 0;
	// tipo de dato del parmatro
	String tipoDato();
	// descripcion del parametro
	String descripcion();
	// limites de tamano de dato inferior
	int minLength() default 0;
	// limites de tamano de dato superior
	int maxLength() default 0;
	// link de referencia para objetos y listas
	String link() default "";
	// formato esperado en la variable
	String formato() default "";
	// link del dominio de datos
	String linkDominio() default "";
	
}
