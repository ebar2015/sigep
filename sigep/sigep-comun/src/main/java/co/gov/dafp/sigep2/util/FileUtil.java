package co.gov.dafp.sigep2.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.DateFormatConverter;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.SevenZipNativeInitializationException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;

public class FileUtil {
	private static Logger logger = Logger.getInstance(FileUtil.class);
	private static final int BUFFER_SIZE = 1024;
	private static BufferedOutputStream bos = null;
	private static List<String> subDirectoryRoute;

	public static final String SLASH = "/";
	public static final String BACKSLASH = "\\";

	public static Locale locale;

	public static final int SEPARADOR_COMA = FileUtil.COMA.hashCode();
	public static final int SEPARADOR_PUNTO_COMA = FileUtil.PUNTO_COMA.hashCode();
	public static final int SEPARADOR_TABULADOR = FileUtil.TABULADOR.hashCode();
	public static final int SEPARADOR_RETORNO_CARRO = FileUtil.RETORNO_CARRO.hashCode();

	public static final String COMA = ",";
	public static final String PUNTO_COMA = ";";
	public static final String TABULADOR = "\t";
	public static final String RETORNO_CARRO = "\r";

	public static final int EXT_PDF = FileUtil.PDF.hashCode();
	public static final int EXT_DOC = FileUtil.DOC.hashCode();
	public static final int EXT_DOCX = FileUtil.DOCX.hashCode();
	public static final int EXT_XLS = FileUtil.XLS.hashCode();
	public static final int EXT_XLSX = FileUtil.XLSX.hashCode();
	public static final int EXT_RTF = FileUtil.RTF.hashCode();
	public static final int EXT_TIF = FileUtil.TIF.hashCode();
	public static final int EXT_JPG = FileUtil.JPG.hashCode();
	public static final int EXT_TXT = FileUtil.TXT.hashCode();
	public static final int EXT_HTML = FileUtil.HTML.hashCode();
	public static final int EXT_XHTML = FileUtil.XHTML.hashCode();
	public static final int EXT_XML = FileUtil.XML.hashCode();

	public static final String PDF = ".pdf";
	public static final String DOC = ".doc";
	public static final String DOCX = ".docx";
	public static final String XLSX = ".xlsx";
	public static final String XLS = ".xls";
	public static final String RTF = ".rtf";
	public static final String TIF = ".tif";
	public static final String JPG = ".jpg";
	public static final String TXT = ".txt";
	public static final String HTML = ".html";
	public static final String XHTML = ".xhtml";
	public static final String XML = ".xml";

	public static final String PDF_CONTENT_TYPE = "application/pdf";
	public static final String DOC_CONTENT_TYPE = "application/msword";
	public static final String DOCX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	public static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String RTF_CONTENT_TYPE = "image/rtf";
	public static final String TIF_CONTENT_TYPE = "image/tiff";
	public static final String JPG_CONTENT_TYPE = "image/jpg";
	public static final String TEXT_CONTENT_TYPE = "text/html";
	public static final String HTML_CONTENT_TYPE = "text/html";

	private static List<String> paths = null;
	private static List<File> files = null;

	// Estilos para celdas en excel
	public static final String HEADER = "header";
	public static final String INTEGER_NUMBER_CELL = "integer_number_cell";
	public static final String DECIMAL_NUMBER_CELL = "decimal_number_cell";
	public static final String TEXT_CELL = "text_cell";
	public static final String DATE_CELL = "date_cell";

	public static void copiarArchivo(String rutaArchivo, String nombreArchivo, Object contenido, boolean comprimir)
			throws Exception {
		File rutaArchivoCopia = new File(rutaArchivo);
		if (!rutaArchivoCopia.exists()) {
			rutaArchivoCopia.mkdirs();
		}
		File archivo = new File((rutaArchivo != null ? rutaArchivo : "") + nombreArchivo);
		FileWriter fichero = null;
		PrintWriter pw = null;
		InputStream inputStream = null;
		try {
			fichero = new FileWriter(archivo);
			pw = new PrintWriter(fichero);
			if (contenido instanceof InputStream) {
				inputStream = (InputStream) contenido;
				pw.println(inputStream);
			} else {
				pw.println(contenido);
			}
		} catch (Exception e) {
			logger.log().error("final boolean volcadoArchivoDDBBCorrecto(Long idArchivoCargue)", e.getMessage());
			throw new SIGEP2SistemaException(e);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (fichero != null) {
				fichero.close();
			}
			if (pw != null) {
				pw.close();
			}
			if (comprimir) {
				String archivoComprimir = rutaArchivo + nombreArchivo;
				Zip(archivoComprimir, archivoComprimir);
			}
		}
	}

