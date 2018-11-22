package edu.cnm.deepdive.scoutlog.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import edu.cnm.deepdive.scoutlog.model.dao.BadgeDao;
import edu.cnm.deepdive.scoutlog.model.dao.ScoutDao;
import edu.cnm.deepdive.scoutlog.model.entities.Badge;
import edu.cnm.deepdive.scoutlog.model.entities.Scout;

@Database(entities = {Scout.class, Badge.class},
    version = 1,
    exportSchema = false)
public abstract class ScoutLogDatabase extends RoomDatabase {

  private static final String DATABASE_NAME = "scout_db";

  private static ScoutLogDatabase instance = null;

  public synchronized static ScoutLogDatabase getInstance(Context context) {

    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(),
          ScoutLogDatabase.class,
          DATABASE_NAME)
//          .addCallback(new Callback(context.getApplicationContext()))
          .build();

    }
    return instance;
  }

  public static synchronized void forgetInstance(Context context) {
    instance = null;

  }

  public abstract ScoutDao getScoutDao();

  public abstract BadgeDao getBadgeDao();

//  private static class Callback extends RoomDatabase.Callback {
//
//    private Context context;
//
//    public Callback(Context context) {
//      this.context = context;
//    }

//    @Override
//    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//      super.onCreate(db);
//      new PrepopulateTask(context).execute();
   // }

//    @Override
//    public void onOpen(@NonNull SupportSQLiteDatabase db) {
//      super.onOpen(db);
//    }

  }

//  private static class PrepopulateTask extends AsyncTask<Void, Void, Void> {
//
//    private Context context;
//
//    public PrepopulateTask(Context context) {
//      this.context = context;
//    }
//
//    @Override
//    protected Void doInBackground(Void... voids) {
//      ScoutLogDatabase db = getInstance(context);
//     ScoutDao dao = db.getScoutDao();
//     Scout scout =  new  Scout();
//     scout.setLastName("Smith");
//     scout.setFirstName("Michael");
//     scout.setRank("Cub Scout");
//      scout.setLastName("Smith");
//      scout.setFirstName("David");
//      scout.setRank("Boy Scout");
//      scout.setLastName("Garcia");
//      scout.setFirstName("Jerry");
//      scout.setRank("Boy Scout");
//      scout.setLastName("Ingles");
//      scout.setFirstName("Robby");
//      scout.setRank("Cub Scout");
//      scout.setLastName("George");
//      scout.setFirstName("Steve");
//      scout.setRank("Boy Scout");
//      scout.setLastName("Lucero");
//      scout.setFirstName("Mike");
//      scout.setRank("Boy Scout");
//      scout.setLastName("Montoya");
//      scout.setFirstName("John");
//      scout.setRank("Cub Scout");
//      scout.setLastName("McKinney");
//      scout.setFirstName("Matt");
//      scout.setRank("Cub Scout");
//      scout.setLastName("Brown");
//      scout.setFirstName("Jackson");
//      scout.setRank("Boy Scout");
//      scout.setLastName("Butler");
//      scout.setFirstName("Jamie");
//      scout.setRank("Boy Scout");
//
//      dao.insert(scout);
//      forgetInstance(context);
//      return null;
//    }
//
//  }

//}
