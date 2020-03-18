package com.jayfan.store.service.imp;

import com.jayfan.store.dao.CustomerDao;
import com.jayfan.store.dao.imp.CustomerDaoImpJdbc;
import com.jayfan.store.domain.Customer;
import com.jayfan.store.service.CustomerService;
import com.jayfan.store.service.ServiceException;

public class CustomerServiceImp implements CustomerService {

	CustomerDao customerDao = new CustomerDaoImpJdbc();

	@Override
	public boolean login(Customer customer) {
		// TODO Auto-generated method stub
		Customer dbCustomer = customerDao.findByPk(customer.getId());
		if (dbCustomer.getPassword().equals(customer.getPassword())) {
			customer.setPhone(dbCustomer.getPhone());
			customer.setAddress(dbCustomer.getAddress());
			customer.setName(dbCustomer.getName());
			customer.setBirthday(dbCustomer.getBirthday());
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void register(Customer customer) throws ServiceException {
		// TODO Auto-generated method stub
		Customer dbCustomer = customerDao.findByPk(customer.getId());
		if(dbCustomer != null) { //客戶ID已存在
			throw new ServiceException("Customer ID:" + customer.getId() + " exists!!");					
		}
		else {
			customerDao.create(customer);	
		}

	}

}
