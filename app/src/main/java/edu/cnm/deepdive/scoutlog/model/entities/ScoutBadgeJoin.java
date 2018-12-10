package edu.cnm.deepdive.scoutlog.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "scout_badge_join",
    primaryKeys = { "scout_id", "badge_id"},
    foreignKeys = {
        @ForeignKey(entity = Scout.class,
                  parentColumns = "scout_id",
                   childColumns = "scout_id"),
        @ForeignKey(entity = Badge.class,
                  parentColumns = "badge_id",
                  childColumns = "badge_id")
    })
public class ScoutBadgeJoin {

  @ColumnInfo(name = "scout_id", index = true)
  public  long scoutId;
  @ColumnInfo(name = "badge_id", index = true)
  public  long badgeId;

  public long getScoutId() {
    return scoutId;
  }

  public void setScoutId(long scoutId) {
    this.scoutId = scoutId;
  }

  public long getBadgeId() {
    return badgeId;
  }

  public void setBadgeId(long badgeId) {
    this.badgeId = badgeId;
  }
}
