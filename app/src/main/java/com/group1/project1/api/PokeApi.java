package com.group1.project1.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApi {
	public static final String BASE_URL = "https://pokeapi.co/api/v2/";
	public static final String POKEMON_SPRITE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
	public static final String BERRY_SPRITE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/";
	@GET("pokemon/{id}")
	Call<JsonObject> getPokemon(@Path("id") int id);
	@GET("pokemon-species/{name}")
	Call<JsonObject> getSpecies(@Path("name") String name);
	@GET("berry/{id}")
	Call<JsonObject> getBerry(@Path("id") int id);
	@GET("item/{name}")
	Call<JsonObject> getItem(@Path("name") String name);
}
