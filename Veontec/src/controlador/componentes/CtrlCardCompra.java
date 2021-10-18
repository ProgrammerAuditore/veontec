package controlador.componentes;

import controlador.tabs.CtrlCompras;
import controlador.acciones.CtrlModalHacerPregunta;
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
import javax.swing.JOptionPane;
import modelo.dao.CompraDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dao.VentaDao;
import modelo.dto.CompraDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import modelo.dto.VentaDto;
import vista.paneles.componentes.PanelCardCompra;

public class CtrlCardCompra {

    
    // * Vista
    private PanelCardCompra tarjeta;
    
    // * Modelo
    private ProductoDto prodDto;
    private ProductoDao prodDao;
    private VentaDto ventaDto;
    private VentaDao ventaDao;
    private UsuarioDao usuaDao;
    private UsuarioDto usuaDto;
    
    private CompraDto compDto;
    private CompraDao compDao;
    
    // * Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    private ImageIcon portada;
    
    // Constructor
    public CtrlCardCompra(CompraDto compDto) {
        this.tarjeta = new PanelCardCompra();
        this.compDto = compDto;
        this.compDao = new CompraDao();
        this.usuaDao = new UsuarioDao();
        this.usuaDto = new UsuarioDto();
        this.prodDao = new ProductoDao();
        this.prodDto = new ProductoDto();
        this.ventaDao = new VentaDao();
        this.ventaDto = new VentaDto();
        this.tarjeta_dimensiones = new GridBagConstraints();
    }
    
    // Eventos
    private void mtdCrearEventoBtnCancelarCompra(){
        MouseListener eventoBtnComprar = null;
        tarjeta.btnCancelarCompra.removeMouseListener(eventoBtnComprar);
        
        eventoBtnComprar =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    mtdCancelarCompra();
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(tarjeta, "Producto no encontrado.");
                }
            }
        };
        
        tarjeta.btnCancelarCompra.addMouseListener(eventoBtnComprar);
    }
    
    private void mtdCrearEventoBtnPreguntar(){
        MouseListener eventoBtnHacePregunta = null;
        tarjeta.btnHacerPregunta.removeMouseListener(eventoBtnHacePregunta);
        
        eventoBtnHacePregunta =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    // * Llamar al controlador de accion para preguntar
                    CtrlModalHacerPregunta preguntar = new CtrlModalHacerPregunta(prodDto);
                    preguntar.mtdInit();
                    
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(tarjeta, "Producto no encontrado.");
                }
            }
        };
        
        tarjeta.btnHacerPregunta.addMouseListener(eventoBtnHacePregunta);
    }
    
    // Métodos
    public void mtdInit(){
        
        if( compDto.getCompEstado() == 100 ){
            tarjeta.btnCancelarCompra.setVisible(false);
            tarjeta.btnHacerPregunta.setVisible(false);
        }else{
            mtdCrearEventoBtnCancelarCompra();
            mtdCrearEventoBtnPreguntar();
        }
        
        mtdEstablecerDatos();
        mtdEstablecerImagen();
        mtdEstablecerDimensiones();
    }
    
    private void mtdEstablecerDatos(){
        tarjeta.etqTitulo.setText( compDto.getCompTitulo() );
        tarjeta.cmpPrecioUnidad.setText( "" + compDto.getCompPrecio() );
        tarjeta.cmpStockCompra.setText( ""  + compDto.getCompCantidad() );
        tarjeta.cmpFecha.setText( compDto.getCompFecha() );
        
        // * El usuario actual es el comprador
        prodDto.setProdUsuario( compDto.getCompComprador() );
        
        // * Establecer la descripción
        prodDto = new ProductoDto();
        prodDto.setProdUsuario( compDto.getCompVendedor() );
        prodDto.setProdID( compDto.getCompProducto() );
        prodDto = prodDao.mtdConsultar(prodDto);
        tarjeta.cmpDetalleCompra.setText( prodDto == null ? "Sin Descripción" : prodDto.getProdDescripcion() );
        
        // * Establecer el vendedor
        //System.out.println("" + compDto.getCompVendedor());
        usuaDto = usuaDao.mtdConsultar(compDto.getCompVendedor());
        tarjeta.cmpVendedor.setText( usuaDto == null ? "Desconocido " : usuaDto.getCmpNombreCompleto() );
        
    }
    
    private void mtdEstablecerImagen(){
        prodDto = new ProductoDto();  
        prodDto.setProdUsuario( compDto.getCompVendedor() );
        prodDto.setProdID( compDto.getCompProducto() );
        prodDto = prodDao.mtdConsultar(prodDto);
        
        //System.out.println("********** mtdEstablecerImagen ## Inicio ## ");
        //System.out.println("CompraDto :: " + compDto.toString());
        //System.out.println("CompraDto :: " + prodDto.toString());
        //System.out.println("********** mtdEstablecerImagen ## Fin ## ");
        
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

    private void mtdEstablecerDimensiones(){
        tarjeta_dimensiones.gridx = 0; // Columna 
        tarjeta_dimensiones.gridy = item; // Fila
        tarjeta_dimensiones.gridheight = 1; // Cantidad de columnas a ocupar
        tarjeta_dimensiones.gridwidth = 1; // Cantidad de filas a ocupar
        tarjeta_dimensiones.weightx = 0.0; // Estirar en ancho
        tarjeta_dimensiones.weighty = 0.0;// Estirar en alto
        tarjeta_dimensiones.insets = new Insets(30, 0, 30, 0);  //top padding
        tarjeta_dimensiones.fill = GridBagConstraints.BOTH; // El modo de estirar
        tarjeta.setVisible(true);
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
            prodDto.setProdStock( prodDto.getProdStock() + compDto.getCompCantidad() );
            ventaDto.setVentComprador( compDto.getCompComprador() );
            ventaDto.setVentVendedor( compDto.getCompVendedor() );
            ventaDto.setVentHashCode( compDto.getCompHashCode() );
            if( compDao.mtdRemover(compDto) && prodDao.mtdActualizar(prodDto) && ventaDao.mtdRemoverPorHashCode(ventaDto) ){
                CtrlCompras.mtdRecargarCompras();
                JOptionPane.showMessageDialog(tarjeta, "Producto cancelado exitosamente.");
            }
        }
        
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
