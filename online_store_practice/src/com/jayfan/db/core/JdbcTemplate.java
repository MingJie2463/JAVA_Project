package com.jayfan.db.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
	
	//查詢
	public void query(PreparedStatementCreator pscreator, 
						RowCallbackHandler callbackHandler) throws DataAccessException {
		Connection connection = null;
		PreparedStatement prepraredStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DBHelp.getConnection();
			prepraredStatement = pscreator.createPreparedStatement(connection);
			resultSet = prepraredStatement.executeQuery();
			while(resultSet.next()) {
				callbackHandler.processRow(resultSet);
			}
		}
		catch(SQLException e) {
			//e.printStackTrace();
			throw new DataAccessException("JdbcTemplate中的SQLException:" ,e);
		}
		catch(ClassNotFoundException e) {
			throw new DataAccessException("JdbcTemplate中的ClassNotFoundException:" ,e);
		}
		finally {
			if(connection != null) {
				try {
					connection.close();
				}
				catch(SQLException e) {
					throw new DataAccessException("JdbcTemplate中無法關閉DB連結:" ,e);
				}
			}
			if(prepraredStatement != null) {
				try {
					prepraredStatement.close();
				}
				catch(SQLException e) {
					throw new DataAccessException("JdbcTemplate中無法釋放語句對想:" ,e);
				}
			}
			if(resultSet != null) {
				try {
					resultSet.close();
				}
				catch(SQLException e) {
					throw new DataAccessException("JdbcTemplate中無法關閉結果集對象:" ,e);
				}
			}
		}
	}
	
	//更新
	public void update(PreparedStatementCreator pscreator)  throws DataAccessException {
		Connection connection = null;
		PreparedStatement prepraredStatement = null;
		try {
			connection = DBHelp.getConnection();
			prepraredStatement = pscreator.createPreparedStatement(connection);
			prepraredStatement.executeUpdate();
		}
		catch(SQLException e) {
			//e.printStackTrace();
			throw new DataAccessException("JdbcTemplate中的SQLException:" ,e);
		}
		catch(ClassNotFoundException e) {
			throw new DataAccessException("JdbcTemplate中的ClassNotFoundException:" ,e);
		}
		finally {
			if(connection != null) {
				try {
					connection.close();
				}
				catch(SQLException e) {
					throw new DataAccessException("JdbcTemplate中無法關閉DB連結:" ,e);
				}
			}
			if(prepraredStatement != null) {
				try {
					prepraredStatement.close();
				}
				catch(SQLException e) {
					throw new DataAccessException("JdbcTemplate中無法釋放語句對想:" ,e);
				}
			}
		}
	}
	
}
