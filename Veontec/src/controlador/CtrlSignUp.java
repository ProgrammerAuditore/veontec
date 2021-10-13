package controlador;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import index.Veontec;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import modelo.ObjEmail;
import modelo.dao.CategoriaDao;
import modelo.dao.CuentaDao;
import modelo.dao.UsuarioDao;
import modelo.dto.CategoriaDto;
import modelo.dto.CuentaDto;
import modelo.dto.UsuarioDto;
import src.Info;
import vista.componentes.campos.CampoCorreo;
import vista.paneles.PanelSignUp;
import vista.paneles.PanelRegistrarme;
import vista.ventanas.VentanaHome;
import vista.ventanas.VentanaSingUp;

public class CtrlSignUp implements MouseListener{
    
    // * Vistas
    private PanelRegistrarme pnRegistrarme;
    private PanelSignUp pnInicarSession;
    private VentanaSingUp ni;
    
    // * Modelos

    public CtrlSignUp(VentanaSingUp ni, PanelRegistrarme pnRegistrarme, PanelSignUp pnInicarSession) {
        this.ni = ni;
        this.pnRegistrarme = pnRegistrarme;
        this.pnInicarSession = pnInicarSession;
        
        // * Definir oyentes
        this.pnRegistrarme.btnRegistrarme.addMouseListener(this);
        this.pnInicarSession.btnIniciarSession.addMouseListener(this);
        this.pnInicarSession.btnRecuperarCuenta.addMouseListener(this);
        
    }
    
