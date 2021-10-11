package controlador;

import controlador.componentes.CtrlCardCompra;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.JDialog;
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

    // Constructor
    public CtrlCompras(PanelCompras laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuario_dto = dto;
        this.usuario_dao = dao;
        producto_dao = new ProductoDao();
        producto_dto = new ProductoDto();
        compra_dao = new CompraDao();
        compra_dto = new CompraDto();
        
    }
    
    // Obtener instancia | Singleton
    public static CtrlCompras getInstancia(PanelCompras laVista, UsuarioDto dto, UsuarioDao dao){
        logger.info("Inicializando controlador");
        
        if( instancia == null ){
            logger.warn("Creando instancia");
            instancia = new CtrlCompras(laVista, dto, dao);
            instancia.mtdInit();
        
        }else{
            instancia.mtdMostrarProducto();
        
        }
        
        return instancia;
    }
    
    // Eventos
    // Vacio
    
    // Métodos
    private void mtdInit(){
        logger.info("Ejecutando metodo una vez (obligatorio)");
    }
    
    public static boolean mtdRecargarCompras(){
        logger.warn("Ejecutando metodo ");
        //if( instancia == null ){
            //instancia.mtdInit();
        //}
        
        instancia.mtdMostrarProducto();
        return true;
    }
    
    private void mtdMostrarProducto(){
        logger.info("Iniciando...");
        //lstMisProductos.clear();
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        
        // El usuario actual es el comprador
        compra_dto.setCompComprador( usuario_dto.getCmpID() );

        logger.info("listando...");
        lstMisCompras = compra_dao.mtdListar(compra_dto);
        int totalProductos = lstMisCompras.size();
        
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
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
