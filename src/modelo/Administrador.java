/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.CRUDS.CRUDCliente;

/**
 *
 * @author heyscar
 */
public class Administrador {
    private int id;
    private String nombre_usuario;
    private String contraseña;


    public Administrador(int id, String nombre_usuario, String contraseña) {
        this.id = id;
        this.nombre_usuario = nombre_usuario;
        this.contraseña = contraseña;
     
    }

  
    

    public int getId() {
        return id;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

   
    
}