    public void mtdInit(){
        ni.setTitle(Info.NombreSoftware);
        
        if(CtrlHiloConexion.checkConexion() == false){
            ni.tabContenedor.getTabComponentAt(1).setEnabled(false);
            ni.tabContenedor.getTabComponentAt(2).setEnabled(false);
            //System.out.println("No hay conexion a la base de datos");
        }else{
            //System.out.println("Existe conexion a la base de datos... ");
        }
        
        // * Mostrar las ventana
        ni.setLocationRelativeTo(null);
        ni.setVisible(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
        if( e.getSource() == pnRegistrarme.btnRegistrarme  ){
            mtdRegistrarme();
        }
        
        if( e.getSource() == pnInicarSession.btnIniciarSession ){
            mtdIniciarSession();
        }
        
        if( e.getSource() == pnInicarSession.btnRecuperarCuenta ){
            mtdRecuperarCuenta();
        }
        
    }
    
    private void mtdIniciarSession(){
        if( CtrlHiloConexion.ctrlEstado ){
            // * Verificar los campos de registrarme
            if( mtdObtenerCamposIncorrectos_IniciarSession() ){
                return;
            }

            // * Obtener cuenta de usuarioDto si existe
            if( mtdObtenerUsuario(pnInicarSession.campoCorreo1.getText().trim()) ){

            // * Verificar datos capturado son correctos
            if( mtdValidarDatosDeUsuario(pnInicarSession.campoCorreo1.getText().trim(), 
            String.valueOf(pnInicarSession.campoPassword1.getPassword())) ){
                
                // * Abrir ventana home
                mtdAbrirVentanaHome();
                
            }else{
                JOptionPane.showMessageDialog(ni, "El campo correo o contraseña son incorrectos.");
            }

            }else{
                JOptionPane.showMessageDialog(ni, "Usuario no registrado.");
            }
        }else{
            JOptionPane.showMessageDialog(ni, "No hay conexión");
        }
    }
    
    private void mtdRegistrarme(){
        
        if( CtrlHiloConexion.ctrlEstado ){
            // * Verificar los campos de registrarme
            if( mtdObtenerCamposIncorrectos_Registrarme() ){
                return ;
            }

            // * Registrando usuarioDto
            UsuarioDto usuarioDto = new UsuarioDto();
            UsuarioDao usuarioDao = new UsuarioDao();
            usuarioDto.setCmpNombreCompleto(pnRegistrarme.campoTexto1.getText().trim() );
            usuarioDto.setCmpCorreo( pnRegistrarme.campoCorreo1.getText().trim() );
            usuarioDto.setCmpPassword(mtdObtenerPasswordEncriptado(pnRegistrarme.campoPassword2.getPassword()));
            usuarioDto.setCmpKey("No");
            usuarioDto.setCmpEstado(0);

            // * Comprobar si el correo está disponible
            // es decir, si no está registrado
            if( !usuarioDao.mtdComprobar(usuarioDto) ){
                JOptionPane.showMessageDialog(null, "El correo ya está registrado.");
            }else{
                if( usuarioDao.mtdInsetar(usuarioDto) ){
                    
                    // * Obtener datos de la nueva cuenta
                    usuarioDto = usuarioDao.mtdConsultar(usuarioDto);
                    
                    if( ObjEmail.mtdEnviarValidarEmail(usuarioDto) ){
                    
                        if(usuarioDao.mtdActualizar(usuarioDto)){
                        
                            // * Crear una categoria por defecto llamda 'nueva'
                            CategoriaDto cateDto = new CategoriaDto();
                            CategoriaDao cateDao = new CategoriaDao();
                            cateDto.setCateNombre("Nueva");
                            cateDto.setCateUsuario(usuarioDto.getCmpID());
                            cateDto.setCateTotalProductos(0);
                            cateDao.mtdInsetar(cateDto);

                            mtdVaciarCampos_Registrarme();
                            JOptionPane.showMessageDialog(ni, "Se registro exitosamente.");
                            
                        }
                    }
                }
            }
            
        }else{
            JOptionPane.showMessageDialog(ni, "No hay conexión");
        }
        
    }
    
    public void mtdAbrirVentanaHome(){
        if( Veontec.ventanaHome == null ){
            // * Instanciar objetos para el cuenta actual
            Veontec.ventanaHome = new VentanaHome();
            Veontec.ventanaHome.setTitle( Veontec.usuarioDto.getCmpNombreCompleto() 
                    + " | "  + Veontec.usuarioDto.getCmpCorreo() 
                    + " - " + Info.NombreSoftware );

            mtdGuardarCuenta();

            // * Crear controlador y mostrar la ventana principal
            CtrlHome ctrl = new CtrlHome(Veontec.ventanaHome);
            ctrl.laVista.setLocationRelativeTo(null);
            ctrl.laVista.setVisible(true);

            // * Cerrar y destruir la ventana de SingUp
            Veontec.ventanaSession.setVisible(false);
            Veontec.ventanaSession.dispose();
            Veontec.ventanaSession = null;
        }
    }
    
    public boolean mtdObtenerUsuario(String correoInput){
        Veontec.usuarioDto = new UsuarioDto();
        Veontec.usuarioDao = new UsuarioDao();
        Veontec.usuarioDto.setCmpCorreo( correoInput );
        Veontec.usuarioDto = Veontec.usuarioDao.mtdConsultar(Veontec.usuarioDto);
        
        if( Veontec.usuarioDto.getCmpCorreo() == null || Veontec.usuarioDto.getCmpPassword() == null ){
                JOptionPane.showMessageDialog(null, "Usuario no registrado o verifeque los datos.");
                return false;
        }
        
        return true;
    }
    
    public boolean mtdValidarDatosDeUsuario(String correo, String passwd){
        return correo.equals(Veontec.usuarioDto.getCmpCorreo()) && 
                mtdComprobarPassword(Veontec.usuarioDto.getCmpPassword(), passwd.toCharArray());
    }
    
    private void mtdGuardarCuenta(){
        if( Veontec.cuentaDto == null ){
            // * Registrar los datos del usuarioDto
            Veontec.cuentaDto = new CuentaDto();
            Veontec.cuentaDao = new CuentaDao(); 
            Veontec.cuentaDto.setCorreo(pnInicarSession.campoCorreo1.getText().trim());
            Veontec.cuentaDto.setPasswd(String.valueOf(pnInicarSession.campoPassword1.getPassword()).trim());
            Veontec.cuentaDao.regitrar_datos(Veontec.cuentaDto);
            Veontec.cuentaDto = Veontec.cuentaDao.obtener_datos();
            System.out.println("Cuenta : " + Veontec.cuentaDto.getCorreo());
            System.out.println("Cuenta : " + Veontec.cuentaDto.getPasswd());
        }
    }
    
    private void mtdRecuperarCuenta(){
        String correoActual="";
        Box boxVRecuperarCuenta = Box.createVerticalBox();
        JLabel info1 = new JLabel("Introduza la contraseña actual");
        boxVRecuperarCuenta.add(info1);
        CampoCorreo cmpCampoCorreo = new CampoCorreo();
        boxVRecuperarCuenta.add(cmpCampoCorreo);
        
        boxVRecuperarCuenta.setLocation(Veontec.ventanaSession.getLocation());
        int opc = JOptionPane.showConfirmDialog(null, boxVRecuperarCuenta, "Verificar correo y cuenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if( opc == JOptionPane.OK_OPTION ){
            correoActual = cmpCampoCorreo.getText().trim();
            
            if( !cmpCampoCorreo.isAprobado() ){
                JOptionPane.showMessageDialog(null, "El correo es incorrecto.");
                
            }else
            if( correoActual.isEmpty() || correoActual.length() > 60 ){
                JOptionPane.showMessageDialog(null, "El correo es incorrecto.");
                
            }else{
                
                Veontec.usuarioDto = new UsuarioDto();
                Veontec.usuarioDao = new UsuarioDao();
                Veontec.usuarioDto.setCmpCorreo(correoActual);
                Veontec.usuarioDto = Veontec.usuarioDao.mtdConsultar(Veontec.usuarioDto);
                if( Veontec.usuarioDto == null){
                    JOptionPane.showMessageDialog(null, "No existe una cuenta con el correo introducido.");
                }else if( Veontec.usuarioDto.getCmpCorreo() == null || Veontec.usuarioDto.getCmpPassword() == null ){
                    JOptionPane.showMessageDialog(null, "No existe una cuenta con el correo introducido.");
                }else{
                    
                    if( Veontec.usuarioDto.getCmpEstado().equals(333) ){
                        JOptionPane.showMessageDialog(null, "La cuenta no está verificada.");
                        return;
                    }
                    
                    if(ObjEmail.mtdEnviarRecuperarCuenta(Veontec.usuarioDto)){
                        Veontec.usuarioDto.setCmpPassword( mtdObtenerPasswordEncriptado(Veontec.usuarioDto.getCmpKey().toCharArray()) );
                        if(Veontec.usuarioDao.mtdActualizar(Veontec.usuarioDto)){
                            
                            JOptionPane.showMessageDialog(ni, "Cuenta recuperada exitosamente.\nRevise su correo par obtener su codigo.");
                            
                        }
                    }
                }
                
            }
        }
    }
    
    private boolean mtdComprobarPassword(String passwdDB,  char[] passwordInput){
        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = passwordInput;
        
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
    
    private String mtdObtenerPasswordEncriptado(char[] cmpPasswd){
        // Encriptar la contraseña
        // Create instance
        Argon2 argon2 = Argon2Factory.create();

        // Read password from user
        char[] password = cmpPasswd;
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
    
    private boolean mtdObtenerCamposIncorrectos_IniciarSession(){
    int campos_incorrectos = 0;
    String msg = "Verifica los siguientes datos: \n";
        
        if( pnInicarSession.campoCorreo1.getText().trim().isEmpty() 
                || !pnInicarSession.campoCorreo1.isAprobado()  ){
            campos_incorrectos++;
            msg += "El campo correo es incorrecto. \n";
        }
        
        if( String.valueOf(pnInicarSession.campoPassword1.getPassword()).trim().isEmpty() ){
            campos_incorrectos++;
            msg += "El campo contraseña está vacio. \n";
        }
    
        if( campos_incorrectos > 0 )
                JOptionPane.showMessageDialog(ni, msg);

        //System.out.println("Campos incorrectos = " + campos_incorrectos);
        return ( campos_incorrectos > 0 );
    }
    
    private boolean mtdObtenerCamposIncorrectos_Registrarme(){
    int campos_incorrectos = 0;
    String msg = "Verifica los siguientes datos: \n";
    
        if( pnRegistrarme.campoTexto1.getText().trim().isEmpty() 
                || !pnRegistrarme.campoTexto1.isAprobado() ){
            campos_incorrectos++;
            msg += "El campo nombre es incorrecto. \n";
        }
        
        if( pnRegistrarme.campoCorreo1.getText().trim().isEmpty() 
                || !pnRegistrarme.campoCorreo1.isAprobado()  ){
            campos_incorrectos++;
            msg += "El campo correo es incorrecto. \n";
        }

        if( String.valueOf(pnRegistrarme.campoPassword1.getPassword()).trim().isEmpty() ){
            campos_incorrectos++;
            msg += "El campo contraseña está vacio. \n";
        }
        
        if( String.valueOf(pnRegistrarme.campoPassword2.getPassword()).trim().isEmpty() ){
            campos_incorrectos++;
            msg += "El campo repetir contraseña está vacio. \n";
        }
        
        if( !String.valueOf(pnRegistrarme.campoPassword1.getPassword()).trim()
                .equals(String.valueOf(pnRegistrarme.campoPassword2.getPassword()).trim()) ){
            campos_incorrectos++;
            msg += "La contraseña no coindice. \n";
        }
        
        if( campos_incorrectos > 0 )
            JOptionPane.showMessageDialog(ni, msg);
        
        //System.out.println("Campos incorrectos = " + campos_incorrectos);
        return ( campos_incorrectos > 0 );
    }
    
    private void mtdVaciarCampos_Registrarme(){
        pnRegistrarme.campoTexto1.setText("");
        pnRegistrarme.campoCorreo1.setText("");
        pnRegistrarme.campoPassword1.setText("");
        pnRegistrarme.campoPassword2.setText("");
    }
        
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}


    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
