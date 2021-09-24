package controlador;

import index.Veontec;
import java.awt.Dialog;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import vista.paneles.PanelCardMiProducto;
import vista.paneles.PanelCrearProducto;
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
    private DefaultMutableTreeNode treeNode1;
    
    // Atributos
    private static CtrlMiTienda instancia;
    private List<ProductoDto> lstMisProductos;

    // Constructor
    public CtrlMiTienda(PanelMiTienda laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuario_dto = dto;
        this.usuario_dao = dao;
        producto_dao = new ProductoDao();
        producto_dto = new ProductoDto();
        
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
    
    // Eventos
    public void mtdEstablecerEventos(){
        this.laVista.btnCrearProducto.addMouseListener(this);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if( e.getSource() == laVista.btnCrearProducto ){
            mtdCrearProducto();
        }
    }
   
    // Métodos
    private void mtdInit(){
        laVista.pnContenedor.setLayout(new GridBagLayout());
        mtdEstablecerEventos();
        mtdMostrarProducto();
    }
    
    private void mtdCrearProducto(){
        
        // * Crear objetos
        PanelCrearProducto  pp = new PanelCrearProducto();
        modalCrearProducto = new JDialog(Veontec.ventanaHome);
        
        // * Establecer eventos
        modalCrearProducto.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                modalCrearProducto.removeAll();
                modalCrearProducto.setVisible(false);
                modalCrearProducto.dispose();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                pp.updateUI();
                modalCrearProducto.validate();
                modalCrearProducto.repaint();
                JOptionPane.showMessageDialog(laVista, "Introduce los datos del producto.");
            }
            
        });
        
        // * Establecer propiedades
        //modalCrearProducto.setModal(true);
        //modalCrearProducto.setType(Window.Type.UTILITY);
        modalCrearProducto.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modalCrearProducto.setTitle("Crear un nuevo producto");
        modalCrearProducto.setResizable(false);
        modalCrearProducto.setSize( pp.getSize() );
        modalCrearProducto.setPreferredSize(pp.getSize() );
        modalCrearProducto.setContentPane(pp);
        modalCrearProducto.setLocationRelativeTo(Veontec.ventanaHome);
        modalCrearProducto.setVisible(true); // Mostrar modal
        
        
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
            return;
        }
        
        for (int i = 0; i < totalProductos; i++) {
            CtrlCardMiProducto tarjeta = new CtrlCardMiProducto(lstMisProductos.get(i), producto_dao);
            tarjeta.setItem(i);
            tarjeta.mtdInit();
            laVista.pnContenedor.add(tarjeta.getLaVista(), tarjeta.getTarjeta_dimensiones());
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
