package edu.cnm.deepdive.scoutlog.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
    (
        indices = {
            @Index(value = {"badge_name"}, unique = true)
        }

    )
public class Badge {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name= "badge_name")
  private long id;

  @NonNull
  @ColumnInfo(name = "badge_name", collate = ColumnInfo.NOCASE)
  private String badge;

public String getBadge() {
  return badge;
}


public void setBadge(String badge) {
  this.badge = badge;
}
}
