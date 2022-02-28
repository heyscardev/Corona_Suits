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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import modelo.Conexion;
import modelo.Direccion;
import modelo.Servicio;
import sistema_hotel1.Alertas;

/**
 *
 * @author heyscar
 */
public class CRUDDireccion {
    
       private static CRUDDireccion cth;
    private Conexion cn;
     private Alertas nn = new Alertas(Alert.AlertType.ERROR,"");
    //-----------------singletoooon---------------------
    private CRUDDireccion(){
        cn = Conexion.getInstance();
          nn.setTitle("errror");
          nn.setHeaderText("Coronoa Suites: Direccion Error!");
    }
     public static CRUDDireccion getInstance(){
        if(cth == null){
            cth = new CRUDDireccion();
        }
        return cth;
    }
    //----------------------crear nueva fila en la tabla -----------------
    public boolean create(Direccion temp){
        int indice_ciudad = 0;
        try {
            String sql = "INSERT INTO ciudades(estado_id,nombre)"
                    + "VALUES(?,?)";
             PreparedStatement ps ;
           ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1,temp.getId_estado());
            ps.setString(2, temp.getNombreCiudad());
           ps.executeUpdate();
         
        
            ps.close();
            cn.desconectar();
            sql = "SELECT MAX(id_ciudad) FROM ciudades";
            PreparedStatement ps3 = cn.conectar().prepareStatement(sql);
            
            ResultSet rs3 = ps3.executeQuery();
            while(rs3.next()){
                indice_ciudad = rs3.getInt(1);
            }
            rs3.close();
            ps3.close();
            cn.desconectar();
            System.out.println(Integer.toString(indice_ciudad));
            if(indice_ciudad != 0){
            sql = "INSERT INTO domicilio(id_ciudad,id_cliente,descripcion) "
                    + "VALUES(?,?,?)";
           
           PreparedStatement ps2 = cn.conectar().prepareStatement(sql);
            ps2.setInt(1,indice_ciudad);
                
            ps2.setInt(2, temp.getId_cliente());
            ps2.setString(3, temp.getDescripcionDomicilio());
          
           ps2.executeUpdate();
            ps2.close();
            cn.desconectar();
            System.out.println("domicilio registrado con exito");
             return true;}
        } catch (SQLException ex) {
          nn.setContentText("error al registrar el domicilio");
          nn.showAndWait();
        }
      return false;

    }
    //-----------lee todos los datos de la tabla--------
        public ArrayList<Direccion> readAll(){
             ArrayList<Direccion> array = new ArrayList<>();
        try {
           
            String sql  =  "SELECT  domicilio.id_domicilio AS id_direccion,domicilio.id_cliente AS id_cliente,domicilio.descripcion,\n" +
                            "ciudades.id_ciudad,ciudades.nombre AS nombre_ciudad, \n" +
                            "estados.id_estado,estados.nombre AS nombre_estado, \n" +
                            "paises.id_pais,paises.nombre AS nombre_pais \n" +
                            "FROM domicilio  INNER JOIN ciudades  ON domicilio.id_ciudad =ciudades.id_ciudad INNER JOIN estados ON ciudades.estado_id = estados.id_estado "
                    + "INNER JOIN paises ON paises.id_pais = estados.id_pais";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               Direccion temp = new Direccion(
                       rs.getInt("id_cliente"),
                       rs.getInt("id_pais"),
                       rs.getString("nombre_pais"),
                       rs.getInt("id_estado"),
                       rs.getString("nombre_estado"),
                       rs.getInt("id_ciudad"),
                       rs.getString("nombre_ciudad"),
                       rs.getInt("id_direccion"),
                       rs.getString("descripcion"));
                array.add(temp);
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("Direccion Cargado con exito cargada con exito ");
        } catch (SQLException ex) {
         nn.setContentText("error al cargar las direcciones");
         nn.showAndWait();
        }
         return array;
    }
        //actualizar datos de una fila en tabla---------------
    public void update(Servicio temp){
        try {
            String sql = "UPDATE FROM servicios SET nombre = ? precio = ?  descripcion = ? "
                    + "WHERE id_servicios";
            PreparedStatement  ps= cn.conectar().prepareStatement(sql);
            ps.setString(1,temp.getNombre());
              ps.setDouble(2,temp.getPrecio());
            ps.setString(3, temp.getDescripcion());
            ps.setInt(4,temp.getId_servicio());
            ps.executeUpdate();
            ps.close();
            cn.desconectar();
            System.out.println("actualizacion tipo servicios exitosa");
        } catch (SQLException ex) {
          nn.setContentText("error al actualizar tipo servicio");
          nn.showAndWait();
        }
    }
    public void delete(int id_direccion){
        try {
            String sql = "DELETE FROM domicilio WHERE id_domicilio = ?";
            PreparedStatement ps  = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id_direccion);
            ps.execute();
            ps.close();
            cn.desconectar();
            System.out.println("servico eliminado con exito");
        } catch (SQLException ex) {
            nn.setContentText("no se ha podido eliminar servicio");
            nn.showAndWait();
        }
        
    }
    public Direccion findOneid_cliente(int id){
             Direccion temp = null;
        try {
           
            String sql  =  "SELECT  domicilio.id_domicilio AS id_direccion,domicilio.id_cliente AS id_cliente,domicilio.descripcion,\n" +
                            "ciudades.id_ciudad,ciudades.nombre AS nombre_ciudad, \n" +
                            "estados.id_estado,estados.nombre AS nombre_estado, \n" +
                            "paises.id_pais,paises.nombre AS nombre_pais \n" +
                            "FROM domicilio  INNER JOIN ciudades  ON domicilio.id_ciudad =ciudades.id_ciudad INNER JOIN estados ON ciudades.estado_id = estados.id_estado "
                    + "INNER JOIN paises ON paises.id_pais = estados.id_pais "
                    + "WHERE domicilio.id_cliente = ? ";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
              temp = new Direccion(
                       rs.getInt("id_cliente"),
                       rs.getInt("id_pais"),
                       rs.getString("nombre_pais"),
                       rs.getInt("id_estado"),
                       rs.getString("nombre_estado"),
                       rs.getInt("id_ciudad"),
                       rs.getString("nombre_ciudad"),
                       rs.getInt("id_direccion"),
                       rs.getString("descripcion"));
               
            }
            rs.close();
            ps.close();
            cn.desconectar();
            System.out.println("Direccion Cargado con exito cargada con exito ");
        } catch (SQLException ex) {
         nn.setContentText("error al cargar las direcciones");
         nn.showAndWait();
        }
         return temp;
    }
    public ArrayList<Direccion> getpaises(){
         ArrayList<Direccion> array= new ArrayList<>();
        try {
               
               String sql = "SELECT *  FROM paises ORDER BY nombre";
               PreparedStatement ps = cn.conectar().prepareStatement(sql);
               ResultSet rs =ps.executeQuery();
               while(rs.next()){
                   Direccion temp = new Direccion(0, rs.getInt("id_pais"), rs.getString("nombre"), 0,"", 0,"", 0,"");
                   temp.setTextosString(temp.getNombrePais());
                   array.add(temp);
               }
               rs.close();
               ps.close();
               cn.desconectar();
               System.out.println("lectura de paises con exito");
               }catch (SQLException ex) {
               
               System.out.println("error al obtener listado de paises de la base de datos");
           }
return array;
}
     public ArrayList<Direccion> getEstados(int id_pais){
         ArrayList<Direccion> array= new ArrayList<>();
        try {
               
               String sql = "SELECT *  FROM estados  WHERE id_pais = ? ORDER BY nombre";
               PreparedStatement ps = cn.conectar().prepareStatement(sql);
               ps.setInt(1, id_pais);
               ResultSet rs =ps.executeQuery();
               while(rs.next()){
                   Direccion temp = new Direccion(0, 0, "", rs.getInt("id_estado"),rs.getString("nombre"), 0,"", 0,"");
                   temp.setTextosString(temp.getNombreEstado());
                   array.add(temp);
               }
               rs.close();
               ps.close();
               cn.desconectar();
               System.out.println("lectura de paises con exito");
               }catch (SQLException ex) {
               
               System.out.println("error al obtener listado de paises de la base de datos");
           }
return array;
}
}

    



