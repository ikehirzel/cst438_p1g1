package com.group1.project1;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.group1.project1.dao.UserDao;
import com.group1.project1.data.User;

import java.util.concurrent.Executors;

/**
 * Database to store users and their inventory
 * @author Ike Hirzel
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
	public static final String DB_NAME = "db-proj1";

	private static AppDatabase INSTANCE;

	/**
	 * Gets the DAO to manage users
	 * @return Returns the DAO to manage users
	 */
	public abstract UserDao getUserDao();

	public synchronized static AppDatabase getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = create(context);
		}
		return INSTANCE;
	}

	/**
	 * Factory function to make a handle for the database
	 * @param ctx	Current application context
	 * @return		an object encapsulating the database
	 */
	public static AppDatabase create(final Context ctx) {
		return Room.databaseBuilder(ctx, AppDatabase.class, DB_NAME)
				.addCallback(new Callback(){
					@Override
					public void onCreate(@NonNull SupportSQLiteDatabase db) {
						super.onCreate(db);
						Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
							@Override
							public void run() {
								getInstance(ctx).getUserDao().insert(User.populateData());
							}
						});
					}
				})
				.allowMainThreadQueries().build();
	}
}
