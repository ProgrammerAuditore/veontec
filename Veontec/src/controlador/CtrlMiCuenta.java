package controlador;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import vista.paneles.PanelMiCuenta;

public class CtrlMiCuenta implements MouseListener{
    
    // Vista
    public PanelMiCuenta laVista;
    
    // Modelos
    private UsuarioDto dto;
    private UsuarioDao dao;
    
    // Atributos
    private static CtrlMiCuenta instancia;
    
    // Constructor
    private CtrlMiCuenta(PanelMiCuenta laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.dto = dto;
        this.dao = dao;
    }
    
    // Obtener instancia | Singleton 
    public static CtrlMiCuenta getInstancia(PanelMiCuenta laVista, UsuarioDto dto, UsuarioDao dao){
        if( instancia == null ){
            instancia = new CtrlMiCuenta(laVista, dto, dao);
            instancia.mtdInit();
        }
        
        return instancia;
    }
    
    // Eventos
    public void mtdEstablecerEventos(){
        laVista.btnCerrarSession.addMouseListener(this);
        laVista.btnGuardar.addMouseListener(this);
        laVista.btnEliminarCuenta.addMouseListener(this);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if( e.getSource() == laVista.btnCerrarSession ){
            mtdCerrarSessionActual();
        }
        
        if( e.getSource() == laVista.btnEliminarCuenta ){
            mtdEliminarCuenta();
        }
        
        if( e.getSource() == laVista.btnGuardar ){
            mtdGuardarDatos();
        }
    }
    
    // Métodos
    public void  mtdInit(){
        mtdEstablecerEventos();
        mtdEstablecerDatos();
    }
    
    private void mtdEstablecerDatos(){
        laVista.campoCorreo1.setText( dto.getCmpCorreo() );
        laVista.campoPassword1.setText( "**********" );
        laVista.campoTexto1.setText( dto.getCmpNombreCompleto() );
    }
    
    private void mtdCerrarSessionActual(){
        System.exit(0);
    }
    
    private void mtdEliminarCuenta(){
        
    }
    
    private void mtdGuardarDatos(){
        System.out.println("** Guardar datos .... \n");
    }
    
    private void mtdActualizarDatos(){
        
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
