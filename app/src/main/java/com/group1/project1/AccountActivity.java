package com.group1.project1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.group1.project1.data.User;

public class AccountActivity extends AppCompatActivity {

	private EditText usernameEdit;
	private EditText passwordEdit;

	private Button loginButton;
	private Button createAccountButton;

	private AppDatabase db;

	private boolean verifyCredentials(String username, String password) {

		boolean valid = true;
		// flag error if the user did not put in a username
		if (username.isEmpty()) {
			usernameEdit.setError("Username may not be empty");
			valid = false;
		}

		// flag error if the password did not put in a username
		if (password.isEmpty()) {
			passwordEdit.setError("Password may not be empty");
			valid = false;
		}

		// exit is credentials were not valid
		return valid;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);

		// binding button objects to ui
		loginButton = findViewById(R.id.login_button);
		createAccountButton = findViewById(R.id.create_account_button);
		usernameEdit = findViewById(R.id.username_edit);
		passwordEdit = findViewById(R.id.password_edit);

		db = AppDatabase.create(this);

		// setting button listeners
		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String username = usernameEdit.getText().toString();
				String password = passwordEdit.getText().toString();
				if (!verifyCredentials(username, password)) return;


				User user = db.getUserDao().getUser(username);

				if (user == null) {
					usernameEdit.setError("User '" + username + "' does not exist");
					return;
				}
				if (!user.getPassword().equals(password)) {
					passwordEdit.setError("Incorrect password");
					return;
				}

				String[] pokemon = user.getPokemonList();
				String list = new String();
				for (int i = 0; i < pokemon.length; i++) {
					if (i > 0) list += ", ";
					list += pokemon[i];
				}

				Log.i("AccountActivity", "Current pokemon: " + list);

				// loading credentials into intent to be returned
				Intent resultIntent = new Intent();
				resultIntent.putExtra("id", user.getId());

				// returning data
				setResult(RESULT_OK, resultIntent);
				finish();
			}
		});

		createAccountButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String username = usernameEdit.getText().toString();
				String password = passwordEdit.getText().toString();

				if (!verifyCredentials(username, password)) return;
				if (db.getUserDao().getUser(username) != null) {
					usernameEdit.setError("User '" + username + "' already exists");
					return;
				}

				Toast.makeText(view.getContext(), "Successfully created user: " + username, Toast.LENGTH_SHORT).show();
				User user = new User(username, password);
				long userId =  db.getUserDao().insert(user);
				Log.i("AccountActivity", "Created user with id: " + userId);

				// loading credentials into intent to be returned
				Intent resultIntent = new Intent();
				resultIntent.putExtra("id", userId);

				// returning data
				setResult(RESULT_OK, resultIntent);
				finish();
			}
		});
	}
}
