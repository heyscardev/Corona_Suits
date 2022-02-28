/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author heyscar
 */
public class Habitacion {
    int id;
    int piso;
    int numeroHabitacion;
    int id_tipo_habitaciones;

    public Habitacion(int id, int piso, int numeroHabitacion, int id_tipo_habitaciones) {
        this.id = id;
        this.piso = piso;
        this.numeroHabitacion = numeroHabitacion;
        this.id_tipo_habitaciones = id_tipo_habitaciones;
    }

    public int getId() {
        return id;
    }

    public int getPiso() {
        return piso;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public int getId_tipo_habitaciones() {
        return id_tipo_habitaciones;
    }

    @Override
    public String toString() {
        return  "PISO: " + piso + " - NRO: " + numeroHabitacion ;
    }

    public boolean equals(Habitacion habita) {
        if(habita.getId()==this.getId()){
            return true;
        }
        return false;
    
    }
    
    
}
