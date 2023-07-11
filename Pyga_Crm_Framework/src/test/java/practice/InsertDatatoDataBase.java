package practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class InsertDatatoDataBase {

	public static void main(String[] args) throws SQLException {
		Connection con=null;
		

			Driver driverref = new Driver();
			DriverManager.registerDriver(driverref);
			con= DriverManager.getConnection("jdbc:mysql://rmgtestingserver:3333/projects", "root@%", "root");
			Statement statement = con.createStatement();
			String query="insert into project values('TY_Proj_5544','Shiba_Shankar','29/06/2023','Tyss','Created','3')";
			int result=statement.executeUpdate(query);
			if(result==1)
				System.out.println("Data is created");
			else
				System.out.println("data is not created");
		
			con.close();
		
	}
}
