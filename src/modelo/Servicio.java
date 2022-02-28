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
public class Servicio {
   private int id_servicio; 
   private String nombre;
   private double precio;
   private String descripcion;

    public Servicio(int id_servicio, String nombre, double precio, String descripcion) {
        this.id_servicio = id_servicio;
        this.nombre = nombre.toUpperCase();
        this.precio = precio;
        this.descripcion = descripcion.toUpperCase();
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return  nombre +"-"+ precio+"$";
    }
   public String getCostoFormater(){
       return Double.toString(precio) + " $";
   }
   
}
