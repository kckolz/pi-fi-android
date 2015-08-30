package pack.wolf.com.pifi.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import pack.wolf.com.pifi.PifiApplication;
import pack.wolf.com.pifi.model.AccessToken;
import pack.wolf.com.pifi.model.User;

/**
 * Created by ryanmoore on 2/24/15.
 */
public class SharedPreferenceUtil {

    private static final String ACCESS_TOKEN = "accessToken";
    private static final String LOGGED_IN = "loggedIn";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferenceUtil instance = null;

    protected SharedPreferenceUtil() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PifiApplication.getAppContext());
    }

    public static SharedPreferenceUtil getInstance() {
        if(instance == null) {
            instance = new SharedPreferenceUtil();
        }

        return instance;
    }

    public static boolean isLoggedIn() {
        return sharedPreferences.getBoolean(LOGGED_IN, false);
    }

    public static void setLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putBoolean(LOGGED_IN, loggedIn);
        editor.commit();
    }

    public static AccessToken getAccessToken() {

        Gson gson = new Gson ();
        String json = sharedPreferences.getString (ACCESS_TOKEN, null);
        return gson.fromJson(json, AccessToken.class);
    }

    public static User getUser() {

        Gson gson = new Gson ();
        String json = sharedPreferences.getString ("user", null);
        return gson.fromJson(json, User.class);
    }

    public static void saveAccessToken(AccessToken accessToken) {

        Gson gson = new Gson ();
        String json = gson.toJson(accessToken);
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putString(ACCESS_TOKEN, json);
        editor.commit();
    }

    public static void saveUser(User user) {
        Gson gson = new Gson ();
        String json = gson.toJson(user);
        SharedPreferences.Editor editor = sharedPreferences.edit ();
        editor.putString("user", json);
        editor.commit();
    }
}
