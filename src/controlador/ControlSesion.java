/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import modelo.Conexion;
import sistema_hotel1.Alertas;

public class ControlSesion {
    private static String usuario;
    private static String contraseña;
    private static String codigo;
    private static int id_user;
    private int validador  = 0;
    
    private  static Conexion conexion;
    
    private static ControlSesion cs ;
    private ControlSesion(){
        id_user  = 0;
        usuario = "";
        contraseña = "";
        codigo = "";
       conexion = Conexion.getInstance();
       validador = 0;
    }
     public static ControlSesion getInstance(){
        if(cs == null){
        cs = new ControlSesion();
        }
        return cs;
        
    }
    public boolean iniciarSesion(String User ,String password){
    boolean resp = false;
   
    if((usuario.equals("")|| contraseña.equals(""))&&(codigo.equals(""))){
        try {
           
             String sql =  "SELECT administradores.id_administrador,administradores.nombre_usuario,administradores.contraseña FROM administradores "
                    + "WHERE nombre_usuario = ? ";
             PreparedStatement ps ;
             if(conexion.conectar()!= null){
                 conexion.desconectar();
                 ps = conexion.conectar().prepareStatement(sql);
             
            
            ps.setString(1, User.toUpperCase());
           
             ResultSet rs = ps.executeQuery();
           
             while (rs.next()) 
            { if(rs.getString("contraseña").equals(password) ){
              usuario =  rs.getString("nombre_usuario");
              contraseña = rs.getString("contraseña"); 
              id_user = rs.getInt("id_administrador");
            }
            }
              conexion.desconectar();
               Alertas aa = new Alertas(Alert.AlertType.INFORMATION,"  ");
                if(usuario.isEmpty()||contraseña.isEmpty()){
                 aa.setAlertType(Alert.AlertType.ERROR);
                 aa.setTitle("ERROR");
                aa.setContentText("el usuario: "
                        + usuario +"y/o contraseña no existen");
                resp =false;
                  aa.showAndWait();
                }
                else
                {
                MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "INICIO DE SESIÓN - ADMIN", usuario);
                resp =true;
                validador  = 2;
                
                }
            
             }
            } catch (SQLException ex) {
            System.out.println("modelo.ControlSesion.iniciarSesion()");
            }
        }
        else
        {
          Alertas aa = new Alertas(Alert.AlertType.CONFIRMATION,"");
                aa.setTitle("usuario logueado");
                aa.setContentText("un usuario no cerro sesion anteriormente \n"
                + "la sesion anterior se cerrara para evitar conflictos \n"
                + "¿Desea Continuar?");
                aa.showAndWait();
                if(aa.getResult() == ButtonType.CANCEL){
                    resp = false;
                }
                else{
                     cs.CerrarSesion();
                    return iniciarSesion(User,password);
                    
                }
               
             
        }
        return resp;
    }
     public boolean iniciarSesion(String code){
    boolean resp = false;
    if((usuario.equals("")|| contraseña.equals(""))&&(codigo.equals(""))){
        try {
           
             String sql =  "SELECT reservaciones.id_cliente,reservaciones.codigo_reservacion,clientes.nombre " +
"FROM reservaciones JOIN clientes ON reservaciones.id_cliente  = clientes.id_cliente " +
" WHERE reservaciones.codigo_reservacion = ?" ;
            PreparedStatement ps ;
                      if(conexion.conectar()!= null){
                 conexion.desconectar();
                 ps   = conexion.conectar().prepareStatement(sql);
            ps.setString(1,code.toUpperCase());
           
             ResultSet rs = ps.executeQuery();
           
             while (rs.next()) 
            { 
              codigo =  rs.getString("codigo_reservacion");
              id_user = rs.getInt("id_cliente");
              usuario= rs.getString("nombre");
            }
              conexion.desconectar();
               Alertas aa = new Alertas(Alert.AlertType.INFORMATION,"  ");
                if(codigo.isEmpty()||id_user ==0){
                 aa.setAlertType(Alert.AlertType.ERROR);
                 aa.setTitle("ERROR");
                aa.setContentText("no existe la reservacion "
                        + codigo +"o fue eliminada por el administrador");
                resp =false;
                aa.showAndWait();
                }
                else
                {
               MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "CONSULTA DE CLIENTE", usuario);
                resp =true;
                validador =1;
                }
              
                      }
            } catch (SQLException ex) {
            System.out.println("modelo.ControlSesion.iniciarSesion()");
            }
        }
        else
        {
          Alertas aa = new Alertas(Alert.AlertType.CONFIRMATION,"");
                aa.setTitle("usuario logueado");
                aa.setContentText("un usuario no cerro sesion anteriormente \n"
                + "la sesion anterior se cerrara para evitar conflictos \n"
                + "¿Desea CerrarSesion?");
                aa.showAndWait();
                if(aa.getResult() == ButtonType.CANCEL){
                    resp = false;
                }
                else{
                    cs.CerrarSesion();
                    return iniciarSesion(code);
                    
                }
               
             
        }
        return resp;
    }
    
    
   public void CerrarSesion(){
       MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "CIERRE DE SESIÓN", usuario);
          id_user  = 0;
        usuario = "";
        contraseña = "";
        codigo = "";
        validador = 0;
    }
    public boolean validadorAdmin(){
         if(validador == 2)return true;
           
       
       return false;
    }
    public boolean userNoLogin(){
         if(validador == 0)return true;
           
       
       return false;
    }
    public boolean validadorCliente(){

   if(validador == 1)return true;
           
       
       return false;
}

  
 public int getIDUser(){
      return id_user;
 }
 public String getCodigoReservacion(){
        return codigo;
     
 }
public String getNombreUsuario(){
    return usuario;
}
}
