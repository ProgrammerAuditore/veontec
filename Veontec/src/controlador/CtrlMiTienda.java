package controlador;

import controlador.acciones.CtrlModalCrearProducto;
import controlador.componentes.CtrlCardMiProducto;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.tree.DefaultMutableTreeNode;
import modelo.dao.ImagesDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.ImagesDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import vista.paneles.acciones.PanelCrearProducto;
import vista.paneles.PanelMiTienda;

public class CtrlMiTienda implements MouseListener{
    
    // Vista
    public PanelMiTienda laVista;
    public JDialog modalCrearProducto;
    
    // Modelos
    private UsuarioDto usuario_dto;
    private UsuarioDao usuario_dao;
    private ProductoDao producto_dao;
    private ProductoDto producto_dto;
    private ImagesDao images_dao;
    private ImagesDto images_dto;
    private DefaultMutableTreeNode treeNode1;
    
    // Atributos
    private static CtrlMiTienda instancia;
    private List<ProductoDto> lstMisProductos;
    private PanelCrearProducto pnCrearProducto;

    // Constructor
    public CtrlMiTienda(PanelMiTienda laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuario_dto = dto;
        this.usuario_dao = dao;
        producto_dao = new ProductoDao();
        producto_dto = new ProductoDto();
        images_dao = new ImagesDao();
        images_dto = new ImagesDto();
        
    }
    
    // Obtener instancia | Singleton
    public static CtrlMiTienda getInstancia(PanelMiTienda laVista, UsuarioDto dto, UsuarioDao dao){
        if( instancia == null ){
            instancia = new CtrlMiTienda(laVista, dto, dao);
            instancia.mtdInit();
        }
        
        instancia.mtdRecargarDatos();
        instancia.mtdMostrarProducto();
        return instancia;
    }
    
    public static boolean mtdRecargarMisProductos(){
        instancia.mtdRecargarDatos();
        instancia.mtdMostrarProducto();
        return true;
    }
    
    // Eventos
    @Override
    public void mouseReleased(MouseEvent e) {
        if( e.getSource() == laVista.btnCrearProducto ){
            CtrlModalCrearProducto crear = new CtrlModalCrearProducto();
            crear.mtdInit();
        }
    }
   
    // Métodos
    private void mtdInit(){
        laVista.pnContenedor.setLayout(new GridBagLayout());
        images_dto.setImagUsuario( usuario_dto.getCmpID() );
        this.laVista.btnCrearProducto.addMouseListener(this);
        mtdMostrarProducto();
    }
    
    private void mtdMostrarProducto(){
        //lstMisProductos.clear();
        laVista.pnContenedor.removeAll();
        laVista.pnContenedor.setLayout(new GridBagLayout());
        
        producto_dto.setProdUsuario( usuario_dto.getCmpID() );
        lstMisProductos = producto_dao.mtdListar(producto_dto);
        int totalProductos = lstMisProductos.size();
        
        if( totalProductos == 0 ){
            System.out.println(" No hay producto para mostrar. ");
        
        } else {
            for (int i = 0; i < totalProductos; i++) {
                CtrlCardMiProducto tarjeta = new CtrlCardMiProducto(lstMisProductos.get(i), producto_dao);
                tarjeta.setItem(i);
                tarjeta.mtdInit();
                laVista.pnContenedor.add(tarjeta.getLaVista(), tarjeta.getTarjeta_dimensiones());
            }
            
        }
        
        laVista.pnContenedor.validate();
        laVista.pnContenedor.revalidate();
        laVista.pnContenedor.repaint();
        
    }
    
    public void mtdRecargarDatos(){
        treeNode1 = new DefaultMutableTreeNode("Categorias");
        laVista.lstProductos.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
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
