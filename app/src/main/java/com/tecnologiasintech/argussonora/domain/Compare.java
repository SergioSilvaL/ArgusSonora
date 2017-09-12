package com.tecnologiasintech.argussonora.domain;

import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;

import java.util.Comparator;

/**
 * Created by sergiosilva on 9/11/17.
 */

class CompareGuard implements Comparator<Guardia> {

    @Override
    public int compare(Guardia o1, Guardia o2) {
        return o1.getUsuarioNombre().compareTo(o2.getUsuarioNombre());
    }
}


class CompareServices implements Comparator<Cliente> {

    @Override
    public int compare(Cliente o1, Cliente o2) {
        return o1.getClienteNombre().compareTo(o2.getClienteNombre());
    }
}
