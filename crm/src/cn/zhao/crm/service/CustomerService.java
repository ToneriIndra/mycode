package cn.zhao.crm.service;

import java.util.List;

import cn.zhao.crm.vo.Customer;


// 客户服务接口 
public interface CustomerService {
	/**
	 * 未关联定区客户
	 * @return
	 */
	public List<Customer> findnoassociationCustomers();

	/**
	 * 查询已经关联指定定区的客户
	 * @param decidedZoneId 定区ID
	 * @return
	 */
	public List<Customer> findhasassociationCustomers(String decidedZoneId);

	/**
	 * 将未关联定区客户关联到定区上
	 * @param customerIds 客户ID
	 * @param decidedZoneId 定区
	 */
	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId);
	
	/**
	 * 根据手机号查客户
	 * @param phonenumber
	 * @return
	 */
	public Customer findCustomerByTelephone(String telephone);
	/**
	 * 根据取件地址查询定区ID
	 * @param pickaddress
	 * @return
	 */
	public String findDecidedzoneIdByPickaddress(String pickaddress);
}
