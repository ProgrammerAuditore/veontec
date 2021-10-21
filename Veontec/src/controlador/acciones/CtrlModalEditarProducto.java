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

public class CtrlModalEditarProducto implements ActionListener{
    
    // ***** Vista
    private PanelCrearProducto pnEditarProducto;
    private JDialog modal;
    
    // ***** Modelo
    private ProductoDao productoDao;
    private ProductoDto productoDto;
    private CategoriaDto categoriaDto;
    private CategoriaDao categoriaDao;
    
    // ***** Atributos (Opcional)
    List<CategoriaDto> lstCategorias;
    
    // ***** Constructor
    public CtrlModalEditarProducto(ProductoDto productoDto) {
        this.pnEditarProducto = new PanelCrearProducto();
        this.productoDao = new ProductoDao();
        this.productoDto = productoDto;
        this.categoriaDao = new CategoriaDao();
        this.categoriaDto = new CategoriaDto();
        this.modal = new JDialog(Veontec.ventanaHome);
    }    
    
    // ***** Eventos
    private void mtdEventoBtnAceptar() {
        MouseListener btnAceptar = null;
        pnEditarProducto.btnAceptar.removeMouseListener(btnAceptar);
        
        btnAceptar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEditarProducto();
            }
        };
        
        pnEditarProducto.btnAceptar.addMouseListener(btnAceptar);
    }
    
    private void mtdEventoBtnCancelar() {
        MouseListener btnCancelar = null;
        pnEditarProducto.btnCancelar.removeMouseListener(btnCancelar);
        
        btnCancelar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCerrarModal();
            }
        };
        
        pnEditarProducto.btnCancelar.addMouseListener(btnCancelar);
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
                pnEditarProducto.updateUI();
                modal.validate();
                modal.repaint();
                JOptionPane.showMessageDialog(pnEditarProducto, "Editando producto...");
            }
        };
        
        modal.addWindowListener(evtWindow);
    }
    
    private void mtdEventoBtnSeleccionarImg() {
        MouseListener btnSeleccionarImg = null;
        pnEditarProducto.btnSeleccionarImg.removeMouseListener(btnSeleccionarImg);
        
        btnSeleccionarImg = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdSeleccionarImagen();
            }
        };
        
        pnEditarProducto.btnSeleccionarImg.addMouseListener(btnSeleccionarImg);
    }
    
    // ***** Métodos
    public void mtdInit(){
        pnEditarProducto.cboxBoleto.addActionListener(this);
        pnEditarProducto.cboxProductoExterno.addActionListener(this);
        pnEditarProducto.cmboxVuelos.addActionListener(this);
       
        mtdMostrarCategorias();
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
        modal.setSize(pnEditarProducto.getSize() );
        modal.setPreferredSize(pnEditarProducto.getSize() );
        modal.setContentPane(pnEditarProducto);
        modal.setLocationRelativeTo(Veontec.ventanaHome);
        modal.validate();
        pnEditarProducto.updateUI();
        modal.repaint();
        modal.setVisible(true);
    }
    
    private void mtdEstablecerDatos(){
        pnEditarProducto.etqTitulo.setText("EDITAR PRODUCTO");
        
        pnEditarProducto.cmpTitulo.setText( productoDto.getProdTitulo()  );
        pnEditarProducto.cmpDescripcion.setText( productoDto.getProdDescripcion() );
        pnEditarProducto.cmpPrecio.setText( "" + productoDto.getProdPrecio() );
        pnEditarProducto.cmpStock.setText( "" + productoDto.getProdStock());
        mtdEstablecerTipoProducto();
        
        pnEditarProducto.cmpCategorias.setSelectedItem( productoDto.getProdCategoria() );
        
    }
    
    private void mtdEstablecerTipoProducto(){
        if( null != productoDto.getProdTipo() )switch (productoDto.getProdTipo()) {
            case 3:
                pnEditarProducto.cboxProductoExterno.setSelected(true);
                break;
            case 7:
                pnEditarProducto.cboxBoleto.setSelected(true);
                break;
            case 9:
                pnEditarProducto.cmboxVuelos.setSelected(true);
                break;
            default:
                break;
        }
        
        pnEditarProducto.cmpEnlace.setText( productoDto.getProdEnlace() );
    }
    
    private void mtdEditarProducto() {
        if( pnEditarProducto.mtdComprobar() ){
            productoDto.setProdUsuario(Veontec.usuarioDto.getCmpID() );
            productoDto.setProdCategoria(String.valueOf(pnEditarProducto.cmpCategorias.getSelectedItem() ) );
            productoDto.setProdDescripcion(pnEditarProducto.cmpDescripcion.getText() );
            productoDto.setProdTitulo(pnEditarProducto.cmpTitulo.getText() );
            productoDto.setProdEnlace(pnEditarProducto.cmpEnlace.getText() );
            productoDto.setProdPrecio(Double.parseDouble(pnEditarProducto.cmpPrecio.getText()) );
            productoDto.setProdStock(Integer.parseInt(pnEditarProducto.cmpStock.getText()) );
            mtdEstablecerTipoProductoYEnlace();

            if( productoDao.mtdActualizar(productoDto) ){
                CtrlMiTienda.mtdRecargarMisProductos();
                JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente.");
            }
        
        }else{
            JOptionPane.showMessageDialog(null, "Verifica que los datos sean correctos.");
        
        }
    }
    
    private void mtdMostrarCategorias(){
        pnEditarProducto.cmpCategorias.removeAllItems();
        
        categoriaDto.setCateUsuario(Veontec.usuarioDto.getCmpID());
        lstCategorias = categoriaDao.mtdListar(categoriaDto);
        int categorias = lstCategorias.size();
        
        if( categorias > 0 ){
            for (int i = 0; i < categorias; i++) {
                pnEditarProducto.cmpCategorias.addItem(lstCategorias.get(i).getCateNombre());
            }
        }
        
    }
    
    private void mtdSeleccionarImagen(){
        JFileChooser seleccionarArchivo = new JFileChooser();
        FileNameExtensionFilter fill = new FileNameExtensionFilter("JPG, PNG, &GIF", "jpg", "png", "gif");
        
        seleccionarArchivo.setFileFilter(fill);
        seleccionarArchivo.showOpenDialog(pnEditarProducto);
        File archivo = seleccionarArchivo.getSelectedFile();
        
        if( archivo != null ){
            String path_img = archivo.getAbsolutePath();
            ImageIcon img = new ImageIcon(path_img);
            pnEditarProducto.cmpImagenPath.setText( archivo.getAbsolutePath() );
          
            if( archivo.length() > 805867 ){
                JOptionPane.showMessageDialog(null, "La imágen es demasiado grande.");
                pnEditarProducto.cmpImagenPath.rechazarCampo();
            }else{
                productoDto.setProdImg(getImagen(path_img));
                pnEditarProducto.cmpImagenPath.aceptarCampo();
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
        pnEditarProducto.cboxBoleto.setSelected(false);
        pnEditarProducto.cboxProductoExterno.setSelected(false);
        pnEditarProducto.cmboxVuelos.setSelected(false);
        pnEditarProducto.cmpEnlace.setEditable(false);
        pnEditarProducto.cmpEnlace.setEnabled(false);
        pnEditarProducto.cmpEnlace.setVerificarCampo(false);
        pnEditarProducto.cmpEnlace.getEstiloTextEstablecido();
        
        if(marca){
            opcion.setSelected(true);
            pnEditarProducto.cmpEnlace.setEditable(true);
            pnEditarProducto.cmpEnlace.setEnabled(true);
            pnEditarProducto.cmpEnlace.setVerificarCampo(true);
        }
    }
    
    private void mtdEstablecerTipoProductoYEnlace(){
        // Tipos de productos
        // 1 ; Significa producto interno
        // 3 ; Significa producto externo para Producto Externo
        // 7 ; Significa producto externo para Boletos
        // 9 ; Significa producto externo para Vuelos
        String strEnlace = "";
        
        if( pnEditarProducto.cboxProductoExterno.isSelected() ){
            productoDto.setProdTipo(3);
            
        }else 
        if( pnEditarProducto.cboxBoleto.isSelected() ){
            productoDto.setProdTipo(7);
            
        }else 
        if( pnEditarProducto.cmboxVuelos.isSelected() ){
            productoDto.setProdTipo(9);
            
        } 
        else{
            productoDto.setProdTipo(1);
            
        }
        
        if( productoDto.getProdTipo().intValue() == 1 ){
            productoDto.setProdEnlace("Vacio");
        }else{
            strEnlace = pnEditarProducto.cmpEnlace.getText().trim();
            productoDto.setProdEnlace(strEnlace);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if( e.getSource() == pnEditarProducto.cmboxVuelos ){
            if( pnEditarProducto.cmboxVuelos.isSelected() ){
                mtdDeseleccionar(pnEditarProducto.cmboxVuelos , true);
            }else{
                mtdDeseleccionar(null, false);
            }
        }
        
        if( e.getSource() == pnEditarProducto.cboxBoleto ){
            if( pnEditarProducto.cboxBoleto.isSelected() ){
                mtdDeseleccionar(pnEditarProducto.cboxBoleto , true);
            }else{
                mtdDeseleccionar(null, false);
            }
        }
        
        if( e.getSource() == pnEditarProducto.cboxProductoExterno ){
            if( pnEditarProducto.cboxProductoExterno.isSelected() ){
                mtdDeseleccionar(pnEditarProducto.cboxProductoExterno , true);
            }else{
                mtdDeseleccionar(null, false);
            }
        }
        
    }
    
}
