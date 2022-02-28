/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.CRUDS.CRUDHabitacion;
import modelo.CRUDS.CRUDTipoHabitacion;

/**
 *
 * @author heyscar
 */
public class ContratoHabitacion {
    private int id_contrat_habitacion;
    private int id_habitacion;
    private String Codigo_reservacion;
    private double precioFacturado;

    public ContratoHabitacion(int id_contrat_habitacion, int id_habitacion, String Codigo_reservacion,double precioFacturado) {
        this.id_contrat_habitacion = id_contrat_habitacion;
        this.id_habitacion = id_habitacion;
        this.Codigo_reservacion = Codigo_reservacion;
        this.precioFacturado = precioFacturado;
    }

    public int getId_contrat_habitacion() {
        return id_contrat_habitacion;
    }

    public int getId_habitacion() {
        return id_habitacion;
    }

    public String getCodigo_reservacion() {
        return Codigo_reservacion;
    }
    public double getPrecioFacturado(){
        return precioFacturado;
    }
    public String getNombreHabitacion(){
        
        
      return  CRUDTipoHabitacion.getInstance().
              findOneid_tipoHabitacion(CRUDHabitacion.getInstance()
              .findOneCodigo_reservacion(id_habitacion).
              getId_tipo_habitaciones()).getNombre().toUpperCase();
    }
}
