
package sistema_hotel1;

import controlador.ControlSesion;
import controlador.MetodosAdministradores;
import java.awt.Toolkit;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Cliente;


public class ConsultaController implements Initializable {
@FXML
private TableView<Cliente> tablaClientes;
@FXML
private TableColumn columnaClienteID,columnaClienteNombre,columnaClienteApellido,columnaClienteCedula,columnaClienteSexo,columnaClienteFNacimiento;
@FXML 
private MetodosAdministradores mta = MetodosAdministradores.getInstance();
@FXML 
private TextField labelBusqueda;
@FXML 
Button botonRegistrar,botonModicar,botonEliminar;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   _config();
   SesionActive();
    }    
    public void _config(){
        columnaClienteID.setCellValueFactory(new PropertyValueFactory("Id_cliente"));
        columnaClienteNombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
        columnaClienteApellido.setCellValueFactory(new PropertyValueFactory("Apellido"));
        columnaClienteCedula.setCellValueFactory(new PropertyValueFactory("Cedula"));
        columnaClienteSexo.setCellValueFactory(new PropertyValueFactory("Sexo"));
        columnaClienteFNacimiento.setCellValueFactory(new PropertyValueFactory("SfechaNacimiento"));
    

    }
    public void SesionActive(){
        tablaClientes.setItems(mta.getTablaClientes());
        tablaClientes.refresh();
        labelBusqueda.setOnKeyReleased(event->{ 
            
            if(!labelBusqueda.getText().isEmpty()){
           
                tablaClientes.setItems( mta.getTablaClientes(labelBusqueda.getText().toUpperCase()));
       tablaClientes.refresh();
      
            }else{
                 tablaClientes.setItems(mta.getTablaClientes());
        tablaClientes.refresh();
            }
            
        });
        botonEliminar.setOnAction(event->{   
        if(tablaClientes.getSelectionModel().getSelectedItem() ==null){
            Alertas aa = new Alertas(Alert.AlertType.WARNING, "no hay ningun item selepcionado");
            aa.setHeaderText("Corona Suits: Cuidado");
            aa.showAndWait();
        }else{
            Cliente c1 = tablaClientes.getSelectionModel().getSelectedItem();
            Alertas aa = new Alertas(Alert.AlertType.CONFIRMATION, "esta seguro de eliminar al cliente "+ c1.getNombre()+
                    " esta accion no se podra deshacer");
            aa.setHeaderText("Corona Suits: Cuidado");
            aa.showAndWait();
            if(aa.getResult() == ButtonType.CANCEL){
                
            }else{
               mta.Delete(c1);
               MetodosAdministradores.getInstance().registrarBitacora(LocalDateTime.now(), "ELIMINAR CLIENTE", ControlSesion.getInstance().getNombreUsuario());
               Alertas nn = new Alertas(Alert.AlertType.INFORMATION,"Cliente Eliminado con Exito");
            nn.setHeaderText("Corona Suites: Informacion");
            
            nn.show();
             tablaClientes.setItems(mta.getTablaClientes());
            tablaClientes.refresh();
            }
        }
        });
        
    }
  

}
