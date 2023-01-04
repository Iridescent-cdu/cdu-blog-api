package service;

import model.UserInfo;
import model.response.ResProfile;

import java.sql.SQLException;

public interface ProfileService {

    ResProfile getUserProfile(int userId) throws SQLException;

    Boolean postUserInfo(int userId, UserInfo userInfo);

    Boolean updateAvatar(int userId, String avatar);
}
