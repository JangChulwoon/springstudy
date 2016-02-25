package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.User;

public class UserDao {
	private JdbcTemplate context = null;
	private RowMapper<User> usermapper = new RowMapper<User>() {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPass(rs.getString("password"));
			return user;
		}

	};

	public void setDatasource(DataSource datasource) {
		this.context = new JdbcTemplate(datasource);
	}

	public List<User> getAll() {
		return this.context.query("select * from users order by id", usermapper);
	}

	public User get(String id) {
		return this.context.queryForObject("select * from users where id =?", new Object[] { id }, usermapper);
	}

	// 얘가 클라이 언트가 됨.
	public void deletAll() {
		// 내부 클래스 (로컬 클래스)
		this.context.update("delete from users");
	}

	public int getCount() {
		return this.context.queryForInt("select count(*) from users ");
	}

	public void add(final User user) {
		this.context.update("insert into users(id,name,password) values(?,?,?)", user.getId(), user.getName(),
				user.getPass());
	}

}
