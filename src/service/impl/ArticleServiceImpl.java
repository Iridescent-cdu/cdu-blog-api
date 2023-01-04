package service.impl;

import dao.impl.ArticleDaoImpl;
import model.Article;
import service.ArticleService;

import java.sql.SQLException;
import java.util.List;

public class ArticleServiceImpl implements ArticleService {
    @Override
    public Article getSingleArticle(int articleId) throws SQLException {
        ArticleDaoImpl articleDao = new ArticleDaoImpl();
        Article article = articleDao.findArticleById(articleId);
        return article;
    }

    @Override
    public List<Article> getAllArticles(int userId) throws SQLException {
        ArticleDaoImpl articleDao = new ArticleDaoImpl();
        List<Article> articleList = articleDao.findAllArticles(userId);
        return articleList;
    }

    @Override
    public Boolean postNewArticle(int userId, Article article) {
        ArticleDaoImpl articleDao = new ArticleDaoImpl();
        Boolean result = articleDao.postNewArticle(userId, article);
        return result;
    }

    @Override
    public Boolean updateArticle(Article article) {
        ArticleDaoImpl articleDao = new ArticleDaoImpl();
        Boolean result = articleDao.updateArticle(article);
        return result;
    }

    @Override
    public Boolean deleteArticle(int articleId) {
        ArticleDaoImpl articleDao = new ArticleDaoImpl();
        Boolean result = articleDao.deleteArticle(articleId);
        return result;
    }

    @Override
    public List<Article> getHomeArticles() throws SQLException {
        ArticleDaoImpl articleDao = new ArticleDaoImpl();
        List<Article> articleList = articleDao.findHomeArticles();
        return articleList;
    }

    @Override
    public List<Article> findByTags(String tags) {
        ArticleDaoImpl articleDao = new ArticleDaoImpl();
        return articleDao.findByTags(tags);
    }

}
