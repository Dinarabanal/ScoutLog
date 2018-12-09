package edu.cnm.deepdive.scoutlog.view;

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
import edu.cnm.deepdive.scoutlog.R;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;
import edu.cnm.deepdive.scoutlog.model.entities.ScoutWithBadges;
import java.util.ArrayList;


/**
 * The type Scout fragment.
 */
public class ScoutFragment extends Fragment implements ScoutViewAdapter.ItemClickListener {

  private View view;
  private ScoutViewAdapter adapter;
  private static final String TAG = "ScoutFragment";
  RecyclerView recyclerView;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_scouts, container, false);
    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScoutFragment());
    FloatingActionButton fab = view.findViewById(R.id.add_scout);
    fab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        switchFragment(new AddScout(), true, "");
      }
    });
    initRecycler(view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  private void initRecycler(View view) {
    recyclerView = view.findViewById(R.id.recycled_scouts);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    adapter = new ScoutViewAdapter((ArrayList<ScoutWithBadges>) MainActivity.scouts, getContext());
    recyclerView.setAdapter(adapter);
    adapter.setClickListener(this);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onItemClick(View view, int position) {
    Bundle bundle = new Bundle();
    String scoutName = MainActivity.scouts.get(position).getScout().getFirstName();
    bundle.putString("scout_name", scoutName);
    bundle.putLong("scout_id", MainActivity.scouts.get(position).getScout().getId());
    BadgeFragment badgeFragment = new BadgeFragment();
    badgeFragment.setArguments(bundle);
    switchFragment(badgeFragment, true, "");
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
    ((MainActivity) getActivity()).updateData();
  }


}