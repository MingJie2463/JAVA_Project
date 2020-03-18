package com.jayfan.db.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 處理結果集對象
 */
public interface RowCallbackHandler {
	void processRow(ResultSet rs)throws SQLException;
	
}
