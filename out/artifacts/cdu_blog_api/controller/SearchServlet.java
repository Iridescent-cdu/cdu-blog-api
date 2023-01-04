package controller;

import com.google.gson.Gson;
import model.Article;
import service.ArticleService;
import service.impl.ArticleServiceImpl;
import utils.ResponseResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        StringBuffer stringBuffer = new StringBuffer("");
        BufferedReader bufferedReader = req.getReader();
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            stringBuffer.append(str);
        }
        str = stringBuffer.toString();
        Gson gson = new Gson();
        Article article = gson.fromJson(str, Article.class);
        ArticleService articleService = new ArticleServiceImpl();
//        List<Article> articleList=articleService.findAll();
        List<Article> articleList = articleService.findByTags(article.getTags());
        ResponseResultUtil responseResultUtil = new ResponseResultUtil<List<Article>>(true, 200, articleList, "success");

        String jsonString = gson.toJson(responseResultUtil);
        resp.getWriter().write(jsonString);
    }
}