package utilities;
import java.util.ArrayList;
import java.util.List;

public class FromExcel {
	
	//--------------------------------------------------------------TO GET LIST OF DATA FROM EXCEL-------------------------------------------------------------------------------------------------//
	
	public static List<String> getDataFromExcel(String testCaseName, String sheetName) {
		ExcelUtility excel = new ExcelUtility(System.getProperty("user.dir")+"\\excel\\data.xlsx");
		int totalRows = excel.getRowCount(sheetName);
		System.out.println("Total Rows in the Excel File is : "+totalRows);

		//--------------------Finding TestCase----------------------------//

		int testCaseRow = 0;
		for (testCaseRow = 0; testCaseRow < totalRows; testCaseRow++) {
			String testCase = excel.getCellValue(sheetName, testCaseRow, 0);
			if(testCase.equals(testCaseName))
				break;
		}
		System.out.println("The row number of test case "+testCaseName+" is : "+testCaseRow);

		//---------------Finding number of rows in TestCase----------------//

		int dataRowStart=testCaseRow+2;
		int rows=0;
		while(!excel.getCellValue(sheetName, dataRowStart+rows, 0).equals("")) {
			rows++;
		}
		System.out.println("Total Number of rows of data in TestCase : "+rows);

		//----------------Finding number of columns in TestCase---------------------//

		int dataColStart=testCaseRow+1;
		int cols=1;
		while(!excel.getCellValue(sheetName, dataColStart, cols).equals("")) {
			cols++;
		}
		System.out.println("Total Number of columns in TestCase : "+cols);

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
