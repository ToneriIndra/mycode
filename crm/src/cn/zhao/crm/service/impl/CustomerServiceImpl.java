package cn.zhao.crm.service.impl;

import java.util.List;

import org.hibernate.Session;

import cn.zhao.crm.service.CustomerService;
import cn.zhao.crm.utils.HibernateUtils;
import cn.zhao.crm.vo.Customer;



public class CustomerServiceImpl implements CustomerService {

	public List<Customer> findnoassociationCustomers() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where decidedzone_id is null";
		List<Customer> customers = session.createQuery(hql).list();

		session.getTransaction().commit();
		session.close();

		return customers;
	}

	public List<Customer> findhasassociationCustomers(String decidedZoneId) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where decidedzone_id = ?";
		List<Customer> customers = session.createQuery(hql).setParameter(0, decidedZoneId).list();

		session.getTransaction().commit();
		session.close();

		return customers;
	}

	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		// 取消定区所有关联客户
		String hql2 = "update Customer set decidedzone_id=null where decidedzone_id=?";
		session.createQuery(hql2).setParameter(0, decidedZoneId).executeUpdate();

		// 进行关联
		String hql = "update Customer set decidedzone_id=? where id =?";
		if (customerIds != null) {
			for (Integer id : customerIds) {
				session.createQuery(hql).setParameter(0, decidedZoneId).setParameter(1, id).executeUpdate();
			}
		}
		session.getTransaction().commit();
		session.close();
	}

    public Customer findCustomerByTelephone(String telephone) {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        String hql = "from Customer where telephone = ?";
        List<Customer> list = session.createQuery(hql).setParameter(0, telephone).list();
        session.getTransaction().commit();
        session.close();
        if(list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public String findDecidedzoneIdByPickaddress(String pickaddress) {
        Session session = HibernateUtils.openSession();
        session.beginTransaction();
        String hql = "select decidedzone_id from Customer where address=?";
        List<String> list = session.createQuery(hql).setParameter(0, pickaddress).list();
        session.getTransaction().commit();
        session.close();
        if(list != null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }

}
