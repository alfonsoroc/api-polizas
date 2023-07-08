package com.polizasservice.polizasservice.dto;

public class PolizasDTO {

    public int IDPoliza;
    public float cantidad;
    public String nombre;
    public String apellido;
    public int SKU;
    public String articulo;

    public int getIDPoliza() {
        return IDPoliza;
    }

    public void setIDPoliza(int IDPoliza) {
        this.IDPoliza = IDPoliza;
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

    public int getSKU() {
        return SKU;
    }

    public void setSKU(int SKU) {
        this.SKU = SKU;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }
}
