package co.gov.dafp.sigep2.util.sistema;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.transaction.NotSupportedException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.entity.ReporteDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.factoria.ReporteFactoria;
import co.gov.dafp.sigep2.interfaces.IEnvioCorreoLocal;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.util.NumeroUtil;
import co.gov.dafp.sigep2.util.Parametro;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.Columna;
import co.gov.dafp.sigep2.util.xml.reporte.config.CuentaCorreo;
import co.gov.dafp.sigep2.util.xml.reporte.config.FormatoReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.MallaReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.Notificacion;
import co.gov.dafp.sigep2.util.xml.reporte.config.Registro;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoDato;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoRegistro;

public abstract class ProcesoReporte {
	protected Logger logger = Logger.getInstance(ProcesoReporte.class);

	protected IEnvioCorreoLocal envioCorreo;
	protected ReporteFactoria reporteFactoria;
	protected UsuarioDTO usuarioReporte;
	protected List<Parametro> parametros;

	protected String validacion;

	protected ReporteDTO reporte;

	protected XmlReporte xml;

	protected MallaReporte reporteConfiguracion;

	protected List<co.gov.dafp.sigep2.util.Registro> resultados;

	protected List<String> adjuntos;

	protected String rutaArchivoSalida;

	public int first = 0;
	public int pageSize = 0;

	public int page = 0;

	protected ProcesoReporte() {
		super();
	}

	public ReporteFactoria getReporteFactoria() {
		return reporteFactoria;
	}

	public void setReporteFactoria(ReporteFactoria reporteFactoria) {
		this.reporteFactoria = reporteFactoria;
	}

	public void setEnvioCorreo(IEnvioCorreoLocal envioCorreo) {
		this.envioCorreo = envioCorreo;
	}

	public UsuarioDTO getUsuarioReporte() {
		return usuarioReporte;
	}

	public void setUsuarioReporte(UsuarioDTO usuarioReporte) {
		this.usuarioReporte = usuarioReporte;
	}

	public ReporteDTO getReporte() {
		return reporte;
	}

	public void setReporte(ReporteDTO reporte) {
		this.reporte = reporte;
	}

	public XmlReporte getXml() {
		return xml;
	}

