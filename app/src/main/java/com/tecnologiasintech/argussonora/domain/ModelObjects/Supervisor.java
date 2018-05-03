package com.tecnologiasintech.argussonora.domain.ModelObjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;


@IgnoreExtraProperties
public class Supervisor implements Parcelable{

    @PropertyName("usuarioKey")
    public String id;

    @PropertyName("usuarioEmail")
    public String email;

    @PropertyName("usuarioNombre")
    public String fullName;

    @PropertyName("usuarioDomicilio")
    public String homeAddress;

    @PropertyName("usuarioContrasena")
    public String password;

    @PropertyName("usuarioTurno")
    public String shift;

    @PropertyName("usuarioTipo")
    public String type;

    @PropertyName("usuarioZona")
    public String zone;


    public Supervisor(){}

    // TODO: Implement Builder Pattern
    public Supervisor(String password, String homeAddress, String email, String fullName, String type, String shift, String zone) {
        this.password = password;
        this.homeAddress = homeAddress;
        this.email = email;
        this.fullName = fullName;
        this.type = type;
        this.shift = shift;
        this.zone = zone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    protected Supervisor(Parcel in) {
        password = in.readString();
        homeAddress = in.readString();
        email = in.readString();
        fullName = in.readString();
        type = in.readString();
        shift = in.readString();
        zone = in.readString();
        id = in.readString();
    }

    public static final Creator<Supervisor> CREATOR = new Creator<Supervisor>() {
        @Override
        public Supervisor createFromParcel(Parcel in) {
            return new Supervisor(in);
        }

        @Override
        public Supervisor[] newArray(int size) {
            return new Supervisor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(password);
        dest.writeString(homeAddress);
        dest.writeString(email);
        dest.writeString(fullName);
        dest.writeString(type);
        dest.writeString(shift);
        dest.writeString(zone);
        dest.writeString(id);
    }
}
