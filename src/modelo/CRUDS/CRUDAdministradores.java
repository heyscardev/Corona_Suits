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
import modelo.Administrador;
import modelo.Administrador;
import modelo.Conexion;
import sistema_hotel1.Alertas;

/**
 *
 * @author heyscar
 */
public class CRUDAdministradores {
private static CRUDAdministradores cru;

    private CRUDAdministradores() {
        
    }
    public static CRUDAdministradores getInstance(){
        if(cru == null){
            cru = new CRUDAdministradores();
        }
        return cru;
    }

    public boolean UPDATE(Administrador admin) {
        try {
            String sql = "UPDATE  administradores SET contraseña = ? WHERE id_administrador = ?";
            PreparedStatement  ps = Conexion.getInstance().conectar().prepareStatement(sql);
            ps.setInt(2, admin.getId());
            ps.setString(1, admin.getContraseña());
                ps.execute();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(CRUDAdministradores.class.getName()).log(Level.SEVERE, null, ex);
            }
                 
           return false;
           
        
    }


    public Administrador READ(int id) {
        Administrador admin = null;
        try {
            String sql = "SELECT *  FROM administradores WHERE id_administrador = ?";
            PreparedStatement  ps = Conexion.getInstance().conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               admin = new Administrador(
                        rs.getInt("id_administrador"), 
                        rs.getString("nombre_usuario"),
                        rs.getString("contraseña")
                );
                 
            }
           
            rs.close();
            ps.close();
            Conexion.getInstance().desconectar();
             return admin;
        } catch (SQLException ex) {
            Alertas ad = new Alertas(Alert.AlertType.ERROR);
            ad.showAndWait();
            return null;
        }
    }


    public boolean DELETE() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList READALL() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
