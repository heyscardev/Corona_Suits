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
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import modelo.Conexion;
import modelo.Reservacion;
import sistema_hotel1.Alertas;

/**
 *
 * @author heyscar
 */
public class CRUDReservacion {
   
    private static CRUDReservacion cr;
      private Alertas nn = new Alertas(Alert.AlertType.ERROR,"");
    Conexion cn ;
    private CRUDReservacion(){
        cn = Conexion.getInstance();
          nn.setTitle("errror");
          nn.setHeaderText("Coronoa Suites: Reservacion Error!");
    }
    public static CRUDReservacion getInstance(){
        if(cr == null){
            cr = new CRUDReservacion();
        }
        return cr;
    }
    public boolean create(Reservacion reservacion){
       
       
        try {
            String sql = "INSERT INTO reservaciones(codigo_reservacion,id_cliente,fecha_entrada,fecha_salida,costo_total) values(?,?,?,?,?)";
            PreparedStatement ps ;
            ps = cn.conectar().prepareStatement(sql);
           Timestamp ti = new Timestamp(reservacion.getFechaEntrada().getYear()-1900,reservacion.getFechaEntrada().getMonthValue()-1 , reservacion.getFechaEntrada().getDayOfMonth(), reservacion.getHoraEntrada(), 0, 0, 0);
      Timestamp TS = new Timestamp(reservacion.getFechaSalida().getYear()-1900, reservacion.getFechaSalida().getMonthValue()-1, reservacion.getFechaSalida().getDayOfMonth(),reservacion.getHoraSalida(), 0, 0, 0);
          
          ps.setString(1,reservacion.getCodigoReservacion());
          ps.setInt(2, reservacion.getId_cliente());
          ps.setTimestamp(3,ti);
         
          ps.setTimestamp(4,TS);
        
           ps.setDouble(5, 0);
             ps.executeUpdate();
          ps.close();
          cn.desconectar();
            System.out.println("Registro reservacion Exitosa");
            return true;
        } catch (SQLException ex) {
          nn.setContentText("error al registrar el reservacion");
        
          nn.showAndWait();
          
        }
        return false;
    }
    public void delete(String codigo){
         
            String sql = "DELETE FROM reservaciones WHERE codigo_reservacion = ?";
            
              try {
                 PreparedStatement ps = cn.conectar().prepareStatement(sql);
                  ps.setString(1, codigo);
                  ps.execute();
                  ps.close();
                  cn.desconectar();
                  System.out.println("borrado de reservacion con exito");
              } catch (SQLException ex) {
              
          nn.setContentText("error al eliminar la reservacion");
          nn.showAndWait();    
              }
    }
    public void update(Reservacion res){
            
            String sql = "UPDATE FROM reservacones SET fecha_entrada = ? fecha_salida = ? hora_entrada = ? hora_salida = ? fecha_nacimiento WHERE id_reservacion = ?";
            PreparedStatement ps ;
            
              try {
                   
                  ps = cn.conectar().prepareStatement(sql);
                  Timestamp ti = new Timestamp(res.getFechaEntrada().getYear(),res.getFechaEntrada().getMonthValue() , res.getFechaEntrada().getDayOfMonth(), res.getHoraEntrada(), 0, 0, 0);
      Timestamp TS = new Timestamp(res.getFechaSalida().getYear(), res.getFechaSalida().getMonthValue(), res.getFechaSalida().getDayOfMonth(),res.getHoraSalida(), 0, 0, 0);
        
                  ps.setTimestamp(1, ti);
          ps.setTimestamp(2, TS);
          
          ps.setDouble(5,res.getCostoTotal() );
          ps.setInt(6, res.getId_cliente());
               ps.executeUpdate();
          ps.close();
          cn.desconectar();
            System.out.println("actualizacion de reservacion exitosa");
              } catch (SQLException ex) {
               
          nn.setContentText("error al actualizar datos de la reservacion");
       
          nn.showAndWait();    
              }
    }
    public ArrayList<Reservacion> readAll(){
        ArrayList<Reservacion> array = new ArrayList<>();
        try {
            String sql = "SELECT  *  FROM reservaciones ";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            Reservacion reservacion = new Reservacion(
                    rs.getString("codigo_reservacion"),
                    rs.getInt("id_cliente"),
                    rs.getTimestamp("fecha_entrada").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("fecha_entrada").toLocalDateTime().getHour(),
                    rs.getTimestamp("fecha_salida").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("fecha_salida").toLocalDateTime().getHour(),
                    rs.getDouble("costo_total")
             );
             array.add(reservacion);
              
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("reservacion consultadas con exito");
        } catch (SQLException ex) {
            nn.setContentText("error al consultar  reservaciones");
          nn.showAndWait();  
        }
        return array;
    }
    public  ArrayList<Reservacion> findGlobal(LocalDate f_i,LocalDate f_f){
        
         ArrayList<Reservacion> array = new ArrayList<>();
        try {
            String sql = "SELECT * FROM reservaciones WHERE reservaciones.fecha_entrada BETWEEN ? AND ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setDate(1,Date.valueOf(f_i));
            ps.setDate(2, Date.valueOf(f_f));
           
            ResultSet rs = ps.executeQuery();
        while(rs.next()){
             Reservacion reservacion = new Reservacion(
                   rs.getString("codigo_reservacion"),
                    rs.getInt("id_cliente"),
                    rs.getTimestamp("fecha_entrada").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("fecha_entrada").toLocalDateTime().getHour(),
                    rs.getTimestamp("fecha_salida").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("fecha_salida").toLocalDateTime().getHour(),
                    rs.getDouble("costo_total")
             );
             array.add(reservacion);
        }
      
             rs.close();
             ps.close();
             cn.desconectar();
             System.out.println("consulta de reservacion con exito");
        } catch (SQLException ex) {
              nn.setContentText("error al consultar  reservacion");
          nn.showAndWait();  
        }
        return array;
    }
     public  Reservacion findOneCodigo_reservacion(String consulta){
       
        consulta = consulta.toUpperCase();
         Reservacion reservacionFinal = null;
        try {
            String sql = "SELECT * FROM reservaciones WHERE codigo_reservacion =  ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1,consulta);
            
           
            ResultSet rs = ps.executeQuery();
        while(rs.next()){
             Reservacion reservacion = new Reservacion(
                    rs.getString("codigo_reservacion"),
                    rs.getInt("id_cliente"),
                    rs.getTimestamp("fecha_entrada").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("fecha_entrada").toLocalDateTime().getHour(),
                    rs.getTimestamp("fecha_salida").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("fecha_salida").toLocalDateTime().getHour(),
                    rs.getDouble("costo_total")
             );
             reservacionFinal = reservacion;
        }
      
             rs.close();
             ps.close();
             cn.desconectar();
             System.out.println("consulta de reservacion con exito");
        } catch (SQLException ex) {
              nn.setContentText("error al consultar  reservacion");
          nn.showAndWait();  
        }
        return reservacionFinal;
    }
     public  ArrayList<Reservacion> findGlobalId_cliente(int id){
          ArrayList<Reservacion> array = new ArrayList<>();
     
        try {
            String sql = "SELECT * FROM reservaciones WHERE id_cliente = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
        while(rs.next()){
             Reservacion reservacion = new Reservacion(
                    rs.getString("codigo_reservacion"),
                    rs.getInt("id_cliente"),
                    rs.getTimestamp("fecha_entrada").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("fecha_entrada").toLocalDateTime().getHour(),
                    rs.getTimestamp("fecha_salida").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("fecha_salida").toLocalDateTime().getHour(),
                    rs.getDouble("costo_total")
             );
             array.add(reservacion);
            
        }
         rs.close();
             ps.close();
             cn.desconectar();
             System.out.println("consulta reservaciones realizada con exito");
        } catch (SQLException ex) {
              nn.setContentText("error al consultar reservaciones");
          nn.showAndWait();  
        }
        return array;
    }
     public String generarCodigo(int id_cliente ,int pos){
          int id = id_cliente;
       String nombre = CRUDCliente.getInstance().findOneId(id).getNombre().substring(0, 3);
       int nRclient = this.findGlobalId_cliente(id).size()+pos;
       if(this.findOneCodigo_reservacion(nRclient + nombre + id)!= null){
           return generarCodigo(id_cliente, pos+1);
       }
       
        return  nRclient + nombre + id;
        
     }
      public  ArrayList<Reservacion> findFecha(LocalDateTime fecha_buscar){
        
         ArrayList<Reservacion> array = new ArrayList<>();
        try {System.out.println(fecha_buscar.toString());
            String sql = "SELECT * FROM reservaciones WHERE reservaciones.fecha_entrada <= ? AND reservaciones.fecha_Salida >= ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setTimestamp(1,Timestamp.valueOf(fecha_buscar));
            ps.setTimestamp(2, Timestamp.valueOf(fecha_buscar.withHour(23).withMinute(59)));
           
            ResultSet rs = ps.executeQuery();
        while(rs.next()){
             Reservacion reservacion = new Reservacion(
                   rs.getString("codigo_reservacion"),
                    rs.getInt("id_cliente"),
                    rs.getTimestamp("fecha_entrada").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("fecha_entrada").toLocalDateTime().getHour(),
                    rs.getTimestamp("fecha_salida").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("fecha_salida").toLocalDateTime().getHour(),
                    rs.getDouble("costo_total")
                     
             );System.out.println(reservacion.getCodigoReservacion());
             array.add(reservacion);
        }
      
             rs.close();
             ps.close();
             cn.desconectar();
             System.out.println("consulta de reservacion con exito");
        } catch (SQLException ex) {
              nn.setContentText("error al consultar  reservacion");
          nn.showAndWait();  
        }
        return array;
    }
}
