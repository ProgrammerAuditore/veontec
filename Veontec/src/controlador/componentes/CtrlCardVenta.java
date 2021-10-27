package controlador.componentes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dao.VentaDao;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import modelo.dto.VentaDto;
import vista.paneles.componentes.PanelCardVenta;

public class CtrlCardVenta {

    
    // ***** Vista
    private PanelCardVenta pnCardVenta;
    
    // ***** Modelo
    private VentaDto ventaDto;
    private VentaDao ventaDao;
    private ProductoDto productoDto;
    private ProductoDao productoDao;
    private UsuarioDto usuarioDto;
    private UsuarioDao usuarioDao;
    
    // ***** Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    private ImageIcon portada;
    
    // ***** Constructor
    public CtrlCardVenta(VentaDto ventDto, ProductoDto prodDto) {
        this.pnCardVenta = new PanelCardVenta();
        this.ventaDto = ventDto;
        this.ventaDao = new VentaDao();
        this.productoDto = prodDto;
        this.productoDao = new ProductoDao();
        this.usuarioDao = new UsuarioDao();
        this.usuarioDto = new UsuarioDto();
        this.tarjeta_dimensiones = new GridBagConstraints();
    }
    
    // ***** Eventos
    private void mtdCrearEventoBtnGuardar(){
        MouseListener eventoBtnComprar = null;
        pnCardVenta.etqTitulo.removeMouseListener(eventoBtnComprar);
        
        eventoBtnComprar =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                //System.out.println("" + productoDto.getProdTitulo());
            }
        };
        
        pnCardVenta.etqTitulo.addMouseListener(eventoBtnComprar);
    }
    
    // ***** Métodos
    public void mtdInit(){
        mtdEstablecerDimensiones();
        mtdEstablecerOpciones();
        mtdCrearEventoBtnGuardar();
        mtdEstablecerDatos();
        mtdEstablecerImagen();
    }
    
    private void mtdEstablecerDatos(){
        String detalles = pnCardVenta.cmpDetalleVenta.getText();
        
        pnCardVenta.etqTitulo.setText(ventaDto.getVentTitulo() );
        pnCardVenta.cmpPrecioUnidad.setText("" + ventaDto.getVentPrecio() );
        pnCardVenta.cmpStockVendido.setText(""  + ventaDto.getVentCantidad() );
        
        detalles = detalles.replaceAll("<FechaVendido>", ventaDto.getVentFecha());
        
        // * Establecer el comprador
        usuarioDto = usuarioDao.mtdConsultar(ventaDto.getVentComprador() );
        String comprador = usuarioDto == null ? "Desconocido" : usuarioDto.getCmpNombreCompleto();
        detalles = detalles.replaceAll("<Comprador>", comprador);
        
        // * Establecer descripción
        detalles = detalles.replaceAll("<Descripcion>", productoDto.getProdDescripcion());
        
        pnCardVenta.cmpDetalleVenta.setText(detalles);
        
    }
    
    private void mtdEstablecerImagen(){
        productoDto = new ProductoDto();  
        productoDto.setProdUsuario(ventaDto.getVentVendedor()  );
        productoDto.setProdID(ventaDto.getVentProducto() );
        productoDto = productoDao.mtdConsultar(productoDto);
      
        if( productoDto != null  ){
            if( productoDto.getProdImg() != null ){
                // * Establecer imagen de portada
                try {
                    byte[] img = productoDto.getProdImg();
                    BufferedImage buffimg = null;
                    InputStream inputimg = new ByteArrayInputStream(img);
                    buffimg = ImageIO.read(inputimg);
                    pnCardVenta.pnImgPortada.removeAll();
                    portada = new ImageIcon(buffimg.getScaledInstance(pnCardVenta.pnImgPortada.getWidth(), pnCardVenta.pnImgPortada.getHeight(), Image.SCALE_SMOOTH));
                    JLabel iconocc = new JLabel(portada);
                    iconocc.setBounds(0, 0, pnCardVenta.pnImgPortada.getWidth(), pnCardVenta.pnImgPortada.getHeight());
                    iconocc.setSize(pnCardVenta.pnImgPortada.getWidth(), pnCardVenta.pnImgPortada.getHeight());
                    iconocc.setLocation(0, 0);
                    iconocc.setPreferredSize(new Dimension(pnCardVenta.pnImgPortada.getWidth(), pnCardVenta.pnImgPortada.getHeight()));
                    pnCardVenta.pnImgPortada.add(iconocc);
                    //tarjeta.updateUI();
                    pnCardVenta.validate();
                    pnCardVenta.revalidate();
                    pnCardVenta.repaint();

                } catch (Exception e) {
                }
            }
        }
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
        return pnCardVenta;
    }

    public void setLaVista(PanelCardVenta laVista) {
        this.pnCardVenta = laVista;
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
