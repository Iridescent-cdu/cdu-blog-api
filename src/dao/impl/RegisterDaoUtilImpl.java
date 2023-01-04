package dao.impl;

import dao.RegisterDao;
import model.User;
import utils.BaseDaoUtil;

public class RegisterDaoUtilImpl extends BaseDaoUtil implements RegisterDao {
    @Override
    public int insert(User user) {
        //定义新用户默认数据
        String nickname = user.getUsername();

        String email = "";
        String selfIntro = "Hi! I'm " + nickname;
        String avatar = "https://cdu-blog-api.oss-cn-chengdu.aliyuncs.com/logo/Default%20Profile%20Picture.png";
        int rows = 0;
        String sql = "INSERT INTO user(username,password,nickname,email,selfIntro,avatar) VALUES(?,?,?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, nickname);
            pstmt.setString(4, email);
            pstmt.setString(5, selfIntro);
            pstmt.setString(6, avatar);

            rows = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
}
