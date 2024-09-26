package co.gov.dafp.sigep2.converter;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.SeparadorCsvCaracter;
import co.gov.dafp.sigep2.util.xml.elemento.Extension;

public class ExcelToCSVConverter {
	private static Logger logger = Logger.getInstance(ExcelToCSVConverter.class);

	public static File convertFromXlsx(File inputFile, SeparadorCsvCaracter caracter) {
		String caracterSeparador = caracter.value();
		// For storing data into CSV files
		StringBuffer cellValue = new StringBuffer();
		try {
			File outputFile = new File(
					inputFile.getAbsolutePath().replace(Extension.XLSX.value(), Extension.CSV.value()));
			FileOutputStream fos = new FileOutputStream(outputFile);

			// Get the workbook instance for XLSX file
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(inputFile));

			// Get first sheet from the workbook
			XSSFSheet sheet = wb.getSheetAt(0);

			Row row;
			Cell cell;

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				// For each row, iterate through each columns

				// Validación de que todas las celdas de la fila tengan
				// información, de lo contrario se omitirá dicha fila
				Iterator<Cell> cellIteratorValidator = row.cellIterator();
				boolean algunaTieneDatos = false;
				while (cellIteratorValidator.hasNext()) {
					cell = cellIteratorValidator.next();
					String value = null;
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						value = String.valueOf(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						value = String.valueOf(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						value = String.valueOf(cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = String.valueOf(cell);
					}

					if (value != null && !value.trim().isEmpty()) {
						algunaTieneDatos = true;
						break;
					}
				}

				if (!algunaTieneDatos) {
					continue;
				}

				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						cellValue.append(cell.getBooleanCellValue() + caracterSeparador);
						break;
					case Cell.CELL_TYPE_NUMERIC:
						DecimalFormat num = new DecimalFormat("####0");
						String valor = num.format(new Double(cell.getNumericCellValue()));
						cellValue.append(valor + caracterSeparador);
						break;
					case Cell.CELL_TYPE_STRING:
						cellValue.append(cell.getStringCellValue() + caracterSeparador);
						break;
					case Cell.CELL_TYPE_BLANK:
						cellValue.append(" " + caracterSeparador);
						break;
					default:
						cellValue.append(cell + caracterSeparador);
					}
				}
				cellValue.append(SeparadorCsvCaracter.RETORNO_CARRO.value());
			}

			fos.write(cellValue.toString().getBytes());
			fos.close();
			return outputFile;
		} catch (FileNotFoundException e) {
			logger.error("static File convertFromXlsx(File inputFile, SeparadorCsvCaracter caracter)", e);
		} catch (Exception e) {
			logger.error("static File convertFromXlsx(File inputFile, SeparadorCsvCaracter caracter)", e);
		}
		return inputFile;
	}

	public static File convertFromXls(File inputFile, SeparadorCsvCaracter caracter) {
		String caracterSeparador = caracter.value();
		// For storing data into CSV files
		StringBuffer cellDData = new StringBuffer();
		try {
			File outputFile = new File(
					inputFile.getAbsolutePath().replace(Extension.XLS.value(), Extension.CSV.value()));
			FileOutputStream fos = new FileOutputStream(outputFile);

			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(inputFile));
			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);
			Cell cell;
			Row row;

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();

				// Validación de que todas las celdas de la fila tengan
				// información, de lo contrario se omitirá dicha fila
				Iterator<Cell> cellIteratorValidator = row.cellIterator();
				boolean algunaTieneDatos = false;
				while (cellIteratorValidator.hasNext()) {
					cell = cellIteratorValidator.next();
					String value = null;
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						value = String.valueOf(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						value = String.valueOf(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						value = String.valueOf(cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = String.valueOf(cell);
					}

					if (value != null && !value.trim().isEmpty()) {
						algunaTieneDatos = true;
						break;
					}
				}

				if (!algunaTieneDatos) {
					continue;
				}

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();

					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_BOOLEAN:
						cellDData.append(cell.getBooleanCellValue() + caracterSeparador);
						break;

					case Cell.CELL_TYPE_NUMERIC:
						DecimalFormat num = new DecimalFormat("####0");
						String valor = num.format(new Double(cell.getNumericCellValue()));
						cellDData.append(valor + caracterSeparador);
						break;

					case Cell.CELL_TYPE_STRING:
						cellDData.append(cell.getStringCellValue() + caracterSeparador);
						break;

					case Cell.CELL_TYPE_BLANK:
						cellDData.append(" " + caracterSeparador);
						break;

					default:
						cellDData.append(cell + caracterSeparador);
					}
				}
				cellDData.append(SeparadorCsvCaracter.RETORNO_CARRO.value());
			}

			fos.write(cellDData.toString().getBytes());
			fos.close();
			return outputFile;
		} catch (FileNotFoundException e) {
			logger.error("static File convertFromXls(File inputFile, SeparadorCsvCaracter caracter)", e);
		} catch (IOException e) {
			logger.error("static File convertFromXls(File inputFile, SeparadorCsvCaracter caracter)", e);
		}
		return inputFile;
	}
}
