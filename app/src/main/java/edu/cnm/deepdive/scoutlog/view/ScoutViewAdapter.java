package edu.cnm.deepdive.scoutlog.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.cnm.deepdive.scoutlog.R;
import edu.cnm.deepdive.scoutlog.model.entities.Badge;
import edu.cnm.deepdive.scoutlog.model.entities.ScoutWithBadges;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Scout view adapter.
 */
public class ScoutViewAdapter extends RecyclerView.Adapter<ScoutViewAdapter.ViewHolder>{

  private static final String TAG = "ScoutViewAdapter";

  private Context context;
  private ItemClickListener clickListener;
  private ArrayList<ScoutWithBadges> scoutContents;
  private Map<Long, List<Badge>> scoutsBadges;
  private LayoutInflater inflater;

  /**
   * Instantiates a new Scout view adapter.
   *
   * @param scoutContents the scout contents
   * @param context the context
   */
  public ScoutViewAdapter(ArrayList<ScoutWithBadges> scoutContents, Context context) {
    this.scoutContents = scoutContents;
    this.context = context;
    this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
  }

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
    holder.firstName.setText("First name: " + scoutContents.get(position).getScout().getFirstName());
    holder.lastName.setText("Last name: " +scoutContents.get(position).getScout().getLastName());
    holder.rank.setText("Rank : " + scoutContents.get(position).getScout().getRank());
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
    TextView firstName;
    TextView lastName;
    TextView rank;
    RecyclerView badges;

    /**
     * Instantiates a new View holder.
     *
     * @param itemView the item view
     */
    public ViewHolder(View itemView) {
      super(itemView);
      firstName = itemView.findViewById(R.id.li_scout_first_name);
      lastName = itemView.findViewById(R.id.li_scout_last_name);
      rank = itemView.findViewById(R.id.li_scout_rank);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (clickListener != null) {
        AlertDialog.Builder builder = new Builder(context);
        LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.see_badges_layout, null);
  //      RecyclerView badges = dialogView.findViewById(R.id.recycled_badges_for_scout);
        Button addBadges = (Button) dialogView.findViewById(R.id.add_badge);
        Button back = (Button) dialogView.findViewById(R.id.close);
        for (Badge badge : scoutContents.get(getLayoutPosition()).getBadges()) {
          CircleImageView badgeCircle = new CircleImageView(context);
          Picasso.get().load(badge.getImageLink()).into(badgeCircle);
          ((LinearLayout) dialogView.getChildAt(1)).addView(badgeCircle);
        }
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();
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
//  int getItem(int id) {
//    return scoutContents.get(position);
//  }

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
