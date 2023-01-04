package service.impl;

import dao.impl.ProfileDaoImpl;
import model.Article;
import model.UserInfo;
import model.response.ResProfile;
import service.ProfileService;

import java.sql.SQLException;
import java.util.List;

public class ProfileServiceImpl implements ProfileService {
    @Override
    public ResProfile getUserProfile(int userId) throws SQLException {
        //1.使用ProfileDaoImpl获取userInfo和articleList数据
        ProfileDaoImpl profileDaoImpl = new ProfileDaoImpl();
        UserInfo userInfo = profileDaoImpl.findUserInfo(userId);
        List<Article> articleList = profileDaoImpl.findSomeArticles(userId);

        //2.使用ResProfile组合两个数据源
        ResProfile resProfile = new ResProfile<UserInfo, List<Article>>(userInfo, articleList);

        return resProfile;
    }

    @Override
    public Boolean postUserInfo(int userId, UserInfo userInfo) {
        ProfileDaoImpl profileDaoImpl = new ProfileDaoImpl();
        profileDaoImpl.postUserInfo(userId, userInfo);
        return true;
    }

    @Override
    public Boolean updateAvatar(int userId, String avatar) {
        ProfileDaoImpl profileDaoImpl = new ProfileDaoImpl();
        profileDaoImpl.updateAvatar(userId, avatar);
        return true;
    }
}
