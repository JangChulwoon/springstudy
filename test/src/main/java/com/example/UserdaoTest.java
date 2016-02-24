package com.example;

import static org.hamcrest.CoreMatchers.*;
// import stataic 은 뭐냐
import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bean.User;
import dao.UserDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/xml/set-xml/Test_application.xml")
public class UserdaoTest {
	@Autowired
	private ApplicationContext app;
	@Autowired
	UserDao dao;
	// autowired는 빈중에 해당 타입이 있으면 자동으로 연결해줌 .. 
	// getbean을 굳이 쓸 필요 x 
	User user1;
	User user2;
	User user3;

	@Before
	public void setUp() {
		// context 가 반복해서 만들어 지기 때문에 , 이게 많아지면 시간이 상승 ! 어떻게 해결 ?
		user1 = new User("id", "장철1운", "1234");
		user2 = new User("id1", "장철운", "1234");
		user3 = new User("id12", "장철운", "1234");

	}

	@Test
	public void addAndget() throws SQLException, ClassNotFoundException {

		dao.deletAll();
		assertThat(dao.getCount(), is(0));
		dao.add(user1);
		User user2 = dao.get(user1.getId());
		assertThat(dao.getCount(), is(1));
		assertThat(user2.getName(), is(user1.getName()));
		assertThat(user2.getPass(), is(user1.getPass()));
	}

	@Test
	public void count() throws SQLException {

		dao.deletAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		assertThat(dao.getCount(), is(1));

		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		dao.add(user3);
		assertThat(dao.getCount(), is(3));

	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {

		dao.deletAll();
		assertThat(dao.getCount(), is(0));

		dao.get("unknown_id");
	}

}
