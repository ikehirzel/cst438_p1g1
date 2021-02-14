package com.group1.project1.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

@Entity(tableName="user")
public class User {

	// Username and Password Information
	@PrimaryKey(autoGenerate = true)
	private int id;
	private String username = null;
	private String password = null;
	private String berries = null;
	private String pokemon = null;

	// Constructor
	public User(int id, String username, String password, String berries, String pokemon) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.berries = berries;
		this.pokemon = pokemon;
	}

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public void setPokemon(String pokemon) { this.pokemon = pokemon; }

	public void addPokemon(int pokemonId) {
		Gson gson = new Gson();
		JsonArray arr = gson.fromJson(pokemon, JsonObject.class).getAsJsonArray();
		arr.add(pokemonId);
		pokemon = arr.toString();
	}

	public void addBerry(int berryId) {
		Gson gson = new Gson();
		JsonArray arr = gson.fromJson(berries, JsonObject.class).getAsJsonArray();
		arr.add(berryId);
		berries = arr.toString();
	}
}
