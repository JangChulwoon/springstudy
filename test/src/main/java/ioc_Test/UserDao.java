package ioc_Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {
	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public void add(User user) throws SQLException {
		Connection con = datasource.getConnection();
		PreparedStatement ps = con.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPass());

		ps.executeUpdate();
		ps.close();
		con.close();
	}

	public User get(String id) throws SQLException {
		Connection con = datasource.getConnection();

		PreparedStatement ps = con.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		User user = null;
		while (rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPass(rs.getString("password"));

		}
		rs.close();
		ps.close();
		con.close();
		
		if(user==null) throw new EmptyResultDataAccessException(1);
		
		return user;
	}

	public void deletAll() throws SQLException {
		Connection con = datasource.getConnection();

		PreparedStatement ps = con.prepareStatement("delete from users");
		ps.executeUpdate();

		ps.close();
		con.close();
	}

	public int getCount() throws SQLException {
		Connection con = datasource.getConnection();

		PreparedStatement ps = con.prepareStatement("select count(*) from users");
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		ps.close();
		con.close();
		return count;
	}

}
