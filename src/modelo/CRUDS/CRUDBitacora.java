/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.CRUDS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Bitacora;
import modelo.Conexion;

/**
 *
 * @author heyscar
 */
public class CRUDBitacora {
    private Conexion cn;
    private static CRUDBitacora crud;
    
    private CRUDBitacora(){
        cn = Conexion.getInstance();
    }
    public static CRUDBitacora getInstance(){
        if(crud == null){
            crud = new CRUDBitacora();
        }
        return crud;
    }
    public void create(LocalDateTime hoy,String modulo, String usuario){
        try {
            String sql = "INSERT INTO `bitacora`(`fecha`, `accion`, `usuario`) VALUES(?,?,?)";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(hoy));
            ps.setString(2,modulo);
            ps.setString(3,usuario);
            ps.executeUpdate();
            
            System.out.println("Bitacora registrada con exito ");
        } catch (SQLException ex) {
            System.out.println("ocurrio un error al guardar el registro");
        }
    }
    
    public void createBCliente(){
        try {
            String sql = "INSERT INTO `bitacora_clientes`(`id_bitacora`, `id_cliente`) VALUES (?, ?)";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, this.getIdBitacora());
            ps.setInt(2, CRUDCliente.getInstance().getIdCliente());
            ps.executeUpdate();
            System.out.println("Bitacora cliente registrada con exito ");
        } catch (SQLException ex) {
            System.out.println("ocurrio un error al guardar el registro");
        }
    }
    
    public void createBHabitacion(){
        try {
            String sql = "INSERT INTO `bitacora_habitacion`(`id_bitacora`, `id_habitacion`) VALUES (?, ?)";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, this.getIdBitacora());
            ps.setInt(2, CRUDHabitacion.getInstance().getIdHabitacion());
            ps.executeUpdate();
            System.out.println("Bitacora habitación registrada con exito ");
        } catch (SQLException ex) {
            System.out.println("ocurrio un error al guardar el registro");
        }
    }
    
    public void createBServicio(){
        try {
            String sql = "INSERT INTO `bitacora_servicios`(`id_bitacora`, `id_servicios`) VALUES (?, ?)";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, this.getIdBitacora());
            ps.setInt(2, CRUDServicio.getInstance().getIdServicio());
            ps.executeUpdate();
            System.out.println("Bitacora servicio registrada con exito ");
        } catch (SQLException ex) {
            System.out.println("ocurrio un error al guardar el registro");
        }
    }
    
    public int getIdBitacora(){
            int id = 0;
            try {
                
            String sql = "SELECT * FROM bitacora ORDER BY id_bitacora DESC LIMIT 1";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = rs.getInt("id_bitacora");
            }
           
            System.out.println("ID bitacora leido con exito ");
            } catch (SQLException ex) {
            System.out.println("Ocurrió un error al leer el ID");
            }
            return id;
    }
    
        public ArrayList<Bitacora> read(){
            ArrayList<Bitacora> array = new ArrayList<>();
            try {
            String sql = "SELECT * FROM bitacora ORDER BY bitacora.fecha DESC";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
           int  num = 1;
            while(rs.next()){
                if(rs.getTimestamp("fecha")== null ){
                    
                }
                Bitacora temp = new Bitacora(
                        rs.getInt("id_bitacora"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("accion"),
                        rs.getString("usuario")
                );
                array.add(temp);
                
                System.err.println(num);
                num++;
            }
           
            System.out.println("bitacora leida con exito ");
        } catch (SQLException ex) {
            System.out.println("ocurrio un error al leer el registro");
        }
            return array;
        }
        
        public ArrayList<Bitacora> readDate(String date){
            ArrayList<Bitacora> arrayFecha = new ArrayList<>();
            try {
            String sql = "SELECT * FROM bitacora WHERE fecha LIKE ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1, date+"%");
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Bitacora temp = new Bitacora(
                        rs.getInt("id_bitacora"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("accion"),
                        rs.getString("usuario")                
                );
                arrayFecha.add(temp);
            }
           
            System.out.println("bitacora leida con exito ");
        } catch (SQLException ex) {
            System.out.println("ocurrio un error al leer el registro");
        }
            return arrayFecha;
        }
    
}
