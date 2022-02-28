/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import controlador.MetodosAdministradores;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import modelo.Reservacion;
import zunayedhassan.AnimateFX.FadeInAnimation;
import zunayedhassan.AnimateFX.FadeOutAnimation;
import zunayedhassan.AnimateFX.RotateInUpRightAnimation;
import zunayedhassan.AnimateFX.ShakeAnimation;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class Consultar_reservacionController implements Initializable,Ventana {
@FXML 
private TableView<Reservacion> TablaReservaciones;
@FXML 
private TableColumn columnaNombreUsuario;
@FXML 
private TableColumn columnaCodigoReservacion;
@FXML 
private TableColumn columnaFechaEntrada;
@FXML 
private TableColumn columnaFechaSalida;
@FXML 
private TableColumn columnaCedulaCliente;

@FXML
private Button btnRegistrar;
@FXML
private Button btnModificar;
@FXML
private Button btnEliminar;
@FXML
private Button btnVerDetalles;
@FXML
private TextField Buscar;
@FXML
private Label Alerta;
 
private MetodosAdministradores ma;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      _config();
    }    
     @Override
    public void _config() {
        ma = MetodosAdministradores.getInstance();
        columnaCodigoReservacion.setCellValueFactory(new PropertyValueFactory("CodigoReservacion"));
        columnaFechaEntrada.setCellValueFactory(new PropertyValueFactory("SFechaEntrada"));
        columnaFechaSalida.setCellValueFactory(new PropertyValueFactory("SFechaSalida"));
        columnaCedulaCliente.setCellValueFactory(new PropertyValueFactory("Cedula_cliente"));
        columnaNombreUsuario.setCellValueFactory(new PropertyValueFactory("Nombre_cliente"));
        evento_botonRegistro();
        evento_boton_eliminar();
        evento_boton_modificar();
        btnVerDetalles.setOnAction(eve->{
            if(!TablaReservaciones.getSelectionModel().isEmpty())
         ControlVentanas.getInstance().llamar_detalles(TablaReservaciones.getSelectionModel().getSelectedItem());
            else
                llamaAlertita("debe selepcionar una reservacion", "yellow");
        });
    }

    @Override
    public void iniciar_Ventana() {
      this.actualizarTabla();
    }
    public void actualizarTabla(){
       TablaReservaciones.setItems(ma.getAllReservaciones());
       TablaReservaciones.refresh();
    }
    @Override
    public void clear() {
        TablaReservaciones.getItems().clear();
    }

    @Override
    public void close() {
        clear();
    }
     public void llamaAlertita(String mensaje,String Color){
         FadeInAnimation entra= new FadeInAnimation(Alerta);
         FadeOutAnimation sale = new FadeOutAnimation(Alerta);
         sale.GetTimeline().setDelay(Duration.seconds(3));
         entra.GetTimeline().setOnFinished(eve->{  
         sale.Play();
         
         });
        Alerta.setText(mensaje);
        Alerta.setStyle("-fx-text-fill: "+ Color +";");
        entra.Play();
        Alerta.setVisible(true);
    }
    
    //-------------------eventos---------------
    private void evento_botonRegistro(){
        btnRegistrar.setOnAction(eve->{
           ControlVentanas.getInstance().llamar_RegistroReservacion();
               
           
        });
        
    }
    private void evento_boton_eliminar(){
        btnEliminar.setOnAction(eve->{
            if(TablaReservaciones.getSelectionModel().isEmpty()){
                new ShakeAnimation(TablaReservaciones).Play();
                llamaAlertita("debe selepcionar una reservacion", "yellow");
            }else{
                Reservacion rs  = TablaReservaciones.getSelectionModel().getSelectedItem();
        Alertas aa  = new Alertas(Alert.AlertType.CONFIRMATION, 
                "al eliminar la reservacion tambien se eliminaran \n"
                + "las habitaciones y/o servicios que este aya podido contratar\n"
                + "desea continuar?");
        aa.setHeaderText("Cuidado!");
        aa.showAndWait();
        if(aa.getResult() != ButtonType.CANCEL){
            ma.Eliminar(rs);
            llamaAlertita("reservacion eliminada con  Exito", "green");
            TablaReservaciones.setItems(ma.getAllReservaciones());
            TablaReservaciones.refresh();
        }
            }
        
        });
    }
    private void evento_boton_modificar(){
        btnModificar.setOnAction(event->{
               if(TablaReservaciones.getSelectionModel().isEmpty()){
                new ShakeAnimation(TablaReservaciones).Play();
                llamaAlertita("debe selepcionar una reservacion", "yellow");
            }else{
     ControlVentanas.getInstance().llamar_RegistroReservacion();
      ControlVentanas.getInstance().getControlRegistrarReservacion().close();
      ControlVentanas.getInstance().getControlRegistrarReservacion().iniciar_Ventana(TablaReservaciones.getSelectionModel().getSelectedItem());
               }
    } );
    }
   
   
    
}
