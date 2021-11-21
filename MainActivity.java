package com.developer.jacob.statmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mSignInButton;
    Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignInButton = findViewById(R.id.home_sign_in_button);
        mRegisterButton = findViewById(R.id.register_button);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(signInIntent);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterIntent = new Intent(MainActivity.this, RegisterActivity.class);
                finish();
                startActivity(RegisterIntent);
            }
        });

    }
}
