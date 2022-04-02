package datautils;


import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDrivenXcelUtils
{
	//public static XSSFWorkbook wb;
	//public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static FileInputStream is;
	
	@SuppressWarnings("unused")
	public static Object[][] GetDataXcel(String File,String Sheet) throws IOException
	{
		is=new FileInputStream(File);
		
		XSSFWorkbook wb=new XSSFWorkbook(is);
		
		XSSFSheet sheet=wb.getSheet(Sheet);
		
		int rownum=sheet.getLastRowNum();
		//System.out.println("row"+rownum);
		row=sheet.getRow(rownum);
		
		int colnum=row.getLastCellNum();
		//System.out.println("row"+colnum);
		Object[][] exceldata=new Object[rownum][colnum];
		Object[][] exceldatare=new Object[rownum][colnum];
		int i,j;
		for( i=1;i<=rownum;i++)
		{
			for(j=0;j<colnum;j++)
			{
				exceldata[i-1][j]=sheet.getRow(i).getCell(j).getCellType();
				if(exceldata[i-1][j]==CellType.NUMERIC)
				{	
					Double a=sheet.getRow(i).getCell(j).getNumericCellValue();
					long b=Math.round(a);
					exceldatare[i-1][j]=Long.toString(b);
					//System.out.println("data"+exceldatare[i-1][j]);
					
				}
				if(exceldata[i-1][j]==CellType.STRING)
				{
					exceldatare[i-1][j]=sheet.getRow(i).getCell(j).getStringCellValue();
					//System.out.println("data"+exceldatare[i-1][j]);
				}
				if(exceldata[i-1][j]== CellType.BOOLEAN)
				{
					exceldatare[i-1][j]=sheet.getRow(i).getCell(j).getBooleanCellValue();
					//System.out.println("data"+exceldatare[i-1][j]);
				}
				
			}
		}
		return exceldatare;
	
	 }
 }
