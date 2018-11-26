package edu.cnm.deepdive.scoutlog;


import android.app.Application;
import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class ScoutLogStetho extends Application {
  private static ScoutLogStetho instance = null;

  private GoogleSignInClient client;
  private GoogleSignInAccount account;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    Stetho.initializeWithDefaults(this);
    GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestId()
        // Include requestIdToken if we're using Google Sign-in for authenticating on a back-end server.
        .build();
    client = GoogleSignIn.getClient(this, options);
  }

  public static ScoutLogStetho getInstance() {
    return instance;
  }

  public GoogleSignInClient getClient() {
    return client;
  }

  public void setClient(GoogleSignInClient client) {
    this.client = client;
  }

  public GoogleSignInAccount getAccount() {
    return account;
  }

  public void setAccount(GoogleSignInAccount account) {
    this.account = account;
  }
}
