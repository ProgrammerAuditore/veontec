package controlador.tabs;

import controlador.componentes.CtrlCardVenta;
import index.Veontec;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dao.VentaDao;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import modelo.dto.VentaDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import src.Info;
import vista.paneles.PanelVentas;

public class CtrlVentas{
    
    // Vista
    public PanelVentas laVista;
    
    // Modelos
    private UsuarioDto usuario_dto;
    private UsuarioDao usuario_dao;
    private ProductoDao producto_dao;
    private ProductoDto producto_dto;
    private VentaDto ventDto;
    private VentaDao ventDao;
    private DefaultMutableTreeNode treeNode1;
    
    // Atributos
    static Log logger = LogFactory.getLog(CtrlVentas.class);
    private static CtrlVentas instancia;
    private List<VentaDto> lstMisVentas;
    private Integer cantidadResultados;
    private Integer totalProductosExistentes;
    private Integer cantidadPorPagina;
    private boolean activarBusqueda;

    // Constructor
    public CtrlVentas(PanelVentas laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuario_dto = dto;
        this.usuario_dao = dao;
        this.producto_dao = new ProductoDao();
        this.producto_dto = new ProductoDto();
        this.ventDao = new VentaDao();
        this.ventDto = new VentaDto();
        this.cantidadResultados = 0;
        this.cantidadPorPagina = Info.veontecResultadoPorPagina;
        this.activarBusqueda = false;
    }
    
    // Obtener instancia | Singleton
    public static CtrlVentas getInstancia(PanelVentas laVista, UsuarioDto dto, UsuarioDao dao){
        logger.warn("Inicializando controlador.... ");
        if( instancia == null ){
            logger.warn("Creando instancia.... ");
            instancia = new CtrlVentas(laVista, dto, dao);
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
                laVista.btnPrevia.setEnabled(true);
                laVista.btnSiguiente.setEnabled(true);
                mtdMostrarProductosPrevias();
            }
        });
    }
    
    private void mtdEventoBtnSiguiente(){
        laVista.btnSiguiente.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                laVista.btnPrevia.setEnabled(true);
                laVista.btnSiguiente.setEnabled(true);
                mtdMostrarProductosSiguiente();
            }
        });
    }
    
    // Métodos
    private void mtdInit(){
        logger.info("Ejecutando metodo una vez (Obligatorio)");
        mtdEventoBtnBuscar();
        mtdEventoBtnSiguiente();
        mtdEventoBtnPrevia();
        mtdEventoCmpBuscarProducto();
        mtdMostrarProducto(false);
    }
    
    
    private void mtdMostrarProducto(boolean busqueda){
        logger.info("Iniciando ...");
        
        int totalProductos = 0;
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        logger.info("Listando ventas...");
        ventDto.setVentVendedor( Veontec.usuarioDto.getCmpID() );
        
        if( busqueda == false ){
            lstMisVentas = ventDao.mtdListar(ventDto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.valueOf( ""+ventDao.mtdRowCount(ventDto) );
        }else{
            ventDto.setVentTitulo('%'+laVista.cmpBusqueda.getText()+'%');
            lstMisVentas = ventDao.mtdBuscarAllVentasPorUsuario(ventDto, cantidadPorPagina, cantidadResultados);
            totalProductosExistentes = Integer.valueOf( ""+ventDao.mtdRowCountAllVentasPorUsuario(ventDto) );
        }
        
        totalProductos = lstMisVentas.size();
        if(  totalProductos > 0 ){
            
            logger.warn("Recorriendo productos ....");
            for (int i = 0; i < totalProductos; i++) {
                producto_dto = new ProductoDto();
                producto_dto.setProdID( lstMisVentas.get(i).getVentProducto() );
                producto_dto.setProdUsuario( lstMisVentas.get(i).getVentVendedor() );
                producto_dto = producto_dao.mtdConsultar(producto_dto);

                if( producto_dto != null){ 
                    CtrlCardVenta tarjeta = new CtrlCardVenta(lstMisVentas.get(i), producto_dto);
                    tarjeta.setItem(i);
                    tarjeta.mtdInit();
                    laVista.pnContenedor.add(tarjeta.getLaVista(), tarjeta.getTarjeta_dimensiones());
                }

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
