package controlador.ventanas;

import controlador.CtrlHiloConexion;
import index.Veontec;
import java.util.Objects;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.dao.CategoriaDao;
import modelo.dao.CuentaDao;
import modelo.dao.UsuarioDao;
import modelo.dto.CategoriaDto;
import modelo.dto.CuentaDto;
import modelo.dto.UsuarioDto;
import src.Software;
import src.Recursos;
import vista.ventanas.VentanaMain;

enum TabsMain {
        SignOn, SignUp 
}

public class CtrlMain {
    
    // ***** Vista
    private VentanaMain ventanaMain;
    
    // ***** Modelo
    private UsuarioDao usuarioDao;
    private UsuarioDto usuarioDto;
    private CategoriaDao categoriaDao;
    private CategoriaDto categoriaDto;
    private CuentaDao cuentaDao;
    private CuentaDto cuentaDto;
    
    // ***** Atributos
    private int tabPreviaSeleccionada;
    private Integer estadoVeontec;
    private Integer estadoSuccessVeontec;
    private String titulo;
    private CtrlIniciarSesion ctrlIniciarSession = null;
    private static final Logger LOG = Logger.getLogger(CtrlMain.class.getName());
    
    
    // ***** Constructor
    public CtrlMain(VentanaMain laVista) {
        this.ventanaMain = laVista;
        this.usuarioDao = new UsuarioDao();
        this.usuarioDto = new UsuarioDto();
        this.categoriaDao = new CategoriaDao();
        this.categoriaDto = new CategoriaDto();
        this.cuentaDao = new CuentaDao();
        this.cuentaDto = new CuentaDto();
    }
    
    // ***** Eventos
    private void mtdEventoPnTabMenu(){
        ventanaMain.pnTabMenus.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                mtdCargarTabSeleccionado();
            }

           
        });
    }
    
    // ***** Métodos
    private void mtdInit(){
        LOG.info("Ejecutando metodo una vez (Obligatorio)");
        ventanaMain.setTitle(Software.NombreSoftware);
        mtdEventoPnTabMenu();
        mtdCargarSingOn(false);
        mtdCargarSingUp();
        
        ventanaMain.setLocationRelativeTo(null);
        ventanaMain.setVisible(true);
    }
    
    public void mtdInitLoggin(){
        LOG.info("Ejecutando metodo una vez (Obligatorio)");
        
        // * Verificar si existe los credenciales en .dta
        if( Recursos.dataCuenta().exists() ){
            cuentaDto = cuentaDao.obtener_datos();
            
            // * Verificar si los credenciales en .dta son incorrectos
            if( cuentaDto == null ){
                // * Si no se elimina el archivo
                // y se abreve la ventana de SingUp
                Recursos.dataCuenta().delete();
                mtdInit();
                
            }else{
                // * Ejecutar auto loggin
                mtdCargarSingOn(true);
            
            }
            
        }
        
    }
    
    private void mtdIniciarSessionAutoLoggin(){
        ctrlIniciarSession = CtrlIniciarSesion.getInstancia(ventanaMain.pnLoggin);
            
            // * Verificar si existe el usuario de los credenciales en .dta 
            if(ctrlIniciarSession.mtdObtenerUsuario(cuentaDto.getCorreo())){
                
                // * Verificar si el usuario de los credenciales en .dta son validos
                if( ctrlIniciarSession.mtdValidarDatosDeUsuario(cuentaDto.getCorreo(), cuentaDto.getPasswd()) ){
                    Veontec.cuentaDao = cuentaDao;
                    Veontec.cuentaDto = cuentaDto;
                    ctrlIniciarSession.mtdAbrirVentanaHome();
                }
                
            }else{
                
                // * Si no se elimina el archivo
                // y se abreve la ventana de SingUp
                Recursos.dataCuenta().delete();
                mtdInit();
            
            }
    }
    
    private void mtdCargarSingOn(boolean autologgin){
        mtdValidarAcceso();
        
        if( autologgin == false ){
            // * Cargar la pestaña de iniciar sesion
            if(  estadoVeontec >= estadoSuccessVeontec ){
                ctrlIniciarSession = CtrlIniciarSesion.getInstancia(ventanaMain.pnLoggin);
            }
            
        }else{
            // * Ejecutar auto loggin
            mtdIniciarSessionAutoLoggin();
            
        }
    }
    
    private void mtdCargarSingUp(){
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlRegistrarme ctrl = CtrlRegistrarme.getInstancia(ventanaMain.pnRegistrarme);
        }
    }
    
    private void mtdValidarAcceso(){
        estadoVeontec = mtdObtenerEstadoVeontec();
        estadoSuccessVeontec = 4;
        switch(estadoVeontec){
            case 1 : JOptionPane.showMessageDialog(ventanaMain, "Software Veontec, no hay conexión"); break;
            case 2 : Veontec.ventanaHome.setTitle( titulo + " (cuenta no verificada)"); break;
            case 3 : Veontec.ventanaHome.setTitle( titulo + " (cuenta recuperada)"); break;
            case -1 : estadoVeontec = estadoSuccessVeontec; break;
            default: break;
        }
    }
    
    private Integer mtdObtenerEstadoVeontec(){
        try {
            // * Verificar no hay conexión   
            if(CtrlHiloConexion.ctrlEstado == false){
                return 1;
            } 
             
            // * Verificar si la cuenta no está verificada
            if(Objects.equals(Veontec.usuarioDto.getCmpEstado(), Software.veontecCuentaNoVerificada)){
                return 2;
            }
            
            // * Verificar si la cuenta no está en modo recuperada
            if(Objects.equals(Veontec.usuarioDto.getCmpEstado(), Software.veontecRecuperarCuenta)){
                return 3;
            }
            
        } catch (Exception e) {}
        
        return -1;
    }
    
    private void mtdCargarTabSeleccionado() {
        if( ventanaMain.pnTabMenus.getSelectedIndex() == TabsMain.SignOn.ordinal() 
            && tabPreviaSeleccionada != TabsMain.SignOn.ordinal() ){
            
            mtdCargarSingOn(false);
            tabPreviaSeleccionada = TabsMain.SignOn.ordinal();
            
        }
        
        if( ventanaMain.pnTabMenus.getSelectedIndex() == TabsMain.SignUp.ordinal() 
            && tabPreviaSeleccionada != TabsMain.SignUp.ordinal() ){
            
            mtdCargarSingUp();
            tabPreviaSeleccionada = TabsMain.SignUp.ordinal();
        
        }
    }
    
}
