package com.developer.jacob.statmaster;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageItemInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ViewGame extends AppCompatActivity {

    private TextView mTextViewOne;
    private TextView mTextViewTwo;
    private TextView mTextViewThree;
    private TextView mTextViewFour;
    private TextView mTextViewFive;
    private TextView mTextViewSix;
    private TextView mTextViewSeven;

    private EditText mEditTextOneZero;
    private EditText mEditTextTwoZero;
    private EditText mEditTextThreeZero;
    private EditText mEditTextFourZero;
    private EditText mEditTextFiveZero;
    private EditText mEditTextSixZero;
    private EditText mEditTextSevenZero;

    private EditText mEditTextTwoOne;
    private EditText mEditTextThreeOne;
    private EditText mEditTextFourOne;
    private EditText mEditTextFiveOne;
    private EditText mEditTextSixOne;
    private EditText mEditTextSevenOne;

    private EditText mEditTextOneTwo;
    private EditText mEditTextTwoTwo;
    private EditText mEditTextThreeTwo;
    private EditText mEditTextFourTwo;
    private EditText mEditTextFiveTwo;
    private EditText mEditTextSixTwo;
    private EditText mEditTextSevenTwo;

    private EditText mEditTextSixThree;
    private EditText mEditTextSevenThree;

    private EditText mEditTextOneFour;
    private EditText mEditTextTwoFour;
    private EditText mEditTextThreeFour;
    private EditText mEditTextFourFour;
    private EditText mEditTextFiveFour;
    private EditText mEditTextSixFour;
    private EditText mEditTextSevenFour;

    private ToggleButton mToggleButton1;
    private ToggleButton mToggleButton2;
    private ToggleButton mToggleButton3;
    private ToggleButton mToggleButton4;

    private EditText mRightEditTextInningsOne;
    private EditText mLeftEditTextInningsOne;
    private EditText mRightEditTextInningsFive;
    private EditText mLeftEditTextInningsFive;
    private EditText mLeftETSB;
    private EditText mRightETSB;

    private TextView mTitleTextView;
    private TextView mPitchingNotesTitleTextView;

    private EditText mPitchingNotes;
    private EditText mBattingNotes;

    private ProgressBar mProgressBar;

    private Button mLeftButton;
    private Button mRightButton;
    private Button mEditSeason;

    private static int mCurrentPosition;
    private String[][] mInputsArray;

    private String mCurrentSeason;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game);
        mCurrentPosition = 0;

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mEditSeason = findViewById(R.id.EditSeasonButton);



        mInputsArray = new String[][]{{"Game Info", "Month", "Day", "Year", "Opponent", "Your Score", "Opponent Score", "Location", "Cancel", "Next >"},
                {"Batting", "Innings Batted", "Plate Appearances", "Single", "Double", "Triple", "Home Run", "Walks", "< Back", "Next >"},
                {"Batting Continued", "Sac (Bunt/Fly)", "Hit-by-pitches", "RBIs", "Runs Scored", "Strikeouts", "Catcher Interference", "Stolen Bases", "< Back", "Next >"},
                {"Pitching", "Win", "Loss", "Save", "Blown Save", "Innings Pitched", "Earned Runs", "Total Runs", "< Back", "Next >"},
                {"Pitching Continued", "Strikeouts", "Walks", "Hit-by-pitches", "Wild Pitches", "Hits", "Home Runs", "Pitches", "< Back", "Next >"},
                {"Batting Notes", "Pitching Notes", "Type here...", "< Back", "Finish"}};

        update();


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        final FirebaseUser currentUser = mAuth.getCurrentUser();
        mDatabaseReference.child("users").child(currentUser.getUid()).child("Seasons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String currentSeason = "";
                String check;
                int x = 0;
                for(DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    x++;
                    check = dsp.child("TeamName").getValue().toString() + ", " + dsp.child("LeagueName").getValue().toString() + " - " + dsp.child("Season").getValue().toString() + ", " + dsp.child("Year").getValue().toString();
                    if(check.equals(SaveSharedPreference.getPrefSeasonforgame(ViewGame.this))) {
                        currentSeason = "Season" + x;
                    }
                }
                setCurrentSeason(currentSeason);


                updateEditTextValues();





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentPosition() <= 5 && getCurrentPosition() > 0)
                {

                    setCurrentPosition(getCurrentPosition() - 1);
                    update();

                } else if(getCurrentPosition() == 0) {
                    Intent goBackIntent = new Intent(ViewGame.this, NewSeasonNewGameActivity.class);
                    finish();
                    startActivity(goBackIntent);
                }
            }
        });

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentPosition() >= 0 && getCurrentPosition() < 5)
                {

                    setCurrentPosition(getCurrentPosition() + 1);
                    update();
                } else if(getCurrentPosition() == 5) {

                    if(mEditTextOneZero.getText().toString().equals(""))
                    {
                        showErrorDialog("Please enter a month.");
                    } else if (mEditTextTwoZero.getText().toString().equals("")) {
                        showErrorDialog("Please enter a day.");
                    } else if (mEditTextThreeZero.getText().toString().equals("")) {
                        showErrorDialog("Please enter a year!");
                    } else if (mEditTextFourZero.getText().toString().equals("")) {
                        showErrorDialog("Please enter an opponent.");
                    } else if (mEditTextFiveZero.getText().toString().equals("")) {
                        showErrorDialog("Please enter your team's score.");
                    } else if (mEditTextSixZero.getText().toString().equals("")) {
                        showErrorDialog("Please enter the opponent's score.");
                    } else if (mEditTextSevenZero.getText().toString().equals("")) {
                        showErrorDialog("Please enter a location.");
                    } else if (((!(String.valueOf(mEditTextSevenThree.getText()).equals(""))) || (!(String.valueOf(mEditTextSixThree.getText()).equals("")))) && (Integer.valueOf(mEditTextSixThree.getText().toString()) > Integer.valueOf(mEditTextSevenThree.getText().toString()))) {
                        showErrorDialog("More earned runs than total runs allowed.");
                    } else if (String.valueOf(mToggleButton1.getText()).equals(String.valueOf(mToggleButton2.getText())) && String.valueOf(mToggleButton1.getText()).equals("YES")) {
                        showErrorDialog("Can not win and lose the same game. Please choose one or neither.");
                    } else if (String.valueOf(mToggleButton3.getText()).equals(String.valueOf(mToggleButton4.getText())) && String.valueOf(mToggleButton1.getText()).equals("YES")) {
                        showErrorDialog("Can not get save and blow save in the same game. Please choose one or neither.");
                    } else if (String.valueOf(mToggleButton1.getText()).equals(String.valueOf(mToggleButton3.getText())) && String.valueOf(mToggleButton1.getText()).equals("YES")) {
                        showErrorDialog("Can not get save and win in same game. Please choose one or neither.");
                    } else if (String.valueOf(mToggleButton2.getText()).equals(String.valueOf(mToggleButton3.getText())) && String.valueOf(mToggleButton1.getText()).equals("YES")) {
                        showErrorDialog("Can not get loss and save in the same game. Please choose one or neither.");
                    } else if (((!(String.valueOf(mEditTextOneZero.getText()).equals("")))) && (Integer.valueOf(mEditTextOneZero.getText().toString()) < 0 || Integer.valueOf(mEditTextOneZero.getText().toString()) > 31)) {
                        showErrorDialog("Invalid day.");
                    } else if (((!(String.valueOf(mEditTextTwoZero.getText()).equals("")))) && (Integer.valueOf(mEditTextTwoZero.getText().toString()) < 0 || Integer.valueOf(mEditTextOneZero.getText().toString()) > 12)) {
                        showErrorDialog("Invalid month.");
                    } else if (((!(String.valueOf(mEditTextOneZero.getText()).equals("")))) && (Integer.valueOf(mEditTextThreeZero.getText().toString()) < 1850 || Integer.valueOf(mEditTextOneZero.getText().toString()) > 2025)) {
                        showErrorDialog("Invalid year.");
                    } else {
                        firebaseSendAndFinish();
                    }
                }
            }
        });


        mEditSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editSeasonIntent = new Intent(ViewGame.this, EditSeason.class);
                finish();
                startActivity(editSeasonIntent);
            }
        });


    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        mCurrentPosition = currentPosition;
    }

    public void update()
    {

        // TEXTVIEWS
        mTextViewOne = findViewById(R.id.textView1);
        mTextViewTwo = findViewById(R.id.textView2);
        mTextViewThree = findViewById(R.id.textView3);
        mTextViewFour = findViewById(R.id.textView4);
        mTextViewFive = findViewById(R.id.textView5);
        mTextViewSix = findViewById(R.id.textView6);
        mTextViewSeven = findViewById(R.id.textView7);

        // ZEROTH SET OF ETs
        mEditTextOneZero = findViewById(R.id.editText1_0);
        mEditTextTwoZero = findViewById(R.id.editText2);
        mEditTextThreeZero = findViewById(R.id.editText3);
        mEditTextFourZero = findViewById(R.id.editText4);
        mEditTextFiveZero = findViewById(R.id.editText5);
        mEditTextSixZero = findViewById(R.id.editText6);
        mEditTextSevenZero = findViewById(R.id.editText7);

        // FIRST SET OF ETs
        mEditTextTwoOne = findViewById(R.id.editText2_1);
        mEditTextThreeOne = findViewById(R.id.editText3_1);
        mEditTextFourOne = findViewById(R.id.editText4_1);
        mEditTextFiveOne = findViewById(R.id.editText5_1);
        mEditTextSixOne = findViewById(R.id.editText6_1);
        mEditTextSevenOne = findViewById(R.id.editText7_1);

        // SECOND SET OF ETs
        mEditTextOneTwo = findViewById(R.id.editText1_2);
        mEditTextTwoTwo = findViewById(R.id.editText2_2);
        mEditTextThreeTwo = findViewById(R.id.editText3_2);
        mEditTextFourTwo = findViewById(R.id.editText4_2);
        mEditTextFiveTwo = findViewById(R.id.editText5_2);
        mEditTextSixTwo = findViewById(R.id.editText6_2);
        mEditTextSevenTwo = findViewById(R.id.editText7_2);

        // THIRD SET OF ETs
        mEditTextSixThree = findViewById(R.id.editText6_3);
        mEditTextSevenThree = findViewById(R.id.editText7_3);

        // FOURTH SET OF ETs
        mEditTextOneFour = findViewById(R.id.editText1_4);
        mEditTextTwoFour = findViewById(R.id.editText2_4);
        mEditTextThreeFour = findViewById(R.id.editText3_4);
        mEditTextFourFour = findViewById(R.id.editText4_4);
        mEditTextFiveFour = findViewById(R.id.editText5_4);
        mEditTextSixFour = findViewById(R.id.editText6_4);
        mEditTextSevenFour = findViewById(R.id.editText7_4);


        // TITLE
        mTitleTextView = findViewById(R.id.textViewTitle);
        mPitchingNotesTitleTextView = findViewById(R.id.tempTextViewPitchingTitle);

        // PROGRESS BAR
        mProgressBar = findViewById(R.id.progressBar3);
        // BOTTOM BUTTONS FOR NAVIGATION
        mLeftButton = findViewById(R.id.leftButton);
        mRightButton = findViewById(R.id.rightButton);




        // SET OF TBs
        mToggleButton1 = findViewById(R.id.toggleButton1);
        mToggleButton2 = findViewById(R.id.toggleButton2);
        mToggleButton3 = findViewById(R.id.toggleButton3);
        mToggleButton4 = findViewById(R.id.toggleButton4);



        // THREE SETS OF SMALL SIDE-BY-SIDE ETs
        mLeftEditTextInningsOne = findViewById(R.id.inningEditTextOneLeft);
        mRightEditTextInningsOne = findViewById(R.id.inningsEditTextOneRight);
        mLeftEditTextInningsFive = findViewById(R.id.leftEditTextInningsFive);
        mRightEditTextInningsFive = findViewById(R.id.rightEditTextInningsFive);
        mLeftETSB = findViewById(R.id.leftEditTextSBThree);
        mRightETSB = findViewById(R.id.rightEditTextSBThree);
        mPitchingNotes = findViewById(R.id.PitchingNotesET);
        mBattingNotes = findViewById(R.id.BattingNotesET);


        // SETTING THE VALUES OF ALL TEXTVIEWS
        if(mCurrentPosition < 5) {
            mTitleTextView.setText(mInputsArray[mCurrentPosition][0]);
            mTextViewOne.setText(mInputsArray[mCurrentPosition][1]);
            mTextViewTwo.setText(mInputsArray[mCurrentPosition][2]);
            mTextViewThree.setText(mInputsArray[mCurrentPosition][3]);
            mTextViewFour.setText(mInputsArray[mCurrentPosition][4]);
            mTextViewFive.setText(mInputsArray[mCurrentPosition][5]);
            mTextViewSix.setText(mInputsArray[mCurrentPosition][6]);
            mTextViewSeven.setText(mInputsArray[mCurrentPosition][7]);
            mLeftButton.setText(mInputsArray[mCurrentPosition][8]);
            mRightButton.setText(mInputsArray[mCurrentPosition][9]);
        } else {
            mTitleTextView.setText(mInputsArray[5][0]);
            mPitchingNotesTitleTextView.setText(mInputsArray[5][1]);
            mBattingNotes.setHint(mInputsArray[5][2]);
            mPitchingNotes.setHint(mInputsArray[5][2]);
            mLeftButton.setText(mInputsArray[5][3]);
            mRightButton.setText(mInputsArray[5][4]);
        }

        mTextViewOne.bringToFront();
        mTextViewTwo.bringToFront();
        mTextViewThree.bringToFront();
        mTextViewFour.bringToFront();
        mTextViewFive.bringToFront();
        mTextViewSix.bringToFront();
        mTextViewSeven.bringToFront();



        // VISIBILITIES
        if(getCurrentPosition() == 0) {

            mEditTextOneZero.setVisibility(View.VISIBLE);
            mEditTextTwoZero.setVisibility(View.VISIBLE);
            mEditTextThreeZero.setVisibility(View.VISIBLE);
            mEditTextFourZero.setVisibility(View.VISIBLE);
            mEditTextFiveZero.setVisibility(View.VISIBLE);
            mEditTextSixZero.setVisibility(View.VISIBLE);
            mEditTextSevenZero.setVisibility(View.VISIBLE);

            mEditTextTwoOne.setVisibility(View.INVISIBLE);
            mEditTextThreeOne.setVisibility(View.INVISIBLE);
            mEditTextFourOne.setVisibility(View.INVISIBLE);
            mEditTextFiveOne.setVisibility(View.INVISIBLE);
            mEditTextSixOne.setVisibility(View.INVISIBLE);
            mEditTextSevenOne.setVisibility(View.INVISIBLE);
            mLeftEditTextInningsOne.setVisibility(View.INVISIBLE);
            mRightEditTextInningsOne.setVisibility(View.INVISIBLE);

            mEditTextOneTwo.setVisibility(View.INVISIBLE);
            mEditTextTwoTwo.setVisibility(View.INVISIBLE);
            mEditTextThreeTwo.setVisibility(View.INVISIBLE);
            mEditTextFourTwo.setVisibility(View.INVISIBLE);
            mEditTextFiveTwo.setVisibility(View.INVISIBLE);
            mEditTextSixTwo.setVisibility(View.INVISIBLE);
            mEditTextSevenTwo.setVisibility(View.INVISIBLE);

            mToggleButton1.setVisibility(View.INVISIBLE);
            mToggleButton2.setVisibility(View.INVISIBLE);
            mToggleButton3.setVisibility(View.INVISIBLE);
            mToggleButton4.setVisibility(View.INVISIBLE);
            mLeftEditTextInningsFive.setVisibility(View.INVISIBLE);
            mRightEditTextInningsFive.setVisibility(View.INVISIBLE);
            mEditTextSixThree.setVisibility(View.INVISIBLE);
            mEditTextSevenThree.setVisibility(View.INVISIBLE);

            mEditTextOneFour.setVisibility(View.INVISIBLE);
            mEditTextTwoFour.setVisibility(View.INVISIBLE);
            mEditTextThreeFour.setVisibility(View.INVISIBLE);
            mEditTextFourFour.setVisibility(View.INVISIBLE);
            mEditTextFiveFour.setVisibility(View.INVISIBLE);
            mEditTextSixFour.setVisibility(View.INVISIBLE);
            mEditTextSevenFour.setVisibility(View.INVISIBLE);
            mLeftETSB.setVisibility(View.INVISIBLE);
            mRightETSB.setVisibility(View.INVISIBLE);
            mPitchingNotesTitleTextView.setVisibility(View.INVISIBLE);

            mTextViewOne.setVisibility(View.VISIBLE);
            mTextViewTwo.setVisibility(View.VISIBLE);
            mTextViewThree.setVisibility(View.VISIBLE);
            mTextViewFour.setVisibility(View.VISIBLE);
            mTextViewFive.setVisibility(View.VISIBLE);
            mTextViewSix.setVisibility(View.VISIBLE);
            mTextViewSeven.setVisibility(View.VISIBLE);
            mPitchingNotes.setVisibility(View.INVISIBLE);
            mBattingNotes.setVisibility(View.INVISIBLE);

            mProgressBar.setProgress(0);

        } else if(getCurrentPosition() == 1) {

            mEditTextOneZero.setVisibility(View.INVISIBLE);
            mEditTextTwoZero.setVisibility(View.INVISIBLE);
            mEditTextThreeZero.setVisibility(View.INVISIBLE);
            mEditTextFourZero.setVisibility(View.INVISIBLE);
            mEditTextFiveZero.setVisibility(View.INVISIBLE);
            mEditTextSixZero.setVisibility(View.INVISIBLE);
            mEditTextSevenZero.setVisibility(View.INVISIBLE);

            mEditTextTwoOne.setVisibility(View.VISIBLE);
            mEditTextThreeOne.setVisibility(View.VISIBLE);
            mEditTextFourOne.setVisibility(View.VISIBLE);
            mEditTextFiveOne.setVisibility(View.VISIBLE);
            mEditTextSixOne.setVisibility(View.VISIBLE);
            mEditTextSevenOne.setVisibility(View.VISIBLE);
            mLeftEditTextInningsOne.setVisibility(View.VISIBLE);
            mRightEditTextInningsOne.setVisibility(View.VISIBLE);

            mEditTextOneTwo.setVisibility(View.INVISIBLE);
            mEditTextTwoTwo.setVisibility(View.INVISIBLE);
            mEditTextThreeTwo.setVisibility(View.INVISIBLE);
            mEditTextFourTwo.setVisibility(View.INVISIBLE);
            mEditTextFiveTwo.setVisibility(View.INVISIBLE);
            mEditTextSixTwo.setVisibility(View.INVISIBLE);
            mEditTextSevenTwo.setVisibility(View.INVISIBLE);

            mToggleButton1.setVisibility(View.INVISIBLE);
            mToggleButton2.setVisibility(View.INVISIBLE);
            mToggleButton3.setVisibility(View.INVISIBLE);
            mToggleButton4.setVisibility(View.INVISIBLE);
            mLeftEditTextInningsFive.setVisibility(View.INVISIBLE);
            mRightEditTextInningsFive.setVisibility(View.INVISIBLE);
            mEditTextSixThree.setVisibility(View.INVISIBLE);
            mEditTextSevenThree.setVisibility(View.INVISIBLE);

            mEditTextOneFour.setVisibility(View.INVISIBLE);
            mEditTextTwoFour.setVisibility(View.INVISIBLE);
            mEditTextThreeFour.setVisibility(View.INVISIBLE);
            mEditTextFourFour.setVisibility(View.INVISIBLE);
            mEditTextFiveFour.setVisibility(View.INVISIBLE);
            mEditTextSixFour.setVisibility(View.INVISIBLE);
            mEditTextSevenFour.setVisibility(View.INVISIBLE);
            mLeftETSB.setVisibility(View.INVISIBLE);
            mRightETSB.setVisibility(View.INVISIBLE);
            mPitchingNotesTitleTextView.setVisibility(View.INVISIBLE);

            mTextViewOne.setVisibility(View.VISIBLE);
            mTextViewTwo.setVisibility(View.VISIBLE);
            mTextViewThree.setVisibility(View.VISIBLE);
            mTextViewFour.setVisibility(View.VISIBLE);
            mTextViewFive.setVisibility(View.VISIBLE);
            mTextViewSix.setVisibility(View.VISIBLE);
            mTextViewSeven.setVisibility(View.VISIBLE);
            mPitchingNotes.setVisibility(View.INVISIBLE);
            mBattingNotes.setVisibility(View.INVISIBLE);

            mProgressBar.setProgress(20);


        } else if(getCurrentPosition() == 2) {

            mEditTextOneZero.setVisibility(View.INVISIBLE);
            mEditTextTwoZero.setVisibility(View.INVISIBLE);
            mEditTextThreeZero.setVisibility(View.INVISIBLE);
            mEditTextFourZero.setVisibility(View.INVISIBLE);
            mEditTextFiveZero.setVisibility(View.INVISIBLE);
            mEditTextSixZero.setVisibility(View.INVISIBLE);
            mEditTextSevenZero.setVisibility(View.INVISIBLE);

            mEditTextTwoOne.setVisibility(View.INVISIBLE);
            mEditTextThreeOne.setVisibility(View.INVISIBLE);
            mEditTextFourOne.setVisibility(View.INVISIBLE);
            mEditTextFiveOne.setVisibility(View.INVISIBLE);
            mEditTextSixOne.setVisibility(View.INVISIBLE);
            mEditTextSevenOne.setVisibility(View.INVISIBLE);
            mLeftEditTextInningsOne.setVisibility(View.INVISIBLE);
            mRightEditTextInningsOne.setVisibility(View.INVISIBLE);

            mEditTextOneTwo.setVisibility(View.VISIBLE);
            mEditTextTwoTwo.setVisibility(View.VISIBLE);
            mEditTextThreeTwo.setVisibility(View.VISIBLE);
            mEditTextFourTwo.setVisibility(View.VISIBLE);
            mEditTextFiveTwo.setVisibility(View.VISIBLE);
            mEditTextSixTwo.setVisibility(View.VISIBLE);
            mEditTextSevenTwo.setVisibility(View.INVISIBLE);

            mToggleButton1.setVisibility(View.INVISIBLE);
            mToggleButton2.setVisibility(View.INVISIBLE);
            mToggleButton3.setVisibility(View.INVISIBLE);
            mToggleButton4.setVisibility(View.INVISIBLE);
            mLeftEditTextInningsFive.setVisibility(View.INVISIBLE);
            mRightEditTextInningsFive.setVisibility(View.INVISIBLE);
            mEditTextSixThree.setVisibility(View.INVISIBLE);
            mEditTextSevenThree.setVisibility(View.INVISIBLE);

            mEditTextOneFour.setVisibility(View.INVISIBLE);
            mEditTextTwoFour.setVisibility(View.INVISIBLE);
            mEditTextThreeFour.setVisibility(View.INVISIBLE);
            mEditTextFourFour.setVisibility(View.INVISIBLE);
            mEditTextFiveFour.setVisibility(View.INVISIBLE);
            mEditTextSixFour.setVisibility(View.INVISIBLE);
            mEditTextSevenFour.setVisibility(View.INVISIBLE);
            mLeftETSB.setVisibility(View.VISIBLE);
            mRightETSB.setVisibility(View.VISIBLE);
            mPitchingNotesTitleTextView.setVisibility(View.INVISIBLE);

            mTextViewOne.setVisibility(View.VISIBLE);
            mTextViewTwo.setVisibility(View.VISIBLE);
            mTextViewThree.setVisibility(View.VISIBLE);
            mTextViewFour.setVisibility(View.VISIBLE);
            mTextViewFive.setVisibility(View.VISIBLE);
            mTextViewSix.setVisibility(View.VISIBLE);
            mTextViewSeven.setVisibility(View.VISIBLE);
            mPitchingNotes.setVisibility(View.INVISIBLE);
            mBattingNotes.setVisibility(View.INVISIBLE);

            mProgressBar.setProgress(40);

        } else if(getCurrentPosition() == 3) {

            mEditTextOneZero.setVisibility(View.INVISIBLE);
            mEditTextTwoZero.setVisibility(View.INVISIBLE);
            mEditTextThreeZero.setVisibility(View.INVISIBLE);
            mEditTextFourZero.setVisibility(View.INVISIBLE);
            mEditTextFiveZero.setVisibility(View.INVISIBLE);
            mEditTextSixZero.setVisibility(View.INVISIBLE);
            mEditTextSevenZero.setVisibility(View.INVISIBLE);

            mEditTextTwoOne.setVisibility(View.INVISIBLE);
            mEditTextThreeOne.setVisibility(View.INVISIBLE);
            mEditTextFourOne.setVisibility(View.INVISIBLE);
            mEditTextFiveOne.setVisibility(View.INVISIBLE);
            mEditTextSixOne.setVisibility(View.INVISIBLE);
            mEditTextSevenOne.setVisibility(View.INVISIBLE);
            mLeftEditTextInningsOne.setVisibility(View.INVISIBLE);
            mRightEditTextInningsOne.setVisibility(View.INVISIBLE);

            mEditTextOneTwo.setVisibility(View.INVISIBLE);
            mEditTextTwoTwo.setVisibility(View.INVISIBLE);
            mEditTextThreeTwo.setVisibility(View.INVISIBLE);
            mEditTextFourTwo.setVisibility(View.INVISIBLE);
            mEditTextFiveTwo.setVisibility(View.INVISIBLE);
            mEditTextSixTwo.setVisibility(View.INVISIBLE);
            mEditTextSevenTwo.setVisibility(View.INVISIBLE);

            mToggleButton1.setVisibility(View.VISIBLE);
            mToggleButton2.setVisibility(View.VISIBLE);
            mToggleButton3.setVisibility(View.VISIBLE);
            mToggleButton4.setVisibility(View.VISIBLE);
            mLeftEditTextInningsFive.setVisibility(View.VISIBLE);
            mRightEditTextInningsFive.setVisibility(View.VISIBLE);
            mEditTextSixThree.setVisibility(View.VISIBLE);
            mEditTextSevenThree.setVisibility(View.VISIBLE);

            mEditTextOneFour.setVisibility(View.INVISIBLE);
            mEditTextTwoFour.setVisibility(View.INVISIBLE);
            mEditTextThreeFour.setVisibility(View.INVISIBLE);
            mEditTextFourFour.setVisibility(View.INVISIBLE);
            mEditTextFiveFour.setVisibility(View.INVISIBLE);
            mEditTextSixFour.setVisibility(View.INVISIBLE);
            mEditTextSevenFour.setVisibility(View.INVISIBLE);
            mLeftETSB.setVisibility(View.INVISIBLE);
            mRightETSB.setVisibility(View.INVISIBLE);
            mPitchingNotesTitleTextView.setVisibility(View.INVISIBLE);
            mTextViewOne.setVisibility(View.VISIBLE);
            mTextViewTwo.setVisibility(View.VISIBLE);
            mTextViewThree.setVisibility(View.VISIBLE);
            mTextViewFour.setVisibility(View.VISIBLE);
            mTextViewFive.setVisibility(View.VISIBLE);
            mTextViewSix.setVisibility(View.VISIBLE);
            mTextViewSeven.setVisibility(View.VISIBLE);
            mPitchingNotes.setVisibility(View.INVISIBLE);
            mBattingNotes.setVisibility(View.INVISIBLE);

            mProgressBar.setProgress(60);

        } else if(getCurrentPosition() == 4) {
            mEditTextOneZero.setVisibility(View.INVISIBLE);
            mEditTextTwoZero.setVisibility(View.INVISIBLE);
            mEditTextThreeZero.setVisibility(View.INVISIBLE);
            mEditTextFourZero.setVisibility(View.INVISIBLE);
            mEditTextFiveZero.setVisibility(View.INVISIBLE);
            mEditTextSixZero.setVisibility(View.INVISIBLE);
            mEditTextSevenZero.setVisibility(View.INVISIBLE);

            mEditTextTwoOne.setVisibility(View.INVISIBLE);
            mEditTextThreeOne.setVisibility(View.INVISIBLE);
            mEditTextFourOne.setVisibility(View.INVISIBLE);
            mEditTextFiveOne.setVisibility(View.INVISIBLE);
            mEditTextSixOne.setVisibility(View.INVISIBLE);
            mEditTextSevenOne.setVisibility(View.INVISIBLE);
            mLeftEditTextInningsOne.setVisibility(View.INVISIBLE);
            mRightEditTextInningsOne.setVisibility(View.INVISIBLE);

            mEditTextOneTwo.setVisibility(View.INVISIBLE);
            mEditTextTwoTwo.setVisibility(View.INVISIBLE);
            mEditTextThreeTwo.setVisibility(View.INVISIBLE);
            mEditTextFourTwo.setVisibility(View.INVISIBLE);
            mEditTextFiveTwo.setVisibility(View.INVISIBLE);
            mEditTextSixTwo.setVisibility(View.INVISIBLE);
            mEditTextSevenTwo.setVisibility(View.INVISIBLE);

            mToggleButton1.setVisibility(View.INVISIBLE);
            mToggleButton2.setVisibility(View.INVISIBLE);
            mToggleButton3.setVisibility(View.INVISIBLE);
            mToggleButton4.setVisibility(View.INVISIBLE);
            mLeftEditTextInningsFive.setVisibility(View.INVISIBLE);
            mRightEditTextInningsFive.setVisibility(View.INVISIBLE);
            mEditTextSixThree.setVisibility(View.INVISIBLE);
            mEditTextSevenThree.setVisibility(View.INVISIBLE);

            mEditTextOneFour.setVisibility(View.VISIBLE);
            mEditTextTwoFour.setVisibility(View.VISIBLE);
            mEditTextThreeFour.setVisibility(View.VISIBLE);
            mEditTextFourFour.setVisibility(View.VISIBLE);
            mEditTextFiveFour.setVisibility(View.VISIBLE);
            mEditTextSixFour.setVisibility(View.VISIBLE);
            mEditTextSevenFour.setVisibility(View.VISIBLE);
            mLeftETSB.setVisibility(View.INVISIBLE);
            mRightETSB.setVisibility(View.INVISIBLE);
            mPitchingNotesTitleTextView.setVisibility(View.INVISIBLE);
            mTextViewOne.setVisibility(View.VISIBLE);
            mTextViewTwo.setVisibility(View.VISIBLE);
            mTextViewThree.setVisibility(View.VISIBLE);
            mTextViewFour.setVisibility(View.VISIBLE);
            mTextViewFive.setVisibility(View.VISIBLE);
            mTextViewSix.setVisibility(View.VISIBLE);
            mTextViewSeven.setVisibility(View.VISIBLE);

            mPitchingNotes.setVisibility(View.INVISIBLE);
            mBattingNotes.setVisibility(View.INVISIBLE);

            mProgressBar.setProgress(80);
        } else if (getCurrentPosition() == 5) {
            mEditTextOneZero.setVisibility(View.INVISIBLE);
            mEditTextTwoZero.setVisibility(View.INVISIBLE);
            mEditTextThreeZero.setVisibility(View.INVISIBLE);
            mEditTextFourZero.setVisibility(View.INVISIBLE);
            mEditTextFiveZero.setVisibility(View.INVISIBLE);
            mEditTextSixZero.setVisibility(View.INVISIBLE);
            mEditTextSevenZero.setVisibility(View.INVISIBLE);

            mEditTextTwoOne.setVisibility(View.INVISIBLE);
            mEditTextThreeOne.setVisibility(View.INVISIBLE);
            mEditTextFourOne.setVisibility(View.INVISIBLE);
            mEditTextFiveOne.setVisibility(View.INVISIBLE);
            mEditTextSixOne.setVisibility(View.INVISIBLE);
            mEditTextSevenOne.setVisibility(View.INVISIBLE);
            mLeftEditTextInningsOne.setVisibility(View.INVISIBLE);
            mRightEditTextInningsOne.setVisibility(View.INVISIBLE);

            mEditTextOneTwo.setVisibility(View.INVISIBLE);
            mEditTextTwoTwo.setVisibility(View.INVISIBLE);
            mEditTextThreeTwo.setVisibility(View.INVISIBLE);
            mEditTextFourTwo.setVisibility(View.INVISIBLE);
            mEditTextFiveTwo.setVisibility(View.INVISIBLE);
            mEditTextSixTwo.setVisibility(View.INVISIBLE);
            mEditTextSevenTwo.setVisibility(View.INVISIBLE);

            mToggleButton1.setVisibility(View.INVISIBLE);
            mToggleButton2.setVisibility(View.INVISIBLE);
            mToggleButton3.setVisibility(View.INVISIBLE);
            mToggleButton4.setVisibility(View.INVISIBLE);
            mLeftEditTextInningsFive.setVisibility(View.INVISIBLE);
            mRightEditTextInningsFive.setVisibility(View.INVISIBLE);
            mEditTextSixThree.setVisibility(View.INVISIBLE);
            mEditTextSevenThree.setVisibility(View.INVISIBLE);

            mEditTextOneFour.setVisibility(View.INVISIBLE);
            mEditTextTwoFour.setVisibility(View.INVISIBLE);
            mEditTextThreeFour.setVisibility(View.INVISIBLE);
            mEditTextFourFour.setVisibility(View.INVISIBLE);
            mEditTextFiveFour.setVisibility(View.INVISIBLE);
            mEditTextSixFour.setVisibility(View.INVISIBLE);
            mEditTextSevenFour.setVisibility(View.INVISIBLE);
            mLeftETSB.setVisibility(View.INVISIBLE);
            mRightETSB.setVisibility(View.INVISIBLE);
            mPitchingNotesTitleTextView.setVisibility(View.VISIBLE);

            mPitchingNotes.setVisibility(View.VISIBLE);
            mBattingNotes.setVisibility(View.VISIBLE);

            mTextViewOne.setVisibility(View.INVISIBLE);
            mTextViewTwo.setVisibility(View.INVISIBLE);
            mTextViewThree.setVisibility(View.INVISIBLE);
            mTextViewFour.setVisibility(View.INVISIBLE);
            mTextViewFive.setVisibility(View.INVISIBLE);
            mTextViewSix.setVisibility(View.INVISIBLE);
            mTextViewSeven.setVisibility(View.INVISIBLE);




            mProgressBar.setProgress(100);
        }



    }

    public void firebaseSendAndFinish() {



        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();





        final FirebaseUser currentUser = mAuth.getCurrentUser();
        mDatabaseReference.child("users").child(currentUser.getUid()).child("Seasons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String currentSeason = "";
                String check;
                int x = 0;
                for(DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    x++;
                    check = dsp.child("TeamName").getValue().toString() + ", " + dsp.child("LeagueName").getValue().toString() + " - " + dsp.child("Season").getValue().toString() + ", " + dsp.child("Year").getValue().toString();
                    if(check.equals(SaveSharedPreference.getPrefSeasonforgame(ViewGame.this))) {
                        currentSeason = "Season" + x;
                    }
                }
                setCurrentSeason(currentSeason);

                String numGames = String.valueOf(dataSnapshot.child(currentSeason).child("gameNumber").getValue());
                int numberOfGames = Integer.valueOf(numGames);

                Map<String, Object> gameSend = new HashMap<>();
                gameSend.put("Month", mEditTextOneZero.getText().toString());
                gameSend.put("Day", mEditTextTwoZero.getText().toString());
                gameSend.put("Year", mEditTextThreeZero.getText().toString());
                gameSend.put("Opponent", mEditTextFourZero.getText().toString());
                gameSend.put("User team score", mEditTextFiveZero.getText().toString());
                gameSend.put("Opponent Score", mEditTextSixZero.getText().toString());
                gameSend.put("Location", mEditTextSevenZero.getText().toString());

                gameSend.put("Innings Played", mLeftEditTextInningsOne.getText().toString());
                gameSend.put("Outs Played", mRightEditTextInningsOne.getText().toString());
                gameSend.put("Plate Appearances", mEditTextTwoOne.getText().toString());
                gameSend.put("Singles", mEditTextThreeOne.getText().toString());
                gameSend.put("Doubles", mEditTextFourOne.getText().toString());
                gameSend.put("Triples", mEditTextFiveOne.getText().toString());
                gameSend.put("Batting Homeruns", mEditTextSixOne.getText().toString());
                gameSend.put("Batting Walks", mEditTextSevenOne.getText().toString());

                gameSend.put("Batting HBP", mEditTextTwoTwo.getText().toString());
                gameSend.put("RBI", mEditTextThreeTwo.getText().toString());
                gameSend.put("Batting Runs Scored", mEditTextThreeTwo.getText().toString());
                gameSend.put("Sac", mEditTextOneTwo.getText().toString());
                gameSend.put("Batting Strikeout", mEditTextFiveTwo.getText().toString());
                gameSend.put("Catcher Interference", mEditTextSixTwo.getText().toString());
                gameSend.put("SB True", mLeftETSB.getText().toString());
                gameSend.put("SB Attempts", mRightETSB.getText().toString());

                gameSend.put("Win", mToggleButton1.getText().toString());
                gameSend.put("Loss", mToggleButton2.getText().toString());
                gameSend.put("Save", mToggleButton3.getText().toString());
                gameSend.put("Blown Save", mToggleButton4.getText().toString());
                gameSend.put("Innings Pitched", mLeftEditTextInningsFive.getText().toString());
                gameSend.put("Outs Pitched", mRightEditTextInningsFive.getText().toString());
                gameSend.put("Pitching Earned Runs", mEditTextSixThree.getText().toString());
                gameSend.put("Pitching Runs Total", mEditTextSevenThree.getText().toString());

                gameSend.put("Pitching Strike Outs", mEditTextOneFour.getText().toString());
                gameSend.put("Pitching Walks", mEditTextTwoFour.getText().toString());
                gameSend.put("Wild Pitches", mEditTextThreeFour.getText().toString());
                gameSend.put("Pitching HBP", mEditTextFourFour.getText().toString());
                gameSend.put("Pitching Hits", mEditTextFiveFour.getText().toString());
                gameSend.put("Pitching Homeruns", mEditTextSixFour.getText().toString());
                gameSend.put("Pitches", mEditTextSevenFour.getText().toString());

                gameSend.put("Pitching Notes", mPitchingNotes.getText().toString());
                gameSend.put("Batting Notes", mBattingNotes.getText().toString());

                for (Map.Entry<String, Object> entry : gameSend.entrySet()) {
                    if(entry.getValue().equals(""))
                    {
                        if(!(entry.getKey().equals("Pitching Notes") || entry.getKey().equals("Batting Notes"))) {
                            gameSend.put(entry.getKey(), "" + 0);
                        }
                    }
                }

                Map<String, Object> temp = new HashMap<>();
                temp.put("gameNumber", "" + (numberOfGames + 1));
                mDatabaseReference.child("users").child(currentUser.getUid()).child("Seasons").child(currentSeason).updateChildren(temp);
                mDatabaseReference.child("users").child(currentUser.getUid()).child("Seasons").child(currentSeason).child("Games").child("game" + (numberOfGames + 1)).updateChildren(gameSend);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Intent finishedIntent = new Intent(ViewGame.this, UserHomeScreen.class);
        finish();
        startActivity(finishedIntent);
    }

    private void showErrorDialog(String message) {
        AlertDialog oops = new AlertDialog.Builder(ViewGame.this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        Toast.makeText(this, "Please fix the error to proceed!", Toast.LENGTH_SHORT).show();
    }


    public void updateEditTextValues() {

        Log.d("STATMASTER", "Current Season CHECK - " + getCurrentSeason());
        Log.d("STATMASTER", "Game Num CHECK - " + SaveSharedPreference.getSeasonnumforviewgame(ViewGame.this));


        FirebaseUser currentUser = mAuth.getCurrentUser();
        mDatabaseReference.child("users").child(currentUser.getUid()).child("Seasons").child(getCurrentSeason()).child("Games").child("game" + (SaveSharedPreference.getUserhomeGameclicked(ViewGame.this))).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO: Set fields to each value of current game


                mEditTextOneZero.setText(dataSnapshot.child("Month").getValue().toString());
                mEditTextTwoZero.setText(dataSnapshot.child("Day").getValue().toString());
                mEditTextThreeZero.setText(dataSnapshot.child("Year").getValue().toString());
                mEditTextFourZero.setText(dataSnapshot.child("Opponent").getValue().toString());
                mEditTextFiveZero.setText(dataSnapshot.child("User team score").getValue().toString());
                mEditTextSixZero.setText(dataSnapshot.child("Opponent Score").getValue().toString());
                mEditTextSevenZero.setText(dataSnapshot.child("Location").getValue().toString());

                mEditTextTwoOne.setText(dataSnapshot.child("Plate Appearances").getValue().toString());
                mEditTextThreeOne.setText(dataSnapshot.child("Singles").getValue().toString());
                mEditTextFourOne.setText(dataSnapshot.child("Doubles").getValue().toString());
                mEditTextFiveOne.setText(dataSnapshot.child("Triples").getValue().toString());
                mEditTextSixOne.setText(dataSnapshot.child("Batting Homeruns").getValue().toString());
                mEditTextSevenOne.setText(dataSnapshot.child("Batting Walks").getValue().toString());
                mLeftEditTextInningsOne.setText(dataSnapshot.child("Innings Played").getValue().toString());
                mRightEditTextInningsOne.setText(dataSnapshot.child("Outs Played").getValue().toString());

                mEditTextOneTwo.setText(dataSnapshot.child("Sac").getValue().toString());
                mEditTextTwoTwo.setText(dataSnapshot.child("Batting HBP").getValue().toString());
                mEditTextThreeTwo.setText(dataSnapshot.child("RBI").getValue().toString());
                mEditTextFourTwo.setText(dataSnapshot.child("Batting Runs Scored").getValue().toString());
                mEditTextFiveTwo.setText(dataSnapshot.child("Batting Strikeout").getValue().toString());
                mEditTextSixTwo.setText(dataSnapshot.child("Catcher Interference").getValue().toString());
                mLeftETSB.setText(dataSnapshot.child("SB True").getValue().toString());
                mRightETSB.setText(dataSnapshot.child("SB Attempts").getValue().toString());

                mBattingNotes.setText(dataSnapshot.child("Batting Notes").getValue().toString());
                mPitchingNotes.setText(dataSnapshot.child("Pitching Notes").getValue().toString());

                if(dataSnapshot.child("Win").getValue().toString().equals("true")) {
                    mToggleButton1.setChecked(true);
                } else {
                    mToggleButton1.setChecked(false);
                }
                if(dataSnapshot.child("Loss").getValue().toString().equals("true")) {
                    mToggleButton2.setChecked(true);
                } else {
                    mToggleButton2.setChecked(false);
                }if(dataSnapshot.child("Save").getValue().toString().equals("true")) {
                    mToggleButton3.setChecked(true);
                } else {
                    mToggleButton3.setChecked(false);
                }if(dataSnapshot.child("Blown Save").getValue().toString().equals("true")) {
                    mToggleButton4.setChecked(true);
                } else {
                    mToggleButton4.setChecked(false);
                }
                mLeftEditTextInningsFive.setText(dataSnapshot.child("Innings Pitched").getValue().toString());
                mRightEditTextInningsFive.setText(dataSnapshot.child("Outs Pitched").getValue().toString());
                mEditTextSixThree.setText(dataSnapshot.child("Pitching Earned Runs").getValue().toString());
                mEditTextSevenThree.setText(dataSnapshot.child("Pitching Runs Total").getValue().toString());

                mEditTextOneFour.setText(dataSnapshot.child("Pitching Strike Outs").getValue().toString());
                mEditTextTwoFour.setText(dataSnapshot.child("Pitching Walks").getValue().toString());
                mEditTextThreeFour.setText(dataSnapshot.child("Wild Pitches").getValue().toString());
                mEditTextFourFour.setText(dataSnapshot.child("Pitching HBP").getValue().toString());
                mEditTextFiveFour.setText(dataSnapshot.child("Pitching Hits").getValue().toString());
                mEditTextSixFour.setText(dataSnapshot.child("Pitching Homeruns").getValue().toString());
                mEditTextSevenFour.setText(dataSnapshot.child("Pitches").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public String getCurrentSeason() {
        return mCurrentSeason;
    }

    public void setCurrentSeason(String currentSeason) {
        mCurrentSeason = currentSeason;
    }


}
