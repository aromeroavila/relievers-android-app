package arao.relieversapp.app;

import android.app.Application;
import android.content.Context;

public class RelieversApplication extends Application {

    private static RelieversApplication instance;

    @SuppressWarnings("unused")
    public RelieversApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public static RelieversApplication getInstance() {
        return instance;
    }
}
