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
import modelo.Servicio;
import modelo.TipoHabitacion;
import sistema_hotel1.Alertas;

/**
 *
 * @author heyscar
 */
public class CRUDServicio {
       private static CRUDServicio cth;
    private Conexion cn;
     private Alertas nn = new Alertas(Alert.AlertType.ERROR,"");
    //-----------------singletoooon---------------------
    private CRUDServicio(){
        cn = Conexion.getInstance();
          nn.setTitle("errror");
          nn.setHeaderText("Coronoa Suites: Servicios Error!");
    }
     public static CRUDServicio getInstance(){
        if(cth == null){
            cth = new CRUDServicio();
        }
        return cth;
    }
    //----------------------crear nueva fila en la tabla -----------------
    public boolean create(Servicio temp){
        try {
            String sql = "INSERT INTO servicios(nombre,precio,descripcion) "
                    + "VALUES(?,?,?)";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1,temp.getNombre());
            ps.setDouble(2, temp.getPrecio());
            ps.setString(3, temp.getDescripcion());
           ps.executeUpdate();
            ps.close();
            cn.desconectar();
            System.out.println("Servicio registrado con exito");
            return true;
        } catch (SQLException ex) {
          nn.setContentText("error al registrar el Servicio");
          nn.showAndWait();
          return false;
        }
    }
    //-----------lee todos los datos de la tabla--------
        public ArrayList<Servicio> readAll(){
             ArrayList<Servicio> array = new ArrayList<>();
        try {
           
            String sql  =  "SELECT * FROM servicios";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Servicio temp = new Servicio(
                        rs.getInt("id_servicios"), 
                        rs.getString("nombre"), 
                        rs.getDouble("precio"), 
                        rs.getString("descripcion"));
                        
                array.add(temp);
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("tabla cargada con exito servicio");
        } catch (SQLException ex) {
         nn.setContentText("error al cargar servicios");
         nn.showAndWait();
        }
         return array;
    }
        //actualizar datos de una fila en tabla---------------
    public boolean update(Servicio temp){
        try {
           
            String sql = "UPDATE  servicios SET nombre = ?, precio = ? , descripcion = ? WHERE id_servicios = ?";
            PreparedStatement  ps= cn.conectar().prepareStatement(sql);
            ps.setString(1,temp.getNombre());
              ps.setDouble(2,temp.getPrecio());
            ps.setString(3, temp.getDescripcion());
            ps.setInt(4,temp.getId_servicio());
            ps.executeUpdate();
            ps.close();
            cn.desconectar();
            System.out.println("actualizacion tipo servicios exitosa");
            return true;
        } catch (SQLException ex) {
          nn.setContentText("error al actualizar tipo servicio");
          nn.showAndWait();
          return false;
        }
    }
    public boolean delete(int id){
        try {
            String sql = "DELETE FROM servicios WHERE id_servicios = ?";
            PreparedStatement ps  = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ps.close();
            cn.desconectar();
            System.out.println("servico eliminado con exito");
            return true;
        } catch (SQLException ex) {
            nn.setContentText("no se ha podido eliminar servicio");
            nn.showAndWait();
            return false;
        }
        
    }
    public Servicio findOneid_servicio(int id){
       Servicio FINAL  = null;
       
        try {
            String sql = "SELECT * FROM servicios WHERE id_servicios = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
             
            while(rs.next()){
                 Servicio temp = new Servicio(
                        rs.getInt("id_servicios"), 
                        rs.getString("nombre"), 
                        rs.getDouble("precio"), 
                        rs.getString("descripcion")
                 );
                        
               FINAL = temp;
            }   
           
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("consulta de servicio con exito");
        } catch (SQLException ex) {
            nn.setContentText("error al consultar servicio");
            nn.showAndWait();
        }
        return FINAL;
    }
 
    public int getIdServicio(){
            int id = 0;
            try {
                
            String sql = "SELECT * FROM servicios ORDER BY id_servicios DESC LIMIT 1";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = rs.getInt("id_servicios");
            }
           
            System.out.println("ID servicios leido con exito ");
            } catch (SQLException ex) {
            System.out.println("Ocurrió un error al leer el ID");
            }
            return id;
    }
    
}
