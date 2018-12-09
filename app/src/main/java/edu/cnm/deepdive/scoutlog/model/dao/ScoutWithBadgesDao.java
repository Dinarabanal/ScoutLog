package edu.cnm.deepdive.scoutlog.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.scoutlog.model.entities.ScoutWithBadges;
import java.util.List;

@Dao
public interface ScoutWithBadgesDao {
  @Query("SELECT * FROM Scout")
  List<ScoutWithBadges> getAllScoutsAndBadges();
}
