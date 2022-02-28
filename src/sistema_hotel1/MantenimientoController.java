/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import controlador.ControlSesion;
import controlador.MetodosAdministradores;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import modelo.PreguntaSeguridad;
import reportes.Reporte;
import zunayedhassan.AnimateFX.PulseAnimation;
import zunayedhassan.AnimateFX.ShakeAnimation;

/**
 * FXML Controller class
 *
 * @author heyscar
 */
public class MantenimientoController implements Initializable, Ventana{
//manual de usuario-----------------------------
  @FXML
  private Button btnManualUusuario;
  // copias de seguridad-----------------------
  @FXML
  private Button btnGuardarCopiaSeguridad;
  @FXML
  private Button btnCargarCopiaSeguiridad;
  
 
  // contraseñas------------------------------
  @FXML
  private Button btnCambiarContraseña;
  @FXML
  private TextField campoContraseñaActual;
  @FXML
  private TextField campoContraseñaNueva;
  @FXML
  private TextField campoConfirnarContraseña;
   
   //preguntas de seguridad---------------------------
  @FXML
  private TextField p1,p2,p3;
  @FXML 
  private TextField r1,r2,r3;
  @FXML
  private Button btnCambiarPreguntasSeguridad;   

   //tabla-------------------------------------------
  @FXML
  private TableView tablaBitacora;
  @FXML
  private TableColumn columId, columFecha, columAccion, columUsuario;
  @FXML
  private Button bttnBuscar;
  @FXML
  private DatePicker dtPicker;
  
