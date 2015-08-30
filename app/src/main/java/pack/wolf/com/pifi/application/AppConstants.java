package pack.wolf.com.pifi.application;

public class AppConstants {

    //env stuff

    //logger tag
    public static final String LOG_TAG = "Pifi";

    public static final String METHOD_TOKENS = "login/";
    public static final String METHOD_USER = "user/";
    public static final String METHOD_TRACK = "spotify/getTrack/";
    public static final String METHOD_SEARCH = "spotify/search/";

    public static final int REQUEST_TIMEOUT = 30000;

    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static final String REQUEST_PARAM_REFRESH = "refresh_token";
    public static final String REQUEST_PARAM_GRANT = "grant_type";
    public static final String REQUEST_PARAM_USERNAME= "username";
    public static final String REQUEST_PARAM_PASSWORD= "password";

    public static final String REQUEST_VALUE_PASSWORD= "password";
    public static final String REQUEST_VALUE_REFRESH = "refresh_token";

    public static final String BASIC_PREFIX = "Basic ";
    public static final String AUTHORIZATION = "Authorization";

    //env specific stuff
    public static final String BASIC_USER = "kckolz";
    public static final String BASIC_SECRET = "password";
    public static final String SERVER_PATH = "http://52.20.116.83:1337/api/";

    //fragment stuff
    public static final String CLASS_PREFIX = "pack.wolf.com.pifi.fragment.";
    public static final String FRAGMENT_BASE = "BaseFragment";
    public static final String FRAGMENT_SIGNUP = "SignupFragment";
    public static final String FRAGMENT_SIGNIN = "SigninFragment";
    public static final String FRAGMENT_SEARCH = "SearchFragment";
    public static final String FRAGMENT_TRACK = "TrackFragment";
    public static final String FRAGMENT_SETTINGS = "SettingsFragment";
    public static final String FRAGMENT_MAIN = "MainFragment";


}