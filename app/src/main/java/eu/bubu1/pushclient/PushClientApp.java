package eu.bubu1.pushclient;

import android.app.Application;
import android.arch.persistence.room.Room;

import eu.bubu1.pushclient.db.Database;

public class PushClientApp extends Application {
    private static final String DATABASE_NAME = "Database";

    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();

        // create database
        database = Room.databaseBuilder(getApplicationContext(), Database.class, DATABASE_NAME)
                .build();
    }

    public Database getDB() {
        return database;
    }
}
