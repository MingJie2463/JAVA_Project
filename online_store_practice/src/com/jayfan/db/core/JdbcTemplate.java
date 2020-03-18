package com.jayfan.db.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
	
	//�d��
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
			throw new DataAccessException("JdbcTemplate����SQLException:" ,e);
		}
		catch(ClassNotFoundException e) {
			throw new DataAccessException("JdbcTemplate����ClassNotFoundException:" ,e);
		}
		finally {
			if(connection != null) {
				try {
					connection.close();
				}
				catch(SQLException e) {
					throw new DataAccessException("JdbcTemplate���L�k����DB�s��:" ,e);
				}
			}
			if(prepraredStatement != null) {
				try {
					prepraredStatement.close();
				}
				catch(SQLException e) {
					throw new DataAccessException("JdbcTemplate���L�k����y�y��Q:" ,e);
				}
			}
			if(resultSet != null) {
				try {
					resultSet.close();
				}
				catch(SQLException e) {
					throw new DataAccessException("JdbcTemplate���L�k�������G����H:" ,e);
				}
			}
		}
	}
	
	//��s
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
			throw new DataAccessException("JdbcTemplate����SQLException:" ,e);
		}
		catch(ClassNotFoundException e) {
			throw new DataAccessException("JdbcTemplate����ClassNotFoundException:" ,e);
		}
		finally {
			if(connection != null) {
				try {
					connection.close();
				}
				catch(SQLException e) {
					throw new DataAccessException("JdbcTemplate���L�k����DB�s��:" ,e);
				}
			}
			if(prepraredStatement != null) {
				try {
					prepraredStatement.close();
				}
				catch(SQLException e) {
					throw new DataAccessException("JdbcTemplate���L�k����y�y��Q:" ,e);
				}
			}
		}
	}
	
}
