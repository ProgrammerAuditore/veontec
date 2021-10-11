package controlador;

import index.Veontec;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import modelo.dao.UsuarioDao;
import modelo.dto.UsuarioDto;
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
    private int tabPreviaSeleccionada;

    public CtrlHome(VentanaHome laVista) {
        this.laVista = laVista;
        this.tabPreviaSeleccionada = 0;
        
        // * Establecer oyentes
        laVista.pnTabMenu.addMouseListener(this);
        mtdCargarBienvenida();
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.Inicio.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Inicio.ordinal() ){
            
            mtdCargarBienvenida();
            System.out.println("Estas en Inicio....");
            
            tabPreviaSeleccionada = TabsHome.Inicio.ordinal();
        }
        
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.MiTienda.ordinal() 
            && tabPreviaSeleccionada != TabsHome.MiTienda.ordinal() ){
            
            mtdCargarMiTienda();
            System.out.println("Estas en MiTienda....");
            
            
            tabPreviaSeleccionada = TabsHome.MiTienda.ordinal();
        }
        
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.Compras.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Compras.ordinal() ){
            
            mtdCargarCompras();
            System.out.println("Estas en Compras....");
            
            tabPreviaSeleccionada = TabsHome.Compras.ordinal();
        }
        
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.Ventas.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Ventas.ordinal() ){
            
            mtdCargarVentas();
            System.out.println("Estas en Ventas....");
            
            tabPreviaSeleccionada = TabsHome.Ventas.ordinal();
        }
        
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.Preguntas.ordinal() 
            && tabPreviaSeleccionada != TabsHome.Preguntas.ordinal() ){
            
            mtdCargarPreguntas();
            System.out.println("Estas en Preguntas....");
            
            tabPreviaSeleccionada = TabsHome.Preguntas.ordinal();
        }
        
        if( laVista.pnTabMenu.getSelectedIndex() == TabsHome.MiCuenta.ordinal() 
            && tabPreviaSeleccionada != TabsHome.MiCuenta.ordinal() ){
            
            System.out.println("Estas en MiCuenta....");
            mtdCargarMiCuenta();
           
            tabPreviaSeleccionada = TabsHome.MiCuenta.ordinal();
        }
    }
    
    private void mtdCargarBienvenida(){
        CtrlBienvenida ctrl = CtrlBienvenida.getInstancia(laVista.panelHome, Veontec.usuarioDto, Veontec.usuarioDao);

    }
    
    private void mtdCargarMiTienda(){
        CtrlMiTienda ctrl = CtrlMiTienda.getInstancia(laVista.pnMiTienda, Veontec.usuarioDto, Veontec.usuarioDao);

    }
    
    private void mtdCargarMiCuenta(){
        CtrlMiCuenta ctrl = CtrlMiCuenta.getInstancia(laVista.pnMiCuenta, Veontec.usuarioDto, Veontec.usuarioDao);
        
    }
    
    private void mtdCargarVentas(){
        CtrlVenta ctrl = CtrlVenta.getInstancia(laVista.pnVentas, Veontec.usuarioDto, Veontec.usuarioDao);
        
    }
    
    private void mtdCargarCompras(){
        CtrlCompras ctrl = CtrlCompras.getInstancia(laVista.pnCompras, Veontec.usuarioDto, Veontec.usuarioDao);
        
    }
    
    private void mtdCargarPreguntas(){
        CtrlPreguntas ctrl = CtrlPreguntas.getInstancia(laVista.panelPreguntas, Veontec.usuarioDto, Veontec.usuarioDao);
        
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
