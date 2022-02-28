/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import controlador.ControlSesion;
import java.io.IOException;
import java.time.LocalDate;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import modelo.Reservacion;

/**
 *
 * @author heyscar
 */
public class ControlVentanas {
    private FXMLLoader 
            loaderInicioAdministradore,
            loaderRegistroCliente,
            loaderRegistroReservacion,
            loaderConsultarReservacion,
            loaderConsultarHabitaciones,
            loaderConsultarServicios,
            loaderConsultarCliente,
            loaderMantenimiento,
            loaderFacturas,
            loaderMostrarReservaciones,
            loaderDetalles;
            
    private StackPane contenedor,
            preinicio,
            preconsultarcliente,
            preconsultarreservaciones,
            preregistrocliente,
            preregistroreservaciones,preservicios,
            prehabitaciones,
            preMantenimiento,
            prefacturas,
            preMostrarReservaciones,
            preDetalles;
            
    private BorderPane BorderPaneBarraLateral;
    private static ControlVentanas singletoon;
    private ControlVentanas(){
        contenedor = new StackPane();
      
        
        try {
        loaderInicioAdministradore = new FXMLLoader();
        loaderInicioAdministradore.setLocation(getClass().getResource("InicioAdministrador.fxml"));
        loaderInicioAdministradore.load();
        preinicio = new StackPane();
        preinicio.getChildren().add(loaderInicioAdministradore.getRoot());
        contenedor.getChildren().add(preinicio);
 
        //-----------------precarga de registro cliente---------------
        loaderRegistroCliente = new FXMLLoader();
         loaderRegistroCliente.setLocation(getClass().getResource("RegistroCliente.fxml"));
         loaderRegistroCliente.load();
         preregistrocliente = new StackPane();
         preregistrocliente.getChildren().add(loaderRegistroCliente.getRoot());
         contenedor.getChildren().add(preregistrocliente);
         preregistrocliente.setTranslateX(1920);
         //-------------------precarga de registro reservacion---------------
               loaderRegistroReservacion = new FXMLLoader();
         loaderRegistroReservacion.setLocation(getClass().getResource("RegistrarReservacion.fxml"));
          loaderRegistroReservacion.load();
        preregistroreservaciones = new StackPane();
        preregistroreservaciones.getChildren().add(loaderRegistroReservacion.getRoot());
         contenedor.getChildren().add(preregistroreservaciones);
        preregistroreservaciones.setTranslateX(1920);
        //-------------------precarga de consultar reservaciones---------------
         loaderConsultarReservacion = new FXMLLoader();
          loaderConsultarReservacion.setLocation(getClass().getResource("Consultar_reservacion.fxml"));
         loaderConsultarReservacion.load();
         
        preconsultarreservaciones = new StackPane();
        preconsultarreservaciones.getChildren().add(loaderConsultarReservacion.getRoot());
        contenedor.getChildren().add(preconsultarreservaciones);
        preconsultarreservaciones.setTranslateX(1920);
        //-------------------precarga de consultar habitaciones---------------
        loaderConsultarHabitaciones = new FXMLLoader();
         loaderConsultarHabitaciones.setLocation(getClass().getResource("Servicio_generales.fxml"));
          loaderConsultarHabitaciones.load();
        prehabitaciones = new StackPane();
        prehabitaciones.getChildren().add(loaderConsultarHabitaciones.getRoot());
        contenedor.getChildren().add(prehabitaciones);
        prehabitaciones.setTranslateX(1920);
        //-------------------precarga de consultar servicios---------------
        loaderConsultarServicios = new FXMLLoader();
        loaderConsultarServicios.setLocation(getClass().getResource("Servicios.fxml"));
        loaderConsultarServicios.load();
       preservicios = new StackPane();
       
        preservicios.getChildren().add(loaderConsultarServicios.getRoot());
         contenedor.getChildren().add(preservicios);
         
        preservicios.setTranslateX(1920);
        //-------------------precarga de consultar cliente---------------
         loaderConsultarCliente = new FXMLLoader();
         loaderConsultarCliente.setLocation(getClass().getResource("Consulta.fxml"));
        loaderConsultarCliente.load();
        preconsultarcliente = new StackPane();
        preconsultarcliente.getChildren().add(loaderConsultarCliente.getRoot());
        contenedor.getChildren().add(preconsultarcliente);
        preconsultarcliente.setTranslateX(1920);
        //-------------------precarga de  Mantenimiento---------------
        loaderMantenimiento = new FXMLLoader();
        loaderMantenimiento.setLocation(getClass().getResource("Mantenimiento.fxml"));
        loaderMantenimiento.load();
        preMantenimiento = new StackPane();
        preMantenimiento.getChildren().add(loaderMantenimiento.getRoot());
        contenedor.getChildren().add(preMantenimiento);
        preMantenimiento.setTranslateX(1920);
        //-------------------precarga de consultar facturas---------------
        loaderFacturas = new FXMLLoader();
        loaderFacturas.setLocation(getClass().getResource("Consultar_Facturas.fxml"));
        loaderFacturas.load();
       
       prefacturas = new StackPane();
       
        
        prefacturas.getChildren().add(loaderFacturas.getRoot());
        contenedor.getChildren().add(prefacturas);
        prefacturas.setTranslateX(1920);
        //-------------------precarga de mostrar reservaciones---------------
        loaderMostrarReservaciones = new FXMLLoader();
        loaderMostrarReservaciones.setLocation(getClass().getResource("MostrarReservaciones.fxml"));
        loaderMostrarReservaciones.load();
       
       preMostrarReservaciones = new StackPane();
       
        
        preMostrarReservaciones.getChildren().add(loaderMostrarReservaciones.getRoot());
        contenedor.getChildren().add(preMostrarReservaciones);
        preMostrarReservaciones.setTranslateX(1920);
         //-------------------precarga de mostrar detalles de reservaciones---------------
        loaderDetalles = new FXMLLoader();
        loaderDetalles.setLocation(getClass().getResource("DetallesReservacion.fxml"));
        loaderDetalles.load();
       
       preDetalles = new StackPane();
       
        
        preDetalles.getChildren().add(loaderDetalles.getRoot());
        contenedor.getChildren().add(preDetalles);
        preDetalles.setTranslateX(1920);
    } catch (IOException ex) {
    Alertas aa = new Alertas(Alert.AlertType.ERROR);
    }
    }
    public static ControlVentanas getInstance(){
        if (singletoon == null )singletoon = new ControlVentanas();
        return singletoon;
    }
    
