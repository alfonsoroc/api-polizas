package com.polizasservice.polizasservice.service;

public class DetalleArticulo {
    private int SKU;
    private  String Nombre;

    public int getSKU() {
        return SKU;
    }

    public void setSKU(int SKU) {
        this.SKU = SKU;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
