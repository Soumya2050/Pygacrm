
package practice;

import java.io.IOException;

import com.pygacrm.genericutilities.ExcelUtility;
import com.pygacrm.genericutilities.FileUtility;

public class Excel {

	public static void main(String[] args) throws IOException, Throwable {
		FileUtility f=new FileUtility();
		String path = f.getPathFromPropertiesFile("testScript");
		ExcelUtility e= new ExcelUtility();
		String data = e.getExcelDataById(path, "contact", "tc_06"	,"ContactLastName");
		System.out.println(data);
		e.setdataToExcel(path, "contact", "tc_06"	,"Status", "pass");
	}
}
