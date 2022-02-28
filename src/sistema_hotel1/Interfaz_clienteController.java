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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import modelo.Cliente;
import controlador.ControlSesion;
import controlador.MetodosClientes;
import controlador.modeloVistaContratoServicios;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Reservacion;
import modelo.Servicio;
import zunayedhassan.AnimateFX.*;

public class Interfaz_clienteController implements Initializable {
 
    @FXML
    private BorderPane container;
    @FXML 
    private ImageView logo;
    @FXML
    private Label labelCodigoReservacion,labelNombre,labelApellido,labelCedula,labelFechaNacimiento,labelSexo;
    @FXML
    private Label labelFechaEntrada,labelFechaSalida,labelHoraEntrada,labelHoraSalida;
   @FXML
   private TableView tablaHabitaciones;
   @FXML
   private TableColumn columnaHabitacionesPiso,columnaHabitacionesNumero,columnaHabitacionesPersonas,columnaHabitacionesTipo,columnaHabitacionesPrecio,columnaHabitacionesDescripcion;
    @FXML
    private TableView<modeloVistaContratoServicios> tablaServicios;
    @FXML
    private TableColumn columnaServiciosTipo,columnaServiciosDescripcion,columnaServiciosCantidad,columnaServiciosPrecio;
    @FXML
    private Label total;
    @FXML
    private ComboBox<Servicio> comboServicios;
    @FXML
    private Spinner spinCantidad;
    @FXML
    SpinnerValueFactory modeloSpinner;
    @FXML
    private MetodosClientes mc;  
    private   Cliente c;
    private Reservacion r;
    private ControlSesion cs ;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        _config();
     container.setOnMouseEntered(vv->{  animaciones();});
 animaciones();
      
    }   
    
    public void _config(){
    columnaServiciosTipo.setCellValueFactory(new PropertyValueFactory("Nombre"));
    columnaServiciosDescripcion.setCellValueFactory(new PropertyValueFactory("Descripcion"));
    columnaServiciosPrecio.setCellValueFactory(new PropertyValueFactory("Precio"));
    columnaServiciosCantidad.setCellValueFactory(new PropertyValueFactory("Cantidad"));
    
    columnaHabitacionesPiso.setCellValueFactory(new PropertyValueFactory("Piso"));
    columnaHabitacionesNumero.setCellValueFactory(new PropertyValueFactory("NumeroHabitacion"));
    columnaHabitacionesPersonas.setCellValueFactory(new PropertyValueFactory("CantMaxPersonas"));
    columnaHabitacionesTipo.setCellValueFactory(new PropertyValueFactory("NombreTipoHabitacion"));
    columnaHabitacionesPrecio.setCellValueFactory(new PropertyValueFactory("CostoTipoHabitacion"));
    columnaHabitacionesDescripcion.setCellValueFactory(new PropertyValueFactory("DescripcionTipoHabitacion"));
    modeloSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,1);
    spinCantidad.setValueFactory(modeloSpinner);
    }
    @FXML
    public void evento_cerrar_sesion(MouseEvent e){
       
       
       BounceOutDownAnimation aa = new BounceOutDownAnimation(container);
     
       aa.GetTimeline().setOnFinished(value->{   
       container.setTranslateY(1080);
       this.SesionDesactivate();
       });
         aa.Play();
       
    }
    @FXML
    public void even_boton_añadir(ActionEvent e){
        if(cs.validadorCliente()){
            if(mc.añadirNuevoServicio(comboServicios.getSelectionModel().getSelectedItem(), (int) spinCantidad.getValue())){
                
                Alertas aa= new Alertas(AlertType.INFORMATION,"");
                aa.setHeaderText("Corona Suite:Servicios");
                aa.setContentText("Servicio "+comboServicios.getSelectionModel().getSelectedItem().getNombre()
                        + " Adquirido con exito ");
                aa.show();
                tablaServicios.setItems(mc.getServiciosUser());
                tablaServicios.refresh();
                total.setText("TOTAL: \n"+ String.format(Locale.ITALY,"%1$,.2f",mc.getCostototal())+"$");
     
                comboServicios.getSelectionModel().clearSelection();
                
            }else{
                Alertas aa= new Alertas(AlertType.ERROR,"");
                aa.setHeaderText("Corona Suite:Servicios");
                if(comboServicios.getSelectionModel().getSelectedItem()!=null){
                aa.setContentText("ocurrio un error al contratar el Servicio "+comboServicios.getSelectionModel().getSelectedItem().getNombre()
                        + " informe a recepcion");
               
            }else{
                    aa.setContentText("no hay ningun Servicio Selepcionado "
                        + "SI no es asi  informe a recepcion");
                }
                 aa.showAndWait();
                 
                
            }
        }
    }
    @FXML
    public void even_boton_cancelar(ActionEvent e){
        
    }
    @FXML
    public void even_boton_guardar(ActionEvent e){
        
    }
      public void animaciones(){
         BounceAnimation bb = new BounceAnimation(logo);
        
         
         bb.Play();
     } 
      public  void SesionActivate(){
          comboServicios.requestFocus();
          cs = ControlSesion.getInstance();
       mc = MetodosClientes.getInstance();
           c = mc.getClienteReservacion();
          r = mc.getreservacion();
       labelCodigoReservacion.setText("CODIGO DE RESERVACION: [" + r.getCodigoReservacion()+"]" );
       labelNombre.setText("NOMBRE: "+ c.getNombre());
       labelApellido.setText("APELLIDO: "+  c.getApellido());
       labelCedula.setText("CEDULA: " +  c.getCedula());
       labelFechaNacimiento.setText("FECHA DE NACIMIENTO: "+ c.getSfechaNacimiento());
       if("F".equals(c.getSexo()))labelSexo.setText("SEXO:  FEMENINO");   
      else if("M".equals(c.getSexo()))labelSexo.setText("SEXO:  MASCULINO");
       
      labelFechaEntrada.setText("FECHA DE ENTRADA: "+ r.getSFechaEntrada());
      labelFechaSalida.setText("FECHA DE SALIDA: "
              + r.getSFechaSalida());
      if(r.getHoraEntrada()>= 12){
        if(r.getHoraEntrada()!=12){
         labelHoraEntrada.setText("HORA DE ENTRADA: " + Integer.toString(r.getHoraEntrada()-12)+" PM");
        }else{
            labelHoraEntrada.setText("HORA DE ENTRADA: " + Integer.toString(r.getHoraEntrada())+" PM");
        }
        }else{
          if(r.getHoraEntrada()==0){
              labelHoraEntrada.setText("HORA DE ENTRADA: " +"12 AM");
          }else{
                labelHoraEntrada.setText("HORA DE ENTRADA: " + Integer.toString(r.getHoraEntrada())+" AM");
          }
      }
       if(r.getHoraSalida()>= 12){
        if(r.getHoraSalida()!=12){
         labelHoraSalida.setText("HORA DE SALIDA: " + Integer.toString(r.getHoraSalida()-12)+" PM");
        }else{
            labelHoraSalida.setText("HORA DE SALIDA: " + Integer.toString(r.getHoraSalida())+" PM");
        }
        }else{
          if(r.getHoraSalida()==0){
              labelHoraSalida.setText("HORA DE SALIDA: " + "12 AM");
          }else{
                labelHoraSalida.setText("HORA DE SALIDA: " + Integer.toString(r.getHoraSalida())+" AM");
          }
      }
       tablaServicios.setItems(mc.getServiciosUser());
       tablaServicios.refresh();
       tablaHabitaciones.setItems(mc.getHabitacionesUser());
       tablaHabitaciones.refresh();
       comboServicios.setItems(mc.getServiciosAContratar());
       total.setText("TOTAL: \n"+ String.format(Locale.ITALY,"%1$,.2f",mc.getCostototal())+"$");
      }     
      

      public  void SesionDesactivate(){
          c = null;
          r = null;
          cs.CerrarSesion();
          cs = null;
     labelCodigoReservacion.setText("CODIGO DE RESERVACION: " );
       labelNombre.setText("NOMBRE: ");
       labelApellido.setText("APELLIDO: ");
       labelCedula.setText("CEDULA: ");
       labelFechaNacimiento.setText("FECHA DE NACIMIENTO: ");
       labelSexo.setText("SEXO:");
        labelFechaEntrada.setText("FECHA DE ENTRADA: ");
      labelFechaSalida.setText("FECHA DE SALIDA: ");
      labelHoraEntrada.setText("HORA DE ENTRADA: " );
      labelHoraSalida.setText("HORA DE SALIDA: ");
      tablaServicios.getItems().clear();
      tablaServicios.refresh();
      tablaHabitaciones.getItems().clear();
      comboServicios.getItems().clear();
      total.setText("TOTAL: 0$");
      }
}
