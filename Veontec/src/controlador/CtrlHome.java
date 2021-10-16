package controlador;

import index.Veontec;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import javax.swing.JOptionPane;
import modelo.dao.UsuarioDao;
import modelo.dto.UsuarioDto;
import src.Info;
import vista.ventanas.VentanaHome;

enum TabsHome {
        Inicio, MiTienda, Compras, Ventas, Preguntas, MiCuenta
}

public class CtrlHome implements MouseListener{
    
    // Vista
    public VentanaHome laVista;
    
    // Modelos
    private UsuarioDao dao;
    private UsuarioDto dto;
    
    // Atributos
    private int tabPreviaSeleccionada;
    private Integer estadoVeontec;
    private Integer estadoSuccessVeontec;
    private String titulo;

    public CtrlHome(VentanaHome laVista) {
        this.laVista = laVista;
        this.tabPreviaSeleccionada = 0;
        
        // * Establecer oyentes
        laVista.pnTabMenu.addMouseListener(this);
        titulo = Veontec.ventanaHome.getTitle();
        mtdCargarBienvenida();
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.Inicio.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Inicio.ordinal() ){
            
            mtdCargarBienvenida();
            //System.out.println("Estas en Inicio....");
            
            tabPreviaSeleccionada = TabsHome.Inicio.ordinal();
        }
        
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.MiTienda.ordinal() 
            && tabPreviaSeleccionada != TabsHome.MiTienda.ordinal() ){
            
            mtdCargarMiTienda();
            //System.out.println("Estas en MiTienda....");
            
            
            tabPreviaSeleccionada = TabsHome.MiTienda.ordinal();
        }
        
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.Compras.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Compras.ordinal() ){
            
            mtdCargarCompras();
            //System.out.println("Estas en Compras....");
            
            tabPreviaSeleccionada = TabsHome.Compras.ordinal();
        }
        
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.Ventas.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Ventas.ordinal() ){
            
            mtdCargarVentas();
            //System.out.println("Estas en Ventas....");
            
            tabPreviaSeleccionada = TabsHome.Ventas.ordinal();
        }
        
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.Preguntas.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Preguntas.ordinal() ){
            
            mtdCargarPreguntas();
            //System.out.println("Estas en Preguntas....");
            
            tabPreviaSeleccionada = TabsHome.Preguntas.ordinal();
        }
        
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.MiCuenta.ordinal() 
            && tabPreviaSeleccionada != TabsHome.MiCuenta.ordinal() ){
            
            mtdCargarMiCuenta();
            //System.out.println("Estas en MiCuenta....");
           
            tabPreviaSeleccionada = TabsHome.MiCuenta.ordinal();
        }
    }
    
    private void mtdCargarBienvenida(){
        
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlBienvenida ctrl = CtrlBienvenida.getInstancia(laVista.panelHome, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdCargarMiTienda(){
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlMiTienda ctrl = CtrlMiTienda.getInstancia(laVista.pnMiTienda, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdCargarMiCuenta(){
        mtdValidarAcceso();
        if(  estadoVeontec > 1 ){
            CtrlMiCuenta ctrl = CtrlMiCuenta.getInstancia(laVista.pnMiCuenta, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdCargarVentas(){
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlVentas ctrl = CtrlVentas.getInstancia(laVista.pnVentas, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdCargarCompras(){
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlCompras ctrl = CtrlCompras.getInstancia(laVista.pnCompras, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdCargarPreguntas(){
        mtdValidarAcceso();
        if(  estadoVeontec >= estadoSuccessVeontec ){
            CtrlPreguntas ctrl = CtrlPreguntas.getInstancia(laVista.panelPreguntas, Veontec.usuarioDto, Veontec.usuarioDao);
        }
        
    }
    
    private void mtdValidarAcceso(){
        estadoVeontec = mtdObtenerEstadoVeontec();
        switch(estadoVeontec){
            case 1 : JOptionPane.showMessageDialog(laVista, "Software Veontec, no hay conexión"); break;
            case 2 : Veontec.ventanaHome.setTitle( titulo + " (cuenta no verificada)"); break;
            case 3 : Veontec.ventanaHome.setTitle( titulo + " (cuenta recuperada)"); break;
            case -1 : estadoVeontec = 4; break;
            default: break;
        }
        estadoSuccessVeontec = 4;
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
    
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
