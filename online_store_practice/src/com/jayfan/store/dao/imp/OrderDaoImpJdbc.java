package com.jayfan.store.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jayfan.db.core.JdbcTemplate;
import com.jayfan.db.core.PreparedStatementCreator;
import com.jayfan.db.core.RowCallbackHandler;
import com.jayfan.store.dao.OrderDao;
import com.jayfan.store.domain.Orders;

public class OrderDaoImpJdbc implements OrderDao {

	JdbcTemplate jdbcTemplate = new JdbcTemplate();

	@Override
	public Orders findByPk(String pk) {
		// TODO Auto-generated method stub
		List<Orders> list = new ArrayList<Orders>();
		String sql = "select id,order_date,status,total from Orders where id=?";
		jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, pk);
				return ps;
			}
		}, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Orders orders = new Orders();
				orders.setId(rs.getString("id"));
				orders.setOrderDate(new Date(rs.getLong("order_date")));
				orders.setStatus(rs.getInt("status"));
				orders.setTotal(rs.getFloat("total"));
				list.add(orders);
			}
		});

		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Orders> findAll() {
		// TODO Auto-generated method stub
		List<Orders> list = new ArrayList<Orders>();
		String sql = "select id,order_date,status,total from Orders";
		jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				return ps;
			}
		}, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Orders orders = new Orders();
				orders.setId(rs.getString("id"));
				orders.setOrderDate(new Date(rs.getLong("order_date")));
				orders.setStatus(rs.getInt("status"));
				orders.setTotal(rs.getFloat("total"));
				list.add(orders);
			}
		});

		return list;
	}

	@Override
	public void create(Orders orders) {
		// TODO Auto-generated method stub
		String sql = "insert into Orders (id,order_date,status,total) values (?,?,?,?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, orders.getId());
				ps.setLong(2, orders.getOrderDate().getTime());
				ps.setInt(3, orders.getStatus());
				ps.setDouble(4, orders.getTotal());
				return ps;
			}
		});

	}

	@Override
	public void modify(Orders orders) {
		// TODO Auto-generated method stub
		String sql = "update Orders set order_date=?,status=?,total=? where id=?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(4, orders.getId());
				ps.setLong(1, orders.getOrderDate().getTime());
				ps.setInt(2, orders.getStatus());
				ps.setDouble(3, orders.getTotal());
				return ps;
			}
		});
	}

	@Override
	public void remove(String pk) {
		// TODO Auto-generated method stub
		String sql = "delete from Orders where id=?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, pk);
				return ps;
			}
		});
	}

}
