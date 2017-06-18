package com.zjxjd.manage.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjxjd.manage.common.bean.ItemCatResult;
import com.zjxjd.manage.pojo.ItemCat;
import com.zjxjd.manage.service.ItemCatService;

@RequestMapping("item/cat")
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;
    public static final ObjectMapper MAPPER = new ObjectMapper();
    @RequestMapping(method = RequestMethod.GET)
    //查询商品类目
    public ResponseEntity<List<ItemCat>> queryItemCatList(@RequestParam(value="id", defaultValue="0") Long parentId) {
        try {
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(parentId);
            List<ItemCat> itemCatList = itemCatService.queryByWhere(itemCat);
            if(itemCatList == null || itemCatList.isEmpty()) {
                //资源不存在404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemCatList);
        } catch (Exception e) {
            e.printStackTrace();
            //服务器内部错误500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemCatAll() {
            return ResponseEntity.ok(itemCatService.queryAlltoTree());
    }
}
