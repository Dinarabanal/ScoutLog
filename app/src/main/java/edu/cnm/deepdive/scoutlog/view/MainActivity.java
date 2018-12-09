package edu.cnm.deepdive.scoutlog.view;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import edu.cnm.deepdive.scoutlog.R;
import edu.cnm.deepdive.scoutlog.ScoutLogApplication;
import edu.cnm.deepdive.scoutlog.model.db.ScoutLogDatabase;
import edu.cnm.deepdive.scoutlog.model.entities.Badge;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;
import edu.cnm.deepdive.scoutlog.model.entities.ScoutBadgeJoin;
import edu.cnm.deepdive.scoutlog.model.entities.ScoutWithBadges;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

  public static List<ScoutWithBadges> scouts;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
    String greeting = "Hello, " + ScoutLogApplication.getInstance().getAccount().getDisplayName()+"!";
    Toast.makeText(this, greeting, Toast.LENGTH_SHORT).show();
    new GetAllScouts().execute();
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

  public void updateData() {
    new GetAllScouts().execute();
  }

  private class GetAllScouts extends AsyncTask<Void, Void, List<ScoutWithBadges>> {

    @Override
    protected void onPostExecute(List<ScoutWithBadges> scouts) {
      MainActivity.scouts = new ArrayList<>(scouts);
    }

    @Override
    protected List<ScoutWithBadges> doInBackground(Void... voids) {
      return ScoutLogDatabase.getInstance(MainActivity.this).getScoutBadgeDao().getAllScoutsAndBadges();
    }
  }

}


