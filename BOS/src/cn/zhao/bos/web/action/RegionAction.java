package cn.zhao.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.service.RegionService;
import cn.zhao.bos.utils.PinYin4jUtils;
import cn.zhao.bos.vo.Region;
import cn.zhao.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
    @Autowired
    private RegionService regionService;
    private File myfile;
    public void setMyfile(File myfile) {
        this.myfile = myfile;
    }
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    //分区excel导入功能
    public String importExcel() throws IOException {
        String flag = "1";
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(myfile));
            HSSFSheet sheet = workbook.getSheetAt(0);
            List<Region> regionList = new ArrayList();
            for(Row row:sheet) {
                int rowNum = row.getRowNum();
                if(rowNum == 0) {
                    continue;
                }
                Region region = new Region();
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                region.setId(row.getCell(0).getStringCellValue());
                region.setProvince(province);
                region.setCity(city);
                region.setDistrict(district);
                region.setPostcode(row.getCell(4).getStringCellValue());
                province = province.substring(0,province.length() - 1);
                city = city.substring(0,city.length() - 1);
                district = district.substring(0,district.length() - 1);
                String[] cityCodes = PinYin4jUtils.stringToPinyin(city);
                String cityCode = StringUtils.join(cityCodes,"");
                region.setCitycode(cityCode);
                String[] shortCodes = PinYin4jUtils.getHeadByString(province + city + district);
                String shortCode = StringUtils.join(shortCodes,"");
                region.setShortcode(shortCode);
                regionList.add(region);
            }
            regionService.saveBatch(regionList);
        } catch (Exception e) {
            e.printStackTrace();
            flag = "0";
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(flag);
        return NONE;
    }
    //分页查询
    public String pageQuery() throws IOException {
        this.regionService.pageQuery(pageBean);
        this.writePageBean2Json(pageBean, new String[] {"dc", "pageNum", "pageSize", "subareas"});
        return NONE;
    }
    private String q;
    public String getQ() {
        return q;
    }
    public void setQ(String q) {
        this.q = q;
    }
    //分区添加页面下拉框数据
    public String ajaxList4subareas() throws IOException {
        List<Region>  list = null;
        if(StringUtils.isNotBlank(q)) {
            list = this.regionService.findByQ(q);
        } else {
            list = this.regionService.findAll();
        }
        this.writeList2Json(list, new String[] {"subareas"});
        return NONE;
    }
    
    public String list() {
        return "list";
    }
    
    //保存
    public String save() {
        this.regionService.save(model);
        return "toList";
    }
    //编辑
    public String edit() {
        this.regionService.edit(model);
        return "toList";
    }
    //删除
    public String delete() {
        this.regionService.delete(ids);
        return "toList";
    }
}
