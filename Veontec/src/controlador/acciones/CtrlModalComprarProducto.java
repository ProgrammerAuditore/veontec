package controlador.acciones;

import controlador.tabs.CtrlBienvenida;
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
import src.FncGlobales;
import ticket.GenTicket;
import vista.paneles.acciones.PanelHacerCompra;

public class CtrlModalComprarProducto {

    // ***** Vista
    private PanelHacerCompra pnHacerCompra;
    private JDialog modal;

    // ***** Modelo
    private ProductoDto productoDto;
    private ProductoDao productoDao;
    private CompraDto compraDto;
    private CompraDao compraDao;
    private VentaDao ventaDao;
    private VentaDto ventaDto;
    private UsuarioDao usuarioDao;
    private UsuarioDto usuarioDto;

    // ***** Atributos

    // ***** Controlador
    public CtrlModalComprarProducto(ProductoDto producto_dto) {
        this.productoDto = producto_dto;
        this.productoDao = new ProductoDao();
        this.usuarioDto = new UsuarioDto();
        this.usuarioDao = new UsuarioDao();
        this.compraDto = new CompraDto();
        this.compraDao = new CompraDao();
        this.ventaDto = new VentaDto();
        this.ventaDao = new VentaDao();
    }

    // ***** Eventos
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
                            
                            if( cantidad > productoDto.getProdStock() ){
                                pnHacerCompra.cmpCantidad.rechazarCampo();
                                JOptionPane.showMessageDialog(Veontec.ventanaHome, "La cantidad es superior al stock");
                                e.consume();
                            }else{
                                pnHacerCompra.cmpCantidad.aceptarCampo();
                                pnHacerCompra.cmpTotal.setText("" + ( cantidad * productoDto.getProdPrecio()  ) );
                            }
                        }

                        //System.out.println("" + ( cantidad * productoDto.getProdPrecio()  ) );
                } catch (NumberFormatException err) {
                    e.consume();
                    //System.out.println("" + err.getMessage() );
                }
            }
        };
        
        pnHacerCompra.cmpCantidad.addKeyListener(eventoCampoCantidad);
    }
    
    // ***** Métodos
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
        modal.setTitle("Comprar | " + productoDto.getProdTitulo());
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
        pnHacerCompra.cmpTitulo.setText(productoDto.getProdTitulo() );
        pnHacerCompra.cmpDescripcion.setText(productoDto.getProdDescripcion() );
        pnHacerCompra.cmpPrecio.setText("" + productoDto.getProdPrecio() );
        pnHacerCompra.cmpStock.setText("" + productoDto.getProdStock());   
        pnHacerCompra.cmpEnlace.setText(productoDto.getProdEnlace() );
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
            
            String FechaActual = new FncGlobales().fncObtenerFechaActual();
            Integer cmpCantidad = Integer.parseInt( pnHacerCompra.cmpCantidad.getText() );
            Double cmpPrecio = Double.parseDouble( pnHacerCompra.cmpPrecio.getText() );
            BigDecimal precio  = new BigDecimal( (cmpCantidad * cmpPrecio) );
            String cmpTitulo = pnHacerCompra.cmpTitulo.getText();
            usuarioDto = Veontec.usuarioDto; 
            
            // * Establecer la compra
            compraDto.setCompProducto(productoDto.getProdID() );
            compraDto.setCompVendedor(productoDto.getProdUsuario() );
            compraDto.setCompComprador(usuarioDto.getCmpID() );
            compraDto.setCompTitulo( cmpTitulo );
            compraDto.setCompCantidad( cmpCantidad );        
            compraDto.setCompPrecio( precio.doubleValue() );
            compraDto.setCompFecha( FechaActual );
            compraDto.setCompEstado(0);      
            
            // * Establecer la venta
            ventaDto.setVentCantidad( cmpCantidad );
            ventaDto.setVentComprador(usuarioDto.getCmpID() );
            ventaDto.setVentVendedor(productoDto.getProdUsuario() );
            ventaDto.setVentTitulo(productoDto.getProdTitulo() );
            ventaDto.setVentProducto(productoDto.getProdID() );
            ventaDto.setVentPrecio(productoDto.getProdPrecio() );
            ventaDto.setVentFecha( FechaActual );
            ventaDto.setVentEstado(0);
            
            // * Establecer el producto
            productoDto.setProdStock(productoDto.getProdStock() - cmpCantidad );
            
            // * Establecer hash code
            int hashCode = new FncGlobales().hashCodeCompraVenta(ventaDto, compraDto);
            ventaDto.setVentHashCode(hashCode);
            compraDto.setCompHashCode(hashCode);
            

            // * Realizar la compra
            if( compraDao.mtdInsetar(compraDto) && productoDao.mtdActualizar(productoDto) && ventaDao.mtdInsetar(ventaDto) ){
                CtrlBienvenida.mtdRecargar();
                mtdCerrarModal();
                JOptionPane.showMessageDialog(Veontec.ventanaHome, "La compra se realizo exitosamente.");
            }
            
            String metodoPago="";
            GenTicket ticket = new GenTicket();
            if (pnHacerCompra.btnMtdDebito.isSelected()) {
                metodoPago = pnHacerCompra.btnMtdDebito.getText();
            }else{
            metodoPago = pnHacerCompra.btnMtdPaypal.getText();
            }
            ticket.ConexionTicket(cmpTitulo, cmpCantidad, precio.doubleValue(),usuarioDto.getCmpNombreCompleto() , metodoPago);
                
        }
    }
    
    
    
}
