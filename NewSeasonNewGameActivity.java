package com.developer.jacob.statmaster;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewSeasonNewGameActivity extends AppCompatActivity {

    private Button mNewGameGoButton;
    private Button mNewSeasonGoButton;
    private Spinner mNewGameSeasonSpinner;
    private DatabaseReference mDatabaseReference;
    private Button mBackButton;
    private int numberOfSeasons;
    private String seasonForNewGame;
    private FirebaseAuth mAuth;
    private ArrayList<String> seasonsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_season_new_game);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mNewGameGoButton = findViewById(R.id.new_game_go_button);
        mNewSeasonGoButton = findViewById(R.id.new_season_go_button);
        mNewGameSeasonSpinner = findViewById(R.id.new_game_season_selector_spinner);
        mBackButton = findViewById(R.id.back_button);
        mAuth = FirebaseAuth.getInstance();
        seasonsArray = new ArrayList<>();

        seasonsArray.add("--- Select Season ---");

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        mNewGameSeasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                seasonForNewGame = parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        FirebaseUser currentUser = mAuth.getCurrentUser();
        mDatabaseReference.child("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot dsp : dataSnapshot.child("Seasons").getChildren())
                {
                    addToSeasonsArray(dsp.child("TeamName").getValue().toString() + ", " + dsp.child("LeagueName").getValue().toString() + " - " + dsp.child("Season").getValue().toString() + ", " + dsp.child("Year").getValue().toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackToHomeIntent = new Intent(NewSeasonNewGameActivity.this, UserHomeScreen.class);
                finish();
                startActivity(goBackToHomeIntent);
            }
        });

        mNewGameGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seasonForNewGame.equals("--- Select Season ---"))
                {
                    showErrorDialog("Please select or create a season.");
                } else {
                    SaveSharedPreference.setPrefSeasonforgame(NewSeasonNewGameActivity.this, seasonForNewGame);
                    Intent newGameIntent = new Intent(NewSeasonNewGameActivity.this, NewGameActivity.class);
                    finish();
                    startActivity(newGameIntent);
                }
            }
        });

        mNewSeasonGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newSeasonIntent = new Intent(NewSeasonNewGameActivity.this, NewSeason.class);
                finish();
                startActivity(newSeasonIntent);
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, seasonsArray);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        mNewGameSeasonSpinner.setAdapter(adapter);
        mNewGameSeasonSpinner.setSelection(0);

    }

    private void addToSeasonsArray(String s) {
        seasonsArray.add(s);
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(NewSeasonNewGameActivity.this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
