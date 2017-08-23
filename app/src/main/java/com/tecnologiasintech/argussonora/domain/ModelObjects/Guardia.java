package com.tecnologiasintech.argussonora.domain.ModelObjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sergiosilva on 8/17/17.
 */

public class Guardia implements Parcelable{

    private String usuarioClienteAsignado;
    private boolean usuarioDisponible;
    private String usuarioDomicilio;
    private String usuarioNombre;
    private long usuarioSueldoBase;
    private long usuarioTelefono;
    private long usuarioTelefonoCelular;
    private String usuarioTipo;
    private String usuarioTipoGuardia;
    private String usuarioTurno;
    private String usuarioKey;


    private com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple BitacoraSimple;

    public Guardia(){}

    public String getUsuarioClienteAsignado() {
        return usuarioClienteAsignado;
    }

    public void setUsuarioClienteAsignado(String usuarioClienteAsignado) {
        this.usuarioClienteAsignado = usuarioClienteAsignado;
    }

    public boolean isUsuarioDisponible() {
        return usuarioDisponible;
    }

    public void setUsuarioDisponible(boolean usuarioDisponible) {
        this.usuarioDisponible = usuarioDisponible;
    }

    public String getUsuarioDomicilio() {
        return usuarioDomicilio;
    }

    public void setUsuarioDomicilio(String usuarioDomicilio) {
        this.usuarioDomicilio = usuarioDomicilio;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public long getUsuarioSueldoBase() {
        return usuarioSueldoBase;
    }

    public void setUsuarioSueldoBase(long usuarioSueldoBase) {
        this.usuarioSueldoBase = usuarioSueldoBase;
    }

    public long getUsuarioTelefono() {
        return usuarioTelefono;
    }

    public void setUsuarioTelefono(long usuarioTelefono) {
        this.usuarioTelefono = usuarioTelefono;
    }

    public long getUsuarioTelefonoCelular() {
        return usuarioTelefonoCelular;
    }

    public void setUsuarioTelefonoCelular(long usuarioTelefonoCelular) {
        this.usuarioTelefonoCelular = usuarioTelefonoCelular;
    }

    public String getUsuarioTipo() {
        return usuarioTipo;
    }

    public void setUsuarioTipo(String usuarioTipo) {
        this.usuarioTipo = usuarioTipo;
    }

    public String getUsuarioTipoGuardia() {
        return usuarioTipoGuardia;
    }

    public void setUsuarioTipoGuardia(String usuarioTipoGuardia) {
        this.usuarioTipoGuardia = usuarioTipoGuardia;
    }

    public String getUsuarioTurno() {
        return usuarioTurno;
    }

    public void setUsuarioTurno(String usuarioTurno) {
        this.usuarioTurno = usuarioTurno;
    }

    public String getUsuarioKey() {
        return usuarioKey;
    }

    public void setUsuarioKey(String usuarioKey) {
        this.usuarioKey = usuarioKey;
    }

    public com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple getBitacoraSimple() {
        return BitacoraSimple;
    }

    public void setBitacoraSimple(com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple bitacoraSimple) {
        BitacoraSimple = bitacoraSimple;
    }

    public String toString() {
        return "Cliente Asignado: " + usuarioClienteAsignado + "\n" +
                "Disponible: " + usuarioDisponible + "\n" +
                "Domicilio: " + usuarioDomicilio + "\n" +
                "Nombre: " + usuarioNombre + "\n" +
                "Sueldo Base: " + usuarioSueldoBase + "\n" +
                "Telefono: " + usuarioTelefono + "\n" +
                "Telefono Celular: " + usuarioTelefonoCelular + "\n" +
                "Tipo: " + usuarioTipo + "\n" +
                "Tipo Guardia: " + usuarioTipoGuardia + "\n" +
                "Turno: " + usuarioTurno + "\n" +
                "Key: " + usuarioKey + "\n";
    }


    @Override
    public int describeContents() {
        return 0; // ignore
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(usuarioClienteAsignado);
        dest.writeInt(usuarioDisponible ? 1 : 0);
        dest.writeString(usuarioDomicilio);
        dest.writeString(usuarioNombre);
        dest.writeLong(usuarioSueldoBase);
        dest.writeLong(usuarioTelefono);
        dest.writeLong(usuarioTelefonoCelular);
        dest.writeString(usuarioTipo);
        dest.writeString(usuarioTipoGuardia);
        dest.writeString(usuarioTurno);
        dest.writeString(usuarioKey);
    }

    private Guardia(Parcel in) {
        usuarioClienteAsignado = in.readString();
        usuarioDisponible = in.readInt() != 0;
        usuarioDomicilio = in.readString();
        usuarioNombre = in.readString();
        usuarioSueldoBase = in.readLong();
        usuarioTelefono = in.readLong();
        usuarioTelefonoCelular = in.readLong();
        usuarioTipo = in.readString();
        usuarioTipoGuardia = in.readString();
        usuarioTurno = in.readString();
        usuarioKey = in.readString();
    }

    public static final Creator<Guardia> CREATOR = new Creator<Guardia>() {
        @Override
        public Guardia createFromParcel(Parcel in) {
            return new Guardia(in);
        }

        @Override
        public Guardia[] newArray(int size) {
            return new Guardia[size];
        }
    };
}
