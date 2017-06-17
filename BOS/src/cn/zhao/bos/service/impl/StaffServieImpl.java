package cn.zhao.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zhao.bos.dao.StaffDao;
import cn.zhao.bos.service.StaffService;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Staff;

@Service
@Transactional
public class StaffServieImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;

    public void save(Staff model) {
        staffDao.save(model);
    }

    public void pageQuery(PageBean pageBean) {
        staffDao.pageQuery(pageBean);
    }

    public void delete(String ids) {
        String[] temp = ids.split(",");
        for(String id : temp) {
            staffDao.executeUpdate("staff.delete", id);
        }
    }
    
    public void restore(String ids) {
        String[] temp = ids.split(",");
        for(String id : temp) {
            staffDao.executeUpdate("staff.restore", id);
        }
    }

    public void edit(Staff model) {
        Staff staff = this.staffDao.findById(model.getId());
        staff.setHaspda(model.getHaspda());
        staff.setName(model.getName());
        staff.setStandard(model.getStandard());
        staff.setStation(model.getStation());
        staff.setTelephone(model.getTelephone());
        this.staffDao.update(staff);
    }

    public List<Staff> findListNotDelete() {
        DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
        dc.add(Restrictions.ne("deltag", "1"));
        return this.staffDao.findByDetached(dc);
    }
}
