package com.developer.jacob.statmaster;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    private static final String PREF_EMAIL= "email";
    private static final String PREF_PASSWORD= "password";
    private static final String PREF_NAME = "name";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_SEASONFORGAME = "seasonForGame";
    private static final String REGISTER_EMAIL = "registerEmail";
    private static final String REGISTER_PASSWORD = "registerPassword";
    private static final String USERHOME_GAMECLICKED = "gameClicked";
    private static final String SEASONNUMFORVIEWGAME = "seasonNumForViewGame";





    private static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    public static void clearSharedPreferences(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_EMAIL);
        editor.remove(PREF_PASSWORD);
        editor.remove(PREF_NAME);
        editor.remove(PREF_USERNAME);
        editor.apply();
    }


    public static String getEmail(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_EMAIL, "");
    }
    public static String getPassword(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_PASSWORD, "");
    }
    public static String getPrefName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_NAME, "");
    }
    public static String getPrefUsername(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USERNAME, "");
    }
    public static String getPrefRegisterEmail(Context ctx){
        return getSharedPreferences(ctx).getString(REGISTER_EMAIL, "");
    }
    public static String getPrefRegisterPassword(Context ctx) {
        return getSharedPreferences(ctx).getString(REGISTER_PASSWORD, "");
    }
    public static String getPrefSeasonforgame(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_SEASONFORGAME, "");
    }
    public static String getUserhomeGameclicked(Context ctx) {
        return getSharedPreferences(ctx).getString(USERHOME_GAMECLICKED, "");
    }








    public static void setEmailAndPassWord(Context ctx, String email, String password){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_EMAIL, email);
        editor.putString(PREF_PASSWORD, password);
        editor.apply();
    }
    public static void setUserNameAndName(Context ctx, String UserName, String Name) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USERNAME, UserName);
        editor.putString(PREF_NAME, Name);
        editor.apply();
    }
    public static void setPrefRegisterEmailAndPassword(Context ctx, String email, String password) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(REGISTER_EMAIL, email);
        editor.putString(REGISTER_PASSWORD, password);
        editor.apply();
    }
    public static void setPrefSeasonforgame(Context ctx, String season) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_SEASONFORGAME, season);
        editor.apply();
    }
    public static void setUserhomeGameclicked(Context ctx, String game) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USERHOME_GAMECLICKED, game);
        editor.apply();
    }

    public static String getSeasonnumforviewgame(Context ctx) {
        return getSharedPreferences(ctx).getString(SEASONNUMFORVIEWGAME, "");
    }

    public static void setSeasonnumforviewgame(Context ctx, String game) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(SEASONNUMFORVIEWGAME, game);
        editor.apply();
    }
}
