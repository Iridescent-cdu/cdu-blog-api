package service;

import model.Login;

import java.sql.SQLException;

public interface LoginService {
    /**
     * 验证密码
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    Boolean validatePassword(String username, String password) throws SQLException;

    /**
     * 获取用户信息对象
     *
     * @param username
     * @return
     * @throws SQLException
     */
    Login getUserLoginInfo(String username) throws SQLException;
}
