package controlador.acciones;

import controlador.CtrlBienvenida;
import index.Veontec;
import java.awt.Dialog;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.dao.CompraDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dao.VentaDao;
import modelo.dto.CompraDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import modelo.dto.VentaDto;
import org.apache.commons.lang.builder.HashCodeBuilder;
import src.Funciones;
import vista.paneles.acciones.PanelHacerCompra;

public class CtrlModalComprarProducto {

    // * Vista
    private PanelHacerCompra pnHacerCompra;
    private JDialog modal;

    // * Modelo
    private ProductoDto prodDto;
    private ProductoDao prodDao;
    private CompraDto compDto;
    private CompraDao compDao;
    private VentaDao ventaDao;
    private VentaDto ventaDto;
    private UsuarioDao usuaDao;
    private UsuarioDto usuaDto;

    // * Atributos

    // * Controlador
    public CtrlModalComprarProducto(ProductoDto producto_dto) {
        this.prodDto = producto_dto;
        this.prodDao = new ProductoDao();
        this.usuaDto = new UsuarioDto();
        this.usuaDao = new UsuarioDao();
        this.compDto = new CompraDto();
        this.compDao = new CompraDao();
        this.ventaDto = new VentaDto();
        this.ventaDao = new VentaDao();
    }

    // * Eventos
    private void mtdBuildEventoBtnCancelar() {
        MouseListener eventoBtnCancelar = null;
        pnHacerCompra.btnCancelar.removeMouseListener(eventoBtnCancelar);

        eventoBtnCancelar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCerrarModal();
            }
        };

        pnHacerCompra.btnCancelar.addMouseListener(eventoBtnCancelar);
    }
    
    private void mtdBuildEventoBtnAceptar() {
        MouseListener eventoBtnComprar = null;
        pnHacerCompra.btnAceptar.removeMouseListener(eventoBtnComprar);

        eventoBtnComprar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdComprar();
            }
        };

        pnHacerCompra.btnAceptar.addMouseListener(eventoBtnComprar);
    }
    
    private void mtdBuildEventoParaModal() {
        WindowListener eventoModal = null;
        modal.removeWindowListener(eventoModal);

        eventoModal = new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mtdCerrarModal();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                mtdAbrirModal();
            }

        };

        modal.addWindowListener(eventoModal);
    }
    
    private void mtdBuildEventoCampoCantidad() {
        KeyListener eventoCampoCantidad = null;
        pnHacerCompra.cmpCantidad.removeKeyListener(eventoCampoCantidad);
        
        eventoCampoCantidad = new KeyAdapter() {
            @Override
            public void keyReleased (KeyEvent e) {
                super.keyPressed(e); //To change body of generated methods, choose Tools | Templates.
                try {
                        int cantidad = Integer.parseInt(pnHacerCompra.cmpCantidad.getText());

                        if( pnHacerCompra.cmpCantidad.isAprobado() ){
                            
                            if( cantidad > prodDto.getProdStock() ){
                                pnHacerCompra.cmpCantidad.rechazarCampo();
                                JOptionPane.showMessageDialog(Veontec.ventanaHome, "La cantidad es superior al stock");
                                e.consume();
                            }else{
                                pnHacerCompra.cmpCantidad.aceptarCampo();
                                pnHacerCompra.cmpTotal.setText( "" + ( cantidad * prodDto.getProdPrecio()  ) );
                            }
                        }

                        //System.out.println("" + ( cantidad * prodDto.getProdPrecio()  ) );
                } catch (NumberFormatException err) {
                    e.consume();
                    //System.out.println("" + err.getMessage() );
                }
            }
        };
        
        pnHacerCompra.cmpCantidad.addKeyListener(eventoCampoCantidad);
    }
    
    // * Métodos
    public void mtdInit() {
        modal = new JDialog(Veontec.ventanaHome);
        pnHacerCompra = new PanelHacerCompra();

        mtdEstablecerDatosParaModal();
        mtdBuildEventoBtnCancelar();
        mtdBuildEventoBtnAceptar();
        mtdBuildEventoParaModal(); 
        mtdBuildEventoCampoCantidad();
        mtdBuildModalComprar();
        
    }

    private void mtdBuildModalComprar() {
        //modal.setModal(true);
        //modal.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle("Comprar | " + prodDto.getProdTitulo());
        modal.setResizable(false);
        modal.setSize(pnHacerCompra.getSize());
        modal.setPreferredSize(pnHacerCompra.getSize());
        modal.setContentPane(pnHacerCompra);
        modal.setLocationRelativeTo(Veontec.ventanaHome);
        modal.setVisible(true);
    }

    private void mtdCerrarModal() {
        modal.setVisible(false);
        modal.removeAll();
        modal.dispose();
    }

    private void mtdAbrirModal() {
        pnHacerCompra.revalidate();
        pnHacerCompra.validate();
        pnHacerCompra.repaint();
        JOptionPane.showMessageDialog(Veontec.ventanaHome, "Comprar producto...");
    }

    private void mtdEstablecerDatosParaModal() {
        pnHacerCompra.cmpTitulo.setText( prodDto.getProdTitulo() );
        pnHacerCompra.cmpDescripcion.setText( prodDto.getProdDescripcion() );
        pnHacerCompra.cmpPrecio.setText( "" + prodDto.getProdPrecio() );
        pnHacerCompra.cmpStock.setText( "" + prodDto.getProdStock());   
        pnHacerCompra.cmpEnlace.setText( prodDto.getProdEnlace() );
    }
    
    private void mtdComprar() {
        
        if( !pnHacerCompra.btnMtdDebito.isSelected() && !pnHacerCompra.btnMtdPaypal.isSelected() ){
            JOptionPane.showMessageDialog(Veontec.ventanaHome, "Selecciona un método de pago.");
        
        }else
        if( !pnHacerCompra.cmpCantidad.isAprobado() || Integer.valueOf(pnHacerCompra.cmpCantidad.getText()) == 0  ){
            JOptionPane.showMessageDialog(Veontec.ventanaHome, "Introduce la cantida de compra.");
        
        }else
        if(  !pnHacerCompra.mtdComprobarMtdDebito() && !pnHacerCompra.mtdComprobarMtdPayPal()  ){
            JOptionPane.showMessageDialog(Veontec.ventanaHome, "Verifique que los campos en el método de pago sean correctos.");
        
        }else{
            
            String FechaActual = new Funciones().fncObtenerFechaActual();
            Integer cmpCantidad = Integer.parseInt( pnHacerCompra.cmpCantidad.getText() );
            Double cmpPrecio = Double.parseDouble( pnHacerCompra.cmpPrecio.getText() );
            BigDecimal precio  = new BigDecimal( (cmpCantidad * cmpPrecio) );
            String cmpTitulo = pnHacerCompra.cmpTitulo.getText();
            usuaDto = Veontec.usuarioDto; 
            
            // * Establecer la compra
            compDto.setCompProducto( prodDto.getProdID() );
            compDto.setCompVendedor( prodDto.getProdUsuario() );
            compDto.setCompComprador( usuaDto.getCmpID() );
            compDto.setCompTitulo( cmpTitulo );
            compDto.setCompCantidad( cmpCantidad );        
            compDto.setCompPrecio( precio.doubleValue() );
            compDto.setCompFecha( FechaActual );
            compDto.setCompEstado(0);      
            
            // * Establecer la venta
            ventaDto.setVentCantidad( cmpCantidad );
            ventaDto.setVentComprador( usuaDto.getCmpID() );
            ventaDto.setVentVendedor( prodDto.getProdUsuario() );
            ventaDto.setVentTitulo( prodDto.getProdTitulo() );
            ventaDto.setVentProducto( prodDto.getProdID() );
            ventaDto.setVentPrecio( prodDto.getProdPrecio() );
            ventaDto.setVentFecha( FechaActual );
            ventaDto.setVentEstado(0);
            
            // * Establecer el producto
            prodDto.setProdStock( prodDto.getProdStock() - cmpCantidad );
            
            // * Establecer hash code
            int hashCode = new Funciones().hashCodeCompraVenta(ventaDto, compDto);
            ventaDto.setVentHashCode(hashCode);
            compDto.setCompHashCode(hashCode);
            

            // * Realizar la compra
            if( compDao.mtdInsetar(compDto) && prodDao.mtdActualizar(prodDto) && ventaDao.mtdInsetar(ventaDto) ){
                CtrlBienvenida.mtdRecargar();
                mtdCerrarModal();
                JOptionPane.showMessageDialog(Veontec.ventanaHome, "La compra se realizo exitosamente.");
            }
                
        }
    }
    
    
    
}
