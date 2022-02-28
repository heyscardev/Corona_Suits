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
import java.util.ArrayList;
import javafx.scene.control.Alert;
import modelo.Cliente;
import modelo.Conexion;
import modelo.PreguntaSeguridad;
import sistema_hotel1.Alertas;

/**
 *
 * @author heyscar
 */
public class CRUDPreguntaSeguridad {
     private static CRUDPreguntaSeguridad cps;
      private Alertas nn = new Alertas(Alert.AlertType.ERROR,"");
    Conexion cn ;
    private CRUDPreguntaSeguridad(){
        cn = Conexion.getInstance();
          nn.setTitle("errror");
          nn.setHeaderText("Coronoa Suites: Cliente Error!");
    }
    public static CRUDPreguntaSeguridad getInstance(){
        if(cps == null){
            cps = new CRUDPreguntaSeguridad();
        }
        return cps;
    }
    public boolean create(PreguntaSeguridad psi){
       
        try {
            String sql = "INSERT INTO preguntas_seguridad(id_Administrador,pregunta,respuesta) values(?,?,?)";
            PreparedStatement ps ;
            ps = cn.conectar().prepareStatement(sql);
          ps.setInt(1, psi.getId_administrador());
          ps.setString(2, psi.getPregunta());
          ps.setString(3,psi.getRespuesta());
         
          ps.executeUpdate();
          ps.close();
          cn.desconectar();
            System.out.println("Registro Exitoso");
            return true;
        } catch (SQLException ex) {
          nn.setContentText("error al registrar pregunta de seguridad");
        
          nn.showAndWait();
          
        }
        return false;
    }
    public void delete(int id){
         
            String sql = "DELETE FROM preguntas_seguridad WHERE id_pregunta = ?";
            PreparedStatement ps ;
              try {
                  ps = cn.conectar().prepareStatement(sql);
                  ps.setInt(1, id);
                  ps.execute();
                  ps.close();
                  cn.desconectar();
                  System.out.println("borrado con exito");
              } catch (SQLException ex) {
              
          nn.setContentText("error al eliminar el pregunta de seguridad");
          nn.showAndWait();    
         }
    }
    public boolean update(PreguntaSeguridad psi){
            
            String sql = "UPDATE  preguntas_seguridad SET pregunta = ? ,respuesta = ? WHERE id_pregunta = ? AND id_administrador = ?";
            PreparedStatement ps ;
              try {
                  ps = cn.conectar().prepareStatement(sql);
                  ps.setString(1, psi.getPregunta());
          ps.setString(2, psi.getRespuesta());
          ps.setInt(3,psi.getId());
          ps.setInt(4, psi.getId_administrador());
         
               ps.executeUpdate();
          ps.close();
          cn.desconectar();
            System.out.println("actualizacion exitosa");
            return true;
              } catch (SQLException ex) {
               
          nn.setContentText("error al actualizar datos pregunta");
       
          nn.showAndWait();   
          return false;
              }
    }
    public ArrayList<PreguntaSeguridad> read(int id_administrador){
        ArrayList<PreguntaSeguridad> arraypreguntas = new ArrayList<>();
        try {
            String sql = "SELECT  *  FROM preguntas_seguridad WHERE id_administrador = ? ";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id_administrador);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
           PreguntaSeguridad psi= new PreguntaSeguridad(rs.getInt("id_pregunta"),rs.getInt("id_administrador"), rs.getString("pregunta"), rs.getString("respuesta"));
            
             arraypreguntas.add(psi);
              
            }
            rs.close();
            ps.close();
            cn.desconectar();
            
        } catch (SQLException ex) {
            nn.setContentText("error al consultar  preguntas");
          nn.showAndWait();  
        }
        return arraypreguntas;
    }
 

}
