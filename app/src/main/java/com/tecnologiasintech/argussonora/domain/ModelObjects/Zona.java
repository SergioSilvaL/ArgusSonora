package com.tecnologiasintech.argussonora.domain.ModelObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiosilva on 8/28/17.
 */

public class Zona {

    private String zonaNombre;
    private List<String> mClienteList = new ArrayList<>();

    public Zona(){};

    public String getZonaNombre() {
        return zonaNombre;
    }

    public void setZonaNombre(String zonaNombre) {
        this.zonaNombre = zonaNombre;
    }

    public List<String> getClienteList() {
        return mClienteList;
    }

    public void setClienteList(List<String> clienteList) {
        mClienteList = clienteList;
    }

    public String toString() {
        return "Nombre: " + zonaNombre + "\n";
    }
}
