package edu.cnm.deepdive.scoutlog.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.cnm.deepdive.scoutlog.R;
import edu.cnm.deepdive.scoutlog.model.db.ScoutLogDatabase;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;
import java.util.ArrayList;
import java.util.List;


public class ScoutFragment extends Fragment {
  TextView scoutList;

  private static final String TAG = "ScoutFragment";

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new GetAllScouts().execute();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_scouts, container, false);

    scoutList  = view.findViewById(R.id.scouts_list);
    FloatingActionButton fab = view.findViewById(R.id.add_scout);
    fab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        switchFragment(new AddScout(),true,"");
      }
    });


    return view;
  }

private class GetAllScouts extends AsyncTask<Void,Void, List<Scout>>{

  @Override
  protected void onPostExecute(List<Scout> scouts) {
    for(Scout scouter :  scouts){
      scoutList.append("FirstName: " + scouter.getFirstName() + "\n");
      scoutList.append("LastName: " + scouter.getLastName() + "\n");
      scoutList.append("Rank:" + scouter.getRank() + "\n" +"\n");

    }
  }

  @Override
  protected List<Scout> doInBackground(Void... voids) {
    return ScoutLogDatabase.getInstance(getContext()).getScoutDao().getAll();
  }
}
  public void switchFragment(Fragment fragment, boolean useStack, String variant) {
    FragmentManager manager = getFragmentManager();
    String tag = fragment.getClass().getSimpleName() + ((variant != null) ? variant : "");
    if (manager.findFragmentByTag(tag) != null) {
      manager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.replace(R.id.fragment_container, fragment, tag);
    if (useStack) {
      transaction.addToBackStack(tag);
    }
    transaction.commit();
  }
}
