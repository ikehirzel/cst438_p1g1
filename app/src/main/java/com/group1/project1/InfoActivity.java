package com.group1.project1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.group1.project1.api.PokeApi;

import java.io.IOException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoActivity extends AppCompatActivity {

	private TextView nameText;
	private ImageView image;
	private TextView descriptionText;

	PokeApi api;

	public void updateBerryInfo(String name) {

		Log.i("InfoActivity", "Updating berry info: " + name);

		new Thread() {
			@Override public void run() {
				Call<JsonObject> call =  api.getBerry(name);
				String desc = new String();
				try {
					JsonObject res = call.execute().body();
					if (res == null) {
						Log.e("InfoActivity", "Res was not received");
						return;
					}
					desc += "Size: " + res.get("size").getAsString();
					desc += "\n\nSmoothness: " + res.get("smoothness").getAsString();
					desc += "\n\nGrowth Time: " + res.get("growth_time").getAsString();
					desc += "\n\nMax Harvest: " + res.get("max_harvest").getAsString();

					final String description = desc;

					runOnUiThread(new Runnable() {
						@Override public void run() {
							descriptionText.setText(description);
						}
					});

				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void updatePokemonInfo(String name) {

		Log.i("InfoActivity", "Updating pokemon info: " + name);

		new Thread() {
			@Override public void run() {
				Call<JsonObject> call =  api.getSpecies(name);

				try {
					String description = new String();
					JsonObject res = call.execute().body();
					if (res == null) {
						Log.e("InfoActivity", "Res was not received");
						return;
					}

					if (res.get("is_legendary").getAsBoolean()) {
						description += "Legendary Pokemon\n";
					}
					else if (res.get("is_mythical").getAsBoolean()) {
						description += "Mythical Pokemon\n";
					}
					else if (res.get("is_baby").getAsBoolean()) {
						description += "Baby Pokemon\n";
					}
					else {
						description += "Normal Pokemon\n";
					}

					JsonArray flavorEntries = res.get("flavor_text_entries").getAsJsonArray();
					String flavorText = null;

					for (int i = 0; i < flavorEntries.size(); i++) {
						JsonObject entry = flavorEntries.get(i).getAsJsonObject();
						String lang = entry.get("language").getAsJsonObject().get("name").getAsString();

						if (lang.equals("en")) {

							flavorText = entry.get("flavor_text").getAsString();
							break;
						}
					}

					if (flavorText == null) {
						description += "No Description available";
					} else {
						description += '\n' + flavorText;
					}

					URL url = new URL(PokeApi.POKEMON_SPRITE_URL + res.get("id").getAsInt() + ".png");

					final String desc = description;
					final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

					runOnUiThread(new Runnable() {
						@Override public void run() {
							descriptionText.setText(desc);
							image.setImageBitmap(bmp);
						}
					});

				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		if (name == null) finish();
		boolean isPokemon = intent.getBooleanExtra("pokemon", true);

		nameText = findViewById(R.id.info_name_text);
		descriptionText = findViewById(R.id.info_description_text);
		image = findViewById(R.id.info_image);
		nameText.setText(name);

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://pokeapi.co/api/v2/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		api = retrofit.create(PokeApi.class);

		if (isPokemon) {
			updatePokemonInfo(name);
		}
		else {
			updateBerryInfo(name);
		}

	}
}
