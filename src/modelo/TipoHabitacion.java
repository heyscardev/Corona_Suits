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
public class TipoHabitacion {
    private int id_tipos_habitaciones;
    private String nombre;
    private String descripcion;
    private int cant_max_personas;
    private Double costo;

    public TipoHabitacion(int id_tipos_habitaciones, String nombre, String descripcion, int cant_max_personas, Double costo) {
        this.id_tipos_habitaciones = id_tipos_habitaciones;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cant_max_personas = cant_max_personas;
        this.costo = costo;
    }

    public int getId_tipos_habitaciones() {
        return id_tipos_habitaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCant_max_personas() {
        return cant_max_personas;
    }

    public Double getCosto() {
        return costo;
    }

    @Override
    public String toString() {
        if(id_tipos_habitaciones == -1){
            return nombre;
        }
        return  nombre + " - NRO MAX PERSONAS: " + cant_max_personas + "-  costo: " + costo + '$';
    }
    
    
}
