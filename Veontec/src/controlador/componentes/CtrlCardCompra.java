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

    
    // ***** Vista
    private PanelCardCompra pnCardCompra;
    
    // ***** Modelo
    private ProductoDto productoDto;
    private ProductoDao productoDao;
    private VentaDto ventaDto;
    private VentaDao ventaDao;
    private UsuarioDao usuarioDao;
    private UsuarioDto usuarioDto;
    
    private CompraDto compDto;
    private CompraDao compDao;
    
    // ***** Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    private ImageIcon portada;
    
    // ***** Constructor
    public CtrlCardCompra(CompraDto compDto) {
        this.pnCardCompra = new PanelCardCompra();
        this.compDto = compDto;
        this.compDao = new CompraDao();
        this.usuarioDao = new UsuarioDao();
        this.usuarioDto = new UsuarioDto();
        this.productoDao = new ProductoDao();
        this.productoDto = new ProductoDto();
        this.ventaDao = new VentaDao();
        this.ventaDto = new VentaDto();
        this.tarjeta_dimensiones = new GridBagConstraints();
    }
    
    // ***** Eventos
    private void mtdCrearEventoBtnCancelarCompra(){
        MouseListener eventoBtnComprar = null;
        pnCardCompra.btnCancelarCompra.removeMouseListener(eventoBtnComprar);
        
        eventoBtnComprar =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    mtdCancelarCompra();
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(pnCardCompra, "Producto no encontrado.");
                }
            }
        };
        
        pnCardCompra.btnCancelarCompra.addMouseListener(eventoBtnComprar);
    }
    
    private void mtdCrearEventoBtnPreguntar(){
        MouseListener eventoBtnHacePregunta = null;
        pnCardCompra.btnHacerPregunta.removeMouseListener(eventoBtnHacePregunta);
        
        eventoBtnHacePregunta =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    // * Llamar al controlador de accion para preguntar
                    CtrlModalHacerPregunta preguntar = new CtrlModalHacerPregunta(productoDto);
                    preguntar.mtdInit();
                    
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(pnCardCompra, "Producto no encontrado.");
                }
            }
        };
        
        pnCardCompra.btnHacerPregunta.addMouseListener(eventoBtnHacePregunta);
    }
    
    // ***** Métodos
    public void mtdInit(){
        
        if( compDto.getCompEstado() == 100 ){
            pnCardCompra.btnCancelarCompra.setVisible(false);
            pnCardCompra.btnHacerPregunta.setVisible(false);
        }else{
            mtdCrearEventoBtnCancelarCompra();
            mtdCrearEventoBtnPreguntar();
        }
        
        mtdEstablecerDatos();
        mtdEstablecerImagen();
        mtdEstablecerDimensiones();
    }
    
    private void mtdEstablecerDatos(){
        pnCardCompra.etqTitulo.setText( compDto.getCompTitulo() );
        pnCardCompra.cmpPrecioUnidad.setText( "" + compDto.getCompPrecio() );
        pnCardCompra.cmpStockCompra.setText( ""  + compDto.getCompCantidad() );
        pnCardCompra.cmpFecha.setText( compDto.getCompFecha() );
        
        // * El usuario actual es el comprador
        productoDto.setProdUsuario( compDto.getCompComprador() );
        
        // * Establecer la descripción
        productoDto = new ProductoDto();
        productoDto.setProdUsuario( compDto.getCompVendedor() );
        productoDto.setProdID( compDto.getCompProducto() );
        productoDto = productoDao.mtdConsultar(productoDto);
        pnCardCompra.cmpDetalleCompra.setText(productoDto == null ? "Sin Descripción" : productoDto.getProdDescripcion() );
        
        // * Establecer el vendedor
        //System.out.println("" + compDto.getCompVendedor());
        usuarioDto = usuarioDao.mtdConsultar(compDto.getCompVendedor());
        pnCardCompra.cmpVendedor.setText(usuarioDto == null ? "Desconocido " : usuarioDto.getCmpNombreCompleto() );
        
    }
    
    private void mtdEstablecerImagen(){
        productoDto = new ProductoDto();  
        productoDto.setProdUsuario( compDto.getCompVendedor() );
        productoDto.setProdID( compDto.getCompProducto() );
        productoDto = productoDao.mtdConsultar(productoDto);
        
        //System.out.println("********** mtdEstablecerImagen ## Inicio ## ");
        //System.out.println("CompraDto :: " + compDto.toString());
        //System.out.println("CompraDto :: " + productoDto.toString());
        //System.out.println("********** mtdEstablecerImagen ## Fin ## ");
        
        if( productoDto != null  ){
            if( productoDto.getProdImg() != null ){
                // * Establecer imagen de portada
                try {
                    byte[] img = productoDto.getProdImg();
                    BufferedImage buffimg = null;
                    InputStream inputimg = new ByteArrayInputStream(img);
                    buffimg = ImageIO.read(inputimg);
                    pnCardCompra.pnImgPortada.removeAll();
                    portada = new ImageIcon(buffimg.getScaledInstance(pnCardCompra.pnImgPortada.getWidth(), pnCardCompra.pnImgPortada.getHeight(), Image.SCALE_SMOOTH));
                    JLabel iconocc = new JLabel(portada);
                    iconocc.setBounds(0, 0, pnCardCompra.pnImgPortada.getWidth(), pnCardCompra.pnImgPortada.getHeight());
                    iconocc.setSize(pnCardCompra.pnImgPortada.getWidth(), pnCardCompra.pnImgPortada.getHeight());
                    iconocc.setLocation(0, 0);
                    iconocc.setPreferredSize(new Dimension(pnCardCompra.pnImgPortada.getWidth(), pnCardCompra.pnImgPortada.getHeight()));
                    pnCardCompra.pnImgPortada.add(iconocc);
                    //tarjeta.updateUI();
                    pnCardCompra.validate();
                    pnCardCompra.revalidate();
                    pnCardCompra.repaint();

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
        pnCardCompra.setVisible(true);
    }
    
    private void mtdCancelarCompra(){        
        compDto = compDao.mtdConsultar(compDto);
            
        if( compDto == null ){
            JOptionPane.showMessageDialog(pnCardCompra, "Compra no encontrado.");
            return;
        }
        
        int opc = JOptionPane.showConfirmDialog(pnCardCompra, 
                    "¿Seguro que deseas cancelar el producto?",
                    "Cancelar | " + compDto.getCompTitulo(),
                    JOptionPane.YES_NO_OPTION );
            
        if( opc == JOptionPane.YES_NO_OPTION ){
            productoDto.setProdStock(productoDto.getProdStock() + compDto.getCompCantidad() );
            ventaDto.setVentComprador( compDto.getCompComprador() );
            ventaDto.setVentVendedor( compDto.getCompVendedor() );
            ventaDto.setVentHashCode( compDto.getCompHashCode() );
            if( compDao.mtdRemover(compDto) && productoDao.mtdActualizar(productoDto) && ventaDao.mtdRemoverPorHashCode(ventaDto) ){
                CtrlCompras.mtdRecargarCompras();
                JOptionPane.showMessageDialog(pnCardCompra, "Producto cancelado exitosamente.");
            }
        }
        
    }
    
    public PanelCardCompra getLaVista() {
        return pnCardCompra;
    }

    public void setLaVista(PanelCardCompra laVista) {
        this.pnCardCompra = laVista;
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
