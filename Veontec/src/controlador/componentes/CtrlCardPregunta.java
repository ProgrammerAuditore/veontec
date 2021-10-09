package controlador.componentes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import modelo.dao.PreguntaDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.PreguntaDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import vista.paneles.componentes.PanelCardPregunta;

public class CtrlCardPregunta {
    
    // ***** Vista
    PanelCardPregunta laVista;
    
    // ***** Modelo
    PreguntaDao preguntaDao;
    PreguntaDto preguntaDto;
    ProductoDto productoDto;
    ProductoDao productoDao;
    UsuarioDto usuarioDto;
    UsuarioDao usuarioDao;
    
    // ***** Atributos
    private GridBagConstraints laVista_dimensiones;
    private Integer item;
    private ImageIcon portada;
    
    // ***** Controlador
    public CtrlCardPregunta(PreguntaDto preguntaDto, ProductoDto productoDto, UsuarioDto usuarioDto) {
        // * Instaciar objetos recibidos por parametros
        this.preguntaDto = preguntaDto;
        this.productoDto = productoDto;
        this.usuarioDto = usuarioDto;
        
        // * Instaciar obligatorio
        this.productoDao = new ProductoDao();
        this.laVista = new PanelCardPregunta();
        this.preguntaDao = new PreguntaDao();
        this.usuarioDao = new UsuarioDao();
        this.laVista_dimensiones = new GridBagConstraints();
    }
    
    // ***** Eventos
    
    
    // ***** Metodos
    public void mtdInit(){
        mtdEstablecerImagen();
        mtdEstablecerDatos();
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
        laVista.etqTitulo.setText( productoDto.getProdTitulo() );
        laVista.cmpVendedor.setText( usuarioDto.getCmpNombreCompleto() );
        laVista.cmpDetalleCompra.setText( preguntaDto.getPregPregunta() );
        laVista.cmpFecha.setText( preguntaDto.getPregFecha() );
    }
    
    private void mtdEstablecerImagen(){
        if( productoDto.getProdImg() != null ){
            // * Establecer imagen de portada
            try {
                byte[] img = productoDto.getProdImg();
                BufferedImage buffimg = null;
                InputStream inputimg = new ByteArrayInputStream(img);
                buffimg = ImageIO.read(inputimg);
                laVista.pnImgPortada.removeAll();
                portada = new ImageIcon(buffimg.getScaledInstance(laVista.pnImgPortada.getWidth(), laVista.pnImgPortada.getHeight(), Image.SCALE_SMOOTH));
                JLabel iconocc = new JLabel(portada);
                iconocc.setBounds(0, 0, laVista.pnImgPortada.getWidth(), laVista.pnImgPortada.getHeight());
                iconocc.setSize(laVista.pnImgPortada.getWidth(), laVista.pnImgPortada.getHeight());
                iconocc.setLocation(0, 0);
                iconocc.setPreferredSize(new Dimension(laVista.pnImgPortada.getWidth(), laVista.pnImgPortada.getHeight()));
                laVista.pnImgPortada.add(iconocc);
                //laVista.updateUI();
                laVista.validate();
                laVista.revalidate();
                laVista.repaint();
                
            } catch (Exception e) {
            }
        }
    }

    public PanelCardPregunta getLaVista() {
        return laVista;
    }

    public void setLaVista(PanelCardPregunta laVista) {
        this.laVista = laVista;
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
