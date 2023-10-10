package com.polizasservice.polizasservice.models;

public class Polizas {

    private int idpoliza;
    private float cantidad;
    private String nombre;
    private String apellido;
    private int sku;
    private String articulo;

    public Polizas(int idpoliza, float cantidad, String nombre, String apellido, int sku, String articulo) {
        this.idpoliza = idpoliza;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sku = sku;
        this.articulo = articulo;
    }

    public int getIdpoliza() {
        return idpoliza;
    }

    public void setIdpoliza(int idpoliza) {
        this.idpoliza = idpoliza;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }
}
