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
public class PreguntaSeguridad {
    private int  id;
    private int id_administrador;
    private String pregunta;
    private String respuesta;

    public PreguntaSeguridad(int id, int id_administrador, String pregunta, String respuesta) {
        this.id = id;
        this.id_administrador = id_administrador;
        this.pregunta = pregunta.toUpperCase();
        this.respuesta = respuesta.toUpperCase();
    }

    public int getId() {
        return id;
    }

    public int getId_administrador() {
        return id_administrador;
    }

    public String getPregunta() {
        return pregunta.toUpperCase();
    }

    public String getRespuesta() {
        return respuesta.toUpperCase();
    }
    
}
