package com.group1.project1.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Api object that provides functionality for https://pokeapi.co
 * @author Ike Hirzel
 */
public interface PokeApi {
	public static final String BASE_URL = "https://pokeapi.co/api/v2/";
	public static final String POKEMON_SPRITE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
	public static final String BERRY_SPRITE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/";

	/**
	 * @param id	Id of pokemon to get info on
	 * @return	Json object representing data of requested pokemon or null if invalid id
	 */
	@GET("pokemon/{id}")
	Call<JsonObject> getPokemon(@Path("id") int id);

	/**
	 * @param name	Name of species to get info on
	 * @return	Json object representing data of requested species or null if invalid name
	 */
	@GET("pokemon-species/{name}")
	Call<JsonObject> getSpecies(@Path("name") String name);

	/**
	 * @param id	Id of berry to get info on
	 * @return	Json object representing data of requested berry or null if invalid id
	 */
	@GET("berry/{id}")
	Call<JsonObject> getBerry(@Path("id") int id);

	/**
	 * @param name	Name of item to get info on
	 * @return	Json object representing data of requested item or null if invalid name
	 */
	@GET("item/{name}")
	Call<JsonObject> getItem(@Path("name") String name);
}
