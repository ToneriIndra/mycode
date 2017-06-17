package cn.zhao.bos.vo;

/**
 * 分区实体
 *
 */
public class Subarea implements java.io.Serializable {

    private String id;

    // 与定区多对一
    private Decidedzone decidedzone;

    // 与行政区域多对一
    private Region region;

    private String addresskey;

    private String startnum;

    private String endnum;

    private String single;

    private String position;

    private String subareaId;

    public void setSubareaId(String subareaId) {
        this.subareaId = subareaId;
    }

    public String getSubareaId() {
        return subareaId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Decidedzone getDecidedzone() {
        return this.decidedzone;
    }

    public void setDecidedzone(Decidedzone decidedzone) {
        this.decidedzone = decidedzone;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getAddresskey() {
        return this.addresskey;
    }

    public void setAddresskey(String addresskey) {
        this.addresskey = addresskey;
    }

    public String getStartnum() {
        return this.startnum;
    }

    public void setStartnum(String startnum) {
        this.startnum = startnum;
    }

    public String getEndnum() {
        return this.endnum;
    }

    public void setEndnum(String endnum) {
        this.endnum = endnum;
    }

    public String getSingle() {
        return this.single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}