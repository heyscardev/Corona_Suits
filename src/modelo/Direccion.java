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
public class Direccion {
    private int id_cliente;
    private int id_pais;
    private String nombrePais;
     private int id_estado;
    private String nombreEstado;
     private int id_ciudad;
    private String nombreCiudad;
     private int id_domicilio;
    private String descripcionDomicilio;
    private String textosString;

    public Direccion(int id_cliente,int id_pais, String nombrePais, int id_estado, String nombreEstado, int id_ciudad, String nombreCiudad, int id_domicilio, String descripcionDomicilio) {
       this.id_cliente = id_cliente;
        this.id_pais = id_pais;
        this.nombrePais = nombrePais;
        this.id_estado = id_estado;
        this.nombreEstado = nombreEstado;
        this.id_ciudad = id_ciudad;
        this.nombreCiudad = nombreCiudad;
        this.id_domicilio = id_domicilio;
        this.descripcionDomicilio = descripcionDomicilio;
    }

    public void setTextosString(String textosString) {
        this.textosString = textosString;
    }

    public int getId_pais() {
        return id_pais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public int getId_estado() {
        return id_estado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public int getId_ciudad() {
        return id_ciudad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public int getId_domicilio() {
        return id_domicilio;
    }

    public String getDescripcionDomicilio() {
        return descripcionDomicilio;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    @Override
    public String toString() {
        return textosString;
    }
    
    
}
