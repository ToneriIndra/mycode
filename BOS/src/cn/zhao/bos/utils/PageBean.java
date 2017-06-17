package cn.zhao.bos.utils;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 分页通用bean
 * @author Administrator
 *
 */
public class PageBean {
    private int pageNum;
    private int pageSize;
    private int total;
    private DetachedCriteria dc;
    private List rows;
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public DetachedCriteria getDc() {
        return dc;
    }
    public void setDc(DetachedCriteria dc) {
        this.dc = dc;
    }
    public List getRows() {
        return rows;
    }
    public void setRows(List rows) {
        this.rows = rows;
    }
    
}
