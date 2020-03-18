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
		// 訂單ID = 時間+隨機數
		String ordersId = String.valueOf(date.getTime()) + String.valueOf((int) Math.random() * 100);
		orders.setId(ordersId);
		orders.setOrderDate(date);
		orders.setStatus(1);
		orders.setTotal(0.0f);//先預設為0

		orderDao.create(orders);
		double total = 0.0;
		for (Map<String, Object> item : cart) {
			// item結構 為 [goodsID, quantity]
			Long goodsID = (Long) item.get("goodsid");
			Integer quantity = (Integer) item.get("quantity");
			Goods goods = goodsDao.findByPk(goodsID);
			// 計算金額
			double subTotal = quantity * goods.getPrice();
			total += subTotal;
			OrderLineItem lineItem = new OrderLineItem();
			lineItem.setGoods(goods);
			lineItem.setOrders(orders);
			lineItem.setSubTotal(subTotal);
			lineItem.setQuantity(quantity);
			lineItemDao.create(lineItem);
		}
		//計算總金額並回寫DB
		orders.setTotal(total);
		orderDao.modify(orders);
		
		return ordersId;
	}

}
