package model.response;

public class ResProfile<T, K> {
    private T userInfo;
    private K articles;

    public ResProfile(T userInfo, K articles) {
        this.userInfo = userInfo;
        this.articles = articles;
    }
}
