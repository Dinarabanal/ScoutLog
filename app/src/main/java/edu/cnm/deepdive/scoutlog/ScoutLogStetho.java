package edu.cnm.deepdive.scoutlog;

import android.app.Application;
import com.facebook.stetho.Stetho;

public class ScoutLogStetho extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(getApplicationContext());
  }
}
