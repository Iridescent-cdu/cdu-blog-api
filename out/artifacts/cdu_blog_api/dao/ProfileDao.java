package dao;

import model.Article;
import model.Tags;
import model.UserInfo;

import java.sql.SQLException;
import java.util.List;

public interface ProfileDao {
    /**
     * 查询五条个人文章
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    List<Article> findSomeArticles(int userId) throws SQLException;

    /**
     * 查询个人信息
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    UserInfo findUserInfo(int userId) throws SQLException;

    /**
     * 修改用户信息
     *
     * @param userId
     * @param userInfo
     * @return
     */
    Boolean postUserInfo(int userId, UserInfo userInfo);

    /**
     * 修改用户头像
     *
     * @param userId
     * @param avatar
     * @return
     */
    Boolean updateAvatar(int userId, String avatar);

    interface TagsDao {
        List<Tags> findAll();
    }
}
