package ioc_Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {
	private DataSource datasource;
	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

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
		con = datasource.getConnection();
		ps = con.prepareStatement("select * from users where id = ?");
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

		if (user == null)
			throw new EmptyResultDataAccessException(1);

		return user;
	}

	public void deletAll() throws SQLException {
		try {
			con = datasource.getConnection();
			ps = con.prepareStatement("delete from users");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}
	}

	public int getCount() throws SQLException {
		int count=0;
		try{
			con = datasource.getConnection();
			ps = con.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);		
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			if(rs!= null){
				try{
				rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
	
		return count;
	}

}
