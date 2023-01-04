package dao.impl;

import dao.TagsDao;
import model.Tags;
import utils.BaseDaoUtil;

import java.util.ArrayList;
import java.util.List;

public class TagsDaoImpl extends BaseDaoUtil implements TagsDao {
    @Override
    public List<Tags> findAll() {
        List<Tags> tagsList = new ArrayList<>();
        String sql = "SELECT * FROM tags";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Tags tags = new Tags();
                tags.setTagsId(rs.getInt("tagsId"));
                tags.setName(rs.getString("name"));
                tagsList.add(tags);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tagsList;
    }
}
