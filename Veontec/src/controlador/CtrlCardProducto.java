package controlador;

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
import modelo.dto.ProductoDto;
import vista.paneles.PanelCardProducto;

public class CtrlCardProducto {
    // * Vista
    private PanelCardProducto tarjeta;
    
    // * Modelo
    private ProductoDto prodDto;
    private ProductoDao prodDao;
    
    // * Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    private Integer columna;
    private ImageIcon portada;
    
    // Constructor
    public CtrlCardProducto(ProductoDto prodDto, ProductoDao prodDao) {
        this.prodDto = prodDto;
        this.prodDao = prodDao;
        this.tarjeta = new PanelCardProducto();
        this.tarjeta_dimensiones = new GridBagConstraints();
    }
    
    // Eventos
    private void mtdCrearEventoBtnComprar(){
        MouseListener eventoBtnComprar = null;
        tarjeta.btnHacerCompra.removeMouseListener(eventoBtnComprar);
        
        eventoBtnComprar = new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                CtrlModalComprar comprar = new CtrlModalComprar(prodDto);
                comprar.mtdInit();
            }
        };
        
        tarjeta.btnHacerCompra.addMouseListener(eventoBtnComprar);
    }
    
    // Métodos
    public void mtdInit(){
        mtdEstablecerImagen();
        mtdEstablecerOpciones();
        mtdCrearEventoBtnComprar();
        mtdEstablecerDatos();
        mtdEstablecerDimensiones();
    }
    
    private void mtdEstablecerDatos(){
        tarjeta.etqTitulo.setText( prodDto.getProdTitulo() );
        tarjeta.cmpPrecioUnidad.setText( "" + prodDto.getProdPrecio() );
        tarjeta.cmpStockDisponible.setText( ""  + prodDto.getProdStock());
    }
    
    private void mtdEstablecerImagen(){
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
        tarjeta.setVisible(true);
    }

    public PanelCardProducto getLaVista() {
        return tarjeta;
    }

    public void setLaVista(PanelCardProducto laVista) {
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

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }
    
}
