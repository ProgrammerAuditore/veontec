package controlador;

import controlador.componentes.CtrlCardProducto;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
                mtdMostrarProducto(true);
            }
        });
    }
    
    private void mtdEventoCmpBuscarProducto(){
        laVista.cmpBuscarProducto.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                    mtdMostrarProducto(true);
                } 
            }
        });
        
    }
    
    // Métodos
    private void mtdInit(){
        logger.info("Ejecutando || CtrlBienvenida::mtdInit ");
        mtdEventoBtnBuscar();
        mtdEventoCmpBuscarProducto();
        mtdMostrarProducto(false);
    }
    
    public static boolean mtdRecargar(){
        instancia.mtdMostrarProducto(false);
        return true;
    }
    
    private synchronized void mtdMostrarProducto(boolean busqueda){
        logger.warn("Inicializando ... ");
        
        //lstMisProductos.clear();
        int totalProductos=0;
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        if( busqueda == false ){
            lstMisProductos = producto_dao.mtdListar();
        }else{
            producto_dto.setProdTitulo('%'+laVista.cmpBuscarProducto.getText().trim()+'%');
            lstMisProductos = producto_dao.mtdListarBuscarProducto(producto_dto, 10, 0);
        }
        
        totalProductos = lstMisProductos.size();
        if( totalProductos > 0 ){
        
            int columna = 0, fila = 0; // Establecer contadores para columnas y filas
            int productos = 3; // Establecer cantida de producto a mostrar por fila
            logger.warn(" Recorriendo productos. ");
            for (int i = 0; i < totalProductos; i++) {
                CtrlCardProducto tarjeta = new CtrlCardProducto(lstMisProductos.get(i), producto_dao);
                tarjeta.setItem(fila);
                tarjeta.setColumna(columna);

                tarjeta.mtdInit();
                logger.warn(" Agregando producto: #" + i);
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
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
