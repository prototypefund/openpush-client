package eu.bubu1.pushclient.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import eu.bubu1.pushclient.db.entity.Registration;

@Database(entities = {Registration.class}, version = 1)
public abstract class PushClientDatabase extends RoomDatabase {
    public abstract RegistrationDao registrationDao();

    private static volatile PushClientDatabase INSTANCE;

    public static PushClientDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PushClientDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PushClientDatabase.class, "pushclient_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
