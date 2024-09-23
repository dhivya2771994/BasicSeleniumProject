package com.seleniumproject.util;

import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxUtil {
		static XSSFWorkbook workbook ;
		static XSSFSheet sheet;
		static XSSFRow row;
		static int rowcount;
		static int columncount;
		public static int getRowCount() throws IOException {
			 rowcount = sheet.getPhysicalNumberOfRows();
			System.out.println("Row Count "+rowcount);
			return rowcount;
		}
		
		public XlsxUtil(String excelPath, String sheetname) throws IOException {
			workbook = new XSSFWorkbook(excelPath);
		     sheet= workbook.getSheet(sheetname);
		}
		
		public static int getColumnCount() throws IOException {
			columncount = sheet.getRow(0).getPhysicalNumberOfCells();
			System.out.println("Column Count "+columncount);
			return columncount;
		}
		
		public static String getCellData(int rowNo, int colNo) throws IOException {
			CellType celltype = null;
			Object celldata = null;
		     row = sheet.getRow(rowNo);
		     if (row != null) {
		    	 celltype = sheet.getRow(rowNo).getCell(colNo).getCellType();
		    	// XSSFCell cell = sheet.getRow(rowNo).getCell(colNo);
		    	 //DataFormatter df = new DataFormatter();
		    	   //celldat=df.formatCellValue(cell);
		    	 switch(celltype) {
					case STRING:
						celldata=sheet.getRow(rowNo).getCell(colNo).getStringCellValue();
						System.out.print("String value "+celldata);
						break;
					case NUMERIC:
						celldata=sheet.getRow(rowNo).getCell(colNo).getNumericCellValue();
						System.out.print("Numeric value "+celldata);
						break;
					case BOOLEAN:
						celldata=sheet.getRow(rowNo).getCell(colNo).getBooleanCellValue();
						System.out.print("Boolean value "+celldata);
						break;
					case BLANK:
						celldata=sheet.getRow(rowNo).getCell(colNo).getStringCellValue();
						System.out.print("Blank value "+celldata);
						break;
					}
		     }
			return celldata.toString();
		}
		
		public Object[][] testData(String path,String sheetname) throws IOException {
	//	ExcelUtil util =new ExcelUtil(path+"/TestData.xlsx", sheetname);
		int row=getRowCount();
		int col=getColumnCount();
		Object [][] obj =new Object[row-1][col];
		//String cell;
		for(int i=1;i<row;i++) {
			for(int j=0;j<col;j++) {
				String cell=getCellData(i, j);
				obj[i-1][j]=cell;
				//System.out.println(i);
				//System.out.println(cell);
			}
		}
		return obj;
		}
}