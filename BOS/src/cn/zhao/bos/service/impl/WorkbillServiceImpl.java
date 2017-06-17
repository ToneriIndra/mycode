package cn.zhao.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zhao.bos.dao.WorkbillDao;
import cn.zhao.bos.service.WorkbillService;
import cn.zhao.bos.utils.PageBean;
import cn.zhao.bos.vo.Workbill;
@Service
@Transactional
public class WorkbillServiceImpl implements WorkbillService {
    @Autowired
    private WorkbillDao workbillDao;

    public void pageQuery(PageBean pageBean) {
        this.workbillDao.pageQuery(pageBean);
    }

    public void delete(String ids) {
        String[] temp = ids.split(",");
        for (String id : temp) {
            Workbill workbill = this.workbillDao.findById(id);
            workbill.setType("销单");
        }
    }
}
