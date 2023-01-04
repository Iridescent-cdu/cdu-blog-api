package controller;

import com.google.gson.Gson;
import model.User;
import service.impl.RegisterServiceImpl;
import utils.ResponseResultUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        StringBuffer stringBuffer = new StringBuffer("");
        BufferedReader bufferedReader = req.getReader();
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            stringBuffer.append(str);
        }
        str = stringBuffer.toString();

        Gson gson = new Gson();
        User user = gson.fromJson(str, User.class);

        RegisterServiceImpl registerService = new RegisterServiceImpl();
        if (registerService.add(user)) {
            ResponseResultUtil responseResultUtil = new ResponseResultUtil<Boolean>(true, 200, true, "success");
            String registerResultJSONString = gson.toJson(responseResultUtil);
            out.write(registerResultJSONString);

        } else {
            ResponseResultUtil responseResultUtil = new ResponseResultUtil<Boolean>(true, 200, false, "success");
            String registerResultJSONString = gson.toJson(responseResultUtil);
            out.write(registerResultJSONString);
        }

    }
}