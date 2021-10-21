package controlador.tabs;

import controlador.componentes.CtrlCardVenta;
import index.Veontec;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dao.VentaDao;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import modelo.dto.VentaDto;
import src.Info;
import vista.paneles.PanelVentas;

public class CtrlVentas{
    
    // ***** Vista
    public PanelVentas pnVentas;
    
    // ***** Modelos
    private UsuarioDto usuarioDto;
    private UsuarioDao usuarioDao;
    private ProductoDao productoDao;
    private ProductoDto productoDto;
    private VentaDto ventaDto;
    private VentaDao ventaDao;
    private DefaultMutableTreeNode treeNode1;
    
    // ***** Atributos
    private static CtrlVentas instancia;
    private List<VentaDto> lstMisVentas;
    private Integer cantidadResultados;
    private Integer totalProductosExistentes;
    private Integer cantidadPorPagina;
    private boolean activarBusqueda;
    private static final Logger LOG = Logger.getLogger(CtrlVentas.class.getName());
    

    // ***** Constructor
    public CtrlVentas(PanelVentas laVista, UsuarioDto dto, UsuarioDao dao) {
        this.pnVentas = laVista;
        this.usuarioDto = dto;
        this.usuarioDao = dao;
        this.productoDao = new ProductoDao();
        this.productoDto = new ProductoDto();
        this.ventaDao = new VentaDao();
        this.ventaDto = new VentaDto();
        this.cantidadResultados = 0;
        this.cantidadPorPagina = Info.veontecResultadoPorPagina;
        this.activarBusqueda = false;
    }
    
    // Obtener instancia | Singleton
    public static CtrlVentas getInstancia(PanelVentas laVista, UsuarioDto dto, UsuarioDao dao){
        LOG.warning("Inicializando controlador.... ");
        if( instancia == null ){
            LOG.warning("Creando instancia.... ");
            instancia = new CtrlVentas(laVista, dto, dao);
            instancia.mtdInit();
        
        }else{
            instancia.mtdMostrarVentas(false);
        
        }
        
        return instancia;
    }
    
    // ***** Eventos
    private void mtdEventoBtnBuscar(){
        pnVentas.btnBuscar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEstabecerBusqueda();
                mtdMostrarVentas(activarBusqueda);
            }
        });
    }
    
    private void mtdEventoCmpBuscarProducto(){
        pnVentas.cmpBusqueda.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                    mtdEstabecerBusqueda();
                    mtdMostrarVentas(activarBusqueda);
                } 
            }
        });
        
    }
    
    private void mtdEventoBtnPrevia(){
        pnVentas.btnPrevia.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                pnVentas.btnPrevia.setEnabled(true);
                pnVentas.btnSiguiente.setEnabled(true);
                mtdMostrarProductosPrevias();
            }
        });
    }
    
    private void mtdEventoBtnSiguiente(){
        pnVentas.btnSiguiente.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                pnVentas.btnPrevia.setEnabled(true);
                pnVentas.btnSiguiente.setEnabled(true);
                mtdMostrarProductosSiguiente();
            }
        });
    }
    
    // ***** Métodos
    private void mtdInit(){
        LOG.info("Ejecutando metodo una vez (Obligatorio)");
        mtdEventoBtnBuscar();
        mtdEventoBtnSiguiente();
        mtdEventoBtnPrevia();
        mtdEventoCmpBuscarProducto();
        mtdMostrarVentas(false);
    }
    
    
    private void mtdMostrarVentas(boolean busqueda){
        LOG.info("Iniciando ...");
        
        int totalProductos = 0;
        pnVentas.pnContenedor.setLayout(new GridBagLayout());
        pnVentas.pnContenedor.removeAll();
        
        LOG.info("Listando ventas...");
        ventaDto.setVentVendedor( Veontec.usuarioDto.getCmpID() );
        
        if( busqueda == false ){
            lstMisVentas = ventaDao.mtdListarAllVentasPorUsuario(ventaDto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.valueOf(""+ventaDao.mtdRowCountAllVentasPorUsuario(ventaDto) );
        }else{
            ventaDto.setVentTitulo('%'+pnVentas.cmpBusqueda.getText()+'%');
            lstMisVentas = ventaDao.mtdBuscarAllVentasPorUsuarioSimilares(ventaDto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.valueOf(""+ventaDao.mtdRowCountAllVentasPorUsuarioSimilares(ventaDto) );
        }
        
        totalProductos = lstMisVentas.size();
        if(  totalProductos > 0 ){
            
            LOG.warning("Recorriendo productos ....");
            for (int i = 0; i < totalProductos; i++) {
                productoDto = new ProductoDto();
                productoDto.setProdID( lstMisVentas.get(i).getVentProducto() );
                productoDto.setProdUsuario( lstMisVentas.get(i).getVentVendedor() );
                productoDto = productoDao.mtdConsultar(productoDto);

                if( productoDto != null){ 
                    CtrlCardVenta tarjeta = new CtrlCardVenta(lstMisVentas.get(i), productoDto);
                    tarjeta.setItem(i);
                    tarjeta.mtdInit();
                    pnVentas.pnContenedor.add(tarjeta.getLaVista(), tarjeta.getTarjeta_dimensiones());
                }

            }
            
        }
        
        pnVentas.pnContenedor.validate();
        pnVentas.pnContenedor.revalidate();
        pnVentas.pnContenedor.repaint();
        
    }
    
    private void mtdMostrarProductosPrevias(){
        cantidadResultados -= cantidadPorPagina;
        //pagProductos = cantidadResultados <= 0 ? 0 : cantidadResultados;
        //LOG.info("Productos previas : " + cantidadResultados );
        
        if( cantidadResultados < 0  ){
            cantidadResultados = 0;
            JOptionPane.showMessageDialog(pnVentas, "No hay más resultados por mostrar.");
            pnVentas.btnPrevia.setEnabled(false);
            return;
        }
        
        pnVentas.btnSiguiente.setEnabled(true);
        mtdMostrarVentas(activarBusqueda);
        
    }
    
    private void mtdMostrarProductosSiguiente(){
        cantidadResultados += cantidadPorPagina;
        //pagProductos = cantidadResultados >= totalProductosExistentes ? totalProductosExistentes: cantidadResultados;
        //LOG.info("Productos siguientes : " + cantidadResultados );
        
        if( cantidadResultados >= totalProductosExistentes ){
            cantidadResultados = totalProductosExistentes;
            JOptionPane.showMessageDialog(pnVentas, "No hay más resultados por mostrar.");
            pnVentas.btnSiguiente.setEnabled(false);
            return;
        }
        
        pnVentas.btnPrevia.setEnabled(true);
        mtdMostrarVentas(activarBusqueda);
        
    }
    
    private void mtdEstabecerBusqueda(){
        pnVentas.btnPrevia.setEnabled(true);
        pnVentas.btnSiguiente.setEnabled(true);
        
        cantidadResultados=0;
        if( pnVentas.cmpBusqueda.getText().trim().isEmpty() || pnVentas.cmpBusqueda.isVacio() ){
            activarBusqueda = false;
        }else{
            activarBusqueda = true;
        }
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
