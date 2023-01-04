package dao;

import model.Tags;

import java.util.List;

public interface TagsDao {
    List<Tags> findAll();
}
