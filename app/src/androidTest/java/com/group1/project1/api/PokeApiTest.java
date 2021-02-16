package com.group1.project1.api;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.JsonObject;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class PokeApiTest {

	@Test
	public void useAppContext() {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		assertEquals("com.group1.project1", appContext.getPackageName());

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(PokeApi.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		PokeApi api = retrofit.create(PokeApi.class);
		// Testing species api call
		new Thread() {
			@Override public void run() {
				try {
					// Creating species call
					Call<JsonObject> call = api.getSpecies("ditto");
					JsonObject res = call.execute().body();

					// Species assertions
					assertNotEquals(res, null);
					assertEquals(res.get("name").getAsString(), "ditto");
					assertEquals(res.get("id").getAsInt(), 132);
					assertEquals(res.get("is_legendary").getAsBoolean(), false);

					// Creating pokemon call
					call = api.getPokemon(132);
					res = call.execute().body();

					// Pokemon assertions
					assertNotEquals(res, null);
					assertEquals(res.get("name").getAsString(), "ditto");
					assertEquals(res.get("id").getAsString(), 132);
					assertEquals(res.get("weight").getAsInt(), 40);

					// Creating berry call
					call = api.getBerry(16);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
