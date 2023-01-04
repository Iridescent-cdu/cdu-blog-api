package model.response;

public class ResLogin {
    private String sessionId;
    private int userId;

    public ResLogin(String sessionId, int userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

}
