package com.developer.jacob.statmaster;

public class BaseballGame {


    private int mDay;
    private int mMonth;
    private int mYear;
    private String mOpponent;
    private int mUserTeamScore;
    private int mOpponentScore;
    private String mLocation;


    private double mInningsPlayed;
    private int mPlateAppearances;
    private int mBattingHits;
    private int mSingles;
    private int mDoubles;
    private int mTriples;
    private int mBattingHomeRuns;
    private int mBattingWalks;
    private int mBattingHBPs;
    private int mRBIs;
    private int mRunsScored;
    private int mBattingStrikeOuts;
    private int mCatcherInterferences;
    private String mBattingNotes;


    private boolean mWin;
    private boolean mLoss;
    private boolean mSave;
    private boolean mBlownSave;
    private double mInningsPitched;
    private int mOptionalPitches;
    private int mPitchingEarnedRuns;
    private int mPitchingRunsTotal;
    private int mPitchingStrikeOuts;
    private int mPitchingWalks;
    private int mWildPitches;
    private int mPitchingHBPs;
    private int mPitchingHits;
    private int mPitchingHomeRuns;
    private String mPitchingNotes;


    public BaseballGame() {
        mDay = 0;
        mMonth = 0;
        mYear = 0;
        mOpponent = "";
        mUserTeamScore = 0;
        mOpponentScore = 0;
        mLocation = "";

        mInningsPlayed = 0.0;
        mPlateAppearances = 0;
        mBattingHits = 0;
        mSingles = 0;
        mDoubles = 0;
        mTriples = 0;
        mBattingHomeRuns = 0;
        mBattingWalks = 0;
        mBattingHBPs = 0;
        mRBIs = 0;
        mRunsScored = 0;
        mBattingStrikeOuts = 0;
        mCatcherInterferences = 0;
        mBattingNotes = "";

        mWin = false;
        mLoss = false;
        mSave = false;
        mBlownSave = false;
        mInningsPitched = 0.0;
        mOptionalPitches = 0;
        mPitchingEarnedRuns = 0;
        mPitchingRunsTotal = 0;
        mPitchingStrikeOuts = 0;
        mPitchingWalks = 0;
        mWildPitches = 0;
        mPitchingHBPs = 0;
        mPitchingHits = 0;
        mPitchingHomeRuns = 0;
        mPitchingNotes = "";
    }

