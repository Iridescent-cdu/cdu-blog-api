package dao.impl;

import dao.ProfileDao;
import model.Article;
import model.UserInfo;
import utils.JDBCUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileDaoImpl implements ProfileDao {
    @Override
    public List<Article> findSomeArticles(int userId) throws SQLException {
        //1.定义查询sql语句并执行
        String sql = "select * from article where userId = ? order by articleId desc limit 5 ";
        ResultSet rs = JDBCUtil.dynamicExcute(sql, userId);

        //2.创建articleList表，循环遍历结果集向表中添加article
        List articleList = new ArrayList<Article>();
        while (rs.next()) {
            Article article = new Article();
            article.setArticleId(rs.getInt("articleId"));
            article.setTitle(rs.getString("title"));
            article.setAuthor(rs.getString("author"));
            article.setTags(rs.getString("tags"));
            article.setDate(rs.getDate("date"));
            article.setPicture(rs.getString("picture"));
            article.setContent(rs.getString("content"));
            articleList.add(article);
        }
        return articleList;
    }

    @Override
    public UserInfo findUserInfo(int userId) throws SQLException {
        //1.定义查询sql语句并执行
        String sql = "select * from user where userId = ?";
        ResultSet rs = JDBCUtil.dynamicExcute(sql, userId);

        //2.创建userInfo对象并赋值
        UserInfo userInfo = new UserInfo();
        if (rs.next()) {
            userInfo.setUserId(rs.getInt("userId"));
            userInfo.setNickname(rs.getString("nickname"));
            userInfo.setEmail(rs.getString("email"));
            userInfo.setAvatar(rs.getString("avatar"));
            userInfo.setSelfIntro(rs.getString("selfIntro"));
        }
        return userInfo;
    }

    @Override
    public Boolean postUserInfo(int userId, UserInfo userInfo) {
        //1.创建数据源
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        String nickname = userInfo.getNickname();
        String email = userInfo.getEmail();
        String selfIntro = userInfo.getSelfIntro();
        //2.执行修改
        String sql = "update user set username=?,password=?,nickname=?,email=?,selfIntro=? where userId=?";
        ResultSet rs = JDBCUtil.dynamicExcute(sql, username, password, nickname, email, selfIntro, userId);
        return true;
    }

    @Override
    public Boolean updateAvatar(int userId, String avatar) {
        String sql = "update user set avatar=? where userId=?";
        ResultSet rs = JDBCUtil.dynamicExcute(sql, avatar, userId);
        return true;
    }
}
