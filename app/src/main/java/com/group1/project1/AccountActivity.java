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

/**
 * Activity that allows the user to login
 * @author Ike Hirzel
 */
public class AccountActivity extends AppCompatActivity {

	private EditText usernameEdit;
	private EditText passwordEdit;

	private Button loginButton;
	private Button createAccountButton;

	private AppDatabase db;

	/**
	 * Checks if a user with username @username exists and verifies that @password is the correct
	 * password
	 *
	 * @param username	Username of the user
	 * @param password	Password of the user
	 * @return	a boolean that is true if the user exists, false if it does not
	 */
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

	/**
	 * Binds UI elements and sets button listeners
	 * @param savedInstanceState
	 */
	@Override protected void onCreate(Bundle savedInstanceState) {
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

				// gets user from db
				User user = db.getUserDao().getUser(username);

				if (user == null) {
					usernameEdit.setError("User '" + username + "' does not exist");
					return;
				}
				if (!user.getPassword().equals(password)) {
					passwordEdit.setError("Incorrect password");
					return;
				}

				// loading credentials into intent to be returned
				Intent resultIntent = new Intent();
				resultIntent.putExtra("id", user.getId());

				// returning data
				setResult(RESULT_OK, resultIntent);
				finish();
			}
		});

		// Adds user to the database
		createAccountButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				//gets credentials from EditTexts
				String username = usernameEdit.getText().toString();
				String password = passwordEdit.getText().toString();

				// checks if the credentials are OK
				if (!verifyCredentials(username, password)) return;

				if (db.getUserDao().getUser(username) != null) {
					usernameEdit.setError("User '" + username + "' already exists");
					return;
				}

				// creates new user
				User user = new User(username, password);
				// adds user to db
				long userId =  db.getUserDao().insert(user);
				// Confirms to the user that the user was created
				Toast.makeText(view.getContext(), "Successfully created user: " + username, Toast.LENGTH_SHORT).show();
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
