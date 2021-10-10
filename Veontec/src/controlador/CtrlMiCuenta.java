package controlador;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import index.Veontec;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import modelo.dao.UsuarioDao;
import modelo.dto.UsuarioDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vista.paneles.PanelMiCuenta;
import vista.ventanas.VentanaSingUp;

public class CtrlMiCuenta{
    
    // Vista
    public PanelMiCuenta laVista;
    
    // Modelos
    private UsuarioDto dto;
    private UsuarioDao dao;
    
    // Atributos
    private static CtrlMiCuenta instancia;
    String passwd;
    static Log logger = LogFactory.getLog(CtrlMiCuenta.class);
    
    // Constructor
    private CtrlMiCuenta(PanelMiCuenta laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.dto = dto;
        this.dao = dao;
    }
    
    // Obtener instancia | Singleton 
    public static CtrlMiCuenta getInstancia(PanelMiCuenta laVista, UsuarioDto dto, UsuarioDao dao){
        logger.info("Inicializando ");
        if( instancia == null ){
            logger.info("Creando instancia ");
            instancia = new CtrlMiCuenta(laVista, dto, dao);
            
        }
        
        instancia.mtdInit();
        return instancia;
    }
    
    // Eventos
    private void mtdEventoBtnEliminarCuenta(){
        MouseListener btnEliminarCuenta = null;
        laVista.btnEliminarCuenta.removeMouseListener(btnEliminarCuenta);
        
        btnEliminarCuenta = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEliminarCuenta();
            }
        };
        
        laVista.btnEliminarCuenta.addMouseListener(btnEliminarCuenta);
    }
    
    private void mtdEventoBtnCerrarSession(){
        MouseListener btnCerrarSession = null;
        laVista.btnCerrarSession.removeMouseListener(btnCerrarSession);
        
        btnCerrarSession = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCerrarSession();
            }
        };
        
        laVista.btnCerrarSession.addMouseListener(btnCerrarSession);
    }
    
    // Métodos
    public void  mtdInit(){
        logger.warn("Ejecutando controlador");
        mtdEstablecerDatos();
        mtdEventoBtnEliminarCuenta();
        mtdEventoBtnCerrarSession();
    }
    
    private void mtdEstablecerDatos(){
        laVista.campoCorreo1.setText( dto.getCmpCorreo() );
        laVista.campoPassword1.setText( "**********" );
        laVista.campoTexto1.setText( dto.getCmpNombreCompleto() );
    }
    
    private void mtdEliminarCuenta(){
        passwd = "";
        Box boxPassword = Box.createVerticalBox();
        JLabel infoInstruccion = new JLabel("Introduzca su constraseña para eliminiar la cuenta");
        boxPassword.add(infoInstruccion);
        JPasswordField cmpPassword = new JPasswordField(24);
        boxPassword.add(cmpPassword);
        boxPassword.setLocation(Veontec.ventanaHome.getLocation());
        int opc = JOptionPane.showConfirmDialog(laVista, boxPassword, "Eliminar cuenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if( opc == JOptionPane.OK_OPTION ){
            
            // * Covertir char[] a String
            passwd = String.valueOf(cmpPassword.getPassword());
            //System.out.println("CampoPassword#" + cmpPassword.getPassword() );
            //System.out.println("StringPassword#" + passwd );
            // * Si el campo de la contraseña no esta vacío
            if(  !passwd.trim().isEmpty() && passwd.length() > 0 ){
                mtdProcesoEliminarCuenta();
            }else{
                JOptionPane.showMessageDialog(laVista, "El campo esta vacío.");
            }
        }   
    }
    
    private void mtdProcesoEliminarCuenta(){
        // * Verificar / Recuperar datos del usuario actual
        dto = Veontec.usuarioDto;
        dto = dao.mtdConsultar(dto);

        // * Si existe
        if( dto != null ){

            // * Verificamos que la contraseña sea correcta 
            if( mtdVerificarPassword( dto.getCmpPassword(), passwd.toCharArray() ) ){

                // * Tratamos de eliminar la cuenta con una consulta
                if(dao.mtdRemover(dto)){
                    mtdCerrarSession();
                    JOptionPane.showMessageDialog(laVista, "Cuenta eliminada exitosamente.");
                }
            }else{
                JOptionPane.showMessageDialog(laVista, "La contraseña no coinciden\nCuenta no eliminada.");
            }
        }
    }
    
    private boolean mtdVerificarPassword(String passwdDB, char[] passwdCmp){
        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = passwdCmp;
        
        try {
            // Hash password
            //String hash = argon2.hash(10, 65536, 1, password);
            // // Estará almacenado en la base de datos
            String hash = passwdDB;
            
            // Verify password
            if (argon2.verify(hash, password)) {
                // Hash matches password
                System.out.println("Hash matches password");
                return true;
            } else {
                // Hash doesn't match password
                System.out.println("Hash doesn't match password");
            }
            
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }
        
        return false;
    }
    
    private void mtdCerrarSession(){
        Veontec.ventanaHome.setVisible(false);
        Veontec.ventanaHome.dispose();
        Veontec.ventanaHome = null;
        
        if( Veontec.ventanaSession == null ){
            mtdEliminarInstancias();
            Veontec.ventanaSession = new VentanaSingUp();
            CtrlSignUp ctrl = new CtrlSignUp(Veontec.ventanaSession, Veontec.ventanaSession.pnRegistrarme, Veontec.ventanaSession.pnLoggin); 
            ctrl.mtdInit();
        }
        
    }
    
    private void mtdEliminarInstancias(){
        CtrlBienvenida.mtdEliminarInstancia();
        CtrlCompras.mtdEliminarInstancia();
        CtrlMiTienda.mtdEliminarInstancia();
        CtrlMiCuenta.mtdEliminarInstancia();
        CtrlPreguntas.mtdEliminarInstancia();
        CtrlVenta.mtdEliminarInstancia();
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
