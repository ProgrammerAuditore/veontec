package controlador.componentes;

import controlador.tabs.CtrlPreguntas;
import controlador.acciones.CtrlModalHacerPregunta;
import index.Veontec;
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
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import modelo.dao.PreguntaDao;
import modelo.dao.ProductoDao;
import modelo.dao.RespuestaDao;
import modelo.dao.UsuarioDao;
import modelo.dto.PreguntaDto;
import modelo.dto.ProductoDto;
import modelo.dto.RespuestaDto;
import modelo.dto.UsuarioDto;
import vista.paneles.componentes.PanelCardPregunta;

public class CtrlCardPregunta {
    
    // ***** Vista
    private PanelCardPregunta pnCardPregunta;
    
    // ***** Modelo
    private PreguntaDao preguntaDao;
    private PreguntaDto preguntaDto;
    private ProductoDto productoDto;
    private ProductoDao productoDao;
    private RespuestaDao respuestaDao;
    private RespuestaDto respuestaDto;
    private UsuarioDto usuarioDto;
    private UsuarioDao usuarioDao;
    
    // ***** Atributos
    private GridBagConstraints laVista_dimensiones;
    private Integer item;
    private ImageIcon portada;
    private List<RespuestaDto> lstRespuestas;
    
    // ***** Controlador
    public CtrlCardPregunta(PreguntaDto preguntaDto, ProductoDto productoDto, UsuarioDto usuarioDto) {
        // * Instaciar objetos recibidos por parametros
        this.preguntaDto = preguntaDto;
        this.productoDto = productoDto;
        this.usuarioDto = usuarioDto;
        
        // * Instaciar obligatorio
        this.productoDao = new ProductoDao();
        this.pnCardPregunta = new PanelCardPregunta();
        this.preguntaDao = new PreguntaDao();
        this.usuarioDao = new UsuarioDao();
        this.respuestaDao = new RespuestaDao();
        this.respuestaDto = new RespuestaDto();
        this.laVista_dimensiones = new GridBagConstraints();
    }
    
