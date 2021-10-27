package controlador.ventanas;

import controlador.CtrlHiloConexion;
import index.Veontec;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import modelo.ObjEmail;
import modelo.dao.CategoriaDao;
import modelo.dao.CuentaDao;
import modelo.dao.UsuarioDao;
import modelo.dto.CategoriaDto;
import modelo.dto.CuentaDto;
import modelo.dto.UsuarioDto;
import src.FncGlobales;
import src.Software;
import vista.componentes.campos.CampoCorreo;
import vista.paneles.PanelIniciarSesion;
import vista.ventanas.VentanaHome;

public class CtrlIniciarSesion {
    
    // ***** Vista
    private PanelIniciarSesion pnIniciarSesion;
    
    // ***** Modelo
    private UsuarioDao usuarioDao;
    private UsuarioDto usuarioDto;
    private CategoriaDao categoriaDao;
    private CategoriaDto categoriaDto;
    
    // ***** Atributos
    private static CtrlIniciarSesion instancia;
    private static final Logger LOG = Logger.getLogger(CtrlIniciarSesion.class.getName());
    
    
    // ***** Constructor
    private CtrlIniciarSesion(PanelIniciarSesion pnSignOn) {
        this.pnIniciarSesion = pnSignOn;
        this.usuarioDao = new UsuarioDao();
        this.usuarioDto = new UsuarioDto();
        this.categoriaDao = new CategoriaDao();
        this.categoriaDto = new CategoriaDto();
    }
    
