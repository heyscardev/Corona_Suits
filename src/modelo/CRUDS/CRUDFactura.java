
package modelo.CRUDS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import modelo.Cliente;
import modelo.Cliente;
import modelo.Conexion;
import modelo.Conexion;
import modelo.Factura;
import modelo.Factura;
import modelo.Servicio;
import modelo.Servicio;
import modelo.TipoHabitacion;
import modelo.TipoHabitacion;
import sistema_hotel1.Alertas;


public class CRUDFactura {
    
    private static CRUDFactura cr;
    private Alertas nn = new Alertas(Alert.AlertType.ERROR,"");
    Conexion cn;
    
    private CRUDFactura(){
        cn = Conexion.getInstance();
        nn.setTitle("errror");
        nn.setHeaderText("Coronoa Suites: Factura Error!");
    }
    
    public static CRUDFactura getInstance(){
        if(cr == null) cr = new CRUDFactura();
        return cr;
    }
    
    public boolean create(Factura factura){
       
       try {
           
           String sql = "INSERT INTO factura(id_factura, fecha_factura, codigo_reservacion) VALUES (?,?,?)";
           PreparedStatement ps;
           ps = cn.conectar().prepareStatement(sql);
           
           
           
           ps.setInt(1, factura.getId_factura());
           ps.setTimestamp(2,Timestamp.valueOf(factura.getFecha_factura()));
           ps.setString(3,factura.getCodigoReservacion());
           
           ps.executeUpdate();
           ps.close();
           
           cn.desconectar();
           System.out.println("Registro de factura exitoso");
           return true;
           
           } catch (SQLException ex) {
            nn.setContentText("Error al registrar la factura");
            nn.showAndWait();
        }
        return false;
    }
    
    
    public void delete(int codigo){
         
    String sql = "DELETE FROM factura WHERE id_factura = ?";
    PreparedStatement ps;
      
    try {
          ps = cn.conectar().prepareStatement(sql);
          ps.setInt(1, codigo);
          ps.execute();
          ps.close();
          
          cn.desconectar();
          System.out.println("Borrado de factura con exito");
        }catch (SQLException ex){
          nn.setContentText("Error al eliminar la factura.");
          nn.showAndWait();    
        }
    }
    public void delete(String codigo){
         
    String sql = "DELETE FROM factura WHERE codigo_reservacion = ?";
    PreparedStatement ps;
      
    try {
          ps = cn.conectar().prepareStatement(sql);
          ps.setString(1, codigo);
          ps.execute();
          ps.close();
          
          cn.desconectar();
          System.out.println("Borrado de factura con exito");
        }catch (SQLException ex){
          nn.setContentText("Error al eliminar la factura.");
          nn.showAndWait();    
        }
    }
    
