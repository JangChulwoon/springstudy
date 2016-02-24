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

	// final �� ��ߵǴ� ���� ???
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
		// Ŭ���̾�Ʈ�� object �� ���� ���ָ� �װſ� ���� ����ȴ�.
		try {
			con = datasource.getConnection();
			// ���⼭ �̹� deleteAll�� ��Ÿ�� �վ ocp�� ���� ����
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

	// �갡 Ŭ���� ��Ʈ�� ��.
	public void deletAll() throws SQLException {
		// ���� Ŭ���� (���� Ŭ����)
		class DeleteAllStatmenent implements StatementStrategy {

			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				// TODO Auto-generated method stub
				PreparedStatement ps = c.prepareStatement("delete from users");
				return ps;
				// ������ ��� ps �� ��ȯ
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
