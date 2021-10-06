package controlador.acciones;

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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.dao.CompraDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.CompraDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import vista.paneles.PanelBienvenida;
import vista.paneles.PanelHacerCompra;

public class CtrlModalComprar {

    // * Vista
    private PanelHacerCompra pnHacerCompra;
    private JDialog modal;

    // * Modelo
    private ProductoDto prodDto;
    private ProductoDao prodDao;
    private CompraDto compDto;
    private CompraDao compDao;
    private UsuarioDao usuaDao;
    private UsuarioDto usuaDto;

    // * Atributos

    // * Controlador
    public CtrlModalComprar(ProductoDto producto_dto) {
        this.prodDto = producto_dto;
        this.prodDao = new ProductoDao();
        this.usuaDto = new UsuarioDto();
        this.usuaDao = new UsuarioDao();
        this.compDto = new CompraDto();
        this.compDao = new CompraDao();
    }

    // * Eventos
    // * Métodos
    public void mtdInit() {
        modal = new JDialog(Veontec.ventanaHome);
        pnHacerCompra = new PanelHacerCompra();

        mtdEstablecerDatosParaModal();
        mtdBuildEventoBtnCancelar();
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

                        System.out.println("" + ( cantidad * prodDto.getProdPrecio()  ) );
                } catch (NumberFormatException err) {
                    e.consume();
                    System.out.println("" + err.getMessage() );
                }
            }
        };
        
        pnHacerCompra.cmpCantidad.addKeyListener(eventoCampoCantidad);
    }
}
