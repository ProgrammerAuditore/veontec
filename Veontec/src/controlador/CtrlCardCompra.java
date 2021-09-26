package controlador;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import modelo.dao.CompraDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.CompraDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import vista.paneles.PanelCardCompra;

public class CtrlCardCompra {

    
    // * Vista
    private PanelCardCompra tarjeta;
    
    // * Modelo
    private ProductoDto prodDto;
    private ProductoDao prodDao;
    private UsuarioDao usuaDao;
    private UsuarioDto usuaDto;
    
    private CompraDto compDto;
    private CompraDao compDao;
    
    // * Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    
    // Constructor
    public CtrlCardCompra(CompraDto compDto) {
        this.tarjeta = new PanelCardCompra();
        this.compDto = compDto;
        this.compDao = new CompraDao();
        this.usuaDao = new UsuarioDao();
        this.usuaDto = new UsuarioDto();
        this.prodDao = new ProductoDao();
        this.prodDto = new ProductoDto();
        this.tarjeta_dimensiones = new GridBagConstraints();
    }
    
    // Eventos
    private void mtdCrearEventoBtnGuardar(){
        MouseListener eventoBtnComprar = null;
        tarjeta.btnCancelarCompra.removeMouseListener(eventoBtnComprar);
        
        eventoBtnComprar =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCancelarCompra();
            }
        };
        
        tarjeta.btnCancelarCompra.addMouseListener(eventoBtnComprar);
    }
    
    // Métodos
    public void mtdInit(){
        
        if( compDto.getCompEstado() == 100 ){
            tarjeta.btnCancelarCompra.setVisible(false);
            tarjeta.btnHacerPregunta.setVisible(false);
        }else{
            mtdCrearEventoBtnGuardar();
        }
        
        mtdEstablecerDimensiones();
        mtdEstablecerDatos();
    }
    
    private void mtdEstablecerDatos(){
        tarjeta.etqTitulo.setText( compDto.getCompTitulo() );
        tarjeta.cmpPrecioUnidad.setText( "" + compDto.getCompPrecio() );
        tarjeta.cmpStockCompra.setText( ""  + compDto.getCompCantidad() );
        tarjeta.cmpFecha.setText( compDto.getCompFecha() );
        
        // * El usuario actual es el comprador
        prodDto.setProdUsuario( compDto.getCompComprador() );
        
        // * Establecer la descripción
        prodDto.setProdID( compDto.getCompID() );
        prodDto = prodDao.mtdConsultar(prodDto);
        tarjeta.cmpDetalleCompra.setText( prodDto == null ? "Sin Descripción" : prodDto.getProdDescripcion() );
        
        // * Establecer el vendedor
        System.out.println("" + compDto.getCompVendedor());
        usuaDto = usuaDao.mtdConsultar(compDto.getCompVendedor());
        tarjeta.cmpVendedor.setText( usuaDto == null ? "Desconocido " : usuaDto.getCmpNombreCompleto() );
        
    }

    private void mtdEstablecerDimensiones(){
        tarjeta_dimensiones.gridx = 0; // Columna 
        tarjeta_dimensiones.gridy = item; // Fila
        tarjeta_dimensiones.gridheight = 1; // Cantidad de columnas a ocupar
        tarjeta_dimensiones.gridwidth = 1; // Cantidad de filas a ocupar
        tarjeta_dimensiones.weightx = 0.0; // Estirar en ancho
        tarjeta_dimensiones.weighty = 0.0;// Estirar en alto
        tarjeta_dimensiones.insets = new Insets(30, 0, 30, 0);  //top padding
        tarjeta_dimensiones.fill = GridBagConstraints.BOTH; // El modo de estirar
        //tarjeta.setVisible(true);
    }
    
    private void mtdCancelarCompra(){        
        compDto = compDao.mtdConsultar(compDto);
            
        if( compDto == null ){
            JOptionPane.showMessageDialog(tarjeta, "Compra no encontrado.");
            return;
        }
        
        int opc = JOptionPane.showConfirmDialog(tarjeta, 
                    "¿Seguro que deseas cancelar el producto?",
                    "Cancelar | " + compDto.getCompTitulo(),
                    JOptionPane.YES_NO_OPTION );
            
        if( opc == JOptionPane.YES_NO_OPTION ){
            if( compDao.mtdRemover(compDto) ){
                CtrlCompras.mtdRecargarCompras();
                JOptionPane.showMessageDialog(tarjeta, "Producto cancelado exitosamente.");
            }
        }
        
        /*
        if( prodDto != null ){
            int opc = JOptionPane.showConfirmDialog(tarjeta, 
                    "¿Seguro que deseas cancelar el producto?",
                    "Cancelar | " + prodDto.getProdTitulo(),
                    JOptionPane.YES_NO_OPTION );
            
            if( opc == JOptionPane.YES_NO_OPTION ){
                
            }
            
        }else{
            JOptionPane.showMessageDialog(tarjeta, "No hay registro sobre el producto.");
        }
        */
        
    }
    
    public PanelCardCompra getLaVista() {
        return tarjeta;
    }

    public void setLaVista(PanelCardCompra laVista) {
        this.tarjeta = laVista;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public GridBagConstraints getTarjeta_dimensiones() {
        return tarjeta_dimensiones;
    }

    public void setTarjeta_dimensiones(GridBagConstraints tarjeta_dimensiones) {
        this.tarjeta_dimensiones = tarjeta_dimensiones;
    }
    
}
