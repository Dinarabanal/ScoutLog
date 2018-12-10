package edu.cnm.deepdive.scoutlog.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.AsyncTask;
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
import edu.cnm.deepdive.scoutlog.model.db.ScoutLogDatabase;
import edu.cnm.deepdive.scoutlog.model.entities.Badge;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;
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
  private List<Scout> scouts;
  private Map<Long, List<Badge>> scoutsBadges;
  private LayoutInflater inflater;

  /**
   * Instantiates a new Scout view adapter.
   *
   * @param scouts the scout contents
   * @param context the context
   */
  public ScoutViewAdapter(List<Scout> scouts, Context context) {

    this.scouts = scouts;
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
    holder.bind(scouts.get(position));
  }

  @Override
  public int getItemCount() {
    return scouts.size();
  }

  private void showBadges(List<Badge> badges){
    AlertDialog.Builder builder = new Builder(context);
    LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.see_badges_layout, null);
    //      RecyclerView badges = dialogView.findViewById(R.id.recycled_badges_for_scout);
    Button addBadges = (Button) dialogView.findViewById(R.id.add_badge);
    Button back = (Button) dialogView.findViewById(R.id.close);
    for (Badge badge : badges) {
      CircleImageView badgeCircle = new CircleImageView(context);
      Picasso.get().load(badge.getImageLink()).into(badgeCircle);
      ((LinearLayout) dialogView.getChildAt(1)).addView(badgeCircle);
    }
    builder.setView(dialogView);
    final AlertDialog dialog = builder.create();
    dialog.show();
  }

  /**
   * The type View holder.
   */
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /**
     * The Scouts.
     */
    Scout scout;
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
      new QueryBadgesForScout(context).execute(scout.getId());
    }

    public void bind(Scout scout) {
      this.scout = scout;
      firstName.setText("First name: " + scout.getFirstName());
      lastName.setText("Last name: " +scout.getLastName());
      rank.setText("Rank : " + scout.getRank());

    }
  }

  private class QueryBadgesForScout extends AsyncTask<Long,Void,List<Badge>> {

    private Context context;

    public QueryBadgesForScout(Context context) {
      this.context = context;
    }

    @Override
    protected void onPostExecute(List<Badge> badges) {
      showBadges(badges);
    }

    @Override
    protected List<Badge> doInBackground(Long... scoutIds) {
      return ScoutLogDatabase.getInstance(context).getScoutBadgeJoinDao().getBadgesForScout(scoutIds[0]);
    }
  }

}
