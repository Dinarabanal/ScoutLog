package edu.cnm.deepdive.scoutlog.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
    (
        indices = {
            @Index(value = {"badge_name"})
        }

    )
public class Badge {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "badge_id")
  private long id;
  @NonNull
  @ColumnInfo(name = "badge_name", collate = ColumnInfo.NOCASE)
  private String badgeName;
  @ColumnInfo(name = "image_link")
  private String imageLink;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public String getBadgeName() {
    return badgeName;
  }

  public void setBadgeName(@NonNull String badgeName) {
    this.badgeName = badgeName;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }
}
