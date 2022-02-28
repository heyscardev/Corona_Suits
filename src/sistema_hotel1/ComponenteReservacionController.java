/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modelo.Reservacion;

/**
 * FXML Controller class
 *
 * @author heyscar
 */
public class ComponenteReservacionController  implements Initializable {
@FXML
private Label labelNombre,labelCedula,labelFechaInicio,LabelPrecioTotal,labelFechaSalida,labelCodigoReservacion;
private Reservacion reservacion;
@FXML
private Button btnDetalles;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    
    public void IniciarComponente(Reservacion res){
       labelCedula.setText(res.getCedula_cliente());
       labelNombre.setText(res.getNombre_cliente());
       labelCodigoReservacion.setText(res.getCodigoReservacion());
       
       labelFechaInicio.setText(res.getSFechaEntrada() + " : " + res.getStringHoraEntradaformat12());
       labelFechaSalida.setText(res.getSFechaSalida()+ " : " + res.getStringHoraSalidaformat12());
       LabelPrecioTotal.setText(String.format(" %.2f $", res.getPreioTotal()));
       btnDetalles.setOnAction(eve->{
       ControlVentanas.getInstance().llamar_detalles(res);
       
       
       
       });
    }
   
    public Reservacion  getReservacion(){
        return reservacion;
    }
    public void setOnActionDetalles(EventHandler<ActionEvent> e){
        btnDetalles.setOnAction(e);
    }
   
}
