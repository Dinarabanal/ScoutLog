
package edu.cnm.deepdive.scoutlog.model.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import edu.cnm.deepdive.scoutlog.model.dao.BadgeDao;
import edu.cnm.deepdive.scoutlog.model.dao.ScoutBadgeJoinDao;
import edu.cnm.deepdive.scoutlog.model.dao.ScoutDao;
import edu.cnm.deepdive.scoutlog.model.dao.ScoutWithBadgesDao;
import edu.cnm.deepdive.scoutlog.model.entities.Badge;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;
import edu.cnm.deepdive.scoutlog.model.entities.ScoutBadgeJoin;

@Database(entities = {Scout.class, Badge.class, ScoutBadgeJoin.class},
    version = 1,
    exportSchema = true)
public abstract class ScoutLogDatabase extends RoomDatabase {

  private static final String DATABASE_NAME = "scout_db";

  private static ScoutLogDatabase instance = null;

  public synchronized static ScoutLogDatabase getInstance(Context context) {

    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(),
          ScoutLogDatabase.class,
          DATABASE_NAME)
          .addCallback(new Callback(context.getApplicationContext()))
          .build();

    }
    return instance;
  }

  public static synchronized void forgetInstance(Context context) {
    instance = null;

  }

  public abstract ScoutDao getScoutDao();

  public abstract BadgeDao getBadgeDao();

  public abstract ScoutBadgeJoinDao getScoutBadgeJoinDao();

  public abstract ScoutWithBadgesDao getScoutBadgeDao();

  private static class Callback extends RoomDatabase.Callback {

    private Context context;

    public Callback(Context context) {
      this.context = context;
    }

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      new PrepopulateTask(context).execute();
   }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
    }

}

  private static class PrepopulateTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    public PrepopulateTask(Context context) {
      this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      ScoutLogDatabase db = getInstance(context);
     ScoutDao dao = db.getScoutDao();
     Scout scout =  new  Scout();
     scout.setLastName("Smith");
     scout.setFirstName("Michael");
     scout.setRank("Cub Scout");
      Scout scout1 = new Scout();
      scout1.setLastName("Smith");
      scout1.setFirstName("David");
      scout1.setRank("Boy Scout");
      Scout scout2 = new Scout();
      scout2.setLastName("Garcia");
      scout2.setFirstName("Jerry");
      scout2.setRank("Boy Scout");
      Scout scout3 = new Scout();
      scout3.setLastName("Ingles");
      scout3.setFirstName("Robby");
      scout3.setRank("Cub Scout");
      Scout scout4 = new Scout();
      scout4.setLastName("George");
      scout4.setFirstName("Steve");
      scout4.setRank("Boy Scout");
      Scout scout5 = new Scout();
      scout5.setLastName("Lucero");
      scout5.setFirstName("Mike");
      scout5.setRank("Boy Scout");
      Scout scout6 = new Scout();
      scout6.setLastName("Montoya");
      scout6.setFirstName("John");
      scout6.setRank("Cub Scout");
      Scout scout7 = new Scout();
      scout7.setLastName("McKinney");
      scout7.setFirstName("Matt");
      scout7.setRank("Cub Scout");
      Scout scout8 = new Scout();
      scout8.setLastName("Brown");
      scout8.setFirstName("Jackson");
      scout8.setRank("Boy Scout");
      Scout scout9 = new Scout();
      scout9.setLastName("Butler");
      scout9.setFirstName("Jamie");
      scout9.setRank("Boy Scout");


      dao.insert(scout);
      dao.insert(scout1);
      dao.insert(scout2);
      dao.insert(scout3);
      dao.insert(scout4);
      dao.insert(scout5);
      dao.insert(scout6);
      dao.insert(scout7);
      dao.insert(scout8);
      dao.insert(scout9);


      forgetInstance(context);
      return null;
    }

  }

}