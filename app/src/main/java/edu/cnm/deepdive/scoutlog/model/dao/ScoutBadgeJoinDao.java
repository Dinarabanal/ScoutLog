package edu.cnm.deepdive.scoutlog.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.scoutlog.model.entities.Badge;
import edu.cnm.deepdive.scoutlog.model.entities.ScoutBadgeJoin;
import java.util.List;

@Dao
public interface ScoutBadgeJoinDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void insert(ScoutBadgeJoin scoutBadgeJoin);

 @Query("SELECT Badge.* FROM Badge INNER JOIN scout_badge_join ON Badge.badge_id = scout_badge_join.badge_id WHERE scout_badge_join.scout_id=:scoutId")
  List<Badge> getBadgesForScout(long scoutId);

 @Query("SELECT * FROM scout_badge_join")
  List<ScoutBadgeJoin> getScoutBadgeJoins();

}
