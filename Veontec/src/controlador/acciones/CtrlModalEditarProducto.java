package controlador.acciones;

import controlador.CtrlMiTienda;
import index.Veontec;
import java.awt.Dialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.dao.ProductoDao;
import modelo.dto.ProductoDto;
import vista.paneles.acciones.PanelCrearProducto;

public class CtrlModalEditarProducto {
    
    // * Vista
    private PanelCrearProducto laVista;
    public JDialog modal;
    
    // * Modelo
    private ProductoDao productoDao;
    private ProductoDto productoDto;
    
    // * Atributos (Opcional)
    
    // * Constructor
    public CtrlModalEditarProducto(ProductoDto productoDto) {
        this.laVista = new PanelCrearProducto();
        this.productoDao = new ProductoDao();
        this.productoDto = productoDto;
        this.modal = new JDialog(Veontec.ventanaHome);
    }    
    
    // * Eventos
    private void mtdEventoBtnAceptar() {
        MouseListener btnAceptar = null;
        laVista.btnAceptar.removeMouseListener(btnAceptar);
        
        btnAceptar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEditarProducto();
            }
        };
        
        laVista.btnAceptar.addMouseListener(btnAceptar);
    }
    
    private void mtdEventoBtnCancelar() {
        MouseListener btnCancelar = null;
        laVista.btnCancelar.removeMouseListener(btnCancelar);
        
        btnCancelar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCerrarVentana();
            }
        };
        
        laVista.btnCancelar.addMouseListener(btnCancelar);
    }
    
    private void mtdAgregerEventoWindow(){
        WindowListener evtWindow = null;
        modal.removeWindowListener(evtWindow);
        
        evtWindow = new WindowAdapter(){
            @Override
            public void windowClosed(WindowEvent e) {
                mtdCerrarVentana();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                laVista.updateUI();
                modal.validate();
                modal.repaint();
                JOptionPane.showMessageDialog(null, "Editando producto...");
            }
        };
        
        modal.addWindowListener(evtWindow);
    }
    
    private void mtdEventoBtnSeleccionarImg() {
        MouseListener btnSeleccionarImg = null;
        laVista.btnSeleccionarImg.removeMouseListener(btnSeleccionarImg);
        
        btnSeleccionarImg = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdSeleccionarImagen();
            }
        };
        
        laVista.btnSeleccionarImg.addMouseListener(btnSeleccionarImg);
    }
    
    // * Métodos
    public void mtdInit(){
        mtdEstablecerDatos();
        mtdAgregerEventoWindow();
        mtdEventoBtnAceptar();
        mtdEventoBtnCancelar();
        mtdEventoBtnSeleccionarImg();
        mtdBuildModal();
    }

    private void mtdBuildModal() {       
        //modalCrearProducto.setModal(true);
        //modalCrearProducto.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle(productoDto.getProdTitulo());
        modal.setResizable(false);
        modal.setSize( laVista.getSize() );
        modal.setPreferredSize(laVista.getSize() );
        modal.setContentPane(laVista);
        modal.setLocationRelativeTo(Veontec.ventanaHome);
        modal.validate();
        laVista.updateUI();
        modal.repaint();
        modal.setVisible(true);
    }
    
    private void mtdEstablecerDatos(){
        laVista.etqTitulo.setText("EDITAR PRODUCTO");
        
        laVista.cmpTitulo.setText( productoDto.getProdTitulo()  );
        laVista.cmpDescripcion.setText( productoDto.getProdDescripcion() );
        laVista.cmpPrecio.setText( "" + productoDto.getProdPrecio() );
        laVista.cmpStock.setText( "" + productoDto.getProdStock());
        laVista.cmpEnlace.setText( productoDto.getProdEnlace() );
        
    }
    
    private void mtdEditarProducto() {
        productoDto.setProdDescripcion( laVista.cmpDescripcion.getText() );
        productoDto.setProdTitulo( laVista.cmpTitulo.getText() );
        productoDto.setProdEnlace( laVista.cmpEnlace.getText() );
        productoDto.setProdPrecio( Double.parseDouble(laVista.cmpPrecio.getText()) );
        productoDto.setProdStock( Integer.parseInt(laVista.cmpStock.getText()) );
        
        if( productoDao.mtdActualizar(productoDto) ){
            CtrlMiTienda.mtdRecargarMisProductos();
            JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente.");
        }
        
    }
    
    private void mtdSeleccionarImagen(){
        JFileChooser seleccionarArchivo = new JFileChooser();
        FileNameExtensionFilter fill = new FileNameExtensionFilter("JPG, PNG, &GIF", "jpg", "png", "gif");
        
        seleccionarArchivo.setFileFilter(fill);
        seleccionarArchivo.showOpenDialog(laVista);
        File archivo = seleccionarArchivo.getSelectedFile();
        
        if( archivo != null ){
            String path_img = archivo.getAbsolutePath();
            ImageIcon img = new ImageIcon(path_img);
            laVista.cmpImagenPath.setText( archivo.getAbsolutePath() );
          
            if( archivo.length() > 805867 ){
                JOptionPane.showMessageDialog(null, "La imágen es demasiado grande.");
                laVista.cmpImagenPath.rechazarCampo();
            }else{
                productoDto.setProdImg(getImagen(path_img));
                laVista.cmpImagenPath.aceptarCampo();
            }
        }
    }
    
    private byte[] getImagen(String ruta){
        File imagen = new File(ruta);
        try {
            byte[] icono = new byte[(int) imagen.length() ];
            InputStream inpu = new FileInputStream(imagen);
            inpu.read(icono);
            return icono;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    private void mtdCerrarVentana(){
        modal.removeAll();
        modal.setVisible(false);
        modal.dispose();
    }
    
}
