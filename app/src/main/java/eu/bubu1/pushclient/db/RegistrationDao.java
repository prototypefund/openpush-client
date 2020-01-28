package eu.bubu1.pushclient.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

import eu.bubu1.pushclient.db.entity.Registration;

@Dao
public interface RegistrationDao {

    @Query("SELECT * FROM registration")
    LiveData<List<Registration>> getAll();

    @Query("SELECT * FROM registration WHERE registrationId LIKE :registrationId LIMIT 1")
    Registration findByRegistrationId(String registrationId);

    @Query("SELECT * FROM registration WHERE packageName LIKE :packageName LIMIT 1")
    Registration findByPackageName(String packageName);

    @Insert
    void insertAll(List<Registration> registrations);

    @Insert
    void insert(Registration registration);

    @Update
    void update(Registration registration);

    @Delete
    void delete(Registration registration);

    @Query("DELETE FROM registration WHERE packageName LIKE :packageName")
    void delete(String packageName);
}
