package edu.cnm.deepdive.scoutlog.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "scout_badge_join",
    primaryKeys = { "scoutId", "badgeId"},
    foreignKeys = {
        @ForeignKey(entity = Scout.class,
                  parentColumns = "scout_id",
                   childColumns = "scoutId"),
        @ForeignKey(entity = Badge.class,
                  parentColumns = "badge_id",
                  childColumns = "badgeId")
    })
public class ScoutBadgeJoin {
  public  long scoutId;
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
