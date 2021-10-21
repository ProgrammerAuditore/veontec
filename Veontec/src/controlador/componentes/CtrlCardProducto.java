package controlador.componentes;

import controlador.acciones.CtrlModalComprarProducto;
import controlador.acciones.CtrlModalHacerPregunta;
import index.Veontec;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import vista.paneles.componentes.PanelCardProducto;

public class CtrlCardProducto {
    // ***** Vista
    private PanelCardProducto pnCardProducto;
    
    // ***** Modelo
    private ProductoDto productoDto;
    private ProductoDao productoDao;
    private UsuarioDto usuarioDto;
    private UsuarioDao usuarioDao;
    
    // ***** Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    private Integer columna;
    private ImageIcon portada;
    
    // ***** Constructor
    public CtrlCardProducto(ProductoDto prodDto, ProductoDao prodDao) {
        this.productoDto = prodDto;
        this.productoDao = prodDao;
        this.pnCardProducto = new PanelCardProducto();
        this.tarjeta_dimensiones = new GridBagConstraints();
        this.usuarioDao = new UsuarioDao();
        this.usuarioDto = new UsuarioDto();
    }
    
    // ***** Eventos
    private void mtdCrearEventoBtnComprar(){
        pnCardProducto.btnComprar.setTexto("Comparar");
        
        if( Objects.equals(Veontec.usuarioDto.getCmpID(), productoDto.getProdUsuario()) ){
            pnCardProducto.btnComprar.setEnabled(false);
            return;
        }
        
        MouseListener eventoBtnComprar = null;
        pnCardProducto.btnComprar.removeMouseListener(eventoBtnComprar);
        
        eventoBtnComprar = new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                // * Llamar al controlador de accion para comprar 
                CtrlModalComprarProducto comprar = new CtrlModalComprarProducto(productoDto);
                comprar.mtdInit();
            }
        };
        
        pnCardProducto.btnComprar.addMouseListener(eventoBtnComprar);
    }
    
    private void mtdCrearEventoBtnPreguntar(){
        pnCardProducto.btnPreguntar.setTexto("Preguntar");
        
        if( Objects.equals(Veontec.usuarioDto.getCmpID(), productoDto.getProdUsuario()) ){
            pnCardProducto.btnPreguntar.setEnabled(false);
            return;
        }
        
        MouseListener eventoBtnPregunta = null;
        pnCardProducto.btnPreguntar.removeMouseListener(eventoBtnPregunta);
        
        eventoBtnPregunta = new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                // * Llamar al controlador de accion para preguntar
                CtrlModalHacerPregunta preguntar = new CtrlModalHacerPregunta(productoDto);
                preguntar.mtdInit();
            }
        };
        
        pnCardProducto.btnPreguntar.addMouseListener(eventoBtnPregunta);
    }
    
    private void mtdCrearEventoBtnVerProducto(){
        pnCardProducto.btnComprar.setTexto("Ver producto");
        
        if( Objects.equals(Veontec.usuarioDto.getCmpID(), productoDto.getProdUsuario()) ){
            pnCardProducto.btnComprar.setEnabled(false);
            return;
        }
        
        MouseListener eventoBtnPregunta = null;
        pnCardProducto.btnComprar.removeMouseListener(eventoBtnPregunta);
        
        eventoBtnPregunta = new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdVerProducto();
            }
        };
        
        pnCardProducto.btnComprar.addMouseListener(eventoBtnPregunta);
    }
    
    // ***** Métodos
    public void mtdInit(){
        mtdEstablecerImagen();
        mtdEstablecerOpciones();
        
        // * Establecer boton
        if( productoDto.getProdTipo() == 1 ){
            pnCardProducto.mtdBackgroundProductoInterno();
            mtdCrearEventoBtnComprar();
        }else{
            pnCardProducto.mtdBackgroundProductoExterno();
            mtdCrearEventoBtnVerProducto();
        }
        
        mtdCrearEventoBtnPreguntar();
        mtdEstablecerDatos();
        mtdEstablecerDimensiones();
    }
    
    private void mtdEstablecerDatos(){
        pnCardProducto.etqTitulo.setText(productoDto.getProdTitulo() );
        pnCardProducto.cmpPrecioUnidad.setText("" + productoDto.getProdPrecio() );
        pnCardProducto.cmpStockDisponible.setText(""  + productoDto.getProdStock());
        
        String detalles = pnCardProducto.cmpDetalleProducto.getText();
        String vendedor = "";
        
        // Establecer el vendedor 
        usuarioDto = usuarioDao.mtdConsultar(productoDto.getProdUsuario());
        vendedor = usuarioDto == null ? "Desconocido" : usuarioDto.getCmpNombreCompleto();
        pnCardProducto.cmpVendedor.setText(vendedor);

        // Establecer descripcion
        detalles = detalles.replaceAll("<Descripcion>", productoDto.getProdDescripcion());
        
        // * Establecer los detalles del producto
        pnCardProducto.cmpDetalleProducto.setText(detalles);
    }
    
    private void mtdEstablecerImagen(){
        if( productoDto.getProdImg() != null ){
            // * Establecer imagen de portada
            try {
                byte[] img = productoDto.getProdImg();
                BufferedImage buffimg = null;
                InputStream inputimg = new ByteArrayInputStream(img);
                buffimg = ImageIO.read(inputimg);
                pnCardProducto.pnImgPortada.removeAll();
                portada = new ImageIcon(buffimg.getScaledInstance(pnCardProducto.pnImgPortada.getWidth(), pnCardProducto.pnImgPortada.getHeight(), Image.SCALE_SMOOTH));
                JLabel iconocc = new JLabel(portada);
                iconocc.setBounds(0, 0, pnCardProducto.pnImgPortada.getWidth(), pnCardProducto.pnImgPortada.getHeight());
                iconocc.setSize(pnCardProducto.pnImgPortada.getWidth(), pnCardProducto.pnImgPortada.getHeight());
                iconocc.setLocation(0, 0);
                iconocc.setPreferredSize(new Dimension(pnCardProducto.pnImgPortada.getWidth(), pnCardProducto.pnImgPortada.getHeight()));
                pnCardProducto.pnImgPortada.add(iconocc);
                //tarjeta.updateUI();
                pnCardProducto.validate();
                pnCardProducto.revalidate();
                pnCardProducto.repaint();
                
            } catch (Exception e) {
            }
        }
    }
    
    private void mtdEstablecerOpciones(){
        //tarjeta.btnHacerCompra.setTexto("Hacer compra");
        //tarjeta.btnHacerPregunta.setTexto("Hacer pregunta");
    }

    private void mtdEstablecerDimensiones(){
        tarjeta_dimensiones.gridx = columna; // Columna 
        tarjeta_dimensiones.gridy = item; // Fila
        tarjeta_dimensiones.gridheight = 1; // Cantidad de columnas a ocupar
        tarjeta_dimensiones.gridwidth = 1; // Cantidad de filas a ocupar
        tarjeta_dimensiones.weightx = 0.0; // Estirar en ancho
        tarjeta_dimensiones.weighty = 0.0;// Estirar en alto
        tarjeta_dimensiones.insets = new Insets(20, 10, 20, 10);  //top padding
        tarjeta_dimensiones.fill = GridBagConstraints.BOTH; // El modo de estirar
        pnCardProducto.setVisible(true);
    }
    
    private void mtdVerProducto(){
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(productoDto.getProdEnlace()));
            } catch (IOException | URISyntaxException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + productoDto.getProdEnlace());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }
        }
    }
    
    public PanelCardProducto getLaVista() {
        return pnCardProducto;
    }

    public void setLaVista(PanelCardProducto laVista) {
        this.pnCardProducto = laVista;
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

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }
    
}
