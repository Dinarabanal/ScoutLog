package edu.cnm.deepdive.scoutlog.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import edu.cnm.deepdive.scoutlog.R;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity<recyclerView> extends AppCompatActivity {


  private ImageButton ScoutButton;
  private ImageButton BadgeButton;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();

  }

  public void init() {
    ScoutButton = (ImageButton) findViewById(R.id.scout_button);
    ScoutButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            new ScoutFragment()).addToBackStack("scoutlayout").commit();

      }
    });
    BadgeButton = (ImageButton) findViewById(R.id.badge_button);
    BadgeButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            new BadgeFragment()).addToBackStack("badgelayout").commit();
      }
    });
  }
}


