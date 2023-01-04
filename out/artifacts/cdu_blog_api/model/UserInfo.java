package model;

public class UserInfo implements java.io.Serializable {
    private int userId;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String selfIntro;
    private String avatar;

    public UserInfo() {
    }

    public UserInfo(int userId, String username, String password, String nickname, String email, String selfIntro, String avatar) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.selfIntro = selfIntro;
        this.avatar = avatar;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSelfIntro() {
        return selfIntro;
    }

    public void setSelfIntro(String selfIntro) {
        this.selfIntro = selfIntro;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
