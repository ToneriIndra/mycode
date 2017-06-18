package com.zjxjd.manage.controller;

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
import com.zjxjd.manage.pojo.ItemParam;
import com.zjxjd.manage.service.ItemParamService;

@RequestMapping("item/param")
@Controller
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;
    //根据商品类目ID查询商品参数模板是否存在
    @RequestMapping(value = "{itemcatid}", method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemcatid") Long itemCatId) {
        try { 
            ItemParam param = new ItemParam();
            param.setItemCatId(itemCatId);
            ItemParam itemParam = itemParamService.queryOne(param);
            if (itemParam == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    //保存商品参数模板
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId") Long itemCatId, @RequestParam("paramData") String paramData) {
        try { 
            ItemParam param = new ItemParam();
            param.setItemCatId(itemCatId);
            param.setParamData(paramData);
            itemParamService.save(param);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    //查询商品列表
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryLis(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) {
        try {
            PageInfo<ItemParam> pageInfo = this.itemParamService
                    .queryPageListByWhere(null, pageNum, pageSize);
            return ResponseEntity.ok(new EasyUIResult(pageInfo.getTotal(), pageInfo.getList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
