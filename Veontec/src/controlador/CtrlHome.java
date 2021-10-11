package controlador;

import index.Veontec;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
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
        try {
            
            if(CtrlHiloConexion.ctrlEstado == true){
                CtrlBienvenida ctrl = CtrlBienvenida.getInstancia(laVista.panelHome, Veontec.usuarioDto, Veontec.usuarioDao);
            }else{
                JOptionPane.showMessageDialog(laVista, "No hay conexión");
            }
        
        } catch (Exception e) {}
    }
    
    private void mtdCargarMiTienda(){
        try {
            
            if(CtrlHiloConexion.ctrlEstado == true){
                CtrlMiTienda ctrl = CtrlMiTienda.getInstancia(laVista.pnMiTienda, Veontec.usuarioDto, Veontec.usuarioDao);
            }else{
                JOptionPane.showMessageDialog(laVista, "No hay conexión");
            }
        
        } catch (Exception e) {}
    }
    
    private void mtdCargarMiCuenta(){
        try {
            
            if(CtrlHiloConexion.ctrlEstado == true){
                CtrlMiCuenta ctrl = CtrlMiCuenta.getInstancia(laVista.pnMiCuenta, Veontec.usuarioDto, Veontec.usuarioDao);
            }else{
                JOptionPane.showMessageDialog(laVista, "No hay conexión");
            }
        
        } catch (Exception e) {}
    }
    
    private void mtdCargarVentas(){
        try {
            
            if(CtrlHiloConexion.ctrlEstado == true){
                 CtrlVentas ctrl = CtrlVentas.getInstancia(laVista.pnVentas, Veontec.usuarioDto, Veontec.usuarioDao);
            }else{
                JOptionPane.showMessageDialog(laVista, "No hay conexión");
            }
        
        } catch (Exception e) {}
    }
    
    private void mtdCargarCompras(){
        try {
            
            if(CtrlHiloConexion.ctrlEstado == true){
                CtrlCompras ctrl = CtrlCompras.getInstancia(laVista.pnCompras, Veontec.usuarioDto, Veontec.usuarioDao);
            }else{
                JOptionPane.showMessageDialog(laVista, "No hay conexión");
            }
        
        } catch (Exception e) {}
    }
    
    private void mtdCargarPreguntas(){
        try {
            
            if(CtrlHiloConexion.ctrlEstado == true){
                CtrlPreguntas ctrl = CtrlPreguntas.getInstancia(laVista.panelPreguntas, Veontec.usuarioDto, Veontec.usuarioDao);
            }else{
                JOptionPane.showMessageDialog(laVista, "No hay conexión");
            }
        
        } catch (Exception e) {}
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
