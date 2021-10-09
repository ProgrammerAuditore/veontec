package controlador.componentes;

import controlador.CtrlMiTienda;
import controlador.acciones.CtrlModalEditarProducto;
import index.Veontec;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import vista.paneles.acciones.PanelCrearProducto;

public class CtrlCardMiProducto {

    
    // * Vista
    private PanelCardMiProducto tarjeta;
    public JDialog modalCrearProducto;
    
    // * Modelo
    private ProductoDto prodDto;
    private ProductoDao prodDao;
    
    // * Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    private ImageIcon portada;
    
    // Constructor
    public CtrlCardMiProducto(ProductoDto prodDto, ProductoDao prodDao) {
        this.tarjeta = new PanelCardMiProducto();
        this.prodDto = prodDto;
        this.prodDao = prodDao;
        this.tarjeta_dimensiones = new GridBagConstraints();
    }
    
    // Eventos
    private void mtdCrearEventoBtnRemover(){
        MouseListener eventoBtnComprar = null;
        tarjeta.btnEliminar.removeMouseListener(eventoBtnComprar);
        
        eventoBtnComprar =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdModalEliminarProducto();
            }
        };
        
        tarjeta.btnEliminar.addMouseListener(eventoBtnComprar);
    }
    
    private void mtdCrearEventoBtnEditar(){
        MouseListener eventoBtnEditar = null;
        tarjeta.btnEditar.removeMouseListener(eventoBtnEditar);
        
        eventoBtnEditar =  new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                CtrlModalEditarProducto editar = new CtrlModalEditarProducto(prodDto);
                editar.mtdInit();
                //editar.modal.setVisible(true);
            }
        };
        
        tarjeta.btnEditar.addMouseListener(eventoBtnEditar);
    }
    
    // Métodos
    public void mtdInit(){
        mtdEstablecerDimensiones();
        mtdCrearEventoBtnRemover();
        mtdCrearEventoBtnEditar();
        mtdCardEstablecerDatos();
    }
    
    private void mtdCardEstablecerDatos(){
        tarjeta.etqTitulo.setText( prodDto.getProdTitulo() );
        tarjeta.cmpPrecioUnidad.setText( "" + prodDto.getProdPrecio() );
        tarjeta.cmpStockDisponible.setText( ""  + prodDto.getProdStock());
        
        // * Descripción de detalles
        tarjeta.cmpDetalleMiProducto.setText( prodDto.getProdDescripcion() );
        tarjeta.etqFecha.setText( "15/09/2021" );
        
        if( prodDto.getProdImg() != null ){
            // * Establecer imagen de portada
            try {
                byte[] img = prodDto.getProdImg();
                BufferedImage buffimg = null;
                InputStream inputimg = new ByteArrayInputStream(img);
                buffimg = ImageIO.read(inputimg);
                tarjeta.pnImgPortafa.removeAll();
                portada = new ImageIcon(buffimg.getScaledInstance(tarjeta.pnImgPortafa.getWidth(), tarjeta.pnImgPortafa.getHeight(), Image.SCALE_SMOOTH));
                JLabel iconocc = new JLabel(portada);
                iconocc.setBounds(0, 0, tarjeta.pnImgPortafa.getWidth(), tarjeta.pnImgPortafa.getHeight());
                iconocc.setSize(tarjeta.pnImgPortafa.getWidth(), tarjeta.pnImgPortafa.getHeight());
                iconocc.setLocation(0, 0);
                iconocc.setPreferredSize(new Dimension(tarjeta.pnImgPortafa.getWidth(), tarjeta.pnImgPortafa.getHeight()));
                tarjeta.pnImgPortafa.add(iconocc);
                //tarjeta.updateUI();
                tarjeta.validate();
                tarjeta.revalidate();
                tarjeta.repaint();
                
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
        int opc = JOptionPane.showConfirmDialog(tarjeta, 
                "¿Seguro que desar eliminar este producto?",
                "Eliminar | " + prodDto.getProdTitulo(), 
                JOptionPane.YES_NO_OPTION );
        
        if( opc == JOptionPane.YES_OPTION ){
            if( prodDao.mtdRemover(prodDto) ){
                CtrlMiTienda.mtdRecargarMisProductos();
                JOptionPane.showMessageDialog(tarjeta, "Producto eliminado exitosamente.");
            }
        }
        
    }

    public PanelCardMiProducto getLaVista() {
        return tarjeta;
    }

    public void setLaVista(PanelCardMiProducto laVista) {
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
