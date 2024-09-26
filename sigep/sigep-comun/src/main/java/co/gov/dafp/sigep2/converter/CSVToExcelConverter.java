package co.gov.dafp.sigep2.converter;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import co.gov.dafp.sigep2.util.xml.SeparadorCsvCaracter;
import co.gov.dafp.sigep2.util.xml.elemento.Extension;

public class CSVToExcelConverter {
	@SuppressWarnings("deprecation")
	public static void convertXlsToCSV(File inputFile, SeparadorCsvCaracter caracter) throws IOException {
		ArrayList<ArrayList<String>> arList = null;
		ArrayList<String> al = null;
		String thisLine;
		FileInputStream fis = new FileInputStream(inputFile);
		DataInputStream myInput = new DataInputStream(fis);
		arList = new ArrayList<ArrayList<String>>();
		while ((thisLine = myInput.readLine()) != null) {
			al = new ArrayList<String>();
			String strar[] = thisLine.split(caracter.value());
			for (int j = 0; j < strar.length; j++) {
				al.add(strar[j]);
			}
			arList.add(al);
		}
		myInput.close();

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("new sheet");
			for (int k = 0; k < arList.size(); k++) {
				ArrayList<String> ardata = (ArrayList<String>) arList.get(k);
				HSSFRow row = sheet.createRow((short) 0 + k);
				for (int p = 0; p < ardata.size(); p++) {
					HSSFCell cell = row.createCell((short) p);
					String data = ardata.get(p);
					if (data.startsWith("=")) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						data = data.replaceAll("\"", "");
						data = data.replaceAll("=", "");
						cell.setCellValue(data);
					} else if (data.startsWith("\"")) {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(data);
					} else {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(data);
					}
				}
				System.out.println();
			}
			File outputFile = new File(
					inputFile.getAbsolutePath().replace(Extension.XLS.value(), Extension.CSV.value()));
			FileOutputStream fileOut = new FileOutputStream(outputFile);
			hwb.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void convertXlsxToCSV(File inputFile, SeparadorCsvCaracter caracter) throws IOException {
		ArrayList<ArrayList<String>> arList = null;
		ArrayList<String> al = null;
		String thisLine;
		FileInputStream fis = new FileInputStream(inputFile);
		DataInputStream myInput = new DataInputStream(fis);
		arList = new ArrayList<ArrayList<String>>();
		while ((thisLine = myInput.readLine()) != null) {
			al = new ArrayList<String>();
			String strar[] = thisLine.split(caracter.value());
			for (int j = 0; j < strar.length; j++) {
				al.add(strar[j]);
			}
			arList.add(al);
		}
		myInput.close();

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("new sheet");
			for (int k = 0; k < arList.size(); k++) {
				ArrayList<String> ardata = (ArrayList<String>) arList.get(k);
				HSSFRow row = sheet.createRow((short) 0 + k);
				for (int p = 0; p < ardata.size(); p++) {
					HSSFCell cell = row.createCell((short) p);
					String data = ardata.get(p);
					if (data.startsWith("=")) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						data = data.replaceAll("\"", "");
						data = data.replaceAll("=", "");
						cell.setCellValue(data);
					} else if (data.startsWith("\"")) {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(data);
					} else {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(data);
					}
				}
				System.out.println();
			}
			File outputFile = new File(
					inputFile.getAbsolutePath().replace(Extension.XLSX.value(), Extension.CSV.value()));
			FileOutputStream fileOut = new FileOutputStream(outputFile);
			hwb.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
