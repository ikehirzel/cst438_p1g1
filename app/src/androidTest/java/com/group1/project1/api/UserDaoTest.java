package com.group1.project1.api;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.group1.project1.AppDatabase;
import com.group1.project1.dao.UserDao;
import com.group1.project1.data.User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserDaoTest {

	@Test
	public void getUserTest() {
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		assertEquals("com.group1.project1", appContext.getPackageName());
		UserDao dao = AppDatabase.create(appContext).getUserDao();

		String testUsername = "?";
		User retUser = null;
		// will continually modify the username till we definitely aren't modifying a pre-existing user
		while ((retUser = dao.getUser(testUsername)) != null) {
			testUsername += "?";
		}


		assertEquals(retUser, null);
		User user = new User(testUsername, "password");
		assertNotEquals(user, null);
		long id = dao.insert(user);
		user.setId(id);
		retUser = dao.getUser(testUsername);
		assertNotEquals(retUser, null);
		assertEquals(user, retUser);
		assertNotEquals(dao.getUsers().size(), 0);
		dao.delete(user);
	}

	@Test
	public void insertAndDeleteTest() {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		assertEquals("com.group1.project1", appContext.getPackageName());
		UserDao dao = AppDatabase.create(appContext).getUserDao();

		String testUsername = "?";

		User retUser = null;
		// will continually modify the username till we definitely aren't modifying a pre-existing user
		while ((retUser = dao.getUser(testUsername)) != null) {
			testUsername += "?";
		}

		// creating new user and setting its id to the one in the table
		User user = new User(testUsername, "test_password");
		long id = dao.insert(user);
		user.setId(id);
		assertNotEquals(id, 0);
		dao.delete(user);
		assertEquals(dao.getUser(id), null);
	}
}
