package com.group1.project1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.gson.JsonObject;
import com.group1.project1.api.PokeApi;
import com.group1.project1.dao.UserDao;
import com.group1.project1.data.User;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class GachaActivity extends AppCompatActivity {

	private TextView msgView;
	private Button confirmButton;
	private ImageView pokemonImg;
	private ImageView berryImg;

	private AppDatabase db;
	private PokeApi api;

	private void updateUi(int pokemonId, String pokeName, String berryName) {
		try {
			URL url = new URL(PokeApi.POKEMON_SPRITE_URL + pokemonId + ".png");
			final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			runOnUiThread(new Runnable() {
				public void run() {
					pokemonImg.setImageBitmap(bmp);
					String msg = "You got " + pokeName;
					if (berryName != null) {
						msg += " and " + berryName + " berry";
					}
					msgView.setText(msg);
				}
			});

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private Pair<String, Bitmap> getPokemonInfo(int id) throws IOException {
		Call<JsonObject> call = api.getPokemon(id);
		JsonObject res = call.execute().body();
		JsonObject species = res.get("species").getAsJsonObject();
		String name = species.get("name").getAsString();
		URL url = new URL(PokeApi.POKEMON_SPRITE_URL + id + ".png");
		Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		return new Pair(name, bmp);
	}

	private String getBerryName(int id) throws IOException {
		Call<JsonObject> call = api.getBerry(id);
		JsonObject res = call.execute().body();
		return res.get("name").getAsString();
	}

	private void handleCatch(long userId) {

		Random rng = new Random();
		UserDao dao = db.getUserDao();
		User user = dao.getUser(userId);
		int pokemonId = rng.nextInt(800);
		int berryId = rng.nextInt(64);

		new Thread() {
			@Override public void run () {
				try {

					Pair<String, Bitmap> pokemonInfo = getPokemonInfo(pokemonId);
					user.addPokemon(pokemonInfo.first);
					Log.i("GachaActivity", "Adding pokemon '" + pokemonInfo.first + "' to user: " + userId);
					String berryName = null;

					if (user.getCatches() % 3 == 0) {

						berryName = getBerryName(berryId);
						user.addBerry(berryName);
					}

					dao.update(user);
					Log.i("GachaActivity", "User Pokemon: " + user.getPokemon());

					updateUi(pokemonId, pokemonInfo.first, berryName);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gacha);

		long userId = getIntent().getExtras().getLong("id", -1);
		if (userId == -1) {
			finish();
		}

		Log.i("GachaActivity", "Starting for user: " + userId);

		msgView = findViewById(R.id.gacha_msg_view);
		confirmButton = findViewById(R.id.gacha_confirm_button);
		pokemonImg = findViewById(R.id.gacha_pokemon_img);
		berryImg = findViewById(R.id.gacha_berry_img);

		db = Room.databaseBuilder(this, AppDatabase.class, "db-proj1")
			.allowMainThreadQueries().build();

		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://pokeapi.co/api/v2/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();

		api = retrofit.create(PokeApi.class);

		// if the user pressed the catch pokemon button
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});

		handleCatch(userId);
	}
}
