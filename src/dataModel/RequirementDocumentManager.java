package dataModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import GUI.ReportTable;


public class RequirementDocumentManager {

	static int index_document_1;
	static int index = 0;
	private String Corpora;
	public RequirementDocumentManager() {
		super();
	}

	public Map<String,Requirement>  ExtractRequirements(String DocPath) throws IOException{
		Map<String,Requirement> requirement_map = new HashMap<String,Requirement>();
		String in="";
		File myFile = new File(DocPath); 
		FileInputStream fis = new FileInputStream(myFile); 
		XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
		
		for(int i = 0; i < myWorkBook.getNumberOfSheets();++i)
		{
			XSSFSheet mySheet = myWorkBook.getSheetAt(i);	
			File f = new File("Requirement");
			f.delete();
			File filefolder = new File("Requirement");
	    	filefolder.mkdir();
			File file = new File("Requirement"+File.separator+mySheet.getSheetName()+".xml");
			FileWriter fw = new FileWriter(file);
		
			Iterator<Row> rowIterator = mySheet.iterator();
			while (rowIterator.hasNext()){
				Row row = rowIterator.next();
				if(i == 0)
				{
					this.Corpora="Requirement"+File.separator+mySheet.getSheetName()+".xml";
					Cell cell = row.getCell(4);
					in = Integer.toString(index);
					fw.write("<requirement>");
					fw.write(cell.getStringCellValue());
					fw.write("</requirement>");
					fw.write("\r\n");
					index++;
					if(row.getRowNum() > 0 && row.getCell(1).getCellType() == 0)
					{
						
						long last_index = cell.getStringCellValue().codePointCount(0, cell.getStringCellValue().length());
						index_document_1 = index_document_1 + cell.getStringCellValue().codePointCount(0, cell.getStringCellValue().length());
						//System.out.println(cell.getStringCellValue());
						Requirement req = new Requirement(Double.toString(row.getCell(1).getNumericCellValue()), cell.getStringCellValue());
						requirement_map.put(cell.getStringCellValue(), req);
					}
					
				}
				if(i == 1)
				{
					Cell cell = row.getCell(3);
//					System.out.println(cell.getStringCellValue());
//					System.out.println();
					fw.write(cell.getStringCellValue());
					fw.write("\r\n");
					fw.write("\r\n");
				}
				if(i == 2)
				{
					Cell cell = row.getCell(2);
//					System.out.println(cell.getStringCellValue());
//					System.out.println();
					fw.write(cell.getStringCellValue());
					fw.write("\r\n");
					fw.write("\r\n");
				}
				if(i == 3)
				{
					Cell cell = row.getCell(2);
//					System.out.println(cell.getStringCellValue());
//					System.out.println();
					fw.write(cell.getStringCellValue());
					fw.write("\r\n");
					fw.write("\r\n");
				}
				if(i == 4)
				{
					 Cell cell = row.getCell(1);
					 switch (cell.getCellType()) 
	                 {
	 	                 case Cell.CELL_TYPE_STRING:	               
//	 	                   	System.out.println(cell.getStringCellValue());
//	 	   				    System.out.println();
	 	   				fw.write(cell.getStringCellValue());
	 					fw.write("\r\n");
	 					fw.write("\r\n");
	 	                 break;
	 	                 case Cell.CELL_TYPE_NUMERIC:	               
	 	                	 
	 	                 break;
	                 }

				}
			}
			fw.flush();
			fw.close();
		}
		return requirement_map;
	}

	public String getCorpora() {
		return Corpora;
	}

