package controlador.tabs;

import controlador.componentes.CtrlCardProducto;
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
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import src.Info;
import vista.paneles.PanelBienvenida;

public class CtrlBienvenida{
    
    // Vista
    public PanelBienvenida laVista;
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
    private Integer cantidadResultado;
    private Integer totalProductosExistentes;
    private Integer cantidaPorPagina;
    private boolean activarBusqueda;
    private static final Logger LOG = Logger.getLogger(CtrlBienvenida.class.getName());
    

    // Constructor
    public CtrlBienvenida(PanelBienvenida laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuario_dto = dto;
        this.usuario_dao = dao;
        this.producto_dao = new ProductoDao();
        this.producto_dto = new ProductoDto();
        this.cantidadResultado = 0;
        this.cantidaPorPagina = Info.veontecResultadoPorPagina;
        this.activarBusqueda = false;
    }
    
    // Obtener instancia | Singleton
    public static CtrlBienvenida getInstancia(PanelBienvenida laVista, UsuarioDto dto, UsuarioDao dao){
        LOG.info("Inicializando || CtrlBienvenida::getInstancia ");
        if( instancia == null ){
            LOG.info("Creado || CtrlBienvenida::getInstancia ");
            instancia = new CtrlBienvenida(laVista, dto, dao);
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
        LOG.info("Ejecutando || CtrlBienvenida::mtdInit ");
        mtdEventoBtnSiguiente();
        mtdEventoBtnPrevia();
        mtdEventoBtnBuscar();
        mtdEventoCmpBuscarProducto();
        mtdMostrarProducto(false);
    }
    
    public static boolean mtdRecargar(){
        instancia.mtdMostrarProducto(false);
        return true;
    }
    
    private synchronized void mtdMostrarProducto(boolean busqueda){
        LOG.warning("Inicializando ... ");
        
        int totalProductos=0;
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        if( busqueda == false ){
            lstMisProductos = producto_dao.mtdListar(cantidaPorPagina, cantidadResultado);
            totalProductosExistentes = producto_dao.mtdRowCountAllProductos();
        }else{
            producto_dto.setProdTitulo('%'+laVista.cmpBusqueda.getText().trim()+'%');
            producto_dto.setProdCategoria('%'+laVista.cmpBusqueda.getText().trim()+'%');
            lstMisProductos = producto_dao.mtdBuscarAllProductosSimilares(producto_dto, cantidaPorPagina, cantidadResultado);
            totalProductosExistentes = producto_dao.mtdRowCountAllProductosSimilares(producto_dto);
        }
        
        totalProductos = lstMisProductos.size();
        if( totalProductos > 0 ){
        
            int columna = 0, fila = 0; // Establecer contadores para columnas y filas
            int productos = Info.veontecCardsPorFila; // Establecer cantida de producto a mostrar por fila
            LOG.warning(" Recorriendo productos. ");
            for (int i = 0; i < totalProductos; i++) {
                CtrlCardProducto tarjeta = new CtrlCardProducto(lstMisProductos.get(i), producto_dao);
                tarjeta.setItem(fila);
                tarjeta.setColumna(columna);

                tarjeta.mtdInit();
                LOG.warning(" Agregando producto: #" + i);
                laVista.pnContenedor.add(tarjeta.getLaVista(), tarjeta.getTarjeta_dimensiones());
                columna++;

                if( columna >= productos ){
                    columna = 0;
                    fila++;
                }
            }
            
        }
        
        laVista.pnContenedor.validate();
        laVista.pnContenedor.revalidate();
        laVista.pnContenedor.repaint();
        
    }
    
    private void mtdMostrarProductosPrevias(){
        cantidadResultado -= cantidaPorPagina;
        //pagProductos = cantidadResultado <= 0 ? 0 : cantidadResultado;
        //LOG.info("Productos previas : " + cantidadResultado );
        
        if( cantidadResultado < 0  ){
            cantidadResultado = 0;
            JOptionPane.showMessageDialog(laVista, "No hay más resultados por mostrar.");
            laVista.btnPrevia.setEnabled(false);
            return;
        }
        
        laVista.btnSiguiente.setEnabled(true);
        mtdMostrarProducto(activarBusqueda);
        
    }
    
    private void mtdMostrarProductosSiguiente(){
        cantidadResultado += cantidaPorPagina;
        //pagProductos = cantidadResultado >= totalProductosExistentes ? totalProductosExistentes: cantidadResultado;
        //LOG.info("Productos siguientes : " + cantidadResultado );
        
        if( cantidadResultado >= totalProductosExistentes ){
            cantidadResultado = totalProductosExistentes;
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
        
        cantidadResultado=0;
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
