/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import controlador.MetodosAdministradores;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import modelo.Reservacion;

/**
 * FXML Controller class
 *
 * @author heyscar
 */
public class MostrarReservacionesController implements Initializable, Ventana {
    @FXML
    VBox vista;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void _config() {
        
    }

    @Override
    public void iniciar_Ventana() {
        for (Reservacion rr: MetodosAdministradores.getInstance().getAllReservaciones()) {
            try {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("ComponenteReservacion.fxml"));
                fxml.load();
                Parent root =  fxml.getRoot();
                ComponenteReservacionController control = fxml.getController();
                control.IniciarComponente(rr);
                vista.getChildren().add(root);
                
                
            } catch (IOException ex) {
                Logger.getLogger(MostrarReservacionesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
  
    public void iniciar_Ventana(LocalDate fecha) {
        for (Reservacion rr: MetodosAdministradores.getInstance().getReservacionBYDay(fecha)) {
            try {
                System.out.println("sistema_hotel1.MostrarReservacionesController.iniciar_Ventana()");
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("ComponenteReservacion.fxml"));
                fxml.load();
                Parent root =  fxml.getRoot();
                ComponenteReservacionController control = fxml.getController();
                control.IniciarComponente(rr);
                vista.getChildren().add(root);
                System.out.println("sistema_hotel1.MostrarReservacionesController.iniciar_Ventana()");
                
            } catch (IOException ex) {
                Logger.getLogger(MostrarReservacionesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        vista.getChildren().clear();
        
           }
    
}
