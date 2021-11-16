package controlador.tabs;

import controlador.acciones.CtrlModalCrearProducto;
import controlador.componentes.CtrlCardMiProducto;
import index.Veontec;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
import src.Software;
import src.Recursos;
import vista.paneles.acciones.PanelCrearProducto;
import vista.paneles.PanelMiTienda;

public class CtrlMiTienda{
    
    // ***** Vista
    public PanelMiTienda pnMiTienda;
    public JDialog modalCrearProducto;
    
    // ***** Modelos
    private UsuarioDto usuarioDto;
    private UsuarioDao usuarioDao;
    private ProductoDao productoDao;
    private ProductoDto productoDto;
    private ImagesDao imagesDao;
    private ImagesDto imagesDto;
    private CategoriaDto categoriaDto;
    private CategoriaDao categoriaDao;
    private DefaultMutableTreeNode lstCategoriaRaiz;
    private DefaultTreeModel lstCategoriaModelo;
    
    // ***** Atributos
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

    // ***** Constructor
    public CtrlMiTienda(PanelMiTienda laVista, UsuarioDto dto, UsuarioDao dao) {
        this.pnMiTienda = laVista;
        this.usuarioDto = dto;
        this.usuarioDao = dao;
        this.productoDao = new ProductoDao();
        this.productoDto = new ProductoDto();
        this.imagesDao = new ImagesDao();
        this.imagesDto = new ImagesDto();
        this.categoriaDao = new CategoriaDao();
        this.categoriaDto = new CategoriaDto();
        this.lstCategoriaRaiz = new DefaultMutableTreeNode("Categorias");
        this.lstCategoriaModelo = new DefaultTreeModel(lstCategoriaRaiz);
        this.cantidadResultados = 0;
        this.cantidadPorPagina = Software.veontecResultadoPorPagina;
        this.activarBusqueda = false;
    }
    
    // Obtener instancia | Singleton
    public static CtrlMiTienda getInstancia(PanelMiTienda laVista, UsuarioDto dto, UsuarioDao dao){
        LOG.warning("Inicializando controlador.... ");
        
        if( instancia == null ){
            LOG.warning("Creando instancia.... ");
            instancia = new CtrlMiTienda(laVista, dto, dao);
            instancia.mtdInit();
            
        }else{
            instancia.mtdMostrarCategorias();
            instancia.mtdMostrarMisProductos(false);
        
        }
        
        return instancia;
    }
    
    public static boolean mtdRecargarMisProductos(){
        instancia.mtdMostrarCategorias();
        instancia.mtdMostrarMisProductos(false);
        return true;
    }
    
