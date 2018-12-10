package edu.cnm.deepdive.scoutlog;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import edu.cnm.deepdive.scoutlog.model.db.ScoutLogDatabase;
import edu.cnm.deepdive.scoutlog.view.MainFragment;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
    String greeting = "Hello, " + ScoutLogApplication.getInstance().getAccount().getDisplayName()+"!";
    Toast.makeText(this, greeting, Toast.LENGTH_SHORT).show();
    new ForcePopulateTask().execute(this);
  }


  private void switchFragment(Fragment fragment, boolean useStack, String variant) {
    FragmentManager manager = getSupportFragmentManager();
    String tag = fragment.getClass().getSimpleName() + ((variant != null) ? variant : "");
    if (manager.findFragmentByTag(tag) != null) {
      manager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.replace(R.id.fragment_container,fragment, tag);
    if (useStack) {
      transaction.addToBackStack(tag);
    }
    transaction.commit();
  }

  private static class ForcePopulateTask extends AsyncTask<Context, Void, Void> {


    @Override
    protected Void doInBackground(Context... contexts) {
      ScoutLogDatabase.getInstance(contexts[0]).getScoutDao().select(0);
      return null;
    }

  }

}


