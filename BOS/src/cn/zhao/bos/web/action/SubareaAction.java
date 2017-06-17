package cn.zhao.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.dao.RegionDao;
import cn.zhao.bos.service.SubareaService;
import cn.zhao.bos.utils.FileUtils;
import cn.zhao.bos.vo.Region;
import cn.zhao.bos.vo.Subarea;
import cn.zhao.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
    @Autowired
    private SubareaService subareaService;
    @Autowired
    private RegionDao regionDao;
    //分页按条件查询
    public String pageQuery() throws IOException {
        DetachedCriteria dc2 = pageBean.getDc();
        String addresskey = model.getAddresskey();
        Region region = model.getRegion();
        if(StringUtils.isNotBlank(addresskey)) {
            dc2.add(Restrictions.like("addresskey", "%" + addresskey + "%"));
        }
        if(region != null) {
            String province = region.getProvince();
            String city = region.getCity();
            String district = region.getDistrict();
            if(StringUtils.isNotBlank(province)) {
                dc2.add(Restrictions.like("region.province", "%" + province + "%"));
            }
            if(StringUtils.isNotBlank(city)) {
                dc2.add(Restrictions.like("region.city", "%" + city + "%"));
            }
            if(StringUtils.isNotBlank(district)) {
                dc2.add(Restrictions.like("region.district", "%" + district + "%"));
            }
        }
        this.subareaService.pageQuery(pageBean);
        this.writePageBean2Json(pageBean, new String[] {"dc", "pageSize", "pageNum", "decidedzone"});
        return NONE;
    }
    //导出excel
    public String exportExcel() throws IOException {
        List<Subarea> subareas = this.subareaService.findAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("分区数据");
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("分区编号");
        headRow.createCell(1).setCellValue("区域编号");
        headRow.createCell(2).setCellValue("地址关键字");
        headRow.createCell(3).setCellValue("省市区");
        for(Subarea subarea : subareas) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(subarea.getId());
            dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
            dataRow.createCell(2).setCellValue(subarea.getAddresskey());
            Region region = subarea.getRegion();
            dataRow.createCell(3).setCellValue(region.getProvince()+region.getCity()+region.getDistrict());
        }
        String fileName = "分区数据.xls";
        String agent = ServletActionContext.getRequest().getHeader("User-Agent");
        fileName = FileUtils.encodeDownloadFilename(fileName, agent);
        ServletOutputStream sos = ServletActionContext.getResponse().getOutputStream();
        String contentType = ServletActionContext.getRequest().getServletContext().getMimeType(fileName);
        ServletActionContext.getResponse().setContentType(contentType);
        ServletActionContext.getResponse().setHeader("content-disposition", "attchment;fileName=" + fileName);
        workbook.write(sos);
        return NONE;
    }
    //为定区页面提供分区数据
    public String ajaxList4decidedzone() throws IOException {
        List<Subarea> subareas = this.subareaService.findNotAssociation();
        this.writeList2Json(subareas, new String[] {"decidedzone", "region"});
        return NONE;
    }
    //保存
    public String save() {
        this.subareaService.save(model);
        return "toList";
    }
    
    public String list() {
        return "list";
    }
    //编辑
    public String edit() {
        this.subareaService.edit(model);
        return "toList";
    }
    //删除
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    public String delete() {
        this.subareaService.delete(ids);
        return "toList";
    }
    //excel导入
    private File myfile;
    public void setMyfile(File myfile) {
        this.myfile = myfile;
    }
    public String importExcel() throws FileNotFoundException, IOException {
        String flag = "1";
        try{
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(myfile));
            HSSFSheet sheet = workbook.getSheetAt(0);
            List<Subarea>  subareaList = new ArrayList<Subarea>();
            for(Row row:sheet) {
                int rowNum = row.getRowNum();
                if(rowNum == 0) {
                    continue;
                }
                Subarea subarea = new Subarea();
                subarea.setSubareaId(row.getCell(0).getStringCellValue());
                Region region = regionDao.findById(row.getCell(1).getStringCellValue());
                subarea.setRegion(region);
                subarea.setAddresskey(row.getCell(2).getStringCellValue());
                subarea.setStartnum(row.getCell(3).getStringCellValue());
                subarea.setEndnum(row.getCell(4).getStringCellValue());
                subarea.setSingle(row.getCell(5).getStringCellValue());
                subarea.setPosition(row.getCell(6).getStringCellValue());
                subareaList.add(subarea);
            }  
            this.subareaService.saveBatch(subareaList);
            } catch (Exception e) {
            e.printStackTrace();
            flag = "0";
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(flag);
        return NONE;
    }
}
