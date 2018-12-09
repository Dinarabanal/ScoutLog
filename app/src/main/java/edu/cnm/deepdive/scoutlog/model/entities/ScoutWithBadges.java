package edu.cnm.deepdive.scoutlog.model.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import java.util.List;

public class ScoutWithBadges {

  @Embedded
  private Scout scout;
  @Relation(parentColumn = "scout_id", entityColumn = "badge_id")
  private List<Badge> badges;

  public Scout getScout() {
    return scout;
  }

  public void setScout(Scout scout) {
    this.scout = scout;
  }

  public List<Badge> getBadges() {
    return badges;
  }

  public void setBadges(List<Badge> badges) {
    this.badges = badges;
  }
}
