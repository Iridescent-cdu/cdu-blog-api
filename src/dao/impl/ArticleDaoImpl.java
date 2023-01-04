package dao.impl;

import dao.ArticleDao;
import model.Article;
import utils.BaseDaoUtil;
import utils.JDBCUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleDaoImpl extends BaseDaoUtil implements ArticleDao {
    @Override
    public Article findArticleById(int articleId) throws SQLException {
        //1.定义查询sql语句并执行
        String sql = "select * from article where articleId = ?";
        ResultSet rs = JDBCUtil.dynamicExcute(sql, articleId);
        //2.创建article对象被赋值
        Article article = new Article();
        if (rs.next()) {
            article.setUserId(rs.getInt("userId"));
            article.setArticleId(rs.getInt("articleId"));
            article.setTitle(rs.getString("title"));
            article.setAuthor(rs.getString("author"));
            article.setTags(rs.getString("tags"));
            article.setDate(rs.getDate("date"));
            article.setPicture(rs.getString("picture"));
            article.setContent(rs.getString("content"));
        }
        return article;
    }

    @Override
    public List<Article> findAllArticles(int userId) throws SQLException {
        //1.定义查询sql语句并执行
        String sql = "select * from article where userId = ?";
        ResultSet rs = JDBCUtil.dynamicExcute(sql, userId);

        //2.创建articleList表，循环遍历结果集向表中添加article
        List articleList = new ArrayList<Article>();
        while (rs.next()) {
            Article article = new Article();
            article.setUserId(rs.getInt("userId"));
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
    public Boolean postNewArticle(int userId, Article article) {
        //1.使用userId获取当前作者信息
//        String userSql = "select nickname from user where userId = ?";
//        ResultSet userRs = JDBCUtil.dynamicExcute(userSql, userId);
//        if(userRs.next()){
//            author= userRs.getString("nickname");
//        }

        //1.准备新文章需要的数据
        String title = article.getTitle();
        String author = article.getAuthor();
        String tags = article.getTags();
        String picture = article.getPicture();
        String content = article.getContent();
        Date date = new Date();
        //将时间转换成Timestamp类型，这样便可以存入到Mysql数据库中
        Timestamp timestamp = new Timestamp(date.getTime());

        //2.增加新的文章
        String articleSql = "insert into article (userId,title,author,tags,date,picture,content) values (?,?,?,?,?,?,?)";
        ResultSet articleRs = JDBCUtil.dynamicExcute(articleSql, userId, title, author, tags, timestamp, picture, content);
        return true;
    }

    @Override
    public Boolean updateArticle(Article article) {
        //1.准备新文章需要的数据
        int articleId = article.getArticleId();
        String title = article.getTitle();
        String tags = article.getTags();
        String picture = article.getPicture();
        String content = article.getContent();

        //2.执行修改
        String sql = "update article set title=?,tags=?,picture=?,content=? where articleId = ?";
        ResultSet rs = JDBCUtil.dynamicExcute(sql, title, tags, picture, content, articleId);
        return true;
    }

    @Override
    public Boolean deleteArticle(int articleId) {
        //1.定义delete sql语句并执行
        String sql = "delete from article where articleId = ?";
        ResultSet rs = JDBCUtil.dynamicExcute(sql, articleId);
        return true;
    }

    @Override
    public List<Article> findHomeArticles() throws SQLException {
        //1.定义delete sql语句并执行
        String sql = "select * from article order by articleId desc limit 9";
        ResultSet rs = JDBCUtil.dynamicExcute(sql);
        //2.返回List数据
        List articleList = new ArrayList<Article>();
        while (rs.next()) {
            Article article = new Article();
            article.setUserId(rs.getInt("userId"));
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
    public List<Article> findByTags(String tags) {
        List<Article> articleList = new ArrayList<>();
        String sql = "SELECT *FROM  article WHERE tags like CONCAT('%',?,'%')";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tags);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Article article = new Article();
                article.setUserId(rs.getInt("userId"));
                article.setArticleId(rs.getInt("articleId"));
                article.setTags(rs.getString("tags"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                article.setAuthor(rs.getString("author"));
                article.setDate(rs.getDate("date"));
                article.setPicture(rs.getString("picture"));
                articleList.add(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleList;
    }
}
