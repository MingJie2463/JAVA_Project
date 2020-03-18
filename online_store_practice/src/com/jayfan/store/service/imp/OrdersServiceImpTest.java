package com.jayfan.store.service.imp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayfan.store.dao.OrderDao;
import com.jayfan.store.dao.OrderLineItemDao;
import com.jayfan.store.dao.imp.OrderDaoImpJdbc;
import com.jayfan.store.dao.imp.OrderLineItemDaoImpJdbc;
import com.jayfan.store.domain.OrderLineItem;
import com.jayfan.store.domain.Orders;
import com.jayfan.store.service.OrdersService;

public class OrdersServiceImpTest {

	OrdersService ordersService;

	OrderDao orderDao;
	OrderLineItemDao lineItemDao;

	@Before
	public void setUp() throws Exception {
		ordersService = new OrdersServiceImp();
		orderDao = new OrderDaoImpJdbc();
		lineItemDao = new OrderLineItemDaoImpJdbc();
	}

	@After
	public void tearDown() throws Exception {
		ordersService = null;
		orderDao = null;
		lineItemDao = null;
	}

	@Test
	public void testSubmitOrders() {
		List<Map<String, Object>> cart = new ArrayList<Map<String, Object>>();
		Map<String, Object> item1 = new HashMap<String, Object>();
		item1.put("goodsid", 4L);
		item1.put("quantity", 2);
		cart.add(item1);

		Map<String, Object> item2 = new HashMap<String, Object>();
		item2.put("goodsid", 5L);
		item2.put("quantity", 3);
		cart.add(item2);

		Map<String, Object> item3 = new HashMap<String, Object>();
		item3.put("goodsid", 6L);
		item3.put("quantity", 1);
		cart.add(item3);

		String orderid = ordersService.submitOrders(cart);
		assertNotNull(orderid);

		Orders orders = orderDao.findByPk(orderid);
		assertNotNull(orders);
		assertEquals(1, orders.getStatus());
		double total = 6699 * 2 + 4899 * 3 + 2099;
		assertEquals(total, orders.getTotal(), 0.1);

		List<OrderLineItem> list = lineItemDao.findAll();
		List<OrderLineItem> lineItems = new ArrayList<OrderLineItem>();
		for (OrderLineItem item : list) {
			if (item.getOrders().getId().equals(orderid)) {
				lineItems.add(item);
				if(item.getGoods().getId() == 5L) {
					assertEquals(3,item.getQuantity());
					assertEquals(4899 * 3,item.getSubTotal(),0.1);
				}
				if(item.getGoods().getId() == 6L) {
					assertEquals(1,item.getQuantity());
					assertEquals(2099,item.getSubTotal(),0.1);
				}
			}
		}
		assertEquals(3,lineItems.size());
		

	}

}
