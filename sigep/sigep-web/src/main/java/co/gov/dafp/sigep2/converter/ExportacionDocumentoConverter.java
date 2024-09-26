package co.gov.dafp.sigep2.converter;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormatSymbols;

import javax.faces.bean.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFOddHeader;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFHeaderFooter;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;

import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.NumeroUtil;
import co.gov.dafp.sigep2.util.logger.Logger;

@Named
@ApplicationScoped
public class ExportacionDocumentoConverter {

	private String generacion = "Fecha generación " + DateUtils.getAhora();
	private String corte = "Fecha corte de la información " + DateUtils.getAhora();
	private String nota = "Nota : La información aquí contenida corresponde a lo reportado por las entidades\nFuente : SIGEP";

	private Logger logger = Logger.getInstance(getClass());

	private static final DecimalFormatSymbols simbolo = new DecimalFormatSymbols();

	private static final String SEPARADOR_DECIMAL = String.valueOf(simbolo.getDecimalSeparator());
	private static final String SEPARADOR_MILES = String.valueOf(simbolo.getGroupingSeparator());

	private ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

	private String logo_gi 			= externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images"	+ File.separator + "logos"+ File.separator+ "gob-colombia.png";
	private String logo_gi_derecha 	= externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images"	+ File.separator + "logos"+ File.separator+ "logosigep-GI.png";
	
	
	
	private String strTextoTituloEncabezadoReporte, strTextoSubTituloEncabezadoReporte;
	
	

	/*public ExportacionDocumentoConverter(String strTextoTituloEncabezadoReporte,
			String strTextoSubTituloEncabezadoReporte) {
		super();
		this.strTextoTituloEncabezadoReporte = strTextoTituloEncabezadoReporte;
		this.strTextoSubTituloEncabezadoReporte = strTextoSubTituloEncabezadoReporte;
	}*/

	/**
	 * Valida formato de las celdas de xls o xlsx posterior al exportado de la tabla
	 * en componentes de exportacion de Primefaces a traves de la propiedad
	 * <code>postProcessor</code>
	 * 
	 * @param document Documento Excel en formato xls o xlsx. Es importante que el
	 *                 atributo <code>type</code> del componente de exportacion
	 *                 contenga exlusivamente uno de esos dos valores
	 * @param indexRow Posicion inicial de validacion de celdas
	 * @param title    Titulo del reporte
	 */
	public void postProcessExcel(Object document, int indexRow, String title) {
		try {
			xls(document, indexRow, title);
		} catch (Exception e) {
			xlsx(document, indexRow, title);
		}
	}

	/**
	 * Da formato al documento posterior al exportado de la tabla en componentes de
	 * exportacion de Primefaces a traves de la propiedad <code>postProcessor</code>
	 * 
	 * @param document Documento pdf. Es importante que el atributo
	 *                 <code>type</code> del componente de exportacion contenga
	 *                 exlusivamente este valor
	 * @param indexRow Posicion inicial de validacion
	 * @param title    Titulo del reporte
	 */
	public void postProcessPDF(Object document, int indexRow, String title) {
		try {
			Document pdf = (Document) document;
			pdf.open();

			Phrase footerPhrase = new Phrase(generacion);
			com.lowagie.text.HeaderFooter footer = new com.lowagie.text.HeaderFooter(footerPhrase, false);
			pdf.setFooter(footer);

			if (pdf.setPageSize(PageSize.LETTER)) {
				if (!pdf.setMargins(100f, 100f, 100f, 100f)) {
					return;
				}
			}

			pdf.add(Image.getInstance(logo_gi));
		} catch (Exception e) {
			return;
		}
	}