	public void setXml(XmlReporte xml) {
		this.xml = xml;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	public List<co.gov.dafp.sigep2.util.Registro> getResultados() {
		return resultados;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<String> getAdjuntos() {
		if (adjuntos == null) {
			adjuntos = new LinkedList<>();
		}
		Collections.sort(this.adjuntos, new Comparator<String>() {
			@Override
			public int compare(String f1, String f2) {
				return f1.compareTo(f2);
			}
		});
		return adjuntos;
	}

	/**
	 * Cargar la configuración para el archivo seleccionado
	 * 
	 * @throws R2PException
	 *
	 */
	public void cargarConfiguracionXML() throws Exception {
		if (this.xml == null) {
			xml = XmlReporte.getEstructura(XmlReporte.getXml(this.xml.getPlantillaXML(), ""));
		}
		if (xml.getMallaReporte().size() == 1) {
			this.reporteConfiguracion = xml.getMallaReporte().get(0);
		}
	}

	public abstract String validarFiltrosReporte();

	public final void generarReporte(Registro tipoRegistro, String rutaArchivoSalida, boolean generarParaCorreo)
			throws Exception {
		String replaceReporte = " ) reporte";
		this.rutaArchivoSalida = rutaArchivoSalida;
		String sqlQuery = tipoRegistro.getSQL().getSentencia().replace(replaceReporte,
				replaceReporte + this.xml.getOrdenamiento());
		Map<Object, Object> parametros = new HashMap<>();

		sqlQuery = sqlQuery.replace("select * from (", "").replace(") reporte", "");

		logger.info("final void generarReporte(String rutaArchivoSalida)", sqlQuery, parametros);
		List<Object[]> list = reporteFactoria.ejecutarQuery(sqlQuery, parametros, first, pageSize);
		logger.info("final void generarReporte(String rutaArchivoSalida)", "Total registros consulta", list.size());

		resultados = new LinkedList<>();
		int index = 1;
		try {
			for (Object[] columnas : list) {
				List<Parametro> registros = new LinkedList<>();
				for (Object item : columnas) {
					Parametro contenido = new Parametro(item, item);
					registros.add(contenido);
				}

				co.gov.dafp.sigep2.util.Registro registro = new co.gov.dafp.sigep2.util.Registro();
				registro.setId(index++);
				registro.setItem(registros);

				resultados.add(registro);
			}
		} catch (ClassCastException e) {
			for (Object columna : list) {
				List<Parametro> registros = new LinkedList<>();

				Parametro contenido = new Parametro(columna, columna);
				registros.add(contenido);

				co.gov.dafp.sigep2.util.Registro registro = new co.gov.dafp.sigep2.util.Registro();
				registro.setId(index++);
				registro.setItem(registros);

				resultados.add(registro);
			}
		}

		if (generarParaCorreo && !xml.getNotificacion().isEmpty()) {
			this.generarSalida();
			Notificacion notificacion = xml.getNotificacion().get(0);
			this.enviarNotificacion(notificacion);
		}
	}

	protected final List<CuentaCorreo> getA(List<CuentaCorreo> cuentasCorreo) {
		List<CuentaCorreo> a = new ArrayList<>();
		for (CuentaCorreo cuenta : cuentasCorreo) {
			if (cuenta.getBandeja().value().equals(XmlReporte.A)) {
				a.add(cuenta);
			}
		}
		return a;
	}

	protected final List<CuentaCorreo> getCC(List<CuentaCorreo> cuentasCorreo) {
		List<CuentaCorreo> cc = new ArrayList<>();
		for (CuentaCorreo cuenta : cuentasCorreo) {
			if (cuenta.getBandeja().value().equals(XmlReporte.CC)) {
				cc.add(cuenta);
			}
		}
		return cc;
	}

	protected final List<CuentaCorreo> getCCO(List<CuentaCorreo> cuentasCorreo) {
		List<CuentaCorreo> cco = new ArrayList<>();
		for (CuentaCorreo cuenta : cuentasCorreo) {
			if (cuenta.getBandeja().value().equals(XmlReporte.CCO)) {
				cco.add(cuenta);
			}
		}
		return cco;
	}

	protected final void enviarNotificacion(Notificacion validacion) throws Exception {
		String msg = "final void enviarNotificacion(Validacion validacion)";
		if (validacion.isNotificar()) {
			logger.log().info(msg, "Enviado correo notificación");
			envioCorreo.enviarMail(validacion.getAsunto(),
					HTMLUtil.abreParrafo + validacion.getCuerpo() + HTMLUtil.espacioEnBlanco + HTMLUtil.cierraParrafo
							+ (this.validacion != null ? this.validacion : ""),
					validacion.getDesde(), this.getA(validacion.getCuentaCorreo()), adjuntos,
					this.getCC(validacion.getCuentaCorreo()));
		}
	}

	public void generarSalida() throws NotSupportedException {
		if (adjuntos == null)
			adjuntos = new LinkedList<>();
		if (FormatoReporte.TTL_REPORTES_FORMATO_EXCEL.equals(xml.getTipoPlantilla())) {
			this.generarExcel();
		} else if (FormatoReporte.TTL_REPORTES_FORMATO_PDF.equals(xml.getTipoPlantilla())) {
			this.generarPdf();
		} else if (FormatoReporte.TTL_REPORTES_FORMATO_CSV.equals(xml.getTipoPlantilla())) {
			this.generarCsv();
		}
	}

	protected void generarExcel() throws NotSupportedException {
		String tituloLog = "void generarExcel()";
		String pathname = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_ARCHIVO_REPORTE);
		File directorioSalida = new File(pathname);
		if (directorioSalida.mkdirs()) {
			logger.log().info(tituloLog, "'" + pathname + "' creado");
		}
		pathname = pathname + reporteConfiguracion.getNombre().trim() + xml.getTipoPlantilla().value().toLowerCase();
		try {
			// Blank workbook
			XSSFWorkbook workbook = new XSSFWorkbook();
			// Create a blank sheet
			XSSFSheet sheet = workbook.createSheet(reporteConfiguracion.getNombre().trim());
			// This data needs to be written (Object[])
			Map<Object, Object[]> data = new TreeMap<>();
			Map<Object, Object> parameters = new HashMap<>();
			int index = 2;
			for (Registro registro : this.xml.getRegistro()) {
				if (registro.isContieneSentenciaSQL()) {
					List<Object[]> records = null;
					parameters.put("archivo_cargue_id", this.parametros.get(0).getValue());
					if (TipoRegistro.CABECERA.equals(registro.getTipoRegistro())
							|| TipoRegistro.PIE.equals(registro.getTipoRegistro())) {
						records = this.reporteFactoria.executeNativeQuery(registro.getSQL().getSentencia(), parameters,
								1);
					} else {
						records = this.reporteFactoria.executeNativeQuery(registro.getSQL().getSentencia(), parameters,
								0);
					}
					String[] cabecera = new String[registro.getColumna().size()];
					Object[] detalle = null;
					int contRecords = 1;
					for (Object record : records) {
						contRecords++;
						detalle = new Object[registro.getColumna().size()];
						logger.log().debug(tituloLog, record);
						for (Columna columna : registro.getColumna()) {
							int i = columna.getId().intValue() - 1;
							cabecera[i] = FileUtil.HEADER + columna.getEtiquetaColumna();
							Object valor = null;
							if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DECIMAL.equals(columna.getTipoDato())) {
								try {
									valor = (BigDecimal) record;
								} catch (ClassCastException e) {
									valor = BigDecimal.valueOf(Double.valueOf(((Object[]) record)[i].toString()));
								}
								if (columna.getMascara() != null && !columna.getMascara().isEmpty()) {
									valor = NumeroUtil.formatoMoneda((BigDecimal) valor, columna.getMascara());
								}
								if (valor == null) {
									valor = "";
								}
								valor = FileUtil.DECIMAL_NUMBER_CELL + String.valueOf(valor);
							} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_INTEGER.equals(columna.getTipoDato())) {
								BigDecimal valorBigDecimal = null;
								try {
									valorBigDecimal = (BigDecimal) record;
								} catch (ClassCastException e) {
									try {
										valorBigDecimal = BigDecimal
												.valueOf(Long.valueOf(((Object[]) record)[i].toString()));
										valor = valorBigDecimal != null ? valorBigDecimal.intValue() : null;
									} catch (ClassCastException e1) {
										valor = Integer.valueOf(((Object[]) record)[i].toString());
									}
								}
								if (valor == null) {
									valor = "";
								}
								valor = FileUtil.INTEGER_NUMBER_CELL + String.valueOf(valor);
							} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_STRING.equals(columna.getTipoDato())) {
								try {
									valor = (String) record;
								} catch (ClassCastException e) {
									valor = (String) ((Object[]) record)[i];
								}
								if (valor == null) {
									valor = "";
								}
								valor = FileUtil.TEXT_CELL + String.valueOf(valor);
							} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE.equals(columna.getTipoDato())) {
								try {
									valor = DateUtils.formatearACadena((Date) record, columna.getMascara());
								} catch (ClassCastException e) {
									valor = DateUtils.formatearACadena((Date) ((Object[]) record)[i],
											columna.getMascara());
								}
								if (valor == null) {
									valor = "";
								}
								valor = FileUtil.DATE_CELL + String.valueOf(valor);
							} else {
								try {
									valor = record;
								} catch (ClassCastException e) {
									valor = ((Object[]) record)[i];
								}
								if (valor == null) {
									valor = "";
								}
								valor = FileUtil.TEXT_CELL + String.valueOf(valor);
							}
							detalle[i] = valor;
							i++;
						}
						data.put(index + "", detalle);
						index++;
					}
					data.put((index - contRecords) + "", cabecera);
					index = index + 2;
				}
			}

			int rownum = 0;
			// int size = data.values().size();
			for (Object[] objArr : data.values()) {
				if (rownum % 1000 == 0) {
					System.gc();
				}
				// create a row of excelsheet
				Row row = sheet.createRow(rownum++);
				// logger.info(tituloLog, "Escribiendo línea " + rownum + "/" +
				// size + ". En '" + pathname + "'");

				// get object array of prerticuler key

				int cellnum = 0;

				for (Object obj : objArr) {
					Cell cell = row.createCell(cellnum++);

					CellStyle style = null;
					String valueString = obj.toString();
					if (valueString.startsWith(FileUtil.HEADER)) {
						style = FileUtil.createStylesExcel(workbook).get(FileUtil.HEADER);
						obj = valueString.replace(FileUtil.HEADER, "");
					} else if (valueString.startsWith(FileUtil.DECIMAL_NUMBER_CELL)) {
						style = FileUtil.createStylesExcel(workbook).get(FileUtil.DECIMAL_NUMBER_CELL);
						obj = valueString.replace(FileUtil.DECIMAL_NUMBER_CELL, "");
					} else if (valueString.startsWith(FileUtil.INTEGER_NUMBER_CELL)) {
						style = FileUtil.createStylesExcel(workbook).get(FileUtil.INTEGER_NUMBER_CELL);
						obj = valueString.replace(FileUtil.INTEGER_NUMBER_CELL, "");
					} else if (valueString.startsWith(FileUtil.DATE_CELL)) {
						style = FileUtil.createStylesExcel(workbook).get(FileUtil.DATE_CELL);
						obj = valueString.replace(FileUtil.DATE_CELL, "");
					} else if (valueString.startsWith(FileUtil.TEXT_CELL)) {
						style = FileUtil.createStylesExcel(workbook).get(FileUtil.TEXT_CELL);
						obj = valueString.replace(FileUtil.TEXT_CELL, "");
					}
					cell.setCellStyle(style);

					if (obj instanceof String) {
						cell.setCellValue((String) obj);
					} else if (obj instanceof Integer) {
						cell.setCellValue((Integer) obj);
					}
				}
			}
			// Write the workbook in file system
			File salida = new File(pathname);
			FileOutputStream out = new FileOutputStream(salida);
			workbook.write(out);
			out.close();
			logger.log().info(tituloLog, "'" + pathname + "' generado");
			FileUtil.Zip(pathname, pathname);
			String salidaZip = pathname + ConfigurationBundleConstants.getExtensionZip();
			adjuntos.add(salidaZip);
			FileUtil.eliminarArchivo(salida);
			page++;
		} catch (Exception e) {
			logger.log().error(tituloLog, e);
		}
	}

	protected void generarPdf() throws NotSupportedException {
		throw new NotSupportedException();
	}

	protected void generarCsv() throws NotSupportedException {
		throw new NotSupportedException();
	}

	protected void generarTxt() throws NotSupportedException {
		throw new NotSupportedException();
	}

}
