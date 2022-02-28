/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_hotel1;

import controlador.ControlSesion;
import controlador.MetodosAdministradores;
import controlador.modeloVistaContratoHabitaciones;
import controlador.modeloVistaContratoServicios;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
        import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.Cliente;
import modelo.ContratoHabitacion;
import modelo.ContratoServicio;
import modelo.Factura;
import modelo.Habitacion;
import modelo.Reservacion;
import modelo.Servicio;
import modelo.TipoHabitacion;
import zunayedhassan.AnimateFX.*;

/**
 * FXML Controller class
 *
 * @author pc
 */
public class RegistrarReservacionController implements Initializable,Ventana{
@FXML
private Label verNombre,verCedula,labelCodigo;
@FXML 
private Spinner<Integer> horaEntrada,horaSalida,spinnerCantidadServicio;
@FXML 
private ComboBox<String> comboCedula;
@FXML
private ComboBox<TipoHabitacion> ComboTipoHabitaciones;
@FXML
private ComboBox<Habitacion> comboHabitaciones;
@FXML
private TextField campoCedula;
@FXML
private Button btnBuscarCliente,btnCancelarCliente,btnDesconsultarReservacion;
@FXML
private DatePicker fechaEntrada,fechaSalida;
@FXML
private Button btnConsultarDisponibilidad,btnAñadirHabitacion;
@FXML
private GridPane gridBuscar,gridConsultarDisponibilidad, gridRegistroHabitaciones;
@FXML
private ColumnConstraints colBuscar;
@FXML 
private HBox hboxDesconsultarReservacion;
@FXML 
private TableView<modeloVistaContratoHabitaciones> tablaHabitaciones;
@FXML 
private TableView<modeloVistaContratoServicios> tablaServicios;
@FXML 
private ComboBox<Servicio> comboServicios;
@FXML  
private Button  btnLimpiar,btnGuardarReservacion;
@FXML private Button btnAñadirServicios,btnCancelarHabitacion,btnCancelarServicio;
@FXML
private TableColumn tablaHabitacionCosto,tablaHabitacionesTipo,tablaHabitacionNumeroHabitacion,tablaHabitacionPiso,tablaHabitacionCantidadPersonas;
@FXML private TableColumn  columnaServiciosTipo;
@FXML private TableColumn columnaServiciosDescripcion;
@FXML private TableColumn columnaServiciosPrecio;
@FXML private TableColumn columnaServiciosCantidad;
@FXML private TableColumn columnaServiciosPrecioTota;
////----------------------declaraciones internas
private ArrayList<modeloVistaContratoHabitaciones> arrayContratoHabitaciones;
private ArrayList<Habitacion> arrayHabitacionDisponible;
private ArrayList<modeloVistaContratoServicios> arrayServiciosContratados;
private Cliente clienteReservacion;
private String Codigo_Generado ;
private int Factura_Generada;
private Reservacion resUpdate;
//---------------------OBJETOS DE ANIMACIONES--------------
private MetodosAdministradores ma = MetodosAdministradores.getInstance();
 private FadeInAnimation fadeentradaNombre ;
 private FadeInAnimation fadeEntradaCedula ;
 private FadeOutAnimation fadeSalidaNombre ;
 private FadeOutAnimation fadeSalidacEDULA ;
 private RotateInAnimation rotateElementosBuscarEntrar ;
private RotateOutAnimation rotateElementosBuscarSalir ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  _config();

  evento_reservacion_disponible();
  
    } 
    //-----config inicializa variables formatea campos
@Override
    public void _config(){
   //---------configurando componentes----------
   
   btnCancelarHabitacion.setVisible(false);
   btnCancelarServicio.setVisible(false);
   
   horaEntrada.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12));
   horaSalida.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12));
   spinnerCantidadServicio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));
 arrayContratoHabitaciones = new ArrayList<>();
 arrayHabitacionDisponible = new ArrayList<>();
 arrayServiciosContratados = new ArrayList<>();
 Codigo_Generado = "";
 Factura_Generada = 0;
 fadeentradaNombre = new FadeInAnimation(verNombre);
 fadeEntradaCedula = new FadeInAnimation(verCedula);
 fadeSalidaNombre = new FadeOutAnimation(verNombre);
 fadeSalidacEDULA = new FadeOutAnimation(verCedula);
rotateElementosBuscarSalir = new RotateOutAnimation(gridBuscar);
rotateElementosBuscarEntrar = new RotateInAnimation(gridBuscar);


