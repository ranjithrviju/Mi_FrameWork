package utilities;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FromExcel {
	private static Logger log=Logger.getLogger("From Excel");
	
	//--------------------------------------------------------------TO GET LIST OF DATA FROM EXCEL-------------------------------------------------------------------------------------------------//
	
	public static List<String> getDataFromExcel(String testCaseName, String sheetName) {
		ExcelUtility excel = new ExcelUtility(System.getProperty("user.dir")+"\\excel\\data.xlsx");
		int totalRows = excel.getRowCount(sheetName);
		log.info("Total Rows in the Excel File is : "+totalRows);

		//--------------------Finding TestCase----------------------------//

		int testCaseRow = 0;
		for (testCaseRow = 0; testCaseRow < totalRows; testCaseRow++) {
			String testCase = excel.getCellValue(sheetName, testCaseRow, 0);
			if(testCase.equals(testCaseName))
				break;
		}
		log.info("The row number of test case "+testCaseName+" is : "+testCaseRow);

		//---------------Finding number of rows in TestCase----------------//

		int dataRowStart=testCaseRow+2;
		int rows=0;
		while(!excel.getCellValue(sheetName, dataRowStart+rows, 0).equals("")) {
			rows++;
		}
		log.info("Total Number of rows of data in TestCase : "+rows);

		//----------------Finding number of columns in TestCase---------------------//

		int dataColStart=testCaseRow+1;
		int cols=1;
		while(!excel.getCellValue(sheetName, dataColStart, cols).equals("")) {
			cols++;
		}
		log.info("Total Number of columns in TestCase : "+cols);

		//---------------------Getting the data of TestCase------------------//

		List<String> data = new ArrayList<>();
		for (int row = dataRowStart; row < dataRowStart+rows; row++) {
			for (int col = 0; col < cols; col++) {
				data.add(excel.getCellValue(sheetName, row, col));
			}
		}
		return data;
	}
}
