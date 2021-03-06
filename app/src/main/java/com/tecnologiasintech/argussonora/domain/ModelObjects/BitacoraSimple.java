package com.tecnologiasintech.argussonora.domain.ModelObjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sergiosilva on 8/17/17.
 */
public class BitacoraSimple implements Parcelable{

    private boolean mAsistio;
    private boolean mNoasistio;
    private boolean mDobleturno;
    private boolean mCubredescanso;
    private long mHorasExtra;
    private String mFecha;

    public BitacoraSimple() {

    }

    public boolean isAsistio() {
        return mAsistio;
    }

    public void setAsistio(boolean asistio) {
        this.mAsistio = asistio;
    }

    public boolean isNoasistio() {
        return mNoasistio;
    }

    public void setNoasistio(boolean noasistio) {
        this.mNoasistio = noasistio;
    }

    public boolean isDobleturno() {
        return mDobleturno;
    }

    public void setDobleturno(boolean dobleturno) {
        this.mDobleturno = dobleturno;
    }

    public boolean isCubredescanso() {
        return mCubredescanso;
    }

    public void setCubredescanso(boolean cubredescanso) {
        this.mCubredescanso = cubredescanso;
    }

    public long getHorasExtra() {
        return mHorasExtra;
    }

    public void setHorasExtra(long horasExtra) {
        mHorasExtra = horasExtra;
    }

    public static Creator<BitacoraSimple> getCREATOR() {
        return CREATOR;
    }

    public String getFecha() {
        return mFecha;
    }

    public void setFecha(String fecha) {
        mFecha = fecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt (mAsistio ? 1 : 0);
        dest.writeInt(mNoasistio ? 1 : 0);
        dest.writeInt(mDobleturno ? 1 : 0);
        dest.writeInt(mCubredescanso ? 1 : 0);
        dest.writeLong(mHorasExtra);
        dest.writeString(mFecha);
    }

    protected BitacoraSimple(Parcel in) {
        mAsistio = in.readInt() != 0;
        mNoasistio = in.readInt() != 0;
        mDobleturno = in.readInt() != 0;
        mCubredescanso = in.readInt() != 0;
        mHorasExtra = in.readLong();
        mFecha = in.readString();
    }

    public static final Creator<BitacoraSimple> CREATOR = new Creator<BitacoraSimple>() {
        @Override
        public BitacoraSimple createFromParcel(Parcel in) {
            return new BitacoraSimple(in);
        }

        @Override
        public BitacoraSimple[] newArray(int size) {
            return new BitacoraSimple[size];
        }
    };
}
