package com.developer.jacob.statmaster;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {




    // fields
    private EditText mNameView;
    private EditText mEmailView;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private Button mBackButton;
    public boolean showDialog;
    private DatabaseReference mDatabaseReference;
    private ArrayList<String> usedEmailsList;
    private boolean finalCancel;
    private View focusView;
    private String email;






        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            showDialog = true;

            // define fields
            mNameView = findViewById(R.id.new_user_name);
            mEmailView = findViewById(R.id.new_user_email);
            mUsernameView = findViewById(R.id.new_user_username);
            mPasswordView = findViewById(R.id.new_user_password);
            mConfirmPasswordView = findViewById(R.id.new_user_confirm_password);
            mBackButton = findViewById(R.id.new_user_back_button);
            usedEmailsList = new ArrayList<>();


            // Keyboard sign in action
            mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.integer.register_form_finished || id == EditorInfo.IME_NULL) {
                        attemptRegistration();
                        return true;
                    }
                    return false;
                }
            });



            // Go back to home screen when back button is clicked
            mBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent BackFromRegisterIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    finish();
                    startActivity(BackFromRegisterIntent);
                }
            });
        }

        public void signUp(View v) {
            attemptRegistration();
        }

        private void attemptRegistration() {

            // Reset errors displayed in the form.
            mEmailView.setError(null);
            mPasswordView.setError(null);
            setFocusView(null);
            setEmail(mEmailView.getText().toString());

            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();

            mDatabaseReference = FirebaseDatabase.getInstance().getReference();

            setFinalCancel(false);

            boolean cancel = false;
            focusView = null;
            mDatabaseReference.child("users").child("EmailList").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren())
                    {
                        usedEmailsList.add((String) dsp.getValue());
                        Log.d("StatMaster", "VALUE: " + dsp.getValue());

                        for(String s : usedEmailsList)
                        {
                            if(s.equals(getEmail()))
                            {
                                setFinalCancel(true);
                                setFocusView(getEmailView());
                                setDuplicateEmailError("This email already is registered to an account!");
                            }

                        }
                        if(!(isEmailValid(getEmail())))
                        {
                            setDuplicateEmailError(getString(R.string.error_invalid_email));
                            setFocusView(getEmailView());
                            setFinalCancel(true);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            if(!(isEmailValid(getEmail())))
            {
                setDuplicateEmailError(getString(R.string.error_invalid_email));
                setFocusView(getEmailView());
                setFinalCancel(true);
            }

            // Check for a valid password, if the user entered one.
            if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            if(!(mPasswordView.getText().toString().equals(mConfirmPasswordView.getText().toString())))
            {
                mPasswordView.setError("These passwords do not match!");
                setFocusView(mPasswordView);
                cancel = true;

            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            }


            // Call create FirebaseUser() here
            if (cancel || finalCancel) {
                focusView.requestFocus();
                showErrorDialog();

            } else {
                mEmailView.setError(null);
                mPasswordView.setError(null);
                setDuplicateEmailError(null);
                focusView = null;
                createFirebaseUser();

            }

        }

        private boolean isEmailValid(String email) {
            return email.contains("@");
        }

        private boolean isPasswordValid(String password) {
            String confirmPassword = mConfirmPasswordView.getText().toString();
            return confirmPassword.equals(password) && password.length() > 4;
        }

        private void createFirebaseUser() {
            final String email = mEmailView.getText().toString();
            final String password = mPasswordView.getText().toString();

            SaveSharedPreference.setPrefRegisterEmailAndPassword(RegisterActivity.this, email, password);
            SaveSharedPreference.setEmailAndPassWord(RegisterActivity.this, mEmailView.getText().toString(), mPasswordView.getText().toString());
            SaveSharedPreference.setUserNameAndName(RegisterActivity.this, mUsernameView.getText().toString(), mNameView.getText().toString());

            Intent postRegisterSettings = new Intent(RegisterActivity.this, post_register_settings.class);
            finish();
            startActivity(postRegisterSettings);

        }

        private void setFinalCancel(boolean b) {
        finalCancel = b;
        }

        private void setFocusView(View v)
        {
            focusView  = v;
        }

        private void setDuplicateEmailError(String s) {
            mEmailView.setError(s);
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String tEmail) {
            email = tEmail;
        }

        public EditText getEmailView() {
            return mEmailView;
    }

        private void showErrorDialog() {
            Toast.makeText(this, "Click on the red exclamation point to see what's wrong!", Toast.LENGTH_SHORT).show();
    }
}

