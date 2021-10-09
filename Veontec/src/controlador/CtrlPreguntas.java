package controlador;

import controlador.componentes.CtrlCardPregunta;
import index.Veontec;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import modelo.dao.PreguntaDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.PreguntaDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import vista.paneles.PanelPreguntas;

public class CtrlPreguntas implements MouseListener{
    
    // * Vista
    PanelPreguntas laVista;
    
    // * Modelo
    private ProductoDao producto_dao;
    private ProductoDto producto_dto;
    private PreguntaDao preguntaDao;
    private PreguntaDto preguntaDto;
    private UsuarioDao usuarioDao;
    private UsuarioDto usuarioDto;
    
    // * Atributos
    private static CtrlPreguntas instancia;
    private List<PreguntaDto> lstPreguntas;
    
    // * Constructor
    private CtrlPreguntas(PanelPreguntas laVista, UsuarioDto dto, UsuarioDao dao){
        // * Se recibe por parametros
        this.laVista = laVista;
        this.usuarioDto = dto;
        this.usuarioDao = dao;
        
        // * Es necesario instanciarlos
        this.preguntaDto = new PreguntaDto();
        this.preguntaDao = new PreguntaDao();
        this.producto_dto = new ProductoDto();
        this.producto_dao = new ProductoDao();
    }
    
    // * Eventos
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    // * Métodos
    public static CtrlPreguntas getInstancia(PanelPreguntas laVista, UsuarioDto dto, UsuarioDao dao){
        
        if( instancia == null ){
            instancia = new CtrlPreguntas(laVista, dto, dao);
            instancia.mtdInit();
        }
        
        return instancia;
    }
    
    private void mtdInit(){
        laVista.pnContenedor.setLayout(new GridBagLayout());
        mtdMostrarPreguntas();
    }
    
    private void mtdMostrarPreguntas(){
        laVista.pnContenedor.removeAll();
        laVista.pnContenedor.setLayout(new GridBagLayout());
        
        preguntaDto.setPregComprador( Veontec.usuarioDto.getCmpID() );
        preguntaDto.setPregVendedor( Veontec.usuarioDto.getCmpID() );
        lstPreguntas = preguntaDao.mtdListar(preguntaDto);
        int totalPreguntas = lstPreguntas.size();
        
        if( totalPreguntas > 0 ){
            
            for (int i = 0; i < totalPreguntas; i++) {
                System.out.println("Pregunta : " + i + ": \n " + lstPreguntas.get(i).toString());
                CtrlCardPregunta card_pregunta = new CtrlCardPregunta( lstPreguntas.get(i) );
                card_pregunta.setItem(i);
                card_pregunta.mtdInit();
                laVista.pnContenedor.add(card_pregunta.getLaVista(), card_pregunta.getTarjeta_dimensiones() );
            }
            
        }
        
        laVista.pnContenedor.validate();
        laVista.pnContenedor.revalidate();
        laVista.pnContenedor.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