	public String Report(ArrayList<ReportTable> clarity_defect, ArrayList<ReportTable> nonambiguity_defect, ArrayList<ReportTable> completeness_defect, ArrayList<ReportTable> general_defect) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		String result =null;
		try {
			    XSSFWorkbook wb = new XSSFWorkbook();	
//			    InputStream inp = new FileInputStream("Report.xlsx");
//			    Workbook wb = WorkbookFactory.create(inp);

			    /*header cell style*/
				 CellStyle style = wb.createCellStyle();
				 style.setBorderBottom(CellStyle.BORDER_DOUBLE);
			     style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
			     style.setBorderLeft(CellStyle.BORDER_MEDIUM);
			     style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
			     style.setBorderRight(CellStyle.BORDER_MEDIUM);
			     style.setRightBorderColor(IndexedColors.WHITE.getIndex());
			     style.setBorderTop(CellStyle.BORDER_DOUBLE);
			     style.setTopBorderColor(IndexedColors.WHITE.getIndex());
			     style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
			     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			     style.setWrapText(true);
			     Font font = wb.createFont();
			     font.setBold(true);
			     font.setFontName("Alstom");
			     font.setFontHeightInPoints((short) 12);
			     font.setColor(HSSFColor.WHITE.index);
			     style.setFont(font);
			     
			     /*generic cell style*/
			     CellStyle style2 = wb.createCellStyle();
				 style2.setBorderBottom(CellStyle.BORDER_THIN);
			     style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			     style2.setBorderLeft(CellStyle.BORDER_THIN);
			     style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			     style2.setBorderRight(CellStyle.BORDER_THIN);
			     style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
			     style2.setBorderTop(CellStyle.BORDER_THIN);
			     style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
			     style2.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			     style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
			     style2.setWrapText(true);
			     Font font2 = wb.createFont();
			     font2.setBold(false);
			     font2.setFontName("Alstom");
			     font2.setFontHeightInPoints((short) 12);
			     font2.setColor(HSSFColor.BLACK.index);
			     style2.setFont(font2);	
			     if(clarity_defect.size() > 0)
			     {
			    	 createSheet("Clarity", wb, style, style2, clarity_defect);				     
			     }
			     
			     if(nonambiguity_defect.size() > 0)
			     {
			    	 createSheet("Non Ambiguity", wb, style, style2, nonambiguity_defect);				     
			     }
			     
			     if(completeness_defect.size() > 0)
			     {
			    	 createSheet("Completeness", wb, style, style2, completeness_defect);				     
			     }
			     
			     if(general_defect.size() > 0)
			     {
			    	 createSheet("General", wb, style, style2, general_defect);				     
			     }
				 
			     File f = new File ("Report");

			     boolean exist = f.isDirectory ();
			     if(!exist)
			     {
			    	 f.mkdir();
			     }
			     Date data =new Date();
			     String day = Integer.toString(data.getDate());
			     String month = Integer.toString(data.getMonth());
			     String hour = Integer.toString(data.getHours());
			     String minute = Integer.toString(data.getMinutes());
			     String name = "Report"+ File.separator+"Report"+day+"_"+month+"_"+hour+"_"+minute+".xlsx";
			    // Write the output to a file
			    FileOutputStream fileOut = new FileOutputStream(name);
			    wb.write(fileOut);
			    fileOut.close();
			    result = name;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}
	
	
	
