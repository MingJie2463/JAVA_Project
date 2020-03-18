package com.jayfan.store.service.imp;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayfan.store.domain.Customer;
import com.jayfan.store.service.CustomerService;
import com.jayfan.store.service.ServiceException;

public class CustomerServiceImpTest {

	CustomerService customerService;

	@Before
	public void setUp() throws Exception {
		customerService = new CustomerServiceImp();
	}

	@After
	public void tearDown() throws Exception {
		customerService = null;
	}

	@Test
	public void testLogin() {

		Customer customer2 = new Customer();
		customer2.setId("Jay");
		customer2.setPassword("123");
		assertTrue(customerService.login(customer2));
		assertNotNull(customer2);
		assertEquals("Jay", customer2.getId());
		assertEquals("FanMingJie", customer2.getName());
		assertEquals("123", customer2.getPassword());
		assertEquals("Taiwan", customer2.getAddress());
		assertEquals("0937862463", customer2.getPhone());
		assertEquals(11111111111L, customer2.getBirthday().getTime());

	}

	@Test
	public void testLoginfail() {

		Customer customer = new Customer();
		customer.setId("Jay");
		customer.setPassword("751953");
		assertFalse(customerService.login(customer));

	}

	@Test
	public void testRegister() throws ServiceException {
		Customer customer = new Customer();
		customer.setId("Joy");
		customer.setPassword("759153");
		customer.setName("FanJoy");
		customer.setAddress("Taiwan");
		customer.setPhone("0937862463");
		customer.setBirthday(new Date(11111234567L));
		customerService.register(customer);

		Customer customer2 = new Customer();
		customer2.setId("Joy");
		customer2.setPassword("759153");
		assertTrue(customerService.login(customer2));
		assertNotNull(customer2);
		assertEquals("Joy", customer2.getId());
		assertEquals("FanJoy", customer2.getName());
		assertEquals("759153", customer2.getPassword());
		assertEquals("Taiwan", customer2.getAddress());
		assertEquals("0937862463", customer2.getPhone());
		assertEquals(11111234567L, customer2.getBirthday().getTime());

	}

	@Test(expected = ServiceException.class)
	public void testRegisterExists() throws ServiceException {
		Customer customer = new Customer();
		customer.setId("Joy");
		customer.setPassword("759153");
		customerService.register(customer);
	}

}
