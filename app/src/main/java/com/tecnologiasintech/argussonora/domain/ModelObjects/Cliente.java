package com.tecnologiasintech.argussonora.domain.ModelObjects;

/**
 * Created by sergiosilva on 8/22/17.
 */

public class Cliente {

    private boolean clienteDisponible;
    private String clienteDomicilio;
    private String clienteNombre;
    private String clienteZonaAsignada;

    public Cliente(){}

    public boolean isClienteDisponible() {
        return clienteDisponible;
    }

    public void setClienteDisponible(boolean clienteDisponible) {
        this.clienteDisponible = clienteDisponible;
    }

    public String getClienteDomicilio() {
        return clienteDomicilio;
    }

    public void setClienteDomicilio(String clienteDomicilio) {
        this.clienteDomicilio = clienteDomicilio;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteZonaAsignada() {
        return clienteZonaAsignada;
    }

    public void setClienteZonaAsignada(String clienteZonaAsignada) {
        this.clienteZonaAsignada = clienteZonaAsignada;
    }
}
