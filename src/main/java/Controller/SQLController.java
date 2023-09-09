package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * @author Vo Hoai Nam 4592
 * @version 1.0 11/2/2023
 * Class 136813, Teacher's name Trung.TT
 * @see https://viettuts.vn/java-jdbc/ket-noi-java-voi-sqlserver
 */
public class SQLController {
    // url = "jdbc:sqlserver://" +serverName + ":1433;DatabaseName=" + dbName +
    // ";encrypt=true;trustServerCertificate=true;
    public static String DB_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=QLDC;encrypt=true;trustServerCertificate=true;";

    public static String USER_NAME = "sa";
    public static String PASSWORD = "123";

    public static void main(String args[]) {
        try {
            // connnect to database 'testdb'
            Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
            // crate statement
            Statement stmt = conn.createStatement();
            // get data from table 'student'
            ResultSet rs = stmt.executeQuery("select * from HoKhau");
            // show data
            while (rs.next()) {
                System.out.println(rs.getString(1) + "  " + rs.getString(2));
            }
            // close connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection(String dbURL, String userName,
            String password) {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(dbURL, userName, password);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }
}