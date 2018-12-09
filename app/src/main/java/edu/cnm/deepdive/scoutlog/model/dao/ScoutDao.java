package edu.cnm.deepdive.scoutlog.model.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface ScoutDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insert(Scout scout);

  @Query("SELECT * FROM scout")
  List<Scout> getAll();

  @Query("SELECT * FROM Scout WHERE scout_id =:scout_id")
  List<Scout> loadAllByIds(int[] scout_id);

  @Query("SELECT * FROM Scout WHERE first_name LIKE :first AND " +
      "last_name LIKE :last LIMIT 1")
  Scout findByName(String first, String last);

  @Query("SELECT * FROM Scout WHERE scout_id =:scoutId")
  Scout select(long scoutId);

  @Insert
 void  insertAll(Scout... Scouts);

  @Delete
  void delete(Scout Scout);


}

