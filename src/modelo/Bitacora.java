/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.time.LocalDateTime;

/**
 *
 * @author heyscar
 */
public class Bitacora {
    private int id_bitacora;
    private LocalDateTime tiempo_modificacion; 
    private String accion;
    private String usuario;
     

    public Bitacora(int id_bitacora, LocalDateTime tiempo_modificacion, String accion, String usuario) {
        this.id_bitacora = id_bitacora;
        this.tiempo_modificacion = tiempo_modificacion;
        this.accion = accion;
        this.usuario = usuario;
    }

    public int getId_bitacora() {
        return id_bitacora;
    }

    public LocalDateTime getTiempo_modificacion() {
        return tiempo_modificacion;
    }

    public String getAccion() {
        return accion;
    }

    public String getUsuario() {
        return usuario;
    }    
}
