/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import controlador.MetodosAdministradores;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modelo.CRUDS.CRUDFactura;
import modelo.ContratoHabitacion;
import modelo.ContratoServicio;
import modelo.Factura;
import modelo.Reservacion;

/**
 * FXML Controller class
 *
 * @author heyscar
 */
public class ComponenteFacturaController implements Initializable {
 @FXML
    private VBox vboxItems;
    @FXML
    private Label labelTotal,labelHora,labelFecha,labelCedula,labelNombre,labelId,labelCodigo;
   private ArrayList<ComponteItemController> ITEMS = new ArrayList<>();
   private Reservacion reservacion;
   private Factura factura;
   private double total = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void generarFactura(Reservacion resA) {
        reservacion = resA;
         factura = CRUDFactura.getInstance().findOnCodigoReservacion(resA.getCodigoReservacion());
        labelCedula.setText(resA.getCedula_cliente());
        labelCodigo.setText(resA.getCodigoReservacion());
        
        
        labelNombre.setText(resA.getNombre_cliente());
        
        
        double total = 0;
        int num = 1;
        
    for(Object o:MetodosAdministradores.getInstance().getListItemFactura(resA)){
            
        if( o instanceof ContratoHabitacion ){
            try {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("ComponteItem.fxml"));
                fxml.load();
                ComponteItemController co = fxml.getController();
                ITEMS.add(co);
                co.iniciar_Componente((ContratoHabitacion)o,num);
                num++;
                vboxItems.getChildren().add(fxml.getRoot());
                total += co.getPrecioTotalItem();
            } catch (IOException ex) {
                Logger.getLogger(DetallesReservacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("ComponteItem.fxml"));
                fxml.load();
                ComponteItemController co = fxml.getController();
                co.iniciar_Componente((ContratoServicio)o,num);
                ITEMS.add(co);
                num++;
                vboxItems.getChildren().add(fxml.getRoot());
                total+= co.getPrecioTotalItem();
            } catch (IOException ex) {
                Logger.getLogger(DetallesReservacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    this.total = total;
    labelTotal.setText(String.format("%.2f$", this.total));
    labelFecha.setText(factura.getFecha_factura().format(DateTimeFormatter.ISO_LOCAL_DATE));
        labelHora.setText(factura.getFecha_factura().format(DateTimeFormatter.ISO_LOCAL_TIME));
        labelId.setText(Integer.toString(factura.getId_factura()));
    }
    public void clearFactura(){
        labelTotal.setText("");
        labelHora.setText("");
        labelFecha.setText("");
        labelCedula.setText("");
        labelNombre.setText("");
        labelId.setText("");
        labelCodigo.setText("");
        reservacion = null;
        factura = null;
        total = 0;
        ITEMS.clear();
        Node g =  vboxItems.getChildren().get(0);
            vboxItems.getChildren().clear();
            vboxItems.getChildren().add(g);
    }
    public void  actualizar(){
        Node g =  vboxItems.getChildren().get(0);
            vboxItems.getChildren().clear();
            vboxItems.getChildren().add(g);
        generarFactura(reservacion);
    }
    public Reservacion getReservacion(){
        return reservacion;
    }

    public ArrayList<ComponteItemController> getITEMS() {
        return ITEMS;
    }

    public Factura getFactura() {
        return factura;
    }

    public double getTotal() {
        return total;
    }
    
}
