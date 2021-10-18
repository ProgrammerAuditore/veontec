package controlador.tabs;

import controlador.ventanas.CtrlRegistrarme;
import controlador.ventanas.CtrlIniciarSesion;
import controlador.ventanas.CtrlMain;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import index.Veontec;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import modelo.ObjEmail;
import modelo.dao.UsuarioDao;
import modelo.dto.UsuarioDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import src.Info;
import src.Recursos;
import vista.paneles.PanelMiCuenta;
import vista.ventanas.VentanaMain;

public class CtrlMiCuenta{
    
    // Vista
    public PanelMiCuenta laVista;
    
    // Modelos
    private UsuarioDto usuarioDto;
    private UsuarioDao usuarioDao;
    
    // Atributos
    private static CtrlMiCuenta instancia;
    String passwd;
    static Log logger = LogFactory.getLog(CtrlMiCuenta.class);
    
    // Constructor
    private CtrlMiCuenta(PanelMiCuenta laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuarioDto = dto;
        this.usuarioDao = dao;
    }
    
    // Obtener instancia | Singleton 
    public static CtrlMiCuenta getInstancia(PanelMiCuenta laVista, UsuarioDto dto, UsuarioDao dao){
        logger.info("Inicializando ");
        if( instancia == null ){
            logger.info("Creando instancia ");
            instancia = new CtrlMiCuenta(laVista, dto, dao);
            instancia.mtdInit();
            
        }
        
        instancia.mtdEstablecerDatos();
        return instancia;
    }
    
    // Eventos
    private synchronized void mtdEventoBtnEliminarCuenta(){
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
    
    private synchronized void mtdEventoBtnCerrarSession(){
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
    
    private synchronized void mtdEventoBtnCambiarPasswd(){
        MouseListener btnCambiarPasswd = null;
        laVista.btnChgPasswd.removeMouseListener(btnCambiarPasswd);
        
        btnCambiarPasswd = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                    mtdCambiarContrasena();
            }
        };
        
        laVista.btnChgPasswd.addMouseListener(btnCambiarPasswd);
    }
    
