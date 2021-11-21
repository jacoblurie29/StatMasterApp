package com.developer.jacob.statmaster;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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

import java.util.ArrayList;

public class UserHomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private ArrayList<String> seasonsArray;
    private Spinner spinner;
    private Button mGoButton;
    private int seasonNum;
    private int numGames;
    private Button mViewSeasonStatsButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        seasonNum = 0;
        numGames = 0;
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference =  FirebaseDatabase.getInstance().getReference();
        seasonsArray = new ArrayList<>();
        setContentView(R.layout.content_user_home_screen);

        setContentView(R.layout.activity_user_home_screen);

        Toast.makeText(UserHomeScreen.this, "Loading....", Toast.LENGTH_SHORT).show();

        if(SaveSharedPreference.getEmail(UserHomeScreen.this) == null || SaveSharedPreference.getPassword(UserHomeScreen.this) == null || SaveSharedPreference.getEmail(UserHomeScreen.this).length() == 0 || SaveSharedPreference.getPassword(UserHomeScreen.this).length() == 0)
        {
            Intent noUserLoggedInIntent = new Intent(UserHomeScreen.this, MainActivity.class);
            finish();
            startActivity(noUserLoggedInIntent);
        }
        else {
            mAuth.signInWithEmailAndPassword(SaveSharedPreference.getEmail(UserHomeScreen.this), SaveSharedPreference.getPassword(UserHomeScreen.this)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("StatMaster", "signInWithEmail() onComplete: " + task.isSuccessful());

                    if (!task.isSuccessful()) {
                        Log.d("StatMaster", "Problem signing in: " + task.getException());
                        showErrorDialog("There was a problem signing in...");
                        Intent failedReLoginIntent = new Intent(UserHomeScreen.this, MainActivity.class);
                        SaveSharedPreference.clearSharedPreferences(UserHomeScreen.this);
                        finish();
                        startActivity(failedReLoginIntent);
                    } else {
                        Log.d("StatMaster", "User: " + SaveSharedPreference.getEmail(UserHomeScreen.this) + " has been successfully signed in." + "  Their password is: " + SaveSharedPreference.getPassword(UserHomeScreen.this));

                        setSidebarText();
                    }
                }
            });






            seasonsArray.add("--- Select Season ---");

            setContentView(R.layout.activity_user_home_screen);

            mViewSeasonStatsButton = findViewById(R.id.viewSeasonStats);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent NewSeasonNewGameIntent = new Intent(UserHomeScreen.this, NewSeasonNewGameActivity.class);
                    finish();
                    startActivity(NewSeasonNewGameIntent);
                }
            });

            mViewSeasonStatsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getSeasonNum() == 0) {
                        Toast.makeText(UserHomeScreen.this, "Please select a season!", Toast.LENGTH_SHORT).show();
                    } else {

                        SaveSharedPreference.setSeasonnumforviewgame(UserHomeScreen.this, "Season" + getSeasonNum());
                        Intent viewSeasonStatsIntent = new Intent(UserHomeScreen.this, ViewSeasonStats.class);
                        finish();
                        startActivity(viewSeasonStatsIntent);
                    }
                }
            });

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();


            final FirebaseUser currentUser = mAuth.getCurrentUser();
            mDatabaseReference.child("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot dsp : dataSnapshot.child("Seasons").getChildren()) {
                        addToSeasonsArray(dsp.child("TeamName").getValue().toString() + ", " + dsp.child("LeagueName").getValue().toString() + " - " + dsp.child("Season").getValue().toString() + ", " + dsp.child("Year").getValue().toString());

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            final Spinner spinner = findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserHomeScreen.this, R.layout.spinner_item, seasonsArray);
            adapter.setDropDownViewResource(R.layout.spinner_item);
            spinner.setAdapter(adapter);
            spinner.setSelection(0);

            mGoButton = findViewById(R.id.selectSeasonGo);
            setSpinner(spinner);

            mGoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mDatabaseReference = FirebaseDatabase.getInstance().getReference();


                    if(seasonsArray.get(getSpinner().getSelectedItemPosition()).equals("--- Select Season ---"))
                    {
                        Toast.makeText(UserHomeScreen.this, "Please select a season!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserHomeScreen.this, "Click on a game to view and edit it!", Toast.LENGTH_SHORT).show();
                    }
                    setSeasonNum(getSpinner().getSelectedItemPosition());
                    Log.d("STATMASTER", "SEASON: " + getSeasonNum());
                    SaveSharedPreference.setSeasonnumforviewgame(UserHomeScreen.this, "" + getSeasonNum());

                    mDatabaseReference.child("users").child(currentUser.getUid()).child("Seasons").child("Season" + getSeasonNum()).child("Games").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            setNumGames((int) dataSnapshot.getChildrenCount());
                            Log.d("STATMASTER", "GAMES: " + getNumGames());
                            ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewGames);
                            scrollView.removeAllViews();
                            LinearLayout linearLayout = new LinearLayout(UserHomeScreen.this);

                            for (int i = 0; i < getNumGames(); i++) {
                                // 12/30/2018 5 - 4 W/L vs. Opponent
                                String Day = String.valueOf(dataSnapshot.child("game" + (i + 1)).child("Day").getValue());
                                String Month = String.valueOf(dataSnapshot.child("game" + (i + 1)).child("Month").getValue());
                                String Year = String.valueOf(dataSnapshot.child("game" + (i + 1)).child("Year").getValue());
                                int userScore = Integer.valueOf(String.valueOf(dataSnapshot.child("game" + (i + 1)).child("User team score").getValue()));
                                int oppoScore = Integer.valueOf(String.valueOf(dataSnapshot.child("game" + (i + 1)).child("Opponent Score").getValue()));
                                String WinLossTie = "";
                                if (userScore > oppoScore) {
                                    WinLossTie = "Win";
                                } else if (oppoScore > userScore) {
                                    WinLossTie = "Loss";
                                } else if (userScore == oppoScore) {
                                    WinLossTie = "Tie";
                                }

                                linearLayout.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.topMargin = 35;
                                params.bottomMargin = 35;

                                String Opponent = String.valueOf(dataSnapshot.child("game" + (i + 1)).child("Opponent").getValue());
                                String finalGameInfo = Month + "/" + Day + "/" + Year + ": " + userScore + " - " + oppoScore + " " + WinLossTie + " vs. " + Opponent;
                                Log.d("STATMASTER", "FINALINFO: " + finalGameInfo);

                                Button b = createNewButton(finalGameInfo, i);
                                linearLayout.addView(b, params);

                            }
                            linearLayout.setTag("linLay");
                            scrollView.addView(linearLayout);


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });


        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_seasonCompare) {

        } else if (id == R.id.nav_Settings) {

        } else if (id == R.id.nav_logout_button) {
            SaveSharedPreference.clearSharedPreferences(UserHomeScreen.this);
            Intent logoutIntent = new Intent(UserHomeScreen.this, MainActivity.class);
            finish();
            startActivity(logoutIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(UserHomeScreen.this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void setSidebarText(){

        mDatabaseReference =  FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Log.d("StatMaster", "UID:  " + currentFirebaseUser.getUid());

        mDatabaseReference.child("users").child(currentFirebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String e = (String)dataSnapshot.child("Email").getValue();

                String n = (String)dataSnapshot.child("Name").getValue();

                Log.d("StatMaster", "EMAIL: " + e);

                NavigationView navigationView =  findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(UserHomeScreen.this);
                View header=navigationView.getHeaderView(0);
                TextView emailTextView = header.findViewById(R.id.email_navbar_textview);
                emailTextView.setText(e);
                TextView nameTextView = header.findViewById(R.id.name_navbar_textview);
                nameTextView.setText(n);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addToSeasonsArray(String s) {
        seasonsArray.add(s);
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }

    public int getSeasonNum() {
        return seasonNum;
    }

    public void setSeasonNum(int seasonNum) {
        this.seasonNum = seasonNum;
    }

    public int getNumGames() {
        return numGames;
    }

    public void setNumGames(int numGames) {
        this.numGames = numGames;
    }



    public Button createNewButton(String s, int x) {
        final Button newButton = new Button(UserHomeScreen.this);
        newButton.setText(s);
        newButton.setTextSize(20);
        newButton.setAllCaps(false);
        Typeface tf = mGoButton.getTypeface();
        newButton.setTag("" + (x + 1));
        newButton.setTypeface(tf);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveSharedPreference.setUserhomeGameclicked(UserHomeScreen.this, view.getTag().toString());
                Log.d("STATMASTER", "BUTTONCLICKED: " + view.getTag().toString());
                Intent viewGameIntent = new Intent(UserHomeScreen.this, ViewGame.class);
                finish();
                startActivity(viewGameIntent);
            }
        });
        newButton.setBackground(this.getResources().getDrawable(R.drawable.round_button_grey));
        newButton.setTextColor(this.getResources().getColor(R.color.colorIcons));
        return newButton;
    }

}
