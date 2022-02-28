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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.CRUDS.CRUDBitacora;
import modelo.Cliente;
import modelo.Direccion;
import zunayedhassan.AnimateFX.BounceOutAnimation;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class RegistroClienteController implements Initializable {
   //modo de registro 0 y modo de actualizacion 1 mode 2 registro de devolucion de cedula + cierre de ventana
    private int mode = 0;
    private MetodosAdministradores ma =MetodosAdministradores.getInstance();
  
    @FXML
    private TextField textoNombre,textoApellido,textoCedula;
    @FXML
    private ToggleGroup togleSexo = new  ToggleGroup();
    @FXML
    private DatePicker datePickerFechaNacimiento;
    @FXML
    private RadioButton radioMasculino,radioFemenino;
    @FXML
    private ComboBox<Direccion>comboEstados;
     @FXML
    private ComboBox<String> comboCedula;
      @FXML
    private ComboBox<Direccion> comboPaises;
    @FXML
    private TextField textoCiudad,textoDireccion;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpiar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    _config();
    }
    public void _config(){
        
        textoNombre.setOnKeyTyped(ev->{
            int c= ev.getCharacter().charAt(0);
            if (textoNombre.getText().length()>=20||Character.isDigit(c)){
            ev.consume();
            Toolkit.getDefaultToolkit().beep();
            }
        });
        
        
        
        textoApellido.setOnKeyTyped(ev->{
            int c= ev.getCharacter().charAt(0);
            if (textoApellido.getText().length()>=20 ||Character.isDigit(c)){
            ev.consume();
            Toolkit.getDefaultToolkit().beep();
            }
        });
        
        textoCiudad.setOnKeyTyped(ev->{
            if (textoCiudad.getText().length()>=20){
            ev.consume();
            Toolkit.getDefaultToolkit().beep();
            }
        });
        
        textoDireccion.setOnKeyTyped(ev->{
            if (textoDireccion.getText().length()>=50){
            ev.consume();
            Toolkit.getDefaultToolkit().beep();
            }
        });
       
          evento_guardar();
     comboPaises.setItems(ma.getPaises());
     comboCedula.setItems(FXCollections.observableArrayList("V-","E-"));
     comboCedula.getSelectionModel().select(0);
      comboPaises.setOnAction(event->{ 
         if(!comboPaises.getSelectionModel().isEmpty()){
          comboEstados.setItems(ma.getEstados( comboPaises.getSelectionModel().getSelectedItem().getId_pais()));
         }
      });
        datePickerFechaNacimiento.setDayCellFactory(dayCellFactory);
        datePickerFechaNacimiento.setValue(LocalDate.now().minusYears(18));
     
        textoCedula.setOnKeyTyped(value->{
            int c= value.getCharacter().charAt(0);
            
        if(!Character.isDigit(c)||textoCedula.getText().length()>=10){
           
            Toolkit.getDefaultToolkit().beep();
            value.consume();
            
            
        }
        }
        );
        btnLimpiar.setOnAction(rds->{ 
    clear();
      if(mode==2){
           Stage ss =(Stage)btnLimpiar.getScene().getWindow();
        BounceOutAnimation bb  = new BounceOutAnimation(ss.getScene().getRoot());
        bb.GetTimeline().setOnFinished(handle->{ 
          ss.close();
        });
        bb.Play();
      
                }
    });
        
    }
    public void evento_guardar(){
         btnGuardar.setOnAction(e->{
            if(mode ==0|| mode == 2){
                if(no_vacio()){
          if(radioMasculino.isSelected()){
              if(ma.Registrar(new Cliente(comboCedula.getValue()+textoCedula.getText(), textoNombre.getText(), textoApellido.getText(), datePickerFechaNacimiento.getValue(), "M"))){
                if(ma.registrarDireccionCliente(comboEstados.getSelectionModel().getSelectedItem().getId_estado(),
                   textoCiudad.getText(), comboCedula.getValue()+textoCedula.getText(), textoDireccion.getText())){
                  
                  MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "REGISTRAR CLIENTE", ControlSesion.getInstance().getNombreUsuario());
                  CRUDBitacora.getInstance().createBCliente();
                  clear();
                  
                  Alertas al = new Alertas(Alert.AlertType.INFORMATION, "Registro de Cliente Exitoso");
                  al.setHeaderText("informacion");
                  al.setTitle("REGISTRO EXITOSO");
                  al.showAndWait();
                   if(mode==2){
           Stage ss =(Stage)btnLimpiar.getScene().getWindow();
        BounceOutAnimation bb  = new BounceOutAnimation(ss.getScene().getRoot());
        bb.GetTimeline().setOnFinished(handle->{ 
          ss.close();
          BarraLateralController.getCotrolRegistroReservacion().estableceCliente(ma.getUltimoClienteRegistro());
        });
        bb.Play();
      
                }
                }else {
                     Alertas al = new Alertas(Alert.AlertType.INFORMATION, "Registro de Cliente Exitoso mas no se ha podido registrar su direccion ");
                  al.setHeaderText("informacion");
                  al.setTitle("Alerta de Registro");
                  al.showAndWait();
                 }
              }
          }else{
           if(ma.Registrar(new Cliente(comboCedula.getValue()+ textoCedula.getText(), textoNombre.getText(), textoApellido.getText(), datePickerFechaNacimiento.getValue(), "F"))){
                 if(ma.registrarDireccionCliente(comboEstados.getSelectionModel().getSelectedItem().getId_estado(),
                    textoCiudad.getText(), comboCedula.getValue()+textoCedula.getText(), textoDireccion.getText())){
                    
                  MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "REGISTRAR CLIENTE", ControlSesion.getInstance().getNombreUsuario());
                  CRUDBitacora.getInstance().createBCliente();
                    clear();
               
                  Alertas al = new Alertas(Alert.AlertType.INFORMATION, "Registro de Cliente Exitoso");
                  al.setHeaderText("informacion");
                  al.showAndWait();
                   if(mode==2){
           Stage ss =(Stage)btnLimpiar.getScene().getWindow();
        BounceOutAnimation bb  = new BounceOutAnimation(ss.getScene().getRoot());
        bb.GetTimeline().setOnFinished(handle->{ 
          ss.close();
          BarraLateralController.getCotrolRegistroReservacion().estableceCliente(ma.getUltimoClienteRegistro());
        });
        bb.Play();
      
                }
                 }else {
                     Alertas al = new Alertas(Alert.AlertType.INFORMATION, "Registro de Cliente Exitoso mas no se ha podido registrar su direccion ");
                  al.setHeaderText("informacion");
                  al.showAndWait();
                 }
           }
           
      }
           
      }
              
           
            }
      
        });
    }
    public boolean no_vacio(){
        if(textoCedula.getText().isEmpty()
                ||textoApellido.getText().isEmpty()
                ||textoNombre.getText().isEmpty()
                ||textoCiudad.getText().isEmpty()
                || textoDireccion.getText().isEmpty()
                || comboPaises.getSelectionModel().getSelectedItem() ==null
                ||comboEstados.getSelectionModel().getSelectedItem() ==null
                
          ){Alertas aa = new Alertas(Alert.AlertType.ERROR,"");
          aa.setHeaderText("error: introduccion de datos");
          aa.setContentText("todos los campos deben ser rellenados para poder registrar un usuario");
          aa.showAndWait();
          return false;}
            
        return true;
    }
    public void clear(){
        textoCedula.setText("");
        textoApellido.setText("");
        textoNombre.setText("");
        textoCiudad.setText("");
        textoDireccion.setText("");
        comboPaises.getSelectionModel().clearSelection();
        comboEstados.getSelectionModel().clearSelection();
       datePickerFechaNacimiento.setValue(LocalDate.now().minusYears(18));
       radioMasculino.setSelected(true);
    }
    public void mode2(String Cedula){
        mode =2;
        btnLimpiar.setText("CANCELAR");
        textoCedula.setText(Cedula);
        
    }
       final Callback<DatePicker, DateCell> dayCellFactory = 
        new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now().minusYears(18))) {
                            setDisable(true);
                            setStyle("-fx-text-fill: red;");
                        }   
                    }
                };
            }
        };    
}


