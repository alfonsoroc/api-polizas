package com.polizasservice.polizasservice.dto;

import com.polizasservice.polizasservice.service.DetalleArticulo;
import com.polizasservice.polizasservice.service.Empleado;
import com.polizasservice.polizasservice.service.Poliza;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private Poliza poliza;

    private Empleado empleado;

    private DetalleArticulo detalleArticulo;

    public Poliza getPoliza() {
        return poliza;
    }

    public void setPoliza(Poliza poliza) {
        this.poliza = poliza;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public DetalleArticulo getDetalleArticulo() {
        return detalleArticulo;
    }

    public void setDetalleArticulo(DetalleArticulo detalleArticulo) {
        this.detalleArticulo = detalleArticulo;
    }
}
