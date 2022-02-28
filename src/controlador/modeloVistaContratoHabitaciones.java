/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.ContratoHabitacion;
import modelo.Habitacion;
import modelo.TipoHabitacion;

/**
 *
 * @author heyscar
 */
public class modeloVistaContratoHabitaciones extends ContratoHabitacion{
    private Habitacion habi;
    private TipoHabitacion tipoHabi;

    public modeloVistaContratoHabitaciones(ContratoHabitacion conHabi,Habitacion habi,TipoHabitacion tipoHabi) {
        super(conHabi.getId_contrat_habitacion(), conHabi.getId_habitacion(), conHabi.getCodigo_reservacion(),conHabi.getPrecioFacturado());
        this.habi = habi;
        this.tipoHabi = tipoHabi;
               
    }
    public int getNumeroHabitacion(){
    return habi.getNumeroHabitacion();
    }
    public int getPiso(){
       return habi.getPiso();
    }

    public int getGetId_TipoHabitacion() {
        return habi.getId_tipo_habitaciones();
    }
    public int getCantMaxPersonas(){
        return tipoHabi.getCant_max_personas();
    }
    public double getCostoTipoHabitacion(){
        return  tipoHabi.getCosto();
    }
    public String getDescripcionTipoHabitacion(){
        return  tipoHabi.getDescripcion(); 
    }
    public String getNombreTipoHabitacion(){
        return tipoHabi.getNombre();
    }
public  Habitacion getHabitacion(){
    return habi;
}
    @Override
    public String toString() {
        return "modeloVistaContratoHabitaciones{" + "habi=" + habi + ", tipoHabi=" + tipoHabi + '}';
    }
            
}