    // ***** Eventos
    private void mtdEventoBtnIniciarSesion(){
        pnIniciarSesion.btnIniciarSession.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdIniciarSession();
            }
        });
    }
    
    private void mtdEventoBtnRecuperarCuenta(){
        pnIniciarSesion.btnRecuperarCuenta.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdRecuperarCuenta();
            }
        });
    }
    
    // ***** Métodos
    public static CtrlIniciarSesion getInstancia(PanelIniciarSesion pnSignOn){
        if( instancia ==  null){
            instancia = new CtrlIniciarSesion(pnSignOn);
            instancia.mtdInit();
        }else{
            
        }
        
        return instancia;
    }
    
    // ***** Métodos
    public void mtdInit(){
        LOG.info("Ejecutando metodo una vez (Obligatorio)");
        mtdEventoBtnIniciarSesion();
        mtdEventoBtnRecuperarCuenta();
        
    }
    
    private void mtdRecuperarCuenta(){
        String correoActual="";
        Box boxVRecuperarCuenta = Box.createVerticalBox();
        JLabel info1 = new JLabel("Introduzca su correo eléctronico");
        boxVRecuperarCuenta.add(info1);
        CampoCorreo cmpCampoCorreo = new CampoCorreo();
        boxVRecuperarCuenta.add(cmpCampoCorreo);
        
        boxVRecuperarCuenta.setLocation(Veontec.ventanaSession.getLocation());
        int opc = JOptionPane.showConfirmDialog(Veontec.ventanaSession, boxVRecuperarCuenta, "Recuperar cuenta", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
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
                    JOptionPane.showMessageDialog(Veontec.ventanaSession, "No existe una cuenta con el correo introducido.");
                }else if( Veontec.usuarioDto.getCmpCorreo() == null || Veontec.usuarioDto.getCmpPassword() == null ){
                    JOptionPane.showMessageDialog(Veontec.ventanaSession, "No existe una cuenta con el correo introducido.");
                }else{
                    
                    if( Veontec.usuarioDto.getCmpEstado().equals(333) ){
                        JOptionPane.showMessageDialog(null, "La cuenta no está verificada.");
                        return;
                    }
                    
                    if(ObjEmail.mtdEnviarRecuperarCuenta(Veontec.usuarioDto)){
                        Veontec.usuarioDto.setCmpPassword(new FncGlobales().mtdObtenerPasswordEncriptado(Veontec.usuarioDto.getCmpKey().toCharArray()) );
                        if(Veontec.usuarioDao.mtdActualizar(Veontec.usuarioDto)){
                            
                            JOptionPane.showMessageDialog(Veontec.ventanaSession, "Cuenta recuperada exitosamente.\nRevise su correo par obtener su codigo.");
                            
                        }
                    }
                }
                
            }
        }
    }
    
    private void mtdIniciarSession(){
        if( CtrlHiloConexion.ctrlEstado ){
            // * Verificar los campos de registrarme
            if( mtdObtenerCamposIncorrectos_IniciarSession() ){
                return;
            }

            // * Obtener cuenta de usuarioDto si existe
            if( mtdObtenerUsuario(pnIniciarSesion.campoCorreo1.getText().trim()) ){

                // * Verificar datos capturado son correctos
                if( mtdValidarDatosDeUsuario(pnIniciarSesion.campoCorreo1.getText().trim(), 
                String.valueOf(pnIniciarSesion.campoPassword1.getPassword())) ){

                    // * Abrir ventana home
                    mtdAbrirVentanaHome();

                }else
                JOptionPane.showMessageDialog(Veontec.ventanaSession, "Verifique que los datos introducido sean correctos.");

            }
            
        }else
        JOptionPane.showMessageDialog(Veontec.ventanaSession, "No hay conexión");
        
    }
    
    public void mtdAbrirVentanaHome(){
        if( Veontec.ventanaHome == null ){
            // * Instanciar objetos para el cuenta actual
            Veontec.ventanaHome = new VentanaHome();
            Veontec.ventanaHome.setTitle( Veontec.usuarioDto.getCmpNombreCompleto() 
                    + " | "  + Veontec.usuarioDto.getCmpCorreo() 
                    + " - " + Software.NombreSoftware );

            mtdGuardarCuenta();

            // * Crear controlador y mostrar la ventana principal
            CtrlHome ctrl = new CtrlHome(Veontec.ventanaHome);
            ctrl.mtdInit();
            ctrl.ventanaHome.setLocationRelativeTo(null);
            ctrl.ventanaHome.setVisible(true);

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
                JOptionPane.showMessageDialog(Veontec.ventanaSession, "No hay cuenta asociado con el correo introducido.");
                return false;
        }
        
        return true;
    }
    
    public boolean mtdValidarDatosDeUsuario(String correo, String passwd){
        return correo.equals(Veontec.usuarioDto.getCmpCorreo()) && 
                new FncGlobales().mtdComprobarPassword(Veontec.usuarioDto.getCmpPassword(), passwd.toCharArray());
    }
    
    private void mtdGuardarCuenta(){
        if( Veontec.cuentaDto == null ){
            // * Registrar los datos del usuarioDto
            Veontec.cuentaDto = new CuentaDto();
            Veontec.cuentaDao = new CuentaDao(); 
            Veontec.cuentaDto.setCorreo(pnIniciarSesion.campoCorreo1.getText().trim());
            Veontec.cuentaDto.setPasswd(String.valueOf(pnIniciarSesion.campoPassword1.getPassword()).trim());
            Veontec.cuentaDao.regitrar_datos(Veontec.cuentaDto);
            Veontec.cuentaDto = Veontec.cuentaDao.obtener_datos();
            System.out.println("Cuenta : " + Veontec.cuentaDto.getCorreo());
            System.out.println("Cuenta : " + Veontec.cuentaDto.getPasswd());
        }
    }
    
    private boolean mtdObtenerCamposIncorrectos_IniciarSession(){
    int campos_incorrectos = 0;
    String msg = "Verifica los siguientes datos: \n";
        
        if( pnIniciarSesion.campoCorreo1.getText().trim().isEmpty() 
                || !pnIniciarSesion.campoCorreo1.isAprobado()  ){
            campos_incorrectos++;
            msg += "El campo correo es incorrecto. \n";
        }
        
        if( String.valueOf(pnIniciarSesion.campoPassword1.getPassword()).trim().isEmpty() ){
            campos_incorrectos++;
            msg += "El campo contraseña está vacio. \n";
        }
    
        if( campos_incorrectos > 0 )
                JOptionPane.showMessageDialog(Veontec.ventanaSession, msg);

        //System.out.println("Campos incorrectos = " + campos_incorrectos);
        return ( campos_incorrectos > 0 );
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
