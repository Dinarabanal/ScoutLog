package edu.cnm.deepdive.scoutlog.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

@Entity(indices = {@Index(value =
    {"last_name", "first_name"}, unique = true)
})

public class Scout {

  // @Entity(primaryKeys = {"firstName", "lastName"})
  // public class User {
//    public String firstName;
//    public String lastName;
//  }

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "scout_id")
  private long id;
  //@NonNull
  @ColumnInfo(name = "last_name")
  private String lastName;
  //@NonNull
  @ColumnInfo(name = "first_name")
  private String firstName;
  //@NonNull
  @ColumnInfo(index = true)
  private String rank;
//  @ColumnInfo(index = true)
//  private List<Badge> badges;



  public void setFullName(@NonNull String firstName, @NonNull String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  //@NonNull
  public String getLastName() {
    return lastName;
  }

  public void setLastName(@NonNull String lastname) {
    this.lastName = lastname;
  }

  //@NonNull
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(@NonNull String firstName) {
    this.firstName = firstName;
  }

  //@NonNull
  public String getRank() {
    return rank;
  }

  public void setRank(@NonNull String rank) {
    this.rank = rank;
  }
}

