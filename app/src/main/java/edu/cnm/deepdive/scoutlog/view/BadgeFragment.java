package edu.cnm.deepdive.scoutlog.view;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.cnm.deepdive.scoutlog.R;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


public class BadgeFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {

  RecyclerViewAdapter adapter;

  private static final String TAG = "BadgeFragment";


  ArrayList<String> images = new ArrayList<String>();

  CircleImageView test;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_badges, container, false);

    //TODO Set up log to initialize images;

    test = view.findViewById(R.id.badges);

    RecyclerView recyclerView = view.findViewById(R.id.recycled_badges);
    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
    adapter = new RecyclerViewAdapter(getContext(),images);
    adapter.setClickListener(this);
    recyclerView.setAdapter(adapter);

    new GetImages().execute();

    return view;



  }


  @Override
  public void onItemClick(View view, int position) {
    Toast.makeText(getContext(),String.valueOf(position), Toast.LENGTH_SHORT).show();

  }

  private class GetImages extends AsyncTask<Void, Void, Void>{

    @Override
    protected Void doInBackground(Void... voids) {
      InputStream inputStream = getContext().getResources().openRawResource(R.raw.badge_links);
      ArrayList<String> links = new ArrayList<>();
      int i = 0;
      try {
        CSVParser csvParser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT);
        for (CSVRecord csvRecord: csvParser.getRecords()){
          links.add(csvRecord.get(0));
          images.add(csvRecord.get(0));
          //Picasso.get().load(csvRecord.get(0)).into(images.get(i));
          i++;
        }
      }catch (Exception e) {

      }

      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      adapter.notifyDataSetChanged();
    }
  }
}

