/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.Window;
import modelo.Conexion;

/**
 *
 * @author pc
 */
public class Sistema_hotel1 extends Application {
    private static Scene pi ;
    @Override
    public void start(Stage stage){
 try{
          Conexion.getInstance().conectar_inicio();
           Conexion.getInstance().desconectar(); 
            try {
           
           
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            
            Scene scene = new Scene(root);
            pi = scene;
            scene.setFill(Paint.valueOf("transparent"));
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResource("icono_logo.png").toExternalForm()));
            stage.setMaxWidth(1920);
            stage.setMaxHeight(1080);
            stage.setMinWidth(1024);
            stage.setMinHeight(560);
            stage.setWidth(1024);
            stage.setHeight(600);
            stage.show();
        } catch (IOException ex) {
                new Alertas(Alert.AlertType.ERROR);
        }
 }catch (SQLException ex) {
            System.out.println("no se ha podido establecer conexion con la base de datos");
            Alertas a = new Alertas(Alert.AlertType.WARNING,"");
             a.setContentText("no se ha podido establecer conexion con la base de datos \n"
                     + "establezca conexion para poder iniciar");
             a.showAndWait();
        }
          
        
       
    }
    public static Window getWindow(){
       if(pi!=null) return pi.getWindow();
       return null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
