package com.jayfan.db.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * �Ыعw�B�z���y�y��H
 */
public interface PreparedStatementCreator {
	PreparedStatement  createPreparedStatement(Connection conn) throws SQLException;
}