    public StackPane getContenedorPrincipal(){
        return contenedor;
    }
    //----------------------get Controllers
    public ConsultaController getControlConsultaCliente(){
       return loaderConsultarCliente.getController();
    }
    public Consultar_reservacionController  getControlConsultaReservacion(){
         return loaderConsultarReservacion.getController();
    }
    public InicioAdministradorController  getControlInicioAdministradorController(){
         return loaderInicioAdministradore.getController();
    }
    public RegistrarReservacionController  getControlRegistrarReservacion(){
         return loaderRegistroReservacion.getController();
    }
    public RegistroClienteController  getControlRegistroCliente(){
         return loaderRegistroCliente.getController();
    }
    public Servicio_generalesController getControlHabitaciones(){
         return loaderConsultarHabitaciones.getController();
     }
    public ServiciosController getControlServicios(){
         return loaderConsultarServicios.getController();
     }
    private  MantenimientoController getControloMantenimiento(){
        return loaderMantenimiento.getController();
    } 
    public  Consultar_FacturasController getControlFacturas(){
        return loaderFacturas.getController();
    }
    public  MostrarReservacionesController getControlMostrarReservaciones(){
        return loaderMostrarReservaciones.getController();
    }
    public  DetallesReservacionController getControlDetallesReservacion(){
        return loaderDetalles.getController();
    }
    public void transicionSalidaVentana(){
        if(preinicio.getTranslateX() == 0){
             Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preinicio.translateYProperty(), 1080, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                   preinicio.setTranslateX(1920);
                  preinicio.setTranslateY(0);
                });
                
               timeline.play();
        }
         if(preconsultarcliente.getTranslateX() == 0){
                Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preconsultarcliente.translateYProperty(), 1080, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                       preconsultarcliente.setTranslateX(1920);
                  preconsultarcliente.setTranslateY(0);  
                });
                
               timeline.play();
             
        } if(preconsultarreservaciones.getTranslateX() == 0){
             Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preconsultarreservaciones.translateYProperty(), 1080, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                    preconsultarreservaciones.setTranslateX(1920);
                   preconsultarreservaciones.setTranslateY(0);
                   getControlConsultaReservacion().close();
                });
                
               timeline.play();
              
        }  if(prehabitaciones.getTranslateX() == 0){
             Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(prehabitaciones.translateYProperty(), 1080, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                    prehabitaciones.setTranslateX(1920);
                   prehabitaciones.setTranslateY(0);
                   getControlHabitaciones().close();
                  
                });
                
               timeline.play();
              
        } if(preregistrocliente.getTranslateX() == 0){
             Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preregistrocliente.translateYProperty(), 1080, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                   preregistrocliente.setTranslateX(1920);
                   preregistrocliente.setTranslateY(0);
                   getControlRegistroCliente().clear();
                   
                });
                
               timeline.play();
               
        } if(preregistroreservaciones.getTranslateX() == 0){
             Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preregistroreservaciones.translateYProperty(), 1080, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                    preregistroreservaciones.setTranslateX(1920);
                   preregistroreservaciones.setTranslateY(0);
                   getControlRegistrarReservacion().close();
                });
                
               timeline.play();
              
        } if(preservicios.getTranslateX() == 0){
             Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preservicios.translateYProperty(), 1080, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                    preservicios.setTranslateX(1920);
                   preservicios.setTranslateY(0);
                  getControlServicios().close();
                });
                
               timeline.play();
              
        }
        if(preMantenimiento.getTranslateX() == 0){
             Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preMantenimiento.translateYProperty(), 1080, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                    preMantenimiento.setTranslateX(1920);
                   preMantenimiento.setTranslateY(0);
                  getControloMantenimiento().close();
                });
                
               timeline.play();
        }
        if(prefacturas.getTranslateX() == 0){
             Timeline timeline  = new Timeline();
            
            KeyValue kv = new KeyValue(prefacturas.translateYProperty(), 1080, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1->{ 
                prefacturas.setTranslateX(1920);
                prefacturas.setTranslateY(0);
                getControlFacturas().close();
            });
                
               timeline.play();
        }
        if(preMostrarReservaciones.getTranslateX() == 0){
             Timeline timeline  = new Timeline();
            
            KeyValue kv = new KeyValue(preMostrarReservaciones.translateYProperty(), 1080, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1->{ 
                preMostrarReservaciones.setTranslateX(1920);
                preMostrarReservaciones.setTranslateY(0);
                getControlMostrarReservaciones().close();
            });
                
               timeline.play();
        }
        if(preDetalles.getTranslateX() == 0){
             Timeline timeline  = new Timeline();
            
            KeyValue kv = new KeyValue(preDetalles.translateYProperty(), 1080, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1->{ 
                preDetalles.setTranslateX(1920);
                preDetalles.setTranslateY(0);
                getControlDetallesReservacion().close();
            });
                
               timeline.play();
        }
    }
    
    
    public void llamar_inicio(){
    transicionSalidaVentana();
      Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preinicio.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                    
                });
                
               timeline.play();
}
public void llamar_RegistroCliente(){
    transicionSalidaVentana();
   Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preregistrocliente.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                    
                });
                
               timeline.play();
}
public  void llamar_RegistroReservacion(){
     transicionSalidaVentana();
     getControlRegistrarReservacion().iniciar_Ventana();
   Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preregistroreservaciones.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                    preregistroreservaciones.setTranslateX(0);
                     preregistroreservaciones.setTranslateY(0);
                });
                
                
               timeline.play();
}

