package com.tecnologiasintech.argussonora.domain.ModelObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class Client implements Parcelable{

    private boolean clienteDisponible;
    private String clienteDomicilio;
    private String clienteNombre;
    private String clienteZonaAsignada;

    public Client(){}

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

    public String toString() {
        return "Disponible: " + clienteDisponible + "\n" +
                "Domcilio: " + clienteDomicilio + "\n" +
                "Nombre: " + clienteNombre + "\n" +
                "zona: " + clienteZonaAsignada + "\n";
    }

    @Override
    public int describeContents() {
        return 0; // ignore
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(clienteDisponible ? 1 : 0);
        dest.writeString(clienteDomicilio);
        dest.writeString(clienteNombre);
        dest.writeString(clienteZonaAsignada);
    }

    private Client(Parcel in) {
        clienteDisponible = in.readInt() != 0;
        clienteDomicilio = in.readString();
        clienteNombre = in.readString();
        clienteZonaAsignada = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };


}
