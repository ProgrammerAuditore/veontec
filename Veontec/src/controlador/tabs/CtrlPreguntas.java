package controlador.tabs;

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
import src.Info;
import vista.paneles.PanelPreguntas;

public class CtrlPreguntas{
    
    // ***** Vista
    PanelPreguntas laVista;
    
    // ***** Modelo
    private ProductoDao producto_dao;
    private ProductoDto producto_dto;
    private PreguntaDao preguntaDao;
    private PreguntaDto preguntaDto;
    private UsuarioDao usuarioDao;
    private UsuarioDto usuarioDto;
    
    // ***** Atributos
    private static CtrlPreguntas instancia;
    private List<PreguntaDto> lstPreguntas;
    Integer usuarioID;
    private Integer cantidadResultado;
    private Integer totalProductosExistentes;
    private Integer cantidadPorPagina;
    private boolean activarBusqueda;
    private static final Logger LOG = Logger.getLogger(CtrlBienvenida.class.getName());
    
    // ***** Constructor
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
        this.cantidadResultado = 0;
        this.cantidadPorPagina = Info.veontecResultadoPorPagina;
        this.activarBusqueda = false;
    }
    
    // ***** Eventos    
    private void mtdEventoBtnBuscar(){
        laVista.btnBuscar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                mtdEstabecerBusqueda();
                mtdMostrarPreguntas(activarBusqueda);
            }
        });
    }
    
    private void mtdEventoCmpBuscarProducto(){
        laVista.cmpBusqueda.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                    mtdEstabecerBusqueda();
                    mtdMostrarPreguntas(activarBusqueda);
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
        
    
    // ***** Métodos
    public static CtrlPreguntas getInstancia(PanelPreguntas laVista, UsuarioDto dto, UsuarioDao dao){
        LOG.warning("Inicializando controlador.... ");
        if( instancia == null ){
            LOG.warning("Creando instancia.... ");
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
        LOG.info("Ejecutando metodo una vez (Obligatorio)");
        mtdEventoBtnBuscar();
        mtdEventoBtnPrevia();
        mtdEventoBtnSiguiente();
        mtdEventoCmpBuscarProducto();
        mtdMostrarPreguntas(false);
    }
    
    private void mtdMostrarPreguntas(boolean busqueda){
        LOG.info("Iniciando ...");
        
        int totalPreguntas = 0;
        laVista.pnContenedor.setLayout(new GridBagLayout());
        laVista.pnContenedor.removeAll();
        
        LOG.info("Listando preguntas...");
        preguntaDto.setPregComprador( Veontec.usuarioDto.getCmpID() );
        preguntaDto.setPregVendedor( Veontec.usuarioDto.getCmpID() );
        
        
        if( busqueda == false ){
            lstPreguntas = preguntaDao.mtdListar(preguntaDto ,cantidadPorPagina, cantidadResultado);
            totalProductosExistentes = Integer.parseInt(""+preguntaDao.mtdRowCountAllPreguntasPorUsuario(preguntaDto));
        }else{
            preguntaDto.setPregTitulo('%'+laVista.cmpBusqueda.getText()+'%');
            lstPreguntas = preguntaDao.mtdBuscarAllPreguntasPorUsuarioSimilares(preguntaDto, cantidadPorPagina, cantidadResultado);
            totalProductosExistentes = Integer.parseInt(""+preguntaDao.mtdRowCountAllPreguntasPorUsuarioSimilares(preguntaDto));
        }
        
        totalPreguntas = lstPreguntas.size();
        if( totalPreguntas > 0 ){
            
            LOG.warning("Recorriendo productos ....");
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
        cantidadResultado -= cantidadPorPagina;
        //pagProductos = cantidadResultado <= 0 ? 0 : cantidadResultado;
        //LOG.info("Productos previas : " + cantidadResultado );
        
        if( cantidadResultado < 0  ){
            cantidadResultado = 0;
            JOptionPane.showMessageDialog(laVista, "No hay más resultados por mostrar.");
            laVista.btnPrevia.setEnabled(false);
            return;
        }
        
        laVista.btnSiguiente.setEnabled(true);
        mtdMostrarPreguntas(activarBusqueda);
        
    }
    
    private void mtdMostrarProductosSiguiente(){
        cantidadResultado += cantidadPorPagina;
        //pagProductos = cantidadResultado >= totalProductosExistentes ? totalProductosExistentes: cantidadResultado;
        //LOG.info("Productos siguientes : " + cantidadResultado );
        
        if( cantidadResultado >= totalProductosExistentes ){
            cantidadResultado = totalProductosExistentes;
            JOptionPane.showMessageDialog(laVista, "No hay más resultados por mostrar.");
            laVista.btnSiguiente.setEnabled(false);
            return;
        }
        
        laVista.btnPrevia.setEnabled(true);
        mtdMostrarPreguntas(activarBusqueda);
        
    }
    
    private void mtdEstabecerBusqueda(){
        laVista.btnPrevia.setEnabled(true);
        laVista.btnSiguiente.setEnabled(true);
        
        cantidadResultado=0;
        if( laVista.cmpBusqueda.getText().trim().isEmpty() || laVista.cmpBusqueda.isVacio() ){
            activarBusqueda = false;
        }else{
            activarBusqueda = true;
        }
    }
    
    public static void mtdEliminarInstancia(){
        instancia = null;
    }
    
}
