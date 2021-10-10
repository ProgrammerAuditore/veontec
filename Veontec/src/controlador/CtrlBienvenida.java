package controlador;

import controlador.componentes.CtrlCardProducto;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.tree.DefaultMutableTreeNode;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    static Log logger = LogFactory.getLog(CtrlBienvenida.class);

    // Constructor
    public CtrlBienvenida(PanelBienvenida laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuario_dto = dto;
        this.usuario_dao = dao;
        producto_dao = new ProductoDao();
        producto_dto = new ProductoDto();
        
    }
    
    // Obtener instancia | Singleton
    public static CtrlBienvenida getInstancia(PanelBienvenida laVista, UsuarioDto dto, UsuarioDao dao){
        logger.info("Inicializando || CtrlBienvenida::getInstancia ");
        if( instancia == null ){
            logger.info("Creado || CtrlBienvenida::getInstancia ");
            instancia = new CtrlBienvenida(laVista, dto, dao);
        }
        
        
        instancia.mtdInit();
        return instancia;
    }
    
    // Eventos
    // Vacio
    
    // Métodos
    private void mtdInit(){
        logger.info("Ejecutando || CtrlBienvenida::mtdInit ");
        mtdMostrarProducto();
    }
    
    
    private void mtdMostrarProducto(){
        logger.warn("Inicializando ... ");
        //lstMisProductos.clear();
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        lstMisProductos = producto_dao.mtdListar();
        int totalProductos = lstMisProductos.size();
        
        if( totalProductos == 0 ){
            System.out.println(" No hay producto para mostrar. ");
            logger.warn(" No hay producto para mostrar. ");
            return;
        }
        
        int columna = 0;
        int col_total = 3;
        int fila = 0;
        logger.warn(" Recorriendo productos. ");
        for (int i = 0; i < totalProductos; i++) {
            CtrlCardProducto tarjeta = new CtrlCardProducto(lstMisProductos.get(i), producto_dao);
            tarjeta.setItem(fila);
            tarjeta.setColumna(columna);
            
            tarjeta.mtdInit();
            logger.warn(" Agregando producto: #" + i);
            laVista.pnContenedor.add(tarjeta.getLaVista(), tarjeta.getTarjeta_dimensiones());
            columna++;
            
            if( columna >= col_total ){
                columna = 0;
                fila++;
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
