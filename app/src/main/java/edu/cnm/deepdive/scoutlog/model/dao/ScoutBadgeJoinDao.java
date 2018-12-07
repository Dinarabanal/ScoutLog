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

 @Query("SELECT * FROM Badge INNER JOIN scout_badge_join "
     + "ON Badge.badge_id = scout_badge_join.badgeId WHERE scout_badge_join.scoutId=:scoutId")
  List<Badge> getBadgesForScout(long scoutId);


}
