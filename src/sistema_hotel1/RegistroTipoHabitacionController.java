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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.CRUDS.CRUDBitacora;
import modelo.Servicio;
import modelo.TipoHabitacion;
import zunayedhassan.AnimateFX.FadeInAnimation;
import zunayedhassan.AnimateFX.FadeOutAnimation;

/**
 * FXML Controller class
 *
 * @author heyscar
 */
public class RegistroTipoHabitacionController implements Initializable {

   @FXML
   private Spinner<Integer> spinnerMaxPersonas;
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

private boolean update = false;
private TipoHabitacion habi_actualizar;
private Alertas aa;
private MetodosAdministradores ma;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ma = MetodosAdministradores.getInstance();
         spinnerMaxPersonas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
      evento_limita_caracters();
formatea_costo();

    }    
    public void iniciarVentana(){
        
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
      
    }
     public void iniciarVentana(TipoHabitacion habb){
        
        btnLimpiar.setVisible(false);
        this.update = true;
        this.habi_actualizar = habb;
         FadeInAnimation ani = new FadeInAnimation(grid);
        ani.GetTimeline().setOnFinished(eve->{ 
       
       textoNombre.setText(habb.getNombre());
       textoDescripcion.setText(habb.getDescripcion());
       textoCosto.setText(Double.toString(habb.getCosto()));
       spinnerMaxPersonas.getValueFactory().setValue(habb.getCant_max_personas());
        aa = new Alertas(Alert.AlertType.ERROR, "");
       aa.initOwner(grid.getScene().getWindow());

        });
        
        ani.Play();
        evento_boton_Cancelar();
        evento_boton_guaradar();
   
    }
  
    public void clear(){
         textoDescripcion.setText("");
        textoCosto.setText("");
        textoNombre.setText("");
        spinnerMaxPersonas.getValueFactory().setValue(1);
    }
      public void close(){
           clear();
        FadeOutAnimation ani = new FadeOutAnimation(btnGuardar.getScene().getRoot());
        ani.GetTimeline().setDelay(Duration.seconds(1.5));
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
      public void evento_boton_guaradar(){
        btnGuardar.setOnAction(eve->{
            if(textoNombre.getText().isEmpty() || textoCosto.getText().isEmpty()||textoDescripcion.getText().isEmpty()){
               llamaAlertita("rellene todos los campos", "yellow");
            }else{
                 if(update){
                    if( ma.Actualizar(new TipoHabitacion(habi_actualizar.getId_tipos_habitaciones(), textoNombre.getText(), textoDescripcion.getText(), spinnerMaxPersonas.getValue(), Double.parseDouble(textoCosto.getText()))
                    )){
                       llamaAlertita("ACTUALIZACION DE TIPO DE HABITACION EXITOSA", "green");
                        this.close();
                         
                    }else{
                        llamaAlertita("ERROR AL INTENTAR ACTUALIZAR TIPO DE HABITACION", "red");
                       
                    }
                     
                 }else{
                     if(ma.Registrar(new TipoHabitacion(0, textoNombre.getText(), textoDescripcion.getText(), spinnerMaxPersonas.getValue(), Double.parseDouble(textoCosto.getText())))){
                        
                        llamaAlertita("rEGISTRO DE TIPO DE HABITACION EXITOSO", "green");
                        this.close();
                       
                     }else{
                          llamaAlertita("ERROR AL INTENTAR REGISTRAR TIPO DE HABITACION", "red");
                       
                       
                     }
                 }   
            }
       
        });
        
    }
}
