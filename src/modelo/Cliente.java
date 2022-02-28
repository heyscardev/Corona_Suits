/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author heyscar
 */
public class Cliente {
    private int id_cliente;
    private String cedula;
    private String nombre;
    private String apellido;
    private LocalDate Fecha_nacimiento;
    private String sFechaNacimiento;
    private String Sexo;

    public Cliente(int id_cliente, String cedula, String nombre, String apellido, LocalDate Fecha_nacimiento, String Sexo) {
        this.id_cliente = id_cliente;
        this.cedula = cedula.toUpperCase();
        this.nombre = nombre.toUpperCase();
        this.apellido = apellido.toUpperCase();
        this.Fecha_nacimiento = Fecha_nacimiento;
         this.sFechaNacimiento = Fecha_nacimiento.toString();
        this.Sexo = Sexo.toUpperCase();
    }
    public Cliente( String cedula, String nombre, String apellido, LocalDate Fecha_nacimiento, String Sexo) {
     
        this.cedula = cedula.toUpperCase();
        this.nombre = nombre.toUpperCase();
        this.apellido = apellido.toUpperCase();
        this.Fecha_nacimiento = Fecha_nacimiento;
         this.sFechaNacimiento = Fecha_nacimiento.toString();
        this.Sexo = Sexo.toUpperCase();
    }
    

    public int getId_cliente() {
        return id_cliente;
    }

    private void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula.toUpperCase();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getApellido() {
        return apellido.toUpperCase();
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFecha_nacimiento() {
        return Fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate Fecha_nacimiento) {
        this.Fecha_nacimiento = Fecha_nacimiento;
        this.sFechaNacimiento = Fecha_nacimiento.toString();
    }

    public String getSfechaNacimiento() {
        return sFechaNacimiento;
    }

  

    public String getSexo() {
            if(this.Sexo.equals("M")){
                return "MASCULINO";
    }else if(this.Sexo.equals("F")){
    
        return "FEMENINO";
    }else{
        return "NO DEFINIDO";
    }
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo.toUpperCase();
    }
    
    
}
