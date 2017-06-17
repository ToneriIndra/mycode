package cn.zhao.bos.dao;

import java.util.List;

import cn.zhao.bos.dao.base.BaseDao;
import cn.zhao.bos.vo.Region;

public interface RegionDao extends BaseDao<Region> {

    public  List<Region>  findByQ(String q);

}
