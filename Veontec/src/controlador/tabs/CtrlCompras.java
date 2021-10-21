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
    public PanelCompras pnCompras;
    public JDialog modalCrearProducto;
    
    // ***** Modelos
    private UsuarioDto usuarioDto;
    private UsuarioDao usuarioDao;
    private ProductoDao productoDao;
    private ProductoDto productoDto;
    private CompraDao compraDao;
    private CompraDto compraDto;
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
        this.pnCompras = laVista;
        this.usuarioDto = dto;
        this.usuarioDao = dao;
        this.productoDao = new ProductoDao();
        this.productoDto = new ProductoDto();
        this.compraDao = new CompraDao();
        this.compraDto = new CompraDto();
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
            instancia.mtdMostrarCompras(false);
        
        }
        
        return instancia;
    }
    
    // ***** Eventos
    private void mtdEventoBtnBuscar(){
        pnCompras.btnBuscar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEstabecerBusqueda();
                mtdMostrarCompras(activarBusqueda);
            }
        });
    }
    
    private void mtdEventoCmpBuscarProducto(){
        pnCompras.cmpBusqueda.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                    mtdEstabecerBusqueda();
                    mtdMostrarCompras(activarBusqueda);
                } 
            }
        });
        
    }
    
    private void mtdEventoBtnPrevia(){
        pnCompras.btnPrevia.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdMostrarProductosPrevias();
            }
        });
    }
    
    private void mtdEventoBtnSiguiente(){
        pnCompras.btnSiguiente.addMouseListener(new MouseAdapter(){
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
        mtdMostrarCompras(false);
    }
    
    public static boolean mtdRecargarCompras(){
        LOG.warning("Ejecutando metodo ");
        //if( instancia == null ){
            //instancia.mtdInit();
        //}
        
        instancia.mtdMostrarCompras(false);
        return true;
    }
    
    private void mtdMostrarCompras(boolean busqueda){
        LOG.info("Iniciando...");
        
        int totalProductos = 0;
        pnCompras.pnContenedor.setLayout(new GridBagLayout());
        pnCompras.pnContenedor.removeAll();
        
        
        // El usuario actual es el comprador
        compraDto.setCompComprador(usuarioDto.getCmpID() );
        
        LOG.info("listando...");
        if( busqueda == false){
            lstMisCompras = compraDao.mtdListarAllComprasPorUsuario(compraDto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.parseInt(""+ compraDao.mtdRowCountAllComprasPorUsuario(compraDto));
        } else{
            compraDto.setCompTitulo('%'+pnCompras.cmpBusqueda.getText()+'%');
            lstMisCompras = compraDao.mtdBuscarAllComprasPorUsuarioSimilares(compraDto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.parseInt(""+ compraDao.mtdRowCountAllComprasPorUsuarioSimilares(compraDto));
        }
        
        totalProductos = lstMisCompras.size();
        if( totalProductos > 0 ){
            
            LOG.warning("Recorriendo productos");
            for (int i = 0; i < totalProductos; i++) {
                CtrlCardCompra tarjeta = new CtrlCardCompra(lstMisCompras.get(i));
                tarjeta.setItem(i);
                tarjeta.mtdInit();
                pnCompras.pnContenedor.add(tarjeta.getLaVista(), tarjeta.getTarjeta_dimensiones());
            }
            
        }
            
        
        pnCompras.pnContenedor.validate();
        pnCompras.pnContenedor.revalidate();
        pnCompras.pnContenedor.repaint();
        
    }
    
    private void mtdMostrarProductosPrevias(){
        cantidadResultados -= cantidadPorPagina;
        //pagProductos = cantidadResultados <= 0 ? 0 : cantidadResultados;
        //LOG.info("Productos previas : " + cantidadResultados );
        
        if( cantidadResultados < 0  ){
            cantidadResultados = 0;
            JOptionPane.showMessageDialog(pnCompras, "No hay más resultados por mostrar.");
            pnCompras.btnPrevia.setEnabled(false);
            return;
        }
        
        pnCompras.btnSiguiente.setEnabled(true);
        mtdMostrarCompras(activarBusqueda);
        
    }
    
    private void mtdMostrarProductosSiguiente(){
        cantidadResultados += cantidadPorPagina;
        //pagProductos = cantidadResultados >= totalProductosExistentes ? totalProductosExistentes: cantidadResultados;
        //LOG.info("Productos siguientes : " + cantidadResultados +" : Productos existentes : " + totalProductosExistentes );
        
        if( cantidadResultados >= totalProductosExistentes ){
            cantidadResultados = totalProductosExistentes;
            JOptionPane.showMessageDialog(pnCompras, "No hay más resultados por mostrar.");
            pnCompras.btnSiguiente.setEnabled(false);
            return;
        }
        
        pnCompras.btnPrevia.setEnabled(true);
        mtdMostrarCompras(activarBusqueda);
        
    }
    
    private void mtdEstabecerBusqueda(){
        pnCompras.btnPrevia.setEnabled(true);
        pnCompras.btnSiguiente.setEnabled(true);
        
        cantidadResultados=0;
        if( pnCompras.cmpBusqueda.getText().trim().isEmpty() || pnCompras.cmpBusqueda.isVacio() ){
            activarBusqueda = false;
        }else{
            activarBusqueda = true;
        }
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