    // ***** Eventos
    private void mtdEventoBtnPreguntar(){
        MouseListener evtBtnPreguntar = null;
        pnCardPregunta.btnRespCons.removeMouseListener(evtBtnPreguntar);
        
        evtBtnPreguntar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                CtrlModalHacerPregunta preguntar = new CtrlModalHacerPregunta(productoDto);
                preguntar.mtdInit();
            }
        };
        
        pnCardPregunta.btnRespCons.addMouseListener(evtBtnPreguntar);
    }
    
    private void mtdEventoBtnEliminar(){
        MouseListener evtBtnEliminar = null;
        pnCardPregunta.btnEliminarPregunta.removeMouseListener(evtBtnEliminar);
        
        evtBtnEliminar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEliminarPregunta();
            }
        };
        
        pnCardPregunta.btnEliminarPregunta.addMouseListener(evtBtnEliminar);
    }
    
    private void mtdEventoBtnContestar(){
        MouseListener evtBtnContestar = null;
        pnCardPregunta.btnRespCons.removeMouseListener(evtBtnContestar);
        
        evtBtnContestar = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
               mtdContestarPregunta();
            }
        };
        
        pnCardPregunta.btnRespCons.addMouseListener(evtBtnContestar);
    }
    
    // ***** Metodos
    public void mtdInit(){
        mtdEstablecerDatos();
        mtdEstablecerImagen();
        mtdEstablecerEventos();
        mtdEstablecerDimensiones();
    }
    
    private void mtdEstablecerDimensiones(){
        laVista_dimensiones.gridx = 0; // Columna 
        laVista_dimensiones.gridy = item; // Fila
        laVista_dimensiones.gridheight = 1; // Cantidad de columnas a ocupar
        laVista_dimensiones.gridwidth = 1; // Cantidad de filas a ocupar
        laVista_dimensiones.weightx = 0.0; // Estirar en ancho
        laVista_dimensiones.weighty = 0.0;// Estirar en alto
        laVista_dimensiones.insets = new Insets(30, 0, 30, 0);  //top padding
        laVista_dimensiones.fill = GridBagConstraints.BOTH; // El modo de estirar
        //laVista.setVisible(true);
    }
    
    private void mtdEstablecerDatos(){
        // * Establecer datos en los campos
        pnCardPregunta.etqTitulo.setText( productoDto.getProdTitulo() );
        pnCardPregunta.cmpVendedor.setText( usuarioDto.getCmpNombreCompleto() );
        pnCardPregunta.cmpFecha.setText( preguntaDto.getPregFecha() );
        
        // * Establecer Pregunta
        String detallePregunta = pnCardPregunta.cmpDetallePregunta.getText();
        detallePregunta = detallePregunta.replace("<Pregunta>", preguntaDto.getPregPregunta());
        
        // * Obtener todas las respuestas
        String respuestas = "";
        respuestaDto.setRespPregunta( preguntaDto.getPregID() );
        respuestaDto.setRespProducto( preguntaDto.getPregProducto() );
        lstRespuestas = respuestaDao.mtdListar(respuestaDto);
        
        // * Ordenar todas las respuestas
        if( lstRespuestas.size() > 0 ){
            for (int i = 0; i < lstRespuestas.size(); i++) {
                respuestas += lstRespuestas.get(i).getRespFecha() + " : ";
                respuestas += lstRespuestas.get(i).getRespRespuesta();
                respuestas += "\n[...]\n";
            }
        }
        
        // * Establecer respuestas
        detallePregunta = detallePregunta.replace("<Respuesta>", lstRespuestas.size() == 0 ? "Sin respuesta" : respuestas );
        pnCardPregunta.cmpDetallePregunta.setText(detallePregunta);
        
        // * Establecer los botones
        if( preguntaDto.getPregVendedor().equals(Veontec.usuarioDto.getCmpID()) ){
            pnCardPregunta.btnEliminarPregunta.setEnabled(false);
            pnCardPregunta.btnEliminarPregunta.setVisible(false);
            pnCardPregunta.btnRespCons.setTexto("Contestar +1");
        }else{
            pnCardPregunta.btnEliminarPregunta.setTexto("Eliminar");
            pnCardPregunta.btnRespCons.setTexto("Preguntar +1");
        }
    }
    
    private void mtdEstablecerEventos(){
        if( preguntaDto.getPregVendedor().equals(Veontec.usuarioDto.getCmpID()) ){
            pnCardPregunta.mtdBackgroundVendedor();
            mtdEventoBtnContestar();
        }else{
            pnCardPregunta.mtdBackgroundComprador();
            mtdEventoBtnEliminar();
            mtdEventoBtnPreguntar();
        }
    }
    
    private void mtdEstablecerImagen(){
        if( productoDto.getProdImg() != null ){
            // * Establecer imagen de portada
            try {
                byte[] img = productoDto.getProdImg();
                BufferedImage buffimg = null;
                InputStream inputimg = new ByteArrayInputStream(img);
                buffimg = ImageIO.read(inputimg);
                pnCardPregunta.pnImgPortada.removeAll();
                portada = new ImageIcon(buffimg.getScaledInstance(pnCardPregunta.pnImgPortada.getWidth(), pnCardPregunta.pnImgPortada.getHeight(), Image.SCALE_SMOOTH));
                JLabel iconocc = new JLabel(portada);
                iconocc.setBounds(0, 0, pnCardPregunta.pnImgPortada.getWidth(), pnCardPregunta.pnImgPortada.getHeight());
                iconocc.setSize(pnCardPregunta.pnImgPortada.getWidth(), pnCardPregunta.pnImgPortada.getHeight());
                iconocc.setLocation(0, 0);
                iconocc.setPreferredSize(new Dimension(pnCardPregunta.pnImgPortada.getWidth(), pnCardPregunta.pnImgPortada.getHeight()));
                pnCardPregunta.pnImgPortada.add(iconocc);
                //laVista.updateUI();
                pnCardPregunta.validate();
                pnCardPregunta.revalidate();
                pnCardPregunta.repaint();
                
            } catch (Exception e) {
            }
        }
    }
    
    private void mtdEliminarPregunta(){
        int opc  = JOptionPane.showConfirmDialog(pnCardPregunta, 
                "¿Seguro que deseas eliminar la pregunta?", 
                "Eliminar pregunta", JOptionPane.YES_NO_OPTION);
        
        if( opc == JOptionPane.YES_OPTION ){
            if( preguntaDao.mtdRemover(preguntaDto) ){
                CtrlPreguntas.mtdRecargarPreguntas();
                JOptionPane.showMessageDialog(pnCardPregunta, "Pregunta eliminada exitosamente.");
            }
        }
        
    }
    
    private void mtdContestarPregunta(){
        String respuesta = JOptionPane.showInputDialog(pnCardPregunta,"Responder", productoDto.getProdTitulo(), JOptionPane.QUESTION_MESSAGE);
        if( respuesta != null && !respuesta.trim().isEmpty() ){
            RespuestaDto dto = new RespuestaDto();
            
            dto.setRespPregunta( preguntaDto.getPregID() );
            dto.setRespProducto( preguntaDto.getPregProducto() );
            dto.setRespComprador( preguntaDto.getPregComprador() );
            dto.setRespVendedor(preguntaDto.getPregVendedor() );
            dto.setRespRespuesta(respuesta);
            dto.setRespFecha("89/12/1020");
            dto.setRespEstado(1);
            
            if( respuestaDao.mtdInsetar(dto) ){
                try { CtrlPreguntas.mtdRecargarPreguntas(); } catch (Exception e) { }
                JOptionPane.showMessageDialog(pnCardPregunta, "Respuesta enviado exitosamente.");
            }
            
        }
    }

    public PanelCardPregunta getPnCardPregunta() {
        return pnCardPregunta;
    }

    public void setPnCardPregunta(PanelCardPregunta pnCardPregunta) {
        this.pnCardPregunta = pnCardPregunta;
    }

    public GridBagConstraints getTarjeta_dimensiones() {
        return laVista_dimensiones;
    }

    public void setTarjeta_dimensiones(GridBagConstraints laVista_dimensiones) {
        this.laVista_dimensiones = laVista_dimensiones;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }
    
}
