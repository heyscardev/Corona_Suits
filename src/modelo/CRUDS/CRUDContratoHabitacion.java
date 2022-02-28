/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.CRUDS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import modelo.Conexion;
import modelo.ContratoHabitacion;

import sistema_hotel1.Alertas;

/**
 *
 * @author heyscar
 */
public class CRUDContratoHabitacion {
       private static CRUDContratoHabitacion cth;
    private Conexion cn;
     private Alertas nn = new Alertas(Alert.AlertType.ERROR,"");
    //-----------------singletoooon---------------------
    private CRUDContratoHabitacion(){
        cn = Conexion.getInstance();
          nn.setTitle("errror");
          nn.setHeaderText("Coronoa Suites: habitaciones Error!");
    }
    public static CRUDContratoHabitacion getInstance(){
        if(cth == null){
            cth = new CRUDContratoHabitacion();
        }
        return cth;
    }
    //----------------------crear nueva fila en la tabla -----------------
    public void create(ContratoHabitacion temp){
        try {
            String sql = "INSERT INTO contrato_habitaciones(codigo_reservacion,id_habitacion,precio_facturado)"
                    + "VALUES(?,?,?)";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1,temp.getCodigo_reservacion());
            ps.setInt(2, temp.getId_habitacion());
            ps.setDouble(3, temp.getPrecioFacturado());
            
           ps.executeUpdate();
            ps.close();
            cn.desconectar();
            System.out.println("habitacion reservada  con exito");
        } catch (SQLException ex) {
          nn.setContentText("error al reservar la habitacion");
          nn.showAndWait();
        }
    }
    //-----------lee todos los datos de la tabla--------
        public ArrayList<ContratoHabitacion> readAll(){
             ArrayList<ContratoHabitacion> array = new ArrayList<>();
        try {
           
            String sql  =  "SELECT * FROM contrato_habitaciones";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ContratoHabitacion temp = new ContratoHabitacion(
                        rs.getInt("id_contrato_habitacion"), 
                        rs.getInt("id_habitacion"),
                rs.getString("codigo_reservacion"),
                rs.getDouble("precio_facturado"));
                        
                        
                array.add(temp);
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("tabla habitaciones reservadas cargada con con exito servicio");
        } catch (SQLException ex) {
         nn.setContentText("error al cargar habitaciones reservadas");
         nn.showAndWait();
        }
         return array;
    }
         public ArrayList<ContratoHabitacion> readhabitacionesReservacion(String codigoReservacion){
             ArrayList<ContratoHabitacion> array = new ArrayList<>();
        try {
           
            String sql  =  "SELECT * FROM contrato_habitaciones where codigo_reservacion = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1, codigoReservacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ContratoHabitacion temp = new ContratoHabitacion(
                        rs.getInt("id_contrato_habitacion"), 
                        rs.getInt("id_habitacion"),
                rs.getString("codigo_reservacion"),
                rs.getDouble("precio_facturado"));
                        
                array.add(temp);
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("tabla habitaciones contratadas cargada con con exito servicio");
        } catch (SQLException ex) {
         nn.setContentText("error al cargar sus habitaciones reservadas");
         nn.showAndWait();
        }
         return array;
    }
        //actualizar datos de una fila en tabla---------------
    public void update(ContratoHabitacion temp){
        try {
            String sql = "UPDATE FROM contrato_habitaciones SET id_habitacion = ? "
                    + "WHERE id_contrato_habitacion";
            PreparedStatement  ps= cn.conectar().prepareStatement(sql);
            ps.setInt(1,temp.getId_habitacion());
              ps.setInt(2,temp.getId_contrat_habitacion());
           
          
            ps.executeUpdate();
            ps.close();
            cn.desconectar();
            System.out.println("actualizacion habitacion reservada con exito ");
        } catch (SQLException ex) {
          nn.setContentText("error al modificar  habitacion reservada");
          nn.showAndWait();
        }
    }
    //--------------------elimina fila de la tabla---------------------
    public void delete(int id){
        try {
            String sql = "DELETE FROM contrato_habitaciones WHERE id_contrato_habitacion = ?";
            PreparedStatement ps  = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ps.close();
            cn.desconectar();
            System.out.println("servico contratado  eliminado con exito");
        } catch (SQLException ex) {
            nn.setContentText("no se ha podido eliminar servicio contratado");
            nn.showAndWait();
        }
        
    }
    public void deleteReservacion(String Codigo){
          try {
            String sql = "DELETE FROM contrato_habitaciones WHERE codigo_reservacion = ?";
            PreparedStatement ps  = cn.conectar().prepareStatement(sql);
            ps.setString(1, Codigo);
            ps.execute();
            ps.close();
            cn.desconectar();
            System.out.println("habitaciones  contratadas de reservacion: "+ Codigo +" eliminado con exito");
        } catch (SQLException ex) {
            nn.setContentText("no se ha podido eliminar servicio contratado");
            nn.showAndWait();
        }
    }
    public ContratoHabitacion findOneid_contrato_habitacion(int id){
       ContratoHabitacion FINAL  = null;
        try {
            String sql = "SELECT * FROM contrato_habitaciones "
                    + "WHERE id_contrato_habitacion = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               ContratoHabitacion temp = new ContratoHabitacion(
                        rs.getInt("id_contrato_habitacion"), 
                        rs.getInt("id_habitacion"),
                rs.getString("codigo_reservacion"),
                rs.getDouble("precio_facturado"));
               FINAL = temp;
            }   
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("consulta de habitacion reservada con exito");
        } catch (SQLException ex) {
            nn.setContentText("error al consultar habitacion reservada ");
            nn.showAndWait();
        }
        return FINAL;
    }
    public ArrayList<ContratoHabitacion> findOneContratoHabitacion(String codigo){
            ArrayList<ContratoHabitacion> array = new ArrayList<>();
        try {
           
            String sql  =  "SELECT * FROM contrato_habitaciones WHERE codigo_reservacion = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ContratoHabitacion temp = new ContratoHabitacion(
                        rs.getInt("id_contrato_habitacion"), 
                        rs.getInt("id_habitacion"),
                rs.getString("codigo_reservacion"),
                rs.getDouble("precio_facturado"));
                        
                        
                array.add(temp);
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("tabla habitaciones reservadas cargada con con exito servicio");
        } catch (SQLException ex) {
         nn.setContentText("error al cargar habitaciones reservadas");
         nn.showAndWait();
        }
         return array;
    }
}
