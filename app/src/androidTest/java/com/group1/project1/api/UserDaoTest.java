package com.group1.project1.api;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.group1.project1.AppDatabase;
import com.group1.project1.dao.UserDao;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserDaoTest {
	@Test
	public void insertTest() {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		assertEquals("com.group1.project1", appContext.getPackageName());
		UserDao dao = AppDatabase.create(appContext).getUserDao();

	}
}
