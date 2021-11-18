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
import src.FncGlobales;
import vista.paneles.acciones.PanelHacerPreguntar;

public class CtrlModalHacerPregunta {

    // ***** Vista
    private PanelHacerPreguntar pnHacerPregunta;
    private JDialog modal;
    
    // ***** Modelo
    private PreguntaDto preguntaDto;
    private PreguntaDao preguntaDao;
    private ProductoDto productoDto;
    private ProductoDao productoDao;
    
    // ***** Atributo
    
    // ***** Constructor
    public CtrlModalHacerPregunta(ProductoDto productoDto) {
        this.productoDto = productoDto;
        this.pnHacerPregunta = new PanelHacerPreguntar();
        this.modal = new JDialog(Veontec.ventanaHome);
        this.preguntaDto = new PreguntaDto();
        this.preguntaDao = new PreguntaDao();
        this.productoDao = new ProductoDao();
        
    }
    
    // ***** Eventos
    private void mtdEventoBtnCancelar() {
        MouseListener btnCancelar = null;
        pnHacerPregunta.btnCancelar.removeMouseListener(btnCancelar);
        
        btnCancelar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdCerrarModal();
            }
        };
        
        pnHacerPregunta.btnCancelar.addMouseListener(btnCancelar);
    }
    
    private void mtdEventoBtnAceptar() {
        MouseListener btnAceptar = null;
        pnHacerPregunta.btnAceptar.removeMouseListener(btnAceptar);
        
        btnAceptar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdHacerPregunta();
            }
        };
        
        pnHacerPregunta.btnAceptar.addMouseListener(btnAceptar);
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
                pnHacerPregunta.updateUI();
                modal.validate();
                modal.repaint();
                JOptionPane.showMessageDialog(pnHacerPregunta, "Presiona aceptar para enviar una pregunta...");
            }
        };
        
        modal.addWindowListener(evtWindow);
    }
    
    // ***** Métodos
    public void mtdInit(){
        mtdEstablecerDatos();
        mtdAgregerEventoWindow();
        mtdEventoBtnAceptar();
        mtdEventoBtnCancelar();
        mtdBuildModal();
    }
    
    private void mtdEstablecerDatos(){
        pnHacerPregunta.cmpTitulo.setText( productoDto.getProdTitulo()  );
        pnHacerPregunta.cmpDescripcion.setText( productoDto.getProdDescripcion() );
        pnHacerPregunta.cmpPrecio.setText( "" + productoDto.getProdPrecio() );
        pnHacerPregunta.cmpStock.setText( "" + productoDto.getProdStock());
        pnHacerPregunta.cmpEnlace.setText( productoDto.getProdEnlace() );
        
    }

    private void mtdBuildModal() {
        //modalCrearProducto.setModal(true);
        //modalCrearProducto.setType(Window.Type.UTILITY);
        modal.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        modal.setTitle(productoDto.getProdTitulo());
        modal.setResizable(false);
        modal.setSize(pnHacerPregunta.getSize() );
        modal.setPreferredSize(pnHacerPregunta.getSize() );
        modal.setContentPane(pnHacerPregunta);
        modal.setLocationRelativeTo(Veontec.ventanaHome);
        modal.validate();
        pnHacerPregunta.updateUI();
        modal.repaint();
        modal.setVisible(true);
    }
    
    private void mtdHacerPregunta(){
        // Comprabar que los campos no esten vacíos
        if( pnHacerPregunta.mtdComprobar() ){
            
            preguntaDto.setPregComprador( Veontec.usuarioDto.getCmpID() );
            preguntaDto.setPregEstado(1);
            preguntaDto.setPregFecha(new FncGlobales().fncObtenerFechaYHoraActualNoSQL() );
            preguntaDto.setPregPregunta(pnHacerPregunta.cmpPregunta.getText() );
            preguntaDto.setPregProducto( productoDto.getProdID() );
            preguntaDto.setPregTitulo( productoDto.getProdTitulo() );
            preguntaDto.setPregVendedor( productoDto.getProdUsuario() );
            preguntaDto.setPregCreadoEn(new FncGlobales().fncObtenerFechaYHoraActualSQL() );
            preguntaDto.setPregActualizadoEn(new FncGlobales().fncObtenerFechaYHoraActualSQL() );
            
            if( preguntaDao.mtdInsetar(preguntaDto) ){
                try { CtrlPreguntas.mtdRecargarPreguntas(); } catch (Exception e) { }
                
                JOptionPane.showMessageDialog(pnHacerPregunta, "La pregunta se envió al vendedor...");
                mtdCerrarModal();
            }
            
        }else{
            JOptionPane.showMessageDialog(pnHacerPregunta, "Verifica que los datos sean correctos");
        }
        
    }
    
    private void mtdCerrarModal(){
        modal.removeAll();
        modal.setVisible(false);
        modal.dispose();
    }
    
}
