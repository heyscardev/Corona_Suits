
package modelo.CRUDS;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert.AlertType;
import modelo.Cliente;
import modelo.Conexion;
import sistema_hotel1.Alertas;

/**
  @author heyscar
 */
public class CRUDCliente {
private static CRUDCliente cc;
private Alertas nn = new Alertas(AlertType.ERROR,"");
    Conexion cn ;
    private CRUDCliente(){
        cn = Conexion.getInstance();
          nn.setTitle("errror");
          nn.setHeaderText("Coronoa Suites: Cliente Error!");
    }
    public static CRUDCliente getInstance(){
        if(cc == null){
            cc = new CRUDCliente();
        }
        return cc;
    }
    public boolean create(Cliente cliente){
       
        try {
            String sql = "INSERT INTO clientes(cedula,nombre,apellido,sexo,fecha_nacimiento) values(?,?,?,?,?)";
            PreparedStatement ps ;
            ps = cn.conectar().prepareStatement(sql);
          ps.setString(1, cliente.getCedula());
          ps.setString(2, cliente.getNombre());
          ps.setString(3, cliente.getApellido());
          ps.setString(4, cliente.getSexo().substring(0, 1));
          ps.setDate(5, Date.valueOf(cliente.getFecha_nacimiento()));
          ps.executeUpdate();
         
          ps.close();
          cn.desconectar();
            System.out.println("Registro Exitoso");
            return true;
        } catch (SQLException ex) {
          nn.setContentText("error al registrar el cliente");
        
          nn.showAndWait();
          
        }
        return false;
    }
    public void delete(int id){
         
            String sql = "DELETE FROM clientes WHERE id_cliente = ?";
            PreparedStatement ps ;
              try {
                  ps = cn.conectar().prepareStatement(sql);
                  ps.setInt(1, id);
                  ps.execute();
                  ps.close();
                  cn.desconectar();
                  System.out.println("borrado con exito");
              } catch (SQLException ex) {
              
          nn.setContentText("error al eliminar el cliente");
          nn.showAndWait();    
              }
    }
    public boolean update(Cliente cliente){
            
            String sql = "UPDATE  clientes SET cedula = ? ,nombre = ? ,apellido = ?, sexo = ?, fecha_nacimiento = ? WHERE id_cliente = ?";
            PreparedStatement ps ;
              try {
                  ps = cn.conectar().prepareStatement(sql);
                  ps.setString(1, cliente.getCedula());
          ps.setString(2, cliente.getNombre());
          ps.setString(3, cliente.getApellido());
          ps.setString(4, cliente.getSexo());
          ps.setDate(5, Date.valueOf(cliente.getFecha_nacimiento()));
          ps.setInt(6, cliente.getId_cliente());
               ps.executeUpdate();
          ps.close();
          cn.desconectar();
            System.out.println("actualizacion exitosa");
            return true;
              } catch (SQLException ex) {
               
          nn.setContentText("error al actualizar datos de  el cliente");
       
          nn.showAndWait();   
          return false;
              }
    }
    public ArrayList<Cliente> readAll(){
        ArrayList<Cliente> arrayClientes = new ArrayList<>();
        try {
            String sql = "SELECT  *  FROM clientes ";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            Cliente cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("sexo")
             );
             arrayClientes.add(cliente);
              
            }
            rs.close();
            ps.close();
            cn.desconectar();
            
        } catch (SQLException ex) {
            nn.setContentText("error al consultar  clientes");
          nn.showAndWait();  
        }
        return arrayClientes;
    }
    public  ArrayList<Cliente> findGlobal(String consulta){
         ArrayList<Cliente> arrayClientes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM clientes WHERE nombre LIKE ? OR apellido LIKE ?  OR cedula LIKE ? OR id_cliente = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1, consulta+"%");
            ps.setString(2, consulta+"%");
             ps.setString(3, "%"+consulta+"%");
             ps.setString(4, consulta+"%");
            
            ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Cliente cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("sexo")
             );
               arrayClientes.add(cliente);
        }
      
             rs.close();
             ps.close();
             cn.desconectar();
        } catch (SQLException ex) {
              nn.setContentText("error al consultar  clientes");
          nn.showAndWait();  
        }
        return arrayClientes;
    }
    
    public  Cliente findOneId(int id){
      Cliente clientefinal = null;
        try {
            String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Cliente cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("sexo")
                    
             );
             clientefinal = cliente;
            
        }
         rs.close();
             ps.close();
             cn.desconectar();
        } catch (SQLException ex) {
              nn.setContentText("error al consultar  clientes");
          nn.showAndWait();  
        }
        return clientefinal;
    }
    public  Cliente findOnCedula(String CEDULA){
      Cliente clientefinal = null;
        try {
            String sql = "SELECT * FROM clientes WHERE cedula = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1, CEDULA);
            
            ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Cliente cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("sexo")
                    
             );
             clientefinal = cliente;
            
        }
         rs.close();
             ps.close();
             cn.desconectar();
        } catch (SQLException ex) {
              nn.setContentText("error al consultar  clientes");
          nn.showAndWait();  
        }
        return clientefinal;
    }
    public Cliente getMaxRegistroCliente(){
        int id_cliente = 0;
         Cliente clientefinal = null;
        try {
            String sql = "SELECT MAX(id_cliente) FROM clientes  ";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
           
            
            ResultSet rs = ps.executeQuery();
             while(rs.next()){
            id_cliente = rs.getInt(1);
                    
             }
             rs.close();
             ps.close();
             cn.desconectar();
             if(id_cliente != 0){
             sql = "SELECT * FROM clientes WHERE id_cliente = ?";
             ps = cn.conectar().prepareStatement(sql);
             ps.setInt(1, id_cliente);
             rs = ps.executeQuery();
        while(rs.next()){
            Cliente cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("cedula"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("sexo")
                    
             );
             clientefinal = cliente;
            
        }
         rs.close();
             ps.close();
             cn.desconectar();
             }else{System.out.println("error en la obtencion del ultimo cliente");}
             
        } catch (SQLException ex) {
              nn.setContentText("error al consultar  clientes");
          nn.showAndWait();  
        }
        return clientefinal;
    }
    
    public int getIdCliente(){
            int id = 0;
            try {
                
            String sql = "SELECT * FROM clientes ORDER BY id_cliente DESC LIMIT 1";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = rs.getInt("id_cliente");
            }
           
            System.out.println("ID leido con exito ");
            } catch (SQLException ex) {
            System.out.println("Ocurrió un error al leer el ID");
            }
            return id;
    }
}
