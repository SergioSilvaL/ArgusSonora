package com.tecnologiasintech.argussonora.domain.ModelObjects;

/**
 * Created by sergiosilva on 8/17/17.
 */
public class BitacoraSimple {

    private boolean mAsistio;
    private boolean mNoasistio;
    private boolean mDobleturno;
    private boolean mCubredescanso;
    private long mHorasextra;
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

    public long getHorasextra() {
        return mHorasextra;
    }

    public void setHorasextra(long horasextra) {
        this.mHorasextra = horasextra;
    }

    public String getFecha() {
        return mFecha;
    }

    public void setFecha(String fecha) {
        mFecha = fecha;
    }
}
