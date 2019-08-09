package eu.bubu1.pushclient.db;

import android.arch.persistence.room.RoomDatabase;

import eu.bubu1.pushclient.db.entity.Registration;

@android.arch.persistence.room.Database(entities = {Registration.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract RegistrationDao productDao();
}
