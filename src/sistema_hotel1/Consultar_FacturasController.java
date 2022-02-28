
package sistema_hotel1;

import controlador.ControlSesion;
import controlador.MetodosAdministradores;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import modelo.Cliente;
import modelo.Factura;
import modelo.Servicio;
import modelo.TipoHabitacion;
import zunayedhassan.AnimateFX.WobbleAnimation;


public class Consultar_FacturasController implements Initializable, Ventana{

    @FXML
    private TableView<Factura> tablaFacturas;
    @FXML
    private TableColumn columnaFacturaId, columnaFacturaFecha, columnaFacturaCodigo;
    @FXML
    private MetodosAdministradores ma = MetodosAdministradores.getInstance();
    @FXML
    private TextField fieldBusqueda;
    @FXML
    private Button bttnDetalle;
    @FXML
    private Label labelId, labelCodigo, labelNombre, labelCedula, labelFecha, labelHora, labelHabitacion, labelMonto1, labelServicio, labelMonto2, labelTotal;
    @FXML
    private GridPane facturaFondo;
    
    private Factura factura;
    private Factura resultados;
    private Cliente resultadosC;
    private TipoHabitacion resultadosTH;
    private Servicio resultadosS;
    private double total;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
       
        _config();
       
        
    }
    
    public void _config(){
        columnaFacturaId.setCellValueFactory(new PropertyValueFactory("Id_factura"));
        columnaFacturaFecha.setCellValueFactory(new PropertyValueFactory("Fecha_factura"));
        columnaFacturaCodigo.setCellValueFactory(new PropertyValueFactory("CodigoReservacion"));
        
        bttnDetalle.setOnAction(eve ->{
            
        WobbleAnimation flipButtnDetalle = new WobbleAnimation(bttnDetalle);
        
        if (tablaFacturas.getSelectionModel().isSelected(tablaFacturas.getFocusModel().getFocusedIndex())){
        encontrar_factura();
        mostrar_detalles();
        facturaFondo.setVisible(true);
        MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "CONSULTAR FACTURA", ControlSesion.getInstance().getNombreUsuario());
        }else{
        flipButtnDetalle.Play();
        }
        });
        
        clear();
        total = 0;
    }
    
   
    
    private void encontrar_factura(){
        factura = tablaFacturas.getSelectionModel().getSelectedItem();
        resultados = ma.devolver_factura(factura.getId_factura());
        resultadosC = ma.devolver_cliente(factura.getId_factura());
        resultadosTH = ma.devolver_habitacion(factura.getId_factura());
        resultadosS = ma.devolver_servicio(factura.getId_factura());
        total = resultadosTH.getCosto() + resultadosS.getPrecio();
    }
    
    private void mostrar_detalles(){
        labelId.setText(String.valueOf(resultados.getId_factura()));
        labelCodigo.setText(resultados.getCodigoReservacion());
        labelNombre.setText(resultadosC.getNombre()+" "+resultadosC.getApellido());
        labelCedula.setText(resultadosC.getCedula());
        labelFecha.setText(String.valueOf(resultados.getFecha_factura()));
        
        labelHabitacion.setText(resultadosTH.getNombre());
        labelMonto1.setText(String.valueOf(resultadosTH.getCosto())+"$");
        labelServicio.setText(resultadosS.getNombre());
        labelMonto2.setText(String.valueOf(resultadosS.getPrecio())+"$");
        labelTotal.setText(String.valueOf(resultadosTH.getCosto()+resultadosS.getPrecio())+"$");
    }
    
   

    @Override
    public void iniciar_Ventana() {
        facturaFondo.setVisible(false);
        tablaFacturas.setItems(ma.getTablaFactura());
        tablaFacturas.refresh();
        
        fieldBusqueda.setOnKeyReleased(event->{ 
            
            if(!fieldBusqueda.getText().isEmpty()){
           
            tablaFacturas.setItems(ma.getTablaFactura(fieldBusqueda.getText().toUpperCase()));
            tablaFacturas.refresh();
      
            }else{
            tablaFacturas.setItems(ma.getTablaFactura());
            tablaFacturas.refresh();
            }
            
        });
    
    }

    @Override
    public void clear() {
         labelId.setText(" ");
        labelCodigo.setText(" ");
        labelNombre.setText(" ");
        labelCedula.setText(" ");
        labelFecha.setText(" ");
        labelHora.setText(" ");
        labelHabitacion.setText(" ");
        labelMonto1.setText(" ");
        labelServicio.setText(" ");
        labelMonto2.setText(" ");
        labelTotal.setText(" ");
    }

    @Override
    public void close() {
    clear();
    }
    
}
