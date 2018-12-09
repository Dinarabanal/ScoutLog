package edu.cnm.deepdive.scoutlog.view;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.cnm.deepdive.scoutlog.R;
import edu.cnm.deepdive.scoutlog.model.db.ScoutLogDatabase;
import edu.cnm.deepdive.scoutlog.model.entities.Badge;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;
import edu.cnm.deepdive.scoutlog.model.entities.ScoutBadgeJoin;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


public class BadgeFragment extends Fragment implements BadgeViewAdapter.ItemClickListener{

  BadgeViewAdapter adapter;

  private static final String TAG = "BadgeFragment";

  ArrayList<String> images = new ArrayList<>();
  Button search;
  Badge badge = new Badge();
  EditText searchText;
  String badgeById = "";
  long scoutId;
  Scout scout;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      Bundle bundle = getArguments();
      scoutId = bundle.getLong("scout_id");
      Toast.makeText(getContext(), "Add badges to scout: " + bundle.getString("scout_name"),
          Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_badges, container, false);


    RecyclerView recyclerView = view.findViewById(R.id.recycled_badges);
    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
    adapter = new BadgeViewAdapter(getContext(),images);
    adapter.setClickListener(this);
    recyclerView.setAdapter(adapter);
    new GetImages().execute();


    searchText = view.findViewById(R.id.badge_search);
    search = view.findViewById(R.id.search_button);
    search.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        new QueryBadges().execute("%"+searchText.getText().toString()+"%");
      }
    });
    if (scoutId != 0) {
      new GetScout().execute(scoutId);
    }
    return view;
  }


  @Override
  public void onItemClick(View view, int position) {
    long x = position +1;
    new QueryById().execute(x);
  }

  private class GetImages extends AsyncTask<Void, Void, Void>{

    @Override
    protected Void doInBackground(Void... voids) {
      InputStream inputStream = getContext().getResources().openRawResource(R.raw.badge_links);
      InputStream inputStreamNames = getContext().getResources().openRawResource(R.raw.badge_names);

      ArrayList<String> links = new ArrayList<>();
      ArrayList<String> names = new ArrayList<>();

      int i = 0;
      try {
        CSVParser csvParserNames = new CSVParser((new InputStreamReader(inputStreamNames)), CSVFormat.DEFAULT);
        CSVParser csvParser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT);

        for(CSVRecord csvRecord: csvParserNames.getRecords()){
         names.add(csvRecord.get(0));
        }
        for (CSVRecord csvRecord: csvParser.getRecords()){
          links.add(csvRecord.get(0));
          images.add(csvRecord.get(0));
          i++;
        }
      }catch (Exception e) {

      }
      for(int x = 0;x<names.size();x++){
        badge.setImageLink(links.get(x));
        badge.setBadgeName(names.get(x));
        new InsertTask().execute(badge);
        badge = new Badge();
      }

      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      adapter.notifyDataSetChanged();
    }
  }
  private class InsertTask extends AsyncTask <Badge, Void, Long>{

    @Override
    protected Long doInBackground(Badge... badges) {

      return ScoutLogDatabase.getInstance(getContext()).getBadgeDao().insert(badges[0]);
    }
  }
  private class QueryBadges extends AsyncTask<String, Void, Badge>{


    @Override
    protected void onPostExecute(Badge badge) {
      if (badge == null) {
        Toast.makeText(getContext(), "No badge found!", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(getContext(), badge.getBadgeName(), Toast.LENGTH_SHORT).show();
      }
    }
    @Override
    protected Badge doInBackground(String... strings) {

      return ScoutLogDatabase.getInstance(getContext()).getBadgeDao().select(strings[0]);

    }
  }

  private class QueryById extends AsyncTask<Long, Void, Badge>{

    @Override
    protected void onPostExecute(Badge badge) {
      if (BadgeFragment.this.scout != null) {
        ScoutBadgeJoin scoutBadgeJoin = new ScoutBadgeJoin();
        scoutBadgeJoin.setBadgeId(badge.getId());
        scoutBadgeJoin.setScoutId(scout.getId());
        String scoutBadgeToast = scout.getLastName() + " gets a " + badge.getBadgeName();
        Toast.makeText(getContext(), scoutBadgeToast, Toast.LENGTH_SHORT).show();
        new InsertScoutBadgeJoin().execute(scoutBadgeJoin);
      } else {
        badgeById = badge.getBadgeName();
        Toast.makeText(getContext(), badgeById, Toast.LENGTH_SHORT).show();
      }
    }
    @Override
    protected Badge doInBackground(Long... longs) {
      return ScoutLogDatabase.getInstance(getContext()).getBadgeDao().selectById(longs[0]);
    }
  }

  private class GetScout extends AsyncTask<Long, Void, Scout> {

    @Override
    protected Scout doInBackground(Long... longs) {
      return ScoutLogDatabase.getInstance(getContext()).getScoutDao().select(longs[0]);
    }

    @Override
    protected void onPostExecute(Scout scout) {
      BadgeFragment.this.scout = scout;
    }
  }

  private class InsertScoutBadgeJoin extends AsyncTask<ScoutBadgeJoin, Void, Void> {

    @Override
    protected Void doInBackground(ScoutBadgeJoin... scoutBadgeJoins) {
      ScoutLogDatabase.getInstance(getContext()).getScoutBadgeJoinDao().insert(scoutBadgeJoins[0]);
      return null;
    }
  }

}

