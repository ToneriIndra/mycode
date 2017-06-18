package com.zjxjd.manage.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageInfo;
import com.zjxjd.manage.common.service.ApiService;
import com.zjxjd.manage.pojo.Item;
import com.zjxjd.manage.pojo.ItemDesc;
import com.zjxjd.manage.pojo.ItemParamItem;
/**
 * 商品业务层
 * @author Administrator
 *
 */
@Service
public class ItemService extends BaseService<Item>{
    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private ItemParamItemService itemParamItemService;
    @Value("${ZJXJD_WEB_URL}")
    private String ZJXJD_WEB_URL;
    @Autowired
    private ApiService apiService;
    /**
     * 保存商品
     * @param item 商品实体
     * @param desc 商品描述
     * @param itemParams 商品规格参数
     */
    public void save(Item item, String desc, String itemParams) {
        item.setStatus(1);
        item.setId(null);
        super.save(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescService.save(itemDesc);
        if(StringUtils.isNotEmpty(itemParams)) {
            ItemParamItem itemParamItem = new ItemParamItem();
            itemParamItem.setItemId(item.getId());
            itemParamItem.setParamData(itemParams);
            this.itemParamItemService.save(itemParamItem);
        }
    }
    /**
     * 自定义分页查询
     * @param pageNum 当前页数
     * @param pageSize 每页显示行数
     * @return
     */
    public PageInfo<Item> queryItemList(Integer pageNum, Integer pageSize) {
        Example example = new Example(Item.class);
        example.setOrderByClause("updated DESC");
        example.createCriteria().andNotEqualTo("status", 3);
        return super.queryPageListByExample(example, pageNum, pageSize);
    }
    /**
     * 逻辑删除商品
     * @param ids 商品ID
     */
    public void updateByIds(List<Object> ids) {
        Example example = new Example(Item.class);
        example.createCriteria().andIn("id", ids);
        Item item = new Item();
        item.setStatus(3);
        super.getMapper().updateByExampleSelective(item, example);
        
    }
    /**
     * 更新商品及商品描述
     * @param item 商品
     * @param desc 商品描述
     */
    public void updateItem(Item item, String desc, ItemParamItem itemParamItem) {
        item.setStatus(null);
        item.setCreated(null);
        super.updateSelective(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.updateSelective(itemDesc);
        if(itemParamItem != null) {
            this.itemParamItemService.updateSelective(itemParamItem);
        }
        //通知其他系统,商品已更新
       /* String url = ZJXJD_WEB_URL + "/item/cache/" + item.getId() + ".html";
        try {
            this.apiService.doPost(url);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
