package co.gov.dafp.sigep2.util.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.elemento.TipoBandeja;
import co.gov.dafp.sigep2.util.xml.elemento.TipoCruceFecha;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

public final class Xml extends Proceso implements Serializable {
	private static final long serialVersionUID = -7957611318301208403L;

	transient static Logger logger = Logger.getInstance(Xml.class);

	public static final String SISTEMA = TipoCruceFecha.SISTEMA.toString();
	public static final String CAPTURA = TipoCruceFecha.CAPTURA.toString();

	public static final String DESDE = TipoBandeja.DESDE.toString();
	public static final String A = TipoBandeja.A.toString();
	public static final String CC = TipoBandeja.CC.toString();
	public static final String CCO = TipoBandeja.CCO.toString();

	private String plantillaXML;
	private String xsdURL;
	private File xmlFile;

	private Xml() {
	}

	private Xml(String plantillaXML) {
		super();
		this.plantillaXML = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML)
				+ plantillaXML;
		logger.log().info("Xml(String plantillaXML)", "" + this.plantillaXML);
		this.xsdURL = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_URL_XSD_XML);
		this.xmlFile = new File(this.plantillaXML);
	}

	public static Xml getInstance() {
		Xml proceso = new Xml("");
		return proceso;
	}

	public static Xml getXml(String plantillaXML) throws Exception {
		Xml xml = new Xml(plantillaXML);
		return xml;
	}

	public static Xml getEstructura(Xml xml) throws Exception {
		Proceso proceso = null;

		// Se crea un SAXBuilder para poder parsear el archivo
		try {
			logger.log().info("getEstructura()", "XML cargado");

			JAXBContext jc = JAXBContext.newInstance(Proceso.class);
			Unmarshaller ums = jc.createUnmarshaller();

			proceso = (Proceso) (ums.unmarshal(xml.getXmlFile()));
			xml.setNombre(proceso.getNombre());
			xml.setVersionEsquema(proceso.getVersionEsquema());
			xml.setTipoCargue(proceso.getTipoCargue());
			xml.getArchivo().addAll(proceso.getArchivo());
			logger.log().info("getEstructura()", proceso.getNombre());
		} catch (Exception e) {
			logger.log().error("getEstructura()", e);
			throw new SIGEP2SistemaException(e);
		}
		return xml;
	}

	/**
	 * Concersión de Java a XML
	 */
	public static File getPlantillaXml(Proceso proceso, String plantillaXML) throws SIGEP2SistemaException {
		Xml xml = new Xml(plantillaXML);
		try {
			OutputStream os = new FileOutputStream(xml.plantillaXML);
			// Instanciamos el contexto, indicando la clase que será el
			// RootElement.
			JAXBContext jaxbContext = JAXBContext.newInstance(Xml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(proceso, os);
		} catch (Exception e) {
			logger.log().error("static Xml getXml(Proceso proceso, String plantillaXML) throws SIGEP2SistemaException",
					e);
			throw new SIGEP2SistemaException(e);
		}
		return xml.xmlFile;

	}

	public static boolean validateXMLSchema(String xmlPath) {
		return validateXMLSchema(null, xmlPath);
	}

	public static boolean validateXMLSchema(String xsdURL, String xmlPath) {
		if (xmlPath == null) {
			logger.log().error("validateXMLSchema(String xsdPath, String xmlPath)", "xmlPath no puede ser nulo");
			return false;
		}
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema;
			if (xsdURL != null) {
				logger.log().info("validateXMLSchema(String xsdPath, String xmlPath)",
						"Validación xml contra esquema en " + xsdURL);
				schema = factory.newSchema(new File(xsdURL));
			} else {
				logger.log().info("validateXMLSchema(String xsdPath, String xmlPath)",
						"Validación xml contra esquema por defecto");
				schema = factory.newSchema();
			}
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
			logger.log().info("validateXMLSchema(String xsdPath, String xmlPath)", "Validación xml completa");
		} catch (SAXException | IOException e) {
			logger.log().error("validateXMLSchema(String xsdPath, String xmlPath)", e);
			return false;
		}
		return true;
	}

	public String getPlantillaXML() {
		return plantillaXML;
	}

	public String getXsdURL() {
		return xsdURL;
	}

	public File getXmlFile() {
		return xmlFile;
	}
}
