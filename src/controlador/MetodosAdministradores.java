/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import modelo.Administrador;
import modelo.Bitacora;
import modelo.CRUDS.CRUDAdministradores;
import modelo.CRUDS.CRUDBitacora;

import modelo.CRUDS.CRUDCliente;
import modelo.CRUDS.CRUDContratoHabitacion;
import modelo.CRUDS.CRUDContratoServicio;
import modelo.CRUDS.CRUDDireccion;
import modelo.CRUDS.CRUDFactura;
import modelo.CRUDS.CRUDHabitacion;
import modelo.CRUDS.CRUDPreguntaSeguridad;
import modelo.CRUDS.CRUDReservacion;
import modelo.CRUDS.CRUDServicio;
import modelo.CRUDS.CRUDTipoHabitacion;
import modelo.Cliente;
import modelo.Conexion;
import modelo.ContratoHabitacion;
import modelo.ContratoServicio;
import modelo.Direccion;
import modelo.Factura;
import modelo.Habitacion;
import modelo.PreguntaSeguridad;
import modelo.Reservacion;
import modelo.Servicio;
import modelo.TipoHabitacion;
import sistema_hotel1.Alertas;

/**
 *
 * @author heyscar
 */
public class MetodosAdministradores {
     private static MetodosAdministradores metodosAdministradores; 
    private ControlSesion controlSesion;
    private CRUDCliente crudCliente;
    private CRUDReservacion crudReservacion;
     private CRUDContratoHabitacion crudContratoHabitacion;
     private CRUDHabitacion crudHabitacion;
     private CRUDTipoHabitacion crudTipoHabitacion;
    private CRUDContratoServicio crudContratoServicios;
    private CRUDServicio crudServicios;
    private CRUDDireccion crudDireccion;
    private CRUDFactura crudFactura;
    private CRUDBitacora crudBitacora;
    
    private MetodosAdministradores(){
        controlSesion = ControlSesion.getInstance();
        crudCliente = CRUDCliente.getInstance();
        crudReservacion = CRUDReservacion.getInstance();
        crudHabitacion= CRUDHabitacion.getInstance();
        crudTipoHabitacion = CRUDTipoHabitacion.getInstance();
                
        crudContratoServicios  = CRUDContratoServicio.getInstance();
        crudContratoHabitacion= CRUDContratoHabitacion.getInstance();
        crudServicios = CRUDServicio.getInstance();
        crudDireccion = CRUDDireccion.getInstance();
        crudFactura = CRUDFactura.getInstance();
        crudBitacora = CRUDBitacora.getInstance();
    }
    /*metodo singletooon que mantiene uno sola instancia de metodosclientes*/
    public static MetodosAdministradores getInstance(){
       
        if(metodosAdministradores == null)
        {
            metodosAdministradores = new MetodosAdministradores();  
        }
        return metodosAdministradores;
    }
//------------------------abre metodos mantenimiento------------
    public void RecuperarContraseña(){
        
    }
    public boolean CambiarContraseña(String ContraseñaVieja,String ContraseñaNueva){
        if(controlSesion.validadorAdmin()){
            
            if(CRUDAdministradores.getInstance().READ(controlSesion.getIDUser()).getContraseña().equals(ContraseñaVieja)){
                CRUDAdministradores.getInstance().UPDATE(new Administrador(controlSesion.getIDUser(), controlSesion.getNombreUsuario(), ContraseñaNueva));
                return true;
            }
            else{
                Alertas aa = new Alertas(Alert.AlertType.ERROR, "la contraseña Actual que introdujo no es igual a la de este usuario");
                aa.setHeaderText("Contraseña Incorrecta!  ");
                aa.showAndWait();
                return false;
            
        }
        }
        return false;
    }
    public ArrayList<PreguntaSeguridad> getPreguntasSeguridad(){
        if(controlSesion.validadorAdmin()){
            return CRUDPreguntaSeguridad.getInstance().read(controlSesion.getIDUser());
        }
        return null;
        
    }
    public boolean CambiarPreguntasSeguridad(ArrayList<PreguntaSeguridad> e){
        boolean resp = true;
        if(controlSesion.validadorAdmin()){
             int iter = 0;
             ArrayList<PreguntaSeguridad> psi2 = this.getPreguntasSeguridad();
             for(PreguntaSeguridad psi : e){
                 if(iter>=3)break;
                 if(!CRUDPreguntaSeguridad.getInstance().update(new PreguntaSeguridad(psi2.get(iter).getId(), controlSesion.getIDUser(), psi.getPregunta(), psi.getRespuesta()))){
                   
                
                 resp = false;
             }
                  iter++;
             }return resp;
        }
       return false;
        
    }
//------------------------cierra metodos mantenimiento------------    
    //devuelve la tabla clientes en un observable list------
    public ObservableList<Cliente> getTablaClientes(){
        if(controlSesion.validadorAdmin()){
            return FXCollections.observableList(crudCliente.readAll());
        }
        return FXCollections.observableList(new ArrayList<>());
    }
    
