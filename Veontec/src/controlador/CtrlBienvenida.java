package controlador;

import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.tree.DefaultMutableTreeNode;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import vista.paneles.PanelHome;

public class CtrlBienvenida{
    
    // Vista
    public PanelHome laVista;
    public JDialog modalCrearProducto;
    
    // Modelos
    private UsuarioDto usuario_dto;
    private UsuarioDao usuario_dao;
    private ProductoDao producto_dao;
    private ProductoDto producto_dto;
    private DefaultMutableTreeNode treeNode1;
    
    // Atributos
    private static CtrlBienvenida instancia;
    private List<ProductoDto> lstMisProductos;

    // Constructor
    public CtrlBienvenida(PanelHome laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuario_dto = dto;
        this.usuario_dao = dao;
        producto_dao = new ProductoDao();
        producto_dto = new ProductoDto();
        
    }
    
    // Obtener instancia | Singleton
    public static CtrlBienvenida getInstancia(PanelHome laVista, UsuarioDto dto, UsuarioDao dao){
        if( instancia == null ){
            instancia = new CtrlBienvenida(laVista, dto, dao);
            instancia.mtdInit();
        }
        
        instancia.mtdMostrarProducto();
        return instancia;
    }
    
    // Eventos
    // Vacio
    
    // Métodos
    private void mtdInit(){
    }
    
    
    private void mtdMostrarProducto(){
        //lstMisProductos.clear();
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        lstMisProductos = producto_dao.mtdListar();
        int totalProductos = lstMisProductos.size();
        
        if( totalProductos == 0 ){
            System.out.println(" No hay producto para mostrar. ");
            return;
        }
        
        for (int i = 0; i < totalProductos; i++) {
            CtrlCardProducto tarjeta = new CtrlCardProducto(lstMisProductos.get(i), producto_dao);
            tarjeta.setItem(i);
            tarjeta.mtdInit();
            laVista.pnContenedor.add(tarjeta.getLaVista(), tarjeta.getTarjeta_dimensiones());
        }
        
        laVista.pnContenedor.validate();
        laVista.pnContenedor.revalidate();
        laVista.pnContenedor.repaint();
        
    }
    
    
    
}
