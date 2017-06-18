package com.zjxjd.manage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjxjd.manage.pojo.ContentCategory;
import com.zjxjd.manage.service.ContentCategoryService;

@Controller
@RequestMapping("content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;
    //查询商品分类列表
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryContentCatagoryList(
            @RequestParam(value = "id", defaultValue = "0") Long id) {
        try {
            ContentCategory contentCategory = new ContentCategory();
            contentCategory.setParentId(id);
            List<ContentCategory> list = this.contentCategoryService.queryByWhere(contentCategory);
            if (list != null) {
                return ResponseEntity.ok(list);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    //右键添加商品分类
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> savContentCategory(ContentCategory contentCategory) {
       try {
           contentCategory.setStatus(1);
           contentCategory.setSortOrder(1);
           contentCategory.setIsParent(false);
           this.contentCategoryService.save(contentCategory);
           ContentCategory parent = this.contentCategoryService.queryById(contentCategory.getParentId());
           if(!parent.getIsParent()) {
               parent.setIsParent(true);
               this.contentCategoryService.updateSelective(parent);
           }
           return ResponseEntity.ok(contentCategory);
    } catch (Exception e) {
        e.printStackTrace();
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    }
    //重命名商品分类
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory) {
        try {
            this.contentCategoryService.updateSelective(contentCategory);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    //删除商品分类
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteContentCategory(ContentCategory contentCategory) {
        try {
            List<Object> ids = new ArrayList();
            ids.add(contentCategory.getId());
            findAllsubNode(contentCategory.getId(), ids);
            ContentCategory param = new ContentCategory();
            param.setParentId(contentCategory.getParentId());
            List<ContentCategory> list = this.contentCategoryService.queryByWhere(param);
            if(list == null || list.isEmpty()) {
                ContentCategory parent = new ContentCategory();
                parent.setId(parent.getParentId());
                parent.setIsParent(false);
                this.contentCategoryService.updateSelective(parent);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //递归查询所有子节点
    private void findAllsubNode(Long parentId, List<Object> ids) {
        ContentCategory param = new ContentCategory();
        param.setParentId(parentId);
        List<ContentCategory> list = this.contentCategoryService.queryByWhere(param);
        for(ContentCategory contentCategory : list) {
            ids.add(contentCategory.getId());
            if(contentCategory.getIsParent()) {
                findAllsubNode(contentCategory.getId(), ids);
            }
        }
    }
}
