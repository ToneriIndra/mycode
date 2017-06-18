package com.zjxjd.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjxjd.manage.mapper.ContentMapper;
import com.zjxjd.manage.pojo.Content;
@Service
@Transactional
public class ContentService extends BaseService<Content> {
    @Autowired
    private ContentMapper contentMapper;
    public PageInfo<Content> queryList(Long categoryId, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        return new PageInfo(this.contentMapper.queryListByUpdateDesc(categoryId));
    }

}
