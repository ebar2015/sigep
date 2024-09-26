package co.gov.dafp.sigep2.sistema;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.mbean.bienesrentas.DeclaracionRentaBeanPrint;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.logger.Logger;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author jesus_Torres
 */
public class FuncionExtraBean {
    protected static Logger logger = Logger.getInstance(FuncionExtraBean.class);
    
    
    /**
     * @param ruta                Ubicacion del *.jasper en el proyecto.
     * @param listaImprimir       Lista, fuente de datos.
     * @param usaCampoDescripcion Por defecto en false.
     * @return true Si el proceso fue exitoso, false si hubo problemas.
     */
    public static boolean verJasperPDF(String ruta, ExternalContext contexto, List<?> listaImprimir, boolean usaCampoDescripcion, String nombreArchivo) {
	try {
	    String jasperPath = "/resources/jasper/" + ruta;
	    String relativePath = contexto.getRealPath(jasperPath);
	    File filePDF = new File(relativePath);
	    JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaImprimir, usaCampoDescripcion);
	    JasperPrint jPrint = JasperFillManager.fillReport(filePDF.getPath(), null, source);
	    HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
	    response.reset();
	    response.setContentType("application/pdf");
	    response.addHeader("Content-disposition", "inline; filename="+ nombreArchivo + ".pdf");
	    OutputStream stream = response.getOutputStream();
	    JasperExportManager.exportReportToPdfStream(jPrint, stream);
	    stream.flush();
	    stream.close();
	    FacesContext.getCurrentInstance().responseComplete();
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
    }

    /**
     * @param ruta                Ubicacion del *.jasper en el proyecto.
     * @param listaImprimir       Lista, fuente de datos.
     * @param usaCampoDescripcion Por defecto en false.
     * @param nombreArchivo       Nombre del archivo como desea ser guardado. Nota:
     *                            Solamente el nombre sin extensión.
     * @return true Si el proceso fue exitoso, false si hubo problemas.
     */
    public static boolean descargarJasperPDF(String ruta, ExternalContext contexto, List<?> listaImprimir, boolean usaCampoDescripcion, String nombreArchivo) {
	try {
	    String jasperPath = "/resources/jasper/" + ruta;
	    String relativePath = contexto.getRealPath(jasperPath);
	    File filePDF = new File(relativePath);
	    JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaImprimir, usaCampoDescripcion);
	    JasperPrint jPrint = JasperFillManager.fillReport(filePDF.getPath(), null, source);
	    HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
	    response.addHeader("Content-disposition", "attachment; filename=" + nombreArchivo + ".pdf");
	    ServletOutputStream stream = response.getOutputStream();
	    JasperExportManager.exportReportToPdfStream(jPrint, stream);
	    stream.flush();
	    stream.close();
	    FacesContext.getCurrentInstance().responseComplete();
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
    }

    public static String mostrarJasperPDF(ExternalContext contexto, List<DeclaracionRentaBeanPrint> listaImprimir, boolean usaCampoDescripcion) {
		try {
			String rutaCO = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_BIENES_RENTAS);
			String rutaFin = rutaCO;
		    String pathB = "/bienesrentas";
		    String jasperPath = "/resources/jasper/bienesrentas/FormatoBienesRentas.jasper";
		    String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
		    pathB = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathB);
		    File filePDF = new File(relativePath);
		    JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaImprimir, usaCampoDescripcion);
		    JasperPrint jPrint = JasperFillManager.fillReport(filePDF.getPath(), null, source);
		    String filename = "DeclaracionBienesYRentas" + UtilidadesFaces.eliminarCaracteresEspeciales(listaImprimir.get(0).getDetalleDeclaracion().getNombreTipoDeclaracion()) + listaImprimir.get(0).getDetalleDeclaracion().getCodDeclaracion()+ DateUtils.formatearACadena(listaImprimir.get(0).getDetalleDeclaracion().getFechaInicio(), "dd-MM-yyyy") + ".pdf";
		    rutaFin = ConfigurationBundleConstants.getRutaArchivo(rutaFin)+filename;
		    File fileExist = new File(rutaFin);
		    if(fileExist.exists())
		    	fileExist.delete();
		    OutputStream stream = new FileOutputStream(new File(rutaFin));
		    JasperExportManager.exportReportToPdfStream(jPrint, stream);
		    stream.flush();
		    stream.close();
		    String rPrint=  obtieneRutaVisualizarArchivo(rutaCO+"/"+filename);
		    return rPrint;
		}catch (Exception e) {
			logger.info("Error en FuncionExtraBean al generar string para PDF", e);
		    return null;
		}
    }

    public static String mostrarJasperPDFName(ExternalContext contexto, List<?> listaImprimir, boolean usaCampoDescripcion, String name) {
	try {
	    String pathB = "/bienesrentas";
	    String jasperPath = "/resources/jasper/bienesrentas/FormatoBienesRentas.jasper";
	    String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
	    pathB = FacesContext.getCurrentInstance().getExternalContext().getRealPath(pathB);
	    File filePDF = new File(relativePath);
	    JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaImprimir, usaCampoDescripcion);
	    JasperPrint jPrint = JasperFillManager.fillReport(filePDF.getPath(), null, source);

	    String filename = name + ".pdf";
	    String filePath = pathB + "/" + filename;
	    OutputStream stream = new FileOutputStream(new File(filePath));
	    JasperExportManager.exportReportToPdfStream(jPrint, stream);
	    stream.flush();
	    stream.close();
	    return "/bienesrentas/" + filename;
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }
    
	public static String obtieneRutaVisualizarArchivo(String ruta){
		if(ruta == null || ruta == "") {
			return null;
		}
		String urlDoc = "../../MostrarPdf?path=";
		String  configPath = System.getProperty("CONFIG_PATH")+""+ruta;
		ruta = new String(Base64.getEncoder().encodeToString(configPath.getBytes()));
		return  urlDoc+ruta;
	}
    
}