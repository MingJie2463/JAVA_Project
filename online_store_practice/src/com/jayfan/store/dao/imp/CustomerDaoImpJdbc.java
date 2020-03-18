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
import com.jayfan.store.dao.CustomerDao;
import com.jayfan.store.domain.Customer;

public class CustomerDaoImpJdbc implements CustomerDao {

	JdbcTemplate jdbcTemplate = new JdbcTemplate();

	@Override
	public Customer findByPk(String pk) {
		// TODO Auto-generated method stub
		List<Customer> list = new ArrayList<Customer>();
		String sql = "select id,name,password,address,phone,birthday from Customers where id=?";
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
				Customer customer = new Customer();
				customer.setId(rs.getString("id"));
				customer.setName(rs.getString("name"));
				customer.setPassword(rs.getString("password"));
				customer.setAddress(rs.getString("address"));
				customer.setPhone(rs.getString("phone"));
				customer.setBirthday(new Date(rs.getLong("birthday")));
				list.add(customer);
			}
		});

		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<Customer> list = new ArrayList<Customer>();
		String sql = "select id,name,password,address,phone,birthday from Customers";
		jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				return ps;
			}
		}, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Customer customer = new Customer();
				customer.setId(rs.getString("id"));
				customer.setName(rs.getString("name"));
				customer.setPassword(rs.getString("password"));
				customer.setAddress(rs.getString("address"));
				customer.setPhone(rs.getString("phone"));
				customer.setBirthday(new Date(rs.getLong("birthday")));
				list.add(customer);
			}
		});
		return list;
	}

	@Override
	public void create(Customer customer) {
		// TODO Auto-generated method stub

		String sql = "insert into Customers (id,name,password,address,phone,birthday) values (?,?,?,?,?,?)";

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, customer.getId());
				ps.setString(2, customer.getName());
				ps.setString(3, customer.getPassword());
				ps.setString(4, customer.getAddress());
				ps.setString(5, customer.getPhone());
				ps.setLong(6, customer.getBirthday().getTime());
				return ps;
			}
		}
		);

	}

	@Override
	public void modify(Customer customer) {
		// TODO Auto-generated method stub
		String sql = "update Customers set name=?,password=?,address=?,phone=?,birthday=? where id=?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, customer.getName());
				ps.setString(2, customer.getPassword());
				ps.setString(3, customer.getAddress());
				ps.setString(4, customer.getPhone());
				ps.setLong(5, customer.getBirthday().getTime());
				ps.setString(6, customer.getId());				
				return ps;
			}
		}
		);

	}

	@Override
	public void remove(String pk) {
		// TODO Auto-generated method stub
		String sql = "delete from Customers where id=?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, pk);
				return ps;
			}
		}
		);
	}

}
