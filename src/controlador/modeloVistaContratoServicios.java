/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.ContratoServicio;
import modelo.Servicio;

/**
 *
 * @author heyscar
 */
public class  modeloVistaContratoServicios  extends ContratoServicio{
   
    private Servicio serv;

    public modeloVistaContratoServicios(Servicio serv,ContratoServicio cont) {
       super(cont.getId_contrato_servicios(), cont.getId_servicio(), cont.getCodigoReservacion(), cont.getCantidad(),cont.getPrecioUnitarioFacturado());
        this.serv = serv;
        
      
        
    }

   public String getNombre(){
       return serv.getNombre();
   }
   public String getDescripcion(){
       return serv.getDescripcion();
   }
   public double getPrecio(){
       return serv.getPrecio();
   }
   
   public double getPrecioTotal(){
       return getPrecio()*getCantidad();
   }
    
    
}
