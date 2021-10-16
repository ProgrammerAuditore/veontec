package controlador.ventanas;

import controlador.CtrlHiloConexion;
import controlador.CtrlIniciarSesion;
import controlador.CtrlRegistrarme;
import index.Veontec;
import java.util.Objects;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.dao.CategoriaDao;
import modelo.dao.UsuarioDao;
import modelo.dto.CategoriaDto;
import modelo.dto.UsuarioDto;
import src.Info;
import vista.ventanas.VentanaMain;

enum TabsMain {
        SignOn, SignUp 
}

public class CtrlMain {
    
    // * Vista
    private VentanaMain laVista;
    
    // * Modelo
    private UsuarioDao usuarioDao;
    private UsuarioDto usuarioDto;
    private CategoriaDao categoriaDao;
    private CategoriaDto categoriaDto;
    
    // * Atributos
    private int tabPreviaSeleccionada;
    private Integer estadoVeontec;
    private Integer estadoSuccessVeontec;
    private String titulo;
    private static final Logger LOG = Logger.getLogger(CtrlMain.class.getName());
    
    
    // * Constructor
    public CtrlMain(VentanaMain laVista) {
        this.laVista = laVista;
        this.usuarioDao = new UsuarioDao();
        this.usuarioDto = new UsuarioDto();
        this.categoriaDao = new CategoriaDao();
        this.categoriaDto = new CategoriaDto();
    }
    
    // * Eventos
    private void mtdEventoPnTabMenu(){
        laVista.pnTabMenus.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                mtdCargarTabSeleccionado();
            }

           
        });
    }
    
    // * Métodos
    public void mtdInit(){
        LOG.info("Ejecutando metodo una vez (Obligatorio)");
        laVista.setTitle(Info.NombreSoftware);
        mtdEventoPnTabMenu();
        mtdCargarSingOn();
        mtdCargarSingUp();
        
        laVista.setLocationRelativeTo(null);
        laVista.setVisible(true);
    }
    
    private void mtdCargarSingOn(){
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlIniciarSesion ctrl = CtrlIniciarSesion.getInstancia(laVista.pnLoggin);
        }
    }
    
    private void mtdCargarSingUp(){
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlRegistrarme ctrl = CtrlRegistrarme.getInstancia(laVista.pnRegistrarme);
        }
    }
    
    private void mtdValidarAcceso(){
        estadoVeontec = mtdObtenerEstadoVeontec();
        estadoSuccessVeontec = 4;
        switch(estadoVeontec){
            case 1 : JOptionPane.showMessageDialog(laVista, "Software Veontec, no hay conexión"); break;
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
            if(Objects.equals(Veontec.usuarioDto.getCmpEstado(), Info.veontecCuentaNoVerificada)){
                return 2;
            }
            
            // * Verificar si la cuenta no está en modo recuperada
            if(Objects.equals(Veontec.usuarioDto.getCmpEstado(), Info.veontecRecuperarCuenta)){
                return 3;
            }
            
        } catch (Exception e) {}
        
        return -1;
    }
    
    private void mtdCargarTabSeleccionado() {
        if( laVista.pnTabMenus.getSelectedIndex() == TabsMain.SignOn.ordinal() 
            && tabPreviaSeleccionada != TabsMain.SignOn.ordinal() ){
            
            mtdCargarSingOn();
            tabPreviaSeleccionada = TabsMain.SignOn.ordinal();
            
        }
        
        if( laVista.pnTabMenus.getSelectedIndex() == TabsMain.SignUp.ordinal() 
            && tabPreviaSeleccionada != TabsMain.SignUp.ordinal() ){
            
            mtdCargarSingUp();
            tabPreviaSeleccionada = TabsMain.SignUp.ordinal();
        
        }
    }
    
}
