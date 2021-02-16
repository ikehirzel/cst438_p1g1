package com.group1.project1;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.group1.project1.dao.UserDao;
import com.group1.project1.data.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
	public static final String DB_NAME = "db-proj1";
	public abstract UserDao getUserDao();
	public static AppDatabase create(Context ctx) {
		return Room.databaseBuilder(ctx, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
	}
}
