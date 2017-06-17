package cn.zhao.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zhao.bos.dao.SubareaDao;
import cn.zhao.bos.service.SubareaService;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Subarea;
@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {
    @Autowired
    private SubareaDao subareaDao;
    public void pageQuery(PageBean pageBean) {
        this.subareaDao.pageQuery(pageBean);
    }
    
    public List<Subarea> findAll() {
        return this.subareaDao.findAll();
    }
    
    public List<Subarea> findNotAssociation() {
        DetachedCriteria dc = DetachedCriteria.forClass(Subarea.class);
        dc.add(Restrictions.isNull("decidedzone"));
        return this.subareaDao.findByDetached(dc);
    }

    public void save(Subarea model) {
        this.subareaDao.save(model);
    }

    public void edit(Subarea model) {
        Subarea subarea = this.subareaDao.findById(model.getId());
        subarea.setAddresskey(model.getAddresskey());
        subarea.setStartnum(model.getStartnum());
        subarea.setEndnum(model.getEndnum());
        subarea.setSingle(model.getSingle());
        subarea.setPosition(model.getPosition());
        subarea.setSubareaId(model.getSubareaId());
        subarea.setRegion(model.getRegion());
        this.subareaDao.update(subarea);
    }

    public void delete(String ids) {
        String[] temp = ids.split(",");
        for(String id:temp) {
            this.subareaDao.delete(this.subareaDao.findById(id));
        }
    }

    public void saveBatch(List<Subarea> subareaList) {
        for(Subarea subarea:subareaList) {
            this.subareaDao.saveOrUpdate(subarea);
        }
    }

}
