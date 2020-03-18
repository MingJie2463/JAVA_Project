package com.jayfan.db.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * 創建預處理的語句對象
 */
public interface PreparedStatementCreator {
	PreparedStatement  createPreparedStatement(Connection conn) throws SQLException;
}
