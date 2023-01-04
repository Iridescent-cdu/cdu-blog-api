package controller;

import com.google.gson.Gson;
import model.Article;
import model.response.ResHomeArticle;
import service.impl.ArticleServiceImpl;
import utils.ResponseResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "homeServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 固定代码
         */
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();


        try {
            ArticleServiceImpl articleService = new ArticleServiceImpl();
            List<Article> articleList = articleService.getHomeArticles();

            //数据切片
            List<Article> bannersList = articleList.subList(0, 2);
            List<Article> mainList = articleList.subList(2, 5);
            List<Article> footerList = articleList.subList(5, articleList.toArray().length);

            ResHomeArticle resHomeArticle = new ResHomeArticle(bannersList, mainList, footerList);
            ResponseResultUtil responseResultUtil = new ResponseResultUtil<ResHomeArticle>(true, 200, resHomeArticle, "success");
            String homeArticlesJSONString = gson.toJson(responseResultUtil);
            out.write(homeArticlesJSONString);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
