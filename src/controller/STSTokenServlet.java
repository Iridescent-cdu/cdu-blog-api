package controller;

import com.google.gson.Gson;
import model.STSToken;
import service.impl.STSTokenServiceImpl;
import utils.ResponseResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "STSTokenServlet", value = "/sts")
public class STSTokenServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        STSTokenServiceImpl stsTokenServiceImpl = new STSTokenServiceImpl();
        STSToken stsToken = stsTokenServiceImpl.getSTSToken();

        resp.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();

        HttpSession httpSession = req.getSession();
        String realSessionId = httpSession.getId();
        String sessionId = String.valueOf(req.getParameter("sessionId"));
        if (realSessionId.equals(sessionId)) {
            ResponseResultUtil responseResultUtil = new ResponseResultUtil<STSToken>(true, 200, stsToken, "success");
            String stsTokenJSONString = gson.toJson(responseResultUtil);
            PrintWriter out = resp.getWriter();
            out.println(stsTokenJSONString);
        }


    }
}
