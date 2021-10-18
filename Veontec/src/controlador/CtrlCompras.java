package controlador;

import controlador.componentes.CtrlCardCompra;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import modelo.dao.CompraDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.CompraDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vista.paneles.PanelCompras;

public class CtrlCompras{
    
    // Vista
    public PanelCompras laVista;
    public JDialog modalCrearProducto;
    
    // Modelos
    private UsuarioDto usuario_dto;
    private UsuarioDao usuario_dao;
    private ProductoDao producto_dao;
    private ProductoDto producto_dto;
    private CompraDao compra_dao;
    private CompraDto compra_dto;
    private DefaultMutableTreeNode treeNode1;
    
    // Atributos
    static Log logger = LogFactory.getLog(CtrlCompras.class);
    private static CtrlCompras instancia;
    private List<CompraDto> lstMisCompras;
    private Integer pagProductos;
    private Integer totalProductosExistentes;
    private Integer productoPorPagina;
    private boolean busquedaProductos;
    private static final Logger LOG = Logger.getLogger(CtrlCompras.class.getName());

    // Constructor
    public CtrlCompras(PanelCompras laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuario_dto = dto;
        this.usuario_dao = dao;
        this.producto_dao = new ProductoDao();
        this.producto_dto = new ProductoDto();
        this.compra_dao = new CompraDao();
        this.compra_dto = new CompraDto();
        this.pagProductos = 0;
        this.productoPorPagina = 3;
        this.busquedaProductos = false;
    }
    
    // Obtener instancia | Singleton
    public static CtrlCompras getInstancia(PanelCompras laVista, UsuarioDto dto, UsuarioDao dao){
        logger.info("Inicializando controlador");
        
        if( instancia == null ){
            logger.warn("Creando instancia");
            instancia = new CtrlCompras(laVista, dto, dao);
            instancia.mtdInit();
        
        }else{
            instancia.mtdMostrarProducto(false);
        
        }
        
        return instancia;
    }
    
    // Eventos
    private void mtdEventoBtnBuscar(){
        laVista.btnBuscar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEstabecerBusqueda();
                mtdMostrarProducto(busquedaProductos);
            }
        });
    }
    
    private void mtdEventoCmpBuscarProducto(){
        laVista.cmpBuscar.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                    mtdEstabecerBusqueda();
                    mtdMostrarProducto(busquedaProductos);
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
        logger.info("Ejecutando metodo una vez (obligatorio)");
        mtdEventoBtnBuscar();
        mtdEventoBtnPrevia();
        mtdEventoBtnSiguiente();
        mtdEventoCmpBuscarProducto();
        mtdMostrarProducto(false);
    }
    
    public static boolean mtdRecargarCompras(){
        logger.warn("Ejecutando metodo ");
        //if( instancia == null ){
            //instancia.mtdInit();
        //}
        
        instancia.mtdMostrarProducto(false);
        return true;
    }
    
    private void mtdMostrarProducto(boolean busqueda){
        logger.info("Iniciando...");
        
        //lstMisProductos.clear();
        int totalProductos = 0;
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        
        // El usuario actual es el comprador
        compra_dto.setCompComprador( usuario_dto.getCmpID() );
        
        logger.info("listando...");
        if( busqueda == false){
            lstMisCompras = compra_dao.mtdListar(compra_dto, productoPorPagina, pagProductos);
            totalProductosExistentes = Integer.parseInt(""+ compra_dao.mtdRowCount(compra_dto));
        } else{
            compra_dto.setCompTitulo('%'+laVista.cmpBuscar.getText()+'%');
            lstMisCompras = compra_dao.mtdBuscarAllComprasPorUsuario(compra_dto, productoPorPagina, pagProductos);
            totalProductosExistentes = Integer.parseInt(""+ compra_dao.mtdRowCountAllComprasPorUsuario(compra_dto));
        }
        
        totalProductos = lstMisCompras.size();
        if( totalProductos > 0 ){
            
            logger.warn("Recorriendo productos");
            for (int i = 0; i < totalProductos; i++) {
                CtrlCardCompra tarjeta = new CtrlCardCompra(lstMisCompras.get(i));
                tarjeta.setItem(i);
                tarjeta.mtdInit();
                laVista.pnContenedor.add(tarjeta.getLaVista(), tarjeta.getTarjeta_dimensiones());
            }
            
        }
            
        
        laVista.pnContenedor.validate();
        laVista.pnContenedor.revalidate();
        laVista.pnContenedor.repaint();
        
    }
    
    private void mtdMostrarProductosPrevias(){
        pagProductos -= productoPorPagina;
        //pagProductos = pagProductos <= 0 ? 0 : pagProductos;
        LOG.info("Productos previas : " + pagProductos );
        
        if( pagProductos < 0  ){
            pagProductos = 0;
            JOptionPane.showMessageDialog(laVista, "No hay más productos para mostrar");
            laVista.btnPrevia.setEnabled(false);
            return;
        }
        
        laVista.btnSiguiente.setEnabled(true);
        mtdMostrarProducto(busquedaProductos);
        
    }
    
    private void mtdMostrarProductosSiguiente(){
        pagProductos += productoPorPagina;
        //pagProductos = pagProductos >= totalProductosExistentes ? totalProductosExistentes: pagProductos;
        LOG.info("Productos siguientes : " + pagProductos +" : Productos existentes : " + totalProductosExistentes );
        
        if( pagProductos >= totalProductosExistentes ){
            pagProductos = totalProductosExistentes;
            JOptionPane.showMessageDialog(laVista, "No hay más productos para mostrar");
            laVista.btnSiguiente.setEnabled(false);
            return;
        }
        
        laVista.btnPrevia.setEnabled(true);
        mtdMostrarProducto(busquedaProductos);
        
    }
    
    private void mtdEstabecerBusqueda(){
        laVista.btnPrevia.setEnabled(true);
        laVista.btnSiguiente.setEnabled(true);
        
        pagProductos=0;
        if( laVista.cmpBuscar.getText().trim().isEmpty() || laVista.cmpBuscar.isVacio() ){
            busquedaProductos = false;
        }else{
            busquedaProductos = true;
        }
        System.out.println("busquedaProductos " + busquedaProductos);
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
