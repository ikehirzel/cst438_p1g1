package com.group1.project1.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.group1.project1.data.User;

import java.util.List;

/**
 * Accessor for the user table in the database
 */
@Dao
public interface UserDao {
	/**
	 * Inserts new user
	 * @param user user object to insert
	 * @return id of new user
	 */
	@Insert
	long insert(User user);

	/**
	 * Updates the database info of the passed in user
	 * @param user User to update
	 */
	@Update
	void update(User user);

	/**
	 * Deletes the database info of the passed in user
	 * @param user	User to delete
	 */
	@Delete
	void delete(User user);

	/**
	 * Gets list of all users
	 * @return list of all users
	 */
	@Query("SELECT * FROM user")
	public List<User> getUsers();

	/**
	 * Gets user with the same user name as specified
	 * @param username	Username of desired user
	 * @return User object
	 */
	@Query("SELECT * FROM user WHERE username=:username")
	public User getUser(String username);

	/**
	 * Gets user with the same id as specified
	 * @param id	Id of specified user
	 * @return	User object
	 */
	@Query("SELECT * FROM user WHERE id=:id")
	public User getUser(long id);
}
