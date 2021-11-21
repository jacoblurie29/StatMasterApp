package com.developer.jacob.statmaster;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.service.autofill.SaveCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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

public class NewGameActivity extends AppCompatActivity {

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

    private static int mCurrentPosition;
    private String[][] mInputsArray;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;

    private EditText mLeftSix;
    private EditText mRightSix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        mCurrentPosition = 0;

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mInputsArray = new String[][]{{"Game Info", "Month", "Day", "Year", "Opponent", "Your Score", "Opponent Score", "Location", "Cancel", "Next >"},
                {"Batting", "Innings Batted", "Plate Appearances", "Single", "Double", "Triple", "Home Run", "Walks", "< Back", "Next >"},
                {"Batting Continued", "Sac (Bunt/Fly)", "Hit-by-pitches", "RBIs", "Runs Scored", "Strikeouts", "Catcher Interference/Reached on Error", "Stolen Bases", "< Back", "Next >"},
                {"Pitching", "Win", "Loss", "Save", "Blown Save", "Innings Pitched", "Earned Runs", "Total Runs", "< Back", "Next >"},
                {"Pitching Continued", "Strikeouts", "Walks", "Hit-by-pitches", "Wild Pitches", "Hits", "Home Runs", "Pitches", "< Back", "Next >"},
                {"Batting Notes", "Pitching Notes", "Type here...", "< Back", "Finish"}};

        update();

        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentPosition() <= 5 && getCurrentPosition() > 0)
                {

                    setCurrentPosition(getCurrentPosition() - 1);
                    update();

                } else if(getCurrentPosition() == 0) {
                    Intent goBackIntent = new Intent(NewGameActivity.this, NewSeasonNewGameActivity.class);
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
        mProgressBar = findViewById(R.id.progressBar2);
        // BOTTOM BUTTONS FOR NAVIGATION
        mLeftButton = findViewById(R.id.leftButton);
        mRightButton = findViewById(R.id.rightButton);




        // SET OF TBs
        mToggleButton1 = findViewById(R.id.toggleButton5);
        mToggleButton2 = findViewById(R.id.toggleButton6);
        mToggleButton3 = findViewById(R.id.toggleButton7);
        mToggleButton4 = findViewById(R.id.toggleButton8);



        // FOUR SETS OF SMALL SIDE-BY-SIDE ETs
        mLeftEditTextInningsOne = findViewById(R.id.inningEditTextOneLeft);
        mRightEditTextInningsOne = findViewById(R.id.inningsEditTextOneRight);
        mLeftEditTextInningsFive = findViewById(R.id.leftEditTextInningsFive);
        mRightEditTextInningsFive = findViewById(R.id.rightEditTextInningsFive);
        mLeftETSB = findViewById(R.id.leftEditTextSBThree);
        mRightETSB = findViewById(R.id.rightEditTextSBThree);
        mPitchingNotes = findViewById(R.id.PitchingNotesET);
        mBattingNotes = findViewById(R.id.BattingNotesET);
        mLeftSix = findViewById(R.id.leftEditTextInningsSix);
        mRightSix = findViewById(R.id.rightEditTextInningsSix);


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

        if(getCurrentPosition() == 2) {
            mTextViewSix.setTextSize(22);
        } else {
            mTextViewSix.setTextSize(30);
        }

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
            mLeftSix.setVisibility(View.INVISIBLE);
            mRightSix.setVisibility(View.INVISIBLE);

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
            mLeftSix.setVisibility(View.INVISIBLE);
            mRightSix.setVisibility(View.INVISIBLE);

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
            mLeftSix.setVisibility(View.VISIBLE);
            mRightSix.setVisibility(View.VISIBLE);

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
            mLeftSix.setVisibility(View.INVISIBLE);
            mRightSix.setVisibility(View.INVISIBLE);

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
            mLeftSix.setVisibility(View.INVISIBLE);
            mRightSix.setVisibility(View.INVISIBLE);

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
            mLeftSix.setVisibility(View.INVISIBLE);
            mRightSix.setVisibility(View.INVISIBLE);




            mProgressBar.setProgress(100);
        }



    }

    public void firebaseSendAndFinish() {

        mProgressBar.setProgress(100);

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
                    if(check.equals(SaveSharedPreference.getPrefSeasonforgame(NewGameActivity.this))) {
                        currentSeason = "Season" + x;
                    }
                }


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
                gameSend.put("Batting Runs Scored", mEditTextFourTwo.getText().toString());
                gameSend.put("Sac", mEditTextOneTwo.getText().toString());
                gameSend.put("Batting Strikeout", mEditTextFiveTwo.getText().toString());
                gameSend.put("Catcher Interference", mLeftSix.getText().toString());
                gameSend.put("Reached On Error", mRightSix.getText().toString());
                gameSend.put("SB True", mLeftETSB.getText().toString());
                gameSend.put("SB Attempts", mRightETSB.getText().toString());

                gameSend.put("Win", String.valueOf(mToggleButton1.isChecked()));
                gameSend.put("Loss", String.valueOf(mToggleButton2.isChecked()));
                gameSend.put("Save", String.valueOf(mToggleButton3.isChecked()));
                gameSend.put("Blown Save", String.valueOf(mToggleButton4.isChecked()));
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

        Intent finishedIntent = new Intent(NewGameActivity.this, UserHomeScreen.class);
        finish();
        startActivity(finishedIntent);
    }

    private void showErrorDialog(String message) {
            AlertDialog oops = new AlertDialog.Builder(NewGameActivity.this)
                    .setTitle("Oops")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            Toast.makeText(this, "Please fix the error to proceed!", Toast.LENGTH_SHORT).show();
    }
}
