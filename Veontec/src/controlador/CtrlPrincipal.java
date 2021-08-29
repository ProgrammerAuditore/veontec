package controlador;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import modelo.dao.UsuarioDao;
import modelo.dto.UsuarioDto;
import vista.ventanas.VentanaHome;

public class CtrlPrincipal implements MouseListener{
    
    // Vista
    public VentanaHome laVista;
    
    // Modelos
    private UsuarioDao dao;
    private UsuarioDto dto;

    public CtrlPrincipal(VentanaHome laVista, UsuarioDao dao, UsuarioDto dto) {
        this.laVista = laVista;
        this.dao = dao;
        this.dto = dto;
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        
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