      public ObservableList<Cliente> getTablaClientes(String Consulta){
        if(controlSesion.validadorAdmin()){
            return FXCollections.observableList(crudCliente.findGlobal(Consulta));
        }
        return FXCollections.observableList(new ArrayList<>());
    }
      
    
      public boolean registrarDireccionCliente(int id_estado,String nombre_Ciudad,String Cedula,String descripcion){
          if(controlSesion.validadorAdmin()){
              int id=0;
             for(Cliente c:crudCliente.findGlobal(Cedula)) {
                 id = c.getId_cliente();
             }if(id!=0){
               if( crudDireccion.create(new Direccion(
                      id, 
                      0,
                      "",
                         id_estado,
                         "",
                         0, 
                         nombre_Ciudad, 0, descripcion)))
              return true; 
          }
              
             }
             
          
          return false;
      }
     
      public ObservableList<Direccion> getPaises(){
           return FXCollections.observableList(crudDireccion.getpaises());
          
         }
        public ObservableList<Direccion> getEstados(int id_pais){
           return FXCollections.observableList(crudDireccion.getEstados(id_pais));
          
         }
        public Cliente devolver_cliente_cedula(String cedula){
            if(controlSesion.validadorAdmin()){
                 return crudCliente.findOnCedula(cedula);
            }
          return null;
            
        }
        public ObservableList<Reservacion> getAllReservaciones(){
           return  FXCollections.observableList(crudReservacion.readAll());
        }
        public ArrayList<Habitacion> getHabitacionesDisponible(LocalDate fec_min ,int hour_min, LocalDate fec_max,int hourmax ){
       /* if(fec_min.isAfter(fec_max)){
            Alertas aa = new Alertas(Alert.AlertType.ERROR,"error fecha invalida");
            aa.showAndWait();
        }else{*/
        if(controlSesion.validadorAdmin()){
            return crudHabitacion.HabitacionesDisponibles(fec_min, hour_min, fec_max, hourmax);
        }
        return new ArrayList<>();
        }
        public ArrayList<TipoHabitacion> obtenerTipoHabitacionDisponible(ArrayList<Habitacion> array){
            
            ArrayList<TipoHabitacion> array2 = new ArrayList<>();
            
            for(Habitacion hab : array){
                boolean registrar = true;
                for(TipoHabitacion tpi : array2){
                if(tpi.getId_tipos_habitaciones()==hab.getId_tipo_habitaciones())registrar = false;
            }
                if(registrar)array2.add(crudTipoHabitacion.findOneid_tipoHabitacion(hab.getId_tipo_habitaciones()));
            }
            return array2;
        }
        public String GenerarCodigo(Cliente cliente){
            if(controlSesion.validadorAdmin())
            {return crudReservacion.generarCodigo(cliente.getId_cliente(),0);}
            return "";
        }
//-----------------Ultimos Registros de tablas
          public Cliente getUltimoClienteRegistro(){
            if(controlSesion.validadorAdmin()){
                return crudCliente.getMaxRegistroCliente();
            }
            else{
                return null;
            }
        }
          public TipoHabitacion getUltimoTipoHabitacion(){
             return crudTipoHabitacion.getMaxTipoHabitacion();
          }
        //convierte
        public modeloVistaContratoHabitaciones getModeloVistaHabitaciones(ContratoHabitacion n){
            
            Habitacion hab = crudHabitacion.findOneCodigo_reservacion(n.getId_habitacion());
            TipoHabitacion tphab = crudTipoHabitacion.findOneid_tipoHabitacion(hab.getId_tipo_habitaciones());
            return new modeloVistaContratoHabitaciones(n, hab, tphab);
         
        }
         public ObservableList<modeloVistaContratoHabitaciones> getModeloVistaHabitaciones(){
             ArrayList<modeloVistaContratoHabitaciones> arra = new ArrayList<>();
             for(Habitacion hab : crudHabitacion.readAll()){
                 arra.add(new modeloVistaContratoHabitaciones(new ContratoHabitacion(0, 0, "",0.00), hab, crudTipoHabitacion.findOneid_tipoHabitacion(hab.getId_tipo_habitaciones())));
             }
           return FXCollections.observableList(arra);
         
        }
        public ObservableList<Servicio> getAllServicios(){
           
                return FXCollections.observableList(crudServicios.readAll());
            
            
        }
//------------------Reservaciones
        public boolean RegistrarReservacion(Reservacion res,ArrayList<modeloVistaContratoHabitaciones> arrayContratoHabitaciones,ArrayList<modeloVistaContratoServicios> arrayContratoServicios ){
         
              boolean resp = crudReservacion.create(new Reservacion(res.getCodigoReservacion(),
                   res.getId_cliente(), 
                   res.getFechaEntrada(),
                   res.getHoraEntrada(),
                   res.getFechaSalida(), 
                   res.getHoraSalida(), 0));
              if(resp){
           for(modeloVistaContratoHabitaciones conhabie: arrayContratoHabitaciones){
                crudContratoHabitacion.create( new ContratoHabitacion(0, conhabie.getId_habitacion(), res.getCodigoReservacion(),conhabie.getCostoTipoHabitacion()));
           }
           for (modeloVistaContratoServicios conserv: arrayContratoServicios){
               crudContratoServicios.create(new ContratoServicio(0, conserv.getId_servicio(), res.getCodigoReservacion(), conserv.getCantidad(),conserv.getPrecio()));
           }
              return true;}
              else{
                  return false;
              }
          
          }
//------------------Clientes
            public boolean Registrar(Cliente c1){
           if(controlSesion.validadorAdmin()){
               boolean resp = true;
               for(Cliente n :crudCliente.findGlobal(c1.getCedula())){
                   if(n.getCedula().equals(c1.getCedula())){
                       Alertas aa = new Alertas(Alert.AlertType.ERROR, "este cliente ya esta registrado \n"
                               + "o la cedula que se esta insertando esta erronea");
                      aa.setHeaderText("no puede registrar este cliente");
                      aa.showAndWait();
                       resp = false;
                   }
               }
               if(resp){
                   
            return crudCliente.create(c1);
               
               }
               
         }
           return false;
      }
            public void Delete(Cliente c1){
         if(controlSesion.validadorAdmin()){
             crudCliente.delete(c1.getId_cliente());
         }   
      }
             public void Actualizar(Cliente c1){
            if(controlSesion.validadorAdmin()){
                
             crudCliente.update(c1);
         }
      
}
             public Cliente getCliente(int id){
                 return crudCliente.findOneId(id);
             }
//------------------Servicios
          public boolean Eliminar(Servicio serv){
             return crudServicios.delete(serv.getId_servicio());
          }
          public boolean Actualizar(Servicio ser){
              return crudServicios.update(ser);
              
             
              
          }
          public boolean Registrar(Servicio ser){
              return crudServicios.create(ser);
              
          }
//------------------Tipo Habitaciones
          public boolean Eliminar(TipoHabitacion tphab){
            return crudTipoHabitacion.delete(tphab.getId_tipos_habitaciones());
          }
          public boolean Actualizar(TipoHabitacion tphab ){
              return crudTipoHabitacion.update(tphab);
        
          }
          public boolean Registrar(TipoHabitacion tphab ){
              return crudTipoHabitacion.create(tphab);
              
          }
          public ObservableList<TipoHabitacion> getAllTipoHabitaciones(){
           
                return FXCollections.observableList(crudTipoHabitacion.readAll());
            
            
        }
           public ArrayList<modeloVistaContratoHabitaciones> getContratosHabitacionesByReservacion(Reservacion res){
              ArrayList<modeloVistaContratoHabitaciones> arra = new ArrayList<>();
               for(ContratoHabitacion con: crudContratoHabitacion.findOneContratoHabitacion(res.getCodigoReservacion())){
                   Habitacion habitacion = crudHabitacion.findOneCodigo_reservacion(con.getId_habitacion());
               arra.add(new modeloVistaContratoHabitaciones(con, habitacion,crudTipoHabitacion.findOneid_tipoHabitacion(habitacion.getId_tipo_habitaciones()) ));
                       }
               return arra;
           }
//------------------Habitaciones
          public boolean Eliminar(Habitacion hab){
            return crudHabitacion.delete(hab.getId());
          }
          public boolean Actualizar(Habitacion hab ){
              return crudHabitacion.update(hab);
        
          }
          public boolean Registrar(Habitacion hab ){
              return crudHabitacion.create(hab);
              
          }
//-------------------Reservaciones
         public void Eliminar(Reservacion res){
              String codigo = res.getCodigoReservacion();
              crudContratoHabitacion.deleteReservacion(codigo);
              crudContratoServicios.delete_reservacion(codigo);
              crudFactura.delete(codigo);
              crudReservacion.delete(codigo);
              
              
              
              
          }
          public ArrayList<Reservacion> getReservacionBYDay(LocalDate ss){
              if(controlSesion.validadorAdmin()){
                   System.out.println("controlador.MetodosAdministradores.getReservacionBYDay()");
                  
                 return crudReservacion.findFecha(LocalDateTime.of(ss, LocalTime.of(1, 1)));
                  
              }
              
              return null;
          }
//------------------ContratoServicios
          public ArrayList<modeloVistaContratoServicios> getServiciosByReservacion(Reservacion reservacion){
              ArrayList<modeloVistaContratoServicios> arra = new ArrayList<>();
              for (ContratoServicio conserv :crudContratoServicios.findOneServicios(reservacion.getCodigoReservacion()))
              {
                  arra.add(new modeloVistaContratoServicios(crudServicios.findOneid_servicio(conserv.getId_servicio()), conserv));
              }
              return arra;
           }
//------------Abre metodos a base de datos---------------
          public boolean crearRespaldoBaseDatos(String Ubicacion) {
              if(controlSesion.validadorAdmin()){
                  Conexion.getInstance().RepaldarBaseDatos(Ubicacion);
                  return true;
              }else{
                  System.out.println("hubo un error con las credenciales y no se respaldo");
                  return false;
              }
          }
          public boolean cargarRespaldoBaseDatos(String ubicacion){
              if(controlSesion.validadorAdmin()){
                  Conexion.getInstance().cargarRespaldoBaseDatos(ubicacion);
                  return true;
              }else{
                  System.out.println("hubo un error con las credenciales y no se respaldo");
                  return false;
              }
          }
          
