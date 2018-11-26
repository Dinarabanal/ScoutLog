package edu.cnm.deepdive.scoutlog.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.cnm.deepdive.scoutlog.R;


public class MainFragment extends Fragment{

  private CircleImageView scoutButton;
  private CircleImageView badgeButton;
  private View view;
  private String apiKey;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_main, container, false);
    init();

    return view;
  }

  private void init() {
    scoutButton = view.findViewById(R.id.scout_button);
    scoutButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        switchFragment(new ScoutFragment(), true,"");
    }
    });
    badgeButton = view.findViewById(R.id.badge_button);
    badgeButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        switchFragment(new BadgeFragment(),true,"");
      }
    });
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
