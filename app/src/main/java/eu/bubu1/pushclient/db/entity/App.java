package eu.bubu1.pushclient.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class App {
    @PrimaryKey
    private String packageName;

}
