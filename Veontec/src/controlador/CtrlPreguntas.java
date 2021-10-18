package controlador;

import controlador.componentes.CtrlCardPregunta;
import index.Veontec;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.dao.PreguntaDao;
import modelo.dao.ProductoDao;
import modelo.dao.UsuarioDao;
import modelo.dto.PreguntaDto;
import modelo.dto.ProductoDto;
import modelo.dto.UsuarioDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vista.paneles.PanelPreguntas;

public class CtrlPreguntas{
    
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
    private Integer pagProductos;
    private Integer totalProductosExistentes;
    private Integer productoPorPagina;
    private boolean tipoBusqueda;
    private static final Logger LOG = Logger.getLogger(CtrlBienvenida.class.getName());
    
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
        this.pagProductos = 0;
        this.productoPorPagina = 3;
        this.tipoBusqueda = false;
    }
    
    // * Eventos    
    private void mtdEventoBtnBuscar(){
        laVista.btnBuscar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEstabecerBusqueda();
                mtdMostrarPreguntas(tipoBusqueda);
            }
        });
    }
    
    private void mtdEventoCmpBuscarProducto(){
        laVista.cmpBusqueda.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                    mtdEstabecerBusqueda();
                    mtdMostrarPreguntas(tipoBusqueda);
                } 
            }
        });
    }
    
    private void mtdEventoBtnPrevia(){
        laVista.btnPrevia.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdMostrarProductosPrevias();
            }
        });
    }
    
    private void mtdEventoBtnSiguiente(){
        laVista.btnSiguiente.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdMostrarProductosSiguiente();
            }
        });
    }
        
    
    // * Métodos
    public static CtrlPreguntas getInstancia(PanelPreguntas laVista, UsuarioDto dto, UsuarioDao dao){
        logger.warn("Inicializando controlador.... ");
        if( instancia == null ){
            logger.warn("Creando instancia.... ");
            instancia = new CtrlPreguntas(laVista, dto, dao);
            instancia.mtdInit();
        
        }else{
            instancia.mtdMostrarPreguntas(false);
        
        }
        
        return instancia;
    }
    
    public static boolean mtdRecargarPreguntas(){
        instancia.mtdMostrarPreguntas(false);
        return true;
    }
    
    private void mtdInit(){
        logger.info("Ejecutando metodo una vez (Obligatorio)");
        mtdEventoBtnBuscar();
        mtdEventoBtnPrevia();
        mtdEventoBtnSiguiente();
        mtdEventoCmpBuscarProducto();
        mtdMostrarPreguntas(false);
    }
    
    private void mtdMostrarPreguntas(boolean busqueda){
        logger.info("Iniciando ...");
        int totalPreguntas = 0;
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        logger.info("Listando preguntas...");
        preguntaDto.setPregComprador( Veontec.usuarioDto.getCmpID() );
        preguntaDto.setPregVendedor( Veontec.usuarioDto.getCmpID() );
        
        
        if( busqueda == false ){
            lstPreguntas = preguntaDao.mtdListar(preguntaDto ,productoPorPagina, pagProductos);
            totalProductosExistentes = Integer.parseInt(""+preguntaDao.mtdRowCount(preguntaDto));
        }else{
            preguntaDto.setPregTitulo('%'+laVista.cmpBusqueda.getText()+'%');
            lstPreguntas = preguntaDao.mtdBuscarAllPreguntasSimilares(preguntaDto, productoPorPagina, pagProductos);
            totalProductosExistentes = Integer.parseInt(""+preguntaDao.mtdRowCountAllPreguntasSimilares(preguntaDto));
        }
        
        totalPreguntas = lstPreguntas.size();
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
    
    private void mtdMostrarProductosPrevias(){
        pagProductos -= productoPorPagina;
        //pagProductos = pagProductos <= 0 ? 0 : pagProductos;
        //LOG.info("Productos previas : " + pagProductos );
        
        if( pagProductos < 0  ){
            pagProductos = 0;
            JOptionPane.showMessageDialog(laVista, "No hay más productos para mostrar");
            laVista.btnPrevia.setEnabled(false);
            return;
        }
        
        laVista.btnSiguiente.setEnabled(true);
        mtdMostrarPreguntas(tipoBusqueda);
        
    }
    
    private void mtdMostrarProductosSiguiente(){
        pagProductos += productoPorPagina;
        //pagProductos = pagProductos >= totalProductosExistentes ? totalProductosExistentes: pagProductos;
        //LOG.info("Productos siguientes : " + pagProductos );
        
        if( pagProductos >= totalProductosExistentes ){
            pagProductos = totalProductosExistentes;
            JOptionPane.showMessageDialog(laVista, "No hay más productos para mostrar");
            laVista.btnSiguiente.setEnabled(false);
            return;
        }
        
        laVista.btnPrevia.setEnabled(true);
        mtdMostrarPreguntas(tipoBusqueda);
        
    }
    
    private void mtdEstabecerBusqueda(){
        laVista.btnPrevia.setEnabled(true);
        laVista.btnSiguiente.setEnabled(true);
        
        pagProductos=0;
        if( laVista.cmpBusqueda.getText().trim().isEmpty() || laVista.cmpBusqueda.isVacio() ){
            tipoBusqueda = false;
        }else{
            tipoBusqueda = true;
        }
        System.out.println("busquedaProductos " + tipoBusqueda);
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