	/**
	 * Valida formato de las celdas de xls posterior al exportado de la tabla en
	 * componentes de exportacion de Primefaces a traves de la propiedad
	 * <code>postProcessor</code>
	 * 
	 * @param document Documento Excel en formato xls. Es importante que el atributo
	 *                 <code>type</code> del componente de exportacion contenga
	 *                 exlusivamente uno de esos dos valores
	 * @param indexRow Posicion inicial de validacion de celdas
	 * @param title    Titulo del reporte
	 */
	private void xls(Object document, int indexRow, String title) {
		String msg = "void xls(Object document, int indexRow)";
		int indexRowTemp = indexRow;

		HSSFWorkbook workbook = (HSSFWorkbook) document;
		HSSFSheet sheet = workbook.getSheetAt(0);

		HSSFRow row = sheet.getRow(indexRowTemp);
		while (row != null) {
			for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
				HSSFCell cell = row.getCell(i);
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				if (cell != null) {
					if (!cell.getStringCellValue().isEmpty()
							&& (!cell.getStringCellValue().startsWith("0") || cell.getStringCellValue().equals("0"))) {
						try {
							try {
								cell.setCellValue(Integer.parseInt(cell.getStringCellValue()));
								cellStyle.setDataFormat(
										workbook.createDataFormat().getFormat(NumeroUtil.FORMATO_ENTERO));
							} catch (NumberFormatException e) {
								cell.setCellValue(Double.parseDouble(cell.getStringCellValue()));
							}

							cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						} catch (Exception numberException) {
							if (cell.getStringCellValue().endsWith("%")) {
								String porcentaje = cell.getStringCellValue().replace("%", "").replace(" ", "");
								try {
									cell.setCellValue(Double.parseDouble(porcentaje) / 100);
									cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
									cellStyle.setDataFormat(
											workbook.createDataFormat().getFormat(NumeroUtil.FORMATO_PORCENTAJE));
								} catch (Exception percentException) {
									logger.error(msg, cell.getStringCellValue(), percentException);
								}
							} else if (cell.getStringCellValue().contains("$")) {
								String monto = cell.getStringCellValue().replace("$", "").replace(SEPARADOR_MILES, "")
										.replace(" ", "");
								monto = monto.replace(SEPARADOR_DECIMAL, ".");
								try {
									cell.setCellValue(Double.parseDouble(monto));
									cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
									cellStyle.setDataFormat(
											workbook.createDataFormat().getFormat(NumeroUtil.FORMATO_MONEDA));
								} catch (Exception moneyException) {
									logger.error(msg, cell.getStringCellValue(), moneyException);
								}
							}
						}
					}
					cell.setCellStyle(cellStyle);
				}
			}
			indexRowTemp++;
			row = sheet.getRow(indexRowTemp);
		}
	}

	/**
	 * Valida formato de las celdas de xlsx posterior al exportado de la tabla en
	 * componentes de exportacion de Primefaces a traves de la propiedad
	 * <code>postProcessor</code>
	 * 
	 * @param document Documento Excel en formato xlsx. Es importante que el
	 *                 atributo <code>type</code> del componente de exportacion
	 *                 contenga exlusivamente uno de esos dos valores
	 * @param indexRow Posicion inicial de validacion de celdas
	 * @param title    Titulo del reporte
	 */
	private void xlsx(Object document, final int indexRow, String title) {
		Parametrica parametrica;
		try {
			parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.GI_TITULO_REPORTES.getValue()));
			if (parametrica != null && parametrica.getValorParametro() != null && !"".equals(parametrica.getValorParametro())) 
				strTextoTituloEncabezadoReporte = parametrica.getValorParametro();
			else
				strTextoTituloEncabezadoReporte = "";
		} catch (Exception z) {
			strTextoTituloEncabezadoReporte = "";
		}	
		try {
			parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.GI_SUBTITULO_REPORTES.getValue()));
			if (parametrica != null && parametrica.getValorParametro() != null && !"".equals(parametrica.getValorParametro())) 
				strTextoSubTituloEncabezadoReporte = parametrica.getValorParametro();
			else
				strTextoSubTituloEncabezadoReporte = "";
		} catch (Exception z) {
			strTextoSubTituloEncabezadoReporte = "";
		}			
		
		String msg = "void xlsx(Object document, int indexRow)";
		int indexRowTemp = indexRow;
		if(indexRow == 0) {
			indexRowTemp++;
		}

		XSSFWorkbook workbook = (XSSFWorkbook) document;
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		sheet.shiftRows(0, sheet.getLastRowNum(), 4);

		Color whiteAWT = new Color(255, 255, 255);
		XSSFColor white = new XSSFColor(whiteAWT);

		final short fontSize = 10;

		XSSFFont defaultFont = workbook.createFont();
		defaultFont.setFontHeightInPoints(fontSize);
		defaultFont.setColor(IndexedColors.BLACK.getIndex());
		defaultFont.setBold(true);
		defaultFont.setItalic(false);

		XSSFFont detailFont = workbook.createFont();
		detailFont.setFontHeightInPoints(fontSize);
		detailFont.setColor(IndexedColors.BLACK.getIndex());
		detailFont.setBold(false);
		detailFont.setItalic(false);

		XSSFCellStyle cellStyleDefault = workbook.createCellStyle();
		cellStyleDefault.setWrapText(true);
		cellStyleDefault.setFont(defaultFont);
		cellStyleDefault.setAlignment(HorizontalAlignment.CENTER);
		cellStyleDefault.setBorderBottom(BorderStyle.HAIR);
		cellStyleDefault.setBorderLeft(BorderStyle.HAIR);
		cellStyleDefault.setBorderRight(BorderStyle.HAIR);
		cellStyleDefault.setBorderTop(BorderStyle.HAIR);
		cellStyleDefault.setWrapText(true);
		cellStyleDefault.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleDefault.setFillForegroundColor(white);
		cellStyleDefault.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		int firtsRow = indexRowTemp;

		XSSFRow row = sheet.getRow(indexRowTemp);
		if (row == null) {
			indexRowTemp++;
			firtsRow++;
			row = sheet.getRow(indexRowTemp);
		}
		int contCols = 0;
		while (row != null) {
			for (int i = 0; i < row.getLastCellNum(); i++) {
				XSSFCell cell = row.getCell(i);
				XSSFCellStyle cellStyle = workbook.createCellStyle();
				if (cell != null) {
					if (firtsRow == indexRowTemp) {
						contCols++;
					}
					if (!cell.getStringCellValue().isEmpty()
							&& (!cell.getStringCellValue().startsWith("0") || cell.getStringCellValue().equals("0"))) {
						try {
							try {
								cell.setCellValue(
										BigInteger.valueOf(Long.valueOf(cell.getStringCellValue())).longValue());
								cellStyle.setDataFormat(
										workbook.createDataFormat().getFormat(NumeroUtil.FORMATO_ENTERO));
							} catch (NumberFormatException e) {
								cell.setCellValue(Double.parseDouble(cell.getStringCellValue()));
							}

							cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						} catch (Exception numberException) {
							if (cell.getStringCellValue().endsWith("0 %")) {
								String porcentaje = cell.getStringCellValue().replace("%", "").replace(" ", "")
										.replace("rojo", "");
								try {
									cell.setCellValue(Double.parseDouble(porcentaje) / 100);
									cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
									cellStyle.setDataFormat(
											workbook.createDataFormat().getFormat(NumeroUtil.FORMATO_PORCENTAJE));
								} catch (Exception percentException) {
									logger.error(msg, cell.getStringCellValue(), percentException);
								}
							} else if (cell.getStringCellValue().contains("$")) {
								String monto = cell.getStringCellValue().replace("$", "").replace(SEPARADOR_MILES, "")
										.replace(" ", "");
								monto = monto.replace(SEPARADOR_DECIMAL, ".");

								try {
									cell.setCellValue(Double.parseDouble(monto));
									cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
									cellStyle.setDataFormat(
											workbook.createDataFormat().getFormat(NumeroUtil.FORMATO_MONEDA));
								} catch (Exception moneyException) {
									logger.error(msg, cell.getStringCellValue(), moneyException);
								}
							}
						}
					}
					if (firtsRow == indexRowTemp) {
						cellStyle.setFont(defaultFont);
						cellStyle.setAlignment(HorizontalAlignment.CENTER);
					} else {
						cellStyle.setFont(detailFont);
					}
					cellStyle.setFillForegroundColor(white);
					cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
					cellStyle.setBorderBottom(BorderStyle.HAIR);
					cellStyle.setBorderLeft(BorderStyle.HAIR);
					cellStyle.setBorderRight(BorderStyle.HAIR);
					cellStyle.setBorderTop(BorderStyle.HAIR);
					cell.setCellStyle(cellStyle);
				}
			}
			indexRowTemp++;
			row = sheet.getRow(indexRowTemp);
		}

		String formatHeaderFooter = HSSFHeader.font(defaultFont.getFontName(), defaultFont.getBold() ? "Bold" : "")
				+ HSSFHeader.fontSize((short) 12);

		Footer footer = sheet.getFooter();
		footer.setCenter(formatHeaderFooter + "\n" + nota);
		footer.setRight(formatHeaderFooter + "\n" + HeaderFooter.page() + " de " + HeaderFooter.numPages());

		try {
			/*Región logo izquierda*/
			CellRangeAddress cellMerge;
			/*Región Texto 1*/
			cellMerge = new CellRangeAddress(1, 1, 2,5);
			sheet.addMergedRegion(cellMerge);
			/*Región Texto 2*/
			cellMerge = new CellRangeAddress(2, 2, 2,5);
			sheet.addMergedRegion(cellMerge);
			/*Región fecha generación*/
			cellMerge = new CellRangeAddress(4, 4, 0,1);
			sheet.addMergedRegion(cellMerge);	
			/*Región nombre reporte*/
			cellMerge = new CellRangeAddress(3, 3, 2,5);
			sheet.addMergedRegion(cellMerge);
		
			/*Imagen*/
			InputStream my_banner_image = new FileInputStream(logo_gi);
			byte[]  bytes = IOUtils.toByteArray(my_banner_image);
            int my_picture_id = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            
            my_banner_image.close();
            XSSFClientAnchor my_anchor = new XSSFClientAnchor();
            XSSFDrawing drawing;
            /*Tamaño fijo*/
            my_anchor.setDx1(0 * XSSFShape.EMU_PER_PIXEL);
            my_anchor.setDx2(400 * XSSFShape.EMU_PER_PIXEL);
            my_anchor.setRow1(1); 
            my_anchor.setRow2(4); 
            my_anchor.setCol1(0);
            my_anchor.setCol2(1);
              
            drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            drawing.createPicture(my_anchor, my_picture_id);
            
            /*Imagen Derecha*/
            //logosigep120
			my_banner_image = new FileInputStream(logo_gi_derecha);
			bytes = IOUtils.toByteArray(my_banner_image);
            int my_picture_id_ld = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            my_banner_image.close();
            my_anchor = new XSSFClientAnchor();
            my_anchor.setDx1(0 * XSSFShape.EMU_PER_PIXEL);
            my_anchor.setDx2(100 * XSSFShape.EMU_PER_PIXEL);
            my_anchor.setRow1(1); 
            my_anchor.setRow2(4); 
            my_anchor.setCol1(6); //Columna G
            my_anchor.setCol2(7); //Columna H
           
           
            drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            drawing.createPicture(my_anchor, my_picture_id_ld);            
            /*Header*/
            XSSFHeaderFooter header = (XSSFOddHeader) sheet.getHeader();
            header.setLeft(formatHeaderFooter + generacion);
			header.setCenter(formatHeaderFooter + "&[Picture]" + title.toUpperCase() + "\n");
			header.setRight(formatHeaderFooter + corte);
		} catch (IOException e) {

		}

		// Configuracion de pagina
		final XSSFPrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);
		printSetup.setFitWidth((short) 1);
		printSetup.setFitHeight((short) 0);
		printSetup.setPaperSize(PrintSetup.LETTER_PAPERSIZE);
		printSetup.setHeaderMargin(0.2);
		printSetup.setFooterMargin(0.15);

		sheet.setAutoFilter(new CellRangeAddress(indexRow, indexRow, 0, contCols - 1));
		sheet.createFreezePane(0, indexRow + 1);
		sheet.setFitToPage(true);
		sheet.setAutobreaks(true);

		sheet.setMargin(Sheet.LeftMargin, 0.5);
		sheet.setMargin(Sheet.RightMargin, 0.5);
		sheet.setMargin(Sheet.TopMargin, 0.6);
		sheet.setMargin(Sheet.BottomMargin, 0.55);

		int rowCount = 0;
		XSSFCellStyle cellStyleHair = workbook.createCellStyle();
		cellStyleHair.setBorderBottom(BorderStyle.HAIR);
		cellStyleHair.setBorderLeft(BorderStyle.HAIR);
		cellStyleHair.setBorderRight(BorderStyle.HAIR);
		cellStyleHair.setBorderTop(BorderStyle.HAIR);
		cellStyleHair.setWrapText(true);
		cellStyleHair.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleHair.setFillForegroundColor(white);
		cellStyleHair.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		XSSFCellStyle cellStyleNone = workbook.createCellStyle();
		cellStyleNone.setFillForegroundColor(white);
		cellStyleNone.setBorderBottom(BorderStyle.NONE);
		cellStyleNone.setBorderLeft(BorderStyle.NONE);
		cellStyleNone.setBorderRight(BorderStyle.NONE);
		cellStyleNone.setBorderTop(BorderStyle.NONE);
		cellStyleNone.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		row = sheet.getRow(indexRow);
		
		while (row != null) {
			row.setRowStyle(cellStyleNone);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				XSSFCell cell = row.getCell(i);
				if (cell != null) {
					if (rowCount == indexRow) {
						cell.setCellStyle(cellStyleDefault);
					} else if (rowCount > indexRow) {
						cell.setCellStyle(cellStyleHair);
					} else {
						cell.setCellStyle(cellStyleNone);
					}
				}
			}
			rowCount++;
			row = sheet.getRow(rowCount);
			if (row == null && rowCount < indexRowTemp) {
				row = sheet.createRow(rowCount);
				for (int i = 0; i < contCols; i++) {
					row.createCell(i);
				}
			}
		}
		workbook.getAllPictures();
		

		/*Estilo de los encabezado*/
		XSSFFont headFont = workbook.createFont();
		headFont.setFontHeightInPoints(fontSize);
		headFont.setColor(IndexedColors.BLACK.getIndex());
		headFont.setBold(true);
		headFont.setItalic(false);
		headFont.setFontHeight(14);		
		XSSFCellStyle cellStyleHead = workbook.createCellStyle();
		cellStyleHead.setFillForegroundColor(white);
		cellStyleHead.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleHead.setAlignment(HorizontalAlignment.CENTER);
		cellStyleHead.setWrapText(true);
		cellStyleHead.setFont(headFont);
		
		/*Estilo del título*/
		XSSFFont titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints(fontSize);
		titleFont.setColor(IndexedColors.BLACK.getIndex());
		titleFont.setBold(true);
		titleFont.setItalic(true);
		titleFont.setFontHeight(12);		
		XSSFCellStyle cellStyleTitulos = workbook.createCellStyle();
		cellStyleTitulos.setFillForegroundColor(white);
		cellStyleTitulos.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleTitulos.setAlignment(HorizontalAlignment.CENTER);
		cellStyleTitulos.setWrapText(true);	
		cellStyleTitulos.setFont(titleFont);

		
		

		
		
		/*Titulo*/
		XSSFRow hssfRowNew = sheet.getRow(1);
		hssfRowNew.setHeight((short) (20 * 20) );
		XSSFCell cell = hssfRowNew.createCell((short)2);
		cell.setCellValue(strTextoTituloEncabezadoReporte);
		cell.setCellStyle(cellStyleHead);
		/*Subtitulo*/
		hssfRowNew = sheet.getRow(2);
		hssfRowNew.setHeight((short) (20 * 20) );
		cell = hssfRowNew.createCell((short)2);
		cell.setCellValue(strTextoSubTituloEncabezadoReporte);	
		cell.setCellStyle(cellStyleHead);
		/*nombre de reporte*/
		hssfRowNew = sheet.getRow(3);
		cell = hssfRowNew.createCell((short)2);
		cell.setCellValue(title.toUpperCase());		
		cell.setCellStyle(cellStyleTitulos);
		/*fecha de generación*/
		hssfRowNew = sheet.getRow(4);
		cell = hssfRowNew.createCell((short)0);
		cell.setCellValue(generacion);			
	}

	/**
	 * Copia una fila ubicada en <code>sourceRowNum</code> a
	 * <code>destinationRowNum</code>
	 * 
	 * @param workbook          Libro de excel donde se encuentra la fila a copiar
	 * @param worksheet         Hoja del libro de excel <code>workbook</code> de
	 *                          donde se tomara la fila a copiar
	 * @param sourceRowNum      indice de la ubicacion de donde se copiara la fila
	 * @param destinationRowNum indice a donde se pegara la fila
	 */
	private static void copyRow(XSSFWorkbook workbook, XSSFSheet worksheet, int sourceRowNum, int destinationRowNum,
			int cols) {
		// Get the source / new row
		XSSFRow newRow = worksheet.getRow(destinationRowNum);
		XSSFRow sourceRow = worksheet.getRow(sourceRowNum);

		// If the row exist in destination, push down all rows by 1 else create
		// a new row
		if (newRow != null) {
			worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
		} else {
			newRow = worksheet.createRow(destinationRowNum);
		}

		if (sourceRow == null) {
			sourceRow = worksheet.createRow(sourceRowNum);

			for (int i = 0; i < cols; i++) {
				sourceRow.createCell(i);
			}
		}

		// Loop through source columns to add to new row
		for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			XSSFCell oldCell = sourceRow.getCell(i);
			XSSFCell newCell = newRow.createCell(i);

			// If the old cell is null jump to next cell
			if (oldCell == null) {
				newCell = null;
				continue;
			}

			// Copy style from old cell and apply to new cell
			XSSFCellStyle newCellStyle = workbook.createCellStyle();
			newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(newCellStyle);

			// If there is a cell comment, copy
			if (oldCell.getCellComment() != null) {
				newCell.setCellComment(oldCell.getCellComment());
			}

			// If there is a cell hyperlink, copy
			if (oldCell.getHyperlink() != null) {
				newCell.setHyperlink(oldCell.getHyperlink());
			}

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				newCell.setCellFormula(oldCell.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				newCell.setCellValue(oldCell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}
		}
		for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
			CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
			if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
				CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
						(newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
						cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
				// worksheet.addMergedRegion(newCellRangeAddress);
			}
		}
	}

}
