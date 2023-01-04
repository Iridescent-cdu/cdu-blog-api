package service;

import model.Article;

import java.sql.SQLException;
import java.util.List;

public interface ArticleService {
    //1.单查
    Article getSingleArticle(int articleId) throws SQLException;

    //2.全查
    List<Article> getAllArticles(int userId) throws SQLException;

    //3.添加
    Boolean postNewArticle(int userId, Article article);

    //4.修改
    Boolean updateArticle(Article article);

    //5.删除
    Boolean deleteArticle(int articleId);

    //6.获取首页数据
    List<Article> getHomeArticles() throws SQLException;

    List<Article> findByTags(String tags);
}
