package cn.zhao.bos.vo;

import java.io.Serializable;

/**
 * 定区实体
 * @administrator
 *
 */
public class Decidedzone implements Serializable {

	private String id;
	private Staff staff;
	private String name;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}