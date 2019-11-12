package com.example.persistenciaenandroid;

public class Parking {
    private String numParking;
    private String idCliente;


    public Parking(String numParking, String idCliente) {
        this.numParking = numParking;
        this.idCliente = idCliente;
    }

    public String getNumParking() {
        return numParking;
    }

    public void setNumParking(String numParking) {
        this.numParking = numParking;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