    // ***** Eventos
    private void mtdEventoBtnCrearProducto(){
        pnMiTienda.btnCrearProducto.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                CtrlModalCrearProducto crear = new CtrlModalCrearProducto();
                crear.mtdInit();
            }
        });
    }
    
    private void mtdEventoBtnAgregarCategoria(){
        pnMiTienda.btnAgregar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                 mtdCrearCategoria();
            }
        });
    }
    
    private void mtdEventoBtnModificarCategoria(){
        pnMiTienda.btnModificar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdModificarCategoria();
            }
        });
    }
    
    private void mtdEventoBtnEliminarCategoria(){
        pnMiTienda.btnEliminar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEliminarCategoria();
            }
        });
    }
    
    private void mtdEventoBtnBuscar(){
        pnMiTienda.btnBuscar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEstabecerBusqueda();
                mtdMostrarMisProductos(activarBusqueda);
            }
        });
    }
    
    private void mtdEventoCmpBuscarProducto(){
        pnMiTienda.cmpBusqueda.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                    mtdEstabecerBusqueda();
                    mtdMostrarMisProductos(activarBusqueda);
                } 
            }
        });
        
    }
    
    private void mtdEventoBtnPrevia(){
        pnMiTienda.btnPrevia.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdMostrarProductosPrevias();
            }
        });
    }
    
    private void mtdEventoBtnSiguiente(){
        pnMiTienda.btnSiguiente.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdMostrarProductosSiguiente();
            }
        });
    }
   
    // ***** Métodos
    private void mtdInit(){
        LOG.info("Ejecutando metodo una vez (Obligatorio)");
        pnMiTienda.pnContenedor.setLayout(new GridBagLayout());
        imagesDto.setImagUsuario(usuarioDto.getCmpID() );
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
        mtdMostrarMisProductos(false);
    }
    
    private void mtdMostrarMisProductos(boolean busqueda){
        LOG.info("Iniciando ...");
        
        int totalProductos = 0;
        pnMiTienda.pnContenedor.removeAll();
        pnMiTienda.pnContenedor.setLayout(new GridBagLayout());
        
        LOG.info("Listando mis productos...");
        productoDto.setProdUsuario(usuarioDto.getCmpID() );
        
        
        if( busqueda == false ){
            lstMisProductos = productoDao.mtdListarAllProductosPorUsuario(productoDto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.parseInt(""+productoDao.mtdRowCountAllProductosPorUsuario(productoDto));
        }else{
            productoDto.setProdTitulo('%'+pnMiTienda.cmpBusqueda.getText().trim()+'%');
            productoDto.setProdCategoria('%'+pnMiTienda.cmpBusqueda.getText().trim()+'%');
            lstMisProductos = productoDao.mtdBuscarAllProductosPorUsuarioSimilares(productoDto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.parseInt(""+productoDao.mtdRowCountAllProductosPorUsuarioSimilares(productoDto));
        }
        
        totalProductos = lstMisProductos.size();
        if( totalProductos > 0){
            
            LOG.warning("Recorriendo productos ....");
            for (int i = 0; i < totalProductos; i++) {
                CtrlCardMiProducto tarjeta = new CtrlCardMiProducto(lstMisProductos.get(i), productoDao);
                tarjeta.setItem(i);
                tarjeta.mtdInit();
                pnMiTienda.pnContenedor.add(tarjeta.getLaVista(), tarjeta.getTarjeta_dimensiones());
            }
            
        }
        
        pnMiTienda.pnContenedor.validate();
        pnMiTienda.pnContenedor.revalidate();
        pnMiTienda.pnContenedor.repaint();
        
    }
           
    public void mtdMostrarCategorias(){
        LOG.info("Iniciando ...");
        pnMiTienda.lstCategorias.setModel(null);
        lstCategoriaRaiz.removeAllChildren();
        
        LOG.info("Listando mis categorias...");
        categoriaDto.setCateUsuario(usuarioDto.getCmpID());
        lstMisCategorias = categoriaDao.mtdListar(categoriaDto);
        int totalCategorias = lstMisCategorias.size();
        LOG.info("Total categorias: " + totalCategorias);
        
        
        if( totalCategorias > 0 ){
            
            LOG.warning("Recorriendo categorias ....");
            for (int i = 0; i < totalCategorias; i++) {
                DefaultMutableTreeNode categoria = new DefaultMutableTreeNode(lstMisCategorias.get(i).getCateNombre());
                lstCategoriaModelo.insertNodeInto(categoria, lstCategoriaRaiz, i);
            }
            
        }
        
        lstCategoriaModelo.reload();
        pnMiTienda.lstCategorias.setModel(lstCategoriaModelo);
        pnMiTienda.lstCategorias.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }
    
    private void mtdDefinirIconos(){
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) pnMiTienda.lstCategorias.getCellRenderer();
        renderer.setClosedIcon(Recursos.imgIconoDefaultCategorias());
        renderer.setOpenIcon(Recursos.imgIconoDefaultCategorias());
        renderer.setLeafIcon(Recursos.imgIconoDefaultCategorias());
    }
    
    private void mtdCrearCategoria(){
       
        String categoriaNueva="";
        Box boxCorreo = Box.createVerticalBox();

        JLabel info2 = new JLabel("Introduzca la categoría nueva");
        boxCorreo.add(info2);
        JTextField cmpCategoriaNueva = new JTextField(24);
        boxCorreo.add(cmpCategoriaNueva);
        
        boxCorreo.setLocation(Veontec.ventanaHome.getLocation());
        int opc = JOptionPane.showConfirmDialog(pnMiTienda, boxCorreo, "Crear nueva categoría",
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
            JLabel info1 = new JLabel("Nombre categoría actual");
            boxCorreo.add(info1);
            JTextField cmpCategoriaActual = new  JTextField(categoriaDto.getCateNombre());
            cmpCategoriaActual.setEditable(false);
            boxCorreo.add(cmpCategoriaActual);

            JLabel info2 = new JLabel("Introduzca la categoría nueva");
            boxCorreo.add(info2);
            JTextField cmpCategoriaNueva = new JTextField(24);
            boxCorreo.add(cmpCategoriaNueva);

            boxCorreo.setLocation(Veontec.ventanaHome.getLocation());
            int opc = JOptionPane.showConfirmDialog(pnMiTienda, boxCorreo, "Cambiar nombre de categoría",
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
            JLabel info1 = new JLabel("Categoría seleccionada");
            boxCorreo.add(info1);
            JTextField cmpCategoriaActual = new  JTextField(categoriaSeleccionda);
            cmpCategoriaActual.setEditable(false);
            boxCorreo.add(cmpCategoriaActual);

            JLabel info2 = new JLabel("¿Seguro que deseas eliminar la categoría?");
            boxCorreo.add(info2);

            boxCorreo.setLocation(Veontec.ventanaHome.getLocation());
            int opc = JOptionPane.showConfirmDialog(pnMiTienda, boxCorreo, "Eliminar categoría",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            if( opc == JOptionPane.OK_OPTION ){
                if( categoriaDao.mtdRemover(categoriaDto) ){
                    mtdMostrarCategorias();
                    JOptionPane.showMessageDialog(pnMiTienda, "Categoría eliminada exitosamente.");
                }
            }
            
        }
    }
    
    private void mtdProcesoModificarCategoria(String categoriaActual, String categoriaNueva ){
        if( categoriaNueva.trim().isEmpty()){
            JOptionPane.showMessageDialog(pnMiTienda, "Los campos están incompletos");

        }else if( categoriaNueva.equals("Categorías") || categoriaNueva.equals("Nueva") ){
            JOptionPane.showMessageDialog(pnMiTienda, "Nombre de categoría no válido.");

            // * Verificar que la categoria sean diferentes
        }else if( categoriaActual.equals(categoriaNueva) ){
            JOptionPane.showMessageDialog(pnMiTienda, "La categoría nueva no está disponible.");

        }else if( categoriaActual.length() > 30){
            JOptionPane.showMessageDialog(pnMiTienda, "* Solo se acepta 30 caracteres");

        }else{

            categoriaDto.setCateNombre(categoriaNueva);
            categoriaDto.setCateUsuario(Veontec.usuarioDto.getCmpID());

            // * Verificar si la categoria está disponible
            // es decir, que no está registrado
            if( categoriaDao.mtdComprobar(categoriaDto) ){
                if( categoriaDao.mtdActualizar(categoriaDto) ){
                    mtdMostrarCategorias();
                    JOptionPane.showMessageDialog(pnMiTienda, "Categoría modificada exitosamente.");
                }
            }else{
               JOptionPane.showMessageDialog(pnMiTienda, "La categoría nueva no está disponible.");

            }

        }
    }
    
    private void mtdProcesoCrearCategoria(String categoriaNueva) {
        if( categoriaNueva.trim().isEmpty()){
            JOptionPane.showMessageDialog(pnMiTienda, "Los campos están incompletos.");

        }else if(categoriaNueva.equals("Categorias") || categoriaNueva.equals("Nueva")){
            JOptionPane.showMessageDialog(pnMiTienda, "Nombre de categoría no válido.");

        }else if( categoriaNueva.length() > 30){
            JOptionPane.showMessageDialog(pnMiTienda, "* Solo se aceptan 30 caracteres.");

        }else{

            // * Verificar que la categoria sean diferentes
            categoriaDto.setCateNombre(categoriaNueva);
            categoriaDto.setCateUsuario( Veontec.usuarioDto.getCmpID() );
            categoriaDto.setCateTotalProductos(0);

            if( categoriaDao.mtdComprobar(categoriaDto) ){
                if(categoriaDao.mtdInsetar(categoriaDto)){
                    mtdMostrarCategorias();
                    JOptionPane.showMessageDialog(pnMiTienda, "Categoría nueva creada exitosamente.");
                }
            }else{
                JOptionPane.showMessageDialog(pnMiTienda, "La categoría nueva no está disponible.");

            }


        }
    }
    
    private boolean mtdValidarCategoria(){
        
        int indexCategoria = -1;
        try {
            indexCategoria = pnMiTienda.lstCategorias.getSelectionRows()[0];
        } catch (Exception e) {}

        if(indexCategoria == -1) return false;
        
        TreePath a = pnMiTienda.lstCategorias.getPathForRow( indexCategoria );
        categoriaSeleccionda = String.valueOf( a.getLastPathComponent() );
        
        if(categoriaSeleccionda.equals("Categorías") || categoriaSeleccionda.equals("Nueva") ) return false;
        
        // * Obtener los datos de la categoria seleccionada
        categoriaDto = new CategoriaDto();
        categoriaDto.setCateNombre(categoriaSeleccionda);
        categoriaDto.setCateUsuario(Veontec.usuarioDto.getCmpID());
        categoriaDto = categoriaDao.mtdConsultarNombreUsuario(categoriaDto);
        
        return true;
    }
    
    private void mtdMostrarProductosPrevias(){
        cantidadResultados -= cantidadPorPagina;
        //pagProductos = cantidadResultados <= 0 ? 0 : cantidadResultados;
        //LOG.info("Productos previas : " + cantidadResultados );
        
        if( cantidadResultados < 0  ){
            cantidadResultados = 0;
            JOptionPane.showMessageDialog(pnMiTienda, "No hay más resultados por mostrar.");
            pnMiTienda.btnPrevia.setEnabled(false);
            return;
        }
        
        pnMiTienda.btnSiguiente.setEnabled(true);
        mtdMostrarMisProductos(activarBusqueda);
        
    }
    
    private void mtdMostrarProductosSiguiente(){
        cantidadResultados += cantidadPorPagina;
        //pagProductos = cantidadResultados >= totalProductosExistentes ? totalProductosExistentes: cantidadResultados;
        //LOG.info("Productos siguientes : " + cantidadResultados );
        
        if( cantidadResultados >= totalProductosExistentes ){
            cantidadResultados = totalProductosExistentes;
            JOptionPane.showMessageDialog(pnMiTienda, "No hay más resultados por mostrar.");
            pnMiTienda.btnSiguiente.setEnabled(false);
            return;
        }
        
        pnMiTienda.btnPrevia.setEnabled(true);
        mtdMostrarMisProductos(activarBusqueda);
        
    }
    
    private void mtdEstabecerBusqueda(){
        pnMiTienda.btnPrevia.setEnabled(true);
        pnMiTienda.btnSiguiente.setEnabled(true);
        
        cantidadResultados=0;
        if( pnMiTienda.cmpBusqueda.getText().trim().isEmpty() || pnMiTienda.cmpBusqueda.isVacio() ){
            activarBusqueda = false;
        }else{
            activarBusqueda = true;
        }
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }

}