	public String ReportAll(ArrayList<ReportTable> clarity_defect, ArrayList<ReportTable> nonambiguity_defect, ArrayList<ReportTable> completeness_defect,ArrayList<ReportTable> general_defect) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		String result =null;
		try {
			    XSSFWorkbook wb = new XSSFWorkbook();	
//			    InputStream inp = new FileInputStream("Report.xlsx");
//			    Workbook wb = WorkbookFactory.create(inp);

			    /*header cell style*/
				 CellStyle style = wb.createCellStyle();
				 style.setBorderBottom(CellStyle.BORDER_DOUBLE);
			     style.setBottomBorderColor(IndexedColors.WHITE.getIndex());
			     style.setBorderLeft(CellStyle.BORDER_MEDIUM);
			     style.setLeftBorderColor(IndexedColors.WHITE.getIndex());
			     style.setBorderRight(CellStyle.BORDER_MEDIUM);
			     style.setRightBorderColor(IndexedColors.WHITE.getIndex());
			     style.setBorderTop(CellStyle.BORDER_DOUBLE);
			     style.setTopBorderColor(IndexedColors.WHITE.getIndex());
			     style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
			     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			     style.setWrapText(true);
			     Font font = wb.createFont();
			     font.setBold(true);
			     font.setFontName("Alstom");
			     font.setFontHeightInPoints((short) 12);
			     font.setColor(HSSFColor.WHITE.index);
			     style.setFont(font);
			     
			     /*generic cell style*/
			     CellStyle style2 = wb.createCellStyle();
				 style2.setBorderBottom(CellStyle.BORDER_THIN);
			     style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			     style2.setBorderLeft(CellStyle.BORDER_THIN);
			     style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			     style2.setBorderRight(CellStyle.BORDER_THIN);
			     style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
			     style2.setBorderTop(CellStyle.BORDER_THIN);
			     style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
			     style2.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			     style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
			     style2.setWrapText(true);
			     Font font2 = wb.createFont();
			     font2.setBold(false);
			     font2.setFontName("Alstom");
			     font2.setFontHeightInPoints((short) 12);
			     font2.setColor(HSSFColor.BLACK.index);
			     style2.setFont(font2);	
			     if(clarity_defect.size() > 0)
			     {
			    	 createSheetAll("Clarity", wb, style, style2, clarity_defect);				     
			     }
			     
			     if(nonambiguity_defect.size() > 0)
			     {
			    	 createSheetAll("Non Ambiguity", wb, style, style2, nonambiguity_defect);				     
			     }
			     
			     if(completeness_defect.size() > 0)
			     {
			    	 createSheetAll("Completeness", wb, style, style2, completeness_defect);				     
			     }
				 
			     if(general_defect.size() > 0)
			     {
			    	 createSheetAll("General", wb, style, style2, general_defect);				     
			     }
			     File f = new File ("Report");

			     boolean exist = f.isDirectory ();
			     if(!exist)
			     {
			    	 f.mkdir();
			     }
			     Date data =new Date();
			     String day = Integer.toString(data.getDate());
			     String month = Integer.toString(data.getMonth());
			     String hour = Integer.toString(data.getHours());
			     String minute = Integer.toString(data.getMinutes());
			     String name = "Report"+ File.separator+"Report_All"+day+"_"+month+"_"+hour+"_"+minute+".xlsx";
			    // Write the output to a file
			    FileOutputStream fileOut = new FileOutputStream(name);
			    wb.write(fileOut);
			    fileOut.close();
			    result = name;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}
	
