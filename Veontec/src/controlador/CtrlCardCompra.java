package controlador;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import modelo.dao.CompraDao;
import modelo.dao.ProductoDao;
import modelo.dto.CompraDto;
import modelo.dto.ProductoDto;
import vista.paneles.PanelCardCompra;
import vista.paneles.PanelCardMiProducto;

public class CtrlCardCompra {

    
    // * Vista
    private PanelCardCompra tarjeta;
    
    // * Modelo
    private ProductoDto prodDto;
    private ProductoDao prodDao;
    private CompraDto compDto;
    private CompraDao compDao;
    
    // * Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    
    // Constructor
    public CtrlCardCompra(ProductoDto prodDto, CompraDto compDto) {
        this.tarjeta = new PanelCardCompra();
        this.prodDto = prodDto;
        this.compDto = compDto;
        this.tarjeta_dimensiones = new GridBagConstraints();
    }
    
    // Eventos
    private void mtdCrearEventoBtnGuardar(){
        MouseListener eventoBtnComprar = null;
        tarjeta.btnCancelarCompra.removeMouseListener(eventoBtnComprar);
        
        eventoBtnComprar =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("" + prodDto.getProdTitulo());
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
        tarjeta.etqTitulo.setText( prodDto.getProdTitulo() );
        tarjeta.cmpPrecioUnidad.setText( "" + compDto.getCompPrecio() );
        tarjeta.cmpStockCompra.setText( ""  + compDto.getCompCantidad());
        
        // * Establecer los detalles de compra
        String detalles_de_compra = tarjeta.cmpDetalleCompra.getText();
        detalles_de_compra = detalles_de_compra.replaceAll("<FechaDeCompra>", compDto.getCompFecha());
        detalles_de_compra = detalles_de_compra.replaceAll("<Descripcion>", prodDto.getProdDescripcion());
        tarjeta.cmpDetalleCompra.setText(detalles_de_compra);
        
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
