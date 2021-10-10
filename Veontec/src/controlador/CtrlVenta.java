package controlador;

import controlador.componentes.CtrlCardVenta;
import index.Veontec;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.tree.DefaultMutableTreeNode;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dao.VentaDao;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import modelo.dto.VentaDto;
import vista.paneles.PanelVentas;

public class CtrlVenta{
    
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
    private static CtrlVenta instancia;
    private List<VentaDto> lstMisVentas;

    // Constructor
    public CtrlVenta(PanelVentas laVista, UsuarioDto dto, UsuarioDao dao) {
        this.laVista = laVista;
        this.usuario_dto = dto;
        this.usuario_dao = dao;
        producto_dao = new ProductoDao();
        producto_dto = new ProductoDto();
        ventDao = new VentaDao();
        ventDto = new VentaDto();
        
    }
    
    // Obtener instancia | Singleton
    public static CtrlVenta getInstancia(PanelVentas laVista, UsuarioDto dto, UsuarioDao dao){
        if( instancia == null ){
            instancia = new CtrlVenta(laVista, dto, dao);
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
    
    
    private void mtdMostrarProducto(){
        //lstMisProductos.clear();
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        
        ventDto.setVentVendedor( Veontec.usuarioDto.getCmpID() );
        lstMisVentas = ventDao.mtdListar(ventDto);
        int totalProductos = lstMisVentas.size();
        
        if( totalProductos == 0 ){
            System.out.println(" No hay producto para mostrar. ");
            return;
        }
        
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
        
        laVista.pnContenedor.validate();
        laVista.pnContenedor.revalidate();
        laVista.pnContenedor.repaint();
        
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
