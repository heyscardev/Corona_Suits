
package sistema_hotel1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class BarraLateralController implements Initializable {
private  static String estado =  "Inicio";
@FXML
private  StackPane contenedor;

@FXML
private VBox fondo;
@FXML
private  BorderPane container;
private ControlVentanas control;
private ConsultaController consultaClienteController;
private RegistroClienteController registroClienteController;
private static RegistrarReservacionController registroreserController;
 private static Servicio_generalesController habitacionesController;
 private static ServiciosController serviciosController;
  private static Consultar_reservacionController  consultar_reservacionController ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         _config();
    }   
    public void  _config(){
      
      control = ControlVentanas.getInstance();
      contenedor.getChildren().add(control.getContenedorPrincipal());
      control.SetVentanaPrincipalSesion(container);
    }
   
public void evento_inicio(MouseEvent e){
control.llamar_inicio();
}
public void evento_RegistroCliente(MouseEvent e){
   control.llamar_RegistroCliente();
}
public  void evento_RegistroReservacion(MouseEvent e){
     control.llamar_RegistroReservacion();
}
public void evento_reservaciones(MouseEvent e){
    control.llamar_reservaciones();
}
public void evento_Habitaciones(MouseEvent e){
    control.llamar_Habitaciones();
}
public void evento_mantenimiento(MouseEvent e){
    control.llamar_Matenimiento();
}
public void evento_Servicios(MouseEvent e){
   control.llamar_Servicios();
}
public void evento_Clientes(MouseEvent e){
  control.llamar_Clientes();
}
public void evento_Facturas(MouseEvent e){
    control.llamarFacturas();
}
@FXML
public  void evento_cerrar_sesion(MouseEvent e){
     
   control.llamar_cerrar_sesion();
               
    
}
public static RegistrarReservacionController getCotrolRegistroReservacion(){
    return ControlVentanas.getInstance().getControlRegistrarReservacion();
}
public static ServiciosController getControlServicios(){
    return  ControlVentanas.getInstance().getControlServicios();
}
 
public StackPane getContenedorVentanas(){
return contenedor;
}
}