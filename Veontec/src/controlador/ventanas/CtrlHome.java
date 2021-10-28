package controlador.ventanas;

import controlador.tabs.CtrlBienvenida;
import controlador.tabs.CtrlCompras;
import controlador.CtrlHiloConexion;
import controlador.tabs.CtrlMiCuenta;
import controlador.tabs.CtrlMiTienda;
import controlador.tabs.CtrlPreguntas;
import controlador.tabs.CtrlVentas;
import index.Veontec;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import src.Software;
import vista.ventanas.VentanaHome;

enum TabsHome {
        Inicio, MiTienda, Compras, Ventas, Preguntas, MiCuenta
}

public class CtrlHome{
    
    // ***** Vista
    public VentanaHome ventanaHome;
    
    // ***** Modelos
    // Vacío
    
    // ***** Atributos
    private int tabPreviaSeleccionada;
    private Integer estadoVeontec;
    private Integer estadoSuccessVeontec;
    private String titulo;

    // ***** Constructor
    public CtrlHome(VentanaHome laVista) {
        this.ventanaHome = laVista;
        this.tabPreviaSeleccionada = 0;
        
    }
    
    // ***** Eventos
    private void mtdEventoPnTabMenu(){
        ventanaHome.pnTabMenu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                mtdCargarTabSeleccionado();
            }
        });
    }
    
    // ***** Métodos
    public void mtdInit(){
        
        // * Establecer oyentes
        mtdEventoPnTabMenu();
        mtdCargarBienvenida();
        titulo = Veontec.ventanaHome.getTitle();
        
    }
    
    private void mtdCargarBienvenida(){
        
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlBienvenida ctrl = CtrlBienvenida.getInstancia(ventanaHome.panelHome, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdCargarMiTienda(){
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlMiTienda ctrl = CtrlMiTienda.getInstancia(ventanaHome.pnMiTienda, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdCargarMiCuenta(){
        mtdValidarAcceso();
        if(  estadoVeontec > 1 ){
            CtrlMiCuenta ctrl = CtrlMiCuenta.getInstancia(ventanaHome.pnMiCuenta, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdCargarVentas(){
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlVentas ctrl = CtrlVentas.getInstancia(ventanaHome.pnVentas, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdCargarCompras(){
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlCompras ctrl = CtrlCompras.getInstancia(ventanaHome.pnCompras, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdCargarPreguntas(){
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlPreguntas ctrl = CtrlPreguntas.getInstancia(ventanaHome.panelPreguntas, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdValidarAcceso(){
        estadoVeontec = mtdObtenerEstadoVeontec();
        estadoSuccessVeontec = 4;
        switch(estadoVeontec){
            case 1 : JOptionPane.showMessageDialog(ventanaHome, "Software Veontec, no hay conexión"); break;
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
    
    private void mtdCargarTabSeleccionado(){
        if( ventanaHome.pnTabMenu.getSelectedIndex() == TabsHome.Inicio.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Inicio.ordinal() ){
            
            mtdCargarBienvenida();
            tabPreviaSeleccionada = TabsHome.Inicio.ordinal();
            
        }
        
        if( ventanaHome.pnTabMenu.getSelectedIndex() == TabsHome.MiTienda.ordinal() 
            && tabPreviaSeleccionada != TabsHome.MiTienda.ordinal() ){
            
            mtdCargarMiTienda();
            tabPreviaSeleccionada = TabsHome.MiTienda.ordinal();
        }
        
        if( ventanaHome.pnTabMenu.getSelectedIndex() == TabsHome.Compras.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Compras.ordinal() ){
            
            mtdCargarCompras();
            tabPreviaSeleccionada = TabsHome.Compras.ordinal();
        }
        
        if( ventanaHome.pnTabMenu.getSelectedIndex() == TabsHome.Ventas.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Ventas.ordinal() ){
            
            mtdCargarVentas();
            tabPreviaSeleccionada = TabsHome.Ventas.ordinal();
        }
        
        if( ventanaHome.pnTabMenu.getSelectedIndex() == TabsHome.Preguntas.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Preguntas.ordinal() ){
            
            mtdCargarPreguntas();
            tabPreviaSeleccionada = TabsHome.Preguntas.ordinal();
        }
        
        if( ventanaHome.pnTabMenu.getSelectedIndex() == TabsHome.MiCuenta.ordinal() 
            && tabPreviaSeleccionada != TabsHome.MiCuenta.ordinal() ){
            
            mtdCargarMiCuenta();
            tabPreviaSeleccionada = TabsHome.MiCuenta.ordinal();
        }
    }
    
}
