package service.impl;

import dao.impl.LoginDaoImpl;
import model.Login;
import service.LoginService;

import java.sql.SQLException;
import java.util.Objects;

public class LoginServiceImpl implements LoginService {
    @Override
    public Boolean validatePassword(String username, String password) throws SQLException {
        //1.创建LoginDaoImpl对象并调用findByUsername方法获取到Login对象
        LoginDaoImpl loginDaoImpl = new LoginDaoImpl();
        Login login = loginDaoImpl.findByUsername(username);
        //2.密码校验并返回校验结果
        return Objects.equals(login.getPassword(), password);
    }

    public Login getUserLoginInfo(String username) throws SQLException {
        //创建LoginDaoImpl对象并调用findByUsername方法获取到Login对象
        LoginDaoImpl loginDaoImpl = new LoginDaoImpl();
        Login login = loginDaoImpl.findByUsername(username);
        return login;
    }
}
