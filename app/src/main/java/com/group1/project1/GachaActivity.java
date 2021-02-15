package com.group1.project1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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

import java.net.URL;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GachaActivity extends AppCompatActivity {

	private TextView msgView;
	private Button confirmButton;
	private ImageView pokemonImg;
	private ImageView berryImg;

	private AppDatabase db;
	private PokeApi api;

	/*
	* URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
imageView.setImageBitmap(bmp);*/

	private void updateImage(String urlString, ImageView img) {
		Log.i("GachaActivity", "update image call");
		new Thread() {
			@Override
			public void run() {
				try {
					URL url = new URL(urlString);
					final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
					runOnUiThread(new Runnable() {
						public void run() {
							img.setImageBitmap(bmp);
						}
					});

				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void handleCatch(long userId) {
		Random rng = new Random();
		int pokemonId = rng.nextInt(800);
		Log.i("GachaActivity", "Getting data for pokemon: " + pokemonId);
		Call<JsonObject> pokemonCall = api.getPokemon(pokemonId);
		pokemonCall.enqueue(new Callback<JsonObject>() {
			@Override
			public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
				JsonObject json = response.body();
				String name = json.get("name").getAsString();
				String spriteUrl = json.get("sprites").getAsJsonObject().get("front_default").getAsString();
				Log.i("GachaActivity", "Poke name: " + name);
				Log.i("GachaActivity", "Poke url: " + spriteUrl);
				updateImage(spriteUrl, pokemonImg);
				msgView.setText("You got " + name);

				//Log.i("GachaActivity", "");
				Log.i("GachaActivity", "Adding pokemon " + pokemonId + " to user: " + userId);
				UserDao dao = db.getUserDao();
				User user = dao.getUser(userId);
				user.addPokemon(pokemonId);
				dao.update(user);
				Log.i("GachaActivity", "User pokemon: " + user.getPokemon());
			}

			@Override
			public void onFailure(Call<JsonObject> call, Throwable t) {
				//Handle failure
			}
		});
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
				Log.i("GachaActivity", "Hit confirm button");
				finish();
			}
		});

		handleCatch(userId);
	}
}
