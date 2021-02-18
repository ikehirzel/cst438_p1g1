package com.group1.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.group1.project1.dao.UserDao;
import com.group1.project1.data.User;

/**
 * Main activity that acts a landing page for the app
 * @author Ike Hirzel
 */
public class MainActivity extends AppCompatActivity {

	public static final int ACCOUNT_REQUEST_CODE = 1;

	private long userId = -1;

	private AppDatabase db;

	private TextView greetingView;
	private Button catchButton;
	private Button pokemonButton;
	private Button berriesButton;
	private Button logoutButton;
	private Button deleteButton;

	/**
	 * Binds all the UI elements and adds button listeners to all the buttons
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// logging into account
		// safeguards against if we happen to start the activity while already logged in
		if (userId < 1) {
			startActivityForResult(new Intent(this, AccountActivity.class), ACCOUNT_REQUEST_CODE);
		}

		// binding objects to interface
		greetingView = findViewById(R.id.greeting_view);
		catchButton = findViewById(R.id.catch_button);
		pokemonButton = findViewById(R.id.pokemon_button);
		berriesButton = findViewById(R.id.berries_button);
		deleteButton = findViewById(R.id.delete_account_button);
		logoutButton = findViewById(R.id.logout_button);

		db = AppDatabase.create(this);

		// if the user pressed the catch pokemon button
		catchButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				// if not logged in, do nothing
				if (userId < 1) return;
				Log.i("MainActivity", "Starting gacha");
				Intent gachaIntent = new Intent(view.getContext(), GachaActivity.class);
				// pass in user id so it can add things to user inventory
				gachaIntent.putExtra("id", userId);
				startActivity(gachaIntent);
			}
		});

		// brings user to list of pokemon in inventory
		pokemonButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				// if not logged in, do nothing
				if (userId < 1) return;
				Log.i("MainActivity", "Checking pokemon for user: " + userId);
				Intent intent = new Intent(view.getContext(), InventoryActivity.class);
				// pass in current user's id
				intent.putExtra("id", userId);
				intent.putExtra("pokemon", true);
				startActivity(intent);
			}
		});

		// brings user to list of berries in inventory
		berriesButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				// if not logged in, do nothing
				if (userId < 1) return;
				Log.i("MainActivity", "Checking berries for user: " + userId);
				Intent intent = new Intent(view.getContext(), InventoryActivity.class);
				// pass in user id so we can see inventory
				intent.putExtra("id", userId);
				intent.putExtra("pokemon", false);
				startActivity(intent);
			}
		});

		// logs out the user
		logoutButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Log.i("MainActivity", "Logging out user: " + userId);
				// set current id to an invalid one
				userId = -1;
				// log the user in
				startActivityForResult(new Intent(view.getContext(), AccountActivity.class), ACCOUNT_REQUEST_CODE);
			}
		});

		// Deletes the account from the database
		deleteButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				if (userId < 1) return;

				// getting user with corresponding id
				UserDao dao = db.getUserDao();
				User user = dao.getUser(userId);

				// if no such user exists, do nothing
				if (user == null) return;

				// remove user from database
				Log.i("MainActivity", "Deleting user '" + user.getUsername() + "'");
				dao.delete(user);

				// log user in
				startActivityForResult(new Intent(view.getContext(), AccountActivity.class), ACCOUNT_REQUEST_CODE);
			}
		});
	}

	/**
	 * Activated upon result from a started activity. In the common use case, sets the account id
	 * for the logged in user
	 * @param requestCode	The code sent to the activity requesting a result from
	 * @param resultCode	The code sent back from the activity
	 * @param data			The intent passed back from the activity
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			// switch statement in case there are more in the future
			switch (requestCode) {

			case ACCOUNT_REQUEST_CODE:
				long id = data.getLongExtra("id", -1);
				if (id < 1) {
					Log.i("MainActivity", "Invalid account info!!!");
					return;
				}

				// set user id to the corresponding log in
				Log.i("MainActivity", "Got id: " + id);
				userId = id;
				greetingView.setText("Hello, " + db.getUserDao().getUser(userId).getUsername());

				break;
			}
		}
	}
}