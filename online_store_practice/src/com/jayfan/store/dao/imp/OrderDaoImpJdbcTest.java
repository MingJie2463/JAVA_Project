package com.jayfan.store.dao.imp;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayfan.store.dao.OrderDao;
import com.jayfan.store.domain.Orders;

public class OrderDaoImpJdbcTest {

	OrderDao dao;

	@Before
	public void setUp() throws Exception {
		dao = new OrderDaoImpJdbc();
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}

	@Test
	public void testFindByPk() {
		Orders orders = dao.findByPk("69");
		assertNotNull(orders);
		assertEquals("69", orders.getId());
		assertEquals(11111111111L, orders.getOrderDate().getTime());
		assertEquals(1, orders.getStatus());
		assertEquals(9600, orders.getTotal(), 0.1);
	}

	@Test
	public void testFindAll() {
		List<Orders> list = dao.findAll();
		assertNotNull(list);
		assertEquals(list.size(), 2);
		Orders orders = list.get(1);
		assertEquals("69", orders.getId());
		assertEquals(11111111111L, orders.getOrderDate().getTime());
		assertEquals(1, orders.getStatus());
		assertEquals(9600, orders.getTotal(), 0.1);

	}

	@Test
	public void testCreate() {
		Orders orders = new Orders();
		orders.setId("1200");
		orders.setStatus(0);
		orders.setOrderDate(new Date(11223344556L));
		orders.setTotal(999999);
		dao.create(orders);

		Orders orders1 = dao.findByPk("1200");
		assertNotNull(orders1);
		assertEquals("1200", orders1.getId());
		assertEquals(11223344556L, orders1.getOrderDate().getTime());
		assertEquals(0, orders1.getStatus());
		assertEquals(999999, orders1.getTotal(), 0.1);
	}

	@Test
	public void testModify() {
		Orders orders = new Orders();
		orders.setId("1200");
		orders.setStatus(1);
		orders.setOrderDate(new Date(65432154321L));
		orders.setTotal(696969);
		dao.modify(orders);

		Orders orders1 = dao.findByPk("1200");
		assertNotNull(orders1);
		assertEquals("1200", orders1.getId());
		assertEquals(65432154321L, orders1.getOrderDate().getTime());
		assertEquals(1, orders1.getStatus());
		assertEquals(696969, orders1.getTotal(), 0.1);
	}

	@Test
	public void testRemove() {
		dao.remove("1200");
		Orders orders1 = dao.findByPk("1200");
		assertNull(orders1);
		List<Orders> list = dao.findAll();
		assertEquals(list.size(), 2);
	}

}