//----formato de tabla habitaciones-------------

    tablaHabitacionPiso.setCellValueFactory(new PropertyValueFactory("Piso"));
    tablaHabitacionNumeroHabitacion.setCellValueFactory(new PropertyValueFactory("NumeroHabitacion"));
    tablaHabitacionCantidadPersonas.setCellValueFactory(new PropertyValueFactory("CantMaxPersonas"));
    tablaHabitacionesTipo.setCellValueFactory(new PropertyValueFactory("NombreTipoHabitacion"));
    tablaHabitacionCosto.setCellValueFactory(new PropertyValueFactory("CostoTipoHabitacion"));
//----formato de Servicios-------------
      columnaServiciosTipo.setCellValueFactory(new PropertyValueFactory("Nombre"));
    columnaServiciosDescripcion.setCellValueFactory(new PropertyValueFactory("Descripcion"));
    columnaServiciosPrecio.setCellValueFactory(new PropertyValueFactory("Precio"));
    columnaServiciosCantidad.setCellValueFactory(new PropertyValueFactory("Cantidad"));
    columnaServiciosPrecioTota.setCellValueFactory(new PropertyValueFactory("PrecioTotal"));

fechaEntrada.setDayCellFactory(dayCellFactoryentrada);
fechaEntrada.setOnAction(i->{ 
fechaSalida.setDayCellFactory(dayCellFactory);
fechaSalida.setValue(fechaEntrada.getValue());
});


   //---------formateando el campo cedula solo para numeros no mas de 10 digitos-----------
   campoCedula.setOnKeyTyped(event->{ 
   
   int c = event.getCharacter().charAt(0);
   if(!Character.isDigit(c)||campoCedula.getText().length()>10){
       Toolkit.getDefaultToolkit().beep();
       event.consume();
   } 
   });
   campoCedula.setOnKeyPressed(event->{ 
    if(event.getCode() == KeyCode.ENTER){
       btnBuscarCliente.fire();
   }
  
   });
   ////----------------eventos----------------
   evento_reservacion_disponible();
   evento_boton_buscar_cliente();
   evento_añadir_habitacion();
   evento_combo_tipo_habitacion();
   evento_desconsultarReservacion();
  evento_boton_añadir_Servicios();
  evento_boton_guardar();
  evento_botonLimpiar();
    }
      //-----establece valores por defectos, llama otros metodos ,---------esta llamada por defecto
@Override
    public void iniciar_Ventana(){
       comboServicios.setItems(ma.getAllServicios());
         comboCedula.setItems(FXCollections.observableArrayList("V-","E-"));
         horaEntrada.getValueFactory().setValue(1);
         horaSalida.getValueFactory().setValue(1);
        
         clear();
    }
    
    public void iniciar_Ventana(Reservacion rr){
       resUpdate = rr;
       btnCancelarCliente.setDisable(true);
       //establece cliente
        estableceCliente(ma.getCliente(rr.getId_cliente()));
        
         ArrayList<Habitacion> habitacionesDisponibles = ma.getHabitacionesDisponible(rr.getFechaEntrada(), rr.getHoraEntrada(), rr.getFechaSalida(), rr.getHoraSalida());
       
          //vigila
          new FadeInLeftAnimation(gridRegistroHabitaciones).Play();
          FadeOutLeftAnimation ff = new FadeOutLeftAnimation(gridConsultarDisponibilidad);
          
          ff.GetTimeline().setOnFinished(es->{ 
          new FadeInRightBigAnimation(hboxDesconsultarReservacion).Play();
          });
            ff.Play();
            hboxDesconsultarReservacion.setVisible(true);
                   arrayHabitacionDisponible = habitacionesDisponibles;
                   this.establecer_habitaciones();
         arrayContratoHabitaciones =ma.getContratosHabitacionesByReservacion(rr);
         tablaHabitaciones.setItems(FXCollections.observableList(arrayContratoHabitaciones));
         tablaHabitaciones.refresh();
         tablaServicios.setItems(FXCollections.observableList(ma.getServiciosByReservacion(resUpdate)));
         tablaServicios.refresh();
         comboServicios.setItems(ma.getAllServicios());
      
        
       
    }
    