    public ArrayList<Factura> readAll(){
        System.out.println("Llegó hasta aquí");
        ArrayList<Factura> array = new ArrayList<>();
        
        try {
            String sql = "SELECT  *  FROM factura";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
            Factura factura = new Factura(
            rs.getInt("id_factura"), 
            rs.getTimestamp("fecha_factura").toLocalDateTime(),
           
            rs.getString("codigo_reservacion"));
            
            array.add(factura);
            }
            
            rs.close();
            ps.close();
            
            cn.desconectar();
            System.out.println("Facturas consultadas con exito");
            }catch (SQLException ex){
            nn.setContentText("Error al consultar las facturas");
            nn.showAndWait();  
            }
        return array;
    }
    
    public Factura findOnId(int id){
        
        Factura facturaFinal = null;
        
        try {
            
            String sql = "SELECT * FROM factura WHERE id_factura = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
        while(rs.next()){
                
            Factura factura = new Factura(
            rs.getInt("id_factura"), 
            rs.getTimestamp("fecha_factura").toLocalDateTime(),
            
            rs.getString("codigo_reservacion"));
            
            facturaFinal = factura;
        }
        
        rs.close();
        ps.close();
        cn.desconectar();
        
        }catch (SQLException ex){
          nn.setContentText("Error al consultar la factura!");
          nn.showAndWait();  
        }
        return facturaFinal;
    }
    
    public Cliente findOnIdCliente(int id){
        
        Cliente clientefinal = null;
        try {
            
        String sql = "SELECT * FROM clientes c INNER JOIN reservaciones r ON c.id_cliente = r.id_cliente INNER JOIN factura f ON r.codigo_reservacion = f.codigo_reservacion WHERE f.id_factura = ?";
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
                rs.getString("sexo"));
        
        clientefinal = cliente;
        }
        
        rs.close();
        ps.close();
        cn.desconectar();
        
        }catch (SQLException ex){
            
        nn.setContentText("error al consultar  clientes");
        nn.showAndWait();
        
        }
        return clientefinal;
    }
    
    public TipoHabitacion findOnIdHabitacion (int id){
    
        TipoHabitacion habitacionFinal = null;
        
        try{
            
        String sql  =  "SELECT TH.* FROM tipos_habitaciones th INNER JOIN habitaciones h ON th.id_tipos_habitaciones = h.id_tipos_habitaciones INNER JOIN contrato_habitaciones ch ON h.id_habitacion = ch.id_habitacion INNER JOIN reservaciones r on ch.codigo_reservacion = r.codigo_reservacion INNER JOIN factura f ON r.codigo_reservacion = f.codigo_reservacion WHERE f.id_factura = ?";
        PreparedStatement ps = cn.conectar().prepareStatement(sql);
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
        TipoHabitacion temp = new  TipoHabitacion(
        rs.getInt("id_tipos_habitaciones"), 
        rs.getString("nombre"), 
        rs.getString("descripcion"), 
        rs.getInt("cant_max_personas"), 
        rs.getDouble("costo"));
        
        habitacionFinal = temp;
        }
        rs.close();
        ps.close();
        cn.desconectar();
        }catch (SQLException ex){
            
        nn.setContentText("Error al consultar habitaciones");
        nn.showAndWait();
        
        }
    
        return habitacionFinal;
    }
    
    public Servicio findOnIdServicio (int id){
        
        Servicio servicioFinal = null;
        
        try{
            
        String sql  =  "SELECT S.* FROM servicios s INNER JOIN contrato_servicios cs ON s.id_servicios = cs.id_servicios INNER JOIN reservaciones r ON cs.codigo_reservacion = r.codigo_reservacion INNER JOIN factura f ON r.codigo_reservacion = f.codigo_reservacion WHERE f.id_factura = ?";
        PreparedStatement ps = cn.conectar().prepareStatement(sql);
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
        
        Servicio temp = new Servicio(
        rs.getInt("id_servicios"), 
        rs.getString("nombre"), 
        rs.getDouble("precio"), 
        rs.getString("descripcion"));
        
        servicioFinal = temp;
        }
        rs.close();
        ps.close();
        cn.desconectar();
        }catch (SQLException ex){
            
        nn.setContentText("Error al consultar habitaciones");
        nn.showAndWait();
        
        }
    
        return servicioFinal;
    }
    
    public  ArrayList<Factura> findGlobal(String consulta){
        
        ArrayList<Factura> arrayFactura = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM factura f INNER JOIN reservaciones r ON f.codigo_reservacion = r.codigo_reservacion INNER JOIN clientes c ON r.id_cliente = c.id_cliente WHERE f.id_factura LIKE ? OR f.fecha_factura LIKE ?  OR f.codigo_reservacion LIKE ? OR c.nombre LIKE ? OR c.cedula LIKE ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            
            ps.setString(1, consulta+"%");
            ps.setString(2, consulta+"%");
            ps.setString(3, consulta+"%");
            ps.setString(4, consulta+"%");
            ps.setString(5, "%"+consulta+"%");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Factura factura = new Factura(
                rs.getInt("id_factura"), 
                rs.getTimestamp("fecha_factura").toLocalDateTime(),
               
                rs.getString("codigo_reservacion"));
            
                arrayFactura.add(factura);}
      
                rs.close();
                ps.close();
                cn.desconectar();
        } catch (SQLException ex) {
        nn.setContentText("Error al consultar facturas!");
        nn.showAndWait();  
        }
        return arrayFactura;
    }
    
    
    
    public int generarFactura(int id_cliente, LocalDate fecha_factura){
     int id = id_cliente;
       
       int hora = LocalDateTime.now().getHour();
       int minuto = LocalDateTime.now().getMinute();
       int segundo = LocalDateTime.now().getMinute();
       int agno = LocalDateTime.now().getYear();
       int mes = LocalDateTime.now().getMonthValue();
       int dia = LocalDateTime.now().getDayOfMonth();
       
       int factura = (id * (hora + minuto + segundo + agno + mes + dia))/10; 
       return factura; 
     }
    public Factura findOnCodigoReservacion(String codigo){
        
        Factura facturaFinal = null;
        
        try {
            
            String sql = "SELECT * FROM factura WHERE codigo_reservacion = ?";
            PreparedStatement ps = cn.conectar().prepareStatement(sql);
            ps.setString(1, codigo);
            
            ResultSet rs = ps.executeQuery();
            
        while(rs.next()){
                
            Factura factura = new Factura(
            rs.getInt("id_factura"), 
            rs.getTimestamp("fecha_factura").toLocalDateTime(),
            
            rs.getString("codigo_reservacion"));
            
            facturaFinal = factura;
        }
        
        rs.close();
        ps.close();
        cn.desconectar();
        
        }catch (SQLException ex){
          nn.setContentText("Error al consultar la factura!");
          nn.showAndWait();  
        }
        return facturaFinal;
    }
}
