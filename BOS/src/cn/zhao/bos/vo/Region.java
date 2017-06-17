package cn.zhao.bos.vo;

import java.util.HashSet;
import java.util.Set;
/**
 * 区域实体
 *
 */

public class Region implements java.io.Serializable {

	private String id;
	private String province;
	private String city;
	private String district;
	private String postcode;
	private String shortcode;
	private String citycode;
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getShortcode() {
		return this.shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public String getCitycode() {
		return this.citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	//分区获取区域名称
	public String getName() {
	    return province + city + district;
	}
}