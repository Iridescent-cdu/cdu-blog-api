package controller;

import com.google.gson.Gson;
import model.Login;
import model.response.ResLogin;
import service.impl.LoginServiceImpl;
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

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 处理退出登录逻辑
         */
        resp.setContentType("application/json;charset=UTF-8");

        PrintWriter out = resp.getWriter();
        //1.获取session并调用invalidate使session失效
        HttpSession httpSession = req.getSession();
        httpSession.invalidate();
        //2.响应给客户端，通知退出登录成功
        ResponseResultUtil responseResultUtil = new ResponseResultUtil<Boolean>(true, 200, true, "退出登录成功");
        Gson gson = new Gson();
        String signOutResultJSONString = gson.toJson(responseResultUtil);
        out.write(signOutResultJSONString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * 处理登录逻辑
         */
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
//        resp.setHeader("Access-Control-Expose-Headers", "Set-Cookie");
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
        Login login = gson.fromJson(str, Login.class);

        //8.调用validatePassword验证结果
        try {
            LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
            //获取用户信息对象
            Login userLoginInfo = loginServiceImpl.getUserLoginInfo(login.getUsername());
            //获取验证结果
            Boolean validateResult = loginServiceImpl.validatePassword(login.getUsername(), login.getPassword());

            //9.根据结果返回session和处理结果
            if (validateResult) {
                //校验成功
                HttpSession httpSession = req.getSession();
                httpSession.setMaxInactiveInterval(0);
                //设置一个标识，每次请求前判断该标识来判断是否已登录
//                httpSession.setAttribute("logined", true);
                //为该session对象绑定userId，以便后续请求直接通过session获取用户id
//                httpSession.setAttribute("userId", userLoginInfo.getUserId());

                /**
                 * 用户登录鉴权策略：简单的方案
                 * 1.验证客户端的sessionId是否与服务端sessionId相同：在第一次登录时将sessionId响应给前端，存入localStorage
                 * 2.在需要登录才能使用的接口，验证sessionId
                 * 3.userId需要前端单独传
                 */
                String JSESSIONID = httpSession.getId();
                ResLogin resLogin = new ResLogin(JSESSIONID, userLoginInfo.getUserId());
                //使用工具泛型类包裹响应的数据
                ResponseResultUtil responseResultUtil = new ResponseResultUtil<ResLogin>(true, 200, resLogin, "登录成功");
                //将登录结果转为JSON并返回
                String loginResultJSONString = gson.toJson(responseResultUtil);
                out.write(loginResultJSONString);
            } else {
                //校验失败
                ResponseResultUtil responseResultUtil = new ResponseResultUtil<Boolean>(true, 200, false, "登录失败");
                String loginResultJSONString = gson.toJson(responseResultUtil);
                out.write(loginResultJSONString);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
