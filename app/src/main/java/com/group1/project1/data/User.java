package com.group1.project1.data;

import android.util.Log;

import androidx.room.ColumnInfo;
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
	private long id;
	private String username;
	private String password;
	private String berries;
	private String pokemon;

	public User(String username, String password, String berries, String pokemon) {
		this.username = username;
		this.password = password;
		this.berries = berries;
		this.pokemon = pokemon;
		if (this.berries == null) this.berries = "[]";
		if (this.pokemon == null) this.pokemon = "[]";
	}

	public long getId() { return id; }

	public void setId(long id) { this.id = id; }

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

	public int[] getPokemonList() {
		return new Gson().fromJson(pokemon, int[].class);
	}

	public int[] getBerryList() {
		return new Gson().fromJson(berries, int[].class);
	}

	public void addPokemon(int pokemonId) {
		Gson gson = new Gson();
		JsonArray arr = gson.fromJson(pokemon, JsonArray.class);
		arr.add(pokemonId);
		pokemon = gson.toJson(arr);
	}

	public void addBerry(int berryId) {
		Gson gson = new Gson();
		JsonArray arr = gson.fromJson(berries, JsonArray.class);
		arr.add(berryId);
		berries = gson.toJson(arr);
	}
}
