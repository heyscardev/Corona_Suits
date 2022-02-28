/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import controlador.ControlSesion;
import controlador.MetodosAdministradores;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modelo.CRUDS.CRUDBitacora;
import modelo.Habitacion;
import modelo.TipoHabitacion;
import zunayedhassan.AnimateFX.FadeInAnimation;
import zunayedhassan.AnimateFX.FadeOutAnimation;

/**
 * FXML Controller class
 *
 * @author heyscar
 */
public class RegistroHabitacionController implements Initializable {

   

   @FXML
private Button btnLimpiar;
@FXML
private Button btnGuardar;
@FXML
private Button btnCancelar;
@FXML
private Button btnModificarTipo;
@FXML
private Spinner<Integer> piso;
@FXML
private Spinner<Integer> numero;
@FXML
private ComboBox<TipoHabitacion> TiposHabitaciones;
@FXML
private Label alertita;
@FXML
private GridPane grid;

private boolean update = false;
private Habitacion habi_actualizar;
private Alertas aa;
private MetodosAdministradores ma;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ma = MetodosAdministradores.getInstance();
         piso.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
          numero.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
  btnModificarTipo.setVisible(false);
    }    
    public void iniciarVentana(){
        this.actualizarComboTipoHabitaciones();
           this.update = false;
        this.habi_actualizar = null;
         FadeInAnimation ani = new FadeInAnimation(grid);
        ani.GetTimeline().setOnFinished(eve->{ 
       aa = new Alertas(Alert.AlertType.ERROR, "");
       aa.initOwner(grid.getScene().getWindow());

        });
        
        ani.Play();
//llamada de eventos
        evento_boton_Cancelar();
        evento_boton_guaradar();
       evento_boton_limpiar();
       evento_boton_actualizar_tipo();
      
    }
     public void iniciarVentana(Habitacion habb){
       this.actualizarComboTipoHabitaciones();
        btnLimpiar.setVisible(false);
        this.update = true;
        this.habi_actualizar = habb;
         FadeInAnimation ani = new FadeInAnimation(grid);
        ani.GetTimeline().setOnFinished(eve->{ 
       
       numero.getValueFactory().setValue(habb.getNumeroHabitacion());
    piso.getValueFactory().setValue(habb.getPiso());
    for(TipoHabitacion TP :TiposHabitaciones.getItems() ){
        if(TP.getId_tipos_habitaciones() == habb.getId_tipo_habitaciones()){
             TiposHabitaciones.getSelectionModel().select(TP);
        }
    }
        aa = new Alertas(Alert.AlertType.ERROR, "");
       aa.initOwner(grid.getScene().getWindow());

        });
        
        ani.Play();
        evento_boton_Cancelar();
        evento_boton_guaradar();
        evento_boton_actualizar_tipo();
   
    }
  public void actualizarComboTipoHabitaciones(){
        btnModificarTipo.setVisible(false);
      TiposHabitaciones.setItems(ma.getAllTipoHabitaciones());
      TiposHabitaciones.getItems().add(new TipoHabitacion(-1, "NUEVO TIPO HABITACION", " ", 0, 0.00));
  }
    public void clear(){
          btnModificarTipo.setVisible(false);
       numero.getValueFactory().setValue(0);
    piso.getValueFactory().setValue(0);
   
             TiposHabitaciones.getItems().clear();
       
    }
      public void close(){
           clear();
        FadeOutAnimation ani = new FadeOutAnimation(btnGuardar.getScene().getRoot());
        ani.GetTimeline().setDelay(Duration.seconds(00.5));
        ani.GetTimeline().setOnFinished(eve->{ 
        Stage p = (Stage) btnGuardar.getScene().getWindow();
       p.close();
        });
        ani.Play();
      }     
    public void llamaAlertita(String mensaje,String Color){
         FadeInAnimation entra= new FadeInAnimation(alertita);
         FadeOutAnimation sale = new FadeOutAnimation(alertita);
         sale.GetTimeline().setDelay(Duration.seconds(3));
         entra.GetTimeline().setOnFinished(eve->{  
         sale.Play();
         
         });
        alertita.setText(mensaje);
        alertita.setStyle("-fx-text-fill: "+ Color +";");
        entra.Play();
        alertita.setVisible(true);
    }
   
    //-------------eventos-----------------------
    
      public void evento_boton_limpiar(){
        btnLimpiar.setOnAction(eve->{
        this.clear();
        } );
    }
    public void evento_boton_Cancelar(){
       btnCancelar.setOnAction(eve->{
         this.close();
       });
   
   
    }
   public void evento_boton_guaradar(){
        btnGuardar.setOnAction(eve->{
            if(TiposHabitaciones.getSelectionModel().isEmpty()|| TiposHabitaciones.getSelectionModel().getSelectedItem().getId_tipos_habitaciones()==-1){
               llamaAlertita("seleccione o registre un Tipo de Habitaciontodos ", "red");
            }else{
                
                
                 if(update){
                     Alertas ab = new Alertas(Alert.AlertType.CONFIRMATION, "Los cambios seran aplicados tambien \n a las ¿habitaciones de los clientes desea continuar? ");
                ab.setHeaderText("cuidado!");
                ab.showAndWait();
                     if(ab.getResult() != ButtonType.CANCEL){
                    if( ma.Actualizar(new Habitacion(habi_actualizar.getId(),piso.getValue() , numero.getValue(), TiposHabitaciones.getSelectionModel().getSelectedItem().getId_tipos_habitaciones())
                    )){
                       llamaAlertita("ACTUALIZACION DE  HABITACION EXITOSA", "green");
                       MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "ACTUALIZAR HABITACIÓN", ControlSesion.getInstance().getNombreUsuario());
                        this.close();
                         
                    }else{
                        llamaAlertita("ERROR AL INTENTAR ACTUALIZAR TIPO DE HABITACION", "red");
                       
                    }
                     }
                 }else{  Alertas ab = new Alertas(Alert.AlertType.CONFIRMATION, "Los cambios seran aplicados tambien \n a las ¿habitaciones de los clientes desea continuar? ");
                ab.setHeaderText("cuidado!");
                ab.showAndWait();
                     if(ab.getResult() != ButtonType.CANCEL){
                     if(ma.Registrar(new Habitacion(0,piso.getValue() , numero.getValue(), TiposHabitaciones.getSelectionModel().getSelectedItem().getId_tipos_habitaciones())
                    )){
                        MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "REGISTRAR HABITACIÓN", ControlSesion.getInstance().getNombreUsuario());
                        CRUDBitacora.getInstance().createBHabitacion();
                        llamaAlertita("REGISTRO DE TIPO DE HABITACION EXITOSO", "green");
                        this.close();
                       
                     }else{
                          llamaAlertita("ERROR AL INTENTAR REGISTRAR TIPO DE HABITACION", "red");
                       
                       
                     }
                     }
                 }   
            }
       
        });
        
    }
    public void     evento_boton_actualizar_tipo(){
             TiposHabitaciones.setOnAction(eve->{
             if(!TiposHabitaciones.getSelectionModel().isEmpty()){
                 if(TiposHabitaciones.getSelectionModel().getSelectedItem().getId_tipos_habitaciones() == -1){
                     btnModificarTipo.setVisible(false);
                     try {
                         FXMLLoader fxml = new FXMLLoader();
                         fxml.setLocation(getClass().getResource("RegistroTipoHabitacion.fxml"));
                         fxml.load();
                         RegistroTipoHabitacionController rthc = fxml.getController();
                         Stage stage = new Stage();
                         Scene scene = new Scene(fxml.getRoot());
                         
                         scene.setFill(Paint.valueOf("transparent"));
                         stage.setScene(scene);
                         stage.initOwner(grid.getScene().getWindow());
                         stage.initModality(Modality.APPLICATION_MODAL);
                         stage.initStyle(StageStyle.TRANSPARENT);
                         rthc.iniciarVentana();
                         stage.showAndWait();
                         this.actualizarComboTipoHabitaciones();
                         int i = ma.getUltimoTipoHabitacion().getId_tipos_habitaciones();
                         for(TipoHabitacion tp :TiposHabitaciones.getItems()){
                             if(tp.getId_tipos_habitaciones() == i )
                                 TiposHabitaciones.getSelectionModel().select(tp);
                         }
                         
                                 } catch (IOException ex) {
                         llamaAlertita("error al llamar el registro", "red");
                     }
                 }else{
                     btnModificarTipo.setVisible(true);
                 }
             }
             });
             btnModificarTipo.setOnAction(eve->{ 
             if(!TiposHabitaciones.getSelectionModel().isEmpty()){
                  try {
                         FXMLLoader fxml = new FXMLLoader();
                         fxml.setLocation(getClass().getResource("RegistroTipoHabitacion.fxml"));
                         fxml.load();
                         RegistroTipoHabitacionController rthc = fxml.getController();
                         Stage stage = new Stage();
                         Scene scene = new Scene(fxml.getRoot());
                         
                         scene.setFill(Paint.valueOf("transparent"));
                         stage.setScene(scene);
                         stage.initOwner(grid.getScene().getWindow());
                         stage.initModality(Modality.APPLICATION_MODAL);
                         stage.initStyle(StageStyle.TRANSPARENT);
                         rthc.iniciarVentana(TiposHabitaciones.getSelectionModel().getSelectedItem());
                         TipoHabitacion temporal =TiposHabitaciones.getSelectionModel().getSelectedItem();
                         stage.showAndWait();
                         this.actualizarComboTipoHabitaciones();
                        
                         for(TipoHabitacion tp :TiposHabitaciones.getItems()){
                             if(tp.getId_tipos_habitaciones() == temporal.getId_tipos_habitaciones())
                                 TiposHabitaciones.getSelectionModel().select(tp);
                         }
                         
                                 } catch (IOException ex) {
                         llamaAlertita("error al llamar el registro", "red");
                     }
             }
             
             });
         }
}
