package cn.edu.zucc.personalplan.util;

import java.sql.Connection;
import java.util.Properties;
import java.util.ResourceBundle;

public class DBUtil {
    private static String jdbcUrl;
    private static String dbUser;
    private static String dbPwd;
    private static String Driver;

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("DBconfig");
            Driver = bundle.getString("Driver");
            dbUser = bundle.getString("dbUser");
            dbPwd = bundle.getString("dbPwd");
            jdbcUrl = bundle.getString("jdbcUrl");
            Class.forName(Driver);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws java.sql.SQLException {
        return java.sql.DriverManager.getConnection(jdbcUrl, dbUser, dbPwd);
    }
}
