package eu.bubu1.pushclient.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class Registration {
    @PrimaryKey
    @NonNull
    private String registrationId;

    private String packageName;

    private String packageSignature;

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageSignature() {
        return packageSignature;
    }

    public void setPackageSignature(String packageSignature) {
        this.packageSignature = packageSignature;
    }
}
