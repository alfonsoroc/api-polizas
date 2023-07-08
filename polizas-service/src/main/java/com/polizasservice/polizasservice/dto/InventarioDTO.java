package com.polizasservice.polizasservice.dto;

public class InventarioDTO {
    public int codigo;
    public String Pnombre;
    public float Pcantidad;
    public String Pfamilia;
    public float Pprecio;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getPnombre() {
        return Pnombre;
    }

    public void setPnombre(String pnombre) {
        Pnombre = pnombre;
    }

    public float getPcantidad() {
        return Pcantidad;
    }

    public void setPcantidad(float pcantidad) {
        Pcantidad = pcantidad;
    }

    public String getPfamilia() {
        return Pfamilia;
    }

    public void setPfamilia(String pfamilia) {
        Pfamilia = pfamilia;
    }

    public float getPprecio() {
        return Pprecio;
    }

    public void setPprecio(float pprecio) {
        Pprecio = pprecio;
    }
}
