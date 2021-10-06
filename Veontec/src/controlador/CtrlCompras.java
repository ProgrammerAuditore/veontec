package controlador;

import controlador.componentes.CtrlCardCompra;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.tree.DefaultMutableTreeNode;
import modelo.dao.CompraDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.CompraDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import vista.paneles.PanelCompras;
import vista.paneles.PanelBienvenida;

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
        if( instancia == null ){
            instancia = new CtrlCompras(laVista, dto, dao);
            instancia.mtdInit();
        }
        
        instancia.mtdMostrarProducto();
        return instancia;
    }
    
    // Eventos
    // Vacio
    
    // Métodos
    private void mtdInit(){
    }
    
    public static boolean mtdRecargarCompras(){
        //if( instancia == null ){
            //instancia.mtdInit();
        //}
        
        instancia.mtdMostrarProducto();
        return true;
    }
    
    private void mtdMostrarProducto(){
        //lstMisProductos.clear();
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        
        // El usuario actual es el comprador
        compra_dto.setCompComprador( usuario_dto.getCmpID() );

        lstMisCompras = compra_dao.mtdListar(compra_dto);
        int totalProductos = lstMisCompras.size();
        
        if( totalProductos == 0 ){
            System.out.println(" No hay producto para mostrar. ");
        
        }else{
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
    
    
    
}