@Override
    public void clear(){
        resUpdate =null;
      fechaEntrada.setValue(LocalDate.now());
      fechaSalida.setValue(LocalDate.now().plusDays(1));
       comboCedula.getSelectionModel().select("V-");
       campoCedula.setText("");
       desestablecer_cliente();
       Codigo_Generado = "";
       Factura_Generada = 0;
       labelCodigo.setText("CODIGO: ");
           new FadeOutAnimation(gridRegistroHabitaciones).Play();
       hboxDesconsultarReservacion.setVisible(false);
       comboServicios.setItems(ma.getAllServicios());
       comboServicios.getSelectionModel().clearSelection();
       spinnerCantidadServicio.getValueFactory().setValue(1);
    }
   
    
    public void  estableceCliente(Cliente cc){
        if(cc != null){
            clienteReservacion = cc;
            verNombre.setText(cc.getNombre()+" " +cc.getApellido());
            verCedula.setText(cc.getCedula());
            colBuscar.setPrefWidth(0);
            rotateElementosBuscarSalir.GetTimeline().setOnFinished(e->{ 
            comboCedula.setVisible(false);
            campoCedula.setVisible(false);
            btnBuscarCliente.setVisible(false);
        });
            fadeEntradaCedula.Play();
            fadeentradaNombre.Play();
            new FadeInAnimation(btnCancelarCliente).Play();
            new FadeInAnimation(gridConsultarDisponibilidad).Play();
            rotateElementosBuscarSalir.Play();
             btnCancelarCliente.setVisible(true); 
             if(resUpdate == null){
             Factura_Generada = ma.GenerarFactura(cc);
             Codigo_Generado = ma.GenerarCodigo(cc);    
             }        
             else  Codigo_Generado = resUpdate.getCodigoReservacion();
             labelCodigo.setText( "CODIGO: " + Codigo_Generado);
        }
    }
    public void desestablecer_cliente(){
            clienteReservacion = null;
            comboCedula.setVisible(true);
            campoCedula.setVisible(true);
            btnBuscarCliente.setVisible(true);
            colBuscar.setPrefWidth(230);
            new FadeOutAnimation(btnCancelarCliente).Play();
            new FadeOutLeftAnimation(gridConsultarDisponibilidad).Play();
            verNombre.setText("");
            verCedula.setText("");
             fadeSalidaNombre.GetTimeline().setOnFinished(e->{ 
            
               btnCancelarCliente.setVisible(false);
        });
             rotateElementosBuscarEntrar.Play();
           
            fadeSalidacEDULA.Play(); 
             
              fadeSalidaNombre.Play();
             
             labelCodigo.setText("CODIGO: ");
             Codigo_Generado = "";
             desestablecer_habitaciones();
              
    }
    public void establecer_habitaciones(){
        
     ComboTipoHabitaciones.setItems( FXCollections.observableList(ma.obtenerTipoHabitacionDisponible(this.arrayHabitacionDisponible)));
   
 
    }
    public void desestablecer_habitaciones(){
        if(!arrayHabitacionDisponible.isEmpty()){
         new FadeOutLeftAnimation(gridRegistroHabitaciones).Play();}
         ComboTipoHabitaciones.getItems().clear();
         comboHabitaciones.getItems().clear();
         hboxDesconsultarReservacion.setVisible(false);
         arrayContratoHabitaciones.clear();
          spinnerCantidadServicio.getValueFactory().setValue(1);
         tablaHabitaciones.setItems(FXCollections.observableList(arrayContratoHabitaciones));
         tablaHabitaciones.refresh();
         arrayServiciosContratados.clear();
         tablaServicios.setItems(FXCollections.observableList(arrayServiciosContratados));
         tablaServicios.refresh();
    }
    public boolean valida_vacio(){
         if(arrayContratoHabitaciones.isEmpty()){
               Alertas aa = new Alertas(Alert.AlertType.ERROR,"debe reservar por lo \n"
                       + "menos una habitacion");
               
               aa.setHeaderText("ERROR!!");
               aa.showAndWait();
               return false;
           }else if(hboxDesconsultarReservacion.getTranslateX()!= 0){
                 Alertas aa = new Alertas(Alert.AlertType.ERROR,"debe consultar la disponibilidad ");
               
               aa.setHeaderText("ERROR!!");
               aa.showAndWait();
               return false;
           }
           else if (clienteReservacion== null) {
             Alertas aa = new Alertas(Alert.AlertType.ERROR,"debe seleccionar un cliente para poder reservar ");
               
               aa.setHeaderText("ERROR!!");
               aa.showAndWait();
               return false;
        }else {
               return true;
           }
    }
    //---------------------eventos-------------
    public void evento_reservacion_disponible(){
       btnConsultarDisponibilidad.setOnAction(event->{  
           
           
       ArrayList<Habitacion> habitacionesDisponibles = ma.getHabitacionesDisponible(fechaEntrada.getValue(), horaEntrada.getValue(), fechaSalida.getValue(), horaSalida.getValue());
      if(habitacionesDisponibles.isEmpty()){
                   Alertas aa = new Alertas(Alert.AlertType.INFORMATION, "No hay habitaciones Disponibles");
                   aa.setHeaderText("Informacion habitacion");
                   aa.showAndWait();
               }else{
          if(!habitacionesDisponibles.isEmpty()){
          new FadeInLeftAnimation(gridRegistroHabitaciones).Play();
          FadeOutLeftAnimation ff = new FadeOutLeftAnimation(gridConsultarDisponibilidad);
          
          ff.GetTimeline().setOnFinished(es->{ 
          new FadeInRightBigAnimation(hboxDesconsultarReservacion).Play();
          });
            ff.Play();
            hboxDesconsultarReservacion.setVisible(true);
                   arrayHabitacionDisponible = habitacionesDisponibles;
                   this.establecer_habitaciones();
                   
                  
                
          }
         }
       
       
       
       });
    }
    public void evento_reservacion_disponibleModificar(){
        
    }
     private void evento_añadir_habitacion(){
         btnAñadirHabitacion.setOnAction(eve-> {   
         if(ComboTipoHabitaciones.getSelectionModel().getSelectedItem()!=null || comboHabitaciones.getSelectionModel().getSelectedItem()!=null){
         if(!Codigo_Generado.isEmpty()){
         Habitacion temp  = comboHabitaciones.getSelectionModel().getSelectedItem();
        arrayHabitacionDisponible.remove(  comboHabitaciones.getSelectionModel().getSelectedItem());
         comboHabitaciones.getSelectionModel().clearSelection();
         
         arrayContratoHabitaciones.add(ma.getModeloVistaHabitaciones(new ContratoHabitacion(0,
                 temp.getId(), 
                 Codigo_Generado,ComboTipoHabitaciones.getSelectionModel().getSelectedItem().getCosto()))); 
                ComboTipoHabitaciones.setItems( FXCollections.observableList(ma.obtenerTipoHabitacionDisponible(this.arrayHabitacionDisponible)));

         tablaHabitaciones.setItems(FXCollections.observableList(arrayContratoHabitaciones));
              tablaHabitaciones.refresh();
                comboHabitaciones.getItems().clear();
    

             }else{
                 System.out.println("no se genero codigo");
             }
             
         }else{
                  Alertas aa = new Alertas(Alert.AlertType.ERROR, "debe selepcionar una habitacion \n"
                          + "para añadir");
                  aa.setHeaderText("faltan datos");
                  aa.showAndWait();
              }} );
     }
       private void evento_boton_buscar_cliente(){
       
        WobbleAnimation flipcampocedula = new WobbleAnimation(campoCedula);
       
        
        btnBuscarCliente.setOnAction(event->{  
          
        if(campoCedula.getText().isEmpty()){
            flipcampocedula.Play();
        }else{
            Cliente temp = ma.devolver_cliente_cedula(comboCedula.getSelectionModel().getSelectedItem()+campoCedula.getText());
            if(temp== null){
                Alertas aa = new Alertas(Alert.AlertType.CONFIRMATION ,"no hay un cliente registrado\n"
                        + "con esta cedula \n"
                        + "¿Desea Registrarlo? ");
                aa.showAndWait();
                if(aa.getResult() == ButtonType.CANCEL){
                    campoCedula.setText("");
                    campoCedula.requestFocus();
                }else{
                    
                    try {
                        
                        FXMLLoader fx = new FXMLLoader();
                        fx.setLocation(getClass().getResource("RegistroCliente.fxml"));
                        fx.load();
                        Parent root = fx.getRoot();
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        RegistroClienteController control= fx.getController();
                        control.mode2(campoCedula.getText());

                        stage.initOwner(horaEntrada.getScene().getWindow());
                         stage.initStyle(StageStyle.TRANSPARENT);
                          stage.setHeight(600);
                        stage.initModality(Modality.WINDOW_MODAL);
                    scene.setFill(Paint.valueOf("transparent"));
                  stage.getIcons().add(new Image(getClass().getResource("logo.png").toExternalForm()));
                        stage.setTitle("registro de nuevo usuario para reservar");
                        new BounceInAnimation(root).Play();
                        stage.showAndWait();
                    } catch (IOException ex) {
                        Logger.getLogger(RegistrarReservacionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }else{
                estableceCliente(temp);
            
            
            }
            
        }
        
        });
        btnCancelarCliente.setOnAction(e->{ 
        desestablecer_cliente();
       
        });
    }
       private void evento_combo_tipo_habitacion(){
               ComboTipoHabitaciones.setOnAction(eee->{  
       
           ArrayList<Habitacion> TEMP = new ArrayList<>();
         comboHabitaciones.getSelectionModel().clearSelection();
         if(ComboTipoHabitaciones.getSelectionModel().getSelectedItem()!= null){
           for(Habitacion e:this.arrayHabitacionDisponible){
               
               if(e.getId_tipo_habitaciones()== ComboTipoHabitaciones.getValue().getId_tipos_habitaciones()){
                   TEMP.add(e);
               
           }}
     comboHabitaciones.setItems(FXCollections.observableList(TEMP));
         }
     } );
       }
       private void evento_desconsultarReservacion(){
           btnDesconsultarReservacion.setOnAction(evento->{
           FadeOutRightBigAnimation nn = new FadeOutRightBigAnimation(hboxDesconsultarReservacion);
           nn.GetTimeline().setOnFinished(l->{  
          
           new FadeInLeftBigAnimation(gridConsultarDisponibilidad).Play();
            desestablecer_habitaciones();
           });
           nn.Play();
         
           });
       }
       private void evento_boton_añadir_Servicios(){
          btnAñadirServicios.setOnAction(eve->{  
                 if(comboServicios.getSelectionModel().isEmpty()){
              new  WobbleAnimation(comboServicios).Play();
           }else{
            arrayServiciosContratados.add(new  modeloVistaContratoServicios(
                    comboServicios.getSelectionModel().getSelectedItem(),
                    new ContratoServicio(
                            0,
                            comboServicios.getSelectionModel().getSelectedItem().getId_servicio() , 
                            Codigo_Generado,spinnerCantidadServicio.getValue(),comboServicios.getSelectionModel().getSelectedItem().getPrecio()) )
            );
            
            tablaServicios.setItems(FXCollections.observableList(arrayServiciosContratados));
            tablaServicios.refresh();
           comboServicios.setItems(ma.getAllServicios());
       comboServicios.getSelectionModel().clearSelection();
       
           }
                  });
           
       }
       private void evento_botonLimpiar(){
           btnLimpiar.setOnAction(event->{
           clear();
           });
       }
       private void evento_boton_guardar(){
           btnGuardarReservacion.setOnAction(eve->{  
           
           if(valida_vacio()){
             if( ma.RegistrarReservacion(
                      new Reservacion(Codigo_Generado,
                              clienteReservacion.getId_cliente(),
                              fechaEntrada.getValue(), horaEntrada.getValue(), 
                              fechaSalida.getValue(), horaSalida.getValue(), 0),
                      arrayContratoHabitaciones, arrayServiciosContratados)){
                  MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "REGISTRAR RESERVACIÓN", ControlSesion.getInstance().getNombreUsuario());
                  ma.RegistrarFactura(new Factura(Factura_Generada, LocalDateTime.now(), Codigo_Generado));
              Alertas aa = new Alertas(Alert.AlertType.CONFIRMATION, "El registro de su reservacion \nse ha completado"
                      + "con exito su Codigo es: "+Codigo_Generado+" \n ¿Desea Registrar otra reservacion para este Cliente?");
              aa.setHeaderText("registro exitoso");
              aa.showAndWait();
              if(aa.getResult() == ButtonType.CANCEL ){
                  clear();
              }else{Cliente CC =  clienteReservacion;
                  clear();
                  this.estableceCliente(CC);
                  
              }
              
          }
          }
           });
          
       }
     //formateando fechas
       
        final Callback<DatePicker, DateCell> dayCellFactoryentrada = 
        new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-text-fill: gray;");
                        }   
                    }
                };
            }
        };    
         final Callback<DatePicker, DateCell> dayCellFactory = 
        new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(fechaEntrada.getValue())) {
                            setDisable(true);
                            setStyle("-fx-text-fill: gray;");
                        }   
                    }
                };
            }
        };    

    @Override
    public void close() {
       desestablecer_cliente();
    }
}

