package model.response;

import model.Article;

import java.util.List;

public class ResHomeArticle {
    private List<Article> banners;
    private List<Article> main;
    private List<Article> footer;

    public ResHomeArticle(List<Article> banners, List<Article> main, List<Article> footer) {
        this.banners = banners;
        this.main = main;
        this.footer = footer;
    }
}
