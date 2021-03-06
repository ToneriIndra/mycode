package cn.zhao.bos.vo;

import java.util.HashSet;
import java.util.Set;
/**
 * 取派员实体
 * @author Administrator
 *
 */
public class Staff implements java.io.Serializable {


	private String id;
	private String name;
	private String telephone;
	private String haspda = "0";//是否有PDA 1：有 0：无
	private String deltag = "0";//删除标识位 1：已删除 0：未删除
	private String station;
	private String standard;

	public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getHaspda() {
		return this.haspda;
	}

	public void setHaspda(String haspda) {
		this.haspda = haspda;
	}

	public String getDeltag() {
		return this.deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

}