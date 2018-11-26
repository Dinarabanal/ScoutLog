package edu.cnm.deepdive.scoutlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import edu.cnm.deepdive.scoutlog.view.MainActivity;

/**
 * The type Sign in activity.
 */
public class SignInActivity extends AppCompatActivity {
  private static final int REQUEST_CODE = 1010;
  /**
   * The Sign in.
   * add the
   */
  SignInButton signIn;
  static GoogleSignInAccount account;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);
    signIn = findViewById(R.id.sign_in);
    signIn.setOnClickListener((view) -> signin());

  }

  @Override
  protected void onStart() {
    super.onStart();
    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    if(account != null){
      ScoutLogStetho.getInstance().setAccount(account);
      switchToMain();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if(requestCode== REQUEST_CODE){
      try {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
         account = task.getResult(ApiException.class);
        ScoutLogStetho.getInstance().setAccount(account);
      } catch (ApiException e) {
        //e.printStackTrace();
        Toast.makeText(this,"There was a error logging in", Toast.LENGTH_LONG).show();
      }
      switchToMain();
    }
  }


  private void signin(){
    Intent intent = ScoutLogStetho.getInstance().getClient().getSignInIntent();
    startActivityForResult(intent, REQUEST_CODE);
  }

  private void switchToMain(){
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }
}
