
package reportes;

import java.util.HashMap;
import java.util.Map;
import javax.swing.WindowConstants;
import modelo.Conexion;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


public class Reporte {
    String reporte;

    public Reporte(String reporte) {
        this.reporte = reporte;
    }
    
    public void getReporte(String parametro1, String parametro2){
        try{
            Map parameter = new HashMap();
            parameter.put("parameter1", parametro1+"%");
            parameter.put("parameter2", parametro2);
            
            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource(this.reporte+".jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(report, parameter, Conexion.getInstance().conectar());
            
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setVisible(true);
        }catch(JRException ex){
            ex.getMessage();
        }
    }
}
