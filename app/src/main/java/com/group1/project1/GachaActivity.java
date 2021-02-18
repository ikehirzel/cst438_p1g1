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

	private void updateUi(Pair<String, Bitmap> pokemonInfo, Pair<String, Bitmap> berryInfo) {
		runOnUiThread(new Runnable() {
			public void run() {
				pokemonImg.setImageBitmap(pokemonInfo.second);
				String msg = "You got " + pokemonInfo.first;
				if (berryInfo != null) {
					msg += " and " + berryInfo.first;
					berryImg.setImageBitmap(berryInfo.second);
				}
				msgView.setText(msg);
			}
		});
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

	private Pair<String, Bitmap> getBerryInfo(int id) throws IOException {

		Call<JsonObject> call = api.getBerry(id);
		JsonObject res = call.execute().body().get("item").getAsJsonObject();
		String name = res.get("name").getAsString();

		URL url = new URL(PokeApi.BERRY_SPRITE_URL + name + ".png");
		Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

		return new Pair(name, bmp);
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
					Pair<String, Bitmap> berryInfo = null;

					if (user.getCatches() % 3 == 0) {

						berryInfo = getBerryInfo(berryId);
						user.addBerry(berryInfo.first);
					}

					dao.update(user);
					Log.i("GachaActivity", "User Pokemon: " + user.getPokemon());
					Log.i("GachaActivity", "User berries: " + user.getBerries());
					updateUi(pokemonInfo, berryInfo);
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

		db = AppDatabase.create(this);

		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl(PokeApi.BASE_URL)
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
