package service.impl;

import dao.impl.TagsDaoImpl;
import model.Tags;
import service.TagsService;

import java.util.List;

public class TagsServiceImpl implements TagsService {

    @Override
    public List<Tags> findAll() {
        TagsDaoImpl tagsDao = new TagsDaoImpl();
        return tagsDao.findAll();
    }

}
