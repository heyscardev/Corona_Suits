
package sistema_hotel1;

import controlador.MetodosAdministradores;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modelo.Servicio;
import zunayedhassan.AnimateFX.FadeInAnimation;
import zunayedhassan.AnimateFX.FadeOutAnimation;


public class ServiciosController implements Initializable {
@FXML
private TableColumn columnaTipo;
@FXML
private TableColumn columnaDescripcion;
@FXML
private TableColumn columnaCosto;
@FXML
private Button btnDelete;
@FXML
private Button btnRegister;
@FXML
private Button btnModify;
@FXML
private TableView<Servicio> tablaServicios;
@FXML Label alertita;
private MetodosAdministradores ma;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
       _config();
      eventoBotonRegister();
     eventoBotonDelete();
     eventoBotonModify();
    
    }    
    private void _config(){
         ma = MetodosAdministradores.getInstance();
          columnaTipo.setCellValueFactory(new PropertyValueFactory("Nombre"));
    columnaDescripcion.setCellValueFactory(new PropertyValueFactory("Descripcion"));
    columnaCosto.setCellValueFactory(new PropertyValueFactory("CostoFormater"));
    
     }
    public void iniciar_ventana(){
         actualizarTabla();
     }
    public void clear(){
        tablaServicios.getItems().clear();
    }
    public void close(){
        
    }
    public void actualizarTabla(){
        tablaServicios.setItems(ma.getAllServicios());
        tablaServicios.refresh();
    }
      public void llamaAlertita(String mensaje,String Color){
         FadeInAnimation entra= new FadeInAnimation(alertita);
         FadeOutAnimation sale = new FadeOutAnimation(alertita);
         sale.GetTimeline().setDelay(Duration.seconds(1.5));
         entra.GetTimeline().setOnFinished(eve->{  
         sale.Play();
         
         });
        alertita.setText(mensaje);
        alertita.setStyle("-fx-text-fill: "+ Color +";");
        entra.Play();
        alertita.setVisible(true);
    }
      //----------------eventos------------------------
    
    public void  eventoBotonDelete(){
        btnDelete.setOnAction(eve->{ 
        if(tablaServicios.getSelectionModel().isEmpty()){
          
           this.llamaAlertita("debe seleccionar una reservacion primero", "yellow");
            tablaServicios.requestFocus();
        }else{ 
        ma.Eliminar(tablaServicios.getSelectionModel().getSelectedItem());
        actualizarTabla();
        }
        
        });
    }
    public void  eventoBotonRegister(){
        btnRegister.setOnAction(eve->{
         try {
        FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("RegistroServicios.fxml"));
        fxml.load();
        RegistroServiciosController rsc = fxml.getController();
        Stage stage = new Stage();
        Scene  scene = new Scene(fxml.getRoot());
        stage.setScene(scene);
          scene.setFill(Paint.valueOf("transparent"));
       stage.initOwner(btnDelete.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        rsc.iniciar_ventana();
        
       
        
    } catch (IOException ex) {
             llamaAlertita("error al abrir ventana de registro servicio", "red");
    }
        });
   
    }
    public void  eventoBotonModify(){
         btnModify.setOnAction(eve->{
             if(!tablaServicios.getSelectionModel().isEmpty()){
         try {
        FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("RegistroServicios.fxml"));
        fxml.load();
        RegistroServiciosController rsc = fxml.getController();
        Stage stage = new Stage();
        Scene  scene = new Scene(fxml.getRoot());
        stage.setScene(scene);
        scene.setFill(Paint.valueOf("transparent"));
       stage.initOwner(btnDelete.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        rsc.iniciar_ventana(tablaServicios.getSelectionModel().getSelectedItem());
        
    } catch (IOException ex) {
             System.out.println("error");
    }}else{
             this.llamaAlertita("debe seleccionar una reservacion primero", "yellow");
            tablaServicios.requestFocus();
             }
        });
    }
}
