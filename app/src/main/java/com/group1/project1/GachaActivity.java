package com.group1.project1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Random;

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

	private int getCatch() {
		Random rng = new Random();
		int id = rng.nextInt(800);

		return 0;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gacha);

		msgView = findViewById(R.id.gacha_msg_view);
		confirmButton = findViewById(R.id.gacha_confirm_button);
		pokemonImg = findViewById(R.id.gacha_pokemon_img);
		berryImg = findViewById(R.id.gacha_berry_img);

		db = Room.databaseBuilder(this, AppDatabase.class, "db-proj1")
			.allowMainThreadQueries().build();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://jsonplaceholder.typicode.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		api = retrofit.create(PokeApi.class);

		// if the user pressed the catch pokemon button
		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Log.i("GachaActivity", "Hit confirm button");
			}
		});
	}
}
