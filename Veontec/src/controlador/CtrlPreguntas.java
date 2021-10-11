package controlador;

import static controlador.CtrlVentas.logger;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    static Log logger = LogFactory.getLog(CtrlPreguntas.class);
    private static CtrlPreguntas instancia;
    private List<PreguntaDto> lstPreguntas;
    Integer usuarioID;
    
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
        logger.warn("Inicializando controlador.... ");
        if( instancia == null ){
            logger.warn("Creando instancia.... ");
            instancia = new CtrlPreguntas(laVista, dto, dao);
            instancia.mtdInit();
        
        }else{
            instancia.mtdMostrarPreguntas();
        
        }
        
        return instancia;
    }
    
    public static boolean mtdRecargarPreguntas(){
        instancia.mtdMostrarPreguntas();
        return true;
    }
    
    private void mtdInit(){
        logger.info("Ejecutando metodo una vez (Obligatorio)");
        mtdMostrarPreguntas();
    }
    
    private void mtdMostrarPreguntas(){
        logger.info("Iniciando ...");
        laVista.pnContenedor.removeAll();
        laVista.pnContenedor.setLayout(new GridBagLayout());
        
        logger.info("Listando preguntas...");
        preguntaDto.setPregComprador( Veontec.usuarioDto.getCmpID() );
        preguntaDto.setPregVendedor( Veontec.usuarioDto.getCmpID() );
        lstPreguntas = preguntaDao.mtdListar(preguntaDto);
        int totalPreguntas = lstPreguntas.size();
        
        if( totalPreguntas > 0 ){
            
            logger.warn("Recorriendo productos ....");
            for (int i = 0; i < totalPreguntas; i++) {
                producto_dto = producto_dao.mtdConsultar( lstPreguntas.get(i).getPregProducto() );
                
                // * Verificar si soy un vendedor 
                // Si lo soy, muestrame al comprador; en caso contrario muestrame al vendedor
                if( lstPreguntas.get(i).getPregVendedor().equals(Veontec.usuarioDto.getCmpID()) ){
                    usuarioID = lstPreguntas.get(i).getPregComprador();
                }else{
                    usuarioID = lstPreguntas.get(i).getPregVendedor();
                }
                
                usuarioDto = usuarioDao.mtdConsultar( usuarioID );
                
                if( producto_dto != null && usuarioDto != null ){
                    CtrlCardPregunta card_pregunta = new CtrlCardPregunta( lstPreguntas.get(i), producto_dto, usuarioDto );
                    card_pregunta.setItem(i);
                    card_pregunta.mtdInit();
                    laVista.pnContenedor.add(card_pregunta.getLaVista(), card_pregunta.getTarjeta_dimensiones() );
                }
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
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
