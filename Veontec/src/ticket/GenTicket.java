
package ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author Jhered Armus
 */
public class GenTicket {
    
    public void ConexionTicket(String titulo, int cantidad, double precio,String usuario, String metodoP){
        try {
            Object [] opc = {"Aceptar", "Cancelar"};
            int select = JOptionPane.showOptionDialog(null, "¿Quieres Generar un Ticket de tu compra?","Confirmacion"
                    ,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opc,"Mensaje");
           
            if (select == JOptionPane.YES_OPTION) {
            //Obtenmos datos para los parametros 
            Map<String, Object> Datos = new HashMap<String, Object>();
            Datos.clear();
            Datos.put("cmpTitulo",titulo);
            Datos.put("cmpCantidad",cantidad);
            Datos.put("cmpTotal",precio);
            Datos.put("UsuarioDto", usuario);
            Datos.put("Metodo", metodoP);
            
            //Estructura para mostrar el ticket  
            JasperReport ticket = (JasperReport) JRLoader.loadObject(getClass().getResource("Ticket.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(ticket,Datos,new JREmptyDataSource());
            JasperViewer view = new JasperViewer(jprint,false);
             
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
          }
        } catch (JRException ex) {
            Logger.getLogger(GenTicket.class.getName()).log(Level.SEVERE, null, ex);
        
        }

    }
}
