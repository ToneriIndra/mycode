package com.zjxjd.manage.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.zjxjd.manage.pojo.Content;

public interface ContentMapper extends Mapper<Content> {

   public List<Content> queryListByUpdateDesc(Long categoryId);
   
}
