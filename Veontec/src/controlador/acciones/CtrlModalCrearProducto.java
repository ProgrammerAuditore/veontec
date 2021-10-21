package controlador.acciones;

import controlador.tabs.CtrlMiTienda;
import index.Veontec;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.dao.CategoriaDao;
import modelo.dao.ProductoDao;
import modelo.dto.CategoriaDto;
import modelo.dto.ProductoDto;
import vista.paneles.acciones.PanelCrearProducto;

public class CtrlModalCrearProducto implements ActionListener{
    
    // ***** Vista
    private PanelCrearProducto pnCrearProducto;
    private JDialog modal;
    
    // ***** Modelo
    private ProductoDao productoDao;
    private ProductoDto productoDto;
    private CategoriaDto categoriaDto;
    private CategoriaDao categoriaDao;
    
    // ***** Atributos (Opcional)
    List<CategoriaDto> lstCategorias;
    
    // ***** Constructor
    public CtrlModalCrearProducto() {
        this.pnCrearProducto = new PanelCrearProducto();
        this.productoDao = new ProductoDao();
        this.productoDto = new ProductoDto();
        this.categoriaDao = new CategoriaDao();
        this.categoriaDto = new CategoriaDto();
        this.modal = new JDialog(Veontec.ventanaHome);
    }    
    
