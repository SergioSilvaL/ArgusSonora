package com.tecnologiasintech.argussonora.domain.ModelObjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

/**
 * Created by sergiosilva on 9/11/17.
 */

public class Supervisor implements Parcelable{

    private String usuarioContrasena;
    private String usuarioDomicilio;
    private String usuarioEmail;
    private String usuarioNombre;
    private String usuarioTipo;
    private String usuarioTurno;
    private String usuarioZona;
    private String usuarioKey;

    public Supervisor(){}

    public Supervisor(String usuarioContrasena, String usuarioDomicilio, String usuarioEmail, String usuarioNombre, String usuarioTipo, String usuarioTurno, String usuarioZona) {
        this.usuarioContrasena = usuarioContrasena;
        this.usuarioDomicilio = usuarioDomicilio;
        this.usuarioEmail = usuarioEmail;
        this.usuarioNombre = usuarioNombre;
        this.usuarioTipo = usuarioTipo;
        this.usuarioTurno = usuarioTurno;
        this.usuarioZona = usuarioZona;
    }

    protected Supervisor(Parcel in) {
        usuarioContrasena = in.readString();
        usuarioDomicilio = in.readString();
        usuarioEmail = in.readString();
        usuarioNombre = in.readString();
        usuarioTipo = in.readString();
        usuarioTurno = in.readString();
        usuarioZona = in.readString();
        usuarioKey = in.readString();
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


    public String getUsuarioKey() {

        return usuarioKey;
    }

    public void setUsuarioKey(String usuarioKey) {
        this.usuarioKey = usuarioKey;
    }

    public String getUsuarioContrasena() {
        return usuarioContrasena;
    }

    public void setUsuarioContrasena(String usuarioContrasena) {
        this.usuarioContrasena = usuarioContrasena;
    }

    public String getUsuarioDomicilio() {
        return usuarioDomicilio;
    }

    public void setUsuarioDomicilio(String usuarioDomicilio) {
        this.usuarioDomicilio = usuarioDomicilio;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioTipo() {
        return usuarioTipo;
    }

    public void setUsuarioTipo(String usuarioTipo) {
        this.usuarioTipo = usuarioTipo;
    }

    public String getUsuarioTurno() {
        return usuarioTurno;
    }

    public void setUsuarioTurno(String usuarioTurno) {
        this.usuarioTurno = usuarioTurno;
    }

    public String getUsuarioZona() {
        return usuarioZona;
    }

    public void setUsuarioZona(String usuarioZona) {
        this.usuarioZona = usuarioZona;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(usuarioContrasena);
        dest.writeString(usuarioDomicilio);
        dest.writeString(usuarioEmail);
        dest.writeString(usuarioNombre);
        dest.writeString(usuarioTipo);
        dest.writeString(usuarioTurno);
        dest.writeString(usuarioZona);
        dest.writeString(usuarioKey);
    }
}
