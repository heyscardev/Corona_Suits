
package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Factura {
    
    private int id_factura;
    private LocalDateTime fecha_factura;
  
    private String  codigoReservacion;

    public Factura(int id_factura, LocalDateTime fecha_factura,  String codigoReservacion) {
        this.id_factura = id_factura;
        this.fecha_factura = fecha_factura;
        
        this.codigoReservacion = codigoReservacion;
    }
     

    
    public int getId_factura() {
        return id_factura;
    }

  

    public LocalDateTime getFecha_factura() {
        return fecha_factura;
    }

  

    public String getCodigoReservacion() {
        return codigoReservacion;
    }

   

 
            
}

