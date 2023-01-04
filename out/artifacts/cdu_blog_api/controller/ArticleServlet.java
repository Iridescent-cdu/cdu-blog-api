package controller;

import com.google.gson.Gson;
import model.Article;
import service.impl.ArticleServiceImpl;
import utils.ResponseResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "articleServlet", value = "/article")
public class ArticleServlet extends HttpServlet {
    /**
     * 处理单查和全查
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 固定代码
         */
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        //前端使用isFindAll作为标志区分
        Boolean isFindAll = Boolean.valueOf(req.getParameter("isFindAll"));

        ArticleServiceImpl articleService = new ArticleServiceImpl();

        if (!isFindAll) {
            /**
             * 处理单查
             */
            try {
                int articleId = Integer.parseInt(req.getParameter("articleId"));
                Article article = articleService.getSingleArticle(articleId);
                ResponseResultUtil responseResultUtil = new ResponseResultUtil<Article>(true, 200, article, "success");
                String singleArticleJSONString = gson.toJson(responseResultUtil);
                out.write(singleArticleJSONString);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            /**
             * 处理全查
             */
            try {
                HttpSession httpSession = req.getSession();
                //int userId = (int) httpSession.getAttribute("userId");
                String realSessionId = httpSession.getId();
                int userId = Integer.parseInt(req.getParameter("userId"));
                String sessionId = String.valueOf(req.getParameter("sessionId"));
                if (realSessionId.equals(sessionId)) {
                    List<Article> articleList = articleService.getAllArticles(userId);
                    ResponseResultUtil responseResultUtil = new ResponseResultUtil<List<Article>>(true, 200, articleList, "success");
                    String allArticlesJSONString = gson.toJson(responseResultUtil);
                    out.write(allArticlesJSONString);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 处理新增文章
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 固定代码
         */
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        //1.由于请求体数据可能很大，所以servlet标准在设计api的时候要求我们通过输入流来读取
        BufferedReader bufferedReader = req.getReader();
        //2.创建StringBuffer对象来累加存储从请求体读取到的每一行
        StringBuffer stringBuffer = new StringBuffer();
        //3.声明临时变量
        String str = null;
        //4.循环读取
        while ((str = bufferedReader.readLine()) != null) {
            stringBuffer.append(str);
        }
        //5.将stringBuffer转化成String字符串
        str = stringBuffer.toString();

        //6.使用Gson 将JSON字符串转换为java对象 两种创建方式，GsonBuilder功能更加强大
        // Gson gson =new GsonBuilder().create();
        Gson gson = new Gson();
        //7.将str转化为Login类型的对象
        Article article = gson.fromJson(str, Article.class);

        ArticleServiceImpl articleService = new ArticleServiceImpl();
        HttpSession httpSession = req.getSession();
        String realSessionId = httpSession.getId();
        int userId = Integer.parseInt(req.getParameter("userId"));
        String sessionId = String.valueOf(req.getParameter("sessionId"));
        if (realSessionId.equals(sessionId)) {
            Boolean result = articleService.postNewArticle(userId, article);
            ResponseResultUtil responseResultUtil = new ResponseResultUtil<Boolean>(true, 200, result, "success");

            String postResultJSONString = gson.toJson(responseResultUtil);
            out.write(postResultJSONString);
        }
    }

    /**
     * 处理修改文章
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 固定代码
         */
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        //1.由于请求体数据可能很大，所以servlet标准在设计api的时候要求我们通过输入流来读取
        BufferedReader bufferedReader = req.getReader();
        //2.创建StringBuffer对象来累加存储从请求体读取到的每一行
        StringBuffer stringBuffer = new StringBuffer();
        //3.声明临时变量
        String str = null;
        //4.循环读取
        while ((str = bufferedReader.readLine()) != null) {
            stringBuffer.append(str);
        }
        //5.将stringBuffer转化成String字符串
        str = stringBuffer.toString();

        //6.使用Gson 将JSON字符串转换为java对象 两种创建方式，GsonBuilder功能更加强大
        // Gson gson =new GsonBuilder().create();
        Gson gson = new Gson();
        //7.将str转化为Login类型的对象
        Article article = gson.fromJson(str, Article.class);

        ArticleServiceImpl articleService = new ArticleServiceImpl();
        HttpSession httpSession = req.getSession();
        String realSessionId = httpSession.getId();
        String sessionId = String.valueOf(req.getParameter("sessionId"));
        if (realSessionId.equals(sessionId)) {
            Boolean result = articleService.updateArticle(article);
            ResponseResultUtil responseResultUtil = new ResponseResultUtil<Boolean>(true, 200, result, "success");
            String updateResultJSONString = gson.toJson(responseResultUtil);
            out.write(updateResultJSONString);
        }
    }

    /**
     * 处理删除文章
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 固定代码
         */
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        //1.由于请求体数据可能很大，所以servlet标准在设计api的时候要求我们通过输入流来读取
        BufferedReader bufferedReader = req.getReader();
        //2.创建StringBuffer对象来累加存储从请求体读取到的每一行
        StringBuffer stringBuffer = new StringBuffer();
        //3.声明临时变量
        String str = null;
        //4.循环读取
        while ((str = bufferedReader.readLine()) != null) {
            stringBuffer.append(str);
        }
        //5.将stringBuffer转化成String字符串
        str = stringBuffer.toString();

        //6.使用Gson 将JSON字符串转换为java对象 两种创建方式，GsonBuilder功能更加强大
        // Gson gson =new GsonBuilder().create();
        Gson gson = new Gson();
        //7.将str转化为Login类型的对象
        Article article = gson.fromJson(str, Article.class);
        ArticleServiceImpl articleService = new ArticleServiceImpl();
        HttpSession httpSession = req.getSession();
        String realSessionId = httpSession.getId();
        String sessionId = String.valueOf(req.getParameter("sessionId"));
        if (realSessionId.equals(sessionId)) {
            Boolean result = articleService.deleteArticle(article.getArticleId());
            ResponseResultUtil responseResultUtil = new ResponseResultUtil<Boolean>(true, 200, result, "success");
            String deleteResultJSONString = gson.toJson(responseResultUtil);
            out.write(deleteResultJSONString);
        }
    }
}
