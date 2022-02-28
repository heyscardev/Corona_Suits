/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.ControlSesion;
import controlador.HiloLector;
import controlador.MetodosAdministradores;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sistema_hotel1.Alertas;

/**
 *
 * @author heyscar
 */
public class Conexion {
    
    private String port = "3306";
    private String user = "root";
     private String bd = "db_corona_suites";
    private String password = "";
    private String url;
    private Connection linea;
    private static Conexion conecto;
    private Conexion(){
        url =  "jdbc:mariadb://127.0.0.1:"+port+"/"+bd;
    }
     private Conexion(String ipred){
         url =  "jdbc:mariadb://"+ipred+":"+port+"/"+bd;
         
     }
     public static Conexion getInstance(){
         if(conecto == null){
              conecto =  new Conexion();
         }
         return conecto;
     }
     public static Conexion getInstance(String ipred){
         if(conecto == null){
              conecto =  new Conexion(ipred);
         }
         return conecto;
     }
     public Connection conectar()  {
         
        try {
            return conectar_inicio();
        } catch (SQLException ex) {
            System.out.println("no se ha podido establecer conexion con la base de datos");
            Alertas a = new Alertas(Alert.AlertType.WARNING,"");
             a.setContentText("no se ha podido establecer conexion con la base de datos \n");
             a.showAndWait();
        }
        return linea;
     }   
          public void desconectar(){
     if(linea != null){
            try {
                if(!linea.isClosed()){
                    linea.close();
                     System.out.println("se cerro base de datos con exito");
                }
            } catch (SQLException ex) {
                 Alertas a = new Alertas(Alert.AlertType.WARNING,"");
             a.setContentText("no se pudo cerrar de base de datos");
                 System.out.println("no se pudo cerrar de base de datos");
                  a.showAndWait();
            }
        }
}
          public Connection conectar_inicio() throws SQLException {
              try{
            Class.forName("org.mariadb.jdbc.Driver");
            linea = DriverManager.getConnection(url,user,password);
             System.out.println("se conecto con exito");
        }catch (ClassNotFoundException ex){
            System.out.println("no se encuentran librerias de base de datos");
            Alertas a = new Alertas(Alert.AlertType.WARNING,"");
            a.setContentText("no se encuentran librerias instaladas en\n base de datos");
             a.showAndWait();
        }
              return linea;
          }
          public boolean RepaldarBaseDatos(String Ubicacion){
        try {
            
            
            conectar_inicio();
            Process p = Runtime.getRuntime().exec("mysqldump -u "+ user +" -p"+ password+" " + bd);
             new HiloLector(p.getErrorStream()).start(); 
             
            InputStream is = p.getInputStream();//Pedimos la entrada
            FileOutputStream fos = new FileOutputStream(Ubicacion); //creamos el archivo para le respaldo
            byte[] buffer = new byte[1000]; //Creamos una variable de tipo byte para el buffer
            int leido = is.read(buffer); //Devuelve el número de bytes leídos o -1 si se alcanzó el final del stream.
            while (leido > 0) {
                fos.write(buffer, 0, leido);//Buffer de caracteres, Desplazamiento de partida para empezar a escribir caracteres, Número de caracteres para escribir
                leido = is.read(buffer);
            }
            
            fos.close();//Cierra respaldo
            desconectar();
             
            Alertas aa = new Alertas(Alert.AlertType.CONFIRMATION, "su respaldo se ha realizado con exito");
            aa.setHeaderText("Operacion Exitosa");
            aa.showAndWait();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }return false;
          }
          public void cargarRespaldoBaseDatos(String ubicacion){
        try {
            conectar_inicio();
           
            Process p = Runtime.getRuntime().exec("mysql -u "+user+" -p" + password
                    +" "+bd);
            new HiloLector(p.getErrorStream()).start();
          
            OutputStream os = p.getOutputStream(); //Pedimos la salida
            FileInputStream fis = new FileInputStream(ubicacion); //creamos el archivo para le respaldo
            byte[] buffer = new byte[1000]; //Creamos una variable de tipo byte para el buffer
            
            int leido = fis.read(buffer);//Devuelve el número de bytes leídos o -1 si se alcanzó el final del stream.
            while (leido > 0) {
                os.write(buffer, 0, leido); //Buffer de caracteres, Desplazamiento de partida para empezar a escribir caracteres, Número de caracteres para escribir
                leido = fis.read(buffer);
            }
            
            os.flush(); //vacía los buffers de salida
            os.close(); //Cierra
            fis.close(); //Cierra respaldo
            desconectar();
            
            Alertas aa = new Alertas(Alert.AlertType.CONFIRMATION, "se ha cargado la copia de Seguridad con exito");
            aa.setHeaderText("Operacion Exitosa ");
            aa.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
          }
     
}
