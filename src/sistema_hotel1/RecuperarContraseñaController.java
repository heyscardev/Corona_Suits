/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import controlador.RecuperacionMetodos;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import zunayedhassan.AnimateFX.*;

/**
 * FXML Controller class
 *
 * @author heyscar
 */
public class RecuperarContraseñaController implements Initializable, Ventana {
    @FXML
     private VBox vistaPedirUusario;
    @FXML
    
     private VBox vistaPedirPreguntas;
    @FXML
     private VBox vistaPedirNuevaContraseña;
    @FXML
     private VBox vistaSucessfully;
    @FXML
     private VBox vistaError;
     
    @FXML
    private Button btnSiguiente1;
    @FXML
    private Button btnSiguiente2;
    @FXML
    private Button btnSiguiente3;
    @FXML
    private Button btnAtras1;
    @FXML
    private Button btnAtras2;
    @FXML
    private Button btnAtras3;
    @FXML
    private Label p1,p2,p3;
    @FXML
    private TextField r1,r2,r3;
    @FXML
    private TextField nombreUsuario;
    @FXML
    private TextField contraseN;
    @FXML
    private TextField contraseñaNConf;
    @FXML
    private Label MensajeRecuperacion;
    private RecuperacionMetodos recup;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       _config();
       iniciar_Ventana();
    }    

    @Override
    public void _config() {
       RecuperacionMetodos ee = new RecuperacionMetodos();
       vistaError.setTranslateX(300);
         vistaSucessfully.setTranslateX(300);
           vistaPedirUusario.setTranslateX(0);
             vistaPedirNuevaContraseña.setTranslateX(300);
               vistaPedirPreguntas.setTranslateX(300);
               
         Evento_siguiente();
         btnAtras2 .setOnAction(eve->{ evento_Atras_preguntas_seguridad();});
         btnAtras3.setOnAction(eve->{ this.close();});
          btnAtras1.setOnAction(eve->{ this.close();  });
               
    }

    @Override
    public void iniciar_Ventana() {
       recup = new RecuperacionMetodos();
    }
    private void llamar_success(Node node){
       BounceOutLeftAnimation salida = new BounceOutLeftAnimation(node);
          
              salida.Play();
              
              
              
        
         BounceOutLeftAnimation salidae = new BounceOutLeftAnimation(vistaSucessfully);
      BounceInRightAnimation entradae = new BounceInRightAnimation(vistaSucessfully);
      entradae.GetTimeline().setDelay(Duration.seconds(1));
      salidae.GetTimeline().setDelay(Duration.seconds(3));
      
           entradae.Play();
      salidae.Play();
      
           
    }
    private void llamar_Error(String error,Node node){
        MensajeRecuperacion.setText(error);
        
          BounceOutLeftAnimation salida = new BounceOutLeftAnimation(node);
               
                   
              BounceInRightAnimation entrada = new BounceInRightAnimation(node);
              entrada.GetTimeline().setDelay(Duration.seconds(0.6));
              salida.Play();
              
              
              
        
         BounceOutLeftAnimation salidae = new BounceOutLeftAnimation(vistaError);
      BounceInRightAnimation entradae = new BounceInRightAnimation(vistaError);
      entradae.GetTimeline().setDelay(Duration.seconds(1));
      salidae.GetTimeline().setDelay(Duration.seconds(3));
      entrada.GetTimeline().setDelay(Duration.seconds(4));
           entradae.Play();
      salidae.Play();
      entrada.Play();
    }
   
    @Override
    public void clear() {
         p1.setText("");
     p2.setText("");
     p3.setText("");
     r1.setText("");
     r2.setText("");
     r3.setText("");
     contraseN.setText("");
     contraseñaNConf.setText("");
     nombreUsuario.setText("");
    }

    @Override
    public void close() {
    
     Stage stage  = (Stage) btnAtras1.getScene().getWindow();
     stage.close();
    }
    public void close(double delay) {
    FadeOutAnimation dd  = new FadeOutAnimation(btnAtras1.getScene().getRoot());
    dd.GetTimeline().setDelay(Duration.seconds(delay));
      dd.GetTimeline().setOnFinished(eve->{
           Stage stage  = (Stage) btnAtras1.getScene().getWindow();
     stage.close();
      });
    dd.Play();
    }
    //--------eventos-------------------
   
    private void Evento_siguiente(){
       
     btnSiguiente1.setOnAction(eve->{
         
          ArrayList<String> arrayPreguntas= recup.getPreguntasSeguridad(nombreUsuario.getText());
          if (arrayPreguntas==null) {
              
               
                   String er = "USUARIO NO EXISTE O\nNO TIENE PREGUNTAS\nDE SEGURIDAD";
              if(nombreUsuario.getText().isEmpty())er = "USUARIO ESTÁ VACIO";
               llamar_Error(er, vistaPedirUusario);
              
              
         }else{
              BounceOutLeftAnimation salida = new BounceOutLeftAnimation(vistaPedirUusario);
                BounceInRightAnimation entrada = new BounceInRightAnimation(vistaPedirPreguntas);
                p1.setText(arrayPreguntas.get(0));
                p2.setText(arrayPreguntas.get(1));
                p3.setText(arrayPreguntas.get(2));
                
                salida.GetTimeline().setOnFinished(ee->{
                    
           });
      entrada.GetTimeline().setDelay(Duration.seconds(0.6));
           salida.Play();
           entrada.Play();
           nombreUsuario.setText("");
          }
      
      
     });
     btnSiguiente2.setOnAction(eve->{
        if( recup.ValidaRespuesta(r1.getText().toUpperCase(), r2.getText().toUpperCase(), r3.getText().toUpperCase())){
      BounceOutLeftAnimation salida = new BounceOutLeftAnimation(vistaPedirPreguntas);
      BounceInRightAnimation entrada = new BounceInRightAnimation(vistaPedirNuevaContraseña);
      salida.GetTimeline().setOnFinished(ee->{
          clear();
           });
      entrada.GetTimeline().setDelay(Duration.seconds(0.6));
           salida.Play();
           entrada.Play();
        }else{
            
            llamar_Error("RECUPERACION FALLIDA", vistaPedirPreguntas);
            
        }
     
     });
     btnSiguiente3.setOnAction(eve->{
         if(!contraseñaNConf.getText().equals(contraseN.getText())){
             llamar_Error("Las contraseñas no son iguales", vistaPedirNuevaContraseña);
         }else if(contraseN.getText().isEmpty() || contraseñaNConf.getText().isEmpty()){
             llamar_Error("Hay campos vacios", vistaPedirNuevaContraseña);
         }else{
             if (recup.Cambio_Contraseña(contraseN.getText())) {
                 llamar_success(vistaPedirNuevaContraseña);
                 close(2);
             }else{
                 llamar_Error("ERROR AL CAMBIAR\nCONTRASEÑA", p1);
                 close(2);
             }
         }
    
            
        
     });
    }
    private void evento_Atras_preguntas_seguridad(){
           BounceOutRightAnimation salida = new BounceOutRightAnimation(vistaPedirPreguntas);
           BounceInLeftAnimation entrada = new BounceInLeftAnimation(vistaPedirUusario);
           salida.GetTimeline().setOnFinished(ee->{
           
           
           });
           entrada.GetTimeline().setDelay(Duration.seconds(0.6));
           salida.Play();
           entrada.Play();
    }
   
}
