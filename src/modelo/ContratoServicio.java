/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.CRUDS.CRUDServicio;

/**
 *
 * @author heyscar
 */
public class ContratoServicio {
    private int id_contrato_servicios;
    private int id_servicio;
    private String CodigoReservacion;
    private int cantidad;
    private double precioFacturado;

    public ContratoServicio(int id_contrato_servicios, int id_servicio, String CodigoReservacion, int cantidad,double precioFacturado) {
        this.id_contrato_servicios = id_contrato_servicios;
        this.id_servicio = id_servicio;
        this.CodigoReservacion = CodigoReservacion;
        this.cantidad = cantidad;
        this.precioFacturado = precioFacturado;
    }

    public int getId_contrato_servicios() {
        return id_contrato_servicios;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public String getCodigoReservacion() {
        return CodigoReservacion;
    }

    public int getCantidad() {
        return cantidad;
    }
    public double getPrecioUnitarioFacturado(){
        return precioFacturado;
    }
    public String getNombreServicio(){
       return CRUDServicio.getInstance().findOneid_servicio(id_servicio).getNombre().toUpperCase();
    }
     public double getPrecioTotalFacturado(){
        return precioFacturado * cantidad;
    }
}