	/**
	 * Funcion mover archivo
	 * 
	 * @param origen
	 * @param destino
	 * @return Path del archivo destino
	 */
	public final static Path moverArchivo(String origen, String destino) {
		String msg = "final static void moverArchivo(String origen, String destino)";
		String from = origen.replace("/", FileUtil.getFileSeparator());
		String to = destino.replace("/", FileUtil.getFileSeparator());

		Path FROM = Paths.get(from);
		Path TO = Paths.get(to);

		File folderTO = TO.toFile();
		if (!folderTO.exists()) {
			folderTO.mkdirs();
		}

		// sobreescribir el fichero de destino, si existe, y copiar
		// los atributos, incluyendo los permisos rwx
		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
		try {
			Files.move(FROM, TO, options);
		} catch (IOException e) {
			try {
				Files.copy(FROM, TO, options);
			} catch (IOException e1) {
				logger.error(msg, e1);
			}
			logger.error(msg, e);
		}
		return TO;
	}

	public final static void Zip(String pFile, String pZipFile) throws Exception {
		pZipFile = pZipFile + ConfigurationBundleConstants.getExtensionZip();
		// objetos en memoria
		FileInputStream fis = null;
		FileOutputStream fos = null;
		ZipOutputStream zipos = null;

		byte[] buffer = new byte[BUFFER_SIZE];
		try {
			fis = new FileInputStream(pFile);
			fos = new FileOutputStream(pZipFile);
			zipos = new ZipOutputStream(fos);

			ZipEntry zipEntry = new ZipEntry(pFile);
			zipos.putNextEntry(zipEntry);
			int len = 0;
			while ((len = fis.read(buffer, 0, BUFFER_SIZE)) != -1) {
				zipos.write(buffer, 0, len);
			}
			zipos.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			zipos.close();
			fis.close();
			fos.close();
		}
	}

