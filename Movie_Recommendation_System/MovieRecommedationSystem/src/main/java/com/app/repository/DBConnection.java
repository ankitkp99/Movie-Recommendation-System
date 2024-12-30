package com.app.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
	
	protected DBConfig config = DBConfig.getInstance();
	protected Connection conn = config.getConnection();
	protected PreparedStatement pstmt = config.getPreparedStatement();
	protected ResultSet rs = config.getResultSet();
	protected ResultSet rs1 = config.getOtherResultSet();
}
