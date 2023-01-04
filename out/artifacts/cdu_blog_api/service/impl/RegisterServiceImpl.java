package service.impl;

import dao.impl.RegisterDaoUtilImpl;
import model.User;
import service.RegisterService;

public class RegisterServiceImpl implements RegisterService {
    RegisterDaoUtilImpl registerDao = new RegisterDaoUtilImpl();

    public boolean add(User user) {
        return registerDao.insert(user) == 1;
    }
}
