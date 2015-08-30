package pack.wolf.com.pifi;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import pack.wolf.com.pifi.network.VolleyManager;
import pack.wolf.com.pifi.util.SharedPreferenceUtil;

public class PifiApplication extends Application {

    private static PifiApplication application;
    private static Context context;

    public void onCreate(){
        super.onCreate();

        application = this;

        // Assign the context to the Application Scope
        context = getApplicationContext();

        // Instantiate the SharedPreferenceUtil Singleton
        SharedPreferenceUtil.getInstance();

        // Instantiate global VolleyManager Singleton
        int memoryClass = ((ActivityManager) getSystemService (Context.ACTIVITY_SERVICE)).getMemoryClass ();
        int cacheSize = memoryClass * 1024 / 4;
        VolleyManager.initialize(getApplicationContext(), cacheSize);
    }

    public static Context getAppContext() {
        return PifiApplication.context;
    }

    public static PifiApplication getApplication() {
        if (application == null) {
            throw new IllegalStateException("Application not initialized");
        }
        return application;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
