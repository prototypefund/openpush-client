package eu.bubu1.pushclient.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

import eu.bubu1.pushclient.db.entity.Registration;

@Dao
public interface RegistrationDao {

    @Query("SELECT * FROM registration")
    List<Registration> getAll();

    @Query("SELECT * FROM registration WHERE registrationId LIKE :registrationId LIMIT 1")
    Registration findByRegistrationId(String registrationId);

    @Query("SELECT * FROM registration WHERE packageName LIKE :packageName LIMIT 1")
    Registration findByPackageName(String packageName);

    @Insert
    void insertAll(List<Registration> registrations);

    @Update
    void update(Registration registration);

    @Delete
    void delete(Registration registration);
}
