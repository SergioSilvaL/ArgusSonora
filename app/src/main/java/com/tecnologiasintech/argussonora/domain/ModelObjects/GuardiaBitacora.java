package com.tecnologiasintech.argussonora.domain.ModelObjects;

/**
 * Created by sergiosilva on 8/24/17.
 */

public class GuardiaBitacora {

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

}
