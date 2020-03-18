package com.jayfan.store.dao.imp;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayfan.store.dao.OrderLineItemDao;
import com.jayfan.store.domain.Goods;
import com.jayfan.store.domain.OrderLineItem;
import com.jayfan.store.domain.Orders;

public class OrderLineItemDaoImpJdbcTest {
	

	OrderLineItemDao dao;
	@Before
	public void setUp() throws Exception {
		dao = new OrderLineItemDaoImpJdbc();
	}

	@After
	public void tearDown() throws Exception {
		dao = null;		
	}

	@Test
	public void testFindByPk() {
		OrderLineItem lineItem = dao.findByPk(1);
		assertNotNull(lineItem);
		assertEquals(1,lineItem.getId());
		assertEquals(3,lineItem.getQuantity());
		assertEquals(9297,lineItem.getSubTotal(),0.1);
		assertEquals("21",lineItem.getOrders().getId());
		assertEquals(3,lineItem.getGoods().getId());
		
	}

	@Test
	public void testFindAll() {
		List<OrderLineItem> list =  dao.findAll();
		assertEquals(2,list.size());

		OrderLineItem lineItem2 = list.get(1);
		assertEquals(2,lineItem2.getId());
		assertEquals(2,lineItem2.getQuantity());
		assertEquals(4198,lineItem2.getSubTotal(),0.1);
		assertEquals("69",lineItem2.getOrders().getId());
		assertEquals(6,lineItem2.getGoods().getId());
	}

	@Test
	public void testCreate() {
		OrderLineItem lineItem = new OrderLineItem();
		lineItem.setQuantity(9865);
		lineItem.setSubTotal(987456321);
		Goods goods = new Goods();
		goods.setId(2);
		lineItem.setGoods(goods);
		Orders orders = new Orders();
		orders.setId("69");
		lineItem.setOrders(orders);
		dao.create(lineItem);
		
		OrderLineItem lineItem2= dao.findByPk(3);
		assertNotNull(lineItem2);
		assertEquals(3,lineItem2.getId());
		assertEquals(9865,lineItem2.getQuantity());
		assertEquals(987456321,lineItem2.getSubTotal(),0.1);
		assertEquals("69",lineItem2.getOrders().getId());
		assertEquals(2,lineItem2.getGoods().getId());
	}

	@Test
	public void testModify() {
		OrderLineItem lineItem = new OrderLineItem();
		lineItem.setId(10);
		lineItem.setQuantity(9696);
		lineItem.setSubTotal(223345);
		Goods goods = new Goods();
		goods.setId(1);
		lineItem.setGoods(goods);
		Orders orders = new Orders();
		orders.setId("21");
		lineItem.setOrders(orders);
		dao.modify(lineItem);
		
		OrderLineItem lineItem2= dao.findByPk(10);
		assertNotNull(lineItem2);
		assertEquals(10,lineItem2.getId());
		assertEquals(9696,lineItem2.getQuantity());
		assertEquals(223345,lineItem2.getSubTotal(),0.1);
		assertEquals("21",lineItem2.getOrders().getId());
		assertEquals(1,lineItem2.getGoods().getId());
	}

	@Test
	public void testRemove() {
		dao.remove(10);
		OrderLineItem lineItem2= dao.findByPk(10);
		assertNull(lineItem2);
		
		
	}

}
