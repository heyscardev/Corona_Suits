/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import modelo.Administrador;
import modelo.CRUDS.CRUDAdministradores;
import modelo.CRUDS.CRUDPreguntaSeguridad;
import modelo.Conexion;
import modelo.PreguntaSeguridad;

/**
 *
 * @author heyscar
 */
public class RecuperacionMetodos {
    private ArrayList<PreguntaSeguridad>  arrayPreguntas;
    private String User = null;
    private String Password = null;
    private int id_user = -1;
    private boolean pass = false;
    public RecuperacionMetodos(){
        
    }
    public ArrayList<String> getPreguntasSeguridad(String nombreUsuario){
       Salir();
        try {
           
            String sql =  "SELECT administradores.id_administrador,administradores.nombre_usuario,administradores.contraseña FROM administradores "
                    + "WHERE nombre_usuario = ? ";
            PreparedStatement ps ;
            ps = Conexion.getInstance().conectar().prepareStatement(sql);
            
            
            ps.setString(1, nombreUsuario.toUpperCase());
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) 
            {
                 User =  rs.getString("nombre_usuario").toUpperCase();
                 Password = rs.getString("contraseña");
                 id_user = rs.getInt("id_administrador");
            }
            
            Conexion.getInstance().desconectar();
        } catch (SQLException ex) {
         return null;
        }
        if(User == null || Password == null || id_user == -1){
            return null;
        }else{
            arrayPreguntas = CRUDPreguntaSeguridad.getInstance().read(id_user);
            ArrayList<String> temp = new ArrayList<>();
            for(PreguntaSeguridad psi :arrayPreguntas){
                temp.add(psi.getPregunta());
            }
            return temp;
        }
    }
    public boolean ValidaRespuesta(String res1,String res2,String res3){
         pass = false;
        int count = 0;
       
            if(arrayPreguntas.get(0).getRespuesta().toUpperCase().equals( res1.toUpperCase())){
                count++;
                
            }
            if(arrayPreguntas.get(1).getRespuesta().toUpperCase().equals( res2.toUpperCase())){
                count++;
                
            }
            if(arrayPreguntas.get(2).getRespuesta().toUpperCase().equals( res3.toUpperCase())){
                count++;
                
            }
            if(count<2){
                pass = false;
            }
            else{
                pass = true;
                return true;
            }
            return pass;
        
        
    }
    public boolean Cambio_Contraseña(String contraseña){
        if (pass) {
            CRUDAdministradores.getInstance().UPDATE(new Administrador(id_user, User, contraseña));
            Salir();
            return true;
        }
        else{
            Alert ab = new Alert(Alert.AlertType.ERROR);
            ab.setHeaderText("error!");
            ab.setTitle("Corona  Suites");
            ab.setContentText("error no se respondieron las preguntas de seguridad \n o las respuestas son incorrectas");
            ab.showAndWait();
        }
        return false;
    }
    public void Salir(){
        id_user = -1;
        pass = false;
        Password = null;
        User = null;
        arrayPreguntas = null;
    }
}
