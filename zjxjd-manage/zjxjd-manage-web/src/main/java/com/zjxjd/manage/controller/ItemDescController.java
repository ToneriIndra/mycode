package com.zjxjd.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjxjd.manage.pojo.ItemDesc;
import com.zjxjd.manage.service.ItemDescService;

@Controller
@RequestMapping("item/desc")
public class ItemDescController {
    @Autowired
    private ItemDescService itemDescService;
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    //根据商品ID查询商品描述，用于回显
    public ResponseEntity<ItemDesc> queryItemDescById(@PathVariable("itemId") Long itemId) {
        try {
            ItemDesc itemDesc = itemDescService.queryById(itemId);
            if (itemDesc == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
