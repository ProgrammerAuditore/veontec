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
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import modelo.ObjEmail;
import modelo.dao.UsuarioDao;
import modelo.dto.UsuarioDto;
import src.FncGlobales;
import src.Software;
import src.Recursos;
import vista.paneles.PanelMiCuenta;
import vista.ventanas.VentanaMain;

public class CtrlMiCuenta{
    
    // ***** Vista
    public PanelMiCuenta pnMiCuenta;
    
    // ***** Modelos
    private UsuarioDto usuarioDto;
    private UsuarioDao usuarioDao;
    
    // ***** Atributos
    private static CtrlMiCuenta instancia;
    String passwd;
    private static final Logger LOG = Logger.getLogger(CtrlMiCuenta.class.getName());
    
    
    // ***** Constructor
    private CtrlMiCuenta(PanelMiCuenta laVista, UsuarioDto dto, UsuarioDao dao) {
        this.pnMiCuenta = laVista;
        this.usuarioDto = dto;
        this.usuarioDao = dao;
    }
    
    // Obtener instancia | Singleton 
    public static CtrlMiCuenta getInstancia(PanelMiCuenta laVista, UsuarioDto dto, UsuarioDao dao){
        LOG.info("Inicializando ");
        if( instancia == null ){
            LOG.info("Creando instancia ");
            instancia = new CtrlMiCuenta(laVista, dto, dao);
            instancia.mtdInit();
            
        }
        
        instancia.mtdEstablecerDatos();
        return instancia;
    }
    
