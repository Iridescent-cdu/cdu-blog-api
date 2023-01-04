package utils;

import java.sql.*;

public class JDBCUtil {
    /**
     * 1.创建连接
     *
     * @return
     * @throws Exception
     */
    private static Connection getConnection() throws Exception {
        //1.定义配置信息
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/cdu-blog?serverTimezone=GMT";
        String jdbcUser = "root";
        String jdbcPwd = "admin";
        //2.加载驱动
        Class.forName(driver);
        //3.获取连接对象
        Connection conn = DriverManager.getConnection(url, jdbcUser, jdbcPwd);
        return conn;
    }

    /**
     * 关闭连接
     *
     * @param conn
     * @param ps
     */
    private static void closeResource(Connection conn, PreparedStatement ps) {
        //1.关闭连接对象
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //2.关闭操作sql的对象
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * sql查询通用方法
     *
     * @param sql
     */
    public static ResultSet staticExcute(String sql) {
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            //1.获取数据库的连接
            conn = JDBCUtil.getConnection();
            //2.创建Statement对象
            statement = conn.createStatement();
            //3.执行
            Boolean hasResultSet = statement.execute(sql);
            //如果有结果集则获取结果集
            if (hasResultSet) {
                rs = statement.getResultSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4.资源的关闭，如果要获取结果集不能关闭
            //4.1关闭连接对象
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
            //4.2关闭操作sql的对象
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }
        return rs;
    }

    /**
     * sql增删改通用方法
     *
     * @param sql
     * @param args
     */
    public static ResultSet dynamicExcute(String sql, Object... args) {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            //1.获取数据库的连接
            conn = JDBCUtil.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例对象
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //4.执行
            Boolean hasResultSet = ps.execute();

            //如果有结果集则获取结果集
            if (hasResultSet) {
                rs = ps.getResultSet();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭，如果要获取结果集不能关闭
            //JDBCUtil.closeResource(conn, ps);
        }
        return rs;
    }
}
