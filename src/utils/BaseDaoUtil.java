package utils;

import java.sql.*;

public class BaseDaoUtil {
    //链接数据库 添加用户
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/cdu-blog?serverTimezone=GMT";
    String dbUsername = "root";
    String dbPwd = "admin";
    protected Connection conn = null;
    protected Statement stmt = null;
    protected PreparedStatement pstmt = null;
    protected ResultSet rs = null;

    public BaseDaoUtil() {
        connect();
    }

    public void connect() {
        try {
            //1.加载驱动程序
            Class.forName(driver).newInstance();
            //2.链接数据库
            conn = DriverManager.getConnection(url, dbUsername, dbPwd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
            if (pstmt != null && !pstmt.isClosed()) {
                pstmt.close();
            }
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
