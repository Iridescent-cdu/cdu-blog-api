package dao;

import model.Login;

import java.sql.SQLException;

public interface LoginDao {
    /**
     * 根据username查询出用户名、密码和ID
     *
     * @return
     */
    Login findByUsername(String username) throws SQLException;
}
