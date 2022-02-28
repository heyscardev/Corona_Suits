/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.CRUDS;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.util.converter.LocalDateTimeStringConverter;
import modelo.Conexion;
import modelo.Habitacion;
import sistema_hotel1.Alertas;


/**
 *
 * @author heyscar
 */
public class CRUDHabitacion {
    private static CRUDHabitacion crud;
      private Alertas nn = new Alertas(Alert.AlertType.ERROR,"");
    Conexion cn ;
    private CRUDHabitacion(){
        cn = Conexion.getInstance();
          nn.setTitle("errror");
          nn.setHeaderText("Coronoa Suites: Habitacion Error!");
    }
    public static CRUDHabitacion getInstance(){
        if(crud == null){
            crud = new CRUDHabitacion();
        }
        return crud;
    }
    public boolean create(Habitacion habitacion){
       
       
        try {
            String sql = "INSERT INTO habitaciones(piso,num_habitacion,id_tipos_habitaciones) values(?,?,?);";
            PreparedStatement ps ;
            ps = cn.conectar().prepareStatement(sql);
            
   
         
          ps.setInt(1, habitacion.getPiso());
          ps.setInt(2,habitacion.getNumeroHabitacion());
          ps.setInt(3,habitacion.getId_tipo_habitaciones());
            ps.executeUpdate();
          ps.close();
          cn.desconectar();
            System.out.println("Registro Habitacion Exitoso");
            return true;
        } catch (SQLException ex) {
          nn.setContentText("error al registrar la habitacion");
        
          nn.showAndWait();
          return false;
        }
    }
    public boolean delete(int id ){
         
            String sql = "DELETE FROM habitaciones WHERE id_habitacion = ?";
            PreparedStatement ps ;
              try {
                  ps = cn.conectar().prepareStatement(sql);
                  ps.setInt(1, id);
                  ps.execute();
                  ps.close();
                  cn.desconectar();
                  System.out.println("borrado de habitacion con exito con exito");
                  return true;
              } catch (SQLException ex) {
              
          nn.setContentText("error al eliminar la habitacion");
          nn.showAndWait();    
          return false;
              }
    }
    public boolean update( Habitacion habitacion ){
            
            String sql = "UPDATE  habitaciones SET piso = ?, num_habitacion = ?, id_tipos_habitaciones = ?  WHERE id_habitacion = ?";
            PreparedStatement ps ;
              try {
                  ps = cn.conectar().prepareStatement(sql);
                  ps.setInt(1,habitacion.getPiso() );
          ps.setInt(2, habitacion.getNumeroHabitacion());
          ps.setInt(3,habitacion.getId_tipo_habitaciones());
          ps.setInt(4, habitacion.getId());
        ps.executeUpdate();
          ps.close();
          cn.desconectar();
            System.out.println("actualizacion de habitacion exitosa");
            return true;
              } catch (SQLException ex) {
               
          nn.setContentText("error al actualizar datos de esta habitacion");
       
          nn.showAndWait();    
          return false;
              }
    }
    public ArrayList<Habitacion> readAll(){
        ArrayList<Habitacion> array = new ArrayList<>();
        try {
            String sql = "SELECT  *  FROM habitaciones ";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            Habitacion temp = new Habitacion(
                    rs.getInt("id_habitacion"),
                    rs.getInt("piso"),
                    rs.getInt("num_habitacion"),
                    rs.getInt("id_tipos_habitaciones")
                    
             );
             array.add(temp);
              
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("Habitaciones consultadas con exito");
        } catch (SQLException ex) {
            nn.setContentText("Error al consultar  habitaciones");
          nn.showAndWait();  
        }
        return array;
    }
  
     public  Habitacion findOneCodigo_reservacion(int id){
       
      
         Habitacion Final = null;
        try {
            String sql = "SELECT * FROM habitaciones WHERE id_habitacion =  ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
        while(rs.next()){
             Habitacion temp = new Habitacion(
                    rs.getInt("id_habitacion"),
                    rs.getInt("piso"),
                    rs.getInt("num_habitacion"),
                    rs.getInt("id_tipos_habitaciones")
             );
             Final = temp;
        }
      
             rs.close();
             ps.close();
             cn.desconectar();
             System.out.println("consulta de habitacion con exito");
        } catch (SQLException ex) {
              nn.setContentText("error al consultar  habitacion");
          nn.showAndWait();  
        }
        return Final;
    }
    public ArrayList<Habitacion> HabitacionesDisponibles(LocalDate fe,int houre,LocalDate fs,int hours){
       ArrayList<Integer> array = new ArrayList<>();
      
       Timestamp ti = new Timestamp(
               fe.getYear()-1900,
                    fe.getMonthValue()-1 ,
                    fe.getDayOfMonth(),
                    houre, 0, 0, 0);

      Timestamp TS = new Timestamp(fs.getYear()-1900, fs.getMonthValue()-1, fs.getDayOfMonth(),hours, 0, 0, 0);
              System.out.println(ti.toString());
         System.out.println(TS.toString());
       ArrayList<Habitacion> arrayhabitacion = new ArrayList<>();
        try {
            String sql = "SELECT contrato_habitaciones.id_habitacion FROM reservaciones \n" +
"     JOIN contrato_habitaciones ON contrato_habitaciones.codigo_reservacion = reservaciones.codigo_reservacion \n" +
"WHERE   reservaciones.fecha_entrada>=? "
                    + "AND reservaciones.fecha_salida<=? ";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            
          
            ps.setTimestamp(1, ti);
            ps.setTimestamp(2, TS);
           
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                array.add(Integer.valueOf(rs.getInt(1)));
            }
            rs.close();
            ps.close();
            cn.desconectar();
            sql = "Select * FROM habitaciones";
            ps = cn.conectar().prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                boolean n = true;
                for(Integer in :array){
                    if( rs.getInt("id_habitacion") == in){
                        System.err.println(in.toString() + "primer array");
                        n = false;
                      
                    }
                   
                }
                 if(n == true){
                         Habitacion temp = new Habitacion(
                    rs.getInt("id_habitacion"),
                    rs.getInt("piso"),
                    rs.getInt("num_habitacion"),
                    rs.getInt("id_tipos_habitaciones")
                    
             );
                         arrayhabitacion.add(temp);
                    }
            }
            rs.close();
            ps.close();
            cn.desconectar();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUDReservacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    return arrayhabitacion;
     }
    
    public int getIdHabitacion(){
            int id = 0;
            try {
                
            String sql = "SELECT * FROM habitaciones ORDER BY id_habitacion DESC LIMIT 1";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = rs.getInt("id_habitacion");
            }
           
            System.out.println("ID habitación leido con exito ");
            } catch (SQLException ex) {
            System.out.println("Ocurrió un error al leer el ID");
            }
            return id;
    }
}
