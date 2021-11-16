package controlador.componentes;

import controlador.tabs.CtrlMiTienda;
import controlador.acciones.CtrlModalEditarProducto;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import modelo.dao.ProductoDao;
import modelo.dto.ProductoDto;
import vista.paneles.componentes.PanelCardMiProducto;

public class CtrlCardMiProducto {

    
    // ***** Vista
    private PanelCardMiProducto pnCardMiProducto;
    public JDialog modalCrearProducto;
    
    // ***** Modelo
    private ProductoDto productoDto;
    private ProductoDao productoDao;
    
    // ***** Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    private ImageIcon portada;
    
    // ***** Constructor
    public CtrlCardMiProducto(ProductoDto prodDto, ProductoDao prodDao) {
        this.pnCardMiProducto = new PanelCardMiProducto();
        this.productoDto = prodDto;
        this.productoDao = prodDao;
        this.tarjeta_dimensiones = new GridBagConstraints();
    }
    
    // ***** Eventos
    private void mtdCrearEventoBtnRemover(){
        MouseListener eventoBtnComprar = null;
        pnCardMiProducto.btnEliminar.removeMouseListener(eventoBtnComprar);
        
        eventoBtnComprar =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdModalEliminarProducto();
            }
        };
        
        pnCardMiProducto.btnEliminar.addMouseListener(eventoBtnComprar);
    }
    
    private void mtdCrearEventoBtnEditar(){
        MouseListener eventoBtnEditar = null;
        pnCardMiProducto.btnEditar.removeMouseListener(eventoBtnEditar);
        
        eventoBtnEditar =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                CtrlModalEditarProducto editar = new CtrlModalEditarProducto(productoDto);
                editar.mtdInit();
                //editar.modal.setVisible(true);
            }
        };
        
        pnCardMiProducto.btnEditar.addMouseListener(eventoBtnEditar);
    }
    
    // ***** Métodos
    public void mtdInit(){
        mtdEstablecerDimensiones();
        mtdCrearEventoBtnRemover();
        mtdCrearEventoBtnEditar();
        mtdCardEstablecerDatos();
    }
    
    private void mtdCardEstablecerDatos(){
        pnCardMiProducto.etqTitulo.setText(productoDto.getProdTitulo() );
        pnCardMiProducto.cmpPrecioUnidad.setText("" + productoDto.getProdPrecio() );
        pnCardMiProducto.cmpStockDisponible.setText(""  + productoDto.getProdStock());
        
        // * Descripción de detalles
        pnCardMiProducto.cmpDetalleMiProducto.setText(productoDto.getProdDescripcion() );
        pnCardMiProducto.etqFecha.setText( productoDto.getProdCreadoEn() );
        pnCardMiProducto.etqCategoria.setText( productoDto.getProdCategoria() );
        
        if( productoDto.getProdImg() != null ){
            // * Establecer imagen de portada
            try {
                byte[] img = productoDto.getProdImg();
                BufferedImage buffimg = null;
                InputStream inputimg = new ByteArrayInputStream(img);
                buffimg = ImageIO.read(inputimg);
                pnCardMiProducto.pnImgPortada.removeAll();
                portada = new ImageIcon(buffimg.getScaledInstance(pnCardMiProducto.pnImgPortada.getWidth(), pnCardMiProducto.pnImgPortada.getHeight(), Image.SCALE_SMOOTH));
                JLabel iconocc = new JLabel(portada);
                iconocc.setBounds(0, 0, pnCardMiProducto.pnImgPortada.getWidth(), pnCardMiProducto.pnImgPortada.getHeight());
                iconocc.setSize(pnCardMiProducto.pnImgPortada.getWidth(), pnCardMiProducto.pnImgPortada.getHeight());
                iconocc.setLocation(0, 0);
                iconocc.setPreferredSize(new Dimension(pnCardMiProducto.pnImgPortada.getWidth(), pnCardMiProducto.pnImgPortada.getHeight()));
                pnCardMiProducto.pnImgPortada.add(iconocc);
                //tarjeta.updateUI();
                pnCardMiProducto.validate();
                pnCardMiProducto.revalidate();
                pnCardMiProducto.repaint();
                
            } catch (Exception e) {
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
        //tarjeta.setVisible(true);
    }
    
    private void mtdModalEliminarProducto(){
        int opc = JOptionPane.showConfirmDialog(pnCardMiProducto, 
                "¿Seguro que desar eliminar este producto?",
                "Eliminar | " + productoDto.getProdTitulo(), 
                JOptionPane.YES_NO_OPTION );
        
        if( opc == JOptionPane.YES_OPTION ){
            if( productoDao.mtdRemover(productoDto) ){
                CtrlMiTienda.mtdRecargarMisProductos();
                JOptionPane.showMessageDialog(pnCardMiProducto, "Producto eliminado exitosamente.");
            }
        }
        
    }

    public PanelCardMiProducto getLaVista() {
        return pnCardMiProducto;
    }

    public void setLaVista(PanelCardMiProducto laVista) {
        this.pnCardMiProducto = laVista;
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