  //Reportes-------------
  @FXML
  private TextField textAgno;
  @FXML
  private Button botonReporte;
  @FXML
  private DatePicker dateDia;
  @FXML
  private ComboBox <String> comboOpcion, comboMeses;  
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
    }    
    
    
    


    @Override
    public void _config() {
        btnCambiarPreguntasSeguridad.setOnAction(eve->{evento_cambiar_Preguntas_seguridad();} );
        btnCambiarContraseña.setOnAction(ee->{evento_cambiar_contraseña();});
        btnGuardarCopiaSeguridad.setOnAction(eve->{this.evento_crea_copía_Seguridad();});
        btnCargarCopiaSeguiridad.setOnAction(eve->{this.evento_carga_copia_Seguridad();});
        campoConfirnarContraseña.setOnKeyTyped(eve->{  this.evento_limita_caracteres(eve, campoConfirnarContraseña, 20);});
        campoContraseñaNueva.setOnKeyTyped(eve->{  this.evento_limita_caracteres(eve, campoContraseñaNueva, 20);});
        campoContraseñaActual.setOnKeyTyped(eve->{  this.evento_limita_caracteres(eve, campoContraseñaActual, 20);});
        p1.setOnKeyTyped(eve->{  this.evento_limita_caracteres(eve, p1, 50);});
        p2.setOnKeyTyped(eve->{  this.evento_limita_caracteres(eve, p2, 50);});
        p3.setOnKeyTyped(eve->{  this.evento_limita_caracteres(eve, p3, 50);});
        r1.setOnKeyTyped(eve->{  this.evento_limita_caracteres(eve, r1, 50);});
        r2.setOnKeyTyped(eve->{  this.evento_limita_caracteres(eve, r2, 50);});
        r3.setOnKeyTyped(eve->{  this.evento_limita_caracteres(eve, r3, 50);});
        
        
        columId.setCellValueFactory(new PropertyValueFactory("Id_bitacora"));
        columFecha.setCellValueFactory(new PropertyValueFactory("Tiempo_modificacion"));
        columAccion.setCellValueFactory(new PropertyValueFactory("Accion"));
        columUsuario.setCellValueFactory(new PropertyValueFactory("Usuario"));
        
        bttnBuscar.setOnAction(eve ->{
        tablaBitacora.setItems(MetodosAdministradores.getInstance().getTablaBitacoraFecha(String.valueOf(dtPicker.getValue())));
        tablaBitacora.refresh();
        });
        
        comboOpcion.setItems(FXCollections.observableArrayList("CLIENTES","CLIENTES POR PAISES","CLIENTES-DIA","CLIENTES-MES","CLIENTES-AÑO","HABITACIONES",
        "HABITACIONES SOLICITADAS","HABITACIONES-DIA", "HABITACIONES-MES", "HABITACIONES-AÑO",
        "SERVICIOS", "SERVICIOS SOLICITADOS","SERVICIOS-DIA","SERVICIOS-MES", "SERVICIOS-AÑO", 
        "RESERVACIONES", "RESERVACIONES-DIA", "RESERVACIONES-MES", "RESERVACIONES-AÑO", "FACTURAS POR CLIENTE"));
        
        comboOpcion.getSelectionModel().selectFirst();
        
        comboMeses.setItems(FXCollections.observableArrayList("ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO",
                "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE","DICIEMBRE"));
        comboMeses.getSelectionModel().selectFirst();
        
        dateDia.setDayCellFactory(param -> new DateCell(){
        @Override
        public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
        }
        });
        dateDia.setValue(LocalDate.now());
        
        comboMeses.setVisible(false);
        dateDia.setVisible(false);
        textAgno.setVisible(false);
        
        textAgno.setOnKeyTyped(value->{
            int c= value.getCharacter().charAt(0);
            
        if(!Character.isDigit(c)||textAgno.getText().length()>=4){
            Toolkit.getDefaultToolkit().beep();
            value.consume();
        }
        });
        
        comboOpcion.setOnAction(eve->{
        if(SeleccionDia()) dateDia.setVisible(true);
        else dateDia.setVisible(false);
        if(SeleccionMes()){
            comboMeses.setVisible(true);
            textAgno.setVisible(true);
        }
        else if(SeleccionAgno()){
            textAgno.setVisible(true);
            comboMeses.setVisible(false);
        }    
        else textAgno.setVisible(false);
    });
                
        botonReporte.setOnAction(eve->{
            Reporte report = new Reporte(this.Seleccion(comboOpcion.getValue().toString()));
            if (SeleccionDia()){
                report.getReporte(dateDia.getValue().toString(), null);
            }else if (SeleccionAgno()){
                report.getReporte(textAgno.getText(), null);
            }else if (SeleccionMes()){
                report.getReporte(String.valueOf(this.SeleccionDeMeses(comboMeses.getValue().toString())),textAgno.getText());
            }else{
                report.getReporte(null, null);
            }
            
        });
    }

    @Override
    public void iniciar_Ventana() {
      dtPicker.setValue(LocalDate.now());
      tablaBitacora.setItems(MetodosAdministradores.getInstance().getTablaBitacora());
      tablaBitacora.refresh();
      this._config();
      cargarPreguntasSeguridad();
    }
    private void cargarPreguntasSeguridad(){
          ArrayList<PreguntaSeguridad> e = MetodosAdministradores.getInstance().getPreguntasSeguridad();
        if(e != null){
           
                p1.setText(e.get(0).getPregunta());
               p2.setText(e.get(1).getPregunta());
                p3.setText(e.get(2).getPregunta());
                r1.setText(e.get(0).getRespuesta());
                r2.setText(e.get(1).getRespuesta());
                r3.setText(e.get(2).getRespuesta());
                
         
        }
    }
    @Override
    public void clear() {
    campoConfirnarContraseña.setText("");
    campoContraseñaActual.setText("");
    campoContraseñaNueva.setText("");
    p1.setText("");
     p2.setText("");
      p3.setText("");
       r1.setText("");
        
   r2.setText("");
   r3.setText("");

       p1.setDisable(true);
       p2.setDisable(true);
       p3.setDisable(true);
       r1.setDisable(true);
       r2.setDisable(true);
       r3.setDisable(true);
       campoConfirnarContraseña.setDisable(true);
       campoContraseñaActual.setDisable(true);
       campoContraseñaNueva.setDisable(true);
    
    }

    @Override
    public void close() {
      clear();
    }
    
        @FXML
    private void evento_crea_copía_Seguridad(){
         FileChooser  fi = new FileChooser();
        FileChooser.ExtensionFilter sqlFilter = new FileChooser.ExtensionFilter("SQL files (*.sql)", "*.sql");
        fi.getExtensionFilters().add(sqlFilter);
        fi.setInitialFileName("Backup_bd_corona_suites.sql");
        File f = fi.showSaveDialog(btnCambiarContraseña.getScene().getWindow());
        
        if(f!= null){
          MetodosAdministradores.getInstance().crearRespaldoBaseDatos(f.toString());
        }
    }
    @FXML
    private void evento_abre_manual_usuario(){
         
    }
    @FXML 
    private void evento_carga_copia_Seguridad(){
        FileChooser  fi = new FileChooser();
        FileChooser.ExtensionFilter sqlFilter = new FileChooser.ExtensionFilter("SQL files (*.sql)", "*.sql");
        fi.getExtensionFilters().add(sqlFilter);
       
   
  
        File f = fi.showOpenDialog(btnCambiarContraseña.getScene().getWindow());
        
        if(f!= null){
            System.out.println(f.toString());
           MetodosAdministradores.getInstance().cargarRespaldoBaseDatos(f.toString());
        }
        
        
    }
    @FXML 
    private void evento_cambiar_contraseña(){
     if(campoContraseñaActual.isDisable()){
         campoConfirnarContraseña.setDisable(false);
         campoContraseñaActual.setDisable(false);
         campoContraseñaNueva.setDisable(false);
     }else{
         //valida campos vacios
         if(campoConfirnarContraseña.getText().isEmpty() || campoContraseñaActual.getText().isEmpty() ||campoContraseñaNueva.getText().isEmpty()){
             Alertas aa = new Alertas(Alert.AlertType.ERROR, "Debe rellenar todos los campos");
             aa.setHeaderText("Campos Vacios");
             aa.showAndWait();
             new ShakeAnimation(campoConfirnarContraseña).Play();
             new ShakeAnimation(campoContraseñaActual).Play();
             new ShakeAnimation(campoContraseñaNueva).Play();
         }else{
             //if valida contraseña nueva con su cinfirmacion
             if(!campoConfirnarContraseña.getText().equals(campoContraseñaNueva.getText())){
                  Alertas aa = new Alertas(Alert.AlertType.ERROR, "la contraseña nueva y su confirmacion son diferentes");
             aa.setHeaderText("Campos diferentes");
             aa.showAndWait();
             new ShakeAnimation(campoConfirnarContraseña).Play();
             new ShakeAnimation(campoContraseñaNueva).Play();
             }else{
                 if(MetodosAdministradores.getInstance().CambiarContraseña(campoContraseñaActual.getText(), campoConfirnarContraseña.getText())){
                        Alertas aa = new Alertas(Alert.AlertType.INFORMATION, "la contraseña se ha cambiado con exito vuelva a iniciar sesion");
             aa.setHeaderText("cambio de contraseña exitoso");
             MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "CAMBIAR CONTRASEÑA", ControlSesion.getInstance().getNombreUsuario());
             MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "CERRAR SESIÓN", ControlSesion.getInstance().getNombreUsuario());
             aa.showAndWait();
         campoConfirnarContraseña.setDisable(false);
         campoContraseñaActual.setDisable(false);
         campoContraseñaNueva.setDisable(false);
             ControlVentanas.getInstance().llamar_cerrar_sesion();
                 }
             }
         }
     }
    }
    @FXML 
    private void evento_cambiar_Preguntas_seguridad(){
        if(p1.isDisable()){
            p1.setDisable(false);
            p2.setDisable(false);
            p3.setDisable(false);
            r1.setDisable(false);
            r2.setDisable(false);
            r3.setDisable(false);
        }else{
             if(p1.getText().isEmpty() || p2.getText().isEmpty() ||p3.getText().isEmpty() || r1.getText().isEmpty() || r2.getText().isEmpty()|| r3.getText().isEmpty()){
             Alertas aa = new Alertas(Alert.AlertType.ERROR, "Debe rellenar todos los campos");
             aa.setHeaderText("Campos Vacios");
             aa.showAndWait();
            new PulseAnimation(p1).Play();
              new PulseAnimation(p2).Play();
              new PulseAnimation(p3).Play();
              new PulseAnimation(r1).Play();
              new PulseAnimation(r2).Play();
              new PulseAnimation(r3).Play();
        }else{
                 ArrayList<PreguntaSeguridad> array = new ArrayList<>();
                 array.add(new PreguntaSeguridad(0, 0, p1.getText(), r1.getText()));
                 array.add(new PreguntaSeguridad(0, 0, p2.getText(), r2.getText()));
                 array.add(new PreguntaSeguridad(0, 0, p3.getText(), r3.getText()));
                 if(MetodosAdministradores.getInstance().CambiarPreguntasSeguridad(array)){
                 Alertas aa = new Alertas(Alert.AlertType.INFORMATION, "Preguntas de Seguridad Cambiadas con exito");
             aa.setHeaderText("proceso exitoso");
             MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "CAMBIAR PREGUNTAS DE SEGURIDAD", ControlSesion.getInstance().getNombreUsuario());
             aa.showAndWait();
              new PulseAnimation(p1).Play();
               new PulseAnimation(r1).Play();
              new PulseAnimation(p2).Play();
              new PulseAnimation(r2).Play();
              new PulseAnimation(p3).Play();

              new PulseAnimation(r3).Play();
              cargarPreguntasSeguridad();
               p1.setDisable(true);
            p2.setDisable(true);
            p3.setDisable(true);
            r1.setDisable(true);
            r2.setDisable(true);
            r3.setDisable(true);
                 }
             }
        }
    }
    private void evento_limita_caracteres(KeyEvent e, TextField te, int limite){
        if(te.getText().length()>=limite){
            e.consume();
            Toolkit.getDefaultToolkit().beep();
        }
      
    }
    
    boolean SeleccionDia(){
        if ((comboOpcion.getSelectionModel().getSelectedItem()=="CLIENTES-DIA")||(comboOpcion.getSelectionModel().getSelectedItem()=="HABITACIONES-DIA")||(comboOpcion.getSelectionModel().getSelectedItem()=="SERVICIOS-DIA")||(comboOpcion.getSelectionModel().getSelectedItem()=="RESERVACIONES-DIA")) return true;
        else return false;
    }
    boolean SeleccionMes(){
        if((comboOpcion.getSelectionModel().getSelectedItem()=="CLIENTES-MES")||(comboOpcion.getSelectionModel().getSelectedItem()=="HABITACIONES-MES")||(comboOpcion.getSelectionModel().getSelectedItem()=="SERVICIOS-MES")||(comboOpcion.getSelectionModel().getSelectedItem()=="RESERVACIONES-MES")) return true;
        else return false;
    }
    
    boolean SeleccionAgno(){
        if((comboOpcion.getSelectionModel().getSelectedItem()=="CLIENTES-AÑO")||(comboOpcion.getSelectionModel().getSelectedItem()=="HABITACIONES-AÑO")||(comboOpcion.getSelectionModel().getSelectedItem()=="SERVICIOS-AÑO")||(comboOpcion.getSelectionModel().getSelectedItem()=="RESERVACIONES-AÑO")) return true;
        else return false;
    }
    
    String Seleccion(String opcion){
        switch(opcion){
            case "CLIENTES":
                return "CLIENTES";
            case "CLIENTES-DIA":
                return "CLIENTESXDIA";
            case "CLIENTES-MES":
                return "CLIENTESXMES";
            case "CLIENTES-AÑO":
                return "CLIENTESXAGNO";
            case "HABITACIONES":
                return "HABITACIONES";
            case "HABITACIONES-DIA":
                return "HABITACIONESXDIA";
            case "HABITACIONES-MES":
                return "HABITACIONESXMES";
            case "HABITACIONES-AÑO":
                return "HABITACIONESXAGNO";
            case "SERVICIOS":
                return "SERVICIOS";
            case "SERVICIOS-DIA":
                return "SERVICIOSXDIA";
            case "SERVICIOS-MES":
                return "SERVICIOSXMES";
            case "SERVICIOS-AÑO":
                return "SERVICIOSXAGNO";
            case "RESERVACIONES":
                return "RESERVACIONES";
            case "RESERVACIONES-DIA":
                return "RESERVACIONESXDIA";
             case "RESERVACIONES-MES":
                return "RESERVACIONESXMES";
            case "RESERVACIONES-AÑO":
                return "RESERVACIONESXAGNO";
            case "FACTURAS POR CLIENTE":
                return "FACTURASXCLIENTE";
            case "CLIENTES POR PAISES":
                return "CLIENTESXPAISES";
            case "HABITACIONES SOLICITADAS":
                return "HABITACIONES_SOLICITADAS";
        }
        return "no sé";
    }
    
    int SeleccionDeMeses(String opcion){
        switch(opcion){
            case "ENERO":
                return 1;
            case "FEBRERO":
                return 2;
            case "MARZO":
                return 3;
            case "ABRIL":
                return 4;
            case "MAYO":
                return 5;
            case "JUNIO":
                return 6;
            case "JULIO":
                return 7;
            case "AGOSTO":
                return 8;
            case "SEPTIEMBRE":
                return 9;
            case "OCTUBRE":
                return 10;
            case "NOVIEMBRE":
                return 11;  
            case "DICIEMBRE":
                return 12;
        
        }
        
        return 0;
    }
           
}
