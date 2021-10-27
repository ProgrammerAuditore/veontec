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
import src.Software;
import vista.paneles.PanelBienvenida;

public class CtrlBienvenida{
    
    // ***** Vista
    public PanelBienvenida pnBienvenida;
    public JDialog modalCrearProducto;
    
    // ***** Modelos
    private UsuarioDto usuarioDto;
    private UsuarioDao usuarioDao;
    private ProductoDao productoDao;
    private ProductoDto productoDto;
    private DefaultMutableTreeNode treeNode1;
    
    // ***** Atributos
    private static CtrlBienvenida instancia;
    private List<ProductoDto> lstProductos;
    private Integer cantidadResultado;
    private Integer totalProductosExistentes;
    private Integer cantidaPorPagina;
    private boolean activarBusqueda;
    private static final Logger LOG = Logger.getLogger(CtrlBienvenida.class.getName());
    

    // ***** Constructor
    public CtrlBienvenida(PanelBienvenida laVista, UsuarioDto dto, UsuarioDao dao) {
        this.pnBienvenida = laVista;
        this.usuarioDto = dto;
        this.usuarioDao = dao;
        this.productoDao = new ProductoDao();
        this.productoDto = new ProductoDto();
        this.cantidadResultado = 0;
        this.cantidaPorPagina = Software.veontecResultadoPorPagina;
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
            instancia.mtdMostrarProductos(false);
            
        }
        
        return instancia;
    }
    
    // ***** Eventos
    private void mtdEventoBtnBuscar(){
        pnBienvenida.btnBuscar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEstabecerBusqueda();
                mtdMostrarProductos(activarBusqueda);
            }
        });
    }
    
    private void mtdEventoCmpBuscarProducto(){
        pnBienvenida.cmpBusqueda.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                    mtdEstabecerBusqueda();
                    mtdMostrarProductos(activarBusqueda);
                } 
            }
        });
        
    }
    
    private void mtdEventoBtnPrevia(){
        pnBienvenida.btnPrevia.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdMostrarProductosPrevias();
            }
        });
    }
    
    private void mtdEventoBtnSiguiente(){
        pnBienvenida.btnSiguiente.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdMostrarProductosSiguiente();
            }
        });
    }
    
    // ***** Métodos
    private void mtdInit(){
        LOG.info("Ejecutando || CtrlBienvenida::mtdInit ");
        mtdEventoBtnSiguiente();
        mtdEventoBtnPrevia();
        mtdEventoBtnBuscar();
        mtdEventoCmpBuscarProducto();
        mtdMostrarProductos(false);
    }
    
    public static boolean mtdRecargar(){
        instancia.mtdMostrarProductos(false);
        return true;
    }
    
    private synchronized void mtdMostrarProductos(boolean busqueda){
        LOG.warning("Inicializando ... ");
        
        int totalProductos=0;
        pnBienvenida.pnContenedor.setLayout(new GridBagLayout());
        pnBienvenida.pnContenedor.removeAll();
        
        if( busqueda == false ){
            lstProductos = productoDao.mtdListarAllProductos(cantidaPorPagina, cantidadResultado);
            totalProductosExistentes = Integer.parseInt(""+productoDao.mtdRowCountAllProductos());
        }else{
            productoDto.setProdTitulo('%'+pnBienvenida.cmpBusqueda.getText().trim()+'%');
            productoDto.setProdCategoria('%'+pnBienvenida.cmpBusqueda.getText().trim()+'%');
            lstProductos = productoDao.mtdBuscarAllProductosSimilares(productoDto, cantidaPorPagina, cantidadResultado);
            totalProductosExistentes = productoDao.mtdRowCountAllProductosSimilares(productoDto);
        }
        
        totalProductos = lstProductos.size();
        if( totalProductos > 0 ){
        
            int columna = 0, fila = 0; // Establecer contadores para columnas y filas
            int productos = Software.veontecCardsPorFila; // Establecer cantida de producto a mostrar por fila
            LOG.warning(" Recorriendo productos. ");
            for (int i = 0; i < totalProductos; i++) {
                CtrlCardProducto tarjeta = new CtrlCardProducto(lstProductos.get(i), productoDao);
                tarjeta.setItem(fila);
                tarjeta.setColumna(columna);

                tarjeta.mtdInit();
                //LOG.warning(" Agregando producto: #" + i);
                pnBienvenida.pnContenedor.add(tarjeta.getLaVista(), tarjeta.getTarjeta_dimensiones());
                columna++;

                if( columna >= productos ){
                    columna = 0;
                    fila++;
                }
            }
            
        }
        
        pnBienvenida.pnContenedor.validate();
        pnBienvenida.pnContenedor.revalidate();
        pnBienvenida.pnContenedor.repaint();
        
    }
    
    private void mtdMostrarProductosPrevias(){
        cantidadResultado -= cantidaPorPagina;
        //pagProductos = cantidadResultado <= 0 ? 0 : cantidadResultado;
        //LOG.info("Productos previas : " + cantidadResultado );
        
        if( cantidadResultado < 0  ){
            cantidadResultado = 0;
            JOptionPane.showMessageDialog(pnBienvenida, "No hay más resultados por mostrar.");
            pnBienvenida.btnPrevia.setEnabled(false);
            return;
        }
        
        pnBienvenida.btnSiguiente.setEnabled(true);
        mtdMostrarProductos(activarBusqueda);
        
    }
    
    private void mtdMostrarProductosSiguiente(){
        cantidadResultado += cantidaPorPagina;
        //pagProductos = cantidadResultado >= totalProductosExistentes ? totalProductosExistentes: cantidadResultado;
        //LOG.info("Productos siguientes : " + cantidadResultado );
        
        if( cantidadResultado >= totalProductosExistentes ){
            cantidadResultado = totalProductosExistentes;
            JOptionPane.showMessageDialog(pnBienvenida, "No hay más resultados por mostrar.");
            pnBienvenida.btnSiguiente.setEnabled(false);
            return;
        }
        
        pnBienvenida.btnPrevia.setEnabled(true);
        mtdMostrarProductos(activarBusqueda);
        
    }
    
    private void mtdEstabecerBusqueda(){
        pnBienvenida.btnPrevia.setEnabled(true);
        pnBienvenida.btnSiguiente.setEnabled(true);
        
        cantidadResultado=0;
        if( pnBienvenida.cmpBusqueda.getText().trim().isEmpty() || pnBienvenida.cmpBusqueda.isVacio() ){
            activarBusqueda = false;
        }else{
            activarBusqueda = true;
        }
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