    // ***** Eventos
    private void mtdEventoBtnAceptar() {
        MouseListener btnAceptar = null;
        pnCrearProducto.btnAceptar.removeMouseListener(btnAceptar);
        
        btnAceptar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCrearProducto();
            }
        };
        
        pnCrearProducto.btnAceptar.addMouseListener(btnAceptar);
    }
    
    private void mtdEventoBtnCancelar() {
        MouseListener btnCancelar = null;
        pnCrearProducto.btnCancelar.removeMouseListener(btnCancelar);
        
        btnCancelar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCerrarModal();
            }
        };
        
        pnCrearProducto.btnCancelar.addMouseListener(btnCancelar);
    }
    
    private void mtdAgregerEventoWindow(){
        WindowListener evtWindow = null;
        modal.removeWindowListener(evtWindow);
        
        evtWindow = new WindowAdapter(){
            @Override
            public void windowClosed(WindowEvent e) {
                mtdCerrarModal();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                pnCrearProducto.updateUI();
                modal.validate();
                modal.repaint();
                JOptionPane.showMessageDialog(null, "Crear un producto...");
            }
        };
        
        modal.addWindowListener(evtWindow);
    }
    
    private void mtdEventoBtnSeleccionarImg() {
        MouseListener btnSeleccionarImg = null;
        pnCrearProducto.btnSeleccionarImg.removeMouseListener(btnSeleccionarImg);
        
        btnSeleccionarImg = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdSeleccionarImagen();
            }
        };
        
        pnCrearProducto.btnSeleccionarImg.addMouseListener(btnSeleccionarImg);
    }
    
    // ***** Métodos
    public void mtdInit(){
        pnCrearProducto.cboxBoleto.addActionListener(this);
        pnCrearProducto.cboxProductoExterno.addActionListener(this);
        pnCrearProducto.cmboxVuelos.addActionListener(this);
        
        mtdMostrarCategorias();
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
        modal.setSize(pnCrearProducto.getSize() );
        modal.setPreferredSize(pnCrearProducto.getSize() );
        modal.setContentPane(pnCrearProducto);
        modal.setLocationRelativeTo(Veontec.ventanaHome);
        modal.validate();
        pnCrearProducto.updateUI();
        modal.repaint();
        modal.setVisible(true);
    }
    
    private void mtdCrearProducto() {
        if( pnCrearProducto.mtdComprobar() ){
            productoDto.setProdUsuario( Veontec.usuarioDto.getCmpID() );
            productoDto.setProdCategoria(String.valueOf(pnCrearProducto.cmpCategorias.getSelectedItem() ) );
            productoDto.setProdDescripcion(pnCrearProducto.cmpDescripcion.getText() );
            productoDto.setProdTitulo(pnCrearProducto.cmpTitulo.getText() );
            productoDto.setProdEnlace(pnCrearProducto.cmpEnlace.getText() );
            productoDto.setProdPrecio(Double.parseDouble(pnCrearProducto.cmpPrecio.getText()) );
            productoDto.setProdStock(Integer.parseInt(pnCrearProducto.cmpStock.getText()) );
            mtdEstablecerTipoProductoYEnlace();

            if( productoDao.mtdInsetar(productoDto) ){
                CtrlMiTienda.mtdRecargarMisProductos();
                JOptionPane.showMessageDialog(pnCrearProducto, "Producto se creo exitosamente.");
                mtdCerrarModal();
            }
            
        }else{
            JOptionPane.showMessageDialog(null, "Verifica que los datos sean correctos.");
        
        }
    }
    
    private void mtdMostrarCategorias(){
        pnCrearProducto.cmpCategorias.removeAllItems();
        
        categoriaDto.setCateUsuario(Veontec.usuarioDto.getCmpID());
        lstCategorias = categoriaDao.mtdListar(categoriaDto);
        int categorias = lstCategorias.size();
        
        if( categorias > 0 ){
            for (int i = 0; i < categorias; i++) {
                pnCrearProducto.cmpCategorias.addItem(lstCategorias.get(i).getCateNombre());
            }
        }
        
    }
    
    private void mtdSeleccionarImagen(){
        JFileChooser seleccionarArchivo = new JFileChooser();
        FileNameExtensionFilter fill = new FileNameExtensionFilter("JPG, PNG, &GIF", "jpg", "png", "gif");
        
        seleccionarArchivo.setFileFilter(fill);
        seleccionarArchivo.showOpenDialog(pnCrearProducto);
        File archivo = seleccionarArchivo.getSelectedFile();
        
        if( archivo != null ){
            String path_img = archivo.getAbsolutePath();
            ImageIcon img = new ImageIcon(path_img);
            pnCrearProducto.cmpImagenPath.setText( archivo.getAbsolutePath() );
          
            if( archivo.length() > 805867 ){
                JOptionPane.showMessageDialog(null, "La imágen es demasiado grande.");
                pnCrearProducto.cmpImagenPath.rechazarCampo();
            }else{
                productoDto.setProdImg(getImagen(path_img));
                pnCrearProducto.cmpImagenPath.aceptarCampo();
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
    
    private void mtdCerrarModal(){
        modal.removeAll();
        modal.setVisible(false);
        modal.dispose();
    }
    
    private void mtdDeseleccionar( JCheckBox opcion, boolean marca){
        pnCrearProducto.cboxBoleto.setSelected(false);
        pnCrearProducto.cboxProductoExterno.setSelected(false);
        pnCrearProducto.cmboxVuelos.setSelected(false);
        pnCrearProducto.cmpEnlace.setEditable(false);
        pnCrearProducto.cmpEnlace.setEnabled(false);
        pnCrearProducto.cmpEnlace.setVerificarCampo(false);
        pnCrearProducto.cmpEnlace.getEstiloTextEstablecido();
        
        if(marca){
            opcion.setSelected(true);
            pnCrearProducto.cmpEnlace.setEditable(true);
            pnCrearProducto.cmpEnlace.setEnabled(true);
            pnCrearProducto.cmpEnlace.setVerificarCampo(true);
        }
    }
    
    private void mtdEstablecerTipoProductoYEnlace(){
        // Tipos de productos
        // 1 ; Significa producto interno
        // 3 ; Significa producto externo para Producto Externo
        // 7 ; Significa producto externo para Boletos
        // 9 ; Significa producto externo para Vuelos
        String strEnlace = "";
        
        if( pnCrearProducto.cboxProductoExterno.isSelected() ){
            productoDto.setProdTipo(3);
            
        }else 
        if( pnCrearProducto.cboxBoleto.isSelected() ){
            productoDto.setProdTipo(7);
            
        }else 
        if( pnCrearProducto.cmboxVuelos.isSelected() ){
            productoDto.setProdTipo(9);
            
        } 
        else{
            productoDto.setProdTipo(1);
            
        }
        
        if( productoDto.getProdTipo().intValue() == 1 ){
            productoDto.setProdEnlace("Vacio");
        }else{
            strEnlace = pnCrearProducto.cmpEnlace.getText().trim();
            productoDto.setProdEnlace(strEnlace);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if( e.getSource() == pnCrearProducto.cmboxVuelos ){
            if( pnCrearProducto.cmboxVuelos.isSelected() ){
                mtdDeseleccionar(pnCrearProducto.cmboxVuelos , true);
            }else{
                mtdDeseleccionar(null, false);
            }
        }
        
        if( e.getSource() == pnCrearProducto.cboxBoleto ){
            if( pnCrearProducto.cboxBoleto.isSelected() ){
                mtdDeseleccionar(pnCrearProducto.cboxBoleto , true);
            }else{
                mtdDeseleccionar(null, false);
            }
        }
        
        if( e.getSource() == pnCrearProducto.cboxProductoExterno ){
            if( pnCrearProducto.cboxProductoExterno.isSelected() ){
                mtdDeseleccionar(pnCrearProducto.cboxProductoExterno , true);
            }else{
                mtdDeseleccionar(null, false);
            }
        }
        
    }
    
}