package com.developer.jacob.statmaster;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button mBackButton;
    Button mSignInButtonFinal;
    EditText mEmailView;
    EditText mPasswordView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBackButton = findViewById(R.id.back_button);
        mSignInButtonFinal = findViewById(R.id.sign_in_button);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackIntent = new Intent(LoginActivity.this, MainActivity.class);
                finish();
                startActivity(goBackIntent);
            }
        });
			
				mEmailView = findViewById(R.id.Username_Email_FillIn);
				mPasswordView = findViewById(R.id.Password_FillIn);

				mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
								if (id == R.integer.login || id == EditorInfo.IME_NULL) {
										attemptLogin();
										return true;
								}
								return false;
						}
				});

						// TODO: Grab an instance of FirebaseAuth
						mAuth = FirebaseAuth.getInstance();
				}

				// Executed when Sign in button pressed
				public void signInExistingUser(View v) {
						// TODO: Call attemptLogin() here
						attemptLogin();
				}


				// TODO: Complete the attemptLogin() method
				private void attemptLogin() {

				String email = mEmailView.getText().toString();
				String password = mPasswordView.getText().toString();

				if (email.equals("") || password.equals("")) return;

				Toast.makeText(LoginActivity.this, "Login in progress...", Toast.LENGTH_SHORT).show();

				// TODO: Use FirebaseAuth to sign in with email & password
				mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
						@Override
						public void onComplete(@NonNull Task<AuthResult> task) {
								Log.d("StatMaster", "signInWithEmail() onComplete: " + task.isSuccessful());
								SaveSharedPreference.setEmailAndPassWord(LoginActivity.this, mEmailView.getText().toString(), mPasswordView.getText().toString());
								if (!task.isSuccessful()) {
										Log.d("StatMaster", "Problem signing in: " + task.getException());
										showErrorDialog("There was a problem signing in...");
										Toast.makeText(LoginActivity.this, "Make sure you are logging in with your EMAIL not username!", Toast.LENGTH_SHORT).show();
								} else {

										Intent intent = new Intent(LoginActivity.this, UserHomeScreen.class);
										finish();
										startActivity(intent);
								}
						}
				});


		}

		// TODO: Show error on screen with an alert dialog
		private void showErrorDialog(String message) {
				new AlertDialog.Builder(LoginActivity.this)
								.setTitle("Oops")
								.setMessage(message)
								.setPositiveButton(android.R.string.ok, null)
								.setIcon(android.R.drawable.ic_dialog_alert)
								.show();
		}
	}