    private synchronized void mtdEventoBtnCambiarCorreo(){
        MouseListener btnCambiarCorreo = null;
        laVista.btnChgCorreo.removeMouseListener(btnCambiarCorreo);
        
        btnCambiarCorreo = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                    mtdCambiarCorreo();
            }
        };
        
        laVista.btnChgCorreo.addMouseListener(btnCambiarCorreo);
    }
    
    private synchronized void mtdEventoBtnActualizarDatos(){
        MouseListener btnActualizarDatos = null;
        laVista.btnEditar.removeMouseListener(btnActualizarDatos);
        
        btnActualizarDatos = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                    mtdActualizarDatos();
            }
        };
        
        laVista.btnEditar.addMouseListener(btnActualizarDatos);
    }
    
    private synchronized void mtdEventoBtnVerificarEmail(){
        MouseListener btnVerificarEmail = null;
        laVista.btnVerificarEmail.removeMouseListener(btnVerificarEmail);
        
        btnVerificarEmail = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                    mtdVerificarEmail();
            }
        };
        
        laVista.btnVerificarEmail.addMouseListener(btnVerificarEmail);
    }
    
    // Métodos
    private void  mtdInit(){
        // * Se inicializa o ejecuta una sola vez (Obligatorio)
        logger.warn("Ejecutando controlador");
        mtdEventoBtnEliminarCuenta();
        mtdEventoBtnCerrarSession();
        mtdEventoBtnCambiarPasswd();
        mtdEventoBtnCambiarCorreo();
        mtdEventoBtnActualizarDatos();
        mtdEventoBtnVerificarEmail();
    }
    
    private void mtdEstablecerDatos(){
        logger.info("Iniciando ...");
        instancia.usuarioDto = Veontec.usuarioDto;
        
        logger.warn("Estableciendo datos ....");
        laVista.cmpCorreo.setText(usuarioDto.getCmpCorreo() );
        laVista.cmpNombreCompleto.setText(usuarioDto.getCmpNombreCompleto() );
        laVista.cmpDireccion.setText( usuarioDto.getCmpDireccion() );
        laVista.cmpTelefono.setText( usuarioDto.getCmpTelefono() );
        
        if( Veontec.usuarioDto.getCmpEstado() == 333 ){
            DesHabilitarBotones(false);
            JOptionPane.showMessageDialog(laVista, "Verifica la cuenta, por favor.\nRevise su email para obtener el codigo de verificación.");
        }else{
            DesHabilitarBotones(true);
        }
        
        if( Veontec.usuarioDto.getCmpEstado() == 777 ){
            JOptionPane.showMessageDialog(laVista, "Cuenta recupera exitosamente.\nCambie su contraseña por seguridad.");
        }
        
    }   
    
    private void DesHabilitarBotones(boolean estado){
        laVista.btnChgCorreo.setEnabled(estado);
        laVista.btnChgPasswd.setEnabled(estado);
        laVista.btnEditar.setEnabled(estado);
        laVista.btnEliminarCuenta.setEnabled(estado);
        laVista.btnVerificarEmail.setEnabled(!estado);
    }
        
    private void mtdEliminarCuenta(){
        passwd = "";
        // * Verificar el estado de cuenta
        if( Objects.equals(usuarioDto.getCmpEstado(), Info.veontecCuentaNoVerificada) ){
                JOptionPane.showMessageDialog(laVista, "Por favor, verifique la cuenta.");
                return;
        }
        
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
        usuarioDto = Veontec.usuarioDto;
        usuarioDto = usuarioDao.mtdConsultar(usuarioDto);

        // * Si existe
        if( usuarioDto != null ){

            // * Verificamos que la contraseña sea correcta 
            if( mtdVerificarPassword(usuarioDto.getCmpPassword(), passwd.toCharArray() ) ){

                // * Tratamos de eliminar la cuenta con una consulta
                if(usuarioDao.mtdRemover(usuarioDto)){
                    mtdCerrarSession();
                    JOptionPane.showMessageDialog(laVista, "Cuenta eliminada exitosamente.");
                }
            }else{
                JOptionPane.showMessageDialog(laVista, "La contraseña no coinciden\nCuenta no eliminada.");
            }
        }
    }
    
    private void mtdCambiarContrasena() {
        // * Verificar el estado de cuenta
        if( Objects.equals(usuarioDto.getCmpEstado(), Info.veontecCuentaNoVerificada) ){
                JOptionPane.showMessageDialog(laVista, "Por favor, verifique la cuenta.");
                return;
        }
        
        String passwdActual="", passwdNueva="", passwdRepetir="";
        Box boxPassword = Box.createVerticalBox();
        JLabel info1 = new JLabel("Introduza la constraseña actual");
        boxPassword.add(info1);
        JPasswordField cmpPasswdActual = new JPasswordField(24);
        boxPassword.add(cmpPasswdActual);
        
        JLabel info2 = new JLabel("Introduza la constraseña nueva");
        boxPassword.add(info2);
        JPasswordField cmpPasswdNueva = new JPasswordField(24);
        boxPassword.add(cmpPasswdNueva);
        
        JLabel info3 = new JLabel("Repite la constraseña nueva");
        boxPassword.add(info3);
        JPasswordField cmpPasswdRepetir = new JPasswordField(24);
        boxPassword.add(cmpPasswdRepetir);
        
        boxPassword.setLocation(Veontec.ventanaHome.getLocation());
        int opc = JOptionPane.showConfirmDialog(laVista, boxPassword, "Cambiar contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if( opc == JOptionPane.OK_OPTION ){
            passwdActual = String.valueOf(cmpPasswdActual.getPassword()).trim();
            passwdNueva = String.valueOf(cmpPasswdNueva.getPassword()).trim();
            passwdRepetir = String.valueOf(cmpPasswdRepetir.getPassword()).trim();
            
            if( passwdActual.trim().isEmpty() || passwdNueva.trim().isEmpty() || passwdRepetir.trim().isEmpty() ){
                JOptionPane.showMessageDialog(laVista, "Los campos están incompletos");
            }else{
                if( mtdVerificarPassword(usuarioDto.getCmpPassword(), passwdActual.toCharArray()) ){
                    if( passwdNueva.equals(passwdRepetir) ){
                        
                        // * Encriptar contraseña y alamacenarlo en el DTO 
                        usuarioDto.setCmpPassword( mtdEncriptarPassword( passwdRepetir.toCharArray() ) );
                        usuarioDto.setCmpEstado(Info.veontecCuentaVerificada);
                        usuarioDto.setCmpKey("No");
                        
                        // * Intetamos actualizar los datos del usuario
                        if(usuarioDao.mtdActualizar(usuarioDto)){
                            Veontec.usuarioDto = usuarioDto;
                            
                            // * Registrar los nuevos cambios de la cuenta
                            Veontec.cuentaDto.setPasswd(passwdRepetir);
                            Veontec.cuentaDao.regitrar_datos(Veontec.cuentaDto);
                            
                            JOptionPane.showMessageDialog(laVista, "Contraseña modificado exitosamnete.");
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(laVista, "La contraseña nueva no coinciden.");
                    }
                }else{
                    JOptionPane.showMessageDialog(laVista, "La contraseña actual es incorrecta.");
                }
            }
        }
    }
    
    private void mtdCambiarCorreo() {
        
        // * Verificar el estado de cuenta
        if( Objects.equals(usuarioDto.getCmpEstado(), Info.veontecCuentaNoVerificada) ){
                JOptionPane.showMessageDialog(laVista, "Por favor, verifique la cuenta.");
                return;
        }
        
        String correoActual="", correoNueva="", correoRepetir="";
        Box boxCorreo = Box.createVerticalBox();
        JLabel info1 = new JLabel("Introduza la contraseña actual");
        boxCorreo.add(info1);
        JPasswordField cmpPasswordActual = new JPasswordField(24);
        boxCorreo.add(cmpPasswordActual);
        
        JLabel info2 = new JLabel("Introduza el correo nuevo");
        boxCorreo.add(info2);
        JTextField cmpCorreoNueva = new JTextField(24);
        boxCorreo.add(cmpCorreoNueva);
        
        JLabel info3 = new JLabel("Repite el correo nuevo");
        boxCorreo.add(info3);
        JTextField cmpCorreoRepetir = new JTextField(24);
        boxCorreo.add(cmpCorreoRepetir);
        
        boxCorreo.setLocation(Veontec.ventanaHome.getLocation());
        int opc = JOptionPane.showConfirmDialog(laVista, boxCorreo, "Cambiar correo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if( opc == JOptionPane.OK_OPTION ){
            correoActual = String.valueOf(cmpPasswordActual.getPassword()).trim();
            correoNueva = cmpCorreoNueva.getText().trim();
            correoRepetir = cmpCorreoRepetir.getText().trim();
            
            if( correoActual.trim().isEmpty() || correoNueva.trim().isEmpty() || correoRepetir.trim().isEmpty() ){
                JOptionPane.showMessageDialog(laVista, "Los campos están incompletos");
            }else{
                if( mtdVerificarPassword(usuarioDto.getCmpPassword(), correoActual.toCharArray()) ){
                    if( correoNueva.equals(correoRepetir) ){
                        
                        // * Salvar el nuevo correo
                        usuarioDto.setCmpCorreo( correoRepetir.trim() );
                        usuarioDto.setCmpEstado(Info.veontecCuentaVerificada);
                        usuarioDto.setCmpKey("No");
                        
                        logger.info("Correo existente: " + usuarioDao.mtdComprobar(usuarioDto));
                        
                        // * Comprobar si el correo está disponible
                        // es decir, si no está registrado
                        if( usuarioDao.mtdComprobar(usuarioDto) == true ){
                            
                            // * Intetamos actualizar los datos del usuario
                            if(usuarioDao.mtdActualizar(usuarioDto)){
                                Veontec.usuarioDto = usuarioDto;
                                
                                // * Registrar los nuevos cambios de la cuenta
                                Veontec.cuentaDto.setCorreo(correoRepetir);
                                Veontec.cuentaDao.regitrar_datos(Veontec.cuentaDto);

                                // * Establecer el titulo de la ventana
                                Veontec.ventanaHome.setTitle( Veontec.usuarioDto.getCmpNombreCompleto() 
                                + " | "  + Veontec.usuarioDto.getCmpCorreo() + " - " + Info.NombreSoftware );
                                
                                // * Actualizar los datos
                                mtdEstablecerDatos();
                                JOptionPane.showMessageDialog(laVista, "Correo modificado exitosamnete.");
                            }
                            
                        }else{
                            JOptionPane.showMessageDialog(laVista, "El nuevo correo no está disponible.");
                        }
                    }else{
                        JOptionPane.showMessageDialog(laVista, "El correo nuevo no coinciden.");
                    }
                }else{
                    JOptionPane.showMessageDialog(laVista, "La contraseña actual es incorrecta.");
                }
            }
        }
    }
    
    private void  mtdActualizarDatos(){
        
        // * Verificar el estado de cuenta
        if( Objects.equals(usuarioDto.getCmpEstado(), Info.veontecCuentaNoVerificada) ){
                JOptionPane.showMessageDialog(laVista, "Por favor, verifique la cuenta.");
                return;
        }
        
        if( laVista.mtdVerificarCampos() ){
            
            // * Establecer los nuevos datos
            usuarioDto.setCmpNombreCompleto(laVista.cmpNombreCompleto.getText().trim());
            usuarioDto.setCmpDireccion( laVista.cmpDireccion.getText().trim() );
            usuarioDto.setCmpTelefono( laVista.cmpTelefono.getText().trim() );
            usuarioDto.setCmpCorreo( laVista.cmpCorreo.getText().trim() );
            usuarioDto.setCmpEstado(Info.veontecCuentaVerificada);
            usuarioDto.setCmpKey("No");
            
                // * Intentar actualizar los datos con una consulta
                // a la base de datos
                if(usuarioDao.mtdActualizar(usuarioDto)){
                    Veontec.usuarioDto = usuarioDto;
                    // * Establecer el titulo de la ventana
                    Veontec.ventanaHome.setTitle( Veontec.usuarioDto.getCmpNombreCompleto() 
                    + " | "  + Veontec.usuarioDto.getCmpCorreo() + " - " + Info.NombreSoftware );
                    // * Actualizar los datos
                    mtdEstablecerDatos();
                    JOptionPane.showMessageDialog(laVista, "Datos actualizados exitosamnete.");
                }
            
        }else{
            JOptionPane.showMessageDialog(laVista, "Verifica que los datos sean correctos.");
        }
    }
    
    private void mtdVerificarEmail(){
        
        // * Verificar el estado de cuenta
        if( !Objects.equals(usuarioDto.getCmpEstado(), Info.veontecCuentaNoVerificada) ){
                JOptionPane.showMessageDialog(laVista, "La cuenta ya está verificada.");
                return;
        }
        
        String passwdActual="", codigoVerificacion="";
        Box boxVerificacion = Box.createVerticalBox();
        JLabel info1 = new JLabel("Introduza la contraseña actual");
        boxVerificacion.add(info1);
        JPasswordField cmpPasswordActual = new JPasswordField(24);
        boxVerificacion.add(cmpPasswordActual);
        
        JLabel info2 = new JLabel("Introduza el código de verifación");
        boxVerificacion.add(info2);
        JTextField cmpCodigoVerificacion = new JTextField(24);
        boxVerificacion.add(cmpCodigoVerificacion);
        
        boxVerificacion.setLocation(Veontec.ventanaHome.getLocation());
        int opc = JOptionPane.showConfirmDialog(laVista, boxVerificacion, "Verificar correo y cuenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if( opc == JOptionPane.OK_OPTION ){
            passwdActual = String.valueOf(cmpPasswordActual.getPassword()).trim();
            codigoVerificacion = cmpCodigoVerificacion.getText().trim();
            
            if( passwdActual.trim().isEmpty() || codigoVerificacion.trim().isEmpty() ){
                JOptionPane.showMessageDialog(laVista, "Los campos están incompletos");
            }else if( !mtdVerificarPassword( usuarioDto.getCmpPassword(), passwdActual.toCharArray() )){
                JOptionPane.showMessageDialog(laVista, "La contraseña actual es incorrecta.");
            }else if( !usuarioDto.getCmpKey().equals(codigoVerificacion) ){
                JOptionPane.showMessageDialog(laVista, "Codigo de verificación incorrecta");
            }else{

                if( !usuarioDao.mtdComprobar(usuarioDto) ){
                    if( usuarioDto.getCmpKey().equals(codigoVerificacion) ){
                        
                        ObjEmail.mtdEnviarBienvenida(usuarioDto);
                        usuarioDto.setCmpEstado(Info.veontecCuentaVerificada);
                        usuarioDto.setCmpKey("No");
                        usuarioDao.mtdActualizar(usuarioDto);
                        Veontec.usuarioDto = usuarioDto;
                        mtdCerrarSession();
                        JOptionPane.showMessageDialog(laVista, "La cuenta ha sido verificada exitosamente.");
                        
                    }
                }
            }
        }
    }
    
    private String mtdEncriptarPassword(char[] cmpPassword){
        // Encriptar la contraseña
        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = cmpPassword;
         String hash = "";
         
        try {
            // Hash password
            hash = argon2.hash(10, 65536, 1, password);
            
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }
        
        //System.out.println("" + hash);
        return hash;
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
                //System.out.println("Hash matches password");
                return true;
            } else {
                // Hash doesn't match password
                //System.out.println("Hash doesn't match password");
            }
            
        } finally {
            // Wipe confidential data
            argon2.wipeArray(password);
        }
        
        return false;
    }
    
    private void mtdCerrarSession(){
        try {
            Veontec.ventanaHome.setVisible(false);
            Veontec.ventanaHome.dispose();
            Veontec.ventanaHome = null;
        } catch (Exception e) {}

        if( Veontec.ventanaSession == null ){
            mtdEliminarInstancias();
            Veontec.ventanaSession = new VentanaMain();
            CtrlMain ctrl = new CtrlMain(Veontec.ventanaSession);
            ctrl.mtdInit();
        }
        
    }
    
    private void mtdEliminarInstancias(){
        Recursos.dataCuenta().delete();
        Veontec.cuentaDto = null;
        
        CtrlBienvenida.mtdEliminarInstancia();
        CtrlCompras.mtdEliminarInstancia();
        CtrlMiTienda.mtdEliminarInstancia();
        CtrlMiCuenta.mtdEliminarInstancia();
        CtrlPreguntas.mtdEliminarInstancia();
        CtrlVentas.mtdEliminarInstancia();
        CtrlIniciarSesion.mtdEliminarInstancia();
        CtrlRegistrarme.mtdEliminarInstancia();
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
