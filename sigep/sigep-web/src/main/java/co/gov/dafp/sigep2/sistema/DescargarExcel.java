package co.gov.dafp.sigep2.sistema;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

public class DescargarExcel extends HttpServlet {

	private static final long serialVersionUID = 4440011247408877539L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Base64 b64 = new Base64();
		String archivo = req.getParameter("path");
		String archivo1 = req.getParameter("path1");
		String proceso = req.getParameter("proceso");

		archivo = System.getProperty("CONFIG_PATH") + archivo;

		downloadFile(resp, archivo);
		eliminarPestanaLog(archivo1, proceso);
	}

	protected void downloadFile(HttpServletResponse response, String filePath) throws ServletException, IOException {

		File fileToDownload = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(fileToDownload);

		ServletOutputStream out = response.getOutputStream();
		String mimeType = new MimetypesFileTypeMap().getContentType(filePath);

		response.setContentType(mimeType);
		response.setContentLength(fileInputStream.available());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileToDownload.getName() + "\"");

		int c;
		while ((c = fileInputStream.read()) != -1) {
			out.write(c);
		}
		out.flush();
		out.close();
		fileInputStream.close();
	}

	protected void eliminarPestanaLog(String pathArchivoLogs, String proceso) throws ServletException, IOException {

		try {
			if(pathArchivoLogs != null && proceso != null) {
				InputStream excelStream = null;
				// OutputStream excelNewOutputStream = null;
				int index = 1;
	
				// String pathArchivoLogs = pathArchivoLog + "historico/" + nombreArchivoCargado
				// + FileUtil.XLS;
	
				// Después de que el usuario descargue la plantilla, procedemos a eliminarla
				excelStream = new FileInputStream(pathArchivoLogs);
	
				// Representación del más alto nivel de la hoja excel.
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(excelStream);
				//XSSFWorkbook hssfWorkbook = new XSSFWorkbook(new File(pathArchivoLogs));
				//HSSFWorkbook hssfWorkbook = new HSSFWorkbook(excelStream);
				// HSSFWorkbook hssfWorkbookNew = new HSSFWorkbook();
	
				Parametrica paramProceso = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(proceso));
	
				// SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				List<Parametrica> tabsArchivo = ComunicacionServiciosSis
						.getParametricaPorIdPadre(paramProceso.getCodTablaParametrica().longValue());
	
				// Si no trae ningÃºn resultado es porque el proceso no tiene varias tabs.
				if (tabsArchivo.isEmpty()) {
					tabsArchivo = new ArrayList<Parametrica>();
					tabsArchivo.add(paramProceso);
				}
	
				for (Parametrica tabArchivo : tabsArchivo) {
					// Trae la hoja de Excel de acuerdo al parÃ¡metro
					// String prueba = tabArchivo.getValorParametro();
					// HSSFSheet hojaPlantilla = archivo.getSheet(tabArchivo.getValorParametro());//
					// Selecciona la hoja del
					// archivo
	
					String hojaBorrar = tabArchivo.getValorParametro();
	
					String logs = "LOGS_" + hojaBorrar;
	
					if (logs.length() > 31) {
						logs = logs.substring(0, 31);
					}
	
					// Elegimos la hoja que se pasa por parámetro.
					HSSFSheet hssfSheet = hssfWorkbook.getSheet(logs);
	
					index = hssfWorkbook.getSheetIndex(hssfSheet);
	
					// Eliminamos la Hoja
					hssfWorkbook.removeSheetAt(index);
	
				}
	
				FileOutputStream outFile = new FileOutputStream(new File(pathArchivoLogs));
				hssfWorkbook.write(outFile);
				outFile.flush();
				outFile.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
