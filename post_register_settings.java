package com.developer.jacob.statmaster;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class post_register_settings extends AppCompatActivity {

    private String pPosition;
    private String sPosition;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private Boolean showDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_register_settings);



        mAuth = FirebaseAuth.getInstance();

        Spinner pSpinner = findViewById(R.id.post_register_primary_spinner);
        ArrayAdapter<CharSequence> pAdapter = ArrayAdapter.createFromResource(this,
                R.array.primary_positions_array, R.layout.spinner_item);
        pAdapter.setDropDownViewResource(R.layout.spinner_item);
        pSpinner.setAdapter(pAdapter);
        pSpinner.setSelection(0);


        Spinner sSpinner = findViewById(R.id.post_register_secondary_spinner);
        ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this,
                R.array.secondary_positions_array, R.layout.spinner_item);
        sAdapter.setDropDownViewResource(R.layout.spinner_item);
        sSpinner.setAdapter(sAdapter);
        sSpinner.setSelection(0);

        pSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                pPosition = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                sPosition = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }

    public void OnClickContinue(View v) {


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        ArrayAdapter<CharSequence> pAdapter = ArrayAdapter.createFromResource(post_register_settings.this,
                R.array.primary_positions_array, R.layout.spinner_item);
        ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(post_register_settings.this,
                R.array.secondary_positions_array, R.layout.spinner_item);

        setShowDialog(true);




        mAuth.createUserWithEmailAndPassword(SaveSharedPreference.getPrefRegisterEmail(post_register_settings.this), SaveSharedPreference.getPrefRegisterPassword(post_register_settings.this)).addOnCompleteListener(post_register_settings.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("StatMaster", "createUser onComplete: " + task.isSuccessful());

                if(!task.isComplete()) {
                    Log.d("StatMaster", "User creation failed");
                    showErrorDialog("User creation failed!");


                } else {
                    Log.d("StatMaster", "IT WORKED YAS");

                }

            }
        });

        mAuth.signInWithEmailAndPassword(SaveSharedPreference.getPrefRegisterEmail(post_register_settings.this), SaveSharedPreference.getPrefRegisterPassword(post_register_settings.this)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete())
                {
                    Log.d("StatMaster", "IT WORKED");


                    FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();


                    Log.d("StatMaster", "UID: " + currentFirebaseUser.getUid());

                    String currentUser = currentFirebaseUser.getUid();

                    Map<String, Object> newUser = new HashMap<>();
                    newUser.put("Username", SaveSharedPreference.getPrefUsername(post_register_settings.this));
                    newUser.put("Email", SaveSharedPreference.getEmail(post_register_settings.this));
                    newUser.put("Name", SaveSharedPreference.getPrefName(post_register_settings.this));
                    newUser.put("Password", SaveSharedPreference.getPassword(post_register_settings.this));
                    newUser.put("PrimaryPosition", pPosition);
                    newUser.put("SecondaryPosition", sPosition);
                    newUser.put("SeasonNumber", "0");

                    mDatabaseReference.child("users").child(currentUser).updateChildren(newUser);

                    Map<String, Object> newUserEmail = new HashMap<>();
                    newUserEmail.put("Email for: " + SaveSharedPreference.getPrefUsername(post_register_settings.this) + ((int) (Math.random() * 9999999)), SaveSharedPreference.getEmail(post_register_settings.this));
                    mDatabaseReference.child("users").child("EmailList").updateChildren(newUserEmail);



                    Intent done_settings = new Intent(post_register_settings.this, LoginActivity.class);
                    Toast.makeText(post_register_settings.this, "Register Complete! Login to continue", Toast.LENGTH_LONG).show();


                    mAuth.signOut();
                    finish();
                    startActivity(done_settings);
                } else {
                    Log.d("StatMaster", "NOPE");

                }
            }
        });





    }

    private void setShowDialog(boolean b)
    {
        showDialog = b;
    }

    private void showErrorDialog(String message) {
        if(showDialog) {
            AlertDialog oops = new AlertDialog.Builder(this)
                    .setTitle("Oops")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            Toast.makeText(this, "Click on the red exclamation point to see what's wrong!", Toast.LENGTH_SHORT).show();
        }

    }



}
