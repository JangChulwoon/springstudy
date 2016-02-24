package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public interface StatementStrategy {
	PreparedStatement makePreparedStatement(Connection c) throws SQLException;
	// 여기에 try catch등 뼈대가 들어감
}
