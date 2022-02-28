/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import controlador.ControlSesion;
import controlador.MetodosAdministradores;
import java.awt.Toolkit;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.CRUDS.CRUDBitacora;
import modelo.Servicio;
import zunayedhassan.AnimateFX.FadeInAnimation;
import zunayedhassan.AnimateFX.FadeOutAnimation;

/**
 * FXML Controller class
 *
 * @author heyscar
 */
public class RegistroServiciosController implements Initializable {
@FXML
private Button btnLimpiar;
@FXML
private Button btnGuardar;
@FXML
private Button btnCancelar;
@FXML
private TextField textoNombre;
@FXML
private TextField textoCosto;
@FXML
private TextField textoDescripcion;
@FXML
private Label alertita;
@FXML
private GridPane grid;
//-----udate true para actualizar update false para registrar
private boolean update = false;
private Servicio serv_actualizar;
private Alertas aa;
private MetodosAdministradores ma;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       new FadeOutAnimation(alertita).Play();
       alertita.setVisible(true);
       
      
        ma = MetodosAdministradores.getInstance();
      evento_limita_caracters();
formatea_costo();
       

    }   
    public void iniciar_ventana(Servicio serv){
        btnLimpiar.setVisible(false);
        this.update = true;
        this.serv_actualizar = serv;
         FadeInAnimation ani = new FadeInAnimation(grid);
        ani.GetTimeline().setOnFinished(eve->{ 
       
       textoNombre.setText(serv.getNombre());
       textoDescripcion.setText(serv.getDescripcion());
       textoCosto.setText(Double.toString(serv.getPrecio()));
        aa = new Alertas(Alert.AlertType.ERROR, "");
       aa.initOwner(grid.getScene().getWindow());

        });
        
        ani.Play();
        evento_boton_Cancelar();
        evento_boton_guaradar();
   
    }
     public void iniciar_ventana( ){
        this.update = false;
        this.serv_actualizar = null;
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
      
    }
    public void clear(){
        textoDescripcion.setText("");
        textoCosto.setText("");
        textoNombre.setText("");
    }
    public void close(){
        clear();
        FadeOutAnimation ani = new FadeOutAnimation(btnGuardar.getScene().getRoot());
        ani.GetTimeline().setOnFinished(eve->{ 
        Stage p = (Stage) btnGuardar.getScene().getWindow();
       p.close();
        });
        ani.Play();
        
    }
    //----------eventos----------------
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
    public void evento_boton_guaradar(){
        btnGuardar.setOnAction(eve->{
            if(textoNombre.getText().isEmpty() || textoCosto.getText().isEmpty()||textoDescripcion.getText().isEmpty()){
               llamaAlertita("rellene todos los campos", "yellow");
            }else{
                 if(update){
                    if( ma.Actualizar(new Servicio(serv_actualizar.getId_servicio(), textoNombre.getText(), Double.parseDouble(textoCosto.getText()), textoDescripcion.getText()))){
                        MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "REGISTRAR SERVICIO", ControlSesion.getInstance().getNombreUsuario());
                        aa.setAlertType(Alert.AlertType.INFORMATION);
                        aa.setHeaderText("INFORMACION");
                        aa.setContentText("Actualizacion de Servicio Exitosa");
                        aa.showAndWait();
                        this.close();
                        BarraLateralController.getControlServicios().actualizarTabla();
                    }else{
                        llamaAlertita("ERROR AL INTENTAR ACTUALIZAR SERVICIO", "red");
                       
                    }
                     
                 }else{
                     if(ma.Registrar(new Servicio(0, textoNombre.getText(), Double.parseDouble(textoCosto.getText()), textoDescripcion.getText()))){
                        MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "REGISTRAR SERVICIO", ControlSesion.getInstance().getNombreUsuario());
                        CRUDBitacora.getInstance().createBServicio();
                        aa.setAlertType(Alert.AlertType.INFORMATION);
                        aa.setHeaderText("INFORMACION");
                        aa.setContentText("Registro de Servicio Exitoso");
                        aa.showAndWait();
                        this.close();
                        BarraLateralController.getControlServicios().actualizarTabla();
                     }else{
                          llamaAlertita("ERROR AL INTENTAR REGISTRAR EL SERVICIO", "red");
                       
                       
                     }
                 }   
            }
       
        });
        
    }
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
    
     public void evento_limita_caracters(){
        textoNombre.setOnKeyTyped(event->{  
         if(textoNombre.getText().length()>=20){
             event.consume();
             Toolkit.getDefaultToolkit().beep();
               llamaAlertita("no se puede introducir mas caracteres", "yellow");
         }
         });
        textoDescripcion.setOnKeyTyped(event->{  
         if(textoDescripcion.getText().length()>=50){
             event.consume();
             Toolkit.getDefaultToolkit().beep();
               llamaAlertita("no se puede introducir mas caracteres", "yellow");
         }
         });
         
     }
    public void formatea_costo(){
        textoCosto.setOnKeyTyped(evento->{
        Character e = evento.getCharacter().charAt(0);
       
        if(Character.isDigit(e)||e == '.' ){
            boolean n = false;
            int decimales = 0;
             for(int i = 0;i<textoCosto.getText().length();i++){
                 if(n)
                    decimales++;
                
                if(textoCosto.getText().charAt(i)=='.')n =true;
                
            }if(n &&e=='.'){
                evento.consume();
                 Toolkit.getDefaultToolkit().beep();
            }  if(decimales>=2){
                 evento.consume();
                 Toolkit.getDefaultToolkit().beep();
            } 
            
        }else{
            evento.consume();
            Toolkit.getDefaultToolkit().beep();
            
        }
        });
       textoCosto.setOnDragExited(eve->{
           String nuevo = "";
            boolean n = false;
            int decimales = 0;
        for(int i = 0;i<textoCosto.getText().length();i++){
            
            if(decimales>=2){
                break;
            }
             if(n){
                    decimales++;
             }
                if(textoCosto.getText().charAt(i)=='.')n =true;
                nuevo = nuevo+textoCosto.getText().charAt(i);
        }
        textoCosto.setText(nuevo);
       });
       
    }
    
    
    
}
