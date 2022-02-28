
package sistema_hotel1;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 *
 * @author heyscar
 */
public class Alertas extends Alert{

    public Alertas(AlertType e){
        
        super(e);if(Sistema_hotel1.getWindow() !=null){
             super.initOwner(Sistema_hotel1.getWindow());
        super.initModality(Modality.WINDOW_MODAL);
        
        }
       this.getDialogPane().getScene().setFill(Paint.valueOf("transparent"));
        this.setTitle("Error al cargar ");
                this.getDialogPane().getStylesheets().add("sistema_hotel1/alertasStilos.css");
        this.setHeaderText("Corona Suites: Error!");
      this.initStyle(StageStyle.TRANSPARENT);

     this.setContentText("informamos que hubo un error "
             + "  si el error persiste consulte con el equipo de soporte");
     this.showAndWait();
    }
    public Alertas(AlertType e,String CONTENIDO){
        super(e);
       if(Sistema_hotel1.getWindow() !=null){
             super.initOwner(Sistema_hotel1.getWindow());
        super.initModality(Modality.WINDOW_MODAL);
        }
        this.setTitle("Error al cargar ");
               this.getDialogPane().getScene().getStylesheets().add("sistema_hotel1/alertasStilos.css");
        this.setHeaderText("Corona Suites");
      this.initStyle(StageStyle.TRANSPARENT);
  
        this.getDialogPane().getScene().setFill(Paint.valueOf("transparent"));

     this.setContentText(CONTENIDO);
    
    }
   
    
}
