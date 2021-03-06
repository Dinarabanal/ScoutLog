package edu.cnm.deepdive.scoutlog.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import edu.cnm.deepdive.scoutlog.MainActivity;
import edu.cnm.deepdive.scoutlog.R;
import edu.cnm.deepdive.scoutlog.model.db.ScoutLogDatabase;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;
import edu.cnm.deepdive.scoutlog.model.entities.ScoutWithBadges;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Scout fragment.
 */
public class ScoutFragment extends Fragment  {

  private static final String TAG = "ScoutFragment";

  private View view;
  private ScoutViewAdapter adapter;
  private RecyclerView recyclerView;
  private List<ScoutWithBadges> scouts;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_scouts, container, false);
    recyclerView = view.findViewById(R.id.recycled_scouts);
    FloatingActionButton fab = view.findViewById(R.id.add_scout);
    fab.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
       switchFragment(new AddScout(), true, "");
      }
    });
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    new GetAllScouts().execute();
  }

  private void initRecycler() {
    adapter = new ScoutViewAdapter(scouts, getContext(),getFragmentManager());
    recyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
  }


  /**
   * Switch fragment.
   *
   * @param fragment the fragment
   * @param useStack the use stack
   * @param variant the variant switches between fragments with goiing bcak to the main activity
   */
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
  //  ((MainActivity) getActivity()).updateData();
  }


  private class GetAllScouts extends AsyncTask<Void, Void, List<ScoutWithBadges>> {

    @Override
    protected void onPostExecute(List<ScoutWithBadges> scouts) {
      ScoutFragment.this.scouts = new ArrayList<>(scouts);
      initRecycler();
    }

    @Override
    protected List<ScoutWithBadges> doInBackground(Void... voids) {
      return ScoutLogDatabase.getInstance(getContext()).getScoutBadgeDao().getAllScoutsAndBadges();
    }

  }


}