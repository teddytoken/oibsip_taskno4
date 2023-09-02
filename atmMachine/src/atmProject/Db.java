package atmProject;

import java.sql.Connection;
import java.sql.DriverManager;

public class Db {
	public static Connection provideConnection() {
		Connection conn = null;
		try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM","root","Snehal@#123");
		}catch(Exception e) {
			e.printStackTrace();
		}
	return conn;
	}
}
