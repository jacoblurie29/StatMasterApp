package com.developer.jacob.statmaster;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ViewSeasonStats extends AppCompatActivity {

    private TextView Title;

    private Button BackButton;

    private TextView TeamLeft1;
    private TextView TeamLeft2;
    private TextView TeamLeft3;
    private TextView TeamLeft4;
    private TextView TeamLeft5;
    private TextView TeamLeft6;
    private TextView TeamRight1;
    private TextView TeamRight2;
    private TextView TeamRight3;
    private TextView TeamRight4;
    private TextView TeamRight5;
    private TextView TeamRight6;

    private TextView TotalLeftLeft1;
    private TextView TotalLeftLeft2;
    private TextView TotalLeftLeft3;
    private TextView TotalLeftLeft4;
    private TextView TotalLeftLeft5;
    private TextView TotalLeftLeft6;
    private TextView TotalLeftLeft7;
    private TextView TotalLeftLeft8;
    private TextView TotalLeftRight1;
    private TextView TotalLeftRight2;
    private TextView TotalLeftRight3;
    private TextView TotalLeftRight4;
    private TextView TotalLeftRight5;
    private TextView TotalLeftRight6;
    private TextView TotalLeftRight7;
    private TextView TotalLeftRight8;
    private TextView TotalRightLeft1;
    private TextView TotalRightLeft2;
    private TextView TotalRightLeft3;
    private TextView TotalRightLeft4;
    private TextView TotalRightLeft5;
    private TextView TotalRightLeft6;
    private TextView TotalRightLeft7;
    private TextView TotalRightLeft8;
    private TextView TotalRightRight1;
    private TextView TotalRightRight2;
    private TextView TotalRightRight3;
    private TextView TotalRightRight4;
    private TextView TotalRightRight5;
    private TextView TotalRightRight6;
    private TextView TotalRightRight7;
    private TextView TotalRightRight8;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_battingTotals:
                    updateLayout(2);
                    return true;
                case R.id.navigation_battingStats:
                    updateLayout(3);
                    return true;
                case R.id.navigation_pitchingTotals:
                    updateLayout(4);
                    return true;
                case R.id.navigation_pitchingStats:
                    updateLayout(5);
                    return true;
                case R.id.navigation_teamStats:
                    updateLayout(1);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_season_stats);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        updateLayout(1);

        BackButton = findViewById(R.id.backButtonViewStats);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackHomeIntent = new Intent(ViewSeasonStats.this, UserHomeScreen.class);
                finish();
                startActivity(goBackHomeIntent);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void updateLayout(int pos) {

        FirebaseUser currentUser = mAuth.getCurrentUser();

        Title = findViewById(R.id.TitleTextView);
        Title.setVisibility(View.VISIBLE);

        BackButton = findViewById(R.id.backButtonViewStats);
        BackButton.setVisibility(View.VISIBLE);

        TeamLeft1 = findViewById(R.id.TeamLeftOneTextView);
        TeamLeft2 = findViewById(R.id.TeamLeftTwoTextView);
        TeamLeft3 = findViewById(R.id.TeamLeftThreeTextView);
        TeamLeft4 = findViewById(R.id.TeamLeftFourTextView);
        TeamLeft5 = findViewById(R.id.TeamLeftTextViewFive);
        TeamLeft6 = findViewById(R.id.TeamLeftTextViewSix);

        TeamRight1 = findViewById(R.id.TeamRightTextViewOne);
        TeamRight2 = findViewById(R.id.TeamRightTextViewTwo);
        TeamRight3 = findViewById(R.id.TeamRightTextViewThree);
        TeamRight4 = findViewById(R.id.TeamRightTextViewFour);
        TeamRight5 = findViewById(R.id.TeamRightTextViewFive);
        TeamRight6 = findViewById(R.id.TeamRightTextViewSix);



        String[] titles = {"Team Totals", "Batting Totals", "Batting Stats", "Pitching Totals", "Pitching Stats"};

        String[] teamTextViewTitlesArray = {"Games Played","Wins", "Ties","Losses","Runs For", "Runs Against"};

        String[] battingTotalsArray = {"Innings Batted", "Plate Appearances", "Total Hits", "Stolen Bases",
                                        "Singles", "Doubles", "Triples", "Home runs",
                                        "Walks", "RBIs", "HBPs", "Runs Scored",
                                        "Sacs", "Strikeouts", "Catcher Interference", "Reached On Error"};

        TeamLeft1.setText(teamTextViewTitlesArray[0]);
        TeamLeft2.setText(teamTextViewTitlesArray[1]);
        TeamLeft3.setText(teamTextViewTitlesArray[2]);
        TeamLeft4.setText(teamTextViewTitlesArray[3]);
        TeamLeft5.setText(teamTextViewTitlesArray[4]);
        TeamLeft6.setText(teamTextViewTitlesArray[5]);



        TotalLeftLeft1 = findViewById(R.id.battingTotalsLeftLeftOne);
        TotalLeftLeft2 = findViewById(R.id.battingTotalsLeftLeftTwo);
        TotalLeftLeft3 = findViewById(R.id.battingTotalsLeftLeftThree);
        TotalLeftLeft4 = findViewById(R.id.battingTotalsLeftLeftFour);
        TotalLeftLeft5 = findViewById(R.id.battingTotalsLeftLeftFive);
        TotalLeftLeft6 = findViewById(R.id.battingTotalsLeftLeftSix);
        TotalLeftLeft7 = findViewById(R.id.battingTotalsLeftLeftSeven);
        TotalLeftLeft8 = findViewById(R.id.battingTotalsLeftLeftEight);
        TotalLeftRight1 = findViewById(R.id.battingTotalsLeftRightOne);
        TotalLeftRight2 = findViewById(R.id.battingTotalsLeftRightTwo);
        TotalLeftRight3 = findViewById(R.id.battingTotalsLeftRightThree);
        TotalLeftRight4 = findViewById(R.id.battingTotalsLeftRightFour);
        TotalLeftRight5 = findViewById(R.id.battingTotalsLeftRightFive);
        TotalLeftRight6 = findViewById(R.id.battingTotalsLeftRightSix);
        TotalLeftRight7 = findViewById(R.id.battingTotalsLeftRightSeven);
        TotalLeftRight8 = findViewById(R.id.battingTotalsLeftRightEight);
        TotalRightLeft1 = findViewById(R.id.battingTotalsRightLeftOne);
        TotalRightLeft2 = findViewById(R.id.battingTotalsRightLeftTwo);
        TotalRightLeft3 = findViewById(R.id.battingTotalsRightLeftThree);
        TotalRightLeft4 = findViewById(R.id.battingTotalsRightLeftFour);
        TotalRightLeft5 = findViewById(R.id.battingTotalsRightLeftFive);
        TotalRightLeft6 = findViewById(R.id.battingTotalsRightLeftSix);
        TotalRightLeft7 = findViewById(R.id.battingTotalsRightLeftSeven);
        TotalRightLeft8 = findViewById(R.id.battingTotalsRightLeftEight);
        TotalRightRight1 = findViewById(R.id.battingTotalsRightRightOne);
        TotalRightRight2 = findViewById(R.id.battingTotalsRightRightTwo);
        TotalRightRight3 = findViewById(R.id.battingTotalsRightRightThree);
        TotalRightRight4 = findViewById(R.id.battingTotalsRightRightFour);
        TotalRightRight5 = findViewById(R.id.battingTotalsRightRightFive);
        TotalRightRight6 = findViewById(R.id.battingTotalsRightRightSix);
        TotalRightRight7 = findViewById(R.id.battingTotalsRightRightSeven);
        TotalRightRight8 = findViewById(R.id.battingTotalsRightRightEight);


        TotalLeftLeft1.setText(battingTotalsArray[0]);
        TotalLeftLeft2.setText(battingTotalsArray[1]);
        TotalLeftLeft3.setText(battingTotalsArray[2]);
        TotalLeftLeft4.setText(battingTotalsArray[3]);
        TotalLeftLeft5.setText(battingTotalsArray[4]);
        TotalLeftLeft6.setText(battingTotalsArray[5]);
        TotalLeftLeft7.setText(battingTotalsArray[6]);
        TotalLeftLeft8.setText(battingTotalsArray[7]);

        TotalRightLeft1.setText(battingTotalsArray[8]);
        TotalRightLeft2.setText(battingTotalsArray[9]);
        TotalRightLeft3.setText(battingTotalsArray[10]);
        TotalRightLeft4.setText(battingTotalsArray[11]);
        TotalRightLeft5.setText(battingTotalsArray[12]);
        TotalRightLeft6.setText(battingTotalsArray[13]);
        TotalRightLeft7.setText(battingTotalsArray[14]);
        TotalRightLeft8.setText(battingTotalsArray[15]);



        if (pos == 1) {
            TeamLeft1.setVisibility(View.VISIBLE);
            TeamLeft2.setVisibility(View.VISIBLE);
            TeamLeft3.setVisibility(View.VISIBLE);
            TeamLeft4.setVisibility(View.VISIBLE);
            TeamLeft5.setVisibility(View.VISIBLE);
            TeamLeft6.setVisibility(View.VISIBLE);

            TeamRight1.setVisibility(View.VISIBLE);
            TeamRight2.setVisibility(View.VISIBLE);
            TeamRight3.setVisibility(View.VISIBLE);
            TeamRight4.setVisibility(View.VISIBLE);
            TeamRight5.setVisibility(View.VISIBLE);
            TeamRight6.setVisibility(View.VISIBLE);

            TotalLeftLeft1.setVisibility(View.INVISIBLE);
            TotalLeftLeft2.setVisibility(View.INVISIBLE);
            TotalLeftLeft3.setVisibility(View.INVISIBLE);
            TotalLeftLeft4.setVisibility(View.INVISIBLE);
            TotalLeftLeft5.setVisibility(View.INVISIBLE);
            TotalLeftLeft6.setVisibility(View.INVISIBLE);
            TotalLeftLeft7.setVisibility(View.INVISIBLE);
            TotalLeftLeft8.setVisibility(View.INVISIBLE);
            TotalLeftRight1.setVisibility(View.INVISIBLE);
            TotalLeftRight2.setVisibility(View.INVISIBLE);
            TotalLeftRight3.setVisibility(View.INVISIBLE);
            TotalLeftRight4.setVisibility(View.INVISIBLE);
            TotalLeftRight5.setVisibility(View.INVISIBLE);
            TotalLeftRight6.setVisibility(View.INVISIBLE);
            TotalLeftRight7.setVisibility(View.INVISIBLE);
            TotalLeftRight8.setVisibility(View.INVISIBLE);
            TotalRightLeft1.setVisibility(View.INVISIBLE);
            TotalRightLeft2.setVisibility(View.INVISIBLE);
            TotalRightLeft3.setVisibility(View.INVISIBLE);
            TotalRightLeft4.setVisibility(View.INVISIBLE);
            TotalRightLeft5.setVisibility(View.INVISIBLE);
            TotalRightLeft6.setVisibility(View.INVISIBLE);
            TotalRightLeft7.setVisibility(View.INVISIBLE);
            TotalRightLeft8.setVisibility(View.INVISIBLE);
            TotalRightRight1.setVisibility(View.INVISIBLE);
            TotalRightRight2.setVisibility(View.INVISIBLE);
            TotalRightRight3.setVisibility(View.INVISIBLE);
            TotalRightRight4.setVisibility(View.INVISIBLE);
            TotalRightRight5.setVisibility(View.INVISIBLE);
            TotalRightRight6.setVisibility(View.INVISIBLE);
            TotalRightRight7.setVisibility(View.INVISIBLE);
            TotalRightRight8.setVisibility(View.INVISIBLE);

            TeamLeft1.bringToFront();
            TeamLeft2.bringToFront();
            TeamLeft3.bringToFront();
            TeamLeft4.bringToFront();
            TeamLeft5.bringToFront();
            TeamLeft6.bringToFront();

            Title.setText(titles[0]);

            mDatabaseReference.child("users").child(currentUser.getUid()).child("Seasons").child(SaveSharedPreference.getSeasonnumforviewgame(ViewSeasonStats.this)).child("Games").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TeamRight1.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                    int wins = 0, losses = 0, ties = 0;
                    int runsFor = 0, runsAgainst = 0;
                    for(DataSnapshot dsp : dataSnapshot.getChildren()) {

                        if (Integer.valueOf((String) dsp.child("User team score").getValue()) > Integer.valueOf((String) dsp.child("Opponent Score").getValue()))
                        {
                            wins++;
                        } else if(Integer.valueOf((String) dsp.child("User team score").getValue()) < Integer.valueOf((String) dsp.child("Opponent Score").getValue())) {
                            losses++;
                        } else {
                            ties++;
                        }

                        runsFor += Integer.valueOf((String) dsp.child("User team score").getValue());
                        runsAgainst += Integer.valueOf((String) dsp.child("Opponent Score").getValue());
                    }
                    TeamRight2.setText("" + wins);
                    TeamRight3.setText("" + ties);
                    TeamRight4.setText("" + losses);

                    TeamRight5.setText("" + runsFor);
                    TeamRight6.setText("" + runsAgainst);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else if(pos == 2) {

            Title.setText(titles[1]);

            TeamLeft1.setVisibility(View.INVISIBLE);
            TeamLeft2.setVisibility(View.INVISIBLE);
            TeamLeft3.setVisibility(View.INVISIBLE);
            TeamLeft4.setVisibility(View.INVISIBLE);
            TeamLeft5.setVisibility(View.INVISIBLE);
            TeamLeft6.setVisibility(View.INVISIBLE);

            TeamRight1.setVisibility(View.INVISIBLE);
            TeamRight2.setVisibility(View.INVISIBLE);
            TeamRight3.setVisibility(View.INVISIBLE);
            TeamRight4.setVisibility(View.INVISIBLE);
            TeamRight5.setVisibility(View.INVISIBLE);
            TeamRight6.setVisibility(View.INVISIBLE);

            TotalLeftLeft1.setVisibility(View.VISIBLE);
            TotalLeftLeft2.setVisibility(View.VISIBLE);
            TotalLeftLeft3.setVisibility(View.VISIBLE);
            TotalLeftLeft4.setVisibility(View.VISIBLE);
            TotalLeftLeft5.setVisibility(View.VISIBLE);
            TotalLeftLeft6.setVisibility(View.VISIBLE);
            TotalLeftLeft7.setVisibility(View.VISIBLE);
            TotalLeftLeft8.setVisibility(View.VISIBLE);
            TotalLeftRight1.setVisibility(View.VISIBLE);
            TotalLeftRight2.setVisibility(View.VISIBLE);
            TotalLeftRight3.setVisibility(View.VISIBLE);
            TotalLeftRight4.setVisibility(View.VISIBLE);
            TotalLeftRight5.setVisibility(View.VISIBLE);
            TotalLeftRight6.setVisibility(View.VISIBLE);
            TotalLeftRight7.setVisibility(View.VISIBLE);
            TotalLeftRight8.setVisibility(View.VISIBLE);
            TotalRightLeft1.setVisibility(View.VISIBLE);
            TotalRightLeft2.setVisibility(View.VISIBLE);
            TotalRightLeft3.setVisibility(View.VISIBLE);
            TotalRightLeft4.setVisibility(View.VISIBLE);
            TotalRightLeft5.setVisibility(View.VISIBLE);
            TotalRightLeft6.setVisibility(View.VISIBLE);
            TotalRightLeft7.setVisibility(View.VISIBLE);
            TotalRightLeft8.setVisibility(View.VISIBLE);
            TotalRightRight1.setVisibility(View.VISIBLE);
            TotalRightRight2.setVisibility(View.VISIBLE);
            TotalRightRight3.setVisibility(View.VISIBLE);
            TotalRightRight4.setVisibility(View.VISIBLE);
            TotalRightRight5.setVisibility(View.VISIBLE);
            TotalRightRight6.setVisibility(View.VISIBLE);
            TotalRightRight7.setVisibility(View.VISIBLE);
            TotalRightRight8.setVisibility(View.VISIBLE);

            TotalRightLeft1.bringToFront();
            TotalRightLeft2.bringToFront();
            TotalRightLeft3.bringToFront();
            TotalRightLeft4.bringToFront();
            TotalRightLeft5.bringToFront();
            TotalRightLeft6.bringToFront();
            TotalRightLeft7.bringToFront();
            TotalRightLeft8.bringToFront();

            TotalLeftLeft1.bringToFront();
            TotalLeftLeft2.bringToFront();
            TotalLeftLeft3.bringToFront();
            TotalLeftLeft4.bringToFront();
            TotalLeftLeft5.bringToFront();
            TotalLeftLeft6.bringToFront();
            TotalLeftLeft7.bringToFront();
            TotalLeftLeft8.bringToFront();

            mDatabaseReference.child("users").child(currentUser.getUid()).child("Seasons").child(SaveSharedPreference.getSeasonnumforviewgame(ViewSeasonStats.this)).child("Games").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int innings = 0, outs = 0, pa = 0, th = 0, s = 0, d = 0, t = 0, hr = 0, w =0, HBP = 0, RBI = 0, runs = 0, sacs = 0, so = 0, sb = 0, ci = 0, roe = 0;
                    for(int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                        int inningsPlayed = Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Innings Played").getValue().toString());
                        int outsPlayed = Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Outs Played").getValue().toString());
                        int outsLeft = outsPlayed % 3;
                        inningsPlayed += outsPlayed / 3;
                        innings += inningsPlayed;
                        outs += outsLeft;
                        th += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Singles").getValue().toString()) +
                                Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Doubles").getValue().toString()) +
                                Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Triples").getValue().toString()) +
                                Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Batting Homeruns").getValue().toString());
                        pa += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Plate Appearances").getValue().toString());
                        sb += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("SB True").getValue().toString());
                        s += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Singles").getValue().toString());
                        d += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Doubles").getValue().toString());
                        t += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Triples").getValue().toString());
                        hr += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Batting Homeruns").getValue().toString());
                        w += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Batting Walks").getValue().toString());
                        HBP += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Batting HBP").getValue().toString());
                        RBI += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("RBI").getValue().toString());
                        runs += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Batting Runs Scored").getValue().toString());
                        sacs += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Sac").getValue().toString());
                        so += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Batting Strikeout").getValue().toString());
                        ci += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Catcher Interference").getValue().toString());
                        so += Integer.valueOf(dataSnapshot.child("game" + (i + 1)).child("Error").getValue().toString());
                    }
                    innings += outs / 3;
                    outs = outs % 3;

                      TotalLeftRight1.setText(innings + "." + outs);
                      TotalLeftRight2.setText("" + pa);
                      TotalLeftRight3.setText("" + th);
                      TotalLeftRight4.setText("" + sb);
                      TotalLeftRight5.setText("" + s);
                      TotalLeftRight6.setText("" + d);
                      TotalLeftRight7.setText("" + t);
                      TotalLeftRight8.setText("" + hr);

                      TotalRightRight1.setText("" + w);
                      TotalRightRight2.setText("" + RBI);
                      TotalRightRight3.setText("" + HBP);
                      TotalRightRight4.setText("" + runs);
                      TotalRightRight5.setText("" + sacs);
                      TotalRightRight6.setText("" + so);
//                    TotalRightRight7.setText();
//                    TotalRightRight8.setText();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

}
