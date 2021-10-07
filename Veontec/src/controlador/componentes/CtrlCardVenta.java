package controlador.componentes;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dao.VentaDao;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import modelo.dto.VentaDto;
import vista.paneles.componentes.PanelCardVenta;

public class CtrlCardVenta {

    
    // * Vista
    private PanelCardVenta tarjeta;
    
    // * Modelo
    private VentaDto ventDto;
    private VentaDao ventDao;
    private ProductoDto prodDto;
    private ProductoDao prodDao;
    private UsuarioDto usuaDto;
    private UsuarioDao usuaDao;
    
    // * Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    
    // Constructor
    public CtrlCardVenta(VentaDto ventDto, ProductoDto prodDto) {
        this.tarjeta = new PanelCardVenta();
        this.ventDto = ventDto;
        this.ventDao = new VentaDao();
        this.prodDto = prodDto;
        this.prodDao = new ProductoDao();
        this.usuaDao = new UsuarioDao();
        this.usuaDto = new UsuarioDto();
        this.tarjeta_dimensiones = new GridBagConstraints();
    }
    
    // Eventos
    private void mtdCrearEventoBtnGuardar(){
        MouseListener eventoBtnComprar = null;
        tarjeta.etqTitulo.removeMouseListener(eventoBtnComprar);
        
        eventoBtnComprar =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                //System.out.println("" + prodDto.getProdTitulo());
            }
        };
        
        tarjeta.etqTitulo.addMouseListener(eventoBtnComprar);
    }
    
    // Métodos
    public void mtdInit(){
        mtdEstablecerDimensiones();
        mtdEstablecerOpciones();
        mtdCrearEventoBtnGuardar();
        mtdEstablecerDatos();
    }
    
    private void mtdEstablecerDatos(){
        String detalles = tarjeta.cmpDetalleVenta.getText();
        
        tarjeta.etqTitulo.setText(ventDto.getVentTitulo() );
        tarjeta.cmpPrecioUnidad.setText("" + ventDto.getVentPrecio() );
        tarjeta.cmpStockVendido.setText(""  + ventDto.getVentCantidad() );
        
        detalles = detalles.replaceAll("<FechaVendido>", ventDto.getVentFecha());
        
        // * Establecer el comprador
        usuaDto = usuaDao.mtdConsultar( ventDto.getVentComprador() );
        String comprador = usuaDto == null ? "Desconocido" : usuaDto.getCmpNombreCompleto();
        detalles = detalles.replaceAll("<Comprador>", comprador);
        
        // * Establecer descripción
        detalles = detalles.replaceAll("<Descripcion>", prodDto.getProdDescripcion());
        
        tarjeta.cmpDetalleVenta.setText(detalles);
        
    }
    
    private void mtdEstablecerOpciones(){
        //tarjeta.btnEliminar.setVisible(false);
        //tarjeta.btnEditar.setVisible(false);
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
    
    public PanelCardVenta getLaVista() {
        return tarjeta;
    }

    public void setLaVista(PanelCardVenta laVista) {
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
