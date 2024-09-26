package co.gov.dafp.sigep2.util.xml.reporte;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.XMLConstants;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoBandeja;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoCruceFecha;

public final class XmlReporte extends Reporte implements Serializable {
	private static final long serialVersionUID = -7957611318301208403L;

	transient static Logger logger = Logger.getInstance(XmlReporte.class);

	public static final String SISTEMA = TipoCruceFecha.SISTEMA.toString();
	public static final String CAPTURA = TipoCruceFecha.CAPTURA.toString();

	public static final String DESDE = TipoBandeja.DESDE.toString();
	public static final String A = TipoBandeja.A.toString();
	public static final String CC = TipoBandeja.CC.toString();
	public static final String CCO = TipoBandeja.CCO.toString();

	private String plantillaXML;
	private String xsdURL;
	private File xmlFile;

	private Ordenamiento ordenamiento;

	private XmlReporte() {
	}

	private XmlReporte(String plantillaXML, String subcarpeta) {
		super();
		this.plantillaXML = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML)
				+ subcarpeta + (subcarpeta.endsWith("/") ? "" : "/") + plantillaXML;
		logger.log().info("Xml(String plantillaXML)", "" + this.plantillaXML);
		this.xsdURL = ConfigurationBundleConstants
				.getRutaArchivo(ConfigurationBundleConstants.CNS_URL_XSD_XML_REPORTES);
		this.xmlFile = new File(this.plantillaXML);
	}

	public static XmlReporte getInstance() {
		return new XmlReporte("", "");
	}

	public static XmlReporte getXml(String plantillaXML, String subcarpeta) throws Exception {
		return new XmlReporte(plantillaXML, subcarpeta);
	}

	public static XmlReporte getEstructura(XmlReporte xml) throws Exception {
		String msg = "getEstructura()";
		Reporte reporte = null;

		// Se crea un SAXBuilder para poder parsear el archivo
		try {
			JAXBContext jc = JAXBContext.newInstance(Reporte.class);
			Unmarshaller ums = jc.createUnmarshaller();

			reporte = (Reporte) (ums.unmarshal(xml.getXmlFile()));
			logger.log().info(msg, "XML cargado");

			xml.setId(reporte.getId());
			xml.setNombre(reporte.getNombre());
			xml.setDescripcion(reporte.getDescripcion());
			xml.setFormaConsulta(reporte.getFormaConsulta());
			xml.setFormatoReporte(reporte.getFormatoReporte());
			xml.setPeriodoGeneracion(reporte.getPeriodoGeneracion());
			xml.setEliminado(reporte.isEliminado());
			xml.setPublicado(reporte.isPublicado());
			xml.setClaseJava(reporte.getClaseJava());
			xml.setTipoGrafico(reporte.getTipoGrafico());

			xml.getRol().addAll(reporte.getRol());
			xml.getMallaReporte().addAll(reporte.getMallaReporte());
			xml.getNotificacion().addAll(reporte.getNotificacion());
			xml.setUsuarioSolicita(reporte.getUsuarioSolicita());

			xml.setVersionEsquema(reporte.getVersionEsquema());
			xml.setTipoPlantilla(reporte.getTipoPlantilla());
			xml.getMallaConfiguracion().addAll(reporte.getMallaConfiguracion());
			xml.getRegistro().addAll(reporte.getRegistro());
			logger.log().info(msg, reporte.getNombre());
		} catch (Exception e) {
			logger.log().error(msg, e);
			throw new SIGEP2SistemaException(e);
		}
		return xml;
	}

	/**
	 * Concersi�n de Java a XML
	 */
	public static File getPlantillaXml(Reporte reporte, String plantillaXML, String subcarpeta)
			throws SIGEP2SistemaException {
		XmlReporte xml = new XmlReporte(plantillaXML, subcarpeta);
		try {
			OutputStream os = new FileOutputStream(xml.plantillaXML);
			// Instanciamos el contexto, indicando la clase que ser� el
			// RootElement.
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlReporte.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(reporte, os);
		} catch (Exception e) {
			logger.log().error("static Xml getXml(Reporte proceso, String plantillaXML) throws SIGEP2SistemaException",
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
						"Validaci�n xml contra esquema en " + xsdURL);
				schema = factory.newSchema(new File(xsdURL));
			} else {
				logger.log().info("validateXMLSchema(String xsdPath, String xmlPath)",
						"Validaci�n xml contra esquema por defecto");
				schema = factory.newSchema();
			}
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
			logger.log().info("validateXMLSchema(String xsdPath, String xmlPath)", "Validaci�n xml completa");
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

	public Ordenamiento getOrdenamiento() {
		if (this.ordenamiento == null) {
			this.ordenamiento = new Ordenamiento();
		}
		return ordenamiento;
	}

	public void unsetOrdenamiento() {
		this.ordenamiento = null;
	}
}
