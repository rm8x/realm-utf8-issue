package com.sample.app;

import android.app.Application;
import android.util.Log;

public class ExampleJavaApplication extends Application {

    public static final String EXAMPLE_TAG = "RealmUTF8";

    public static ExampleJavaApplication getInstance() { return exampleJavaApplication; }

    private static ExampleJavaApplication exampleJavaApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        exampleJavaApplication = this;
        Log.v(EXAMPLE_TAG, "ExampleJavaApplication onCreate");
    }
}
