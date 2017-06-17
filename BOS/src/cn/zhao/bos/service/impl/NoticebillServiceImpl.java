package cn.zhao.bos.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zhao.bos.dao.DecidedzoneDao;
import cn.zhao.bos.dao.NoticebillDao;
import cn.zhao.bos.dao.StaffDao;
import cn.zhao.bos.dao.WorkbillDao;
import cn.zhao.bos.service.NoticebillService;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Decidedzone;
import cn.zhao.bos.vo.Noticebill;
import cn.zhao.bos.vo.Staff;
import cn.zhao.bos.vo.Workbill;
import cn.zhao.crm.service.CustomerService;
@Service
@Transactional
public class NoticebillServiceImpl implements NoticebillService {
    @Autowired
    private NoticebillDao noticebillDao;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DecidedzoneDao decidedzoneDao;
    @Autowired
    private WorkbillDao workbillDao;
    @Autowired
    private StaffDao staffDao;
    public void save(Noticebill model) {
        String decidedzoneId = this.customerService.findDecidedzoneIdByPickaddress(model.getPickaddress());
        if(decidedzoneId != null) {
            Decidedzone decidedzone = this.decidedzoneDao.findById(decidedzoneId);
            Staff staff = decidedzone.getStaff();
            model.setStaff(staff);
            model.setOrdertype("自动");
            Workbill workbill = new Workbill();
            workbill.setAttachbilltimes(0);
            workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));
            workbill.setNoticebill(model);
            workbill.setPickstate("未取件");
            workbill.setStaff(staff);
            workbill.setRemark(model.getRemark());
            workbill.setType("新单");
            this.workbillDao.save(workbill);
        } else {
            model.setOrdertype("人工");
        }
        
        this.noticebillDao.save(model);
    }

    public List<Noticebill> findNoAssociations() {
        DetachedCriteria dc = DetachedCriteria.forClass(Noticebill.class);
        dc.add(Restrictions.isNull("staff"));
        return this.noticebillDao.findByDetached(dc);
    }

    public void associationStaff(Noticebill model) {
        model.setStaff(model.getStaff());
        this.noticebillDao.update(model);
    }

    public List<Noticebill> findBytelephone(String telephone) {
        DetachedCriteria dc = DetachedCriteria.forClass(Noticebill.class);
        dc.add(Restrictions.eq("telephone", telephone));
        return this.noticebillDao.findByDetached(dc);
    }
}
