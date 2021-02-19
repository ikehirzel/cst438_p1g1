package com.group1.project1.data;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents the data for an account
 * @author Ike Hirzel
 */
@Entity(tableName="user", indices = {@Index(value = {"username"}, unique = true)})
public class User {

	// Username and Password Information
	@PrimaryKey(autoGenerate = true)
	private long id;
	private String username;
	private String password;
	private boolean admin;
	// The inventory that holds the users berries
	private String berries;
	// The inventory that holds the users pokemon
	private String pokemon;
	// the amount of pokemon the user has caught
	private long catches;

	/**
	 * Constructs new user with username and password as specified and empty inventory/ no catches
	 * @param username	username of new user
	 * @param password	password of new user
	 */
	public User(String username, String password, boolean admin) {
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.catches = 0;
		this.berries = "[]";
		this.pokemon = "[]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getAdmin() { return admin; }

	public void setAdmin(boolean admin) { this.admin = admin; }

	public String getBerries() {
		return berries;
	}

	public void setBerries(String berries) {
		this.berries = berries;
	}

	public String getPokemon() {
		return pokemon;
	}

	public void setPokemon(String pokemon) {
		this.pokemon = pokemon;
	}

	public long getCatches() {
		return catches;
	}

	public void setCatches(long catches) {
		this.catches = catches;
	}

	public String[] getPokemonList() {
		return new Gson().fromJson(pokemon, String[].class);
	}

	public String[] getBerryList() {
		return new Gson().fromJson(berries, String[].class);
	}

	/**
	 *
	 * @return
	 */
	public static User populateData() {
		return new User("admin", "admin", true);
	}

	/**
	 * Converts pokemon to json object and adds pokemon's name
	 * @param name	name of the pokemon
	 */
	public void addPokemon(String name) {
		Gson gson = new Gson();
		JsonArray arr = gson.fromJson(pokemon, JsonArray.class);
		arr.add(name);
		catches++;
		pokemon = gson.toJson(arr);
	}

	/**
	 * Converts pokemon to json object and adds berry's name
	 * @param name	name of the berry
	 */
	public void addBerry(String name) {
		Gson gson = new Gson();
		JsonArray arr = gson.fromJson(berries, JsonArray.class);
		arr.add(name);
		berries = gson.toJson(arr);
	}

	/**
	 * Checks to see if user is equivalent to an object
	 * @param	o	object being compared to
	 * @return	a boolean that represents if the object and user are equal
	 */
	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return 	id == user.id &&
				catches == user.catches &&
				username.equals(user.username) &&
				password.equals(user.password) &&
				berries.equals(user.berries) &&
				pokemon.equals(user.pokemon);
	}

	/**
	 * Gets hashcode of user
	 * @return hashcode of user
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override public int hashCode() {
		return Objects.hash(id, username, password, berries, pokemon, catches);
	}


}
