package com.group1.project1.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApi {
	public static final String POKEMON_SPRITE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
	@GET("pokemon/{id}")
	Call<JsonObject> getPokemon(@Path("id") int id);
	@GET("pokemon-species/{id}")
	Call<JsonObject> getSpecies(@Path("id") int id);
	@GET("berry/{id}")
	Call<JsonObject> getBerry(@Path("id") int id);
}
