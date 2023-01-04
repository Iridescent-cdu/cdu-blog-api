package dao.impl;

import dao.LoginDao;
import model.Login;
import utils.JDBCUtil;

import java.sql.*;

public class LoginDaoImpl implements LoginDao {
    @Override
    public Login findByUsername(String username) throws SQLException {
        //1.定义查询sql语句并执行
        String sql = "select * from user where username = ?";
        ResultSet rs = JDBCUtil.dynamicExcute(sql, username);
        //2.创建login对象被赋值
        Login login = new Login();
        if (rs.next()) {
            login.setUserId(rs.getInt("userId"));
            login.setUsername(rs.getString("username"));
            login.setPassword(rs.getString("password"));

        }
        return login;
    }
}
