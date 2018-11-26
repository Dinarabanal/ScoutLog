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
import android.widget.Toast;
import edu.cnm.deepdive.scoutlog.R;
import edu.cnm.deepdive.scoutlog.model.db.ScoutLogDatabase;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;
import edu.cnm.deepdive.scoutlog.view.BadgeViewAdapter.ItemClickListener;
import java.util.ArrayList;

/**
 * The type Scout view adapter.
 */
public class ScoutViewAdapter extends RecyclerView.Adapter<ScoutViewAdapter.ViewHolder>{

  private static final String TAG = "ScoutViewAdapter";

  /**
   * Instantiates a new Scout view adapter.
   *
   * @param scoutContents the scout contents
   * @param context the context
   */
  public ScoutViewAdapter(ArrayList<String> scoutContents, Context context) {
    this.scoutContents = scoutContents;
    this.context = context;
  }


  private  ItemClickListener clickListener;
  private ArrayList<String> scoutContents = new ArrayList<>();
  private Context context;




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

  /**
   * The type View holder.
   */
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /**
     * The Scouts.
     */
    TextView scouts;
    /**
     * The Linear layout.
     */
    LinearLayout linearLayout;

    /**
     * Instantiates a new View holder.
     *
     * @param itemView the item view
     */
    public ViewHolder(View itemView) {
      super(itemView);
      scouts = itemView.findViewById(R.id.scouts);
      linearLayout = itemView.findViewById(R.id.recycled_scouts);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (clickListener != null) {
        clickListener.onItemClick(view, getAdapterPosition());
      }
    }
  }

  /**
   * Gets item.
   *
   * @param id the id
   * @return the item
   */
// convenience method for getting data at click position
  String getItem(int id) {
    return scoutContents.get(id);
  }

  /**
   * Sets click listener.
   *
   * @param itemClickListener the item click listener
   */
// allows clicks events to be caught
  void setClickListener(ItemClickListener itemClickListener) {
    this.clickListener = itemClickListener;
  }

  /**
   * The interface Item click listener.
   */
// parent activity will implement this method to respond to click events
  public interface ItemClickListener {

    /**
     * On item click.
     *
     * @param view the view
     * @param position the position
     */
    void onItemClick(View view, int position);
  }
  

}
