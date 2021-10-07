package controlador;

import controlador.componentes.CtrlCardMiProducto;
import index.Veontec;
import java.awt.Dialog;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
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
        //if( instancia == null ){
            //instancia.mtdInit();
        //}
        
        instancia.mtdRecargarDatos();
        instancia.mtdMostrarProducto();
        return true;
    }
    
    // Eventos
    public void mtdEstablecerEventos(){
        this.laVista.btnCrearProducto.addMouseListener(this);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if( e.getSource() == laVista.btnCrearProducto ){
            mtdBuildModalCrearProducto();
        }
    }
   
    // Métodos
    private void mtdInit(){
        laVista.pnContenedor.setLayout(new GridBagLayout());
        images_dto.setImagUsuario( usuario_dto.getCmpID() );
        mtdEstablecerEventos();
        mtdMostrarProducto();
    }
    
    private void mtdBuildModalCrearProducto(){
        
        // * Crear objetos
        pnCrearProducto = null;
        pnCrearProducto = new PanelCrearProducto();
        modalCrearProducto = new JDialog(Veontec.ventanaHome);
        
        mtdCrearEventoBtnSeleccionarImg();
        mtdCrearEventoBtnCrear();
        mtdCrearEventoBtnCancelar();
        
        // * Establecer eventos
        modalCrearProducto.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mtdSalirDelModal();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                pnCrearProducto.updateUI();
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
        modalCrearProducto.setSize(pnCrearProducto.getSize() );
        modalCrearProducto.setPreferredSize(pnCrearProducto.getSize() );
        modalCrearProducto.setContentPane(pnCrearProducto);
        modalCrearProducto.setLocationRelativeTo(Veontec.ventanaHome);
        modalCrearProducto.validate();
        pnCrearProducto.updateUI();
        modalCrearProducto.repaint();
        modalCrearProducto.setVisible(true); // Mostrar modal
        
        
    }
    
    private void mtdCrearEventoBtnCrear(){
        MouseListener eventoBtnCrear = null;
        pnCrearProducto.btnAceptar.removeMouseListener(eventoBtnCrear);
        
        eventoBtnCrear = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCrearProducto();
            }
        };   
        
        pnCrearProducto.btnAceptar.addMouseListener(eventoBtnCrear);
    }
    
    private void mtdCrearEventoBtnCancelar(){
        MouseListener eventoBtnCancelar = null;
        pnCrearProducto.btnCancelar.removeMouseListener(eventoBtnCancelar);
        
        eventoBtnCancelar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdSalirDelModal();
            }
        };   
        
        pnCrearProducto.btnCancelar.addMouseListener(eventoBtnCancelar);
    }
    
    private void mtdCrearEventoBtnSeleccionarImg(){
        MouseListener eventoBtnSeleccionarImg = null;
        pnCrearProducto.btnSeleccionarImg.removeMouseListener(eventoBtnSeleccionarImg);
        
        eventoBtnSeleccionarImg = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdSeleccionarImg();
            }
        };   
        
        pnCrearProducto.btnSeleccionarImg.addMouseListener(eventoBtnSeleccionarImg);
    }
    
    private void mtdSeleccionarImg(){
        JFileChooser seleccionarArchivo = new JFileChooser();
        FileNameExtensionFilter fill = new FileNameExtensionFilter("JPG, PNG, &GIF", "jpg", "png", "gif");
        
        seleccionarArchivo.setFileFilter(fill);
        seleccionarArchivo.showOpenDialog(laVista);
        File archivo = seleccionarArchivo.getSelectedFile();
        
        if( archivo != null ){
            String path_img = archivo.getAbsolutePath();
            ImageIcon img = new ImageIcon(path_img);
            pnCrearProducto.cmpImagenPath.setText( archivo.getAbsolutePath() );
          
            if( archivo.length() > 805867 ){
                JOptionPane.showMessageDialog(null, "La imágen es demasiado grande.");
                pnCrearProducto.cmpImagenPath.rechazarCampo();
            }else{
                producto_dto.setProdImg(getImagen(path_img));
                pnCrearProducto.cmpImagenPath.aceptarCampo();
            }
        }        
        
    }
    
    private byte[] getImagen(String ruta){
        File imagen = new File(ruta);
        try {
            byte[] icono = new byte[(int) imagen.length() ];
            InputStream inpu = new FileInputStream(imagen);
            inpu.read(icono);
            return icono;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    private void mtdCrearProducto(){
        if( pnCrearProducto.mtdComprobar() ){
            producto_dto.setProdUsuario( usuario_dto.getCmpID() );
            producto_dto.setProdCategoria( String.valueOf( pnCrearProducto.cmpCategoria.getSelectedItem() ) );
            producto_dto.setProdTitulo( pnCrearProducto.cmpTitulo.getText().trim() );
            producto_dto.setProdDescripcion( pnCrearProducto.cmpDescripcion.getText().trim() );
            producto_dto.setProdPrecio( Double.parseDouble( pnCrearProducto.cmpPrecio.getText().trim() ) );
            producto_dto.setProdStock( Integer.parseInt( pnCrearProducto.cmpStock.getText().trim() ) );
            producto_dto.setProdTipo(0);
            producto_dto.setProdEnlace("Vacio");
            
            if( producto_dao.mtdInsetar(producto_dto) ){
                JOptionPane.showMessageDialog(null, "Producto creado exitosamente.");
                mtdSalirDelModal();
                mtdRecargarDatos();
                mtdMostrarProducto();
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "Verifica que los datos sean correctos.");
        }
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
    
    private void mtdSalirDelModal(){
        modalCrearProducto.removeAll();
        modalCrearProducto.setVisible(false);
        modalCrearProducto.dispose();
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
