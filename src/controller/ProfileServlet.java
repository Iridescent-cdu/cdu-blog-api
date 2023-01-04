package controller;

import com.google.gson.Gson;
import model.UserInfo;
import model.response.ResProfile;
import service.impl.ProfileServiceImpl;
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

@WebServlet(name = "profileServlet", value = "/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 固定代码
         */
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        //1. 获取session，从session中获取userId
        HttpSession httpSession = req.getSession();
        String realSessionId = httpSession.getId();
//        String sessionId = String.valueOf(req.getParameter("sessionId"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        //2. 使用ProfileServiceImpl查询数据，最后利用ResponseResultUtil泛型工具类拼接返回的数据格式
//        if (realSessionId.equals(sessionId)) {
            try {
                ProfileServiceImpl profileServiceImpl = new ProfileServiceImpl();
                ResProfile resProfile = profileServiceImpl.getUserProfile(userId);
                ResponseResultUtil responseResultUtil = new ResponseResultUtil<ResProfile>(true, 200, resProfile, "success");
                String resProfileJSONString = gson.toJson(responseResultUtil);
                out.write(resProfileJSONString);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//        }
    }

    /**
     * 修改用户信息
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        //7.将str转化为UserInfo类型的对象
        UserInfo userInfo = gson.fromJson(str, UserInfo.class);

        ProfileServiceImpl profileService = new ProfileServiceImpl();
        HttpSession httpSession = req.getSession();
        String realSessionId = httpSession.getId();
        String sessionId = String.valueOf(req.getParameter("sessionId"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        if (realSessionId.equals(sessionId)) {
            Boolean postResult = profileService.postUserInfo(userId, userInfo);
            ResponseResultUtil responseResultUtil = new ResponseResultUtil<Boolean>(true, 200, postResult, "success");
            String postResultJSONString = gson.toJson(responseResultUtil);
            out.write(postResultJSONString);
        }
    }

    /**
     * 修改用户头像
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        //7.将str转化为userInfo类型的对象
        UserInfo userInfo = gson.fromJson(str, UserInfo.class);
        ProfileServiceImpl profileService = new ProfileServiceImpl();
        HttpSession httpSession = req.getSession();
        String realSessionId = httpSession.getId();
        String sessionId = String.valueOf(req.getParameter("sessionId"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        if (realSessionId.equals(sessionId)) {
            Boolean updateResult = profileService.updateAvatar(userId, userInfo.getAvatar());
            ResponseResultUtil responseResultUtil = new ResponseResultUtil<Boolean>(true, 200, updateResult, "success");
            String updateResultJSONString = gson.toJson(responseResultUtil);
            out.write(updateResultJSONString);
        }

    }
}
