package practice;

import org.testng.annotations.*;
import com.pygacrm.genericutilities.ExcelUtility;
import com.pygacrm.genericutilities.FileUtility;
public class DataProvider1 {
	@Test(dataProvider = "a")
	public void data(String vendor,String contact) {
		System.out.println(vendor+"  "+contact);
	}

	@DataProvider
	public Object[][] a() throws Throwable {
		ExcelUtility elib= new ExcelUtility();
		FileUtility flib= new FileUtility();
		String expath = flib.getPathFromPropertiesFile("testdata");
		Object obj[][]=new Object[3][2];
		obj[0][0]=elib.getExcelDataById(expath, "DataProvider", "tc01", "Vendor name");
		obj[0][1]=elib.getExcelDataById(expath, "DataProvider", "tc01", "Contact Name");
		obj[1][0]=elib.getExcelDataById(expath, "DataProvider", "tc02", "Vendor name");
		obj[1][1]=elib.getExcelDataById(expath, "DataProvider", "tc02", "Contact Name");
		obj[2][0]=elib.getExcelDataById(expath, "DataProvider", "tc03", "Vendor name");
		obj[2][1]=elib.getExcelDataById(expath, "DataProvider", "tc03", "Contact Name");
		return obj;
		
	}
}
