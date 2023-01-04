package model;

import java.sql.Date;

public class Article implements java.io.Serializable {
    private int userId;
    private int articleId;
    private String title;
    private String tags;
    private String author;
    private Date date;
    private String picture;
    private String content;

    public Article() {
    }


    public Article(int userId, int articleId, String title, String tags, String author, Date date, String picture, String content) {
        this.userId = userId;
        this.articleId = articleId;
        this.title = title;
        this.tags = tags;
        this.author = author;
        this.date = date;
        this.picture = picture;
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
