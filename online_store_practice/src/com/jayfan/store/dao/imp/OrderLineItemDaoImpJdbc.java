package com.jayfan.store.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jayfan.db.core.JdbcTemplate;
import com.jayfan.db.core.PreparedStatementCreator;
import com.jayfan.db.core.RowCallbackHandler;
import com.jayfan.store.dao.OrderLineItemDao;
import com.jayfan.store.domain.Goods;
import com.jayfan.store.domain.OrderLineItem;
import com.jayfan.store.domain.Orders;

public class OrderLineItemDaoImpJdbc implements OrderLineItemDao {
	JdbcTemplate jdbcTemplate = new JdbcTemplate();

	@Override
	public OrderLineItem findByPk(long pk) {
		// TODO Auto-generated method stub
		List<OrderLineItem> list = new ArrayList<OrderLineItem>();
		String sql = "select id,goodsid,orderid,quantity,sub_total from OrderLineItems where id=?";
		jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setLong(1, pk);
				return ps;
			}
		}, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				OrderLineItem lineItem = new OrderLineItem();
				lineItem.setId(rs.getLong("id"));
				lineItem.setQuantity(rs.getInt("quantity"));
				lineItem.setSubTotal(rs.getFloat("sub_total"));

				Orders orders = new Orders();
				orders.setId(rs.getString("orderid"));
				lineItem.setOrders(orders);

				Goods goods = new Goods();
				goods.setId(rs.getLong("goodsid"));
				lineItem.setGoods(goods);
				list.add(lineItem);
			}
		});

		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<OrderLineItem> findAll() {
		// TODO Auto-generated method stub
		List<OrderLineItem> list = new ArrayList<OrderLineItem>();
		String sql = "select id,goodsid,orderid,quantity,sub_total from OrderLineItems";
		jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				return ps;
			}
		}, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				OrderLineItem lineItem = new OrderLineItem();
				lineItem.setId(rs.getLong("id"));
				lineItem.setQuantity(rs.getInt("quantity"));
				lineItem.setSubTotal(rs.getFloat("sub_total"));

				Orders orders = new Orders();
				orders.setId(rs.getString("orderid"));
				lineItem.setOrders(orders);

				Goods goods = new Goods();
				goods.setId(rs.getLong("goodsid"));
				lineItem.setGoods(goods);
				list.add(lineItem);
			}
		});
		return list;
	}

	@Override
	public void create(OrderLineItem lineItem) {
		// TODO Auto-generated method stub
		String sql = "insert into OrderLineItems (id,goodsid,orderid,quantity,sub_total) values (?,?,?,?,?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setLong(1, lineItem.getId());
				ps.setLong(2, lineItem.getGoods().getId());
				ps.setString(3, lineItem.getOrders().getId());
				ps.setInt(4, lineItem.getQuantity());
				ps.setDouble(5, lineItem.getSubTotal());
				return ps;
			}
		});
	}

	@Override
	public void modify(OrderLineItem lineItem) {
		// TODO Auto-generated method stub
		String sql = "update OrderLineItems set goodsid=?,orderid=?,quantity=?,sub_total=? where id=?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setLong(5, lineItem.getId());
				ps.setLong(1, lineItem.getGoods().getId());
				ps.setString(2, lineItem.getOrders().getId());
				ps.setInt(3, lineItem.getQuantity());
				ps.setDouble(4, lineItem.getSubTotal());
				return ps;
			}
		});
	}

	@Override
	public void remove(int pk) {
		// TODO Auto-generated method stub
		String sql = "delete  from OrderLineItems where id=?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, pk);
				return ps;
			}
		});
	}

}
