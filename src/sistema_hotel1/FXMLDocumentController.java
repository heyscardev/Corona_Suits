/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import controlador.ControlSesion;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import zunayedhassan.AnimateFX.*;

/**
 *
 * @author pc
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private StackPane ContenedorPrincipal;
    @FXML
    private BorderPane container;
    @FXML
    private HBox hBoxPassword;
    @FXML
    VBox espacecorona;
    @FXML
    private Label label;
    @FXML
    private StackPane satckLateral;
    @FXML
    private Button boton1;
    private  int userMode  = 1;
    @FXML
    private Hyperlink cambio,olvidoContraseña;
    @FXML  Label textoAlerta;
    @FXML
    private TextField textoUsuario,textoContraseña;
    private Tooltip tooluser = new Tooltip(),toolpass = new Tooltip(),tooladminuser = new Tooltip();
    private Parent user1,admin1;
    private Interfaz_clienteController icc;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        config_();
       
        
    } 
    public void config_(){
         
      try {
          admin1 = FXMLLoader.load(getClass().getResource("BarraLateral.fxml"));
         admin1.setTranslateX(1920);
        
                ContenedorPrincipal.getChildren().add(admin1);
      } catch (IOException ex) {
          new Alertas(Alert.AlertType.ERROR);
      }
         try {
             FXMLLoader xml = new FXMLLoader();
             xml.setLocation(getClass().getResource("Interfaz_cliente.fxml"));
             xml.load();
          user1 = xml.getRoot();
         user1.setTranslateY(1080);
          icc = xml.getController();
               ContenedorPrincipal.getChildren().add(user1);
              
      } catch (IOException ex) {
           new Alertas(Alert.AlertType.ERROR);
      }
               olvidoContraseña.setVisible(false);
        textoUsuario.setTooltip(tooluser);
        textoContraseña.setTooltip(toolpass);
        cambio.setTooltip(tooladminuser);
        toolpass.setText("introduzca su contraseña esta es sensible\n a mayusculas y minisculas");
        cambiarModo();
        textoUsuario.setContextMenu(new ContextMenu());
         textoContraseña.setContextMenu(new ContextMenu());
          boton1.setOnAction(event->{ 
          this.evento_inicio_sesion();
          });
        
          textoUsuario.setOnKeyReleased(even->{  
          if(even.getCode()== KeyCode.ENTER ){
              evento_inicio_sesion();
          }
          });
           textoContraseña.setOnKeyReleased(even->{  
          if(even.getCode()== KeyCode.ENTER ){
              evento_inicio_sesion();
          }
          
        });
           olvidoContraseña.setOnAction(e->{
               Evento_olvido_contraseña();
          });
           
        textoUsuario.setOnKeyTyped(ev->{
            if (textoUsuario.getText().length()>=20){
            ev.consume();
            Toolkit.getDefaultToolkit().beep();
            }
        });
        
        textoContraseña.setOnKeyTyped(ev->{
            if (textoContraseña.getText().length()>=20){
            ev.consume();
            Toolkit.getDefaultToolkit().beep();
            }
        });
           
    }
    public void cambiarModo(){
         tooluser.setText("Introduzca codigo dado \n por la recepcion\n EJ:M12-123");
            tooladminuser.setText("selepcione para loguearse\n como administrador");
       hBoxPassword.setVisible(false);
       olvidoContraseña.setVisible(false);
        cambio.setOnAction(event->{
            FlipAnimation FL = new FlipAnimation(espacecorona);
            
            textoUsuario.setText("");
                textoContraseña.setText("");
            if(userMode == 0){
            
            userMode = 1;
            hBoxPassword.setVisible(false);
            tooluser.setText("Introduzca codigo dado \n por la recepcion\n EJ:M12-123");
            tooladminuser.setText("selepcione para loguearse\n como administrador");
               FlipAnimation FL1 = new FlipAnimation(cambio);
               FL1.GetTimeline().setOnFinished(EVENT->{  
               cambio.setText("Administrador");
               });
            FL1.Play(); 
            FL.Play(); 
            
             FL.GetTimeline().setOnFinished(value->{
            
            });
            
        }
        else{
            userMode = 0;
             hBoxPassword.setVisible(true);
             olvidoContraseña.setVisible(true);
             FlipAnimation FL1 = new FlipAnimation(cambio);
               FL1.GetTimeline().setOnFinished(EVENT->{  
            cambio.setText("Usuario");
               });
            FL1.Play(); 
            FL.Play(); 
            
              tooluser.setText("Administrador: \nIntroduzca su usuario");
            tooladminuser.setText("selepcione para loguearse\n como un usuario");
             FL.GetTimeline().setOnFinished(value->{
           
             });
            
        }
        
            
        
        });
       
    }
   public void evento_inicio_sesion(){
         ControlSesion cs = ControlSesion.getInstance();
           if(!valido_vacio()){
            
            if(userMode == 0){
            if(cs.iniciarSesion(textoUsuario.getText(), textoContraseña.getText()))
            {
                textoUsuario.setText("");
                textoContraseña.setText("");
                 BounceInRightAnimation NN = new BounceInRightAnimation(admin1);
                NN.Play();
                
            }
            }else{
                if(cs.iniciarSesion(textoUsuario.getText())){
                     textoUsuario.setText("");
                textoContraseña.setText("");
                FadeInUpBigAnimation U =  new FadeInUpBigAnimation(user1);
                icc.SesionActivate();
                 U.Play();  
            }
                
            }
           }
          
   }
   private boolean valido_vacio(){
         if(userMode == 0){
             if(textoUsuario.getText().equals("")||textoContraseña.getText().equals("")){
           textoAlerta.setVisible(true);
           FadeInAnimation fda = new FadeInAnimation(textoAlerta);
           FadeOutAnimation fdo = new FadeOutAnimation(textoAlerta);
           fdo.GetTimeline().setDelay(Duration.seconds(2));
           fda.GetTimeline().setOnFinished(vv->{ 
           fdo.Play();
           });
           fda.Play();
           return true;
       }
         }else{
              if(textoUsuario.getText().equals("")){
           textoAlerta.setVisible(true);
           FadeInAnimation fda = new FadeInAnimation(textoAlerta);
           FadeOutAnimation fdo = new FadeOutAnimation(textoAlerta);
           fdo.GetTimeline().setDelay(Duration.seconds(2));
           fda.GetTimeline().setOnFinished(vv->{ 
           fdo.Play();
           });
           fda.Play();
           return true; 
         }
         }
       
       return false;
   }
   private void Evento_olvido_contraseña(){
        
       try {
            Parent root = FXMLLoader.load(getClass().getResource("RecuperarContraseña.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, Paint.valueOf("transparent"));
            stage.setScene(scene);
            stage.initOwner(ContenedorPrincipal.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.showAndWait();
            textoUsuario.requestFocus();
                    
                    } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
      
        
               
   }
}
