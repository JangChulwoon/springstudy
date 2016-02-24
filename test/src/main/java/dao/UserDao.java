package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;

import bean.User;

public class UserDao {
	private DataSource datasource;
	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	// final 로 써야되는 이유 ???
	public void add(final User user) throws SQLException {
		class AddStatement implements StatementStrategy {
			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPass());
				return ps;
			}
		}
		StatementStrategy stmt = new AddStatement();
		jdbcContextWithStatementStrategy(stmt);
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

	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		// 클라이언트가 object 를 주입 해주면 그거에 맞춰 실행된다.
		try {
			con = datasource.getConnection();
			// 여기서 이미 deleteAll이 나타나 잇어서 ocp에 맞지 않음
			ps = stmt.makePreparedStatement(con);
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

	// 얘가 클라이 언트가 됨.
	public void deletAll() throws SQLException {
		// 내부 클래스 (로컬 클래스)
		class DeleteAllStatmenent implements StatementStrategy {

			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = c.prepareStatement("delete from users");
				return ps;
				// 쿼리가 담긴 ps 를 반환
			}

		}

		StatementStrategy stmt = new DeleteAllStatmenent();
		jdbcContextWithStatementStrategy(stmt);
	}

	public int getCount() throws SQLException {
		int count = 0;
		try {
			con = datasource.getConnection();
			ps = con.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
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
