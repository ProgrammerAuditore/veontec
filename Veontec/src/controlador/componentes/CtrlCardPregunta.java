package controlador.componentes;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;
import modelo.dao.PreguntaDao;
import modelo.dto.PreguntaDto;
import vista.paneles.componentes.PanelCardPregunta;
import vista.paneles.componentes.PanelCardRespPreg;

public class CtrlCardPregunta {
    
    // ***** Vista
    PanelCardPregunta laVista;
    
    // ***** Modelo
    PreguntaDao preguntaDao;
    PreguntaDto preguntaDto;
    
    // ***** Atributos
    private GridBagConstraints tarjeta_dimensiones;
    private Integer item;
    private ImageIcon portada;
    
    // ***** Controlador
    public CtrlCardPregunta(PreguntaDto preguntaDto) {
        this.preguntaDto = preguntaDto;
        this.laVista = new PanelCardPregunta();
        this.preguntaDao = new PreguntaDao();
        this.tarjeta_dimensiones = new GridBagConstraints();
    }
    
    // ***** Eventos
    
    
    // ***** Metodos
    public void mtdInit(){
        mtdEstablecerDatos();
        mtdEstablecerDimensiones();
    }
    
    private void mtdEstablecerDimensiones(){
        tarjeta_dimensiones.gridx = 0; // Columna 
        tarjeta_dimensiones.gridy = item; // Fila
        tarjeta_dimensiones.gridheight = 1; // Cantidad de columnas a ocupar
        tarjeta_dimensiones.gridwidth = 1; // Cantidad de filas a ocupar
        tarjeta_dimensiones.weightx = 0.0; // Estirar en ancho
        tarjeta_dimensiones.weighty = 0.0;// Estirar en alto
        tarjeta_dimensiones.insets = new Insets(30, 0, 30, 0);  //top padding
        tarjeta_dimensiones.fill = GridBagConstraints.BOTH; // El modo de estirar
        //tarjeta.setVisible(true);
    }
    
    private void mtdEstablecerDatos(){
        laVista.cmpDetalleCompra.setText( preguntaDto.getPregPregunta() );
        laVista.cmpFecha.setText( preguntaDto.getPregFecha() );
    }

    public PanelCardPregunta getLaVista() {
        return laVista;
    }

    public void setLaVista(PanelCardPregunta laVista) {
        this.laVista = laVista;
    }

    public GridBagConstraints getTarjeta_dimensiones() {
        return tarjeta_dimensiones;
    }

    public void setTarjeta_dimensiones(GridBagConstraints tarjeta_dimensiones) {
        this.tarjeta_dimensiones = tarjeta_dimensiones;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }
    
}
