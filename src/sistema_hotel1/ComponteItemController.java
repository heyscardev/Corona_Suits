/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import modelo.ContratoHabitacion;
import modelo.ContratoServicio;
import modelo.Factura;

/**
 * FXML Controller class
 *
 * @author heyscar
 */
public class ComponteItemController implements Initializable {

    @FXML
    Label Descripcion;
        @FXML
    Label cantidad;
            @FXML
    Label precioUnitario;
                @FXML
    Label PrecioTotal;
                 @FXML
    Label numero;
                 double preciot = 0;
                
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void iniciar_Componente(ContratoHabitacion cn, int n ){
        numero.setText(Integer.toString(n));
        Descripcion.setText(cn.getNombreHabitacion());
       precioUnitario.setText(String.format("%.2f$", cn.getPrecioFacturado()));
       cantidad.setText("1");
       PrecioTotal.setText(String.format("%.2f$", cn.getPrecioFacturado()));
       this.preciot = cn.getPrecioFacturado();
    }
     public void iniciar_Componente(ContratoServicio cn ,int n){
         numero.setText(Integer.toString(n));
       Descripcion.setText(cn.getNombreServicio());
       precioUnitario.setText(String.format("%.2f$", cn.getPrecioUnitarioFacturado()));
       cantidad.setText(Integer.toString(cn.getCantidad()));

       PrecioTotal.setText(String.format("%.2f$", cn.getPrecioTotalFacturado()));
        this.preciot = cn.getPrecioTotalFacturado();
    }
    public double getPrecioUnitarioItem(){
        return Double.parseDouble(precioUnitario.getText().substring(0, precioUnitario.getText().length()-2));
    }
    public double getPrecioTotalItem(){
        return this.preciot;
                }
    public int cantidadItem(){
        return Integer.parseInt(cantidad.getText());
    }
    public String nombreItem(){
        return Descripcion.getText();
    }
}
