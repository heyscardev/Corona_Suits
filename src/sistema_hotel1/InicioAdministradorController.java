/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Marlene
 */
public class InicioAdministradorController implements Initializable {
@FXML
BorderPane Fondo;



//conectar botones fxml
@FXML
Button   boton0,boton1,boton2,boton3,boton4,boton5,boton6,
        boton7,boton8,boton9,boton10,boton11,boton12,boton13,
        boton14,boton15,boton16,boton17,boton18,boton19,boton20,
        boton21,boton22,boton23,boton24,boton25,boton26,boton27,
        boton28,boton29,boton30,boton31,boton32,boton33,boton34,
        boton35,boton36;
@FXML
    ComboBox combomes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        AJUSTECombo();
      
      
        
    } 
  
    @FXML
    public void botonCalendario(ActionEvent e){
        
    }
    
    //ajustando el combobox de los mes
    public void AJUSTECombo(){
        
        ObservableList<String> listaMes= FXCollections.observableArrayList();
        listaMes.addAll("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
        combomes.setItems(listaMes);
        combomes.getSelectionModel().select(LocalDate.now().getMonth().getValue()-1);
          ajustar_calendario(LocalDate.now());
}
    public void asignaEvento(Button btn, LocalDate fecha){
        btn.setOnAction(ee->{
        ControlVentanas.getInstance().llamarMostrarReservaciones(fecha);
        
        });
    }
    //ajustando los dias al calendario
    public void ajustar_calendario(LocalDate p){
        //ajusta el combo box para el mes en el que estamos
     
     //asigna los botones del mes para un rapido manejo en un arreglos
        Button cal[] = { boton0,boton1,boton2,boton3,boton4,boton5,boton6,
        boton7,boton8,boton9,boton10,boton11,boton12,boton13,
        boton14,boton15,boton16,boton17,boton18,boton19,boton20,
        boton21,boton22,boton23,boton24,boton25,boton26,boton27,
        boton28,boton29,boton30,boton31,boton32,boton33,boton34,
        boton35,boton36};
        //asigna el dia de la semana en que empieza el mes
        int dian  = p.with(TemporalAdjusters.firstDayOfMonth()).getDayOfWeek().getValue();
        //asigna el ultimo dia del mes 
        int dia_termino = p.lengthOfMonth();
        /*si el sia cae domingo como en el localdate es 7 y en mi calendario es el 0 
        entoces cambia el domingo a 0 y tambien le resta uno al dia termino para que 
        no halla un dia extra
        */
        if(dian == 7){
            dian = 0;
            dia_termino = dia_termino -1;
        }
        
       
         
        // contador de dias del mes ya que el (i) cuanta los botones 
         int dia = 1;
    for(int i  =0; i<37;i++){
        //muestra los botones 
         cal[i].setVisible(true);
         /*condicion que le dice que si (i = el boton)ya es mayor a el dia de la semana
         de inicio y empieza a aumentar dia para que este solo llegue al maximo
         */
        if(i>=dian && dia<=dia_termino){
           this.asignaEvento(cal[i], p.withDayOfMonth(dia));
            cal[i].setText(Integer.toString(dia));
            
            dia++;
        }else{
            //esconde las cajas o botenes qque no son requeridas
            cal[i].setVisible(false);
        }
        
    }
}
    public void eventoCombo(ActionEvent e){
        switch(combomes.getSelectionModel().getSelectedIndex()){
            case 0:
                ajustar_calendario(LocalDate.now().withMonth(1));
            break;
             case 1: ajustar_calendario(LocalDate.now().withMonth(2));
            break;
             case 2: ajustar_calendario(LocalDate.now().withMonth(3));
            break;
             case 3: ajustar_calendario(LocalDate.now().withMonth(4));
            break;
             case 4: ajustar_calendario(LocalDate.now().withMonth(5));
            break;
             case 5: ajustar_calendario(LocalDate.now().withMonth(6));
            break;
             case 6: ajustar_calendario(LocalDate.now().withMonth(7));
            break;
             case 7: ajustar_calendario(LocalDate.now().withMonth(8));
            break;
             case 8: ajustar_calendario(LocalDate.now().withMonth(9));
            break;
             case 9: ajustar_calendario(LocalDate.now().withMonth(10));
            break;
             case 10: ajustar_calendario(LocalDate.now().withMonth(11));
            break;
             case 11: ajustar_calendario(LocalDate.now().withMonth(12));
            break;
            
            
            
            
            
        }
    }

}
