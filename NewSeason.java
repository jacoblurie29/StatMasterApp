package com.developer.jacob.statmaster;

import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewSeason extends AppCompatActivity {

    private EditText mTeamNameET;
    private EditText mLeagueNameET;
    private EditText mSeasonET;
    private EditText mYearET;
    private Button mSubmitButton;
    private Button mBackButton;
    private DatabaseReference mDatabaseReference;
    private String numSeasons;
    private Map<String, Object> temp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_season);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference();

        mTeamNameET = findViewById(R.id.team_name_edittext);
        mTeamNameET.setHintTextColor(getResources().getColor(R.color.colorIcons));

        mLeagueNameET = findViewById(R.id.league_name_edittext);
        mLeagueNameET.setHintTextColor(getResources().getColor(R.color.colorIcons));

        mSeasonET = findViewById(R.id.editTextSeason);
        mSeasonET.setHintTextColor(getResources().getColor(R.color.colorIcons));

        mYearET = findViewById(R.id.editTextYear);
        mYearET.setHintTextColor(getResources().getColor(R.color.colorIcons));

        mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackIntent = new Intent(NewSeason.this, UserHomeScreen.class);
                finish();
                startActivity(goBackIntent);
            }
        });


        mSubmitButton = findViewById(R.id.new_season_submit_button);

        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabaseReference.child("users").child(currentFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        numSeasons = (String) dataSnapshot.child("SeasonNumber").getValue();

                        Map<String, Object> newSeason = new HashMap<>();
                        newSeason.put("TeamName", mTeamNameET.getText().toString());
                        newSeason.put("LeagueName", mLeagueNameET.getText().toString());
                        newSeason.put("Season", mSeasonET.getText().toString());
                        newSeason.put("Year", mYearET.getText().toString());
                        newSeason.put("gameNumber", "0");
                        Map<String, Object> temp = new HashMap<>();
                        temp.put("SeasonNumber", String.valueOf(Integer.parseInt(numSeasons)+ 1));
                        mDatabaseReference.child("users").child(currentFirebaseUser.getUid()).updateChildren(temp);
                        numSeasons = String.valueOf(Integer.parseInt((String) dataSnapshot.child("SeasonNumber").getValue()) + 1);
                        mDatabaseReference.child("users").child(currentFirebaseUser.getUid()).child("Seasons").child("Season" + numSeasons).updateChildren(newSeason);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





                Intent doneNewSeasonIntent = new Intent(NewSeason.this, UserHomeScreen.class);
                finish();
                startActivity(doneNewSeasonIntent);
            }
        });
    }
}
