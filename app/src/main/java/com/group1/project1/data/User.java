package com.group1.project1.data;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

@Entity(tableName="user")
public class User {

	// Username and Password Information
	@PrimaryKey(autoGenerate = true)
	private long id;
	private String username;
	private String password;
	private String berries;
	private String pokemon;
	private long catches;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
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

	public void addPokemon(String name) {
		Gson gson = new Gson();
		JsonArray arr = gson.fromJson(pokemon, JsonArray.class);
		arr.add(name);
		catches++;
		pokemon = gson.toJson(arr);
	}

	public void addBerry(String name) {
		Gson gson = new Gson();
		JsonArray arr = gson.fromJson(berries, JsonArray.class);
		arr.add(name);
		berries = gson.toJson(arr);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id == user.id &&
				catches == user.catches &&
				username.equals(user.username) &&
				password.equals(user.password) &&
				berries.equals(user.berries) &&
				pokemon.equals(user.pokemon);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, berries, pokemon, catches);
	}
}
