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
import modelo.ContratoServicio;

import sistema_hotel1.Alertas;

/**
 *
 * @author heyscar
 */
public class CRUDContratoServicio {
          private static CRUDContratoServicio cth;
    private Conexion cn;
     private Alertas nn = new Alertas(Alert.AlertType.ERROR,"");
    //-----------------singletoooon---------------------
    private CRUDContratoServicio(){
        cn = Conexion.getInstance();
          nn.setTitle("errror");
          nn.setHeaderText("Coronoa Suites: Servicios Error!");
    }
    public static CRUDContratoServicio getInstance(){
        if(cth == null){
            cth = new CRUDContratoServicio();
        }
        return cth;
    }
    //----------------------crear nueva fila en la tabla -----------------
    public void create(ContratoServicio temp){
        try {
            String sql = "INSERT INTO contrato_servicios(id_servicios,codigo_reservacion,cantidad,precioFacturado)"
                    + "VALUES(?,?,?,?)";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1,temp.getId_servicio());
            ps.setString(2, temp.getCodigoReservacion());
            ps.setInt(3, temp.getCantidad());
            ps.setDouble(4, temp.getPrecioUnitarioFacturado());
           ps.executeUpdate();
            ps.close();
            cn.desconectar();
            System.out.println("Servicio contratado  con exito");
        } catch (SQLException ex) {
          nn.setContentText("error al contratar el Servicio");
          nn.showAndWait();
        }
    }
    //-----------lee todos los datos de la tabla--------
        public ArrayList<ContratoServicio> readAll(){
             ArrayList<ContratoServicio> array = new ArrayList<>();
        try {
           
            String sql  =  "SELECT * FROM contrato_servicios";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ContratoServicio temp = new ContratoServicio(
                        rs.getInt("id_contrato_servicios"), 
                        rs.getInt("id_servicios"), 
                        rs.getString("codigo_reservacion"), 
                        rs.getInt("cantidad"),
                        rs.getDouble("precioFacturado")
                );
                        
                array.add(temp);
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("tabla servicios contratados con con exito servicio");
        } catch (SQLException ex) {
         nn.setContentText("error al cargar servicios");
         nn.showAndWait();
        }
         return array;
    }
         public ArrayList<ContratoServicio> readserviciosReservacion(String codigoReservacion){
             ArrayList<ContratoServicio> array = new ArrayList<>();
        try {
           
            String sql  =  "SELECT * FROM contrato_servicios where codigo_reservacion = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1, codigoReservacion);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               ContratoServicio temp = new ContratoServicio(
                        rs.getInt("id_contrato_servicios"), 
                        rs.getInt("id_servicios"), 
                        rs.getString("codigo_reservacion"), 
                        rs.getInt("cantidad"),
                        rs.getDouble("precioFacturado")
                );
                        
                array.add(temp);
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("tabla servicios contratados con con exito servicio");
        } catch (SQLException ex) {
         nn.setContentText("error al cargar servicios");
         nn.showAndWait();
        }
         return array;
    }
        //actualizar datos de una fila en tabla---------------
    public void update(ContratoServicio temp){
        try {
            String sql = "UPDATE FROM contrato_servicios SET id_servicios = ? cantidad = ? "
                    + "WHERE id_contrato_servicios";
            PreparedStatement  ps= cn.conectar().prepareStatement(sql);
            ps.setInt(1,temp.getId_servicio());
              ps.setInt(2,temp.getCantidad());
            ps.setInt(3, temp.getId_contrato_servicios());
          
            ps.executeUpdate();
            ps.close();
            cn.desconectar();
            System.out.println("actualizacion servicio contrado con exito ");
        } catch (SQLException ex) {
          nn.setContentText("error al modificar  servicio contratado");
          nn.showAndWait();
        }
    }
    //--------------------elimina fila de la tabla---------------------
    public void delete(int id){
        try {
            String sql = "DELETE FROM contrato_servicios WHERE id_contrato_servicios = ?";
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
    public boolean delete_reservacion(String Codigo){
           try {
            String sql = "DELETE FROM contrato_servicios WHERE codigo_reservacion = ?";
            PreparedStatement ps  = cn.conectar().prepareStatement(sql);
            ps.setString(1, Codigo);
            ps.execute();
            ps.close();
            cn.desconectar();
            System.out.println("servicios contratados de reservacion: "+Codigo+"   eliminados con exito");
            return true;
        } catch (SQLException ex) {
            nn.setContentText("no se ha podido eliminar servicio contratado");
            nn.showAndWait();
            return false;
        }
        
    }
    public ContratoServicio findOneid_contrato_servicio(int id){
       ContratoServicio FINAL  = null;
        try {
            String sql = "SELECT * FROM contrato_servicios WHERE id_contrato_servicios = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ContratoServicio temp = new ContratoServicio(
                        rs.getInt("id_contrato_servicios"), 
                        rs.getInt("id_servicios"), 
                        rs.getString("codigo_reservacion"), 
                        rs.getInt("cantidad"),
                        rs.getDouble("precioFacturado")
                );
                        
               FINAL = temp;
            }   
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("consulta de servicio contratado con exito");
        } catch (SQLException ex) {
            nn.setContentText("error al consultar servicio contratado");
            nn.showAndWait();
        }
        return FINAL;
    }
      public ArrayList<ContratoServicio> findOneServicios(String codigo){
             ArrayList<ContratoServicio> array = new ArrayList<>();
        try {
           
            String sql  =  "SELECT * FROM contrato_servicios WHERE codigo_reservacion = ?";
            
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               ContratoServicio temp = new ContratoServicio(
                        rs.getInt("id_contrato_servicios"), 
                        rs.getInt("id_servicios"), 
                        rs.getString("codigo_reservacion"), 
                        rs.getInt("cantidad"),
                        rs.getDouble("precioFacturado")
                );
                        
                array.add(temp);
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("tabla servicios contratados con con exito servicio");
        } catch (SQLException ex) {
         nn.setContentText("error al cargar servicios");
         nn.showAndWait();
        }
         return array;
    }
}
