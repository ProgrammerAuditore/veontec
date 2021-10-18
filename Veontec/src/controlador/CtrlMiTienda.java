package controlador;

import controlador.acciones.CtrlModalCrearProducto;
import controlador.componentes.CtrlCardMiProducto;
import index.Veontec;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.logging.Logger;
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

public class CtrlMiTienda{
    
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
    private Integer cantidadResultados;
    private Integer totalProductosExistentes;
    private Integer cantidadPorPagina;
    private boolean activarBusqueda;
    private static final Logger LOG = Logger.getLogger(CtrlBienvenida.class.getName());

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
        this.cantidadResultados = 0;
        this.cantidadPorPagina = 3;
        this.activarBusqueda = false;
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
            instancia.mtdMostrarProducto(false);
        
        }
        
        return instancia;
    }
    
    public static boolean mtdRecargarMisProductos(){
        instancia.mtdMostrarCategorias();
        instancia.mtdMostrarProducto(false);
        return true;
    }
    
    // Eventos
    private void mtdEventoBtnCrearProducto(){
        laVista.btnCrearProducto.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                CtrlModalCrearProducto crear = new CtrlModalCrearProducto();
                crear.mtdInit();
            }
        });
    }
    
    private void mtdEventoBtnAgregarCategoria(){
        laVista.btnAgregar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                 mtdCrearCategoria();
            }
        });
    }
    
    private void mtdEventoBtnModificarCategoria(){
        laVista.btnModificar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdModificarCategoria();
            }
        });
    }
    
    private void mtdEventoBtnEliminarCategoria(){
        laVista.btnEliminar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEliminarCategoria();
            }
        });
    }
    
    private void mtdEventoBtnBuscar(){
        laVista.btnBuscar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEstabecerBusqueda();
                mtdMostrarProducto(activarBusqueda);
            }
        });
    }
    
    private void mtdEventoCmpBuscarProducto(){
        laVista.cmpBusqueda.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                    mtdEstabecerBusqueda();
                    mtdMostrarProducto(activarBusqueda);
                } 
            }
        });
        
    }
    
    private void mtdEventoBtnPrevia(){
        laVista.btnPrevia.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdMostrarProductosPrevias();
            }
        });
    }
    
    private void mtdEventoBtnSiguiente(){
        laVista.btnSiguiente.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdMostrarProductosSiguiente();
            }
        });
    }
   
    // Métodos
    private void mtdInit(){
        logger.info("Ejecutando metodo una vez (Obligatorio)");
        laVista.pnContenedor.setLayout(new GridBagLayout());
        images_dto.setImagUsuario( usuario_dto.getCmpID() );
        mtdEventoBtnAgregarCategoria();
        mtdEventoBtnModificarCategoria();
        mtdEventoBtnCrearProducto();
        mtdEventoBtnEliminarCategoria();
        mtdEventoBtnBuscar();
        mtdEventoCmpBuscarProducto();
        mtdEventoBtnPrevia();
        mtdEventoBtnSiguiente();
        mtdDefinirIconos();
        mtdMostrarCategorias();
        mtdMostrarProducto(false);
    }
    
    private void mtdMostrarProducto(boolean busqueda){
        logger.info("Iniciando ...");
        
        int totalProductos = 0;
        laVista.pnContenedor.removeAll();
        laVista.pnContenedor.setLayout(new GridBagLayout());
        
        logger.info("Listando mis productos...");
        producto_dto.setProdUsuario( usuario_dto.getCmpID() );
        
        
        if( busqueda == false ){
            lstMisProductos = producto_dao.mtdListar(producto_dto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.parseInt(""+producto_dao.mtdRowCount(producto_dto));
        }else{
            producto_dto.setProdTitulo('%'+laVista.cmpBusqueda.getText().trim()+'%');
            producto_dto.setProdCategoria('%'+laVista.cmpBusqueda.getText().trim()+'%');
            lstMisProductos = producto_dao.mtdBuscarAllProductosPorUsuarioSimilares(producto_dto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.parseInt(""+producto_dao.mtdRowCountAllProductosPorUsuarioSimilares(producto_dto));
        }
        
        totalProductos = lstMisProductos.size();
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
            int opc = JOptionPane.showConfirmDialog(laVista, boxCorreo, "Eliminar categoria",
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
    
    private void mtdMostrarProductosPrevias(){
        cantidadResultados -= cantidadPorPagina;
        //pagProductos = cantidadResultados <= 0 ? 0 : cantidadResultados;
        //LOG.info("Productos previas : " + cantidadResultados );
        
        if( cantidadResultados < 0  ){
            cantidadResultados = 0;
            JOptionPane.showMessageDialog(laVista, "No hay más resultados por mostrar.");
            laVista.btnPrevia.setEnabled(false);
            return;
        }
        
        laVista.btnSiguiente.setEnabled(true);
        mtdMostrarProducto(activarBusqueda);
        
    }
    
    private void mtdMostrarProductosSiguiente(){
        cantidadResultados += cantidadPorPagina;
        //pagProductos = cantidadResultados >= totalProductosExistentes ? totalProductosExistentes: cantidadResultados;
        //LOG.info("Productos siguientes : " + cantidadResultados );
        
        if( cantidadResultados >= totalProductosExistentes ){
            cantidadResultados = totalProductosExistentes;
            JOptionPane.showMessageDialog(laVista, "No hay más resultados por mostrar.");
            laVista.btnSiguiente.setEnabled(false);
            return;
        }
        
        laVista.btnPrevia.setEnabled(true);
        mtdMostrarProducto(activarBusqueda);
        
    }
    
    private void mtdEstabecerBusqueda(){
        laVista.btnPrevia.setEnabled(true);
        laVista.btnSiguiente.setEnabled(true);
        
        cantidadResultados=0;
        if( laVista.cmpBusqueda.getText().trim().isEmpty() || laVista.cmpBusqueda.isVacio() ){
            activarBusqueda = false;
        }else{
            activarBusqueda = true;
        }
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }

}
