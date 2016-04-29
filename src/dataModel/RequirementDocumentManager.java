package dataModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RequirementDocumentManager {

	static int index_document_1;
	private String Corpora;
	public RequirementDocumentManager() {
		super();
	}

	public Map<String,Requirement>  ExtractRequirements(String DocPath) throws IOException{
		Map<String,Requirement> requirement_map = new HashMap<String,Requirement>();

		File myFile = new File(DocPath); 
		FileInputStream fis = new FileInputStream(myFile); 
		XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
		
		for(int i = 0; i < myWorkBook.getNumberOfSheets();++i)
		{
			XSSFSheet mySheet = myWorkBook.getSheetAt(i);	
			File file = new File(mySheet.getSheetName()+".txt");
			FileWriter fw = new FileWriter(file);
			Iterator<Row> rowIterator = mySheet.iterator();
			while (rowIterator.hasNext()){
				Row row = rowIterator.next();
				if(i == 0)
				{
					this.Corpora=mySheet.getSheetName()+".txt";
					Cell cell = row.getCell(4);
					fw.write(cell.getStringCellValue());
					fw.write("\r\n");
					fw.write("\r\n");
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



}
