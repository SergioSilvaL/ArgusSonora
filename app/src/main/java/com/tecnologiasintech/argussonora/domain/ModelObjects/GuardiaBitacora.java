package com.tecnologiasintech.argussonora.domain.ModelObjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sergiosilva on 8/24/17.
 */

public class GuardiaBitacora implements Parcelable{

    private String mUsuarioKey;
    private String mUsuarioNombre;
    private com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple BitacoraSimple;


    public GuardiaBitacora(){}

    public String getUsuarioKey() {
        return mUsuarioKey;
    }

    public void setUsuarioKey(String usuarioKey) {
        mUsuarioKey = usuarioKey;
    }

    public String getUsuarioNombre() {
        return mUsuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        mUsuarioNombre = usuarioNombre;
    }


    public com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple getBitacoraSimple() {
        return BitacoraSimple;
    }

    public void setBitacoraSimple(com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple bitacoraSimple) {
        BitacoraSimple = bitacoraSimple;
    }

    public String toString() {
        return "Guardia Key: " + mUsuarioKey + "\n" +
                "Guardia Nombre: " + mUsuarioNombre + "\n" +
                "Bitacora asistio: " + BitacoraSimple.isAsistio() + "\n" +
                "Bitacora no asistio: " + BitacoraSimple.isNoasistio() + "\n" +
                "Bitacora dobleturno: " + BitacoraSimple.isDobleturno() + "\n" +
                "Bitacora cubredescanso: " + BitacoraSimple.isCubredescanso() + "\n" +
                "Bitacora horasextra: " + BitacoraSimple.getHorasextra() + "\n" +
                "Bitcora fecha: " + BitacoraSimple.getFecha() + "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUsuarioKey);
        dest.writeString(mUsuarioNombre);
        dest.writeParcelable(BitacoraSimple, flags);
    }

    protected GuardiaBitacora(Parcel in) {
        mUsuarioKey = in.readString();
        mUsuarioNombre = in.readString();
        BitacoraSimple = in.readParcelable(com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple.class.getClassLoader());
    }

    public static final Creator<GuardiaBitacora> CREATOR = new Creator<GuardiaBitacora>() {
        @Override
        public GuardiaBitacora createFromParcel(Parcel in) {
            return new GuardiaBitacora(in);
        }

        @Override
        public GuardiaBitacora[] newArray(int size) {
            return new GuardiaBitacora[size];
        }
    };
}
