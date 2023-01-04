package model;

public class Tags implements java.io.Serializable {
    private int tagsId;
    private String name;

    public Tags(int tagsId, String name) {
        this.tagsId = tagsId;
        this.name = name;
    }

    public Tags() {
    }

    public int getTagsId() {
        return tagsId;
    }

    public void setTagsId(int tagsId) {
        this.tagsId = tagsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