    // ***** Eventos
    private synchronized void mtdEventoBtnEliminarCuenta(){
        MouseListener btnEliminarCuenta = null;
        pnMiCuenta.btnEliminarCuenta.removeMouseListener(btnEliminarCuenta);
        
        btnEliminarCuenta = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                    mtdEliminarCuenta();
            }
        };
        
        pnMiCuenta.btnEliminarCuenta.addMouseListener(btnEliminarCuenta);
    }
    
    private synchronized void mtdEventoBtnCerrarSession(){
        MouseListener btnCerrarSession = null;
        pnMiCuenta.btnCerrarSession.removeMouseListener(btnCerrarSession);
        
        btnCerrarSession = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCerrarSession();
            }
        };
        
        pnMiCuenta.btnCerrarSession.addMouseListener(btnCerrarSession);
    }
    
    private synchronized void mtdEventoBtnCambiarPasswd(){
        MouseListener btnCambiarPasswd = null;
        pnMiCuenta.btnChgPasswd.removeMouseListener(btnCambiarPasswd);
        
        btnCambiarPasswd = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                    mtdCambiarContrasena();
            }
        };
        
        pnMiCuenta.btnChgPasswd.addMouseListener(btnCambiarPasswd);
    }
    
    private synchronized void mtdEventoBtnCambiarCorreo(){
        MouseListener btnCambiarCorreo = null;
        pnMiCuenta.btnChgCorreo.removeMouseListener(btnCambiarCorreo);
        
        btnCambiarCorreo = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                    mtdCambiarCorreo();
            }
        };
        
        pnMiCuenta.btnChgCorreo.addMouseListener(btnCambiarCorreo);
    }
    
    private synchronized void mtdEventoBtnActualizarDatos(){
        MouseListener btnActualizarDatos = null;
        pnMiCuenta.btnEditar.removeMouseListener(btnActualizarDatos);
        
        btnActualizarDatos = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                    mtdActualizarDatos();
            }
        };
        
        pnMiCuenta.btnEditar.addMouseListener(btnActualizarDatos);
    }
    
    private synchronized void mtdEventoBtnVerificarEmail(){
        MouseListener btnVerificarEmail = null;
        pnMiCuenta.btnVerificarEmail.removeMouseListener(btnVerificarEmail);
        
        btnVerificarEmail = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                    mtdVerificarEmail();
            }
        };
        
        pnMiCuenta.btnVerificarEmail.addMouseListener(btnVerificarEmail);
    }
    
    // ***** Métodos
    private void  mtdInit(){
        // * Se inicializa o ejecuta una sola vez (Obligatorio)
        LOG.warning("Ejecutando controlador");
        mtdEventoBtnEliminarCuenta();
        mtdEventoBtnCerrarSession();
        mtdEventoBtnCambiarPasswd();
        mtdEventoBtnCambiarCorreo();
        mtdEventoBtnActualizarDatos();
        mtdEventoBtnVerificarEmail();
    }
    
    private void mtdEstablecerDatos(){
        LOG.info("Iniciando ...");
        instancia.usuarioDto = Veontec.usuarioDto;
        
        LOG.warning("Estableciendo datos ....");
        pnMiCuenta.cmpCorreo.setText(usuarioDto.getCmpCorreo() );
        pnMiCuenta.cmpNombreCompleto.setText(usuarioDto.getCmpNombreCompleto() );
        pnMiCuenta.cmpDireccion.setText( usuarioDto.getCmpDireccion() );
        pnMiCuenta.cmpTelefono.setText( usuarioDto.getCmpTelefono() );
        
        if( Veontec.usuarioDto.getCmpEstado() == 333 ){
            DesHabilitarBotones(false);
            JOptionPane.showMessageDialog(pnMiCuenta, "Verifica la cuenta, por favor.\nRevise su email para obtener el código de verificación.");
        }else{
            DesHabilitarBotones(true);
        }
        
        if( Veontec.usuarioDto.getCmpEstado() == 777 ){
            JOptionPane.showMessageDialog(pnMiCuenta, "Cuenta recuperada exitosamente.\nCambie su contraseña por seguridad.");
        }
        
    }   
    
    private void DesHabilitarBotones(boolean estado){
        pnMiCuenta.btnChgCorreo.setEnabled(estado);
        pnMiCuenta.btnChgPasswd.setEnabled(estado);
        pnMiCuenta.btnEditar.setEnabled(estado);
        pnMiCuenta.btnEliminarCuenta.setEnabled(estado);
        pnMiCuenta.btnVerificarEmail.setEnabled(!estado);
    }
        
    private void mtdEliminarCuenta(){
        passwd = "";
        // * Verificar el estado de cuenta
        if( Objects.equals(usuarioDto.getCmpEstado(), Software.veontecCuentaNoVerificada) ){
                JOptionPane.showMessageDialog(pnMiCuenta, "Por favor, verifique la cuenta.");
                return;
        }
        
        Box boxPassword = Box.createVerticalBox();
        JLabel infoInstruccion = new JLabel("Introduzca la contraseña actual");
        boxPassword.add(infoInstruccion);
        JPasswordField cmpPassword = new JPasswordField(24);
        boxPassword.add(cmpPassword);
        boxPassword.setLocation(Veontec.ventanaHome.getLocation());
        int opc = JOptionPane.showConfirmDialog(pnMiCuenta, boxPassword, "Eliminar cuenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if( opc == JOptionPane.OK_OPTION ){
            
            // * Covertir char[] a String
            passwd = String.valueOf(cmpPassword.getPassword());
            //System.out.println("CampoPassword#" + cmpPassword.getPassword() );
            //System.out.println("StringPassword#" + passwd );
            // * Si el campo de la contraseña no esta vacío
            if(  !passwd.trim().isEmpty() && passwd.length() > 0 ){
                mtdProcesoEliminarCuenta();
            }else{
                JOptionPane.showMessageDialog(pnMiCuenta, "El campo está vacío.");
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
                    JOptionPane.showMessageDialog(pnMiCuenta, "Cuenta eliminada exitosamente.");
                }
            }else{
                JOptionPane.showMessageDialog(pnMiCuenta, "La contraseña no coincide\nCuenta no eliminada.");
            }
        }
    }
    
    private void mtdCambiarContrasena() {
        // * Verificar el estado de cuenta
        if( Objects.equals(usuarioDto.getCmpEstado(), Software.veontecCuentaNoVerificada) ){
                JOptionPane.showMessageDialog(pnMiCuenta, "Por favor, verifique la cuenta.");
                return;
        }
        
        String passwdActual="", passwdNueva="", passwdRepetir="";
        Box boxPassword = Box.createVerticalBox();
        JLabel info1 = new JLabel("Introduzca la contraseña actual");
        boxPassword.add(info1);
        JPasswordField cmpPasswdActual = new JPasswordField(24);
        boxPassword.add(cmpPasswdActual);
        
        JLabel info2 = new JLabel("Introduzca la contraseña nueva");
        boxPassword.add(info2);
        JPasswordField cmpPasswdNueva = new JPasswordField(24);
        boxPassword.add(cmpPasswdNueva);
        
        JLabel info3 = new JLabel("Repite la contraseña nueva");
        boxPassword.add(info3);
        JPasswordField cmpPasswdRepetir = new JPasswordField(24);
        boxPassword.add(cmpPasswdRepetir);
        
        boxPassword.setLocation(Veontec.ventanaHome.getLocation());
        int opc = JOptionPane.showConfirmDialog(pnMiCuenta, boxPassword, "Cambiar contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if( opc == JOptionPane.OK_OPTION ){
            passwdActual = String.valueOf(cmpPasswdActual.getPassword()).trim();
            passwdNueva = String.valueOf(cmpPasswdNueva.getPassword()).trim();
            passwdRepetir = String.valueOf(cmpPasswdRepetir.getPassword()).trim();
            
            if( passwdActual.trim().isEmpty() || passwdNueva.trim().isEmpty() || passwdRepetir.trim().isEmpty() ){
                JOptionPane.showMessageDialog(pnMiCuenta, "Los campos están incompletos");
            }else{
                if( mtdVerificarPassword(usuarioDto.getCmpPassword(), passwdActual.toCharArray()) ){
                    if( passwdNueva.equals(passwdRepetir) ){
                        
                        // * Encriptar contraseña y alamacenarlo en el DTO 
                        usuarioDto.setCmpPassword( mtdEncriptarPassword( passwdRepetir.toCharArray() ) );
                        usuarioDto.setCmpActualizadoEn(new FncGlobales().fncObtenerFechaYHoraActualSQL() );
                        usuarioDto.setCmpEstado(Software.veontecCuentaVerificada);
                        usuarioDto.setCmpKey("No");
                        
                        // * Intetamos actualizar los datos del usuario
                        if(usuarioDao.mtdActualizar(usuarioDto)){
                            Veontec.usuarioDto = usuarioDto;
                            
                            // * Registrar los nuevos cambios de la cuenta
                            Veontec.cuentaDto.setPasswd(passwdRepetir);
                            Veontec.cuentaDao.regitrar_datos(Veontec.cuentaDto);
                            
                            // * Establecer el titulo de la ventana
                            Veontec.ventanaHome.setTitle( Veontec.usuarioDto.getCmpNombreCompleto() 
                            + " | "  + Veontec.usuarioDto.getCmpCorreo() + " - " + Software.NombreSoftware );
                            
                            JOptionPane.showMessageDialog(pnMiCuenta, "Contraseña modificada exitosamente.");
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(pnMiCuenta, "La contraseña nueva no coincide.");
                    }
                }else{
                    JOptionPane.showMessageDialog(pnMiCuenta, "La contraseña actual es incorrecta.");
                }
            }
        }
    }
    
    private void mtdCambiarCorreo() {
        
        // * Verificar el estado de cuenta
        if( Objects.equals(usuarioDto.getCmpEstado(), Software.veontecCuentaNoVerificada) ){
                JOptionPane.showMessageDialog(pnMiCuenta, "Por favor, verifique la cuenta.");
                return;
        }
        
        String correoActual="", correoNueva="", correoRepetir="";
        Box boxCorreo = Box.createVerticalBox();
        JLabel info1 = new JLabel("Introduzca la contraseña actual");
        boxCorreo.add(info1);
        JPasswordField cmpPasswordActual = new JPasswordField(24);
        boxCorreo.add(cmpPasswordActual);
        
        JLabel info2 = new JLabel("Introduzca el correo nuevo");
        boxCorreo.add(info2);
        JTextField cmpCorreoNueva = new JTextField(24);
        boxCorreo.add(cmpCorreoNueva);
        
        JLabel info3 = new JLabel("Repite el correo nuevo");
        boxCorreo.add(info3);
        JTextField cmpCorreoRepetir = new JTextField(24);
        boxCorreo.add(cmpCorreoRepetir);
        
        boxCorreo.setLocation(Veontec.ventanaHome.getLocation());
        int opc = JOptionPane.showConfirmDialog(pnMiCuenta, boxCorreo, "Cambiar correo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if( opc == JOptionPane.OK_OPTION ){
            correoActual = String.valueOf(cmpPasswordActual.getPassword()).trim();
            correoNueva = cmpCorreoNueva.getText().trim();
            correoRepetir = cmpCorreoRepetir.getText().trim();
            
            if( correoActual.trim().isEmpty() || correoNueva.trim().isEmpty() || correoRepetir.trim().isEmpty() ){
                JOptionPane.showMessageDialog(pnMiCuenta, "Los campos están incompletos");
            }else{
                if( mtdVerificarPassword(usuarioDto.getCmpPassword(), correoActual.toCharArray()) ){
                    if( correoNueva.equals(correoRepetir) ){
                        
                        // * Actualizar los datos del usuario
                        usuarioDto.setCmpActualizadoEn(new FncGlobales().fncObtenerFechaYHoraActualSQL() );
                        usuarioDto.setCmpEstado(Software.veontecCuentaVerificada);
                        // Salvar el nuevo correo
                        usuarioDto.setCmpCorreo( correoRepetir.trim() );
                        usuarioDto.setCmpKey("No");
                        
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
                                + " | "  + Veontec.usuarioDto.getCmpCorreo() + " - " + Software.NombreSoftware );
                                
                                // * Actualizar los datos
                                mtdEstablecerDatos();
                                JOptionPane.showMessageDialog(pnMiCuenta, "Correo modificado exitosamente.");
                            }
                            
                        }else{
                            JOptionPane.showMessageDialog(pnMiCuenta, "El nuevo correo no está disponible.");
                        }
                    }else{
                        JOptionPane.showMessageDialog(pnMiCuenta, "El correo nuevo no coincide.");
                    }
                }else{
                    JOptionPane.showMessageDialog(pnMiCuenta, "La contraseña actual es incorrecta.");
                }
            }
        }
    }
    
    private void  mtdActualizarDatos(){
        
        // * Verificar el estado de cuenta
        if( Objects.equals(usuarioDto.getCmpEstado(), Software.veontecCuentaNoVerificada) ){
                JOptionPane.showMessageDialog(pnMiCuenta, "Por favor, verifique la cuenta.");
                return;
        }
        
        if( pnMiCuenta.mtdVerificarCampos() ){
            
            // * Establecer los nuevos datos
            usuarioDto.setCmpActualizadoEn(new FncGlobales().fncObtenerFechaYHoraActualSQL() );
            usuarioDto.setCmpNombreCompleto(pnMiCuenta.cmpNombreCompleto.getText().trim());
            usuarioDto.setCmpDireccion(pnMiCuenta.cmpDireccion.getText().trim() );
            usuarioDto.setCmpTelefono(pnMiCuenta.cmpTelefono.getText().trim() );
            usuarioDto.setCmpCorreo(pnMiCuenta.cmpCorreo.getText().trim() );
            usuarioDto.setCmpEstado(Software.veontecCuentaVerificada);
            usuarioDto.setCmpKey("No");
            
                // * Intentar actualizar los datos con una consulta
                // a la base de datos
                if(usuarioDao.mtdActualizar(usuarioDto)){
                    Veontec.usuarioDto = usuarioDto;
                    // * Establecer el titulo de la ventana
                    Veontec.ventanaHome.setTitle( Veontec.usuarioDto.getCmpNombreCompleto() 
                    + " | "  + Veontec.usuarioDto.getCmpCorreo() + " - " + Software.NombreSoftware );
                    // * Actualizar los datos
                    mtdEstablecerDatos();
                    JOptionPane.showMessageDialog(pnMiCuenta, "Datos actualizados exitosamente.");
                }
            
        }else{
            JOptionPane.showMessageDialog(pnMiCuenta, "Verifica que los datos sean correctos.");
        }
    }
    
    private void mtdVerificarEmail(){
        
        // * Verificar el estado de cuenta
        if( !Objects.equals(usuarioDto.getCmpEstado(), Software.veontecCuentaNoVerificada) ){
                JOptionPane.showMessageDialog(pnMiCuenta, "La cuenta ya está verificada.");
                return;
        }
        
        String passwdActual="", codigoVerificacion="";
        Box boxVerificacion = Box.createVerticalBox();
        JLabel info1 = new JLabel("Introduzca la contraseña actual");
        boxVerificacion.add(info1);
        JPasswordField cmpPasswordActual = new JPasswordField(24);
        boxVerificacion.add(cmpPasswordActual);
        
        JLabel info2 = new JLabel("Introduzca el código de verificación");
        boxVerificacion.add(info2);
        JTextField cmpCodigoVerificacion = new JTextField(24);
        boxVerificacion.add(cmpCodigoVerificacion);
        
        boxVerificacion.setLocation(Veontec.ventanaHome.getLocation());
        int opc = JOptionPane.showConfirmDialog(pnMiCuenta, boxVerificacion, "Verificar correo y cuenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if( opc == JOptionPane.OK_OPTION ){
            passwdActual = String.valueOf(cmpPasswordActual.getPassword()).trim();
            codigoVerificacion = cmpCodigoVerificacion.getText().trim();
            
            if( passwdActual.trim().isEmpty() || codigoVerificacion.trim().isEmpty() ){
                JOptionPane.showMessageDialog(pnMiCuenta, "Los campos están incompletos");
            }else if( !mtdVerificarPassword( usuarioDto.getCmpPassword(), passwdActual.toCharArray() )){
                JOptionPane.showMessageDialog(pnMiCuenta, "La contraseña actual es incorrecta.");
            }else if( !usuarioDto.getCmpKey().equals(codigoVerificacion) ){
                JOptionPane.showMessageDialog(pnMiCuenta, "Código de verificación incorrecta");
            }else{

                if( !usuarioDao.mtdComprobar(usuarioDto) ){
                    if( usuarioDto.getCmpKey().equals(codigoVerificacion) ){
                        
                        // * Enviar mensanje de Bienvenida
                        ObjEmail.mtdEnviarBienvenida(usuarioDto);
                        
                        // * Actualizar los datos del usuario
                        usuarioDto.setCmpActualizadoEn(new FncGlobales().fncObtenerFechaYHoraActualSQL());
                        usuarioDto.setCmpEstado(Software.veontecCuentaVerificada);
                        usuarioDto.setCmpKey("No");
                        usuarioDao.mtdActualizar(usuarioDto);
                        
                        // * Establecer el titulo de la ventana
                        Veontec.ventanaHome.setTitle( Veontec.usuarioDto.getCmpNombreCompleto() 
                        + " | "  + Veontec.usuarioDto.getCmpCorreo() + " - " + Software.NombreSoftware );
                        
                        Veontec.usuarioDto = usuarioDto;
                        mtdCerrarSession();
                        JOptionPane.showMessageDialog(pnMiCuenta, "La cuenta ha sido verificada exitosamente.");
                        
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
            ctrl.mtdInitLoggin();
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