public void llamar_reservaciones(){
    transicionSalidaVentana();
    
   Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preconsultarreservaciones.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                      preconsultarreservaciones.setTranslateX(0);
                     preconsultarreservaciones.setTranslateY(0);
                     getControlConsultaReservacion().iniciar_Ventana();
                });
                
               timeline.play();
}
public void llamar_Habitaciones(){
    transicionSalidaVentana();
   Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(prehabitaciones.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                   getControlHabitaciones().iniciar_ventana();
                });
                
               timeline.play();
}
public void llamar_Servicios(){
    transicionSalidaVentana();
   Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preservicios.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                   getControlServicios().iniciar_ventana();
                });
                
               timeline.play();
}
public void llamar_Clientes(){
     transicionSalidaVentana();
   Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preconsultarcliente.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                    getControlConsultaCliente().SesionActive();
                });
                
               timeline.play();
}
public void llamar_Matenimiento(){
       transicionSalidaVentana();
   Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(preMantenimiento.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{  
                    this.getControloMantenimiento().iniciar_Ventana();
                    
                });
                
               timeline.play();
}
public void llamarFacturas(){
    transicionSalidaVentana();
 Timeline timeline  = new Timeline();
     
    KeyValue kv = new KeyValue(prefacturas.translateXProperty(), 0, Interpolator.EASE_IN);
    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
    timeline.getKeyFrames().add(kf);
    timeline.setOnFinished(event1->{  
    getControlFacturas().iniciar_Ventana();
    });
                
    timeline.play();
   
}
public void llamarMostrarReservaciones(LocalDate f){
    transicionSalidaVentana();
 Timeline timeline  = new Timeline();
     
    KeyValue kv = new KeyValue(preMostrarReservaciones.translateXProperty(), 0, Interpolator.EASE_IN);
    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
    timeline.getKeyFrames().add(kf);
    timeline.setOnFinished(event1->{  
   getControlMostrarReservaciones().iniciar_Ventana(f);
    });
                
    timeline.play();
   
}
public void llamar_cerrar_sesion(){


             
               
    
    ControlSesion.getInstance().CerrarSesion();
                 Timeline timeline  = new Timeline();
                KeyValue kv = new KeyValue(BorderPaneBarraLateral.translateXProperty(), 1920, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1->{
               
                
                });
                
               timeline.play();
               if(preinicio.getTranslateX()!=0 || preinicio.getTranslateY()!= 0)
               transicionSalidaVentana();
                  preinicio.setTranslateX(0);
                  preinicio.setTranslateY(0);
    
}
public void llamar_detalles(Reservacion res){
     transicionSalidaVentana();
 Timeline timeline  = new Timeline();
     
    KeyValue kv = new KeyValue(preDetalles.translateXProperty(), 0, Interpolator.EASE_IN);
    KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
    timeline.getKeyFrames().add(kf);
    timeline.setOnFinished(event1->{  
   getControlDetallesReservacion().cargarDetalles(res);
    });
                
    timeline.play();
   
}
    public void SetVentanaPrincipalSesion(BorderPane BarraLateralContainer){
    BorderPaneBarraLateral = BarraLateralContainer;
    
}
}
