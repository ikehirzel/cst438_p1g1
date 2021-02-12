package com.group1.project1;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.group1.project1.dao.UserDao;
import com.group1.project1.data.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
	public abstract UserDao getUserDao();
}
