package utilities;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {
	private static Logger log=Logger.getLogger("ExcelUtility");
	public  String path;
	public static FileInputStream fis;
	public static FileOutputStream fos;
	private static Workbook wb=null;
	private static Sheet sheet=null;
	private static Cell cell=null;
	private static Row row=null;
	
	//-----------------------------------------CONSTRUCTOR(Initializing path and creating workbook)------------------------------------------------------------------------------------------------//
	
	public ExcelUtility(String path) {
		this.path=path;
		try {
			log.info("Loading Excel File from the path : "+path);
			fis=new FileInputStream(path);
			wb=WorkbookFactory.create(fis);
			log.info("Creating the WorkBook");
			fis.close();
		} catch (Exception e) {
			log.error("Failed to create the WorkBook : "+e.toString());
			e.printStackTrace();
		}
	}
	
	//-----------------------------------------------------------GETTING ROW COUNT------------------------------------------------------------------------------------------------------------------------//
	
	public int getRowCount(String sheetName) {
		int rowCount=0;
		try {
			System.out.println("Get row count sheetname : "+sheetName);
			rowCount=wb.getSheet(sheetName).getLastRowNum();
			log.info("The number of Rows : "+rowCount);
		} catch (Exception e) {
			log.error("Error finding rows"+e.toString());
			e.printStackTrace();
		}
		return rowCount;
	}
	
	//-----------------------------------------------------------GETTING COLUMN COUNT------------------------------------------------------------------------------------------------------------------------//
	
	public int getColumnCount(String sheetName) {
		int columnCount=0;
		try {
			columnCount = wb.getSheet(sheetName).getRow(0).getLastCellNum();
			log.info("The number of Columns : "+columnCount);
		} catch (Exception e) {
			log.error("Error finding columns"+e.toString());
			e.printStackTrace();
		}
		return columnCount;
	}
	
	//-----------------------------------------------------------GETTING DATA FROM EXCEL------------------------------------------------------------------------------------------------------------------------//
	
	public String getCellValue(String sheetName, String colName,int rowNo) {
		int colNo=0;
		String cellValue =null;
		try {
			Sheet sh = wb.getSheet(sheetName);
			Row firstRow = sh.getRow(0);
			for (int i = 0; i < firstRow.getLastCellNum(); i++) {
				if(firstRow.getCell(i).getStringCellValue().equalsIgnoreCase(colName)) {
					colNo=i;
				}
			}
			sheet = wb.getSheet(sheetName);
			row=sheet.getRow(rowNo);
			if(row==null)
				return "";
			cell=row.getCell(colNo);
			if(cell==null)
				return "";
			if(cell.getCellType()==CellType.STRING) {
				cellValue = cell.getStringCellValue();
			}
			else if(cell.getCellType()==CellType.NUMERIC) {
				cellValue=String.valueOf((int)cell.getNumericCellValue());
			}
			else if(cell.getCellType()==CellType.BLANK) {
				return "";
			}
			else
				 return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
		}
		return cellValue;
	}
	
	//-----------------------------------------------------------GETTING DATA FROM EXCEL------------------------------------------------------------------------------------------------------------------------//
	
	public String getCellValue(String sheetName, int rowNo,int colNo) {
		String cellValue =null;
		try {
			sheet = wb.getSheet(sheetName);
			row=sheet.getRow(rowNo);
			if(row==null)
				return "";
			cell=row.getCell(colNo);
			if(cell==null)
				return "";
			if(cell.getCellType()==CellType.STRING) {
				cellValue = cell.getStringCellValue();
			}
			else if(cell.getCellType()==CellType.NUMERIC) {
				cellValue=String.valueOf((int)cell.getNumericCellValue());
			}
			else if(cell.getCellType()==CellType.BLANK) {
				return "";
			}
			else
				 return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
		}

		return cellValue;
	}
	
	//-----------------------------------------------------------SETTING THE DATA IN TO EXCEL------------------------------------------------------------------------------------------------------------------------//
	
	public boolean setCellValue(String sheetName, String colName,int rowNo,String data) {
		int colNo=0;
		try {
			fis=new FileInputStream(path);
			wb=WorkbookFactory.create(fis);
			sheet = wb.getSheet(sheetName);
			Row firstRow = sheet.getRow(0);
			for (int i = 0; i < firstRow.getLastCellNum(); i++) {
				if(firstRow.getCell(i).getStringCellValue().equals(colName)) {
					colNo=i;
				}
			}
			row = sheet.getRow(rowNo);
			if(row==null) {
				row=sheet.createRow(rowNo);
			}
			cell = row.getCell(colNo);
			if(cell==null) {
				cell = row.createCell(colNo);
			}
			cell.setCellValue(data);
			fos=new FileOutputStream(path);
			wb.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Data Written");
		return true;
	}
}
