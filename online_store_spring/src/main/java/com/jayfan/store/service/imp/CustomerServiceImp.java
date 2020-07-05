package com.jayfan.store.service.imp;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayfan.store.domain.Customer;
import com.jayfan.store.domain.CustomerRepository;
import com.jayfan.store.service.CustomerService;

@Service
public class CustomerServiceImp implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public boolean login(Customer customer) {
		// TODO Auto-generated method stub
		boolean result = false;

		try {
			Customer dbCustomer = customerRepository.getOne(customer.getId());
			System.out.print(dbCustomer.toString());
			if (dbCustomer.getPassword().equals(customer.getPassword())) {
				BeanUtils.copyProperties(dbCustomer, customer);
				result = true;
			} else {
				result = false;
			}
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public void register(Customer customer) throws ServiceException {
		// TODO Auto-generated method stub
		Customer dbCustomer = customerRepository.getOne(customer.getId());
		if (dbCustomer != null) { // 客戶ID已存在
			throw new ServiceException("Customer ID:" + customer.getId() + " exists!!");
		} else {
			customerRepository.save(customer);
		}
	}

}
