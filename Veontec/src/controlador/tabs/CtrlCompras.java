package controlador.tabs;

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
import src.Info;
import vista.paneles.PanelCompras;

public class CtrlCompras{
    
    // ***** Vista
    public PanelCompras laVista;
    public JDialog modalCrearProducto;
    
    // ***** Modelos
    private UsuarioDto usuario_dto;
    private UsuarioDao usuario_dao;
    private ProductoDao producto_dao;
    private ProductoDto producto_dto;
    private CompraDao compra_dao;
    private CompraDto compra_dto;
    private DefaultMutableTreeNode treeNode1;
    
    // ***** Atributos
    private static CtrlCompras instancia;
    private List<CompraDto> lstMisCompras;
    private Integer cantidadResultados;
    private Integer totalProductosExistentes;
    private Integer cantidadPorPagina;
    private boolean activarBusqueda;
    private static final Logger LOG = Logger.getLogger(CtrlCompras.class.getName());

    // ***** Constructor
    public CtrlCompras(PanelCompras laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuario_dto = dto;
        this.usuario_dao = dao;
        this.producto_dao = new ProductoDao();
        this.producto_dto = new ProductoDto();
        this.compra_dao = new CompraDao();
        this.compra_dto = new CompraDto();
        this.cantidadResultados = 0;
        this.cantidadPorPagina = Info.veontecResultadoPorPagina;
        this.activarBusqueda = false;
    }
    
    // Obtener instancia | Singleton
    public static CtrlCompras getInstancia(PanelCompras laVista, UsuarioDto dto, UsuarioDao dao){
        LOG.info("Inicializando controlador");
        
        if( instancia == null ){
            LOG.warning("Creando instancia");
            instancia = new CtrlCompras(laVista, dto, dao);
            instancia.mtdInit();
        
        }else{
            instancia.mtdMostrarProducto(false);
        
        }
        
        return instancia;
    }
    
    // ***** Eventos
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
    
    // ***** Métodos
    private void mtdInit(){
        LOG.info("Ejecutando metodo una vez (obligatorio)");
        mtdEventoBtnBuscar();
        mtdEventoBtnPrevia();
        mtdEventoBtnSiguiente();
        mtdEventoCmpBuscarProducto();
        mtdMostrarProducto(false);
    }
    
    public static boolean mtdRecargarCompras(){
        LOG.warning("Ejecutando metodo ");
        //if( instancia == null ){
            //instancia.mtdInit();
        //}
        
        instancia.mtdMostrarProducto(false);
        return true;
    }
    
    private void mtdMostrarProducto(boolean busqueda){
        LOG.info("Iniciando...");
        
        int totalProductos = 0;
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        
        // El usuario actual es el comprador
        compra_dto.setCompComprador( usuario_dto.getCmpID() );
        
        LOG.info("listando...");
        if( busqueda == false){
            lstMisCompras = compra_dao.mtdListar(compra_dto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.parseInt(""+ compra_dao.mtdRowCountAllComprasPorUsuario(compra_dto));
        } else{
            compra_dto.setCompTitulo('%'+laVista.cmpBusqueda.getText()+'%');
            lstMisCompras = compra_dao.mtdBuscarAllComprasPorUsuarioSimilares(compra_dto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.parseInt(""+ compra_dao.mtdRowCountAllComprasPorUsuarioSimilares(compra_dto));
        }
        
        totalProductos = lstMisCompras.size();
        if( totalProductos > 0 ){
            
            LOG.warning("Recorriendo productos");
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
        //LOG.info("Productos siguientes : " + cantidadResultados +" : Productos existentes : " + totalProductosExistentes );
        
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
