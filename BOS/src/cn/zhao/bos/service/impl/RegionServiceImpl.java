package cn.zhao.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zhao.bos.dao.RegionDao;
import cn.zhao.bos.service.RegionService;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Region;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionDao regionDao;

    public void saveBatch(List<Region> regionList) {
        for(Region region : regionList) {
           this.regionDao.saveOrUpdate(region);
        }
    }

    public void pageQuery(PageBean pageBean) {
        this.regionDao.pageQuery(pageBean);
    }

    public List<Region> findAll() {
        return this.regionDao.findAll();
    }

    public List<Region> findByQ(String q) {
        return this.regionDao.findByQ(q);
    }

    public void save(Region region) {
        this.regionDao.save(region);
    }

    public void edit(Region model) {
        Region region = this.regionDao.findById(model.getId());
        region.setProvince(model.getProvince());
        region.setCity(model.getCity());
        region.setDistrict(model.getDistrict());
        region.setPostcode(model.getPostcode());
        region.setCitycode(model.getCitycode());
        region.setShortcode(model.getShortcode());
        this.regionDao.update(region);
    }

    public void delete(String ids) {
        String temp[] = ids.split(",");
        for(String id : temp) {
            this.regionDao.delete(this.regionDao.findById(id));
        }
    }
    
    
}
