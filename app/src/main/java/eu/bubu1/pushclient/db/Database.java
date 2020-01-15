package eu.bubu1.pushclient.db;

import androidx.room.RoomDatabase;

import eu.bubu1.pushclient.db.entity.Registration;

@androidx.room.Database(entities = {Registration.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract RegistrationDao productDao();
}
