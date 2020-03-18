package com.jayfan.store.service;

import com.jayfan.store.domain.Customer;

public interface CustomerService {
	//處理Client登入
	boolean login(Customer customer);
	
	//client註冊，失敗拋出異常
	void register(Customer customer) throws ServiceException;
	
}
