package com.jayfan.db.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * �B�z���G����H
 */
public interface RowCallbackHandler {
	void processRow(ResultSet rs)throws SQLException;
	
}
