package com.jayfan.store.service;

import com.jayfan.store.domain.Customer;

public interface CustomerService {
	//�B�zClient�n�J
	boolean login(Customer customer);
	
	//client���U�A���ѩߥX���`
	void register(Customer customer) throws ServiceException;
	
}
