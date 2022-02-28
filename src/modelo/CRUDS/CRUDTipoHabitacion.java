
package modelo.CRUDS;

import com.sun.deploy.panel.DeleteFilesDialog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import modelo.Conexion;
import modelo.TipoHabitacion;
import sistema_hotel1.Alertas;

/**
 *
 * @author heyscar
 */
public class CRUDTipoHabitacion {
    private static CRUDTipoHabitacion cth;
    private Conexion cn;
     private Alertas nn = new Alertas(Alert.AlertType.ERROR,"");
    //-----------------singletoooon---------------------
    private CRUDTipoHabitacion(){
        cn = Conexion.getInstance();
          nn.setTitle("errror");
          nn.setHeaderText("Coronoa Suites: Habitacion Error!");
    }
     public static CRUDTipoHabitacion getInstance(){
        if(cth == null){
            cth = new CRUDTipoHabitacion();
        }
        return cth;
    }
    //----------------------crear nueva fila en la tabla -----------------
    public boolean create(TipoHabitacion temp){
        try {
            String sql = "INSERT INTO tipos_habitaciones(nombre,descripcion,cant_max_personas,costo)"
                    + "VALUES(?,?,?,?)";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1,temp.getNombre());
            ps.setString(2, temp.getDescripcion());
            ps.setInt(3, temp.getCant_max_personas());
            ps.setDouble(4, temp.getCosto());
            ps.executeUpdate();
            ps.close();
            cn.desconectar();
            System.out.println("tipo de habitacion registrada con exito");
            return true;
        } catch (SQLException ex) {
          nn.setContentText("error al registrar el tipo de habitacion");
          nn.showAndWait();
          return false;
        }
    }
    //-----------lee todos los datos de la tabla--------
        public ArrayList<TipoHabitacion> readAll(){
             ArrayList<TipoHabitacion> array = new ArrayList<>();
        try {
           
            String sql  =  "SELECT * FROM tipos_habitaciones";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                TipoHabitacion temp = new  TipoHabitacion(
                        rs.getInt("id_tipos_habitaciones"), 
                        rs.getString("nombre"), 
                        rs.getString("descripcion"), 
                        rs.getInt("cant_max_personas"), 
                        rs.getDouble("costo"));
                array.add(temp);
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("tabla cargada con exito tipo habitacion");
        } catch (SQLException ex) {
         nn.setContentText("error al cargar tipos de habitaciones");
         nn.showAndWait();
        }
         return array;
    }
        //actualizar datos de una fila en tabla---------------
    public boolean update(TipoHabitacion temp){
        try {
            String sql = "UPDATE  tipos_habitaciones SET nombre = ?, descripcion = ? ,cant_max_personas = ? ,costo = ? WHERE id_tipos_habitaciones=?";
            PreparedStatement  ps= cn.conectar().prepareStatement(sql);
            ps.setString(1,temp.getNombre());
            ps.setString(2, temp.getDescripcion());
            ps.setInt(3,temp.getCant_max_personas());
            ps.setDouble(4,temp.getCosto());
            ps.setInt(5, temp.getId_tipos_habitaciones());
                 ps.executeUpdate();
            ps.close();
            cn.desconectar();
            System.out.println("actualizacion tipo habitacion con exito");
            return true;
        } catch (SQLException ex) {
          nn.setContentText("error al actualizar tipo de habitaciones");
          nn.showAndWait();
          return false;
        }
    }
    public boolean delete(int id){
        try {
            String sql = "DELETE FROM tipos_habitaciones WHERE id_tipos_habitaciones = ?";
            PreparedStatement ps  = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ps.close();
            cn.desconectar();
            System.out.println("tipo de habitacion eliminada con exito");
            return true;
        } catch (SQLException ex) {
            nn.setContentText("tipo de habitacion eliminada con exito");
            nn.showAndWait();
            return false;
        }
        
    }
    public TipoHabitacion findOneid_tipoHabitacion(int id){
       TipoHabitacion FINAL  = null;
        try {
            String sql = "SELECT * FROM  tipos_habitaciones   WHERE id_tipos_habitaciones = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                TipoHabitacion th = new TipoHabitacion(
                        rs.getInt("id_tipos_habitaciones"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("cant_max_personas"),
                        rs.getDouble("costo"));
                         FINAL = th;
            }   
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("consulta de tipo de habitacion con exito");
        } catch (SQLException ex) {
            nn.setContentText("error al consultar tipo de habitacion");
            nn.showAndWait();
        }
        return FINAL;
    }
    public TipoHabitacion getMaxTipoHabitacion(){
         int max_id = 0; TipoHabitacion u = null;
        try {
         
            String sql = "SELECT MAX(id_tipos_habitaciones) from tipos_habitaciones ";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
              max_id = rs.getInt(1);
                
            }
            rs.close();
            ps.close();
            cn.desconectar();
        } catch (SQLException ex) {
            System.out.println("modelo.CRUDS.CRUDTipoHabitacion.getMaxTipoHabitacion()");
        }
        if(max_id != 0){
              try {
                 
           
            String sql  =  "SELECT * FROM tipos_habitaciones WHERE id_tipos_habitaciones= ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, max_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                TipoHabitacion temp = new  TipoHabitacion(
                        rs.getInt("id_tipos_habitaciones"), 
                        rs.getString("nombre"), 
                        rs.getString("descripcion"), 
                        rs.getInt("cant_max_personas"), 
                        rs.getDouble("costo"));
                u = temp;
            }
            rs.close();
            ps.close();
            cn.desconectar();
            return u;
            
        } catch (SQLException ex) {
         nn.setContentText("error al cargar tipos de habitaciones");
         nn.showAndWait();
        }
        }
        return null;
    }
}
