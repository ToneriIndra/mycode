package cn.zhao.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zhao.bos.dao.DecidedzoneDao;
import cn.zhao.bos.dao.SubareaDao;
import cn.zhao.bos.service.DecidedzoneService;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Decidedzone;
import cn.zhao.bos.vo.Subarea;

@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {
    @Autowired
    private DecidedzoneDao decidedzoneDao;
    @Autowired
    private SubareaDao subareaDao;
    
    public void save(Decidedzone model, String[] subareaId) {
        this.decidedzoneDao.save(model);
        if(subareaId != null && subareaId.length >0) {
            for(String sid : subareaId) {
                Subarea subarea = this.subareaDao.findById(sid);
                subarea.setDecidedzone(model);
            }
        }
    }
    
    public void pageQuery(PageBean pageBean) {
        this.decidedzoneDao.pageQuery(pageBean);
    }
    
    public void edit(Decidedzone model,String[] subareaId) {
        Decidedzone decidedzone = this.decidedzoneDao.findById(model.getId());
        decidedzone.setName(model.getName());
        decidedzone.setStaff(model.getStaff());
        if(subareaId != null && subareaId.length >0) {
            for(String sid : subareaId) {
                Subarea subarea = this.subareaDao.findById(sid);
                subarea.setDecidedzone(model);
            }
        }
        this.decidedzoneDao.update(decidedzone);
    }

    public void delete(String ids) {
        String[] temp = ids.split(",");
        for(String id : temp) {
            this.decidedzoneDao.delete(this.decidedzoneDao.findById(id));
        }
    }
}