	public void createSheet(String name, Workbook wb, CellStyle style ,CellStyle style2, ArrayList<ReportTable> defect)
	{	    
		Sheet sheet = wb.createSheet(name);	    
	    sheet.setColumnWidth(0, 9000);
	    sheet.setColumnWidth(2, 6000);
	    sheet.setColumnWidth(1, 3000);
		Row row = sheet.createRow(0);
		 Cell cell = row.createCell(0);
		 if (cell == null)
		    cell = row.createCell(0);
		 cell.setCellType(Cell.CELL_TYPE_STRING);
		 cell.setCellValue("Requirement");
		 cell.setCellStyle(style);
		 cell = row.createCell(1);
		 if (cell == null)
		      cell = row.createCell(1);
		 cell.setCellType(Cell.CELL_TYPE_STRING);
		 cell.setCellValue("Indicator");
		 cell.setCellStyle(style);
		 cell = row.createCell(2);
		 if (cell == null)
		      cell = row.createCell(2);
		 cell.setCellType(Cell.CELL_TYPE_STRING);
		 cell.setCellValue("Relevance");
		 cell.setCellStyle(style);
		 cell = row.createCell(3);
		 if (cell == null)
		      cell = row.createCell(2);
		 cell.setCellType(Cell.CELL_TYPE_STRING);
		 cell.setCellValue("Description");
		 cell.setCellStyle(style);
		 cell = row.createCell(4);
						 
		 int i = 1;

		 Iterator<ReportTable> it = defect.iterator();
		 while(it.hasNext())
		 {
			 ReportTable ts = it.next();
			 if(!ts.isDefect())
			 {
				 Row row1 = sheet.createRow(i);
				 cell = row1.createCell(0);
				 if (cell == null)
				    cell = row1.createCell(0);
				 cell.setCellType(Cell.CELL_TYPE_STRING);
				 cell.setCellValue(ts.getText());
				 cell.setCellStyle(style2);
				 cell = row1.createCell(1);
				 if (cell == null)
				      cell = row1.createCell(1);
				 cell.setCellType(Cell.CELL_TYPE_STRING);
				 cell.setCellValue(ts.getIndicator());
				 cell.setCellStyle(style2);
				 cell = row1.createCell(2);
				 if (cell == null)
				      cell = row1.createCell(2);
				 cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				 cell.setCellValue(ts.getRank());
				 cell.setCellStyle(style2);
				 cell = row1.createCell(3);
				 if (cell == null)
				      cell = row1.createCell(3);
				 cell.setCellType(Cell.CELL_TYPE_STRING);
				 cell.setCellValue(ts.getDescription());
				 cell.setCellStyle(style2);
				 i++;
			 }
		 }
	}
	
	
	public void createSheetAll(String name, Workbook wb, CellStyle style ,CellStyle style2, ArrayList<ReportTable> defect)
	{	    
		Sheet sheet = wb.createSheet(name);	    
	    sheet.setColumnWidth(0, 9000);
	    sheet.setColumnWidth(2, 6000);
	    sheet.setColumnWidth(1, 3000);
		Row row = sheet.createRow(0);
		 Cell cell = row.createCell(0);
		 if (cell == null)
		    cell = row.createCell(0);
		 cell.setCellType(Cell.CELL_TYPE_STRING);
		 cell.setCellValue("Requirement");
		 cell.setCellStyle(style);
		 cell = row.createCell(1);
		 if (cell == null)
		      cell = row.createCell(1);
		 cell.setCellType(Cell.CELL_TYPE_STRING);
		 cell.setCellValue("Indicator");
		 cell.setCellStyle(style);
		 cell = row.createCell(2);
		 if (cell == null)
		      cell = row.createCell(2);
		 cell.setCellType(Cell.CELL_TYPE_STRING);
		 cell.setCellValue("Relevance");
		 cell.setCellStyle(style);
		 cell = row.createCell(3);
		 if (cell == null)
		      cell = row.createCell(3);
		 cell.setCellType(Cell.CELL_TYPE_STRING);
		 cell.setCellValue("Description");
		 cell.setCellStyle(style);
		 cell = row.createCell(4);
		 if (cell == null)
		      cell = row.createCell(4);
		 cell.setCellType(Cell.CELL_TYPE_STRING);
		 cell.setCellValue("Not a Defect");
		 cell.setCellStyle(style);
				 
		 int i = 1;

		 Iterator<ReportTable> it = defect.iterator();
		 while(it.hasNext())
		 {
			 ReportTable ts = it.next();

				 Row row1 = sheet.createRow(i);
				 cell = row1.createCell(0);
				 if (cell == null)
				    cell = row1.createCell(0);
				 cell.setCellType(Cell.CELL_TYPE_STRING);
				 cell.setCellValue(ts.getText());
				 cell.setCellStyle(style2);
				 cell = row1.createCell(1);
				 if (cell == null)
				      cell = row1.createCell(1);
				 cell.setCellType(Cell.CELL_TYPE_STRING);
				 cell.setCellValue(ts.getIndicator());
				 cell.setCellStyle(style2);
				 cell = row1.createCell(2);
				 if (cell == null)
				      cell = row1.createCell(2);
				 cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				 cell.setCellValue(ts.getRank());
				 cell.setCellStyle(style2);
				 cell = row1.createCell(3);
				 if (cell == null)
				      cell = row1.createCell(3);
				 cell.setCellType(Cell.CELL_TYPE_STRING);
				 cell.setCellValue(ts.getDescription());
				 cell.setCellStyle(style2);
				 cell = row1.createCell(4);
				 if (cell == null)
				      cell = row1.createCell(4);
				 cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
				 cell.setCellValue(ts.isDefect());
				 cell.setCellStyle(style2);
				 i++;
	
		 }
	}

}
