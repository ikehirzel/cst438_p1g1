package com.group1.project1.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.group1.project1.data.User;

import java.util.List;

@Dao
public interface UserDao {
	@Insert
	void insert(User... users);

	@Update
	void update(User... users);

	@Delete
	void delete(User user);

	@Query("SELECT * FROM user")
	public List<User> getUsers();

	@Query("SELECT * FROM user WHERE username=:username")
	public User getUser(String username);
}
