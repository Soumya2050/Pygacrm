package practice;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.pygacrm.genericutilities.ExcelUtility;
import com.pygacrm.genericutilities.FileUtility;

public class Aa {
public static void main(String[] args) throws Throwable {
	FileUtility p= new FileUtility();
	
	String excelpath=p.getPathFromPropertiesFile("testScript");
	String vend=Aa.getExcelDataById(excelpath, "System", "tc_04", "Vedor name");
	String prod=Aa.getExcelDataById(excelpath, "System", "tc_04", "Product Name");
	System.out.println(vend+" "+prod);
}
	public static String getExcelDataById(String path,String sheetName, String testid,String columnHeader) throws Throwable, IOException {
		FileInputStream fin= new FileInputStream(path);
		Workbook wb = WorkbookFactory.create(fin);
		Sheet sheet = wb.getSheet(sheetName);
		int rowCount=sheet.getLastRowNum();
		int testrowno = 0;
		for (int i = 0; i <= rowCount; i++) {
			String acttestId = sheet.getRow(i).getCell(0).toString();
			if (acttestId.equalsIgnoreCase(testid)) {
				break;
			}
			testrowno++;
		}
		int cellCount=sheet.getRow(testrowno-1).getLastCellNum();
		int testcolno=0;
		for (int i = 0; i <cellCount ; i++) {
			String actcolHeader = sheet.getRow(testrowno-1).getCell(i).toString();
			if (actcolHeader.equalsIgnoreCase(columnHeader)) {
				break;
			}
			testcolno++;
		}
		
		String data = sheet.getRow(testrowno).getCell(testcolno).toString();
		wb.close();
		return data;	
		
	}

}
