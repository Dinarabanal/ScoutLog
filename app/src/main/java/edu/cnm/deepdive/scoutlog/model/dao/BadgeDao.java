package edu.cnm.deepdive.scoutlog.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.scoutlog.model.entities.Badge;
import java.util.List;


@Dao

public interface BadgeDao {
  @Insert (onConflict = OnConflictStrategy.FAIL)
 long insert (Badge badge);

  @Query("SELECT * FROM Badge")
 List<Badge> getAllBadges();

  @Query("SELECT * FROM Badge WHERE badge_name LIKE :badgeName")
  Badge select(String badgeName);

  @Query("SELECT * FROM Badge WHERE badge_id =:id")
  Badge selectById(long id);
}
