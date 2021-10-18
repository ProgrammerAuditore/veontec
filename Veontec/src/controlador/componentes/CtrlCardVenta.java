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
    private PanelCardVenta tarjeta;
    
    // ***** Modelo
    private VentaDto ventDto;
    private VentaDao ventDao;
    private ProductoDto prodDto;
    private ProductoDao prodDao;
    private UsuarioDto usuaDto;
    private UsuarioDao usuaDao;
    
    // ***** Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    private ImageIcon portada;
    
    // ***** Constructor
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
    
    // ***** Eventos
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
    
    // ***** Métodos
    public void mtdInit(){
        mtdEstablecerDimensiones();
        mtdEstablecerOpciones();
        mtdCrearEventoBtnGuardar();
        mtdEstablecerDatos();
        mtdEstablecerImagen();
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
    
    private void mtdEstablecerImagen(){
        prodDto = new ProductoDto();  
        prodDto.setProdUsuario(  ventDto.getVentVendedor()  );
        prodDto.setProdID( ventDto.getVentProducto() );
        prodDto = prodDao.mtdConsultar(prodDto);
      
        if( prodDto != null  ){
            if( prodDto.getProdImg() != null ){
                // * Establecer imagen de portada
                try {
                    byte[] img = prodDto.getProdImg();
                    BufferedImage buffimg = null;
                    InputStream inputimg = new ByteArrayInputStream(img);
                    buffimg = ImageIO.read(inputimg);
                    tarjeta.pnImgPortada.removeAll();
                    portada = new ImageIcon(buffimg.getScaledInstance(tarjeta.pnImgPortada.getWidth(), tarjeta.pnImgPortada.getHeight(), Image.SCALE_SMOOTH));
                    JLabel iconocc = new JLabel(portada);
                    iconocc.setBounds(0, 0, tarjeta.pnImgPortada.getWidth(), tarjeta.pnImgPortada.getHeight());
                    iconocc.setSize(tarjeta.pnImgPortada.getWidth(), tarjeta.pnImgPortada.getHeight());
                    iconocc.setLocation(0, 0);
                    iconocc.setPreferredSize(new Dimension(tarjeta.pnImgPortada.getWidth(), tarjeta.pnImgPortada.getHeight()));
                    tarjeta.pnImgPortada.add(iconocc);
                    //tarjeta.updateUI();
                    tarjeta.validate();
                    tarjeta.revalidate();
                    tarjeta.repaint();

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
