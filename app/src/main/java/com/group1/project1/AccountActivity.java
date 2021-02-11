package com.group1.project1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

	private EditText usernameEdit;
	private EditText passwordEdit;

	private Button loginButton;
	private Button createAccountButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);

		// binding button objects to ui
		loginButton = findViewById(R.id.login_button);
		createAccountButton = findViewById(R.id.create_account_button);
		usernameEdit = findViewById(R.id.username_edit);
		passwordEdit = findViewById(R.id.password_edit);

		// setting button listeners
		loginButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				// get the entered credentials
				String username = usernameEdit.getText().toString();
				String password = passwordEdit.getText().toString();

				boolean inputNotValid = false;
				// flag error if the user did not put in a username
				if (username.isEmpty()) {
					usernameEdit.setError("Username may not be empty");
					inputNotValid = true;
				}

				// flag error if the password did not put in a username
				if (password.isEmpty()) {
					passwordEdit.setError("Password may not be empty");
					inputNotValid = true;
				}

				// exit is credentials were not valid
				if (inputNotValid) return;

				// TODO get a way to return user id from credentials

				// loading credentials into intent to be returned
				Intent resultIntent = new Intent();
				resultIntent.putExtra("id", 0);

				// returning data
				setResult(RESULT_OK, resultIntent);
				finish();
			}
		});

		createAccountButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

			}
		});
	}
}
