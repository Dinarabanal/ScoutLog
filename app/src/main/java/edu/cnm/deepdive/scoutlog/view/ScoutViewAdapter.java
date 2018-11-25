package edu.cnm.deepdive.scoutlog.view;

import android.content.Context;
import android.opengl.GLException;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cnm.deepdive.scoutlog.R;
import edu.cnm.deepdive.scoutlog.model.db.ScoutLogDatabase;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;
import java.util.ArrayList;

public class ScoutViewAdapter extends RecyclerView.Adapter<ScoutViewAdapter.ViewHolder>{

  private static final String TAG = "ScoutViewAdapter";

  public ScoutViewAdapter(ArrayList<String> scoutContents, Context context) {
    this.scoutContents = scoutContents;
    this.context = context;
  }

  ArrayList<String> scoutContents = new ArrayList<>();
  Context context;

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scoutlist_item, parent, false);
    ViewHolder holder = new ViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Log.d(TAG, "onBindViewHolder: started");
    holder.scouts.setText(scoutContents.get(position));
  }

  @Override
  public int getItemCount() {
    return scoutContents.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{
    TextView scouts;
    LinearLayout linearLayout;
    public ViewHolder(View itemView) {
      super(itemView);
      scouts = itemView.findViewById(R.id.scouts);
      linearLayout = itemView.findViewById(R.id.recycled_scouts);
    }
  }
  private class GetAScout extends AsyncTask<Long, Void, Scout>{

    @Override
    protected void onPostExecute(Scout scout) {

    }

    @Override
    protected Scout doInBackground(Long... longs) {
      return ScoutLogDatabase.getInstance(context).getScoutDao().select(longs[0]);
    }
  }
}
