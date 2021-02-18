package com.group1.project1;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.group1.project1.dao.UserDao;
import com.group1.project1.data.User;

/**
 * Database to store users and their inventory
 * @author Ike Hirzel
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
	public static final String DB_NAME = "db-proj1";

	/**
	 * Gets the DAO to manage users
	 * @return Returns the DAO to manage users
	 */
	public abstract UserDao getUserDao();

	/**
	 * Factory function to make a handle for the database
	 * @param ctx	Current application context
	 * @return		an object encapsulating the database
	 */
	public static AppDatabase create(Context ctx) {
		return Room.databaseBuilder(ctx, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
	}
}