    public BaseballGame(int day, int month, int year, String opponent, int userTeamScore, int opponentScore, String location) {
        mDay = day;
        mMonth = month;
        mYear = year;
        mOpponent = opponent;
        mUserTeamScore = userTeamScore;
        mOpponentScore = opponentScore;
        mLocation = location;

        mInningsPlayed = 0.0;
        mPlateAppearances = 0;
        mBattingHits = 0;
        mSingles = 0;
        mDoubles = 0;
        mTriples = 0;
        mBattingHomeRuns = 0;
        mBattingWalks = 0;
        mBattingHBPs = 0;
        mRBIs = 0;
        mRunsScored = 0;
        mBattingStrikeOuts = 0;
        mCatcherInterferences = 0;
        mBattingNotes = "";

        mWin = false;
        mLoss = false;
        mSave = false;
        mBlownSave = false;
        mInningsPitched = 0.0;
        mOptionalPitches = 0;
        mPitchingEarnedRuns = 0;
        mPitchingRunsTotal = 0;
        mPitchingStrikeOuts = 0;
        mPitchingWalks = 0;
        mWildPitches = 0;
        mPitchingHBPs = 0;
        mPitchingHits = 0;
        mPitchingHomeRuns = 0;
        mPitchingNotes = "";
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int day) {
        mDay = day;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public String getOpponent() {
        return mOpponent;
    }

    public void setOpponent(String opponent) {
        mOpponent = opponent;
    }

    public int getUserTeamScore() {
        return mUserTeamScore;
    }

    public void setUserTeamScore(int userTeamScore) {
        mUserTeamScore = userTeamScore;
    }

    public int getOpponentScore() {
        return mOpponentScore;
    }

    public void setOpponentScore(int opponentScore) {
        mOpponentScore = opponentScore;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public double getInningsPlayed() {
        return mInningsPlayed;
    }

    public void setInningsPlayed(double inningsPlayed) {
        mInningsPlayed = inningsPlayed;
    }

    public int getPlateAppearances() {
        return mPlateAppearances;
    }

    public void setPlateAppearances(int plateAppearances) {
        mPlateAppearances = plateAppearances;
    }

    public int getBattingHits() {
        return mBattingHits;
    }

    public void setBattingHits(int battingHits) {
        mBattingHits = battingHits;
    }

    public int getSingles() {
        return mSingles;
    }

    public void setSingles(int singles) {
        mSingles = singles;
    }

    public int getDoubles() {
        return mDoubles;
    }

    public void setDoubles(int doubles) {
        mDoubles = doubles;
    }

    public int getTriples() {
        return mTriples;
    }

    public void setTriples(int triples) {
        mTriples = triples;
    }

    public int getBattingHomeRuns() {
        return mBattingHomeRuns;
    }

    public void setBattingHomeRuns(int battingHomeRuns) {
        mBattingHomeRuns = battingHomeRuns;
    }

    public int getBattingWalks() {
        return mBattingWalks;
    }

    public void setBattingWalks(int battingWalks) {
        mBattingWalks = battingWalks;
    }

    public int getBattingHBPs() {
        return mBattingHBPs;
    }

    public void setBattingHBPs(int battingHBPs) {
        mBattingHBPs = battingHBPs;
    }

    public int getRBIs() {
        return mRBIs;
    }

    public void setRBIs(int RBIs) {
        mRBIs = RBIs;
    }

    public int getRunsScored() {
        return mRunsScored;
    }

    public void setRunsScored(int runsScored) {
        mRunsScored = runsScored;
    }

    public int getBattingStrikeOuts() {
        return mBattingStrikeOuts;
    }

    public void setBattingStrikeOuts(int battingStrikeOuts) {
        mBattingStrikeOuts = battingStrikeOuts;
    }

    public int getCatcherInterferences() {
        return mCatcherInterferences;
    }

    public void setCatcherInterferences(int catcherInterferences) {
        mCatcherInterferences = catcherInterferences;
    }

    public String getBattingNotes() {
        return mBattingNotes;
    }

    public void setBattingNotes(String battingNotes) {
        mBattingNotes = battingNotes;
    }

    public boolean isWin() {
        return mWin;
    }

    public void setWin(boolean win) {
        mWin = win;
    }

    public boolean isLoss() {
        return mLoss;
    }

    public void setLoss(boolean loss) {
        mLoss = loss;
    }

    public boolean isSave() {
        return mSave;
    }

    public void setSave(boolean save) {
        mSave = save;
    }

    public boolean isBlownSave() {
        return mBlownSave;
    }

    public void setBlownSave(boolean blownSave) {
        mBlownSave = blownSave;
    }

    public double getInningsPitched() {
        return mInningsPitched;
    }

    public void setInningsPitched(double inningsPitched) {
        mInningsPitched = inningsPitched;
    }

    public int getOptionalPitches() {
        return mOptionalPitches;
    }

    public void setOptionalPitches(int optionalPitches) {
        mOptionalPitches = optionalPitches;
    }

    public int getPitchingEarnedRuns() {
        return mPitchingEarnedRuns;
    }

    public void setPitchingEarnedRuns(int pitchingEarnedRuns) {
        mPitchingEarnedRuns = pitchingEarnedRuns;
    }

    public int getPitchingRunsTotal() {
        return mPitchingRunsTotal;
    }

    public void setPitchingRunsTotal(int pitchingRunsTotal) {
        mPitchingRunsTotal = pitchingRunsTotal;
    }

    public int getPitchingStrikeOuts() {
        return mPitchingStrikeOuts;
    }

    public void setPitchingStrikeOuts(int pitchingStrikeOuts) {
        mPitchingStrikeOuts = pitchingStrikeOuts;
    }

    public int getPitchingWalks() {
        return mPitchingWalks;
    }

    public void setPitchingWalks(int pitchingWalks) {
        mPitchingWalks = pitchingWalks;
    }

    public int getWildPitches() {
        return mWildPitches;
    }

    public void setWildPitches(int wildPitches) {
        mWildPitches = wildPitches;
    }

    public int getPitchingHBPs() {
        return mPitchingHBPs;
    }

    public void setPitchingHBPs(int pitchingHBPs) {
        mPitchingHBPs = pitchingHBPs;
    }

    public int getPitchingHits() {
        return mPitchingHits;
    }

    public void setPitchingHits(int pitchingHits) {
        mPitchingHits = pitchingHits;
    }

    public int getPitchingHomeRuns() {
        return mPitchingHomeRuns;
    }

    public void setPitchingHomeRuns(int pitchingHomeRuns) {
        mPitchingHomeRuns = pitchingHomeRuns;
    }

    public String getPitchingNotes() {
        return mPitchingNotes;
    }

    public void setPitchingNotes(String pitchingNotes) {
        mPitchingNotes = pitchingNotes;
    }








}
