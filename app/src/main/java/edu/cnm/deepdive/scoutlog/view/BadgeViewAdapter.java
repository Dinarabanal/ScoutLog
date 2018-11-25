package edu.cnm.deepdive.scoutlog.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.cnm.deepdive.scoutlog.R;
import java.util.ArrayList;

public class BadgeViewAdapter extends RecyclerView.Adapter<BadgeViewAdapter.ViewHolder> {

  private ArrayList <String> mData;
  private LayoutInflater mInflater;
  private ItemClickListener mClickListener;

  // data is passed into the constructor
  BadgeViewAdapter(Context context, ArrayList<String> data) {
    this.mInflater = LayoutInflater.from(context);
    this.mData = data;
  }

  // inflates the cell layout from xml when needed
  @Override
  @NonNull
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
    return new ViewHolder(view);
  }

  // binds the data to the TextView in each cell
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Picasso.get().load(getItem(position)).into(holder.myImageView);
  }

  // total number of cells
  @Override
  public int getItemCount() {
    return mData.size();
  }

  // stores and recycles views as they are scrolled off screen
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CircleImageView myImageView;

    ViewHolder(View itemView) {
      super(itemView);
      myImageView = itemView.findViewById(R.id.badges);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      if (mClickListener != null) {
        mClickListener.onItemClick(view, getAdapterPosition());
      }
    }
  }

  // convenience method for getting data at click position
  String getItem(int id) {
    return mData.get(id);
  }

  // allows clicks events to be caught
  void setClickListener(ItemClickListener itemClickListener) {
    this.mClickListener = itemClickListener;
  }

  // parent activity will implement this method to respond to click events
  public interface ItemClickListener {

    void onItemClick(View view, int position);
  }
}



