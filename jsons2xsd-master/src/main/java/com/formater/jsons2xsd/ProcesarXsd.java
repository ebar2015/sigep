/**
* 
 */
package com.formater.jsons2xsd;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.sigep2.bean.Parametrica;
import co.sigep2.services.ParametricaService;
import co.sigep2.services.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar 
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Date 2/10/2018
 */
public class ProcesarXsd {

	/**
	 * 
	 * Elaborado por Jose Viscaya 
	 * @fecha 9/10/2018 - 8:04:22 a. m.
	 * @return
	 */
	public static String getXds(String claseName, String json) {
		final  Reader targetReader = new StringReader(json);
	    try {
	    	
			final Config cfg = new Config.Builder()
                    .createRootElement(false)
                    .targetNamespace("http://sigep.gov.co/SigepII/schema/"+claseName+"-1.0.xsd")
                    .name(claseName)
                    .ignoreUnknownFormats(true)
                    .customTypeMapping(JsonSimpleType.INTEGER, "int64", XsdSimpleType.LONG)
                    .customTypeMapping(JsonSimpleType.INTEGER, "number", XsdSimpleType.INT)
                    .customTypeMapping(JsonSimpleType.INTEGER, "int32", XsdSimpleType.INT)
                    .customTypeMapping(JsonSimpleType.INTEGER, "int64b", XsdSimpleType.DECIMAL)
                    .customTypeMapping(JsonSimpleType.STRING, "string", XsdSimpleType.STRING)
                    .customTypeMapping(JsonSimpleType.DATE, "date", XsdSimpleType.DATE)
                    .customTypeMapping(JsonSimpleType.BOOLEAN, UtilsConstantes.MSG_BOOLEAN, XsdSimpleType.BOOLEAN)
                    .build();
			String xds = XmlUtil.asXmlString(Jsons2Xsd.convert(targetReader, cfg));
            targetReader.close();
            return xds;
		} catch (IOException e) {
			e.printStackTrace();
			 return "{}";
		}
	}
	/**
	 * 
	 * Elaborado Por: Jose Viscaya
	 * 28 ene. 2019
	 * ProcesarXsd.java
	 * @param nameXds
	 * @param nameField
	 * @param clase
	 * @return
	 */
	public static String getInfoClaseList(String nombreServicio, @SuppressWarnings("rawtypes") Class padre, boolean otraExt, @SuppressWarnings("rawtypes") Class ... clase) {
		String json = "{";
		if(padre != null) {
			final Field[] variables = padre.getDeclaredFields();
			   json += "\""+nombreServicio+"\": {";
			   json +="\"type\": \"object["+padre.getSimpleName()+"]\",";
		       json +="\"properties\": {";
		       for (final Field variable : variables) {
		    	   final Annotation anotacionObtenida = variable.getAnnotation(JsonXsd.class);
		    	   if(anotacionObtenida == null) {
		    		   continue;
		    	   }
		   		   final JsonXsd anotacionJsonXsd = (JsonXsd) anotacionObtenida;
			   		if(anotacionJsonXsd.nombreServicio().length > 0) {
			   			int cont = 0;
			   			for (String nombre : anotacionJsonXsd.nombreServicio()) {
							if(nombre.equals(nombreServicio)) {
								cont++;
							}
						}
			   			if(cont == 0) {
			   				continue;
			   			}
			   		}
		   		    String resF = getTypeFormat(anotacionJsonXsd);
					if(!resF.equals("{},")) {
						json += resF;
					}
				}
		    Class<?>  classExt = padre.getSuperclass();
			if(classExt != null) {
				final Field[] variablesExt = classExt.getDeclaredFields();
				for (final Field variable : variablesExt) {
					final Annotation anotacionObtenida = variable.getAnnotation(JsonXsd.class);
			   		   final JsonXsd anotacionJsonXsd = (JsonXsd) anotacionObtenida;
			   		   if(anotacionObtenida == null) {
			    		   continue;
			    	   }
				   		if(anotacionJsonXsd.nombreServicio().length > 0) {
				   			int cont = 0;
				   			for (String nombre : anotacionJsonXsd.nombreServicio()) {
								if(nombre.equals(nombreServicio)) {
									cont++;
								}
							}
				   			if(cont == 0) {
				   				continue;
				   			}
				   		}
			   		    String resF = getTypeFormat(anotacionJsonXsd);
						if(!resF.equals("{},")) {
							json += resF;
						}
				}
			}
			if(otraExt) {
				 Class<?>  classExt2 = classExt.getSuperclass();
					if(classExt2 != null) {
						final Field[] variablesExt = classExt2.getDeclaredFields();
						for (final Field variable : variablesExt) {
							final Annotation anotacionObtenida = variable.getAnnotation(JsonXsd.class);
					   		   final JsonXsd anotacionJsonXsd = (JsonXsd) anotacionObtenida;
					   		   if(anotacionObtenida == null) {
					    		   continue;
					    	   }
						   		if(anotacionJsonXsd.nombreServicio().length > 0) {
						   			int cont = 0;
						   			for (String nombre : anotacionJsonXsd.nombreServicio()) {
										if(nombre.equals(nombreServicio)) {
											cont++;
										}
									}
						   			if(cont == 0) {
						   				continue;
						   			}
						   		}
					   		    String resF = getTypeFormat(anotacionJsonXsd);
								if(!resF.equals("{},")) {
									json += resF;
								}
						}
					}
			}
			json = json.substring(0, json.length() -1);
			json +="}},";
			
		}
		
		if(clase != null) {
			if(clase.length > 1) {
				for (@SuppressWarnings("rawtypes") Class class1 : clase) {
					final Field[] variables = class1.getDeclaredFields();
					json += "\""+class1.getSimpleName()+"\": {";
					json +="\"type\": \"object["+class1.getSimpleName()+"]\",";
				       json +="\"properties\": {";
				       for (final Field variable : variables) {
				    	   final Annotation anotacionObtenida = variable.getAnnotation(JsonXsd.class);
				   		   final JsonXsd anotacionJsonXsd = (JsonXsd) anotacionObtenida;
				   		   if(anotacionObtenida == null) {
				    		   continue;
				    	   }
				   		   if(anotacionJsonXsd.nombreServicio().length > 0) {
					   			int cont = 0;
					   			for (String nombre : anotacionJsonXsd.nombreServicio()) {
									if(nombre.equals(nombreServicio)) {
										cont++;
									}
								}
					   			if(cont == 0) {
					   				continue;
					   			}
					   		}
				   		   String resF = getTypeFormat(anotacionJsonXsd);
							if(!resF.equals("{},")) {
								json += resF;
							}
						}
				    Class<?>  classExt = class1.getSuperclass();
					if(classExt != null) {
						final Field[] variablesExt = classExt.getDeclaredFields();
						for (final Field variable : variablesExt) {
							final Annotation anotacionObtenida = variable.getAnnotation(JsonXsd.class);
							 if(anotacionObtenida == null) {
					    		   continue;
					    	   }  
							final JsonXsd anotacionJsonXsd = (JsonXsd) anotacionObtenida;
						   		if(anotacionJsonXsd.nombreServicio().length > 0) {
						   			int cont = 0;
						   			for (String nombre : anotacionJsonXsd.nombreServicio()) {
										if(nombre.equals(nombreServicio)) {
											cont++;
										}
									}
						   			if(cont == 0) {
						   				continue;
						   			}
						   		}
					   		    String resF = getTypeFormat(anotacionJsonXsd);
								if(!resF.equals("{},")) {
									json += resF;
								}
						}
					}
					
					json = json.substring(0, json.length() -1);
					json +="}},";
				}
			}else if(clase.length == 1) {
				final Field[] variables = clase[0].getDeclaredFields();
				json += "\""+clase[0].getSimpleName()+"\": {";
				json +="\"type\": \"object["+clase[0].getSimpleName()+"]\",";
			       json +="\"properties\": {";
			       for (final Field variable : variables) {
			    	   final Annotation anotacionObtenida = variable.getAnnotation(JsonXsd.class);
			    	   if(anotacionObtenida == null) {
			    		   continue;
			    	   }
			   		   final JsonXsd anotacionJsonXsd = (JsonXsd) anotacionObtenida;
				   		if(anotacionJsonXsd.nombreServicio().length > 0) {
				   			int cont = 0;
				   			for (String nombre : anotacionJsonXsd.nombreServicio()) {
								if(nombre.equals(nombreServicio)) {
									cont++;
								}
							}
				   			if(cont == 0) {
				   				continue;
				   			}
				   		}
			   		    String resF = getTypeFormat(anotacionJsonXsd);
						if(!resF.equals("{},")) {
							json += resF;
						}
					}
			    Class<?>  classExt = clase[0].getSuperclass();
				if(classExt != null) {
					final Field[] variablesExt = classExt.getDeclaredFields();
					for (final Field variable : variablesExt) {
						final Annotation anotacionObtenida = variable.getAnnotation(JsonXsd.class);
				   		   final JsonXsd anotacionJsonXsd = (JsonXsd) anotacionObtenida;
				   		   if(anotacionObtenida == null) {
				    		   continue;
				    	   }
					   		if(anotacionJsonXsd.nombreServicio().length > 0) {
					   			int cont = 0;
					   			for (String nombre : anotacionJsonXsd.nombreServicio()) {
									if(nombre.equals(nombreServicio)) {
										cont++;
									}
								}
					   			if(cont == 0) {
					   				continue;
					   			}
					   		}
				   		    String resF = getTypeFormat(anotacionJsonXsd);
							if(!resF.equals("{},")) {
								json += resF;
							}
					}
				}
				json = json.substring(0, json.length() -1);
				json +="}},";
			}
	}
		json = json.substring(0, json.length() -1);
		json +="}";
		return json;
	}
	
	
	/**
	 * 
	 * Elaborado por Jose Viscaya 
	 * @fecha 9/10/2018 - 11:03:16 a. m.
	 * @param dato
	 * @param nameAtrb
	 * @return
	 */
	public static String getTypeFormat(JsonXsd anotacionJsonXsd) {
		String rerult = "";
		String listEnum = "";
		int [] docs = {38,39,42};
		int padredocs = 23;
		switch (anotacionJsonXsd.tipoDato()) {
		case "String":
			rerult = "\""+anotacionJsonXsd.nombreAtributo()+"\":{";
			rerult += "\"type\": \"String\",";
			if(anotacionJsonXsd.formato().isEmpty()) {
				rerult +="\"format\": \"String\"";
			}else {
				rerult +="\"format\":\""+anotacionJsonXsd.formato()+"\"";
			}
			
			break;
        case "Integer":
        	rerult = "\""+anotacionJsonXsd.nombreAtributo()+"\":{";
        	rerult += "\"type\": \"Integer\",";
        	if(anotacionJsonXsd.formato().isEmpty()) {
				rerult +="\"format\": \"numeric\"";
			}else {
				rerult +="\"format\":\""+anotacionJsonXsd.formato()+"\"";
			}
			break;
        case "Double":
        	rerult = "\""+anotacionJsonXsd.nombreAtributo()+"\":{";
        	rerult += "\"type\": \"Double\",";
        	if(anotacionJsonXsd.formato().isEmpty()) {
				rerult +="\"format\": \"numeric\"";
			}else {
				rerult +="\"format\":\""+anotacionJsonXsd.formato()+"\"";
			}
			break;
        case "BigDecimal":
        	rerult = "\""+anotacionJsonXsd.nombreAtributo()+"\":{";
        	rerult += "\"type\": \"BigDecimal\",";
        	if(anotacionJsonXsd.formato().isEmpty()) {
				rerult +="\"format\": \"numeric\"";
			}else {
				rerult +="\"format\":\""+anotacionJsonXsd.formato()+"\"";
			}
        	break;
        case UtilsConstantes.MSG_BOOLEAN:
        	rerult = "\""+anotacionJsonXsd.nombreAtributo()+"\":{";
        	rerult += "\"type\": \"Boolean\",";
        	if(anotacionJsonXsd.formato().isEmpty()) {
				rerult +="\"format\": \"boolean\"";
			}else {
				rerult +="\"format\":\""+anotacionJsonXsd.formato()+"\"";
			}
			break;
        case "Long":
        	rerult = "\""+anotacionJsonXsd.nombreAtributo()+"\":{";
        	rerult += "\"type\": \"Long\",";
        	if(anotacionJsonXsd.formato().isEmpty()) {
				rerult +="\"format\": \"numeric\"";
			}else {
				rerult +="\"format\":\""+anotacionJsonXsd.formato()+"\"";
			}
			break;
        case "Date":
        	rerult = "\""+anotacionJsonXsd.nombreAtributo()+"\":{";
        	rerult += "\"type\": \"Date\",";
        	if(anotacionJsonXsd.formato().isEmpty()) {
				rerult +="\"format\": \"date\"";
			}else {
				rerult +="\"format\":\""+anotacionJsonXsd.formato()+"\"";
			}
			break;
        case "Short":
        	rerult = "\""+anotacionJsonXsd.nombreAtributo()+"\":{";
        	rerult += "\"type\": \"Short\",";
        	if(anotacionJsonXsd.formato().isEmpty()) {
				rerult +="\"format\": \"numeric\"";
			}else {
				rerult +="\"format\":\""+anotacionJsonXsd.formato()+"\"";
			}
			break;
        case "Array":
        	rerult = "\""+anotacionJsonXsd.nombreAtributo()+"\":{";
        	rerult += "\"type\": \"List\",";
			rerult +="\"format\": \"List<Object>\"";
			break;
        case "Object":
        	rerult = "\""+anotacionJsonXsd.nombreAtributo()+"\":{";
        	rerult += "\"type\": \"Object\",";
        	rerult +="\"format\": \"Object\"";
        	break;
		default:
			rerult ="{";
			break;
		}
		rerult +=",\"requerido\": \""+anotacionJsonXsd.requerido()+"\"";
		if(anotacionJsonXsd.minLength() > 0) {
			rerult +=",\"minLength\":"+anotacionJsonXsd.minLength()+"";
		}
		if(anotacionJsonXsd.maxLength() > 0 ) {
			rerult +=",\"maxLength\":"+anotacionJsonXsd.maxLength()+"";
		}
		if(anotacionJsonXsd.link() != null) {
			if(!anotacionJsonXsd.link().isEmpty()) {
				rerult +=",\"link\":\""+anotacionJsonXsd.link()+"\"";
			}
		}
		rerult +=",\"description\": \""+anotacionJsonXsd.descripcion()+"\"";
	
		if(anotacionJsonXsd.listEnumID() > 0) {
			ParametricaService service = new ParametricaService();
			Parametrica param = new Parametrica();
			param.setCodPadreParametrica(new BigDecimal(anotacionJsonXsd.listEnumID()));
			param.setFlgActivo((short) 1);
			List<Parametrica> listP = service.getPrametricaByPadreiId(param);
			listEnum = "";
			for (Parametrica data : listP) {
				if(padredocs == anotacionJsonXsd.listEnumID()) {
					for(int num : docs) {
						if(num == data.getCodTablaParametrica().intValue()) {
							listEnum +="\""+data.getValorParametro()+"\",";
						}
					}
				}else {
					listEnum +="\""+data.getValorParametro()+"\",";
				}
			}
			if(!listEnum.isEmpty()) {
				listEnum = listEnum.substring(0,listEnum.length() - 1);
				if(listEnum.length() > 2) {
					rerult +=",\"enum\":["+listEnum+"]";
				}
			}
			
		}
		rerult +="},";
		return rerult;
	}
	
