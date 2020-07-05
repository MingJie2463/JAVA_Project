package com.jayfan.store.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayfan.store.domain.Goods;
import com.jayfan.store.domain.GoodsRepository;
import com.jayfan.store.domain.OrderLineItem;
import com.jayfan.store.domain.OrderLineItemRepository;
import com.jayfan.store.domain.Orders;
import com.jayfan.store.domain.OrdersRepository;
import com.jayfan.store.service.OrdersService;

@Service
public class OrdersServiceImp implements OrdersService {

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private GoodsRepository goodsRepository;

	@Autowired
	private OrderLineItemRepository orderLineItemRepository;

	@Override
	public String submitOrders(List<Map<String, Object>> cart) {
		// TODO Auto-generated method stub
		Orders orders = new Orders();
		Date date = new Date();
		// 訂單ID = 時間+隨機數
		String ordersId = String.valueOf(date.getTime()) + String.valueOf((int) Math.random() * 100);
		orders.setId(ordersId);
		orders.setOrderDate(date.getTime());
		orders.setStatus(1);
		orders.setTotal(0.0f);// 先預設為0

		ordersRepository.save(orders);

		double total = 0.0;
		for (Map<String, Object> item : cart) {
			// item結構 為 [goodsID, quantity]
			Long goodsID = new Long(item.get("goodsid").toString());
			Integer quantity = (Integer) item.get("quantity");
			Goods goods = goodsRepository.getOne(goodsID);
			// 計算金額
			double subTotal = quantity * goods.getPrice();
			total += subTotal;
			OrderLineItem lineItem = new OrderLineItem();
			lineItem.setGoodsid(goodsID);
			lineItem.setOrderid(ordersId);
			lineItem.setSubTotal(subTotal);
			lineItem.setQuantity(quantity);
			orderLineItemRepository.save(lineItem);
		}
		// 計算總金額並回寫DB
		orders.setTotal(total);
		ordersRepository.save(orders);

		return ordersId;
	}

}
