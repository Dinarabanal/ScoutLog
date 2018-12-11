package edu.cnm.deepdive.scoutlog.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
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
import java.util.List;
import java.util.Map;

/**
 * The type Scout view adapter.
 */
public class ScoutViewAdapter extends RecyclerView.Adapter<ScoutViewAdapter.ViewHolder>{

  private static final String TAG = "ScoutViewAdapter";
  private Context context;
  private List<ScoutWithBadges> scouts;
  private Map<Long, List<Badge>> scoutsBadges;
  private LayoutInflater inflater;
  private FragmentManager manager;
  /**
   * Instantiates a new Scout view adapter.
   *
   * @param scouts the scout contents
   * @param context the context
   */
  public ScoutViewAdapter(List<ScoutWithBadges> scouts, Context context,FragmentManager manager) {
    this.manager = manager;
    this.scouts = scouts;
    this.context = context;
    this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

  private void showBadges(List<Badge> badges, long position){
    AlertDialog.Builder builder = new Builder(context);
    LinearLayout dialogView = (LinearLayout) inflater.inflate(R.layout.see_badges_layout, null);
    //RecyclerView badges = dialogView.findViewById(R.id.recycled_badges_for_scout);
    Button addBadges = dialogView.findViewById(R.id.add_badge);
    Button back = dialogView.findViewById(R.id.close);
    TextView scoutTitle = dialogView.findViewById(R.id.textview);
    ScoutWithBadges scout = scouts.get((int)position-1);
    scoutTitle.setText(scout.getScout().getFirstName() + " " + scout.getScout().getLastName() + " Badges");
    LinearLayout badgeImageLayout = dialogView.findViewById(R.id.scout_recycled_badges);
    LinearLayout view =  dialogView.findViewById(R.id.scout_recycled_badges);
    for (int i = 0; i < badges.size() && i < 16; i++){
      LinearLayout imageView = (LinearLayout) ((LinearLayout) view.getChildAt(i / 4)).getChildAt(i % 4);
      CircleImageView civ = (CircleImageView) imageView.getChildAt(0);
      // Possibly add the names of badges
//      TextView text = (TextView) imageView.getChildAt(1);
//      text.setText(badges.get(i).getBadgeName());
      Picasso.get().load(badges.get(i).getImageLink()).into(civ);
    }

    builder.setView(dialogView);
    final AlertDialog dialog = builder.create();
    dialog.show();
    back.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.cancel();
      }
    });
    addBadges.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putLong("id",scouts.get((int)position-1).getScout().getId());
        bundle.putString("scout_name",scouts.get((int)position-1).getScout().getFirstName());
        BadgeFragment badgeFragment = new BadgeFragment();
        badgeFragment.setArguments(bundle);
        manager.beginTransaction().replace(R.id.fragment_container, badgeFragment).addToBackStack(null).commit();
        dialog.cancel();
      }
    });

  }

  /**.
   * The type View holder.
   */
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /**
     * The Scouts.
     */
    ScoutWithBadges scout;
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
      new QueryBadgesForScout(context,scout.getScout().getId()).execute(scout.getScout().getId());
    }

    public void bind(ScoutWithBadges scout) {
      this.scout = scout;
      firstName.setText("First name: " + scout.getScout().getFirstName());
      lastName.setText("Last name: " +scout.getScout().getLastName());
      rank.setText("Rank : " + scout.getScout().getRank());

    }
  }

  private class QueryBadgesForScout extends AsyncTask<Long,Void,List<Badge>> {

    private Context context;
    private long position;

    public QueryBadgesForScout(Context context, long position) {
      this.context = context;
      this.position = position;
    }

    @Override
    protected void onPostExecute(List<Badge> badges) {
      showBadges(badges, position);
    }

    @Override
    protected List<Badge> doInBackground(Long... scoutIds) {
      return ScoutLogDatabase.getInstance(context).getScoutBadgeJoinDao().getBadgesForScout(scoutIds[0]);
    }
  }

}
