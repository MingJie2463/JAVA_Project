package com.jayfan.store.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jayfan.store.dao.GoodsDao;
import com.jayfan.store.dao.OrderDao;
import com.jayfan.store.dao.OrderLineItemDao;
import com.jayfan.store.dao.imp.GoodsDaoImpJdbc;
import com.jayfan.store.dao.imp.OrderDaoImpJdbc;
import com.jayfan.store.dao.imp.OrderLineItemDaoImpJdbc;
import com.jayfan.store.domain.Goods;
import com.jayfan.store.domain.OrderLineItem;
import com.jayfan.store.domain.Orders;
import com.jayfan.store.service.OrdersService;

public class OrdersServiceImp implements OrdersService {

	GoodsDao goodsDao = new GoodsDaoImpJdbc();
	OrderDao orderDao = new OrderDaoImpJdbc();
	OrderLineItemDao lineItemDao = new OrderLineItemDaoImpJdbc();

	@Override
	public String submitOrders(List<Map<String, Object>> cart) {
		// TODO Auto-generated method stub
		Orders orders = new Orders();
		Date date = new Date();
		// �q��ID = �ɶ�+�H����
		String ordersId = String.valueOf(date.getTime()) + String.valueOf((int) Math.random() * 100);
		orders.setId(ordersId);
		orders.setOrderDate(date);
		orders.setStatus(1);
		orders.setTotal(0.0f);//���w�]��0

		orderDao.create(orders);
		double total = 0.0;
		for (Map<String, Object> item : cart) {
			// item���c �� [goodsID, quantity]
			Long goodsID = (Long) item.get("goodsid");
			Integer quantity = (Integer) item.get("quantity");
			Goods goods = goodsDao.findByPk(goodsID);
			// �p����B
			double subTotal = quantity * goods.getPrice();
			total += subTotal;
			OrderLineItem lineItem = new OrderLineItem();
			lineItem.setGoods(goods);
			lineItem.setOrders(orders);
			lineItem.setSubTotal(subTotal);
			lineItem.setQuantity(quantity);
			lineItemDao.create(lineItem);
		}
		//�p���`���B�æ^�gDB
		orders.setTotal(total);
		orderDao.modify(orders);
		
		return ordersId;
	}

}
