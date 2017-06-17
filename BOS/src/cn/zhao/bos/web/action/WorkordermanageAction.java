package cn.zhao.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zhao.bos.service.WorkordermanageService;
import cn.zhao.bos.utils.DownloadUtils;
import cn.zhao.bos.vo.Workordermanage;
import cn.zhao.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {
    @Autowired
    private WorkordermanageService workordermanageService;
    
    private File myfile;
    public void setMyfile(File myfile) {
        this.myfile = myfile;
    }
    
    private String filename;
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public String getContentType() {
        String mimeType = ServletActionContext.getServletContext().getMimeType(filename);
        return mimeType;
    }
    
    public String getDownloadFileName() throws UnsupportedEncodingException {
        return DownloadUtils.getDownloadFileName(ServletActionContext.getRequest().getHeader("user-agent"), filename);
        
    }
    
    public InputStream getInputStream() throws  Exception {
        filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
        FileInputStream fis = new FileInputStream("F:/BOSModel/" + filename);
        return fis;
    }
    
    public String list() {
        List<Workordermanage> list = this.workordermanageService.findListNotStart();
        ActionContext.getContext().getValueStack().set("list", list);
        return "list";
    }
    
    public String start() {
        String id = model.getId();
        this.workordermanageService.start(id);
        return "toList";
    }
    
    public String quickSave() throws IOException {
        String flag = "1";
        try {
            this.workordermanageService.quickSave(model);
        } catch(Exception e) {
            e.printStackTrace();
            flag = "0";
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(flag);
        return NONE;
    }
    
    public String pageQuery() throws IOException {
        this.workordermanageService.pageQuery(pageBean);
        writePageBean2Json(pageBean, new String[] {"dc","pageBean","pageSize","pageNum"});
        return NONE;
    }
    
    public String batchImport() throws IOException {
        String flag = "1";
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(myfile));
            HSSFSheet sheet = workbook.getSheetAt(0);
            List<Workordermanage> list = new ArrayList<Workordermanage>();
            for(Row row:sheet) {
                int rowNum = row.getRowNum();
                if(rowNum == 0) {
                    continue;
                }
                Workordermanage workordermanage = new Workordermanage();
                workordermanage.setId(row.getCell(0).getStringCellValue());
                workordermanage.setArrivecity(row.getCell(1).getStringCellValue());
                workordermanage.setProduct(row.getCell(2).getStringCellValue());
                workordermanage.setNum(Integer.parseInt(row.getCell(3).getStringCellValue()));
                workordermanage.setWeight(Double.parseDouble(row.getCell(4).getStringCellValue()));
                String temp = row.getCell(5).getStringCellValue();
                switch (temp) {
                case "禁航":
                    workordermanage.setFloadreqr("2");
                    break;
                case "禁铁路":
                    workordermanage.setFloadreqr("3");
                    break;

                default:
                    workordermanage.setFloadreqr("1");
                    break;
                }
                workordermanage.setProdtimelimit(row.getCell(6).getStringCellValue());
                workordermanage.setProdtype(row.getCell(7).getStringCellValue());
                workordermanage.setSendername(row.getCell(8).getStringCellValue());
                workordermanage.setSenderphone(row.getCell(9).getStringCellValue());
                workordermanage.setSenderaddr(row.getCell(10).getStringCellValue());
                workordermanage.setReceivername(row.getCell(11).getStringCellValue());
                workordermanage.setReceiverphone(row.getCell(12).getStringCellValue());
                workordermanage.setReceiveraddr(row.getCell(13).getStringCellValue());
                workordermanage.setFeeitemnum(Integer.parseInt(row.getCell(14).getStringCellValue()));
                workordermanage.setActlweit(Double.parseDouble(row.getCell(15).getStringCellValue()));
                workordermanage.setVol(row.getCell(16).getStringCellValue());
                workordermanage.setManagerCheck("0");
                workordermanage.setUpdatetime(new Date());
                workordermanage.setStart("0");
                list.add(workordermanage);
            }
            workordermanageService.saveBatch(list);
        } catch(Exception e) {
            e.printStackTrace();
            flag = "0";
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
        ServletActionContext.getResponse().getWriter().print(flag);
        return NONE;
    }
    
    public String download() {
        return null;
    }
}
