package com.zjxjd.manage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.zjxjd.manage.common.bean.EasyUIResult;
import com.zjxjd.manage.pojo.Item;
import com.zjxjd.manage.pojo.ItemParamItem;
import com.zjxjd.manage.service.ItemParamItemService;
import com.zjxjd.manage.service.ItemService;
@RequestMapping("item")
@Controller
public class ItemController {
    //slf日志
    public static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private ItemService itemService;
    @RequestMapping(method = RequestMethod.POST)
    //添加商品
    public ResponseEntity<Void> save(Item item, @RequestParam("desc") String desc, @RequestParam("itemParams") String itemParams) {
        try {
            //debug模式下输出日志
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("开始添加商品,item={}" ,item);
            }
            itemService.save(item, desc, itemParams);
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("商品添加成功,item={}",item);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //分页查询商品
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", defaultValue = "30") Integer pageSize) {
            try {
                PageInfo<Item> pageInfo = itemService.queryItemList(pageNum, pageSize);
                return ResponseEntity.ok(new EasyUIResult(pageInfo.getTotal(), pageInfo.getList()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    //根据IDS删除商品
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteItemByIds(@RequestParam("ids") List<Object> ids) {
        try {
            itemService.updateByIds(ids);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  //更新商品
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc, 
             @RequestParam("itemParams") String itemParams, @RequestParam("itemParamId") Long itemParamId) {
        try {
            //debug模式下输出日志
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("开始更新商品,item={}" ,item);
            }
            ItemParamItem itemParamItem = null;
            if(itemParamId != null) {
                itemParamItem = new ItemParamItem();
                itemParamItem.setId(itemParamId);
                itemParamItem.setParamData(itemParams);
            }
            itemService.updateItem(item, desc, itemParamItem);
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("商品更新成功,item={}",item);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //根据商品ID查询商品
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ResponseEntity<Item> queryItemById(@PathVariable("itemId") Long itemId) {
        try {
            Item item = this.itemService.queryById(itemId);
            if(item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
