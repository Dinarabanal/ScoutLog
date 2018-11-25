package edu.cnm.deepdive.scoutlog.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import edu.cnm.deepdive.scoutlog.R;
import edu.cnm.deepdive.scoutlog.model.db.ScoutLogDatabase;
import edu.cnm.deepdive.scoutlog.model.entities.Badge;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;
import java.util.ArrayList;
import java.util.List;


public class ScoutFragment extends Fragment implements ScoutViewAdapter.ItemClickListener{

  List<Long> ids;
  List<Scout> scouts;
  private ArrayList<String> scoutsInfo = new ArrayList<>();
  View view;
  private static final String TAG = "ScoutFragment";

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_scouts, container, false);
    new GetAllScouts().execute();

    FloatingActionButton fab = view.findViewById(R.id.add_scout);
    fab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        switchFragment(new AddScout(), true, "");
      }
    });


    return view;
  }

  private void initRecycler(View view){
    RecyclerView recyclerView = view.findViewById(R.id.recycled_scouts);
    ScoutViewAdapter adapter = new ScoutViewAdapter(scoutsInfo, getContext());
    recyclerView.setAdapter(adapter);
    adapter.setClickListener(this);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

  }

  @Override
  public void onItemClick(View view, int position) {
    Bundle bundle = new Bundle();
    String scoutName = scouts.get(position).getFirstName();
    bundle.putString("scout_name", scoutName);
    BadgeFragment badgeFragment = new BadgeFragment();
    badgeFragment.setArguments(bundle);
    switchFragment(badgeFragment,true,"");
  }

  private class GetAllScouts extends AsyncTask<Void, Void, List<Scout>>{

    @Override
    protected void onPostExecute(List<Scout> scouts) {

      ScoutFragment.this.scouts = scouts;
      for(Scout index : scouts){
        scoutsInfo.add("First Name: " + index.getFirstName() + "\n" + "Last Name: " + index.getLastName() + "\n" + "Rank :" + index.getRank());
      }
      initRecycler(view);
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