
package sistema_hotel1;

import controlador.MetodosAdministradores;
import controlador.modeloVistaContratoHabitaciones;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.TipoHabitacion;
import zunayedhassan.AnimateFX.WobbleAnimation;

public class Servicio_generalesController implements Initializable {
    @FXML 
   private TableView<modeloVistaContratoHabitaciones> tablaHabitaciones;
    
    @FXML
private TableColumn columnaTipo;
     @FXML
private TableColumn columnaPiso;
      @FXML
private TableColumn columnaNumero;
   @FXML
private TableColumn columnaDescripcion;
     @FXML
private TableColumn columnaCantidadPersonas;
       @FXML
private TableColumn columnaPrecio;
 @FXML
 private Button btnRegistrar;
 @FXML
 private Button btnModificar;
 @FXML
 private Button btnEliminar;
       private MetodosAdministradores ma;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      _config();
   
    }    
    public void _config(){
         ma = MetodosAdministradores.getInstance();
    columnaPiso.setCellValueFactory(new PropertyValueFactory("Piso"));
    columnaNumero.setCellValueFactory(new PropertyValueFactory("NumeroHabitacion"));
    columnaCantidadPersonas.setCellValueFactory(new PropertyValueFactory("CantMaxPersonas"));
    columnaTipo.setCellValueFactory(new PropertyValueFactory("NombreTipoHabitacion"));
    columnaPrecio.setCellValueFactory(new PropertyValueFactory("CostoTipoHabitacion"));
    columnaDescripcion.setCellValueFactory(new PropertyValueFactory("DescripcionTipoHabitacion"));
    
    evento_registrar_habitacion();
    evento_Eliminar_habitacion();
    evento_modificar_habitacion();
    }
    public void iniciar_ventana(){
        tablaHabitaciones.setItems(ma.getModeloVistaHabitaciones());
        tablaHabitaciones.refresh();
    }
    public void clear(){
        
    }
    public void close(){
        
    }
    //-------------------------eventos--------------
    private void evento_registrar_habitacion(){
        btnRegistrar.setOnAction(ec->{
            try{
         FXMLLoader fxml = new FXMLLoader();
                         fxml.setLocation(getClass().getResource("RegistroHabitacion.fxml"));
                         fxml.load();
                         RegistroHabitacionController rthc = fxml.getController();
                         Stage stage = new Stage();
                         Scene scene = new Scene(fxml.getRoot());
                         
                         scene.setFill(Paint.valueOf("transparent"));
                         stage.setScene(scene);
                         stage.initOwner(btnRegistrar.getScene().getWindow());
                         stage.initModality(Modality.APPLICATION_MODAL);
                         stage.initStyle(StageStyle.TRANSPARENT);
                         rthc.iniciarVentana();
                        
                         stage.showAndWait();
                         tablaHabitaciones.setItems(ma.getModeloVistaHabitaciones());
                         tablaHabitaciones.refresh();
                         
                                 } catch (IOException ex) {
                        System.out.println("sistema_hotel1.Servicio_generalesController.evento_registrar_habitacion()");
                     }
        
        });
    }
     private void evento_modificar_habitacion(){
         btnModificar.setOnAction(eve->{
         if(tablaHabitaciones.getSelectionModel().isEmpty()){
             new WobbleAnimation(tablaHabitaciones).Play();
            
         }else{
                try{
         FXMLLoader fxml = new FXMLLoader();
                         fxml.setLocation(getClass().getResource("RegistroHabitacion.fxml"));
                         fxml.load();
                         RegistroHabitacionController rthc = fxml.getController();
                         Stage stage = new Stage();
                         Scene scene = new Scene(fxml.getRoot());
                         
                         scene.setFill(Paint.valueOf("transparent"));
                         stage.setScene(scene);
                         stage.initOwner(btnRegistrar.getScene().getWindow());
                         stage.initModality(Modality.APPLICATION_MODAL);
                         stage.initStyle(StageStyle.TRANSPARENT);
                         rthc.iniciarVentana(tablaHabitaciones.getSelectionModel().getSelectedItem().getHabitacion());
                        
                         stage.showAndWait();
                         tablaHabitaciones.setItems(ma.getModeloVistaHabitaciones());
                         tablaHabitaciones.refresh();
                         
                                 } catch (IOException ex) {
                        System.out.println("sistema_hotel1.Servicio_generalesController.evento_registrar_habitacion()");
                     }
         }
         } );
     }
    private void evento_Eliminar_habitacion(){
        btnEliminar.setOnAction(ee->{
          if(tablaHabitaciones.getSelectionModel().isEmpty()){
             new WobbleAnimation(tablaHabitaciones).Play();
             
         }else{
               ma.Eliminar(tablaHabitaciones.getSelectionModel().getSelectedItem().getHabitacion());
             tablaHabitaciones.setItems(ma.getModeloVistaHabitaciones());
             tablaHabitaciones.refresh();
           }
        });
         
     }
}