    //-------cierrra metodos a base de datos--------------  
          
          //------------ METODOS FACTURA -------------------------------------------------
        
        public Factura devolver_factura (int id){
           if(controlSesion.validadorAdmin()){
           return crudFactura.findOnId(id);
           }
           return null;
        }
        
        public Cliente devolver_cliente (int id){
           if(controlSesion.validadorAdmin()){
           return crudFactura.findOnIdCliente(id);
           }
           return null;
        }
        
        public TipoHabitacion devolver_habitacion (int id){
           if(controlSesion.validadorAdmin()){
           return crudFactura.findOnIdHabitacion(id);
           }
           return null;
        }
        
        public Servicio devolver_servicio (int id){
           if(controlSesion.validadorAdmin()){
           return crudFactura.findOnIdServicio(id);
           }
           return null;
        }
         //devuelve la tabla factura en un observable list------
    public ObservableList<Factura> getTablaFactura(){
        if(controlSesion.validadorAdmin()){
        return FXCollections.observableList(crudFactura.readAll());
        }
        return FXCollections.observableList(new ArrayList<>());
    }
     //devuelve la tabla factura consultada en un observable list------
    public ObservableList<Factura> getTablaFactura(String Consulta){
        if(controlSesion.validadorAdmin()){
        return FXCollections.observableList(crudFactura.findGlobal(Consulta));
        }
        return FXCollections.observableList(new ArrayList<>());
    }
    
public int GenerarFactura (Cliente cliente) {
            if (controlSesion.validadorAdmin())
            {return crudFactura.generarFactura(cliente.getId_cliente(), LocalDate.now());}
            return 0;
        }
 public boolean RegistrarFactura (Factura fact){
        
            boolean resp = crudFactura.create(new Factura(
                                                fact.getId_factura(),
                                                fact.getFecha_factura(), 
                                                
                                                fact.getCodigoReservacion()));
           if(resp) return true;
           return false;
        }
 
    public void registrarBitacora(LocalDateTime now, String modulo, String usuario){

        crudBitacora.create(now, modulo, usuario);

    }
    
    public ObservableList<Bitacora> getTablaBitacora(){
        if(controlSesion.validadorAdmin()){
        return FXCollections.observableList(crudBitacora.read());
        }
        return FXCollections.observableList(new ArrayList<>());
    }
    
    public ObservableList<Bitacora> getTablaBitacoraFecha(String date){
        if(controlSesion.validadorAdmin()){
        return FXCollections.observableList(crudBitacora.readDate(date));
        }
        return FXCollections.observableList(new ArrayList<>());
    }
//------------------------------------------------------------------------------
    public ArrayList getListItemFactura(Reservacion rr){
      ArrayList items = new ArrayList();
      items.addAll(crudContratoHabitacion.readhabitacionesReservacion(rr.getCodigoReservacion()));
      items.addAll(crudContratoServicios.findOneServicios(rr.getCodigoReservacion()));
      return items;
    }
}
