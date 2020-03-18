/**
 * 
 */
package com.jayfan.store.dao.imp;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayfan.store.dao.CustomerDao;
import com.jayfan.store.domain.Customer;

/**
 * @author Master
 *
 */
public class CustomerDaoImpJdbcTest {

	CustomerDao dao;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dao = new CustomerDaoImpJdbc();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		dao = null;
	}

	/**
	 * Test method for
	 * {@link com.jayfan.store.dao.imp.CustomerDaoImpJdbc#findByPk(java.lang.String)}.
	 */
	@Test
	public void testFindByPk() {
		Customer customer = dao.findByPk("Jay");
		assertNotNull(customer);
		assertEquals("Jay", customer.getId());
		assertEquals("FanMingJie", customer.getName());
		assertEquals("123", customer.getPassword());
		assertEquals("Taiwan", customer.getAddress());
		assertEquals("0937862463", customer.getPhone());
		assertEquals(11111111111L, customer.getBirthday().getTime());
	}

	/**
	 * Test method for
	 * {@link com.jayfan.store.dao.imp.CustomerDaoImpJdbc#findAll()}.
	 */
	@Test
	public void testFindAll() {
		List<Customer> list = dao.findAll();
		assertNotNull(list);
		assertEquals(list.size(), 1);
	}

	/**
	 * Test method for
	 * {@link com.jayfan.store.dao.imp.CustomerDaoImpJdbc#create(com.jayfan.store.domain.Customer)}.
	 */
	@Test
	public void testCreate() {
		Customer customer = new Customer();
		customer.setId("Tom");
		customer.setName("Tom Bear");
		customer.setPassword("456");
		customer.setAddress("Japan");
		customer.setPhone("0800-000123");
		customer.setBirthday(new Date(11111111234L));
		dao.create(customer);
		// Query by pk
		Customer customerTemp = dao.findByPk("Tom");
		assertNotNull(customerTemp);
		assertEquals("Tom", customerTemp.getId());
		assertEquals("Tom Bear", customerTemp.getName());
		assertEquals("456", customerTemp.getPassword());
		assertEquals("Japan", customerTemp.getAddress());
		assertEquals("0800-000123", customerTemp.getPhone());
		assertEquals(11111111234L, customerTemp.getBirthday().getTime());
	}

	/**
	 * Test method for
	 * {@link com.jayfan.store.dao.imp.CustomerDaoImpJdbc#modify(com.jayfan.store.domain.Customer)}.
	 */
	@Test
	public void testModify() {
		Customer customer = new Customer();
		customer.setId("Tom");
		customer.setName("Tom Cat");
		customer.setPassword("789");
		customer.setAddress("America");
		customer.setPhone("0800-080080");
		customer.setBirthday(new Date(11111114567L));
		dao.modify(customer);
		Customer customerTemp = dao.findByPk("Tom");
		assertNotNull(customerTemp);
		assertEquals("Tom Cat", customerTemp.getName());
		assertEquals("789", customerTemp.getPassword());
		assertEquals("America", customerTemp.getAddress());
		assertEquals("0800-080080", customerTemp.getPhone());
		assertEquals(11111114567L, customerTemp.getBirthday().getTime());
	}

	/**
	 * Test method for
	 * {@link com.jayfan.store.dao.imp.CustomerDaoImpJdbc#remove(java.lang.String)}.
	 */
	@Test
	public void testRemove() {
		dao.remove("Tom");
		Customer customerTemp = dao.findByPk("Tom");
		assertNull(customerTemp);
	}

}
