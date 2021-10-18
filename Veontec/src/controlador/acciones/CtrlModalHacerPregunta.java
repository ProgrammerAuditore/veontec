package controlador.acciones;

import controlador.tabs.CtrlPreguntas;
import index.Veontec;
import java.awt.Dialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.dao.PreguntaDao;
import modelo.dao.ProductoDao;
import modelo.dto.PreguntaDto;
import modelo.dto.ProductoDto;
import src.Funciones;
import vista.paneles.acciones.PanelHacerPreguntar;

public class CtrlModalHacerPregunta {

    // * Vista
    private PanelHacerPreguntar laVista;
    private JDialog modal;
    
    // * Modelo
    private PreguntaDto preguntaDto;
    private PreguntaDao preguntaDao;
    private ProductoDto productoDto;
    private ProductoDao productoDao;
    
    // * Atributo
    
    // * Constructor
    public CtrlModalHacerPregunta(ProductoDto productoDto) {
        this.productoDto = productoDto;
        this.laVista = new PanelHacerPreguntar();
        this.modal = new JDialog(Veontec.ventanaHome);
        this.preguntaDto = new PreguntaDto();
        this.preguntaDao = new PreguntaDao();
        this.productoDao = new ProductoDao();
        
    }
    
    // * Eventos
    private void mtdEventoBtnCancelar() {
        MouseListener btnCancelar = null;
        laVista.btnCancelar.removeMouseListener(btnCancelar);
        
        btnCancelar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCerrarModal();
            }
        };
        
        laVista.btnCancelar.addMouseListener(btnCancelar);
    }
    
    private void mtdEventoBtnAceptar() {
        MouseListener btnAceptar = null;
        laVista.btnAceptar.removeMouseListener(btnAceptar);
        
        btnAceptar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdHacerPregunta();
            }
        };
        
        laVista.btnAceptar.addMouseListener(btnAceptar);
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
                laVista.updateUI();
                modal.validate();
                modal.repaint();
                JOptionPane.showMessageDialog(laVista, "Presiona aceptar para enviar una pregunta...");
            }
        };
        
        modal.addWindowListener(evtWindow);
    }
    
    // * Métodos
    public void mtdInit(){
        mtdEstablecerDatos();
        mtdAgregerEventoWindow();
        mtdEventoBtnAceptar();
        mtdEventoBtnCancelar();
        mtdBuildModal();
    }
    
    private void mtdEstablecerDatos(){
        laVista.cmpTitulo.setText( productoDto.getProdTitulo()  );
        laVista.cmpDescripcion.setText( productoDto.getProdDescripcion() );
        laVista.cmpPrecio.setText( "" + productoDto.getProdPrecio() );
        laVista.cmpStock.setText( "" + productoDto.getProdStock());
        laVista.cmpEnlace.setText( productoDto.getProdEnlace() );
        
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
    
    private void mtdHacerPregunta(){
        // Comprabar que los campos no esten vacíos
        if( laVista.mtdComprobar() ){
            
            preguntaDto.setPregComprador( Veontec.usuarioDto.getCmpID() );
            preguntaDto.setPregEstado(0);
            preguntaDto.setPregFecha( new Funciones().fncObtenerFechaActual());
            preguntaDto.setPregPregunta( laVista.cmpPregunta.getText() );
            preguntaDto.setPregProducto( productoDto.getProdID() );
            preguntaDto.setPregTitulo( productoDto.getProdTitulo() );
            preguntaDto.setPregVendedor( productoDto.getProdUsuario() );
            
            if( preguntaDao.mtdInsetar(preguntaDto) ){
                try { CtrlPreguntas.mtdRecargarPreguntas(); } catch (Exception e) { }
                
                JOptionPane.showMessageDialog(laVista, "La pregunta se envio al vendedor....");
                mtdCerrarModal();
            }
            
        }else{
            JOptionPane.showMessageDialog(laVista, "Verifica que los datos sean correctos");
        }
        
    }
    
    private void mtdCerrarModal(){
        modal.removeAll();
        modal.setVisible(false);
        modal.dispose();
    }
    
}
