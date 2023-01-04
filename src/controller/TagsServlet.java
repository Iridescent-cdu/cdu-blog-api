package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import service.TagsService;
import service.impl.TagsServiceImpl;
import model.Tags;
import utils.ResponseResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/tags")
public class TagsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TagsService tagsService = new TagsServiceImpl();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/json");
        PrintWriter out = resp.getWriter();
        Gson gson = new GsonBuilder().create();
        List<Tags> tagsList = tagsService.findAll();
        ResponseResultUtil responseResultUtil = new ResponseResultUtil<List<Tags>>(true, 200, tagsList, "success");
        String jsonString = gson.toJson(responseResultUtil);
        out.println(jsonString);

    }
}
