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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import modelo.CRUDS.CRUDReservacion;
import modelo.Reservacion;
import zunayedhassan.AnimateFX.FadeInAnimation;
import zunayedhassan.AnimateFX.FadeOutAnimation;

/**
 * FXML Controller class
 *
 * @author heyscar
 */
public class DetallesReservacionController implements Initializable,Ventana{
    @FXML
    private Button btnEliminar;
    @FXML
    private VBox vboxFactura;
    @FXML
    private TextField textoCedula,textoNombre,textoApellido,textoSexo,textoCodigo;
    @FXML
    private DatePicker fechaNacimiento,fechaLLegada,fechaSalida;
    private ComponenteFacturaController cfc;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    public void cargarDetalles(Reservacion rs){
        if(rs != null){
        textoCodigo.setText(rs.getCodigoReservacion());
        textoCedula.setText(rs.getCedula_cliente());
        textoNombre.setText(rs.getNombre_cliente());
        textoSexo.setText(rs.getCliente().getSexo());
        textoApellido.setText(rs.getCliente().getApellido());
        fechaNacimiento.setValue(rs.getCliente().getFecha_nacimiento());
        fechaLLegada.setValue(rs.getFechaEntrada());
        fechaSalida.setValue(rs.getFechaSalida());
           try {
               if(cfc==null){
            FXMLLoader fx = new FXMLLoader(getClass().getResource("ComponenteFactura.fxml"));
            fx.load();
             cfc = fx.getController();
            vboxFactura.getChildren().add(fx.getRoot());
               }
            cfc.generarFactura(CRUDReservacion.getInstance().findOneCodigo_reservacion(rs.getCodigoReservacion()));
                btnEliminar.setOnAction(ee->{
                MetodosAdministradores.getInstance().Eliminar(rs);
               
                
                ControlVentanas.getInstance().llamar_inicio();
                });
           
        } catch (IOException ex) {
            Logger.getLogger(DetallesReservacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }     
    }
    @Override
    public void _config() {
        
    }

    @Override
    public void iniciar_Ventana() {
        
    }

    @Override
    public void clear() {
        textoCedula.setText("");
        textoNombre.setText("");
        textoApellido.setText("");
        textoSexo.setText("");
        textoCodigo.setText("");
        cfc.clearFactura();
        btnEliminar.setOnAction(ee->{});
        fechaNacimiento.getEditor().setText("");
        fechaLLegada.getEditor().setText("");
        fechaSalida.getEditor().setText("");
    }
   

    @Override
    public void close() {
        clear();
    }
    
    
}
