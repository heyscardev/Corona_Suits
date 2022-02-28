
package controlador;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.CRUDS.CRUDCliente;
import modelo.CRUDS.CRUDContratoHabitacion;
import modelo.CRUDS.CRUDContratoServicio;
import modelo.CRUDS.CRUDHabitacion;
import modelo.CRUDS.CRUDReservacion;
import modelo.CRUDS.CRUDServicio;
import modelo.CRUDS.CRUDTipoHabitacion;
import modelo.Cliente;
import modelo.ContratoHabitacion;
import modelo.ContratoServicio;
import modelo.Habitacion;
import modelo.Reservacion;
import modelo.Servicio;
import modelo.TipoHabitacion;


public class MetodosClientes {
    private static MetodosClientes mt; 
    private ControlSesion cs;
    private CRUDCliente cc;
    private CRUDReservacion cr;
     private CRUDContratoHabitacion hab;
     private CRUDHabitacion ch;
     private CRUDTipoHabitacion ctp;
    private CRUDContratoServicio serv;
    private CRUDServicio cserv;
    
    private MetodosClientes(){
        cs = ControlSesion.getInstance();
        cc = CRUDCliente.getInstance();
        cr = CRUDReservacion.getInstance();
        ch= CRUDHabitacion.getInstance();
        ctp = CRUDTipoHabitacion.getInstance();
                
        serv  = CRUDContratoServicio.getInstance();
        hab= CRUDContratoHabitacion.getInstance();
        cserv = CRUDServicio.getInstance();
    }
    /*metodo singletooon que mantiene uno sola instancia de metodosclientes*/
    public static MetodosClientes getInstance(){
        if(mt == null)
        {
            mt = new MetodosClientes();  
        }
        return mt;
    }
    /*----------retorna el cliente que va a ser usado en interfaz usuario---*/
    public Cliente getClienteReservacion(){
        if(cs.validadorCliente()){
            return cc.findOneId(cs.getIDUser());
        }
        return null; 
    }
    /*--retorna  datos de la reservacion-------------------------------*/
    public Reservacion getreservacion(){
          if(cs.validadorCliente()){
            return cr.findOneCodigo_reservacion(cs.getCodigoReservacion());
        }
        return null; 
    }
      /*--retorna  datos de los servicios contratados-------------------------------*/
    public ObservableList<modeloVistaContratoServicios>  getServiciosUser(){
        if(cs.validadorCliente()){
            ArrayList<modeloVistaContratoServicios> vistaarray = new ArrayList<>();
        for(ContratoServicio conserv:serv.readserviciosReservacion(cs.getCodigoReservacion())){
            ContratoServicio temporalconserv=  conserv;
            Servicio temporalservicio = cserv.findOneid_servicio(conserv.getId_servicio());
            modeloVistaContratoServicios  ee= new modeloVistaContratoServicios(temporalservicio,temporalconserv );
        vistaarray.add(ee);
        }
    return FXCollections.observableList(vistaarray);
        }
        return null;
    }
     /*--retorna  datos de los servicios contratados-------------------------------*/
      public ObservableList<modeloVistaContratoHabitaciones>  getHabitacionesUser(){
        if(cs.validadorCliente()){
            ArrayList<modeloVistaContratoHabitaciones> vistaarray = new ArrayList<>();
        for(ContratoHabitacion conhab:hab.readhabitacionesReservacion(cs.getCodigoReservacion())){
            ContratoHabitacion temporalconserv=  conhab;
            Habitacion temporalhabitacion = ch.findOneCodigo_reservacion(conhab.getId_habitacion());
            TipoHabitacion temporaltiphab=  ctp.findOneid_tipoHabitacion(temporalhabitacion.getId_tipo_habitaciones());
            modeloVistaContratoHabitaciones  ee= new modeloVistaContratoHabitaciones(conhab,temporalhabitacion,temporaltiphab);
        vistaarray.add(ee);
        }
    return FXCollections.observableList(vistaarray);
        }
        return null;
    }
      //--------------carga habitaciones para añadir por el usuario-------------
     public ObservableList<Servicio> getServiciosAContratar(){
         if(cs.validadorCliente()){
             return FXCollections.observableList(cserv.readAll());
         }
         return null;
     }
    public double getCostototal(){
    if(cs.validadorCliente()){
            double total = 0;
        for(ContratoHabitacion conhab:hab.readhabitacionesReservacion(cs.getCodigoReservacion())){
            Habitacion habitacion= ch.findOneCodigo_reservacion(conhab.getId_habitacion());
            TipoHabitacion tipoha= ctp.findOneid_tipoHabitacion(habitacion.getId_tipo_habitaciones());
            total+=tipoha.getCosto();
        }
        for(ContratoServicio conserv:serv.readserviciosReservacion(cs.getCodigoReservacion())){
            Servicio ss= cserv.findOneid_servicio(conserv.getId_servicio());
            total+=(ss.getPrecio()*conserv.getCantidad());
        }
        return total;
        }
    return 0;
    }
    public boolean añadirNuevoServicio(Servicio servicio,int cantidad){
      boolean resp = false;
        if(cs.validadorCliente()){
            if(servicio!=null){
                ContratoServicio contratoServicio = new ContratoServicio(0, servicio.getId_servicio(), cs.getCodigoReservacion(), cantidad,servicio.getPrecio());
            this.serv.create(contratoServicio);
            resp = true;
            }
        }
        return resp;
    }
       
    
}