	public final static void UnZip(String pZipFile, String pFile) throws Exception {
		pZipFile = pZipFile + ConfigurationBundleConstants.getExtensionZip();
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		ZipInputStream zipis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(pZipFile);
			zipis = new ZipInputStream(new BufferedInputStream(fis));
			if (zipis.getNextEntry() != null) {
				int len = 0;
				byte[] buffer = new byte[BUFFER_SIZE];
				fos = new FileOutputStream(pFile);
				bos = new BufferedOutputStream(fos, BUFFER_SIZE);

				while ((len = zipis.read(buffer, 0, BUFFER_SIZE)) != -1)
					bos.write(buffer, 0, len);
				bos.flush();
			} else {
				logger.log().error("final void UnZip(String pZipFile, String pFile)", MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_ARCHIVO_NO_EXISTE));
				throw new Exception(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_ARCHIVO_NO_EXISTE));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (bos != null)
				bos.close();
			if (zipis != null)
				zipis.close();
			if (fos != null)
				fos.close();
			if (fis != null)
				fis.close();
		}
	}

	/**
	 * CountOfItems. Cuenta el numero de elementos contenidos en el archivo
	 * comprimido
	 * 
	 * @param pZipFile.
	 *            Ruta y nombre del archivo comprimido @return. Devuelve el
	 *            numero de elementos encontrados.
	 * @throws Exception.
	 *             Se produce si no encuentra el archivo comprimido o si est�
	 *             corrupto
	 */
	public final static int CountOfItems(String fileZip, String dirFileZip) throws Exception {
		final String msg = "final static int CountOfItems(String fileZip, String dirFileZip)";
		int contadorArchivos = 0;
		File rutaArchivo = new File(dirFileZip + File.separator + fileZip);
		if (!rutaArchivo.exists())
			throw new IOException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_ARCHIVO_NO_EXISTE));

		try {
			SevenZip.initSevenZipFromPlatformJAR(); // Inicializing
													// 7-Zip-JBinding library

			RandomAccessFile randomAccessFile = null;
			IInArchive inArchive = null;
			try {
				randomAccessFile = new RandomAccessFile(fileZip, "r");
				inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
				contadorArchivos = inArchive.getNumberOfItems();
			} catch (Exception e) {
				throw new Exception("Error occurs: " + e);
			} finally {
				if (inArchive != null) {
					try {
						inArchive.close();
					} catch (SevenZipException e) {
						throw e;
					}
				}
				if (randomAccessFile != null) {
					try {
						randomAccessFile.close();
					} catch (IOException e) {
						throw e;
					}
				}
			}
		} catch (SevenZipNativeInitializationException e) {
			logger.error(msg, e);
		}

		return contadorArchivos;
	}

	/**
	 * CompressZipEncrypt. Comprime una lista de elmentos en un archivo ZIP
	 * protegido con contrase�a.
	 * 
	 * @param listFile.
	 *            Lista de archivos a comprimir.
	 * @param pZipFile.
	 *            Nombre del archivo ZIP
	 * @param password.
	 *            Contrase�a para la protecci�n.
	 * @throws Exception.
	 *             Excepciones producidas.
	 */
	public final static void CompressZipEncrypt(List<String> listFile, String pZipFile, String password)
			throws Exception {
		// pZipFile = pZipFile + ConfigurationBundleConstants.getExtensionZip();
		File file = null;
		File fileZip = null;
		ArrayList<File> list = new ArrayList<File>();

		try {
			fileZip = new File(pZipFile);
			ZipFile zipFile = new ZipFile(pZipFile);
			if (listFile.size() >= 1) {
				for (int i = 0; i < listFile.size(); i++) {
					file = new File(listFile.get(i));
					if (file.exists())
						list.add(file);
					else {
						throw new Exception(MessagesBundleConstants
								.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_ARCHIVO_NO_EXISTE));
					}
				}

				ZipParameters parametros = new ZipParameters();
				parametros.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
				parametros.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
				parametros.setEncryptFiles(true);
				parametros.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
				parametros.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
				parametros.setPassword(password);
				if (!fileZip.exists())
					zipFile.createZipFile(list, parametros);
				else
					zipFile.addFiles(list, parametros);
			}
		} catch (ZipException e) {
			throw e;
		}
	}

	/**
	 * ExtractZipEncrypt. Descomprime archivos ZIP protegidos con contrase�a
	 * 
	 * @param pZipFile.
	 *            Ruta y nombre del Archivo ZIP
	 * @param password.
	 *            Contrase�a requerida para la descompresi�n.
	 * @param rutaUnzip.
	 *            Ruta en la cual se descomprimiran los archivos.
	 * @throws Exception.
	 *             Excepciones producidas.
	 */
	public final static void ExtractZipEncrypt(String fileZip, String dirFileZip, String extFileZip, String dirUnzip,
			String password) throws Exception {
		String fileZipTemp = fileZip;
		String destination = dirUnzip + FileUtil.getFileSeparator() + fileZipTemp; // Aqui
																					// va
																					// la
																					// ruta
		// que
		// se configura por
		// par�metros
		if (!fileZipTemp.contains(extFileZip)) {
			fileZipTemp = fileZipTemp + extFileZip;
		}
		try {
			ZipFile zipFile = new ZipFile(dirFileZip + FileUtil.getFileSeparator() + fileZipTemp);
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}

			UnzipParameters param = new UnzipParameters();
			param.setIgnoreArchiveFileAttribute(true);
			zipFile.extractAll(destination, param);
		} catch (ZipException e) {
			throw e;
		}
	}

	public synchronized final static void ExtractSevenZip(String fileZip, String dirFileZip, String dirUnzip,
			String password) throws IOException {
		final String msg = "final static void ExtractSevenZip(String fileZip, String dirFileZip, String dirUnzip, String password)";
		FileInputStream fis = null;
		ZipInputStream zipis = null;
		FileOutputStream fos = null;
		File rutaUnzip;

		if (FileUtil.getSubDirectoryRoute() == null)
			FileUtil.setSubDirectoryRoute(new ArrayList<String>());

		if (fileZip == null) {
			throw new IOException("El Nombre del Archivo no puede ser Nulo.");
		}

		String dirUnzipFull = "";

		try {
			SevenZip.initSevenZipFromPlatformJAR();
			RandomAccessFile randomAccessFile = new RandomAccessFile(dirFileZip + fileZip, "r");

			IInArchive inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
			ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
			rutaUnzip = new File(dirUnzip);

			FileUtils.forceMkdir(rutaUnzip);

			for (ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
				final int[] hash = new int[] { 0 };
				if (!item.isFolder()) {
					ExtractOperationResult result;
					dirUnzipFull = dirUnzip + item.getPath();

					final long[] sizeArray = new long[1];
					fos = new FileOutputStream(dirUnzipFull);
					bos = new BufferedOutputStream(fos, BUFFER_SIZE);

					result = item.extractSlow(new ISequentialOutStream() {
						public int write(byte[] data) throws SevenZipException {
							hash[0] ^= Arrays.hashCode(data); // Consume data
							sizeArray[0] += data.length;
							try {
								bos.write(data);
							} catch (IOException e) {
								logger.error(msg, e);
							}
							return data.length; // Return amount of consumed
												// data
						}
					}, password);

					bos.flush();
					bos.close();
					if (result == ExtractOperationResult.OK) {
						logger.info("Descompresion Satisfactoria.");
					} else {
						if (rutaUnzip.exists() && rutaUnzip.isDirectory()) {
							for (String archivo : rutaUnzip.list()) {
								File fil = new File(rutaUnzip + File.separator + archivo);
								fil.delete();
							}
							FileUtils.deleteDirectory(rutaUnzip);
						}
						throw new IOException("Error al Extraer el archivo " + result);
					}
				} else {
					dirUnzipFull = dirUnzip + item.getPath();
					if (!existsDirectory(dirUnzipFull))
						FileUtils.forceMkdir(new File(dirUnzipFull));
					FileUtil.getSubDirectoryRoute().add(dirUnzipFull);
				}
			}
			// simpleInArchive.close();
			// inArchive.close();
			randomAccessFile.close();
		} catch (SevenZipNativeInitializationException | SevenZipException | FileNotFoundException e) {
			logger.error(msg, e);
		} finally {
			if (bos != null)
				bos.close();
			if (zipis != null)
				zipis.close();
			if (fos != null)
				fos.close();
			if (fis != null)
				fis.close();

		}

	}

	/**
	 * ListFiles. Obtiene el listado de los archivos contenidos dentro del
	 * archivo comprimido.
	 * 
	 * @param fileZip.
	 *            Nombre del archivo comprimido.
	 * @param dirFileZip.
	 *            Ruta del archivo comprimido. @return. Devuelve una lista con
	 *            los nombres de los archivos contenidos en comprimido.
	 * @throws IOException
	 */
	public final static List<String> ListItemZip(String fileZip) throws IOException {
		List<String> listaItem = new ArrayList<>();
		File rutaArchivo = new File(fileZip);

		if (!rutaArchivo.exists())
			throw new IOException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_ARCHIVO_NO_EXISTE));
		try {
			SevenZip.initSevenZipFromPlatformJAR();
			RandomAccessFile randomAccessFile = new RandomAccessFile(fileZip, "r");

			IInArchive inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
			ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
			for (ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
				if (!item.isFolder()) {
					listaItem.add(item.getPath());
				}
			}
		} catch (SevenZipNativeInitializationException | SevenZipException | FileNotFoundException e) {
			e.printStackTrace();
		}

		return listaItem;
	}

	public final static List<String> listFilesInDirString(String dir) throws IOException {
		File ruta = new File(dir);
		ruta.mkdirs();
		paths = null;
		files = null;
		if (ruta.exists() && ruta.isDirectory()) {
			return FileUtil.obtenerListaArchivos(ruta);
		} else {
			throw new IOException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_RUTA_ARCHIVO_CARGADO_NO_EXISTE));
		}
	}

	public final static List<File> listFilesInDir(String dir) throws IOException {
		File ruta = new File(dir);
		ruta.mkdirs();
		paths = null;
		files = null;

		if (ruta.exists() && ruta.isDirectory()) {
			FileUtil.obtenerListaArchivos(ruta);
			return FileUtil.getFiles();
		} else {
			throw new IOException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_RUTA_ARCHIVO_CARGADO_NO_EXISTE));
		}
	}

	public static ResourceBundle getBundle(String bundleName) {
		if (FileUtil.locale == null) {
			FileUtil.locale = Locale.ROOT;
		}
		return ResourceBundle.getBundle(bundleName, FileUtil.locale);
	}

	public static Properties convertResourceBundleToProperties(ResourceBundle resource) {
		Properties properties = new Properties();

		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			properties.put(key, resource.getString(key));
		}

		return properties;
	}

	public static List<String> obtenerListaArchivos(File argFile) {
		if (paths == null) {
			paths = new LinkedList<>();
		}
		if (files == null) {
			files = new LinkedList<>();
		}
		File[] lista = argFile.listFiles();
		if (lista != null) {
			for (File elemento : lista) {
				if (elemento.isDirectory()) {
					obtenerListaArchivos(elemento);
				} else if (!files.contains(elemento)) {
					paths.add(elemento.getName());
					files.add(elemento);
				}
			}
		}
		return paths;
	}

	public static List<String> getPaths() {
		return paths;
	}

	public static void setPaths(List<String> paths) {
		FileUtil.paths = paths;
	}

	public static List<File> getFiles() {
		return files;
	}

	public static void setFiles(List<File> files) {
		FileUtil.files = files;
	}

	public static Map<String, CellStyle> createStylesExcel(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<>();
		CellStyle style;

		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 12);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(titleFont);
		style.setWrapText(false);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
		styles.put(FileUtil.HEADER, style);

		Font cellFont = wb.createFont();
		cellFont.setFontHeightInPoints((short) 10);
		cellFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		style.setFont(cellFont);
		style.setWrapText(false);
		style.setDataFormat(wb.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(3)));
		styles.put(FileUtil.INTEGER_NUMBER_CELL, style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		style.setFont(cellFont);
		style.setWrapText(false);
		style.setDataFormat(wb.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(4)));
		styles.put(FileUtil.DECIMAL_NUMBER_CELL, style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		style.setFont(cellFont);
		style.setWrapText(false);
		style.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));
		styles.put(FileUtil.TEXT_CELL, style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		style.setFont(cellFont);
		style.setWrapText(false);
		style.setDataFormat(wb.createDataFormat()
				.getFormat(DateFormatConverter.convert(Locale.getDefault(), DateUtils.FECHA_FORMATO)));
		styles.put(FileUtil.DATE_CELL, style);
		return styles;
	}

	public static boolean eliminarArchivo(Object file) {
		String msg = "static boolean deleteFile(Object file)";
		if (file == null) {
			return false;
		}
		File fileDelete = null;
		if (file instanceof File) {
			fileDelete = (File) file;
		} else if (file instanceof String) {
			fileDelete = new File(file.toString());
		} else {
			logger.error(msg, file + " No soportado");
			return false;
		}
		boolean fileDeleted = fileDelete.exists() && fileDelete.delete();
		if (fileDeleted) {
			logger.info(msg, "Archivo borrado : " + fileDelete.getName());
		} else {
			logger.warn(msg, "Archivo NO encontrado o No borrado : " + fileDelete.getName());
		}
		return fileDeleted;
	}

	public static boolean existsDirectory(String url) {
		boolean exists = false;
		File file = new File(url);
		if (file.exists())
			exists = true;

		return exists;
	}

	public synchronized static void setSubDirectoryRoute(List<String> subDirectoryRoute) {
		FileUtil.subDirectoryRoute = subDirectoryRoute;
	}

	public synchronized static List<String> getSubDirectoryRoute() {
		return subDirectoryRoute;
	}

	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}
}
