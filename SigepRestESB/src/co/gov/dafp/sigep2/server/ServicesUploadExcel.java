/**
 * 
 */
package co.gov.dafp.sigep2.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import co.gov.dafp.sigep2.bean.Archivo;
import co.gov.dafp.sigep2.bean.CalidadDeDatos;
import co.gov.dafp.sigep2.bean.CalidadDeDatosNotificacion;
import co.gov.dafp.sigep2.services.CalidadDeDatosService;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author joseviscaya
 *
 */
@Path("/fileEx")
public class ServicesUploadExcel implements Serializable {

	private static final long serialVersionUID = -3924764748244386L;
	public Gson gson = new Gson();
	
	@GET()
    @Path("testAlive/{msg}")
	@Produces("text/plain")
	public Response cliente(@Context HttpServletRequest req, @PathParam("msg") String msg) {    	
		return Response.status(201).entity("Hello: Services Context sigepbr... "+msg).build();	
	}


	/**
	 * 
	 * @Author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @param auditJson
	 * @return
	 * @Date: 12 nov. 2021
	 */
	@POST()
	@Path("/uploadExcel")
	@Consumes("text/plain")
	public Response uploadDoc(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson){
		Gson gson = null;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		Archivo catFiles;
		boolean status = false;

		try {
			catFiles = gson.fromJson(json, Archivo.class);
		} catch (JsonSyntaxException e) {
			return Response.status(404).entity(e.getMessage()).build();
		}

		String fileName = "rt_." + catFiles.getNombreArchivo();

		catFiles.setNombreArchivo(fileName);

		if (catFiles.getArchivo() == null) {
			return Response.status(404).entity(status).build();
		} else {
			status = procesarArchivo(catFiles.getArchivo(), req);
			return Response.status(404).entity("ok").build();

		}

	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @param archivo
	 * @param req
	 * @return
	 * @Date: 12 nov. 2021
	 */
	private boolean procesarArchivo(String archivo, @Context HttpServletRequest req){
		byte[] decoder = Base64.getDecoder().decode(archivo.getBytes(StandardCharsets.UTF_8));
		ByteArrayInputStream fis = new ByteArrayInputStream(decoder);
		return this.readExcelReport(fis, req);
		
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @param fis
	 * @param req
	 * @return
	 * @Date: 12 nov. 2021
	 */
	private boolean readExcelReport(ByteArrayInputStream fis, HttpServletRequest req){
		HSSFWorkbook wb;
		try {
			wb = new HSSFWorkbook(fis);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			return false;
		}
		HSSFSheet sheet = wb.getSheetAt(0);

		int rows = sheet.getLastRowNum();
		ArrayList<CalidadDeDatos> ListaReporte = new ArrayList<CalidadDeDatos>();
		ArrayList<String> Errores = new ArrayList<String>();

		for (int i = 1; i <= rows; ++i) {
			HSSFRow row = sheet.getRow(i);
				try {
					ListaReporte.add(this.procesarExcel(row));
				} catch (Exception e) {
					return false;
				}
		}
		Gson gson = new Gson();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CalidadDeDatosService service = new CalidadDeDatosService();

		CalidadDeDatos reporte = gson.fromJson("{}", CalidadDeDatos.class);

		for (CalidadDeDatos datosInsert : ListaReporte) {
			CalidadDeDatos calidad = new CalidadDeDatos();
			reporte.setDatoDestino(datosInsert.getDatoDestino());
			reporte.setDatoOrigen(datosInsert.getDatoOrigen());
			reporte.setTipo(datosInsert.getTipo());
			List<CalidadDeDatos> listReportes = service.getCalidadDeDatosFiltro(reporte);

			if (listReportes.size() == 0) {
				calidad.setDatoOrigen(datosInsert.getDatoOrigen());
				calidad.setDatoDestino(datosInsert.getDatoDestino());
				calidad.setTipo(datosInsert.getTipo());
				calidad.setProcesado(new BigDecimal(0));
				calidad = service.insertCalidadDeDatos(calidad);
				
			} 
		}
		return true;


	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @param row
	 * @return
	 * @Date: 12 nov. 2021
	 */
	private CalidadDeDatos procesarExcel(HSSFRow row) {
		CalidadDeDatos reporte = new CalidadDeDatos();
		if (row.getCell(0) != null) {
			reporte.setDatoOrigen(row.getCell(0).getStringCellValue());
		}
		if (row.getCell(1) != null) {
			reporte.setDatoDestino(row.getCell(1).getStringCellValue());
		}
		if (row.getCell(2) != null) {
			reporte.setTipo(row.getCell(2).getStringCellValue());
		}
		return reporte;
	}
	
	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @param auditJson
	 * @return
	 * @Date: 12 nov. 2021
	 */
	@POST()
	@Path("/aplicarCalidadDatos")
	@Consumes("text/plain")
	public Response aplicarCalidadDatos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson){
		try{
			json  = new String(com.sun.jersey.core.util.Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
			gson = gsonBuilder.create();	
			CalidadDeDatosNotificacion calidadNotificacion = gson.fromJson(json, CalidadDeDatosNotificacion.class);
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			calidadNotificacion.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
			calidadNotificacion.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
			CalidadDeDatosService service = new CalidadDeDatosService();
			service.aplicarCalidadDatos(calidadNotificacion);
			return Response.status(404).entity("ok").build();
		}catch(Exception z){
			return Response.status(404).entity(z.getMessage()).build();
		}
	}	

}
