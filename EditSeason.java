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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class EditSeason extends AppCompatActivity {

    private EditText mTeamNameET;
    private EditText mLeagueNameET;
    private EditText mSeasonET;
    private EditText mYearET;
    private Button mSubmitButton;
    private Button mBackButton;
    private DatabaseReference mDatabaseReference;
    private TextView mTitleView;




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

        mTitleView = findViewById(R.id.new_season_textview);
        mTitleView.setText("Edit Season");


        mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackIntent = new Intent(EditSeason.this, ViewGame.class);
                finish();
                startActivity(goBackIntent);
            }
        });
        UpdateEditTexts();

        mSubmitButton = findViewById(R.id.new_season_submit_button);

        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabaseReference.child("users").child(currentFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {



                        Map<String, Object> newSeason = new HashMap<>();
                        newSeason.put("TeamName", mTeamNameET.getText().toString());
                        newSeason.put("LeagueName", mLeagueNameET.getText().toString());
                        newSeason.put("Season", mSeasonET.getText().toString());
                        newSeason.put("Year", mYearET.getText().toString());
                        newSeason.put("gameNumber", "0");
                        Map<String, Object> temp = new HashMap<>();
                        mDatabaseReference.child("users").child(currentFirebaseUser.getUid()).updateChildren(temp);
                        mDatabaseReference.child("users").child(currentFirebaseUser.getUid()).child("Seasons").child("Season" + SaveSharedPreference.getSeasonnumforviewgame(EditSeason.this)).updateChildren(newSeason);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





                Intent doneNewSeasonIntent = new Intent(EditSeason.this, UserHomeScreen.class);
                finish();
                startActivity(doneNewSeasonIntent);
            }
        });
    }

    private void UpdateEditTexts() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference.child("users").child(currentFirebaseUser.getUid()).child("Seasons").child("Season" + SaveSharedPreference.getSeasonnumforviewgame(EditSeason.this)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTeamNameET.setText(dataSnapshot.child("TeamName").getValue().toString());
                mSeasonET.setText(dataSnapshot.child("Season").getValue().toString());
                mLeagueNameET.setText(dataSnapshot.child("LeagueName").getValue().toString());
                mYearET.setText(dataSnapshot.child("Year").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}