	/**
	 * 
	 * @Elaborado Por: Jose Viscaya
	 * @return
	 * @Fecha: May 3, 2019 9:03:37 AM
	 * ProcesarXsd.java
	 */
	public static ValidacionResult validarCampo(Object object, String nombreServicio) {
		Class clase = null;
		final ValidacionResult validacionResult = new ValidacionResult();
		validacionResult.setValido(true);
		try {
			clase = object.getClass();
		} catch (NullPointerException e) {
		  Validacion validacion = new Validacion();
		  List<Validacion> lstVatalidacion = new ArrayList<>();
		   validacionResult.setValido(false);
		   validacionResult.setCodeRespuesta(400);
		   validacion.setKey(null);
		   validacion.setValue(null);
		   validacion.setValidacionName(UtilsConstantes.MSG_FALTA_OBJETO_REQUERIDO);
		   lstVatalidacion.add(validacion);
		   validacionResult.setValidaciones(lstVatalidacion);
		   return validacionResult;
		}
		
		if(clase != null) {
			final Field[] variables = clase.getDeclaredFields();
			final List<Validacion> lstVatalidacion = new ArrayList<>();
			  for (final Field variable : variables) {
		    	   final Annotation anotacionObtenida = variable.getAnnotation(JsonXsd.class);
		    	   final Validacion validacion = new Validacion();
		    	   if(anotacionObtenida != null) {
		    		   final JsonXsd anotacionJsonXsd = (JsonXsd) anotacionObtenida;
			    	   try {
			    		   try {
			    			   boolean requerido = false;
			    			   Object value = clase.getMethod(ucFirst(anotacionJsonXsd.nombreAtributo())).invoke(object);
			    			   if(anotacionJsonXsd.nombreServicio().length > 0 ) {
			    				   for (int i = 0; i < anotacionJsonXsd.nombreServicio().length; i++) {
									 if(anotacionJsonXsd.nombreServicio()[i].equals(nombreServicio)) {
										 if(anotacionJsonXsd.requerido()) {
											 requerido = true;
										 }
									 }
								   }
			    			   }
			    			   
			    			   if(requerido) {
			    				   if(value == null) {
			    					   validacionResult.setValido(false);
			    					   validacionResult.setCodeRespuesta(400);
			    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
			    					   validacion.setValue(UtilsConstantes.MSG_NULL);
			    					   validacion.setValidacionName(UtilsConstantes.MSG_REQUERIDO);
			    					   lstVatalidacion.add(validacion);
			    					   continue;
			    				   }
			    			   }
			    			   if(anotacionJsonXsd.minLength() > 0 & getArrayFind(anotacionJsonXsd.nombreServicio(),nombreServicio)) {
			    				   if(value != null) {
				    				   if(value.toString().length() < anotacionJsonXsd.minLength()) {
				    					   validacionResult.setValido(false);
				    					   validacionResult.setCodeRespuesta(413);
				    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
				    					   validacion.setValue(value.toString());
				    					   validacion.setValidacionName(UtilsConstantes.MSG_MIN_LENGTH);
				    					   lstVatalidacion.add(validacion);
				    				   }
			    				   }
			    			   }
			    			   if(anotacionJsonXsd.maxLength() > 0 & getArrayFind(anotacionJsonXsd.nombreServicio(),nombreServicio)) {
			    				   if(value != null) {
				    				   if(value.toString().length() > anotacionJsonXsd.maxLength()) {
				    					   validacionResult.setValido(false);
				    					   validacionResult.setCodeRespuesta(413);
				    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
				    					   validacion.setValue(value.toString());
				    					   validacion.setValidacionName(UtilsConstantes.MSG_MAX_LENGTH);
				    					   lstVatalidacion.add(validacion);
				    				   }
			    				   }
			    			   }
			    		   } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			    			   validacionResult.setValido(false);
	    					   validacionResult.setCodeRespuesta(500);
	    					   validacion.setKey(null);
	    					   validacion.setValue(null);
	    					   validacion.setValidacionName(UtilsConstantes.MSG_DESCONOCIDO);
	    					   lstVatalidacion.add(validacion);
	    					   validacionResult.setValidaciones(lstVatalidacion);
			    			   return validacionResult;
						}
					} catch (NoSuchMethodException e) {
					} catch (SecurityException e) {
						   validacionResult.setValido(false);
	 					   validacionResult.setCodeRespuesta(500);
	 					   validacion.setKey(null);
	 					   validacion.setValue(null);
	 					   validacion.setValidacionName(UtilsConstantes.MSG_DESCONOCIDO);
	 					   lstVatalidacion.add(validacion);
	 					   validacionResult.setValidaciones(lstVatalidacion);
						return validacionResult;
					}
		    	   }
			  }
			  Class<?>  classExt = clase.getSuperclass();
			  if(classExt != null) {
				  final Field[] variablesExt = classExt.getDeclaredFields();
					  for (final Field variable : variablesExt) {
				    	   final Annotation anotacionObtenida = variable.getAnnotation(JsonXsd.class);
				    	   final Validacion validacion = new Validacion();
				    	   if(anotacionObtenida != null) {
				    		   final JsonXsd anotacionJsonXsd = (JsonXsd) anotacionObtenida;
					    	   try {
					    		   try {
					    			   boolean requerido = false;
					    			   Object value = classExt.getMethod(ucFirst(anotacionJsonXsd.nombreAtributo())).invoke(object);
					    			   if(anotacionJsonXsd.nombreServicio().length > 0 ) {
					    				   for (int i = 0; i < anotacionJsonXsd.nombreServicio().length; i++) {
											 if(anotacionJsonXsd.nombreServicio()[i].equals(nombreServicio)) {
												 if(anotacionJsonXsd.requerido()) {
													 requerido = true;
												 }
											 }
										   }
					    			   }
					    			   if(requerido) {
					    				   if(value == null) {
					    					   validacionResult.setValido(false);
					    					   validacionResult.setCodeRespuesta(400);
					    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
					    					   validacion.setValue(UtilsConstantes.MSG_NULL);
					    					   validacion.setValidacionName(UtilsConstantes.MSG_REQUERIDO);
					    					   lstVatalidacion.add(validacion);
					    					   continue;
					    				   }
					    			   }
					    			   if(anotacionJsonXsd.minLength() > 0 & getArrayFind(anotacionJsonXsd.nombreServicio(),nombreServicio)) {
					    				   if(value != null) {
						    				   if(value.toString().length() < anotacionJsonXsd.minLength()) {
						    					   validacionResult.setValido(false);
						    					   validacionResult.setCodeRespuesta(413);
						    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
						    					   validacion.setValue(value.toString());
						    					   validacion.setValidacionName(UtilsConstantes.MSG_MIN_LENGTH);
						    					   lstVatalidacion.add(validacion);
						    				   }
					    				   }
					    			   }
					    			   if(anotacionJsonXsd.maxLength() > 0 & getArrayFind(anotacionJsonXsd.nombreServicio(),nombreServicio)) {
					    				   if(value != null) {
						    				   if(value.toString().length() > anotacionJsonXsd.maxLength()) {
						    					   validacionResult.setValido(false);
						    					   validacionResult.setCodeRespuesta(413);
						    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
						    					   validacion.setValue(value.toString());
						    					   validacion.setValidacionName(UtilsConstantes.MSG_MAX_LENGTH);
						    					   lstVatalidacion.add(validacion);
						    				   }
					    				   }
					    			   }
					    			   if(!anotacionJsonXsd.formato().isEmpty()& getArrayFind(anotacionJsonXsd.nombreServicio(),nombreServicio)) {
					    				   if(value != null) {
						    				   if(anotacionJsonXsd.tipoDato().equals(UtilsConstantes.MSG_BOOLEAN)){
						    					   String d[] = anotacionJsonXsd.formato().split("/");
						    					   if(!value.toString().equals(d[0]) && !value.toString().equals(d[1])) {
						    						   validacionResult.setValido(false);
							    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
							    					   validacion.setValue(value.toString());
							    					   validacion.setValidacionName(UtilsConstantes.MSG_FORMATO);
							    					   lstVatalidacion.add(validacion);
						    					   }
						    				   }
					    				   }
					    			   }
					    		   } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					    			   validacionResult.setValido(false);
			    					   validacionResult.setCodeRespuesta(500);
			    					   validacion.setKey(null);
			    					   validacion.setValue(null);
			    					   validacion.setValidacionName(UtilsConstantes.MSG_DESCONOCIDO);
			    					   lstVatalidacion.add(validacion);
			    					   validacionResult.setValidaciones(lstVatalidacion);
					    			   return validacionResult;
								}
							} catch (NoSuchMethodException e) {
							} catch (SecurityException e) {
									validacionResult.setValido(false);
		    					   validacionResult.setCodeRespuesta(500);
		    					   validacion.setKey(null);
		    					   validacion.setValue(null);
		    					   validacion.setValidacionName(UtilsConstantes.MSG_DESCONOCIDO);
		    					   lstVatalidacion.add(validacion);
		    					   validacionResult.setValidaciones(lstVatalidacion);
								return validacionResult;
							}
				    	   }
					  }
			  }
			  validacionResult.setValidaciones(lstVatalidacion);
			  return validacionResult;
		}else {
			validacionResult.setValido(false);
			return validacionResult;
		}
	}
	/**
	 * 
	 * @Elaborado Por: Jose Viscaya
	 * @param str
	 * @return
	 * @Fecha: May 3, 2019 10:55:10 AM
	 * ProcesarXsd.java
	 */
	public static String ucFirst(String str) {
		  if (str == null || str.isEmpty()) {
		    return str;            
		  } else {
		    return "get"+str.substring(0, 1).toUpperCase() + str.substring(1); 
		  }
	}
	public static ValidacionResult validarCampoList(List<?> object, String nombreServicio) {
		final ValidacionResult validacionResult = new ValidacionResult();
		validacionResult.setValido(true);
		if(object != null) {
			if(object.size() > 0) {
				for (Object object2 : object) {
					Class clase = object2.getClass();
					validacionResult.setValido(true);
					if(clase != null) {
						final Field[] variables = clase.getDeclaredFields();
						final List<Validacion> lstVatalidacion = new ArrayList<>();
						  for (final Field variable : variables) {
					    	   final Annotation anotacionObtenida = variable.getAnnotation(JsonXsd.class);
					    	   final Validacion validacion = new Validacion();
					    	   if(anotacionObtenida != null) {
					    		   final JsonXsd anotacionJsonXsd = (JsonXsd) anotacionObtenida;
						    	   try {
						    		   try {
						    			   Object value = clase.getMethod(ucFirst(anotacionJsonXsd.nombreAtributo())).invoke(object2);
						    			   if(Arrays.asList(anotacionJsonXsd.requerido()).contains(nombreServicio)) {
						    				   if(value == null) {
						    					   validacionResult.setValido(false);
						    					   validacionResult.setCodeRespuesta(400);
						    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
						    					   validacion.setValue(UtilsConstantes.MSG_NULL);
						    					   validacion.setValidacionName(UtilsConstantes.MSG_REQUERIDO);
						    					   lstVatalidacion.add(validacion);
						    					   continue;
						    				   }
						    			   }
						    			   if(anotacionJsonXsd.minLength() > 0 & getArrayFind(anotacionJsonXsd.nombreServicio(),nombreServicio)) {
						    				   if(value != null) {
							    				   if(value.toString().length() < anotacionJsonXsd.minLength()) {
							    					   validacionResult.setValido(false);
							    					   validacionResult.setCodeRespuesta(413);
							    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
							    					   validacion.setValue(value.toString());
							    					   validacion.setValidacionName(UtilsConstantes.MSG_MIN_LENGTH);
							    					   lstVatalidacion.add(validacion);
							    				   }
						    				   }
						    			   }
						    			   if(anotacionJsonXsd.maxLength() > 0 & getArrayFind(anotacionJsonXsd.nombreServicio(),nombreServicio)) {
						    				   if(value != null) {
							    				   if(value.toString().length() > anotacionJsonXsd.maxLength()) {
							    					   validacionResult.setValido(false);
							    					   validacionResult.setCodeRespuesta(413);
							    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
							    					   validacion.setValue(value.toString());
							    					   validacion.setValidacionName(UtilsConstantes.MSG_MAX_LENGTH);
							    					   lstVatalidacion.add(validacion);
							    				   }
						    				   }
						    			   }
						    		   } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						    			   validacionResult.setValido(false);
				    					   validacionResult.setCodeRespuesta(500);
				    					   validacion.setKey(null);
				    					   validacion.setValue(null);
				    					   validacion.setValidacionName(UtilsConstantes.MSG_DESCONOCIDO);
				    					   lstVatalidacion.add(validacion);
				    					   validacionResult.setValidaciones(lstVatalidacion);
										return validacionResult;
									}
								} catch (NoSuchMethodException e) {
								} catch (SecurityException e) {
									   validacionResult.setValido(false);
			    					   validacionResult.setCodeRespuesta(500);
			    					   validacion.setKey(null);
			    					   validacion.setValue(null);
			    					   validacion.setValidacionName(UtilsConstantes.MSG_DESCONOCIDO);
			    					   lstVatalidacion.add(validacion);
			    					   validacionResult.setValidaciones(lstVatalidacion);
									return validacionResult;
								}
					    	   }
						  }
						  Class<?>  classExt = clase.getSuperclass();
						  if(classExt != null) {
							  final Field[] variablesExt = classExt.getDeclaredFields();
								  for (final Field variable : variablesExt) {
							    	   final Annotation anotacionObtenida = variable.getAnnotation(JsonXsd.class);
							    	   final Validacion validacion = new Validacion();
							    	   if(anotacionObtenida != null) {
							    		   final JsonXsd anotacionJsonXsd = (JsonXsd) anotacionObtenida;
								    	   try {
								    		   try {
								    			   Object value = classExt.getMethod(ucFirst(anotacionJsonXsd.nombreAtributo())).invoke(object2);
								    			   if(Arrays.asList(anotacionJsonXsd.requerido()).contains(nombreServicio)) {
								    				   if(value == null) {
								    					   validacionResult.setValido(false);
								    					   validacionResult.setCodeRespuesta(400);
								    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
								    					   validacion.setValue(UtilsConstantes.MSG_NULL);
								    					   validacion.setValidacionName(UtilsConstantes.MSG_REQUERIDO);
								    					   lstVatalidacion.add(validacion);
								    					   continue;
								    				   }
								    			   }
								    			   if(anotacionJsonXsd.minLength() > 0 & getArrayFind(anotacionJsonXsd.nombreServicio(),nombreServicio)) {
								    				   if(value != null) {
									    				   if(value.toString().length() < anotacionJsonXsd.minLength()) {
									    					   validacionResult.setValido(false);
									    					   validacionResult.setCodeRespuesta(413);
									    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
									    					   validacion.setValue(value.toString());
									    					   validacion.setValidacionName(UtilsConstantes.MSG_MIN_LENGTH);
									    					   lstVatalidacion.add(validacion);
									    				   }
								    				   }
								    			   }
								    			   if(anotacionJsonXsd.maxLength() > 0 & getArrayFind(anotacionJsonXsd.nombreServicio(),nombreServicio)) {
								    				   if(value != null) {
									    				   if(value.toString().length() > anotacionJsonXsd.maxLength()) {
									    					   validacionResult.setValido(false);
									    					   validacionResult.setCodeRespuesta(413);
									    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
									    					   validacion.setValue(value.toString());
									    					   validacion.setValidacionName(UtilsConstantes.MSG_MAX_LENGTH);
									    					   lstVatalidacion.add(validacion);
									    				   }
								    				   }
								    			   }
								    			   if(!anotacionJsonXsd.formato().isEmpty()& getArrayFind(anotacionJsonXsd.nombreServicio(),nombreServicio)) {
								    				   if(value != null) {
									    				   if(anotacionJsonXsd.tipoDato().equals(UtilsConstantes.MSG_BOOLEAN)){
									    					   String d[] = anotacionJsonXsd.formato().split("/");
									    					   if(!value.toString().equals(d[0]) && !value.toString().equals(d[1])) {
									    						   validacionResult.setValido(false);
										    					   validacion.setKey(anotacionJsonXsd.nombreAtributo());
										    					   validacion.setValue(value.toString());
										    					   validacion.setValidacionName(UtilsConstantes.MSG_FORMATO);
										    					   lstVatalidacion.add(validacion);
									    					   }
									    				   }
								    				   }
								    			   }
								    		   } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								    			   validacionResult.setValido(false);
						    					   validacionResult.setCodeRespuesta(500);
						    					   validacion.setKey(null);
						    					   validacion.setValue(null);
						    					   validacion.setValidacionName(UtilsConstantes.MSG_DESCONOCIDO);
						    					   lstVatalidacion.add(validacion);
						    					   validacionResult.setValidaciones(lstVatalidacion);
												return validacionResult;
											}
										} catch (NoSuchMethodException e) {
										} catch (SecurityException e) {
											   validacionResult.setValido(false);
					    					   validacionResult.setCodeRespuesta(500);
					    					   validacion.setKey(null);
					    					   validacion.setValue(null);
					    					   validacion.setValidacionName(UtilsConstantes.MSG_DESCONOCIDO);
					    					   lstVatalidacion.add(validacion);
					    					   validacionResult.setValidaciones(lstVatalidacion);
											return validacionResult;
										}
							    	   }
								  }
						  }
						  validacionResult.setValidaciones(lstVatalidacion);
					}else {
						validacionResult.setValido(false);
						return validacionResult;
					}
				}
			}
		}
		return validacionResult;
	}
	/**
	 * 
	 * @Elaborado Por: Jose Viscaya
	 * @param datos
	 * @param valor
	 * @return
	 * @Fecha: Aug 28, 2019 2:22:54 PM
	 * ProcesarXsd.java
	 */
	public static boolean getArrayFind(String [] datos, String valor) {
		for (int i = 0; i < datos.length; i++) {
			if(valor.equals(datos[i])) {
				return true;
			}
		}
		return false;
	}

}
