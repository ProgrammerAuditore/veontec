package controlador;

import controlador.acciones.CtrlModalCrearProducto;
import controlador.componentes.CtrlCardMiProducto;
import index.Veontec;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import modelo.dao.CategoriaDao;
import modelo.dao.ImagesDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.CategoriaDto;
import modelo.dto.ImagesDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    private CategoriaDto categoria_dto;
    private CategoriaDao categoria_dao;
    private DefaultMutableTreeNode lstCategoriaRaiz;
    private DefaultTreeModel lstCategoriaModelo;
    private JTree lstCategoriasArbol;
    
    // Atributos
    static Log logger = LogFactory.getLog(CtrlMiTienda.class);
    private static CtrlMiTienda instancia;
    private List<ProductoDto> lstMisProductos;
    private List<CategoriaDto> lstMisCategorias;
    private PanelCrearProducto pnCrearProducto;
    private String categoriaSeleccionda;

    // Constructor
    public CtrlMiTienda(PanelMiTienda laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuario_dto = dto;
        this.usuario_dao = dao;
        this.producto_dao = new ProductoDao();
        this.producto_dto = new ProductoDto();
        this.images_dao = new ImagesDao();
        this.images_dto = new ImagesDto();
        this.categoria_dao = new CategoriaDao();
        this.categoria_dto = new CategoriaDto();
        this.lstCategoriaRaiz = new DefaultMutableTreeNode("Categorias");
        this.lstCategoriaModelo = new DefaultTreeModel(lstCategoriaRaiz);
    }
    
    // Obtener instancia | Singleton
    public static CtrlMiTienda getInstancia(PanelMiTienda laVista, UsuarioDto dto, UsuarioDao dao){
        logger.warn("Inicializando controlador.... ");
        
        if( instancia == null ){
            logger.warn("Creando instancia.... ");
            instancia = new CtrlMiTienda(laVista, dto, dao);
            instancia.mtdInit();
            
        }else{
            instancia.mtdMostrarCategorias();
            instancia.mtdMostrarProducto();
        
        }
        
        return instancia;
    }
    
    public static boolean mtdRecargarMisProductos(){
        instancia.mtdMostrarCategorias();
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
        
        if( e.getSource() == laVista.btnAgregar ){
            mtdCrearCategoria();
        }
        
        if( e.getSource() == laVista.btnModificar ){
            mtdModificarCategoria();
        }
        
        if( e.getSource() == laVista.btnEliminar ){
            mtdEliminarCategoria();
        }
        
    }
   
    // Métodos
    private void mtdInit(){
        logger.info("Ejecutando metodo una vez (Obligatorio)");
        laVista.pnContenedor.setLayout(new GridBagLayout());
        images_dto.setImagUsuario( usuario_dto.getCmpID() );
        laVista.btnCrearProducto.addMouseListener(this);
        laVista.btnAgregar.addMouseListener(this);
        laVista.btnModificar.addMouseListener(this);
        laVista.btnEliminar.addMouseListener(this);
        mtdDefinirIconos();
        mtdMostrarCategorias();
        mtdMostrarProducto();
    }
    
    private void mtdMostrarProducto(){
        logger.info("Iniciando ...");
        //lstMisProductos.clear();
        laVista.pnContenedor.removeAll();
        laVista.pnContenedor.setLayout(new GridBagLayout());
        
        logger.info("Listando mis productos...");
        producto_dto.setProdUsuario( usuario_dto.getCmpID() );
        lstMisProductos = producto_dao.mtdListar(producto_dto);
        int totalProductos = lstMisProductos.size();
        
        if( totalProductos > 0){
            
            logger.warn("Recorriendo productos ....");
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
           
    public void mtdMostrarCategorias(){
        logger.info("Iniciando ...");
        laVista.lstCategorias.setModel(null);
        lstCategoriaRaiz.removeAllChildren();
        
        logger.info("Listando mis categorias...");
        categoria_dto.setCateUsuario(usuario_dto.getCmpID());
        lstMisCategorias = categoria_dao.mtdListar(categoria_dto);
        int totalCategorias = lstMisCategorias.size();
        logger.info("Total categorias: " + totalCategorias);
        
        
        if( totalCategorias > 0 ){
            
            logger.warn("Recorriendo categorias ....");
            for (int i = 0; i < totalCategorias; i++) {
                DefaultMutableTreeNode categoria = new DefaultMutableTreeNode(lstMisCategorias.get(i).getCateNombre());
                lstCategoriaModelo.insertNodeInto(categoria, lstCategoriaRaiz, i);
            }
            
        }
        
        lstCategoriaModelo.reload();
        laVista.lstCategorias.setModel(lstCategoriaModelo);
        laVista.lstCategorias.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }
    
    private void mtdDefinirIconos(){
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) laVista.lstCategorias.getCellRenderer();
        Icon closedIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/storage/icons/icono.jpg")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        Icon openIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/storage/icons/icono.jpg")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        Icon leafIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/storage/icons/icono.jpg")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        renderer.setClosedIcon(closedIcon);
        renderer.setOpenIcon(openIcon);
        renderer.setLeafIcon(leafIcon);
    }
    
    private void mtdCrearCategoria(){
       
        String categoriaNueva="";
        Box boxCorreo = Box.createVerticalBox();

        JLabel info2 = new JLabel("Introduza la categoria nueva");
        boxCorreo.add(info2);
        JTextField cmpCategoriaNueva = new JTextField(24);
        boxCorreo.add(cmpCategoriaNueva);
        
        boxCorreo.setLocation(Veontec.ventanaHome.getLocation());
        int opc = JOptionPane.showConfirmDialog(laVista, boxCorreo, "Crear nueva categoria",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if( opc == JOptionPane.OK_OPTION ){
            categoriaNueva = cmpCategoriaNueva.getText().trim();
            
            mtdProcesoCrearCategoria(categoriaNueva);
        }
    }
    
    private void mtdModificarCategoria(){
        
        if( mtdValidarCategoria() ){
        
            String categoriaActual="", categoriaNueva="";
            Box boxCorreo = Box.createVerticalBox();
            JLabel info1 = new JLabel("Nombre categoria actual");
            boxCorreo.add(info1);
            JTextField cmpCategoriaActual = new  JTextField(categoria_dto.getCateNombre());
            cmpCategoriaActual.setEditable(false);
            boxCorreo.add(cmpCategoriaActual);

            JLabel info2 = new JLabel("Introduza la categoria nueva");
            boxCorreo.add(info2);
            JTextField cmpCategoriaNueva = new JTextField(24);
            boxCorreo.add(cmpCategoriaNueva);

            boxCorreo.setLocation(Veontec.ventanaHome.getLocation());
            int opc = JOptionPane.showConfirmDialog(laVista, boxCorreo, "Cambiar nombre de categoria",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if( opc == JOptionPane.OK_OPTION ){
                categoriaActual = cmpCategoriaActual.getText().trim();
                categoriaNueva = cmpCategoriaNueva.getText().trim();

                mtdProcesoModificarCategoria(categoriaActual, categoriaNueva);
                
            }
        }
    }
    
    private void mtdEliminarCategoria(){
        if( mtdValidarCategoria() ){

            Box boxCorreo = Box.createVerticalBox();
            JLabel info1 = new JLabel("Categoria seleccionada");
            boxCorreo.add(info1);
            JTextField cmpCategoriaActual = new  JTextField(categoriaSeleccionda);
            cmpCategoriaActual.setEditable(false);
            boxCorreo.add(cmpCategoriaActual);

            JLabel info2 = new JLabel("¿Seguro que desear eliminar la categoria?");
            boxCorreo.add(info2);

            boxCorreo.setLocation(Veontec.ventanaHome.getLocation());
            int opc = JOptionPane.showConfirmDialog(laVista, boxCorreo, "Cambiar nombre de categoria",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if( opc == JOptionPane.OK_OPTION ){
                if( categoria_dao.mtdRemover(categoria_dto) ){
                    mtdMostrarCategorias();
                    JOptionPane.showMessageDialog(laVista, "Categoria eliminada exitosamente.");
                }
            }
            
        }
    }
    
    private void mtdProcesoModificarCategoria(String categoriaActual, String categoriaNueva ){
        if( categoriaNueva.trim().isEmpty()){
            JOptionPane.showMessageDialog(laVista, "Los campos están incompletos");

        }else if( categoriaNueva.equals("Categorias") || categoriaNueva.equals("Nueva") ){
            JOptionPane.showMessageDialog(laVista, "Nombre de categoria no valido.");

            // * Verificar que la categoria sean diferentes
        }else if( categoriaActual.equals(categoriaNueva) ){
            JOptionPane.showMessageDialog(laVista, "La categoria nueva no está disponible.");

        }else if( categoriaActual.length() > 30){
            JOptionPane.showMessageDialog(laVista, "* Solo se acepta 30 caracteres");

        }else{

            categoria_dto.setCateNombre(categoriaNueva);
            categoria_dto.setCateUsuario(Veontec.usuarioDto.getCmpID());

            // * Verificar si la categoria está disponible
            // es decir, que no está registrado
            if( categoria_dao.mtdComprobar(categoria_dto) ){
                if( categoria_dao.mtdActualizar(categoria_dto) ){
                    mtdMostrarCategorias();
                    JOptionPane.showMessageDialog(laVista, "Categoria modificado exitosamente");
                }
            }else{
               JOptionPane.showMessageDialog(laVista, "La categoria nueva no está disponible.");

            }

        }
    }
    
    private void mtdProcesoCrearCategoria(String categoriaNueva) {
        if( categoriaNueva.trim().isEmpty()){
            JOptionPane.showMessageDialog(laVista, "Los campos están incompletos");

        }else if(categoriaNueva.equals("Categorias") || categoriaNueva.equals("Nueva")){
            JOptionPane.showMessageDialog(laVista, "Nombre de categoria no valido.");

        }else if( categoriaNueva.length() > 30){
            JOptionPane.showMessageDialog(laVista, "* Solo se acepta 30 caracteres");

        }else{

            // * Verificar que la categoria sean diferentes
            categoria_dto.setCateNombre(categoriaNueva);
            categoria_dto.setCateUsuario( Veontec.usuarioDto.getCmpID() );
            categoria_dto.setCateTotalProductos(0);

            if( categoria_dao.mtdComprobar(categoria_dto) ){
                if(categoria_dao.mtdInsetar(categoria_dto)){
                    mtdMostrarCategorias();
                    JOptionPane.showMessageDialog(laVista, "Categoria nueva creada exitosamente.");
                }
            }else{
                JOptionPane.showMessageDialog(laVista, "La categoria nueva no está disponible.");

            }


        }
    }
    
    private boolean mtdValidarCategoria(){
        
        int indexCategoria = -1;
        try {
            indexCategoria = laVista.lstCategorias.getSelectionRows()[0];
        } catch (Exception e) {}

        if(indexCategoria == -1) return false;
        
        TreePath a = laVista.lstCategorias.getPathForRow( indexCategoria );
        categoriaSeleccionda = String.valueOf( a.getLastPathComponent() );
        
        if(categoriaSeleccionda.equals("Categorias") || categoriaSeleccionda.equals("Nueva") ) return false;
        
        // * Obtener los datos de la categoria seleccionada
        categoria_dto = new CategoriaDto();
        categoria_dto.setCateNombre(categoriaSeleccionda);
        categoria_dto.setCateUsuario(Veontec.usuarioDto.getCmpID());
        categoria_dto = categoria_dao.mtdConsultarNombreUsuario(categoria_dto);
        
        return true;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }

}
